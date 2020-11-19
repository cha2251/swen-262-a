import java.util.ArrayList;
import java.util.Arrays;

public class BorrowRequest implements Request {

    /*
    this class is used for the formatting of requests to borrow books
     */

    private Library library;

    /**
     Instantiates Library in this class
     @param library
     */
    public BorrowRequest(Library library) {
        this.library = library;
    }

    /**
     * Returns a formatted string for a borrow request of books from a visitor
     * @param args
     * @return
     */
    @Override
    public String execute(String[] args) {
        String clientID = args[0];
        String prefix = args[1] + ",";
        if (args.length == 4) {
            String visitor = args[2];
            String ids = args[3];
            String fixed = ids.replace("{", "").replace("}", "");
            String[] tentativeList = fixed.split(",");
            ArrayList<String> books = new ArrayList<>(Arrays.asList(tentativeList));
            UndoRedo.getInstance().addCommand(new Command("Borrow", args));
            return prefix + library.borrowBook(visitor, books, clientID);
        }
        return prefix + "visitor ID,{id};";
    }

    /**
     * Undo Borrow Request
     * @param args
     * @return
     */
    public String undo(String[] args) {
        String prefix = args[0] + ",";
        if (args.length == 4) {
            String visitor = args[1];
            String ids = args[2];
            String fixed = ids.replace("{", "").replace("}", "");
            String[] tentativeList = fixed.split(",");
            ArrayList<String> books = new ArrayList<>(Arrays.asList(tentativeList));
            return library.undoBorrowBook(visitor, books);
        }
        return "Undid Borrow Book";
    }


}
