public class DepartRequest implements Request {
    private Library library;

    public DepartRequest(Library library) {
        this.library = library;
    }

    @Override
    public String execute(String[] args) {
        return null;
    }

    @Override
    public void error(String message) {

    }
}
