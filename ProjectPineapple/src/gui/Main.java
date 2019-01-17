package gui;

import java.util.Optional;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {
//private Host host;

public Main(){

}
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root;
        root = FXMLLoader.load(getClass().getResource("startingScreenController.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/gui/button.css");
        primaryStage.setResizable(false);
        primaryStage.setTitle("RUMMYKUB");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);
    }

    private void closeWindowEvent(WindowEvent event){
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No",
            ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(AlertType.CONFIRMATION, "bla", yes, no);
        alert.setContentText("Do you really want to quit?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == no) {
            event.consume();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
