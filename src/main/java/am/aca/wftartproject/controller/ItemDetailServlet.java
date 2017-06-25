package am.aca.wftartproject.controller;

import am.aca.wftartproject.exception.dao.NotEnoughMoneyException;
import am.aca.wftartproject.model.Item;
import am.aca.wftartproject.model.User;
import am.aca.wftartproject.service.ArtistService;
import am.aca.wftartproject.service.ItemService;
import am.aca.wftartproject.service.impl.ArtistServiceImpl;
import am.aca.wftartproject.service.impl.ItemServiceImpl;
import am.aca.wftartproject.util.SpringBeanType;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by ASUS on 11-Jun-17
 */
public class ItemDetailServlet extends HttpServlet {

    private ItemService itemService = CtxListener.getBeanFromSpring(SpringBeanType.ITEMSERVICE, ItemServiceImpl.class);
    private ArtistService artistService = CtxListener.getBeanFromSpring(SpringBeanType.ARTISTSERVICE, ArtistServiceImpl.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String[] pathInfo = request.getPathInfo().split("/");
        Long itemId = Long.parseLong(pathInfo[pathInfo.length - 1]);

        HttpSession session = request.getSession();

        Item itemById = itemService.findItem(itemId);

        session.setAttribute("itemDetail", itemById);
        request.setAttribute("artistItems", itemService.getArtistItems(itemById.getArtistId(), itemById.getId(), 10L));
        request.setAttribute("artistInfo", artistService.findArtist(itemById.getArtistId()));

        request.getRequestDispatcher("/WEB-INF/views/item-detail.jsp")
                .forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            request.getRequestDispatcher("/WEB-INF/views/logIn.jsp")
                    .forward(request, response);
        } else {
            try {
                Item item = (Item) session.getAttribute("itemDetail");
                itemService.itemBuying(item, user.getId());
                request.getRequestDispatcher("/WEB-INF/views/thank-you.jsp")
                        .forward(request, response);
            } catch (NotEnoughMoneyException ex) {
                session.setAttribute("msgNotEnoughMoney",
                        "You don't have enough money. Please top-up your account and try again.");
                response.sendRedirect("/shop");
            } catch (RuntimeException ex) {
                request.getRequestDispatcher("/WEB-INF/views/index.jsp")
                        .forward(request, response);
            }
        }
    }
}
