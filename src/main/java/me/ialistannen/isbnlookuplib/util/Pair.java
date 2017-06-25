package me.ialistannen.isbnlookuplib.util;

/**
 * A Pair.
 *
 * @param <K> The type of the key
 * @param <V> The type of the value
 */
public class Pair<K, V> {

  private K key;
  private V value;

  public Pair(K key, V value) {
    this.key = key;
    this.value = value;
  }

  /**
   * @return The key
   */
  public K getKey() {
    return key;
  }

  /**
   * @return The value
   */
  public V getValue() {
    return value;
  }

  @Override
  public String toString() {
    return "Pair{"
        + "key=" + key
        + ", value=" + value
        + '}';
  }
}
