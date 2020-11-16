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
        String prefix = args[0] + ",";
        if (args.length == 2) {
            String id = args[1];
            String result = library.endVisit(id);
            UndoRedo.addCommand(new Command("Depart", args));
            return prefix + result;
        }
        return prefix + "missing-parameters,{id};";
    }

    /**
     * Undo a Depart request
     * @param args
     * @return
     */
    public String undo(String[] args) {
        String prefix = args[0] + ",";
        if (args.length == 2) {
            String id = args[1];
            library.replaceVisitor(id);
            return "Undid Depart Request";
        }
        return "Undid Depart Request";
    }

}
