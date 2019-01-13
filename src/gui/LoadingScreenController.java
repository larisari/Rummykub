package gui;

import java.io.IOException;
import java.util.Optional;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class LoadingScreenController {

  @FXML
  private Button startGame;

  @FXML
  private void initialize() {
    startGame.setDisable(true);
  }

  @FXML
  protected void handleStartGamePressed(MouseEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("clientgui.fxml"));
    Scene scene = new Scene(root);
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    // server.gameStart();
//close startingscreen

  }
}
