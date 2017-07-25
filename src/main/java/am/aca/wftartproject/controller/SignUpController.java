package am.aca.wftartproject.controller;

import am.aca.wftartproject.exception.service.InvalidEntryException;
import am.aca.wftartproject.model.*;
import am.aca.wftartproject.service.ArtistService;
import am.aca.wftartproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    private HttpSession session;
    private UserService userService;
    private ArtistService artistService;
    private static final String MESSAGE_ATTR = "message";

    @Autowired
    public SignUpController(UserService userService, ArtistService artistService) {
        this.userService = userService;
        this.artistService = artistService;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public ModelAndView showRegistrationPage() {
        ModelAndView mv = new ModelAndView("sign-up");

        // Get required information and add attributes for view page
        mv.addObject("user", new User());
        mv.addObject("artist", new Artist());
        mv.addObject("artistSpecTypes", ArtistSpecialization.values());

        return mv;
    }

    @RequestMapping(value = "/userRegister", method = RequestMethod.POST)
    public ModelAndView addUser(HttpServletRequest request, HttpServletResponse response,
                                RedirectAttributes redirectAttributes,
                                @ModelAttribute("user") User user,
                                @RequestParam("paymentType") String paymentType,
                                @RequestParam("userPasswordRepeat") String userPasswordRepeat) {
        ModelAndView mv = new ModelAndView();
        session = request.getSession(true);
        String page = "home";

        try {
            // Get new user info from UI
            ShoppingCard shoppingCardFromRequest = new ShoppingCard(ShoppingCardType.valueOf(paymentType));
            user.setShoppingCard(shoppingCardFromRequest);
            user.setUserPasswordRepeat(userPasswordRepeat);

            // Add user in database
            userService.addUser(user);

            // Set user info in session attribute and in cookie
            session.setAttribute("user", user);
            Cookie userEmail = new Cookie("userEmail", user.getEmail());
            userEmail.setMaxAge(3600);    // 60 minutes
            response.addCookie(userEmail);
        } catch (InvalidEntryException e) {
            redirectAttributes.addFlashAttribute(MESSAGE_ATTR,
                    "There are invalid fields, please fill them all correctly and try again.");
            page = "redirect:/signup";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute(MESSAGE_ATTR, e.getMessage());
            page = "redirect:/signup";
        }
        mv.setViewName(page);

        return mv;
    }

    @RequestMapping(value = "/artistRegister", method = RequestMethod.POST)
    public ModelAndView addArtist(HttpServletRequest request, HttpServletResponse response,
                                  RedirectAttributes redirectAttributes
                                  /*@ModelAttribute("user") User user*/,
                                  @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {
        ModelAndView mv = new ModelAndView();
        session = request.getSession();
        Artist artistFromRequest = new Artist();
        String page = "home";

        try {
            // Check artist info validation and
            // get profile details from UI
            if (isValidArtistFromRequest(request, image)) {
                artistFromRequest = getArtistFromRequest(request, artistFromRequest, image);
            }

            // Add artist in database
            artistService.addArtist(artistFromRequest);

            // Set user info in session attribute and in cookie
            session.setAttribute("user", artistFromRequest);
            Cookie userEmail = new Cookie("userEmail", artistFromRequest.getEmail());
            userEmail.setMaxAge(3600);     // 60 minutes
            response.addCookie(userEmail);
        } catch (InvalidEntryException e) {
            redirectAttributes.addFlashAttribute(MESSAGE_ATTR,
                    "There are invalid fields, please fill them all correctly and try again.");
            page = "redirect:/signup";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute(MESSAGE_ATTR, "There was a problem " + e.getMessage());
            page = "redirect:/signup";
        }
        mv.setViewName(page);

        return mv;
    }


    private boolean isValidArtistFromRequest(HttpServletRequest request, MultipartFile image) {
        return !image.isEmpty() &&
                !request.getParameter("artistSpec").equals("-1") &&
                !request.getParameter("password").isEmpty() &&
                request.getParameter("password").equals(request.getParameter("passwordRepeat"));
    }

    private Artist getArtistFromRequest(HttpServletRequest request, Artist artist, MultipartFile image) throws IOException {
        ShoppingCard shoppingCard = new ShoppingCard(ShoppingCardType.valueOf(request.getParameter("paymentType")));
        byte[] imageBytes = image.getBytes();

        artist.setSpecialization(ArtistSpecialization.valueOf(request.getParameter("artistSpec")))
                .setArtistPhoto(imageBytes)
                .setFirstName(request.getParameter("firstName"))
                .setLastName(request.getParameter("lastName"))
                .setAge(Integer.parseInt(request.getParameter("age")))
                .setEmail(request.getParameter("email"))
                .setPassword(request.getParameter("password"))
                .setUserPasswordRepeat(request.getParameter("passwordRepeat"));
        artist.setShoppingCard(shoppingCard);

        return artist;
    }

}
