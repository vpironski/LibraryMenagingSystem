package books;

import java.util.Date;

public class Book {
    private int id;
    private String name;
    private String author;
    private java.sql.Date returnDate;
    private int status;
    private int takenBy;


    public Book(int bookId, String bookName, String author, int status, java.sql.Date returnDate, int takenBy) {
        this.id = bookId;
        this.name = bookName;
        this.author = author;
        this.status = status;
        this.returnDate = returnDate;
        this.takenBy = takenBy;
    }

    public Book(String bookName, String author) {
        this.name = bookName;
        this.author = author;
        this.status = 0;
    }


    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public Date getDueReturn() {
        return returnDate;
    }

    public int getStatus() {
        return status;
    }

    public int getTakenBy() {
        return takenBy;
    }


    public void setStatus(int status) {
        this.status = status;
    }

    public void setTakenBy(int takenBy) {
        this.takenBy = takenBy;
    }

    @Override
    public String toString() {
        return "books.Book{" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", dueReturn=" + returnDate +
                '}';
    }
}
