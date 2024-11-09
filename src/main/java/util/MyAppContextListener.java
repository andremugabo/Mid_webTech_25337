//package util;
//
//import javax.servlet.ServletContextEvent;
//import javax.servlet.ServletContextListener;
//import javax.servlet.annotation.WebServlet;
//
//import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
//
//@WebServlet
//public class MyAppContextListener implements ServletContextListener {
//    @Override
//    public void contextDestroyed(ServletContextEvent sce) {
//        try {
//            // Unregister the JDBC driver
//            java.sql.DriverManager.deregisterDriver(java.sql.DriverManager.getDrivers().nextElement());
//            // Stop MySQL cleanup thread
//            AbandonedConnectionCleanupThread.checkedShutdown();
//        } catch (Exception e) {
//            System.err.println("Failed to deregister JDBC driver or stop MySQL cleanup thread.");
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void contextInitialized(ServletContextEvent sce) {}
//}
