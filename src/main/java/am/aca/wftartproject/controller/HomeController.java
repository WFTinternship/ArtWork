package am.aca.wftartproject.controller;

import am.aca.wftartproject.service.ArtistService;
import am.aca.wftartproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Armen on 6/26/2017.
 */
@Controller
public class HomeController {

    @Autowired
    UserService userService;
    @Autowired
    ArtistService artistService;
    Cookie[] cookies;

    @RequestMapping(value = {"/", "index"})
    public ModelAndView welcome(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("index");
    }
}
