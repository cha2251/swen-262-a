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
            args.add(String.valueOf(library.getFine(visitor, ids)));
            UndoRedo.addCommand(new Command("Return", args, dates));
            return prefix + library.returnBook(visitor, ids);
        }
        return prefix + "visitor ID,id,[ids];";
    }

    public String undo(String[] args, ArrayList<String> dates) {
        String prefix = args[0] + ",";
        if (args.length >= 3) {
            String visitor = args[1];
            ArrayList<String> ids = new ArrayList<String>(Arrays.asList(Arrays.copyOfRange(args, 2, args.length)));
            double fine = Double.parseDouble(args[4]);
            return library.undoReturnBook(visitor, ids, dates, fine);
        }
        return "Undid Return Request";
    }
}
