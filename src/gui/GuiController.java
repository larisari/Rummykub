package gui;

import gameinfo.tile.Tile;
import gui.Image;
import gameinfo.GIFactory;
import gameinfo.GIGameInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.application.Platform;
import javafx.fxml.FXML;
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
  private Group topBoard;
  @FXML
  private Group rightBoard;
  @FXML
  private Group leftBoard;
  @FXML
  private Label playerTopName;
  @FXML
  private Label playerRightName;
  @FXML
  private Label playerLeftName;
  private List<ImageView> selectedTiles = new ArrayList<>();
  private List<HBox> placedCombinations = new ArrayList<>();
  private List<Image> hand = new ArrayList<>();
  private TileView tView = new TileView();
  private int playerID;
  static int numberOfPlayers;
  private static final int HAND_SPACE = 13;
  private GIGameInfo gameInfo;

  public GuiController() {
    gameInfo = GIFactory.make();
    // playerID = client.getID();
  }

  /**
   * funktioniert.
   */
  @FXML
  private void initialize() {
    numberOfPlayers = gameInfo.getNumberOfPlayers().get();
    switch (numberOfPlayers) {
      case 2:
        rightBoard.setVisible(false);
        leftBoard.setVisible(false);
        break;
      case 3:
        leftBoard.setVisible(false);
        break;
    }
    setPlayerNames();
//while is first turn: manipulate.setDisable(true);
  }

  private void setPlayerNames() {
    switch (playerID) {
      case 1:
        break; //bleibt auf default
      case 2:
        playerTopName.setText("Player 1");
        playerRightName.setText("Player 4");
        playerLeftName.setText("Player 3");
        break;
      case 3:
        playerTopName.setText("Player 4");
        playerRightName.setText("Player 2");
        playerLeftName.setText("Player 1");
        break;
      case 4:
        playerTopName.setText("Player 3");
        playerRightName.setText("Player 1");
        playerLeftName.setText("Player 2");
        break;
    }
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
      String selectedT = GuiParser.parseToString(selectedTiles);
      Optional<Boolean> valid = gameInfo.play(GuiParser.parseStringToTile(selectedT), "1");
      if (valid.get()) {
        // if (client.send(GuiParser.parseToString(selectedTiles)) == true) {
        placeTiles();
        bag.setDisable(true);
        disableTileControl(selectedTiles);
        selectedTiles.clear();
        updateHand();
      }
    }
  }

  @FXML
  protected void handleDrawTile(MouseEvent event) {
    gameInfo.registerBy("1");
    if (gameInfo.isValidPlayerBy("1").get()) {
      String parsedTiles = GuiParser.parseTileToString(gameInfo.drawBy("1"));
      hand = tView.createImgs(parsedTiles);
      for (int i = 0; i < hand.size(); i++) {
        createTile(hand.get(i));
      }

      // if (client.send("isValidPlayer").equals(true){
      // client.send("draw");
      updateHand();
      // playerTurn.setText(client.send("getNextPlayerID"));
      //  disableControl();
      // }
      //
    }
  }

  /**
   * If topHand is full insert in bottomHand.
   */
  void createTile(Image tile) {
    for (int i = 0; i < topHand.getChildren().size(); i++) {
      ImageView imageView = (ImageView) topHand.getChildren().get(i);
      if (imageView.getImage() == null) {
        imageView.setImage(tile);
        return;
      }
    }
    for (int i = 0; i < bottomHand.getChildren().size(); i++) {
      ImageView iView = (ImageView) bottomHand.getChildren().get(i);
      if (iView.getImage() == null) {
        iView.setImage(tile);
        return;
      }
    }

  }

  private void updateHand() {
    refillImageViews(topHand);
    refillImageViews(bottomHand);
    // String handTiles = client.receive(getAllTilesBy);
    // if (handTiles.equals(""){
    // endGame();
    // } else {
    // hand = tView.createImgs(stack);
    // for (int i = 0; i < hand.size(); i++){
    // tView.createTile(hand.get(i));
    // }
    //}
  }

  private void refillImageViews(HBox hand) {
    while (hand.getChildren().size() < HAND_SPACE) {
      ImageView iView = new ImageView();
      iView.setFitWidth(45);
      iView.setFitHeight(65);
      hand.getChildren().add(iView);
    }
  }

  @FXML
  protected void handleEndTurn(MouseEvent event) {
    List<List<Tile>> allCombinations = new ArrayList<>();
    List<ImageView> comb = new ArrayList<>();
    for (int i = 0; i < placedCombinations.size(); i++) {
      HBox box = placedCombinations.get(i);
      for (int j = 0; j < box.getChildren().size(); j++) {
        comb = new ArrayList<>();
        ImageView iView = (ImageView) box.getChildren().get(j);
        comb.add(iView);
      }
      allCombinations.add(GuiParser.parseStringToTile(GuiParser.parseToString(comb)));
    }
 //   if (gameInfo.play(allCombinations, "1").get()) { //TODO enable wenn play List<List<Tile>> format hat. (else auch)
            // if (client.send(boardcombinations)==true){
            //client.endTurn();
            // playerTurn.setText(client.send("getNextPlayerID"));
      disableControl();
  //  } else {
      Alert alert = new Alert(AlertType.CONFIRMATION, "Error! Unable to end turn. Rearrange "
          + "the combinations on the board to valid combinations", ButtonType.OK);
      alert.showAndWait();

      if (alert.getResult() == ButtonType.YES) {
        //do stuff
      }
    }


  /**
   * funktioniert nur für Tiles die von der Hand kommen.
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
//if first turn do not allow
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
    }
    placedCombinations.add(comb);
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
    List<Tile> selectedTls = GuiParser.parseStringToTile(selectedT);
    List<Tile> combinationTiles = GuiParser.parseStringToTile(combTiles);
    List<Tile> boardT = GuiParser.parseStringToTile(boardTiles);
    List<List<Tile>> newCombs = new ArrayList<>();
    newCombs.add(combinationTiles);
    if (gameInfo.play(selectedTls, boardT, newCombs, "1").get()) {

      //if (client.send("list<" + selectedT + "><" + boardTiles + "><" + combTiles + ">") == true){
      deleteAddToButtons();
      for (int j = 0; j < selectedTiles.size(); j++) {
        if (comb.getChildren().contains(selectedTiles.get(j))) {
          return;
        }
      }
      for (int i = selectedTiles.size() - 1; i >= 0;
          i--) {    //soll ja in richtiger reihenfolge eingefügt werden, bei einer Tile ist index get(0)
        ImageView tile = selectedTiles.get(i);
        comb.getChildren().add(0, tile); //added tile vorne in hbox
      }

      disableTileControl(selectedTiles);
      selectedTiles.clear();
      updateHand();
      updateBoard();
    }
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
    List<Tile> selectedTls = GuiParser.parseStringToTile(selectedT);
    List<Tile> combinationTiles = GuiParser.parseStringToTile(combTiles);
    List<Tile> boardT = GuiParser.parseStringToTile(boardTiles);
    List<List<Tile>> newCombs = new ArrayList<>();
    newCombs.add(combinationTiles);
    if (gameInfo.play(selectedTls, boardT, newCombs, "1").get()) {
      //if (client.send("list<" + selectedT + "><" + boardTiles + "><" + combTiles + ">") == true){
      deleteAddToButtons();
      for (int j = 0; j < selectedTiles.size(); j++) {
        if (comb.getChildren().contains(selectedTiles.get(j))) {
          return;
        }
      }
      for (int i = 0; i < selectedTiles.size();
          i++) { //problem weil tile nach verschieben nicht mehr als selected gilt -> selectedtiles.size verringert sich
        ImageView tile = selectedTiles.get(i);
        comb.getChildren().add(comb.getChildren().size(), tile);
      }

      disableTileControl(selectedTiles);
      selectedTiles.clear();
      updateHand();
      updateBoard();
    }
  }


  private void deleteAddToButtons() {
    for (int i = 0; i < board.getChildren().size(); i++) {
      HBox box = (HBox) board.getChildren().get(i);
      box.getChildren().remove(box.getChildren().get(box.getChildren().size() - 1));
      box.getChildren().remove(box.getChildren().get(0));

    }
  }

  /**
   * Deletes unused Hboxes
   */
  private void updateBoard() {
    for (int i = 0; i < board.getChildren().size(); i++) {
      HBox box = (HBox) board.getChildren().get(i);
      if (box.getChildren().isEmpty()) {
        board.getChildren().remove(box);
      }
    }
  }
  /**
   * don't need because Imageviews update themselves automatically in hbox!!!!
   */
  /**
   private void updateBoard(HBox comb) {
   for (int i = 0; i < placedCombinations.size(); i++) {
   if (placedCombinations.get(i).idProperty().equals(comb.idProperty())) {
   System.out.println(placedCombinations.get(i).getChildren());
   placedCombinations.remove(i);
   placedCombinations.add(comb);
   System.out.println(placedCombinations.get(i).getChildren() + "after");
   }
   }
   }
   **/

  /**
   *
   */
  private void disableControl() {
    enter.setDisable(true);
    endTurn.setDisable(true);
    cancelSelection.setDisable(true);
    addToExisting.setDisable(true);
    manipulate.setDisable(true);
    List<ImageView> handTiles = new ArrayList<>();
    for (int i = 0; i < topHand.getChildren().size(); i++) {
      ImageView iView = (ImageView) topHand.getChildren().get(i);
      handTiles.add(iView);
    }
    for (int i = 0; i < bottomHand.getChildren().size(); i++) {
      ImageView iView = (ImageView) bottomHand.getChildren().get(i);
      handTiles.add(iView);
    }
    disableTileControl(handTiles);
  }

  /**
   * Disables tile control for player.
   */
  private void disableTileControl(List<ImageView> tiles) {
    for (int i = 0; i < tiles.size(); i++) {
      ImageView tile = tiles.get(i);
      tile.setStyle("-fx-translate-y: 0");
      tile.setEffect(null);
      tile.setDisable(true);
      selectedTiles.remove(tile);
    }
  }

}

// TODO Anzeige für wenn ein Player aussteigt.

// TODO Anzeige mit Gewinner (+ Punktestand der anderen Spieler)

//TODO hover funktion für bag



