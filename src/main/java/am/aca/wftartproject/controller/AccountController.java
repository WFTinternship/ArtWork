package am.aca.wftartproject.controller;

import am.aca.wftartproject.exception.service.InvalidEntryException;
import am.aca.wftartproject.exception.service.ServiceException;
import am.aca.wftartproject.model.*;
import am.aca.wftartproject.service.ArtistService;
import am.aca.wftartproject.service.ItemService;
import am.aca.wftartproject.service.PurchaseHistoryService;
import am.aca.wftartproject.service.UserService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * Created by Armen on 6/26/2017
 */
@MultipartConfig
@Controller
public class AccountController {

    private HttpSession session;
    private UserService userService;
    private ArtistService artistService;
    private PurchaseHistoryService purchaseHistoryService;
    private ItemService itemService;

    @Autowired
    public AccountController(UserService userService, ArtistService artistService,
                             PurchaseHistoryService purchaseHistoryService, ItemService itemService) {
        this.userService = userService;
        this.artistService = artistService;
        this.purchaseHistoryService = purchaseHistoryService;
        this.itemService = itemService;
    }

    @RequestMapping(value = {"/account"})
    public ModelAndView accountInformation(HttpServletRequest request) {
        session = request.getSession();
        ModelAndView mv = new ModelAndView();
        String page = "account";
        AbstractUser abstractUser = (AbstractUser) session.getAttribute("user");

        // Check whether the user is authenticated
        if (abstractUser == null) {
            return new ModelAndView("redirect:/login");
        }

        // Check and get user information for account.jsp view page
        try {
            if (abstractUser instanceof User) {
                User userFromRequest = (User) session.getAttribute("user");
                User findUserFromDB = userService.findUser(userFromRequest.getId());
                if (findUserFromDB != null) {
                    session.setAttribute("user", findUserFromDB);
                }
            } else if (abstractUser instanceof Artist) {
                Artist artistFromRequest = (Artist) session.getAttribute("user");
                Artist findArtistFromDB = artistService.findArtist(artistFromRequest.getId());
                if (findArtistFromDB != null) {
                    String image = Base64.getEncoder().encodeToString(findArtistFromDB.getArtistPhoto());
                    mv.addObject("image", image);
                    session.setAttribute("user", findArtistFromDB);
                }
            }
        } catch (RuntimeException e) {
            String errorMessage = "There is problem with the info retrieving";
            page = "redirect:/index";
            request.getSession().setAttribute("message", errorMessage);
        }
        mv.setViewName(page);
        return mv;
    }

    @RequestMapping(value = {"/edit-profile"}, method = RequestMethod.GET)
    public ModelAndView editProfileInitialPage(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("edit-profile");
        session = request.getSession();
        AbstractUser abstractUser = (AbstractUser) session.getAttribute("user");

        // Check whether the user is authenticated
        if (abstractUser == null) {
            return new ModelAndView("redirect:/login");
        }

        modelAndView.addObject("artistSpecTypes", ArtistSpecialization.values());
        return modelAndView;
    }

    @RequestMapping(value = {"/edit-profile"}, method = RequestMethod.POST)
    public ModelAndView editProfileProcess(HttpServletRequest request,
                                           @RequestParam(value = "image", required = false) MultipartFile image,
                                           @RequestParam(value = "editBlock") String command) throws IOException {
        session = request.getSession();
        ModelAndView modelAndView = new ModelAndView("edit-profile");
        AbstractUser abstractUser = (AbstractUser) session.getAttribute("user");

        // Perform user profile information editing based on chosen command
        switch (command) {
            case ("persInfo"):
                changePersonalInfo(abstractUser, request, modelAndView);
                break;
            case ("password"):
                changePassword(abstractUser, request, modelAndView);
                break;
            case ("avatar"):
                changeProfilePicture((Artist) abstractUser, modelAndView, image);
                break;
        }
        return modelAndView;
    }

    @RequestMapping(value = {"/purchase-history"}, method = RequestMethod.GET)
    public ModelAndView purchaseHistoryInitialPage(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("purchase-history");
        session = request.getSession();
        AbstractUser abstractUser = (AbstractUser) session.getAttribute("user");

        // Check whether the user is authenticated
        if (abstractUser == null) {
            return new ModelAndView("redirect:/login");
        }

        // Get purchase history information from DB
        // and add appropriate attribute to ModelAndView object
        List<PurchaseHistory> purchaseHistories = purchaseHistoryService.getPurchase(abstractUser.getId());
        modelAndView.addObject("purchaseHistory", purchaseHistories);

        return modelAndView;
    }

