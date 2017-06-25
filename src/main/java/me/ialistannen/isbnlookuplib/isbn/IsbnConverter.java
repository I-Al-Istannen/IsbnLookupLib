package me.ialistannen.isbnlookuplib.isbn;

import java.util.Optional;
import me.ialistannen.isbnlookuplib.isbn.factories.IsbnFactories;
import me.ialistannen.isbnlookuplib.isbn.factories.IsbnFactory;
import me.ialistannen.isbnlookuplib.isbn.util.IsbnUtil;
import me.ialistannen.isbnlookuplib.isbn.validators.IsbnValidators;

/**
 * A class to convert a String ISBN to an {@link Isbn}.
 */
public class IsbnConverter {

  private IsbnValidators validators;
  private IsbnFactories factories;

  public IsbnConverter() {
    validators = new IsbnValidators();
    factories = new IsbnFactories(this);
  }

  /**
   * @return The registered {@link IsbnValidators}.
   */
  public IsbnValidators getValidators() {
    return validators;
  }

  /**
   * Tries to convert a String to a valid ISBN.
   *
   * @param input The ISBN as a string
   * @return The {@link Isbn}, if any {@link IsbnFactory} for it was found.
   */
  public Optional<Isbn> fromString(String input) {
    String isbnString = IsbnUtil.normalizeIsbnString(input);

    for (IsbnType isbnType : factories.getAllFactories().keySet()) {
      if (isbnType.getDigitCount() == isbnString.length()) {
        return factories.getFactory(isbnType).generate(isbnString);
      }
    }

    return Optional.empty();
  }

  public static void main(String[] args) {
    String isbnString = "0-306-40615-2";
    IsbnConverter converter = new IsbnConverter();
    System.out.println(converter.fromString(isbnString));
  }
}
