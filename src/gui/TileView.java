package gui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;


public class TileView {

  private static final int TILE_WIDTH = 45;
  private static final int TILE_HEIGHT = 65;
  @FXML
  private HBox topHand;
  @FXML
  private HBox bottomHand;

/**
  public Image getImage(Tile tile) {
    String color = null;
    Color tileColor = tile.getColor();
    switch (tileColor) {
      case BLACK:
        color = "black";
        break;
      case BLUE:
        color = "blue";
        break;
      case RED:
        color = "red";
        break;
      case YELLOW:
        color = "yellow";
        break;
    }
      if (tile.getColor().equals(Color.JOKER)) {
        Image joker = new Image(
            getClass().getResourceAsStream("images/tiles/" + color + "/joker" + ".png"));
        return joker;
      } else {
        Image normTile = new Image(getClass()
            .getResourceAsStream("images/tiles/" + color + "/" + tile.getNumber() + ".png"));
        return normTile;
      }
    }

**/
  /**
   * Returns false if ImageView contains an image or if an error occured while loading.
   *
   * @param imageV - ImageView to be checked.
   */
  private static boolean isEmpty(ImageView imageV) {
    Image image = imageV.getImage();
    if (image != null || image.isError()) {
      return false;
    } else {
      return true;
    }
  }

  /**
   * If topHand is full insert in bottomHand.
   */
  public void createTile(Image tile) {
    for (int i = 0; i < topHand.getChildren().size(); i++) {
      Node node = topHand.getChildren().get(i);
      if (isEmpty((ImageView) node)) {
        ((ImageView) node).setImage(tile);
        return;
      }
    }
    for (int i = 0; i < bottomHand.getChildren().size(); i++) {
      Node node = bottomHand.getChildren().get(i);
      if (isEmpty((ImageView) node)) {
        ((ImageView) node).setImage(tile);
      }
    }

  }

  public static void highlightTile(ImageView imageV) {
    DropShadow borderGlow = new DropShadow();
    borderGlow.setOffsetX(0f);
    borderGlow.setOffsetY(0f);
    borderGlow.setColor(Color.WHITE);
    borderGlow.setWidth(50);
    borderGlow.setHeight(50);

    imageV.setEffect(borderGlow);
  }

/**
 public ImageView createTileView(gui.Tile tile){
 ImageView iView = new ImageView(getImage(tile));
 iView.setFitHeight(TILE_HEIGHT);
 iView.setFitWidth(TILE_WIDTH);
 return iView;

 }
 **/

}
