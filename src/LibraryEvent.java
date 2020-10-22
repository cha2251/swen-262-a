import java.time.LocalDate;

public class LibraryEvent {

    /**
     * Used for the creation of library events
     */

    private LocalDate time;
    private String[] args;

    /**
     * Constructs a Library event
     * @param time
     * @param args
     */
    public LibraryEvent(LocalDate time, String[] args) {
        this.time = time;
        this.args = args;
    }

    /**
     * Gets the current time
     * @return
     */
    public LocalDate getTime() {
        return time;
    }

    /**
     * gets the current request
     * @return
     */
    public String[] getRequest() {
        return args;
    }
}
