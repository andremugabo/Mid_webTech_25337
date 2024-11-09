package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDao;
import models.Location;

import javax.servlet.RequestDispatcher;
import java.io.IOException;

@WebServlet("/findProvince")
public class LocationServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private UserDao userDao = new UserDao();  

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String phoneNumber = request.getParameter("phoneNumber");
        
        Location province = userDao.getProvinceByPhoneNumber(phoneNumber);
        
        if (province != null) {
            request.setAttribute("provinceName", province.getLocationName());
        } else {
            request.setAttribute("provinceName", "Province not found. Please update village information first.");
        }
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("dashboard.jsp");
        dispatcher.forward(request, response);
    }
}
