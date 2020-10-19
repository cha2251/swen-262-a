public class Library {
    private static Library instance = new Library();

    private Library(){}

    private Catalog catalog;

    public static Library getInstance(){
        return instance;
    }

    public void showMessage(){
        System.out.println("In R1-dev branch");
    }
}
