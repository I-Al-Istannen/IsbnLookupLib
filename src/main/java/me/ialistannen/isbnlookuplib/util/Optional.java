package me.ialistannen.isbnlookuplib.util;

import java.util.Objects;

/**
 * A small basic recreation of the java 8Optional
 */
public class Optional<T> {

  private static final Optional<?> EMPTY = new Optional<>("");

  private final T value;

  private Optional(T value) {
    this.value = Objects.requireNonNull(value, "value can not be null!");
  }

  /**
   * @return True if a value is present
   */
  public boolean isPresent() {
    return this != EMPTY;
  }

  /**
   * @return The value
   */
  public T get() {
    return value;
  }

  /**
   * @param message The message of the {@link IllegalArgumentException}
   * @return The value, if present
   * @throws IllegalArgumentException if the value is not present
   */
  public T orElseThrowIllegalArgumentException(String message) {
    if (isPresent()) {
      return get();
    }
    throw new IllegalArgumentException(message);
  }

  /**
   * @param action The action to apply to this value
   */
  public void ifPresent(Consumer<T> action) {
    if (isPresent()) {
      action.accept(get());
    }
  }

  /**
   * @param value The value to return if {@link #isPresent()} is false
   * @return The value of this optional or the passed value
   */
  @SuppressWarnings("unused")
  public T orElse(T value) {
    if (isPresent()) {
      return get();
    }
    return value;
  }

  /**
   * @param <T> The type of the {@link Optional}
   * @return An empty {@link Optional}
   */
  public static <T> Optional<T> empty() {
    @SuppressWarnings("unchecked")
    Optional<T> empty = (Optional<T>) EMPTY;
    return empty;
  }

  /**
   * Returns an {@link Optional} wrapping the value.
   *
   * @param value The value to encapsulate
   * @param <T> The type of the value
   * @return An {@link Optional} wrapping the value
   * @throws NullPointerException if value is null
   */
  public static <T> Optional<T> of(T value) {
    return new Optional<>(value);
  }

  /**
   * Returns an {@link Optional} wrapping the value.
   *
   * @param value The value to encapsulate
   * @param <T> The type of the value
   * @return An {@link Optional} wrapping the value or {@link #empty()} if null
   */
  public static <T> Optional<T> ofNullable(T value) {
    return value == null ? Optional.<T>empty() : new Optional<>(value);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Optional<?> optional = (Optional<?>) o;
    return Objects.equals(value, optional.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public String toString() {
    return "Optional{"
        + "value=" + value
        + ", present=" + isPresent()
        + ", =" + get()
        + '}';
  }
}
