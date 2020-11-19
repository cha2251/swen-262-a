public class RedoRequest implements Request {


    public RedoRequest(Library library) {
    }

    /**
     * Request call for redoing a request
     * @param args
     * @return
     */
    @java.lang.Override
    public String execute(String[] args) {
        return UndoRedo.getInstance().redoCommand();
    }
}
