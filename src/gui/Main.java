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
        Parent root;
        root = FXMLLoader.load(getClass().getResource("clientgui.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/gui/game.css");
        primaryStage.setResizable(false);
        primaryStage.setTitle("RUMMYKUB");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}