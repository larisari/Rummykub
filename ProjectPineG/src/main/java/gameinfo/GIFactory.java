package gameinfo;

import java.util.Map;

/**
 * Use this class to create an object of the GIGameInfo. Different implementations of the GIGameInfo
 * Interface could be put behind the make() method.
 */
public class GIFactory {

  private GIFactory() {}

  public static GIGameInfo make(Map<String, String> infos) {
    return new GameInfoImpl();
  }
}
