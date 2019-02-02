package gui;

import gui.util.ControllerUtil;
import gui.util.Image;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import network.ClientParser;

/**
 * Controller for game view, handles mouse events and other user input for clientgui.fxml file.
 * Communicates with network via ClientParser.
 */
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
  @FXML
  private Button swapJoker;
  private final static int HAND_SPACE = 13;
  private final static int MAX_BOXHEIGHT = 68;
  private final static int MAX_BOXWIDTH = 45;
  private final static int DRAW_HEIGHT = 65;
  private final static int DRAW_WIDTH = 45;
  private List<ImageView> selectedTiles = new ArrayList<>();
  private List<List<ImageView>> selectedCombinations = new ArrayList<>();
  private int numberOfPlayers;
  private int playerID = 0;
  private double tileHeight = 65;
  private double tileWidth = 45;
  private ImageView tile = new ImageView();
  private ImageView joker = new ImageView();
  private HBox boardComb = new HBox();
  private Stage stage = new Stage();
  private MediaPlayer mediaPlayer;
  private ClientParser parser;

  /**
   * For handling mouse events and other user interactions. Interface between network and view.
   */
  public GuiController() {
    parser = new ClientParser(this);
  }

  /**
   * Sets forwarded number of players.
   *
   * @param numberPlayers - number of players.
   */
  public void setNumberOfPlayers(int numberPlayers) {
    this.numberOfPlayers = numberPlayers;
  }

  /**
   * Sets forwarded playerID.
   *
   * @param playerID - this gui's client id.
   */
  void setPlayerID(int playerID) {
    this.playerID = playerID;
  }

  /**
   * Sets forwarded stage.
   *
   * @param stage - this fxml file's stage.
   */
  void setStage(Stage stage) {
    this.stage = stage;
  }

  /**
   * Initializes FXML file.
   */
  @FXML
  private void initialize() {
    disableControl();

  }

  /**
   * Sets player boards invisible according to number of players.
   */
  void setPlayerBoards() {
    switch (numberOfPlayers) {
      case 2:
        rightBoard.setVisible(false);
        leftBoard.setVisible(false);
        break;
      case 3:
        switch (playerID) {
          case 0: //Player 1
            leftBoard.setVisible(false);
            break;
          case 1: //Player 2
            rightBoard.setVisible(false);
            break;
          case 2: //Player 3
            topBoard.setVisible(false);
            break;
        }
    }
  }

  /**
   * Sets player names on player boards, according to each player.
   */
  void setPlayerNames() {
    switch (playerID) {
      case 0: //Player 1
        break;
      case 1: //Player 2
        playerTopName.setText("PLAYER 1");
        playerRightName.setText("PLAYER 4");
        playerLeftName.setText("PLAYER 3");
        break;
      case 2: //Player 3
        playerTopName.setText("PLAYER 4");
        playerRightName.setText("PLAYER 2");
        playerLeftName.setText("PLAYER 1");
        break;
      case 3: //Player 4
        playerTopName.setText("PLAYER 3");
        playerRightName.setText("PLAYER 1");
        playerLeftName.setText("PLAYER 2");
        break;
    }
  }


  /**
   * Opens confirmation alert window if user presses "Quit" button. Exits application upon
   * confirmation.
   */
  @FXML
  private void handleQuit() {
    ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
    ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
    Alert alert = new Alert(AlertType.CONFIRMATION, "bla", yes, no);
    alert.setContentText("Do you really want to quit?");
    Optional<ButtonType> result = alert.showAndWait();

    if (result.isPresent() && result.get() == yes) {
      System.exit(0);
    }
  }


  /**
   * Handles user clicking on the bag. Requests tiles to be drawn from the bag. Alerts user if hand
   * is full.
   */
  @FXML
  private void handleDrawTile() {
    if (topHand.getChildren().size() >= HAND_SPACE
        && bottomHand.getChildren().size() >= HAND_SPACE) {
      Alert alert = new Alert(AlertType.CONFIRMATION, "Your hand is full, please place "
          + "tiles on the board before drawing more tiles!", ButtonType.OK);
      alert.showAndWait();

    } else {
      parser.draw();
    }
  }


  /**
   * For loading tiles. Gets called if user may draw from the bag. Disables the bag.
   *
   * @param tiles to be loaded.
   */
  public void loadTiles(List<Image> tiles) {
    for (Image image : tiles) {
      if (topHand.getChildren().size() <= HAND_SPACE) {
        createTile(image, topHand);
      } else if (bottomHand.getChildren().size() <= HAND_SPACE) {
        createTile(image, bottomHand);
      }
    }
    bag.setDisable(true);
    disableControl();
  }

  /**
   * Adds a tile to the player's hand.
   *
   * @param tile to be added to the player's hand.
   */
  private void createTile(Image tile, HBox hand) {
    ImageView readyTile = ControllerUtil.setImage(tile);
    hand.getChildren().add(readyTile);
    onTileClicked(readyTile);
  }

  /**
   * Initialises selection effect for a tile.
   *
   * @param tile for which mouse event should be set.
   */
  private void onTileClicked(ImageView tile) {
    tile.setOnMouseClicked(
        e -> {
          if (!selectedTiles.contains(tile)) {
            tile.setStyle("-fx-translate-y: -15;");
            ControllerUtil.highlightTile(tile);
            selectedTiles.add(tile);
          } else {
            tile.setStyle("-fx-translate-y: 0");
            tile.setEffect(null);
            selectedTiles.remove(tile);
          }
        });
  }

  /**
   * Handles event if user presses "Enter new Selection" button. Checks if selectedTiles are only in
   * the player's hand or also contain tiles from the main board.
   */
  @FXML
  private void handleEnterComb() {
    deleteAddToButtons();
    if (!selectedTiles.isEmpty()) {
      if (areOnlyTilesFromHand(selectedTiles)) {
        moveToSelectionBoard();
      } else {
        placeHandAndBoardTiles();
      }
    }
  }

  /**
   * Gets called if user releases "Enter new Selection" button. For updating the main board in case
   * it intersects with selection board.
   */
  @FXML
  private void handleEnterCombRel() {
    updateBoard();
  }

  /**
   * Checks if combination contains only tiles from the player's hand.
   *
   * @param tiles - a tile combination.
   * @return true if given tile combination is only contained in the player's hand.
   */
  private boolean areOnlyTilesFromHand(List<ImageView> tiles) {
    for (ImageView tile : tiles) {
      if (tile.getParent().getParent() == board) {
        return false;
      }
    }
    return true;
  }

  /**
   * Moves tiles from player's hand to the selection board. If the selection board is full, the
   * tiles are moved back to the player's hand.
   */
  private void moveToSelectionBoard() {
    int boxSpacing = 8;
    int maxSelBoardWidth = 653;
    List<ImageView> sTiles = new ArrayList<>();
    HBox comb = new HBox();
    comb.setMaxWidth(MAX_BOXHEIGHT);
    comb.setMaxWidth(selectedTiles.size() * MAX_BOXWIDTH);
    for (ImageView tile : selectedTiles) {
      sTiles.add(tile);
      comb.getChildren().add(tile);
    }
    selectionBoard.getChildren().add(comb);
    selectedCombinations.add(sTiles);
    int counter = 0;
    for (int i = 0; i < selectionBoard.getChildren().size(); i++) {
      HBox box = (HBox) selectionBoard.getChildren().get(i);
      counter += box.getChildren().size() * DRAW_WIDTH + boxSpacing;
    }
    if (counter >= maxSelBoardWidth) {
      HBox lastComb = (HBox) selectionBoard.getChildren()
          .get(selectionBoard.getChildren().size() - 1);
      for (int i = lastComb.getChildren().size() - 1; i >= 0; i--) {
        ImageView iView = (ImageView) lastComb.getChildren().get(i);
        if (topHand.getChildren().size() <= HAND_SPACE) {
          topHand.getChildren().add(iView);
        } else if (bottomHand.getChildren().size() <= HAND_SPACE) {
          bottomHand.getChildren().add(iView);

        }
        lastComb.getChildren().remove(iView);
        selectionBoard.getChildren().remove(lastComb);
        selectedCombinations.remove(sTiles);
      }

    }
    cancelSelEffect();
  }


  /**
   * Checks in which combinations on the board the selected tiles are contained in and requests
   * verification for alteration wishes of user.
   */
  private void placeHandAndBoardTiles() {
    List<List<ImageView>> newBoardCombs = new ArrayList<>();
    List<List<ImageView>> oldBoardCombs = new ArrayList<>();
    List<ImageView> selectedTilesHand = new ArrayList<>();
    for (ImageView tile : selectedTiles) {

      if (tile.getParent().getParent() == board) {
        List<ImageView> combination = new ArrayList<>();
        List<ImageView> oldCombination = new ArrayList<>();
        HBox parent = (HBox) tile.getParent();
        for (int i = 0; i < parent.getChildren().size(); i++) {
          ImageView sTile = (ImageView) parent.getChildren().get(i);
          oldCombination.add(sTile);
          if (!selectedTiles.contains(sTile)) {
            combination.add((ImageView) parent.getChildren().get(i));
          }
        }
        newBoardCombs.add(combination);
        oldBoardCombs.add(oldCombination);
      } else {
        selectedTilesHand.add(tile);
      }
    }
    ControllerUtil.eraseDuplicate(newBoardCombs);
    ControllerUtil.eraseDuplicate(oldBoardCombs);
    newBoardCombs.add(selectedTiles);
    parser.playHandWithBoard(selectedTilesHand, oldBoardCombs, newBoardCombs);
  }


  /**
   * For creating a new combination on the main board with the selected tiles.
   */
  public void moveTiles() {
    moveTilesAux(selectedTiles);

  }

  /**
   * Handles user pressing "Place Selection" button. Checks if tile combinations on the selection
   * board are valid.
   */
  @FXML
  private void handlePlaceOnBoard() {
    deleteAddToButtons();
    if (!selectionBoard.getChildren().isEmpty()) {
      parser.play(selectedCombinations);

    }
  }

  /**
   * Gets called when user releases "Place Selection" button. For updating board if main board
   * intersects with selection board.
   */
  @FXML
  private void handlePlaceOnBoardRel() {
    updateBoard();
  }

  /**
   * Gets called if combinations on the selection board are valid. For placing tiles combination on
   * the main board. Checks if the player's hand is now empty.
   */
  public void placeTiles() {
    for (List<ImageView> combination : selectedCombinations) {
      moveTilesAux(combination);
    }
  }

  /**
   * Places tiles on the main board. Checks if player's hand is now empty.
   *
   * @param combination which should be placed on the main board.
   */
  private void moveTilesAux(List<ImageView> combination) {
    HBox comb = new HBox();
    for (ImageView tile : combination) {
      tile.setStyle("-fx-translate-y: 0");
      tile.setEffect(null);
      tile.setFitHeight(tileHeight);
      tile.setFitWidth(tileWidth);
      comb.getChildren().add(tile);
    }

    board.getChildren().add(comb);

    bag.setDisable(true);
    endTurn.setDisable(false);
    disableTiles(combination);
    enableTilesOnBoard();

    if (topHand.getChildren().isEmpty() && bottomHand.getChildren().isEmpty()) {
      parser.getPlayerPoints();
      parser.notifyWin();
    }
  }

  /**
   * Handles event if user pressed "Cancel Selection" button. For cancelling selected tiles on hand
   * and on selection board.
   */
  @FXML
  private void handleCancelSel() {
    cancelSelection();
  }

  /**
   * Returns all combinations on the selection board back to the player's hand and deselects tiles
   * on hand.
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
          } else if (bottomHand.getChildren().size() <= HAND_SPACE) {
            bottomHand.getChildren().add(tile);

          } else {
            cancelSelEffect();
            return;
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
    for (ImageView imageV : selectedTiles) {
      imageV.setStyle("-fx-translate-y: 0");
      imageV.setEffect(null);
    }
    selectedTiles.clear();
  }


  /**
   * Handles user pressing "Add to existing Combination" button. For adding to existing
   * combinations. Creates "+" button in front of and at the back of each combination on the main
   * board. Deletes "+" buttons if "Add to.." button is clicked a second time.
   */
  @FXML
  private void handleAddTo() {

    if (!selectedTiles.isEmpty()) {
      for (int i = 0; i < board.getChildren().size(); i++) {
        HBox box = (HBox) board.getChildren().get(i);
        for (int j = 0; j < box.getChildren().size(); j++) {

          if (box.getChildren().get(j) instanceof Button) {
            deleteAddToButtons();
            cancelSelEffect();
            return;
          }
        }
        Button front = new Button("+");
        box.getChildren().add(0, front);
        front.setOnMousePressed(event1 -> addToFront(box));
        Button back = new Button("+");
        box.getChildren().add(box.getChildren().size(), back);
        back.setOnMousePressed(event2 -> addToBack(box));


      }

    } else {
      deleteAddToButtons();
    }
  }


  /**
   * Gets called if user releases "Add to..." button. For updating board if main board intersects
   * with selection board.
   */
  @FXML
  private void handleAddToRel() {
    updateBoard();
  }


  /**
   * For placing tiles to the front of existing combinations on main board. Checks if selected tiles
   * may be added to existing combination.
   *
   * @param comb - combination on the main board where tiles should be added.
   */
  private void addToFront(HBox comb) {
    if (!selectedTiles.isEmpty()) {
      boardComb = comb;
      List<ImageView> boardTiles = new ArrayList<>();
      for (int i = 1; i < comb.getChildren().size() - 1; i++) {
        boardTiles.add((ImageView) comb.getChildren().get(i));
      }
      List<List<ImageView>> newComb = new ArrayList<>();
      List<ImageView> newCombination = new ArrayList<>();
      newCombination.addAll(selectedTiles);
      newCombination.addAll(boardTiles);
      newComb.add(newCombination);
      parser.playL(selectedTiles, boardTiles, newComb);
    } else {
      deleteAddToButtons();
      cancelSelEffect();
    }
  }

  /**
   * Adds selected tiles to the front of a combination on the main board. Exits method, if selected
   * tiles should be added to the same combination they are already contained in. Deletes "+"
   * buttons. Checks if player's hand is now empty.
   */
  public void allowAddFront() {
    deleteAddToButtons();
    for (ImageView iView : selectedTiles) {
      if (boardComb.getChildren().contains(iView)) {
        return;
      }
    }
    for (int i = selectedTiles.size() - 1; i >= 0; i--) {
      ImageView tile = selectedTiles.get(i);
      tile.setFitWidth(tileWidth);
      tile.setFitHeight(tileHeight);
      boardComb.getChildren().add(0, tile);
    }

    endTurn.setDisable(false);
    bag.setDisable(true);
    cancelSelEffect();
    updateBoard();

    if (topHand.getChildren().isEmpty() && bottomHand.getChildren().isEmpty()) {
      parser.getPlayerPoints();
      parser.notifyWin();
    }
  }


  /**
   * For placing tiles to the back of existing combinations on main board. Checks if selected tiles
   * may be added to the back of a combination on the main board.
   *
   * @param comb - combination on the main board where tiles should be added.
   */
  private void addToBack(HBox comb) {
    if (!selectedTiles.isEmpty()) {
      boardComb = comb;
      List<ImageView> boardTiles = new ArrayList<>();
      for (int i = 1; i < comb.getChildren().size() - 1; i++) {
        boardTiles.add((ImageView) comb.getChildren().get(i));
      }
      List<List<ImageView>> newComb = new ArrayList<>();
      List<ImageView> newCombination = new ArrayList<>();
      newCombination.addAll(boardTiles);
      newCombination.addAll(selectedTiles);
      newComb.add(newCombination);
      parser.playR(selectedTiles, boardTiles, newComb);
    } else {
      deleteAddToButtons();
      cancelSelEffect();
    }
  }

  /**
   * Adds selected tiles to the front of combination on the main board. Exits method if selected
   * tiles should be added to the same combination they are already contained in. Deletes "+"
   * buttons. Checks if player's hand is now empty.
   */
  public void allowAddBack() {
    deleteAddToButtons();
    for (ImageView iView : selectedTiles) {
      if (boardComb.getChildren().contains(iView)) {
        return;
      }
    }
    for (ImageView tile : selectedTiles) {
      tile.setFitHeight(tileHeight);
      tile.setFitWidth(tileWidth);
      boardComb.getChildren().add(boardComb.getChildren().size(), tile);
    }

    endTurn.setDisable(false);
    bag.setDisable(true);
    cancelSelEffect();
    updateBoard();


    if (topHand.getChildren().isEmpty() && bottomHand.getChildren().isEmpty()) {
      parser.getPlayerPoints();
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
   * Deletes all "+" buttons on the main board.
   */
  private void deleteAddToButtons() {
    for (int i = 0; i < board.getChildren().size(); i++) {
      HBox box = (HBox) board.getChildren().get(i);
      if (box.getChildren().get(0) instanceof Button) {
        box.getChildren().remove(box.getChildren().get(box.getChildren().size() - 1));
        box.getChildren().remove(box.getChildren().get(0));

      } else {
        return;
      }
    }
  }

  /**
   * Handles user pressing "Swap Joker" button. Checks if selected tile may be swapped with a joker
   * tile.
   */
  @FXML
  private void handleSwapJoker() {
    if (selectedTiles.size() == 2) {
      Image jokerImage = (Image) selectedTiles.get(1).getImage();
      tile = selectedTiles.get(0);
      if (jokerImage.getId() != null && (tile.getParent() == topHand
          || tile.getParent() == bottomHand)) {
        joker = selectedTiles.get(1);
        HBox box = (HBox) joker.getParent();
        if (ControllerUtil.getIndexOf(box, joker) != null) {
          int jokerIndex = ControllerUtil.getIndexOf(box, joker);
          List<List<ImageView>> combination = new ArrayList<>();
          List<ImageView> comb = new ArrayList<>();

          for (int j = 0; j < box.getChildren().size(); j++) {
            comb.add((ImageView) box.getChildren().get(j));

          }
          List<ImageView> oldComb = new ArrayList<>(comb);
          List<List<ImageView>> oldCombList = new ArrayList<>();
          oldCombList.add(oldComb);
          comb.set(jokerIndex, tile);

          combination.add(comb);
          List<ImageView> tileFromHand = new ArrayList<>();
          tileFromHand.add(tile);
          parser.playSwapJoker(tileFromHand, oldCombList, combination);
        }
      }
    }

  }


  /**
   * Gets called if tile may be swapped with joker. Swaps a tile from the hand with a joker on the
   * main board.
   */
  public void swapWithJoker() {
    HBox box = (HBox) joker.getParent();
    joker.setFitHeight(DRAW_HEIGHT);
    joker.setFitWidth(DRAW_WIDTH);
    tile.setFitWidth(tileWidth);
    tile.setFitHeight(tileHeight);
    if (ControllerUtil.getIndexOf(box, joker) != null) {
      int jokerIndex = ControllerUtil.getIndexOf(box, joker);
      if (topHand.getChildren().size() <= HAND_SPACE) {
        topHand.getChildren().add(joker);
      } else {
        bottomHand.getChildren().add(joker);
      }
      box.getChildren().add(jokerIndex, tile);
      endTurn.setDisable(false);
      cancelSelEffect();
    }
  }

  /**
   * Deletes unused Hboxes on the main board. Checks if main board intersects with player hand, if
   * it does the tiles on the main board are resized.
   */
  private void updateBoard() {
    for (int i = board.getChildren().size()-1; i >= 0; i--) {
      HBox box = (HBox) board.getChildren().get(i);
      if (box.getChildren().isEmpty()) {
        board.getChildren().remove(box);
      }
    }

    if (board.intersects(
        board.sceneToLocal(selectionBoard.localToScene(selectionBoard.getBoundsInLocal())))) {
      System.out.println("INTERSECTS");
      double reduce = 0.95;
      tileHeight *= reduce;
      tileWidth *= reduce;
      for (int i = 0; i < board.getChildren().size(); i++) {
        HBox box = (HBox) board.getChildren().get(i);
        for (int j = 0; j < box.getChildren().size(); j++) {
          if (!(box.getChildren().get(j) instanceof Button)) {
            ImageView tile = (ImageView) box.getChildren().get(j);
            tile.setFitHeight(tileHeight);
            tile.setFitWidth(tileWidth);
          }
        }
      }
    }
  }

  /**
   * Handles user pressing "End Turn" button. Notifies model and disables control for this user.
   */
  @FXML
  private void handleEndTurn() {
    parser.finishedTurn();
    disableControl();
    cancelSelection();
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
   * Gets called when turn is finished, reloads board for all players
   */
  public void reloadBoard(List<List<Image>> boardTiles) {
    board.getChildren().clear();
    if (boardTiles.isEmpty()){
      tileWidth = DRAW_WIDTH;
      tileHeight = DRAW_HEIGHT;
    }
    for (List<Image> tiles : boardTiles) {
      HBox box = new HBox();
      for (Image image : tiles) {
        ImageView imageView = new ImageView();
        imageView.setFitHeight(tileHeight);
        imageView.setFitWidth(tileWidth);
        imageView.setImage(image);
        box.getChildren().add(imageView);
        onTileClicked(imageView);
        imageView.setDisable(true);
      }
      board.getChildren().add(box);

    }
  }

  /**
   * Disables control for the user. The bag and all buttons and tiles from the hand are set to
   * disabled.
   */
  private void disableControl() {
    enter.setDisable(true);
    endTurn.setDisable(true);
    cancelSelection.setDisable(true);
    addToExisting.setDisable(true);
    placeOnBoard.setDisable(true);
    swapJoker.setDisable(true);
    bag.setEffect(null);
    bag.setDisable(true);
    List<ImageView> handTiles = new ArrayList<>();
    handTiles.addAll(getTilesFromHand());
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
   * Returns all tiles from the players hand.
   *
   * @return all tiles from the players hand.
   */
  private List<ImageView> getTilesFromHand() {
    List<ImageView> handTiles = new ArrayList<>();
    for (int i = 0; i < topHand.getChildren().size(); i++) {
      ImageView iView = (ImageView) topHand.getChildren().get(i);
      handTiles.add(iView);
    }
    for (int i = 0; i < bottomHand.getChildren().size(); i++) {
      ImageView iView = (ImageView) bottomHand.getChildren().get(i);
      handTiles.add(iView);
    }
    return handTiles;
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

  /**
   * Gets called if it's this players turn, enables button and tile control. Plays short
   * notification sound.
   */
  public void enableControl() {
    enter.setDisable(false);
    cancelSelection.setDisable(false);
    addToExisting.setDisable(false);
    placeOnBoard.setDisable(false);
    swapJoker.setDisable(false);
    bag.setDisable(false);
    enableTilesOnHand();
    enableTilesOnBoard();
    playNotification();
    updateBoard();
  }

  /**
   * Enables all tiles on the main board.
   */
  private void enableTilesOnBoard() {
    for (int i = 0; i < board.getChildren().size(); i++) {
      HBox box = (HBox) board.getChildren().get(i);
      for (int j = 0; j < box.getChildren().size(); j++) {
        ImageView tile = (ImageView) box.getChildren().get(j);
        tile.setDisable(false);
      }
    }
  }

  /**
   * Enables all tiles on the players hand.
   */
  private void enableTilesOnHand() {
    for (int i = 0; i < topHand.getChildren().size(); i++) {
      ImageView iView = (ImageView) topHand.getChildren().get(i);
      iView.setDisable(false);
    }
    for (int i = 0; i < bottomHand.getChildren().size(); i++) {
      ImageView iView = (ImageView) bottomHand.getChildren().get(i);
      iView.setDisable(false);
    }
  }

  /**
   * For playing music on loop.
   */
  void playMusic() {
    Media media = null;
    try {
      media = new Media(getClass().getResource("/audio/Light-People.mp3").toURI().toString());
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    if (media != null) {
      mediaPlayer = new MediaPlayer(media);
      mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
      mediaPlayer.play();
    }
  }

  /**
   * Plays notification sound if it's this players turn.
   */
  private void playNotification() {
    AudioClip notification = null;
    try {
      notification = new AudioClip(getClass().getResource("/audio/ding.mp3").toURI().toString());
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    if (notification != null) {
      notification.setVolume(0.3);
      notification.play();
    }
  }

  /**
   * Handle user pressing mute or unmute icon. If music is playing, by clicking the icon music is
   * muted and vice versa.
   */
  @FXML
  private void handleMute(MouseEvent event) {
    if (!mediaPlayer.isMute()) {
      mediaPlayer.setMute(true);
      ImageView iView = (ImageView) event.getSource();
      URL url = this.getClass().getResource("/images/mute.png");
      String urlString = url.toString();
      Image unmute = new Image(urlString);
      iView.setImage(unmute);
    } else {
      mediaPlayer.setMute(false);
      ImageView iView = (ImageView) event.getSource();
      URL url = this.getClass().getResource("/images/unmute.png");
      String urlString = url.toString();
      Image unmute = new Image(urlString);
      iView.setImage(unmute);
    }

  }


  /**
   * Gets called if connection of another client has been lost. Closes game window and stops music.
   */
  public void closeGame() {
    this.stage.close();
    mediaPlayer.stop();
  }

  /**
   * Loads starting screen if another user has quit the game.
   *
   * @throws IOException if some error occurs while loading fxml file.
   */
  public void openLobby() throws IOException {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("/startingScreen.fxml"));
    Parent root = loader.load();
    StartingScreenController controller = loader.getController();
    Scene scene = new Scene(root);
    Stage primaryStage = new Stage();
    scene.getStylesheets().add("/button.css");
    primaryStage.setResizable(false);
    primaryStage.setTitle("RUMMYKUB");
    primaryStage.setScene(scene);
    primaryStage.show();
    primaryStage.getScene().getWindow()
        .addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, ControllerUtil::closeWindowEvent);
    controller.setStage(primaryStage);
    Alert alert = new Alert(AlertType.CONFIRMATION,
        "Another Player quit the game!", ButtonType.OK);
    alert.initOwner(primaryStage);
    alert.showAndWait();
  }


  /**
   * Gets called if user has won, loads winnerScreen and closes game window.
   *
   * @throws IOException if some error occurs while loading fxml file.
   */
  public void openWinScreen(List<Integer> points) throws IOException {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation((getClass().getResource("/winnerScreen.fxml")));
    Parent root = loader.load();
    Scene scene = new Scene(root);
    Stage stage = new Stage();
    stage.setScene(scene);
    stage.setResizable(false);
    stage.show();
    WinScreenController winScreenController = loader.getController();
    winScreenController.setNumberOfPlayers(numberOfPlayers);
    winScreenController.setNewGame(playerID);
    winScreenController.setStage(stage);
    winScreenController.playMusic();
    winScreenController.setPlayerPoints(points);
    winScreenController.setPointsNamesVisible();
    this.stage.close();
  }

  /**
   * Gets called if user has lost, loads loserScreen and closes game window.
   *
   * @throws IOException if some error occurs while loading fxml file.
   */
  public void openLoserScreen(List<Integer> points) throws IOException {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation((getClass().getResource("/loserScreen.fxml")));
    Parent root = loader.load();
    Scene scene = new Scene(root);
    Stage stage = new Stage();
    stage.setScene(scene);
    stage.setResizable(false);
    stage.show();
    LoseScreenController loController = loader.getController();
    loController.setID(playerID);
    loController.setNumberOfPlayers(numberOfPlayers);
    loController.setStage(stage);
    loController.setNewGameBtn();
    loController.playMusic();
    loController.setPlayerPoints(points);
    loController.setPointsNamesVisible();
    this.stage.close();
  }

  /**
   * Adds hover effect to bag on mouse entered.
   */
  @FXML
  private void handleStartHoverBag() {
    ControllerUtil.highlightTile(bag);
  }

  /**
   * Removes hover effect off bag on mouse exited.
   */
  @FXML
  private void handleStopHoverBag() {
    bag.setEffect(null);
  }
}


