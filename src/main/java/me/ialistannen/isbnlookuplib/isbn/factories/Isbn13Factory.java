package me.ialistannen.isbnlookuplib.isbn.factories;

import java.util.Optional;
import me.ialistannen.isbnlookuplib.isbn.Isbn;
import me.ialistannen.isbnlookuplib.isbn.IsbnType;
import me.ialistannen.isbnlookuplib.isbn.util.IsbnUtil;
import me.ialistannen.isbnlookuplib.isbn.validators.Isbn13Validator;

/**
 * An {@link IsbnFactory} for {@link IsbnType#ISBN_13}.
 */
public class Isbn13Factory extends IsbnFactory {

  /**
   * @param validator The {@link Isbn13Validator} to use
   */
  Isbn13Factory(Isbn13Validator validator) {
    super(validator);
  }

  @Override
  public Optional<Isbn> generate(String asString) {
    String isbnString = IsbnUtil.normalizeIsbnString(asString);

    Isbn isbn = getIsbn(isbnString);

    if (getValidator().isValidIsbn(isbn)) {
      return Optional.of(isbn);
    }

    return Optional.empty();
  }

  private Isbn getIsbn(String isbn) {
    short[] digits = new short[isbn.length()];

    char[] charArray = isbn.toCharArray();
    for (int i = 0; i < charArray.length; i++) {
      char c = charArray[i];

      digits[i] = (short) Character.getNumericValue(c);
    }

    return new Isbn(digits, IsbnType.ISBN_13);
  }
}
