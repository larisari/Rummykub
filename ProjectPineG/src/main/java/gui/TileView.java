package gui;

import gui.util.Image;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;


public class TileView {

  private static final int TILE_WIDTH = 45;
  private static final int TILE_HEIGHT = 65;


  public static void highlightTile(Node node) {
    DropShadow borderGlow = new DropShadow();
    borderGlow.setOffsetX(0f);
    borderGlow.setOffsetY(0f);
    borderGlow.setColor(Color.WHITE);
    borderGlow.setWidth(50);
    borderGlow.setHeight(50);

    node.setEffect(borderGlow);
  }

}
