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
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Pair;
import network.Client;
import network.ClientParser;
import network.Server;

/**
 * Controller for starting screen, handles mouse events and other user input for startingScreen.fxml
 * file. Communicates with network via ClientParser.
 */
public class StartingScreenController {

  private ClientParser parser;
  private Stage stage = new Stage();

  /**
   * Initialises ClientParser for communication between network and gui.
   */
  public StartingScreenController() {
    parser = new ClientParser(this);

  }

  /**
   * For forwarding starting screen stage to loading screen.
   *
   * @param stage of starting screen.
   */
  public void setStage(Stage stage) {
    this.stage = stage;
  }

  /**
   * Induces server and host initialisation.
   *
   * @param event - onMouseClicked event if user presses "Create Game".
   */
  @FXML
  private void handleCreateGame(MouseEvent event) {
    TextInputDialog dialog = new TextInputDialog();
    dialog.setTitle("Login");
    dialog.setHeaderText("Please enter your age.");

    Optional<String> result = dialog.showAndWait();
    if (result.isPresent()) {
      if (validAge(result.get()) && !result.get().isEmpty()) {
        Server server = new Server();
        try {
          Client host = new Client("localhost");
        } catch (IOException e) {
          return;
        }
        parser.setAgeFor(result.get());


      }
    }
  }

  /**
   * Prompts user to input IP adress and age. Informs user if input is not valid or if no connection
   * could be established.
   *
   * @param event - onMouseClicked event if user presses "Join Game" button.
   */
  @FXML
  private void handleJoinGame(MouseEvent event) {
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
            Client client = new Client(ipAdress);
            parser.setAgeFor(ageP);
          } catch (ConnectException e) {
            Alert alert = new Alert(AlertType.CONFIRMATION,
                "No host found! Need to create a new game first!", ButtonType.OK);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
              event.consume();
            }
          } catch (UnknownHostException e) {
            Alert alert = new Alert(AlertType.CONFIRMATION,
                "Wrong ip adress!", ButtonType.OK);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
              event.consume();
            }

          } catch (IOException e) {
            event.consume();

          }
        }
      }
    }
  }

  /**
   * Loads loading screen. Gets called if user could register successfully.
   *
   * @param playerID of this gui's client.
   * @throws IOException if some error occurs while loading fxml file.
   */
  public void loadLoadingScreen(Integer playerID) throws IOException {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation((getClass().getResource("/loadingScreen.fxml")));
    Parent dialogue = loader.load();
    Scene scene = new Scene(dialogue);
    Stage stage = new Stage();
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.setScene(scene);
    stage.getScene().getWindow()
        .addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);
    stage.setResizable(false);
    stage.show();
    LoadingScreenController lController = loader.getController();
    lController.setPlayerID(playerID);
    lController.setStartingStage(this.stage);
  }

  /**
   * Opens confirmation alert if user tries to exit application by pressing "x". If user confirms,
   * the application is exited.
   *
   * @param event - WindowEvent if user presses "x" icon.
   */
  private void closeWindowEvent(WindowEvent event) {
    ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
    ButtonType no = new ButtonType("No",
        ButtonBar.ButtonData.CANCEL_CLOSE);
    Alert alert = new Alert(AlertType.CONFIRMATION, "bla", yes, no);
    alert.setContentText("Do you really want to quit the application?");
    Optional<ButtonType> result = alert.showAndWait();

    if (result.isPresent() && result.get() == no) {
      event.consume();
    } else if (result.isPresent() && result.get() == yes) {
      System.exit(0);
    }
  }


  /**
   * For validating age input.
   *
   * @param age to be verified.
   * @return false if input is anything but an Integer.
   */
  private boolean validAge(String age) {
    try {
      int ageP = Integer.parseInt(age);
      if (ageP > 0) {
        return true;
      }
    } catch (NumberFormatException e) {
      return false;
    }
    return false;
  }

}



