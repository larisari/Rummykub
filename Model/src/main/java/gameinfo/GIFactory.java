package gameinfo;

public class GIFactory {

  private GIFactory() {
  }

  public static GIGameInfo makeFor() {
    return new GameInfoImpl();
  }
}
