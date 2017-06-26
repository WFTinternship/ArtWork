package am.aca.wftartproject.web;

import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.model.User;
import am.aca.wftartproject.service.ArtistService;
import am.aca.wftartproject.service.UserService;
import am.aca.wftartproject.service.impl.ArtistServiceImpl;
import am.aca.wftartproject.service.impl.UserServiceImpl;
import am.aca.wftartproject.util.SpringBean;
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
 * Created by Armen on 6/26/2017.
 */
@Controller
public class LogInController {
    @Autowired
    UserService userService;
    @Autowired
    ArtistService artistService;
    HttpSession session;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView showLogin(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("logIn");
        return mav;
    }

    @RequestMapping(value = "/loginProcess", method = RequestMethod.POST)
    public ModelAndView loginProcess(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = null;
        String userEmailStr = request.getParameter("email");
        String userPasswordStr = request.getParameter("password");
        try {
            User userFromDB = userService.login(userEmailStr, userPasswordStr);
            Artist artistFromDB = artistService.findArtist(userFromDB.getId());
            if (artistFromDB != null) {
                session = request.getSession(true);
                session.setAttribute("user", artistFromDB);
                Cookie userEmail = new Cookie("userEmail", artistFromDB.getEmail());
                userEmail.setMaxAge(3600);    // 60 minutes
                response.addCookie(userEmail);
                mav = new ModelAndView("index");
            } else if (userFromDB != null) {
                session = request.getSession(true);
                session.setAttribute("user", userFromDB);
                Cookie userEmail = new Cookie("userEmail", userFromDB.getEmail());
                userEmail.setMaxAge(3600);    // 60 minutes
                response.addCookie(userEmail);
                mav = new ModelAndView("index");
            } else {
                throw new RuntimeException();
            }
        } catch (RuntimeException e) {
            String userNotExists = "The user with the entered username and password does not exists.";
            request.setAttribute("errorMessage", userNotExists);
            mav = new ModelAndView("logIn");
        }

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
        return new ModelAndView("index");
    }

}