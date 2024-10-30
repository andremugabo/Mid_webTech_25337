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

    // Handle location creation
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String locationCode = request.getParameter("locationCode");
        String locationName = request.getParameter("locationName");
        String locationTypeParam = request.getParameter("locationType");
        String parentIdParam = request.getParameter("parentId"); // Optional parent ID

        LocationType locationType = null;
        if (locationTypeParam != null) {
            try {
                locationType = LocationType.valueOf(locationTypeParam.toUpperCase());
            } catch (IllegalArgumentException e) {
                response.getWriter().write("Invalid location type provided");
                return;
            }
        }

        if (locationCode == null || locationName == null || locationType == null) {
            response.getWriter().write("All fields are required");
            return;
        }

        Location location = new Location();
        location.setLocationCode(locationCode);
        location.setLocationName(locationName);
        location.setLocationType(locationType);

        // Set parent location if provided
        if (parentIdParam != null && !parentIdParam.isEmpty()) {
            UUID parentId = UUID.fromString(parentIdParam);
            Location parentLocation = locationDao.selectLocation(parentId);
            location.setParentLocation(parentLocation);
        }

        locationDao.createLocation(location);
        response.getWriter().write("<h1 class=\"success\">Location created successfully</h1>");
        RequestDispatcher dispatcher = request.getRequestDispatcher("locationForm.html"); // Redirect to the form page
        dispatcher.include(request, response);
    }

    // Handle location updates
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UUID locationId = UUID.fromString(request.getParameter("locationId"));
        Location location = locationDao.selectLocation(locationId);

        if (location != null) {
            String newLocationCode = request.getParameter("locationCode");
            String newLocationName = request.getParameter("locationName");
            String newLocationTypeParam = request.getParameter("locationType");

            LocationType newLocationType = null;
            if (newLocationTypeParam != null) {
                try {
                    newLocationType = LocationType.valueOf(newLocationTypeParam.toUpperCase());
                } catch (IllegalArgumentException e) {
                    response.getWriter().write("Invalid location type provided");
                    return;
                }
            }

            location.setLocationCode(newLocationCode);
            location.setLocationName(newLocationName);
            location.setLocationType(newLocationType);

            locationDao.updateLocation(location);
            response.getWriter().write("Location updated successfully");
        } else {
            response.getWriter().write("Location not found");
        }
    }

    // Handle deletion of a location
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UUID locationId = UUID.fromString(request.getParameter("locationId"));
        locationDao.softDeleteLocation(locationId);
        response.getWriter().write("Location deleted successfully");
    }

    // Handle listing of all locations
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Location> locationList = locationDao.listAllLocations();
        request.setAttribute("locationList", locationList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("manageLocations.jsp"); // Redirect to a JSP page to display locations
        dispatcher.forward(request, response);
    }

    // Handle fetching child locations for a parent
    protected void doGetChild(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UUID parentId = UUID.fromString(request.getParameter("parentId"));
        List<Location> childLocations = locationDao.getChildLocations(parentId);
        request.setAttribute("childLocations", childLocations);
        RequestDispatcher dispatcher = request.getRequestDispatcher("manageChildLocations.jsp"); // Redirect to a JSP page to display child locations
        dispatcher.forward(request, response);
    }
}
