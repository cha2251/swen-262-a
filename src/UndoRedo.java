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

    public String undoCommand(){
        if (undoList.size() > 0){
            //actually running command
            //Add: Advance, borrow, buy, Depart, Pay, Return
            String com = undoList.get(0).getCommand();
            String[] args = undoList.get(0).getArgs();
            String str = "\n";
            switch (com) {
                case "Advance":
                    AdvanceRequest advanceRequest = new AdvanceRequest(Library.getInstance());
                    str = advanceRequest.undo(args);
                    break;
                case "Arrive":
                    ArriveRequest arriveRequest = new ArriveRequest(Library.getInstance());
                    str = arriveRequest.undo(args);
                    break;
                case "Borrow":
                    BorrowRequest borrowRequest = new BorrowRequest(Library.getInstance());
                    str = borrowRequest.undo(args);
                    break;
                case "Buy":
                    BuyRequest buyRequest = new BuyRequest(Library.getInstance());
                    str = buyRequest.undo(args);
                    break;
                case "Depart":
                    DepartRequest departRequest = new DepartRequest(Library.getInstance());
                    str = departRequest.undo(args);
                    break;
                case "Pay":
                    PayRequest payRequest = new PayRequest(Library.getInstance());
                    str = payRequest.undo(args);
                    break;
                case "Return":
                    ReturnRequest returnRequest = new ReturnRequest(Library.getInstance());
                    str = returnRequest.undo(args, undoList.get(0).getDates(), undoList.get(0).getFine());
            }
            redoList.add(0, undoList.remove(0));
            return str;
        }
        else{
            return "Nothing to be undone.";
        }
    }

    public String redoCommand(){
        if (redoList.size() > 0){
            String com = redoList.get(0).getCommand();
            String[] args = redoList.get(0).getArgs();
            String str = "";
            Command command = redoList.get(0);
            switch (com) {
                case "Advance":
                    AdvanceRequest advanceRequest = new AdvanceRequest(Library.getInstance());
                    str = advanceRequest.execute(args);
                    break;
                case "Arrive":
                    ArriveRequest arriveRequest = new ArriveRequest(Library.getInstance());
                    str = arriveRequest.execute(args);
                    break;
                case "Borrow":
                    BorrowRequest borrowRequest = new BorrowRequest(Library.getInstance());
                    str = borrowRequest.execute(args);
                    break;
                case "Buy":
                    BuyRequest buyRequest = new BuyRequest(Library.getInstance());
                    str = buyRequest.execute(args);
                    break;
                case "Depart":
                    DepartRequest departRequest = new DepartRequest(Library.getInstance());
                    str = departRequest.execute(args);
                    break;
                case "Pay":
                    PayRequest payRequest = new PayRequest(Library.getInstance());
                    str = payRequest.execute(args);
                    break;
                case "Return":
                    ReturnRequest returnRequest = new ReturnRequest(Library.getInstance());
                    str = returnRequest.execute(args);
            }
            undoList.add(0, command);
            return str;
        }
        return "Nothing to be undone";
    }





}
