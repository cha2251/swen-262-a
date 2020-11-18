public class UndoRequest implements Request {

    /**
     * Request call for undoing a request
     * @param args
     * @return
     */
    @java.lang.Override
    public String execute(String[] args) {
        return UndoRedo.getInstance().undoCommand();
    }
}
