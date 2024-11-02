package dao;

import models.Room;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;
import java.util.List;
import java.util.UUID;

public class RoomDAO {

    // Save a Room
    public void save(Room room) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession().openSession()) {
            transaction = session.beginTransaction();
            session.save(room);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Find a Room by ID
    public Room findById(UUID roomId) {
        try (Session session = HibernateUtil.getSession().openSession()) {
            return session.get(Room.class, roomId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Get all Rooms
    public List<Room> findAll() {
        try (Session session = HibernateUtil.getSession().openSession()) {
            return session.createQuery("from Room", Room.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Update an existing Room
    public void update(Room room) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession().openSession()) {
            transaction = session.beginTransaction();
            session.update(room);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Delete a Room by ID
    public void delete(UUID roomId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession().openSession()) {
            transaction = session.beginTransaction();
            Room room = session.get(Room.class, roomId);
            if (room != null) {
                session.delete(room);
                transaction.commit();
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
