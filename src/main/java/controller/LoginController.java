package controller;

import models.User;
import dao.UserDao;

import javax.servlet.RequestDispatcher;
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
    RequestDispatcher dispatcher;
   
    @Override
    public void init() throws ServletException {
        userDao = new UserDao();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        
        
        User user = userDao.authenticateUser(username, password);

        if (user != null) {
            
            HttpSession session = request.getSession(true);
            session.setAttribute("user", user);
            session.setAttribute("role", user.getRole());
            session.setAttribute("userId", user.getPersonId());
            session.setMaxInactiveInterval(3 * 60); 

            
            Cookie userCookie = new Cookie("username", username);
            userCookie.setMaxAge(24 * 60 * 60); 
            userCookie.setHttpOnly(true);  
            userCookie.setSecure(true);    
            response.addCookie(userCookie);

           
            System.out.println("User logged in: " + username);
            System.out.println("User ID: " + user.getPersonId());
            System.out.println("User role: " + user.getRole());

            
            request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
        } else {
            
            System.out.println("Login attempt failed for username: " + username);
            request.setAttribute("errorMessage", "Invalid Username or Password");

            
            request.getRequestDispatcher("login.html").forward(request, response);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            
            response.sendRedirect("dashboard.jsp");
        } else {
            
            request.getRequestDispatcher("login.html").forward(request, response);
        }
    }
}
