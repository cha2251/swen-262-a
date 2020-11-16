import java.util.Scanner;

public class LBMS_TextBased implements ClientType {

    /**
     * This class is the main class handling the creation and maintaince of library
     */
    static String root;
    static RequestParser parser = new RequestParser();

    public void runLibrary(){
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
        if (response != null) {//First argument is the client id
            String clientID = response[0].toLowerCase();
            if (clientID.equals("connect")){ //Case to handle connect without id
                Request request = new ConnectRequest(Library.getInstance());
                System.out.println(request.execute(response));
            }else {

                try {
                    //Second argument is the request type
                    String type = response[1].toLowerCase();
                    //Get the request type with an capitalized first letter
                    type = type.substring(0, 1).toUpperCase() + type.substring(1).toLowerCase();
                    Request request = (Request) Class.forName(type + "Request").getConstructor(Library.class).newInstance(Library.getInstance());
                    System.out.println(request.execute(response));
                } catch (ClassNotFoundException ex) {
                    System.out.println("Try adding your client ID before requests!");
                } catch (Exception e) {
                    System.out.println("Not a valid request");
                }
            }
        }
    }
}
