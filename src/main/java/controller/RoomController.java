package controller;

import dao.RoomDAO;
import models.RoleType;
import models.Room;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet("/rooms")
public class RoomController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private RoomDAO roomDAO;
	RequestDispatcher dispatcher;

	@Override
	public void init() {
		roomDAO = new RoomDAO();
	}

	// Handle GET request to list all rooms
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		switch (action) {
		case "manageRooms":
			RoleType userRole = (RoleType) request.getSession().getAttribute("role");
			if (userRole == null) {
				response.sendRedirect(request.getContextPath() + "login.html");
				return;
			}
			List<Room> rooms = roomDAO.findAll();
			request.setAttribute("userRole", userRole);
			request.setAttribute("rooms", rooms);
			dispatcher = request.getRequestDispatcher("rooms.jsp");
			dispatcher.forward(request, response);
		default:
			dispatcher = request.getRequestDispatcher("login.html");
			dispatcher.include(request, response);

		}
	}

	// Handle POST request to add a new room
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String roomCode = request.getParameter("roomCode");

		Room room = new Room();
		room.setRoomCode(roomCode);

		roomDAO.save(room);

		response.sendRedirect("rooms?action=manageRooms"); 
	}

	// Handle PUT request to update an existing room
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UUID roomId = UUID.fromString(request.getParameter("roomId"));
		String roomCode = request.getParameter("roomCode");

		Room room = roomDAO.findById(roomId);
		if (room != null) {
			room.setRoomCode(roomCode);
			roomDAO.update(room);
		}

		response.sendRedirect("rooms");
	}

	// Handle DELETE request to delete a room
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UUID roomId = UUID.fromString(request.getParameter("roomId"));
		roomDAO.delete(roomId);

		response.sendRedirect("rooms");
	}
}
