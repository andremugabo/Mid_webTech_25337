package dao;

import models.Borrower;
import org.hibernate.Session;
import org.hibernate.Transaction;
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
            session.save(borrower);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
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
            if (transaction != null) transaction.rollback();
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

    // Display all Borrowers
    public List<Borrower> displayAll() {
        try (Session session = HibernateUtil.getSession().openSession()) {
            return session.createQuery("from Borrower where isDeleted = false", Borrower.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
