public class ArriveRequest implements Request {

    /*
    Used for the simulated string formatting of when someone arrives
     */

    private Library library;

    //Instantiates Library in this class
    public ArriveRequest(Library library) {
        this.library = library;
    }

    //Returns a string of the formatted notification someone has arrived.
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
