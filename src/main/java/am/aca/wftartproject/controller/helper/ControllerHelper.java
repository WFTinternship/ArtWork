package am.aca.wftartproject.controller.helper;

import am.aca.wftartproject.entity.*;
import am.aca.wftartproject.exception.service.ServiceException;
import am.aca.wftartproject.service.*;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Armen on 7/15/2017.
 */
public class ControllerHelper {
    @Autowired
    protected UserService userService;
    @Autowired
    protected ArtistService artistService;
    @Autowired
    protected ItemService itemService;
    @Autowired
    ShoppingCardService shoppingCardService;
    @Autowired
    protected PurchaseHistoryService purchaseHistoryService;

    //page location constants
    public static final String HOME = "/index";
    public static final String SIGNUP = "/signUp";
    public static final String REDIRECT_SIGNUP = "redirect:/signUp";
    public static final String MY_WORKS = "my-works";
    public static final String REDIRECT_MY_WORKS = "redirect:/my-works";
    public static final String PURCHASE_HISTORY = "purchaseHistory";
    public static final String SHOP = "shop";
    public static final String REDIRECT_SHOP = "shop";
    public static final String THANK_YOU = "thank-you";
    public static final String LOGIN = "logIn";
    public static final String ITEM_DETAIL = "item-detail";
    public static final String EDIT_PROFILE = "editProfile";
    public static final String REDIRECT_EDIT_PROFILE = "redirect:/editProfile";
    public static final String CONTACT = "contact";
    public static final String ADD_ITEM = "additem";
    public static final String ACCOUNT = "account";
    public static final String ABOUT = "about";

    //model attribute constants
    public static final String USER = "user";
    public static final String ATTRIBUTE_PURCHASE_HISTORY = "purchaseHistory";

    protected User updateUserParameters(User findUser, HttpServletRequest request) {
        int counter = 0;
        findUser.setUserPasswordRepeat(findUser.getPassword());
        if (request.getParameter("firstName") != null && !request.getParameter("firstName").isEmpty()) {
            findUser.setFirstName(request.getParameter("firstName"));
            counter++;
        }
        if (request.getParameter("lastName") != null && !request.getParameter("lastName").isEmpty()) {
            findUser.setLastName(request.getParameter("lastName"));
            counter++;
        }
        if (request.getParameter("age") != null && !request.getParameter("age").isEmpty()) {
            findUser.setAge(Integer.parseInt(request.getParameter("age")));
            counter++;
        }
        if (request.getParameter("oldpassword") != null && !request.getParameter("oldpassword").isEmpty() && request.getParameter("oldpassword").equals(findUser.getPassword()) && request.getParameter("newpassword") != null && request.getParameter("retypepassword") != null && request.getParameter("newpassword").equals(request.getParameter("retypepassword"))) {
            findUser.setPassword(request.getParameter("newpassword"));
            findUser.setUserPasswordRepeat(request.getParameter("retypepassword"));
            counter++;
        }
        if (counter == 0) {
            throw new RuntimeException("Nothing Was Changed");
        }
        return findUser;
    }

    protected Artist updateArtistParameters(Artist findArtist, HttpServletRequest request, MultipartFile image) throws IOException {
        int count = 0;
        findArtist.setUserPasswordRepeat(findArtist.getPassword());
        if (request.getParameter("firstName") != null && !request.getParameter("firstName").isEmpty()) {
            findArtist.setFirstName(request.getParameter("firstName"));
            count++;
        }
        if (request.getParameter("lastName") != null && !request.getParameter("lastName").isEmpty()) {
            findArtist.setLastName(request.getParameter("lastName"));
            count++;
        }
        if (request.getParameter("age") != null && !request.getParameter("age").isEmpty()) {
            findArtist.setAge(Integer.parseInt(request.getParameter("age")));
            count++;
        }
        if (request.getParameter("specialization") != null && !request.getParameter("specialization").isEmpty() && Integer.valueOf(request.getParameter("specialization")) != -1) {
            findArtist.setSpecialization(ArtistSpecialization.valueOf(request.getParameter("specialization")));
            count++;
        }
        if (request.getParameter("oldpassword") != null && !request.getParameter("oldpassword").isEmpty() && request.getParameter("oldpassword").equals(findArtist.getPassword()) && request.getParameter("newpassword") != null && !request.getParameter("newpassword").isEmpty() && !request.getParameter("retypepassword").isEmpty() && request.getParameter("retypepassword") != null && request.getParameter("newpassword").equals(request.getParameter("retypepassword"))) {
            findArtist.setPassword(request.getParameter("newpassword"));
            findArtist.setUserPasswordRepeat(request.getParameter("retypepassword"));
            count++;
        }
        if ((image) != null) {
            byte[] imageBytes = image.getBytes();
            findArtist.setArtistPhoto(imageBytes);
            count++;
        }
        if (count == 0) {
            throw new RuntimeException();
        }
        return findArtist;

    }


    protected void userServiceUpdater(User findUser, HttpServletRequest request) {
        try {
            userService.updateUser(findUser);
            String message = "User updated successfully";
            request.setAttribute("message", message);
            request.getSession().setAttribute("user", findUser);
        } catch (ServiceException e) {
            String errorMessage = "The entered info is not correct";
            request.setAttribute("message", errorMessage);
        }
    }

