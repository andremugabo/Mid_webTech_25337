package dao;

import models.Shelf;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;
import java.util.UUID;

public class ShelfDao {

    // Save a Shelf
    public void addShelf(Shelf shelf) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession().openSession()) {
            transaction = session.beginTransaction();
            session.save(shelf);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Find a Shelf by ID
    public Shelf getShelfById(UUID shelfId) {
        try (Session session = HibernateUtil.getSession().openSession()) {
            return session.get(Shelf.class, shelfId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Get all Shelves
    public List<Shelf> getAllShelves() {
        try (Session session = HibernateUtil.getSession().openSession()) {
            return session.createQuery("from Shelf", Shelf.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Update an existing Shelf
    public void updateShelf(Shelf shelf) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession().openSession()) {
            transaction = session.beginTransaction();
            session.update(shelf);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Delete a Shelf by ID
    public void deleteShelf(UUID shelfId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession().openSession()) {
            transaction = session.beginTransaction();
            Shelf shelf = session.get(Shelf.class, shelfId);
            if (shelf != null) {
                session.delete(shelf);
                transaction.commit();
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

	// Increment the available stock of a shelf by 1
    public void incrementAvailableStock(UUID shelfId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession().openSession()) {
            transaction = session.beginTransaction();
            Shelf shelf = session.get(Shelf.class, shelfId);
            if (shelf != null) {
               
                shelf.setAvailableStock(shelf.getAvailableStock() + 1);                
                session.update(shelf);
                transaction.commit();
            } else {
                System.out.println("Shelf with ID " + shelfId + " not found.");
            }

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

}
