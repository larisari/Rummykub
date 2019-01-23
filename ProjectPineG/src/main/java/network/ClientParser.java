package network;

import gui.EndScreenController;
import gui.GuiController;
import gui.LoadingScreenController;
import gui.StartingScreenController;
import gui.util.Image;
import java.sql.SQLOutput;
import javafx.application.Platform;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.List;
import org.graalvm.compiler.code.SourceStackTraceBailoutException;

/**
 * Clients Parser connects the controller with the Client-Server architecture and translates in
 * beetween.
 */
public class ClientParser {

  // TODO CALL ALL METHODS OF CONTROLLER IN ITS THREAD (POSSIBLE MAIN)

  private static String recievedMessageFromServer;
  private static String sendMessage;

  private static GuiController guiController;
  private static LoadingScreenController loadingScreenController;
  private static EndScreenController endScreenController;
  private static StartingScreenController startingScreenController;

  private static GuiParser guiParser = new GuiParser();

  public ClientParser(GuiController controller) {
    guiController = controller;
  }

  public ClientParser(LoadingScreenController controller) {
    loadingScreenController = controller;
  }

  public ClientParser(EndScreenController controller) {
    endScreenController = controller;
  }

  public ClientParser(StartingScreenController controller) {
    startingScreenController = controller;
  }

  public static void getStringIntoClientParser(String receivedMessageFromServer) {
    receivedMessageFromServer = recievedMessageFromServer;
  }

  // Messages to send to Server.

  // TODO add null checks

  /**
   * Parses and incoming String from the server and initialize controllers reaction.
   * @param message Incoming String-message from server.
   */
  static void parseForController(String message) {

    String[] messageAsArray = message.split("[|]");

    switch (messageAsArray[0]) {
      case "responseStartGame":
        Platform.runLater(
            () -> {
              try {
                System.out.println(loadingScreenController);
                loadingScreenController.openGameWindow(
                    Integer.parseInt(messageAsArray[1]), Integer.parseInt(messageAsArray[2]));
              } catch (IOException e) {
                e.printStackTrace();
              }
            });
        break;

      case "closeStartScreen":
        //        Platform.runLater(() -> startingScreenController.closeStartScreen());
        break;

      case "responseForDraw":
        Platform.runLater(
            () ->
                guiController.loadTiles(guiParser.parseStringToImgsForOneComb(messageAsArray[1])));
        break;

      case "responseForFinishedTurn":
        if (!messageAsArray[1].equals("list<")) {
          Platform.runLater(
              () ->
                  guiController.reloadBoard(guiParser.parseStringToWholeBoard(messageAsArray[1])));
        }
        break;

      case "itsYourTurn":
        Platform.runLater(() -> guiController.enableControl());
        break;

      case "UpdateCurrentPlayerTurn":
        Platform.runLater(
            () -> guiController.updateNextPlayerName(Integer.parseInt(messageAsArray[1])));
        break;

      case "responseForPlay":
        if (messageAsArray[1].equals("true")) {
          Platform.runLater(
              () -> {
                try {
                  guiController.placeTiles();
                } catch (IOException e) {
                  e.printStackTrace();
                }
              });
        } else if (messageAsArray[1].equals("false")) {
          Platform.runLater(() -> guiController.cancelSelEffect());
        }
        break;

      case "responseForPlayHandWithBoard":
        if (messageAsArray[1].equals("true")) {
          Platform.runLater(
              () -> {
                try {
                  guiController.moveTiles();
                } catch (IOException e) {
                  e.printStackTrace();
                }
              });
        } else if (messageAsArray[1].equals("false")) {
          Platform.runLater(() -> guiController.cancelSelEffect());
        }
        break;

      case "responseForPlaySwapJoker":
        if (messageAsArray[1].equals("true")) {
          Platform.runLater(() -> guiController.swapWithJoker());
        } else if (messageAsArray[1].equals("false")) {
          Platform.runLater(() -> guiController.cancelSelEffect());
        }
        break;

      case "responseForPlayWithBoardTilesR":
        if (messageAsArray[1].equals("true")) {
          Platform.runLater(
              () -> {
                try {
                  guiController.allowAddBack();
                } catch (IOException e) {
                  e.printStackTrace();
                }
              });
        } else if (messageAsArray[1].equals("false")) {
          Platform.runLater(() -> guiController.disallowAddTo());
        }
        break;

      case "responseForPlayWithBoardTilesL":
        if (messageAsArray[1].equals("true")) {
          Platform.runLater(
              () -> {
                try {
                  guiController.allowAddFront();
                } catch (IOException e) {
                  e.printStackTrace();
                }
              });
        } else if (messageAsArray[1].equals("false")) {
          Platform.runLater(() -> guiController.disallowAddTo());
        }
        break;

      case "responseForNumberOfPlayers":
        Platform.runLater(
            () -> guiController.setNumberOfPlayers(Integer.parseInt(messageAsArray[1])));
        break;

        /*
        case "responseForGetPlayerID":
          Platform.runLater(() -> loadingScreenController.setPlayerID(Integer.parseInt(messageAsArray[1])));
          break;
          */
      case "responseForGetPlayerPoints":
        Platform.runLater(
            () ->
                endScreenController.setPlayerPoints(
                    GuiParser.parseStringToIntegerList(messageAsArray[1])));
        break;

      case "possibleToStart":
        Platform.runLater(() -> loadingScreenController.enableStart());
        break;

      case "addJoined":
        Platform.runLater(
            () -> loadingScreenController.addJoined(Integer.parseInt(messageAsArray[1])));
        break;

      case "loadLoadingScreen":
        Platform.runLater(
            () -> {
              try {
                startingScreenController.loadLoadingScreen(Integer.parseInt(messageAsArray[1]));
              } catch (IOException e) {
                e.printStackTrace();
              }
            });
        break;
      case "responseToNotifyWin":
        Platform.runLater(
            () -> {
              try {
                guiController.openLoserScreen();
              } catch (IOException e) {
                e.printStackTrace();
              }
            });
        break;
    }
  }

