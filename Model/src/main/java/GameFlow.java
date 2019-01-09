import java.util.ArrayList;
import java.util.List;

public class GameFlow {
  private List<String> players;
  private GameState state;
  private int counter;


  public GameFlow() {
    this.players = new ArrayList<>();
    this.state = GameState.distributing;
  }

  public String getPlayerID(int position) {
    return players.get(position);
  }

}
