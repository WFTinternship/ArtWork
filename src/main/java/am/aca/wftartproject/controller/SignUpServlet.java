//package am.aca.wftartproject.controller;
//
//import am.aca.wftartproject.model.Artist;
//import am.aca.wftartproject.model.ArtistSpecialization;
//import am.aca.wftartproject.model.User;
//import am.aca.wftartproject.service.ArtistService;
//import am.aca.wftartproject.service.UserService;
//import am.aca.wftartproject.service.impl.ArtistServiceImpl;
//import am.aca.wftartproject.service.impl.UserServiceImpl;
//import am.aca.wftartproject.util.SpringBeanType;
//import org.apache.commons.io.FileUtils;
//import org.apache.commons.io.IOUtils;
//import org.springframework.ui.ModelMap;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.MultipartConfig;
//import javax.servlet.http.*;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Base64;
//
///**
// * @author surik
// */
//@MultipartConfig(maxFileSize = 2177215)
//public class SignUpServlet extends HttpServlet {
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//        request.setAttribute("artistSpecTypes", ArtistSpecialization.values());
//        request.getRequestDispatcher("/WEB-INF/views/signUp.jsp")
//                .forward(request, response);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
////        UserService userService = SpringBean.getBeanFromSpring("userService", UserServiceImpl.class);
////        ArtistService artistService = SpringBean.getBeanFromSpring("artistService", ArtistService.class);
//
//
//        UserService userService = CtxListener.getBeanFromSpring(SpringBeanType.USERSERVICE, UserServiceImpl.class);
//        ArtistService artistService = CtxListener.getBeanFromSpring(SpringBeanType.ARTISTSERVICE, ArtistServiceImpl.class);
//
//        User userFromRequest = null;
//        Artist artistFromRequest = null;
//        InputStream inputStream = null; // input stream of the upload file
//        Part filePart = null;
//
//        // obtains the upload file part in this multipart request
//
//        boolean chosenBuyerUser = request.getParameter("artistSpec") == null;
//
//        if (chosenBuyerUser) {
//            userFromRequest = new User();
//            userFromRequest.setFirstName(request.getParameter("firstName"))
//                    .setLastName(request.getParameter("lastName"))
//                    .setAge(Integer.parseInt(request.getParameter("age")))
//                    .setEmail(request.getParameter("email"))
//                    .setPassword(request.getParameter("password"));
//        } else {
//            artistFromRequest = new Artist();
//            filePart = request.getPart("image");
//            if (filePart != null) {
//                inputStream = filePart.getInputStream();
//                byte[] imageBytes = IOUtils.toByteArray(inputStream);
//                //  FileUtils.writeByteArrayToFile(new File("pathname"), imageBytes);
//                artistFromRequest
//                        .setSpecialization(ArtistSpecialization.valueOf(request.getParameter("artistSpec")))
//                        .setArtistPhoto(imageBytes)
//                        .setFirstName(request.getParameter("firstName"))
//                        .setLastName(request.getParameter("lastName"))
//                        .setAge(Integer.parseInt(request.getParameter("age")))
//                        .setEmail(request.getParameter("email"))
//                        .setPassword(request.getParameter("password"));
//            }
//
//        }
//
//        String page = "";
//        try {
//            if (chosenBuyerUser) {
//                userService.addUser(userFromRequest);
//            } else {
//                artistService.addArtist(artistFromRequest);
//            }
//            page = "/index";
//        } catch (RuntimeException e) {
//            String errorMessage = "The entered info is not correct";
//            request.setAttribute("errorMessage", errorMessage);
//            page = "/signup";
//        }
//        HttpSession session = request.getSession(true);
//        session.setAttribute("user", chosenBuyerUser ? userFromRequest : artistFromRequest);
//        Cookie userEmail = new Cookie("userEmail", chosenBuyerUser ? userFromRequest.getEmail() : artistFromRequest.getEmail());
//        userEmail.setMaxAge(3600);             // 60 minutes
//        response.addCookie(userEmail);
//      /*
//        }
//        else {
//            Artist artistFromRequest = new Artist();
//            artistFromRequest.setSpecialization(ArtistSpecialization.PAINTER)
//                    .setFirstName(request.getParameter("artistName"))
//                    .setLastName(request.getParameter("artistLastName"))
//                    .setAge(Integer.parseInt(request.getParameter("artistAge")))
//                    .setEmail(request.getParameter("artistEmail"))
//                    .setPassword(request.getParameter("artistPassword"));
//
//            try {
//                artistService.addArtist(artistFromRequest);
//            }catch (RuntimeException e){
//                String errorMessage = "The entered info is not correct";
//                request.setAttribute("errorMessage",errorMessage);
//                request.getRequestDispatcher("/signup")
//                        .forward(request,response);
//            }
//            HttpSession session = request.getSession(true);
//            session.setAttribute("user", artistFromRequest);
//            Cookie userEmail = new Cookie("userEmail", artistFromRequest.getEmail());
//            userEmail.setMaxAge(3600);             // 60 minutes
//            response.addCookie(userEmail);
//        }
//
//        */
//
//        try {
//            response.setContentType("html/text");
//            response.sendRedirect(page);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
