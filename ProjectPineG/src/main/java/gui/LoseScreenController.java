package gui;

import java.net.URISyntaxException;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import network.ClientParser;

/**
 * Controller for loser screen, handles mouse events and other user input for loserScreen.fxml file.
 * Communicates with network via ClientParser.
 */
public class LoseScreenController extends EndScreen {

  @FXML
  private Label player1LoserName;
  @FXML
  private Label player2LoserName;
  @FXML
  private Label player3LoserName;
  @FXML
  private Label player4LoserName;
  @FXML
  private Label Loserp1Points;
  @FXML
  private Label Loserp2Points;
  @FXML
  private Label Loserp3Points;
  @FXML
  private Label Loserp4Points;
  private int p1Points = 0;
  private int p2Points = 0;
  private int p3Points = 0;
  private int p4Points = 0;
  private int numberOfPlayers = 0;
  private int playerID;
  private ClientParser parser;
  private MediaPlayer mediaPlayer;

  /**
   * Initialises ClientParser for communication between network and gui.
   */
  public LoseScreenController() {
    parser = new ClientParser(this);
  }


  void setNumberOfPlayers(Integer numberOfPlayers) {
    this.numberOfPlayers = numberOfPlayers;
  }

  /**
   * Gets called from GuiController to forward playerID.
   *
   * @param ID - this gui's client id.
   */
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


  void setPointsNamesVisible() {

    switch (numberOfPlayers) {
      case 2:
        Loserp3Points.setVisible(false);
        Loserp4Points.setVisible(false);
        player3LoserName.setVisible(false);
        player4LoserName.setVisible(false);
        setPoints2Players();
        break;
      case 3:
        Loserp4Points.setVisible(false);
        player4LoserName.setVisible(false);
        setPoints3Players();
        break;
      case 4:
        setPoints4Players();
        break;
    }

  }


  void setPoints2Players() {
    Loserp1Points.setText(p1Points + " Points");
    Loserp2Points.setText(p2Points + " Points");
    switch (playerID) {
      case 0:
        player1LoserName.setText("You");
        break;
      case 1:
        player2LoserName.setText("You");
        break;
    }
  }


  void setPoints3Players() {
    Loserp1Points.setText(p1Points + " Points");
    Loserp2Points.setText(p2Points + " Points");
    Loserp3Points.setText(p3Points + " Points");
    switch (playerID) {
      case 0:
        player1LoserName.setText("You");
        break;
      case 1:
        player2LoserName.setText("You");
        break;
      case 2:
        player3LoserName.setText("You");
        break;
    }
  }


  void setPoints4Players() {
    Loserp1Points.setText(p1Points + " Points");
    Loserp2Points.setText(p2Points + " Points");
    Loserp3Points.setText(p3Points + " Points");
    Loserp4Points.setText(p4Points + " Points");
    switch (playerID) {
      case 0:
        player1LoserName.setText("You");
        break;
      case 1:
        player2LoserName.setText("You");
        break;
      case 2:
        player3LoserName.setText("You");
        break;
      case 3:
        player4LoserName.setText("You");
        break;
    }
  }


  void playMusic() {
    Media media = null;
    try {
      media = new Media(getClass().getResource("/audio/wahwahfail.mp3").toURI().toString());
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    mediaPlayer = new MediaPlayer(media);
    mediaPlayer.play();
  }
}



