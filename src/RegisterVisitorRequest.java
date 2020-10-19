public class RegisterVisitorRequest implements Request {

    private Library library;

    public RegisterVisitorRequest(Library library) {
        this.library = library;
    }

    @Override
    public String execute(String[] args) {
        //Do register visitor logic and call the library method to register a visitor
        return "Placeholder";
    }
}
