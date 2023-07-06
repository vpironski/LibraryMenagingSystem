import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Admin extends User{
    public Admin(int id, String name, String key, int isAdmin, double taxes) {
        super(id, name, key, isAdmin, taxes);
    }

//    public Admin(){
//        super(2,"Vihren Pironski", "1234", 1,0.0);
//    }

    public void removeBook() {
    }
    public void addBook(){
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


}
