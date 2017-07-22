package am.aca.wftartproject.controller;

import am.aca.wftartproject.controller.helper.ControllerHelper;
import am.aca.wftartproject.entity.Artist;
import am.aca.wftartproject.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Armen on 6/26/2017
 */
@Controller
public class LogInController extends ControllerHelper {
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(LogInController.class);

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView showLogin() {

        //return to login page
        return new ModelAndView(LOGIN);
    }

    @RequestMapping(value = "/loginProcess", method = RequestMethod.POST)
    public ModelAndView loginProcess(HttpServletRequest request) {
        ModelAndView mav = null;
        Artist artistFromDB;
        User userFromDB = null;

        //retrieve user password and email from request
        String userEmailStr = request.getParameter("email");
        String userPasswordStr = request.getParameter("password");

        //try to log in, if log in successfull, set User attribute to session
        try {
            userFromDB = userService.login(userEmailStr, userPasswordStr);
            request.getSession(true).setAttribute(USER, userFromDB);
            return new ModelAndView(HOME);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

        //if the type of User attribute is artist  try to lg in as artist
        try {
            if (userFromDB == null) {
                artistFromDB = artistService.login(userEmailStr, userPasswordStr);
                request.getSession(true).setAttribute(USER, artistFromDB);
                return new ModelAndView(HOME);
            }
        } catch (Exception e) {
            String userNotExists = "The user with the entered username and password does not exists.";
            request.setAttribute("message", userNotExists);
            mav = new ModelAndView(LOGIN);
        }

        return mav;
    }

    @RequestMapping(value = "/logoutProcess", method = RequestMethod.GET)
    public ModelAndView logout(HttpServletRequest request) {

        //close session , set user valu to null and return to home page
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.setAttribute(USER, null);
            session.invalidate();
        }

        return new ModelAndView(HOME);
    }

}
