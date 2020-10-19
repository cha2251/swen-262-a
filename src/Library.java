import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Library {

    private static String root;
    private static LocalDateTime epoch;

    private static Library instance = new Library();

    private ArrayList<Request> requests = new ArrayList<>();
    private ArrayList<Book> books = new ArrayList<>();
    private RequestParser parser;

    private Library(){}

    private Catalog catalog = new Catalog();

    public static Library getInstance() {
        return instance;
    }

    public void loadBooks(File file) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (line != null) {
                parseBook(line);
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startUp(String root) {
        this.parser = new RequestParser();
        this.root = root;
        File info = new File(root + "/data/info.txt");
        if(info.exists()) {
            //Load state from files
            System.out.println("Restoring state");
        }
        else {
            //First time startup
            epoch = LocalDateTime.now();
            System.out.println("New startup: " + epoch);
        }

        loadBooks(new File(root + "/data/books.txt"));
    }

    public void shutDown() {
        //Save the current state into files and shut down
        System.out.println("Shutting down");
    }


    void parseBook(String info) {
        boolean inField = false;

        char[] characters = info.toCharArray();
        ArrayList<String> args = new ArrayList<>();

        StringBuilder current = new StringBuilder();
        for(char character : characters) {
            switch (character) {
                case ',':
                    if(inField) {
                        current.append(character);
                        break;
                    }
                    args.add(current.toString());
                    current = new StringBuilder();
                    break;
                case '{':
                    inField = true;
                    current.append(character);
                    break;
                case '}':
                    inField = false;
                    current.append(character);
                    break;
                case '"':
                    inField = !inField;
                default:
                    current.append(character);
                    break;
            }
        }
        args.add(current.toString());

        long isbn = Long.parseLong(args.get(0));
        String title = args.get(1).replace("\"","");
        String authorsString = args.get(2).replace("{", "").replace("}", "");
        String publisher = args.get(3);
        String publishedDate = args.get(4);
        Book book = new Book(isbn, title, authorsString, publisher, publishedDate);
        addBook(book);

    }
    public boolean isUp() {
        return true;
    }

    public void handle(String input) {
        String[] response = parser.parseRequest(input);
        if(response!=null) {
            //First argument is the request type
            String type = response[0].toLowerCase();

            //Switch on type of request
            switch (type) {
                case "register":
                    Request register = new RegisterVisitorRequest(this);
                    System.out.println(register.execute(response));
                    break;
                case "arrive":
                    Request arrive = new ArriveRequest(this);
                    System.out.println(arrive.execute(response));
                    break;
                case "depart":
                    Request depart = new DepartRequest(this);
                    System.out.println(depart.execute(response));
                    break;
                case "info":
                    Request info = new InfoRequest(this);
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

    void addBook(Book book) {
        books.add(book);
    }
}
