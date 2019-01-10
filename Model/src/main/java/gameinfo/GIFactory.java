package gameinfo;

public class GIFactory {

  private GIFactory() {
  }

  public static GameInfo makeFor(int numberOfPlayers) {
    return new GameInfoImpl(numberOfPlayers);
  }
}
