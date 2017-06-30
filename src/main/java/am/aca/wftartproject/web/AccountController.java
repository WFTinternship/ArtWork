package am.aca.wftartproject.web;

import am.aca.wftartproject.controller.CtxListener;
import am.aca.wftartproject.exception.service.ServiceException;
import am.aca.wftartproject.model.*;
import am.aca.wftartproject.service.ArtistService;
import am.aca.wftartproject.service.ItemService;
import am.aca.wftartproject.service.PurchaseHistoryService;
import am.aca.wftartproject.service.UserService;
import am.aca.wftartproject.service.impl.ArtistServiceImpl;
import am.aca.wftartproject.service.impl.UserServiceImpl;
import am.aca.wftartproject.util.SpringBean;
import am.aca.wftartproject.util.SpringBeanType;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

/**
 * Created by Armen on 6/26/2017.
 */
@Controller
public class AccountController {
    User user;
    User finduser;
    Item item;
    Artist artist;
    Artist findArtist;
    Cookie[] cookies;
    String userEmailFromCookie = null;

    @Autowired
    UserService userService;
    @Autowired
    ArtistService artistService;
    @Autowired
    PurchaseHistoryService purchaseHistoryService;
    @Autowired
    ItemService itemService;

    @RequestMapping(value = {"/account"})
    public ModelAndView accountInfo(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        cookies = request.getCookies();
        String page = "redirect:/signup";
        request.setAttribute("artist", new Artist());
        try {
            if (session.getAttribute("user") != null) {
                if (session.getAttribute("user").getClass() == User.class) {
                    user = (User) session.getAttribute("user");
                    finduser = userService.findUser(user.getId());
                    if (user != null) {
                        request.setAttribute("user", finduser);
                        page = "account";
                    } else {
                        throw new RuntimeException("Incorrect program logic");
                    }
                } else if (session.getAttribute("user").getClass() == Artist.class) {
                    artist = (Artist) session.getAttribute("user");
                    findArtist = artistService.findArtist(artist.getId());
                    String image = Base64.getEncoder().encodeToString(findArtist.getArtistPhoto());
                    if (findArtist != null) {
                        request.getSession().setAttribute("image", image);
                        request.getSession().setAttribute("user", findArtist);
                        page = "account";
                    } else {
                        throw new RuntimeException("Incorrect program logic");
                    }
                }
            }
        } catch (Exception e) {
            String errorMessage = String.format("There is problem with artist info retrieving: %s", e.getMessage());
            throw new RuntimeException(errorMessage, e);
        }
        return new ModelAndView(page);
    }

    @RequestMapping(value = {"/edit-profile"}, method = RequestMethod.GET)
    public ModelAndView editProfile(HttpServletRequest request, HttpServletResponse response) {
        String page = "edit-profile";
        if (request.getSession().getAttribute("user") == null) {
            page = "redirect:/signup";
        }
        request.setAttribute("artistSpecTypes", ArtistSpecialization.values());
        return new ModelAndView(page);
    }

