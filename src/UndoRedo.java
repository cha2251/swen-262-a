import java.util.ArrayList;
import java.util.List;

public class UndoRedo {

    private List<Command> undoList;
    private List<Command> redoList;

    public UndoRedo(){
        undoList = new ArrayList<>();
        redoList = new ArrayList<>();
    }


    public void addCommand(Command command){
        undoList.add(0, command);
    }

    public void undoCommand(){
        if (undoList.size() > 0){
            //actually running command

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
