package dao;

import models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;  // Ensure HibernateUtil provides session factory management
import util.PasswordUtil;

import java.util.List;
import java.util.UUID;

public class UserDao {
    private static final SessionFactory sessionFactory = HibernateUtil.getSession(); // Initialize once for all instances

    // Check if a user exists by username
    public boolean checkIfUserExists(String username) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT COUNT(u) FROM User u WHERE u.userName = :username";
            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("username", username);
            return query.getSingleResult() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Create a new user
    public void createUser(User user) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    // Update user information
    public void updateUser(User user) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    // Select a user by their UUID
    public User selectUser(UUID userId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(User.class, userId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Soft delete a user by setting isDeleted to true
    public void softDeleteUser(UUID userId) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, userId);
            if (user != null) {
                user.setDeleted(true); // Assuming your User model has a setDeleted method
                session.update(user);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    // Count the number of users who are not deleted
    public long countUsers() {
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT COUNT(u) FROM User u WHERE u.isDeleted = false";
            Query<Long> query = session.createQuery(hql, Long.class);
            return query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // List all users who are not deleted
    public List<User> listAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM User u WHERE u.isDeleted = false";
            Query<User> query = session.createQuery(hql, User.class);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Authenticate a user with hashed password
    public User authenticateUser(String username, String password) {
        String hashedPassword = PasswordUtil.hashPassword(password);

        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM User u WHERE u.userName = :username AND u.password = :password AND u.isDeleted = false";
            Query<User> query = session.createQuery(hql, User.class);
            query.setParameter("username", username);
            query.setParameter("password", hashedPassword);

            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
