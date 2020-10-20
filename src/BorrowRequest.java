import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BorrowRequest implements Request {
    private Library library;

    public BorrowRequest(Library library) {
        this.library = library;
    }

    @Override
    public String execute(String[] args) {
        String prefix = args[0] + ",";
        if(args.length == 3) {
            String visitor = args[1];
            String ids = args[2];
            String fixed = ids.replace("{", "").replace("}", "");
            String[] tentativeList = fixed.split(",");
            List<String> books = Arrays.asList(tentativeList);
            return prefix + library.borrowBook(visitor, (ArrayList<String>) books);
        }
        return prefix + "visitor ID,{id};";
    }
}
