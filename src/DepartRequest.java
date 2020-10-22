public class DepartRequest implements Request {
    private Library library;

    public DepartRequest(Library library) {
        this.library = library;
    }

    @Override
    public String execute(String[] args) {
        String prefix = args[0] + ",";
        if (args.length == 2) {
            String id = args[1];
            String result = library.endVisit(id);
            return prefix + result;
        }
        return prefix + "missing-parameters,{id};";
    }
}
