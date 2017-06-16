package am.aca.wftartproject.controller;

import am.aca.wftartproject.exception.ServiceException;
import am.aca.wftartproject.model.User;
import am.aca.wftartproject.service.UserService;
import am.aca.wftartproject.service.impl.UserServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Created by ASUS on 10-Jun-17
 */
public class LoginServlet extends HttpServlet {

    private ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-root.xml");
    private UserService userService = applicationContext.getBean("userService", UserServiceImpl.class);

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/views/logInSignUp.jsp")
                .forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String userEmailStr = request.getParameter("email");
        String userPasswordStr = request.getParameter("password");

        User userFromDB = null;
        try {
            userFromDB = userService.findUser(userEmailStr);
        } catch (ServiceException e) {
            String errorMsg = "Email format was incorrect";
            request.setAttribute("errorMessage", errorMsg);

            request.getRequestDispatcher("/login")
                    .forward(request, response);
        }


        if (userFromDB != null) {
            HttpSession session = request.getSession(true);
            session.setAttribute("user", userFromDB);

            //setting session to expiry in 30 mins
            session.setMaxInactiveInterval(1200);
            Cookie userEmail = new Cookie("userEmail", userFromDB.getEmail());
            Cookie userPassword = new Cookie("userPassword", userFromDB.getPassword());
            userEmail.setMaxAge(1800);
            userPassword.setMaxAge(1800);
            response.addCookie(userEmail);
            response.addCookie(userPassword);
            response.sendRedirect("/index");
        } else {
            String userNotExists = "The user with the entered username and password does not exists.";
            request.setAttribute("errorMessage", userNotExists);
            request.getRequestDispatcher("/login")
                    .forward(request, response);
        }


    }


}
