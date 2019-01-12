import gameinfo.GIFactory;
import gameinfo.GIGameInfo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import gameinfo.tile.Tile;
import gameinfo.tile.util.Color;
import gameinfo.tile.util.Number;

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
  }

  // done done
  @Test
  void playFirstMoveWrongComb() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(Number.TEN, Color.BLUE));
    combination.add(new Tile(Number.TEN, Color.BLACK));
    combination.add(new Tile(Number.ELEVEN, Color.YELLOW));

    assert !gameInfo.play(combination, player_1_id);
  }

  // done done
  @Test
  void playFirstMoveValid() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(Number.TEN, Color.BLUE));
    combination.add(new Tile(Number.TEN, Color.BLACK));
    combination.add(new Tile(Number.TEN, Color.YELLOW));

    assert gameInfo.play(combination, player_2_id);
  }

  // done done
  @Test
  void playFirstMoveWithJoker() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(Number.JOKER, Color.JOKER));
    combination.add(new Tile(Number.TEN, Color.BLUE));
    combination.add(new Tile(Number.TEN, Color.BLACK));
    combination.add(new Tile(Number.TEN, Color.YELLOW));

    assert gameInfo.play(combination, player_3_id);
  }

  // done done
  @Test
  void playFirstMoveNotEnoughPoints() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(Number.NINE, Color.BLUE));
    combination.add(new Tile(Number.NINE, Color.BLACK));
    combination.add(new Tile(Number.NINE, Color.YELLOW));

    assert !gameInfo.play(combination, player_4_id);
  }

  // done done
  @Test
  void playStreetOneToFive() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(Number.ONE, Color.BLUE));
    combination.add(new Tile(Number.TWO, Color.BLUE));
    combination.add(new Tile(Number.THREE, Color.BLUE));
    combination.add(new Tile(Number.FOUR, Color.BLUE));
    combination.add(new Tile(Number.FIVE, Color.BLUE));

    assert gameInfo.play(combination, player_2_id);
  }

  // done done
  @Test
  void playStreetTenToOne() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(Number.TEN, Color.BLUE));
    combination.add(new Tile(Number.ELEVEN, Color.BLUE));
    combination.add(new Tile(Number.TWELVE, Color.BLUE));
    combination.add(new Tile(Number.THIRTEEN, Color.BLUE));
    combination.add(new Tile(Number.ONE, Color.BLUE));

    assert gameInfo.play(combination, player_2_id);
  }

  // done done
  @Test
  void playGroupTens() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(Number.TEN, Color.BLUE));
    combination.add(new Tile(Number.TEN, Color.BLACK));
    combination.add(new Tile(Number.TEN, Color.YELLOW));
    combination.add(new Tile(Number.TEN, Color.RED));

    assert gameInfo.play(combination, player_2_id);
  }

  // done done
  @Test
  void playGroupWithJokerNotFirst() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(Number.TEN, Color.BLUE));
    combination.add(new Tile(Number.JOKER, Color.JOKER));
    combination.add(new Tile(Number.TEN, Color.BLACK));
    combination.add(new Tile(Number.TEN, Color.YELLOW));

    assert gameInfo.play(combination, player_2_id);
    assert gameInfo.getPointsForMove(combination) == 40;
  }

  // done done
  @Test
  void playStreetOneToFiveJoker() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(Number.JOKER, Color.JOKER));
    combination.add(new Tile(Number.TWO, Color.BLUE));
    combination.add(new Tile(Number.THREE, Color.BLUE));
    combination.add(new Tile(Number.FOUR, Color.BLUE));
    combination.add(new Tile(Number.FIVE, Color.BLUE));

    assert gameInfo.play(combination, player_2_id);
    assert gameInfo.getPointsForMove(combination) == 15;
  }

  @Test
  void testStreetOneToFiveJOKERNotAtFirst() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(Number.ONE, Color.BLUE));
    combination.add(new Tile(Number.TWO, Color.BLUE));
    combination.add(new Tile(Number.JOKER, Color.JOKER));
    combination.add(new Tile(Number.FOUR, Color.BLUE));
    combination.add(new Tile(Number.FIVE, Color.BLUE));

    assert gameInfo.play(combination, player_2_id);
    assert gameInfo.getPointsForMove(combination) == 15;
  }

  @Test
  void testStreetTenToOneJOKER() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(Number.JOKER, Color.JOKER));
    combination.add(new Tile(Number.ELEVEN, Color.BLUE));
    combination.add(new Tile(Number.TWELVE, Color.BLUE));
    combination.add(new Tile(Number.THIRTEEN, Color.BLUE));
    combination.add(new Tile(Number.ONE, Color.BLUE));

    assert gameInfo.play(combination, player_2_id);
    assert gameInfo.getPointsForMove(combination) == 47;
  }

  @Test
  void testStreetTenToOneJOKERNotAtFirst() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(Number.TEN, Color.BLUE));
    combination.add(new Tile(Number.ELEVEN, Color.BLUE));
    combination.add(new Tile(Number.TWELVE, Color.BLUE));
    combination.add(new Tile(Number.JOKER, Color.JOKER));
    combination.add(new Tile(Number.ONE, Color.BLUE));

    assert gameInfo.play(combination, player_2_id);
    assert gameInfo.getPointsForMove(combination) == 47;
  }
}
