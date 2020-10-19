import java.util.Scanner;

public class LibraryManagementSystem {

    static String root;

    public static void main(String[] args) {
        root = System.getProperty("user.dir");
        Library library = Library.getInstance();
        library.startUp(root);

        Scanner scanner = new Scanner(System.in);
        while (library.isUp()) {
            String input = scanner.nextLine();
            library.handle(input);
        }
    }
}
