package gameinfo;

public class GameInfoFactory {

  public static GameInfo makeFor(int numberOfPlayers) {
    return new GameInfoImpl(numberOfPlayers);
  }

}
