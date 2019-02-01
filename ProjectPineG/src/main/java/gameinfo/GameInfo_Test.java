package gameinfo;

import gameinfo.util.GIColor;
import gameinfo.util.GINumber;
import gameinfo.util.GITile;
import org.junit.jupiter.api.BeforeAll;
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

  // TODO create a custom game run that covers as much as possible

  @Test
  void orderedTestRun() {
    setAge();
    start();
    assert gameInfo.getStartingPlayerId().equals(player_2_ID);
    checkNumOfPlayers();
    assert gameInfo.getCurrentBoard().isEmpty();
    draw();
    play_lessThan30Points();
    play_validMove();
    assert gameInfo.getCurrentPlayerId().equals(player_2_ID);
    gameInfo.finishedTurnBy(player_2_ID);
    assert gameInfo.getCurrentPlayerId().equals(player_1_ID);
  }

  /**
   * This method checks whether a combination is valid or not.
   *
   * @param combinations wished by a player.
   * @param id of the player to wish the combinations.
   * @return true, is the combinations are valid and false otherwise.
   */
  private boolean validate(List<List<GITile>> combinations, Integer id) {
    if (gameInfo.play(combinations,id).isPresent()) {
      return gameInfo.play(combinations,id).get().getSecond();
    }
    else {
      return false;
    }
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

  void checkNumOfPlayers() {
    if (gameInfo.getNumberOfPlayers().isPresent()) {
      assert gameInfo.getNumberOfPlayers().get() == 2;
    }
    else {
      assert false;
    }
  }

  void play_lessThan30Points() {
    List<List<GITile>> combinations = new ArrayList<>();
    List<GITile> lessThanMinimumPoints = new ArrayList<>();
    lessThanMinimumPoints.add(hand_player_2.get(11));
    lessThanMinimumPoints.add(hand_player_2.get(12));
    lessThanMinimumPoints.add(hand_player_2.get(13));
    combinations.add(lessThanMinimumPoints);
    assert ! validate(combinations,player_2_ID);
  }

  void play_validMove() {
    List<List<GITile>> combinations = new ArrayList<>();
    List<GITile> elevens = new ArrayList<>();
    List<GITile> twelves= new ArrayList<>();
    List<GITile> thirteens = new ArrayList<>();
    elevens.add(hand_player_2.get(0));
    elevens.add(hand_player_2.get(1));
    elevens.add(hand_player_2.get(2));
    elevens.add(hand_player_2.get(3));
    twelves.add(hand_player_2.get(4));
    twelves.add(hand_player_2.get(5));
    twelves.add(hand_player_2.get(6));
    twelves.add(hand_player_2.get(7));
    thirteens.add(hand_player_2.get(8));
    thirteens.add(hand_player_2.get(9));
    thirteens.add(hand_player_2.get(10));
    combinations.add(elevens);
    combinations.add(twelves);
    combinations.add(thirteens);
    assert validate(combinations,player_2_ID);
  }



  void play_streetOfFourteen() {
    List<List<GITile>> combinations = new ArrayList<>();
    List<GITile> streetOfFourteen = hand_player_2;
    combinations.add(streetOfFourteen);
    if (gameInfo.play(combinations, player_2_ID).isPresent()) {
    assert !gameInfo.play(combinations, player_2_ID).get().getSecond();
    }
    else {
      assert false;
    }
  }

}
