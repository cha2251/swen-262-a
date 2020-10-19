public class SearchRequest implements Request {
    @Override
    public String execute(String[] args) {
        if(args.length >= 3) {
            String title = args[1];
            String authorList = args[2];

        }
        return "info,missing-parameters,{title,{authors},[isbn, [publisher,[sort order]]]};";
    }
}
