package am.aca.wftartproject.controller;

import am.aca.wftartproject.exception.ServiceException;
import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.model.ArtistSpecialization;
import am.aca.wftartproject.service.ArtistService;
import am.aca.wftartproject.service.ItemService;
import am.aca.wftartproject.service.impl.ItemServiceImpl;
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
public class EditProfileServlet extends HttpServlet {
    ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-root.xml");
    ArtistService artistService = (ArtistService)ctx.getBean("artistService");
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("artistSpecTypes", ArtistSpecialization.values());
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/edit-profile.jsp");
        dispatcher.forward(request, response);

    }

    public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
        Artist artist = (Artist)request.getSession().getAttribute("artist");
        response.setContentType("text/html");
        if(request.getParameter("firstname" ) != null){
            artist.setFirstName(request.getParameter("firstname"));
        }
        if(request.getParameter("lastname" ) != null){
            artist.setLastName(request.getParameter("lasstname"));
        }
        if(request.getParameter("age" ) != null){
            artist.setAge(Integer.parseInt(request.getParameter("age")));
        }
        if(request.getParameter("specialization" ) != null){
            artist.setSpecialization(ArtistSpecialization.PAINTER);
        }


        try {
            artistService.updateArtist(artist.getId(),artist);
        }catch (ServiceException e){
            String errorMessage = "The entered info is not correct";
            request.setAttribute("errorMessage",errorMessage);
//            request.getRequestDispatcher("/signup")
//                    .forward(request,response);
        }

        try {
            response.setContentType("html/text");
            response.sendRedirect("/edit-profile");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
