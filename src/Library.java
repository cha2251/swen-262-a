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

    public static Library getInstance(){
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
        int pages = Integer.parseInt(args.get(5));
        Book book = new Book(isbn, title, authorsString, publisher, publishedDate, pages);
        addBook(book);

    }
    public boolean isUp() {
        return true;
    }

    public void handle(String input) {
        String response = parser.parseRequest(input);
        if(response != "") {
            Request request = new Request(response);
            requests.add(request);
        }
    }

    void addBook(Book book) {
        books.add(book);
    }
}
