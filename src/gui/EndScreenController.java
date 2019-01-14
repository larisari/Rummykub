package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class EndScreenController {

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
   * nach anz players label hiden
   * bei winnerScreen: wer hat gewonnen -> namen anpassen
   * bei beiden: wer hat wieviele punkte
   */
  @FXML
  private void initialize(){
    switch (GuiController.numberOfPlayers){
      case 2:
    }
    //client
  }
}
