import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Library {

    private static String root;
    private static LocalDateTime epoch;
    private LocalDateTime currentDateTime = epoch;
    private LocalDateTime modifiedTime = currentDateTime;
    private static int currentID = 1;

    private static Library instance = new Library();

    private ArrayList<Request> requests = new ArrayList<>();

    private Library(){}

    private Catalog catalog = new Catalog();
    private List<Visitor> visitorList = new ArrayList<>();
    private List<Visit> activeVisits = new ArrayList<>();

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


    public LocalDateTime getEpoch(){
        return epoch;
    }

    private void addBook(Book book) {
        catalog.addBook(book);
    }

    private boolean checkForVisitor(Visitor visitor){
        return visitorList.contains(visitor);
    }

    private boolean checkForID(String id){
        for(Visitor visitor : visitorList){
            if (visitor.getId().equals(id)) return false;
        }
        return true;
    }

    private boolean checkActiveVisitors(Visitor visitor){
        for(Visit visit : activeVisits){
            if (visit.getVisitor().equals(visitor)) return true;
        }
        return false;
    }

    private Visit getVisit(String id){
        for(Visit visit : activeVisits){
            if (visit.getVisitor().getId().equals(id)) return visit;
        }
        return null;
    }

    private Visitor getVisitor(String id){
        for(Visitor visitor : visitorList){
            if (visitor.getId().equals(id)) return visitor;
        }
        return null;
    }


    public String registerVisitor(String firstName, String lastName, String address, String phoneNumber){
        String str = ""+currentID;
        String id  = ("0000000000"+str).substring(str.length());

        Visitor visitor = new Visitor(id, firstName, lastName, address, phoneNumber);

        if (!checkForVisitor(visitor)){
            visitorList.add(visitor);
            return id;
        }

        return "register,duplicate;";
    }

    public String beginVisit(String id){
        if (checkForID(id)) return "arrive,invalid-id;";
        Visitor visitor = getVisitor(id);
        if (!checkActiveVisitors(visitor)) return "arrive,duplicate;";
        Visit visit = new Visit(visitor, modifiedTime);
        activeVisits.add(visit);

        return "arrive,"+id+","+visit.getStartDate()+","+visit.getStartTime()+";";
    }

    public String search(String title, String[] authors, String isbn, String publisher, String sort) {
        return null;
    }
    public String endVisit(String id){
        if (checkForID(id)) return "depart,invalid-id;";
        Visitor visitor = getVisitor(id);
        if (checkActiveVisitors(visitor)) return "depart,duplicate;";
        Visit visit = getVisit(id);
        activeVisits.remove(visit);

        return "depart,"+id+","+modifiedTime.format(DateTimeFormatter.ISO_LOCAL_TIME)+","+visit.getElapsedTime(modifiedTime)+";";
    }

    public String borrowBook(String id, ArrayList<String> bookID){
        if (checkForID(id)) return "borrow,invalid-vsitor-id;";
        List<?> tempList = catalog.getBooks(bookID);
        if (tempList.get(0) instanceof String) return "borrow,invalid-book-id,{"+tempList+"};";
        Visitor visitor = getVisitor(id);
        if (visitor.getNumBooksBorrowed()+bookID.size() > 5) return "borrow,book-limit-exceeded;";
        if (visitor.getFinesOwed() > 0 ) return "borrow,outstanding-fine,"+visitor.getFinesOwed();

        return visitor.borrowBook((List<Book>) tempList, modifiedTime);
    }

    public String findBorrowedBooks(String id){
        if (checkForID(id)) return "borrowed,invalid-vsitor-id;";
        Visitor visitor = getVisitor(id);
        String str = "borrowed,"+visitor.getNumBooksBorrowed()+"";
        return "borrowed,n,[<nl>[books]";
    }

}
