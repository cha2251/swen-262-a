import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ClientTab {
    private Tab tab;
    private String clientID;
    private Account account;
    static RequestParser parser = new RequestParser();

    public ClientTab(){
        VBox page = new VBox();
        TextArea output = new TextArea("Welcome to the Library Management System. Type 'connect;' to begin!");
        output.setPrefRowCount(30);
        output.setPrefColumnCount(60);
        output.setWrapText(true);
        output.setEditable(false);

        HBox inputBar = new HBox();
        Button enterButton = new Button("Enter");
        TextField input = new TextField();
        input.setPrefColumnCount(60);
        enterButton.setOnAction( e -> runCommand(input.getText(), output));
        inputBar.getChildren().add(input);
        inputBar.getChildren().add(enterButton);

        page.getChildren().add(output);
        page.getChildren().add(inputBar);

        tab = new Tab("New User" , page);
    }

    public Tab getTab() {
        return tab;
    }

    private void runCommand(String command, TextArea output){
        String current = output.getText();
        current += ""+command+"\n";
        current += ""+handle(command)+"\n";
        output.setText(current);
    }

    public static String handle(String input) {
        String[] response = parser.parseRequest(input);
        if (response != null) {
            //First argument is the request type
            String type = response[0].toLowerCase();
            //Get the request type with an capitalized first letter
            type = type.substring(0, 1).toUpperCase() + type.substring(1).toLowerCase();

            try {
                Request request = (Request) Class.forName(type + "Request").getConstructor(Library.class).newInstance(Library.getInstance());
                return request.execute(response);
            } catch (Exception e) {
                e.printStackTrace();
                return "Not a valid request";
            }
        }
        return "Cannot Parse";
    }
}
