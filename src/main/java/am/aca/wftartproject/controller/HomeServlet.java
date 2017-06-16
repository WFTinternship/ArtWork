package am.aca.wftartproject.controller;

import am.aca.wftartproject.service.ArtistSpecializationService;
import am.aca.wftartproject.service.UserService;
import am.aca.wftartproject.service.impl.ArtistSpecializationServiceImpl;
import am.aca.wftartproject.service.impl.UserServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Armen on 6/9/2017
 */
public class HomeServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ApplicationContext  applicationContext = new ClassPathXmlApplicationContext("spring-root.xml");
        ArtistSpecializationService artistSpecialization = applicationContext.getBean("artistSpecializationService", ArtistSpecializationServiceImpl.class);
        UserService userService = applicationContext.getBean("userService", UserServiceImpl.class);

        if (artistSpecialization.getArtistSpecialization(1) == null) {
            artistSpecialization.addArtistSpecialization();
        }

//        User user = null;
//        Cookie[] cookies = request.getCookies();
//        if(cookies != null) {
//            String userEmail = cookies[0].getValue();
//            String userPassword = cookies[1].getValue();
//            user = userService.findUser(userPassword);
//        }
//
//        if(user != null){
//            HttpSession session = request.getSession();
//            session.setAttribute("user",user);
//        }

        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/index.jsp");
        dispatcher.forward(request, response);
    }
}
