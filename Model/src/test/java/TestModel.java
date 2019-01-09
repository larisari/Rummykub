import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class TestModel {

  @Test
  void testStreetOneToFive() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(Number.ONE, Color.BLUE));
    combination.add(new Tile(Number.TWO, Color.BLUE));
    combination.add(new Tile(Number.THREE, Color.BLUE));
    combination.add(new Tile(Number.FOUR, Color.BLUE));
    combination.add(new Tile(Number.FIVE, Color.BLUE));

    assert CombRules.isValid(combination);
  }

  @Test
  void testStreetTenToOne() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(Number.TEN, Color.BLUE));
    combination.add(new Tile(Number.ELEVEN, Color.BLUE));
    combination.add(new Tile(Number.TWELVE, Color.BLUE));
    combination.add(new Tile(Number.THIRTEEN, Color.BLUE));
    combination.add(new Tile(Number.ONE, Color.BLUE));

    assert CombRules.isValid(combination);
  }

  @Test
  void testGroup() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(Number.TEN, Color.BLUE));
    combination.add(new Tile(Number.TEN, Color.BLACK));
    combination.add(new Tile(Number.TEN, Color.YELLOW));
    combination.add(new Tile(Number.TEN, Color.RED));

    assert CombRules.isValid(combination);
  }

  @Test
  void testValidFirstMoveWrongComb() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(Number.TEN, Color.BLUE));
    combination.add(new Tile(Number.TEN, Color.BLACK));
    combination.add(new Tile(Number.ELEVEN, Color.YELLOW));

    assert !CombRules.isValidFirst(combination);
  }

  @Test
  void testValidFirstMove() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(Number.TEN, Color.BLUE));
    combination.add(new Tile(Number.TEN, Color.BLACK));
    combination.add(new Tile(Number.TEN, Color.YELLOW));

    assert CombRules.isValidFirst(combination);
  }

  @Test
  void testValidFirstMoveNotEnoughPoints() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(Number.NINE, Color.BLUE));
    combination.add(new Tile(Number.NINE, Color.BLACK));
    combination.add(new Tile(Number.NINE, Color.YELLOW));

    assert !CombRules.isValidFirst(combination);
  }

  @Test
  void playerTest() {
    Player player_1 = new PlayerImpl();
    System.out.println("ID_1 = " + player_1.getId());

    Player player_2 = new PlayerImpl();
    System.out.println("ID_2 = " + player_2.getId());

    Player player_3 = new PlayerImpl();
    System.out.println("ID_3 = " + player_3.getId());

    player_1.putTile(new Tile(Number.ELEVEN, Color.YELLOW));

    for (Tile tile : player_1.getTilesOnHand()) {
      System.out.println("TILE = " + tile.getNumber() + ", " + tile.getColor());
    }
  }

}
