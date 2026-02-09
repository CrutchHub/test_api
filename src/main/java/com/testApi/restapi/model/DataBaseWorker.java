package com.testApi.restapi.model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

import java.sql.*;

@Repository
public class DataBaseWorker {
    private final DataSource dataSource;

    @Autowired
    public DataBaseWorker(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public User selectUserByLogin(String login) {
        String sql = "SELECT u.login, u.password, u.date, e.email " +
                "FROM \"Test\".\"User\" u " +
                "INNER JOIN \"Test\".\"Email\" e ON u.login = e.login " +
                "WHERE u.login = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, login);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    String foundedUserLogin = resultSet.getString("login");
                    String foundedPassword = resultSet.getString("password");
                    String foundedDate = resultSet.getString("date");
                    String foundedEmail = resultSet.getString("email");

                    return new User(foundedUserLogin, foundedPassword, foundedEmail, foundedDate);
                }
                else{
                    throw new SQLException("Пользователь с логином " + login + " не найден");
                }
            }

        } catch (SQLException e) {
            System.err.println("Ошибка поиска пользователя: " + e.getMessage());
            return null;
        }
    }

    public int insertUser(User user) {
        String sqlUser = "INSERT INTO \"Test\".\"User\" (login, password, date) VALUES (?, ?, ?::timestamp)";
        String sqlEmail = "INSERT INTO \"Test\".\"Email\" (login, email) VALUES (?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmtUser = connection.prepareStatement(sqlUser);
             PreparedStatement stmtEmail = connection.prepareStatement(sqlEmail)
        )
        {
            connection.setAutoCommit(false);
            int totalRows = 0;

            stmtUser.setString(1, user.getLogin());
            stmtUser.setString(2, user.getPassword());
            stmtUser.setString(3, user.getDate());
            totalRows += stmtUser.executeUpdate();

            stmtEmail.setString(1, user.getLogin());
            stmtEmail.setString(2, user.getEmail());
            totalRows += stmtEmail.executeUpdate();

            connection.commit();
            return totalRows;

        }
        catch (SQLException e) {
            System.err.println("Ошибка вставки: " + e.getMessage());
            return 0;
        }
    }
}