package gui;

import java.io.IOException;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import network.ClientParser;

public class LoadingScreenController {

  @FXML
  private Button startGame;
  @FXML private AnchorPane loadingScreen;
  @FXML private Text player1Joined;
  @FXML private Text player2Joined;
  @FXML private Text player3Joined;
  @FXML private Text player4Joined;
  private ClientParser parser;
  private int playerID = 0;
  private Stage startingStage = new Stage();

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

  void setPlayerID(Integer playerID){
    this.playerID = playerID;
  }

  /**
   * Gets called if at least two Players are present.
   */
  public void enableStart(){
    if (playerID == 0) {
      startGame.setDisable(false);
    }
  }

  void setStartingStage(Stage stage){
    this.startingStage = stage;
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
   */
  @FXML
  protected void handleStartGamePressed() {
    parser.startGame();
    startGame.getScene().getWindow().hide();
  }


  /**
   * Opens Game Window.
   * @throws IOException if some error occurs while loading fxml file.
   */
  public void openGameWindow(Integer numberOfPlayers, Integer playerID) throws IOException{
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation((getClass().getResource("/clientgui.fxml")));
    Parent root = loader.load();
    Scene scene = new Scene(root);
    Stage stage = new Stage(); //new Stage
    stage.setResizable(false);
    stage.setTitle("RUMMYKUB");
    stage.setScene(scene);
    stage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);
    stage.show();
  GuiController controller = loader.getController();
    controller.setNumberOfPlayers(numberOfPlayers);
    controller.setPlayerID(playerID);
    controller.setPlayerNames();
    controller.setPlayerBoards();
    controller.setStage(stage);
    controller.playMusic();
    loadingScreen.getScene().getWindow().hide();
    this.startingStage.close();



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
    } else if (result.isPresent() && result.get() == yes){
      System.exit(0);
    }
  }



}
