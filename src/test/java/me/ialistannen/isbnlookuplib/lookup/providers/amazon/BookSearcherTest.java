package me.ialistannen.isbnlookuplib.lookup.providers.amazon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import me.ialistannen.isbnlookuplib.isbn.Isbn;
import me.ialistannen.isbnlookuplib.isbn.IsbnType;
import org.junit.jupiter.api.Test;

/**
 *
 */
class BookSearcherTest {

  private static final Pattern idExtractor = Pattern.compile(".+\\/dp\\/(.+?)\\/.+");

  private BookSearcher bookSearcher = new BookSearcher();
  private Map<Isbn, String> isbnUrlMap = new HashMap<Isbn, String>() {{
    put(
        new Isbn(new short[]{9, 7, 8, 0, 3, 4, 5, 3, 9, 1, 8, 0, 3}, IsbnType.ISBN_13),
        "0345391802"
    );
    put(
        new Isbn(new short[]{9, 7, 8, 3, 7, 9, 1, 5, 0, 4, 5, 4, 4}, IsbnType.ISBN_13),
        "3791504541"
    );
    put(
        new Isbn(new short[]{3, 4, 4, 2, 2, 4, 4, 0, 2, 1}, IsbnType.ISBN_13),
        "3442244021"
    );
  }};

  @Test
  void getBookUrl() {
    for (Entry<Isbn, String> entry : isbnUrlMap.entrySet()) {
      Optional<String> bookUrl = bookSearcher.getBookUrl(entry.getKey());

      if (entry.getValue() == null) {
        assertFalse(bookUrl.isPresent());
      } else {
        assertTrue(bookUrl.isPresent());
        assertEquals(entry.getValue(), getId(bookUrl.get()));
      }
    }
  }

  private String getId(String url) {
    Matcher matcher = idExtractor.matcher(url);
    return matcher.find() ? matcher.group(1) : "";
  }
}