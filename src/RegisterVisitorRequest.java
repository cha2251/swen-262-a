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
            //Return error if visitors are duplicate
            String result = library.registerVisitor(firstName, lastName, address, phone);
            return result;
        }
        return "register,missing-parameters,{first name,last name,address, phone-number};";
    }
}
