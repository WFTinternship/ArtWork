package am.aca.wftartproject.web;

import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.model.ArtistSpecialization;
import am.aca.wftartproject.model.User;
import am.aca.wftartproject.service.ArtistService;
import am.aca.wftartproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Armen on 6/26/2017.
 */
@MultipartConfig
@Controller
public class SignUpController {
    @Autowired
    UserService userService;
    @Autowired
    ArtistService artistService;

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public ModelAndView showRegister(HttpServletRequest request, HttpServletResponse respons) {
        ModelAndView mav = new ModelAndView("signUp");
        mav.addObject("user", new User());
        mav.addObject("artist", new Artist());
        mav.addObject("artistSpecTypes", ArtistSpecialization.values());
        return mav;
    }

    @RequestMapping(value = "/userRegister", method = RequestMethod.POST)
    public ModelAndView addUser(HttpServletRequest request, HttpServletResponse response,
                                @ModelAttribute("user") User user) {
        String page = "";
        try {
            userService.addUser(user);
            page = "index";
            HttpSession session = request.getSession(true);
            session.setAttribute("user", user);
            Cookie userEmail = new Cookie("userEmail", user.getEmail());
            userEmail.setMaxAge(3600);             // 60 minutes
            response.addCookie(userEmail);
        } catch (RuntimeException e) {
            String errorMessage = "The entered info is not correct";
            request.setAttribute("errorMessage", errorMessage);
            page = "/signUp";
        }
        return new ModelAndView(page, "user", user);
    }


    @RequestMapping(value = "/artistRegister", method = RequestMethod.POST)
    public ModelAndView addArtist(HttpServletRequest request, HttpServletResponse response,
                                  @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {
        Artist artistFromRequest = new Artist();
        if (!image.isEmpty()) {
            byte[] imageBytes = image.getBytes();
            artistFromRequest
                    .setSpecialization(ArtistSpecialization.valueOf(request.getParameter("artistSpec")))
                    .setArtistPhoto(imageBytes)
                    .setFirstName(request.getParameter("firstName"))
                    .setLastName(request.getParameter("lastName"))
                    .setAge(Integer.parseInt(request.getParameter("age")))
                    .setEmail(request.getParameter("email"))
                    .setPassword(request.getParameter("password"));
        }

        String page = "";
        try {

            artistService.addArtist(artistFromRequest);
            page = "/index";
            HttpSession session = request.getSession(true);
            session.setAttribute("user", artistFromRequest);
            Cookie userEmail = new Cookie("userEmail", artistFromRequest.getEmail());
            userEmail.setMaxAge(3600);             // 60 minutes
            response.addCookie(userEmail);
        } catch (RuntimeException e) {
            String errorMessage = "The entered info is not correct";
            request.setAttribute("errorMessage", errorMessage);
            page = "/signUp";
        }

        return new ModelAndView(page, "user", artistFromRequest);
    }

}
