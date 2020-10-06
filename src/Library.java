public class Library {
    private static Library instance = new Library();

    private Library(){}

    public static Library getInstance(){
        return instance;
    }

    public void showMessage(){
        System.out.println("Test");
    }
}
