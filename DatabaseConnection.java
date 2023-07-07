import java.sql.* ;

public class DatabaseConnection {
    private static String url = "jdbc:mysql://localhost:3306/LibraryManagingSystem";
    private static String username = "root";
    private static String password = "";
    private static Connection connection;

    public static void openConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(url, username, password);
//            System.out.println("Connection to the database is successful!");
        }
    }
    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(url, username, password);
//                System.out.println("Connection to the database is successful!");
            } catch (SQLException e) {
                System.err.println("Error connecting to the database: " + e.getMessage());
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
//                System.out.println("Connection closed.");
            } catch (SQLException e) {
                System.err.println("Error closing the database connection: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {


        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connection to the database is successful!");

            System.out.println("Books Table:");
            Statement statement = connection.createStatement();
//            String booksQuery = "SELECT * FROM Books INNER JOIN Users ON Books.TakenBy=Users.idUsers";
            String booksQuery = "SELECT * FROM Books";
            ResultSet booksResult = statement.executeQuery(booksQuery);
            while (booksResult.next()) {
                int id = booksResult.getInt("idBooks");
                String name = booksResult.getString("Name");
                String author = booksResult.getString("Author");
                int status = booksResult.getInt("Status");
                String takenBy = booksResult.getString("Taken By");

                System.out.println("ID: " + id + ", Name: " + name + ", Author: " + author + ", Status: " + status + ", Taken By: " + takenBy);
            }

            System.out.println("Users Table:");
            String usersQuery = "SELECT * FROM Users";
            ResultSet usersResult = statement.executeQuery(usersQuery);
            while (usersResult.next()) {
                int userId = usersResult.getInt("idUsers");
                String name = usersResult.getString("Name");
                String key = usersResult.getString("Key");
                int isAdmin = usersResult.getInt("IsAdmin");
                float taxes = usersResult.getFloat("Taxes");

                System.out.println("User ID: " + userId + ", Name: " + name + ", Key: " + key + ", Is Admin: " + isAdmin + ", Taxes: " + taxes);
            }



            connection.close();
            System.out.println("Connection closed.");
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        }
    }
    
}