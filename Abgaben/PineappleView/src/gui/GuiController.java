package gui;

import java.util.Optional;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;

public class GuiController {
  ClientGui cgui;
  Client client;

@FXML protected void handleQuit(MouseEvent event){

  ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
  ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
  Alert alert = new Alert(AlertType.CONFIRMATION, "bla", yes, no);
  alert.setContentText("Do you really want to quit?");
  Optional<ButtonType> result = alert.showAndWait();

  if (result.get() == yes){
    Platform.exit();
  }
  //noch: Alert wenn auf x gedrückt wird.
}

@FXML protected void handleEnterComb(MouseEvent event){
 // client.checkComb();
  cgui.placeTiles();
}

@FXML protected void handleSurrender(MouseEvent event){
  //surrender = quit? geht in "Lobby" zurück?
}


@FXML protected void handleDrawTile(MouseEvent event){
  //client.drawTile();
  //Fallunterscheidung erster Zug - normaler Zug
  updateHand();
}
@FXML protected void handleEndTurn(MouseEvent event){
  //client.endTurn();
}

//wie select tile wenn nur temporary?


public void updateHand(){
  cgui.setHand(client.getHand());
}

}

//multi threads für client views?

//Anzeige für wenn ein Player aussteigt.

//Anzeige mit Gewinner (+ Punktestand der anderen Spieler)

//disable enter/endturn button wenn Spieler nicht am Zug ist.