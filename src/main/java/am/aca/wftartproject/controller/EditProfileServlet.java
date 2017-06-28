package am.aca.wftartproject.controller;

import am.aca.wftartproject.model.AbstractUser;
import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.model.ArtistSpecialization;
import am.aca.wftartproject.model.User;
import am.aca.wftartproject.service.ArtistService;
import am.aca.wftartproject.service.UserService;
import am.aca.wftartproject.service.impl.ArtistServiceImpl;
import am.aca.wftartproject.service.impl.UserServiceImpl;
import am.aca.wftartproject.util.SpringBeanType;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Armen on 6/9/2017
 */
@MultipartConfig(maxFileSize = 2177215)
public class EditProfileServlet extends HttpServlet {

    private ArtistService artistService = CtxListener.getBeanFromSpring(SpringBeanType.ARTISTSERVICE, ArtistServiceImpl.class);
    private UserService userService = CtxListener.getBeanFromSpring(SpringBeanType.USERSERVICE, UserServiceImpl.class);


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("artistSpecTypes", ArtistSpecialization.values());
        request.getRequestDispatcher("/WEB-INF/views/edit-profile.jsp")
                .forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        String command = request.getParameter("editBlock");
        AbstractUser abstractUser = (AbstractUser) session.getAttribute("user");

        switch (command) {
            case ("persInfo"):
                changePersonalInfo(abstractUser, request, response);
                break;
            case ("password"):
                changePassword(abstractUser, request, response);
                break;
            case ("avatar"):
                changeProfilePicture((Artist) abstractUser, request, response);
                break;
        }

        try {
            response.setContentType("html/text");
            response.sendRedirect("/account");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void changePersonalInfo(AbstractUser tempUser, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String firstName = request.getParameter("firstname");
        String lastName = request.getParameter("lastname");
        User user = null;
        Artist artist = null;
        int age = -1;
        if (!request.getParameter("age").isEmpty()) {
            age = Integer.parseInt(request.getParameter("age"));
        }

        if (tempUser instanceof User) {
            user = (User) tempUser;
            user.setFirstName(firstName)
                    .setLastName(lastName)
                    .setAge(age);
        } else {
            artist = (Artist) tempUser;
            ArtistSpecialization artistSpecialization = ArtistSpecialization.valueOf(request.getParameter("specialization"));
            artist.setFirstName(firstName)
                    .setLastName(lastName)
                    .setAge(age);
            artist.setSpecialization(artistSpecialization);
        }
        try {
            if (user != null) {
                userService.updateUser(tempUser.getId(), user);
            } else {
                artistService.updateArtist(tempUser.getId(), artist);
            }
            request.getSession().setAttribute("user", tempUser);
        } catch (RuntimeException e) {
            String errorMessage = "The entered info is not correct";
            request.getSession().setAttribute("errorMessage", errorMessage);
        }

    }

    private void changePassword(AbstractUser tempUser, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
        } catch (RuntimeException e) {
            String errorMessage = "The entered info is not correct";
            request.getSession().setAttribute("errorMessage", errorMessage);
        }
    }

    private void changeProfilePicture(Artist artist, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        Part filePart;
        InputStream inputStream;

        if (request.getPart("image") != null) {
            filePart = request.getPart("image");
            if (filePart != null) {
                inputStream = filePart.getInputStream();
                byte[] imageBytes = IOUtils.toByteArray(inputStream);
                artist.setArtistPhoto(imageBytes);
            }
        }

        try {
            artistService.updateArtist(artist.getId(), artist);
            request.getSession().setAttribute("user", artist);
        } catch (RuntimeException e) {
            String errorMessage = "The entered info is not correct";
            request.getSession().setAttribute("errorMessage", errorMessage);
        }
    }


//        HttpSession session = request.getSession();
//
//        User userFromRequest;
//        User findUserFromDB;
//        Artist artistFromRequest;
//        Artist findArtistFromDB;
//
//        Part filePart;
//        InputStream inputStream;
//
//        if (session.getAttribute("user") != null) {
//
//            if (session.getAttribute("user").getClass() == User.class) {
//
//                userFromRequest = (User) session.getAttribute("user");
//                findUserFromDB = userService.findUser(userFromRequest.getId());
//                if (userFromRequest != null) {
//                    if (request.getParameter("firstname") != null) {
//                        findUserFromDB.setFirstName(request.getParameter("firstname"));
//                    }
//                    if (request.getParameter("lastname") != null) {
//                        findUserFromDB.setLastName(request.getParameter("lastname"));
//                    }
//                    if (request.getParameter("age") != null) {
//                        findUserFromDB.setAge(Integer.parseInt(request.getParameter("age")));
//                    }
//
//                    try {
//                        userService.updateUser(findUserFromDB.getId(), findUserFromDB);
//                        session.setAttribute("user", findUserFromDB);
//                    } catch (InvalidEntryException | ServiceException e) {
//                        String errorMessage = "The entered info is not correct";
//                        request.setAttribute("errorMessage", errorMessage);
//                        request.getRequestDispatcher("/edit-profile")
//                                .forward(request, response);
//                    }
//                } else {
//                    throw new RuntimeException("Incorrect program logic");
//                }
//
//
//            } else if (session.getAttribute("user").getClass() == Artist.class) {
//
//                artistFromRequest = (Artist) session.getAttribute("user");
//                findArtistFromDB = artistService.findArtist(artistFromRequest.getId());
//
//                if (artistFromRequest != null) {
//                    if (request.getParameter("firstname") != null) {
//                        findArtistFromDB.setFirstName(request.getParameter("firstname"));
//                    }
//                    if (request.getParameter("lastname") != null) {
//                        findArtistFromDB.setLastName(request.getParameter("lastname"));
//                    }
//                    if (request.getParameter("age") != null) {
//                        findArtistFromDB.setAge(Integer.parseInt(request.getParameter("age")));
//                    }
//
//
//                    if (request.getParameter("specialization") != null) {
//                        findArtistFromDB.setSpecialization(ArtistSpecialization.valueOf(request.getParameter("specialization")));
//                    }
//                    if (request.getParameter("oldpassword") != null && request.getParameter("oldpassword").equals(findArtistFromDB.getPassword()) && request.getParameter("newpassword") != null && request.getParameter("retypepassword") != null && request.getParameter("newpassword").equals(request.getParameter("retypepassword"))) {
//                        findArtistFromDB.setPassword(request.getParameter("newpassword"));
//                    }
//                    if (request.getPart("image") != null) {
//                        filePart = request.getPart("image");
//                        if (filePart != null) {
//                            inputStream = filePart.getInputStream();
//                            byte[] imageBytes = IOUtils.toByteArray(inputStream);
//                            findArtistFromDB.setArtistPhoto(imageBytes);
//                        }
//                    }
//                    try {
//                        artistService.updateArtist(artistFromRequest.getId(), findArtistFromDB);
//                        session.setAttribute("user", findArtistFromDB);
//                    } catch (InvalidEntryException | ServiceException e) {
//                        String errorMessage = "The entered info is not correct";
//                        request.setAttribute("errorMessage", errorMessage);
//                        request.getRequestDispatcher("/edit-profile")
//                                .forward(request, response);
//                    }
//                } else {
//                    throw new RuntimeException("Incorrect program logic");
//                }
//            }
//        }
//        try {
//            response.setContentType("html/text");
//            response.sendRedirect("/account");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

}






