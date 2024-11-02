package dao;

import models.Membership;
import models.User;
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
            if (transaction != null) transaction.rollback();
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
            if (transaction != null) transaction.rollback();
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
            if (transaction != null) transaction.rollback();
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

}
