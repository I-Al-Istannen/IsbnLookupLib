package me.ialistannen.isbnlookuplib.isbn.factories;

import me.ialistannen.isbnlookuplib.isbn.Isbn;
import me.ialistannen.isbnlookuplib.isbn.IsbnType;
import me.ialistannen.isbnlookuplib.isbn.util.IsbnUtil;
import me.ialistannen.isbnlookuplib.isbn.validators.Isbn10Validator;
import me.ialistannen.isbnlookuplib.util.Optional;

/**
 * An {@link IsbnFactory} for {@link IsbnType#ISBN_10}
 */
public class Isbn10Factory extends IsbnFactory {

  Isbn10Factory(Isbn10Validator validator) {
    super(validator);
  }

  @Override
  public Optional<Isbn> generate(String asString) {
    String isbnString = IsbnUtil.normalizeIsbnString(asString);

    short[] isbnDigits = isbnStringToDigits(isbnString);

    Isbn isbn = new Isbn(isbnDigits, IsbnType.ISBN_10);

    if (!getValidator().isValidIsbn(isbn)) {
      return Optional.empty();
    }

    return Optional.of(isbn);
  }

  private short[] isbnStringToDigits(String isbnString) {
    short[] array = new short[isbnString.length()];
    char[] charArray = isbnString.toCharArray();
    for (int i = 0; i < charArray.length; i++) {
      char c = charArray[i];

      if (Character.isDigit(c)) {
        array[i] = (short) Character.getNumericValue(c);
      } else if (c == 'X') {
        array[i] = 10;
      }
    }
    return array;
  }
}
