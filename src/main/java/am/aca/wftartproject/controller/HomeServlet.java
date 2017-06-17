package am.aca.wftartproject.controller;

import am.aca.wftartproject.model.User;
import am.aca.wftartproject.service.ArtistSpecializationService;
import am.aca.wftartproject.service.UserService;
import am.aca.wftartproject.service.impl.ArtistSpecializationServiceImpl;
import am.aca.wftartproject.service.impl.UserServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
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


        Cookie[] cookies = request.getCookies();
        String userEmailFromCookie = null;
        if(cookies != null) {
            for(Cookie ckElement: cookies){
                if(ckElement.getName().equals("userEmail")){
                    userEmailFromCookie = ckElement.getValue();
                }
            }
        }

        if(userEmailFromCookie!=null){
            User user = userService.findUser(userEmailFromCookie);
            HttpSession session = request.getSession(true);
            session.setAttribute("user",user);
        }

        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/index.jsp");
        dispatcher.forward(request, response);
    }
}
