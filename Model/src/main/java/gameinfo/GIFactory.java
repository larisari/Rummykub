package gameinfo;

public class GIFactory {

  private GIFactory() {
  }

  public static GameInfo makeFor() {
    return new GameInfoImpl();
  }
}
