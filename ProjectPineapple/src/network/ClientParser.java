package network;

import gui.GuiController;
import javafx.scene.image.ImageView;

import java.util.List;

public class ClientParser {

  private static String recievedMessageFromServer;
  private static String sendMessage;

  private static GuiController guiController;

  public ClientParser(GuiController controller) {
    guiController = controller;
  }

  public static void getStringIntoClientParser(String receivedMessageFromServer) {
    receivedMessageFromServer = recievedMessageFromServer;
  }

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
}
