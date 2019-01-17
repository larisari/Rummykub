package gui;

//TODO methoden splitten wenn bei parser methode aufgerufen wird.

//TODO Buttons per default disablen und enablen wenn player am zug ist.


//TODO sachen die für alle angezeigt werden muss im MainThread ausgeführt werden.

import gameinfo.util.GITile;
import gameinfo.util.GITuple;
import gameinfo.GIGameInfo;
import gameinfo.GIFactory;
import gui.util.Image;

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
import network.Client;
import network.ClientParser;
import network.GuiParser;

//test: mousepressed besser als mouseclicked?
//Alle anfragen an Server.

public class GuiController {

  Client client;

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
  @FXML
  private HBox selectionBoard;
  @FXML
  private Button placeOnBoard;

  private List<ImageView> selectedTiles = new ArrayList<>();
  private List<List<ImageView>> selectedCombinations = new ArrayList<>();
  private List<HBox> placedCombinations = new ArrayList<>(); //wird an Server geschickt.
  private List<Image> hand = new ArrayList<>();
  private TileView tView = new TileView();
  private int playerID;
  static int numberOfPlayers;
  private static final int HAND_SPACE = 13;
  private GIGameInfo gameInfo;
  private int turn = 0;
  private final static int MAX_BOXHEIGHT = 68;
  private final static int MAX_BOXWIDTH = 45;
  private boolean isFirstTurn = false;
  private Integer nextPlayerID = 0;

  private ClientParser parser;

  public GuiController() {
    gameInfo = GIFactory.make();
    parser = new ClientParser(this);
  }

  private void getNumberOfPlayers(){
   parser.numberOfPlayers();
  }

  /**
   * wird von Server aufgerufen. antwort auf numberOfPlayers()
   * @param numberPlayers
   */
  public void setNumberOfPlayers(int numberPlayers){
    numberOfPlayers = numberPlayers;
  }

  private void getPlayerID(){
    parser.getPlayerID();
  }

  /**
   * wird vom Server aufgerufen. antwort auf getPlayerID.
   * @param playerID
   */
  public void setPlayerID(Integer playerID){
    this.playerID = playerID;
  }

  private void getNextPlayerID(){
    parser.getNextPlayerID();
  }

  /**
   * wird von Server aufgerufen, antwort auf getNextplayerID
   * @param ID
   */
  public void setNextPlayerID(Integer ID){
    this.nextPlayerID = ID;
  }

