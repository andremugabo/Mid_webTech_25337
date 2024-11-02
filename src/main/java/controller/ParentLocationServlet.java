package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.LocationDao;
import models.Location;

@WebServlet("/getParentLocations")
public class ParentLocationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String locationType = request.getParameter("locationType");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (locationType == null || locationType.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"Missing locationType parameter\"}");
            return;
        }

        
        String parentLocationType = getParentLocationType(locationType);
        
        if (parentLocationType == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"Invalid locationType parameter\"}");
            return;
        }

        try {
            LocationDao locationDaoObject = new LocationDao();
            List<Location> parentLocations = locationDaoObject.getParentLocationsByType(parentLocationType);

            List<Map<String, String>> locationsList = new ArrayList<>();
            for (Location location : parentLocations) {
                Map<String, String> locData = new HashMap<>();
                locData.put("locationId", location.getLocationId().toString());
                locData.put("locationName", location.getLocationName());
                locData.put("locationType", location.getLocationType().toString());
                locationsList.add(locData);
            }
            String jsonResponse = new Gson().toJson(locationsList);
            response.getWriter().write(jsonResponse);

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
            e.printStackTrace();
        }
    }

    
    private String getParentLocationType(String locationType) {
        switch (locationType.toLowerCase()) {
            case "village":
                return "cell";   
            case "cell":
                return "sector"; 
            case "sector":
                return "district"; 
            case "district":
                return "province"; 
            default:
                return null; 
        }
    }
}
