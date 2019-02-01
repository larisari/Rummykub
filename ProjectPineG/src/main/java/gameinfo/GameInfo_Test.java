package gameinfo;

import gameinfo.util.GIColor;
import gameinfo.util.GINumber;
import gameinfo.util.GITile;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class GameInfo_Test {

  private static GIGameInfo gameInfo = GIFactory.make();

  private static final Integer player_1_ID = 1;
  private static final Integer player_2_ID = 2;

  private static Player p1 = new Player(player_1_ID, new PointsCalculator());
  private static Player p2 = new Player(player_2_ID, new PointsCalculator());

  private static List<GITile> hand_player_1 =
      new ArrayList<>(
          Arrays.asList(
              new GITile(GINumber.ONE, GIColor.BLUE),
              new GITile(GINumber.TWO, GIColor.BLUE),
              new GITile(GINumber.THREE, GIColor.BLUE),
              new GITile(GINumber.FOUR, GIColor.BLUE),
              new GITile(GINumber.FIVE, GIColor.BLUE),
              new GITile(GINumber.SIX, GIColor.BLUE),
              new GITile(GINumber.SEVEN, GIColor.BLUE),
              new GITile(GINumber.EIGHT, GIColor.BLUE),
              new GITile(GINumber.NINE, GIColor.BLUE),
              new GITile(GINumber.TEN, GIColor.BLUE),
              new GITile(GINumber.ELEVEN, GIColor.BLUE),
              new GITile(GINumber.TWELVE, GIColor.BLUE),
              new GITile(GINumber.THIRTEEN, GIColor.BLUE),
              new GITile(GINumber.JOKER, GIColor.JOKER)));

  private static List<GITile> hand_player_2 =
      new ArrayList<>(
          Arrays.asList(
              new GITile(GINumber.ELEVEN, GIColor.BLACK),
              new GITile(GINumber.ELEVEN, GIColor.RED),
              new GITile(GINumber.ELEVEN, GIColor.BLUE),
              new GITile(GINumber.ELEVEN, GIColor.YELLOW),
              new GITile(GINumber.TWELVE, GIColor.BLACK),
              new GITile(GINumber.TWELVE, GIColor.RED),
              new GITile(GINumber.TWELVE, GIColor.BLUE),
              new GITile(GINumber.TWELVE, GIColor.YELLOW),
              new GITile(GINumber.THIRTEEN, GIColor.BLUE),
              new GITile(GINumber.THIRTEEN, GIColor.RED),
              new GITile(GINumber.THIRTEEN, GIColor.YELLOW),
              new GITile(GINumber.TWO, GIColor.BLUE),
              new GITile(GINumber.THREE, GIColor.BLUE),
              new GITile(GINumber.JOKER, GIColor.JOKER)));

  @BeforeAll
  static void setup() {
    gameInfo.registerBy(player_1_ID);
    gameInfo.registerBy(player_2_ID);
  }

  //TODO create a custom game run that covers as much as possible

  @Test
  void orderedTestRun() {
    setAge();
    start();
    assert gameInfo.getStartingPlayerId().equals(player_2_ID);
    draw();
  }

  void start() {
    gameInfo.startGame();
  }

  void setAge() {
    gameInfo.setAgeFor(player_1_ID, 22);
    gameInfo.setAgeFor(player_2_ID, 19);
  }

  void draw() {
    gameInfo.drawBy(player_1_ID, hand_player_1);
    gameInfo.drawBy(player_2_ID, hand_player_2);
  }

}
