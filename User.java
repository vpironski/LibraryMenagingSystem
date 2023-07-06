import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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


    public void viewBooks(){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection =DatabaseConnection.getConnection();
            statement = connection.createStatement();

            String query = "SELECT b.idBooks, b.Name, b.Author, b.Status, b.TakenBy, u.Name AS UserName " +
                    "FROM Books b " +
                    "LEFT JOIN Users u ON b.TakenBy = u.idUsers";
            resultSet = statement.executeQuery(query);

            System.out.println("Books Table:");
            while (resultSet.next()) {
                int bookId = resultSet.getInt("idBooks");
                String bookName = resultSet.getString("Name");
                String author = resultSet.getString("Author");
                int status = resultSet.getInt("Status");
                int takenBy = resultSet.getInt("TakenBy");
                String userName = resultSet.getString("UserName");

                System.out.println("Book ID: " + bookId +
                        ", Name: " + bookName +
                        ", Author: " + author +
                        ", Status: " + status +
                        ", Taken By: " + takenBy +
                        ", User Name: " + userName);
            }
        } catch (SQLException e) {
            System.err.println("Error executing the query: " + e.getMessage());
        } finally {
            // Close resources in the reverse order of their creation
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", key='" + key + '\'' +
                ", isAdmin=" + isAdmin +
                ", taxes=" + taxes +
                '}';
    }
}


