package controller;

import dao.RoomDAO;
import models.Room;

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

    @Override
    public void init() {
        roomDAO = new RoomDAO();
    }

    // Handle GET request to list all rooms
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Room> rooms = roomDAO.findAll();
        request.setAttribute("rooms", rooms);
        request.getRequestDispatcher("/rooms.jsp").forward(request, response);
    }

    // Handle POST request to add a new room
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String roomCode = request.getParameter("roomCode");

        Room room = new Room();
        room.setRoomCode(roomCode);
        
        roomDAO.save(room);
        
        response.sendRedirect("rooms"); // Redirect to list rooms
    }

    // Handle PUT request to update an existing room
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UUID roomId = UUID.fromString(request.getParameter("roomId"));
        roomDAO.delete(roomId);
        
        response.sendRedirect("rooms");
    }
}
