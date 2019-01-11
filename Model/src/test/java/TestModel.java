import gameinfo.GIFactory;
import gameinfo.GIGameInfo;
import gameinfo.player.PFactory;
import gameinfo.player.Player;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import tile.Tile;
import tile.util.TileColor;
import tile.util.TileNumber;

import java.util.ArrayList;
import java.util.List;

class TestModel {

  private static GIGameInfo gameInfo;

  @BeforeAll
  static void createCombRules() {
    gameInfo = GIFactory.makeFor();
  }

  @Test
  void testStreetOneToFive() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(TileNumber.ONE, TileColor.BLUE));
    combination.add(new Tile(TileNumber.TWO, TileColor.BLUE));
    combination.add(new Tile(TileNumber.THREE, TileColor.BLUE));
    combination.add(new Tile(TileNumber.FOUR, TileColor.BLUE));
    combination.add(new Tile(TileNumber.FIVE, TileColor.BLUE));

    assert gameInfo.isValidMove(combination);
  }

  @Test
  void testStreetTenToOne() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(TileNumber.TEN, TileColor.BLUE));
    combination.add(new Tile(TileNumber.ELEVEN, TileColor.BLUE));
    combination.add(new Tile(TileNumber.TWELVE, TileColor.BLUE));
    combination.add(new Tile(TileNumber.THIRTEEN, TileColor.BLUE));
    combination.add(new Tile(TileNumber.ONE, TileColor.BLUE));

    assert gameInfo.isValidMove(combination);
  }

  @Test
  void testGroup() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(TileNumber.TEN, TileColor.BLUE));
    combination.add(new Tile(TileNumber.TEN, TileColor.BLACK));
    combination.add(new Tile(TileNumber.TEN, TileColor.YELLOW));
    combination.add(new Tile(TileNumber.TEN, TileColor.RED));

    assert gameInfo.isValidMove(combination);
  }

  @Test
  void testValidFirstMoveWrongComb() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(TileNumber.TEN, TileColor.BLUE));
    combination.add(new Tile(TileNumber.TEN, TileColor.BLACK));
    combination.add(new Tile(TileNumber.ELEVEN, TileColor.YELLOW));

    assert !(gameInfo.isValidMove(combination) && gameInfo.getPointsForMove(combination) >= 30);
  }

  @Test
  void testValidFirstMove() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(TileNumber.TEN, TileColor.BLUE));
    combination.add(new Tile(TileNumber.TEN, TileColor.BLACK));
    combination.add(new Tile(TileNumber.TEN, TileColor.YELLOW));

    assert (gameInfo.isValidMove(combination) && gameInfo.getPointsForMove(combination) >= 30);
  }

  @Test
  void testValidFirstMoveNotEnoughPoints() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(TileNumber.NINE, TileColor.BLUE));
    combination.add(new Tile(TileNumber.NINE, TileColor.BLACK));
    combination.add(new Tile(TileNumber.NINE, TileColor.YELLOW));

    assert !(gameInfo.isValidMove(combination) && gameInfo.getPointsForMove(combination) >= 30);
  }

  @Test
  void testGroupWithJoker() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(TileNumber.JOKER, TileColor.JOKER));
    combination.add(new Tile(TileNumber.TEN, TileColor.BLUE));
    combination.add(new Tile(TileNumber.TEN, TileColor.BLACK));
    combination.add(new Tile(TileNumber.TEN, TileColor.YELLOW));

    assert (gameInfo.isValidMove(combination) && gameInfo.getPointsForMove(combination) >= 30);
  }

  @Test
  void testGroupWithJokerNotFirst() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(TileNumber.TEN, TileColor.BLUE));
    combination.add(new Tile(TileNumber.JOKER, TileColor.JOKER));
    combination.add(new Tile(TileNumber.TEN, TileColor.BLACK));
    combination.add(new Tile(TileNumber.TEN, TileColor.YELLOW));

    assert gameInfo.isValidMove(combination);
    assert gameInfo.getPointsForMove(combination) == 40;
  }

  @Test
  void testStreetOneToFiveJOKER() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(TileNumber.JOKER, TileColor.JOKER));
    combination.add(new Tile(TileNumber.TWO, TileColor.BLUE));
    combination.add(new Tile(TileNumber.THREE, TileColor.BLUE));
    combination.add(new Tile(TileNumber.FOUR, TileColor.BLUE));
    combination.add(new Tile(TileNumber.FIVE, TileColor.BLUE));

    assert gameInfo.isValidMove(combination);
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

    assert gameInfo.isValidMove(combination);
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

    assert gameInfo.isValidMove(combination);
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

    assert gameInfo.isValidMove(combination);
    assert gameInfo.getPointsForMove(combination) == 47;
  }

  @Test
  void playerTest() {
    Player player_1 = PFactory.make();
    System.out.println("ID_1 = " + player_1.getId());

    Player player_2 = PFactory.make();
    System.out.println("ID_2 = " + player_2.getId());

    Player player_3 = PFactory.make();
    System.out.println("ID_3 = " + player_3.getId());

    player_1.putTile(new Tile(TileNumber.ELEVEN, TileColor.YELLOW));

    for (Tile tile : player_1.getTilesOnHand()) {
      System.out.println("TILE = " + tile.getNumber() + ", " + tile.getColor());
    }
  }
}
