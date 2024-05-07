# Library Management System

The goal of this project is to create a program that supports the functionality of a library. Its structure will consist of a list of books in the library and the different users, including librarians and readers.

The user has access to view the available books in the library and also to see which books are checked out in their name. As functionalities, they can also check out and return books. Additionally, the system implements a fee system based on how overdue a book is. This fee can be adjusted, which can be added as a future enhancement to the program. For the current version, a fee of $0.50 per day will be charged for overdue books. Each user will be able to see at any time how much money they owe to the library.

Meanwhile, the librarian has access to view all books at any time, including whether they have been checked out, when they are due, and whether they are overdue. These are details that the user cannot see. The librarian can also view all users, with the most frequently used feature being to check if a given user has a debt to the library. As functionalities of the library, it can add and remove books. When adding a book, it creates a new book object and adds it to the library. When removing a book, it checks if the book exists and then deletes it.

In the database, the books will have the following fields: Name, Author, Status (whether it's checked out or not, symbolized by 0 or 1), Return Date, and Borrower (which also serves as a foreign key linking to the table of user profiles). This will constitute the table for books in our library.

The user table will consist of the following fields: Name, Key (to be used as a unique password), whether the user is a Librarian (represented by 0 or 1), and how much debt the user has to the library.