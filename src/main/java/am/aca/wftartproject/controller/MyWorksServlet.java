package am.aca.wftartproject.controller;

import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.service.ItemService;
import am.aca.wftartproject.service.impl.ItemServiceImpl;
import am.aca.wftartproject.util.SpringBeanType;

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

    private ItemService itemService = CtxListener.getBeanFromSpring(SpringBeanType.ITEMSERVICE,ItemServiceImpl.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if(session.getAttribute("user")!= null  && session.getAttribute("user").getClass() == Artist.class) {
            Artist artist = (Artist) session.getAttribute("user");
            request.setAttribute("artistItems", itemService.getAvailableItemsForGivenArtist(artist.getId()));
        }

        request.getRequestDispatcher("/WEB-INF/views/my-works.jsp")
                .forward(request,response);

//        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/my-works.jsp");
//        dispatcher.forward(request, response);

    }
}
