package am.aca.wftartproject.controller;

import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.model.ArtistSpecialization;
import am.aca.wftartproject.model.User;
import am.aca.wftartproject.service.ArtistService;
import am.aca.wftartproject.service.UserService;
import am.aca.wftartproject.service.impl.ArtistServiceImpl;
import am.aca.wftartproject.service.impl.UserServiceImpl;
import am.aca.wftartproject.util.SpringBean;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author surik
 */
public class SignUpServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/signUp.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UserService userService = SpringBean.getBeanFromSpring("userService", UserServiceImpl.class);
        ArtistService artistService = SpringBean.getBeanFromSpring("artistService", ArtistServiceImpl.class);

        if(request.getParameter("artistName")== null){
            User userFromRequest = new User();
            userFromRequest.setFirstName(request.getParameter("firstName"))
                    .setLastName(request.getParameter("lastName"))
                    .setAge(Integer.parseInt(request.getParameter("age")))
                    .setEmail(request.getParameter("email"))
                    .setPassword(request.getParameter("password"));

            try {
                userService.addUser(userFromRequest);
            }catch (RuntimeException e){
                String errorMessage = "The entered info is not correct";
                request.setAttribute("errorMessage",errorMessage);
                request.getRequestDispatcher("/signup")
                        .forward(request,response);
            }
            HttpSession session = request.getSession(true);
            session.setAttribute("user", userFromRequest);
            Cookie userEmail = new Cookie("userEmail", userFromRequest.getEmail());
            userEmail.setMaxAge(3600);             // 60 minutes
            response.addCookie(userEmail);
        }
        else {
            Artist artistFromRequest = new Artist();
            artistFromRequest.setSpecialization(ArtistSpecialization.PAINTER)
                    .setFirstName(request.getParameter("artistName"))
                    .setLastName(request.getParameter("artistLastName"))
                    .setAge(Integer.parseInt(request.getParameter("artistAge")))
                    .setEmail(request.getParameter("artistEmail"))
                    .setPassword(request.getParameter("artistPassword"));

            try {
                artistService.addArtist(artistFromRequest);
            }catch (RuntimeException e){
                String errorMessage = "The entered info is not correct";
                request.setAttribute("errorMessage",errorMessage);
                request.getRequestDispatcher("/signup")
                        .forward(request,response);
            }
            HttpSession session = request.getSession(true);
            session.setAttribute("user", artistFromRequest);
            Cookie userEmail = new Cookie("userEmail", artistFromRequest.getEmail());
            userEmail.setMaxAge(3600);             // 60 minutes
            response.addCookie(userEmail);
        }
        try {
            response.setContentType("html/text");
            response.sendRedirect("/index");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
