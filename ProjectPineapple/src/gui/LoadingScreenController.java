package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class LoadingScreenController {

  @FXML
  private Button startGame;
  private StartingScreenController sC = new StartingScreenController();

  @FXML
  private void initialize() {

    startGame.setDisable(true);


  }

  @FXML
  protected void handleStartGamePressed(MouseEvent event) throws IOException {
    Stage startingStage = sC.getStage();
    Parent root = FXMLLoader.load(getClass().getResource("clientgui.fxml"));
    Scene scene = new Scene(root);
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    startingStage.close();
    // server.gameStart();

  }
}
