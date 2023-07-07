import java.sql.*;
import java.text.*;
import java.util.*;

public class Admin extends User{
    public Admin(int id, String name, String key, int isAdmin, double taxes) {
        super(id, name, key, isAdmin, taxes);
    }


    public void removeBook(Book book) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DatabaseConnection.getConnection();
            String query = "DELETE FROM Books WHERE Name = ? AND Author = ?";
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, book.getName());
            preparedStatement.setString(2, book.getAuthor());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Book deleted successfully.");
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
    }
    public void addBook(Book book){
            Connection connection = null;
            PreparedStatement preparedStatement = null;

            try {
                connection = DatabaseConnection.getConnection();

                String query = "INSERT INTO Books (Name, Author, Status) VALUES (?, ?, ?)";
                preparedStatement = connection.prepareStatement(query);

                preparedStatement.setString(1, book.getName());
                preparedStatement.setString(2, book.getAuthor());
                preparedStatement.setInt(3, book.getStatus());


                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Book added successfully.");
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
    }


    public void viewUsers(){
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
    }


    public void resetTaxes(User user) {
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
    }

    public void addAccount(User user) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DatabaseConnection.getConnection();

            // Insert a new user account into the Users table
            String query = "INSERT INTO Users (idUsers, Name, `Key`, IsAdmin, Taxes) VALUES (?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(query);
            statement.setInt(1,user.getId());
            statement.setString(2, user.getName());
            statement.setString(3, user.getKey());
            statement.setInt(4, user.getIsAdmin());
            statement.setDouble(5, user.getTaxes());
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
    }

    public void removeAccount(User user) {
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
                System.out.println("User account removed successfully.");
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
    }
}
