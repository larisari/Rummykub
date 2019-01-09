import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class ModelTest {

  @Test
  void testStreetOneToFive() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(1, Color.BLUE));
    combination.add(new Tile(2, Color.BLUE));
    combination.add(new Tile(3, Color.BLUE));
    combination.add(new Tile(4, Color.BLUE));
    combination.add(new Tile(5, Color.BLUE));

    assert CombRules.isValid(combination);
  }

  @Test
  void testStreetTenToOne() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(10, Color.BLUE));
    combination.add(new Tile(11, Color.BLUE));
    combination.add(new Tile(12, Color.BLUE));
    combination.add(new Tile(13, Color.BLUE));
    combination.add(new Tile(14, Color.BLUE));

    assert CombRules.isValid(combination);
  }

  @Test
  void testGroup() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(10, Color.BLUE));
    combination.add(new Tile(10, Color.BLACK));
    combination.add(new Tile(10, Color.YELLOW));
    combination.add(new Tile(10, Color.RED));

    assert CombRules.isValid(combination);
  }

  @Test
  void testValidFirstMoveWrongComb() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(10, Color.BLUE));
    combination.add(new Tile(10, Color.BLACK));
    combination.add(new Tile(11, Color.YELLOW));

    assert !CombRules.isValidFirst(combination);
  }

  @Test
  void testValidFirstMove() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(10, Color.BLUE));
    combination.add(new Tile(10, Color.BLACK));
    combination.add(new Tile(10, Color.YELLOW));

    assert CombRules.isValidFirst(combination);
  }

  @Test
  void testValidFirstMoveNotEnoughPoints() {
    List<Tile> combination = new ArrayList<>();
    combination.add(new Tile(9, Color.BLUE));
    combination.add(new Tile(9, Color.BLACK));
    combination.add(new Tile(9, Color.YELLOW));

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

    player_1.putTile(new Tile(11, Color.YELLOW));

    for (Tile tile : player_1.getTilesOnHand()) {
      System.out.println("TILE = " + tile.getNumber() + ", " + tile.getColor());
    }
  }

}
