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
        String prefix = args[0] + ",";
        if (args.length == 2) {
            days = Integer.parseInt(args[1]);
        }
        return prefix + library.generateReport(days);
    }
}
