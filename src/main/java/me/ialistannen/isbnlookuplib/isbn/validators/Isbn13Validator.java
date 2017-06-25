package me.ialistannen.isbnlookuplib.isbn.validators;

import me.ialistannen.isbnlookuplib.isbn.Isbn;
import me.ialistannen.isbnlookuplib.isbn.IsbnType;

/**
 * An {@link IsbnValidator} for {@link IsbnType#ISBN_13}.
 */
public class Isbn13Validator implements IsbnValidator {

  @Override
  public boolean isValidIsbn(Isbn isbn) {
    short[] digits = isbn.getDigits();

    int sum = 0;
    for (int i = 1; i <= digits.length; i++) {
      short digit = digits[i - 1];
      sum += digit * getMultiplier(i);
    }

    return sum % 10 == 0;
  }

  private int getMultiplier(int position) {
    // Alternate between 1 and 3
    // 1 is 1, 2 is 3 and so on
    return position % 2 == 1 ? 1 : 3;
  }
}
