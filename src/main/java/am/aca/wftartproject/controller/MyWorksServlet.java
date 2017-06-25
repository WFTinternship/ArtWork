package am.aca.wftartproject.controller;

import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.service.ItemService;
import am.aca.wftartproject.service.impl.ItemServiceImpl;
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
 * Created by Armen on 6/16/2017
 */
public class MyWorksServlet extends HttpServlet {
    ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-root.xml");
    ItemService itemService = ctx.getBean("itemService",ItemServiceImpl.class);
    Artist artist ;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if(session.getAttribute("user")!= null  && session.getAttribute("user").getClass() == Artist.class) {
            artist= (Artist) request.getSession().getAttribute("user");
            request.setAttribute("artistItems", itemService.getArtistItems(artist.getId(),8888L,100L));
        }


        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/my-works.jsp");
        dispatcher.forward(request, response);

    }
}
