import java.util.ArrayList;
import java.util.List;


public class BoardImpl {
  public Bag bag;
  public List<List<Tile>> combos;

  public BoardImpl() {
    this.bag = new Bag(); //filled Bag
    this.combos = new ArrayList<>(); //empty List of Combos
  }

  public List<List<Tile>> getActiveCombos() {
    return this.combos;
  }

  /*
  -----add combo to playedTiles
  the combination is wished by the client, prepared by the server and
  then passed to board
  */
  public void addCombo(List<Tile> combo) {
    this.combos.add(combo);
  }

  //rearrange combo

  //check for valid combo -> SERVER -> <CLASS> CombRules
  //e.g. method to check by sending request to server and parse answer?



}
