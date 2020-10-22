public class InfoRequest implements Request{

    /*
    this class is used for the formatting book information
     */

    private Library library;

    //Instantiates Library in this class
    public InfoRequest(Library library) {
        this.library = library;
    }

    //Returns a formatted string for information on a book
    @Override
    public String execute(String[] args) {
        String prefix = args[0] + ",";
        if(args.length == 6) {
            String title = args[1];
            String authorList = args[2];
            String isbn = args[3];
            String publisher = args[4];
            String sort = args[5];
            return prefix + library.search(title,authorList,isbn,publisher,sort,BookList.OWNED);
        }
        return prefix + "missing-parameters,{title,{authors},[isbn, [publisher,[sort order]]]};";
    }
}
