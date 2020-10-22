import java.util.ArrayList;
import java.util.Arrays;

public class BuyRequest implements Request {
    private Library library;

    public BuyRequest(Library library) {
        this.library = library;
    }

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
}
