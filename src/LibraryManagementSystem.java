import java.util.Scanner;

public class LibraryManagementSystem {

    static String root;
    static RequestParser parser = new RequestParser();

    public static void main(String[] args) {
        root = System.getProperty("user.dir");
        Library library = Library.getInstance();
        library.startUp(root);

        Scanner scanner = new Scanner(System.in);
        while (library.isUp()) {
            String input = scanner.nextLine();
            handle(input);
        }
    }

    public static void handle(String input) {
        String[] response = parser.parseRequest(input);
        if(response!=null) {
            //First argument is the request type
            String type = response[0].toLowerCase();

            //Switch on type of request
            switch (type) {
                case "register":
                    Request register = new RegisterVisitorRequest(Library.getInstance());
                    System.out.println(register.execute(response));
                    break;
                case "arrive":
                    Request arrive = new ArriveRequest(Library.getInstance());
                    System.out.println(arrive.execute(response));
                    break;
                case "depart":
                    Request depart = new DepartRequest(Library.getInstance());
                    System.out.println(depart.execute(response));
                    break;
                case "info":
                    Request info = new InfoRequest(Library.getInstance());
                    System.out.println(info.execute(response));
                    break;
                case "borrow":
                    break;
                case "borrowed":
                    break;
                case "return":
                    break;
                case "pay":
                    break;
                case "search":
                    break;
                case "buy":
                    break;
                case "advance":
                    break;
                case "datetime":
                    break;
                case "report":
                    break;
                default:
                    System.out.println("Not a valid request");
                    break;
            }
        }
    }
}
