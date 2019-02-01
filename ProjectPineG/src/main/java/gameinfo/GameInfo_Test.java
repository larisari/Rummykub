package gameinfo;

import gameinfo.util.GIColor;
import gameinfo.util.GINumber;
import gameinfo.util.GITile;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
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
    assert ! gameInfo.getCurrentBoard().isEmpty();
    play_streetOfFourteen();
    play_streetOfThirteen();
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

  private List<List<GITile>> makeCombinationList(int fromHandPosition, int toHandPosition,
                                                 List<GITile> hand) {

    List<List<GITile>> combinationList = new ArrayList<>();
    List<GITile> combination = new ArrayList<>();
    for (int i = fromHandPosition; i < toHandPosition + 1; i++) {
      combination.add(hand.get(i));
    }
    combinationList.add(combination);
    return combinationList;
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
      assert gameInfo.getNumberOfPlayers().get() == gameInfo.getAllPlayerIds().size();
    }
    else {
      assert false;
    }
  }

  void play_lessThan30Points() {
    List<List<GITile>> combinations = makeCombinationList(11,13,hand_player_2);
    assert ! validate(combinations,player_2_ID);
  }

  void play_validMove() {
    //elevens
    List<List<GITile>> combinations = makeCombinationList(0,3,hand_player_2);
    //twelves
    combinations.add(makeCombinationList(4,7,hand_player_2).get(0));
    //thirteens
    combinations.add(makeCombinationList(8,10,hand_player_2).get(0));
    assert validate(combinations,player_2_ID);
  }

  void play_streetOfFourteen() {
    List<List<GITile>> combinations = new ArrayList<>();
    List<GITile> streetOfFourteen = hand_player_1;
    combinations.add(streetOfFourteen);
    assert ! validate(combinations,player_1_ID);
  }

  void play_streetOfThirteen() {
    List<List<GITile>> combinations =  makeCombinationList(0,12,hand_player_1);
    assert validate(combinations,player_1_ID);
  }

}
