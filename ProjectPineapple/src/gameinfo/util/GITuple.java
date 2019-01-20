package gameinfo.util;

public class GITuple<F, S> {
  private F first;
  private S second;

  public GITuple(F first, S second) {
    this.first = first;
    this.second = second;
  }

  public F getFirst() {
    return first;
  }

  public S getSecond() {
    return second;
  }

  @Override
  public String toString() {
    return "(" + getFirst().toString() + ";" + getSecond().toString() + ")";
  }
}
