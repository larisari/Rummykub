package gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

  public class GuiController {

    ClientGui cgui;
    //Client client;
    //Host host;
    @FXML
    private Label playerTurn;
    private List<ImageView> selectedTiles = new ArrayList<>();


    @FXML
    protected void handleQuit(MouseEvent event) {

      ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
      ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
      Alert alert = new Alert(AlertType.CONFIRMATION, "bla", yes, no);
      alert.setContentText("Do you really want to quit?");
      Optional<ButtonType> result = alert.showAndWait();

      if (result.get() == yes) {
        Platform.exit();
      }
      //noch: Alert wenn auf x gedrückt wird.
    }

    @FXML
    protected void handleEnterComb(MouseEvent event) {
      //reformat tiles from imageview to Tile
      // client.checkComb(selectedTiles);
      cgui.placeTiles();
    }
/*DELETE
@FXML protected void handleSurrender(MouseEvent event){
  ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
  ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
  Alert alert = new Alert(AlertType.CONFIRMATION, "bla", yes, no);
  alert.setContentText("Do you really want to surrender?");
  Optional<ButtonType> result = alert.showAndWait();

  if (result.get() == yes){
    Platform.exit();
  }
  //surrender = quit? geht in "Lobby" zurück?
}
*/

    @FXML
    protected void handleDrawTile(MouseEvent event) {
      //client.drawTile();
      //Fallunterscheidung erster Zug - normaler Zug
      //updateHand();
    }

    @FXML
    protected void handleEndTurn(MouseEvent event) {
      //client.endTurn();
      // playerTurn.setText(client.getNextPlayerName());
      playerTurn.setText("next player's turn");
    }


    @FXML
    protected void handleTileClick(
        MouseEvent event) { //wirft nullpointer wenn imageview ohne image geklickt wird.
      ImageView imageV = (ImageView) event.getSource();
      if (!selectedTiles.contains(imageV) && (imageV.getImage() != null || !imageV.getImage()
          .isError())) { //Problem mit contains (funkt nicht)
        imageV.setStyle("-fx-translate-y: -15;");
        TileView.highlightTile(imageV);
        selectedTiles.add(imageV);
        return;
      } else if (selectedTiles.contains(imageV)) {
        imageV.setStyle("-fx-translate-y: 0;");
        imageV.setEffect(null);
        selectedTiles.remove(imageV);
        return;
      } else {
        return;
      }

    }

    @FXML
    protected void handleCancelSel(MouseEvent event) {
      for (int i = 0; i < selectedTiles.size(); i++) {
        ImageView imageV = selectedTiles.get(i);
        imageV.setStyle("-fx-translate-y: 0");
        imageV.setEffect(null);
      }
      selectedTiles.clear();


    }

    @FXML
    protected void handleHoverEnter(MouseEvent event) {

    }

    @FXML
    protected void handleEnterPressed(MouseEvent event) {

    }

    @FXML
    protected void handleHoverEndTurn(MouseEvent event) {

    }

    @FXML
    protected void handleEndTurnPressed(MouseEvent event) {

    }

/*public void updateHand(){
  cgui.setHand(client.getHand());
}
*/


  }

//multi threads für client views?

//Anzeige für wenn ein Player aussteigt.

//Anzeige mit Gewinner (+ Punktestand der anderen Spieler)

//disable enter/endturn button wenn Spieler nicht am Zug ist.


