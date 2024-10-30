package dao;

import models.Location;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.List;
import java.util.UUID;

public class LocationDao {
    private final SessionFactory sessionFactory;

    public LocationDao() {
        this.sessionFactory = HibernateUtil.getSession();
    }

    // Create a new location
    public void createLocation(Location location) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(location);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    // Update an existing location
    public void updateLocation(Location location) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(location);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    // Select a location by ID
    public Location selectLocation(UUID locationId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Location.class, locationId);
        }
    }

    // Soft delete a location by ID
    public void softDeleteLocation(UUID locationId) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Location location = session.get(Location.class, locationId);
            if (location != null) {
                location.setDeleted(true); // Set the isDeleted flag
                session.update(location);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    // Check if location exists by ID
    public boolean existsById(UUID locationId) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT COUNT(l) FROM Location l WHERE l.locationId = :locationId AND l.deleted = false";
            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("locationId", locationId);
            return query.uniqueResult() > 0;
        }
    }

    // List all locations (excluding deleted ones)
    public List<Location> listAllLocations() {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Location l WHERE l.deleted = false";
            Query<Location> query = session.createQuery(hql, Location.class);
            return query.list();
        }
    }

    // Get child locations of a given parent location
    public List<Location> getChildLocations(UUID parentId) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Location l WHERE l.parentLocation.locationId = :parentId AND l.deleted = false";
            Query<Location> query = session.createQuery(hql, Location.class);
            query.setParameter("parentId", parentId);
            return query.list();
        }
    }

    // Get the parent location of a given location
    public Location getParentLocation(UUID locationId) {
        try (Session session = sessionFactory.openSession()) {
            Location location = session.get(Location.class, locationId);
            return (location != null) ? location.getParentLocation() : null;
        }
    }
}
