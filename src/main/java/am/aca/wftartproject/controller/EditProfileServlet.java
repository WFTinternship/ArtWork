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
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Armen on 6/9/2017
 */
public class EditProfileServlet extends HttpServlet {
    ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-root.xml");
    ArtistService artistService = (ArtistService)ctx.getBean("artistService");
    UserService userService = (UserService) ctx.getBean("userService");
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("artistSpecTypes", ArtistSpecialization.values());
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/edit-profile.jsp");
        dispatcher.forward(request, response);

    }

    public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{

        HttpSession session = request.getSession();
        User user;
        User finduser;
        Artist artist;
        Artist findArtist;
        if (session.getAttribute("user") != null ) {
            if (session.getAttribute("user").getClass().isInstance(User.class)) {
                user = (User) session.getAttribute("user");
                finduser = userService.findUser(user.getId());
                request.setAttribute("user", finduser);
                if (user != null) {
                    response.setContentType("text/html");
                    if(request.getParameter("firstname" ) != null){
                        finduser.setFirstName(request.getParameter("firstname"));
                    }
                    if(request.getParameter("lastname" ) != null){
                        finduser.setLastName(request.getParameter("lasstname"));
                    }
                    if(request.getParameter("age" ) != null){
                        finduser.setAge(Integer.parseInt(request.getParameter("age")));
                    }
                    try {
                        userService.updateUser(finduser.getId(),finduser);
                        request.getSession().setAttribute("user", finduser);
                        request.setAttribute("user", finduser);
                    }catch (ServiceException e){
                        String errorMessage = "The entered info is not correct";
                        request.setAttribute("errorMessage",errorMessage);
//            request.getRequestDispatcher("/signup")
//                    .forward(request,response);
                    }
                    request.setAttribute("user", finduser);
                    request.getSession().setAttribute("user", finduser);
                } else {
                    throw new RuntimeException("Incorrect program logic");
                }
            } else if (session.getAttribute("user").getClass().isInstance(Artist.class)) {
                artist = (Artist) session.getAttribute("user");
                findArtist = artistService.findArtist(artist.getId());
                if (artist != null) {
                    response.setContentType("text/html");
                    if(request.getParameter("firstname" ) != null){
                        findArtist.setFirstName(request.getParameter("firstname"));
                    }
                    if(request.getParameter("lastname" ) != null){
                        findArtist.setLastName(request.getParameter("lasstname"));
                    }
                    if(request.getParameter("age" ) != null){
                        findArtist.setAge(Integer.parseInt(request.getParameter("age")));
                    }
//                    if(request.getParameter("specialization" ) != null){
//                        artist.setSpecialization(ArtistSpecialization.PAINTER);
//                    }
                    try {
                        artistService.updateArtist(artist.getId(),findArtist);
                        request.getSession().setAttribute("user", findArtist);
                        request.setAttribute("user", findArtist);
                    }catch (ServiceException e){
                        String errorMessage = "The entered info is not correct";
                        request.setAttribute("errorMessage",errorMessage);
//            request.getRequestDispatcher("/signup")
//                    .forward(request,response);
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
