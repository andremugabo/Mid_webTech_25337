package dao;

import models.Book;
import models.BookStatus;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;
import java.util.List;
import java.util.UUID;

public class BookDAO {

	private SessionFactory sessionFactory = HibernateUtil.getSession();

	// Check if a book exists by title
	public boolean checkIfExists(String title) {
		try (Session session = sessionFactory.openSession()) {
			String hql = "SELECT 1 FROM Book WHERE title = :title";
			Query<?> query = session.createQuery(hql);
			query.setParameter("title", title);
			return query.uniqueResult() != null;
		}
	}

	// Create a new book
	public void create(Book book) {
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			session.save(book);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
	}

	// Update an existing book
	public void update(Book book) {
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			session.update(book);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
	}

	// Soft delete a book by setting a deletion flag (isDeleted)
	public void softDelete(UUID bookId) {
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			String hql = "UPDATE Book SET isDeleted = true WHERE bookId = :bookId";
			Query<?> query = session.createQuery(hql);
			query.setParameter("bookId", bookId);
			query.executeUpdate();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
	}

	// Select one book by ID
	public Book selectOne(UUID bookId) {
		try (Session session = sessionFactory.openSession()) {
			return session.get(Book.class, bookId);
		}
	}

	// Retrieve a book by its ID (this is your method)
	public Book getBookById(UUID bookId) {
		try (Session session = sessionFactory.openSession()) {
			return session.get(Book.class, bookId);
		}
	}

	// Display all books that are not deleted
	public List<Book> displayAll() {
		try (Session session = sessionFactory.openSession()) {
			String hql = "FROM Book";
			Query<Book> query = session.createQuery(hql, Book.class);
			return query.list();
		}
	}
	
	public void updateBookStatus(UUID bookId, BookStatus newStatus) {
	    Transaction transaction = null;
	    try (Session session = sessionFactory.openSession()) {
	        
	        transaction = session.beginTransaction();
	        
	        
	        String hql = "UPDATE Book SET bookStatus = :bookStatus WHERE bookId = :bookId";
	        int result = session.createQuery(hql)
	                            .setParameter("bookStatus", newStatus)
	                            .setParameter("bookId", bookId)
	                            .executeUpdate();
	        
	        
	        transaction.commit();
	        
	        
	        if(result > 0) {
	            System.out.println("Book status updated successfully.");
	        } else {
	            System.out.println("No book found with the given ID.");
	        }
	        
	    } catch (Exception e) {
	        if (transaction != null) {
	            transaction.rollback();
	        }
	        e.printStackTrace();
	    }
	}

}
