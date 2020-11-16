import java.util.ArrayList;
import java.util.Arrays;

public class BuyRequest implements Request {

    /*
    this class is used for the formatting of requests to buy books
     */

    private Library library;

    /**
     Instantiates Library in this class
     @param library
     */
    public BuyRequest(Library library) {
        this.library = library;
    }

    /**
     * Returns a formatted string for a buy request of books from a visitor
     * @param args
     * @return
     */
    @Override
    public String execute(String[] args) {
        String prefix = args[0] + ",";
        if (args.length >= 3) {
            String amount = args[1];
            String ids = args[2];
            String fixed = ids.replace("{", "").replace("}", "");
            String[] tentativeList = fixed.split(",");
            ArrayList<String> books = new ArrayList<>(Arrays.asList(tentativeList));
            return prefix + library.buyBook(books, Integer.parseInt(amount));
        }
        return prefix + "quantity,id[ids];";
    }

    /**
     * Undo Buy Request
     * @param args
     * @return
     */
    public String undo(String[] args) {
        String prefix = args[0] + ",";
        if (args.length >= 3) {
            String amount = args[1];
            String ids = args[2];
            String fixed = ids.replace("{", "").replace("}", "");
            String[] tentativeList = fixed.split(",");
            ArrayList<String> books = new ArrayList<>(Arrays.asList(tentativeList));
            return prefix + library.undoBuyBook(books, Integer.parseInt(amount));
        }
        return "Undid Buy Book";
    }
}
