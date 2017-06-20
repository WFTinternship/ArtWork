package am.aca.wftartproject.controller;

import am.aca.wftartproject.model.Item;
import am.aca.wftartproject.model.User;
import am.aca.wftartproject.service.ArtistService;
import am.aca.wftartproject.service.ItemService;
import am.aca.wftartproject.service.impl.ArtistServiceImpl;
import am.aca.wftartproject.service.impl.ItemServiceImpl;
import am.aca.wftartproject.util.SpringBean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ASUS on 11-Jun-17
 */
public class ItemDetailServlet extends HttpServlet {

    private ItemService itemService = SpringBean.getBean("itemService",ItemServiceImpl.class);
    private ArtistService artistService = SpringBean.getBean("artistService",ArtistServiceImpl.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String[] pathInfo = request.getPathInfo().split("/");
        Long itemId = Long.parseLong(pathInfo[pathInfo.length-1]);

        Item itemById = itemService.findItem(itemId);

        request.setAttribute("itemDetail",itemById);
        request.setAttribute("artistItems",itemService.getArtistItems(itemById.getArtistId(),itemById.getId(),6L));
        request.setAttribute("artistInfo",artistService.findArtist(itemById.getArtistId()));

        request.getRequestDispatcher("/WEB-INF/views/shop-detail.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        User user  = (User) req.getAttribute("user");

    }
}
