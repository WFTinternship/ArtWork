package am.aca.wftartproject.controller;

import am.aca.wftartproject.exception.ServiceException;
import am.aca.wftartproject.model.User;
import am.aca.wftartproject.service.UserService;
import am.aca.wftartproject.service.impl.UserServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author surik
 */
public class SignUpServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/views/signUp.html")
                .forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User userFromRequest = new User();
        userFromRequest.setFirstName(request.getParameter("firstname"))
                .setLastName(request.getParameter("lastname"))
                .setAge(Integer.parseInt(request.getParameter("age")))
                .setEmail(request.getParameter("email"))
                .setPassword(request.getParameter("password"));

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-root.xml");
        UserService userService = applicationContext.getBean("userService", UserServiceImpl.class);

        try {
            userService.addUser(userFromRequest);
        }catch (ServiceException e){
            String errorMessage = "The entered info is not correct";
            request.setAttribute("errorMessage",errorMessage);
            request.getRequestDispatcher("/signup")
                    .forward(request,response);
        }


        HttpSession session = request.getSession(true);
        session.setAttribute("user", userFromRequest);


        try {
            response.setContentType("html/text");
            response.sendRedirect("/index");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
