package me.ialistannen.isbnlookuplib.book;

import java.util.List;
import me.ialistannen.isbnlookuplib.isbn.Isbn;

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
   * A double (Yes, not accurate...)
   */
  PRICE,
  /**
   * A double (System depends on platform, this is normalized to percent out of one hundred)
   */
  RATING;

  @Override
  public int displayPriority() {
    return ordinal();
  }
}