import java.time.LocalDateTime;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

public class BorrowedBook {
    private Book book;
    private LocalDateTime dueDate;
    private LocalDateTime checkoutDate;

    public BorrowedBook(Book book, LocalDateTime checkoutDate){
        this.book = book;
        this.checkoutDate = checkoutDate;
        this.dueDate = checkoutDate.plusDays(7);
    }

    public double returnBook(Book book, LocalDateTime currentDate){
        if (this.book.equals(book)){
            return checkFine(currentDate);
        }
        return -1;
    }

    public double checkFine(LocalDateTime currentDate){
        int daysOverdue = currentDate.getDayOfYear()-dueDate.getDayOfYear();
        if (daysOverdue > 0){
            int fine = Math.min(7, daysOverdue)*10;
            fine += (Math.max(7, daysOverdue) % 7)*2;
            return Math.min(30, fine);
        }
        return 0;
    }

    @Override
    public String toString() {
        return ""+book.getIsbn()+","+book.getTitle()+","+checkoutDate.format(ISO_LOCAL_DATE);
    }
}
