package controller;

import dao.BookDAO;
import models.Book;
import models.BookStatus;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/book")
public class BookController extends HttpServlet {

    
	private static final long serialVersionUID = 1L;
	private BookDAO bookDAO = new BookDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
            default:
                listBooks(request, response);
                break;
        }
    }

    private void viewBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UUID bookId = UUID.fromString(request.getParameter("bookId"));
        Book book = bookDAO.selectOne(bookId);
        request.setAttribute("book", book);
        request.getRequestDispatcher("viewBook.jsp").forward(request, response);
    }

    private void listBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("bookList", bookDAO.displayAll());
        request.getRequestDispatcher("listBooks.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
                response.sendRedirect("listBooks.jsp");
                break;
        }
    }

    private void createBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Book book = new Book();
        book.setTitle(request.getParameter("title"));
        book.setBookStatus(BookStatus.valueOf(request.getParameter("bookStatus")));
        book.setImage(request.getParameter("image"));
        book.setEdition(Integer.parseInt(request.getParameter("edition")));
        book.setIsbnCode(request.getParameter("isbnCode"));
        book.setPublisherName(request.getParameter("publisherName"));
        book.setPublicationYear(new java.util.Date()); // Set current date for simplicity

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