  /**
   * funktioniert.
   */
  @FXML
  private void initialize() { ;
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


  @FXML
  protected void handleEnterComb(MouseEvent event) {
    if (!selectedTiles.isEmpty()) {
      List<ImageView> sTiles = new ArrayList<>();
      HBox comb = new HBox();
      comb.prefHeight(MAX_BOXHEIGHT);
      comb.prefWidth(selectedTiles.size() * MAX_BOXWIDTH);
      for (int i = 0; i < selectedTiles.size(); i++) {
        sTiles.add(selectedTiles.get(i));
        comb.getChildren().add(sTiles.get(i));
      }
      selectionBoard.getChildren().add(comb);
      selectedCombinations.add(sTiles);
      cancelSelection();

    }
  }


  /**
   * checks if selected combination is valid.
   */
  @FXML
  protected void handleplaceOnBoard(MouseEvent event) {
    if (!selectionBoard.getChildren().isEmpty()) {
      List<List<ImageView>> allCombinations = new ArrayList<>();
      for (int i = 0; i < selectedCombinations.size(); i++) {
        allCombinations.add(selectedCombinations.get(i));
      }
      parser.play(allCombinations);
      }
    }


  /**
   * wird von Server aufgerufen. antwort auf placeOnBoard == true
   * For placing new combinations on the board. validated in handleEnterComb
   */
  public void placeTiles() {
    System.out.println(selectionBoard.getChildren().size());
    for (int i = 0; i < selectedCombinations.size(); i++) {
      HBox comb = new HBox();
      List<ImageView> combination = selectedCombinations.get(i);
      for (int j = 0; j < combination.size(); j++) {
        ImageView tile = combination.get(j);
        tile.setStyle("-fx-translate-y: 0");
        tile.setEffect(null);
        comb.getChildren().add(tile);
      }
      placedCombinations.add(comb);
      board.getChildren().add(comb);
    }
    selectedCombinations.clear();
    int size = selectionBoard.getChildren().size();
    for (int i = 0; i < size; i++) {
      selectionBoard.getChildren().remove(selectionBoard.getChildren().get(i));
    }
    bag.setDisable(true);
    disableTileControl(selectedTiles);
    selectedTiles.clear();
    updateHand();
    // if (hand.isEmpty(){
    // TODO öffne Gewinnerfenster

  }

  /**
   * check dass bag disabled is für alle anderen
   * @param event
   */
  @FXML
  protected void handleDrawTile(MouseEvent event) {
      parser.draw();
      parser.getNextPlayerID();
    }

  /**
   * wird von Server aufgerufen. antwort auf draw
   * @param tiles
   */
  public void loadTiles(String tiles){
  hand = tView.createImgs(tiles);
  for (int i = 0; i < hand.size(); i++) {
    createTile(hand.get(i));
  }
  bag.setDisable(true);
}

  /**
   * wird von Server aufgerufen. antwort auf getNextPlayerID
   * @param ID
   */
  public void updateNextPlayerName(Integer ID){
    playerTurn.setText("Player " + ID + "'s turn.");
    disableControl();
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
    List<List<ImageView>> allCombinations = new ArrayList<>();
    for (int i = 0; i < placedCombinations.size(); i++) {
      HBox box = placedCombinations.get(i);
      List<ImageView> comb = new ArrayList<>();
      for (int j = 0; j < box.getChildren().size(); j++) {
        ImageView iView = (ImageView) box.getChildren().get(j);
        comb.add(iView);
      }
      allCombinations.add(comb);
    }

      parser.play(allCombinations);
      // if (client.send(boardcombinations)==true){
      //client.endTurn();
      // playerTurn.setText(client.send("getNextPlayerID"));

    }

  /**
   * von Server aufgerufen, antwort auf play = true. (handleEndTurn)
   */
  public void validEndTurn(){
    disableControl();
    cancelSelection();
    parser.getNextPlayerID();
    parser.finishedTurn();
  }

  /**
   * von Server aufgerufen, antwort auf play = false. (handleEndTurn)
   */
  public void errorEndTurn(){
    Alert alert = new Alert(AlertType.CONFIRMATION, "Error! Unable to end turn. Rearrange "
        + "the combinations on the board to valid combinations", ButtonType.OK);
    alert.showAndWait();

    if (alert.getResult() == ButtonType.YES) {

    }
  }


  /**
   * funktioniert nur für Tiles die von der Hand kommen. ne sollte eigentlich für alle
   * funktionieren.
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


  //TODO passt noch nicht. mittlere Tiles von selection werden nicht gecancelled
  @FXML
  protected void handleCancelSel(MouseEvent event) {
    if (!selectionBoard.getChildren().isEmpty()) {
      System.out.println(selectionBoard.getChildren().size() + " selectionboardsize");
      for (int i = 0; i < selectionBoard.getChildren().size(); i++) {
        HBox comb = (HBox) selectionBoard.getChildren().get(i);

        for (int j = comb.getChildren().size(); j >= 0; j--) {
          ImageView tile = (ImageView) comb.getChildren().get(j);
          if (topHand.getChildren().size() <= HAND_SPACE) {
            topHand.getChildren().add(tile);
          } else {
            bottomHand.getChildren().add(tile);
//TODO leere Hboxen aus selectionboard löschen.
          }
        }

      }
      cancelSelection();
    }
  }
  //wird auch vom Server aufgerufen wenn entercomb false zurückgibt.
//TODO noch anpassen
    public void cancelSelection () {
      for (int i = 0; i < selectedTiles.size(); i++) {
        ImageView imageV = selectedTiles.get(i);
        imageV.setStyle("-fx-translate-y: 0");
        imageV.setEffect(null);
      }
      selectedTiles.clear();
    }

    /**
     * enables control on tiles on board
     */

    @FXML
    protected void handleManipulate (MouseEvent event){
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
     * for adding to existing combinations.
     */
    @FXML
    protected void handleAddTo (MouseEvent event){

      if (!selectedTiles.isEmpty()) {
        for (int i = 0; i < board.getChildren().size(); i++) {
          HBox box = (HBox) board.getChildren().get(i);
          Button front = new Button("add here");
          front.setOnMousePressed(event1 -> {
            addToExisting.setDisable(true);
            addToFront(box);
          });
          Button back = new Button("add here");
          back.setOnMousePressed(event2 -> {
            addToExisting.setDisable(true);
            addToBack(box);
          });
          box.getChildren().add(0, front);
          box.getChildren().add(box.getChildren().size(), back);
        }
      }
    }

    //TODO jedes mal wenn methode im ClientParser aufgerufen wird -> dieser ruft je nach ergebnis methode im controller auf.

    /**
     * For placing tiles to existing combinations on board. check if new tiles may be laid to existing
     * combination. (4 or 13 max) check if new tiles fit existing combination.
     */
    void addToFront (HBox comb){
      List<ImageView> combination = new ArrayList<>();   //erstellt liste mit gewünschter neuer kombination.
      for (int i = 1; i < comb.getChildren().size() - 1;
          i++) {   //1 und size-1 weil an diesen Stellen Buttons sind.
        combination.add((ImageView) comb.getChildren().get(i));
      }
      String selectedT = GuiParser.parseToString(selectedTiles);
      String boardTiles = GuiParser.parseToString(combination);
      combination.addAll(0, selectedTiles);
      String combTiles = GuiParser.parseToString(combination);
      List<GITile> selectedTls = GuiParser.parseStringToTile(selectedT);
      List<GITile> combinationTiles = GuiParser.parseStringToTile(combTiles);
      List<GITile> boardT = GuiParser.parseStringToTile(boardTiles);
      List<List<GITile>> newCombs = new ArrayList<>();
      newCombs.add(combinationTiles);
      if (gameInfo.play(selectedTls, boardT, newCombs, 1).get().getSecond()) {

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
        updateHand();
        updateBoard();
      } else {
        deleteAddToButtons();
      }
      addToExisting.setDisable(false);
      cancelSelection();
    }

    void addToBack (HBox comb){
      // if (client.send(isValidPlayerBy) == true) {
      List<ImageView> combination = new ArrayList<>();   //erstellt liste mit gewünschter neuer kombination.
      for (int i = 1; i < comb.getChildren().size() - 1; i++) {
        combination.add((ImageView) comb.getChildren().get(i));
      }
      String boardTiles = GuiParser.parseToString(combination);
      String selectedT = GuiParser.parseToString(selectedTiles);
      combination.addAll(selectedTiles);  //selected tiles hinten.
      String combTiles = GuiParser.parseToString(combination);
      List<GITile> selectedTls = GuiParser.parseStringToTile(selectedT);
      List<GITile> combinationTiles = GuiParser.parseStringToTile(combTiles);
      List<GITile> boardT = GuiParser.parseStringToTile(boardTiles);
      List<List<GITile>> newCombs = new ArrayList<>();
      newCombs.add(combinationTiles);
      if (gameInfo.play(selectedTls, boardT, newCombs, 1).get().getSecond()) {
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
        updateHand();
        updateBoard();
      } else {
        deleteAddToButtons();
      }
      cancelSelection();
      addToExisting.setDisable(false);
    }

    private void deleteAddToButtons () {
      for (int i = 0; i < board.getChildren().size(); i++) {
        HBox box = (HBox) board.getChildren().get(i);
        box.getChildren().remove(box.getChildren().get(box.getChildren().size() - 1));
        box.getChildren().remove(box.getChildren().get(0));

      }
    }

    /**
     * Deletes unused Hboxes
     */
    private void updateBoard () {
      for (int i = 0; i < board.getChildren().size(); i++) {
        HBox box = (HBox) board.getChildren().get(i);
        if (box.getChildren().isEmpty()) {
          board.getChildren().remove(box);
        }
      }
    }

    /**
     *
     */
    private void disableControl () {
      enter.setDisable(true);
      endTurn.setDisable(true);
      cancelSelection.setDisable(true);
      addToExisting.setDisable(true);
      manipulate.setDisable(true);
      placeOnBoard.setDisable(true);
      List<ImageView> handTiles = new ArrayList<>();
      for (int i = 0; i < topHand.getChildren().size(); i++) {
        ImageView iView = (ImageView) topHand.getChildren().get(i);
        handTiles.add(iView);
      }
      for (int i = 0; i < bottomHand.getChildren().size(); i++) {
        ImageView iView = (ImageView) bottomHand.getChildren().get(i);
        handTiles.add(iView);
      }
      for (int i = 0; i < placedCombinations.size(); i++) {
        HBox box = placedCombinations.get(i);
        for (int j = 0; j < box.getChildren().size(); j++) {
          ImageView iView = (ImageView) box.getChildren().get(j);
          handTiles.add(iView);
        }
      }
      disableTileControl(handTiles);
    }

    /**
     * Disables tile control for player.
     */
    private void disableTileControl (List < ImageView > tiles) {
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

//TODO hover funktion für bag



