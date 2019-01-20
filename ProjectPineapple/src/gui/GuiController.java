package gui;


//TODO Buttons per default disablen und enablen wenn player am zug ist.

//TODO sachen die für alle angezeigt werden muss im MainThread ausgeführt werden.

import gui.util.Image;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
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
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import network.ClientParser;

//test: mousepressed besser als mouseclicked?
//Alle anfragen an Server.

public class GuiController {

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
  @FXML private Button swapJoker;

  private List<ImageView> selectedTiles = new ArrayList<>();
  private List<List<ImageView>> selectedCombinations = new ArrayList<>();
  private List<HBox> placedCombinations = new ArrayList<>(); //wird an Server geschickt.
  private List<Image> hand = new ArrayList<>();
  private TileView tView = new TileView();
  private int playerID;
  static int numberOfPlayers;
  private final static int HAND_SPACE = 13;
  private int turn = 0;
  private final static int MAX_BOXHEIGHT = 68;
  private final static int MAX_BOXWIDTH = 45;
  private final static int MAX_IVIEW_HEIGHT = 65;
  private Integer nextPlayerID = 0;
  private ImageView tile = new ImageView();
  private ImageView joker = new ImageView();
  private HBox boardComb = new HBox();

  private ClientParser parser;

  /**
   * For handling mouse events and other user interactions.
   * Interface between network and view.
   */
  public GuiController() {
    parser = new ClientParser(this);
    getClientID();
  }

  /**
   * Requests the current number of players from the network.
   */
  private void getNumberOfPlayers() {
    parser.numberOfPlayers();
  }

  /**
   * Gets called by network. Sets the number of players.
   */
  public void setNumberOfPlayers(int numberPlayers) {
    numberOfPlayers = numberPlayers;
  }

  /**
   * Requests the user's player ID from the network.
   */
  private void getClientID() {
    parser.getPlayerID();
  }

  /**
   * Gets called by network. Sets the player ID.
   */
  public void setPlayerID(Integer playerID) {
    this.playerID = playerID;
  }


  public Integer getPlayerID(){
    return this.playerID;
  }

  /**
   * Requests the next player's ID from the network.
   */
  private void getNextPlayerID() {
    parser.getNextPlayerID();
  }

  /**
   * Gets called by network. Sets the next player's ID.
   */
  public void setNextPlayerID(Integer ID) {
    this.nextPlayerID = ID;
  }

  /**
   * Initializes FXML file.
   * Sets player boards invisible according to the number of players.
   */
  @FXML
  private void initialize() {
    ;
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


  /**
   * Sets player names on player boards, according to each player.
   */
  private void setPlayerNames() {
    switch (playerID) {
      case 0:
        break; //bleibt auf default
      case 1:
        playerTopName.setText("Player 1");
        playerRightName.setText("Player 4");
        playerLeftName.setText("Player 3");
        break;
      case 2:
        playerTopName.setText("Player 4");
        playerRightName.setText("Player 2");
        playerLeftName.setText("Player 1");
        break;
      case 3:
        playerTopName.setText("Player 3");
        playerRightName.setText("Player 1");
        playerLeftName.setText("Player 2");
        break;
    }
  }

  /**
   * Opens confirmation alert window if user presses "Quit" button.
   * @param event - onMouseClicked event if user presses "Quit" button.
   */
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
   * Moves selected tiles to selection board.
   * @param event - onMouseClicked event if user presses "Enter new Selection" button.
   */
  @FXML
  protected void handleEnterComb(MouseEvent event) {
    for (int i = 0; i < selectedTiles.size(); i++) {
      ImageView tile = selectedTiles.get(i);
      if (!topHand.getChildren().contains(tile) && !bottomHand.getChildren().contains(tile)) {
        moveTiles(selectedTiles);
        return;
      }
      updateBoard();
    }
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
      cancelSelEffect();

    }
  }


