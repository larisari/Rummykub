package gameinfo;

import gameinfo.board.Board;
import gameinfo.board.BoardFactory;
import gameinfo.player.Player;
import gameinfo.player.PlayerFactory;
import gameinfo.rules.Rules;
import gameinfo.rules.RulesFactory;
import tile.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class GameInfoImpl implements GameInfo {

  private Board board;
  private Rules rules;
  private List<Player> players;

  GameInfoImpl(int numberOfPlayers) {
    board = BoardFactory.make();
    rules = RulesFactory.make();
    players = new ArrayList<>();

    for (int i = 0; i < numberOfPlayers; i++) {
      players.add(PlayerFactory.make());
    }
  }

  @Override
  public String getNextPlayerID() {
    return rules.getNextPlayerID();
  }

  @Override
  public Optional<List<Tile>> getAllTilesBy(String id) {
    Player player = null;

    for (Player temPlayer : players) {
      if (temPlayer.getId().equals(id)) {
        player = temPlayer;
      }
    }

    if (player != null) {
      return Optional.of(player.getTilesOnHand());
    } else {
      return Optional.empty();
    }
  }

  @Override
  public List<Tile> getStackFromBag() {
    return board.getStackFromBag();
  }

  @Override
  public boolean isValidMove(List<Tile> combination) {
    // TODO !!! if its first move use other method of rules.CombRules with minimum Points !!!
    return rules.isValid(combination);
  }

  @Override
  public Tile getOneTileFromBag() {
    return board.getTileFromBag();
  }

  @Override
  public int getPointsForMove(List<Tile> combination) {
    return rules.getPointsFor(combination);
  }
}
