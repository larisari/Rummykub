package gui;

import java.net.URISyntaxException;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import network.ClientParser;

public class LoseScreenController {
  @FXML private Label player1LoserName;
  @FXML private Label player2LoserName;
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


  public LoseScreenController() {
    parser = new ClientParser(this);
  }

  public void setNumberOfPlayers(Integer numberOfPlayers){
    this.numberOfPlayers = numberOfPlayers;
  }

  public void setID(Integer ID){
    this.playerID = ID;
  }

  //wird nie gecalled?
  public void setPlayerPoints(List<Integer> points) {
    //TODO sout weg
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
  public void setPointsNamesVisible(){
    //klappt noch nicht ganz
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
  }

  void setPoints3Players() {
    Loserp1Points.setText(p1Points + " Points");
    Loserp2Points.setText(p2Points + " Points");
    Loserp3Points.setText(p3Points + " Points");
  }

  void setPoints4Players() {
    Loserp1Points.setText(p1Points + " Points");
    Loserp2Points.setText(p2Points + " Points");
    Loserp3Points.setText(p3Points + " Points");
    Loserp4Points.setText(p4Points + " Points");
  }
  public void playMusic(){
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



