import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DatetimeRequest implements Request {
    private Library library;

    public DatetimeRequest(Library library) {
        this.library = library;
    }

    @Override
    public String execute(String[] args) {
        String prefix = args[0] + ",";
        LocalDateTime time = library.getLibraryTime();
        DateTimeFormatter myAwesomeCustomFormatForDateTimeYAYYYYY = DateTimeFormatter.ofPattern("yyyy/MM/dd,HH:mm:ss");
        return prefix + time.format(myAwesomeCustomFormatForDateTimeYAYYYYY);
    }
}
