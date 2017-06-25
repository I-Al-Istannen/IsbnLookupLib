package me.ialistannen.isbnlookuplib.util;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Fetches a website's code.
 */
public class WebsiteFetcher {

  private static final Logger LOGGER = Logger.getLogger("WebsiteFetcher");

  /**
   * @param url The Url of the website
   * @return The parsed {@link Document} or null if not possible.
   */
  public static Document getWebsite(String url) {
    try {
      return Jsoup.connect(url).userAgent("Mozilla/5.0").get();
    } catch (IOException e) {
      LOGGER.log(Level.WARNING, "Error while fetching website '" + url + "'", e);
      return null;
    }
  }
}
