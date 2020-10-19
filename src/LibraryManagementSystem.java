import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class LibraryManagementSystem {

    public static void main(String[] args) {
        Library library = Library.getInstance();
        library.showMessage();
        String root = System.getProperty("user.dir");
        library.loadBooks(new File(root + "/data/books.txt"));
    }
}
