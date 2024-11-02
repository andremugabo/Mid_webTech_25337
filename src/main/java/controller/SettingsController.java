package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/settings")
public class SettingsController extends HttpServlet {

  
	private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("settings.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle form submissions for settings
        String action = request.getParameter("action");
        switch (action) {
            case "updateProfile":
                // Logic to update user profile
                break;
            case "updateNotifications":
                // Logic to update notification preferences
                break;
            case "updateMembership":
                // Logic to update membership settings
                break;
            // Add more cases as needed for other settings
            default:
                break;
        }

        // After processing, redirect to the settings page or display a success message
        response.sendRedirect("settings?success=true");
    }
}
