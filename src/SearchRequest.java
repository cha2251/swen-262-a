public class SearchRequest implements Request {
    @Override
    public String execute(String[] args) {
        if(args.length >= 3) {
            String title = args[1];
            String authorList = args[2];
            if(!args[3].isEmpty()) {
                String isbn = args[3];
                if(!args[4].isEmpty()) {
                    String publisher = args[4];
                    if(!args[5].isEmpty()) {

                    }
                }
            }
        }
        return "info,missing-parameters,{title,{authors},[isbn, [publisher,[sort order]]]};";
    }
}
