import gameinfo.player.Player;
import gameinfo.player.PlayerFactory;
import gameinfo.rules.PointsCalculator;
import gameinfo.rules.Rules;
import gameinfo.rules.RulesFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import tile.Tile;
import tile.util.TileColor;
import tile.util.TileNumber;

import java.util.ArrayList;
import java.util.List;

class TestModel {

  private static Rules rules;
  private static PointsCalculator calculator;

  @BeforeAll
  static void createCombRules() {
    calculator = new PointsCalculator();
    rules = RulesFactory.make();
  }

  @Test
  void testStreetOneToFive() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(TileNumber.ONE, TileColor.BLUE));
    combination.add(new Tile(TileNumber.TWO, TileColor.BLUE));
    combination.add(new Tile(TileNumber.THREE, TileColor.BLUE));
    combination.add(new Tile(TileNumber.FOUR, TileColor.BLUE));
    combination.add(new Tile(TileNumber.FIVE, TileColor.BLUE));

    assert rules.isValid(combination);
  }

  @Test
  void testStreetTenToOne() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(TileNumber.TEN, TileColor.BLUE));
    combination.add(new Tile(TileNumber.ELEVEN, TileColor.BLUE));
    combination.add(new Tile(TileNumber.TWELVE, TileColor.BLUE));
    combination.add(new Tile(TileNumber.THIRTEEN, TileColor.BLUE));
    combination.add(new Tile(TileNumber.ONE, TileColor.BLUE));

    assert rules.isValid(combination);
  }

  @Test
  void testGroup() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(TileNumber.TEN, TileColor.BLUE));
    combination.add(new Tile(TileNumber.TEN, TileColor.BLACK));
    combination.add(new Tile(TileNumber.TEN, TileColor.YELLOW));
    combination.add(new Tile(TileNumber.TEN, TileColor.RED));

    assert rules.isValid(combination);
  }

  @Test
  void testValidFirstMoveWrongComb() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(TileNumber.TEN, TileColor.BLUE));
    combination.add(new Tile(TileNumber.TEN, TileColor.BLACK));
    combination.add(new Tile(TileNumber.ELEVEN, TileColor.YELLOW));

    assert !rules.isValid(combination, 30);
  }

  @Test
  void testValidFirstMove() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(TileNumber.TEN, TileColor.BLUE));
    combination.add(new Tile(TileNumber.TEN, TileColor.BLACK));
    combination.add(new Tile(TileNumber.TEN, TileColor.YELLOW));

    assert rules.isValid(combination, 30);
  }

  @Test
  void testValidFirstMoveNotEnoughPoints() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(TileNumber.NINE, TileColor.BLUE));
    combination.add(new Tile(TileNumber.NINE, TileColor.BLACK));
    combination.add(new Tile(TileNumber.NINE, TileColor.YELLOW));

    assert !rules.isValid(combination, 30);
  }

  @Test
  void testGroupWithJoker() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(TileNumber.JOKER, TileColor.JOKER));
    combination.add(new Tile(TileNumber.TEN, TileColor.BLUE));
    combination.add(new Tile(TileNumber.TEN, TileColor.BLACK));
    combination.add(new Tile(TileNumber.TEN, TileColor.YELLOW));

    assert rules.isValid(combination);
  }

  @Test
  void testGroupWithJokerNotFirst() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(TileNumber.TEN, TileColor.BLUE));
    combination.add(new Tile(TileNumber.JOKER, TileColor.JOKER));
    combination.add(new Tile(TileNumber.TEN, TileColor.BLACK));
    combination.add(new Tile(TileNumber.TEN, TileColor.YELLOW));

    assert rules.isValid(combination);
    assert calculator.getPointsForGroup(combination) == 40;
  }

  @Test
  void testStreetOneToFiveJOKER() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(TileNumber.JOKER, TileColor.JOKER));
    combination.add(new Tile(TileNumber.TWO, TileColor.BLUE));
    combination.add(new Tile(TileNumber.THREE, TileColor.BLUE));
    combination.add(new Tile(TileNumber.FOUR, TileColor.BLUE));
    combination.add(new Tile(TileNumber.FIVE, TileColor.BLUE));

    assert rules.isValid(combination);
    assert calculator.getPointsForStreet(combination) == 15;
  }

  @Test
  void testStreetOneToFiveJOKERNotAtFirst() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(TileNumber.ONE, TileColor.BLUE));
    combination.add(new Tile(TileNumber.TWO, TileColor.BLUE));
    combination.add(new Tile(TileNumber.JOKER, TileColor.JOKER));
    combination.add(new Tile(TileNumber.FOUR, TileColor.BLUE));
    combination.add(new Tile(TileNumber.FIVE, TileColor.BLUE));

    assert rules.isValid(combination);
    assert calculator.getPointsForStreet(combination) == 15;
  }

  @Test
  void testStreetTenToOneJOKER() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(TileNumber.JOKER, TileColor.JOKER));
    combination.add(new Tile(TileNumber.ELEVEN, TileColor.BLUE));
    combination.add(new Tile(TileNumber.TWELVE, TileColor.BLUE));
    combination.add(new Tile(TileNumber.THIRTEEN, TileColor.BLUE));
    combination.add(new Tile(TileNumber.ONE, TileColor.BLUE));

    assert rules.isValid(combination);
    assert calculator.getPointsForStreet(combination) == 47;
  }

  @Test
  void testStreetTenToOneJOKERNotAtFirst() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(TileNumber.TEN, TileColor.BLUE));
    combination.add(new Tile(TileNumber.ELEVEN, TileColor.BLUE));
    combination.add(new Tile(TileNumber.TWELVE, TileColor.BLUE));
    combination.add(new Tile(TileNumber.JOKER, TileColor.JOKER));
    combination.add(new Tile(TileNumber.ONE, TileColor.BLUE));

    assert rules.isValid(combination);
    assert calculator.getPointsForStreet(combination) == 47;
  }

  @Test
  void playerTest() {
    Player player_1 = PlayerFactory.make();
    System.out.println("ID_1 = " + player_1.getId());

    Player player_2 = PlayerFactory.make();
    System.out.println("ID_2 = " + player_2.getId());

    Player player_3 = PlayerFactory.make();
    System.out.println("ID_3 = " + player_3.getId());

    player_1.putTile(new Tile(TileNumber.ELEVEN, TileColor.YELLOW));

    for (Tile tile : player_1.getTilesOnHand()) {
      System.out.println("TILE = " + tile.getNumber() + ", " + tile.getColor());
    }
  }
}
