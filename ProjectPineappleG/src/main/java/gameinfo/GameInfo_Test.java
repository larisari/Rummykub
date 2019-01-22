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

  private static List<GITile> hand_player_1 =
      new ArrayList<>(Arrays.asList(new GITile(GINumber.THREE,GIColor.BLACK),
       new GITile(GINumber.THREE,GIColor.BLUE), new GITile(GINumber.THREE,
              GIColor.YELLOW), new GITile(GINumber.FOUR,GIColor.BLACK),
          new GITile(GINumber.FOUR,GIColor.BLUE),
          new GITile(GINumber.FOUR,GIColor.YELLOW), new GITile(GINumber.FIVE,
              GIColor.BLACK),
          new GITile(GINumber.FIVE,GIColor.BLUE), new GITile(GINumber.FIVE,
              GIColor.YELLOW), new GITile(GINumber.ELEVEN,GIColor.BLACK),
          new GITile(GINumber.ELEVEN,GIColor.BLUE),
          new GITile(GINumber.ELEVEN,GIColor.YELLOW), new GITile(GINumber.JOKER,
              GIColor.JOKER
              ), new GITile(GINumber.JOKER,GIColor.JOKER)));

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
    draw();
    //lessThanMinPoints();
    validCombo();
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
    gameInfo.drawBy(player_1_ID, hand_player_1);
    gameInfo.drawBy(player_2_ID);
    gameInfo.drawBy(player_3_ID);
    gameInfo.drawBy(player_4_ID);
  }


  void lessThanMinPoints() {
    List<GITile> playersGroup =
        new ArrayList<>(Arrays.asList(gameInfo.getAllTilesBy(player_1_ID).get().getSecond().get(0),
            gameInfo.getAllTilesBy(player_1_ID).get().getSecond().get(1),
            gameInfo.getAllTilesBy(player_1_ID).get().getSecond().get(2)));

    List<List<GITile>> combinations = new ArrayList<>();
    combinations.add(playersGroup);

    assert !gameInfo.play(combinations,player_1_ID).get().getSecond();
  }

  void validCombo() {
    List<GITile> playersGroup =
        new ArrayList<>(Arrays.asList(gameInfo.getAllTilesBy(player_1_ID).get()
            .getSecond().get(9), gameInfo.getAllTilesBy(player_1_ID).get()
            .getSecond().get(10), gameInfo.getAllTilesBy(player_1_ID).get()
            .getSecond().get(11)));

    List<List<GITile>> combinations = new ArrayList<>();
    combinations.add(playersGroup);

    assert gameInfo.play(combinations,player_1_ID).get().getSecond();

  }

  void getAllPlayersHands() {
    System.out.println(gameInfo.getPlayerPoints());
  }

  void showhands() {
    System.out.println(gameInfo.getAllTilesBy(player_1_ID).get());
    System.out.println(gameInfo.getAllTilesBy(player_2_ID).get());
    System.out.println(gameInfo.getAllTilesBy(player_3_ID).get());
    System.out.println(gameInfo.getAllTilesBy(player_4_ID).get());

    //player playes 3 tiles, so he should now have 11 left on his hand
    assert gameInfo.getAllTilesBy(player_1_ID).get().getSecond().size() == 11;
  }

  void makeFirstMove() {
    assert gameInfo.getNextPlayerId().get().equals(player_2_ID);
    gameInfo.drawBy(player_1_ID);
    assert gameInfo.getNextPlayerId().get().equals(player_3_ID);
    assert gameInfo.getAllTilesBy(player_1_ID).get().getSecond().size() == 15;
  }
}