package gameinfo;

import gameinfo.util.GIColor;
import gameinfo.util.GINumber;
import gameinfo.util.GITile;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

class GameInfo_Test {

  private static GIGameInfo gameInfo = GIFactory.make(new HashMap<>());

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
              new GITile(GINumber.ONE, GIColor.BLACK),
              new GITile(GINumber.ONE, GIColor.RED),
              new GITile(GINumber.ONE, GIColor.YELLOW),
              new GITile(GINumber.TWELVE, GIColor.BLACK),
              new GITile(GINumber.TWELVE, GIColor.RED),
              new GITile(GINumber.TWELVE, GIColor.YELLOW),
              new GITile(GINumber.TWELVE, GIColor.BLUE),
              new GITile(GINumber.THIRTEEN, GIColor.BLACK),
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
    assert gameInfo.getCurrentPlayerId().equals(player_2_ID);
    // p2
    play_groupOfThirteens();
    // p1
    assert gameInfo.getCurrentPlayerId().equals(player_1_ID);
    assert !gameInfo.getCurrentBoard().isEmpty();
    play_streetOfFourteen();
    play_wrongTurn();
    play_streetOfThirteen();
    // p2
    play_wrongJokerPosition();
    play_groupOfOnes();
    // p1
    play_draw();
    // p2
    play_groupOfTwelves();
    // p1
    play_addJokerToExistingCombination();
    // p2
    play_swapJoker();
  }

  /**
   * This method checks whether a combination is valid or not.
   *
   * @param combinations wished by a player.
   * @param id of the player to wish the combinations.
   * @return true, is the combinations are valid and false otherwise.
   */
  private boolean validate(List<List<GITile>> combinations, Integer id) {
    return gameInfo.play(combinations, id).get().getSecond();
  }

  /**
   * This method creates wished combinations from a given hand.
   *
   * @param fromHandPosition is the starting index of the given player's hand.
   * @param toHandPosition is the ending index of the given player's hand.
   * @param hand the List out of which the final List of Lists is created.
   * @return List of Lists of one List to be passed as parameter for a play() method.
   */
  private List<List<GITile>> makeCombinationList(
      int fromHandPosition, int toHandPosition, List<GITile> hand) {

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
    gameInfo.drawBy(player_2_ID, hand_player_2);
    gameInfo.finishedTurnBy(player_2_ID);
    gameInfo.drawBy(player_1_ID, hand_player_1);
    gameInfo.finishedTurnBy(player_1_ID);
  }

  void checkNumOfPlayers() {
    if (gameInfo.getNumberOfPlayers().isPresent()) {
      assert gameInfo.getNumberOfPlayers().get() == 2;
      assert gameInfo.getNumberOfPlayers().get() == gameInfo.getAllPlayerIds().size();
    } else {
      assert false;
    }
  }

  void play_lessThan30Points() {
    List<List<GITile>> combinations = makeCombinationList(11, 13, hand_player_2);
    assert !validate(combinations, player_2_ID);
  }

  void play_groupOfThirteens() {
    // thirteens
    List<List<GITile>> combinations = makeCombinationList(7, 10, hand_player_2);
    assert validate(combinations, player_2_ID);
    gameInfo.finishedTurnBy(player_2_ID);
  }

  void play_streetOfFourteen() {
    List<List<GITile>> combinations = new ArrayList<>();
    List<GITile> streetOfFourteen = hand_player_1;
    combinations.add(streetOfFourteen);
    assert !validate(combinations, player_1_ID);
  }

  void play_wrongTurn() {
    List<List<GITile>> combinations = makeCombinationList(11, 13, hand_player_2);
    assert !validate(combinations, player_2_ID);
  }

  void play_streetOfThirteen() {
    List<List<GITile>> combinations = makeCombinationList(0, 12, hand_player_1);
    assert validate(combinations, player_1_ID);
    gameInfo.finishedTurnBy(player_1_ID);
  }

  void play_wrongJokerPosition() {
    List<List<GITile>> combinations = new ArrayList<>();
    List<GITile> invalidCombination = new ArrayList<>();
    invalidCombination.add(hand_player_2.get(12));
    invalidCombination.add(hand_player_2.get(11));
    invalidCombination.add(hand_player_2.get(13));
    combinations.add(invalidCombination);
    assert !validate(combinations, player_2_ID);
  }

  void play_groupOfOnes() {
    List<List<GITile>> combinations = makeCombinationList(0, 2, hand_player_2);
    assert validate(combinations, player_2_ID);
    gameInfo.finishedTurnBy(player_2_ID);
  }

  void play_draw() {
    if (gameInfo.getAllTilesBy(player_1_ID).isPresent()) {
      int tilesOnHand = gameInfo.getAllTilesBy(player_1_ID).get().getSecond().size();
      gameInfo.drawBy(player_1_ID);
      assert gameInfo.getAllTilesBy(player_1_ID).get().getSecond().size() == tilesOnHand + 1;
    } else {
      assert false;
    }
    gameInfo.finishedTurnBy(player_1_ID);
  }

  void play_groupOfTwelves() {
    List<List<GITile>> combinations = makeCombinationList(3, 5, hand_player_2);
    assert validate(combinations, player_2_ID);
    gameInfo.finishedTurnBy(player_2_ID);
  }

  void play_addJokerToExistingCombination() {
    List<GITile> tilesFromHand = new ArrayList<>();
    tilesFromHand.add(hand_player_1.get(13));

    List<List<GITile>> currentBoard = new ArrayList<>();
    currentBoard.add(gameInfo.getCurrentBoard().get(3));

    List<GITile> combinationWithJoker = new ArrayList<>(currentBoard.get(0));
    combinationWithJoker.add(tilesFromHand.get(0));

    System.out.println(combinationWithJoker);

    List<List<GITile>> newBoard = new ArrayList<>();
    newBoard.add(combinationWithJoker);

    assert gameInfo
        .manipulateBoardWith(tilesFromHand, currentBoard, newBoard, player_1_ID)
        .get()
        .getSecond();
    gameInfo.finishedTurnBy(player_1_ID);
  }

  void play_swapJoker() {
    List<GITile> blueTwelveFromHand = new ArrayList<>();
    blueTwelveFromHand.add(gameInfo.getAllTilesBy(player_2_ID).get().getSecond().get(0));

    System.out.println("12 blue: " + blueTwelveFromHand);

    List<List<GITile>> twelvesWithJoker = new ArrayList<>();
    twelvesWithJoker.add(gameInfo.getCurrentBoard().get(gameInfo.getCurrentBoard().size() - 1));

    System.out.println("12 with joker: " + twelvesWithJoker);

    List<GITile> fourTwelves = new ArrayList<>(twelvesWithJoker.get(0));
    fourTwelves.removeIf(tile -> tile.isEquals(new GITile(GINumber.JOKER, GIColor.JOKER)));
    fourTwelves.add(blueTwelveFromHand.get(0));

    System.out.println("4x12: " + fourTwelves);

    List<List<GITile>> twelvesWithoutJoker = new ArrayList<>();
    twelvesWithoutJoker.add(fourTwelves);

    System.out.println("12 without joker: " + twelvesWithoutJoker);

    assert gameInfo
        .manipulateBoardWith(blueTwelveFromHand, twelvesWithJoker, twelvesWithoutJoker, player_2_ID)
        .get()
        .getSecond();
    gameInfo.finishedTurnBy(player_2_ID);
    System.out.println(gameInfo.getAllTilesBy(player_2_ID).get().getSecond());
  }



}
