package am.aca.wftartproject.controller;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Created by ASUS on 16-Jun-17
 */
public class LogOutServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session=request.getSession(false);
        if(session!=null) {
            Cookie cookie = new Cookie("userEmail","");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            session.setAttribute("user",null);
            session.invalidate();
        }
        response.sendRedirect("/index");
    }
}
