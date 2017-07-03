package me.ialistannen.isbnlookuplib.lookup.providers.amazon;

import static me.ialistannen.isbnlookuplib.util.JsoupUtil.getFirst;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import me.ialistannen.isbnlookuplib.book.Book;
import me.ialistannen.isbnlookuplib.book.BookDataKey;
import me.ialistannen.isbnlookuplib.book.StandardBookDataKeys;
import me.ialistannen.isbnlookuplib.i18n.DefaultCategories;
import me.ialistannen.isbnlookuplib.i18n.Language;
import me.ialistannen.isbnlookuplib.isbn.Isbn;
import me.ialistannen.isbnlookuplib.isbn.IsbnConverter;
import me.ialistannen.isbnlookuplib.util.Consumer;
import me.ialistannen.isbnlookuplib.util.JsoupUtil;
import me.ialistannen.isbnlookuplib.util.NumberUtil;
import me.ialistannen.isbnlookuplib.util.Optional;
import me.ialistannen.isbnlookuplib.util.Pair;
import me.ialistannen.isbnlookuplib.util.WebsiteFetcher;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Scrapes data from the detail page
 */
class DetailPageScraper {

  private Locale locale;
  private Language language;
  private IsbnConverter isbnConverter;

  /**
   * @param isbnConverter The {@link IsbnConverter} to use
   * @param locale The {@link Locale} to use. Throws an exception if not found!
   * @throws IllegalArgumentException if the locale was not found
   */
  DetailPageScraper(Locale locale, IsbnConverter isbnConverter) {
    this.locale = locale;
    this.isbnConverter = isbnConverter;

    language = DefaultCategories.AMAZON_SCRAPER.getCategory().getLanguage(locale)
        .orElseThrowIllegalArgumentException("No language found!");
  }

  /**
   * Scrapes a detail page for book information.
   *
   * @param url The URL of the book
   * @return The scraped book. May contain no data.
   */
  Book scrape(String url) {
    return scrape(url, "");
  }

  /**
   * Scrapes a detail page for book information.
   *
   * @param url The URL of the book
   * @param preferredEdition The preferred edition of the book
   * @return The scraped book. May contain no data.
   */
  Book scrape(String url, String preferredEdition) {
    Book book = new Book();

    Document document = WebsiteFetcher.getWebsite(url);

    if (document == null) {
      return book;
    }

    document = selectRightEdition(document, preferredEdition);

    if (document == null) {
      return book;
    }

    addAuthor(document, book);
    addPageCount(document, book);
    addLanguage(document, book);
    addPublisher(document, book);
    addRating(document, book);
    addIsbn(document, book);
    addCoverType(document, book);
    addTitle(document, book);
    addDescription(document, book);
    addPrice(document, book);

    book.setData(new BookDataKey() {
      @Override
      public String name() {
        return "URL";
      }
    }, document.location());

    return book;
  }

  private void addAuthor(Document document, Book book) {
    Elements authors = document.getElementsByClass("author");

    List<Pair<String, String>> authorData = new ArrayList<>();

    for (Element author : authors) {
      String contribution = getContribution(author);
      String name = getAuthorName(author);

      authorData.add(new Pair<>(name, contribution));
    }

    book.setData(StandardBookDataKeys.AUTHORS, authorData);
  }

  private String getContribution(Element authorByline) {
    Elements contributionElement = authorByline.getElementsByClass("contribution");
    if (!contributionElement.isEmpty()) {
      String contribution = contributionElement.get(0).text();
      if (contribution.trim().endsWith(",")) {
        contribution = contribution.trim().substring(0, contribution.trim().length() - 1);
      }
      contribution = contribution.replace("(", "").replace(")", "");
      return contribution;
    }
    return "";
  }

  private String getAuthorName(Element author) {
    Elements authorName = author.getElementsByClass("contributorNameID");
    if (!authorName.isEmpty()) {
      return authorName.get(0).text();
    }
    return author.getElementsByTag("a").text();
  }

