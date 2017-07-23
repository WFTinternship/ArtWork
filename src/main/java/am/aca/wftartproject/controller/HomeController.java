package am.aca.wftartproject.controller;

import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.model.User;
import am.aca.wftartproject.service.ArtistService;
import am.aca.wftartproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Armen on 6/26/2017
 */
@Controller
public class HomeController {

    private final UserService userService;
    private final ArtistService artistService;

    @Autowired
    public HomeController(UserService userService, ArtistService artistService) {
        this.userService = userService;
        this.artistService = artistService;
    }

    @RequestMapping(value = {"/", "index"})
    public ModelAndView welcome(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        Cookie[] cookies = request.getCookies();
        String userEmailFromCookie = null;

        // Check and get cookie info, if any
        if (cookies != null) {
            for (Cookie ckElement : cookies) {
                if (ckElement.getName().equals("userEmail")) {
                    userEmailFromCookie = ckElement.getValue();
                }
            }
        }

        // Get or create session and set attribute with user/artist object
        if (userEmailFromCookie != null) {
            if (artistService.findArtist(userEmailFromCookie) != null) {
                Artist artist = artistService.findArtist(userEmailFromCookie);
                session.setAttribute("user", artist);
            } else {
                if (userService.findUser(userEmailFromCookie) != null) {
                    User user = userService.findUser(userEmailFromCookie);
                    session.setAttribute("user", user);
                }
            }
        }

        return new ModelAndView("index");
    }
}
