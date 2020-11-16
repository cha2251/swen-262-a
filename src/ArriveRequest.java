public class ArriveRequest implements Request {

    /*
    Used for the simulated string formatting of when someone arrives
     */

    private Library library;

    /**
     Instantiates Library in this class
     @param library
     */
    public ArriveRequest(Library library) {
        this.library = library;
    }

    /**
     * Returns a string of the formatted notification someone has arrived.
     * @param args
     * @return
     */
    @Override
    public String execute(String[] args) {
        if (args.length == 3) {
            String clientID = args[0];
            String id = args[2];
            String result = library.beginVisit(id,clientID);
            return result;
        }
        return "arrive,missing-parameters,{id};";
    }
}
