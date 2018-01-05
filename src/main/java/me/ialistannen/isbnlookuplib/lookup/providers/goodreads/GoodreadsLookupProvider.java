package me.ialistannen.isbnlookuplib.lookup.providers.goodreads;

import me.ialistannen.isbnlookuplib.book.Book;
import me.ialistannen.isbnlookuplib.isbn.Isbn;
import me.ialistannen.isbnlookuplib.lookup.IsbnLookupProvider;
import me.ialistannen.isbnlookuplib.util.Optional;
import org.jsoup.nodes.Document;

/**
 * Looks up books on goodreads.com.
 */
public class GoodreadsLookupProvider implements IsbnLookupProvider {

  private BookSearcher bookSearcher;
  private DetailPageScraper detailPageScraper;

  public GoodreadsLookupProvider() {
    this.bookSearcher = new BookSearcher();
    this.detailPageScraper = new DetailPageScraper();
  }

  @Override
  public Optional<Book> lookup(Isbn isbn) {
    Optional<Document> search = bookSearcher.search(isbn);

    if (!search.isPresent()) {
      return Optional.empty();
    }

    return Optional.ofNullable(detailPageScraper.parseBook(search.get()));
  }
}
