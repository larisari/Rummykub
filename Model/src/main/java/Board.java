import java.util.List;

public interface Board {

  public List<List<Tile>> getActiveCombos();

  public void addCombo(List<Tile> combo);

  public List<Tile> getCombo(int index);

}
