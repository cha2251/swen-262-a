import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

/**
 * Visitor class, stores visitor information and manages rentals for a
 * specific visitor.
 */

public class Visitor {
    private String id, firstName, lastName, address, phoneNumber;

    private List<BorrowedBook> borrowedBooks;
    private double finesOwed = 0;

    /**
     * Constructor
     *
     * @param id
     * @param firstName
     * @param lastName
     * @param address
     * @param phoneNumber
     */
    public Visitor(String id, String firstName, String lastName, String address, String phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;

        borrowedBooks = new ArrayList<>();
    }

    /**
     * adds a number of books to the visitor's list of book rentals
     *
     * @param books
     * @param currentDate
     * @return
     */
    public String borrowBook(List<RealBook> books, LocalDateTime currentDate) {
        for (RealBook book : books) {
            borrowedBooks.add(new BorrowedBook(book, currentDate));
        }

        return currentDate.plusDays(7).format(ISO_LOCAL_DATE);
    }

    /**
     * returns list of borrowed books
     *
     * @return
     */
    public List<BorrowedBook> findBorrowedBooks() {
        return borrowedBooks;
    }

    /**
     * returns a book to the library (removes from rentals list)
     *
     * @param books
     * @param currentDate
     * @return
     */
    public double returnBook(List<RealBook> books, LocalDateTime currentDate) {
        double fine = 0;
        for (RealBook book : books) {
            double status = 0;
            for (BorrowedBook borrowedBook : borrowedBooks) {
                status = borrowedBook.returnBook(book, currentDate);
                if (status >= 0) {
                    borrowedBooks.remove(borrowedBook);
                    fine += status;
                    break;
                }
            }
        }
        finesOwed += fine;
        return fine;
    }

    /**
     * pays an amount toward the visitor's fines
     *
     * @param amount
     * @return
     */
    public double payFine(double amount) {
        if (amount > finesOwed || amount < 0) return -1;
        finesOwed -= amount;
        return finesOwed;
    }

    /**
     * returns visitor id
     *
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * returns current number of borrowed books
     *
     * @return
     */
    public int getNumBooksBorrowed() {
        return borrowedBooks.size();
    }

    /**
     * returns amount of fines owed
     *
     * @return
     */
    public double getFinesOwed() {
        return finesOwed;
    }

    /**
     * Comparator, sees if one visitor object is identical to another one
     *
     * @param o
     * @return
     */
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
