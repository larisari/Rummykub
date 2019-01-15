package gui;

import gameinfo.tile.util.Number;
import gameinfo.tile.util.Color;
import gameinfo.tile.Tile;
import java.util.List;
import java.util.Optional;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GuiParser {

  public GuiParser(){

  }

  public static String parseToString(List<ImageView> tiles){
    String selectedT = "comb:";
    for (int i = 0; i < tiles.size(); i++){
      ImageView iView = tiles.get(i);
      Image tile = iView.getImage();
      String url = tile.getUrl();
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

  public static String parseTileToString(Optional<List<Tile>> stack){
    String parsedTiles = "comb:";
    List<Tile> tiles = stack.get();
    for (int i = 0; i < tiles.size(); i++){
      String colorNumber = parseColor(tiles.get(i).getColor(), tiles.get(i).getNumber().value());
      parsedTiles += colorNumber;
    }
    System.out.println(parsedTiles);
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
