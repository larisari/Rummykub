package gui;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Group;
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
//Alle anfragen an Server.

public class GuiController {

  //Client client;
  //Host host;
  //List<Tile> hand;
  GuiParser parser;
  @FXML
  private Label playerTurn;
  @FXML
  private Button enter;
  @FXML
  private Button endTurn;
  @FXML
  private Button cancelSelection;
  @FXML
  private FlowPane board;
  @FXML private ImageView bag;
  @FXML private Group player2Hand;
  @FXML private Group player3Hand;
  @FXML private Group player4Hand;
  private List<ImageView> selectedTiles = new ArrayList<>();
  private List<HBox> placedCombinations = new ArrayList<>();
  private boolean isPlayersTurn;
  private static final int MAX_TILES = 13;

  @FXML private void initialize(){
    //int numberPlayers = client.send(getNumberOfPlayers);
    // switch (numberPlayers){
    //case 2:
    //player3Hand.setVisible(false);
    //player4Hand.setVisible(false);
    //break;
    //case 3:
    //player4Hand.setVisible(false);
    //break;
    //}
  }

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
    //TODO Alert wenn auf x gedrückt wird.
  }

  /**
   * checks if selected combination is valid.
   */
  @FXML
  protected void handleEnterComb(MouseEvent event) {
    if (!selectedTiles.isEmpty()) {
      System.out.println(GuiParser.parseToString(selectedTiles));
      // if (client.checkComb(selectedTiles) == true) {
      placeTiles();
      bag.setDisable(true);
    }
  }

  @FXML
  protected void handleDrawTile(MouseEvent event) {
    // if (client.send("isValidPlayer").equals(true){
    // client.send("draw");
    //updateHand();
    // }
    //
  }

   public void updateHand() {
  // String hand = client.receive("drawStack");
     // Hand hand = tView.createImgs(hand);
  // tView.createTile(tileImage);
  // }
   }

  @FXML
  protected void handleEndTurn(MouseEvent event) {
    //client.endTurn();
    // playerTurn.setText(client.send("getNextPlayerID"));
    disableControl();
  }

  @FXML
  protected void handleTileClick(
      MouseEvent event) { //wirft nullpointer wenn imageview ohne image geklickt wird.
    ImageView imageV = (ImageView) event.getSource();
    if (imageV.getImage() == null || imageV.getImage().isError()) {
      return;
    }
    if (!selectedTiles.contains(imageV)) { //Problem mit contains (funkt nicht)
      imageV.setStyle("-fx-translate-y: -15;");
      TileView.highlightTile(imageV);
      selectedTiles.add(imageV);
    } else {
      imageV.setStyle("-fx-translate-y: 0;");
      imageV.setEffect(null);
      selectedTiles.remove(imageV);

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

  @FXML protected void handleManipulate(MouseEvent event){
    //enable tile control for all tiles on hand and on board.
  }
  /**
   * For placing new combinations on the board.
   * validated in handleEnterComb
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
   *
   * For placing tiles to existing combinations on board. check if new tiles may be laid to existing combination. (4 or
   * 13 max) check if new tiles fit existing combination.
   */
  void addToFront(MouseEvent event) {
    // if (client.send(isValidPlayerBy) == true) {
    if (!selectedTiles.isEmpty()) {
      HBox comb = (HBox) event.getSource();
      List<ImageView> combination = new ArrayList<>();   //erstellt liste mit gewünschter neuer kombination.
      //selected tiles vorne
      for (int i = 0; i < comb.getChildren().size(); i++) {
        combination.add((ImageView) comb.getChildren().get(i));
      }
      String selectedT = GuiParser.parseToString(selectedTiles);
      String boardTiles = GuiParser.parseToString(combination);
      combination.addAll(0, selectedTiles);
      String combTiles = GuiParser.parseToString(combination);
      //if (client.send("list<" + selectedT + "><" + boardTiles + "><" + combTiles + ">") == true){
      for (int i = 0; i < selectedTiles.size(); i++) {
        ImageView tile = selectedTiles.get(i);
        comb.getChildren().add(0, tile);
        disableTileControl(tile);
      }

    }
  }

  void addToBack(MouseEvent event) {
    // if (client.send(isValidPlayerBy) == true) {
    if (!selectedTiles.isEmpty()) {
      HBox comb = (HBox) event.getSource();
      List <ImageView> combination = new ArrayList<>();   //erstellt liste mit gewünschter neuer kombination.
      for (int i = 0; i < comb.getChildren().size(); i++){
        combination.add((ImageView)comb.getChildren().get(i));
      }
      String boardTiles = GuiParser.parseToString(combination);
      String selectedT = GuiParser.parseToString(selectedTiles);
      combination.addAll(selectedTiles);  //selected tiles hinten.
      String combTiles = GuiParser.parseToString(combination);
      //if (client.send("list<" + selectedT + "><" + boardTiles + "><" + combTiles + ">") == true){
        for (int i = 0; i < selectedTiles.size(); i++) {
          ImageView tile = selectedTiles.get(i);
          comb.getChildren().add(comb.getChildren().size(), tile);
          disableTileControl(tile);
        }
      }
    }

  /**
   * //TODO buttons überlagern sich wenn disabled
   * Disables control for player.
   */
  private void disableControl() {
    enter.setDisable(true);
    endTurn.setDisable(true);
    cancelSelection.setDisable(true);
    //  for (int i = 0; i < hand.size(); i++) {
    //  ImageView tile = hand.get(i);
    //   disableTileControl(tile);
  }

  /**
   * Disables tile control for player.
   * @param tile
   */
  private void disableTileControl(ImageView tile) {
    tile.setStyle("-fx-translate-y: 0");
    tile.setEffect(null);
    tile.setDisable(true);
    selectedTiles.remove(tile);
  }

}


// TODO Anzeige für wenn ein Player aussteigt.

// TODO Anzeige mit Gewinner (+ Punktestand der anderen Spieler)

//TODO hover funktion für bag



