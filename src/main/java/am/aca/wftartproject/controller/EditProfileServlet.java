package am.aca.wftartproject.controller;

import am.aca.wftartproject.exception.service.ServiceException;
import am.aca.wftartproject.exception.service.ServiceException;
import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.model.ArtistSpecialization;
import am.aca.wftartproject.model.User;
import am.aca.wftartproject.service.ArtistService;
import am.aca.wftartproject.service.ItemService;
import am.aca.wftartproject.service.UserService;
import am.aca.wftartproject.service.impl.ArtistServiceImpl;
import am.aca.wftartproject.service.impl.ItemServiceImpl;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.RequestDispatcher;
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
    @Autowired
    ArtistService artistService ;
    @Autowired
    UserService userService ;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("artistSpecTypes", ArtistSpecialization.values());
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/edit-profile.jsp");
        dispatcher.forward(request, response);

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user;
        User finduser;
        Artist artist;
        Artist findArtist;
        Part filePart;
        InputStream inputStream;
        if (session.getAttribute("user") != null) {
            if (session.getAttribute("user").getClass() == User.class) {
                user = (User) session.getAttribute("user");
                finduser = userService.findUser(user.getId());
                request.setAttribute("user", finduser);
                if (user != null) {
                    response.setContentType("text/html");
                    if (request.getParameter("firstname") != null) {
                        finduser.setFirstName(request.getParameter("firstname"));
                    }
                    if (request.getParameter("lastname") != null) {
                        finduser.setLastName(request.getParameter("lasstname"));
                    }
                    if (request.getParameter("age") != null) {
                        finduser.setAge(Integer.parseInt(request.getParameter("age")));
                    }

                    try {
                        userService.updateUser(finduser.getId(), finduser);
                        request.getSession().setAttribute("user", finduser);
                        request.setAttribute("user", finduser);
                    } catch (ServiceException e) {
                        String errorMessage = "The entered info is not correct";
                        request.setAttribute("errorMessage", errorMessage);

                    }
                    request.setAttribute("user", finduser);
                    request.getSession().setAttribute("user", finduser);
                } else {
                    throw new RuntimeException("Incorrect program logic");
                }
            } else if (session.getAttribute("user").getClass() == Artist.class) {
                artist = (Artist) session.getAttribute("user");
                findArtist = artistService.findArtist(artist.getId());
                if (artist != null) {
                    response.setContentType("text/html");
                    if (request.getParameter("firstname") != null) {
                        findArtist.setFirstName(request.getParameter("firstname"));
                    }
                    if (request.getParameter("lastname") != null) {
                        findArtist.setLastName(request.getParameter("lastname"));
                    }
                    if (request.getParameter("age") != null) {
                        findArtist.setAge(Integer.parseInt(request.getParameter("age")));
                    }
                    if (request.getParameter("specialization") != null) {
                        findArtist.setSpecialization(ArtistSpecialization.valueOf(request.getParameter("specialization")));
                    }
                    if (request.getParameter("oldpassword") != null && request.getParameter("oldpassword").equals(findArtist.getPassword()) && request.getParameter("newpassword") != null && request.getParameter("retypepassword") != null && request.getParameter("newpassword").equals(request.getParameter("retypepassword"))) {
                        findArtist.setPassword(request.getParameter("newpassword"));
                    }
                    if (request.getPart("image") != null) {
                        filePart = request.getPart("image");
                        if (filePart != null) {
                            inputStream = filePart.getInputStream();
                            byte[] imageBytes = IOUtils.toByteArray(inputStream);
                            findArtist.setArtistPhoto(imageBytes);
                        }
                    }

                    try {
                        artistService.updateArtist(artist.getId(), findArtist);
                        request.getSession().setAttribute("user", findArtist);
                        request.setAttribute("user", findArtist);
                    } catch (ServiceException e) {
                        String errorMessage = "The entered info is not correct";
                        request.setAttribute("errorMessage", errorMessage);

                    }
                } else {
                    throw new RuntimeException("Incorrect program logic");
                }
            }
        }

        try {
            response.setContentType("html/text");

            response.sendRedirect("/edit-profile");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
