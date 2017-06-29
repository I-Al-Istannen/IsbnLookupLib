package me.ialistannen.isbnlookuplib.book;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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
   * <em>Will cast to what you assign it to. Beware of {@link ClassCastException}s.</em>
   *
   * @param <T> The type to cast it to
   * @param key The {@link BookDataKey} to retrieve
   * @return The stored data
   */
  @SuppressWarnings("TypeParameterUnusedInFormals") // you are technically right google
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

  /**
   * Prints all data as a String in a slightly nicer format.
   *
   * @return A nicer form of toString
   */
  @SuppressWarnings("unused")
  public String nicerToString() {
    int maxLength = getAllData().keySet().stream()
        .map(BookDataKey::name)
        .mapToInt(String::length)
        .max()
        .orElse(10);

    StringBuilder stringBuilder = new StringBuilder();
    for (Entry<BookDataKey, Object> entry : getAllData().entrySet()) {
      stringBuilder.append(
          String.format("%-" + maxLength + "s : %s\n", entry.getKey().name(), entry.getValue())
      );
    }

    return stringBuilder.toString();
  }

  @Override
  public String toString() {
    return "Book{"
        + "allData=" + getAllData()
        + '}';
  }
}
