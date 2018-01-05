package me.ialistannen.isbnlookuplib.lookup.providers.goodreads;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import me.ialistannen.isbnlookuplib.book.Book;
import me.ialistannen.isbnlookuplib.book.StandardBookDataKeys;
import me.ialistannen.isbnlookuplib.isbn.IsbnConverter;
import me.ialistannen.isbnlookuplib.util.Pair;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Scrapes the detail page for a {@link Book}.
 */
class DetailPageScraper {

  private static Pattern PUBLISHER_EXTRACT_PATTERN = Pattern.compile("by\\s*(.+?)\\s*\\(");

  private IsbnConverter isbnConverter;

  DetailPageScraper() {
    isbnConverter = new IsbnConverter();
  }

  /**
   * Parses a detail page to a {@link Book}.
   *
   * @param detailPage the page to parse
   * @return the parsed book
   */
  public Book parseBook(Document detailPage) {
    Book book = new Book();

    book.setData(StandardBookDataKeys.TITLE, parseTitle(detailPage));
    book.setData(StandardBookDataKeys.DESCRIPTION, parseDescription(detailPage));
    book.setData(StandardBookDataKeys.PAGE_COUNT, parsePageCount(detailPage));
    book.setData(StandardBookDataKeys.COVER_IMAGE_URL, parseCoverImageUrl(detailPage));
    book.setData(StandardBookDataKeys.COVER_TYPE, parseCoverType(detailPage));
    book.setData(StandardBookDataKeys.ISBN_STRING, parseIsbn(detailPage));
    book.setData(StandardBookDataKeys.ISBN, isbnConverter.fromString(parseIsbn(detailPage)).get());
    book.setData(StandardBookDataKeys.LANGUAGE, parseLanguage(detailPage));
    book.setData(StandardBookDataKeys.RATING, parseRating(detailPage));
    book.setData(StandardBookDataKeys.GENRE, parseGenre(detailPage));
    book.setData(StandardBookDataKeys.AUTHORS, parseAuthors(detailPage));
    if (parsePublisher(detailPage) != null) {
      book.setData(StandardBookDataKeys.PUBLISHER, parsePublisher(detailPage));
    }

    return book;
  }

  private String parseTitle(Document document) {
    return document.getElementById("bookTitle").text();
  }

  private String parseDescription(Document document) {
    return document.getElementById("description").child(1).text();
  }

  private int parsePageCount(Document document) {
    return Integer.parseInt(
        document.getElementsByAttributeValue("itemprop", "numberOfPages").text()
            .replaceAll("[^\\d]", "")
    );
  }

  private String parseCoverImageUrl(Document document) {
    return document.getElementById("coverImage").absUrl("src");
  }

  private String parseCoverType(Document document) {
    return document.getElementsByAttributeValue("itemprop", "bookFormat").text();
  }

  private String parseIsbn(Document document) {
    return document.getElementsByAttributeValue("itemprop", "isbn").text();
  }

  private String parseLanguage(Document document) {
    return document.getElementsByAttributeValue("itemprop", "inLanguage").text();
  }

  private double parseRating(Document document) {
    return Double.parseDouble(
        document.getElementsByAttributeValue("itemprop", "ratingValue").text()
    ) / 5.0;
  }

  private List<String> parseGenre(Document document) {
    List<String> genres = new ArrayList<>();
    Elements elements = document.getElementsByAttributeValueContaining("href", "/genre");

    for (Element element : elements) {
      if (element.className().contains("bookPageGenreLink")) {
        genres.add(element.text());
      }
    }

    return genres;
  }

  private List<Pair<String, String>> parseAuthors(Document document) {
    List<Pair<String, String>> authors = new ArrayList<>();

    Elements authorNameElements = document.getElementsByClass("authorName");
    String lastName = null;
    for (Element element : authorNameElements) {
      if (element.classNames().contains("role")) {
        if (lastName != null) {
          authors.add(new Pair<>(
              lastName,
              element.text()
                  .replace("(", "")
                  .replace(")", "")
                  .trim()
          ));
          lastName = null;
        }
        continue;
      }
      if (lastName == null) {
        lastName = element.text();
      } else {
        authors.add(new Pair<>(lastName, "Autor"));
        lastName = element.text();
      }
    }

    if (lastName != null) {
      authors.add(new Pair<>(lastName, "Autor"));
    }

    return authors;
  }

  private String parsePublisher(Document document) {
    for (Element element : document.select("#details > div:nth-child(2)")) {
      Matcher matcher = PUBLISHER_EXTRACT_PATTERN.matcher(element.text().trim());
      if (matcher.find()) {
        return matcher.group(1);
      }
    }

    return null;
  }
}
