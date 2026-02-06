package com.testApi.restapi.model;

import org.yaml.snakeyaml.constructor.DuplicateKeyException;

import java.sql.*;

public class DataBaseWorker {
    private final String url;
    private final String user;
    private final String password;

    public DataBaseWorker(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public User selectUserByLogin(String login) {
        String sql = "SELECT u.login, u.password, u.date, e.email " +
                "FROM \"Test\".\"User\" u " +
                "INNER JOIN \"Test\".\"Email\" e ON u.login = e.login " +
                "WHERE u.login = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
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

        try (Connection connection = DriverManager.getConnection(url, this.user, password);
             PreparedStatement stmtUser = connection.prepareStatement(sqlUser);
             PreparedStatement stmtEmail = connection.prepareStatement(sqlEmail)
        )
        {
            int totalRows = 0;

            stmtUser.setString(1, user.getLogin());
            stmtUser.setString(2, user.getPassword());
            stmtUser.setString(3, user.getDate());
            totalRows += stmtUser.executeUpdate();

            stmtEmail.setString(1, user.getLogin());
            stmtEmail.setString(2, user.getEmail());
            totalRows += stmtEmail.executeUpdate();

            return totalRows;

        } catch (SQLException e) {
            System.err.println("Ошибка вставки: " + e.getMessage());
            return 0;
        }
    }
}