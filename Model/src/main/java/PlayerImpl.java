import java.util.List;
import java.util.UUID;

public class PlayerImpl implements Player {

  private Hand hand;
  private String id;

  public PlayerImpl() {
    hand = new Hand();
    id = UUID.randomUUID().toString();
  }

  public PlayerImpl(String id) {
    this.hand = new Hand();
    this.id = id;
  }

  public List<Tile> getTilesOnHand() {
    return hand.getTilesOnHand();
  }

  public String getId() {
    return id;
  }

  public void putTile(Tile tile) {
    hand.putTile(tile);
  }

  public boolean removeTile(Tile tile) {
    return hand.removeTile(tile);
  }
}
