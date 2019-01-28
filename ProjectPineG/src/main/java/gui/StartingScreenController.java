package gui;

import java.io.IOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.Optional;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;
import network.Client;
import network.ClientParser;
import network.Server;

public class StartingScreenController {

  private ClientParser parser;
  private Stage stage = new Stage();
  private Server server;
  private Client host;
  private Client client;

  public StartingScreenController() {
    parser = new ClientParser(this);

  }


  void setStage(Stage stage) {
    this.stage = stage;
  }

  /**
   * Creates a new Server and the host.
   *
   * @param event - onMouseClicked event if user presses "Create Game" button.
   * @throws IOException if some error occurs while loading fxml file.
   */
  @FXML
  protected void handleCreateGame(MouseEvent event) throws IOException {
    TextInputDialog dialog = new TextInputDialog();
    dialog.setTitle("Login");
    dialog.setHeaderText("Please enter your age.");

    Optional<String> result = dialog.showAndWait();
    if (result.isPresent()) {
      if (validAge(result.get()) && !result.get().isEmpty()) {
        server = new Server();
        host = new Client("localhost");
        parser.setAgeFor(result.get());


      }
    }
  }

    /**
     * Prompts user to input IP adress. Prints error message if input is not valid. Opens loading
     * screen if client could register successfully.
     *
     * @param event - onMouseClicked event if user presses "Join Game" button.
     */
    @FXML
    protected void handleJoinGame (MouseEvent event){
      String ipAdress;
      String ageP;
      Dialog<Pair<String, String>> dialog = new Dialog<>();
      dialog.setTitle("Login");
      dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
      GridPane gridPane = new GridPane();
      gridPane.setHgap(10);
      gridPane.setVgap(10);
      gridPane.setPadding(new Insets(20, 150, 10, 10));

      TextField adress = new TextField();
      TextField age = new TextField();
      adress.setPromptText("Enter your ip adress");
      age.setPromptText("Enter your age");

      gridPane.add(adress, 0, 0);
      gridPane.add(age, 2, 0);

      dialog.getDialogPane().setContent(gridPane);
      dialog.setHeaderText("Please enter your hosts IP adress and your age!");

      Platform.runLater(adress::requestFocus);

      Optional<Pair<String, String>> result = dialog.showAndWait();
      if (result.isPresent()) {
        ipAdress = adress.getText();
        ageP = age.getText();
        if (validAge(ageP)) {
          if (!ipAdress.isEmpty() && !ageP.isEmpty()) {
            try {
              client = new Client(ipAdress);
              parser.setAgeFor(ageP);
            } catch (ConnectException e) {
              Alert alert = new Alert(AlertType.CONFIRMATION,
                  "No host found! Need to create a new game first!", ButtonType.OK);
              alert.showAndWait();

              if (alert.getResult() == ButtonType.YES) {
                return;
              }
            } catch (UnknownHostException e) {
              Alert alert = new Alert(AlertType.CONFIRMATION,
                  "Wrong ip adress!", ButtonType.OK);
              alert.showAndWait();

              if (alert.getResult() == ButtonType.YES) {
                return;
              }

            } catch (IOException e) {
              return;

            }
          }
        }
      }
    }

    /**
     * Gets called it Client could register successfully.
     *
     * @throws IOException if some error occurs while loading fxml file.
     */
    //TODO muss von Server aufgerufen werden.
    public void loadLoadingScreen (Integer playerID) throws IOException {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation((getClass().getResource("/loadingScreen.fxml")));
      Parent dialogue = loader.load();
      Scene scene = new Scene(dialogue);
      Stage stage = new Stage();
      stage.initModality(Modality.APPLICATION_MODAL);
      stage.setScene(scene);
      stage.setResizable(false);
      stage.show();
      LoadingScreenController lController = loader.getController();
      lController.setPlayerID(playerID);
      lController.setStartingStage(this.stage);
    }

    private boolean validAge (String age){
      try {
        Integer.parseInt(age);
        return true;
      } catch (NumberFormatException e) {
        return false;
      }

    }

  }