    @RequestMapping(value = {"/edit-profile"}, method = RequestMethod.POST)
    public ModelAndView editProfileProcess(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {
        HttpSession session = request.getSession();
        String message ="";
        int counter = 0;
        if (session.getAttribute("user") != null) {
            if (session.getAttribute("user").getClass() == User.class) {
                user = (User) session.getAttribute("user");
                finduser = userService.findUser(user.getId());
                request.setAttribute("user", finduser);
                if (user != null) {
                    response.setContentType("text/html");
                    if (request.getParameter("firstname") != null && !request.getParameter("firstname").isEmpty()) {
                        finduser.setFirstName(request.getParameter("firstname"));
                        counter++;
                    }
                    if ( request.getParameter("lastname") != null && !request.getParameter("lastname").isEmpty()) {
                        finduser.setLastName(request.getParameter("lasstname"));
                        counter++;
                    }
                    if (request.getParameter("age") != null && !request.getParameter("age").isEmpty()) {
                        finduser.setAge(Integer.parseInt(request.getParameter("age")));
                        counter++;
                    }
                    if (request.getParameter("oldpassword") != null && !request.getParameter("oldpassword").isEmpty() &&  request.getParameter("oldpassword").equals(finduser.getPassword()) && request.getParameter("newpassword") != null && request.getParameter("retypepassword") != null && request.getParameter("newpassword").equals(request.getParameter("retypepassword"))) {
                        finduser.setPassword(request.getParameter("newpassword"));
                        message = "Your password was successfully changed";
                        counter++;
                    }

                    try {
                        userService.updateUser(finduser.getId(), finduser);
                        if(counter == 0){
                            message = "No changes ,empty field or The entered info is not correct";
                        }
                        request.setAttribute("message",message);
                        request.getSession().setAttribute("user", finduser);
                        request.setAttribute("user", finduser);
                    } catch (ServiceException e) {
                        String errorMessage = "The entered info is not correct";
                        request.setAttribute("message", errorMessage);

                    }
                } else {
                    throw new RuntimeException("Incorrect program logic");
                }
            } else if (session.getAttribute("user").getClass() == Artist.class) {
                artist = (Artist) session.getAttribute("user");
                findArtist = artistService.findArtist(artist.getId());
                if (artist != null) {
                    response.setContentType("text/html");
                    if ( request.getParameter("firstname") != null && !request.getParameter("firstname").isEmpty()) {
                        findArtist.setFirstName(request.getParameter("firstname"));
                    }
                    if ( request.getParameter("lastname") != null && !request.getParameter("lastname").isEmpty()) {
                        findArtist.setLastName(request.getParameter("lastname"));
                    }
                    if ( request.getParameter("age") != null && !request.getParameter("age").isEmpty()) {
                        findArtist.setAge(Integer.parseInt(request.getParameter("age")));
                    }
                    if (request.getParameter("specialization") != null  && !request.getParameter("specialization").isEmpty() && Integer.valueOf(request.getParameter("specialization")) != -1 ) {
                        findArtist.setSpecialization(ArtistSpecialization.valueOf(request.getParameter("specialization")));
                    }
                    if (request.getParameter("oldpassword") != null && !request.getParameter("oldpassword").isEmpty() &&  request.getParameter("oldpassword").equals(findArtist.getPassword()) && request.getParameter("newpassword") != null && request.getParameter("retypepassword") != null && request.getParameter("newpassword").equals(request.getParameter("retypepassword"))) {
                        findArtist.setPassword(request.getParameter("newpassword"));
                    }
                    if ((image) != null) {
                        byte[] imageBytes = image.getBytes();
                        findArtist.setArtistPhoto(imageBytes);
                    }

                    try {
                        artistService.updateArtist(artist.getId(), findArtist);
                        request.setAttribute("message","You have successfully updated your profile");
                        request.getSession().setAttribute("user", findArtist);
                        request.setAttribute("user", findArtist);
                    } catch (ServiceException e) {
                        String errorMessage = "The entered info is not correct";
                        request.setAttribute("message", errorMessage);
                    }
                } else {
                    throw new RuntimeException("Incorrect program logic");
                }
            }
        }
        return new ModelAndView("edit-profile");
    }

    @RequestMapping(value = {"purchase-history"}, method = RequestMethod.GET)
    public ModelAndView purchaseHistory(HttpServletRequest request, HttpServletResponse response) {
        String page = "purchase-history";
        HttpSession session = request.getSession();
        if (session.getAttribute("user") != null) {
            if (session.getAttribute("user").getClass() == User.class) {
                user = (User) session.getAttribute("user");
                request.setAttribute("purchaseHistory", purchaseHistoryService.getPurchase(user.getId()));
            }
            if (session.getAttribute("user").getClass() == Artist.class) {
                artist = (Artist) session.getAttribute("user");
                request.setAttribute("purchaseHistory", purchaseHistoryService.getPurchase(artist.getId()));
            }
        } else {
            page = "redirect:/signup";
        }
        return new ModelAndView(page);
    }

    @RequestMapping(value = {"additem"}, method = RequestMethod.GET)
    public ModelAndView addItem(HttpServletRequest request, HttpServletResponse response) {
        String page = "additem";
        if (request.getSession().getAttribute("user") == null) {
            page = "redirect:/signup";
        }
        request.getSession().setAttribute("itemTypes", ItemType.values());
        return new ModelAndView(page);
    }

    @RequestMapping(value = {"additem"}, method = RequestMethod.POST)
    public ModelAndView addItemProcess(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {
        HttpSession session = request.getSession();
        String message = "";
        item = new Item();
        if (session.getAttribute("user") != null && session.getAttribute("user").getClass() == Artist.class) {
            artist = (Artist) session.getAttribute("user");
            findArtist = artistService.findArtist(artist.getId());
            if (findArtist != null) {
                request.setAttribute("user", findArtist);
                if(image != null && !request.getParameter("title").isEmpty() && !request.getParameter("description").isEmpty() && !request.getParameter("type").isEmpty() && !request.getParameter("price").isEmpty() && !request.getParameter("type").equals("-1") ){
                    item.setTitle(request.getParameter("title"));
                    item.setDescription(request.getParameter("description"));
                    item.setItemType(ItemType.valueOf(request.getParameter("type")));
                    item.setPrice(Double.parseDouble(request.getParameter("price")));
                    byte[] imageBytes = image.getBytes();
                    String uploadPath = "resources/images/artists/" + artist.getId();
                    String realPath = request.getServletContext().getRealPath("resources/images/artists/" + artist.getId());
                    File uploadDir = new File(realPath);
                    if (!uploadDir.exists()) {
                        uploadDir.mkdir();
                    }
                    String fileName = item.getTitle();
                    String filePath = realPath + File.separator + fileName + ".jpg";
                    FileUtils.writeByteArrayToFile(new File(filePath), imageBytes);
                    item.setPhotoURL(uploadPath + File.separator + fileName + ".jpg");
                    try {
                        itemService.addItem(artist.getId(), item);
                        message = "Your ArtWork has been successfully added, Now you can see it in My ArtWork page";
                    } catch (ServiceException e) {
                         message = "The entered info is not correct";
                        request.setAttribute("errorMessage", message);
                    }
                }
                else message = "Warning !!! You have an empty fields, Please Try again";
                request.setAttribute("errorMessage", message);
            }

        }
        return new ModelAndView("additem");
    }

    @RequestMapping(value = {"my-works"}, method = RequestMethod.GET)
    public ModelAndView myWorks(HttpServletRequest request, HttpServletResponse response) {
        String page = "my-works";
        HttpSession session = request.getSession();
        if (session.getAttribute("user") != null && session.getAttribute("user").getClass() == Artist.class) {
            artist = (Artist) request.getSession().getAttribute("user");
            request.setAttribute("artistItems", itemService.getArtistItems(artist.getId(), 8888L, 100L));
        } else page = "redirect:/signup";
        return new ModelAndView("my-works");
    }

    @RequestMapping(value = {"deleteItem/*"}, method = RequestMethod.GET)
    public ModelAndView deleteItem(HttpServletRequest request, HttpServletResponse response) {
        String[] pathInfo = request.getServletPath().split("/");
        Long itemId = Long.parseLong(pathInfo[pathInfo.length - 1]);
        itemService.deleteItem(itemId);
        return new ModelAndView("my-works");
    }

}
