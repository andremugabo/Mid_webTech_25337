package controller;

import dao.LocationDao;
import models.Location;
import models.LocationType;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet("/location")
public class LocationController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private LocationDao locationDao;

    @Override
    public void init() throws ServletException {
        locationDao = new LocationDao();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String locationCode = request.getParameter("locationCode");
        String locationName = request.getParameter("locationName");
        String locationTypeParam = request.getParameter("locationType");
        String parentIdParam = request.getParameter("parentId");

        if (locationCode == null || locationName == null || locationTypeParam == null) {
            response.getWriter().write("All fields are required");
            return;
        }
        
        if (locationDao.existsByLocationCode(locationCode)) {
            response.getWriter().write("Location code already exists");
            return;
        }
        

        LocationType locationType;
        try {
            locationType = LocationType.valueOf(locationTypeParam.toUpperCase());
        } catch (IllegalArgumentException e) {
            response.getWriter().write("Invalid location type provided");
            return;
        }

        Location location = new Location();
        location.setLocationCode(locationCode);
        location.setLocationName(locationName);
        location.setLocationType(locationType);

        // Set parent location if provided and valid
        if (parentIdParam != null && !parentIdParam.isEmpty()) {
            try {
                Location parentLocation = locationDao.selectLocation(UUID.fromString(parentIdParam));
                if (parentLocation != null) {
                    location.setParentLocation(parentLocation);
                } else {
                    response.getWriter().write("Parent location not found for ID: " + parentIdParam);
                    return;
                }
            } catch (IllegalArgumentException e) {
                response.getWriter().write("Invalid parent ID format");
                return;
            }
        }

        locationDao.createLocation(location);
        response.getWriter().write("<h1 class=\"success\">Location created successfully</h1>");
        RequestDispatcher dispatcher = request.getRequestDispatcher("locationManagement.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String locationIdParam = request.getParameter("locationId");
        String locationCode = request.getParameter("locationCode");
        String locationName = request.getParameter("locationName");
        String locationTypeParam = request.getParameter("locationType");
        String parentIdParam = request.getParameter("parentId");

        if (locationIdParam == null || locationIdParam.isEmpty()) {
            response.getWriter().write("Location ID is required");
            return;
        }

        Location location;
        try {
            location = locationDao.selectLocation(UUID.fromString(locationIdParam));
            if (location == null) {
                response.getWriter().write("Location not found");
                return;
            }
        } catch (IllegalArgumentException e) {
            response.getWriter().write("Invalid location ID format");
            return;
        }

        location.setLocationCode(locationCode);
        location.setLocationName(locationName);

        if (locationTypeParam != null) {
            try {
                LocationType locationType = LocationType.valueOf(locationTypeParam.toUpperCase());
                location.setLocationType(locationType);
            } catch (IllegalArgumentException e) {
                response.getWriter().write("Invalid location type provided");
                return;
            }
        }

        // Update parent location if provided; clear if not
        if (parentIdParam != null && !parentIdParam.isEmpty()) {
            try {
                Location parentLocation = locationDao.selectLocation(UUID.fromString(parentIdParam));
                if (parentLocation != null) {
                    location.setParentLocation(parentLocation);
                } else {
                    response.getWriter().write("Parent location not found for ID: " + parentIdParam);
                    return;
                }
            } catch (IllegalArgumentException e) {
                response.getWriter().write("Invalid parent ID format");
                return;
            }
        } else {
            location.setParentLocation(null); // Remove parent if no parentId is provided
        }

        locationDao.updateLocation(location);
        response.getWriter().write("<h1 class=\"success\">Location updated successfully</h1>");
        RequestDispatcher dispatcher = request.getRequestDispatcher("locationManagement.jsp");
        dispatcher.forward(request, response);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Location> locationList = locationDao.listAllLocations();
        System.out.println("Retrieved Locations: " + locationList.size());

        request.setAttribute("listLocation", locationList);
        request.getRequestDispatcher("locationManagement.jsp").forward(request, response);
    }
}
