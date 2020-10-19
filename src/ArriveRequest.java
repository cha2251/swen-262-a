public class ArriveRequest implements Request {

    private Library library;

    public ArriveRequest(Library library) {
        this.library = library;
    }

    @Override
    public String execute(String[] args) {
        if(args.length == 2) {
            String id = args[1];
            String result = library.beginVisit(id);
            return result;
        }
        return "arrive,missing-parameters,{id};";
    }
}
