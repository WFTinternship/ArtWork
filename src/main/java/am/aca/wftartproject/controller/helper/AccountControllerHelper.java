package am.aca.wftartproject.controller.helper;

import am.aca.wftartproject.entity.*;
import am.aca.wftartproject.exception.service.ServiceException;
import am.aca.wftartproject.service.ArtistService;
import am.aca.wftartproject.service.ItemService;
import am.aca.wftartproject.service.UserService;
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
public class AccountControllerHelper {
    @Autowired
    private UserService userService;
    @Autowired
    private ArtistService artistService;
    @Autowired
    private ItemService itemService;

    protected User updateUserParameters(User findUser, HttpServletRequest request){
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
        if(counter==0){
            throw new RuntimeException("Nothing Was Changed");
        }
        return findUser;
    }
    protected  Artist updateArtistParameters(Artist findArtist, HttpServletRequest request, MultipartFile image) throws IOException {
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
        if(count == 0){
            throw new RuntimeException();
        }
        return findArtist;

    }


    protected void userServiceUpdater(User findUser,HttpServletRequest request){
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

    protected void artistServiceUpdater(Artist findArtist,HttpServletRequest request){
        try {
            artistService.updateArtist(findArtist);
            request.setAttribute("message", "You have successfully updated your profile");
            request.getSession().setAttribute("user", findArtist);
        } catch (ServiceException e) {
            String errorMessage = "The entered info is not correct";
            request.setAttribute("message", errorMessage);
        }
    }

    protected void itemServiceSaver(Item item,HttpServletRequest request){
        itemService.addItem(item);
        request.getSession().setAttribute("item", item);
        String  message = "Your ArtWork has been successfully added, Now you can see it in My ArtWorks page";
        request.setAttribute("message", message);
    }

    protected void retrieveUserDetailsFromSession(HttpSession session){
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
                if (findArtist != null) {
                    session.setAttribute("image", image);
                    session.setAttribute("user", findArtist);
                } else {
                    throw new RuntimeException("Incorrect program logic");
                }
            }
        }
        else throw new RuntimeException("");
    }

    protected void createItemFromRequest(Item item,Artist findArtist, List<String> photoUrl, HttpServletRequest request){
        if( !request.getParameter("title").isEmpty() && !request.getParameter("description").isEmpty() && !request.getParameter("type").isEmpty() && !request.getParameter("price").isEmpty() && !request.getParameter("type").equals("-1"))
        {
            item.setTitle(request.getParameter("title"));
            item.setDescription(request.getParameter("description"));
            item.setItemType(ItemType.valueOf(request.getParameter("type")));
            item.setPrice(Double.parseDouble(request.getParameter("price")));
            item.setStatus(true);
            item.setPhotoURL(photoUrl);
            item.setArtist(findArtist);
            item.setAdditionDate(Calendar.getInstance().getTime());
        }
        else throw new ServiceException("The entered info is not correct or empty fields");

    }
    protected void artistImageUploader(Artist artist, MultipartFile[] image, List<String> photoUrl,HttpServletRequest request) throws IOException {
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
    }
    protected User getUserFromSession(HttpSession session){
        User user = (User) session.getAttribute("user");
        user = userService.findUser(user.getId());
        session.setAttribute("user", user);
        return user;
    }

    protected Artist getArtistFromSession(HttpSession session){
        Artist artist = (Artist) session.getAttribute("user");
        artist = artistService.findArtist(artist.getId());
        return artist;
    }
    protected void setErrorMessage(HttpServletRequest request){
       String message = "No changes ,empty field or The entered info is not correct";
        request.setAttribute("message", message);
    }


}
