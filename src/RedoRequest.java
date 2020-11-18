public class RedoRequest implements Request {

    @java.lang.Override
    public String execute(String[] args) {
        return UndoRedo.getInstance().redoCommand();
    }
}
