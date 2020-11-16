public class DepartRequest implements Request {

    /*
    this class is used for the formatting a visitor leaving notification
     */

    private Library library;

    /**
     Instantiates Library in this class
     @param library
     */
    public DepartRequest(Library library) {
        this.library = library;
    }

    /**
     * Returns a formatted string for when a visitor leaves the library
     * @param args
     * @return
     */
    @Override
    public String execute(String[] args) {
        String clientID = args[0];
        String prefix = args[1] + ",";
        if (args.length == 3) {
            String id = args[2];
            String result = library.endVisit(id, clientID);
            return prefix + result;
        }
        return prefix + "missing-parameters,{id};";
    }
}
