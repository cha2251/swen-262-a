import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_TIME;

public class Visit {
    private Visitor visitor;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public Visit(Visitor v, LocalDateTime date) {
        visitor = v;
        startDate = date;
    }

    public String getStartDate() {
        return startDate.format(ISO_LOCAL_DATE);
    }

    public String getStartTime() {
        return startDate.format(ISO_LOCAL_TIME);
    }

    public Visitor getVisitor() {
        return visitor;
    }

    public LocalDateTime getVisitLength() {
        return null;
    }

    public String getElapsedTime(LocalDateTime currentDate) {
        endDate = currentDate;
        long hours = startDate.until(currentDate, ChronoUnit.HOURS);
        long minutes = startDate.until(currentDate, ChronoUnit.MINUTES);
        long seconds = startDate.until(currentDate, ChronoUnit.SECONDS);
        return "" + hours + ":" + minutes + ":" + seconds;
    }
}
