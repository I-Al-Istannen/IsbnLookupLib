package me.ialistannen.isbnlookuplib.isbn.validators;

import me.ialistannen.isbnlookuplib.isbn.Isbn;

/**
 * A validator for an ISBN.
 */
public interface IsbnValidator {

  /**
   * Checks if an {@link Isbn} is valid.
   *
   * @param isbn The {@link Isbn} to check
   * @return True if the given {@link Isbn} is valid.
   */
  boolean isValidIsbn(Isbn isbn);
}
