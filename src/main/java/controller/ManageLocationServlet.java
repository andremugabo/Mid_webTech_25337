package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.LocationDao;
import models.Location;

@WebServlet("/manageLocation")
public class ManageLocationServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
//	private LocationDao locationDao = new LocationDao();
//	@Override
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
//		List<Location> locationList = locationDao.listAllLocations();
//		System.out.println("Retrieved Locations: " + locationList.size());
//
//		request.setAttribute("listLocation", locationList);
//		request.getRequestDispatcher("locationManagement.jsp").forward(request, response);
//	}

}
