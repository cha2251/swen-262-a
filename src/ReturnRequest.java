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
        String prefix = args[0] + ",";
        if (args.length >= 3) {
            String visitor = args[1];
            ArrayList<String> ids = new ArrayList<String>(Arrays.asList(Arrays.copyOfRange(args, 2, args.length)));
            ArrayList<String> dates = library.borrowedDates(visitor, ids);
            args.add(dates);
            UndoRedo.addCommand(new Command("Return", args));
            return prefix + library.returnBook(visitor, ids);
        }
        return prefix + "visitor ID,id,[ids];";
    }

    public String undo(String[] args) {
        String prefix = args[0] + ",";
        if (args.length >= 3) {
            String visitor = args[1];
            ArrayList<String> ids = new ArrayList<String>(Arrays.asList(Arrays.copyOfRange(args, 2, args.length)));
            ArrayList<String> dates = new ArrayList<String>(Arrays.asList(Arrays.copyOfRange(args, 3, args.length)));
            return library.undoReturnBook(visitor, ids, dates);
        }
        return "Undid Return Request";
    }
}
