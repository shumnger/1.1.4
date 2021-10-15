package jm.task.core.jdbc.dao;

import java.sql.Connection;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {
    private final Connection connection = getConnection();

    public UserDaoJDBCImpl() {

    }

    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS USERS  (id BIGINT NOT NULL AUTO_INCREMENT, " +
                "name VARCHAR (45), LastName VARCHAR(45), age SMALLINT NOT NULL, PRIMARY KEY (id))";

        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS USERS");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        String sql = "INSERT INTO USERS (NAME, LASTNAME, AGE) VALUES (?, ?, ?)";

        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, name);
            pst.setString(2, lastName);
            pst.setByte(3, age);
            pst.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void removeUserById(long id) {

        String sql = "DELETE FROM USERS WHERE ID = ?";

        try (PreparedStatement pst = connection.prepareStatement("DELETE FROM USERS WHERE ID = ?")) {
            pst.setLong(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        try (Statement st = connection.createStatement(); ResultSet resultSet =
                st.executeQuery("SELECT ID, NAME, LASTNAME, AGE FROM USERS")) {
            while (resultSet.next()) {
                User tUser = new User();
                tUser.setId(resultSet.getLong("Id"));
                tUser.setName(resultSet.getString("Name"));
                tUser.setLastName(resultSet.getString("LastName"));
                tUser.setAge(resultSet.getByte("Age"));

                userList.add(tUser);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public void cleanUsersTable () {
        try (Statement statement = connection.createStatement()) {
            statement.execute("TRUNCATE TABLE USERS");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

