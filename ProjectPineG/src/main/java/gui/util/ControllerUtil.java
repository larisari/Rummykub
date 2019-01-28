package gui.util;

import java.util.List;
import java.util.Optional;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.WindowEvent;

/**
 * Helper class for controllers, mostly GuiController.
 */
public class ControllerUtil {

  private final static int DRAW_HEIGHT = 65;
  private final static int DRAW_WIDTH = 45;

  public ControllerUtil() {
  }

  /**
   * Creates highlight effect for given node.
   * @param node - to be highlighted.
   */
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
   * Inserts image into ImageView.
   * @param tile - image to be inserted.
   * @return ImageView with inserted image.
   */
  public static ImageView setImage(Image tile) {
    ImageView imageView = new ImageView();
    imageView.setFitHeight(DRAW_HEIGHT);
    imageView.setFitWidth(DRAW_WIDTH);
    imageView.setPreserveRatio(true);
    imageView.setImage(tile);
    return imageView;
  }


  /**
   * Deletes duplicate combinations.
   *
   * @param combinations from which duplicates should be deleted.
   */
  public static void eraseDuplicate(List<List<ImageView>> combinations) {
    for (int i = combinations.size() - 1; i > 0; i--) {
      if (haveSameChildren(combinations.get(i), combinations.get(i - 1))) {
        combinations.remove(i);
      }
    }
  }

  /**
   * Checks if two combinations are duplicates.
   *
   * @param comb - a tile combination.
   * @param otherComb - another tile combination.
   * @return true if combinations are duplicates.
   */
  private static boolean haveSameChildren(List<ImageView> comb, List<ImageView> otherComb) {
    if (comb.size() == otherComb.size()) {
      for (ImageView tile : comb) {
        if (!otherComb.contains(tile)) {
          return false;
        }
      }
      return true;
    } else {
      return false;
    }
  }

  /**
   * Returns the index of a tile in an HBox, returns null if the tile is not contained in the Hbox.
   *
   * @param box - Hbox to be searched
   * @param tile - Tile to be found
   * @return index of tile or null
   */
  public static Integer getIndexOf(HBox box, ImageView tile) {
    for (int i = 0; i < box.getChildren().size(); i++) {
      if (box.getChildren().get(i).equals(tile)) {
        return i;
      }
    }
    return null;
  }


  /**
   * Opens alert window if user tries to place invalid combination.
   */
  public static void invalidCombinations() {
    Alert alert = new Alert(AlertType.CONFIRMATION, "Error! Combination(s) not valid,"
        + " please check again!", ButtonType.OK);
    alert.showAndWait();

  }


  /**
   * Opens confirmation alert if user tries to exit application by pressing "x". If user confirms
   * exit, the application is exited.
   *
   * @param event - WindowEvent if user presses "x" icon.
   */
  public static void closeWindowEvent(WindowEvent event) {
    ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
    ButtonType no = new ButtonType("No",
        ButtonBar.ButtonData.CANCEL_CLOSE);
    Alert alert = new Alert(AlertType.CONFIRMATION, "bla", yes, no);
    alert.setContentText("Do you really want to quit?");
    Optional<ButtonType> result = alert.showAndWait();

    if (result.isPresent() && result.get() == no) {
      event.consume();
    } else if (result.isPresent() && result.get() == yes) {
      System.exit(0);
    }
  }

}
