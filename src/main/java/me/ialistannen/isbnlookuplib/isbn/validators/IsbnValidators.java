package me.ialistannen.isbnlookuplib.isbn.validators;

import java.util.HashMap;
import java.util.Map;
import me.ialistannen.isbnlookuplib.isbn.IsbnType;
import me.ialistannen.isbnlookuplib.util.Optional;

/**
 * A collection of {@link IsbnValidator} for different {@link IsbnType}s.
 */
public class IsbnValidators {

  private Map<IsbnType, IsbnValidator> validatorMap = new HashMap<>();

  public IsbnValidators() {
    addValidator(IsbnType.ISBN_10, new Isbn10Validator());
    addValidator(IsbnType.ISBN_13, new Isbn13Validator());
  }

  /**
   * Adds an {@link IsbnValidator}.
   *
   * @param isbnType The type of the ISBN
   * @param isbnValidator The {@link IsbnValidator} to use
   */
  public void addValidator(IsbnType isbnType, IsbnValidator isbnValidator) {
    validatorMap.put(isbnType, isbnValidator);
  }

  /**
   * @param isbnType The type of the ISBN
   * @return The {@link IsbnValidator}, if any was found.
   */
  public Optional<IsbnValidator> getValidator(IsbnType isbnType) {
    return Optional.ofNullable(validatorMap.get(isbnType));
  }
}
