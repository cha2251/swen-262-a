public class DepartRequest implements Request {

    /*
    this class is used for the formatting a visitor leaving notification
     */

    private Library library;

    //Instantiates Library in this class
    public DepartRequest(Library library) {
        this.library = library;
    }

    //Returns a formatted string for when a visitor leaves the library
    @Override
    public String execute(String[] args) {
        String prefix = args[0] + ",";
        if (args.length == 2) {
            String id = args[1];
            String result = library.endVisit(id);
            return prefix + result;
        }
        return prefix + "missing-parameters,{id};";
    }
}
