package gui;

import java.net.URISyntaxException;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import network.ClientParser;

/**
 * Controller for winner screen, handles mouse events and other user input for winnerScreen.fxml
 * file. Communicates with network via ClientParser.
 */
public class WinScreenController extends EndScreen {

  @FXML
  private Label player2Points;
  @FXML
  private Label player3Points;
  @FXML
  private Label player4Points;

  @FXML
  private Label player2;
  @FXML
  private Label player3;
  @FXML
  private Label player4;
  @FXML
  private Button newGame;
  private int p1Points = 0;
  private int p2Points = 0;
  private int p3Points = 0;
  private int p4Points = 0;
  private ClientParser parser;
  private int numberOfPlayers;
  private MediaPlayer mediaPlayer;
  private Stage stage;

  /**
   * Initialises ClientParser for communication between network and gui.
   */
  public WinScreenController() {
    parser = new ClientParser(this);
  }

  /**
   * sets "new game" button visible for host.
   *
   * @param playerID of this gui's player.
   */
  void setNewGame(int playerID) {
    if (playerID == 0) {
      newGame.setVisible(true);
    }
  }

  /**
   * For receiving the stage of this controller from the controller of the previous stage.
   *
   * @param thisStage - this controller's stage.
   */
  void setStage(Stage thisStage) {
    this.stage = thisStage;
  }

  /**
   * For receiving the number of players from the controller of the previous stage.
   *
   * @param numberOfPlayers - number of players.
   */
  void setNumberOfPlayers(Integer numberOfPlayers) {
    this.numberOfPlayers = numberOfPlayers;
  }

  /**
   * For receiving the points of all players from the controller of the previous stage.
   *
   * @param points - list of points of all players.
   */
  void setPlayerPoints(List<Integer> points) {

    switch (points.size()) {
      case 2:
        p1Points = points.get(0);
        p2Points = points.get(1);
        break;
      case 3:
        p1Points = points.get(0);
        p2Points = points.get(1);
        p3Points = points.get(2);
        break;
      case 4:
        p1Points = points.get(0);
        p2Points = points.get(1);
        p3Points = points.get(2);
        p4Points = points.get(3);
        break;

    }
  }

  /**
   * Sets points and names visible according to the number of players.
   */
  public void setPointsNamesVisible() {
    switch (numberOfPlayers) {
      case 2:
        player3.setVisible(false);
        player4.setVisible(false);
        player3Points.setVisible(false);
        player4Points.setVisible(false);
        setPoints2Players();
        break;
      case 3:
        player4.setVisible(false);
        player4Points.setVisible(false);
        setPoints3Players();
        break;
      case 4:
        setPoints4Players();
        break;
    }

  }

  /**
   * Sets the points for two players and sets the name of this gui's client to "You".
   */
  void setPoints2Players() {
    if (p2Points > p1Points) {
      player2.setText("Player 1");
      player2Points.setText(p1Points + " Points");
    } else {
      player2Points.setText(p2Points + " Points");
    }
  }

  /**
   * Sets the points for three players and sets the name of this gui's client to "You".
   */
  void setPoints3Players() {
    if (p3Points == 0) {
      player2.setText("Player 1");
      player3.setText("Player 2");
      player2Points.setText(p1Points + " Points");
      player3Points.setText(p2Points + " Points");
    }
    if (p2Points == 0) {
      player2.setText("Player 1");
      player2Points.setText(p1Points + " Points");
      player3Points.setText(p3Points + " Points");
    }
    if (p1Points == 0) {
      player2Points.setText(p2Points + " Points");
      player3Points.setText(p3Points + " Points");
    }
  }

  /**
   * Sets the points for four players and sets the name of this gui's client to "You".
   */
  void setPoints4Players() {
    if (p4Points == 0) {
      player2Points.setText(p2Points + " Points");
      player3Points.setText(p3Points + " Points");
      player4Points.setText(p4Points + " Points");
    } else if (p2Points == 0) {
      player2.setText("Player 1");
      player2Points.setText(p1Points + " Points");
      player3Points.setText(p3Points + " Points");
      player4Points.setText(p4Points + " Points");
    } else if (p3Points == 0) {
      player2.setText("Player 1");
      player3.setText("Player 2");
      player2Points.setText(p1Points + " Points");
      player3Points.setText(p2Points + " Points");
      player4Points.setText(p4Points + " Points");
    } else if (p4Points == 0) {
      player2.setText("Player 1");
      player3.setText("Player 2");
      player4.setText("Player 3");
      player2Points.setText(p1Points + " Points");
      player3Points.setText(p2Points + " Points");
      player4Points.setText(p3Points + " Points");
    }

  }

  /**
   * Handles user pressing "new game" button. Requests new game to be opened. Closes winner screen.
   */
  @FXML
  private void handleNewGame() {
    parser.newGame();
    stage.close();

  }

  /**
   * Loads music and plays it once.
   */
  void playMusic() {
    Media media = null;
    try {
      media = new Media(getClass().getResource("/audio/fanfare.mp3").toURI().toString());
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    mediaPlayer = new MediaPlayer(media);
    mediaPlayer.play();
  }

}

