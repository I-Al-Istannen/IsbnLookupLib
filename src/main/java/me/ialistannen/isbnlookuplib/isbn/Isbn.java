package me.ialistannen.isbnlookuplib.isbn;

import java.util.Arrays;

/**
 * An ISBN number.
 *
 * <em>The existence of this class does not imply the ISBN is valid. No checks are done.</em>
 */
public class Isbn {

  private short[] digits;
  private IsbnType type;

  /**
   * @param digits The digits of the isbn
   * @param type The type of the isbn
   */
  public Isbn(short[] digits, IsbnType type) {
    this.digits = digits;
    this.type = type;
  }

  /**
   * @return All digits in the {@link Isbn}
   */
  public short[] getDigits() {
    return Arrays.copyOf(digits, digits.length);
  }

  /**
   * @return All digits in the {@link Isbn} as a String
   */
  public String getDigitsAsString() {
    StringBuilder builder = new StringBuilder();
    for (short digit : digits) {
      builder.append(digit);
    }
    return builder.toString();
  }

  /**
   * @return The type of the {@link Isbn}.
   */
  public IsbnType getType() {
    return type;
  }

  @Override
  public String toString() {
    return "Isbn{"
        + "digits=" + Arrays.toString(digits)
        + '}';
  }
}
