package controller;

import dao.UserDao;
import models.RoleType;
import models.GenderType;
import models.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet("/user")
public class UserController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDao userDao;

    @Override
    public void init() throws ServletException {
        userDao = new UserDao(); 
    }

    // Handle user creation
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("userName");
        String password = request.getParameter("password"); 
        String roleParam = request.getParameter("role");
        String phoneNumber = request.getParameter("phoneNumber");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String genderParam = request.getParameter("gender");

        // Convert role from String to RoleType
        RoleType role = null;
        if (roleParam != null) {
            try {
                role = RoleType.valueOf(roleParam.toUpperCase());
            } catch (IllegalArgumentException e) {
                response.getWriter().write("Invalid role provided");
                return; // Exit if the role is invalid
            }
        }

        // Convert gender from String to GenderType
        GenderType genderType = null;
        if (genderParam != null) {
            try {
                genderType = GenderType.valueOf(genderParam.toUpperCase());
            } catch (IllegalArgumentException e) {
                response.getWriter().write("Invalid gender provided");
                return; // Exit if the gender is invalid
            }
        }

        // Additional validation for input
        if (userName == null || password == null || role == null || phoneNumber == null || firstName == null || lastName == null || genderType == null) {
            response.getWriter().write("All fields are required");
            return; // Exit if any required fields are missing
        }

        User user = new User();
        user.setUserName(userName);
        user.setPassword(password); // Store hashed password instead
        user.setRole(role);
        user.setPhoneNumber(phoneNumber);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setGender(genderType); // Set gender from GenderType
        user.setDeleted(false); // Default to false

        if (!userDao.checkIfUserExists(userName)) {
            userDao.createUser(user);
            response.getWriter().write("User created successfully");
        } else {
            response.getWriter().write("Username already exists");
        }
    }

    // Handle user updates
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UUID userId = UUID.fromString(request.getParameter("userId"));
        User user = userDao.selectUser(userId);

        if (user != null) {
            String newUserName = request.getParameter("userName");
            String newRoleParam = request.getParameter("role");
            String newFirstName = request.getParameter("firstName");
            String newLastName = request.getParameter("lastName");
            String newGenderParam = request.getParameter("gender");
            RoleType newRole = null;

            // Convert new role from String to RoleType
            if (newRoleParam != null) {
                try {
                    newRole = RoleType.valueOf(newRoleParam.toUpperCase());
                } catch (IllegalArgumentException e) {
                    response.getWriter().write("Invalid role provided");
                    return; // Exit if the new role is invalid
                }
            }

            // Convert new gender from String to GenderType
            GenderType newGender = null;
            if (newGenderParam != null) {
                try {
                    newGender = GenderType.valueOf(newGenderParam.toUpperCase());
                } catch (IllegalArgumentException e) {
                    response.getWriter().write("Invalid gender provided");
                    return; // Exit if the new gender is invalid
                }
            }

            user.setUserName(newUserName);
            user.setRole(newRole); // Update with the new role
            user.setFirstName(newFirstName); // Update first name
            user.setLastName(newLastName); // Update last name
            user.setGender(newGender); // Update gender

            userDao.updateUser(user);
            response.getWriter().write("User updated successfully");
        } else {
            response.getWriter().write("User not found");
        }
    }

    // Handle soft deletion of a user
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UUID userId = UUID.fromString(request.getParameter("userId"));
        userDao.softDeleteUser(userId);
        response.getWriter().write("User soft deleted successfully");
    }

    
  
 
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDao userDao = new UserDao();
        List<User> userList = userDao.listAllUsers();
        request.setAttribute("userList", userList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("manageUsers.jsp");
        dispatcher.forward(request, response);
    }

}
