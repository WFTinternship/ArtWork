package am.aca.wftartproject.controller;

import am.aca.wftartproject.controller.helper.AccountControllerHelper;
import am.aca.wftartproject.exception.service.ServiceException;
import am.aca.wftartproject.entity.*;
import am.aca.wftartproject.service.ArtistService;
import am.aca.wftartproject.service.ItemService;
import am.aca.wftartproject.service.PurchaseHistoryService;
import am.aca.wftartproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Armen on 6/26/2017.
 */
@Controller
public class AccountController extends AccountControllerHelper {

    @Autowired
    private UserService userService;
    @Autowired
    private ArtistService artistService;
    @Autowired
    private PurchaseHistoryService purchaseHistoryService;
    @Autowired
    private ItemService itemService;
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(AccountController.class);

    @RequestMapping(value = {"account-details"}, method = RequestMethod.GET)
    public ModelAndView accountInfo(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String page = "redirect:/signup";
        try
        {
            retrieveUserDetailsFromSession(session);
            page = "account";
        }
        catch (Exception e)
        {
            LOGGER.error(String.format(e.getMessage()));
        }
        return new ModelAndView(page);
    }

    @RequestMapping(value = {"edit-profile"}, method = RequestMethod.GET)
    public ModelAndView editProfile(HttpServletRequest request, HttpServletResponse response) {
        String page = "editProfile";
        if (request.getSession().getAttribute("user") == null) {
            page = "redirect:/signup";
        }
        request.getSession().setAttribute("artistSpecTypes", ArtistSpecialization.values());
        return new ModelAndView(page);
    }

    @RequestMapping(value = {"edit-profile"}, method = RequestMethod.POST)
    public ModelAndView editProfileProcess(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "image", required = false) MultipartFile image) throws IOException, CloneNotSupportedException {
        HttpSession session = request.getSession();
        if (session.getAttribute("user") != null) {
            if (session.getAttribute("user").getClass() == User.class && session.getAttribute("user") != null) {
                User  finduser = (User)getUserFromSession(session);
                finduser.setUserPasswordRepeat(finduser.getPassword());
                try
                {
                    updateUserParameters(finduser,request);
                    userServiceUpdater(finduser,request);
                }
                catch (Exception e){
                    setErrorMessage(request);
                    LOGGER.error(String.format(e.getMessage()));
                }
            }
            else if (session.getAttribute("user").getClass() == Artist.class && session.getAttribute("user") != null) {
                Artist findArtist = (Artist)getArtistFromSession(session);
                findArtist.setUserPasswordRepeat(findArtist.getPassword());
                try
                {
                    updateArtistParameters(findArtist,request, image);
                }
                catch (RuntimeException e)
                {
                    setErrorMessage(request);
                    return new ModelAndView("editProfile");
                }
                artistServiceUpdater(findArtist,request);
            }
        }
        return new ModelAndView("editProfile");
    }

    @RequestMapping(value = {"purchase-history"}, method = RequestMethod.GET)
    public ModelAndView purchaseHistory(HttpServletRequest request, HttpServletResponse response) {
        String page = "purchaseHistory";
        HttpSession session = request.getSession();
        if (session.getAttribute("user") != null) {
            if (session.getAttribute("user").getClass() == User.class)
            {
                User user = (User) session.getAttribute("user");
                session.setAttribute("purchaseHistory", purchaseHistoryService.getPurchaseList(user.getId()));
            }
            if (session.getAttribute("user").getClass() == Artist.class)
            {
                Artist artist = (Artist) session.getAttribute("user");
                session.setAttribute("purchaseHistory", purchaseHistoryService.getPurchaseList(artist.getId()));
            }
        }
        else page = "redirect:/signup";
        return new ModelAndView(page);
    }

    @RequestMapping(value = {"add-item"}, method = RequestMethod.GET)
    public ModelAndView addItem(HttpServletRequest request, HttpServletResponse response) {
        String page = "additem";
        if (request.getSession().getAttribute("user") == null)
        {
            page = "redirect:/signup";
        }
        request.getSession().setAttribute("itemTypes", ItemType.values());
        return new ModelAndView(page);
    }

    @RequestMapping(value = {"add-item"}, method = RequestMethod.POST)
    public ModelAndView addItemProcess(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "files", required = false) MultipartFile[] image) throws IOException {
        HttpSession session = request.getSession();
        List<String> photoUrl = new ArrayList<>();
        Item item = new Item();
        try {
            if (session.getAttribute("user") != null && session.getAttribute("user").getClass() == Artist.class)
            {
                Artist artist = (Artist) session.getAttribute("user");
                Artist findArtist = artistService.findArtist(artist.getId());
                if (findArtist != null && image != null)
                {
                    session.setAttribute("user", findArtist);
                    artistImageUploader(findArtist, image, photoUrl, request);
                    createItemFromRequest(item, findArtist, photoUrl, request);
                    itemServiceSaver(item,request);
                }
                else
                {
                    setErrorMessage(request);
                    return new ModelAndView("additem");
                }
            }
        }
        catch (ServiceException e)
        {
            request.setAttribute("message", "The entered info is not correct or empty fields");
        }
        return new ModelAndView("additem");
    }

    @RequestMapping(value = {"my-works"}, method = RequestMethod.GET)
    public ModelAndView myWorks(HttpServletRequest request, HttpServletResponse response) {
        String page = "my-works";
        HttpSession session = request.getSession();
        if (session.getAttribute("user") != null && session.getAttribute("user").getClass() == Artist.class)
        {
            Artist artist = (Artist) request.getSession().getAttribute("user");
            request.getSession().setAttribute("artistItems", itemService.getArtistItems(artist.getId()));
        }
        else page = "redirect:/signup";
        return new ModelAndView("my-works");
    }

    @RequestMapping(value = {"deleteItem/*"}, method = RequestMethod.GET)
    @Transactional(readOnly = false)
    public ModelAndView deleteItem(HttpServletRequest request, HttpServletResponse response) {
        String[] pathInfo = request.getServletPath().split("/");
        Long itemId = Long.parseLong(pathInfo[pathInfo.length - 1]);
        Item itemForRemove = itemService.findItem(itemId);
        itemService.deleteItem(itemForRemove);
        response.setStatus(HttpServletResponse.SC_OK);
        request.setAttribute("message", "Your ArtWork has been successfully deletet");
        return new ModelAndView("redirect:/my-works");
    }

}
