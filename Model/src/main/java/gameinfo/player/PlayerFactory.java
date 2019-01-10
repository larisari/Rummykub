package gameinfo.player;

public class PlayerFactory {

  public static Player make() {
    return new PlayerImpl();
  }

  public static Player makeWith(String id) {
    return new PlayerImpl(id);
  }
}
