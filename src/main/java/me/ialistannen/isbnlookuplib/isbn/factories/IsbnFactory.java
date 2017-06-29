package me.ialistannen.isbnlookuplib.isbn.factories;

import java.util.Optional;
import me.ialistannen.isbnlookuplib.isbn.Isbn;
import me.ialistannen.isbnlookuplib.isbn.validators.IsbnValidator;

/**
 * A class that generates {@link Isbn}'s from a String.
 */
public abstract class IsbnFactory {

  private IsbnValidator validator;

  /**
   * @param validator The {@link IsbnValidator} to use
   */
  IsbnFactory(IsbnValidator validator) {
    this.validator = validator;
  }

  /**
   * Converts an ISBN String to an {@link Isbn}.
   *
   * @param asString The string representation of the Isbn.
   * @return The {@link Isbn}, if any.
   */
  public abstract Optional<Isbn> generate(String asString);

  /**
   * @return The {@link IsbnValidator}
   */
  IsbnValidator getValidator() {
    return validator;
  }
}
