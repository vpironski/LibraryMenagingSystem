package util;

import personas.Admin;
import personas.Reader;
import personas.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Util {
    private static int id;
    private static String name = "Vihren Pironski";
    private static String key = "12345";
    private static int isAdmin;
    private static double taxes;
    private static User populateUserInfo(String name, String key) throws SQLException {
        DatabaseConnection.openConnection();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.createStatement();

            String query = "SELECT idUsers, IsAdmin, Taxes FROM Users WHERE `Name` = '" + name + "' AND `Key` = '" + key + "'";
            resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                id = resultSet.getInt("idUsers");
                isAdmin = resultSet.getInt("IsAdmin");
                taxes = resultSet.getDouble("Taxes");
                user = new User(id,name,key,isAdmin,taxes);

            }
            else {
                System.out.println("This profile doesn't exist.");
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Error executing the query: " + e.getMessage());
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    System.err.println("Error closing the result set: " + e.getMessage());
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.err.println("Error closing the statement: " + e.getMessage());
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Error closing the connection: " + e.getMessage());
                }
            }
        }
        DatabaseConnection.closeConnection();
        return user;
    }
    public User login(String username, String key) throws SQLException {
        DatabaseConnection.openConnection();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        User user = null;

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.createStatement();

            String query = "SELECT * FROM Users WHERE Name = '" + username + "' AND `Key` = '" + key + "'";
            resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                int id = resultSet.getInt("idUsers");
                String name = resultSet.getString("Name");
                String userKey = resultSet.getString("Key");
                int isAdmin = resultSet.getInt("IsAdmin");
                double taxes = resultSet.getDouble("Taxes");

                // Create the appropriate User object based on the isAdmin value
                if (isAdmin == 0) {
                    user = new Reader(id, name, userKey, isAdmin, taxes);
                    // Additional actions specific to a Reader user
                } else if (isAdmin == 1) {
                    user = new Admin(id, name, userKey, isAdmin, taxes);
                    // Additional actions specific to an Admin user
                }
            } else {
                System.out.println("User does not exist.");
            }
        } catch (SQLException e) {
            System.err.println("Error executing the query: " + e.getMessage());
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    System.err.println("Error closing the result set: " + e.getMessage());
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.err.println("Error closing the statement: " + e.getMessage());
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Error closing the connection: " + e.getMessage());
                }
            }
        }
        DatabaseConnection.closeConnection();
        return user;
    }
}
