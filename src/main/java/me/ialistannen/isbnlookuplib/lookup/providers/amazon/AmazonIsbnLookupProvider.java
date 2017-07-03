package me.ialistannen.isbnlookuplib.lookup.providers.amazon;

import java.util.Locale;
import me.ialistannen.isbnlookuplib.book.Book;
import me.ialistannen.isbnlookuplib.isbn.Isbn;
import me.ialistannen.isbnlookuplib.isbn.IsbnConverter;
import me.ialistannen.isbnlookuplib.lookup.IsbnLookupProvider;
import me.ialistannen.isbnlookuplib.util.Optional;

/**
 * Looks up {@link Book} by {@link Isbn} using Amazon.
 */
@SuppressWarnings("unused")
public class AmazonIsbnLookupProvider implements IsbnLookupProvider {

  private Locale locale;
  private BookSearcher bookSearcher;
  private DetailPageScraper detailPageScraper;

  /**
   * Creates a new {@link IsbnLookupProvider} using Amazon.
   *
   * @param locale The {@link Locale} to use
   * @param converter The {@link IsbnConverter} to use
   * @throws IllegalArgumentException if the locale was not found
   */
  public AmazonIsbnLookupProvider(Locale locale, IsbnConverter converter) {
    this.locale = locale;
    this.bookSearcher = new BookSearcher();
    this.detailPageScraper = new DetailPageScraper(locale, converter);
  }

  @Override
  public Optional<Book> lookup(Isbn isbn) {
    Optional<String> bookUrl = bookSearcher.getBookUrl(isbn, locale);
    if (!bookUrl.isPresent()) {
      return Optional.empty();
    }

    Book scrapedBook = detailPageScraper.scrape(bookUrl.get());
    if (scrapedBook.getAllData().isEmpty()) {
      return Optional.empty();
    }

    return Optional.of(scrapedBook);
  }
}
