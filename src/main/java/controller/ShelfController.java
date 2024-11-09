package controller;

import dao.RoomDAO;
import dao.ShelfDao;
import models.RoleType;
import models.Room;
import models.Shelf;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet("/shelves")
public class ShelfController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private ShelfDao shelfDao;
	private RoomDAO roomDao;
	Room room;
	RequestDispatcher dispatcher;

	@Override
	public void init() throws ServletException {
		shelfDao = new ShelfDao();
		roomDao = new RoomDAO();
		room = new Room();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		switch (action) {
		case "manageShelfs":
			RoleType userRole = (RoleType) request.getSession().getAttribute("role");
			if (userRole == null) {
				response.sendRedirect(request.getContextPath() + "login.html");
				return;
			}
			List<Shelf> shelfList = shelfDao.getAllShelves();
			List<Room> roomList = roomDao.findAll();
			request.setAttribute("shelfList", shelfList);
			request.setAttribute("userRole", userRole);
			request.setAttribute("roomList",roomList);
			dispatcher = request.getRequestDispatcher("shelves.jsp");
			dispatcher.forward(request, response);
			break;
		default:
			dispatcher = request.getRequestDispatcher("login.html");
			dispatcher.include(request, response);

		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Add a new shelf
		String bookCategory = request.getParameter("bookCategory");
		int initialStock = Integer.parseInt(request.getParameter("initialStock"));
		Integer availableStock = 0;
		String shelfCode = request.getParameter("shelfCode");
		
		
		
		room = roomDao.findById(UUID.fromString(request.getParameter("roomId")));
		 
		Shelf shelf = new Shelf();
		shelf.setBookCategory(bookCategory);
		shelf.setInitialStock(initialStock);
		shelf.setAvailableStock(availableStock);
		shelf.setRoom(room);
		shelf.setShelfCode(shelfCode);
		shelfDao.addShelf(shelf);

		response.sendRedirect("shelves?action=manageShelfs");
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Update an existing shelf
		String shelfId = request.getParameter("shelfId");
		String bookCategory = request.getParameter("bookCategory");
		int initialStock = Integer.parseInt(request.getParameter("initialStock"));
		int availableStock = Integer.parseInt(request.getParameter("availableStock"));

		Shelf shelf = shelfDao.getShelfById(UUID.fromString(shelfId));
		if (shelf != null) {
			shelf.setBookCategory(bookCategory);
			shelf.setInitialStock(initialStock);
			shelf.setAvailableStock(availableStock);
			shelfDao.updateShelf(shelf);
		}

		response.sendRedirect("shelves");
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Delete a shelf
		String shelfId = request.getParameter("shelfId");

		if (shelfId != null) {
			shelfDao.deleteShelf(UUID.fromString(shelfId));
		}

		response.sendRedirect("shelves");
	}
}
