package am.aca.wftartproject.controller;

import am.aca.wftartproject.model.ItemType;
import am.aca.wftartproject.service.ItemService;
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
}
