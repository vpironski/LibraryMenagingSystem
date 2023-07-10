import books.Book;
import personas.*;
import util.Util;


import java.util.ArrayList;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

//import static util.Util.populateUserInfo;

public class Main {
    private static List<Book> bookList = new ArrayList<>();
    
    public static void main(String[] args) throws SQLException {
        System.out.println("h");
//        Admin testAdmin = populateUserInfo(name, key);
        books.Book book = new books.Book("The Art of War","Sun Tzu");

//        personas.Reader reader = populateUserInfo("Slavi Pironski", "s1234");;
//
//        testAdmin.viewBooks();
//        testAdmin.viewUsers();




    }
}
