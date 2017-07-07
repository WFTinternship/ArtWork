//package am.aca.wftartproject.controller;
//
//import am.aca.wftartproject.entity.User;
//import am.aca.wftartproject.service.UserService;
//import am.aca.wftartproject.service.impl.UserServiceImpl;
//import am.aca.wftartproject.util.SpringBeanType;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.*;
//import java.io.IOException;
//
///**
// * Created by ASUS on 10-Jun-17
// */
//public class LoginServlet extends HttpServlet {
//
////    private UserService userService = SpringBean.getBeanFromSpring("userService",UserServiceImpl.class);
//    private UserService userService = CtxListener.getBeanFromSpring(SpringBeanType.USERSERVICE,UserServiceImpl.class);
//
//    @Override
//    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        request.getRequestDispatcher("/WEB-INF/views/logIn.jsp")
//                .forward(request, response);
//    }
//
//    @Override
//    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//        String userEmailStr = request.getParameter("email");
//        String userPasswordStr = request.getParameter("password");
//
//        try {
//            User userFromDB = userService.login(userEmailStr, userPasswordStr);
//            if(userFromDB != null) {
//                HttpSession session = request.getSession(true);
//                session.setAttribute("user", userFromDB);
//
//                Cookie userEmail = new Cookie("userEmail", userFromDB.getEmail());
//                userEmail.setMaxAge(3600);    // 60 minutes
//                response.addCookie(userEmail);
//                response.sendRedirect("/index");
//            }else{
//                throw new RuntimeException();
//            }
//        } catch (RuntimeException e) {
//            String userNotExists = "The user with the entered username and password does not exists.";
//            request.setAttribute("errorMessage", userNotExists);
//            request.getRequestDispatcher("/WEB-INF/views/logIn.jsp")
//                    .forward(request, response);
//        }
//
//    }
//}
