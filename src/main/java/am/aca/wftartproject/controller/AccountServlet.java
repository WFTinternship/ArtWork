package am.aca.wftartproject.controller;

import am.aca.wftartproject.exception.service.ServiceException;
import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.model.User;
import am.aca.wftartproject.service.ArtistService;
import am.aca.wftartproject.service.UserService;
import am.aca.wftartproject.service.impl.ArtistServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Created by Armen on 6/9/2017
 */
public class AccountServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-root.xml");
        ArtistService artistService = applicationContext.getBean("artistService", ArtistServiceImpl.class);
        UserService userService = (UserService) applicationContext.getBean("userService");

        HttpSession session = request.getSession();

        User user;
        User finduser;
        Artist artist;
        Artist findArtist;
        Cookie[] cookies = request.getCookies();
        String userEmailFromCookie = null;

        try {
            if (session.getAttribute("user") != null ) {
                if (session.getAttribute("user").getClass().isInstance(User.class)) {
                    user = (User) session.getAttribute("user");
                    finduser = userService.findUser(user.getId());
                    if (user != null) {
                        request.setAttribute("user", finduser);
                    } else {
                        throw new RuntimeException("Incorrect program logic");
                    }
                } else if (session.getAttribute("user").getClass().isInstance(Artist.class)) {
                    artist = (Artist) session.getAttribute("user");
                    findArtist = artistService.findArtist(artist.getId());
                    if (artist != null) {
                        request.setAttribute("user", findArtist);
                    } else {
                        throw new RuntimeException("Incorrect program logic");
                    }
                }
            }
            else {
                if(cookies != null) {
                    for(Cookie ckElement: cookies){
                        if(ckElement.getName().equals("userEmail")){
                            userEmailFromCookie = ckElement.getValue();
                        }
                    }
                    if(userEmailFromCookie!=null){
                        if(artistService.findArtist(userEmailFromCookie) != null) {
                            Artist artistFromCookies = artistService.findArtist(userEmailFromCookie);
                            HttpSession sessionForArtist = request.getSession(true);
                            session.setAttribute("user", artistFromCookies);
                        }
                        else {
                            if(userService.findUser(userEmailFromCookie) != null){
                                User userFromCookies = userService.findUser(userEmailFromCookie);
                                HttpSession sessionForUser = request.getSession(true);
                                session.setAttribute("user", userFromCookies);
                            }
                        }
                    }
                }
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
