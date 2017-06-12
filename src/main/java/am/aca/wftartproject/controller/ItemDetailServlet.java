package am.aca.wftartproject.controller;

import am.aca.wftartproject.model.Item;
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

/**
 * Created by ASUS on 11-Jun-17
 */
public class ItemDetailServlet extends HttpServlet {

    private ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-root.xml");
    private ItemService itemService = ctx.getBean("itemService",ItemServiceImpl.class);
    private ArtistService artistService = ctx.getBean("artistService",ArtistServiceImpl.class);
    
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String[] pathInfo = req.getPathInfo().split("/");
        Long itemId = Long.parseLong(pathInfo[pathInfo.length-1]);

        Item itemById = itemService.findItem(itemId);

        req.setAttribute("itemDetail",itemById);
        req.setAttribute("artistItems",itemService.getArtistItems(itemById.getArtistId(),itemById.getId(),6L));
        req.setAttribute("artistInfo",artistService.findArtist(itemById.getArtistId()));

        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/shop-detail.jsp");
        dispatcher.forward(req,resp);
    }
}
