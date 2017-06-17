package am.aca.wftartproject.controller;

import am.aca.wftartproject.model.Item;
import am.aca.wftartproject.service.ArtistService;
import am.aca.wftartproject.service.ItemService;
import am.aca.wftartproject.service.PurchaseHistoryService;
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
public class PurchaseHistoryServlet extends HttpServlet {
    private ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-root.xml");
    private ItemService itemService = (ItemService) ctx.getBean("itemService");
    private ArtistService artistService = (ArtistService) ctx.getBean("artistService");
    private PurchaseHistoryService purchaseHistoryService = (PurchaseHistoryService) ctx.getBean("purchaseHistoryService");

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("itemService",itemService.getRecentlyAddedItems(1000));
        request.setAttribute("purchaseHistoryService",purchaseHistoryService.getPurchase(7L));
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/purchase-history.jsp");
        dispatcher.forward(request, response);

    }
}
