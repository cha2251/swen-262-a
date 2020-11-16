public class ReportRequest implements Request {

    /*
    Used for the string formatting of a library report
     */

    private Library library;

    /**
     * Instantiates Library in this class
     * @param library
     */
    public ReportRequest(Library library) {
        this.library = library;
    }

    /**
     * Returns a formatted string for a library report
     * @param args
     * @return
     */
    @Override
    public String execute(String[] args) {
        int days = -1;
        String clientID = args[0];
        String prefix = args[1] + ",";
        if (args.length == 3) {
            days = Integer.parseInt(args[2]);
        }
        return prefix + library.generateReport(days, clientID);
    }
}
