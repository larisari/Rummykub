package network;

import gui.GuiController;
import gui.LoadingScreenController;
import gui.LoseScreenController;
import gui.StartingScreenController;
import gui.WinScreenController;
import gui.util.ControllerUtil;
import java.io.IOException;
import java.util.List;
import javafx.application.Platform;
import javafx.scene.image.ImageView;

/**
 * Clients Parser connects the controller with the Client-Server architecture and translates in
 * beetween.
 */
public class ClientParser {

  // TODO CALL ALL METHODS OF CONTROLLER IN ITS THREAD (POSSIBLE MAIN)

  private static boolean gameStarted;
  private static GuiController guiController;
  private static LoadingScreenController loadingScreenController;
  private static WinScreenController winScreenController;
  private static StartingScreenController startingScreenController;
  private static LoseScreenController loseScreenController;

  private static GuiParser guiParser = new GuiParser();

  public ClientParser(GuiController controller) {
    guiController = controller;
  }

  public ClientParser(LoadingScreenController controller) {
    loadingScreenController = controller;
  }

  public ClientParser(WinScreenController controller) {
    winScreenController = controller;
  }

  public ClientParser(LoseScreenController controller) {
    loseScreenController = controller;
  }

  public ClientParser(StartingScreenController controller) {
    startingScreenController = controller;
  }


  // TODO add null checks

