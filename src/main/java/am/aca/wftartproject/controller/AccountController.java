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

    private Artist artist;
    private Artist findArtist;
    private HttpSession session;

    private final UserService userService;
    private final ArtistService artistService;
    private final PurchaseHistoryService purchaseHistoryService;
    private final ItemService itemService;

    @Autowired
    public AccountController(UserService userService, ArtistService artistService,
                             PurchaseHistoryService purchaseHistoryService, ItemService itemService) {
        this.userService = userService;
        this.artistService = artistService;
        this.purchaseHistoryService = purchaseHistoryService;
        this.itemService = itemService;
    }

    @RequestMapping(value = {"/account"})
    public ModelAndView accountInfo(HttpServletRequest request, HttpServletResponse response) {
        session = request.getSession();
        ModelAndView mv = new ModelAndView();
        String page = "redirect:/signup";
        Class<?> userClass = session.getAttribute("user").getClass();
        try {
            if (session.getAttribute("user") != null) {
                if (userClass == User.class) {
                    User user = (User) session.getAttribute("user");
                    User findUser = userService.findUser(user.getId());
                    if (findUser != null) {
                        session.setAttribute("user", findUser);
                        page = "account";
                    } else {
                        throw new RuntimeException("Incorrect program logic");
                    }
                } else if (userClass == Artist.class) {
                    artist = (Artist) session.getAttribute("user");
                    findArtist = artistService.findArtist(artist.getId());
                    if (findArtist != null) {
                        String image = Base64.getEncoder().encodeToString(findArtist.getArtistPhoto());
                        mv.addObject("image", image);
                        session.setAttribute("user", findArtist);
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
    public ModelAndView editProfileProcess(HttpServletRequest request, HttpServletResponse response,
                                           @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {

        session = request.getSession();
        String command = request.getParameter("editBlock");
        AbstractUser abstractUser = (AbstractUser) session.getAttribute("user");
        ModelAndView modelAndView = new ModelAndView("/edit-profile");

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
    public ModelAndView purchaseHistory(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        String page = "purchase-history";
        session = request.getSession();
        if (session.getAttribute("user") != null) {
            AbstractUser abstractUser = (AbstractUser) session.getAttribute("user");
            modelAndView.addObject("purchaseHistory", purchaseHistoryService.getPurchase(abstractUser.getId()));
        } else {
            page = "redirect:/signup";
        }
        return new ModelAndView(page);
    }

    @RequestMapping(value = {"/additem"}, method = RequestMethod.GET)
    public ModelAndView addItem(HttpServletRequest request, HttpServletResponse response) {
        String page = "additem";
        ModelAndView modelAndView = new ModelAndView();
        if (request.getSession().getAttribute("user") == null) {
            page = "redirect:/signup";
        }
        modelAndView.addObject("item", new Item());
        session.setAttribute("itemTypes", ItemType.values());
        return new ModelAndView(page);
    }


    @RequestMapping(value = {"/additem"}, method = RequestMethod.POST)
    public ModelAndView addItemProcess(HttpServletRequest request, HttpServletResponse response,
                                       @RequestParam(value = "files", required = false) MultipartFile[] image
    ) throws IOException {
        session = request.getSession();
        String message;
        List<String> photoUrl = new ArrayList<>();
        Item item = new Item();
        if (session.getAttribute("user") != null && session.getAttribute("user").getClass() == Artist.class) {
            artist = (Artist) session.getAttribute("user");
            findArtist = artistService.findArtist(artist.getId());
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
    public ModelAndView myWorks(HttpServletRequest request, HttpServletResponse response) {
        String page = "my-works";
        ModelAndView modelAndView = new ModelAndView();
        session = request.getSession();
        if (session.getAttribute("user") != null && session.getAttribute("user").getClass() == Artist.class) {
            artist = (Artist) session.getAttribute("user");
            modelAndView.addObject("artistItems", itemService.getArtistItems(artist.getId(), 8888L, 100L));
        } else {
            page = "redirect:/signup";
        }
        modelAndView.setViewName(page);
        return modelAndView;
    }

    @RequestMapping(value = "/edit-item/{id}", method = RequestMethod.GET)
    public ModelAndView editItemGet(HttpServletRequest request,@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("edit-item");
        Item item = itemService.findItem(id);
        request.getSession().setAttribute("item",item);
        mv.addObject("itemTypes", ItemType.values());
        return mv;
    }

    @RequestMapping(value = "/edit-item/{id}", method = RequestMethod.POST)
    public ModelAndView editItemPost(HttpServletRequest request,
                                     @PathVariable("id") Integer id,
                                     @RequestParam("title") String title,
                                     @RequestParam("description") String description,
                                     @RequestParam("price") Double price,
                                     @RequestParam("itemType") String itemType,
                                     @RequestParam(value = "image", required = false) MultipartFile image,
                                     @SessionAttribute("item") Item item) throws IOException {
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
        }catch (ServiceException e){
            message = "Failed to update item info. Please try again";
        }
        mv.addObject("message", message);
        mv.addObject("item", item);
        return mv;
    }


    @RequestMapping(value = {"/deleteItem/*"}, method = RequestMethod.GET)
    public ModelAndView deleteItem(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        String[] pathInfo = request.getServletPath().split("/");
        Long itemId = Long.parseLong(pathInfo[pathInfo.length - 1]);
        itemService.deleteItem(itemId);
        request.setAttribute("message", "Your ArtWork has been successfully deleted");
        modelAndView.setViewName("redirect:/my-works");
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
