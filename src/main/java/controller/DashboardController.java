package controller;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Import RoleType Enum
import models.RoleType;

@WebServlet("/dashboard")
public class DashboardController extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve user role from session as RoleType
        RoleType userRole = (RoleType) request.getSession().getAttribute("role");

        if (userRole == null) {
            // Redirect to login if role is not found in session
            response.sendRedirect(request.getContextPath() + "/login.html");
            return;
        }

        // Set the user role as an attribute for display in JSP
        request.setAttribute("userRole", userRole);

        // Fetch and set data based on role
        if (userRole == RoleType.ADMIN || userRole == RoleType.MANAGER) {
            request.setAttribute("totalBooks", getTotalBooks());
            request.setAttribute("totalUsers", getTotalUsers());
            request.setAttribute("recentActivities", getRecentActivities());
        }
        if (userRole == RoleType.LIBRARIAN || userRole == RoleType.STUDENT) {
            request.setAttribute("totalBorrowings", getTotalBorrowings());
        }
        if (userRole == RoleType.ADMIN) {
            request.setAttribute("overdueBooks", getOverdueBooks());
        }

        // Data for charts
        if (userRole == RoleType.ADMIN || userRole == RoleType.MANAGER) {
            request.setAttribute("booksChartData", getBooksChartData());
            request.setAttribute("userRegistrationsData", getUserRegistrationsData());
        }
        if (userRole == RoleType.LIBRARIAN) {
            request.setAttribute("borrowingActivityData", getBorrowingActivityData());
        }

        // Forward to JSP
        request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
    }

    // Methods to fetch data
    private int getTotalBooks() {
        return 120; // Example placeholder
    }

    private int getTotalUsers() {
        return 300; // Example placeholder
    }

    private int getTotalBorrowings() {
        return 45; // Example placeholder
    }

    private int getOverdueBooks() {
        return 10; // Example placeholder
    }

    private List<Activity> getRecentActivities() {
        List<Activity> activities = new ArrayList<>();
        activities.add(new Activity("Book borrowed", "2024-11-02"));
        activities.add(new Activity("New user registered", "2024-11-01"));
        return activities;
    }

    private int[] getBooksChartData() {
        return new int[] {120, 45, 10}; // Placeholder data
    }

    private int[] getUserRegistrationsData() {
        return new int[] {10, 20, 15, 30, 25, 35, 40}; // Placeholder data
    }

    private int[] getBorrowingActivityData() {
        return new int[] {15, 25, 30, 10, 20, 25}; // Placeholder data
    }
}

// Helper class for activity logs
class Activity {
    private String description;
    private String date;

    public Activity(String description, String date) {
        this.description = description;
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }
}
