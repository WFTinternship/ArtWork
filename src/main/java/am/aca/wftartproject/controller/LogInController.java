package am.aca.wftartproject.controller;

import am.aca.wftartproject.entity.Artist;
import am.aca.wftartproject.entity.User;
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
 * Created by Armen on 6/26/2017.
 */
@Controller
public class LogInController {
    @Autowired
    private UserService userService;

    @Autowired
    private ArtistService artistService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView showLogin(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("logIn");
        return mav;
    }

    @RequestMapping(value = "/loginProcess", method = RequestMethod.POST)
    public ModelAndView loginProcess(HttpServletRequest request, HttpServletResponse response) {
        String userEmailStr = request.getParameter("email");
        String userPasswordStr = request.getParameter("password");
        ModelAndView mav = null;
        Artist artistFromDB = null;
        User userFromDB = null;
            try
            {
               userFromDB = userService.login(userEmailStr, userPasswordStr);
               request.getSession(true).setAttribute("user", userFromDB);
               return new ModelAndView("index");
            }
            catch (Exception e){}
            try{
                if(userFromDB==null)
                {
                    artistFromDB = artistService.login(userEmailStr,userPasswordStr);
                    request.getSession(true).setAttribute("user", artistFromDB);
                    return new ModelAndView("index");
                }
            }
            catch (Exception e)
            {
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
            session.setAttribute("user", null);
            session.invalidate();
        }
        return new ModelAndView("index");
    }

}
