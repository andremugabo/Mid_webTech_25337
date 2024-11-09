package controller;

import dao.BookDAO;
import dao.BorrowerDAO;
import dao.MembershipDAO;
import dao.ShelfDao;
import dao.UserDao;
import models.Borrower;
import models.Membership;
import models.MembershipType;
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
import java.util.Locale;
import java.util.UUID;

@WebServlet("/borrower")
public class BorrowerController extends HttpServlet {
    
	private static final long serialVersionUID = 1L;
	private BorrowerDAO borrowerDAO;
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    @Override
    public void init() {
        borrowerDAO = new BorrowerDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null || action.equals("list")) {
            displayAllBorrowers(request, response);
        } else if (action.equals("edit")) {
            showEditForm(request, response);
        } 
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action.equals("borrow")) {
            try {
				createBorrower(request, response);
			} catch (IOException | ParseException e) {
			    e.printStackTrace();
			}
        } else if (action.equals("update")) {
            try {
				updateBorrower(request, response);
			} catch (IOException | ParseException e) {
			    e.printStackTrace();
			}
        }
    }

    private void displayAllBorrowers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Borrower> listBorrowers = borrowerDAO.displayAll();
        request.setAttribute("listBorrowers", listBorrowers);
        RequestDispatcher dispatcher = request.getRequestDispatcher("borrower.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UUID borrowerId = UUID.fromString(request.getParameter("borrowerId"));
        Borrower existingBorrower = borrowerDAO.selectOne(borrowerId);
        RequestDispatcher dispatcher = request.getRequestDispatcher("borrower-form.jsp");
        request.setAttribute("borrower", existingBorrower);
        dispatcher.forward(request, response);
    }

    private void createBorrower(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
       
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
        
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
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
        long borrowedBooksCount = borrowerDAO.countBorrowedBooksByReaderWithStatus(readerUUID, BookStatus.BORROWED);
        if (borrowedBooksCount >= allowedBooks) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Borrow limit reached. Return a book before borrowing.");
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



    private void updateBorrower(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
        UUID borrowerId = UUID.fromString(request.getParameter("borrowerId"));
        Borrower borrower = borrowerDAO.selectOne(borrowerId);
        borrower.setDueDate(formatter.parse(request.getParameter("dueDate")));
        borrower.setPickupDate(formatter.parse(request.getParameter("pickupDate")));
        borrower.setFine(Integer.parseInt(request.getParameter("fine")));
        borrower.setLateChargeFees(Integer.parseInt(request.getParameter("lateChargeFees")));

        borrowerDAO.update(borrower);
        response.sendRedirect("borrower?action=list");
    }

    
}
