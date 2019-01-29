package gui;

import java.net.URISyntaxException;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
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
  private int playerID;
  private MediaPlayer mediaPlayer;

  /**
   * Initialises ClientParser for communication between network and gui.
   */
  public WinScreenController() {
    parser = new ClientParser(this);
  }

  @FXML
  private void initialize(){
    if (playerID == 0){
      newGame.setVisible(true);
    }
  }

  void setNumberOfPlayers(Integer numberOfPlayers) {
    this.numberOfPlayers = numberOfPlayers;
  }

  void setID(Integer ID) {
    this.playerID = ID;
  }

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

  void setPoints2Players() {
    if (p2Points > p1Points) {
      player2.setText("Player 1");
      player2Points.setText(p1Points + " Points");
    } else {
      player2Points.setText(p2Points + " Points");
    }
  }

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

  @FXML
  private void handleNewGameL(){
    //TODO
  }

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

