package network;

import gameinfo.util.GIColor;
import gameinfo.util.GINumber;
import gameinfo.util.GITile;
import gui.GuiController;
import gui.util.Image;
import java.io.IOException;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class GuiParser {

  // parse format
  // list<comb:tile.color/number,tile.color/number;comb:tile.color/number;
  private static GuiController guiController;

  public GuiParser(GuiController controller) {
    guiController = controller;
  }

  // TODO rename method
  public static String parseListToString(List<List<ImageView>> listOfTiles) {
    StringBuilder builder = new StringBuilder();
    builder.append("list<");
    listOfTiles.forEach(combination -> builder.append(parseToString(combination)));
    return builder.toString();
  }

  public static String parseToString(List<ImageView> tiles) {
    String selectedT = "comb:";
    for (int i = 0; i < tiles.size(); i++) {
      ImageView iView = tiles.get(i);
      Image tile = (Image) iView.getImage();
      String url = tile.getURL();
      System.out.println(url);
      String[] urlArray = url.split("/");
      String color = urlArray[urlArray.length - 2];
      String[] numberArray = urlArray[urlArray.length - 1].split("[.]");
      String number = numberArray[0];
      if (number.equals("20")){
        selectedT += "tile." + color + "/joker";
      } else {
        selectedT += "tile." + color + "/" + number;
      }
      if (i != tiles.size() - 1) {
        selectedT += ",";
      }
    }
    return selectedT + ";";
  }

  public static List<Integer> parseStringToIntegerList(String pointsFromServer){
    List<Integer> points = new ArrayList<>();
    String[] pointsString = pointsFromServer.split(",");
    for (String point : pointsString){
      points.add(Integer.parseInt(point));
    }
    return points;
  }

  /*
  public static void response(String antwort) {
    if (antwort.equals("true")) {
      try {
        guiController.placeTiles();
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else if (antwort.equals("false")) {
      guiController.cancelSelEffect();
    }
  }*/




  static List<List<Image>> parseStringToWholeBoard(String message){
    List<List<Image>> endList = new ArrayList<>();
    String[] tempS = message.split("<");
    String[] combs = tempS[1].split(";");
    for (int i = 0; i < combs.length; i++){
      endList.add(parseStringToImgsForOneComb(combs[i]));
    }
    return endList;
  }

  static List<Image> parseStringToImgsForOneComb(String hand) {
    List<Image> tilesImg = new ArrayList<>();
    hand = hand.substring(0,hand.length()-1);
    String[] block = hand.split(":");
    String[] tiles = block[1].split(",");
    for (int i = 0; i < tiles.length; i++) {
      String[] attributeswS = tiles[i].split("[.]");
      String[] attributes = attributeswS[1].split("/");
      String color = attributes[0];
      String number = attributes[1];

      if (color.equals("joker")) {
        Image joker = new Image("gui/images/tiles/joker/joker.png", "joker");
        tilesImg.add(joker);
      } else {
        Image tile = new Image("gui/images/tiles/" + color + "/" + number + ".png");
        tilesImg.add(tile);
      }
    }
    return tilesImg;
  }
}