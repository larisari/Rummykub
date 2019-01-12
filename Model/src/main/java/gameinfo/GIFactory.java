package gameinfo;

public class GIFactory {

  private GIFactory() {
  }

  public static GIGameInfo make() {
    return new GameInfoImpl();
  }
}
