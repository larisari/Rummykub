public enum Number {
  ONE,
  TWO,
  THREE,
  FOUR,
  FIVE,
  SIX,
  SEVEN,
  EIGHT,
  NINE,
  TEN,
  ELEVEN,
  TWELVE,
  THIRTEEN,

  JOKER;

  public Number next() {
    switch (this) {
      case ONE:
        return TWO;
      case TWO:
        return THREE;
      case THREE:
        return FOUR;
      case FOUR:
        return FIVE;
      case FIVE:
        return SIX;
      case SIX:
        return SEVEN;
      case SEVEN:
        return EIGHT;
      case EIGHT:
        return NINE;
      case NINE:
        return TEN;
      case TEN:
        return ELEVEN;
      case ELEVEN:
        return TWELVE;
      case TWELVE:
        return THIRTEEN;
      case THIRTEEN:
        return ONE;
      case JOKER:
        // TODO !!!
        return JOKER;
      default:
        throw new IllegalArgumentException("Not a valid Number.");
    }
  }

  public int value() {
    switch (this) {
      case ONE:
        return 1;
      case TWO:
        return 2;
      case THREE:
        return 3;
      case FOUR:
        return 4;
      case FIVE:
        return 5;
      case SIX:
        return 6;
      case SEVEN:
        return 7;
      case EIGHT:
        return 8;
      case NINE:
        return 9;
      case TEN:
        return 10;
      case ELEVEN:
        return 11;
      case TWELVE:
        return 12;
      case THIRTEEN:
        return 13;
      case JOKER:
        // TODO !!!
        return 0;
      default:
        throw new IllegalArgumentException("Not a valid Number.");
    }
  }
}
