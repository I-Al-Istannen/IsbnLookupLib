package me.ialistannen.isbnlookuplib.lookup.providers.amazon;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.Locale;
import me.ialistannen.isbnlookuplib.book.Book;
import me.ialistannen.isbnlookuplib.book.StandardBookDataKeys;
import me.ialistannen.isbnlookuplib.isbn.IsbnConverter;
import me.ialistannen.isbnlookuplib.util.Pair;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class DetailPageScraperTest {

  private IsbnConverter isbnConverter = new IsbnConverter();
  private Locale locale = Locale.GERMAN;
  private DetailPageScraper pageScraper = new DetailPageScraper(locale, isbnConverter);

  @Test
  void scrapeFunkeDrachenreiter() {
    Book book = pageScraper
        .scrape("https://www.amazon.de/Drachenreiter-Cornelia-Funke/dp/3791504541");

    assertEquals(book.getData(StandardBookDataKeys.TITLE), "Drachenreiter");
    assertEquals(book.getData(StandardBookDataKeys.LANGUAGE), "Deutsch");
    assertEquals(book.getData(StandardBookDataKeys.PAGE_COUNT), Integer.valueOf(448));
    assertEquals(book.getData(StandardBookDataKeys.PUBLISHER), "Dressler Verlag");
    assertEquals(
        book.getData(StandardBookDataKeys.AUTHORS),
        Collections.singletonList(new Pair<>("Cornelia Funke", "Autor, Illustrator"))
    );
  }

  @Test
  void scrapeHitchhikersGuide() {
    Book book = pageScraper
        .scrape("https://www.amazon.de/Hitchhikers-Guide-Galaxy-Douglas-Adams/dp/0345391802/");

    assertEquals(book.getData(StandardBookDataKeys.TITLE), "The Hitchhiker's Guide to the Galaxy");
    assertEquals(book.getData(StandardBookDataKeys.LANGUAGE), "Englisch");
    assertEquals(book.getData(StandardBookDataKeys.PAGE_COUNT), Integer.valueOf(216));
    assertEquals(book.getData(StandardBookDataKeys.PUBLISHER), "Del Rey");
    assertEquals(
        book.getData(StandardBookDataKeys.AUTHORS),
        Collections.singletonList(new Pair<>("Douglas Adams", "Autor"))
    );
  }

  @Test
  @Disabled
  void scrapeFunkeDrachenreiterWrongVersionSelected() {
    // FIXME: 28.06.17 Crashes due to an Out of memory exception!
    Book book = pageScraper
        .scrape("https://www.amazon.de/Drachenreiter-Cornelia-Funke-ebook/dp/B008PQZU4M");

    assertEquals(book.getData(StandardBookDataKeys.TITLE), "Drachenreiter");
    assertEquals(book.getData(StandardBookDataKeys.LANGUAGE), "Deutsch");
    assertEquals(book.getData(StandardBookDataKeys.PAGE_COUNT), Integer.valueOf(448));
    assertEquals(book.getData(StandardBookDataKeys.PUBLISHER), "Dressler Verlag");
    assertEquals(
        book.getData(StandardBookDataKeys.AUTHORS),
        Collections.singletonList(new Pair<>("Cornelia Funke", "Autor, Illustrator"))
    );
  }

}