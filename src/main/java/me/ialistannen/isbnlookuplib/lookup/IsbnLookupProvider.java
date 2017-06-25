package me.ialistannen.isbnlookuplib.lookup;

import java.util.Optional;
import me.ialistannen.isbnlookuplib.book.Book;
import me.ialistannen.isbnlookuplib.isbn.Isbn;

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
