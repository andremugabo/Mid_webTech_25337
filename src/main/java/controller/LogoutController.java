package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Logout requested.");

        
        HttpSession session = request.getSession(false);
        if (session != null) {
            System.out.println("Invalidating session.");
            session.invalidate();
        } else {
            System.out.println("No session found to invalidate.");
        }

        
        Cookie usernameCookie = new Cookie("username", null);
        usernameCookie.setMaxAge(0);
        usernameCookie.setPath(request.getContextPath());
        response.addCookie(usernameCookie);
        System.out.println("Deleted username cookie.");

        
        Cookie jsessionidCookie = new Cookie("JSESSIONID", null);
        jsessionidCookie.setMaxAge(0);
        jsessionidCookie.setPath(request.getContextPath());
        response.addCookie(jsessionidCookie);
        System.out.println("Deleted JSESSIONID cookie.");

        
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        
        response.sendRedirect(request.getContextPath() + "/login.html");
        System.out.println("Redirected to login page.");
    }
}
