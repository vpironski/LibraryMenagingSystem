import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Reader extends User{
//    private Book firstBook;
    private  List<Book> bookList = new ArrayList<>();
    public Reader(int id, String name, String key, int isAdmin, double taxes) {
        super(id, name, key, isAdmin, taxes);
    }

//    public ArrayList<Book> yourBooks(){
//        firstBook = new Book();
//        bookList.add(firstBook);
//        return bookList;
public List<Book> yourBookList() {

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
            int takenBy = resultSet.getInt("TakenBy");

            Book book = new Book(bookId, bookName, author, status, takenBy);
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

    return bookList;
}
    public void takeBook(Book book){
        if(book.getStatus() != 1){
            book.setStatus(1);
            book.setTakenBy(getName());
            System.out.println("You have taken "+ book.getName());
            bookList.add(book);
        }
        else{
            System.out.println("This book is already taken");
        }
    }

    public void returnBook(Book book){
        if(book.getStatus() == 1){
            book.setStatus(0);
            book.setTakenBy(null);
            System.out.println("You have returned " + book.getName());
            bookList.remove(book);
        }
        else if(!bookList.contains(book)){
            System.out.println("This book isnt in your book list");
        }
    }

}
