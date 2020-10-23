package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.List;
import java.util.ArrayList;

public class UserDaoJDBCImpl implements UserDao {

    private Connection connection;

    public UserDaoJDBCImpl() {
        connection = Util.getConnection();
    }

    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Users(\n" +
                "id INT NOT NULL AUTO_INCREMENT,\n" +
                "name TINYTEXT,\n" +
                "lastName TINYTEXT,\n" +
                "age TINYINT,\n" +
                "PRIMARY KEY (id)\n" +
                "); ";

        try {
            Statement statement = connection.createStatement();
            statement.execute(sql);
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void dropUsersTable() {
        try {
            Statement statement = connection.createStatement();
            statement.execute("DROP TABLE Users;");
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            PreparedStatement statement = null;
            String sql = "INSERT INTO Users(name, lastName, age) VALUES(?, ?, ?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.printf("User с именем %1s %2s добавлен в базу данных%n", name, lastName);
    }

    public void removeUserById(long id) {
        try {
            PreparedStatement statement = null;
            String sql = "delete from Users where id = ?;";
            statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        ResultSet res = null;
        try {
            String query = "select * from users";
            Statement statement = connection.createStatement();
            res = statement.executeQuery(query);
            while(res.next()) {
                User user = new User(res.getLong(1), res.getString(2), res.getString(3), res.getByte(4));
                user.setId(res.getLong(1));
                list.add(user);
                System.out.println(user.toString());
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public void cleanUsersTable() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("TRUNCATE TABLE Users");
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
