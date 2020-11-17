import utils.FileUtils;
import utils.StoredType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Library {

    /**
     * Class for the creation and management of the library
     */

    private static String root;
    private static int currentID = 1;
    private static Library instance = new Library();
    int total_visits = 0;
    long length_seconds = 0;
    long average_length_seconds = 0;
    private LocalDateTime modifiedTime;
    private FileUtils utils;
    private NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
    private ArrayList<Request> requests = new ArrayList<>();
    private Catalog catalog = new Catalog();
    private List<Visitor> visitorList = new ArrayList<>();
    private List<Visit> activeVisits = new ArrayList<>();
    private List<Book> queriedBooks = new ArrayList<>();


    private Library() {
    }


    /**
     * Constructor
     */
    public static Library getInstance() {
        return instance;
    }

    /**
     * Refreshes all data stored in lines
     * @param lines
     */
    void reloadData(List<String> lines) {
        for (String line : lines) {
            String[] args = line.split(",");
            switch (StoredType.valueOf(args[1])) {
                case VISITOR:
                    Visitor visitor = new Visitor(args[2], args[3], args[4], args[5], args[6]);
                    visitorList.add(visitor);
                    break;
                case VISIT:
                    if (args.length > 4) {
                        Visit visit = getVisit(args[2]);
                        visit.getElapsedTime(LocalDateTime.parse(args[3], DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                        activeVisits.remove(visit);
                        break;
                    }
                    Visit visit = new Visit(getVisitor(args[2]), LocalDateTime.parse(args[3], DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                    activeVisits.add(visit);
                    break;
                case BORROWED_BOOK:
                    List<String> bookID = Arrays.asList(args[3]);
                    List<Book> books = (List<Book>) catalog.getBooks(bookID);
                    getVisitor(args[2]).borrowBook(books, LocalDateTime.parse(args[4], DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                    break;
                case PAYMENT:
                    Visitor user = getVisitor(args[2]);
                    user.payFine(Double.parseDouble(args[3]));
                    break;
                case RETURN_BOOK:
                    List<String> returnBookID = Arrays.asList(args[3]);
                    List<Book> returnBooks = (List<Book>) catalog.getBooks(returnBookID);
                    getVisitor(args[2]).returnBook(returnBooks, LocalDateTime.parse(args[4], DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                    break;
                case OWNED_BOOK:
                    for (Book b : (List<Book>) catalog.sortPurchasable()) {
                        if (b.getIsbn() == Long.parseLong(args[2])) {
                            catalog.buyBook(b, Integer.parseInt(args[3]));
                        }
                    }
            }
        }
    }

    /**
     * Parses all books from file
     * @param file
     */
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

    /**
     * Start up functions for the library
     * @param root
     */
    public void startUp(String root) {
        Library.root = root;
        FileUtils utils = new FileUtils(root);
        this.utils = utils;
        loadBooks(new File(root + "/data/books.txt"));
        File info = new File(root + "/data/config.properties");
        if (info.exists()) {
            //Load state from files

            String time = utils.readProperty("ModifiedTime");

            modifiedTime = LocalDateTime.parse(time, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            currentID = Integer.parseInt(utils.readProperty("currentID"));
            reloadData(utils.readFromFile(new File(root + "/data/library.lbms")));
        } else {
            //First time startup
            //Time modifications
            modifiedTime = LocalDateTime.of(0, 1, 1, 0, 0);

            //Set default properties
            utils.saveDefaults();

            //Library data file
            utils.CreateFile(root, "/data/library.lbms");
        }
    }

    /**
     * Shut down procedure for Library to save its state and turn off
     */
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

    /**
     * Modifies the time of the library by the specified amount
     * @param days
     * @param hours
     * @return
     */
    public LocalDateTime modifyTime(long days, long hours) {
        //Update modified time
        modifiedTime = modifiedTime.plusDays(days).plusHours(hours);

        //Update in file
        String time = modifiedTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        utils.setProperty("ModifiedTime", time);

        //Return new "time"
        return getLibraryTime();
    }

    /**
     * Parses books from passes from file and adds parsed books to the catalog
     * @param info
     */
    void parseBook(String info) {
        boolean inField = false;

        char[] characters = info.toCharArray();
        ArrayList<String> args = new ArrayList<>();

        StringBuilder current = new StringBuilder();
        for (char character : characters) {
            switch (character) {
                case ',':
                    if (inField) {
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
        String title = args.get(1).replace("\"", "");
        String authorsString = args.get(2).replace("{", "").replace("}", "");
        String publisher = args.get(3);
        String publishedDate = args.get(4);
        int pages = Integer.parseInt(args.get(5));
        Book book = new Book(isbn, title, authorsString, publisher, publishedDate, pages);
        catalog.addBook(book);

    }

    /**
     * checks if library is up, always true
     * @return
     */
    public boolean isUp() {
        return true;
    }

    /**
     * checks if visitor is in part of the visitor list
     * @param visitor
     * @return
     */
    private boolean checkForVisitor(Visitor visitor) {
        return visitorList.contains(visitor);
    }


    /**
     * checks if the visitor is visiting currently
     * @param visitor
     * @return
     */
    private boolean checkActiveVisitors(Visitor visitor) {
        for (Visit visit : activeVisits) {
            if (visit.getVisitor().equals(visitor)) return true;
        }
        return false;
    }

    /**
     * Returns whether a visitor is currently visiting the library
     * @param id
     * @return
     */
    private boolean isVisiting(String id) {
        Visitor inQuestion = getVisitor(id);
        if (inQuestion != null) {
            return checkActiveVisitors(inQuestion);
        }
        return false;
    }

    /**
     * returns whether a visitor has an active visit
     * @param id
     * @return
     */
    private boolean hasActiveVisit(String id) {
        if (getVisitor(id) != null) {
            for (Visit visit : activeVisits) {
                if (visit.getVisitor().getId().equals(id)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * gets info on a visitors current visit
     * @param id
     * @return
     */
    private Visit getVisit(String id) {
        for (Visit visit : activeVisits) {
            if (visit.getVisitor().getId().equals(id)) return visit;
        }
        return null;
    }

    /**
     * Gets info on visitor
     * @param id
     * @return
     */
    private Visitor getVisitor(String id) {
        for (Visitor visitor : visitorList) {
            if (visitor.getId().equals(id)) return visitor;
        }
        return null;
    }

    /**
     * Registors a new visitor
     * @param firstName
     * @param lastName
     * @param address
     * @param phoneNumber
     * @return
     */
    public String registerVisitor(String firstName, String lastName, String address, String phoneNumber) {
        String str = "" + currentID++;
        String id = ("0000000000" + str).substring(str.length());

        Visitor visitor = new Visitor(id, firstName, lastName, address, phoneNumber);

        if (!checkForVisitor(visitor)) {
            //Adding to file
            utils.addEntry(StoredType.VISITOR, new String[]{id, firstName, lastName, address, phoneNumber});
            utils.setProperty("currentID", String.valueOf(currentID));
            visitorList.add(visitor);
            return id;
        }
        currentID--;

        return "register,duplicate;";
    }

    /**
     * deals with a visitor arriving
     * @param id
     * @return
     */
    public String beginVisit(String id) {
        if (getVisitor(id) == null) {
            return "arrive,invalid-id;";
        }
        if (hasActiveVisit(id)) {
            return "arrive,duplicate;";
        }
        Visitor visitor = getVisitor(id);
        LocalDateTime time = getLibraryTime();
        Visit visit = new Visit(visitor, time);
        activeVisits.add(visit);
        utils.addEntry(StoredType.VISIT, new String[]{id, time.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)});

        return "arrive," + id + "," + visit.getStartDate() + "," + visit.getStartTime() + ";";
    }


    /**
     * Remove a visitor without having the system track that they were there
     * @param id
     * @return
     */
    public void removeVisitor(String id){
        Visitor visitor = getVisitor(id);
        Visit visit = getVisit(id);
        activeVisits.remove(visit);
    }

    /**
     * Searches to see if a book is in the catalog, returns the formatted results
     * @param title
     * @param authors
     * @param isbn
     * @param publisher
     * @param sort
     * @param listType
     * @return
     */
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
        for (Book b : results) {
            queriedBooks.add(b);
            str.append(index++).append(",").append(b.toString());
        }
        return str.toString();
    }

    /**
     * Deals with a visitor leaving
     * @param id
     * @return
     */
    public String endVisit(String id) {
        if (getVisitor(id) == null) {
            return "invalid-id;";
        }
        if (!hasActiveVisit(id)) {
            return "invalid-id;";
        }
        Visit visit = getVisit(id);
        activeVisits.remove(visit);
        LocalDateTime currentTime = getLibraryTime();
        //Save action to file
        utils.addEntry(StoredType.VISIT, new String[]{id, currentTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), "DONE"});
        DateTimeFormatter hours = DateTimeFormatter.ofPattern("HH:mm:ss");
        return id + "," + currentTime.format(hours) + "," + visit.getElapsedTime(currentTime) + ";";
    }

    /**
     * Undo a depart request
     * @param id
     */
    public void replaceVisitor(String id) {
        if (getVisitor(id) == null) {
            return ;
        }
        if (!hasActiveVisit(id)) {
            return ;
        }
        Visit visit = getVisit(id);
        activeVisits.add(visit);

    }

    /**
     * Allows a visitor to borrow a book, removing that book from availability
     * @param id
     * @param bookID
     * @return
     */
    public String borrowBook(String id, ArrayList<String> bookID) {
        if (!isVisiting(id)) return "invalid-visitor-id;";
        List<?> tempList = catalog.checkBooks(bookID);
        if (tempList.get(0) instanceof String) return "invalid-book-id,{" + tempList + "};";
        Visitor visitor = getVisitor(id);
        if (visitor.getNumBooksBorrowed() + bookID.size() > 5) return "book-limit-exceeded;";
        if (visitor.getFinesOwed() > 0) return "outstanding-fine," + format.format(visitor.getFinesOwed());
        //Save action to file
        LocalDateTime time = getLibraryTime();
        for (Book b : (List<Book>) tempList) {
            utils.addEntry(StoredType.BORROWED_BOOK,
                    new String[]{id, String.valueOf(b.getIsbn()), time.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)});
        }
        return visitor.borrowBook((List<Book>) tempList, time);
    }

    /**
     * Undo a borrow book request
     * @param id
     * @param bookID
     * @return
     */
    public String undoBorrowBook(String id, ArrayList<String> bookID, ArrayList<String> dates) {
        List<Book> returning = new ArrayList<>();
        Visitor visitor = getVisitor(id);
        queriedBooks.clear();
        for (BorrowedBook b : visitor.findBorrowedBooks()) {
            queriedBooks.add(b.getBook());
        }
        //Query all of the users borrowed books
        List<String> failList = new ArrayList<>();
        for (String bID : bookID) {
            try {
                returning.add(queriedBooks.get(Integer.parseInt(bID)));
            } catch (Exception e) {
                failList.add(bID);
            }
        }
        if (failList.size() > 0) return "Undid Book Borrow";


        visitor.returnBookForUndo(returning, dates);

        return "Undid Book Borrow";
    }

    /**
     * Undo a borrow book request
     * @param id
     * @param bookID
     * @return
     */
    public String redoBorrowBook(String id, ArrayList<String> bookID, ArrayList<String> dates) {
        List<Book> returning = new ArrayList<>();
        Visitor visitor = getVisitor(id);
        queriedBooks.clear();
        for (BorrowedBook b : visitor.findBorrowedBooks()) {
            queriedBooks.add(b.getBook());
        }
        //Query all of the users borrowed books
        List<String> failList = new ArrayList<>();
        for (String bID : bookID) {
            try {
                returning.add(queriedBooks.get(Integer.parseInt(bID)));
            } catch (Exception e) {
                failList.add(bID);
            }
        }
        if (failList.size() > 0) return "Redid Book Borrow";


        visitor.borrowBookForUndo(returning, dates);

        return "Redid Book Borrow";
    }


    /**
     * Allows a book to be bought for an amount of money, removes the book from availability
     * @param bookID
     * @param amount
     * @return
     */
    public String buyBook(ArrayList<String> bookID, int amount) {
        List<String> failList = new ArrayList<>();
        List<Book> toAdd = new ArrayList<>();
        for (String book : bookID) {
            try {
                toAdd.add(queriedBooks.get(Integer.parseInt(book)));
            } catch (Exception e) {
                failList.add(book);
            }
        }
        if (failList.size() > 0) return "invalid-book-id," + failList + ";";
        StringBuilder str = new StringBuilder();
        str.append(toAdd.size()).append(",\n");
        for (Book b : toAdd) {
            catalog.buyBook(b, amount);
            utils.addEntry(StoredType.OWNED_BOOK, new String[]{String.valueOf(b.getIsbn()), String.valueOf(amount)});
            str.append(b.toString()).deleteCharAt(str.length() - 1).append(",").append(amount).append("\n");
        }
        return str.toString();
    }

    /**
     * Undoes a Buy Book request
     * @param bookID
     * @param amount
     * @return
     */
    public String undoBuyBook(ArrayList<String> bookID, int amount){
        List<String> failList = new ArrayList<>();
        List<Book> toAdd = new ArrayList<>();
        for (String book : bookID) {
            try {
                toAdd.add(queriedBooks.get(Integer.parseInt(book)));
            } catch (Exception e) {
                failList.add(book);
            }
        }
        if (failList.size() > 0) return "Undid Buy Book";
        for (Book b : toAdd) {
            catalog.buyBook(b, amount * -1);
        }
        return "Undid Buy Book";
    }

    public void markRequest(Request request) {
        requests.add(request);
    }

    /**
     * Returns a string of the books a visitor has borrowed
     * @param id
     * @return
     */
    public String findBorrowedBooks(String id){
        if (!isVisiting(id)) return "invalid-vsitor-id;";
        queriedBooks.clear();
        Visitor visitor = getVisitor(id);
        String str = "" + visitor.getNumBooksBorrowed();
        int i = 0;
        for (BorrowedBook book : visitor.findBorrowedBooks()) {
            str += "\n" + i++ + "," + book;
            queriedBooks.add(book.getBook());
        }
        str += ";";
        return str;
    }

    /**
     * Uses the visitors id and bookID to return a book back to the library
     * @param id
     * @param bookID
     * @return
     */
    public String returnBook(String id, ArrayList<String> bookID) {
        if (!isVisiting(id)) return "invalid-visitor-id;";
        List<Book> returning = new ArrayList<>();
        Visitor visitor = getVisitor(id);
        queriedBooks.clear();
        for (BorrowedBook b : visitor.findBorrowedBooks()) {
            queriedBooks.add(b.getBook());
        }
        //Query all of the users borrowed books
        List<String> failList = new ArrayList<>();
        for (String bID : bookID) {
            try {
                returning.add(queriedBooks.get(Integer.parseInt(bID)));
            } catch (Exception e) {
                failList.add(bID);
            }
        }
        if (failList.size() > 0) return "invalid-book-id," + failList + ";";
        LocalDateTime time = getLibraryTime();

        for (Book b : returning) {
            utils.addEntry(StoredType.RETURN_BOOK, new String[]{id, String.valueOf(b.getIsbn()), time.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)});
        }

        double fine = visitor.returnBook(returning, time);
        if (fine > 0) return "overdue," + format.format(fine) + ";";
        return "success;";
    }

    public ArrayList<String> borrowedDates(String id, ArrayList<String> bookID){
        if (!isVisiting(id)) return null;
        List<Book> returning = new ArrayList<>();
        Visitor visitor = getVisitor(id);
        queriedBooks.clear();
        for (BorrowedBook b : visitor.findBorrowedBooks()) {
            queriedBooks.add(b.getBook());
        }
        //Query all of the users borrowed books
        List<String> failList = new ArrayList<>();
        for (String bID : bookID) {
            try {
                returning.add(queriedBooks.get(Integer.parseInt(bID)));
            } catch (Exception e) {
                failList.add(bID);
            }
        }
        if (failList.size() > 0) return null;
        return visitor.checkOutDates(returning);
    }

    /**
     * Undo a Return Book Request
     * @param id
     * @param bookID
     * @return
     */
    public String undoReturnBook(String id, ArrayList<String> bookID, ArrayList<String> dates) {
        List<?> tempList = catalog.checkBooks(bookID);
        Visitor visitor = getVisitor(id);
        visitor.borrowBookForUndo((List<Book>) tempList, dates);

        return "Undid Return Request";
    }

    /**
     * Pays the fine owed by a vistor
     * @param id
     * @param amount
     * @return
     */
    public String payFine(String id, String amount) {
        if (!isVisiting(id)) return "invalid-visitor-id;";
        Visitor visitor = getVisitor(id);
        double balance = visitor.getFinesOwed();
        double amt = Double.parseDouble(amount);
        if (amt < 0 || amt > balance)
            return "invalid-amount," + format.format(amt) + "," + format.format(balance) + ";";
        utils.addEntry(StoredType.PAYMENT, new String[]{id, amount});
        balance = visitor.payFine(amt);
        return "success," + format.format(balance) + ";";
    }

    /**
     * Undo payment of a fine
     * @param id
     * @param amount
     * @return
     */
    public String undoPayFine(String id, String amount) {
        if (!isVisiting(id)) return "Undid Pay Fine Request";
        Visitor visitor = getVisitor(id);
        double amt = Double.parseDouble(amount);
        visitor.payFine(amt * -1);
        return "Undid Pay Fine Request";
    }

    /**
     * Generates the report for the past number of days
     * @param days
     * @return
     */
    public String generateReport(int days) {
        String n = ",\n";
        int books = 0;
        int total_visitors = 0;
        int books_purchased = 0;
        int outstanding = 0;
        int fines = 0;
        LocalDateTime current = getLibraryTime();
        List<LibraryEvent> events = readEvents();


        HashMap<String, LocalDateTime> vis = new HashMap<>();
        for (LibraryEvent event : events) {
            if (days == -1 || event.getTime().isAfter(ChronoLocalDate.from(current.minusDays(days))) || event.getTime().isEqual(ChronoLocalDate.from(current.minusDays(days)))) {
                String[] args = event.getRequest();
                switch (StoredType.valueOf(args[1])) {
                    case VISITOR:
                        total_visitors++;
                        break;
                    case VISIT:
                        if (args.length == 5) {
                            if (!vis.containsKey(args[2])) {
                                break;
                            }
                            LocalDateTime start = vis.remove(args[2]);
                            LocalDateTime end = LocalDateTime.parse(args[3], DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                            addToAverage(start, end);
                        } else {
                            total_visits++;
                            vis.put(args[2], LocalDateTime.parse(args[3], DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                        }
                        break;
                    case OWNED_BOOK:
                        books += Integer.parseInt(args[3]);
                        books_purchased++;
                        break;
                    case PAYMENT:
                        fines += Integer.parseInt(args[3]);
                        break;
                }
            }
        }
        DateTimeFormatter customTimeFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        String numberOfHours = String.valueOf((average_length_seconds % 86400) / 3600);
        String numberOfMinutes = String.valueOf(((average_length_seconds % 86400) % 3600) / 60);
        String numberOfSeconds = String.valueOf(((average_length_seconds % 86400) % 3600) % 60);
        String hour = ("00" + numberOfHours).substring(numberOfHours.length());
        String minute = ("00" + numberOfMinutes).substring(numberOfMinutes.length());
        String second = ("00" + numberOfSeconds).substring(numberOfSeconds.length());
        for (Visitor v : visitorList) {
            outstanding += v.getFinesOwed();
        }
        return current.format(customTimeFormat) + n +
                "Number of Books: " + books + n +
                "Number of Visitors: " + total_visitors + n +
                "Average Length of Visit: " + hour + ":" + minute + ":" + second + n +
                "Number of Books purchased: " + books_purchased + n +
                "Fines Collected: " + format.format(fines) + n +
                "Fines Outstanding: " + format.format(outstanding);
    }

    /**
     * returns a list of formatted library events
     * @return
     */
    public List<LibraryEvent> readEvents() {
        List<LibraryEvent> events = new ArrayList<>();
        List<String> raw = utils.readFromFile(new File(root + "/data/library.lbms"));
        for (String s : raw) {
            String[] args = s.split(",");
            events.add(new LibraryEvent(LocalDate.parse(args[0], DateTimeFormatter.ISO_LOCAL_DATE), args));
        }
        return events;
    }

    /**
     * Adds a visitors time spent in library to the calculation for the average length
     * @param start
     * @param end
     */
    private void addToAverage(LocalDateTime start, LocalDateTime end) {
        long seconds = start.until(end, ChronoUnit.SECONDS);
        length_seconds += seconds;
        average_length_seconds = length_seconds / total_visits;
    }
}
