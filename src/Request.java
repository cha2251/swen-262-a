import java.time.LocalDateTime;

public class Request {
    private Visitor visitor;
    private LocalDateTime time;
    private String content;

    public Request(String request) {
        this.time = LocalDateTime.now();
        this.content = request;
        System.out.println("New request: " + request);
    }
}
