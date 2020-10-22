import java.util.Scanner;

public class LibraryManagementSystem {

    /**
     * This class is the main class handling the creation and maintaince of library
     */
    static String root;
    static RequestParser parser = new RequestParser();

    /**
     * The main function of the system, calls for creation and start up of library,
     * as wellas checking for input
     * @param args
     */
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

    /**
     * Parses and handles requests
     * @param input
     */
    public static void handle(String input) {
        String[] response = parser.parseRequest(input);
        if (response != null) {
            //First argument is the request type
            String type = response[0].toLowerCase();
            //Get the request type with an capitalized first letter
            type = type.substring(0, 1).toUpperCase() + type.substring(1).toLowerCase();

            try {
                Request request = (Request) Class.forName(type + "Request").getConstructor(Library.class).newInstance(Library.getInstance());
                System.out.println(request.execute(response));
            } catch (Exception e) {
                System.out.println("Not a valid request");
                e.printStackTrace();
            }
        }
    }
}
