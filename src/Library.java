import utils.FileUtils;
import utils.StoredType;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
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
            switch(StoredType.valueOf(args[1])) {
                case VISITOR:
                    Visitor visitor = new Visitor(args[2],args[3],args[4],args[5],args[6]);
                    visitorList.add(visitor);
                    break;
                case VISIT:
                    if(args.length > 4) {
                        Visit visit = getVisit(args[2]);
                        visit.getElapsedTime(LocalDateTime.parse(args[3],DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                        activeVisits.remove(visit);
                        break;
                    }
                    Visit visit = new Visit(getVisitor(args[2]), LocalDateTime.parse(args[3],DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                    activeVisits.add(visit);
                    break;
                case BORROWED_BOOK:
                    List<String> bookID = Arrays.asList(args[3]);
                    List<Book> books = (List<Book>) catalog.getBooks(bookID);
                    getVisitor(args[2]).borrowBook(books,LocalDateTime.parse(args[4],DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                    break;
                case PAYMENT:
                    Visitor user = getVisitor(args[2]);
                    user.payFine(Double.parseDouble(args[3]));
                    break;
                case RETURN_BOOK:
                    List<String> returnBookID = Arrays.asList(args[3]);
                    List<Book> returnBooks = (List<Book>) catalog.getBooks(returnBookID);
                    getVisitor(args[2]).returnBook(returnBooks,LocalDateTime.parse(args[4],DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                    break;
                case OWNED_BOOK:
                    for(Book b : (List<Book>) catalog.sortPurchasable()) {
                        if(b.getIsbn() == Long.parseLong(args[2])){
                            catalog.buyBook(b,Integer.parseInt(args[3]));
                        }
                    }
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
        loadBooks(new File(root + "/data/books.txt"));
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
    }

    public void shutDown() {
        //Save the current state into files and shut down
        System.out.println("Shutting down");
    }

    public LocalDateTime getLibraryTime() {
        LocalDateTime realTime = LocalDateTime.now();
        long addedHours = modifiedTime.getHour();
        long addedDays = modifiedTime.getDayOfYear();
        return realTime.plusHours(addedHours).plusDays(addedDays).minusDays(1);

    }
    public LocalDateTime modifyTime(long days, long hours) {
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
        catalog.addBook(book);

    }
    public boolean isUp() {
        return true;
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
        LocalDateTime time = getLibraryTime();
        Visit visit = new Visit(visitor, time);
        activeVisits.add(visit);
        utils.addEntry(StoredType.VISIT, new String[]{id,time.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)});

        return "arrive,"+id+","+visit.getStartDate()+","+visit.getStartTime()+";";
    }

    public String search(String title, String authors, String isbn, String publisher, String sort, BookList listType) {
        Search search = new BasicSearch(catalog, listType);
        search = new SearchTitle(search, title);
        search = new SearchAuthor(search, authors);
        search = new SearchISBN(search, isbn);
        search = new SearchPublisher(search, publisher);
        List<Book> results = search.result(catalog.sortCatalog());

        StringBuilder str = new StringBuilder();
        queriedBooks.clear();
        str.append(results.size()).append(",\n");
        int index = 0;
        for(Book b : results) {
            queriedBooks.add(b);
            str.append(index++).append(",").append(b.toString());
        }
        return str.toString();
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
        //Save action to file
        utils.addEntry(StoredType.VISIT, new String[]{id,currentTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),"DONE"});
        DateTimeFormatter hours  = DateTimeFormatter.ofPattern("HH:mm:ss");
        return id+","+currentTime.format(hours)+","+visit.getElapsedTime(currentTime)+";";
    }

    public String borrowBook(String id, ArrayList<String> bookID){
        if (!isVisiting(id)) return "invalid-visitor-id;";
        List<?> tempList = catalog.getBooks(bookID);
        if (tempList.get(0) instanceof String) return "invalid-book-id,{"+tempList+"};";
        Visitor visitor = getVisitor(id);
        if (visitor.getNumBooksBorrowed()+bookID.size() > 5) return "book-limit-exceeded;";
        if (visitor.getFinesOwed() > 0 ) return "outstanding-fine,"+visitor.getFinesOwed();
        //Save action to file
        LocalDateTime time = getLibraryTime();
        for(Book b : (List<Book>) tempList) {
            utils.addEntry(StoredType.BORROWED_BOOK,
                    new String[]{id,String.valueOf(b.getIsbn()),time.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)});
        }
        return visitor.borrowBook((List<Book>) tempList, time);
    }

    public String buyBook(ArrayList<String> bookID, int amount) {
        List<String> failList = new ArrayList<>();
        List<Book> toAdd = new ArrayList<>();
        for(String book : bookID) {
            try{
                toAdd.add(queriedBooks.get(Integer.parseInt(book)));
            }catch (Exception e){
                failList.add(book);
            }
        }
        if(failList.size() > 0) return "invalid-book-id,"+failList+";";
        StringBuilder str = new StringBuilder();
        str.append(toAdd.size()).append(",\n");
        for(Book b : toAdd) {
            catalog.buyBook(b, amount);
            utils.addEntry(StoredType.OWNED_BOOK, new String[]{String.valueOf(b.getIsbn()),String.valueOf(amount)});
            str.append(b.toString()).deleteCharAt(str.length()-1).append(",").append(amount).append("\n");
        }
        return str.toString();
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
        if (!isVisiting(id)) return "invalid-visitor-id;";
        List<Book> returning = new ArrayList<>();
        Visitor visitor = getVisitor(id);
        queriedBooks.clear();
        for(BorrowedBook b : visitor.findBorrowedBooks()) {
            queriedBooks.add(b.getBook());
        }
        //Query all of the users borrowed books
        List<String> failList = new ArrayList<>();
        for (String bID : bookID) {
            try{
                returning.add(queriedBooks.get(Integer.parseInt(bID)));
            }catch (Exception e){
                failList.add(bID);
            }
        }
        if(failList.size() > 0) return "invalid-book-id,"+failList+";";
        LocalDateTime time = getLibraryTime();

        for(Book b : returning) {
            utils.addEntry(StoredType.RETURN_BOOK, new String[]{id,String.valueOf(b.getIsbn()),time.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)});
        }

        double fine = visitor.returnBook(returning,time);
        if(fine > 0) return "overdue,"+fine+";";
        return "success;";
    }

    public String payFine(String id, String amount){
        if (!isVisiting(id)) return "invalid-visitor-id;";
        Visitor visitor = getVisitor(id);
        double balance = visitor.getFinesOwed();
        double amt = Double.parseDouble(amount);
        if (amt < 0 || amt > balance) return "invalid-amount,"+amount+","+balance+";";
        utils.addEntry(StoredType.PAYMENT, new String[]{id, amount});
        balance = visitor.payFine(amt);
        return "success,"+balance+";";
    }
}