    @RequestMapping(value = {"/additem"}, method = RequestMethod.GET)
    public ModelAndView addItemInitialPage(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        session = request.getSession();
        AbstractUser abstractUser = (AbstractUser) session.getAttribute("user");

        // Check whether the user is authenticated
        if (abstractUser == null) {
            return new ModelAndView("redirect:/login");
        }

        // Get item types and add appropriate attribute to ModelAndView object
        modelAndView.addObject("itemTypes", ItemType.values());
        modelAndView.setViewName("add-item");
        return modelAndView;
    }

    @RequestMapping(value = {"/additem"}, method = RequestMethod.POST)
    public ModelAndView addItemProcess(HttpServletRequest request, HttpServletResponse response,
                                       @RequestParam(value = "files", required = false) MultipartFile[] image) throws IOException {
        //TODO to be fixed

        session = request.getSession();
        String message;
        List<String> photoUrl = new ArrayList<>();
        Item item = new Item();
        if (session.getAttribute("user") != null && session.getAttribute("user").getClass() == Artist.class) {
            Artist artist = (Artist) session.getAttribute("user");
            Artist findArtist = artistService.findArtist(artist.getId());
            if (findArtist != null) {
                if (image != null && !request.getParameter("title").isEmpty()
                        && !request.getParameter("description").isEmpty()
                        && !request.getParameter("type").isEmpty()
                        && !request.getParameter("price").isEmpty()
                        && !request.getParameter("type").equals("-1")) {
                    item.setTitle(request.getParameter("title"));
                    item.setDescription(request.getParameter("description"));
                    item.setItemType(ItemType.valueOf(request.getParameter("type")));
                    item.setPrice(Double.parseDouble(request.getParameter("price")));

                    for (MultipartFile multipartFile : image) {
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
                    try {
                        item.setPhotoURL(photoUrl);
                        itemService.addItem(artist.getId(), item);
                        message = "Your ArtWork has been successfully added, Now you can see it in 'My ArtWork' page";
                    } catch (ServiceException e) {
                        message = "The entered info is not correct";
                        request.setAttribute("errorMessage", message);
                    }
                } else message = "Warning !!! You have an empty fields, Please Try again";
                request.setAttribute("errorMessage", message);
            }
        }
        return new ModelAndView("add-item");
    }

    @RequestMapping(value = {"/my-works"}, method = RequestMethod.GET)
    public ModelAndView myWorksInitialPage(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("my-works");
        session = request.getSession();
        AbstractUser abstractUser = (AbstractUser) session.getAttribute("user");

        // Check whether the user is authenticated
        if (abstractUser == null) {
            return new ModelAndView("redirect:/login");
        }

        // Get artist items from DB and
        // add appropriate attribute to ModelAndView object
        List<Item> artistItemList = itemService.getArtistItems(abstractUser.getId(), 8888L, 100L);
        modelAndView.addObject("artistItems", artistItemList);

        return modelAndView;
    }

    @RequestMapping(value = "/edit-item/{id}", method = RequestMethod.GET)
    public ModelAndView editItemInitialPage(HttpServletRequest request, @PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("edit-item");
        session = request.getSession();
        AbstractUser abstractUser = (AbstractUser) session.getAttribute("user");

        // Check whether the user is authenticated
        if(abstractUser==null){
            return new ModelAndView("redirect:/login");
        }

        // Get chosen item from DB and
        // add appropriate attributes to ModelAndView object
        Item item = itemService.findItem(id);
        mv.addObject("item", item);
        mv.addObject("itemTypes", ItemType.values());

        return mv;
    }

    @RequestMapping(value = "/edit-item/{id}", method = RequestMethod.POST)
    public ModelAndView editItemProcess(HttpServletRequest request,
                                     @PathVariable("id") Integer id,
                                     @RequestParam("title") String title,
                                     @RequestParam("description") String description,
                                     @RequestParam("price") Double price,
                                     @RequestParam("itemType") String itemType,
                                     @RequestParam(value = "image", required = false) MultipartFile image,
                                     @SessionAttribute("item") Item item) throws IOException {
        //TODO to be fixed


        ModelAndView mv = new ModelAndView("edit-item");
        List<String> photoUrlList = new ArrayList<>();
        String message;
        String page = null;
        item.setTitle(title)
                .setDescription(description)
                .setItemType(ItemType.valueOf(itemType))
                .setPrice(price);

        if (image != null) {
            byte[] imageBytes = image.getBytes();
            String uploadPath = "resources/images/artists/" + item.getArtistId();
            String realPath = request.getServletContext().getRealPath("resources/images/artists/" + item.getArtistId());
            File uploadDir = new File(realPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String fileName = image.getOriginalFilename();
            String filePath = realPath + File.separator + fileName + ".jpg";
            FileUtils.writeByteArrayToFile(new File(filePath), imageBytes);
            photoUrlList.add(uploadPath + File.separator + fileName + ".jpg");
        }

        try {
            item.setPhotoURL(photoUrlList);
            itemService.updateItem(item.getId(), item);
            message = "The Item info was successfully updated";
        } catch (InvalidEntryException e) {
            message = "The entered info is not correct";
        } catch (ServiceException e) {
            message = "Failed to update item info. Please try again";
        }
        mv.addObject("message", message);
        mv.addObject("item", item);
        return mv;
    }


    @RequestMapping(value = {"/deleteItem/{id}"}, method = RequestMethod.GET)
    public ModelAndView deleteItem(HttpServletRequest request,
                                   @PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("redirect:/my-works");
        session = request.getSession();
        AbstractUser abstractUser = (AbstractUser) session.getAttribute("user");

        // Check whether the user is authenticated
        if(abstractUser==null){
            return new ModelAndView("redirect:/login");
        }
//        String[] pathInfo = request.getServletPath().split("/");
//        Long itemId = Long.parseLong(pathInfo[pathInfo.length - 1]);

        try {
            itemService.deleteItem(id);
            session.setAttribute("message", "Your ArtWork has been successfully deleted");
        }catch (RuntimeException e){
            String errorMessage = "There is problem with item deletion. Please try again";
            session.setAttribute("message",errorMessage);
        }
        return modelAndView;
    }


    private void changePersonalInfo(AbstractUser abstractUser, HttpServletRequest request, ModelAndView modelAndView) {
        User user = null;
        Artist artist = null;
        String firstName = request.getParameter("firstname");
        String lastName = request.getParameter("lastname");
        int age = 0;
        if (!request.getParameter("age").isEmpty()) {
            age = Integer.parseInt(request.getParameter("age"));
        }

        if (abstractUser instanceof User) {
            user = (User) abstractUser;
            user.setFirstName(firstName)
                    .setLastName(lastName)
                    .setAge(age);
        } else {
            artist = (Artist) abstractUser;
            artist.setFirstName(firstName)
                    .setLastName(lastName)
                    .setAge(age);
            ArtistSpecialization artistSpecialization = ArtistSpecialization.valueOf(request.getParameter("specialization"));
            artist.setSpecialization(artistSpecialization);
        }
        try {
            if (user != null) {
                userService.updateUser(user.getId(), user);
                request.getSession().setAttribute("user", user);
            } else {
                artistService.updateArtist(artist.getId(), artist);
                request.getSession().setAttribute("user", artist);
            }
            modelAndView.addObject("message", "You have successfully updated your profile info");
        } catch (RuntimeException e) {
            String errorMessage = "The entered info is not correct";
            modelAndView.addObject("message", errorMessage);
        }
    }

    private void changePassword(AbstractUser tempUser, HttpServletRequest request, ModelAndView modelAndView) {
        String newPassword = null;
        if (request.getParameter("oldpassword") != null &&
                request.getParameter("oldpassword").equals(tempUser.getPassword()) &&
                request.getParameter("newpassword") != null && request.getParameter("retypepassword") != null
                && request.getParameter("newpassword").equals(request.getParameter("retypepassword"))) {
            newPassword = request.getParameter("newpassword");
        }
        tempUser.setPassword(newPassword);

        try {
            if (tempUser instanceof User) {
                userService.updateUser(tempUser.getId(), (User) tempUser);
            } else {
                artistService.updateArtist(tempUser.getId(), (Artist) tempUser);
            }
            request.getSession().setAttribute("user", tempUser);
            modelAndView.addObject("message", "You have successfully updated your password");
        } catch (RuntimeException e) {
            String errorMessage = "The entered info is not correct";
            modelAndView.addObject("message", errorMessage);
        }
    }

    private void changeProfilePicture(Artist artist, ModelAndView modelAndView, MultipartFile image) throws IOException {
        try {
            if ((image) != null) {
                byte[] imageBytes = image.getBytes();
                artist.setArtistPhoto(imageBytes);
            } else {
                throw new RuntimeException();
            }
            artistService.updateArtist(artist.getId(), artist);
            modelAndView.addObject("user", artist);
            modelAndView.addObject("message", "You have successfully updated your profile picture");
        } catch (RuntimeException e) {
            String errorMessage = "The entered info is not correct";
            modelAndView.addObject("message", errorMessage);
        }
    }
}
