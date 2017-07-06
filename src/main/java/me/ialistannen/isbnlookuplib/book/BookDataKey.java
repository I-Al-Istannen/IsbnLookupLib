package me.ialistannen.isbnlookuplib.book;

/**
 * A key for data about a book
 */
public interface BookDataKey {

  /**
   * @return The name of the key
   */
  String name();

  /**
   * @return The priority this {@link BookDataKey} has when displaying it.
   */
  int displayPriority();
}
