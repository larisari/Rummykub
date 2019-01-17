package network;

import gui.GuiController;
import gui.LoadingScreenController;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.List;

public class ClientParser {

  private static String recievedMessageFromServer;
  private static String sendMessage;

  private static GuiController guiController;
  private static LoadingScreenController loadingScreenController;

  public ClientParser(GuiController controller) {
    guiController = controller;
  }

  public ClientParser(LoadingScreenController controller) {
    loadingScreenController = controller;
  }

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

  public void getAllTiles() {
    Client.sendMessageToServer("getAllTiles");
  }

  public void calculatePoints() {
    Client.sendMessageToServer("calculatePoints");
  }

  public void finishedTurn() {
    Client.sendMessageToServer("finishedTurn");
  }

  public void isFirstTurn() {
    Client.sendMessageToServer("isFirstTurn");
  }

  static void parseForController(String message) {

    String[] messageAsArray = message.split("[|]");

    switch (messageAsArray[0]) {
      case "responseForDraw":
        guiController.loadTiles(messageAsArray[1]);
        break;
      case "responseForGetNextPlayerId":
        guiController.updateNextPlayerName(Integer.parseInt(messageAsArray[1]));
        break;
      case "responseForPlay":
        if (messageAsArray[1].equals("true")) {
          guiController.placeTiles();
        } else if (messageAsArray[1].equals("false")) {
          guiController.cancelSelection();
        }
        break;
      case "responseForPlayBoard":
        // TODO wenn es in guiContoller steht.
      case "responseForGetNextPlayerID":
        guiController.updateNextPlayerName(Integer.parseInt(messageAsArray[1]));
        break;
      case "responseForNumberOfPlayers":
        guiController.setNumberOfPlayers(Integer.parseInt(messageAsArray[1]));
        break;
      case "responseForGetPlayerID":
        guiController.setPlayerID(Integer.parseInt(messageAsArray[1]));
        break;
      // TODO REST
      case "forStartGame":
        try {
          loadingScreenController.openGameWindow();
        } catch (IOException e) {
        }
        break;
    }
  }

  public void getNextPlayerID() {
    Client.sendMessageToServer("getNextPlayerID");
  }

  public void numberOfPlayers() {
    Client.sendMessageToServer("numberOfPlayers");
  }

  // Received messages from Server.

  public void getPlayerID() {
    Client.sendMessageToServer("getPlayerID");
  }
}
