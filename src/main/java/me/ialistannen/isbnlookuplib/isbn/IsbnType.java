package me.ialistannen.isbnlookuplib.isbn;

/**
 * The type of the ISBN.
 */
public enum IsbnType {
  ISBN_10(10),
  ISBN_13(13);

  private final int digitCount;

  IsbnType(int digitCount) {
    this.digitCount = digitCount;
  }

  /**
   * @return The amount of digits in this {@link IsbnType}.
   */
  public int getDigitCount() {
    return digitCount;
  }
}
