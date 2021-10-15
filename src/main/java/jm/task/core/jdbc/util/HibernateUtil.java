package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.util.Properties;


public class HibernateUtil {

    private static SessionFactory sessionFactory;
    private static Properties properties = new Properties();
    private static Configuration cfg = new Configuration();

    static {
        properties.setProperty(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
        properties.setProperty(Environment.HBM2DDL_AUTO, "update");
        properties.setProperty(Environment.DRIVER, "com.mysql.jdbc.Driver");
        properties.setProperty(Environment.USER, "root");
        properties.setProperty(Environment.PASS, "root");
        properties.setProperty(Environment.URL, "jdbc:mysql://localhost:3306/users?useSSL=false");

        cfg.setProperties(properties);
        cfg.addAnnotatedClass(User.class);

        sessionFactory = cfg.buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static Session getSession() {
        return sessionFactory.openSession();
    }
}
