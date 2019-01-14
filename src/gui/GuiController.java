package gui;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

//test: mousepressed besser als mouseclicked?
//Alle anfragen an Server.

public class GuiController {

  //Client client;
  //Host host;
  GuiParser parser;
  @FXML
  private HBox topHand;
  @FXML
  private HBox bottomHand;
  @FXML
  private Label playerTurn;
  @FXML
  private Button enter;
  @FXML
  private Button endTurn;
  @FXML
  private Button cancelSelection;
  @FXML
  private Button manipulate;
  @FXML
  private Button addToExisting;
  @FXML
  private FlowPane board;
  @FXML
  private ImageView bag;
  @FXML
  private Group player2Hand;
  @FXML
  private Group player3Hand;
  @FXML
  private Group player4Hand;
  private List<ImageView> selectedTiles = new ArrayList<>();
  private List<HBox> placedCombinations = new ArrayList<>();
  private List<Image> hand = new ArrayList<>();
  private TileView tView = new TileView();

  /**
   * funktioniert.
   */
  @FXML
  private void initialize() {
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
  }

  /**
   * checks if selected combination is valid.
   */
  @FXML
  protected void handleEnterComb(MouseEvent event) {
    if (!selectedTiles.isEmpty()) {
      // if (client.send(GuiParser.parseToString(selectedTiles)) == true) {
      placeTiles();
      bag.setDisable(true);
      updateHand();
    }
  }

  @FXML
  protected void handleDrawTile(MouseEvent event) {
    // if (client.send("isValidPlayer").equals(true){
    // client.send("draw");
    updateHand();
    // playerTurn.setText(client.send("getNextPlayerID"));
    disableControl();
    // }
    //
  }

  public void updateHand() {
    // String handTiles = client.receive("hand");
    // hand = tView.createImgs(stack);
    // for (int i = 0; i < hand.size(); i++){
    // tView.createTile(hand.get(i));
    // }
  }

  @FXML
  protected void handleEndTurn(MouseEvent event) {
    //client.endTurn();
    // playerTurn.setText(client.send("getNextPlayerID"));
    disableControl();
  }

