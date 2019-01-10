package gameinfo.board;

public class BFactory {

  private BFactory() {
  }

  public static Board make() {
    return new BoardImpl();
  }

}
