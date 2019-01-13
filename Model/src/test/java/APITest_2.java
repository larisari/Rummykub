import gameinfo.GIGameInfo;
import gameinfo.GIFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class APITest_2 {
  private static GIGameInfo gameInfo;

  private static String player_1_id = UUID.randomUUID().toString();
  private static String player_2_id = UUID.randomUUID().toString();
  private static String player_3_id = UUID.randomUUID().toString();
  private static String player_4_id = UUID.randomUUID().toString();

  //TODO temporary toString() method for Tile representation?

  @BeforeAll
  static void setup() {
    gameInfo = GIFactory.make();

    gameInfo.registerBy(player_1_id);
    gameInfo.registerBy(player_2_id);
    gameInfo.registerBy(player_3_id);
    gameInfo.registerBy(player_4_id);
  }

  @Test
  void sortedTestRun() {
    start();
    distribute();
    playerSignsOut();
    drawOneTile();
    putCombo();
  }

  @Test
  void start() {
    gameInfo.start();
  }

  @Test
  void distribute() {
    gameInfo.drawBy(player_1_id);
    gameInfo.drawBy(player_2_id);
    gameInfo.drawBy(player_3_id);
    gameInfo.drawBy(player_4_id);
  }

  @Test
  void playerSignsOut() {
    gameInfo.deregisterBy(player_4_id);
    System.out.println(gameInfo.getAllPlayerIds());
    System.out.println(gameInfo.getNumberOfPlayers());
  }

  //three players left

  @Test
  void drawOneTile() {
    gameInfo.drawBy(player_1_id);
    gameInfo.drawBy(player_2_id);
    gameInfo.drawBy(player_3_id);
  }

  //next player will put a combo on the board

  @Test
  void putCombo() {
    gameInfo.isValidPlayerBy(player_1_id);

    System.out.println(gameInfo.getAllTilesBy(player_1_id));
  }

  //getAllTileById possible

}
