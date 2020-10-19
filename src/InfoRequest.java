public class InfoRequest implements Request{
    private Library library;

    public InfoRequest(Library library) {
        this.library = library;
    }
    @Override
    public String execute(String[] args) {
        if(args.length >= 3) {
            String title = args[1];
        }
        return "info,title,{authors},[isbn, [publisher,[sort order]]];";
    }
}
