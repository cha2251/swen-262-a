import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static java.time.format.DateTimeFormatter.*;

/**
 * Visit class stores information about a library visit
 */
public class Visit {
    private Visitor visitor;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    /**
     * Constructor.
     * @param v
     * @param date
     */
    public Visit(Visitor v, LocalDateTime date){
        visitor = v;
        startDate = date;
    }

    /**
     * returns the set start date for the visit
     * @return
     */
    public String getStartDate(){
        return startDate.format(ISO_LOCAL_DATE);
    }

    /**
     * returns the set start time for the visit
     * @return
     */
    public String getStartTime(){
        return startDate.format(ISO_LOCAL_TIME);
    }

    /**
     * returns the visitor this visit belongs to
     * @return
     */
    public Visitor getVisitor() {
        return visitor;
    }

    /**
     * returns the length of the visit
     * @return
     */
    public LocalDateTime getVisitLength() {
        return null;
    }

    /**
     * returns the amount of time from the start time until a given time
     * @param currentDate
     * @return
     */
    public String getElapsedTime(LocalDateTime currentDate) {
        endDate = currentDate;
        long hours = startDate.until( currentDate, ChronoUnit.HOURS );
        long minutes = startDate.until( currentDate, ChronoUnit.MINUTES );
        long seconds = startDate.until( currentDate, ChronoUnit.SECONDS );
        return ""+hours+":"+minutes+":"+seconds;
    }
}
