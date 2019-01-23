package gui;

import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import network.ClientParser;

public class EndScreenController {

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
  private int p1Points;
  private int p2Points;
  private int p3Points;
  private int p4Points;
  private ClientParser parser;
  private int numberOfPlayers = 0;

  public EndScreenController() {
    parser = new ClientParser(this);
  }


  public void setNumberOfPlayers(Integer numberOfPlayers){
    this.numberOfPlayers = numberOfPlayers;
  }

  public void setPlayerPoints(List<Integer> points) {
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
        Loserp3Points.setVisible(false);
        Loserp4Points.setVisible(false);
        player3LoserName.setVisible(false);
        player4LoserName.setVisible(false);

        player3.setVisible(false);
        player4.setVisible(false);
        player3Points.setVisible(false);
        player4Points.setVisible(false);
        setPoints2Players();
        break;
      case 3:
        Loserp4Points.setVisible(false);
        player4LoserName.setVisible(false);
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
    Loserp1Points.setText(p1Points + "");
    Loserp2Points.setText(p2Points + "");
    if (p2Points < p1Points) {
      player2.setText("Player 1");
      player2Points.setText(p1Points + "");
    } else { //Name ist ja default
      player2Points.setText(p2Points + "");
    }
  }

  void setPoints3Players() {
    Loserp1Points.setText(p1Points + "");
    Loserp2Points.setText(p2Points + "");
    Loserp3Points.setText(p3Points + "");
    if (p3Points == 0) {
      player2.setText("Player 1");
      player3.setText("Player 2");
      player2Points.setText(p1Points + "");
      player3Points.setText(p2Points + "");
    }
    if (p2Points == 0) {
      player2.setText("Player 1");
      //player3 default
      player2Points.setText(p1Points + "");
      player3Points.setText(p3Points + "");
    }
    if (p1Points == 0) {
      player2Points.setText(p2Points + "");
      player3Points.setText(p3Points + "");
    }
  }

  void setPoints4Players() {
    Loserp1Points.setText(p1Points + "");
    Loserp2Points.setText(p2Points + "");
    Loserp3Points.setText(p3Points + "");
    Loserp4Points.setText(p4Points + "");
    if (p4Points == 0) {
      player2Points.setText(p2Points + "");
      player3Points.setText(p3Points + "");
      player4Points.setText(p4Points + "");
    } else if (p2Points == 0) {
      player2.setText("Player 1");
      player2Points.setText(p1Points + "");
      player3Points.setText(p3Points + "");
      player4Points.setText(p4Points + "");
    } else if (p3Points == 0) {
      player2.setText("Player 1");
      player3.setText("Player 2");
      player2Points.setText(p1Points + "");
      player3Points.setText(p2Points + "");
      player4Points.setText(p4Points + "");
    } else if (p4Points == 0) {
      player2.setText("Player 1");
      player3.setText("Player 2");
      player4.setText("Player 3");
      player2Points.setText(p1Points + "");
      player3Points.setText(p2Points + "");
      player4Points.setText(p3Points + "");
    }

  }
}

