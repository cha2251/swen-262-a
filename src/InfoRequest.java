public class InfoRequest implements Request{
    private Library library;

    public InfoRequest(Library library) {
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
