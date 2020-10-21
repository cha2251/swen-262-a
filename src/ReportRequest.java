public class ReportRequest implements Request {

    private Library library;

    public ReportRequest(Library library) {
        this.library = library;
    }
    @Override
    public String execute(String[] args) {
        int days = -1;
        String prefix = args[0] + ",";
        if(args.length == 2) {
            days = Integer.parseInt(args[1]);
        }
        return prefix + library.generateReport(days);
    }
}
