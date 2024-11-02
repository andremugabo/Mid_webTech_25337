package dao;

import models.MembershipType;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.List;
import java.util.UUID;

public class MembershipTypeDAO {

    public boolean checkIfExists(UUID membershipTypeId) {
        try (Session session = HibernateUtil.getSession().openSession()) {
            MembershipType membershipType = session.get(MembershipType.class, membershipTypeId);
            return membershipType != null;
        }
    }

    public void create(MembershipType membershipType) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession().openSession()) {
            transaction = session.beginTransaction();
            session.save(membershipType);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void update(MembershipType membershipType) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession().openSession()) {
            transaction = session.beginTransaction();
            session.update(membershipType);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void softDelete(UUID membershipTypeId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession().openSession()) {
            transaction = session.beginTransaction();
            MembershipType membershipType = session.get(MembershipType.class, membershipTypeId);
            if (membershipType != null) {
                
                session.delete(membershipType); 
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public MembershipType selectOne(UUID membershipTypeId) {
        try (Session session = HibernateUtil.getSession().openSession()) {
            return session.get(MembershipType.class, membershipTypeId);
        }
    }

    public List<MembershipType> displayAll() {
        try (Session session = HibernateUtil.getSession().openSession()) {
            Query<MembershipType> query = session.createQuery("FROM MembershipType", MembershipType.class);
            return query.getResultList(); 
        }
    }
}
