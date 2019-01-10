package gameinfo.player;

import tile.Tile;
import java.util.List;

public interface Player {

  String getId();

  List<Tile> getTilesOnHand();

  void putTile(Tile tile);

  boolean removeTile(Tile tile);
}
