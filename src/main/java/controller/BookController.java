package controller;

import dao.BookDAO;
import dao.ShelfDao;
import models.Book;
import models.BookStatus;
import models.RoleType;
import models.Shelf;
import util.ISBNGenerator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@WebServlet("/book")
public class BookController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private BookDAO bookDAO = new BookDAO();
	private ShelfDao shelfDao = new ShelfDao();
	private Shelf shelf = new Shelf();
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
	RequestDispatcher disptcher;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		if (action == null) {
			action = "list";
		}

		switch (action) {
		case "view":
			viewBook(request, response);
			break;
		case "list":
			listBooks(request, response);
			break;
		case "displayBooks":
			displayBooks(request, response);
			break;
		default:
			listBooks(request, response);
			break;
		}
	}

	private void viewBook(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RoleType userRole = (RoleType) request.getSession().getAttribute("role");
		if (userRole == null) {
			response.sendRedirect(request.getContextPath() + "login.html");
			return;
		}
		UUID bookId = UUID.fromString(request.getParameter("bookId"));
		Book book = bookDAO.selectOne(bookId);
		request.setAttribute("book", book);
		request.getRequestDispatcher("viewBook.jsp").forward(request, response);
	}

	private void listBooks(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RoleType userRole = (RoleType) request.getSession().getAttribute("role");
		if (userRole == null) {
			response.sendRedirect(request.getContextPath() + "login.html");
			return;
		}
		List<Book> bookList = bookDAO.displayAll();
		List<Shelf> shelfList = shelfDao.getAllShelves();
		request.setAttribute("shelfList", shelfList);
		request.setAttribute("userRole", userRole);
		request.setAttribute("bookList", bookList);
		disptcher = request.getRequestDispatcher("manageBooks.jsp");
		disptcher.forward(request, response);
	}

	private void displayBooks(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RoleType userRole = (RoleType) request.getSession().getAttribute("role");
		if (userRole == null) {
			response.sendRedirect(request.getContextPath() + "login.html");
			return;
		}
		List<Book> bookList = bookDAO.displayAll();
		List<Shelf> shelfList = shelfDao.getAllShelves();
		request.setAttribute("shelfList", shelfList);
		request.setAttribute("userRole", userRole);
		request.setAttribute("bookList", bookList);
		disptcher = request.getRequestDispatcher("borrowingRecords.jsp");
		disptcher.forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		if (action == null) {
			action = "create";
		}

		switch (action) {
		case "create":
			createBook(request, response);
			break;
		case "update":
			updateBook(request, response);
			break;
		case "delete":
			deleteBook(request, response);
			break;
		default:
			response.sendRedirect("book?action=list");
			break;
		}
	}

	private void createBook(HttpServletRequest request, HttpServletResponse response)
	        throws IOException {
	    Book book = new Book();
	    
	   
	    String publicationYear = request.getParameter("publicationYear");
	    if (publicationYear == null || publicationYear.isEmpty()) {
	        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Publication year is required.");
	        return;
	    }
	    Date pYear;
	    try {
	        pYear = formatter.parse(publicationYear);
	    } catch (ParseException e) {
	        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid publication year format.");
	        return;
	    }
	    Date today = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
	    if (pYear.after(today)) {
	        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Publication year cannot be in the future.");
	        return;
	    }
	    book.setPublicationYear(pYear);

	   
	    String shelfId = request.getParameter("shelfId");
	    if (shelfId == null || shelfId.isEmpty()) {
	        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Shelf ID is required.");
	        return;
	    }
	    try {
	        shelf = shelfDao.getShelfById(UUID.fromString(shelfId));
	    } catch (IllegalArgumentException e) {
	        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Shelf ID format.");
	        return;
	    }
	    if (shelf == null) {
	        response.sendError(HttpServletResponse.SC_NOT_FOUND, "Shelf not found.");
	        return;
	    }
	    
	    ShelfDao shelfDao = new ShelfDao();
	    UUID shelf_Id = UUID.fromString(shelfId); 
	    shelfDao.incrementAvailableStock(shelf_Id);
	    book.setShelf(shelf);
	    
	    String isbnCode = ISBNGenerator.generateISBN();
	    
	    book.setTitle(request.getParameter("title"));
	    book.setBookStatus(BookStatus.valueOf(request.getParameter("bookStatus")));
	    book.setImage(request.getParameter("image"));
	    book.setEdition(Integer.parseInt(request.getParameter("edition")));
	    book.setIsbnCode(isbnCode);
	    book.setPublisherName(request.getParameter("publisherName"));

	   
	    bookDAO.create(book);
	    response.sendRedirect("book?action=list");
	}


	private void updateBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
		UUID bookId = UUID.fromString(request.getParameter("bookId"));
		Book book = bookDAO.selectOne(bookId);

		if (book != null) {
			book.setTitle(request.getParameter("title"));
			book.setBookStatus(BookStatus.valueOf(request.getParameter("bookStatus")));
			book.setImage(request.getParameter("image"));
			book.setEdition(Integer.parseInt(request.getParameter("edition")));
			book.setIsbnCode(request.getParameter("isbnCode"));
			book.setPublisherName(request.getParameter("publisherName"));
			book.setPublicationYear(new java.util.Date());

			bookDAO.update(book);
		}
		response.sendRedirect("book?action=list");
	}

	private void deleteBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
		UUID bookId = UUID.fromString(request.getParameter("bookId"));
		bookDAO.softDelete(bookId);
		response.sendRedirect("book?action=list");
	}
}
