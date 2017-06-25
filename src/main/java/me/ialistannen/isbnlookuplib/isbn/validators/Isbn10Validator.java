package me.ialistannen.isbnlookuplib.isbn.validators;

import me.ialistannen.isbnlookuplib.isbn.Isbn;
import me.ialistannen.isbnlookuplib.isbn.IsbnType;

/**
 * Validates {@link IsbnType#ISBN_10} {@link Isbn}'s
 */
public class Isbn10Validator implements IsbnValidator {

  @Override
  public boolean isValidIsbn(Isbn isbn) {
    short[] digits = isbn.getDigits();

    if (digits.length != 10) {
      return false;
    }

    int result = 0;
    for (int i = 1; i <= 10; i++) {
      // iterate in reverse order. The rightmost digit is 1, the leftmost 10
      int digit = digits[digits.length - i];

      // sum up all digits and multiply them with their position (10 left, 1 right)
      result += digit * i;
    }

    return result % 11 == 0;
  }
}
