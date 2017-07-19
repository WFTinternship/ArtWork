package am.aca.wftartproject.controller;

import am.aca.wftartproject.controller.helper.ControllerHelper;
import am.aca.wftartproject.entity.*;
import am.aca.wftartproject.service.ArtistService;
import am.aca.wftartproject.service.ShoppingCardService;
import am.aca.wftartproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
public class SignUpController extends ControllerHelper {

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public ModelAndView showRegister(HttpServletRequest request, HttpServletResponse respons) {
        ModelAndView mav = new ModelAndView(SIGNUP);
        request.getSession().setAttribute("artistSpecTypes",ArtistSpecialization.values());
        mav.addObject(USER, new User());
        mav.addObject("artist", new Artist());
        mav.addObject("artistSpecTypes", ArtistSpecialization.values());
        return mav;
    }
    @Transactional
    @RequestMapping(value = "/userRegister", method = RequestMethod.POST)
    public ModelAndView addUser(HttpServletRequest request, HttpServletResponse response,
                                @ModelAttribute(USER) User user) {
        String page = "";
        if(!user.getPassword().equals(request.getParameter("userPasswordRepeat")))
        {
            throw new RuntimeException("Password don't match");
        }
        try
        {
            userSaver(user,request);
            page = HOME;
        }
        catch (RuntimeException e)
        {
            request.setAttribute("errorMessage", e.getMessage());
            page = SIGNUP;
        }
        return new ModelAndView(page, USER, user);
    }
    @Transactional
    @RequestMapping(value = "/artistRegister", method = RequestMethod.POST)
    public ModelAndView addArtist(HttpServletRequest request, HttpServletResponse response,
                                  @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {
        String page = "";
        Artist artistFromRequest = new Artist();

        if (!image.isEmpty() && request.getParameter("artistSpec")!=null && !request.getParameter("artistSpec").equals("-1") && !request.getParameter("password").isEmpty() && request.getParameter("password").equals(request.getParameter("passwordRepeat")))
        {
            createArtistFromRequest(artistFromRequest,image,request);
        }
        else
        {
            setErrorMessage(request);
            return new ModelAndView(SIGNUP);
        }
        try
        {
            artistSaver(artistFromRequest,request);
            page = HOME;
        }
        catch (RuntimeException e)
        {
            request.setAttribute("errorMessage", e.getMessage());
            page = SIGNUP;
        }

        return new ModelAndView(page, USER, artistFromRequest);
    }

}
