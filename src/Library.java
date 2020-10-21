import utils.FileUtils;
import utils.StoredType;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Library {

    private static String root;
    private LocalDateTime modifiedTime;
    private static int currentID = 1;
    private FileUtils utils;

    private static Library instance = new Library();

    private ArrayList<Request> requests = new ArrayList<>();

    private Library(){}

    private Catalog catalog = new Catalog();
    private List<Visitor> visitorList = new ArrayList<>();
    private List<Visit> activeVisits = new ArrayList<>();
    private List<Book> queriedBooks = new ArrayList<>();

    public static Library getInstance() {
        return instance;
    }

    void reloadData(List<String> lines) {
        for(String line : lines) {
            String[] args = line.split(",");
            switch(StoredType.valueOf(args[0])) {
                case VISITOR:
                    Visitor visitor = new Visitor(args[1],args[2],args[3],args[4],args[5]);
                    visitorList.add(visitor);
                    break;
                case VISIT:
                    if(args.length > 3) {
                        Visit visit = getVisit(args[1]);
                        visit.getElapsedTime(LocalDateTime.parse(args[2],DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                        activeVisits.remove(visit);
                        break;
                    }
                    Visit visit = new Visit(getVisitor(args[1]), LocalDateTime.parse(args[2],DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                    activeVisits.add(visit);
                    break;
            }
        }
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
        FileUtils utils = new FileUtils(root);
        this.utils = utils;
        File info = new File(root + "/data/config.properties");
        if(info.exists()) {
            //Load state from files

            String time = utils.readProperty("ModifiedTime");

            modifiedTime = LocalDateTime.parse(time,DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            currentID = Integer.parseInt(utils.readProperty("currentID"));
            reloadData(utils.readFromFile(new File(root + "/data/library.lbms")));
        }
        else {
            //First time startup
            //Time modifications
            modifiedTime = LocalDateTime.of(0,1,1,0,0);

            //Set default properties
            utils.saveDefaults();

            //Library data file
            utils.CreateFile(root, "/data/library.lbms");
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
        //Update modified time
        modifiedTime = modifiedTime.plusDays(days).plusHours(hours);

        //Update in file
        String time = modifiedTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        utils.setProperty("ModifiedTime", time);

        //Return new "time"
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

    private void addBook(Book book) {
        catalog.addBook(book);
    }

    private boolean checkForVisitor(Visitor visitor){
        return visitorList.contains(visitor);
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
    private boolean hasActiveVisit(String id) {
        if (getVisitor(id) != null) {
            for(Visit visit : activeVisits){
                if (visit.getVisitor().getId().equals(id)) {
                    return true;
                }
            }
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
        String str = ""+currentID++;
        String id  = ("0000000000"+str).substring(str.length());

        Visitor visitor = new Visitor(id, firstName, lastName, address, phoneNumber);

        if (!checkForVisitor(visitor)) {
            //Adding to file
            utils.addEntry(StoredType.VISITOR, new String[]{id,firstName,lastName,address,phoneNumber});
            utils.setProperty("currentID", String.valueOf(currentID));
            visitorList.add(visitor);
            return id;
        }
        currentID--;

        return "register,duplicate;";
    }

    public String beginVisit(String id){
        if(getVisitor(id) == null) {
            return "arrive,invalid-id;";
        }
        if(hasActiveVisit(id)) {
            return "arrive,duplicate;";
        }
        Visitor visitor = getVisitor(id);
        System.out.println("Arriving: " + visitor.getId());
        LocalDateTime time = getLibraryTime();
        Visit visit = new Visit(visitor, time);
        System.out.println("Visit: " + visit.getVisitor().getId());
        activeVisits.add(visit);
        utils.addEntry(StoredType.VISIT, new String[]{id,time.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)});

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
        if(getVisitor(id) == null) {
            return "invalid-id;";
        }
        if(!hasActiveVisit(id)) {
            return "invalid-id;";
        }
        Visit visit = getVisit(id);
        activeVisits.remove(visit);
        LocalDateTime currentTime = getLibraryTime();
        utils.addEntry(StoredType.VISIT, new String[]{id,currentTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),"DONE"});
        return id+","+currentTime.format(DateTimeFormatter.ISO_LOCAL_TIME)+","+visit.getElapsedTime(currentTime)+";";
    }

    public String borrowBook(String id, ArrayList<String> bookID){
        if (!isVisiting(id)) return "invalid-visitor-id;";
        List<?> tempList = catalog.getBooks(bookID);
        if (tempList.get(0) instanceof String) return "invalid-book-id,{"+tempList+"};";
        Visitor visitor = getVisitor(id);
        if (visitor.getNumBooksBorrowed()+bookID.size() > 5) return "book-limit-exceeded;";
        if (visitor.getFinesOwed() > 0 ) return "outstanding-fine,"+visitor.getFinesOwed();

        return visitor.borrowBook((List<Book>) tempList, getLibraryTime());
    }

    public void markRequest(Request request) {
        requests.add(request);
    }

    public String findBorrowedBooks(String id){
        if (!isVisiting(id)) return "invalid-vsitor-id;";
        queriedBooks.clear();
        Visitor visitor = getVisitor(id);
        String str = ""+visitor.getNumBooksBorrowed();
        int i = 0;
        for (BorrowedBook book : visitor.findBorrowedBooks()){
            str+="\n"+i+++","+book;
            queriedBooks.add(book.getBook());
        }
        str+=";";
        return str;
    }

    public String returnBook(String id, ArrayList<String> bookID){
        if (!isVisiting(id)) return "invalid-vsitor-id;";
        List<String> failList = new ArrayList<String>();
        for (String bID : bookID) {
            try{
                queriedBooks.get(Integer.parseInt(bID));
            }catch (IndexOutOfBoundsException e){
                failList.add(bID);
            }
        }
        if(failList.size() > 0) return "invalid-book-id,"+failList+";";
        Visitor visitor = getVisitor(id);
        double fine = visitor.returnBook(queriedBooks,getLibraryTime());
        if(fine > 0) return "overdue,"+fine+";";
        return "success;";
    }

    public String payFine(String id, String amount){
        if (!isVisiting(id)) return "invalid-visitor-id;";
        Visitor visitor = getVisitor(id);
        double balance = visitor.getFinesOwed();
        double amt = Double.parseDouble(amount);
        if (amt < 0 || amt > balance) return "invalid-amount,"+amount+","+balance+";";
        balance = visitor.payFine(amt);
        return "success,"+balance+";";
    }
}
