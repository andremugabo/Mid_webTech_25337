package controller;

import dao.BookDAO;
import dao.BorrowerDAO;
import dao.MembershipDAO;
import dao.ShelfDao;
import dao.UserDao;
import models.Borrower;
import models.Membership;
import models.MembershipType;
import models.RoleType;
import models.Shelf;
import models.Book;
import models.BookStatus;
import models.User;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@WebServlet("/borrower")
public class BorrowerController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private BorrowerDAO borrowerDAO;
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	RequestDispatcher dispatcher;

	@Override
	public void init() {
		borrowerDAO = new BorrowerDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		if (action == null) {
			response.sendRedirect(request.getContextPath() + "login.html");
			return;
		}
		switch (action) {
		case "ViewBorrowedBook":
			ViewBorrowedBook(request, response);
			break;
		case "ReturnBorrowedBook":
			returnBook(request, response);
			break;
		case "list":
			ViewBorrowedBook(request, response);
			break;
		}

	}

	private void ViewBorrowedBook(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RoleType userRole = (RoleType) request.getSession().getAttribute("role");
		if (userRole == null) {
			response.sendRedirect(request.getContextPath() + "login.html");
			return;
		}
		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			response.sendRedirect(request.getContextPath() + "login.html");
			return;
		}

		UUID userId = user.getPersonId();
		if (userId == null) {
			response.sendRedirect(request.getContextPath() + "login.html");
			return;
		}

		List<Borrower> borrowerList = borrowerDAO.selectByReaderId(userId);

		int totalBooksBorrowed = 0;
		int totalFines = 0;

		for (Borrower borrower : borrowerList) {
			totalBooksBorrowed++;
			totalFines += borrower.getFine();
		}

		request.setAttribute("totalBooksBorrowed", totalBooksBorrowed);
		request.setAttribute("totalFines", totalFines);
		request.setAttribute("borrowerById", borrowerList);
		RequestDispatcher dispatcher = request.getRequestDispatcher("borrower.jsp");
		dispatcher.forward(request, response);

	}

	private void returnBook(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RoleType userRole = (RoleType) request.getSession().getAttribute("role");
		if (userRole == null) {
			response.sendRedirect(request.getContextPath() + "login.html");
			return;
		}

		List<Borrower> allBorrowedBook = borrowerDAO.selectAllBorrowers();
		request.setAttribute("listBorrowers", allBorrowedBook);
		RequestDispatcher dispatcher = request.getRequestDispatcher("borrowerManagement.jsp");
		dispatcher.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		if (action.equals("borrow")) {
			try {
				createBorrower(request, response);
			} catch (IOException | ParseException e) {
				e.printStackTrace();
			}
		} else if (action.equals("updateReturnDate")) {
			try {
				updateBorrower(request, response);
			} catch (IOException | ParseException e) {
				e.printStackTrace();
			}
		}
	}

	private void createBorrower(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ParseException {

		Borrower borrower = new Borrower();
		String bookId = request.getParameter("bookId");
		String readerId = request.getParameter("readerId");
		String dueDateStr = request.getParameter("returnDate");
		String pickupDateStr = request.getParameter("pickupDate");

		int fine = 0;
		int lateChargeFees = 500;

		if (bookId == null || readerId == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing bookId or readerId.");
			return;
		}

		UUID bookUUID = UUID.fromString(bookId);
		UUID readerUUID = UUID.fromString(readerId);

		BookDAO bookDAO = new BookDAO();
		Book book = bookDAO.getBookById(bookUUID);
		if (book == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Book not found.");
			return;
		}

		UserDao userDAO = new UserDao();
		User user = userDAO.selectUser(readerUUID);
		if (user == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found.");
			return;
		}

		borrower.setBook(book);
		borrower.setReader(user);

		Date dueDate = formatter.parse(dueDateStr);
		Date pickupDate = formatter.parse(pickupDateStr);

		borrower.setDueDate(dueDate);
		borrower.setPickupDate(pickupDate);
		borrower.setFine(fine);
		borrower.setLateChargeFees(lateChargeFees);

		MembershipDAO membershipDAO = new MembershipDAO();
		Membership membership = membershipDAO.selectByUserId(readerUUID);

		if (membership == null) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "User has no membership.");
			return;
		}

		if (membership.getExpiringTime().before(new Date())) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "Membership has expired.");
			return;
		}

		Membership.Status status = membershipDAO.getMembershipStatus(readerUUID);
		System.out.println("Membership Status: " + status);
		if (status == null || status != Membership.Status.APPROVED) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "Membership status is not APPROVED.");
			return;
		}

		MembershipType membershipType = membership.getMembershipType();
		int allowedBooks;
		switch (membershipType.getMembershipName()) {
		case "GOLD":
			allowedBooks = 5;
			break;
		case "SILVER":
			allowedBooks = 3;
			break;
		case "STRIVER":
			allowedBooks = 2;
			break;
		default:
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid membership type.");
			return;
		}
		
		BorrowerDAO borrowerDAO = new BorrowerDAO();
		boolean borrowedBooksCount = borrowerDAO.canBorrowMoreBooks(readerUUID, allowedBooks);
		if (!borrowedBooksCount) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN,
					"Borrow limit reached. Return a book before borrowing.");
			return;
		}

		Shelf shelf = book.getShelf();
		if (shelf == null || shelf.getAvailableStock() <= 0) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Book is currently unavailable.");
			return;
		}

		ShelfDao shelfDAO = new ShelfDao();
		shelf.setAvailableStock(shelf.getAvailableStock() - 1);
		shelf.setBorrowedNumber(shelf.getBorrowedNumber() + 1);
		shelfDAO.updateShelf(shelf);

		book.setBookStatus(BookStatus.BORROWED);
		bookDAO.updateBookStatus(bookUUID, BookStatus.BORROWED);

		borrowerDAO.create(borrower);

		response.sendRedirect("borrower?action=list");
	}

	private void updateBorrower(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ParseException {

		UUID borrowerId = UUID.fromString(request.getParameter("borrowerId"));
		String theReturnDate = request.getParameter("returnDate");
		Borrower borrower = borrowerDAO.selectOne(borrowerId);

		if (borrower == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Borrower record not found.");
			return;
		}

		Book book = borrower.getBook();
		if (book == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Book not associated with borrower.");
			return;
		}

		Shelf shelf = book.getShelf();
		if (shelf == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Shelf information is missing.");
			return;
		}

		Date dueDate = borrower.getDueDate();
		// Date returnDate = new Date();
		Date returnDate = formatter.parse(theReturnDate);

		int fine = 0;
		if (returnDate.after(dueDate)) {
			long daysLate = (returnDate.getTime() - dueDate.getTime()) / (1000 * 60 * 60 * 24);
			fine = (int) daysLate * borrower.getLateChargeFees();
		}
		borrower.setFine(fine);
		borrower.setReturn_date(returnDate);

		BookDAO bookDAO = new BookDAO();
		book.setBookStatus(BookStatus.AVAILABLE);
		bookDAO.updateBookStatus(book.getBookId(), BookStatus.AVAILABLE);

		ShelfDao shelfDAO = new ShelfDao();
		shelf.setAvailableStock(shelf.getAvailableStock() + 1);
		shelf.setBorrowedNumber(shelf.getBorrowedNumber() - 1);
		shelfDAO.updateShelf(shelf);

		borrowerDAO.update(borrower);

		response.sendRedirect("borrower?action=ReturnBorrowedBook");
	}

}
