package application;	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        Screens.getMainScreen().show();
    }

    public static void main(String[] args) {
//        Structure.initTheDataList();
       // DataBase.loadData();
        launch(args);
    
    }

}