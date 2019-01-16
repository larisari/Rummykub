package gui;

import gui.util.Image;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;


public class TileView {

  private static final int TILE_WIDTH = 45;
  private static final int TILE_HEIGHT = 65;
  @FXML
  private HBox topHand;
  @FXML
  private HBox bottomHand;

  /**
   * Returns false if ImageView contains an image or if an error occured while loading.
   *
   * @param imageV - ImageView to be checked.
   */
  private static boolean isEmpty(ImageView imageV) {
    Image image = (Image) imageV.getImage();
    return image == null && !image.isError();
  }

  public static void highlightTile(Node node) {
    DropShadow borderGlow = new DropShadow();
    borderGlow.setOffsetX(0f);
    borderGlow.setOffsetY(0f);
    borderGlow.setColor(Color.WHITE);
    borderGlow.setWidth(50);
    borderGlow.setHeight(50);

    node.setEffect(borderGlow);
  }

  /**
   *
   */
  List<Image> createImgs(String hand) {
    List<Image> tilesImg = new ArrayList<>();
    String[] block = hand.split(":");
    String[] tiles = block[1].split(",");
    for (int i = 0; i < tiles.length; i++) {
      String[] attributeswS = tiles[i].split("[.]");
      String[] attributes = attributeswS[1].split("/");
      String color = attributes[0];
      String number = attributes[1];

      if (number.equals("joker")) {
        Image joker = new Image("gui/images/tiles/joker/joker.png");
        tilesImg.add(joker);
      } else {
        Image tile = new Image("gui/images/tiles/" + color + "/" + number + ".png");
        tilesImg.add(tile);
      }


    }
    return tilesImg;
  }

}
