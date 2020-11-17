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
                    AdvanceRequest advanceRequest = new AdvanceRequest(Library.getInstance());
                    advanceRequest.undo(args);
                    break;
                case "Borrow":
                    BorrowRequest borrowRequest = new BorrowRequest(Library.getInstance());
                    borrowRequest.undo(args);
                    break;
                case "Buy":
                    BuyRequest buyRequest = new BuyRequest(Library.getInstance());
                    buyRequest.undo(args);
                    break;
                case "Depart":
                    DepartRequest departRequest = new DepartRequest(Library.getInstance());
                    departRequest.undo(args);
                    break;
                case "Pay":
                    PayRequest payRequest = new PayRequest(Library.getInstance());
                    payRequest.undo(args);
                    break;
                case "Return":
                    ReturnRequest returnRequest = new ReturnRequest(Library.getInstance());
                    returnRequest.undo(args, undoList.get(0).getDates(), undoList.get(0).getFine());
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
                    AdvanceRequest advanceRequest = new AdvanceRequest(Library.getInstance());
                    advanceRequest.execute(args);
                    break;
                case "Borrow":
                    BorrowRequest borrowRequest = new BorrowRequest(Library.getInstance());
                    borrowRequest.execute(args);
                    break;
                case "Buy":
                    BuyRequest buyRequest = new BuyRequest(Library.getInstance());
                    buyRequest.execute(args);
                    break;
                case "Depart":
                    DepartRequest departRequest = new DepartRequest(Library.getInstance());
                    departRequest.execute(args);
                    break;
                case "Pay":
                    PayRequest payRequest = new PayRequest(Library.getInstance());
                    payRequest.execute(args);
                    break;
                case "Return":
                    ReturnRequest returnRequest = new ReturnRequest(Library.getInstance());
                    returnRequest.execute(args);
            }

            undoList.add(0, redoList.remove(0));
        }
    }





}
