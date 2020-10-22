import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DatetimeRequest implements Request {

    /*
    this class is used for the formatting of the date and time
     */

    private Library library;

    /**
     Instantiates Library in this class
     @param library
     */
    public DatetimeRequest(Library library) {
        this.library = library;
    }

    /**
     * Returns a formatted string of the date and time
     * @param args
     * @return
     */
    @Override
    public String execute(String[] args) {
        String prefix = args[0] + ",";
        LocalDateTime time = library.getLibraryTime();
        DateTimeFormatter myAwesomeCustomFormatForDateTimeYAYYYYY  = DateTimeFormatter.ofPattern("yyyy/MM/dd,HH:mm:ss");
        return prefix + time.format(myAwesomeCustomFormatForDateTimeYAYYYYY);
    }
}
