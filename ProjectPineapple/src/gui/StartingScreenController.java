package gui;

import java.io.IOException;
import java.net.ConnectException;
import java.util.Optional;
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

public class StartingScreenController {

  @FXML
  private AnchorPane startingS;

  public StartingScreenController(){

  }

  /**
   * Creates a new Server and the host.
   * @param event - onMouseClicked event if user presses "Create Game" button.
   * @throws IOException if some error occurs while loading fxml file.
   */
  @FXML
  protected void handleCreateGame(MouseEvent event) throws IOException {
    Server server = new Server();
    Client host = new Client("localhost");
    loadLoadingScreen();
// TODO wenn Fenster geschlossen wird -> Abbruch für alle gejointen clients.
  }

  /**
   * Prompts user to input IP adress. Prints error message if input is not valid.
   * Opens loading screen if client could register successfully.
   * @param event - onMouseClicked event if user presses "Join Game" button.
   */
  @FXML
  protected void handleJoinGame(MouseEvent event) {
    String ipAdress = "";
    TextInputDialog dialog = new TextInputDialog();
    dialog.setTitle("Login");
    dialog.setHeaderText("Please enter your IP Adress!");
    Button ok = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
    Optional<String> result = dialog.showAndWait();
    if (result.isPresent()) {
      ipAdress = result.get();
      try {
        Client c = new Client(ipAdress);
        loadLoadingScreen();
      } catch (Exception e) {

        return;
      }
    }
  }

  /**
   * Loads loading screen.
   * @throws IOException if some error occurs while loading fxml file.
   */
  private void loadLoadingScreen() throws IOException {
    Parent dialogue = FXMLLoader.load(getClass().getResource("loadingScreen.fxml"));
    Scene scene = new Scene(dialogue);
    Stage stage = new Stage();
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.setScene(scene);
    stage.setResizable(false);
    stage.show();
  }

  /**
   * Closes starting screen window.
   */
  public void close(){
    startingS.getScene().getWindow().hide();
  }
}



