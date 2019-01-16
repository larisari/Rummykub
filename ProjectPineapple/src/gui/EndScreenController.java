package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class EndScreenController {

  final int p1Points = 1;
  final int p2Points = 2;
  final int p3Points = 3;
  final int p4Points = 4;
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

  /**
   * nach anz players label hiden bei winnerScreen: wer hat gewonnen -> namen anpassen bei beiden:
   * wer hat wieviele punkte
   */
  @FXML
  private void initialize() {
    switch (GuiController.numberOfPlayers) {
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

    //client
  }

  void setPoints2Players() {
    //p1Points = client.receive(getPointsBy(1));
    //p2Points = client.receive(getPointsBy(2));
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
    //p1Points = client.receive(getPointsBy(1));
    // p2Points = client.receive(getPointsBy(2));
    // p3Points = client.receive(getPointsBy(3));
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
    //p1Points = client.receive(getPointsBy(1));
    // p2Points = client.receive(getPointsBy(2));
    // p3Points = client.receive(getPointsBy(3));
    // p4Points = client.receive(getPointsBy(4));
    Loserp1Points.setText(p1Points + "");
    Loserp2Points.setText(p2Points + "");
    Loserp3Points.setText(p3Points + "");
    Loserp4Points.setText(p4Points + "");
    switch (0) {
      case p1Points:
        player2Points.setText(p2Points + "");
        player3Points.setText(p3Points + "");
        player4Points.setText(p4Points + "");
        break;
      case p2Points:
        player2.setText("Player 1");
        player2Points.setText(p1Points + "");
        player3Points.setText(p3Points + "");
        player4Points.setText(p4Points + "");
        break;
      case p3Points:
        player2.setText("Player 1");
        player3.setText("Player 2");
        player2Points.setText(p1Points + "");
        player3Points.setText(p2Points + "");
        player4Points.setText(p4Points + "");
        break;
      case p4Points:
        player2.setText("Player 1");
        player3.setText("Player 2");
        player4.setText("Player 3");
        player2Points.setText(p1Points + "");
        player3Points.setText(p2Points + "");
        player4Points.setText(p3Points + "");
        break;
    }

  }
}

