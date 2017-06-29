package me.ialistannen.isbnlookuplib.util;

import java.util.Objects;

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
  @SuppressWarnings("WeakerAccess")
  public K getKey() {
    return key;
  }

  /**
   * @return The value
   */
  @SuppressWarnings("WeakerAccess")
  public V getValue() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Pair<?, ?> pair = (Pair<?, ?>) o;
    return Objects.equals(getKey(), pair.getKey()) &&
        Objects.equals(getValue(), pair.getValue());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getKey(), getValue());
  }

  @Override
  public String toString() {
    return "Pair{"
        + "key=" + key
        + ", value=" + value
        + '}';
  }
}
