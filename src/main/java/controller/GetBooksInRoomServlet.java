package controller;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ShelfDao;

@WebServlet("/getBooksInRoom")
public class GetBooksInRoomServlet extends HttpServlet {
    
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	ShelfDao shelfDao = new ShelfDao();
        String roomId = request.getParameter("roomId");

       
        UUID roomUUID = UUID.fromString(roomId);

        
        int totalBooks = shelfDao.getTotalBooksInRoom(roomUUID);

        System.out.println(totalBooks+" MMMMMMMMMMMMMMMMM");
        request.setAttribute("totalBooks", totalBooks);
        RequestDispatcher dispatcher = request.getRequestDispatcher("rooms?action=manageRooms");
        dispatcher.forward(request, response);
    }
}

