package am.aca.wftartproject.controller;

import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.model.ArtistSpecialization;
import am.aca.wftartproject.model.User;
import am.aca.wftartproject.service.ArtistService;
import am.aca.wftartproject.service.UserService;
import am.aca.wftartproject.service.impl.ArtistServiceImpl;
import am.aca.wftartproject.service.impl.UserServiceImpl;
import am.aca.wftartproject.util.SpringBeanType;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * @author surik
 */
public class SignUpServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("artistSpecTypes", ArtistSpecialization.values());
        request.getRequestDispatcher("/WEB-INF/views/signUp.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        UserService userService = SpringBean.getBeanFromSpring("userService", UserServiceImpl.class);
//        ArtistService artistService = SpringBean.getBeanFromSpring("artistService", ArtistService.class);


        UserService userService = CtxListener.getBeanFromSpring(SpringBeanType.USERSERVICE, UserServiceImpl.class);
        ArtistService artistService = CtxListener.getBeanFromSpring(SpringBeanType.ARTISTSERVICE, ArtistServiceImpl.class);

        User userFromRequest = null;
        Artist artistFromRequest = null;

        boolean chosenBuyerUser = request.getParameter("artistSpec") == null;

        if (chosenBuyerUser) {
            userFromRequest = new User();
            userFromRequest.setFirstName(request.getParameter("firstName"))
                    .setLastName(request.getParameter("lastName"))
                    .setAge(Integer.parseInt(request.getParameter("age")))
                    .setEmail(request.getParameter("email"))
                    .setPassword(request.getParameter("password"));
        } else {
            artistFromRequest = new Artist();
            artistFromRequest.setFirstName(request.getParameter("firstName"))
                    .setLastName(request.getParameter("lastName"))
                    .setAge(Integer.parseInt(request.getParameter("age")))
                    .setEmail(request.getParameter("email"))
                    .setPassword(request.getParameter("password"));

            //Photo download should be configured
//            artistFromRequest.setSpecialization(ArtistSpecialization.valueOf(request.getParameter("artistSpec")))
//                    .setArtistPhoto(request.getParameter("imageUpload"));

        }


        try {
            if (chosenBuyerUser) {
                userService.addUser(userFromRequest);
            } else {
                artistService.addArtist(artistFromRequest);
            }
        } catch (RuntimeException e) {
            String errorMessage = "The entered info is not correct";
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("/signup")
                    .forward(request, response);
        }
        HttpSession session = request.getSession(true);
        session.setAttribute("user", chosenBuyerUser ? userFromRequest : artistFromRequest);
        Cookie userEmail = new Cookie("userEmail", chosenBuyerUser ? userFromRequest.getEmail() : artistFromRequest.getEmail());
        userEmail.setMaxAge(3600);             // 60 minutes
        response.addCookie(userEmail);
      /*
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
        */
    }
}
