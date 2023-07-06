import java.util.ArrayList;
import java.util.Arrays;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Main {
    private static List<Book> bookList = new ArrayList<>();
    private static boolean profileFlag = false;
    private static int id;
    private static String name = "Vihren Pironski";
//    private static String name = "Pesho";
    private static String key = "12345";
    private static int isAdmin;
    private static double taxes;
    private static void populateUserInfo(String name, String key) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.createStatement();

            String query = "SELECT idUsers, IsAdmin, Taxes FROM Users WHERE `Name` = '" + name + "' AND `Key` = '" + key + "'";
            resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                id = resultSet.getInt("idUsers");
                isAdmin = resultSet.getInt("IsAdmin");
                taxes = resultSet.getDouble("Taxes");

//                setId(id);
//                setIsAdmin(isAdmin);
//                setTaxes(taxes);
            }
            else {
                profileFlag = true;
            System.out.println("This profile doesn't exist.");
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

    public static void main(String[] args) {
        if(!profileFlag){
            populateUserInfo(name, key);
//            Admin testAdmin = new Admin(id,name,key,isAdmin,taxes);
//            System.out.println(testAdmin.toString());
//            testAdmin.viewUsers();
//            testAdmin.viewBooks();
            Reader testReader = new Reader(id,name,key,isAdmin,taxes);
            bookList =  testReader.yourBookList();
            System.out.println(bookList);

        }


    }
}
