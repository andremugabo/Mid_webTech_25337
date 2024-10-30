package controller;

import models.User;
import dao.UserDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDao userDao;

    @Override
    public void init() throws ServletException {
        userDao = new UserDao();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Authenticate user
        User user = userDao.authenticateUser(username, password);

        if (user != null) {
            // Create a new session or get the existing one
            HttpSession session = request.getSession(true);
            session.setAttribute("user", user);
            session.setAttribute("role", user.getRole());
            session.setMaxInactiveInterval(3 * 60); // 3 minutes

            // Create a cookie for the username
            Cookie userCookie = new Cookie("username", username);
            userCookie.setMaxAge(24 * 60 * 60); // 1 day
            userCookie.setHttpOnly(true);  // Prevent JavaScript access to cookie
            userCookie.setSecure(true);    // Ensure cookie is sent over HTTPS
            response.addCookie(userCookie);

            // Log the user login activity
            System.out.println("User logged in: " + username);
            System.out.println("User role: " + user.getRole());

            // Redirect to dashboard
            response.sendRedirect("views/dashboard.jsp");
        } else {
            // Login failed
            System.out.println("Login attempt failed for username: " + username);
            request.setAttribute("errorMessage", "Invalid Username or Password");

            // Forward to the login page with an error message
            request.getRequestDispatcher("login.html").forward(request, response);
        }
    }
}
