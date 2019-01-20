package network;

import gui.EndScreenController;
import gui.GuiController;
import gui.LoadingScreenController;
import gui.StartingScreenController;
import gui.util.Image;
import javafx.application.Platform;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.List;

public class ClientParser {

  // TODO CALL ALL METHODS OF CONTROLLER IN ITS THREAD (POSSIBLE MAIN)

  private static String recievedMessageFromServer;
  private static String sendMessage;

  private static GuiController guiController;
  private static LoadingScreenController loadingScreenController;
  private static EndScreenController endScreenController;
  private static StartingScreenController startingScreenController;


  public ClientParser(GuiController controller) {
    guiController = controller;
  }

  public ClientParser(LoadingScreenController controller) { loadingScreenController = controller; }

  public ClientParser(EndScreenController controller) { endScreenController = controller; }

  public ClientParser(StartingScreenController controller) { startingScreenController = controller; }

  public static void getStringIntoClientParser(String receivedMessageFromServer) {
    receivedMessageFromServer = recievedMessageFromServer;
  }

  // Messages to send to Server.

  // TODO add null checks

  public void startGame() {
    Client.sendMessageToServer("startGame");
  }

  public void draw() {
    Client.sendMessageToServer("draw");
  }

  public void play(List<List<ImageView>> combinations) {
    StringBuilder builder = new StringBuilder();
    builder.append("play|");
    builder.append(GuiParser.parseListToString(combinations));
    Client.sendMessageToServer(builder.toString());
  }

  public void play(
          List<ImageView> tilesFromHand,
          List<ImageView> tilesFromBoard,
          List<List<ImageView>> newCombinations) {

    StringBuilder builder = new StringBuilder();

    builder.append("play|");

    builder.append(GuiParser.parseToString(tilesFromHand));

    builder.append("|");

    builder.append(GuiParser.parseToString(tilesFromBoard));

    builder.append("|");

    builder.append(GuiParser.parseListToString(newCombinations));

    Client.sendMessageToServer(builder.toString());
  }

  public void finishedTurn() {
    Client.sendMessageToServer("finishedTurn");
  }

  public void isFirstTurn() {
    Client.sendMessageToServer("isFirstTurn");
  }

  public void getNextPlayerID() {
    Client.sendMessageToServer("getNextPlayerID");
  }

  public void numberOfPlayers() {
    Client.sendMessageToServer("numberOfPlayers");
  }

  public void getPlayerID() {
    Client.sendMessageToServer("getPlayerID");
  }

  public void getPlayerPoints() {
    Client.sendMessageToServer("getPlayerPoints");
  }

  public void notifyWin() {
    Client.sendMessageToServer("notifyWin");
  }




  // Received messages from Server.

  static void parseForController(String message) {

    String[] messageAsArray = message.split("[|]");

    switch (messageAsArray[0]) {
      case "responseStartGame":
          Platform.runLater(() -> {
            try {
              loadingScreenController.openGameWindow();
            } catch (IOException e) {
              e.printStackTrace();
            }
          });
        break;

      case "closeStartScreen":
//        Platform.runLater(() -> startingScreenController.closeStartScreen());

      case "responseForDraw":
        Platform.runLater(() ->guiController.loadTiles(GuiParser.parseStringToImgsForOneComb(messageAsArray[1])));
        break;

      case "responseForFinishedTurn":
        Platform.runLater(() -> guiController.reloadBoard(GuiParser.parseStringToWholeBoard(messageAsArray[1])));
        break;

      case "itsYourTurn":
        Platform.runLater(() -> guiController.enableControl());
        break;

      case "responseForGetNextPlayerId":
        Platform.runLater(() ->guiController.updateNextPlayerName(Integer.parseInt(messageAsArray[1])));
        break;

      case "responseForPlay":
        if (messageAsArray[1].equals("true")) {
            Platform.runLater(() -> {
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

      case "responseForPlayBoard":
        // TODO wenn es in guiContoller steht.

      case "responseForGetNextPlayerID":
        Platform.runLater(() -> guiController.updateNextPlayerName(Integer.parseInt(messageAsArray[1])));
        break;

      case "responseForNumberOfPlayers":
        Platform.runLater(() -> guiController.setNumberOfPlayers(Integer.parseInt(messageAsArray[1])));
        break;

      case "responseForGetPlayerID":
        Platform.runLater(() -> guiController.setPlayerID(Integer.parseInt(messageAsArray[1])));
        break;

      case "responseForGetPlayerPoints":
        Platform.runLater(() -> endScreenController.setPlayerPoints(GuiParser.parseStringToIntegerList(messageAsArray[1])));
        break;
      case "possibleToStart":
        Platform.runLater(() -> loadingScreenController.enableStart());
        break;
      case "responseToNotifyWin":
          Platform.runLater(() -> {
            try {
              guiController.openLoserScreen();
            } catch (IOException e) {
              e.printStackTrace();
            }
          });
      // TODO REST
    }
  }
}
