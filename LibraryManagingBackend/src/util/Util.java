package util;

import books.*;
import personas.*;


import java.sql.*;
public class Util {
    private static int id;
    private static String name = "Vihren Pironski";
    private static String key = "12345";
    private static int isAdmin;
    private static double taxes;

    public static int checkAdmin(String name, String key) throws Exception {
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
                if(resultSet.getInt("isAdmin") == 1){
                    return 1;
                }
                else if(resultSet.getInt("isAdmin") == 0){
                    return 0;
                }

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
        return 2;
    }
    public static User populateUserInfo(String name, String key) throws SQLException {
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
                if(resultSet.getInt("isAdmin") == 1){
                    id = resultSet.getInt("idUsers");
                    isAdmin = resultSet.getInt("IsAdmin");
                    taxes = resultSet.getDouble("Taxes");
                    user = new Admin(id,name,key,isAdmin,taxes);
                }
                else if(resultSet.getInt("isAdmin") == 0){
                    id = resultSet.getInt("idUsers");
                    isAdmin = resultSet.getInt("IsAdmin");
                    taxes = resultSet.getDouble("Taxes");
                    user = new Reader(id,name,key,isAdmin,taxes);
                }
                else{
                    return null;
                }


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
    public static User login(String username, String key) throws SQLException {
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

    public static Reader createAccount(String name, String key) throws SQLException {
        DatabaseConnection.openConnection();
        Reader newReader = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getConnection();
            String query = "INSERT INTO Users (Name, `Key`, IsAdmin, Taxes) VALUES (?, ?, 0, 0)";
            statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, name);
            statement.setString(2, key);

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    newReader = new Reader(name, key, 0, 0.0);
                }
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
        return newReader;
    }
    public static void quit(){
        System.out.println("The program has been terminated");
        System.exit(0);

    }

//    public void userInput(){
//        Scanner scan = new Scanner(System.in);
//        while(true){
//            System.out.println();
//            System.out.println("*******************************");
//            System.out.println("Enter your user info (Enter C to Create new user): ");
//
//            char response = Character.toUpperCase(scan.nextLine().charAt(0));
//
//            switch (response){
//                case'N':
//                    break;
//                case'G':
//                    break;
//                case'E':
//                    break;
//                case'D':
//                    break;
//                case'Q':
//                    quit();
//                    break;
//                case'P':
//                    break;
//
//                default:
//                    System.out.println("Not a valid command!");
//            }
//        }
//    }


}
