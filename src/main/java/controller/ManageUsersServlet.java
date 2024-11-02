package controller;

import dao.UserDao;
import models.User;
import models.RoleType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

//@WebServlet("/manageUsers")
public class ManageUsersServlet extends HttpServlet {

//    private static final long serialVersionUID = 1L;
//    private UserDao userDao = new UserDao();

//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        RoleType userRole = (RoleType) request.getSession().getAttribute("role");
//
//        if (userRole == null) {
//            response.sendRedirect(request.getContextPath() + "/login.html");
//            return;
//        }
//
//        List<User> userList = userDao.listAllUsers();
//        request.setAttribute("userList", userList);
//        request.setAttribute("userRole", userRole);  
//        request.getRequestDispatcher("manageUsers.jsp").forward(request, response);
//    }
}