  private void addPageCount(Document document, final Book book) {
    String pageCountSuffix = language.translate("page_count_suffix");

    Pattern extractorPattern = Pattern.compile(".+:\\s*(\\d+)\\s*" + pageCountSuffix);

    applyToProductInformation(
        document,
        extractorPattern,
        new Consumer<Matcher>() {
          @Override
          public void accept(Matcher matcher) {
            NumberUtil.parseInt(matcher.group(1))
                .ifPresent(new Consumer<Integer>() {
                  @Override
                  public void accept(Integer page) {
                    book.setData(StandardBookDataKeys.PAGE_COUNT, page);
                  }
                });
          }
        }
    );
  }

  private void addLanguage(Document document, final Book book) {
    String languagePrefix = language.translate("language_prefix");
    Pattern extractorPattern = Pattern.compile(".*" + languagePrefix + ".*:\\s*(.+)\\s*");

    applyToProductInformation(
        document,
        extractorPattern,
        new Consumer<Matcher>() {
          @Override
          public void accept(Matcher matcher) {
            book.setData(StandardBookDataKeys.LANGUAGE, matcher.group(1));
          }
        }
    );
  }

  private void addPublisher(Document document, final Book book) {
    String publisherPrefix = language.translate("publisher_prefix");
    Pattern extractorPattern = Pattern.compile("\\s*" + publisherPrefix + ":\\s*([\\w\\s]+).*\\s*");

    applyToProductInformation(
        document,
        extractorPattern,
        new Consumer<Matcher>() {
          @Override
          public void accept(Matcher matcher) {
            book.setData(StandardBookDataKeys.PUBLISHER, matcher.group(1));
          }
        }
    );
  }

  private void addRating(Document document, final Book book) {
    String ratingPrefix = language.translate("rating_prefix");
    Pattern extractorPattern = Pattern.compile("\\s*" + ratingPrefix + ":\\s*([\\d.]+).*\\s*");

    applyToProductInformation(
        document,
        extractorPattern,
        new Consumer<Matcher>() {
          @Override
          public void accept(Matcher matcher) {
            NumberUtil.parseDouble(matcher.group(1)).ifPresent(new Consumer<Double>() {
              @Override
              public void accept(Double value) {
                book.setData(StandardBookDataKeys.RATING, value / 5.0);
              }
            });
          }
        }
    );
  }

  private void addIsbn(Document document, final Book book) {
    String isbnPrefix = language.translate("isbn_prefix");
    Pattern extractorPattern = Pattern.compile("\\s*" + isbnPrefix + ":\\s*([\\d-]+).*\\s*");

    applyToProductInformation(
        document,
        extractorPattern,
        new Consumer<Matcher>() {
          @Override
          public void accept(Matcher matcher) {
            isbnConverter.fromString(matcher.group(1))
                .ifPresent(new Consumer<Isbn>() {
                  @Override
                  public void accept(Isbn isbn) {
                    book.setData(StandardBookDataKeys.ISBN, isbn);
                  }
                });
          }
        });
  }

  private void addCoverType(Document document, final Book book) {
    String pageCountSuffix = language.translate("page_count_suffix");
    Pattern extractorPattern = Pattern.compile("\\s*(.+):\\s*\\d+\\s*" + pageCountSuffix + ".*");

    applyToProductInformation(
        document,
        extractorPattern,
        new Consumer<Matcher>() {
          @Override
          public void accept(Matcher matcher) {
            book.setData(StandardBookDataKeys.COVER_TYPE, matcher.group(1));
          }
        }
    );
  }

  private void addTitle(Document document, Book book) {
    Element productTitle = document.getElementById("productTitle");
    if (productTitle == null) {
      return;
    }
    book.setData(StandardBookDataKeys.TITLE, productTitle.text());
  }

  private void addDescription(Document document, Book book) {
    Elements descriptionElements = document
        .getElementsByAttributeValue("data-feature-name", "bookDescription");
    if (descriptionElements.isEmpty()) {
      return;
    }
    Element description = descriptionElements.get(0);
    if (!description.getElementsByTag("noscript").isEmpty()) {
      description = description.getElementsByTag("noscript").get(0);
    }

    String descriptionString = JsoupUtil.toStringRespectLinebreak(description).trim();
    if (descriptionString.isEmpty()) {
      return;
    }

    book.setData(
        StandardBookDataKeys.DESCRIPTION,
        descriptionString
    );
  }

