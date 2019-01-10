package gameinfo.player;

public class PFactory {

  private PFactory() {
  }

  public static Player make() {
    return new PlayerImpl();
  }

  public static Player makeWith(String id) {
    return new PlayerImpl(id);
  }
}
