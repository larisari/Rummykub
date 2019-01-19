package gameinfo;

import gameinfo.util.GIColor;
import gameinfo.util.GINumber;
import gameinfo.util.GITile;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class GameInfo_Test {

  //TODO create test folder ?

  private static GIGameInfo gameInfo;

  private static Player p1;
  private static Player p2;
  private static Player p3;
  private static Player p4;

  private static final Integer player_1_ID = 1;
  private static final Integer player_2_ID = 2;
  private static final Integer player_3_ID = 3;
  private static final Integer player_4_ID = 4;

  @BeforeAll
  static void setup() {
    gameInfo = GIFactory.make();
    p1 = new Player(player_1_ID,new PointsCalculator());
    p2 = new Player(player_2_ID,new PointsCalculator());
    p3 = new Player(player_3_ID,new PointsCalculator());
    p4 = new Player(player_4_ID,new PointsCalculator());
    gameInfo.startGame();
  }

  @Test
  void orderedTestCase() {
    signInPlayers();
    draw();
    putTilesOnHand();
    validCombo();
  }

  @Test
  void signInPlayers() {
    gameInfo.registerBy(player_1_ID);
    gameInfo.registerBy(player_2_ID);
    gameInfo.registerBy(player_3_ID);
    gameInfo.registerBy(player_4_ID);
  }

  @Test
  void draw() {
    gameInfo.drawBy(player_1_ID);
    gameInfo.drawBy(player_2_ID);
    gameInfo.drawBy(player_3_ID);
    gameInfo.drawBy(player_4_ID);
  }

  @Test
  void putTilesOnHand() {
    //group under 30 points
    p1.put(new GITile(GINumber.ONE, GIColor.BLACK));
    p1.put(new GITile(GINumber.ONE, GIColor.BLUE));
    p1.put(new GITile(GINumber.ONE, GIColor.YELLOW));

    //group 30 points
    p1.put(new GITile(GINumber.ELEVEN, GIColor.BLACK));
    p1.put(new GITile(GINumber.ELEVEN, GIColor.BLUE));
    p1.put(new GITile(GINumber.ELEVEN, GIColor.YELLOW));
  }

  @Test
  void lessThanMinPoints() {
    List<GITile> playersGroup =
        new ArrayList<>(Arrays.asList(p1.getTilesOnHand().get(0),
            p1.getTilesOnHand().get(1), p1.getTilesOnHand().get(2)));

    List<List<GITile>> combinations = new ArrayList<>();
    combinations.add(playersGroup);

    System.out.println(gameInfo.play(combinations,player_1_ID));
  }

  @Test
  void validCombo() {
    List<GITile> playersGroup =
        new ArrayList<>(Arrays.asList(p1.getTilesOnHand().get(3),
            p1.getTilesOnHand().get(4), p1.getTilesOnHand().get(5)));

    List<List<GITile>> combinations = new ArrayList<>();
    combinations.add(playersGroup);

    System.out.println(gameInfo.play(combinations,player_1_ID));
  }

  @Test
  void getAllPlayersHands() {
    System.out.println(gameInfo.getPlayerPoints());
  }
}