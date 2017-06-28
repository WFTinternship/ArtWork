package am.aca.wftartproject.controller;

import am.aca.wftartproject.model.*;
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
 * @author surik
 */
@MultipartConfig(maxFileSize = 2177215)
public class SignUpServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("artistSpecTypes", ArtistSpecialization.values());
        request.getRequestDispatcher("/WEB-INF/views/signUp.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UserService userService = CtxListener.getBeanFromSpring(SpringBeanType.USERSERVICE, UserServiceImpl.class);
        ArtistService artistService = CtxListener.getBeanFromSpring(SpringBeanType.ARTISTSERVICE, ArtistServiceImpl.class);

        User userFromRequest = null;
        ShoppingCard shoppingCard = null;
        Artist artistFromRequest = null;
        InputStream inputStream; // input stream of the upload file
        Part filePart;


        // obtains the upload file part in this multipart request

//        boolean chosenArtist = request.getParameter("artistSpec") != null;
        boolean chosenBuyer = request.getParameter("chosenOption").equals("buyer");

        if (chosenBuyer) {
            userFromRequest = new User();
            userFromRequest.setFirstName(request.getParameter("firstName"))
                    .setLastName(request.getParameter("lastName"))
                    .setAge(Integer.parseInt(request.getParameter("age")))
                    .setEmail(request.getParameter("email"))
                    .setPassword(request.getParameter("password"))
                    .setShoppingCard(new ShoppingCard(ShoppingCardType.valueOf(request.getParameter("paymentMethod"))));
        } else {
            artistFromRequest = new Artist();
            filePart = request.getPart("imageUpload");
            if (filePart != null) {
                inputStream = filePart.getInputStream();
                byte[] imageBytes = IOUtils.toByteArray(inputStream);
                //  FileUtils.writeByteArrayToFile(new File("pathname"), imageBytes);
                artistFromRequest
                        .setFirstName(request.getParameter("firstName"))
                        .setLastName(request.getParameter("lastName"))
                        .setEmail(request.getParameter("email"))
                        .setPassword(request.getParameter("password"))
                        .setAge(Integer.parseInt(request.getParameter("age")));
                artistFromRequest.setArtistPhoto(imageBytes)
                        .setSpecialization(ArtistSpecialization.valueOf(request.getParameter("artistSpec")));
            }

        }

        String page;
        try {
            if (chosenBuyer) {
                userService.addUser(userFromRequest);
            } else {
                artistService.addArtist(artistFromRequest);
            }

            HttpSession session = request.getSession(true);
            session.setAttribute("user", chosenBuyer ? userFromRequest : artistFromRequest);
            Cookie userEmail = new Cookie("userEmail", chosenBuyer ? userFromRequest.getEmail() : artistFromRequest.getEmail());
            userEmail.setMaxAge(3600);             // 60 minutes
            response.addCookie(userEmail);
            page = "/index";
            response.sendRedirect(page);
        } catch (RuntimeException e) {
            String errorMessage = "The entered info is not correct";
            request.setAttribute("errorMessage", errorMessage);
            page = "/signup";
            response.sendRedirect(page);
//            request.getRequestDispatcher(page)
//                    .forward(request, response);
        }




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

        */


    }
}
