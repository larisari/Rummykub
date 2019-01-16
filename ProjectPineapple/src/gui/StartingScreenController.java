package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import network.Client;
import network.Server;

import java.io.IOException;
import java.util.Optional;

public class StartingScreenController {

  @FXML
  private Button createGame;
  @FXML
  private Button joinGame;
  @FXML
  private AnchorPane startingS;
  @FXML
  private AnchorPane loadingScreen; //in loadingscreen

  private int numberOfPlayers;
  private int JoinedY = 160;

  public StartingScreenController() {

  }

  /**
   * Loads loadingScreen after clicking "Create Game".
   */
  @FXML
  protected void handleCreateGame(MouseEvent event) throws IOException {
    Server server = new Server();
    Client host = new Client("localhost");
    loadLoadingScreen();
// TODO wenn Fenster geschlossen wird -> Abbruch für alle gejointen clients.
  }


  @FXML
  protected void handleJoinGame(MouseEvent event) throws IOException {

    // TODO if (host.waitForConnection() == true) { //else Pop up Fenster "Error! Could not connect, try again later" oder "Error! Could not connect, game is full" oder "Error! No game found!"
    String ipAdress;
    TextInputDialog dialogue = new TextInputDialog();
    dialogue.setTitle("Login");
    dialogue.setHeaderText("Please enter your IP Adress!");
    Button ok = (Button) dialogue.getDialogPane().lookupButton(ButtonType.OK);
    // ok.setDisable(true);
    Optional<String> result = dialogue.showAndWait();
    if (result.isPresent()) {
      ipAdress = result.get();
      try {
        // Client c = new Client(ipAdress);
      } catch (Exception e) {

      }
      ok.setDisable(false);
//kein loadingscreen wenn kein game existiert.
      loadLoadingScreen();
      numberOfPlayers++;
      JoinedY += 50;
      Text joined = new Text("Joined");
      joined.setStyle(
              "-fx-text-fill: 18b522; -fx-font-family: 'Franklin Gothic Medium'; -fx-font-size:19; ");
      joined.relocate(497, JoinedY);
      loadingScreen.getChildren().add(joined); // TODO selbes Problem wie bei StartButton disablen. // loading screen existiert nur im loadingscreen controller
    }
  }

  private void loadLoadingScreen() throws IOException {
    Parent dialogue = FXMLLoader.load(getClass().getResource("loadingScreen.fxml"));
    Scene scene = new Scene(dialogue);
    Stage stage = new Stage();
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.setScene(scene);
    stage.setResizable(false);
    stage.show();
  }

  public Stage getStage() {
    Stage startingScreen = (Stage) startingS.getScene().getWindow();
    return startingScreen;
  }
}



