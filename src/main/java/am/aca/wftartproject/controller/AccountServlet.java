package am.aca.wftartproject.controller;

import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.model.User;
import am.aca.wftartproject.service.ArtistService;
import am.aca.wftartproject.service.UserService;
import am.aca.wftartproject.service.impl.ArtistServiceImpl;
import am.aca.wftartproject.service.impl.UserServiceImpl;
import am.aca.wftartproject.util.SpringBeanType;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Created by Armen on 6/9/2017
 */
public class AccountServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UserService userService = CtxListener.getBeanFromSpring(SpringBeanType.USERSERVICE,UserServiceImpl.class);
        ArtistService artistService = CtxListener.getBeanFromSpring(SpringBeanType.ARTISTSERVICE, ArtistServiceImpl.class);

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
            if(artistService.findArtist(userEmailFromCookie) != null) {
                Artist artist = artistService.findArtist(userEmailFromCookie);
                HttpSession session = request.getSession(true);
                session.setAttribute("user", artist);
                System.out.println("artist = " + artist);
            }
            else {
                if(userService.findUser(userEmailFromCookie) != null){
                    User user = userService.findUser(userEmailFromCookie);
                    HttpSession session = request.getSession(true);
                    session.setAttribute("user", user);
                    System.out.println("user = " + user);
                }
            }
        }

        request.getRequestDispatcher("/WEB-INF/views/account.jsp")
                .forward(request, response);

        request.getSession().removeAttribute("errorMessage");













//        ArtistService artistService = CtxListener.getBeanFromSpring(SpringBeanType.ARTISTSERVICE, ArtistServiceImpl.class);
//        UserService userService = CtxListener.getBeanFromSpring(SpringBeanType.USERSERVICE, UserServiceImpl.class);
//
//        HttpSession session = request.getSession();
//
//        User user;
//        User findUser;
//        Artist artist;
//        Artist findArtist;
//        Cookie[] cookies = request.getCookies();
//        String userEmailFromCookie = null;
//        Object obj = session.getAttribute("user").getClass().isInstance(User.class);
//
//        try {
//            if (session.getAttribute("user") != null) {
//                if (session.getAttribute("user").getClass() == User.class) {
//                    user = (User) session.getAttribute("user");
//                    findUser = userService.findUser(user.getId());
//                    if (user != null) {
//                        request.setAttribute("user", findUser);
//                    } else {
//                        throw new RuntimeException("Incorrect program logic");
//                    }
//                } else if (session.getAttribute("user").getClass() == Artist.class) {
//                    artist = (Artist) session.getAttribute("user");
//                    findArtist = artistService.findArtist(artist.getId());
//                    String image = Base64.getEncoder().encodeToString(findArtist.getArtistPhoto());
//                    if (findArtist != null) {
//                        request.getSession().setAttribute("image", image);
//                        request.getSession().setAttribute("user", findArtist);
//                    } else {
//                        throw new RuntimeException("Incorrect program logic");
//                    }
//                }
//            } else {
//                if (cookies != null) {
//                    for (Cookie ckElement : cookies) {
//                        if (ckElement.getName().equals("userEmail")) {
//                            userEmailFromCookie = ckElement.getValue();
//                        }
//                    }
//                    if (userEmailFromCookie != null) {
//                        if (artistService.findArtist(userEmailFromCookie) != null) {
//                            Artist artistFromCookies = artistService.findArtist(userEmailFromCookie);
//                            HttpSession sessionForArtist = request.getSession(true);
//                            session.setAttribute("user", artistFromCookies);
//                        } else {
//                            if (userService.findUser(userEmailFromCookie) != null) {
//                                User userFromCookies = userService.findUser(userEmailFromCookie);
//                                HttpSession sessionForUser = request.getSession(true);
//                                session.setAttribute("user", userFromCookies);
//                            }
//                        }
//                    }
//                }
//            }
//        } catch (ServiceException e) {
//            String errorMessage = String.format("There is problem with artist info retrieving: %s", e.getMessage());
//            throw new RuntimeException(errorMessage, e);
//        }
//
//        request.getRequestDispatcher("/WEB-INF/views/account.jsp")
//                .forward(request, response);

    }



    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {

    }

}
