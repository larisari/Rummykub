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

  public void addCombo(List<Tile> combo) {
    this.combos.add(combo);
  }

  public List<Tile> getCombo(int index) {
    return this.combos.get(index);
  }

}
