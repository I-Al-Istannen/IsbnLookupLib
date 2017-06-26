package me.ialistannen.isbnlookuplib.lookup.providers.amazon;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import me.ialistannen.isbnlookuplib.book.Book;
import me.ialistannen.isbnlookuplib.book.StandardBookDataKeys;
import me.ialistannen.isbnlookuplib.i18n.DefaultCategories;
import me.ialistannen.isbnlookuplib.i18n.Language;
import me.ialistannen.isbnlookuplib.util.NumberUtil;
import me.ialistannen.isbnlookuplib.util.Pair;
import me.ialistannen.isbnlookuplib.util.WebsiteFetcher;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Scrapes data from the detail page
 */
class DetailPageScraper {

  @SuppressWarnings("ConstantConditions")
  private Language language = DefaultCategories.AMAZON_SCRAPER.getCategory().getLanguage(
      Locale.GERMAN
  ).get();

  /**
   * Scrapes a detail page for book information.
   *
   * @param url The URL of the book
   * @return The scraped book. May contain no data.
   */
  Book scrape(String url) {
    Book book = new Book();

    Document document = WebsiteFetcher.getWebsite(url);

    if (document == null) {
      return book;
    }

    addAuthor(document, book);
    addPageCount(document, book);
    addLanguage(document, book);
    addPublisher(document, book);
    addRating(document, book);

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

  private void addPageCount(Document document, Book book) {
    String pageCountName = language.translate("page_count_suffix");

    Pattern extractorPattern = Pattern.compile(".+:\\s*(\\d+)\\s*" + pageCountName);

    applyToProductInformation(
        document,
        extractorPattern,
        matcher -> NumberUtil.parseInt(matcher.group(1))
            .ifPresent(page -> book.setData(StandardBookDataKeys.PAGE_COUNT, page))
    );
  }

  private void addLanguage(Document document, Book book) {
    String languagePrefix = language.translate("language_prefix");
    Pattern extractorPattern = Pattern.compile(".*" + languagePrefix + ".*:\\s*(.+)\\s*");

    applyToProductInformation(
        document,
        extractorPattern,
        matcher -> book.setData(StandardBookDataKeys.LANGUAGE, matcher.group(1))
    );
  }

  private void addPublisher(Document document, Book book) {
    String publisherPrefix = language.translate("publisher_prefix");
    Pattern extractorPattern = Pattern.compile("\\s*" + publisherPrefix + ":\\s*([\\w\\s]+).+\\s*");

    applyToProductInformation(
        document,
        extractorPattern,
        matcher -> book.setData(StandardBookDataKeys.PUBLISHER, matcher.group(1))
    );
  }

  private void addRating(Document document, Book book) {
    String ratingPrefix = language.translate("rating_prefix");
    Pattern extractorPattern = Pattern.compile("\\s*" + ratingPrefix + ":\\s*([\\d.]+).+\\s*");

    applyToProductInformation(
        document,
        extractorPattern,
        matcher -> NumberUtil.parseDouble(matcher.group(1)).ifPresent(value ->
            book.setData(StandardBookDataKeys.RATING, value / 5.0)
        )
    );
  }

  private void applyToProductInformation(Document document, Pattern extractorPattern,
      Consumer<Matcher> action) {
    getProductInformation(document).ifPresent(information -> information.getElementsByTag("ul")
        .stream()
        .limit(1)
        .flatMap(element -> element.children().stream())
        .forEach(r -> {
          Matcher matcher = extractorPattern.matcher(r.text());
          if (matcher.find()) {
            action.accept(matcher);
          }
        })
    );
  }

  private Optional<Element> getProductInformation(Document document) {
    String productInformation = language.translate("product_information");

    return document.getElementsByTag("h2").stream()
        .filter(element -> element.text().contains(productInformation))
        .findFirst()
        .map(Element::nextElementSibling);
  }

  public static void main(String[] args) {
    DetailPageScraper scraper = new DetailPageScraper();
    String url = "https://www.amazon.de/Drachenreiter-Cornelia-Funke/dp/3791504541/"
        + "ref=sr_1_1/258-7589277-1449228?ie=UTF8&qid=1498418023&sr=8-1&keywords=9783791504544";
//    url = "https://www.amazon.de/Die-Eule-Beule-Popular-Fiction/dp/3789167061"
//        + "/ref=sr_1_1?ie=UTF8&qid=1498418557&sr=8-1&keywords=Buch";
    System.out.println(scraper.scrape(url));
  }
}
