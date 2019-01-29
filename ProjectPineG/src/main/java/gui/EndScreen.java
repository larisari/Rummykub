package gui;

import java.util.List;
import javafx.stage.Stage;

/**
 * Wrapper class for end screens.
 */
abstract class EndScreen {

  /**
   * Gets called from GuiController to forward numberOfPlayers.
   *
   * @param numberOfPlayers - number of players.
   */
  abstract void setNumberOfPlayers(Integer numberOfPlayers);

  /**
   * For forwarding stage to its controller.
   * @param stage - to be forwarded.
   */
  abstract void setStage(Stage stage);

  /**
   * Sets points for each player.
   *
   * @param points - list of points of all players.
   */
  abstract void setPlayerPoints(List<Integer> points);

  /**
   * Sets points and names visible according to the number of players.
   */
  abstract void setPointsNamesVisible();

  /**
   * Sets the points for two players and sets the name of this gui's client to "You".
   */
  abstract void setPoints2Players();

  /**
   * Sets the points for three players and sets the name of this gui's client to "You".
   */
  abstract void setPoints3Players();

  /**
   * Sets the points for four players and sets the name of this gui's client to "You".
   */
  abstract void setPoints4Players();

  /**
   * Loads music and plays it once.
   */
  abstract void playMusic();
}
