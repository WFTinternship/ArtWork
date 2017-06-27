package am.aca.wftartproject.controller;

import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.model.User;
import am.aca.wftartproject.service.ArtistService;
import am.aca.wftartproject.service.UserService;
import am.aca.wftartproject.service.impl.ArtistServiceImpl;
import am.aca.wftartproject.service.impl.UserServiceImpl;
import am.aca.wftartproject.util.SpringBean;
import am.aca.wftartproject.util.SpringBeanType;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Created by Armen on 6/9/2017
 */
public class HomeServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        ArtistSpecializationService artistSpecialization = SpringBean.getBeanFromSpring("artistSpecializationService", ArtistSpecializationServiceImpl.class);
//        UserService userService = SpringBean.getBeanFromSpring("userService", UserServiceImpl.class);
        UserService userService = CtxListener.getBeanFromSpring(SpringBeanType.USERSERVICE,UserServiceImpl.class);
        ArtistService artistService = SpringBean.getBeanFromSpring("artistService", ArtistServiceImpl.class);

//        if (artistSpecialization.getArtistSpecialization(1) == null) {
//            artistSpecialization.addArtistSpecialization();
//        }

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
            }
            else {
                if(userService.findUser(userEmailFromCookie) != null){
                    User user = userService.findUser(userEmailFromCookie);
                    HttpSession session = request.getSession(true);
                    session.setAttribute("user", user);
                }
            }
        }

        request.getRequestDispatcher("/WEB-INF/views/index.jsp")
                .forward(request, response);
    }
}
