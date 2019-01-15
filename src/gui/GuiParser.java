package gui;

import gui.util.Image;
import gameinfo.tile.util.Number;
import gameinfo.tile.util.Color;
import gameinfo.tile.Tile;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.scene.image.ImageView;

public class GuiParser {

  public GuiParser(){

  }

  public static String parseToString(List<ImageView> tiles){
    String selectedT = "comb:";
    for (int i = 0; i < tiles.size(); i++){
      ImageView iView = tiles.get(i);
      Image tile = (Image) iView.getImage();
      String url = tile.getURL();
      String [] urlArray = url.split("/");
      String color = urlArray[urlArray.length-2];
      String [] numberArray = urlArray[urlArray.length-1].split("[.]");
      String number = numberArray[0];
      selectedT += "tile." + color + "/" + number;
      if (i != tiles.size()-1){
        selectedT += ",";
      }
    }
    return selectedT;
  }

  public static List<Tile> parseStringToTile(String tiles) {
    List<Tile> tileList = new ArrayList<>();
    String[] block = tiles.split(":");
    String[] tileS = block[1].split(",");
    for (int i = 0; i < tileS.length; i++) {
      String[] attributeswS = tileS[i].split("[.]");
      String[] attributes = attributeswS[1].split("/");
      String color = attributes[0];
      String number = attributes[1];
      Color tileColor = null;
      Number tileNumber = null;
      switch (color){
        case "blue":
          tileColor = Color.BLUE;
          break;
        case "red":
          tileColor = Color.RED;
          break;
        case "yellow":
          tileColor = Color.YELLOW;
          break;
        case "black":
          tileColor = Color.BLACK;
          break;
        case "joker":
          tileColor = Color.JOKER;
          break;
          default:
            break;
      }
      switch (number){
        case "1":
          tileNumber = Number.ONE;
          break;
        case "2":
          tileNumber = Number.TWO;
          break;
        case "3":
          tileNumber = Number.THREE;
          break;
        case "4":
          tileNumber = Number.FOUR;
          break;
        case "5":
          tileNumber = Number.FIVE;
          break;
        case "6":
          tileNumber = Number.SIX;
          break;
        case "7":
          tileNumber = Number.SEVEN;
          break;
        case "8":
          tileNumber = Number.EIGHT;
          break;
        case "9":
          tileNumber = Number.NINE;
          break;
        case "10":
          tileNumber = Number.TEN;
          break;
        case "11":
          tileNumber = Number.ELEVEN;
          break;
        case "12":
          tileNumber = Number.TWELVE;
          break;
        case "13":
          tileNumber = Number.THIRTEEN;
          break;
        case "joker":
          tileNumber = Number.JOKER;
          default:
            break;
      }
      Tile tile = new Tile(tileNumber, tileColor);
      tileList.add(tile);
    }
    return tileList;
    
  }

  public static String parseTileToString(Optional<List<Tile>> stack){
    String parsedTiles = "comb:";
    List<Tile> tiles = stack.get();
    for (int i = 0; i < tiles.size(); i++){
      String colorNumber = parseColor(tiles.get(i).getColor(), tiles.get(i).getNumber().value());
      parsedTiles += colorNumber;
    }
return parsedTiles;
  }

  private static String parseColor(Color color, int number){
    StringBuilder builder = new StringBuilder();
    builder.append("tile.");

    switch (color) {
      case BLUE:
      builder.append("blue");
      break;
      case RED:
      builder.append("red");
      break;
      case BLACK:
        builder.append("black");
        break;
      case YELLOW:
        builder.append("yellow");
        break;
      case JOKER:
      builder.append("joker");
      break;
      default:
        break;
    }


    builder.append("/");


    if (number == 0) {
      builder.append("joker");
    } else {
      builder.append(number);
    }
    builder.append(",");

    return builder.toString();
  }
}
