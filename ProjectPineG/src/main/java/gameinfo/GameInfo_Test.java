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
  private static final Integer player_3_ID = 3;
  private static final Integer player_4_ID = 4;

  private static Player p1 = new Player(player_1_ID, new PointsCalculator());
  private static Player p2 = new Player(player_2_ID, new PointsCalculator());
  private static Player p3 = new Player(player_3_ID, new PointsCalculator());
  private static Player p4 = new Player(player_4_ID, new PointsCalculator());

  private static List<GITile> hand_player_1 =
      new ArrayList<>(
          Arrays.asList(
              new GITile(GINumber.THREE, GIColor.BLACK),
              new GITile(GINumber.THREE, GIColor.BLUE),
              new GITile(GINumber.THREE, GIColor.YELLOW),
              new GITile(GINumber.FOUR, GIColor.BLACK),
              new GITile(GINumber.FOUR, GIColor.BLUE),
              new GITile(GINumber.FOUR, GIColor.YELLOW),
              new GITile(GINumber.FIVE, GIColor.BLACK),
              new GITile(GINumber.FIVE, GIColor.BLUE),
              new GITile(GINumber.FIVE, GIColor.YELLOW),
              new GITile(GINumber.ELEVEN, GIColor.BLACK),
              new GITile(GINumber.ELEVEN, GIColor.BLUE),
              new GITile(GINumber.ELEVEN, GIColor.YELLOW),
              new GITile(GINumber.JOKER, GIColor.JOKER),
              new GITile(GINumber.JOKER, GIColor.JOKER)));

  @BeforeAll
  static void setup() {
    gameInfo.registerBy(player_1_ID);
    gameInfo.registerBy(player_2_ID);
    gameInfo.registerBy(player_3_ID);
    gameInfo.registerBy(player_4_ID);
  }

  //start each test with a new factory? -> one big test or several small? both?
  //rather cover all methods or test game functionality?

  @Test
  void orderedTestRun() {
    setAge();
    start();
    assert gameInfo.getStartingPlayerId().equals(player_4_ID);
    assert gameInfo.getNextPlayerId().get().equals(player_3_ID);
    draw();
    //make moves and test game?
  }

  //-----------------------------------------------------------------------------
  //several tests for small api-methods

//  @Test
//  void fromZeroToDrawing() {
//    start();
//
//    assert gameInfo.getAllTilesBy(player_1_ID).get().getSecond().isEmpty();
//    assert gameInfo.getAllTilesBy(player_2_ID).get().getSecond().isEmpty();
//    assert gameInfo.getAllTilesBy(player_3_ID).get().getSecond().isEmpty();
//    assert gameInfo.getAllTilesBy(player_4_ID).get().getSecond().isEmpty();
//
//    draw();
//
//    assert gameInfo.getAllTilesBy(player_1_ID).get().getSecond().size() == 14;
//    assert gameInfo.getAllTilesBy(player_2_ID).get().getSecond().size() == 14;
//    assert gameInfo.getAllTilesBy(player_3_ID).get().getSecond().size() == 14;
//    assert gameInfo.getAllTilesBy(player_4_ID).get().getSecond().size() == 14;
//  }
//
//  @Test
//  void ageInfluence() {
//    setAge();
//    start();
//
//    assert gameInfo.getStartingPlayerId() == 4;
//    assert gameInfo.getCurrentPlayerId() == 4;
//    assert gameInfo.getNextPlayerId().get() == 3;
//  }
//
//  @Test
//  void invalidFirstCombination() {
//
//    start();
//    draw();
//
//    List<GITile> playersGroup =
//        new ArrayList<>(
//            Arrays.asList(
//                gameInfo.getAllTilesBy(player_1_ID).get().getSecond().get(0),
//                gameInfo.getAllTilesBy(player_1_ID).get().getSecond().get(1),
//                gameInfo.getAllTilesBy(player_1_ID).get().getSecond().get(2)));
//
//    List<List<GITile>> combinations = new ArrayList<>();
//
//    combinations.add(playersGroup);
//
//    //Since the wished combination does not exceed the minimum of 30 points, the move is invalid.
//    assert !gameInfo.play(combinations, player_1_ID).get().getSecond();
//  }
//
//  @Test
//  void validFirstCombination() {
//
//    start();
//    draw();
//
//    //no age is set, therefore the order goes according to the players' ids.
//    assert gameInfo.getStartingPlayerId() == 1;
//
//    List<GITile> playersGroup =
//        new ArrayList<>(
//            Arrays.asList(
//                gameInfo.getAllTilesBy(player_1_ID).get().getSecond().get(9),
//                gameInfo.getAllTilesBy(player_1_ID).get().getSecond().get(10),
//                gameInfo.getAllTilesBy(player_1_ID).get().getSecond().get(11)));
//
//    List<List<GITile>> combinations = new ArrayList<>();
//    combinations.add(playersGroup);
//
//    //The combination exceeds the minimum of 30 points for the first move.
//    assert gameInfo.play(combinations, player_1_ID).get().getSecond();
//  }

  void start() {
    gameInfo.startGame();
  }

  void setAge() {
    gameInfo.setAgeFor(player_1_ID, 4);
    gameInfo.setAgeFor(player_2_ID, 3);
    gameInfo.setAgeFor(player_3_ID, 2);
    gameInfo.setAgeFor(player_4_ID, 1);
  }

  void draw() {
    gameInfo.drawBy(player_1_ID, hand_player_1);
    gameInfo.drawBy(player_2_ID);
    gameInfo.drawBy(player_3_ID);
    gameInfo.drawBy(player_4_ID);
  }

}
