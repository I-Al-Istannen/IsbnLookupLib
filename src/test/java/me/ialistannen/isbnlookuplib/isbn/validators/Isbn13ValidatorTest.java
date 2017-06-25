package me.ialistannen.isbnlookuplib.isbn.validators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import me.ialistannen.isbnlookuplib.isbn.Isbn;
import me.ialistannen.isbnlookuplib.isbn.IsbnType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 */
class Isbn13ValidatorTest {

  private Isbn13Validator validator;

  @BeforeEach
  void setUp() {
    validator = new Isbn13Validator();
  }

  @Test
  void isValidIsbn() {
    // Good ISBNs
    assertTrue(validateIsbn(new short[]{9, 7, 8, 3, 7, 9, 1, 5, 0, 4, 5, 9, 9}));
    assertTrue(validateIsbn(new short[]{9, 7, 8, 3, 5, 9, 6, 8, 0, 3, 0, 0, 2}));
    assertTrue(validateIsbn(new short[]{9, 7, 8, 3, 5, 9, 6, 8, 0, 0, 6, 4, 3}));

    // Bad ISBNs
    assertFalse(validateIsbn(new short[]{9, 7, 8, 3, 7, 9, 1, 5, 0, 4, 5, 9, 10}));
    assertFalse(validateIsbn(new short[]{9, 7, 8, 3, 5, 9, 6, 8, 0, 3, 0, 1, 2}));
    assertFalse(validateIsbn(new short[]{9, 7, 8, 3, 6, 9, 6, 8, 0, 0, 6, 4, 3}));
    assertFalse(validateIsbn(new short[]{9, 7, 8, 3, 5, 6, 8, 0, 0, 6, 4, 3}));
    assertFalse(validateIsbn(new short[]{9, 7, 8, 3, 5, 5, 5, 6, 8, 0, 0, 6, 4, 3}));
  }

  private boolean validateIsbn(short[] digits) {
    return validator.isValidIsbn(new Isbn(digits, IsbnType.ISBN_13));
  }

}