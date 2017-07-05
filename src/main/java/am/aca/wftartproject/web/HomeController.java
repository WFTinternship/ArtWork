package am.aca.wftartproject.web;

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

    @RequestMapping(value = {"/", "index"},method = RequestMethod.GET)
    public ModelAndView welcome(HttpServletRequest request, HttpServletResponse response) {

//       cookies = request.getCookies();
//        String userEmailFromCookie = null;
//        if (cookies != null) {
//            for (Cookie ckElement : cookies) {
//                if (ckElement.getName().equals("userEmail")) {
//                    userEmailFromCookie = ckElement.getValue();
//                }
//            }
//        }
//
//        if (userEmailFromCookie != null) {
//            if (artistService.findArtist(userEmailFromCookie) != null) {
//                Artist artist = artistService.findArtist(userEmailFromCookie);
//                HttpSession session = request.getSession(true);
//                session.setAttribute("user", artist);
//            } else {
//                if (userService.findUser(userEmailFromCookie) != null) {
//                    User user = userService.findUser(userEmailFromCookie);
//                    HttpSession session = request.getSession(true);
//                    session.setAttribute("user", user);
//                }
//            }
//        }
        return new ModelAndView("index");
    }
}
