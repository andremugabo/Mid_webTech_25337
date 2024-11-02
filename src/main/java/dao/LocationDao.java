package dao;

import models.Location;
import models.LocationType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.ArrayList;
import java.util.Collections;
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

			// Ensure parent location exists in the database
			if (location.getParentLocation() != null) {
				Location parent = session.get(Location.class, location.getParentLocation().getLocationId());
				if (parent == null) {
					throw new IllegalArgumentException("Parent location does not exist.");
				}
				location.setParentLocation(parent);
			}

			session.save(location);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
	}

	// Check if location exists by locationCode
	public boolean existsByLocationCode(String locationCode) {
		try (Session session = sessionFactory.openSession()) {
			String hql = "SELECT COUNT(l) FROM Location l WHERE l.locationCode = :locationCode AND l.deleted = false";
			Query<Long> query = session.createQuery(hql, Long.class);
			query.setParameter("locationCode", locationCode);
			return query.uniqueResult() > 0;
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
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
	}

	// Select a location by ID
	public Location selectLocation(UUID locationId) {
		try (Session session = sessionFactory.openSession()) {
			return session.createQuery("FROM Location l WHERE l.locationId = :locationId AND l.deleted = false",
					Location.class).setParameter("locationId", locationId).uniqueResult();
		}
	}

	// Soft delete a location by ID
	public void softDeleteLocation(UUID locationId) {
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			Location location = session.get(Location.class, locationId);
			if (location != null) {
				location.setDeleted(true);
				session.update(location);
			}
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
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
		List<Location> locations = new ArrayList<>();

		try (Session session = sessionFactory.openSession()) {
			
			System.out.println("Opening session to retrieve Location records...");

			String hql = "FROM Location l WHERE l.deleted = false";
			Query<Location> query = session.createQuery(hql, Location.class);

			
			locations = query.list();

			
			System.out.println("Number of Locations retrieved: " + locations.size());

		} catch (Exception e) {
			
			System.err.println("An error occurred while retrieving Locations: " + e.getMessage());
			e.printStackTrace();
		}

		return locations;
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

	public Location getParentLocation(UUID locationId) {
		try (Session session = sessionFactory.openSession()) {
			Location location = session.get(Location.class, locationId);
			return location != null ? location.getParentLocation() : null;
		}
	}

	public List<Location> getParentLocationsByType(String locationType) {
		List<Location> locations = Collections.emptyList();
		try (Session session = sessionFactory.openSession()) {
			String hql = "FROM Location l WHERE l.locationType = :locationType AND l.deleted = false";
			Query<Location> query = session.createQuery(hql, Location.class);

			LocationType locationTypeEnum = LocationType.valueOf(locationType.toUpperCase());
			query.setParameter("locationType", locationTypeEnum);

			locations = query.list();
			System.out.println(
					"Fetched locations for type: " + locationTypeEnum + " | Result count: " + locations.size());

		} catch (IllegalArgumentException e) {
			System.err.println("Invalid locationType: " + locationType);
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("Error fetching parent locations for type: " + locationType);
			e.printStackTrace();
		}
		return locations;
	}
}
