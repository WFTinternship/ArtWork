package am.aca.wftartproject.controller;

import am.aca.wftartproject.exception.service.ServiceException;
import am.aca.wftartproject.entity.*;
import am.aca.wftartproject.service.ArtistService;
import am.aca.wftartproject.service.ItemService;
import am.aca.wftartproject.service.PurchaseHistoryService;
import am.aca.wftartproject.service.UserService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Armen on 6/26/2017.
 */
@Controller
public class AccountController {
    private User user;
    private User finduser;
    private Item item;
    private Artist artist;
    private Artist findArtist;
    private Cookie[] cookies;
    private String userEmailFromCookie = null;

    @Autowired
    private UserService userService;
    @Autowired
    private ArtistService artistService;
    @Autowired
    private PurchaseHistoryService purchaseHistoryService;
    @Autowired
    private ItemService itemService;

    @RequestMapping(value = {"account-details"},method = RequestMethod.GET)
    public ModelAndView accountInfo(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        cookies = request.getCookies();
        String page = "redirect:/signup";
        request.getSession().setAttribute("artist", new Artist());
        try {
            if (session.getAttribute("user") != null) {
                if (session.getAttribute("user").getClass() == User.class) {
                    user = (User) session.getAttribute("user");
                    finduser = userService.findUser(user.getId());
                    if (user != null) {
                        session.setAttribute("user", finduser);
                        page = "account";
                    } else {
                        throw new RuntimeException("Incorrect program logic");
                    }
                } else if (session.getAttribute("user").getClass() == Artist.class) {
                    artist = (Artist) session.getAttribute("user");
                    findArtist = artistService.findArtist(artist.getId());
                    String image = Base64.getEncoder().encodeToString(findArtist.getArtistPhoto());
                    if (findArtist != null) {
                        session.setAttribute("image", image);
                        session.setAttribute("user", findArtist);
                        page = "account";
                    } else {
                        throw new RuntimeException("Incorrect program logic");
                    }
                }
            }
        } catch (Exception e) {
            String errorMessage = String.format("There is problem with user info retrieving: %s", e.getMessage());
            throw new RuntimeException(errorMessage, e);
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
    public ModelAndView editProfileProcess(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {
        HttpSession session = request.getSession();
        String message ="";
        int counter = 0;
        if (session.getAttribute("user") != null) {
            if (session.getAttribute("user").getClass() == User.class) {
                user = (User) session.getAttribute("user");
                if(user != null){
                    finduser = userService.findUser(user.getId());
                    request.getSession().setAttribute("user", finduser);
                }
                if (finduser != null) {
                    finduser.setUserPasswordRepeat(finduser.getPassword());
                    if (request.getParameter("firstName") != null && !request.getParameter("firstName").isEmpty()) {
                        finduser.setFirstName(request.getParameter("firstName"));
                        counter++;
                    }
                    if ( request.getParameter("lastName") != null && !request.getParameter("lastName").isEmpty()) {
                        finduser.setLastName(request.getParameter("lastName"));
                        counter++;
                    }
                    if (request.getParameter("age") != null && !request.getParameter("age").isEmpty()) {
                        finduser.setAge(Integer.parseInt(request.getParameter("age")));
                        counter++;
                    }
                    if (request.getParameter("oldpassword") != null && !request.getParameter("oldpassword").isEmpty() &&  request.getParameter("oldpassword").equals(finduser.getPassword()) && request.getParameter("newpassword") != null && request.getParameter("retypepassword") != null && request.getParameter("newpassword").equals(request.getParameter("retypepassword"))) {
                        finduser.setPassword(request.getParameter("newpassword"));
                        finduser.setUserPasswordRepeat(request.getParameter("retypepassword"));
                        message = "Your password was successfully changed";
                        counter++;
                    }

                    try {
                        userService.updateUser(finduser);
                        if(counter == 0){
                            message = "No changes ,empty field or The entered info is not correct";
                        }
                        else {
                            message = "User updated successfully";
                        }
                        request.getSession().setAttribute("message",message);
                        request.getSession().setAttribute("user", finduser);
                    } catch (ServiceException e) {
                        String errorMessage = "The entered info is not correct";
                        request.getSession().setAttribute("message", errorMessage);

                    }
                } else {
                    throw new RuntimeException("Incorrect program logic");
                }
            } else if (session.getAttribute("user").getClass() == Artist.class) {
                artist = (Artist) session.getAttribute("user");
                if(artist != null){
                    findArtist = artistService.findArtist(artist.getId());
                }
                if (findArtist != null) {
                    findArtist.setUserPasswordRepeat(findArtist.getPassword());
                    if ( request.getParameter("firstname") != null && !request.getParameter("firstname").isEmpty()) {
                        findArtist.setFirstName(request.getParameter("firstname"));
                        counter++;
                    }
                    if ( request.getParameter("lastname") != null && !request.getParameter("lastname").isEmpty()) {
                        findArtist.setLastName(request.getParameter("lastname"));
                        counter++;
                    }
                    if ( request.getParameter("age") != null && !request.getParameter("age").isEmpty()) {
                        findArtist.setAge(Integer.parseInt(request.getParameter("age")));
                        counter++;
                    }
                    if (request.getParameter("specialization") != null  && !request.getParameter("specialization").isEmpty() && Integer.valueOf(request.getParameter("specialization")) != -1 ) {
                        findArtist.setSpecialization(ArtistSpecialization.valueOf(request.getParameter("specialization")));
                        counter++;
                    }
                    if (request.getParameter("oldpassword") != null && !request.getParameter("oldpassword").isEmpty() &&  request.getParameter("oldpassword").equals(findArtist.getPassword()) && request.getParameter("newpassword") != null && !request.getParameter("newpassword").isEmpty() && !request.getParameter("retypepassword").isEmpty() && request.getParameter("retypepassword") != null && request.getParameter("newpassword").equals(request.getParameter("retypepassword"))) {
                        findArtist.setPassword(request.getParameter("newpassword"));
                        findArtist.setUserPasswordRepeat(request.getParameter("retypepassword"));
                        counter++;
                    }
                    if ((image) != null) {
                        byte[] imageBytes = image.getBytes();
                        findArtist.setArtistPhoto(imageBytes);
                    }

                    try {
                        artistService.updateArtist(findArtist);
                        if(counter == 0){
                            message = "No changes ,empty field or The entered info is not correct";
                        }
                        else {
                            message = "User updated successfully";
                        }
                        request.getSession().setAttribute("message","You have successfully updated your profile");
                        request.getSession().setAttribute("user", findArtist);
                    } catch (ServiceException e) {
                        String errorMessage = "The entered info is not correct";
                        request.getSession().setAttribute("message", errorMessage);
                    }
                } else {
                    throw new RuntimeException("Incorrect program logic");
                }
            }
        }
        return new ModelAndView("editProfile");
    }

    @RequestMapping(value = {"purchase-history"}, method = RequestMethod.GET)
    public ModelAndView purchaseHistory(HttpServletRequest request, HttpServletResponse response) {
        String page = "purchaseHistory";
        HttpSession session = request.getSession();
        if (session.getAttribute("user") != null) {
            if (session.getAttribute("user").getClass() == User.class) {
                user = (User) session.getAttribute("user");
                request.getSession().setAttribute("purchaseHistory", purchaseHistoryService.getPurchaseList(user.getId()));
            }
            if (session.getAttribute("user").getClass() == Artist.class) {
                artist = (Artist) session.getAttribute("user");

                request.getSession().setAttribute("purchaseHistory", purchaseHistoryService.getPurchaseList(artist.getId()));
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
    public ModelAndView addItemProcess(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "files", required = false) MultipartFile [] image) throws IOException {
        HttpSession session = request.getSession();
        String message = "";
        List<String> photoUrl = new ArrayList<>();
        item = new Item();
        if (session.getAttribute("user") != null && session.getAttribute("user").getClass() == Artist.class) {
            artist = (Artist) session.getAttribute("user");
            findArtist = artistService.findArtist(artist.getId());
            if (findArtist != null) {
                request.getSession().setAttribute("user", findArtist);
                if(image != null && !request.getParameter("title").isEmpty() && !request.getParameter("description").isEmpty() && !request.getParameter("type").isEmpty() && !request.getParameter("price").isEmpty() && !request.getParameter("type").equals("-1") ){
                    for(MultipartFile multipartFile:image){
                        byte[] imageBytes = multipartFile.getBytes();
                        String uploadPath = "resources/images/artists/" + artist.getId();
                        String realPath = request.getServletContext().getRealPath("resources/images/artists/" + artist.getId());
                        File uploadDir = new File(realPath);
                        if (!uploadDir.exists()) {
                            uploadDir.mkdir();
                        }
                        String fileName = multipartFile.getOriginalFilename();
                        String filePath = realPath + File.separator + fileName + ".jpg";
                        FileUtils.writeByteArrayToFile(new File(filePath), imageBytes);
                        photoUrl.add(uploadPath + File.separator + fileName + ".jpg");
                    }
                    item.setTitle(request.getParameter("title"));
                    item.setDescription(request.getParameter("description"));
                    item.setItemType(ItemType.valueOf(request.getParameter("type")));
                    item.setPrice(Double.parseDouble(request.getParameter("price")));
                    item.setStatus(true);
                    item.setPhotoURL(photoUrl);
                    item.setArtist(findArtist);
                    item.setAdditionDate(Calendar.getInstance().getTime());
                    try {
                        itemService.addItem(item);
                        message = "Your ArtWork has been successfully added, Now you can see it in My ArtWork page";
                    } catch (ServiceException e) {
                         message = "The entered info is not correct";
                        request.getSession().setAttribute("errorMessage", message);
                    }
                }
                else message = "Warning !!! You have an empty fields, Please Try again";
                request.getSession().setAttribute("errorMessage", message);
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
            request.getSession().setAttribute("artistItems", itemService.getArtistItems(artist.getId()));
        } else page = "redirect:/signup";
        return new ModelAndView("my-works");
    }

    @RequestMapping(value = {"deleteItem/*"}, method = RequestMethod.GET)
    @Transactional(readOnly = false)
    public ModelAndView deleteItem(HttpServletRequest request, HttpServletResponse response) {
        String[] pathInfo = request.getServletPath().split("/");
        Long itemId = Long.parseLong(pathInfo[pathInfo.length - 1]);
       Item itemForRemove =  itemService.findItem(itemId);
        itemService.deleteItem(itemForRemove);
        request.getSession().setAttribute("message","Your ArtWork has been successfully deletet");
        return new ModelAndView("redirect:/my-works");
    }

}
