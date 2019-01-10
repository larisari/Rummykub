import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class TestModel {

  @Test
  void testStreetOneToFive() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(TileNumber.ONE, TileColor.BLUE));
    combination.add(new Tile(TileNumber.TWO, TileColor.BLUE));
    combination.add(new Tile(TileNumber.THREE, TileColor.BLUE));
    combination.add(new Tile(TileNumber.FOUR, TileColor.BLUE));
    combination.add(new Tile(TileNumber.FIVE, TileColor.BLUE));

    assert CombRules.isValid(combination);
  }

  @Test
  void testStreetTenToOne() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(TileNumber.TEN, TileColor.BLUE));
    combination.add(new Tile(TileNumber.ELEVEN, TileColor.BLUE));
    combination.add(new Tile(TileNumber.TWELVE, TileColor.BLUE));
    combination.add(new Tile(TileNumber.THIRTEEN, TileColor.BLUE));
    combination.add(new Tile(TileNumber.ONE, TileColor.BLUE));

    assert CombRules.isValid(combination);
  }

  @Test
  void testGroup() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(TileNumber.TEN, TileColor.BLUE));
    combination.add(new Tile(TileNumber.TEN, TileColor.BLACK));
    combination.add(new Tile(TileNumber.TEN, TileColor.YELLOW));
    combination.add(new Tile(TileNumber.TEN, TileColor.RED));

    assert CombRules.isValid(combination);
  }

  @Test
  void testValidFirstMoveWrongComb() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(TileNumber.TEN, TileColor.BLUE));
    combination.add(new Tile(TileNumber.TEN, TileColor.BLACK));
    combination.add(new Tile(TileNumber.ELEVEN, TileColor.YELLOW));

    assert !CombRules.isValidFirst(combination);
  }

  @Test
  void testValidFirstMove() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(TileNumber.TEN, TileColor.BLUE));
    combination.add(new Tile(TileNumber.TEN, TileColor.BLACK));
    combination.add(new Tile(TileNumber.TEN, TileColor.YELLOW));

    assert CombRules.isValidFirst(combination);
  }

  @Test
  void testValidFirstMoveNotEnoughPoints() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(TileNumber.NINE, TileColor.BLUE));
    combination.add(new Tile(TileNumber.NINE, TileColor.BLACK));
    combination.add(new Tile(TileNumber.NINE, TileColor.YELLOW));

    assert !CombRules.isValidFirst(combination);
  }

  @Test
  void testGroupWithJoker() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(TileNumber.JOKER, TileColor.JOKER));
    combination.add(new Tile(TileNumber.TEN, TileColor.BLUE));
    combination.add(new Tile(TileNumber.TEN, TileColor.BLACK));
    combination.add(new Tile(TileNumber.TEN, TileColor.YELLOW));

    assert CombRules.isValid(combination);
  }

  @Test
  void testGroupWithJokerNotFirst() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(TileNumber.TEN, TileColor.BLUE));
    combination.add(new Tile(TileNumber.JOKER, TileColor.JOKER));
    combination.add(new Tile(TileNumber.TEN, TileColor.BLACK));
    combination.add(new Tile(TileNumber.TEN, TileColor.YELLOW));

    assert CombRules.isValid(combination);
    assert PointsCalculator.getPointsOf(combination) == 40;
  }

  @Test
  void testStreetOneToFiveJOKER() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(TileNumber.JOKER, TileColor.JOKER));
    combination.add(new Tile(TileNumber.TWO, TileColor.BLUE));
    combination.add(new Tile(TileNumber.THREE, TileColor.BLUE));
    combination.add(new Tile(TileNumber.FOUR, TileColor.BLUE));
    combination.add(new Tile(TileNumber.FIVE, TileColor.BLUE));

    assert CombRules.isValid(combination);
    assert PointsCalculator.getPointsOf(combination) == 15;
  }

  @Test
  void testStreetOneToFiveJOKERNotAtFirst() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(TileNumber.ONE, TileColor.BLUE));
    combination.add(new Tile(TileNumber.TWO, TileColor.BLUE));
    combination.add(new Tile(TileNumber.JOKER, TileColor.JOKER));
    combination.add(new Tile(TileNumber.FOUR, TileColor.BLUE));
    combination.add(new Tile(TileNumber.FIVE, TileColor.BLUE));

    assert CombRules.isValid(combination);
    assert PointsCalculator.getPointsOf(combination) == 15;
  }

  @Test
  void testStreetTenToOneJOKER() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(TileNumber.JOKER, TileColor.JOKER));
    combination.add(new Tile(TileNumber.ELEVEN, TileColor.BLUE));
    combination.add(new Tile(TileNumber.TWELVE, TileColor.BLUE));
    combination.add(new Tile(TileNumber.THIRTEEN, TileColor.BLUE));
    combination.add(new Tile(TileNumber.ONE, TileColor.BLUE));

    assert CombRules.isValid(combination);
    assert PointsCalculator.getPointsOf(combination) == 47;
  }

  @Test
  void testStreetTenToOneJOKERNotAtFirst() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(TileNumber.TEN, TileColor.BLUE));
    combination.add(new Tile(TileNumber.ELEVEN, TileColor.BLUE));
    combination.add(new Tile(TileNumber.TWELVE, TileColor.BLUE));
    combination.add(new Tile(TileNumber.JOKER, TileColor.JOKER));
    combination.add(new Tile(TileNumber.ONE, TileColor.BLUE));

    assert CombRules.isValid(combination);
    assert PointsCalculator.getPointsOf(combination) == 47;
  }

  @Test
  void playerTest() {
    Player player_1 = new PlayerImpl();
    System.out.println("ID_1 = " + player_1.getId());

    Player player_2 = new PlayerImpl();
    System.out.println("ID_2 = " + player_2.getId());

    Player player_3 = new PlayerImpl();
    System.out.println("ID_3 = " + player_3.getId());

    player_1.putTile(new Tile(TileNumber.ELEVEN, TileColor.YELLOW));

    for (Tile tile : player_1.getTilesOnHand()) {
      System.out.println("TILE = " + tile.getNumber() + ", " + tile.getColor());
    }
  }

}
