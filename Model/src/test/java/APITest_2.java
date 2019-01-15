import gameinfo.GIGameInfo;
import gameinfo.GIFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


class APITest_2 {
  private static GIGameInfo gameInfo;

  private static Integer player_1_id = 1;
  private static Integer player_2_id = 2;
  private static Integer player_3_id = 3;
  private static Integer player_4_id = 4;

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
    //distribute();
    playerSignsOut();
    identifyPlayerSequence();
//    drawOneTile();
//    putCombo();
  }


  void start() {
    gameInfo.start();
  }


  void distribute() {
    gameInfo.drawBy(player_1_id);
    gameInfo.drawBy(player_2_id);
    gameInfo.drawBy(player_3_id);
    gameInfo.drawBy(player_4_id);

  }


  void playerSignsOut() {

    gameInfo.deregisterBy(player_4_id);

    assert gameInfo.getNumberOfPlayers().get() == 3;
  }

  //three players left

  void identifyPlayerSequence() {
    System.out.println(gameInfo.getNextPlayerId());
  }

  void drawOneTile() {
    gameInfo.drawBy(player_1_id).get();
    gameInfo.drawBy(player_2_id).get();
    gameInfo.drawBy(player_3_id).get();
  }

  //next player will put a combo on the board


  void putCombo() {
    gameInfo.isValidPlayerBy(player_1_id).get();

    System.out.println(gameInfo.getAllTilesBy(player_1_id));
  }

  //getAllTileById possible

}
