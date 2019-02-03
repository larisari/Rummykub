package gui;

import java.util.List;
import javafx.stage.Stage;

/**
 * Wrapper class for end screens.
 */
abstract class EndScreen {

  /**
   * For receiving the number of players from the controller of the previous stage.
   *
   * @param numberOfPlayers - number of players.
   */
  abstract void setNumberOfPlayers(Integer numberOfPlayers);

  /**
   * For receiving the stage of this controller from the controller of the previous stage.
   *
   * @param stage - to be forwarded.
   */
  abstract void setStage(Stage stage);

  /**
   * For receiving the points of all players from the controller of the previous stage.
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
