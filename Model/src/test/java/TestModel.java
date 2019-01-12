import gameinfo.GIFactory;
import gameinfo.GIGameInfo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import tile.Tile;
import tile.util.TileColor;
import tile.util.TileNumber;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class TestModel {

  private static GIGameInfo gameInfo;

  private static String player_1_id = UUID.randomUUID().toString();
  private static String player_2_id = UUID.randomUUID().toString();
  private static String player_3_id = UUID.randomUUID().toString();
  private static String player_4_id = UUID.randomUUID().toString();

  @BeforeAll
  static void setupGameInfo() {
    gameInfo = GIFactory.make();

    gameInfo.registerBy(player_1_id);
    gameInfo.registerBy(player_2_id);
    gameInfo.registerBy(player_3_id);
    gameInfo.registerBy(player_4_id);
  }

  @Test
  void testRegistration() {
    List<String> ids = gameInfo.getAllPlayerIds();

    for (String id : ids) {
      assert id.equals(player_1_id) || id.equals(player_2_id) || id.equals(player_3_id) || id.equals(player_4_id);
    }
    System.out.println(ids);
  }

  @Test
  void testDeregistration() {
    List<String> ids = gameInfo.getAllPlayerIds();

    for (String id : ids) {
      assert id.equals(player_1_id) || id.equals(player_2_id) || id.equals(player_3_id) || id.equals(player_4_id);
    }
    System.out.println(ids);

    gameInfo.deregisterBy(ids.get(0));

    System.out.println(ids);
  }

  // done done
  @Test
  void playFirstMoveWrongComb() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(TileNumber.TEN, TileColor.BLUE));
    combination.add(new Tile(TileNumber.TEN, TileColor.BLACK));
    combination.add(new Tile(TileNumber.ELEVEN, TileColor.YELLOW));

    assert !gameInfo.play(combination, player_1_id);
  }

  // done done
  @Test
  void playFirstMoveValid() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(TileNumber.TEN, TileColor.BLUE));
    combination.add(new Tile(TileNumber.TEN, TileColor.BLACK));
    combination.add(new Tile(TileNumber.TEN, TileColor.YELLOW));

    assert gameInfo.play(combination, player_2_id);
  }

  // done done
  @Test
  void playFirstMoveWithJoker() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(TileNumber.JOKER, TileColor.JOKER));
    combination.add(new Tile(TileNumber.TEN, TileColor.BLUE));
    combination.add(new Tile(TileNumber.TEN, TileColor.BLACK));
    combination.add(new Tile(TileNumber.TEN, TileColor.YELLOW));

    assert gameInfo.play(combination, player_3_id);
  }

  // done done
  @Test
  void playFirstMoveNotEnoughPoints() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(TileNumber.NINE, TileColor.BLUE));
    combination.add(new Tile(TileNumber.NINE, TileColor.BLACK));
    combination.add(new Tile(TileNumber.NINE, TileColor.YELLOW));

    assert !gameInfo.play(combination, player_4_id);
  }

  // done done
  @Test
  void playStreetOneToFive() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(TileNumber.ONE, TileColor.BLUE));
    combination.add(new Tile(TileNumber.TWO, TileColor.BLUE));
    combination.add(new Tile(TileNumber.THREE, TileColor.BLUE));
    combination.add(new Tile(TileNumber.FOUR, TileColor.BLUE));
    combination.add(new Tile(TileNumber.FIVE, TileColor.BLUE));

    assert gameInfo.play(combination, player_2_id);
  }

  // done done
  @Test
  void playStreetTenToOne() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(TileNumber.TEN, TileColor.BLUE));
    combination.add(new Tile(TileNumber.ELEVEN, TileColor.BLUE));
    combination.add(new Tile(TileNumber.TWELVE, TileColor.BLUE));
    combination.add(new Tile(TileNumber.THIRTEEN, TileColor.BLUE));
    combination.add(new Tile(TileNumber.ONE, TileColor.BLUE));

    assert gameInfo.play(combination, player_2_id);
  }

  // done done
  @Test
  void playGroupTens() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(TileNumber.TEN, TileColor.BLUE));
    combination.add(new Tile(TileNumber.TEN, TileColor.BLACK));
    combination.add(new Tile(TileNumber.TEN, TileColor.YELLOW));
    combination.add(new Tile(TileNumber.TEN, TileColor.RED));

    assert gameInfo.play(combination, player_2_id);
  }

  // done done
  @Test
  void playGroupWithJokerNotFirst() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(TileNumber.TEN, TileColor.BLUE));
    combination.add(new Tile(TileNumber.JOKER, TileColor.JOKER));
    combination.add(new Tile(TileNumber.TEN, TileColor.BLACK));
    combination.add(new Tile(TileNumber.TEN, TileColor.YELLOW));

    assert gameInfo.play(combination, player_2_id);
    assert gameInfo.getPointsForMove(combination) == 40;
  }

  // done done
  @Test
  void playStreetOneToFiveJoker() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(TileNumber.JOKER, TileColor.JOKER));
    combination.add(new Tile(TileNumber.TWO, TileColor.BLUE));
    combination.add(new Tile(TileNumber.THREE, TileColor.BLUE));
    combination.add(new Tile(TileNumber.FOUR, TileColor.BLUE));
    combination.add(new Tile(TileNumber.FIVE, TileColor.BLUE));

    assert gameInfo.play(combination, player_2_id);
    assert gameInfo.getPointsForMove(combination) == 15;
  }

  @Test
  void testStreetOneToFiveJOKERNotAtFirst() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(TileNumber.ONE, TileColor.BLUE));
    combination.add(new Tile(TileNumber.TWO, TileColor.BLUE));
    combination.add(new Tile(TileNumber.JOKER, TileColor.JOKER));
    combination.add(new Tile(TileNumber.FOUR, TileColor.BLUE));
    combination.add(new Tile(TileNumber.FIVE, TileColor.BLUE));

    assert gameInfo.play(combination, player_2_id);
    assert gameInfo.getPointsForMove(combination) == 15;
  }

  @Test
  void testStreetTenToOneJOKER() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(TileNumber.JOKER, TileColor.JOKER));
    combination.add(new Tile(TileNumber.ELEVEN, TileColor.BLUE));
    combination.add(new Tile(TileNumber.TWELVE, TileColor.BLUE));
    combination.add(new Tile(TileNumber.THIRTEEN, TileColor.BLUE));
    combination.add(new Tile(TileNumber.ONE, TileColor.BLUE));

    assert gameInfo.play(combination, player_2_id);
    assert gameInfo.getPointsForMove(combination) == 47;
  }

  @Test
  void testStreetTenToOneJOKERNotAtFirst() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(TileNumber.TEN, TileColor.BLUE));
    combination.add(new Tile(TileNumber.ELEVEN, TileColor.BLUE));
    combination.add(new Tile(TileNumber.TWELVE, TileColor.BLUE));
    combination.add(new Tile(TileNumber.JOKER, TileColor.JOKER));
    combination.add(new Tile(TileNumber.ONE, TileColor.BLUE));

    assert gameInfo.play(combination, player_2_id);
    assert gameInfo.getPointsForMove(combination) == 47;
  }

  @Test
  void getStackInitialtest() {
    System.out.println(gameInfo.getStackFor(player_1_id));
    System.out.println(gameInfo.getStackFor(player_2_id));
    System.out.println(gameInfo.getStackFor(player_3_id));
    System.out.println(gameInfo.getStackFor(player_4_id));
  }

  @Test
  void getTileInitialTest() {
    System.out.println(gameInfo.getTileFor(player_1_id));
    System.out.println(gameInfo.getTileFor(player_2_id));
    System.out.println(gameInfo.getTileFor(player_3_id));
    System.out.println(gameInfo.getTileFor(player_4_id));
  }

}
