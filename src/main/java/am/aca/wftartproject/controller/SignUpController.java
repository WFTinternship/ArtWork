package am.aca.wftartproject.controller;

import am.aca.wftartproject.controller.helper.ControllerHelper;
import am.aca.wftartproject.entity.*;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Armen on 6/26/2017
 */
@MultipartConfig
@Controller
public class SignUpController extends ControllerHelper {

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public ModelAndView showRegister(HttpServletRequest request) {

        //get session and set artist specialization types
        request.getSession().setAttribute("artistSpecTypes", ArtistSpecialization.values());

        //create new m.a.v , add objects as attribute and return to signup page
        ModelAndView mav = new ModelAndView(SIGNUP);
        mav.addObject(USER, new User());
        mav.addObject("artist", new Artist());
        mav.addObject("artistSpecTypes", ArtistSpecialization.values());
        return mav;
    }

    @Transactional
    @RequestMapping(value = "/userRegister", method = RequestMethod.POST)
    public ModelAndView addUser(HttpServletRequest request,
                                @ModelAttribute(USER) User user) {
        String page;

        //throw exception if password don't match
        if (!user.getPassword().equals(request.getParameter("userPasswordRepeat"))) {
            throw new RuntimeException("Password don't match");
        }

        // save bound user object into db
        try {
            userSaver(user, request);
            page = HOME;
        } catch (RuntimeException e) {
            request.setAttribute("errorMessage", e.getMessage());
            page = SIGNUP;
        }

        return new ModelAndView(page, USER, user);
    }

    @Transactional
    @RequestMapping(value = "/artistRegister", method = RequestMethod.POST)
    public ModelAndView addArtist(HttpServletRequest request,
                                  @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {
        String page;
        Artist artistFromRequest = new Artist();

        //check request parameters for validity and create Artist obj
        if (!image.isEmpty() && request.getParameter("artistSpec") != null && !request.getParameter("artistSpec").equals("-1") && !request.getParameter("password").isEmpty() && request.getParameter("password").equals(request.getParameter("passwordRepeat"))) {
            createArtistFromRequest(artistFromRequest, image, request);
        } else {
            setErrorMessage(request);
            return new ModelAndView(SIGNUP);
        }

        //try to save created Artist into db
        try {
            artistSaver(artistFromRequest, request);
            page = HOME;
        } catch (RuntimeException e) {
            request.setAttribute("errorMessage", e.getMessage());
            page = SIGNUP;
        }

        return new ModelAndView(page, USER, artistFromRequest);
    }

}
