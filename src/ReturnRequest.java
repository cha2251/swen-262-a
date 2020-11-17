import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Implments a request for book returns
 */
public class ReturnRequest implements Request {
    private Library library;

    /**
     * Constructor
     * @param library
     */
    public ReturnRequest(Library library) {
        this.library = library;
    }

    /**
     * Breaks up a request string for parsing
     * @param args
     * @return
     */
    @Override
    public String execute(String[] args) {
        String clientID = args[0];
        String prefix = args[1] + ",";
        if (args.length >= 4) {
            String visitor = args[2];
            ArrayList<String> ids = new ArrayList<String>(Arrays.asList(Arrays.copyOfRange(args, 2, args.length)));
            ArrayList<LocalDateTime> dates = library.borrowedDates(visitor, ids);
            UndoRedo.getInstance().addCommand(new Command("Return", args, dates, library.getFine(visitor, ids)));
            return prefix + library.returnBook(visitor, ids, clientID);
        }
        return prefix + "visitor ID,id,[ids];";
    }

    public String undo(String[] args, ArrayList<LocalDateTime> dates, double fine) {
        String prefix = args[0] + ",";
        if (args.length >= 3) {
            String visitor = args[1];
            ArrayList<String> ids = new ArrayList<String>(Arrays.asList(Arrays.copyOfRange(args, 2, args.length)));
            return library.undoReturnBook(visitor, ids, dates, fine);
        }
        return "Undid Return Request";
    }
}
