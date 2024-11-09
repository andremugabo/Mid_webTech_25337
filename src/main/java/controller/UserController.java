package controller;

import dao.LocationDao;
import dao.UserDao;
import models.RoleType;
import models.GenderType;
import models.Location;
import models.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import util.PasswordUtil;
import java.io.File;

@WebServlet("/user")
@MultipartConfig
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao;
	private LocationDao locationDao;
	RequestDispatcher dispatcher;

	@Override
	public void init() throws ServletException {
		userDao = new UserDao();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		if (action == null) {
			response.getWriter().write("Action parameter is missing");
			return;
		}

		switch (action) {
		case "create":
			handleUserCreation(request, response);
			break;
		case "updateProfile":
			handleProfileUpdate(request, response);
			break;
		case "updateUser":
			handleUserUpdate(request, response);
			break;
		case "addUser":
			addUser(request, response);
			break;
		case "updateUserVillage":
			updateUserVillage(request, response);
			break;
		 case "updateProfileImage":
             handleProfileImageUpdate(request, response);
             break;
		default:
			addUser(request, response);
			break;
		}
	}

	private void handleUserCreation(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String roleParam = request.getParameter("role");
		String phoneNumber = request.getParameter("phoneNumber");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String genderParam = request.getParameter("gender");

		String hashedPassword = PasswordUtil.hashPassword(password);

		RoleType role = null;
		if (roleParam != null) {
			try {
				role = RoleType.valueOf(roleParam.toUpperCase());
			} catch (IllegalArgumentException e) {
				response.getWriter().write("Invalid role provided");
				return;
			}
		}

		GenderType genderType = null;
		if (genderParam != null) {
			try {
				genderType = GenderType.valueOf(genderParam.toUpperCase());
			} catch (IllegalArgumentException e) {
				response.getWriter().write("Invalid gender provided");
				return;
			}
		}

		if (userName == null || password == null || role == null || phoneNumber == null || firstName == null
				|| lastName == null || genderType == null) {
			response.getWriter().write("All fields are required");
			return;
		}

		User user = new User();
		user.setUserName(userName);
		user.setPassword(hashedPassword);
		user.setRole(role);
		user.setPhoneNumber(phoneNumber);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setGender(genderType);
		user.setDeleted(false);

		if (!userDao.checkIfUserExists(userName)) {
			userDao.createUser(user);
			response.getWriter().write("<h1 class=\"success\">User created successfully</h1>");
			RequestDispatcher dispatcher = request.getRequestDispatcher("signup.html");
			dispatcher.include(request, response);
		} else {
			response.getWriter().write("<h1 class=\"error\">Username already exists</h1>");
			RequestDispatcher dispatcher = request.getRequestDispatcher("signup.html");
			dispatcher.include(request, response);
		}
	}

	private void handleProfileUpdate(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String userId = request.getParameter("userId");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String phoneNumber = request.getParameter("phoneNumber");
		String gender = request.getParameter("gender");

		User user = userDao.selectUser(UUID.fromString(userId));

		Part filePart = request.getPart("profileImage");
		String imagePath = null;

		if (filePart != null && filePart.getSize() > 0) {
			String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
			Path uploads = Paths.get("assets/images");
			Files.createDirectories(uploads);
			filePart.write(uploads.resolve(fileName).toString());
			imagePath = "assets/images/" + fileName;
		}

		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setPhoneNumber(phoneNumber);
		user.setGender(GenderType.valueOf(gender));
		if (imagePath != null) {
			user.setPicture(imagePath);
		}

		userDao.updateUser(user);
		response.sendRedirect("profile.jsp?userId=" + userId);
	}

	private void addUser(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		String roleParam = request.getParameter("role");
		String phoneNumber = request.getParameter("phoneNumber");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String genderParam = request.getParameter("gender");

		String hashedPassword = PasswordUtil.hashPassword(password);

		RoleType role = null;
		if (roleParam != null) {
			try {
				role = RoleType.valueOf(roleParam.toUpperCase());
			} catch (IllegalArgumentException e) {
				response.getWriter().write("Invalid role provided");
				return;
			}
		}

		GenderType genderType = null;
		if (genderParam != null) {
			try {
				genderType = GenderType.valueOf(genderParam.toUpperCase());
			} catch (IllegalArgumentException e) {
				response.getWriter().write("Invalid gender provided");
				return;
			}
		}

		if (userName == null || password == null || role == null || phoneNumber == null || firstName == null
				|| lastName == null || genderType == null) {
			response.getWriter().write("All fields are required");
			return;
		}

		User user = new User();
		user.setUserName(userName);
		user.setPassword(hashedPassword);
		user.setRole(role);
		user.setPhoneNumber(phoneNumber);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setGender(genderType);
		user.setDeleted(false);

		if (!userDao.checkIfUserExists(userName)) {
			userDao.createUser(user);
			response.getWriter().write("<h1 class=\"success\">User created successfully</h1>");
			RequestDispatcher dispatcher = request.getRequestDispatcher("user?action=manageUsers");
			dispatcher.include(request, response);
		}
//	    else {
//	    	return;
//	        response.getWriter().write("<h1 class=\"error\">Username already exists</h1>");
//	        RequestDispatcher dispatcher = request.getRequestDispatcher("user?action=manageUsers");
//	        dispatcher.include(request, response);
//	    }
	}

	private void handleUserUpdate(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		UUID userId = UUID.fromString(request.getParameter("userId"));
		User user = userDao.selectUser(userId);

		if (user != null) {
			String newUserName = request.getParameter("username");
			String newRoleParam = request.getParameter("role");
			String newPhoneNumber = request.getParameter("phoneNumber");
			String newGenderParam = request.getParameter("gender");
			RoleType newRole = null;

			if (newRoleParam != null) {
				try {
					newRole = RoleType.valueOf(newRoleParam.toUpperCase());
				} catch (IllegalArgumentException e) {
					response.getWriter().write("Invalid role provided");
					return;
				}
			}

			GenderType newGender = null;
			if (newGenderParam != null) {
				try {
					newGender = GenderType.valueOf(newGenderParam.toUpperCase());
				} catch (IllegalArgumentException e) {
					response.getWriter().write("Invalid gender provided");
					return;
				}
			}

			user.setUserName(newUserName);
			user.setRole(newRole);
			user.setPhoneNumber(newPhoneNumber);
			user.setGender(newGender);

			userDao.updateUser(user);
			request.getSession().setAttribute("message", "User updated successfully");
			request.setAttribute("action", "manageUsers");
			response.sendRedirect("user?action=manageUsers");
		} else {
			response.getWriter().write("User not found");
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");

		switch (action) {
		case "manageUsers":
			RoleType userRole = (RoleType) request.getSession().getAttribute("role");

			if (userRole == null) {
				response.sendRedirect(request.getContextPath() + "/login.html");
				return;
			}

			List<User> userList = userDao.listAllUsers();
			request.setAttribute("userList", userList);
			request.setAttribute("userRole", userRole);
			dispatcher = request.getRequestDispatcher("manageUsers.jsp");
			dispatcher.forward(request, response);
			break;

		case "deleteUser":
			handleUserDeletion(request, response);
			break;

		default:
			String userId = request.getParameter("userId");
			if (userId != null) {
				try {
					User user = userDao.selectUser(UUID.fromString(userId));
					List<Location> provinces = locationDao.getParentLocationsByType("PROVINCE");
					if (user != null) {
						request.setAttribute("user", user);
						request.setAttribute("provinces", provinces);
						dispatcher = request.getRequestDispatcher("profile.jsp");
						dispatcher.forward(request, response);
					} else {
						response.getWriter().write("User not found");
					}
				} catch (IllegalArgumentException e) {
					response.getWriter().write("Invalid user ID format");
				}
			} else {
				response.getWriter().write("User ID is required");
			}
			break;
		}
	}

	private void updateUserVillage(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		UUID userId = UUID.fromString(request.getParameter("userId"));
		UUID newVillageId = UUID.fromString(request.getParameter("village"));
		System.out.println(newVillageId);
		LocationDao locationDao = new LocationDao();
		Location newVillage = locationDao.selectLocation(newVillageId);

		if (newVillage != null) {

			boolean success = userDao.updateVillage(userId, newVillage);

			if (success) {
				dispatcher = request.getRequestDispatcher("profile.jsp");
				dispatcher.forward(request, response);
			} else {
				response.getWriter().write("Failed to update user's village.");
			}
		} else {
			response.getWriter().write("Invalid village ID.");
		}
	}

	private void handleUserDeletion(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String userIdStr = request.getParameter("userId");
		if (userIdStr != null) {
			try {
				UUID userId = UUID.fromString(userIdStr);
				boolean isDeleted = userDao.softDeleteUser(userId);

				if (isDeleted) {
					response.sendRedirect("user?action=manageUsers&message='User successfully deleted'");
				} else {
					response.getWriter().write("User not found or deletion failed");
				}
			} catch (IllegalArgumentException e) {
				response.getWriter().write("Invalid user ID format");
			}
		} else {
			response.getWriter().write("User ID is required");
		}
	}

	private void handleProfileImageUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		UUID userId =UUID.fromString(request.getParameter("userId"));
		Part profileImagePart = request.getPart("profileImage");

		if (profileImagePart != null && profileImagePart.getSize() > 0) {
			
			String uploadDir = getServletContext().getRealPath("/assets/images");
			File uploadFolder = new File(uploadDir);
			if (!uploadFolder.exists()) {
				uploadFolder.mkdir(); 
			}

			
			String fileName = UUID.randomUUID().toString() + ".jpg";
			File file = new File(uploadFolder, fileName);
			try (InputStream inputStream = profileImagePart.getInputStream()) {
				Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
			}

			
			UserDao userDao = new UserDao();
			User user = userDao.selectUser(userId);
			if (user != null) {
				user.setPicture("/assets/images/" + fileName);
				userDao.updateUser(user);
			}
		}

		
		response.sendRedirect("profile.jsp");
	}

}
