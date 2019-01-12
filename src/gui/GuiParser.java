package gui;

import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GuiParser {

  public GuiParser(){

  }

  public static String parseToString(List<ImageView> selectedTiles){
    String selectedT = "comb:";
    for (int i = 0; i < selectedTiles.size(); i++){
      ImageView iView = selectedTiles.get(i);
      Image tile = iView.getImage();
      String url = tile.getUrl();
      String [] urlArray = url.split("/");
      String color = urlArray[urlArray.length-2];
      String [] numberArray = urlArray[urlArray.length-1].split("[.]");
      String number = numberArray[0];
      selectedT += "tile." + color + "/" + number;
      if (i != selectedTiles.size()-1){
        selectedT += ",";
      }
    }
    return selectedT;
  }
}
