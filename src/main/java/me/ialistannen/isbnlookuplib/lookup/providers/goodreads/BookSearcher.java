package me.ialistannen.isbnlookuplib.lookup.providers.goodreads;

import me.ialistannen.isbnlookuplib.isbn.Isbn;
import me.ialistannen.isbnlookuplib.util.Optional;
import me.ialistannen.isbnlookuplib.util.WebsiteFetcher;
import org.jsoup.nodes.Document;

/**
 * Searches a book on goodreads.
 */
class BookSearcher {

  private static final String URL_PATTERN = "https://www.goodreads.com/search?&query=%s";

  /**
   * Returns the detail page for a book on goodreads, if found.
   *
   * @param isbn the {@link Isbn} to lookup
   * @return the detail page if found
   */
  public Optional<Document> search(Isbn isbn) {
    String url = String.format(URL_PATTERN, isbn.getDigitsAsString());

    Document website = WebsiteFetcher.getWebsite(url);
    assert website != null;

    if (!website.location().contains("search")) {
      return Optional.of(website);
    }

    return Optional.empty();
  }
}
