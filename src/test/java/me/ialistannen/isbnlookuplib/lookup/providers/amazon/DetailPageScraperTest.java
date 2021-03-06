package me.ialistannen.isbnlookuplib.lookup.providers.amazon;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.Locale;
import me.ialistannen.isbnlookuplib.book.Book;
import me.ialistannen.isbnlookuplib.book.StandardBookDataKeys;
import me.ialistannen.isbnlookuplib.isbn.IsbnConverter;
import me.ialistannen.isbnlookuplib.util.Pair;
import org.junit.jupiter.api.Test;

class DetailPageScraperTest {

  private IsbnConverter isbnConverter = new IsbnConverter();
  private Locale locale = Locale.GERMAN;
  private DetailPageScraper pageScraper = new DetailPageScraper(locale, isbnConverter);

  @Test
  void scrapeFunkeDrachenreiter() {
    Book book = pageScraper
        .scrape("https://www.amazon.de/Drachenreiter-Cornelia-Funke/dp/3791504541");

    assertEquals("Drachenreiter", book.getData(StandardBookDataKeys.TITLE));
    assertEquals("Deutsch", book.getData(StandardBookDataKeys.LANGUAGE));
    assertEquals(Integer.valueOf(448), book.getData(StandardBookDataKeys.PAGE_COUNT));
    assertEquals("Dressler Verlag", book.getData(StandardBookDataKeys.PUBLISHER));
    assertEquals(
        Collections.singletonList(new Pair<>("Cornelia Funke", "Autor, Illustrator")),
        book.getData(StandardBookDataKeys.AUTHORS)
    );
  }

  @Test
  void scrapeHitchhikersGuide() {
    Book book = pageScraper
        .scrape("https://www.amazon.de/Hitchhikers-Guide-Galaxy-Douglas-Adams/dp/0345391802/");

    assertEquals("The Hitchhiker's Guide to the Galaxy", book.getData(StandardBookDataKeys.TITLE));
    assertEquals("Englisch", book.getData(StandardBookDataKeys.LANGUAGE));
    assertEquals(Integer.valueOf(216), book.getData(StandardBookDataKeys.PAGE_COUNT));
    assertEquals("Del Rey", book.getData(StandardBookDataKeys.PUBLISHER));
    assertEquals(
        Collections.singletonList(new Pair<>("Douglas Adams", "Autor")),
        book.getData(StandardBookDataKeys.AUTHORS)
    );
  }

  @Test
  void scrapeFunkeDrachenreiterWrongVersionSelected() {
    Book book = pageScraper
        .scrape(
            "https://www.amazon.de/Drachenreiter-Cornelia-Funke-ebook/dp/B008PQZU4M",
            "Hardcover"
        );

    assertEquals("Drachenreiter", book.getData(StandardBookDataKeys.TITLE));
    assertEquals("Deutsch", book.getData(StandardBookDataKeys.LANGUAGE));
    assertEquals(Integer.valueOf(448), book.getData(StandardBookDataKeys.PAGE_COUNT));
    assertEquals("Dressler Verlag", book.getData(StandardBookDataKeys.PUBLISHER));
    assertEquals(
        Collections.singletonList(new Pair<>("Cornelia Funke", "Autor, Illustrator")),
        book.getData(StandardBookDataKeys.AUTHORS)
    );
  }
}