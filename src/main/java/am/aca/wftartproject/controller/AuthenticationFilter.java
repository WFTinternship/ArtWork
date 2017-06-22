package am.aca.wftartproject.controller;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by ASUS on 10-Jun-17
 */


public class AuthenticationFilter implements Filter {

    private ServletContext context;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.context = filterConfig.getServletContext();
        this.context.log("AuthenticationFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();

        this.context.log("Requested Resource::" + uri);

        HttpSession session = req.getSession(true);

//        if (session == null && !uri.endsWith("index")) {
//            this.context.log("Unauthorized access request");
//            res.sendRedirect("/index");
//        } else

        if (session.getAttribute("user") == null && uri.endsWith("account")) {
            this.context.log("Unauthorized access request");
            res.sendRedirect("/login");
        } else {
            // pass the request along the filter chain
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
