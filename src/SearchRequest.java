public class SearchRequest implements Request {
    private Library library;

    public SearchRequest(Library library) {
        this.library = library;
    }

    @Override
    public String execute(String[] args) {
        String prefix = args[0] + ",";
        if (args.length == 6) {
            String title = args[1];
            String authorList = args[2];
            String isbn = args[3];
            String publisher = args[4];
            String sort = args[5];
            return prefix + library.search(title, authorList, isbn, publisher, sort, BookList.PURCHASABLE);
        }
        return prefix + "missing-parameters,{title,{authors},[isbn, [publisher,[sort order]]]};";
    }
}
