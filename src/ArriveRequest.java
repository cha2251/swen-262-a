public class ArriveRequest implements Request {

    private Library library;

    public ArriveRequest(Library library) {
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
