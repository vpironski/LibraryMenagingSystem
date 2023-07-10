package personas;

import java.sql.*;
import java.text.*;
import java.time.LocalDate;
import java.util.*;

public class User {
    private int id;
    private String name;
    private String key;
    private int isAdmin;
    private double taxes;

    public User(int id, String name, String key, int isAdmin, double taxes) {
        this.id = id;
        this.name = name;
        this.key = key;
        this.isAdmin = isAdmin;
        this.taxes = taxes;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public double getTaxes() {
        return taxes;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

    public void setTaxes(double taxes) {
        this.taxes = taxes;
    }

    public void viewBooks() throws SQLException {
        DatabaseConnection.openConnection();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.createStatement();

            String query = "SELECT b.idBooks, b.Name, b.Author, b.Status,b.returnDate, b.TakenBy, u.Name AS UserName " +
                    "FROM Books b " +
                    "LEFT JOIN Users u ON b.TakenBy = u.idUsers";
            resultSet = statement.executeQuery(query);

            System.out.println("Books Table:");
            while (resultSet.next()) {
                int bookId = resultSet.getInt("idBooks");
                String bookName = resultSet.getString("Name");
                String author = resultSet.getString("Author");
                int status = resultSet.getInt("Status");
                java.sql.Date returnDate = resultSet.getDate("returnDate");
                int takenBy = resultSet.getInt("TakenBy");
                String userName = resultSet.getString("UserName");

                System.out.println("books.Book ID: " + bookId +
                        ", Name: " + bookName +
                        ", Author: " + author +
                        ", Status: " + status +
                        ", returnDate: " + returnDate +
                        ", Taken By: " + takenBy +
                        ", personas.User Name: " + userName);
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
    public List<Book> yourBookList(List<Book> bookList) throws SQLException {
        DatabaseConnection.openConnection();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.createStatement();

            String query = "SELECT * FROM Books WHERE TakenBy = " + getId();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int bookId = resultSet.getInt("idBooks");
                String bookName = resultSet.getString("Name");
                String author = resultSet.getString("Author");
                int status = resultSet.getInt("Status");
                java.sql.Date returnDate = resultSet.getDate("returnDate");
                int takenBy = resultSet.getInt("TakenBy");

                Book book = new Book(bookId, bookName, author, status, returnDate, takenBy);
                bookList.add(book);
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
        return bookList;
    }
    public void takeBook(Book book) throws SQLException {
        DatabaseConnection.openConnection();
        Connection connection = null;
        PreparedStatement checkStatement = null;
        PreparedStatement takeStatement = null;

        try {
            connection = DatabaseConnection.getConnection();

            String checkQuery = "SELECT Status, TakenBy FROM Books WHERE Name = ? AND Author = ?";
            checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setString(1, book.getName());
            checkStatement.setString(2, book.getAuthor());
            ResultSet checkResult = checkStatement.executeQuery();

            if (checkResult.next()) {
                int status = checkResult.getInt("Status");
                int takenBy = checkResult.getInt("TakenBy");

                if (status == 0 && takenBy == 0) {

                    String takeQuery = "UPDATE Books SET Status = ?, TakenBy = ?, returnDate = ? WHERE Name = ? AND Author = ?";
                    takeStatement = connection.prepareStatement(takeQuery);
                    takeStatement.setInt(1, 1);
                    takeStatement.setInt(2, getId());
                    LocalDate returnDateDate = LocalDate.now().plusMonths(3);
                    takeStatement.setDate(3, java.sql.Date.valueOf(returnDateDate));
                    takeStatement.setString(4, book.getName());
                    takeStatement.setString(5, book.getAuthor());
                    int rowsAffected = takeStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        System.out.println("books.Book taken successfully.");
                    } else {
                        System.out.println("Failed to take the book.");
                    }
                } else {
                    System.out.println("The book is already taken by another user.");
                }
            } else {
                System.out.println("Invalid book name or author.");
            }
        } catch (SQLException e) {
            System.err.println("Error executing the query: " + e.getMessage());
        } finally {
            if (checkStatement != null) {
                try {
                    checkStatement.close();
                } catch (SQLException e) {
                    System.err.println("Error closing the prepared statement: " + e.getMessage());
                }
            }
            if (takeStatement != null) {
                try {
                    takeStatement.close();
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

    public void returnBook(Book book) throws SQLException {
        DatabaseConnection.openConnection();
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DatabaseConnection.getConnection();


            String query = "SELECT TakenBy FROM Books WHERE Name = ? AND Author = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, book.getName());
            statement.setString(2, book.getAuthor());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int takenBy = resultSet.getInt("TakenBy");

                if (takenBy == getId()) {
                    // Update the book's status and details
                    String updateQuery = "UPDATE Books SET Status = ?, TakenBy = ?, returnDate = ? WHERE Name = ? AND Author = ?";
                    statement = connection.prepareStatement(updateQuery);
                    statement.setInt(1, 0);
                    statement.setNull(2, 0);
                    statement.setNull(3, java.sql.Types.DATE);
                    statement.setString(4, book.getName());
                    statement.setString(5, book.getAuthor());
                    int rowsAffected = statement.executeUpdate();

                    if (rowsAffected > 0) {
                        System.out.println("books.Book returned successfully.");
                    } else {
                        System.out.println("Failed to return the book.");
                    }
                } else {
                    System.out.println("You cannot return a book that is taken by another user.");
                }
            } else {
                System.out.println("Invalid book name or author.");
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

    @Override
    public String toString() {
        return "personas.User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", key='" + key + '\'' +
                ", isAdmin=" + isAdmin +
                ", taxes=" + taxes +
                '}';
    }
}


