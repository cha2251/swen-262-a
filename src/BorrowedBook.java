import java.time.LocalDateTime;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

public class BorrowedBook {

    /*
    This class is used for the creation and management of borrowed books
     */

    private Book book;
    private LocalDateTime dueDate;
    private LocalDateTime checkoutDate;

    //Creates a new borrowed book, and removes one copy of the book from availability
    public BorrowedBook(Book book, LocalDateTime checkoutDate){
        this.book = book;
        book.available--;
        this.checkoutDate = checkoutDate;
        this.dueDate = checkoutDate.plusDays(7);
    }

    //Checks a book back in and gets any fine applicable
    public double returnBook(Book book, LocalDateTime currentDate){
        if (this.book.equals(book)){
            book.available++;
            return checkFine(currentDate);
        }
        return -1;
    }

    //Checks and returns if there is a fine on a borrowed book
    public double checkFine(LocalDateTime currentDate){
        int daysOverdue = currentDate.getDayOfYear()-dueDate.getDayOfYear();
        if (daysOverdue > 0){
            int fine = Math.min(7, daysOverdue)*10;
            fine += (Math.max(7, daysOverdue) % 7)*2;
            return Math.min(30, fine);
        }
        return 0;
    }

    //Returns the book in question
    public Book getBook() {
        return book;
    }

    //Default formatting for how to print a borrowed book
    @Override
    public String toString() {
        return "" + book.getIsbn() + "," + book.getTitle() + "," + checkoutDate.format(ISO_LOCAL_DATE);
    }
}
