package me.ialistannen.isbnlookuplib.book;

/**
 * An abstract Skeleton for {@link BookDataKey} which positions these elements at the lowest
 * priority.
 */
public abstract class AbstractBookDataKey implements BookDataKey {

  @Override
  public int displayPriority() {
    return Integer.MIN_VALUE;
  }
}
