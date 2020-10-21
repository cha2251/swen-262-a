import java.util.ArrayList;
import java.util.Arrays;

public class ReturnRequest implements Request{
    private Library library;

    public ReturnRequest(Library library) {
        this.library = library;
    }

    @Override
    public String execute(String[] args) {
        String prefix = args[0] + ",";
        if(args.length >= 3) {
            String visitor = args[1];
            ArrayList<String> ids = new ArrayList<String>(Arrays.asList(Arrays.copyOfRange(args,2,args.length)));
            return prefix + library.returnBook(visitor, ids);
        }
        return prefix + "visitor ID,id,[ids];";
    }
}
