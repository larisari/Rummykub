package network;

import gameinfo.util.GIColor;
import gameinfo.util.GINumber;
import gameinfo.util.GITile;
import gui.GuiController;
import gui.util.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class GuiParser {
  //TODO methoden vom controller nonstatic, antwortmethoden static
//parse format
  //  list<comb:tile.color/number,tile.color/number;comb:tile.color/number;
  private static GuiController guiController;

  public GuiParser(GuiController controller) {
    guiController = controller;
  }

  // TODO rename method
  public static String parseListToString(List<List<ImageView>> listOfTiles) {
    StringBuilder builder = new StringBuilder();
    builder.append("list<");
    listOfTiles.forEach(combination -> builder.append(GuiParser.parseToString(combination)));

    return builder.toString();
  }

  public static String parseToString(List<ImageView> tiles) {
    String selectedT = "comb:";
    for (int i = 0; i < tiles.size(); i++) {
      ImageView iView = tiles.get(i);
      Image tile = (Image) iView.getImage();
      String url = tile.getURL();
      String[] urlArray = url.split("/");
      String color = urlArray[urlArray.length - 2];
      String[] numberArray = urlArray[urlArray.length - 1].split("[.]");
      String number = numberArray[0];
      selectedT += "tile." + color + "/" + number;
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

  public static void response(String antwort) {
    if (antwort.equals("true")) {
      guiController.placeTiles();
    } else if (antwort.equals("false")) {
      guiController.cancelSelEffect();
    }
  }

  //ergebnis in liste packen -> List<List<GITile>>
  public static List<GITile> parseStringToTile(String tiles) {
    List<GITile> tileList = new ArrayList<>();
    tiles = tiles.substring(0, tiles.length() - 1);
    System.out.println(tiles);
    String[] comb = tiles.split(":");
    String[] tileS = comb[1].split(",");
    for (int i = 0; i < tileS.length; i++) {
      String[] attributeswS = tileS[i].split("[.]");
      String[] attributes = attributeswS[1].split("/");
      String color = attributes[0];
      String number = attributes[1];
      GIColor tileColor = null;
      GINumber tileNumber = null;
      switch (color) {
        case "blue":
          tileColor = GIColor.BLUE;
          break;
        case "red":
          tileColor = GIColor.RED;
          break;
        case "yellow":
          tileColor = GIColor.YELLOW;
          break;
        case "black":
          tileColor = GIColor.BLACK;
          break;
        case "joker":
          tileColor = GIColor.JOKER;
          break;
        default:
          break;
      }
      switch (number) {
        case "1":
          tileNumber = GINumber.ONE;
          break;
        case "2":
          tileNumber = GINumber.TWO;
          break;
        case "3":
          tileNumber = GINumber.THREE;
          break;
        case "4":
          tileNumber = GINumber.FOUR;
          break;
        case "5":
          tileNumber = GINumber.FIVE;
          break;
        case "6":
          tileNumber = GINumber.SIX;
          break;
        case "7":
          tileNumber = GINumber.SEVEN;
          break;
        case "8":
          tileNumber = GINumber.EIGHT;
          break;
        case "9":
          tileNumber = GINumber.NINE;
          break;
        case "10":
          tileNumber = GINumber.TEN;
          break;
        case "11":
          tileNumber = GINumber.ELEVEN;
          break;
        case "12":
          tileNumber = GINumber.TWELVE;
          break;
        case "13":
          tileNumber = GINumber.THIRTEEN;
          break;
        case "joker":
          tileNumber = GINumber.JOKER;
        default:
          break;
      }
      GITile tile = new GITile(tileNumber, tileColor);
      tileList.add(tile);
    }
    return tileList;

  }
}
