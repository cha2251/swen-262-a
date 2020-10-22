public class Library {
    private static Library instance = new Library();

    private Library(){}

    private Catalog catalog;

    /*
    returns an instance of the Library object
     */
    public static Library getInstance(){
        return instance;
    }

    /*
    Function for displaying messages, currently just used for testing
     */
    public void showMessage(){
        System.out.println("Test");
    }
}
