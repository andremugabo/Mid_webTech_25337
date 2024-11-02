package controller;

import dao.BorrowerDAO;
import models.Borrower;
import models.Book;
import models.User;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class BorrowerController extends HttpServlet {
    
	private static final long serialVersionUID = 1L;
	private BorrowerDAO borrowerDAO;
	SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);

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

        if (action.equals("add")) {
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
        Book book = new Book();
        User user = new User();
        String bookId  = request.getParameter("bookId");
        String readerId = request.getParameter("readerId");
        String dueDate = request.getParameter("dueDate");
        String pickupDate = request.getParameter("pickupDate");
        String fine = request.getParameter("fine");
        String lateChargeFees = request.getParameter("lateChargeFees");
        book.setBookId(UUID.fromString(bookId));
        user.setPersonId(UUID.fromString(readerId));
        
        
        borrower.setBook(book);
        borrower.setReader(user);
        borrower.setDueDate(formatter.parse(dueDate));
        borrower.setPickupDate(formatter.parse(pickupDate));
        borrower.setFine(Integer.parseInt(fine));
        borrower.setLateChargeFees(Integer.parseInt(lateChargeFees));
       

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
