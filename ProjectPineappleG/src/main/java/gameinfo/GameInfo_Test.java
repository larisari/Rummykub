package gameinfo;

import gameinfo.util.GIColor;
import gameinfo.util.GINumber;
import gameinfo.util.GITile;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;

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

  private static GITile one_black = new GITile(GINumber.ONE, GIColor.BLACK);
  private static GITile one_blue = new GITile(GINumber.ONE, GIColor.BLUE);
  private static GITile one_yellow = new GITile(GINumber.ONE, GIColor.YELLOW);
  private static GITile eleven_black = new GITile(GINumber.ELEVEN, GIColor.BLACK);
  private static GITile eleven_blue = new GITile(GINumber.ELEVEN, GIColor.BLUE);
  private static GITile eleven_yellow = new GITile(GINumber.ELEVEN, GIColor.YELLOW);

 // @BeforeAll
  static void setup() {
    gameInfo = GIFactory.make();
    p1 = new Player(player_1_ID,new PointsCalculator());
    p2 = new Player(player_2_ID,new PointsCalculator());
    p3 = new Player(player_3_ID,new PointsCalculator());
    p4 = new Player(player_4_ID,new PointsCalculator());
  }

//  @Test
  void orderedTestCase() {
    signInPlayers();
    start();
    putTilesOnHand();
    //draw();
    //lessThanMinPoints();
    //validCombo();
    showhands();
    //makeFirstMove();
  }

  void start() {
    gameInfo.startGame();
  }

  void signInPlayers() {
    gameInfo.registerBy(player_1_ID);
    gameInfo.registerBy(player_2_ID);
    gameInfo.registerBy(player_3_ID);
    gameInfo.registerBy(player_4_ID);
  }

  void draw() {
    //gameInfo.drawBy(player_1_ID);
    gameInfo.drawBy(player_2_ID);
    gameInfo.drawBy(player_3_ID);
    gameInfo.drawBy(player_4_ID);
  }

  void putTilesOnHand() {

  //group less than 30 points
    p1.put(one_black);
    p1.put(one_blue);
    p1.put(one_yellow);

  //group 30+ points
    p1.put(eleven_black);
    p1.put(eleven_blue);
    p1.put(eleven_yellow);
}

  void lessThanMinPoints() {
    List<GITile> playersGroup =
        new ArrayList<>(Arrays.asList(p1.getTilesOnHand().get(0),
            p1.getTilesOnHand().get(1), p1.getTilesOnHand().get(2)));

    List<List<GITile>> combinations = new ArrayList<>();
    combinations.add(playersGroup);

    assert !gameInfo.play(combinations,player_1_ID).get().getSecond();
  }

  void validCombo() {
    List<GITile> playersGroup =
        new ArrayList<>(Arrays.asList(p1.getTilesOnHand().get(3),
            p1.getTilesOnHand().get(4), p1.getTilesOnHand().get(5)));

    List<List<GITile>> combinations = new ArrayList<>();
    combinations.add(playersGroup);

    assert gameInfo.getNextPlayerId().get().equals(player_2_ID);

    System.out.println(gameInfo.play(combinations,player_1_ID).get());



    System.out.println(p1.getTilesOnHand());

    assert gameInfo.getNextPlayerId().get().equals(player_3_ID);

    assert p1.getTilesOnHand().contains(one_black);
    assert p1.getTilesOnHand().contains(one_blue);
    assert p1.getTilesOnHand().contains(one_yellow);
    assert p1.getTilesOnHand().size() == 6;
  }

  void getAllPlayersHands() {
    System.out.println(gameInfo.getPlayerPoints());
  }

  void showhands() {

    System.out.println(p1.getTilesOnHand());
    if (gameInfo.getAllTilesBy(player_1_ID).isPresent()) {
      System.out.println(gameInfo.getAllTilesBy(player_1_ID));
    }
    else {

    }
//    System.out.println(gameInfo.getAllTilesBy(player_2_ID).get());
//    System.out.println(gameInfo.getAllTilesBy(player_3_ID).get());
//    System.out.println(gameInfo.getAllTilesBy(player_4_ID).get());
  }

  void makeFirstMove() {
    assert gameInfo.getNextPlayerId().get().equals(player_2_ID);
    gameInfo.drawBy(player_1_ID);
    assert gameInfo.getNextPlayerId().get().equals(player_3_ID);
    assert gameInfo.getAllTilesBy(player_1_ID).get().getSecond().size() == 15;
  }
}