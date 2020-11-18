public class RedoRequest implements Request {

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
