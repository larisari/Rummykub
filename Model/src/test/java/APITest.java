import gameinfo.GIFactory;
import gameinfo.GIGameInfo;
import org.junit.jupiter.api.Test;
import gameinfo.tile.Tile;
import gameinfo.tile.util.Color;
import gameinfo.tile.util.Number;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

class APITest {

  private static GIGameInfo gameInfo;

  private static String player_1_id = UUID.randomUUID().toString();
  private static String player_2_id = UUID.randomUUID().toString();
  private static String player_3_id = UUID.randomUUID().toString();
  private static String player_4_id = UUID.randomUUID().toString();

  @Test
  void runTestSuit() {
    setupGameInfo();
    stage_1_RegisterTest();
    stage_2_GetNextPlayerId();
    stage_3_PlayCombinationFirstMoveValid();
    stage_4_PlayCombinationFirstMoveInvalid();
    stage_5_PlayCombinationValid();
    stage_6_PlayCombinationInvalid();
    stage_7_PlayCombinationWithTilesFromBoardValid();
    stage_8_PlayCombinationWithTilesFromBoardInvalid();
    stage_9_GetAllTilesById();
    stage_10_DeregisterTest();
  }

  static void setupGameInfo() {
    gameInfo = GIFactory.make();

    gameInfo.registerBy(player_1_id);
    gameInfo.registerBy(player_2_id);
    gameInfo.registerBy(player_3_id);
    gameInfo.registerBy(player_4_id);
  }

  void stage_1_RegisterTest() {
    List<String> ids = gameInfo.getAllPlayerIds();

    for (String id : ids) {
      assert id.equals(player_1_id)
              || id.equals(player_2_id)
              || id.equals(player_3_id)
              || id.equals(player_4_id);
    }
  }

  void stage_3_PlayCombinationFirstMoveValid() {
    List<Tile> combination =
            new ArrayList<>(
                    Arrays.asList(
                            new Tile(Number.TEN, Color.YELLOW),
                            new Tile(Number.ELEVEN, Color.YELLOW),
                            new Tile(Number.TWELVE, Color.YELLOW),
                            new Tile(Number.THIRTEEN, Color.YELLOW),
                            new Tile(Number.ONE, Color.YELLOW)));

    assert gameInfo.play(combination, player_1_id);
    assert gameInfo.getPointsForMove(combination) == 47;
  }

  void stage_2_GetNextPlayerId() {
    // TODO Implement !!!
    System.out.println(gameInfo.getNextPlayerId());
  }

  void stage_4_PlayCombinationFirstMoveInvalid() {
    List<Tile> combination =
            new ArrayList<>(
                    Arrays.asList(
                            new Tile(Number.ONE, Color.YELLOW),
                            new Tile(Number.TWO, Color.YELLOW),
                            new Tile(Number.THREE, Color.YELLOW),
                            new Tile(Number.FOUR, Color.YELLOW),
                            new Tile(Number.FIVE, Color.YELLOW)));

    assert !gameInfo.play(combination, player_2_id);
    assert gameInfo.getPointsForMove(combination) == 15;
  }

  void stage_5_PlayCombinationValid() {
    List<Tile> combination =
            new ArrayList<>(
                    Arrays.asList(
                            new Tile(Number.ONE, Color.YELLOW),
                            new Tile(Number.TWO, Color.YELLOW),
                            new Tile(Number.THREE, Color.YELLOW),
                            new Tile(Number.FOUR, Color.YELLOW),
                            new Tile(Number.FIVE, Color.YELLOW)));

    assert gameInfo.play(combination, player_1_id);
    assert gameInfo.getPointsForMove(combination) == 15;
  }

  void stage_6_PlayCombinationInvalid() {
    List<Tile> combination =
            new ArrayList<>(
                    Arrays.asList(
                            new Tile(Number.ONE, Color.YELLOW),
                            new Tile(Number.TWO, Color.BLACK),
                            new Tile(Number.THREE, Color.YELLOW),
                            new Tile(Number.FOUR, Color.YELLOW)));

    assert !gameInfo.play(combination, player_1_id);
  }

  void stage_7_PlayCombinationWithTilesFromBoardValid() {
    // TODO
    // gameInfo.play(List<Tile> tilesFromHand, List<Tile> tilesFromBoard, List<List<Tile>>
    // newCombinations, String id);
  }

  void stage_8_PlayCombinationWithTilesFromBoardInvalid() {
    // TODO
    // gameInfo.play(List<Tile> tilesFromHand, List<Tile> tilesFromBoard, List<List<Tile>>
    // newCombinations, String id);
  }

  void stage_9_GetAllTilesById() {
    List<Tile> hand = gameInfo.getAllTilesBy(player_1_id).get();

    hand.forEach(tile -> System.out.println(tile));
  }

  static void stage_10_DeregisterTest() {
    gameInfo.deregisterBy(player_1_id);
    gameInfo.deregisterBy(player_2_id);
    gameInfo.deregisterBy(player_3_id);
    gameInfo.deregisterBy(player_4_id);

    assert gameInfo.getAllPlayerIds().size() == 0;
  }
}
