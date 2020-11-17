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
        if (redoList.size() > 0) {
            redoList = new ArrayList<>();
        }
    }

    public void undoCommand(){
        if (undoList.size() > 0){
            //actually running command
            //Add: Advance, borrow, buy, Depart, Pay, Return
            String com = undoList.get(0).getCommand();
            String[] args = undoList.get(0).getArgs();
            switch (com) {
                case "Advance":
                    AdvanceRequest.undo(args);
                    break;
                case "Borrow":
                    BorrowRequest.undo(args);
                    break;
                case "Buy":
                    BuyRequest.undo(args);
                    break;
                case "Depart":
                    DepartRequest.undo(args);
                    break;
                case "Pay":
                    PayRequest.undo(args);
                    break;
                case "Return":
                    ReturnRequest.undo(args, undoList.get(0).getDates());
            }

            redoList.add(0, undoList.remove(0));
        }
    }

    public void redoCommand(){
        if (redoList.size() > 0){
            String com = redoList.get(0).getCommand();
            String[] args = redoList.get(0).getArgs();
            switch (com) {
                case "Advance":
                    AdvanceRequest.execute(args);
                    break;
                case "Borrow":
                    BorrowRequest.execute(args);
                    break;
                case "Buy":
                    BuyRequest.execute(args);
                    break;
                case "Depart":
                    DepartRequest.execute(args);
                    break;
                case "Pay":
                    PayRequest.execute(args);
                    break;
                case "Return":
                    ReturnRequest.execute(args);
            }

            undoList.add(0, redoList.remove(0));
        }
    }





}
