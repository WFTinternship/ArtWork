package am.aca.wftartproject.controller;

import am.aca.wftartproject.exception.service.ServiceException;
import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.model.User;
import am.aca.wftartproject.service.ArtistService;
import am.aca.wftartproject.service.UserService;
import am.aca.wftartproject.service.impl.ArtistServiceImpl;
import am.aca.wftartproject.service.impl.UserServiceImpl;
import am.aca.wftartproject.util.SpringBeanType;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Base64;

/**
 * Created by Armen on 6/9/2017
 */
public class AccountServlet extends HttpServlet {


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        ArtistService artistService = SpringBean.getBeanFromSpring("artistService",ArtistServiceImpl.class);

        ArtistService artistService = CtxListener.getBeanFromSpring(SpringBeanType.ARTISTSERVICE, ArtistServiceImpl.class);
        UserService userService = CtxListener.getBeanFromSpring(SpringBeanType.USERSERVICE, UserServiceImpl.class);

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
                    String image = Base64.getEncoder().encodeToString(findArtist.getArtistPhoto());
                    String base64 = "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";
                    if (findArtist != null) {
                        request.getSession().setAttribute("image",base64);
                        request.getSession().setAttribute("img",image);
                        request.getSession().setAttribute("user", findArtist);
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
