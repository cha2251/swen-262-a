import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.*;
import java.util.Scanner;

public class LBMS_GUI extends  Application{
    private Stage mainStage;

    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;
        TabPane tabPane = new TabPane();

        Tab tab1 = new Tab("New User" , createLoginPage());

        tabPane.getTabs().add(tab1);

        VBox vBox = new VBox(tabPane);
        Scene scene = new Scene(vBox);

        mainStage.setScene(scene);
        mainStage.setTitle("LBMS");
        mainStage.show();
    }

    static String root;
    static RequestParser parser = new RequestParser();

    /**
     * The main function of the system, calls for creation and start up of library,
     * as well as checking for input
     * @param args
     */
    public static void main(String[] args) {
        root = System.getProperty("user.dir");
        Library library = Library.getInstance();
        library.startUp(root);
        Application.launch();
        Scanner scanner = new Scanner(System.in);
        while (library.isUp()) {
            String input = scanner.nextLine();
            handle(input);
        }
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

    private VBox createLoginPage(){
        VBox page = new VBox();
        TextArea output = new TextArea();
        output.setPrefRowCount(30);
        output.setPrefColumnCount(60);
        output.setWrapText(true);
        output.setEditable(false);

        HBox inputBar = new HBox();
        Button enterButton = new Button("Enter");
        TextField input = new TextField();
        enterButton.setOnAction( e -> runCommand(input.getText(), output));
        inputBar.getChildren().add(input);
        inputBar.getChildren().add(enterButton);

        page.getChildren().add(output);
        page.getChildren().add(inputBar);
        return page;
    }

    private void runCommand(String command, TextArea output){
        String current = output.getText();
        current += ""+command+"\n";
        current += ""+handle(command)+"\n";
        output.setText(current);

    }
}
