import books.*;
import personas.*;
import util.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//import static util.Util.populateUserInfo;

public class App {
    private static String input;
    private static String name;
    private static String key;
    private static Admin admin = null;
    private static Reader reader = null;

    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);
        String input;


            System.out.println("Login or Create Account (login/create): ");
            input = scan.nextLine().toLowerCase();
        while (true) {
            if (input.equals("login")) {
                System.out.println("Enter name and key: ");
                String name = scan.nextLine();
                String key = scan.nextLine();

                if (Util.checkAdmin(name, key) == 1) {
                    Admin admin = (Admin) Util.login(name, key);
                    if (admin != null) {
                        while (true) {
                            System.out.println("***********Logged as Admin**************");
                            System.out.println("***********" + admin.getName() + "**************");
                            System.out.println("What do you want to do?");
                            System.out.println("VB - View all books in the library");
                            System.out.println("VU - View all users in the library");
                            System.out.println("VBL - View your book list");
                            System.out.println(" ");
                            System.out.println("TB - Take book from the library");
                            System.out.println("RB - Return book from your book list");
                            System.out.println(" ");
                            System.out.println("AD - Add a book to the library");
                            System.out.println("DB - Delete a book from the library");
                            System.out.println(" ");
                            System.out.println("AA - Add an account to the library");
                            System.out.println("DA - Delete an account from the library");
                            System.out.println("RD - Remove the debt from an account");
                            System.out.println("P - Promote an account");
                            System.out.println("Q - Quit");
                            System.out.println("*******************************");
                            System.out.print(": ");
                            input = scan.nextLine().toLowerCase();
                            switch (input) {
                                case "vb":
                                    admin.viewBooks();
                                    break;
                                case "vu":
                                    admin.viewUsers();
                                    break;
                                case "vbl":
                                    System.out.println(admin.yourBookList());
                                    break;
                                case "tb":
                                    System.out.print("Input book name: ");
                                    String bookName = scan.nextLine();
                                    admin.takeBook(bookName);
                                    break;
                                case "rb":
                                    System.out.print("Input book name: ");
                                    bookName = scan.nextLine();
                                    admin.returnBook(bookName);
                                    break;
                                case "ad":
                                    admin.addBook();
                                    break;
                                case "db":
                                    admin.removeBook();
                                    break;
                                case "aa":
                                    admin.addAccount();
                                    break;
                                case "da":
                                    System.out.print("Enter user name: ");
                                    String userName = scan.nextLine();
                                    System.out.print("Enter user key: ");
                                    String userKey = scan.nextLine();
                                    User user = Util.populateUserInfo(userName, userKey);
                                    admin.removeAccount(user);
                                    break;
                                case "rd":
                                    System.out.print("Enter user name: ");
                                    userName = scan.nextLine();
                                    System.out.print("Enter user key: ");
                                    userKey = scan.nextLine();
                                    user = Util.populateUserInfo(userName, userKey);
                                    admin.resetTaxes(user);
                                    break;
                                case "p":
                                    System.out.print("Enter user name: ");
                                    userName = scan.nextLine();
                                    System.out.print("Enter user key: ");
                                    userKey = scan.nextLine();
                                    Reader reader = (Reader) Util.populateUserInfo(userName, userKey);
                                    admin.promoteReader(reader);
                                    break;
                                case "q":
                                    System.out.println("Quitting the program...");
                                    Util.quit();
                                    break;
                                default:
                                    System.out.println("Invalid input!");
                                    break;
                            }
                        }
                    } else {
                        System.out.println("Invalid name or key. Please try again.");
                    }
                } else if (Util.checkAdmin(name, key) == 0) {
                    Reader reader = (Reader) Util.login(name, key);
                    if (reader != null) {
                        while (true) {
                            System.out.println("***********Logged as Reader**************");
                            System.out.println("***********" + reader.getName() + "**************");
                            System.out.println("What do you want to do?");
                            System.out.println("VB - View all books in the library");
                            System.out.println("VBL - View your book list");
                            System.out.println(" ");
                            System.out.println("TB - Take book from the library");
                            System.out.println("RB - Return book from your book list");
                            System.out.println("Q - Quit");
                            System.out.println("*******************************");
                            System.out.print(": ");
                            input = scan.nextLine().toLowerCase();
                            switch (input) {
                                case "vb":
                                    reader.viewBooks();
                                    break;
                                case "vbl":
                                    System.out.println(reader.yourBookList());
                                    break;
                                case "tb":
                                    System.out.print("Input book name: ");
                                    String bookName = scan.nextLine();
                                    reader.takeBook(bookName);
                                    break;
                                case "rb":
                                    System.out.print("Input book name: ");
                                    bookName = scan.nextLine();
                                    reader.returnBook(bookName);
                                    break;
                                case "q":
                                    System.out.println("Quitting the program...");
                                    Util.quit();
                                    break;
                                default:
                                    System.out.println("Invalid input!");
                                    break;
                            }
                        }
                    } else {
                        System.out.println("Invalid name or key. Please try again.");
                    }
                } else if (Util.checkAdmin(name, key) == 2) {
                    System.out.println("Invalid name or key. Please try again.");
                }
            } else if (input.equals("create")) {
                System.out.println("Enter name and key: ");
                String name = scan.nextLine();
                String key = scan.nextLine();
                Reader reader = Util.createAccount(name, key);

                while (true) {
                    System.out.println("***********Logged as Reader**************");
                    System.out.println("***********" + reader.getName() + "**************");
                    System.out.println("What do you want to do?");
                    System.out.println("VB - View all books in the library");
                    System.out.println("VBL - View your book list");
                    System.out.println(" ");
                    System.out.println("TB - Take book from the library");
                    System.out.println("RB - Return book from your book list");
                    System.out.println("Q - Quit");
                    System.out.println("*******************************");
                    System.out.print(": ");
                    input = scan.nextLine().toLowerCase();
                    switch (input) {
                        case "vb":
                            reader.viewBooks();
                            break;
                        case "vbl":
                            System.out.println(reader.yourBookList());
                            break;
                        case "tb":
                            System.out.print("Input book name: ");
                            String bookName = scan.nextLine();
                            reader.takeBook(bookName);
                            break;
                        case "rb":
                            System.out.print("Input book name: ");
                            bookName = scan.nextLine();
                            reader.returnBook(bookName);
                            break;
                        case "q":
                            System.out.println("Quitting the program...");
                            Util.quit();
                            break;
                        default:
                            System.out.println("Invalid input!");
                            break;
                    }
                }
            } else {
                System.out.println("Invalid input! Please try again.");
            }
        }
    }



    }

