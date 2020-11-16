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
        if (args.length == 2) {
            String id = args[1];
            String result = library.beginVisit(id);
            UndoRedo.addCommand(new Command("Arrive", args));
            return result;
        }
        return "arrive,missing-parameters,{id};";
    }

    /**
     * Undo Arrive Request
     * @param args
     * @return
     */
    public String undo(String[] args) {
        String id = args[1];
        library.removeVisitor(id);
        return "Undid Arrive Request";
    }
}
