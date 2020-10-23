package jm.task.core.jdbc.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {

    private static Connection connection;
    private static final String URL = "jdbc:mysql://localhost:3306/task1?useUnicode=true&serverTimezone=UTC&useSSL=false";
    private static final String username = "root";
    private static final String password = "root";

    private static SessionFactory sessionFactory;

    private Util() { }

    public static Connection getConnection() {
        if (connection != null) {
            return connection;
        } else {
            try {
                Driver driver = new com.mysql.cj.jdbc.Driver();
                DriverManager.registerDriver(driver);
                connection = DriverManager.getConnection(URL, username, password);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return connection;
        }
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory != null) {
            return sessionFactory;
        } else {
            try {
                Properties properties = new Properties();
                properties.load(ClassLoader.getSystemClassLoader().getResourceAsStream("hibernate.properties"));

                Configuration configuration = new Configuration();
                //configuration.configure();// configures settings from hibernate.cfg.xml
                configuration.setProperties(properties);
                configuration.addAnnotatedClass(jm.task.core.jdbc.model.User.class);

                StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();
                serviceRegistryBuilder.applySettings(configuration.getProperties());
                ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                System.out.println("Get SessionFactory exception " + e.getMessage());
            }
            return sessionFactory;
        }
    }
}
