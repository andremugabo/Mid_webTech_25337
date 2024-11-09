package controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.LocationDao;
import models.Location;

@WebServlet("/getChildLocations")
public class ChildLocationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String parentIdParam = request.getParameter("parentId");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (parentIdParam == null || parentIdParam.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"Missing parentId parameter\"}");
            return;
        }

        try {
            UUID parentId = UUID.fromString(parentIdParam);
            LocationDao locationDao = new LocationDao();
            List<Location> childLocations = locationDao.getChildLocations(parentId);

            List<Map<String, String>> locationsList = new ArrayList<>();
            for (Location location : childLocations) {
                Map<String, String> locData = new HashMap<>();
                locData.put("locationId", location.getLocationId().toString());
                locData.put("locationName", location.getLocationName());
                locData.put("locationType", location.getLocationType().toString());
                locationsList.add(locData);
            }

            String jsonResponse = new Gson().toJson(locationsList);
            response.getWriter().write(jsonResponse);

        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"Invalid parentId parameter\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
            e.printStackTrace();
        }
    }
}

