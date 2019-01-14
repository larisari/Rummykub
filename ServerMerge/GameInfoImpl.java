import java.util.List;

public class GameInfoImpl extends Thread {

  final List<ServerClientCommunication> clientList;

  public GameInfoImpl(List<ServerClientCommunication> clients){
    this.clientList = clients;
  }

/*
  void registerBy(String id);

  void deregisterBy(String id);

  void start(); --> BROADCAST

  Optional<Boolean> isValidPlayerBy(String id);

  Optional<String> getNextPlayerId();

  // TODO ID mit in Rückgabewert packen um richtigen ClientHandler aufzurufen


  // just for test TODO: REMOVE
  Optional<List<String>> getAllPlayerIds();

  Optional<Boolean> play(List<Tile> combination, String id);

  Optional<Boolean> play(List<Tile> tilesFromHand, List<Tile> tilesFromBoard, List<List<Tile>> newCombinations, String id);

  Optional<List<Tile>> drawBy(String id);

  Optional<List<Tile>> getAllTilesBy(String id);

  Optional<Integer> getPointsBy(String id);

  Optional<Integer> getNumberOfPlayers();

  Optional<Boolean> finishedTurnBy(String id); --> BROADCAST
  */


}
