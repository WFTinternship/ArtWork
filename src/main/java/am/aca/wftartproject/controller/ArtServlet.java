package am.aca.wftartproject.controller;

import am.aca.wftartproject.model.ArtistSpecialization;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ASUS on 27-May-17
 */
public class ArtServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

          RequestDispatcher  dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/shop.jsp");
            dispatcher.forward(request, response);

    }
}