  private void addPrice(Document document, final Book book) {
    Element formatContainer = document.getElementById("tmmSwatches");
    Elements ul = formatContainer.getElementsByTag("ul");
    if (ul.isEmpty()) {
      return;
    }
    final Pattern extractPricePattern = Pattern.compile("[^\\d]*(\\d.+)");

    for (Element child : ul.get(0).children()) {
      if (!child.hasClass("selected")) {
        continue;
      }
      Elements aButtonTexts = child.getElementsByClass("a-button-text");
      if (aButtonTexts.isEmpty()) {
        continue;
      }
      for (Element element : aButtonTexts) {
        getFirst(element.getElementsByClass("a-color-price"))
            .ifPresent(new Consumer<Element>() {
              @Override
              public void accept(Element priceElement) {
                String text = priceElement.text().trim();
                Matcher matcher = extractPricePattern.matcher(text);
                if (matcher.find()) {
                  text = matcher.group(1);

                  NumberUtil.parseDouble(text, locale)
                      .ifPresent(new Consumer<Double>() {
                        @Override
                        public void accept(Double value) {
                          book.setData(StandardBookDataKeys.PRICE, value);
                        }
                      });
                }
              }
            });
      }
    }
  }

  private Document selectRightEdition(Document document, String preferred) {
    final Set<String> acceptedEditions = new HashSet<>(
        Arrays.asList(language.translate("good_editions")
            .split("\\|"))
    );

    Element formatContainer = document.getElementById("tmmSwatches");
    Elements ul = formatContainer.getElementsByTag("ul");
    if (ul.isEmpty()) {
      return document;
    }

    final AtomicReference<String> thisEdition = new AtomicReference<>("");
    final Map<String, String> goodUrls = new HashMap<>();

    for (final Element element : ul.get(0).children()) {
      Elements texts = element.getElementsByClass("a-button-text");
      getFirst(texts)
          .ifPresent(new Consumer<Element>() {
            @Override
            public void accept(Element link) {
              String url = link.absUrl("href");

              String edition = getSwatchEdition(link);
              if (element.hasClass("selected")) {
                thisEdition.set(edition);
              }

              if (acceptedEditions.contains(edition)) {
                goodUrls.put(edition, url);
              }
            }
          });
    }

    // Nothing found!
    if (goodUrls.isEmpty()) {
      return null;
    }

    if (goodUrls.containsKey(preferred)) {
      // Our version is the preferred
      if (preferred.equals(thisEdition.get())) {
        return document;
      }
      // get the preferred one!
      return WebsiteFetcher.getWebsite(goodUrls.get(preferred));
    }

    // Our version is okay. Fetching would not be possible, as the current element has no url!
    if (goodUrls.containsKey(thisEdition.get())) {
      return document;
    }

    // return *some* good version.
    // Will never be thisEdition, which is needed as thisEdition has no ur!
    return WebsiteFetcher.getWebsite(goodUrls.values().iterator().next());
  }

  private String getSwatchEdition(Element element) {
    return JsoupUtil.toStringRespectLinebreak(element).split("\n")[0].trim();
  }

  private void applyToProductInformation(Document document, final Pattern extractorPattern,
      final Consumer<Matcher> action) {
    getProductInformation(document)
        .ifPresent(new Consumer<Element>() {
                     @Override
                     public void accept(Element information) {
                       ListIterator<Element> ul = information.getElementsByTag("ul").listIterator();
                       if (!ul.hasNext()) {
                         return;
                       }
                       for (Element r : ul.next().children()) {
                         Matcher matcher = extractorPattern.matcher(r.text());
                         if (matcher.find()) {
                           action.accept(matcher);
                         }
                       }
                     }
                   }
        );
  }

  private Optional<Element> getProductInformation(Document document) {
    String productInformation = language.translate("product_information");

    for (Element element : document.getElementsByTag("h2")) {
      if (!element.text().contains(productInformation)) {
        continue;
      }
      return Optional.ofNullable(element.nextElementSibling());
    }

    return Optional.empty();
  }
}
