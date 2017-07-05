package me.ialistannen.isbnlookuplib.book;

import java.util.List;
import me.ialistannen.isbnlookuplib.isbn.Isbn;

/**
 * The standard {@link BookDataKey}s.
 */
public enum StandardBookDataKeys implements BookDataKey {
  /**
   * A {@link Isbn}
   */
  ISBN,
  /**
   * A String
   */
  ISBN_STRING,
  /**
   * A String
   */
  TITLE,
  /**
   * Data about the Authors. A {@link List} of {@code Pair<String, String>}'s
   */
  AUTHORS,
  /**
   * An int
   */
  PAGE_COUNT,
  /**
   * A String
   */
  DESCRIPTION,
  /**
   * A double (Yes, not accurate...)
   */
  PRICE,
  /**
   * A String
   */
  LANGUAGE,
  /**
   * A String
   */
  PUBLISHER,
  /**
   * A String
   */
  COVER_TYPE,
  /**
   * A double (System depends on platform, this is normalized to percent out of one hundred)
   */
  RATING
}