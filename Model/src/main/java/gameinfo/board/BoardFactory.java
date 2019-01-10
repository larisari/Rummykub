package gameinfo.board;

public class BoardFactory {

  public static Board make() {
    return new BoardImpl();
  }

}
