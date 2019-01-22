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

  private static GITile one_black = new GITile(GINumber.ONE, GIColor.BLACK);
  private static GITile one_blue = new GITile(GINumber.ONE, GIColor.BLUE);
  private static GITile one_yellow = new GITile(GINumber.ONE, GIColor.YELLOW);
  private static GITile eleven_black = new GITile(GINumber.ELEVEN, GIColor.BLACK);
  private static GITile eleven_blue = new GITile(GINumber.ELEVEN, GIColor.BLUE);
  private static GITile eleven_yellow = new GITile(GINumber.ELEVEN, GIColor.YELLOW);
  private static GITile two_black = new GITile(GINumber.TWO, GIColor.BLACK);
  private static GITile two_blue = new GITile(GINumber.TWO, GIColor.BLUE);
  private static GITile two_yellow = new GITile(GINumber.TWO, GIColor.YELLOW);
  private static GITile three_black = new GITile(GINumber.THREE,
      GIColor.BLACK);
  private static GITile three_blue = new GITile(GINumber.THREE, GIColor.BLUE);
  private static GITile three_yellow = new GITile(GINumber.THREE, GIColor.YELLOW);
  private static GITile four_black = new GITile(GINumber.FOUR, GIColor.BLACK);
  private static GITile four_blue = new GITile(GINumber.FOUR,
      GIColor.BLUE);


  @BeforeAll
  static void setup() {
    gameInfo = GIFactory.make();
    p1 = new Player(player_1_ID,new PointsCalculator());
    p2 = new Player(player_2_ID,new PointsCalculator());
    p3 = new Player(player_3_ID,new PointsCalculator());
    p4 = new Player(player_4_ID,new PointsCalculator());
  }


  @Test
  void orderedTestCase() {
    signInPlayers();
    start();
    drawCustom();
    //draw();
    //lessThanMinPoints();
    //validCombo();
    //showHands();
    //makeFirstMove();
    //getCurrentPlayer();
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

  void drawCustom() {
    List<GITile> player_1_hand = new ArrayList<>(Arrays.asList(one_black,
        one_blue,one_yellow,two_black,two_blue,two_yellow,three_black,
        three_blue,three_yellow,four_black,four_blue,eleven_black,eleven_blue
        , eleven_yellow));

    gameInfo.drawBy(player_1_ID,player_1_hand);
    System.out.println(gameInfo.getAllTilesBy(player_1_ID));
}

void getCurrentPlayer() {
    System.out.println(gameInfo.getCurrentPlayerId());
    gameInfo.drawBy(player_1_ID);
    System.out.println(gameInfo.getCurrentPlayerId());
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

  void showHands() {
    System.out.println(gameInfo.getAllTilesBy(player_1_ID).get());
    System.out.println(gameInfo.getAllTilesBy(player_2_ID).get());
    System.out.println(gameInfo.getAllTilesBy(player_3_ID).get());
    System.out.println(gameInfo.getAllTilesBy(player_4_ID).get());
  }

  void makeFirstMove() {
    assert gameInfo.getNextPlayerId().get().equals(player_2_ID);
    gameInfo.drawBy(player_1_ID);
    assert gameInfo.getNextPlayerId().get().equals(player_3_ID);
    assert gameInfo.getAllTilesBy(player_1_ID).get().getSecond().size() == 15;
  }
}