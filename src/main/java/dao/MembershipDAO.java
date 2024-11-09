package dao;

import models.Membership;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.List;
import java.util.UUID;

public class MembershipDAO {

	public boolean checkIfExists(UUID membershipId) {
		try (Session session = HibernateUtil.getSession().openSession()) {
			Membership membership = session.get(Membership.class, membershipId);
			return membership != null;
		}
	}

	public void create(Membership membership) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSession().openSession()) {
			transaction = session.beginTransaction();
			session.save(membership);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
	}

	public void update(Membership membership) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSession().openSession()) {
			transaction = session.beginTransaction();
			session.update(membership);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
	}

	public void softDelete(UUID membershipId) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSession().openSession()) {
			transaction = session.beginTransaction();
			Membership membership = session.get(Membership.class, membershipId);
			if (membership != null) {
				// Assuming you want to set the status to REJECTED as soft delete
				membership.setMembershipStatus(Membership.Status.REJECTED);
				session.update(membership);
			}
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
	}

	public Membership selectOne(UUID membershipId) {
		try (Session session = HibernateUtil.getSession().openSession()) {
			return session.get(Membership.class, membershipId);
		}
	}

	public List<Membership> displayAll() {
		try (Session session = HibernateUtil.getSession().openSession()) {
			Query<Membership> query = session.createQuery("FROM Membership", Membership.class);
			return query.getResultList();
		}
	}

	public long countMembers() {
		try (Session session = HibernateUtil.getSession().openSession()) {
			Query<Long> query = session.createQuery("SELECT COUNT(m) FROM Membership m", Long.class);
			return query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public Membership selectByUserId(UUID userId) {
		try (Session session = HibernateUtil.getSession().openSession()) {
			Query<Membership> query = session.createQuery("FROM Membership m WHERE m.reader.personId = :userId",
					Membership.class);
			query.setParameter("userId", userId);
			return query.uniqueResult();
		}
	}

	public void updateApprovedStatus(UUID membershipId) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSession().openSession()) {
			transaction = session.beginTransaction();
			Membership membership = session.get(Membership.class, membershipId);
			if (membership != null) {
				membership.setMembershipStatus(Membership.Status.APPROVED);
				session.update(membership);
			} else {
				System.out.println("Membership with ID " + membershipId + " not found.");
			}
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}
	
	public void updateRejectedStatus(UUID membershipId) {
	    Transaction transaction = null;
	    try (Session session = HibernateUtil.getSession().openSession()) {
	        transaction = session.beginTransaction();
	        Membership membership = session.get(Membership.class, membershipId);
	        if (membership != null) {
	            membership.setMembershipStatus(Membership.Status.REJECTED);
	            session.update(membership);
	        } else {
	            System.out.println("Membership with ID " + membershipId + " not found.");
	        }
	        transaction.commit();
	    } catch (Exception e) {
	        if (transaction != null) {
	            transaction.rollback();
	        }
	        e.printStackTrace();
	    }
	}
	
	public Membership.Status getMembershipStatus(UUID readerId) {
	    try (Session session = HibernateUtil.getSession().openSession()) {
	        System.out.println("Executing query for readerId: " + readerId);

	       
	        Query<Membership.Status> query = session.createQuery(
	            "SELECT m.membershipStatus FROM Membership m WHERE m.reader.personId = :readerId", 
	            Membership.Status.class
	        );
	        query.setParameter("readerId", readerId);

	        Membership.Status status = query.uniqueResult(); 
	        System.out.println("Fetched status: " + status);  
	        return status;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;  
	    }
	}



	}

	


