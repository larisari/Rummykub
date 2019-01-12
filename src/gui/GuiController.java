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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GuiController {

  //Client client;
  //Host host;
  //List<Tile> hand;
  //TileView tView;
  @FXML
  private Label playerTurn;
  @FXML
  private Button enter;
  private List<ImageView> selectedTiles = new ArrayList<>();
  private iwas placedCombinations;

  List<ImageView> getSelectedTiles() {
    return this.selectedTiles;
  }

  public void setSelectedTiles(List<ImageView> selectedTiles) {
    this.selectedTiles = selectedTiles;
  }

  @FXML
  protected void handleQuit(MouseEvent event) {

    ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
    ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
    Alert alert = new Alert(AlertType.CONFIRMATION, "bla", yes, no);
    alert.setContentText("Do you really want to quit?");
    Optional<ButtonType> result = alert.showAndWait();

    if (result.isPresent() && result.get() == yes) {
      Platform.exit();
    }
    //noch: Alert wenn auf x gedrückt wird.
  }

  @FXML
  protected void handleEnterComb(MouseEvent event) {
    //reformat tiles from imageview to Tile
    // if (client.checkComb(selectedTiles) == true) {
    // placeTiles();
  }

  void placeTiles() {
    //Unterschied ob an existierende Kombination anlegen oder neu legen




  }


  @FXML
  protected void handleDrawTile(MouseEvent event) {
    //client.drawTile();
    //Fallunterscheidung erster Zug - normaler Zug
    //updateHand();
  }

  /**
  public void updateHand() {
    hand = client.getHand();
    for (int i = 0; i < hand.size(); i++) { //kein foreach wegen
      gui.Tile tile = hand.get(i);
      Image tileImage = tView.getImage(tile);
      tView.createTile(tileImage);
    }
    }
**/
    @FXML
    protected void handleEndTurn (MouseEvent event){
      //client.endTurn();
      // playerTurn.setText(client.getNextPlayerName());
    }

    @FXML
    protected void handleTileClick (
        MouseEvent event){ //wirft nullpointer wenn imageview ohne image geklickt wird.
      ImageView imageV = (ImageView) event.getSource();
      if (!selectedTiles.contains(imageV) && (imageV.getImage() != null || !imageV.getImage()
          .isError())) { //Problem mit contains (funkt nicht)
        imageV.setStyle("-fx-translate-y: -15;");
        TileView.highlightTile(imageV);
        selectedTiles.add(imageV);
      } else if (selectedTiles.contains(imageV)) {
        imageV.setStyle("-fx-translate-y: 0;");
        imageV.setEffect(null);
        selectedTiles.remove(imageV);
      } else {
        return;
      }

    }

    @FXML
    protected void handleCancelSel (MouseEvent event){
      for (int i = 0; i < selectedTiles.size(); i++) {
        ImageView imageV = selectedTiles.get(i);
        imageV.setStyle("-fx-translate-y: 0");
        imageV.setEffect(null);
      }
      selectedTiles.clear();

    }

  }

//multi threads für client views?

//Anzeige für wenn ein Player aussteigt.

//Anzeige mit Gewinner (+ Punktestand der anderen Spieler)

//disable enter/endturn button wenn Spieler nicht am Zug ist.


