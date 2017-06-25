package me.ialistannen.isbnlookuplib.lookup.providers.amazon;

import java.util.Optional;
import me.ialistannen.isbnlookuplib.book.Book;
import me.ialistannen.isbnlookuplib.isbn.Isbn;
import me.ialistannen.isbnlookuplib.lookup.IsbnLookupProvider;

/**
 * Looks up {@link Book} by {@link Isbn} using Amazon.
 */
public class AmazonIsbnLookupProvider implements IsbnLookupProvider {

  @Override
  public Optional<Book> lookup(Isbn isbn) {
    return Optional.empty();
  }
}