  /**
   * Checks if tile combinations on the selection board are valid.
   * @param event - onMouseClicked event if user presses "Place Selection" button
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
   * Places tiles combination on the main board. Disables the bag.
   * Checks if the player's hand is now empty.
   * @throws IOException
   */
  public void placeTiles() throws IOException {

    for (int i = 0; i < selectedCombinations.size(); i++) {
      List<ImageView> combination = selectedCombinations.get(i);
      moveTiles(combination);
    }
    bag.setDisable(true);
    cancelSelection();
    updateBoard();

    if (topHand.getChildren().isEmpty() && bottomHand.getChildren().isEmpty()) {
      openWinScreen();
      parser.notifyWin();
    }
  }

  /**
   * Gets called if user has won.
   * @throws IOException if some error occurs while loading fxml file.
   */
  public void openWinScreen() throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("winnerScreen.fxml"));
    Scene scene = new Scene(root);
    Stage stage = new Stage();
    stage.setScene(scene);
    stage.setResizable(false);
    stage.show();
  }

  /**
   * Gets called if user has lost.
   * @throws IOException if some error occurs while loading fxml file.
   */
  public void openLoserScreen() throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("loserScreen.fxml"));
    Scene scene = new Scene(root);
    Stage stage = new Stage();
    stage.setScene(scene);
    stage.setResizable(false);
    stage.show();
  }

  /**
   * Creates a new combination on the main board with the given List of tiles.
   * @param combination to be added to the board.
   */
  private void moveTiles(List<ImageView> combination) {
    HBox comb = new HBox();
    for (int j = 0; j < combination.size(); j++) {
      ImageView tile = combination.get(j);
      tile.setStyle("-fx-translate-y: 0");
      tile.setEffect(null);
      tile.setDisable(true);
      comb.getChildren().add(tile);
    }
    placedCombinations.add(comb);
    board.getChildren().add(comb);
    deselectTiles(selectedTiles); //löscht auch tiles aus selectedTiles
  }


  /**
   * Requests tiles to be drawn from the bag.
   * Requests the next player's ID.
   * @param event - onMouseClicked event if user clicks on the bag.
   */
  @FXML
  protected void handleDrawTile(MouseEvent event) {
    parser.draw();
    parser.getNextPlayerID();
  }

  /**
   * Gets called if user may draw from the bag. Loads the tiles and disables the bag.
   * @param tiles to be loaded.
   */
  public void loadTiles(String tiles) {
    hand = tView.createImgs(tiles);
    for (int i = 0; i < hand.size(); i++) {
      createTile(hand.get(i));
    }
    bag.setDisable(true);
  }

  /**
   * Updates the text of the next player label.
   * @param ID of the next player.
   */
  public void updateNextPlayerName(Integer ID) {
    playerTurn.setText("Player " + ID + "'s turn.");
    disableControl();
  }

  /**
   * Add a tile to the player's hand.
   * @param tile to be added to the player's hand.
   */
  void createTile(Image tile) {
    for (int i = topHand.getChildren().size(); i < HAND_SPACE; i++) {
      ImageView imageView = new ImageView();
      imageView.setFitHeight(MAX_IVIEW_HEIGHT);
      imageView.setFitWidth(MAX_BOXWIDTH);
      imageView.setImage(tile);
      topHand.getChildren().add(imageView);
      onTileClicked(imageView);
      return;
    }
    for (int i = bottomHand.getChildren().size(); i < HAND_SPACE; i++) {
      ImageView imageView = new ImageView();
      imageView.setFitHeight(MAX_IVIEW_HEIGHT);
      imageView.setFitWidth(MAX_BOXWIDTH);
      imageView.setImage(tile);
      bottomHand.getChildren().add(imageView);
      onTileClicked(imageView);
      return;
    }

  }

  /**
   * Checks if all combinations on the main board are valid.
   * @param event - onMouseClicked event if user presses "End Turn" button.
   */
  @FXML
  protected void handleEndTurn(MouseEvent event) {
    List<List<ImageView>> allCombinations = new ArrayList<>();
    for (int i = placedCombinations.size(); i >= 0; i--) {
      HBox box = placedCombinations.get(i);
      if (box.getChildren().isEmpty()) {
        placedCombinations.remove(box);
      } else {
        List<ImageView> comb = new ArrayList<>();
        for (int j = 0; j < box.getChildren().size(); j++) {
          ImageView iView = (ImageView) box.getChildren().get(j);
          comb.add(iView);
        }
        allCombinations.add(comb);
      }
    }
    parser.play(allCombinations);
  }

  /**
   * Gets called if user may end his turn. Ends the current user's turn.
   */
  public void validEndTurn() {
    disableControl();
    cancelSelection();
    parser.getNextPlayerID();
    parser.finishedTurn();
  }

  /**
   * Opens alert window if non valid combinations are still on the main board.
   */
  public void errorEndTurn() {
    Alert alert = new Alert(AlertType.CONFIRMATION, "Error! Unable to end turn. Rearrange "
        + "the combinations on the board to valid combinations", ButtonType.OK);
    alert.showAndWait();

    if (alert.getResult() == ButtonType.YES) {

    }
  }

  /**
   * Gets
   * @param boardTiles
   */
  public void reloadBoard(List<List<Image>> boardTiles){
    for (int i = 0; i < boardTiles.size(); i++){
      HBox box = new HBox();
      List <Image> tiles = boardTiles.get(i);
      for (Image image : tiles){
        ImageView imageView = new ImageView();
        imageView.setFitHeight(MAX_IVIEW_HEIGHT);
        imageView.setFitWidth(MAX_BOXWIDTH);
        imageView.setImage(image);
        box.getChildren().add(imageView);
        onTileClicked(imageView);
      }
      board.getChildren().add(box);

    }
  }

  /**
   * Handles event if user pressed "Cancel Selection" button.
   * @param event - onMouseClicked event if user pressed "Cancel Selection" button.
   */
  @FXML
  protected void handleCancelSel(MouseEvent event) {
    cancelSelection();
  }

  /**
   * Returns all combinations on the selection board back to the player's hand.
   */
  private void cancelSelection() {
    if (!selectionBoard.getChildren().isEmpty()) {
      int size = selectionBoard.getChildren().size();
      for (int i = size - 1; i >= 0; i--) {
        HBox comb = (HBox) selectionBoard.getChildren().get(i);
        int combSize = comb.getChildren().size();
        for (int j = combSize - 1; j >= 0; j--) {
          ImageView tile = (ImageView) comb.getChildren().get(j);

          if (topHand.getChildren().size() <= HAND_SPACE) {
            topHand.getChildren().add(tile);
          } else {
            bottomHand.getChildren().add(tile);
          }
        }
        selectionBoard.getChildren().remove(selectionBoard.getChildren().get(i));
      }

    }
    selectedCombinations.clear();
    cancelSelEffect();
  }

  /**
   * Disables selection effect on selected tiles.
   */
  public void cancelSelEffect() {
    for (int i = 0; i < selectedTiles.size(); i++) {
      ImageView imageV = selectedTiles.get(i);
      imageV.setStyle("-fx-translate-y: 0");
      imageV.setEffect(null);
    }
    selectedTiles.clear();
  }

  /**
   * Handles "Manipulate" event.
   * @param event - onMouseClicked event if user presses "Manipulate Tiles" button.
   */
  @FXML
  protected void handleManipulate(MouseEvent event) {
//if first turn do not allow
    disableTilesOnBoard();
  }

  /**
   * Disables all tiles on the main board.
   */
  private void disableTilesOnBoard() {
    for (int i = 0; i < board.getChildren().size(); i++) {
      HBox box = (HBox) board.getChildren().get(i);
      for (int j = 0; j < box.getChildren().size(); j++) {
        ImageView tile = (ImageView) box.getChildren().get(j);
        onTileClicked(tile);
      }
    }
  }

  /**
   * Sets selection effect for a tile.
   * @param tile for which mouse event should be set.
   */
  private void onTileClicked(ImageView tile) {
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


  /**
   * For adding to existing combinations.
   * Creates button in front of and at the back of each combination on the main board.
   * @param event - onMouseClicked event if user presses "Add to existing combination" button.
   */
  @FXML
  protected void handleAddTo(MouseEvent event) {

    if (!selectedTiles.isEmpty()) {
      for (int i = 0; i < board.getChildren().size(); i++) {
        HBox box = (HBox) board.getChildren().get(i);
        for (int j = 0; j < box.getChildren().size(); j++) {

          if (box.getChildren().get(j) instanceof Button) {
            return;
          }
        }
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
   * For placing tiles to existing combinations on main board.
   * Checks if selected tiles may be added to existing combination.
   */
  void addToFront(HBox comb) {
    boardComb = comb;
    List<ImageView> boardTiles = new ArrayList<>();   //erstellt liste mit gewünschter neuer kombination.
    for (int i = 1; i < comb.getChildren().size() - 1;
        i++) {   //1 und size-1 weil an diesen Stellen Buttons sind.
      boardTiles.add((ImageView) comb.getChildren().get(i));
    }
    List<List<ImageView>> newComb = new ArrayList<>();
    newComb.add(selectedTiles);
    newComb.add(boardTiles);
    parser.play(selectedTiles, boardTiles, newComb);
  }

  /**
   * Adds selected tiles to the front of a cached combination on the main board.
   * Exits method, if selected tiles should be added to the same combination they are
   * already contained in.
   *
   */
  public void allowAddFront() {

    deleteAddToButtons();
    for (int j = 0; j < selectedTiles.size(); j++) {    //hinzufügen in selber kombination soll nicht gehn.
      if (boardComb.getChildren().contains(selectedTiles.get(j))) {
        return;
      }
    }
    for (int i = selectedTiles.size() - 1; i >= 0;
        i--) {    //soll ja in richtiger reihenfolge eingefügt werden, bei einer Tile ist index get(0)
      ImageView tile = selectedTiles.get(i);
      boardComb.getChildren().add(0, tile); //added tile vorne in hbox
    }

    deselectTiles(selectedTiles); //cleart auch selectedTiles
    updateBoard();
  }

  /**
   * Gets called if selected tiles may not be added to existing combination.
   */
  public void disallowAddTo() {
    deleteAddToButtons();
    cancelSelEffect();

  }

  /**
   * Checks if selected tiles may be added to the back of a combination on the main board.
   * @param comb - combination on the board where tiles should be added.
   */
  void addToBack(HBox comb) {
    boardComb = comb;
    List<ImageView> boardTiles = new ArrayList<>();   //erstellt liste mit gewünschter neuer kombination.
    for (int i = 1; i < comb.getChildren().size() - 1; i++) {
      boardTiles.add((ImageView) comb.getChildren().get(i));
    }
    List<List<ImageView>> newComb = new ArrayList<>();
    newComb.add(boardTiles);
    newComb.add(selectedTiles);
    parser.play(selectedTiles, boardTiles, newComb);
  }

  /**
   * Adds selected tiles to the front of cached combination on the main board.
   * Exits method if selected tiles should be added to the same combination they are
   * already contained in.
   */
  public void allowAddBack() {
    deleteAddToButtons();
    for (int j = 0; j < selectedTiles.size(); j++) {
      if (boardComb.getChildren().contains(selectedTiles.get(j))) {
        return;
      }
    }
    for (int i = 0; i < selectedTiles.size(); i++) {
      ImageView tile = selectedTiles.get(i);
      boardComb.getChildren().add(boardComb.getChildren().size(), tile);
    }

    deselectTiles(selectedTiles);
    selectedTiles.clear();
    updateBoard();
  }

  /**
   * Handles button event. Checks if selected tile may be swapped with a joker tile.
   * @param event - onMouseClicked event of "swap joker" button.
   */
  @FXML
  protected void handleSwapJoker(MouseEvent event) {
    if (selectedTiles.size() == 2) {
      Image image = (Image) selectedTiles.get(1).getImage();
      tile = selectedTiles.get(0);
      if (image.getId() != null && (tile.getParent() == topHand
          || tile.getParent() == bottomHand)) {
        joker = selectedTiles.get(1);
        HBox box = (HBox) joker.getParent();
        int jokerIndex = getIndexOf(box, joker);
        List<List<ImageView>> combination = new ArrayList<>();
        List<ImageView> comb = new ArrayList<>();
        for (int j = 0; j < box.getChildren().size(); j++) {
          comb.add((ImageView) box.getChildren().get(j));
          comb.set(jokerIndex, tile);
        }
        combination.add(comb);
        parser.play(combination);
      }
    }

  }

  /**
   * Returns the index of a tile in an HBox, returns -1 if the tile is not contained in the Hbox.
   * @param box - Hbox to be searched
   * @param tile - Tile to be found
   * @return index of tile
   */
  private int getIndexOf(HBox box, ImageView tile) {
    int counter = 0;
    int noIndexFound = -1;
    for (int i = 0; i < box.getChildren().size(); i++) {
      if (box.getChildren().get(i).equals(tile)) {
        return counter;
      }
      counter++;
    }
    return noIndexFound;
  }

  /**
   * Gets called if tile may be swapped with joker.
   * Swaps a tile from the hand with a joker on the main board.
   */
  public void swapWithJoker() {
    HBox box = (HBox) joker.getParent();
    int jokerIndex = getIndexOf(box, joker);
    if (topHand.getChildren().size() <= HAND_SPACE) {
      topHand.getChildren().add(joker);
    } else {
      bottomHand.getChildren().add(joker);
    }
    box.getChildren().add(jokerIndex, tile);

    cancelSelEffect();
  }

  /**
   * Deletes all "add here" buttons on the main board.
   */
  private void deleteAddToButtons() {
    for (int i = 0; i < board.getChildren().size(); i++) {
      HBox box = (HBox) board.getChildren().get(i);
      box.getChildren().remove(box.getChildren().get(box.getChildren().size() - 1));
      box.getChildren().remove(box.getChildren().get(0));

    }
  }

  /**
   * Deletes unused Hboxes.
   */
  private void updateBoard() {
    for (int i = 0; i < board.getChildren().size(); i++) {
      HBox box = (HBox) board.getChildren().get(i);
      if (box.getChildren().isEmpty()) {
        board.getChildren().remove(box);
      }
    }
  }

  //TODO reload Board für andere clients

  /**
   * Disables control for the user. All buttons and tiles are set to disabled.
   */
  private void disableControl() {
    enter.setDisable(true);
    endTurn.setDisable(true);
    cancelSelection.setDisable(true);
    addToExisting.setDisable(true);
    manipulate.setDisable(true);
    placeOnBoard.setDisable(true);
    swapJoker.setDisable(true);
    List<ImageView> handTiles = new ArrayList<>();
    for (int i = 0; i < topHand.getChildren().size(); i++) {
      ImageView iView = (ImageView) topHand.getChildren().get(i);
      handTiles.add(iView);
    }
    for (int i = 0; i < bottomHand.getChildren().size(); i++) {
      ImageView iView = (ImageView) bottomHand.getChildren().get(i);
      handTiles.add(iView);
    }
    for (int i = 0; i < board.getChildren().size(); i++) {
      HBox box = (HBox) board.getChildren().get(i);
      for (int j = 0; j < box.getChildren().size(); j++) {
        ImageView iView = (ImageView) box.getChildren().get(j);
        handTiles.add(iView);
      }
    }
    deselectTiles(handTiles);
  }

  /**
   * Deselects and disables a list of tiles.
   */
  private void deselectTiles(List<ImageView> tiles) {
    for (int i = tiles.size() - 1; i >= 0; i--) {
      ImageView tile = tiles.get(i);
      tile.setStyle("-fx-translate-y: 0");
      tile.setEffect(null);
      tile.setDisable(true);
      tiles.remove(tile);
    }
  }

}

// TODO Anzeige für wenn ein Player aussteigt.

//TODO hover funktion für bag



