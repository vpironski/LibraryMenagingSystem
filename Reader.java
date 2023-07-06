import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

public class Reader extends User{
//    private Book firstBook;
    private ArrayList<Book> bookList = new ArrayList<>();
    public Reader(int id, String name, String key, int isAdmin, double taxes) {
        super(id, name, key, isAdmin, taxes);
    }

//    public ArrayList<Book> yourBooks(){
//        firstBook = new Book();
//        bookList.add(firstBook);
//        return bookList;
    public void yourBooks(){
        Connection connection = DatabaseConnection.getConnection();
        try {
            Statement statement = connection.createStatement();;
            String query = "SELECT * FROM Books";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                System.out.println("Books Table:");
                String booksQuery = "SELECT * FROM Books" +
                        "WHERE ";
                ResultSet booksResult = statement.executeQuery(booksQuery);
                while (booksResult.next()) {
                    int id = booksResult.getInt("idBooks");
                    String name = booksResult.getString("Name");
                    String author = booksResult.getString("Author");
                    int status = booksResult.getInt("Status");
                    String takenBy = booksResult.getString("Taken By");

                    System.out.println("ID: " + id + ", Name: " + name + ", Author: " + author + ", Status: " + status + ", Taken By: " + takenBy);
                }
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.err.println("Error executing the query: " + e.getMessage());
        }


        DatabaseConnection.closeConnection();
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
