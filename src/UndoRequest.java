public class UndoRequest implements Request {

    @java.lang.Override
    public String execute(String[] args) {
        return UndoRedo.getInstance().undoCommand();
    }
}
