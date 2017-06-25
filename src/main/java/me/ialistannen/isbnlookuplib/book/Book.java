package me.ialistannen.isbnlookuplib.book;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * A book!
 */
public class Book {

  private Map<BookDataKey, Object> dataMap = new HashMap<>();

  /**
   * Stores data in this book.
   *
   * @param key The {@link BookDataKey} to store it under
   * @param value The value to store
   */
  public void setData(BookDataKey key, Object value) {
    dataMap.put(key, value);
  }

  /**
   * The data in this key.
   *
   * f
   * sdsds
   * <em>Will cast to what you assign it to. Beware of {@link ClassCastException}s.</em>
   *
   * @param <T> The type to cast it to
   * @param key The {@link BookDataKey} to retrieve
   * @return The stored data
   */
  public <T> T getData(BookDataKey key) {
    @SuppressWarnings("unchecked")
    T t = (T) dataMap.get(key);
    return t;
  }

  /**
   * @return All data in this {@link Book}
   */
  public Map<BookDataKey, Object> getAllData() {
    return Collections.unmodifiableMap(dataMap);
  }

  @Override
  public String toString() {
    return "Book{"
        + "allData=" + getAllData()
        + '}';
  }
}
