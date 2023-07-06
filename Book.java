import java.util.Date;

public class Book {
    private int id;
    private String name;
    private String author;
    private Date dueReturn;
    private int status;
    private String takenBy;


    public Book(int bookId, String bookName, String author, int status, int takenBy) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.dueReturn = dueReturn;
        this.status = 0;
    }

//    public Book(){
//        this.name = "A Song of Ice and Fire";
//        this.author = "George R.R. Martin";
//        this.dueReturn = new Date(2023, 6,30);
//        this.status = 0;
//        this.takenBy = null;
//
//    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public Date getDueReturn() {
        return dueReturn;
    }

    public int getStatus() {
        return status;
    }

    public String getTakenBy() {
        return takenBy;
    }


    public void setStatus(int status) {
        this.status = status;
    }

    public void setTakenBy(String takenBy) {
        this.takenBy = takenBy;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", dueReturn=" + dueReturn +
                '}';
    }
}
