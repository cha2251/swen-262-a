import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Library {
    private static Library instance = new Library();

    private ArrayList<Book> books = new ArrayList<>();

    private Library(){}

    public static Library getInstance(){
        return instance;
    }

    public void showMessage(){
        System.out.println("In R1-dev branch");
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

    void addBook(Book book) {
        books.add(book);
        System.out.println(book.title + " published by " + book.publisher + " on " + book.publishedDate);
    }
}
