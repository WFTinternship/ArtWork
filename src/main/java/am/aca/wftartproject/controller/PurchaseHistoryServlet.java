package am.aca.wftartproject.controller;

import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.model.Item;
import am.aca.wftartproject.model.User;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Armen on 6/9/2017
 */
public class PurchaseHistoryServlet extends HttpServlet {
    private ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-root.xml");
    private PurchaseHistoryService purchaseHistoryService = (PurchaseHistoryService) ctx.getBean("purchaseHistoryService");

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user;
        Artist artist;
        if (session.getAttribute("user") != null ){
            if (session.getAttribute("user").getClass() == User.class){
                user = (User)session.getAttribute("user");
                request.setAttribute("purchaseHistoryService",purchaseHistoryService.getPurchase(user.getId()));
            }
            if (session.getAttribute("user").getClass() == Artist.class){
                artist = (Artist) session.getAttribute("user");
                request.setAttribute("purchaseHistoryService",purchaseHistoryService.getPurchase(artist.getId()));
            }

        }
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/purchase-history.jsp");
        dispatcher.forward(request, response);

    }
}
