import gameinfo.GIFactory;
import gameinfo.GIGameInfo;
import org.junit.jupiter.api.Test;
import gameinfo.tile.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class APITest {

  private static GIGameInfo gameInfo;

  private static String player_1_id = UUID.randomUUID().toString();
  private static String player_2_id = UUID.randomUUID().toString();
  private static String player_3_id = UUID.randomUUID().toString();
  private static String player_4_id = UUID.randomUUID().toString();

  private static List<Tile> player_1_tiles = new ArrayList<>();
  private static List<Tile> player_2_tiles = new ArrayList<>();
  private static List<Tile> player_3_tiles = new ArrayList<>();
  private static List<Tile> player_4_tiles = new ArrayList<>();


  @Test
  void runTestSuit() {
    gameInfo = GIFactory.make();
    stage_0_registerPlayers();
    stage_1_isValidPlayer();
    stage_2_drawStack();
    stage_3_drawTile();
  }

  void stage_0_registerPlayers() {
    gameInfo.registerBy(player_1_id);
    gameInfo.registerBy(player_2_id);
    gameInfo.registerBy(player_3_id);
    gameInfo.registerBy(player_4_id);

    List<String> ids = gameInfo.getAllPlayerIds().get();

    for (String id : ids) {
      assert id.equals(player_1_id)
              || id.equals(player_2_id)
              || id.equals(player_3_id)
              || id.equals(player_4_id);
    }
  }

  void stage_1_isValidPlayer() {
    assert gameInfo.isValidPlayerBy(player_1_id).get();
  }

  void stage_2_drawStack() {
    player_1_tiles.addAll(gameInfo.drawBy(player_1_id).get());
    player_2_tiles.addAll(gameInfo.drawBy(player_2_id).get());
    player_3_tiles.addAll(gameInfo.drawBy(player_3_id).get());
    player_4_tiles.addAll(gameInfo.drawBy(player_4_id).get());
  }

  void stage_3_drawTile() {
    player_1_tiles.addAll(gameInfo.drawBy(player_1_id).get());
    player_2_tiles.addAll(gameInfo.drawBy(player_2_id).get());
    player_3_tiles.addAll(gameInfo.drawBy(player_3_id).get());
    player_4_tiles.addAll(gameInfo.drawBy(player_4_id).get());
  }

//
//  void stage_3_PlayCombinationFirstMoveValid() {
//    List<Tile> combination =
//            new ArrayList<>(
//                    Arrays.asList(
//                            new Tile(Number.TEN, Color.YELLOW),
//                            new Tile(Number.ELEVEN, Color.YELLOW),
//                            new Tile(Number.TWELVE, Color.YELLOW),
//                            new Tile(Number.THIRTEEN, Color.YELLOW),
//                            new Tile(Number.ONE, Color.YELLOW)));
//
//    assert gameInfo.play(combination, player_1_id).get();
//  }
//
//  void stage_2_GetNextPlayerId() {
//    System.out.println(player_1_id);
//    System.out.println(player_2_id);
//    System.out.println(player_3_id);
//    System.out.println(player_4_id);
//
//    System.out.println(gameInfo.getNextPlayerId());
//  }
//
//  void stage_4_PlayCombinationFirstMoveInvalid() {
//    List<Tile> combination =
//            new ArrayList<>(
//                    Arrays.asList(
//                            new Tile(Number.ONE, Color.YELLOW),
//                            new Tile(Number.TWO, Color.YELLOW),
//                            new Tile(Number.THREE, Color.YELLOW),
//                            new Tile(Number.FOUR, Color.YELLOW),
//                            new Tile(Number.FIVE, Color.YELLOW)));
//
//    assert !gameInfo.play(combination, player_2_id).get();
//  }
//
//  void stage_5_PlayCombinationValid() {
//    List<Tile> combination =
//            new ArrayList<>(
//                    Arrays.asList(
//                            new Tile(Number.ONE, Color.YELLOW),
//                            new Tile(Number.TWO, Color.YELLOW),
//                            new Tile(Number.THREE, Color.YELLOW),
//                            new Tile(Number.FOUR, Color.YELLOW),
//                            new Tile(Number.FIVE, Color.YELLOW)));
//
//    assert gameInfo.play(combination, player_1_id).get();
//  }
//
//  void stage_6_PlayCombinationInvalid() {
//    List<Tile> combination =
//            new ArrayList<>(
//                    Arrays.asList(
//                            new Tile(Number.ONE, Color.YELLOW),
//                            new Tile(Number.TWO, Color.BLACK),
//                            new Tile(Number.THREE, Color.YELLOW),
//                            new Tile(Number.FOUR, Color.YELLOW)));
//
//    assert !gameInfo.play(combination, player_1_id).get();
//  }
//
//  void stage_7_PlayCombinationWithTilesFromBoardValid() {
//    // TODO
//    // gameInfo.play(List<Tile> tilesFromHand, List<Tile> tilesFromBoard, List<List<Tile>>
//    // newCombinations, String id);
//  }
//
//  void stage_8_PlayCombinationWithTilesFromBoardInvalid() {
//    // TODO
//    // gameInfo.play(List<Tile> tilesFromHand, List<Tile> tilesFromBoard, List<List<Tile>>
//    // newCombinations, String id);
//  }
//
//  void stage_9_GetAllTilesById() {
//    List<Tile> hand = gameInfo.getAllTilesBy(player_1_id).get();
//
//    hand.forEach(tile -> System.out.println("tile." + tile.getNumber() + "/" + tile.getColor()));
//  }
//
//  static void stage_10_DeregisterTest() {
//    gameInfo.deregisterBy(player_1_id);
//    gameInfo.deregisterBy(player_2_id);
//    gameInfo.deregisterBy(player_3_id);
//    gameInfo.deregisterBy(player_4_id);
//
//    assert !gameInfo.getAllPlayerIds().isPresent();
//  }
}