  /**
   * funktioniert nur für Tiles die von der Hand kommen. //TODO Tiles auf Hand trennen von Tiles auf
   * Board
   */
  @FXML
  protected void handleTileClick(
      MouseEvent event) {
    ImageView imageV = (ImageView) event.getSource();
    if (topHand.getChildren().contains(imageV) || bottomHand.getChildren()
        .contains(imageV)) { //gilt nur wenn Tile auf der Hand liegt.
      if (imageV.getImage() == null || imageV.getImage().isError()) {
        return;
      }
      if (!selectedTiles.contains(imageV)) {
        imageV.setStyle("-fx-translate-y: -15;");
        TileView.highlightTile(imageV);
        selectedTiles.add(imageV);
      } else {
        imageV.setStyle("-fx-translate-y: 0;");
        imageV.setEffect(null);
        selectedTiles.remove(imageV);

      }
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

  /**
   * for adding to existing combinations.
   */
  @FXML
  protected void handleAddTo(MouseEvent event) {
    if (!selectedTiles.isEmpty()) {
      for (int i = 0; i < board.getChildren().size(); i++) {
        HBox box = (HBox) board.getChildren().get(i);
        Button front = new Button("add here");
        front.setOnMousePressed(event1 -> {
          addToFront(box);
        });
        Button back = new Button("add here");
        back.setOnMousePressed(event2 -> {
          addToBack(box);
        });
        box.getChildren().add(0, front);
        box.getChildren().add(box.getChildren().size(), back);
      }
    }
  }

  /**
   * enables control on tiles on board
   */

  @FXML
  protected void handleManipulate(MouseEvent event) {

    for (int i = 0; i < board.getChildren().size(); i++) {
      HBox box = (HBox) board.getChildren().get(i);
      for (int j = 0; j < box.getChildren().size(); j++) {
        ImageView tile = (ImageView) box.getChildren().get(j);

        tile.setOnMouseClicked(
            e -> {
              if (!selectedTiles.contains(tile)) {
                tile.setStyle("-fx-translate-y: -15;");
                TileView.highlightTile(tile);
                selectedTiles.add(tile);
              } else {
                tile.setStyle("-fx-translate-y: 0");
                tile.setEffect(null);
                selectedTiles.remove(tile);
              }
            });
      }

    }
  }


  /**
   * For placing new combinations on the board. validated in handleEnterComb
   */
  void placeTiles() {
    HBox comb = new HBox();
    for (int i = 0; i < selectedTiles.size(); i++) {
      ImageView tile = selectedTiles.get(i);
      tile.setStyle("-fx-translate-y: 0");
      tile.setEffect(null);
      comb.getChildren().add(tile);
      placedCombinations.add(comb);
    }
    board.getChildren().add(comb);
    selectedTiles.clear();
    updateHand();
    // if (client.send(hand).isEmpty(){
    // TODO öffne Gewinnerfenster
  }

  /**
   * For placing tiles to existing combinations on board. check if new tiles may be laid to existing
   * combination. (4 or 13 max) check if new tiles fit existing combination.
   */
  void addToFront(HBox comb) {
    List<ImageView> combination = new ArrayList<>();   //erstellt liste mit gewünschter neuer kombination.
    for (int i = 1; i < comb.getChildren().size() - 1;
        i++) {   //1 und size-1 weil an diesen Stellen Buttons sind.
      combination.add((ImageView) comb.getChildren().get(i));
    }
    String selectedT = GuiParser.parseToString(selectedTiles);
    String boardTiles = GuiParser.parseToString(combination);
    combination.addAll(0, selectedTiles);
    String combTiles = GuiParser.parseToString(combination);
    //if (client.send("list<" + selectedT + "><" + boardTiles + "><" + combTiles + ">") == true){
    deleteAddToButtons();
    for (int i = selectedTiles.size() - 1; i >= 0;
        i--) {    //soll ja in richtiger reihenfolge eingefügt werden, bei einer Tile ist index get(0)
      ImageView tile = selectedTiles.get(i);
      comb.getChildren().add(0, tile); //added tile vorne in hbox
    }
    disableTileControl(selectedTiles);
    selectedTiles.clear();
    updateBoard(comb);
    updateHand();
  }

  void addToBack(HBox comb) {
    // if (client.send(isValidPlayerBy) == true) {
    List<ImageView> combination = new ArrayList<>();   //erstellt liste mit gewünschter neuer kombination.
    for (int i = 1; i < comb.getChildren().size() - 1; i++) {
      combination.add((ImageView) comb.getChildren().get(i));
    }
    String boardTiles = GuiParser.parseToString(combination);
    String selectedT = GuiParser.parseToString(selectedTiles);
    combination.addAll(selectedTiles);  //selected tiles hinten.
    String combTiles = GuiParser.parseToString(combination);
    //if (client.send("list<" + selectedT + "><" + boardTiles + "><" + combTiles + ">") == true){
    deleteAddToButtons();
    for (int i = 0; i < selectedTiles.size(); i++) { //problem weil tile nach verschieben nicht mehr als selected gilt -> selectedtiles.size verringert sich
      ImageView tile = selectedTiles.get(i);
      System.out.println("selectedsize" + selectedTiles.size());
      System.out.println("combsize" + comb.getChildren().size());
      comb.getChildren().add(comb.getChildren().size(), tile);
    }
    disableTileControl(selectedTiles);
    selectedTiles.clear();
    updateBoard(comb);
    updateHand();
  }

  private void deleteAddToButtons() {
    for (int i = 0; i < board.getChildren().size(); i++) {
      HBox box = (HBox) board.getChildren().get(i);
      box.getChildren().remove(box.getChildren().get(box.getChildren().size() - 1));
      box.getChildren().remove(box.getChildren().get(0));

    }
  }

  /**
   * looks for Hbox in placedCombinations list, deletes old one and enters new one. funktioniert
   * denk ich nicht.
   */
  private void updateBoard(HBox comb) {
    for (int i = 0; i < placedCombinations.size(); i++) {
      if (placedCombinations.get(i).equals(comb)) {
        placedCombinations.remove(i);
        placedCombinations.add(comb);
        System.out.println(placedCombinations);
      }
    }
  }

  /**
   *
   */
  private void disableControl() {
    enter.setDisable(true);
    endTurn.setDisable(true);
    cancelSelection.setDisable(true);
    addToExisting.setDisable(true);
    manipulate.setDisable(true);
    //  for (int i = 0; i < hand.size(); i++) {
    //  ImageView tile = hand.get(i);
    //   disableTileControl(tile);
  }

  /**
   * Disables tile control for player.
   */
  private void disableTileControl(List<ImageView> tiles) {
    for (int i = 0; i < tiles.size(); i++) {
      ImageView tile = tiles.get(i);
      tile.setStyle("-fx-translate-y: 0");
      tile.setEffect(null);
      //  tile.setDisable(true);
      selectedTiles.remove(tile);
    }
  }

}

// TODO Anzeige für wenn ein Player aussteigt.

// TODO Anzeige mit Gewinner (+ Punktestand der anderen Spieler)

//TODO hover funktion für bag



