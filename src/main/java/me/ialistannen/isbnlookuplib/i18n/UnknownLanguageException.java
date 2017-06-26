package me.ialistannen.isbnlookuplib.i18n;

/**
 * Thrown when the language is unknown.
 */
public class UnknownLanguageException extends Exception {

  public UnknownLanguageException(String message) {
    super(message);
  }
}
