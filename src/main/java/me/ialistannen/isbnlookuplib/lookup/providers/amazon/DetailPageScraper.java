package me.ialistannen.isbnlookuplib.lookup.providers.amazon;

import java.util.ArrayList;
import java.util.List;
import me.ialistannen.isbnlookuplib.book.Book;
import me.ialistannen.isbnlookuplib.book.StandardBookDataKeys;
import me.ialistannen.isbnlookuplib.util.Pair;
import me.ialistannen.isbnlookuplib.util.WebsiteFetcher;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Scrapes data from the detail page
 */
class DetailPageScraper {

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

  public static void main(String[] args) {
    DetailPageScraper scraper = new DetailPageScraper();
    String url = "https://www.amazon.de/Drachenreiter-Cornelia-Funke/dp/3791504541/"
        + "ref=sr_1_1/258-7589277-1449228?ie=UTF8&qid=1498418023&sr=8-1&keywords=9783791504544";
    url = "https://www.amazon.de/Die-Eule-Beule-Popular-Fiction/dp/3789167061"
        + "/ref=sr_1_1?ie=UTF8&qid=1498418557&sr=8-1&keywords=Buch";
    System.out.println(scraper.scrape(url));
  }
}
