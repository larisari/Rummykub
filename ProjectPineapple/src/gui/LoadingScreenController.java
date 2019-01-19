package gui;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import network.ClientParser;

public class LoadingScreenController {

  @FXML
  private Button startGame;
  @FXML private AnchorPane loadingScreen;
  private StartingScreenController sC = new StartingScreenController();
  private ClientParser parser = new ClientParser(this);
  private int numberOfPlayers = 1;
  private int JoinedY = 160;

  /**
   * Initializes loadingScreen FXML file.
   * Disables the start button.
   */
  @FXML
  private void initialize() {
    startGame.setDisable(true);
  }

  /**
   * Gets called if at least two Players are present.
   */
  public void enableStart(){
    startGame.setDisable(false);
  }

  /**
   * Gets called if new player joined. Adds "Joined" on loading screen next to the newly joined
   * player.
   */
  public void addJoined(){
    JoinedY += 50;
    Text joined = new Text("Joined");
    joined.setStyle("-fx-text-fill: 18b522; -fx-font-family: 'Franklin Gothic Medium'; -fx-font-size:19; ");
    joined.relocate(497, JoinedY);
    loadingScreen.getChildren().add(joined);
  }

  /**
   * Notifies network of the game start. Closes loading screen and starting screen.
   * @param event - onMouseClicked event if user presses "Start Game" button.
   */
  @FXML
  protected void handleStartGamePressed(MouseEvent event) {
    parser.startGame();
    startGame.getScene().getWindow().hide();
    sC.close();
  }

  /**
   * Opens Game Window.
   * @throws IOException if some error occurs while loading fxml file.
   */
  public void openGameWindow() throws IOException{
    Parent root = FXMLLoader.load(getClass().getResource("clientgui.fxml"));
    Scene scene = new Scene(root);
    Stage stage = new Stage(); //new Stage
    stage.setScene(scene);
    stage.show();
  }
}
