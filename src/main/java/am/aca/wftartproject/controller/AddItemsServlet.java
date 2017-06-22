package am.aca.wftartproject.controller;

import am.aca.wftartproject.exception.service.ServiceException;
import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.model.Item;
import am.aca.wftartproject.model.ItemType;
import am.aca.wftartproject.service.ArtistService;
import am.aca.wftartproject.service.ItemService;
import am.aca.wftartproject.service.impl.ArtistServiceImpl;
import am.aca.wftartproject.service.impl.ItemServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Armen on 6/16/2017.
 */
public class AddItemsServlet extends HttpServlet {

    ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-root.xml");
    ItemService itemService = ctx.getBean("itemService",ItemServiceImpl.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("itemTypes", ItemType.values());
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/additem.jsp");
        dispatcher.forward(request, response);

    }

    public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
        Item item  = new Item();
        Artist artist = (Artist)request.getSession().getAttribute("artist");
        response.setContentType("text/html");
        item.setTitle(request.getParameter("title"))
                .setDescription(request.getParameter("description"))
                .setItemType(ItemType.getItemType("SCULPTURE"))
                .setPrice(Double.parseDouble(request.getParameter("price")))
                .setArtistId(artist.getId()).setStatus(true)
                .setPhotoURL((request.getSession().getAttribute("photourl").toString()));
        try {
            itemService.addItem(artist.getId(),item);
        }catch (ServiceException e){
            String errorMessage = "The entered info is not correct";
            request.setAttribute("errorMessage",errorMessage);
//            request.getRequestDispatcher("/signup")
//                    .forward(request,response);
        }

        try {
            response.setContentType("html/text");
            response.sendRedirect("/additem");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
