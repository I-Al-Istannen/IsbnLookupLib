package me.ialistannen.isbnlookuplib.isbn.factories;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import me.ialistannen.isbnlookuplib.isbn.IsbnConverter;
import me.ialistannen.isbnlookuplib.isbn.IsbnType;
import me.ialistannen.isbnlookuplib.isbn.validators.Isbn10Validator;
import me.ialistannen.isbnlookuplib.isbn.validators.Isbn13Validator;
import me.ialistannen.isbnlookuplib.isbn.validators.IsbnValidator;
import me.ialistannen.isbnlookuplib.isbn.validators.IsbnValidators;
import me.ialistannen.isbnlookuplib.util.Optional;

/**
 * A collection of {@link IsbnFactory}s.
 */
public class IsbnFactories {

  private Map<IsbnType, IsbnFactory> isbnFactoryMap = new HashMap<>();

  /**
   * @param isbnConverter The {@link IsbnConverter} to use
   */
  public IsbnFactories(IsbnConverter isbnConverter) {
    IsbnValidators validators = isbnConverter.getValidators();

    Optional<IsbnValidator> validator10Optional = validators.getValidator(IsbnType.ISBN_10);
    if (validator10Optional.isPresent()) {
      Isbn10Validator isbnValidator = (Isbn10Validator) validator10Optional.get();
      addFactory(IsbnType.ISBN_10, new Isbn10Factory(isbnValidator));
    }

    Optional<IsbnValidator> validator13Optional = validators.getValidator(IsbnType.ISBN_13);
    if (validator13Optional.isPresent()) {
      Isbn13Validator isbnValidator = (Isbn13Validator) validator13Optional.get();
      addFactory(IsbnType.ISBN_13, new Isbn13Factory(isbnValidator));
    }
  }


  /**
   * Adds an {@link IsbnFactory}.
   *
   * @param type The type of the ISBN
   * @param factory The {@link IsbnFactory} to use
   */
  private void addFactory(IsbnType type, IsbnFactory factory) {
    isbnFactoryMap.put(type, factory);
  }

  /**
   * @param type The type of the ISBN
   * @return The appropriate {@link IsbnFactory}, if any is registered
   */
  public IsbnFactory getFactory(IsbnType type) {
    return isbnFactoryMap.get(type);
  }

  /**
   * @return All registered {@link IsbnFactory}s.
   */
  public Map<IsbnType, IsbnFactory> getAllFactories() {
    return Collections.unmodifiableMap(isbnFactoryMap);
  }
}
