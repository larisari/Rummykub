package gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import network.ClientParser;

public class LoadingScreenController {

  @FXML
  private Button startGame;
  @FXML private AnchorPane loadingScreen;
  @FXML private Text player1Joined;
  @FXML private Text player2Joined;
  @FXML private Text player3Joined;
  @FXML private Text player4Joined;
  private ClientParser parser = new ClientParser(this);
  private int playerID = 0;

  public LoadingScreenController(){
    parser = new ClientParser(this);
  }
  /**
   * Initializes loadingScreen FXML file.
   * Disables the start button.
   */
  @FXML
  private void initialize() {
    player1Joined.setVisible(false);
    player2Joined.setVisible(false);
    player3Joined.setVisible(false);
    player4Joined.setVisible(false);
    startGame.setDisable(true);
  }

  public void setPlayerID(Integer playerID){
    this.playerID = playerID;
  }

  //TODO benachrichtigung an server wenn client loadingscreen schließt.

  /**
   * Gets called if at least two Players are present.
   */
  public void enableStart(){
    if (playerID == 0) {
      startGame.setDisable(false);
    }
  }

  /**
   * Gets called if new player joined. Adds "Joined" on loading screen next to the newly joined
   * player.
   * muss checken der wievielte spieler das ist.
   */
  public void addJoined(Integer numberOfClients){
    if (playerID != 0) {
      startGame.setVisible(false);
    }
    switch (numberOfClients){
      case 0:
        player1Joined.setVisible(true);
        break;
      case 1:
        player1Joined.setVisible(true);
        player2Joined.setVisible(true);
        break;
      case 2:
        player1Joined.setVisible(true);
        player2Joined.setVisible(true);
        player3Joined.setVisible(true);
        break;
      case 3:
        player1Joined.setVisible(true);
        player2Joined.setVisible(true);
        player3Joined.setVisible(true);
        player4Joined.setVisible(true);
        break;
    }
  }

  /**
   * Notifies network of the game start. Closes loading screen and starting screen.
   * @param event - onMouseClicked event if user presses "Start Game" button.
   */
  @FXML
  protected void handleStartGamePressed(MouseEvent event) {
    parser.startGame();
    startGame.getScene().getWindow().hide();
  }


  /**
   * Opens Game Window.
   * @throws IOException if some error occurs while loading fxml file.
   */
  //TODO braucht 2 parameter von Server.
  public void openGameWindow(Integer numberOfPlayers, Integer playerID) throws IOException{
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation((getClass().getResource("/clientgui.fxml")));
    Parent root = loader.load();
    GuiController controller = loader.getController();
    controller.setNumberOfPlayers(numberOfPlayers);
    controller.setPlayerID(playerID);
    controller.setPlayerNames();
    controller.setPlayerBoards();
    Scene scene = new Scene(root);
    Stage stage = new Stage(); //new Stage
    stage.setResizable(false);
    stage.setTitle("RUMMYKUB");
    stage.setScene(scene);
    stage.show();



  }
}
