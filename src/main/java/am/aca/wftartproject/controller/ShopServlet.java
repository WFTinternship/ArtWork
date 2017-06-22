package am.aca.wftartproject.controller;

import am.aca.wftartproject.model.ArtistSpecialization;
import am.aca.wftartproject.model.Item;
import am.aca.wftartproject.model.ItemType;
import am.aca.wftartproject.service.ItemService;
import am.aca.wftartproject.service.impl.ItemServiceImpl;
import am.aca.wftartproject.util.SpringBeanType;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by ASUS on 27-May-17
 */
public class ShopServlet extends HttpServlet {

//    private ItemService itemService = SpringBean.getBeanFromSpring("itemService",ItemServiceImpl.class);
    private ItemService itemService = CtxListener.getBeanFromSpring(SpringBeanType.ITEMSERVICE,ItemServiceImpl.class);

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/shop.jsp");

        request.setAttribute("artistSpecTypes", ArtistSpecialization.values());
        request.setAttribute("itemTypes", ItemType.values());
        request.setAttribute("recentlyAddedItems",itemService.getRecentlyAddedItems(20));

        dispatcher.forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws  ServletException, IOException{

        ArtistSpecialization artistSpecialization = ArtistSpecialization.valueOf(request.getParameter("artSpec"));
        System.out.println(artistSpecialization.toString());
        ItemType itemType = ItemType.valueOf(request.getParameter("itemType"));
        System.out.println(itemType.toString());
        String sortType = request.getParameter("sortType");
        System.out.println(sortType);

        List<Item> itemList = itemService.getFilteredAndSortedItems(itemType.toString(),null,null);
        request.setAttribute("recentlyAddedItems",itemList);

        request.getRequestDispatcher("/WEB-INF/views/shop.jsp").forward(request,response);


    }
}
