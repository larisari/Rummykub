package gameinfo;

//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;

class GameInfo_Test {

  //TODO create test folder

  private GIGameInfo gameInfo;
  private GIFactory factory;

  private final Integer player_1_ID = 1;
  private final Integer player_2_ID = 2;
  private final Integer player_3_ID = 3;
  private final Integer player_4_ID = 4;

//  @BeforeAll
  static void setup() {
    // make GIGI
  }

 // @Test
  void getAllPlayersHands() {
    gameInfo = factory.make();

    gameInfo.registerBy(player_1_ID);
    gameInfo.registerBy(player_2_ID);
    gameInfo.registerBy(player_3_ID);
    gameInfo.registerBy(player_4_ID);

    gameInfo.startGame();

    gameInfo.drawBy(player_1_ID);
    gameInfo.drawBy(player_2_ID);
    gameInfo.drawBy(player_3_ID);
    gameInfo.drawBy(player_4_ID);

    System.out.println(gameInfo.getPlayerPoints().get());

    }

}