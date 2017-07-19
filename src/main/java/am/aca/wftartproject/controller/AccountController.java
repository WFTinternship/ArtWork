package am.aca.wftartproject.controller;

import am.aca.wftartproject.controller.helper.ControllerHelper;
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
public class AccountController extends ControllerHelper {
    
    @Autowired
    private ArtistService artistService;
    @Autowired
    private PurchaseHistoryService purchaseHistoryService;
    @Autowired
    private ItemService itemService;
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(AccountController.class);

    @RequestMapping(value = {"account-details"}, method = RequestMethod.GET)
    public ModelAndView accountInfo(HttpServletRequest request) 
    {
        HttpSession session = request.getSession();
        String page = REDIRECT_SIGNUP;
        try
        {
            retrieveUserDetailsFromSession(session);
            page = ACCOUNT;
        }
        catch (Exception e)
        {
            LOGGER.error(String.format(e.getMessage()));
        }
        return new ModelAndView(page);
    }

    @RequestMapping(value = {"edit-profile"}, method = RequestMethod.GET)
    public ModelAndView editProfile(HttpServletRequest request)
    {
        String page = EDIT_PROFILE;
        if (request.getSession().getAttribute(USER) == null)
        {
            page = REDIRECT_SIGNUP;
        }
        request.getSession().setAttribute("artistSpecTypes", ArtistSpecialization.values());
        return new ModelAndView(page);
    }

    @RequestMapping(value = {"edit-profile"}, method = RequestMethod.POST)
    public ModelAndView editProfileProcess(HttpServletRequest request, @RequestParam(value = "image", required = false) MultipartFile image) throws IOException, CloneNotSupportedException 
    {
        HttpSession session = request.getSession();
        if (session.getAttribute(USER) != null)
        {
            if (session.getAttribute(USER).getClass() == User.class && session.getAttribute(USER) != null)
            {
                User  finduser = (User)getUserFromSession(session);
                finduser.setUserPasswordRepeat(finduser.getPassword());
                try
                {
                    updateUserParameters(finduser,request);
                    userServiceUpdater(finduser,request);
                }
                catch (Exception e)
                {
                    setErrorMessage(request);
                    LOGGER.error(String.format(e.getMessage()));
                }
            }
            else if (session.getAttribute(USER).getClass() == Artist.class && session.getAttribute(USER) != null)
            {
                Artist findArtist = (Artist)getArtistFromSession(session);
                findArtist.setUserPasswordRepeat(findArtist.getPassword());
                try
                {
                    updateArtistParameters(findArtist,request, image);
                }
                catch (RuntimeException e)
                {
                    setErrorMessage(request);
                    return new ModelAndView(EDIT_PROFILE);
                }
                artistServiceUpdater(findArtist,request);
            }
        }
        return new ModelAndView(EDIT_PROFILE);
    }

    @RequestMapping(value = {"purchase-history"}, method = RequestMethod.GET)
    public ModelAndView purchaseHistory(HttpServletRequest request)
    {
        String page = PURCHASE_HISTORY;
        HttpSession session = request.getSession();
        if (session.getAttribute(USER) != null)
        {
            if (session.getAttribute(USER).getClass() == User.class)
            {
                User user = (User) session.getAttribute(USER);
                session.setAttribute(ATTRIBUTE_PURCHASE_HISTORY, purchaseHistoryService.getPurchaseList(user.getId()));
            }
            if (session.getAttribute(USER).getClass() == Artist.class)
            {
                Artist artist = (Artist) session.getAttribute(USER);
                session.setAttribute(ATTRIBUTE_PURCHASE_HISTORY, purchaseHistoryService.getPurchaseList(artist.getId()));
            }
        }
        else page = REDIRECT_SIGNUP;;
        return new ModelAndView(page);
    }

    @RequestMapping(value = {"add-item"}, method = RequestMethod.GET)
    public ModelAndView addItem(HttpServletRequest request)
    {
        String page = ADD_ITEM;
        if (request.getSession().getAttribute(USER) == null)
        {
            page =  REDIRECT_SIGNUP;
        }
        request.getSession().setAttribute("itemTypes", ItemType.values());
        return new ModelAndView(page);
    }

    @RequestMapping(value = {"add-item"}, method = RequestMethod.POST)
    public ModelAndView addItemProcess(HttpServletRequest request, @RequestParam(value = "files", required = false) MultipartFile[] image) throws IOException
    {
        HttpSession session = request.getSession();
        List<String> photoUrl = new ArrayList<>();
        Item item = new Item();
        try {
            if (session.getAttribute(USER) != null && session.getAttribute(USER).getClass() == Artist.class)
            {
                Artist artist = (Artist) session.getAttribute(USER);
                Artist findArtist = artistService.findArtist(artist.getId());
                if (findArtist != null && image != null)
                {
                    session.setAttribute(USER, findArtist);
                    artistImageUploader(findArtist, image, photoUrl, request);
                    createItemFromRequest(item, findArtist, photoUrl, request);
                    itemServiceSaver(item,request);
                }
                else
                {
                    setErrorMessage(request);
                    return new ModelAndView(ADD_ITEM);
                }
            }
        }
        catch (ServiceException e)
        {
            request.setAttribute("message", "The entered info is not correct or empty fields");
        }
        return new ModelAndView(ADD_ITEM);
    }

    @RequestMapping(value = {"my-works"}, method = RequestMethod.GET)
    public ModelAndView myWorks(HttpServletRequest request)
    {
        String page = MY_WORKS;
        HttpSession session = request.getSession();
        if (session.getAttribute(USER) != null && session.getAttribute(USER).getClass() == Artist.class)
        {
            Artist artist = (Artist) request.getSession().getAttribute(USER);
            request.getSession().setAttribute("artistItems", itemService.getArtistItems(artist.getId()));
        }
        else page = REDIRECT_SIGNUP;
        return new ModelAndView(MY_WORKS);
    }

    @RequestMapping(value = {"deleteItem/*"}, method = RequestMethod.GET)
    @Transactional(readOnly = false)
    public ModelAndView deleteItem(HttpServletRequest request, HttpServletResponse response)
    {
        String[] pathInfo = request.getServletPath().split("/");
        Long itemId = Long.parseLong(pathInfo[pathInfo.length - 1]);
        Item itemForRemove = itemService.findItem(itemId);
        itemService.deleteItem(itemForRemove);
        response.setStatus(HttpServletResponse.SC_OK);
        request.setAttribute("message", "Your ArtWork has been successfully deletet");
        return new ModelAndView(REDIRECT_MY_WORKS);
    }

}
