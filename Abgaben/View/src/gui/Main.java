package gui;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Main extends Application {
//private Host host;

public Main(){

}
    @Override
    public void start(Stage primaryStage) throws Exception{
        //startet fenster -> option hosten oder verbinden.
        //button hosten: Host host = new Host();
        //join host.joinRequest()
        Parent root;
        root = FXMLLoader.load(getClass().getResource("startingScreen.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setResizable(false);
        primaryStage.setTitle("RUMMYKUB");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}