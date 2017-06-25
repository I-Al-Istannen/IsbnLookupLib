package me.ialistannen.isbnlookuplib.isbn.util;

/**
 * Some static utility functions to help with dealing with ISBNs.
 */
public class IsbnUtil {


  /**
   * Converts an ISBN input string to a digit-only representation.
   *
   * @param isbn The ISBN to normalize.
   * @return The normalized ISBN string
   */
  public static String normalizeIsbnString(String isbn) {
    return isbn.replaceAll("[^\\dXx]", "").replace('x', 'X');
  }
}
