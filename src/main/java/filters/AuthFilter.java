package filters;

import java.io.IOException;

import javax.servlet.*;

import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.User;

@WebFilter("/views/*")
public class AuthFilter implements Filter {
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession(false);
		String requestURI = httpRequest.getRequestURI();

		// Check if session exists and user attribute is set
		if (session == null || session.getAttribute("user") == null) {
			System.out.println("User not logged in. Redirecting to login.html");
			httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.html");
			return;
		}

		// Retrieve the user from session
		User user = (User) session.getAttribute("user");
		if (user == null) {
			System.out.println("User not found in session. Redirecting to login.html");
			httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.html");
			return;
		}

		String role = user.getRole().name();

//		// Check access for the dashboard page
//		if (requestURI.contains("/views/dashboard.jsp")) {
//			if (hasRoleAccess(requestURI, role)) {
//				chain.doFilter(request, response);
//			} else {
//				System.out.println("Unauthorized access to dashboard. Redirecting to unauthorized.jsp");
//				httpResponse.sendRedirect(httpRequest.getContextPath() + "/unauthorized.jsp");
//			}
//			return;
//		}

		
		if (hasRoleAccess(requestURI, role)) {
			chain.doFilter(request, response);
		} else {
			System.out.println("Unauthorized access. Redirecting to unauthorized.jsp");
			httpResponse.sendRedirect(httpRequest.getContextPath() + "/unauthorized.jsp");
		}
	}

	
	private boolean hasRoleAccess(String requestURI, String role) {
        
        switch (role) {
            case "LIBRARIAN":
                return requestURI.contains("/views/librarianDashboard.jsp")
                        || requestURI.contains("/views/manageBooks.jsp")
                        || requestURI.contains("/views/dashboard.jsp"); 
            case "STUDENT":
                return requestURI.contains("/views/studentDashboard.jsp")
                        || requestURI.contains("/views/borrowingRecords.jsp")
                        || requestURI.contains("/views/dashboard.jsp"); 
            case "TEACHER":
                return requestURI.contains("/views/teacherDashboard.jsp")
                        || requestURI.contains("/views/dashboard.jsp"); 
            case "MANAGER":
                return requestURI.contains("/views/managerDashboard.jsp")
                        || requestURI.contains("/views/reports.jsp")
                        || requestURI.contains("/views/dashboard.jsp"); 
            case "ADMIN":
                return requestURI.contains("/views/adminDashboard.jsp")
                        || requestURI.contains("/views/manageUsers.jsp")
                        || requestURI.contains("/views/dashboard.jsp")
                        || requestURI.contains("/views/locationManagement");
            default:
                return false; 
        }
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}
}