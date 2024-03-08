package repository;

import lombok.NoArgsConstructor;
import model.User;
import utils.PropertyUtils;
import utils.SQLUtils;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class UserSystemRepository {
    private Connection connection;
   private  final Properties properties;
   public  UserSystemRepository(){
       properties = PropertyUtils.loadProperty();
   }
    public static final String getAllUserSql = """
            select * from user;
            """;
    public static final String loginUserSql = """
            select * from "user" where username = ? and password = ?;
            """;

    private Connection startDatabaseConnection() throws SQLException {
        return DriverManager.getConnection(
                properties.getProperty("DB_URL"),
                properties.getProperty("USERNAME"),
                properties.getProperty("PASSWORD")
        );
    }
    public boolean login(String username, String password) {
        try (PreparedStatement ps = connection.prepareStatement(SQLUtils.SystemUserSQL.loginUser)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void handleLogin() {
        Scanner scanner = new Scanner(System.in);
        boolean loginSuccessful = false;
        while (!loginSuccessful) {
            System.out.println("Enter username: ");
            String username = scanner.nextLine();
            System.out.println("Enter password: ");
            String password = scanner.nextLine();

            loginSuccessful = login(username, password);
            if (loginSuccessful) {
                System.out.println("Login successful. Welcome to the Person system.");
                // Proceed with Person system operations
            } else {
                System.out.println("Login failed. Please try again.");
            }
        }
    }


    public  boolean loginUser(String username,String password) {
        try (Connection connection = startDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(loginUserSql)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return true;
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error checking user credentials.");
            ex.printStackTrace();
        }

        return false; // Default to invalid
    }
}
