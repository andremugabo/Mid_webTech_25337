package controller;

import dao.ShelfDao;
import models.Shelf;

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

    @Override
    public void init() throws ServletException {
        shelfDao = new ShelfDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if (action != null && action.equals("edit")) {
            // Handle edit request
            String shelfId = request.getParameter("shelfId");
            Shelf shelf = shelfDao.getShelfById(UUID.fromString(shelfId));
            request.setAttribute("shelf", shelf);
            request.getRequestDispatcher("editShelf.jsp").forward(request, response);
        } else {
            // Default action to list all shelves
            List<Shelf> shelves = shelfDao.getAllShelves();
            request.setAttribute("shelves", shelves);
            request.getRequestDispatcher("shelves.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Add a new shelf
        String bookCategory = request.getParameter("bookCategory");
        int initialStock = Integer.parseInt(request.getParameter("initialStock"));
        int availableStock = initialStock;

        Shelf shelf = new Shelf();
        shelf.setBookCategory(bookCategory);
        shelf.setInitialStock(initialStock);
        shelf.setAvailableStock(availableStock);
        shelfDao.addShelf(shelf);

        response.sendRedirect("shelves");
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Delete a shelf
        String shelfId = request.getParameter("shelfId");

        if (shelfId != null) {
            shelfDao.deleteShelf(UUID.fromString(shelfId));
        }

        response.sendRedirect("shelves");
    }
}
