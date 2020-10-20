public class InfoRequest implements Request{
    private Library library;

    public InfoRequest(Library library) {
        this.library = library;
    }
    @Override
    public String execute(String[] args) {
        if(args.length == 6) {
            String title = args[1];
            String authorList = args[2];
            String isbn = args[3];
            String publisher = args[4];
            String sort = args[5];
            library.search(title,authorList,isbn,publisher,sort);
        }
        return "info,missing-parameters,{title,{authors},[isbn, [publisher,[sort order]]]};";
    }
}
