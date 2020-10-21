import java.time.LocalDate;

public class LibraryEvent {
    private LocalDate time;
    private String[] args;

    public LocalDate getTime() {
        return time;
    }
    public String[] getRequest() {
        return args;
    }

    public LibraryEvent(LocalDate time, String[] args) {
        this.time = time;
        this.args = args;
    }
}
