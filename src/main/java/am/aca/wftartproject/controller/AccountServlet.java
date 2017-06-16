package am.aca.wftartproject.controller;

import am.aca.wftartproject.exception.ServiceException;
import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.model.User;
import am.aca.wftartproject.service.ArtistService;
import am.aca.wftartproject.service.impl.ArtistServiceImpl;
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
public class AccountServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-root.xml");
        ArtistService artistService = applicationContext.getBean("artistService", ArtistServiceImpl.class);

        HttpSession session = request.getSession();

        User user;
        Artist artist;
        try {
            if (session.getAttribute("user") != null) {
                user = (User) session.getAttribute("user");
                artist = artistService.findArtist(user.getId());
                if (artist != null) {
                    request.setAttribute("user", artist);
                }
            } else {
                throw new RuntimeException("Incorrect program logic");
            }
        } catch (ServiceException e) {
            String errorMessage = String.format("There is problem with artist info retrieving: %s", e.getMessage());
            throw new RuntimeException(errorMessage, e);
        }

        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/account.jsp");
        dispatcher.forward(request, response);

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {

    }

}
