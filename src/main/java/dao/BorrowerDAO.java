package dao;

import models.BookStatus;
import models.Borrower;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import util.HibernateUtil;

import java.util.List;
import java.util.UUID;

public class BorrowerDAO {

	// Check if Borrower exists by borrowerId
	public boolean checkIfExist(UUID borrowerId) {
		try (Session session = HibernateUtil.getSession().openSession()) {
			Borrower borrower = session.get(Borrower.class, borrowerId);
			return borrower != null;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// Create a new Borrower
	public void create(Borrower borrower) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSession().openSession()) {
			transaction = session.beginTransaction();
			System.out.println("Saving borrower: " + borrower);
			session.save(borrower);
			transaction.commit();
			System.out.println("Borrower saved successfully");
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			System.err.println("Error saving borrower: " + e.getMessage());
			e.printStackTrace();
		}
	}

	// Update an existing Borrower
	public void update(Borrower borrower) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSession().openSession()) {
			transaction = session.beginTransaction();
			session.update(borrower);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
	}

	// Select one Borrower by ID
	public Borrower selectOne(UUID borrowerId) {
		try (Session session = HibernateUtil.getSession().openSession()) {
			return session.get(Borrower.class, borrowerId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public long countBorrowedBooksByReaderWithStatus(UUID readerId, BookStatus status) {
		Session session = HibernateUtil.getSession().openSession();
		Transaction transaction = null;
		long borrowedBooksCount = 0;

		try {
			transaction = session.beginTransaction();

			String hql = "SELECT COUNT(b) FROM Borrower b WHERE b.reader_id =:readerId " + "JOIN b.book book "
					+ "WHERE b.reader.personId = :readerId " + "AND book.bookStatus = :status "
					+ "AND b.dueDate >= CURRENT_DATE";

			Query<Long> query = session.createQuery(hql, Long.class);
			query.setParameter("readerId", readerId);
			query.setParameter("status", status);

			borrowedBooksCount = query.getSingleResult();

			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}

		return borrowedBooksCount;
	}

	// Select Borrowers by readerId
	public List<Borrower> selectByReaderId(UUID readerId) {
		try (Session session = HibernateUtil.getSession().openSession()) {
			String hql = "FROM Borrower b WHERE b.reader.id = :readerId AND b.isDeleted = false";
			Query<Borrower> query = session.createQuery(hql, Borrower.class);
			query.setParameter("readerId", readerId);
			return query.list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// Select all Borrowers
	public List<Borrower> selectAllBorrowers() {
		try (Session session = HibernateUtil.getSession().openSession()) {
			String hql = "FROM Borrower WHERE isDeleted = false";
			Query<Borrower> query = session.createQuery(hql, Borrower.class);
			return query.list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public boolean canBorrowMoreBooks(UUID readerId, int membershipLimit) {
	    Session session = HibernateUtil.getSession().openSession();
	    Transaction transaction = null;
	    long activeBorrowCount = 0;

	    try {
	        transaction = session.beginTransaction();

	        // Count active borrowings for the reader
	        String hql = "SELECT COUNT(b) FROM Borrower b " +
	                     "WHERE b.reader.personId = :readerId AND b.return_date IS NULL AND b.isDeleted = false";
	        Query<Long> query = session.createQuery(hql, Long.class);
	        query.setParameter("readerId", readerId);

	        activeBorrowCount = query.getSingleResult();

	        transaction.commit();
	    } catch (Exception e) {
	        if (transaction != null) {
	            transaction.rollback();
	        }
	        e.printStackTrace();
	    } finally {
	        session.close();
	    }

	    return activeBorrowCount < membershipLimit;
	}


}
