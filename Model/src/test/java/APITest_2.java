import gameinfo.GIGameInfo;
import gameinfo.GIFactory;
import gameinfo.tile.Tile;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class APITest_2 {
  private static GIGameInfo gameInfo;

  private static String player_1_id = "1";
  private static String player_2_id = "2";
  private static String player_3_id = "3";
  private static String player_4_id = "4";

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
/*  drawOneTile();
    putCombo();*/
  }


  void start() {
    gameInfo.start();
  }


  void distribute() {
    gameInfo.drawBy(player_1_id).get();
    gameInfo.drawBy(player_2_id).get();
    gameInfo.drawBy(player_3_id).get();
    gameInfo.drawBy(player_4_id).get();

    //player_1_tiles.addAll(gameInfo.drawBy(player_1_id).get());
  }


  void playerSignsOut() {
    gameInfo.deregisterBy(player_4_id);
    //System.out.println(gameInfo.getAllPlayerIds().get());
    System.out.println(gameInfo.getNumberOfPlayers().get());
  }

  //three players left


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
