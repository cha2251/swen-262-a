import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class LBMS_GUI extends  Application implements ClientType{
    private Stage mainStage;

    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;
        stage.setWidth(800);
        stage.setHeight(600);

        AnchorPane tabRow = new AnchorPane();
        TabPane tabPane = new TabPane();
        Button newTabButton = new Button("+");
        newTabButton.setOnAction( e -> tabPane.getTabs().add(createNewPage()));
        AnchorPane.setTopAnchor(tabPane, 5.0);
        AnchorPane.setLeftAnchor(tabPane, 5.0);
        AnchorPane.setRightAnchor(tabPane, 5.0);
        AnchorPane.setTopAnchor(newTabButton, 10.0);
        AnchorPane.setRightAnchor(newTabButton, 10.0);
        //tabPane.setStyle("-fx-padding: 2 0 0 50;");

        Tab tab1 =createNewPage();

        tabPane.getTabs().add(tab1);

        tabRow.getChildren().addAll(tabPane, newTabButton);
        Scene scene = new Scene(tabRow, 300, 200);
        mainStage.setScene(scene);
        mainStage.setTitle("LBMS");
        mainStage.show();
    }

    static String root;

    public void runLibrary(){
        root = System.getProperty("user.dir");
        Library library = Library.getInstance();
        library.startUp(root);
        Application.launch();
    }


    private Tab createNewPage(){
        ClientTab tab = new ClientTab();
        return tab.getTab();
    }
}
