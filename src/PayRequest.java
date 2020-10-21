public class PayRequest implements Request{
    private Library library;

    public PayRequest(Library library) {
        this.library = library;
    }

    @Override
    public String execute(String[] args) {
        String prefix = args[0] + ",";
        if(args.length == 3) {
            String visitor = args[1];
            String amount = args[2];
            return prefix + library.payFine(visitor,amount);
        }
        return prefix + "visitor ID,amount;";
    }
}
