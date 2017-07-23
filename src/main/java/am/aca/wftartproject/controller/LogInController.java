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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Armen on 6/26/2017
 */
@Controller
public class LogInController {

    private HttpSession session;
    private UserService userService;
    private ArtistService artistService;
    private static final String MESSAGE_ATTR = "message";

    @Autowired
    public LogInController(UserService userService, ArtistService artistService) {
        this.userService = userService;
        this.artistService = artistService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView showLogin() {
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/loginProcess", method = RequestMethod.POST)
    public ModelAndView loginProcess(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView();
        session = request.getSession(true);
        String page;

        // Get email and password parameters from request
        String userEmailStr = request.getParameter("email");
        String userPasswordStr = request.getParameter("password");

        // Get user/artist from db and create cookie with their info
        try {
            User userFromDB = userService.login(userEmailStr, userPasswordStr);
            Artist artistFromDB = artistService.findArtist(userFromDB.getId());

            if (artistFromDB != null) {
                setAttributeInSessionAndCreateCookie(artistFromDB, response, session);
            } else if (userFromDB != null) {
                setAttributeInSessionAndCreateCookie(userFromDB, response, session);
            }
            page = "redirect:/home";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute(MESSAGE_ATTR,
                    "The user with the entered username and password does not exists.");
            page = "redirect:/login";
        }
        mav.setViewName(page);
        return mav;
    }

    @RequestMapping(value = "/logoutProcess", method = RequestMethod.GET)
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
        session = request.getSession(false);

        if (session != null) {
            Cookie cookie = new Cookie("userEmail", "");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            session.setAttribute("user", null);
            session.invalidate();
        }
        return new ModelAndView("home");
    }


    private void setAttributeInSessionAndCreateCookie(AbstractUser abstractUser, HttpServletResponse response, HttpSession session) {
        session.setAttribute("user", abstractUser);
        Cookie userEmail = new Cookie("userEmail", abstractUser.getEmail());
        userEmail.setMaxAge(3600);    // 60 minutes
        response.addCookie(userEmail);
    }
}
