package am.aca.wftartproject.controller;

import am.aca.wftartproject.model.AbstractUser;
import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.model.User;
import am.aca.wftartproject.service.ArtistService;
import am.aca.wftartproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Armen on 6/26/2017
 */
@Controller
public class LogInController {

    private final UserService userService;
    private final ArtistService artistService;

    @Autowired
    public LogInController(UserService userService, ArtistService artistService) {
        this.userService = userService;
        this.artistService = artistService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView showLogin(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("logIn");
    }

    @RequestMapping(value = "/loginProcess", method = RequestMethod.POST)
    public ModelAndView loginProcess(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(true);
        ModelAndView mav;
        String userEmailStr = request.getParameter("email");
        String userPasswordStr = request.getParameter("password");

        try {
            User userFromDB = userService.login(userEmailStr, userPasswordStr);
            Artist artistFromDB = artistService.findArtist(userFromDB.getId());

            if (artistFromDB != null) {
                setAttributeInSessionAndCreateCookie(artistFromDB, request, response, session);
            } else if (userFromDB != null) {
                setAttributeInSessionAndCreateCookie(userFromDB, request, response, session);
            } else {
                throw new RuntimeException();
            }

            mav = new ModelAndView("index");
        } catch (RuntimeException e) {
            String userNotExists = "The user with the entered username and password does not exists.";
            request.setAttribute("errorMessage", userNotExists);
            mav = new ModelAndView("logIn");
        }

        return mav;
    }

    @RequestMapping(value = "/logoutProcess", method = RequestMethod.GET)
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            Cookie cookie = new Cookie("userEmail", "");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            session.setAttribute("user", null);
            session.invalidate();
        }

        return new ModelAndView("index");
    }

    void setAttributeInSessionAndCreateCookie(AbstractUser abstractUser, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        session.setAttribute("user", abstractUser);
        Cookie userEmail = new Cookie("userEmail", abstractUser.getEmail());
        userEmail.setMaxAge(3600);    // 60 minutes
        response.addCookie(userEmail);
    }
}
