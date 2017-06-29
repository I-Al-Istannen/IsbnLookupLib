package me.ialistannen.isbnlookuplib.i18n;

/**
 * Thrown when the language is unknown.
 */
class UnknownLanguageException extends Exception {

  UnknownLanguageException(String message) {
    super(message);
  }
}
