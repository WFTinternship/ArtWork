package am.aca.wftartproject.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
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

        String nextJSP = "/WEB-INF/views/hello.jsp";
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.forward(request,response);

//        RequestDispatcher requestDispatcher = req.getRequestDispatcher("hello.jsp");
//        PrintWriter out = res.getWriter();
//        out.println("<html>");
//        out.println("<body>");
//        out.println("<h1>Hello Servlet Get</h1>");
//        out.println("</body>");
//        out.println("</html>");
    }
}
