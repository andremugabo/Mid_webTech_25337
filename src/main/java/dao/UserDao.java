package dao;

import models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.List;
import java.util.UUID;

public class UserDao {
	private final SessionFactory sessionFactory;

	public UserDao() {
		this.sessionFactory = HibernateUtil.getSession();
	}

	// Check if user exists by username
	public boolean checkIfUserExists(String username) {
		Session session = sessionFactory.openSession();
		try {
			String hql = "SELECT COUNT(u) FROM User u WHERE u.userName = :username";
			Query<Long> query = session.createQuery(hql, Long.class);
			query.setParameter("username", username);
			return query.uniqueResult() > 0;
		} finally {
			session.close();
		}
	}

	// Create a new user
	public void createUser(User user) {
		Transaction transaction = null;
		Session session = sessionFactory.openSession();
		try {
			transaction = session.beginTransaction();
			session.save(user);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	// Update an existing user
	public void updateUser(User user) {
		Transaction transaction = null;
		Session session = sessionFactory.openSession();
		try {
			transaction = session.beginTransaction();
			session.update(user);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	// Select a user by ID
	public User selectUser(UUID userId) {
		Session session = sessionFactory.openSession();
		User user = null;
		try {
			user = session.get(User.class, userId);
		} finally {
			session.close();
		}
		return user;
	}

	// Soft delete a user by ID

	public void softDeleteUser(UUID userId) {
		Transaction transaction = null;
		Session session = sessionFactory.openSession();
		try {
			transaction = session.beginTransaction();
			User user = session.get(User.class, userId);
			if (user != null) {
				user.setDeleted(true);
				session.update(user);
			}
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	// Count total users
	public long countUsers() {
		Session session = sessionFactory.openSession();
		try {
			String hql = "SELECT COUNT(u) FROM User u WHERE u.isDeleted = false";
			Query<Long> query = session.createQuery(hql, Long.class);
			return query.uniqueResult();
		} finally {
			session.close();
		}
	}

	// List all users (if needed)
	public List<User> listAllUsers() {
		Session session = sessionFactory.openSession();
		List<User> users;
		try {
			String hql = "FROM User u WHERE u.isDeleted = false";
			Query<User> query = session.createQuery(hql, User.class);
			users = query.list();
		} finally {
			session.close();
		}
		return users;
	}
	//user authentication
	public User authenticateUser(String username, String password) {
		Session session = sessionFactory.openSession();
		User user = null;
		try {
			String hql = "FROM User u WHERE u.userName = :username AND u.password = :password AND u.isDeleted = false";
			Query<User> query = session.createQuery(hql, User.class);
			query.setParameter("username", username);
			query.setParameter("password", password);
			user = query.uniqueResult();
		} finally {
			session.close();
		}
		return user;
	}

}
