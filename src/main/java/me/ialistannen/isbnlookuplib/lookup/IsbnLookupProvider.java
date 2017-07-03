package me.ialistannen.isbnlookuplib.lookup;

import me.ialistannen.isbnlookuplib.book.Book;
import me.ialistannen.isbnlookuplib.isbn.Isbn;
import me.ialistannen.isbnlookuplib.util.Optional;

/**
 * Returns information about a book based on the {@link Isbn}.
 */
public interface IsbnLookupProvider {

  /**
   * Looks up data about a book.
   *
   * @param isbn The {@link Isbn} to lookup
   * @return The {@link Book}, if any found.
   */
  Optional<Book> lookup(Isbn isbn);
}