    protected void artistServiceUpdater(Artist findArtist, HttpServletRequest request) {
        try {
            artistService.updateArtist(findArtist);
            request.setAttribute("message", "You have successfully updated your profile");
            request.getSession().setAttribute("user", findArtist);
        } catch (ServiceException e) {
            String errorMessage = "The entered info is not correct";
            request.setAttribute("message", errorMessage);
        }
    }

    protected void artistSaver(Artist artistFromRequest, HttpServletRequest request) {
        ShoppingCard shoppingCard = new ShoppingCard(5000, ShoppingCardType.PAYPAL);
        artistFromRequest.setShoppingCard(shoppingCard);
        artistService.addArtist(artistFromRequest);
        artistFromRequest.getShoppingCard().setAbstractUser(artistFromRequest);
        shoppingCardService.addShoppingCard(artistFromRequest.getShoppingCard());
        request.setAttribute("message", "Hi " + artistFromRequest.getFirstName());
        HttpSession session = request.getSession(true);
        session.setAttribute("user", artistFromRequest);
    }

    protected void userSaver(User user, HttpServletRequest request) {
        ShoppingCard shoppingCard = new ShoppingCard(5000, ShoppingCardType.PAYPAL);
        shoppingCard.setAbstractUser(user);
        user.setShoppingCard(shoppingCard);
        user.setUserPasswordRepeat(request.getParameter("userPasswordRepeat"));
        userService.addUser(user);
        user.getShoppingCard().setAbstractUser(user);
        shoppingCardService.addShoppingCard(user.getShoppingCard());
        request.getSession(true).setAttribute("message", "Hi " + user.getFirstName());
        request.getSession().setAttribute("user", user);
    }

    protected void itemServiceSaver(Item item, HttpServletRequest request) {
        itemService.addItem(item);
        request.getSession().setAttribute("item", item);
        String message = "Your ArtWork has been successfully added, Now you can see it in My ArtWorks page";
        request.setAttribute("message", message);
    }

    protected void retrieveUserDetailsFromSession(HttpSession session) {
        if (session.getAttribute("user") != null) {
            if (session.getAttribute("user").getClass() == User.class) {
                User user = (User) session.getAttribute("user");
                if (user != null) {
                    User findUser = userService.findUser(user.getId());
                    session.setAttribute("user", findUser);
                } else {
                    throw new RuntimeException("Incorrect program logic");
                }
            } else if (session.getAttribute("user").getClass() == Artist.class) {
                Artist artist = (Artist) session.getAttribute("user");
                Artist findArtist = artistService.findArtist(artist.getId());
                String image = Base64.getEncoder().encodeToString(findArtist.getArtistPhoto());
                session.setAttribute("image", image);
                session.setAttribute("user", findArtist);
            }
        } else throw new RuntimeException("");
    }

    protected void createItemFromRequest(Item item, Artist findArtist, List<String> photoUrl, HttpServletRequest request) {
        if (!request.getParameter("title").isEmpty() && !request.getParameter("description").isEmpty() && !request.getParameter("type").isEmpty() && !request.getParameter("price").isEmpty() && !request.getParameter("type").equals("-1")) {
            item.setTitle(request.getParameter("title"));
            item.setDescription(request.getParameter("description"));
            item.setItemType(ItemType.valueOf(request.getParameter("type")));
            item.setPrice(Double.parseDouble(request.getParameter("price")));
            item.setStatus(true);
            item.setPhotoURL(photoUrl);
            item.setArtist(findArtist);
            item.setAdditionDate(Calendar.getInstance().getTime());
        } else throw new ServiceException("The entered info is not correct or empty fields");

    }

    protected void createArtistFromRequest(Artist artistFromRequest, MultipartFile image, HttpServletRequest request) throws IOException {
        artistFromRequest
                .setSpecialization(ArtistSpecialization.valueOf(request.getParameter("artistSpec")))
                .setArtistPhoto(image.getBytes())
                .setFirstName(request.getParameter("firstName"))
                .setLastName(request.getParameter("lastName"))
                .setAge(Integer.parseInt(request.getParameter("age")))
                .setEmail(request.getParameter("email"))
                .setPassword(request.getParameter("password"))
                .setUserPasswordRepeat(request.getParameter("passwordRepeat"));
    }

    protected void artistImageUploader(Artist artist, MultipartFile[] image, List<String> photoUrl, HttpServletRequest request) throws IOException {
        for (MultipartFile multipartFile : image) {
            byte[] imageBytes = multipartFile.getBytes();
            String uploadPath = "/resources/images/artists/" + artist.getId();
            String realPath = request.getServletContext().getRealPath(uploadPath);
            File uploadDir = new File(realPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String fileName = multipartFile.getOriginalFilename();
            String filePath = realPath + File.separator + fileName + ".jpg";
            FileUtils.writeByteArrayToFile(new File(filePath), imageBytes);
            photoUrl.add(uploadPath + File.separator + fileName + ".jpg");
        }
    }

    protected User getUserFromSession(HttpSession session) {
        User user = (User) session.getAttribute("user");
        user = userService.findUser(user.getId());
        session.setAttribute("user", user);
        return user;
    }

    protected Artist getArtistFromSession(HttpSession session) {
        Artist artist = (Artist) session.getAttribute("user");
        artist = artistService.findArtist(artist.getId());
        return artist;
    }

    protected void setErrorMessage(HttpServletRequest request) {
        String message = "No changes ,empty field or The entered info is not correct";
        request.setAttribute("message", message);
    }


}
