package personas;

import books.*;
import util.*;


import java.sql.*;
import java.util.*;

public class Admin extends User{
    public Admin(int id, String name, String key, int isAdmin, double taxes) {
        super(id, name, key, isAdmin, taxes);
    }

    public void viewUsers() throws SQLException {
        DatabaseConnection.openConnection();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.createStatement();

            String query = "SELECT * FROM Users";
            resultSet = statement.executeQuery(query);

            System.out.println("Users Table:");
            while (resultSet.next()) {
                int id = resultSet.getInt("idUsers");
                String name = resultSet.getString("Name");
                String key = resultSet.getString("Key");
                int isAdmin = resultSet.getInt("IsAdmin");
                double taxes = resultSet.getDouble("Taxes");

                System.out.println("ID: " + id + ", Name: " + name + ", Key: " + key + ", IsAdmin: " + isAdmin + ", Taxes: " + taxes);
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
    }

    public void addBook() throws SQLException {
        DatabaseConnection.openConnection();
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DatabaseConnection.getConnection();

            // Read book details from terminal inputs
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter book name: ");
            String bookName = scanner.nextLine();
            System.out.print("Enter book author: ");
            String bookAuthor = scanner.nextLine();

            // Create the Book object
            Book book = new Book(bookName, bookAuthor);

            String query = "INSERT INTO Books (Name, Author, Status) VALUES (?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, book.getName());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setInt(3, book.getStatus());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Book '" + bookName + "' added successfully.");
            } else {
                System.out.println("Failed to add book.");
            }
        } catch (SQLException e) {
            System.err.println("Error executing the query: " + e.getMessage());
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    System.err.println("Error closing the prepared statement: " + e.getMessage());
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
    }
    public void removeBook() throws SQLException {
        DatabaseConnection.openConnection();
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DatabaseConnection.getConnection();

            // Read book details from terminal inputs
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter book name: ");
            String bookName = scanner.nextLine();
            System.out.print("Enter book author: ");
            String bookAuthor = scanner.nextLine();

            // Create the Book object
            Book book = new Book(bookName, bookAuthor);

            String query = "DELETE FROM Books WHERE Name = ? AND Author = ?";
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, book.getName());
            preparedStatement.setString(2, book.getAuthor());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Book '" + bookName + "' deleted successfully.");
            } else {
                System.out.println("Failed to delete book.");
            }
        } catch (SQLException e) {
            System.err.println("Error executing the query: " + e.getMessage());
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    System.err.println("Error closing the prepared statement: " + e.getMessage());
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
    }




    public void addAccount() throws SQLException {
        DatabaseConnection.openConnection();
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DatabaseConnection.getConnection();

            // Read user details from terminal inputs
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter user name: ");
            String userName = scanner.nextLine();
            System.out.print("Enter user key: ");
            String userKey = scanner.nextLine();
            System.out.println("Enter user admin status (1/0): ");
            int adminStatus = scanner.nextInt();

            // Create the Reader object
            User user = new User(userName, userKey, adminStatus, 0.0);

            // Insert a new user account into the Users table
            String query = "INSERT INTO Users (Name, `Key`, IsAdmin, Taxes) VALUES (?, ?, ?, ?)";
            statement = connection.prepareStatement(query);
            statement.setString(1, user.getName());
            statement.setString(2, user.getKey());
            statement.setInt(3, user.getIsAdmin());
            statement.setDouble(4, user.getTaxes());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User account added successfully.");
            } else {
                System.out.println("Failed to add the user account.");
            }
        } catch (SQLException e) {
            System.err.println("Error executing the query: " + e.getMessage());
        } finally {
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
    }

    public void removeAccount(User user) throws SQLException {
        DatabaseConnection.openConnection();
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DatabaseConnection.getConnection();

            // Delete the user account from the Users table
            String query = "DELETE FROM Users WHERE idUsers = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, user.getId());
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("personas.User account removed successfully.");
            } else {
                System.out.println("Failed to remove the user account.");
            }
        } catch (SQLException e) {
            System.err.println("Error executing the query: " + e.getMessage());
        } finally {
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
    }

    public void promoteReader(Reader reader) throws SQLException {
        DatabaseConnection.openConnection();
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DatabaseConnection.getConnection();


            if (reader != null) {
                String query = "UPDATE Users SET IsAdmin = ? WHERE idUsers = ?";
                statement = connection.prepareStatement(query);
                statement.setInt(1, 1);
                statement.setInt(2, reader.getId());

                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    reader.setIsAdmin(1);
                    System.out.println("Promoted Reader '" + reader.getName() + "' to Admin.");
                } else {
                    System.out.println("Failed to promote Reader to Admin.");
                }
            } else {
                System.out.println("Reader with the given name and key does not exist.");
            }
        } catch (SQLException e) {
            System.err.println("Error executing the query: " + e.getMessage());
        } finally {
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
    }

    public void resetTaxes(User user) throws SQLException {
        DatabaseConnection.openConnection();
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DatabaseConnection.getConnection();

            // Update the user's taxes to zero
            String query = "UPDATE Users SET Taxes = 0 WHERE idUsers = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, user.getId());
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Taxes reset successfully for user: " + user.getName());
            } else {
                System.out.println("Failed to reset taxes for user: " + user.getName());
            }
        } catch (SQLException e) {
            System.err.println("Error executing the query: " + e.getMessage());
        } finally {
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
    }

}
