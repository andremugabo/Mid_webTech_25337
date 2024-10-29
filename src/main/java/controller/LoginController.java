package controller;

import models.User;
import dao.UserDao;
import util.PasswordUtil;

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
        String password = PasswordUtil.hashPassword(request.getParameter("password"));

        User user = userDao.authenticateUser(username, password);

        if (user != null) {
            // Session
            HttpSession session = request.getSession(true); // Create a new session
            session.setAttribute("user", user);
            session.setMaxInactiveInterval(3 * 60); 

            // persistent cookie for username
            Cookie userCookie = new Cookie("username", username);
            userCookie.setMaxAge(24 * 60 * 60); 
            response.addCookie(userCookie);

            // Redirect based on role
            switch (user.getRole()) {
                case LIBRARIAN:
                    response.sendRedirect("librarianDashboard.jsp");
                    break;
                case STUDENT:
                    response.sendRedirect("studentDashboard.jsp");
                    break;
                case TEACHER:
                    response.sendRedirect("teacherDashboard.jsp");
                    break;
                case MANAGER:
                    response.sendRedirect("managerDashboard.jsp");
                    break;
                default:
                    response.sendRedirect("login.html");
            }
        } else {
            request.setAttribute("errorMessage", "Invalid Username or Password");
            request.getRequestDispatcher("login.html").forward(request, response);
        }
    }

}
