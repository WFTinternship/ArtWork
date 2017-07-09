package am.aca.wftartproject.controller;

import am.aca.wftartproject.model.*;
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
 * Created by Armen on 6/26/2017
 */
@MultipartConfig
@Controller
public class SignUpController {

    private final UserService userService;
    private final ArtistService artistService;

    @Autowired
    public SignUpController(UserService userService, ArtistService artistService) {
        this.userService = userService;
        this.artistService = artistService;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public ModelAndView showRegister(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("signUp");
        request.getSession().setAttribute("artistSpecTypes",ArtistSpecialization.values());
        mav.addObject("user", new User());
        mav.addObject("artist", new Artist());
        mav.addObject("artistSpecTypes", ArtistSpecialization.values());
        return mav;
    }

    @RequestMapping(value = "/userRegister", method = RequestMethod.POST)
    public ModelAndView addUser(HttpServletRequest request, HttpServletResponse response,
                                @ModelAttribute("user") User user) {
        String page;
        try {
            user.setShoppingCard(new ShoppingCard(5000, ShoppingCardType.PAYPAL));
            user.setUserPasswordRepeat(request.getParameter("userPasswordRepeat"));
            userService.addUser(user);
            page = "index";
            request.getSession().setAttribute("message","Hi " + user.getFirstName());
            HttpSession session = request.getSession(true);
            request.getSession().setAttribute("user", user);
            Cookie userEmail = new Cookie("userEmail", user.getEmail());
            userEmail.setMaxAge(3600);             // 60 minutes
            response.addCookie(userEmail);
        } catch (RuntimeException e) {
            request.setAttribute("errorMessage", e.getMessage());
            page = "/signUp";
        }
        return new ModelAndView(page, "user", user);
    }


    @RequestMapping(value = "/artistRegister", method = RequestMethod.POST)
    public ModelAndView addArtist(HttpServletRequest request, HttpServletResponse response,
                                  @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {
        Artist artistFromRequest = new Artist();
        String message;
        artistFromRequest.setShoppingCard(new ShoppingCard(5000, ShoppingCardType.PAYPAL));
        if (!image.isEmpty() && request.getParameter("artistSpec")!=null && !request.getParameter("artistSpec").equals("-1") && !request.getParameter("password").isEmpty() && request.getParameter("password").equals(request.getParameter("passwordRepeat"))) {
            byte[] imageBytes = image.getBytes();
            artistFromRequest
                    .setSpecialization(ArtistSpecialization.valueOf(request.getParameter("artistSpec")))
                    .setArtistPhoto(imageBytes)
                    .setFirstName(request.getParameter("firstName"))
                    .setLastName(request.getParameter("lastName"))
                    .setAge(Integer.parseInt(request.getParameter("age")))
                    .setEmail(request.getParameter("email"))
                    .setPassword(request.getParameter("password")).setUserPasswordRepeat(request.getParameter("passwordRepeat"));
        }
        else{
            message = "No chages, empty fields or Incorrect Data";
            request.setAttribute("errorMessage",message);
            request.setAttribute("user",artistFromRequest);
            return new ModelAndView("signUp");
        }

        String page;
        try {

            artistService.addArtist(artistFromRequest);
            page = "/index";
            request.setAttribute("message","Hi " + artistFromRequest.getFirstName());
            HttpSession session = request.getSession(true);
            session.setAttribute("user", artistFromRequest);
            Cookie userEmail = new Cookie("userEmail", artistFromRequest.getEmail());
            userEmail.setMaxAge(3600);             // 60 minutes
            response.addCookie(userEmail);
        } catch (RuntimeException e) {
            request.setAttribute("errorMessage", e.getMessage());
            page = "/signUp";
        }

        return new ModelAndView(page, "user", artistFromRequest);
    }

}
