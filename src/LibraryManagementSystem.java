public class LibraryManagementSystem {
    /*
    Main function of the Library Management System, creates an instance of the library and shows any messages
    related to the library
     */
    public static void main(String[] args) {
        Library library = Library.getInstance();
        library.showMessage();
    }
}
