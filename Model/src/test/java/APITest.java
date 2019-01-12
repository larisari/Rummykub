import gameinfo.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import tile.Tile;
import tile.util.TileColor;
import tile.util.TileNumber;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class APITest {

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

  @Test
  void playFirstMoveWrongComb() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(TileNumber.TEN, TileColor.BLUE));
    combination.add(new Tile(TileNumber.TEN, TileColor.BLACK));
    combination.add(new Tile(TileNumber.ELEVEN, TileColor.YELLOW));

    assert !gameInfo.play(combination, player_1_id);
  }

  @Test
  void playFirstMoveValid() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(TileNumber.TEN, TileColor.BLUE));
    combination.add(new Tile(TileNumber.TEN, TileColor.BLACK));
    combination.add(new Tile(TileNumber.TEN, TileColor.YELLOW));

    assert gameInfo.play(combination, player_2_id);
  }

  @Test
  void playFirstMoveWithJoker() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(TileNumber.JOKER, TileColor.JOKER));
    combination.add(new Tile(TileNumber.TEN, TileColor.BLUE));
    combination.add(new Tile(TileNumber.TEN, TileColor.BLACK));
    combination.add(new Tile(TileNumber.TEN, TileColor.YELLOW));

    assert gameInfo.play(combination, player_3_id);
  }

  @Test
  void playFirstMoveNotEnoughPoints() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(TileNumber.NINE, TileColor.BLUE));
    combination.add(new Tile(TileNumber.NINE, TileColor.BLACK));
    combination.add(new Tile(TileNumber.NINE, TileColor.YELLOW));

    assert !gameInfo.play(combination, player_4_id);
  }

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

  @Test
  void playGroupTens() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(TileNumber.TEN, TileColor.BLUE));
    combination.add(new Tile(TileNumber.TEN, TileColor.BLACK));
    combination.add(new Tile(TileNumber.TEN, TileColor.YELLOW));
    combination.add(new Tile(TileNumber.TEN, TileColor.RED));

    assert gameInfo.play(combination, player_2_id);
  }

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
}
