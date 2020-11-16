import java.util.ArrayList;
import java.util.List;

public class UndoRedo {

    public static UndoRedo instance = new UndoRedo();
    private List<Command> undoList = new ArrayList<>();
    private List<Command> redoList = new ArrayList<>();

    public static UndoRedo getInstance(){
        return instance;
    }


    public void addCommand(Command command){
        undoList.add(0, command);
    }

    public void undoCommand(){
        if (undoList.size() > 0){
            //actually running command
            //Add: Advance, borrow, buy, Depart, Pay, Return

            redoList.add(0, undoList.remove(0));
        }
    }

    public void redoCommand(){
        if (redoList.size() > 0){
            //actually running command

            undoList.add(0, redoList.remove(0));
        }
    }





}
