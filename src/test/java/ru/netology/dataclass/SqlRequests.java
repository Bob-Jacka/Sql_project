package ru.netology.dataclass;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlRequests {

    private static final String sqlUser = "user";
    private static final String sqlPassword = "password";

    @SneakyThrows
    public static User requestUser(DataHelper.AuthInfo info) {
        var runner = new QueryRunner();
        var userSQL = "SELECT * FROM users WHERE login = '" + info.getLogin() + "';";
        try (var connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/app", sqlUser, sqlPassword)) {
            return runner.query(connection, userSQL, new BeanHandler<>(User.class));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    public static String requestCode(User user) {
        var runner = new QueryRunner();
        var codeSQL = "SELECT code FROM auth_codes WHERE user_id = ?;";

        try (var connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/app", sqlUser, sqlPassword)) {
            return runner.query(connection, codeSQL, user.getId(), new ScalarHandler<>());
        }
    }

    @SneakyThrows
    public static void requestCreateUser(User user) {
        var dataSQL = "INSERT INTO users(id, login, password) VALUES (?, ?, ?);";
        try (var connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/app", sqlUser, sqlPassword);
             var data = connection.prepareStatement(dataSQL)) {
            data.setString(1, user.getId());
            data.setString(2, user.getLogin());
            data.setString(3, DataHelper.getCipheredPassword());
            data.executeUpdate();
        }
    }

    @SneakyThrows
    public static void requestDeleteUser(User user) {
        var runner = new QueryRunner();
        var delUserSQL = "DELETE FROM users WHERE id = ?;";
        var delAuthCodeSQL = "DELETE FROM auth_codes WHERE user_id = ?;";

        try (var connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/app", sqlUser, sqlPassword)) {
            if (user != null) {
                runner.update(connection, delAuthCodeSQL,
                        user.getId());
                runner.update(connection, delUserSQL,
                        user.getId());
            }
        }
    }
}
