package gui;

import java.net.URISyntaxException;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import network.ClientParser;

public class WinScreenController {

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

  //noch ändern:
  private int p1Points = 0; //ev noch initialisieren? oder reicht setter?
  private int p2Points = 0;
  private int p3Points = 0;
  private int p4Points = 0;
  private ClientParser parser;
  private int numberOfPlayers;
  private MediaPlayer mediaPlayer;

  public WinScreenController() {
    parser = new ClientParser(this);
  }


  public void setNumberOfPlayers(Integer numberOfPlayers){
    this.numberOfPlayers = numberOfPlayers;
  }

  public void setPlayerPoints(List<Integer> points) {
    //TODO sout weg.
    for (Integer i : points){
      System.out.println(i);
    }
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
   * nach anz players label hiden bei winnerScreen: wer hat gewonnen -> namen anpassen bei beiden:
   * wer hat wieviele punkte
   */
  @FXML
  private void initialize() {

  }
    public void setPointsNamesVisible(){
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
    if (p2Points < p1Points) {
      player2.setText("Player 1");
      player2Points.setText(p1Points + " Points");
    } else { //Name ist ja default
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
      //player3 default
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
  public void playMusic(){
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

