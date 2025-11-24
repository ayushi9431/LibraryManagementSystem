// Library Management System
import java.util.*;

class Book {  //represent a book in the library
    //private fields-data hiding
    private String isbn;   //unique
    private String title;
    private String author;
    private boolean isAvailable;
    
    //constructor,called when creating a new Book object
    public Book(String isbn, String title, String author) {
        //initializes all book properties
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.isAvailable = true;
    }
    
    // Getters and Setters
    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }
    
    @Override
    public String toString() {
        return "ISBN: " + isbn + " | Title: " + title + " | Author: " + author + 
               " | Available: " + (isAvailable ? "Yes" : "No");
    }
}

// Member.java
class Member {
    private String memberId;
    private String name;
    private String email;
    private List<String> borrowedBooks;
    
    public Member(String memberId, String name, String email) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.borrowedBooks = new ArrayList<>();
    }
    
    public String getMemberId() { return memberId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public List<String> getBorrowedBooks() { return borrowedBooks; }
    
    public void borrowBook(String isbn) {
        borrowedBooks.add(isbn);
    }
    
    public void returnBook(String isbn) {
        borrowedBooks.remove(isbn);
    }
    
    @Override
    public String toString() {
        return "ID: " + memberId + " | Name: " + name + " | Email: " + email +
               " | Books Borrowed: " + borrowedBooks.size();
    }
}

// Library.java
class Library {
    private Map<String, Book> books;
    private Map<String, Member> members;
    
    public Library() {
        books = new HashMap<>();
        members = new HashMap<>();
    }
    
    // Book Operations
    public void addBook(String isbn, String title, String author) {
        if (books.containsKey(isbn)) {
            System.out.println("Book with ISBN " + isbn + " already exists!");
        } else {
            books.put(isbn, new Book(isbn, title, author));
            System.out.println("Book added successfully!");
        }
    }
    
    public void removeBook(String isbn) {
        if (books.remove(isbn) != null) {
            System.out.println("Book removed successfully!");
        } else {
            System.out.println("Book not found!");
        }
    }
    
    public void searchBook(String keyword) { //by title or author(case-insensative)
        System.out.println("\nSearch Results:");
        boolean found = false;
        for (Book book : books.values()) {
            if (book.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                book.getAuthor().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println(book);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No books found matching: " + keyword);
        }
    }
    
    public void displayAllBooks() {
        System.out.println("\n=== All Books ===");
        if (books.isEmpty()) {
            System.out.println("No books in library.");
        } else {
            books.values().forEach(System.out::println);
        }
    }
    
    // Member Operations
    public void registerMember(String memberId, String name, String email) {
        if (members.containsKey(memberId)) {
            System.out.println("Member with ID " + memberId + " already exists!");
        } else {
            members.put(memberId, new Member(memberId, name, email));
            System.out.println("Member registered successfully!");
        }
    }
    
    public void displayAllMembers() {
        System.out.println("\n=== All Members ===");
        if (members.isEmpty()) {
            System.out.println("No members registered.");
        } else {
            members.values().forEach(System.out::println);
        }
    }
    
    // Transaction Operations
    public void issueBook(String isbn, String memberId) {
        Book book = books.get(isbn);
        Member member = members.get(memberId);
        
        if (book == null) {
            System.out.println("Book not found!");
            return;
        }
        if (member == null) {
            System.out.println("Member not found!");
            return;
        }
        if (!book.isAvailable()) {
            System.out.println("Book is not available!");
            return;
        }
        
        book.setAvailable(false);
        member.borrowBook(isbn);
        System.out.println("Book issued successfully to " + member.getName());
    }
    
    public void returnBook(String isbn, String memberId) {
        Book book = books.get(isbn);
        Member member = members.get(memberId);
        
        if (book == null) {
            System.out.println("Book not found!");
            return;
        }
        if (member == null) {
            System.out.println("Member not found!");
            return;
        }
        if (!member.getBorrowedBooks().contains(isbn)) {
            System.out.println("This member hasn't borrowed this book!");
            return;
        }
        
        book.setAvailable(true);
        member.returnBook(isbn);
        System.out.println("Book returned successfully!");
    }
}

// Main.java
public class LibraryManagementSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Library library = new Library();
        
        // Sample data
        library.addBook("978-0134685991", "Effective Java", "Joshua Bloch");
        library.addBook("978-0596009205", "Head First Java", "Kathy Sierra");
        library.registerMember("M001", "John Doe", "john@email.com");
        
        while (true) {
            System.out.println("\n=== Library Management System ===");
            System.out.println("1. Add Book");
            System.out.println("2. Remove Book");
            System.out.println("3. Search Book");
            System.out.println("4. Display All Books");
            System.out.println("5. Register Member");
            System.out.println("6. Display All Members");
            System.out.println("7. Issue Book");
            System.out.println("8. Return Book");
            System.out.println("9. Exit");
            System.out.print("Enter choice: ");
            
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline
            
            switch (choice) {
                case 1:
                    System.out.print("Enter ISBN: ");
                    String isbn = sc.nextLine();
                    System.out.print("Enter Title: ");
                    String title = sc.nextLine();
                    System.out.print("Enter Author: ");
                    String author = sc.nextLine();
                    library.addBook(isbn, title, author);
                    break;
                    
                case 2:
                    System.out.print("Enter ISBN to remove: ");
                    library.removeBook(sc.nextLine());
                    break;
                    
                case 3:
                    System.out.print("Enter search keyword: ");
                    library.searchBook(sc.nextLine());
                    break;
                    
                case 4:
                    library.displayAllBooks();
                    break;
                    
                case 5:
                    System.out.print("Enter Member ID: ");
                    String memberId = sc.nextLine();
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Email: ");
                    String email = sc.nextLine();
                    library.registerMember(memberId, name, email);
                    break;
                    
                case 6:
                    library.displayAllMembers();
                    break;
                    
                case 7:
                    System.out.print("Enter ISBN: ");
                    String issueIsbn = sc.nextLine();
                    System.out.print("Enter Member ID: ");
                    String issueMemberId = sc.nextLine();
                    library.issueBook(issueIsbn, issueMemberId);
                    break;
                    
                case 8:
                    System.out.print("Enter ISBN: ");
                    String returnIsbn = sc.nextLine();
                    System.out.print("Enter Member ID: ");
                    String returnMemberId = sc.nextLine();
                    library.returnBook(returnIsbn, returnMemberId);
                    break;
                    
                case 9:
                    System.out.println("Thank you for using the Library Management System!");
                    sc.close();
                    return;
                    
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}