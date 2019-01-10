package gameinfo.rules;

import tile.Tile;

import java.util.List;

public interface Rules {

  String getNextPlayerID();

  boolean isValid(List<Tile> combination);

  boolean isValid(List<Tile> combination, int minimumPoints);

  int getPointsFor(List<Tile> combination);
}
