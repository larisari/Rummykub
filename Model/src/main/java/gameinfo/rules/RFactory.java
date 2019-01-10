package gameinfo.rules;

public class RFactory {

  private RFactory() {
  }

  public static Rules make() {
    return new RulesImpl();
  }

}
