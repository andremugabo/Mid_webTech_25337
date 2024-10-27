package util;

import java.util.Properties;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import models.*;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HibernateUtil {
    private static final Logger logger = LoggerFactory.getLogger(HibernateUtil.class);
    private static SessionFactory sessionFactory;

    public static SessionFactory getSession() {
        if (sessionFactory == null) {
            synchronized (HibernateUtil.class) {
                if (sessionFactory == null) { 
                    try {
                        Configuration configuration = new Configuration();
                        Properties settings = new Properties();

                        settings.setProperty(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                        settings.setProperty(Environment.URL, "jdbc:mysql://localhost:3306/auca_library_db");
                        settings.setProperty(Environment.USER, "root");
                        settings.setProperty(Environment.PASS, "");
                        settings.setProperty(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
                        settings.setProperty(Environment.HBM2DDL_AUTO, "create"); 
                        settings.setProperty(Environment.SHOW_SQL, "true");

                        configuration.setProperties(settings);

                        // Add all annotated classes
                        configuration.addAnnotatedClass(Location.class);
                        configuration.addAnnotatedClass(Person.class);
                        configuration.addAnnotatedClass(User.class);
                        configuration.addAnnotatedClass(Borrower.class);
                        configuration.addAnnotatedClass(Book.class);
                        configuration.addAnnotatedClass(Shelf.class);
                        configuration.addAnnotatedClass(Room.class);
                        configuration.addAnnotatedClass(Membership.class);
                        configuration.addAnnotatedClass(MembershipType.class);

                        sessionFactory = configuration.buildSessionFactory();
                    } catch (Throwable ex) {
                        logger.error("Initial SessionFactory creation failed: " + ex.getMessage(), ex);
                        throw new ExceptionInInitializerError(ex);
                    }
                }
            }
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
