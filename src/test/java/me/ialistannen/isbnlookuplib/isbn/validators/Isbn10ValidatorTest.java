package me.ialistannen.isbnlookuplib.isbn.validators;

import me.ialistannen.isbnlookuplib.isbn.Isbn;
import me.ialistannen.isbnlookuplib.isbn.IsbnType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test the {@link Isbn10Validator}.
 */
class Isbn10ValidatorTest {

  private Isbn10Validator isbn10Validator;

  @BeforeEach
  private void setup() {
    isbn10Validator = new Isbn10Validator();
  }

  @Test
  void isValidIsbn() {
    // Good ISBNs
    Assertions.assertTrue(validateIsbn(new short[]{3, 7, 9, 1, 5, 0, 4, 6, 4, 9}));
    Assertions.assertTrue(validateIsbn(new short[]{3, 7, 9, 1, 5, 0, 4, 5, 9, 2}));
    Assertions.assertTrue(validateIsbn(new short[]{0, 8, 0, 4, 4, 2, 9, 5, 7, 10}));

    // Bad ISBNs
    Assertions.assertFalse(validateIsbn(new short[]{3, 7, 9, 1, 5, 0, 4, 6, 5, 9}));
    Assertions.assertFalse(validateIsbn(new short[]{3, 7, 9, 1, 5, 0, 4, 5, 9, 1}));
    Assertions.assertFalse(validateIsbn(new short[]{0, 8, 0, 4, 4, 2, 9, 5, 7, 11}));
    Assertions.assertFalse(validateIsbn(new short[]{0, 8, 0, 4, 4, 2, 9, 5, 7}));
    Assertions.assertFalse(validateIsbn(new short[]{0, 8, 0, 4, 4, 2, 9, 5, 7, 11, 5}));
  }

  private boolean validateIsbn(short[] digits) {
    return isbn10Validator.isValidIsbn(new Isbn(digits, IsbnType.ISBN_10));
  }
}