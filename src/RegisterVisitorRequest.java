public class RegisterVisitorRequest implements Request {

    private Library library;

    public RegisterVisitorRequest(Library library) {
        this.library = library;
    }

    @Override
    public String execute(String[] args) {
        if(args.length == 5) {
            String firstName = args[1];
            String lastName = args[2];
            String address = args[3];
            String phone = args[4];
            //Throws error if visitors are duplicate


        }
        //Do register visitor logic and call the library method to register a visitor
        return "Pre";
    }
    @Override
    public void error(String s) {
        System.out.println(s);
    }
}
