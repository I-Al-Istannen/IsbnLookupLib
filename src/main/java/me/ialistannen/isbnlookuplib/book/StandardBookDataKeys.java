package me.ialistannen.isbnlookuplib.book;

import java.util.List;
import me.ialistannen.isbnlookuplib.isbn.Isbn;
import me.ialistannen.isbnlookuplib.util.Price;

/**
 * The standard {@link BookDataKey}s.
 */
public enum StandardBookDataKeys implements BookDataKey {
  /**
   * A String
   */
  TITLE,
  /**
   * Data about the Authors. A {@link List} of {@code Pair<String, String>}'s
   */
  AUTHORS,
  /**
   * A String
   */
  PUBLISHER,
  /**
   * A String
   */
  COVER_TYPE,
  /**
   * A String
   */
  DESCRIPTION,
  /**
   * An int
   */
  PAGE_COUNT,
  /**
   * A String
   */
  LANGUAGE,
  /**
   * A String
   */
  ISBN_STRING,
  /**
   * A {@link Isbn}
   */
  ISBN,
  /**
   * A {@link Price}
   */
  PRICE,
  /**
   * A double (System depends on platform, this is normalized to percent)
   */
  RATING,
  /**
   * A link to the cover image.
   */
  COVER_IMAGE_URL,
  /**
   * A List of strings.
   */
  GENRE;

  @Override
  public int displayPriority() {
    return ordinal();
  }
}