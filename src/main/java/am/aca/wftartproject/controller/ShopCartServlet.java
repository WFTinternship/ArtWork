package am.aca.wftartproject.controller;

import am.aca.wftartproject.service.ShoppingCardService;
import am.aca.wftartproject.util.SpringBean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author surik
 */
public class ShopCartServlet extends HttpServlet {

    private ShoppingCardService shoppingCardService = SpringBean.getBeanFromSpring("shoppingCardService", ShoppingCardService.class);

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        request.setAttribute("shoppingCard", shoppingCardService.getShoppingCard());
        request.getRequestDispatcher("/WEB-INF/views/shop-cart.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {

    }

}
