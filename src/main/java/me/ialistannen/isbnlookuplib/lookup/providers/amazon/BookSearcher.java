package me.ialistannen.isbnlookuplib.lookup.providers.amazon;

import java.util.Locale;
import java.util.Optional;
import me.ialistannen.isbnlookuplib.i18n.DefaultCategories;
import me.ialistannen.isbnlookuplib.i18n.Language;
import me.ialistannen.isbnlookuplib.isbn.Isbn;
import me.ialistannen.isbnlookuplib.util.WebsiteFetcher;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Searches a book's link
 */
class BookSearcher {

  /**
   * Returns the URL for a book.
   *
   * @param isbn The {@link Isbn} of the book
   * @return The String to the detail page, if found
   */
  Optional<String> getBookUrl(Isbn isbn, Locale locale) {
    Optional<Language> languageOptional = DefaultCategories.AMAZON_SCRAPER.getCategory()
        .getLanguage(locale);

    if (!languageOptional.isPresent()) {
      return Optional.empty();
    }

    Language language = languageOptional.get();
    if (!language.hasKey("amazon_search_url_format")) {
      return Optional.empty();
    }

    String url = String.format(
        language.translate("amazon_search_url_format"),
        isbn.getDigitsAsString()
    );
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
