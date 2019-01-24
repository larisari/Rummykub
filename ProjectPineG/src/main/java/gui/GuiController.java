package gui;
//TODO bottomhand begrenzen!!!
//TODO Buttons per default disablen und enablen wenn player am zug ist.

//TODO bei Disconnect Alert window mit ok button zurück zu startbildschirm
//TODO bei endscreen button zurück zum startbildschirm.

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
  @FXML
  private Button swapJoker;
  private List<ImageView> selectedTiles = new ArrayList<>();
  private List<List<ImageView>> selectedCombinations = new ArrayList<>();
  private List<HBox> placedCombinations = new ArrayList<>(); //wird an Server geschickt.
  private List<Image> hand = new ArrayList<>();
  public int numberOfPlayers;
  private final static int HAND_SPACE = 13;
  private int turn = 0;
  private final static int MAX_BOXHEIGHT = 68;
  private final static int MAX_BOXWIDTH = 45;
  private final static int MAX_IVIEW_HEIGHT = 65;
  private Integer nextPlayerID = 0;
  private ImageView tile = new ImageView();
  private ImageView joker = new ImageView();
  private HBox boardComb = new HBox();
  private int playerID = 0;

  private ClientParser parser;

  /**
   * For handling mouse events and other user interactions. Interface between network and view.
   */
  public GuiController() {
    parser = new ClientParser(this);
  }

  public void setNumberOfPlayers(int numberPlayers) {
    this.numberOfPlayers = numberPlayers;
  }

  public void setPlayerID(int playerID) {
    this.playerID = playerID;
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
   * Initializes FXML file. Sets player boards invisible according to the number of players.
   */
  @FXML
  private void initialize() {
    disableControl();
//while is first turn: manipulate.setDisable(true);
  }


  public void setPlayerBoards() {
    switch (numberOfPlayers) {
      case 2:
        rightBoard.setVisible(false);
        leftBoard.setVisible(false);
        break;
      case 3:
        leftBoard.setVisible(false);
        break;
    }
  }

  /**
   * Sets player names on player boards, according to each player.
   */
  public void setPlayerNames() {
    switch (playerID) {
      case 0:
        break; //bleibt auf default
      case 1:
        playerTopName.setText("PLAYER 1");
        playerRightName.setText("PLAYER 4");
        playerLeftName.setText("PLAYER 3");
        break;
      case 2:
        playerTopName.setText("PLAYER 4");
        playerRightName.setText("PLAYER 2");
        playerLeftName.setText("PLAYER 1");
        break;
      case 3:
        playerTopName.setText("PLAYER 3");
        playerRightName.setText("PLAYER 1");
        playerLeftName.setText("PLAYER 2");
        break;
    }
  }

  /**
   * Opens confirmation alert window if user presses "Quit" button.
   *
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
   * Checks if tiles from the main board and the hand should be combined and checks if the altered
   * combinations on the board are valid. If only tiles from the hand should form a combination, the
   * tiles are moved to the selection board.
   *
   * @param event - onMouseClicked event if user presses "Enter new Selection" button.
   */
  @FXML
  protected void handleEnterComb(MouseEvent event) throws IOException {
    List<List<ImageView>> boardSelectedCombs = new ArrayList<>();

    if (!selectedTiles.isEmpty()) {
      if (areOnlyTilesFromHand(selectedTiles)) {
        List<ImageView> sTiles = new ArrayList<>();
        HBox comb = new HBox();
        comb.prefHeight(MAX_BOXHEIGHT);
        comb.prefWidth(selectedTiles.size() * MAX_BOXWIDTH);
        for (ImageView tile : selectedTiles) {
          sTiles.add(tile);
          comb.getChildren().add(tile);
        }
        selectionBoard.getChildren().add(comb);
        selectedCombinations.add(sTiles);
        cancelSelEffect();
      } else {
        for (ImageView tile : selectedTiles) {
          if (tile.getParent().getParent() == board) { //nur wenn die selected tile am board liegt.
            List<ImageView> combination = new ArrayList<>();
            HBox parent = (HBox) tile.getParent();
            for (int i = 0; i < parent.getChildren().size(); i++) {
              if (!selectedTiles.contains(parent.getChildren().get(i))) { //wenn eine oder mehrere selected tiles in der kombination sind füge sie nicht hinzu.
                combination.add((ImageView) parent.getChildren().get(i));
              }
            }
            boardSelectedCombs.add(combination);
            boardSelectedCombs.add(selectedTiles);
            parser.playHandWithBoard(boardSelectedCombs);
          }
        }
      }
    }

  }


  private boolean areOnlyTilesFromHand(List<ImageView> tiles) {
    for (ImageView tile : tiles) {
      if (tile.getParent().getParent() == board) {
        return false;
      }
    }
    return true;
  }

  /**
   * Creates a new combination on the main board with the selected tiles.
   */
  public void moveTiles() throws IOException {
    moveTilesAux(selectedTiles);

  }

  private void moveTilesAux(List<ImageView> combination) throws IOException {
    HBox comb = new HBox();
    for (int j = 0; j < combination.size(); j++) {
      ImageView tile = combination.get(j);
      tile.setStyle("-fx-translate-y: 0");
      tile.setEffect(null);
      tile.setDisable(true);
      comb.getChildren().add(tile);
    }

    board.getChildren().add(comb);
    bag.setDisable(true);
    endTurn.setDisable(false);
    updateBoard();
    disableTiles(combination); //löscht auch tiles aus selectedTiles
    if (topHand.getChildren().isEmpty() && bottomHand.getChildren().isEmpty()) {
      openWinScreen();
      parser.notifyWin();
    }
  }

  /**
   * Checks if tile combinations on the selection board are valid.
   *
   * @param event - onMouseClicked event if user presses "Place Selection" button
   */
  @FXML
  protected void handleplaceOnBoard(MouseEvent event) {
    if (!selectionBoard.getChildren().isEmpty()) {
      parser.play(selectedCombinations);
    }
  }

  /**
   * Places tiles combination on the main board. Disables the bag. Checks if the player's hand is
   * now empty.
   */
  public void placeTiles() throws IOException {
    for (int i = 0; i < selectedCombinations.size(); i++) {
      List<ImageView> combination = selectedCombinations.get(i);
      moveTilesAux(combination);
    }

    if (topHand.getChildren().isEmpty() && bottomHand.getChildren().isEmpty()) {
      openWinScreen();
      parser.notifyWin();
    }
  }

  /**
   * Gets called if user has won.
   *
   * @throws IOException if some error occurs while loading fxml file.
   */
  public void openWinScreen() throws IOException {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation((getClass().getResource("/winnerScreen.fxml")));
    Parent root = loader.load();
    Scene scene = new Scene(root);
    Stage stage = new Stage();
    stage.setScene(scene);
    stage.setResizable(false);
    stage.show();
    EndScreenController eController = loader.getController();
    eController.setNumberOfPlayers(numberOfPlayers);
    eController.setPointsNamesVisible();
  }

  /**
   * Gets called if user has lost.
   *
   * @throws IOException if some error occurs while loading fxml file.
   */
  public void openLoserScreen() throws IOException {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation((getClass().getResource("/loserScreen.fxml")));
    Parent root = loader.load();
    Scene scene = new Scene(root);
    Stage stage = new Stage();
    stage.setScene(scene);
    stage.setResizable(false);
    stage.show();
    EndScreenController eController = loader.getController();
    eController.setNumberOfPlayers(numberOfPlayers);
    eController.setPointsNamesVisible();
  }

  /**
   * Requests tiles to be drawn from the bag. Requests the next player's ID.
   *
   * @param event - onMouseClicked event if user clicks on the bag.
   */
  @FXML
  protected void handleDrawTile(MouseEvent event) {
    parser.draw();

  }

  /**
   * Gets called if user may draw from the bag. Loads the tiles and disables the bag.
   *
   * @param tiles to be loaded.
   */
  public void loadTiles(List<Image> tiles) {
    hand = tiles;
    for (int i = 0; i < hand.size(); i++) {
      createTile(hand.get(i));
    }
    bag.setDisable(true);
    disableControl();
  }

  /**
   * Updates the text of the next player label.
   *
   * @param ID of the next player.
   */
  public void updateNextPlayerName(Integer ID) {
    if (playerID == ID) {
      playerTurn.setText("Your turn.");
    } else {
      ID += 1;
      playerTurn.setText("Player " + ID + "'s turn.");
    }
  }

  /**
   * Add a tile to the player's hand.
   *
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
   *
   * @param event - onMouseClicked event if user presses "End Turn" button.
   */
  @FXML
  protected void handleEndTurn(MouseEvent event) {
    parser.finishedTurn();
    disableControl();
    cancelSelection();
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
   * Gets called when turn is finished, reloads board for all players
   */
  public void reloadBoard(List<List<Image>> boardTiles) {
    board.getChildren().clear();
    for (int i = 0; i < boardTiles.size(); i++) {
      HBox box = new HBox();
      List<Image> tiles = boardTiles.get(i);
      for (Image image : tiles) {
        ImageView imageView = new ImageView();
        imageView.setFitHeight(MAX_IVIEW_HEIGHT);
        imageView.setFitWidth(MAX_BOXWIDTH);
        imageView.setImage(image);
        box.getChildren().add(imageView);
        onTileClicked(imageView);
        imageView.setDisable(true);
      }
      board.getChildren().add(box);

    }
  }

  /**
   * gets called if its this player's turn, enables Button control.
   */
  public void enableControl() {
    enter.setDisable(false); //TODO alle buttons in liste packen -> enablen disablen durchiterieren.
    cancelSelection.setDisable(false);
    addToExisting.setDisable(false);
    manipulate.setDisable(false);
    placeOnBoard.setDisable(false);
    swapJoker.setDisable(false);
    bag.setDisable(false);
    for (int i = 0; i < topHand.getChildren().size(); i++) {
      ImageView iView = (ImageView) topHand.getChildren().get(i);
      iView.setDisable(false);
    }
    for (int i = 0; i < bottomHand.getChildren().size(); i++) {
      ImageView iView = (ImageView) bottomHand.getChildren().get(i);
      iView.setDisable(false);
    }
    for (int i = 0; i < board.getChildren().size(); i++) {
      HBox box = (HBox) board.getChildren().get(i);
      for (int j = 0; j < box.getChildren().size(); j++) {
        ImageView iView = (ImageView) box.getChildren().get(j);
        iView.setDisable(false);
      }
    }

  }

  /**
   * Handles event if user pressed "Cancel Selection" button.
   *
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
   *
   * @param event - onMouseClicked event if user presses "Manipulate Tiles" button.
   */
  @FXML
  protected void handleManipulate(MouseEvent event) {
//if first turn do not allow
    enableTilesOnBoard();
  }

  /**
   * Disables all tiles on the main board.
   */
  private void enableTilesOnBoard() {
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
   *
   * @param tile for which mouse event should be set.
   */
  private void onTileClicked(ImageView tile) {
    tile.setOnMouseClicked(
        e -> {
          if (!selectedTiles.contains(tile)) {
            tile.setStyle("-fx-translate-y: -15;");
            TileView.highlightTile(tile);
            selectedTiles.add(tile);
            tile.setDisable(false);
          } else {
            tile.setStyle("-fx-translate-y: 0");
            tile.setEffect(null);
            selectedTiles.remove(tile);
            tile.setDisable(false);
          }
        });
  }

  /**
   * For adding to existing combinations. Creates button in front of and at the back of each
   * combination on the main board.
   *
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

  private List<List<ImageView>> boardToList(FlowPane pane) {
    List<List<ImageView>> allCombinations = new ArrayList<>();
    for (int i = 0; i < pane.getChildren().size(); i++) {
      HBox box = (HBox) pane.getChildren().get(i);
      if (box.getChildren().isEmpty()) {
        pane.getChildren().remove(box);
      } else {
        List<ImageView> comb = new ArrayList<>();
        for (int j = 0; j < box.getChildren().size(); j++) {
          ImageView iView = (ImageView) box.getChildren().get(j);
          comb.add(iView);
        }
        allCombinations.add(comb);
      }
    }
    return allCombinations;
  }

  /**
   * For placing tiles to existing combinations on main board. Checks if selected tiles may be added
   * to existing combination.
   */
  void addToFront(HBox comb) {
    boardComb = comb;
    List<ImageView> boardTiles = new ArrayList<>();   //erstellt liste mit gewünschter neuer kombination.
    for (int i = 1; i < comb.getChildren().size() - 1;
        i++) {   //1 und size-1 weil an diesen Stellen Buttons sind.
      boardTiles.add((ImageView) comb.getChildren().get(i));
    }
    List<List<ImageView>> newComb = new ArrayList<>();
    List<ImageView> newCombination = new ArrayList<>();
    newCombination.addAll(selectedTiles);
    newCombination.addAll(boardTiles);
    newComb.add(newCombination);
    parser.playL(selectedTiles, boardTiles, newComb);

  }

  /**
   * Adds selected tiles to the front of a cached combination on the main board. Exits method, if
   * selected tiles should be added to the same combination they are already contained in.
   */
  public void allowAddFront() throws IOException {
    deleteAddToButtons();
    for (int j = 0; j < selectedTiles.size();
        j++) {    //hinzufügen in selber kombination soll nicht gehn.
      if (boardComb.getChildren().contains(selectedTiles.get(j))) {
        return;
      }
    }
    for (int i = selectedTiles.size() - 1; i >= 0;
        i--) {    //soll ja in richtiger reihenfolge eingefügt werden, bei einer Tile ist index get(0)
      ImageView tile = selectedTiles.get(i);
      boardComb.getChildren().add(0, tile); //added tile vorne in hbox
    }

    endTurn.setDisable(false);
    bag.setDisable(true);
    disableTiles(selectedTiles); //cleart auch selectedTiles
    updateBoard();

    if (topHand.getChildren().isEmpty() && bottomHand.getChildren().isEmpty()) {
      openWinScreen();
      parser.notifyWin();
    }
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
   *
   * @param comb - combination on the board where tiles should be added.
   */
  void addToBack(HBox comb) {
    boardComb = comb;
    List<ImageView> boardTiles = new ArrayList<>();   //erstellt liste mit gewünschter neuer kombination.
    for (int i = 1; i < comb.getChildren().size() - 1; i++) {
      boardTiles.add((ImageView) comb.getChildren().get(i));
    }
    List<List<ImageView>> newComb = new ArrayList<>();
    List<ImageView> newCombination = new ArrayList<>();
    newCombination.addAll(boardTiles);
    newCombination.addAll(selectedTiles);
    newComb.add(newCombination);
    parser.playR(selectedTiles, boardTiles, newComb);

  }

  /**
   * Adds selected tiles to the front of cached combination on the main board. Exits method if
   * selected tiles should be added to the same combination they are already contained in.
   */
  public void allowAddBack() throws IOException {
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

    disableTiles(selectedTiles);
    updateBoard();
    endTurn.setDisable(false);
    bag.setDisable(true);

    if (topHand.getChildren().isEmpty() && bottomHand.getChildren().isEmpty()) {
      openWinScreen();
      parser.notifyWin();
    }
  }

  /**
   * Handles button event. Checks if selected tile may be swapped with a joker tile.
   *
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
        parser.playSwapJoker(combination);
      }
    }

  }

  /**
   * Returns the index of a tile in an HBox, returns -1 if the tile is not contained in the Hbox.
   *
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
   * Gets called if tile may be swapped with joker. Swaps a tile from the hand with a joker on the
   * main board.
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
    disableTiles(handTiles);
  }

  /**
   * Deselects and disables a list of tiles.
   */
  private void disableTiles(List<ImageView> tiles) {
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