  /**
   * Sends a message to server to initalize to start the game.
   */
  public void startGame() {
    Client.sendMessageToServer("startGame");
  }

  /**
   * Sends a message to server to initalize the drawing function of the game.
   */
  public void draw() {
    Client.sendMessageToServer("draw");
  }

  /**
   * Sends a message to server with a combination, Client wants to play.
   */
  public void play(List<List<ImageView>> combinations) {
    StringBuilder builder = new StringBuilder();
    builder.append("play|");
    builder.append(GuiParser.parseListToString(combinations));
    Client.sendMessageToServer(builder.toString());
  }

  /**
   * Sends a message to server with a combination, Client wants to play within a joker.
   */
  public void playSwapJoker(List<List<ImageView>> combinations) {
    StringBuilder builder = new StringBuilder();
    builder.append("playSwapJoker|");
    builder.append(GuiParser.parseListToString(combinations));
    Client.sendMessageToServer(builder.toString());
  }

  /**
   * Request to the server, if selectedTiles can be combined with combination on board.
   * @param selectedTiles  List of tiles on players hand.
   * @param board List of comination-Tiles.
   */
  public void playHandWithBoard(List<ImageView> selectedTiles, List<List<ImageView>> board) {
    StringBuilder builder = new StringBuilder();
    builder.append("playHandWithBoard|");

    builder.append(GuiParser.parseToString(selectedTiles));

    builder.append("|");

    builder.append(GuiParser.parseListToString(board));

    Client.sendMessageToServer(builder.toString());
  }

  /**
   *Request to server to add tiles to an existing comb on the left.
   * @param tilesFromHand tiles on the hand, we want to add on the left site.
   * @param CombinationsOnBoard List of existing tile-combination.
   */
  public void playL(List<ImageView> tilesFromHand, List<List<ImageView>> CombinationsOnBoard) {

    StringBuilder builder = new StringBuilder();

    builder.append("playWithBoardTilesL|");

    builder.append(GuiParser.parseToString(tilesFromHand));

    builder.append("|");

    builder.append(GuiParser.parseListToString(CombinationsOnBoard));

    Client.sendMessageToServer(builder.toString());
  }


  /**
   *Request to server to add tiles to an existing comb on the right.
   * @param tilesFromHand List of tiles on the hand, client wants to add on the right site.
   * @param tilesFromBoard List of tiles on the board.
   * @param newCombinations new comb.
   */
  public void playR(
      List<ImageView> tilesFromHand,
      List<ImageView> tilesFromBoard,
      List<List<ImageView>> newCombinations) {

    StringBuilder builder = new StringBuilder();

    builder.append("playWithBoardTilesR|");

    builder.append(GuiParser.parseToString(tilesFromHand));

    builder.append("|");

    builder.append(GuiParser.parseToString(tilesFromBoard));

    builder.append("|");

    builder.append(GuiParser.parseListToString(newCombinations));

    Client.sendMessageToServer(builder.toString());
  }

  /**
   * Signalizes the server, that client has finished his turn.
   */
  public void finishedTurn() {
    Client.sendMessageToServer("finishedTurn");
  }


  public void isFirstTurn() {
    Client.sendMessageToServer("isFirstTurn");
  }

  /**
   * Sends a message to server to get nextPLayersID.
   */
  public void getNextPlayerID() {
    Client.sendMessageToServer("getNextPlayerID");
  }

  // public void getPlayerID() { Client.sendMessageToServer("getPlayerID"); }

  public void numberOfPlayers() {
    Client.sendMessageToServer("numberOfPlayers");
  }

  public void getPlayerPoints() {
    Client.sendMessageToServer("calculatePointsForRegisteredPlayers");
  }

  // Received messages from Server.

  public void notifyWin() {
    Client.sendMessageToServer("notifyWin");
  }
}
