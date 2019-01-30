package gameinfo;

import gameinfo.util.GIColor;
import gameinfo.util.GINumber;
import gameinfo.util.GITile;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.awt.image.TileObserver;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class GameInfo_Test {

  private static GIGameInfo gameInfo;

  private static Player p1;
  private static Player p2;
  private static Player p3;
  private static Player p4;

  private static final Integer player_1_ID = 1;
  private static final Integer player_2_ID = 2;
  private static final Integer player_3_ID = 3;
  private static final Integer player_4_ID = 4;

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
    gameInfo = GIFactory.make();
    p1 = new Player(player_1_ID, new PointsCalculator());
    p2 = new Player(player_2_ID, new PointsCalculator());
    p3 = new Player(player_3_ID, new PointsCalculator());
    p4 = new Player(player_4_ID, new PointsCalculator());
  }

  @Test
  void fromZeroToDrawing() {
    signInPlayers();
    start();
    draw();
  }

  @Test
  void ageInfluence() {
    signInPlayers();
    setAge();
    start();

    assert gameInfo.getStartingPlayerId() == 4;
    assert gameInfo.getCurrentPlayerId() == 4;
    assert gameInfo.getNextPlayerId().get() == 3;
  }

  @Test
  void invalidFirstMove() {
    signInPlayers();
    start();
    draw();

    List<GITile> playersGroup =
        new ArrayList<>(
            Arrays.asList(
                gameInfo.getAllTilesBy(player_1_ID).get().getSecond().get(0),
                gameInfo.getAllTilesBy(player_1_ID).get().getSecond().get(1),
                gameInfo.getAllTilesBy(player_1_ID).get().getSecond().get(2)));

    List<List<GITile>> combinations = new ArrayList<>();

    combinations.add(playersGroup);

    //Since the wished combination does not exceed the minimum of 30 points, the move is invalid.
    assert !gameInfo.play(combinations, player_1_ID).get().getSecond();
  }

  void start() {
    gameInfo.startGame();
  }

  void signInPlayers() {
    gameInfo.registerBy(player_1_ID);
    gameInfo.registerBy(player_2_ID);
    gameInfo.registerBy(player_3_ID);
    gameInfo.registerBy(player_4_ID);
  }

  void setAge() {

    gameInfo.setAgeFor(player_1_ID, 4);
    gameInfo.setAgeFor(player_2_ID, 3);
    gameInfo.setAgeFor(player_3_ID, 2);
    gameInfo.setAgeFor(player_4_ID, 1);
  }

  void draw() {
    assert gameInfo.getAllTilesBy(player_1_ID).get().getSecond().isEmpty();
    assert gameInfo.getAllTilesBy(player_2_ID).get().getSecond().isEmpty();
    assert gameInfo.getAllTilesBy(player_3_ID).get().getSecond().isEmpty();
    assert gameInfo.getAllTilesBy(player_4_ID).get().getSecond().isEmpty();

    gameInfo.drawBy(player_1_ID, hand_player_1);
    gameInfo.drawBy(player_2_ID);
    gameInfo.drawBy(player_3_ID);
    gameInfo.drawBy(player_4_ID);

    assert gameInfo.getAllTilesBy(player_1_ID).get().getSecond().size() == 14;
    assert gameInfo.getAllTilesBy(player_2_ID).get().getSecond().size() == 14;
    assert gameInfo.getAllTilesBy(player_3_ID).get().getSecond().size() == 14;
    assert gameInfo.getAllTilesBy(player_4_ID).get().getSecond().size() == 14;
  }

  void checkGroup() {
    List<GITile> street = new ArrayList<>();
    street.add(hand_player_1.get(5));
    street.add(hand_player_1.get(8));
    street.add(hand_player_1.get(12));

    List<GITile> elevens = new ArrayList<>();
    elevens.add(hand_player_1.get(9));
    elevens.add(hand_player_1.get(10));
    elevens.add(hand_player_1.get(11));

    List<List<GITile>> combos = new ArrayList<>();
    combos.add(street);
    combos.add(elevens);

    System.out.println(gameInfo.play(combos, player_1_ID));
  }

  void checkOrder() {
    assert gameInfo.getStartingPlayerId().equals(player_4_ID);
    assert gameInfo.getCurrentPlayerId().equals(player_4_ID);
    assert gameInfo.getNextPlayerId().get().equals(player_3_ID);
  }

  void validCombo() {
    List<GITile> playersGroup =
        new ArrayList<>(
            Arrays.asList(
                gameInfo.getAllTilesBy(player_1_ID).get().getSecond().get(9),
                gameInfo.getAllTilesBy(player_1_ID).get().getSecond().get(10),
                gameInfo.getAllTilesBy(player_1_ID).get().getSecond().get(11)));

    List<List<GITile>> combinations = new ArrayList<>();
    combinations.add(playersGroup);

    assert gameInfo.play(combinations, player_1_ID).get().getSecond();
  }

  void getAllPlayersHands() {
    System.out.println(gameInfo.calculatePointsForRegisteredPlayers());
  }

  void showhands() {
    System.out.println(gameInfo.getAllTilesBy(player_1_ID).get());
    System.out.println(gameInfo.getAllTilesBy(player_2_ID).get());
    System.out.println(gameInfo.getAllTilesBy(player_3_ID).get());
    System.out.println(gameInfo.getAllTilesBy(player_4_ID).get());

    // player playes 3 tiles, so he should now have 11 left on his hand
    assert gameInfo.getAllTilesBy(player_1_ID).get().getSecond().size() == 11;
  }

  void makeFirstMove() {
    assert gameInfo.getNextPlayerId().get().equals(player_2_ID);
    gameInfo.drawBy(player_1_ID);
    assert gameInfo.getNextPlayerId().get().equals(player_3_ID);
    assert gameInfo.getAllTilesBy(player_1_ID).get().getSecond().size() == 15;
  }
}
