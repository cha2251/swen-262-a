import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Visitor {
    private int id;
    private String firstName, lastName, address, phoneNumber;

    private List<BorrowedBook> borrowedBooks;
    private double finesOwed;

    public Visitor(int id, String firstName, String lastName, String address, String phoneNumber){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;

        borrowedBooks = new ArrayList<BorrowedBook>();
    }

    public void borrowBook(List<Book> books){
        for(Book book : books){
            borrowedBooks.add(new BorrowedBook(book, LocalDateTime.now()));
        }
    }

    public List<BorrowedBook> findBorrowedBooks(){
        return borrowedBooks;
    }

    public double returnBook(List<Book> books, LocalDateTime currentDate) {
        double fine = 0;
        for (Book book : books) {
            double status = 0;
            for (BorrowedBook borrowedBook : borrowedBooks) {
                status = borrowedBook.returnBook(book, currentDate);
                if (status >= 0) {
                    borrowedBooks.remove(borrowedBooks);
                    fine += status;
                    break;
                }
            }
        }
        finesOwed += fine;
        return fine;
    }

    public double payFine(double amount){
        if (amount > finesOwed || amount < 0)return -1;
        finesOwed -= amount;
        return finesOwed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Visitor visitor = (Visitor) o;
        return firstName.equals(visitor.firstName) &&
                lastName.equals(visitor.lastName) &&
                address.equals(visitor.address) &&
                phoneNumber.equals(visitor.phoneNumber);
    }
}
