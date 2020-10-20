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
    private LocalDateTime modifiedTime;
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
            modifiedTime = LocalDateTime.of(0,0,0,0,0);
            System.out.println("New startup: " + epoch);
        }

        loadBooks(new File(root + "/data/books.txt"));
    }

    public void shutDown() {
        //Save the current state into files and shut down
        System.out.println("Shutting down");
    }

    public LocalDateTime getLibraryTime() {
        LocalDateTime realTime = LocalDateTime.now();
        long addedHours = modifiedTime.getHour();
        long addedDays = modifiedTime.getDayOfYear();
        return realTime.plusHours(addedHours).plusDays(addedDays);

    }
    private LocalDateTime modifyTime(long days, long hours) {
        modifiedTime = modifiedTime.plusDays(days).plusHours(hours);
        return getLibraryTime();
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


    public LocalDateTime getEpoch(){
        return epoch;
    }

    private void addBook(Book book) {
        catalog.addBook(book);
    }

    private boolean checkForVisitor(Visitor visitor){
        return visitorList.contains(visitor);
    }

    private boolean visitorExists(String id){
        for(Visitor visitor : visitorList){
            if (visitor.getId().equals(id)) return true;
        }
        return false;
    }

    private boolean checkActiveVisitors(Visitor visitor){
        for(Visit visit : activeVisits){
            if (visit.getVisitor().equals(visitor)) return true;
        }
        return false;
    }
    private boolean isVisiting(String id) {
        Visitor inQuestion = getVisitor(id);
        if(inQuestion != null) {
            return checkActiveVisitors(inQuestion);
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
        if(isVisiting(id)) {
            return "duplicate;";
        }
        Visitor visitor = getVisitor(id);
        Visit visit = new Visit(visitor, getLibraryTime());
        activeVisits.add(visit);

        return "arrive,"+id+","+visit.getStartDate()+","+visit.getStartTime()+";";
    }

    public String search(String title, String authors, String isbn, String publisher, String sort) {
        Search search = new BasicSearch(catalog);
        search = new SearchTitle(search, title);
        search = new SearchAuthor(search, authors);
        search = new SearchISBN(search, isbn);
        search = new SearchPublisher(search, publisher);
        List<Book> results = search.result(catalog.sortCatalog());
        return "info," +results.size() + "," + results.toString();
    }
    public String endVisit(String id){
        if (!isVisiting(id))return "invalid-id;";
        Visit visit = getVisit(id);
        activeVisits.remove(visit);
        return id+","+getLibraryTime().format(DateTimeFormatter.ISO_LOCAL_TIME)+","+visit.getElapsedTime(getLibraryTime())+";";
    }

    public String borrowBook(String id, ArrayList<String> bookID){
        if (isVisiting(id)) return "invalid-visitor-id;";
        List<?> tempList = catalog.getBooks(bookID);
        if (tempList.get(0) instanceof String) return "invalid-book-id,{"+tempList+"};";
        Visitor visitor = getVisitor(id);
        if (visitor.getNumBooksBorrowed()+bookID.size() > 5) return "book-limit-exceeded;";
        if (visitor.getFinesOwed() > 0 ) return "outstanding-fine,"+visitor.getFinesOwed();

        return visitor.borrowBook((List<Book>) tempList, modifiedTime);
    }

    public String findBorrowedBooks(String id){
        if (!isVisiting(id)) return "borrowed,invalid-vsitor-id;";
        Visitor visitor = getVisitor(id);
        String str = "borrowed,"+visitor.getNumBooksBorrowed();
        int i = 1;
        for (BorrowedBook book : visitor.findBorrowedBooks()){
            str+="\n"+i+","+book;
            i++;
        }
        str+=";";
        return str;
    }

    public String returnBook(String id, ArrayList<String> bookID){
        if (!isVisiting(id)) return "return,invalid-vsitor-id;";
        return null;
    }
}