  /**
   * Parses and incoming String from the server and initialize controllers reaction.
   *
   * @param message Incoming String-message from server.
   */
  static void parseForController(String message) {

    String[] messageAsArray = message.split("[|]");

    switch (messageAsArray[0]) {
      case "Restart":
        if(gameStarted) {
          Platform.runLater(
                  () -> {
                    guiController.closeGame();
                  });
        } else {
          loadingScreenController.closeLoadingScreen();
        }

        Platform.runLater(
            () -> {
              try {
                guiController.openLobby();
              } catch (IOException e) {
                e.printStackTrace();
              }
            });

        break;
      case "responseStartGame":
        gameStarted = true;
        Platform.runLater(
            () -> {
              try {
                loadingScreenController.openGameWindow(
                    Integer.parseInt(messageAsArray[1]), Integer.parseInt(messageAsArray[2]));
              } catch (IOException e) {
                e.printStackTrace();
              }
            });
        break;

      case "responseForDraw":
        Platform.runLater(
            () ->
                guiController.loadTiles(guiParser.parseStringToImgsForOneComb(messageAsArray[1])));
        break;

      case "responseForFinishedTurn":
        //        if (!messageAsArray[1].equals("list<")) {
        Platform.runLater(
            () -> guiController.reloadBoard(guiParser.parseStringToWholeBoard(messageAsArray[1])));
        //        }
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
                guiController.placeTiles();
              });
        } else if (messageAsArray[1].equals("false")) {
          Platform.runLater(() -> guiController.cancelSelEffect());
          Platform.runLater(() -> ControllerUtil.invalidCombinations());
        }
        break;

      case "responseForPlayHandWithBoard":
        if (messageAsArray[1].equals("true")) {
          Platform.runLater(
              () -> {
                guiController.moveTiles();
              });
        } else if (messageAsArray[1].equals("false")) {
          Platform.runLater(() -> guiController.cancelSelEffect());
          Platform.runLater(() -> ControllerUtil.invalidCombinations());
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
        System.out.println("ENTERED RESPONSE IN CLIENTPARSER");
        if (messageAsArray[1].equals("true")) {
          System.out.println("RESPONSE ADD BACK TRUE!");
          Platform.runLater(
              () -> {
                System.out.println("RESPONSE ADD BACK LAMBDA!");
                guiController.allowAddBack();
              });
        } else if (messageAsArray[1].equals("false")) {
          Platform.runLater(() -> guiController.disallowAddTo());
          Platform.runLater(() -> ControllerUtil.invalidCombinations());
          System.out.println("RESPONSE ADD BACK FALSE!");
        }
        break;

      case "responseForPlayWithBoardTilesL":
        if (messageAsArray[1].equals("true")) {
          Platform.runLater(
              () -> {
                guiController.allowAddFront();
              });
        } else if (messageAsArray[1].equals("false")) {
          Platform.runLater(() -> guiController.disallowAddTo());
          Platform.runLater(() -> ControllerUtil.invalidCombinations());
        }
        break;

      case "responseForNumberOfPlayers":
        Platform.runLater(
            () -> guiController.setNumberOfPlayers(Integer.parseInt(messageAsArray[1])));
        break;

      case "responseForGetPlayerPoints":
        Platform.runLater(
            () -> {
              try {
                guiController.openWinScreen(GuiParser.parseStringToIntegerList(messageAsArray[1]));
              } catch (IOException e) {
                e.printStackTrace();
              }
            });

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
                guiController.openLoserScreen(
                    GuiParser.parseStringToIntegerList(messageAsArray[1]));
              } catch (IOException e) {
                e.printStackTrace();
              }
            });
        break;
      case "closeGame":
        Platform.runLater(
            () -> guiController.closeGame());
        break;
      case "openLobby":
        Platform.runLater(
            () -> {
              try {
                guiController.openLobby();
              } catch (IOException e) {
                e.printStackTrace();
              }
            });
        break;
    }
  }

  /** Sends a message to server to initalize to start the game. */
  public void startGame() {
    Client.sendMessageToServer("startGame");
  }

  /** Sends a message to server to initalize the drawing function of the game. */
  public void draw() {
    Client.sendMessageToServer("draw");
  }

  /** Sends a message to server with a combination, Client wants to play. */
  public void play(List<List<ImageView>> combinations) {
    StringBuilder builder = new StringBuilder();
    builder.append("play|");
    builder.append(GuiParser.parseListToString(combinations));
    Client.sendMessageToServer(builder.toString());
  }

  /**
   * Sends a message to server with a combination, Client wants to play within a joker. //TODO
   * javadoc
   */
  public void playSwapJoker(
      List<ImageView> tilesFromHand, List<List<ImageView>> oldComb, List<List<ImageView>> newComb) {
    StringBuilder builder = new StringBuilder();
    builder.append("playSwapJoker|");
    builder.append(GuiParser.parseToString(tilesFromHand));

    builder.append("|");

    builder.append(GuiParser.parseListToString(oldComb));

    builder.append("|");

    builder.append(GuiParser.parseListToString(newComb));

    Client.sendMessageToServer(builder.toString());
  }

  /**
   * Request to the server, if selectedTiles can be combined with combination on board.
   *
   * @param tilesFromHand List of tiles from hand.
   * @param oldBoardCombs List of board combinations before alteration.
   * @param newBoardCombs List of board combinations that should be altered.
   */
  public void playHandWithBoard(
      List<ImageView> tilesFromHand,
      List<List<ImageView>> oldBoardCombs,
      List<List<ImageView>> newBoardCombs) {
    StringBuilder builder = new StringBuilder();
    builder.append("playHandWithBoard|");

    builder.append(GuiParser.parseToString(tilesFromHand));

    builder.append("|");

    builder.append(GuiParser.parseListToString(oldBoardCombs));

    builder.append("|");

    builder.append(GuiParser.parseListToString(newBoardCombs));

    Client.sendMessageToServer(builder.toString());
  }

  /**
   * Request to server to add tiles to an existing comb on the left.
   *
   * @param tilesFromHand tiles on the hand, we want to add on the left site.
   * @param tilesFromBoard combination on the board where tiles from hand should be added.
   * @param newCombination new combination that should be created.
   */
  public void playL(
      List<ImageView> tilesFromHand,
      List<ImageView> tilesFromBoard,
      List<List<ImageView>> newCombination) {

    StringBuilder builder = new StringBuilder();

    builder.append("playWithBoardTilesL|");

    builder.append(GuiParser.parseToString(tilesFromHand));

    builder.append("|");

    builder.append(GuiParser.parseToString(tilesFromBoard));

    builder.append("|");

    builder.append(GuiParser.parseListToString(newCombination));

    Client.sendMessageToServer(builder.toString());
  }

  /**
   * Request to server to add tiles to an existing comb on the right.
   *
   * @param tilesFromHand List of tiles on the hand, client wants to add on the right site.
   * @param tilesFromBoard List of tiles on the board.
   * @param newCombinations new combination that should be created.
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

  /** Signalizes the server, that client has finished his turn. */
  public void finishedTurn() {
    Client.sendMessageToServer("finishedTurn");
  }

  public void getPlayerPoints() {
    Client.sendMessageToServer("calculatePointsForRegisteredPlayers");
  }


  public void notifyWin() {
    Client.sendMessageToServer("notifyWin");
  }

  public void setAgeFor(String age) {
    Integer.parseInt(age);
    Client.sendMessageToServer("setAge|" + age);
  }

  public void newGame() {
    Client.sendMessageToServer("newGame");
    Client.sendMessageToServer("startGame");
  }
}
