package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Optional;

public class StartApp extends Application {

public StartApp(){

}
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/startingScreen.fxml"));
        Parent root = loader.load();
        StartingScreenController controller = loader.getController();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/button.css");
        primaryStage.setResizable(false);
        primaryStage.setTitle("RUMMYKUB");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);
        controller.setStage(primaryStage);
    }

    /**
     * Opens confirmation alert if user tries to exit application by pressing "x".
     * @param event - WindowEvent if user presses "x" icon.
     */
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
