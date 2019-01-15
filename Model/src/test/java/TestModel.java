//import gameinfo.GIFactory;
//import gameinfo.GIGameInfo;
//import gameinfo.util.GIColor;
//import gameinfo.util.GINumber;
//import gameinfo.util.GITile;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//class TestModel {
//
//  private static GIGameInfo gameInfo;
//
//  private static String player_1_id = UUID.randomUUID().toString();
//  private static String player_2_id = UUID.randomUUID().toString();
//  private static String player_3_id = UUID.randomUUID().toString();
//  private static String player_4_id = UUID.randomUUID().toString();
//
//  @BeforeAll
//  static void setupGameInfo() {
//    gameInfo = GIFactory.make();
//
//    gameInfo.registerBy(player_1_id);
//    gameInfo.registerBy(player_2_id);
//    gameInfo.registerBy(player_3_id);
//    gameInfo.registerBy(player_4_id);
//
//    //valid player
//    assert gameInfo.isValidPlayerBy(player_1_id).get();
//
//    //next player
//    assert gameInfo.getNextPlayerId().get().equals(player_2_id);
//  }
//
//
//  @Test
//  void testRegistration() {
//    List<String> ids = gameInfo.getAllPlayerIds().get();
//
//    for (String id : ids) {
//      assert id.equals(player_1_id) || id.equals(player_2_id) || id.equals(player_3_id) || id.equals(player_4_id);
//    }
//    System.out.println(ids);
//  }
//
//  @Test
//  void testDeregistration() {
//    List<String> ids = gameInfo.getAllPlayerIds().get();
//
//    for (String id : ids) {
//      assert id.equals(player_1_id) || id.equals(player_2_id) || id.equals(player_3_id) || id.equals(player_4_id);
//    }
//
//    System.out.println(ids);
//
//    gameInfo.deregisterBy(player_1_id);
//
//    System.out.println(gameInfo.getAllPlayerIds().get());
//
//    System.out.println(gameInfo.getNumberOfPlayers().get());
//  }
//
//  // done done
//  @Test
//  void playFirstMoveWrongComb() {
//    List<GITile> combination = new ArrayList<>();
//    combination.add(new GITile(GINumber.TEN, GIColor.BLUE));
//    combination.add(new GITile(GINumber.TEN, GIColor.BLACK));
//    combination.add(new GITile(GINumber.ELEVEN, GIColor.YELLOW));
//
//    assert !gameInfo.play(combination, player_1_id).get();
//  }
//
//  // done done
//  @Test
//  void playFirstMoveValid() {
//    List<GITile> combination = new ArrayList<>();
//    combination.add(new GITile(GINumber.TEN, GIColor.BLUE));
//    combination.add(new GITile(GINumber.TEN, GIColor.BLACK));
//    combination.add(new GITile(GINumber.TEN, GIColor.YELLOW));
//
//    assert gameInfo.play(combination, player_2_id).get();
//  }
//
//  // done done
//  @Test
//  void playFirstMoveWithJoker() {
//    List<GITile> combination = new ArrayList<>();
//    combination.add(new GITile(GINumber.JOKER, GIColor.JOKER));
//    combination.add(new GITile(GINumber.TEN, GIColor.BLUE));
//    combination.add(new GITile(GINumber.TEN, GIColor.BLACK));
//    combination.add(new GITile(GINumber.TEN, GIColor.YELLOW));
//
//    assert gameInfo.play(combination, player_3_id).get();
//  }
//
//  // done done
//  @Test
//  void playFirstMoveNotEnoughPoints() {
//    List<GITile> combination = new ArrayList<>();
//    combination.add(new GITile(GINumber.NINE, GIColor.BLUE));
//    combination.add(new GITile(GINumber.NINE, GIColor.BLACK));
//    combination.add(new GITile(GINumber.NINE, GIColor.YELLOW));
//
//    assert !gameInfo.play(combination, player_4_id).get();
//  }
//
//  // done done
//  @Test
//  void playStreetOneToFive() {
//    List<GITile> combination = new ArrayList<>();
//    combination.add(new GITile(GINumber.ONE, GIColor.BLUE));
//    combination.add(new GITile(GINumber.TWO, GIColor.BLUE));
//    combination.add(new GITile(GINumber.THREE, GIColor.BLUE));
//    combination.add(new GITile(GINumber.FOUR, GIColor.BLUE));
//    combination.add(new GITile(GINumber.FIVE, GIColor.BLUE));
//
//    assert gameInfo.play(combination, player_2_id).get();
//  }
//
//  // done done
//  @Test
//  void playStreetTenToOne() {
//    List<GITile> combination = new ArrayList<>();
//    combination.add(new GITile(GINumber.TEN, GIColor.BLUE));
//    combination.add(new GITile(GINumber.ELEVEN, GIColor.BLUE));
//    combination.add(new GITile(GINumber.TWELVE, GIColor.BLUE));
//    combination.add(new GITile(GINumber.THIRTEEN, GIColor.BLUE));
//    combination.add(new GITile(GINumber.ONE, GIColor.BLUE));
//
//    assert gameInfo.play(combination, player_2_id).get();
//  }
//
//  // done done
//  @Test
//  void playGroupTens() {
//    List<GITile> combination = new ArrayList<>();
//    combination.add(new GITile(GINumber.TEN, GIColor.BLUE));
//    combination.add(new GITile(GINumber.TEN, GIColor.BLACK));
//    combination.add(new GITile(GINumber.TEN, GIColor.YELLOW));
//    combination.add(new GITile(GINumber.TEN, GIColor.RED));
//
//    assert gameInfo.play(combination, player_2_id).get();
//  }
//
//  // done done
//  @Test
//  void playGroupWithJokerNotFirst() {
//    List<GITile> combination = new ArrayList<>();
//    combination.add(new GITile(GINumber.TEN, GIColor.BLUE));
//    combination.add(new GITile(GINumber.JOKER, GIColor.JOKER));
//    combination.add(new GITile(GINumber.TEN, GIColor.BLACK));
//    combination.add(new GITile(GINumber.TEN, GIColor.YELLOW));
//
//    assert gameInfo.play(combination, player_2_id).get();
//  }
//
//  // done done
//  @Test
//  void playStreetOneToFiveJoker() {
//    List<GITile> combination = new ArrayList<>();
//    combination.add(new GITile(GINumber.JOKER, GIColor.JOKER));
//    combination.add(new GITile(GINumber.TWO, GIColor.BLUE));
//    combination.add(new GITile(GINumber.THREE, GIColor.BLUE));
//    combination.add(new GITile(GINumber.FOUR, GIColor.BLUE));
//    combination.add(new GITile(GINumber.FIVE, GIColor.BLUE));
//
//    assert gameInfo.play(combination, player_2_id).get();
//  }
//
//  @Test
//  void testStreetOneToFiveJOKERNotAtFirst() {
//    List<GITile> combination = new ArrayList<>();
//    combination.add(new GITile(GINumber.ONE, GIColor.BLUE));
//    combination.add(new GITile(GINumber.TWO, GIColor.BLUE));
//    combination.add(new GITile(GINumber.JOKER, GIColor.JOKER));
//    combination.add(new GITile(GINumber.FOUR, GIColor.BLUE));
//    combination.add(new GITile(GINumber.FIVE, GIColor.BLUE));
//
//    assert gameInfo.play(combination, player_2_id).get();
//  }
//
//  @Test
//  void testStreetTenToOneJOKER() {
//    List<GITile> combination = new ArrayList<>();
//    combination.add(new GITile(GINumber.JOKER, GIColor.JOKER));
//    combination.add(new GITile(GINumber.ELEVEN, GIColor.BLUE));
//    combination.add(new GITile(GINumber.TWELVE, GIColor.BLUE));
//    combination.add(new GITile(GINumber.THIRTEEN, GIColor.BLUE));
//    combination.add(new GITile(GINumber.ONE, GIColor.BLUE));
//
//    assert gameInfo.play(combination, player_2_id).get();
//
//  }
//
//  @Test
//  void testStreetTenToOneJOKERNotAtFirst() {
//    List<GITile> combination = new ArrayList<>();
//    combination.add(new GITile(GINumber.TEN, GIColor.BLUE));
//    combination.add(new GITile(GINumber.ELEVEN, GIColor.BLUE));
//    combination.add(new GITile(GINumber.TWELVE, GIColor.BLUE));
//    combination.add(new GITile(GINumber.JOKER, GIColor.JOKER));
//    combination.add(new GITile(GINumber.ONE, GIColor.BLUE));
//
//    assert gameInfo.play(combination, player_2_id).get();
//  }
//
//  @Test
//  void getTileTest() {
//    gameInfo.start();
//    //everyone gets 14 tiles
//    System.out.println(gameInfo.drawBy(player_1_id));
//    System.out.println(gameInfo.drawBy(player_2_id));
//    System.out.println(gameInfo.drawBy(player_3_id));
//    System.out.println(gameInfo.drawBy(player_4_id));
//    //now everyone should get 1 util at a time
//    System.out.println(gameInfo.drawBy(player_1_id));
//    System.out.println(gameInfo.drawBy(player_2_id));
//    System.out.println(gameInfo.drawBy(player_3_id));
//    System.out.println(gameInfo.drawBy(player_4_id));
//  }
//
//}
