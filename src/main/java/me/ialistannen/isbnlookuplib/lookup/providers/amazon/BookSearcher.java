package me.ialistannen.isbnlookuplib.lookup.providers.amazon;

import java.util.Optional;
import me.ialistannen.isbnlookuplib.isbn.Isbn;
import me.ialistannen.isbnlookuplib.util.WebsiteFetcher;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Searches a book's link
 */
class BookSearcher {

  private static final String URL_FORMAT = "https://www.amazon.de/s/field-keywords=%s";

  /**
   * Returns the URL for a book.
   *
   * @param isbn The {@link Isbn} of the book
   * @return The String to the detail page, if found
   */
  public Optional<String> getBookUrl(Isbn isbn) {
    String url = String.format(URL_FORMAT, isbn.getDigitsAsString());
    Document document = WebsiteFetcher.getWebsite(url);

    if (document == null) {
      return Optional.empty();
    }

    Element firstResult = document.getElementById("result_0");

    if (firstResult == null) {
      return Optional.empty();
    }

    Elements detailPageLink = firstResult.getElementsByClass("s-access-detail-page");

    if (detailPageLink.isEmpty()) {
      return Optional.empty();
    }

    return Optional.ofNullable(detailPageLink.get(0).absUrl("href"));
  }
}
