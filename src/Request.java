public interface Request {
    String execute(String[] args);

    void error(String message);
}
