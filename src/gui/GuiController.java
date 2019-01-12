package gui;


import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

//test: mousepressed besser als mouseclicked?

public class GuiController {

  //Client client;
  //Host host;
  //List<Tile> hand;
  //TileView tView;
  @FXML
  private Label playerTurn;
  @FXML
  private Button enter;
  @FXML
  private FlowPane board;
  private List<ImageView> selectedTiles = new ArrayList<>();
  private List<HBox> placedCombinations = new ArrayList<>();
  private boolean isPlayersTurn;
  private static final int MAX_TILES = 13;

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

  /**
   * checks if selected combination is valid.
   */
  @FXML
  protected void handleEnterComb(MouseEvent event) {
    //reformat tiles from imageview to Tile
    // if (client.checkComb(selectedTiles) == true) {
    placeTiles();
  }

  /**
   * For placing new combinations on the board.
   */
  void placeTiles() {
    HBox comb = new HBox();
    for (int i = 0; i < selectedTiles.size(); i++) {
      ImageView tile = selectedTiles.get(i);
      tile.setStyle("-fx-translate-y: 0");
      tile.setEffect(null);
      tile.setDisable(true);
      comb.getChildren().add(tile);
      placedCombinations.add(comb);
      comb.setOnMouseMoved(event -> {
        comb.setStyle("-fx-background-color: #22CD27;");
      });

      comb.setOnMouseClicked(
          event -> {      //wenn erste Tile geklickt wird vorne anfügen, default: hinten anfügen.
            Point2D point = new Point2D(event.getX(), event.getY());
            if (comb.getChildren().get(0).contains(point)) {
              addToFront(event);
            } else {
              addToBack(event);
            }
          });
    }
    board.getChildren().add(comb);
    selectedTiles.clear();
  }

  /**
   * option to lay tiles in front or at the back of existing combination. For placing tiles to
   * existing combinations on board. check if new tiles may be laid to existing combination. (4 or
   * 13 max) check if new tiles fit existing combination.
   */
  void addToFront(MouseEvent event) {
    // if (isPlayersTurn) {
    if (!selectedTiles.isEmpty()) {
      HBox comb = (HBox) event.getSource();
      if (comb.getChildren().size() <= MAX_TILES) {
        for (int i = 0; i < selectedTiles.size(); i++) {
          ImageView tile = selectedTiles.get(i);
          comb.getChildren().add(0, tile);
          disableTileControl(tile);
        }

      }
    }
  }

  void addToBack(MouseEvent event) {
    if (!selectedTiles.isEmpty()) {
      HBox comb = (HBox) event.getSource();
      if (comb.getChildren().size() <= MAX_TILES) {
        for (int i = 0; i < selectedTiles.size(); i++) {
          ImageView tile = selectedTiles.get(i);
          comb.getChildren().add(comb.getChildren().size(), tile);
          disableTileControl(tile);
        }
      }
    }
  }

  @FXML
  protected void handleDrawTile(MouseEvent event) {
    //client.drawTile();
    //Fallunterscheidung erster Zug - normaler Zug
    //updateHand();
  }

  /**
   * public void updateHand() { hand = client.getHand(); for (int i = 0; i < hand.size(); i++) {
   * //kein foreach wegen gui.Tile tile = hand.get(i); Image tileImage = tView.getImage(tile);
   * tView.createTile(tileImage); } }
   **/
  @FXML
  protected void handleEndTurn(MouseEvent event) {
    //client.endTurn();
    // playerTurn.setText(client.getNextPlayerName());
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
    } else if (selectedTiles.contains(imageV)) {
      imageV.setStyle("-fx-translate-y: 0;");
      imageV.setEffect(null);
      selectedTiles.remove(imageV);
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

  private void disableTileControl(ImageView tile){
    tile.setStyle("-fx-translate-y: 0");
    tile.setEffect(null);
    tile.setDisable(true);
    selectedTiles.remove(tile);
  }

}

//multi threads für client views?

//Anzeige für wenn ein Player aussteigt.

//Anzeige mit Gewinner (+ Punktestand der anderen Spieler)

//disable enter/endturn button wenn Spieler nicht am Zug ist.


