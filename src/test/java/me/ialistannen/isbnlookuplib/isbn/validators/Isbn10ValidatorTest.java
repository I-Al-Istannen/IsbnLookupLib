package me.ialistannen.isbnlookuplib.isbn.validators;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import me.ialistannen.isbnlookuplib.isbn.Isbn;
import me.ialistannen.isbnlookuplib.isbn.IsbnType;
import org.junit.Test;

/**
 * Test the {@link Isbn10Validator}.
 */
public class Isbn10ValidatorTest {

  private Isbn10Validator isbn10Validator = new Isbn10Validator();

  @Test
  public void isValidIsbn() {
    // Good ISBNs
    assertTrue(validateIsbn(new short[]{3, 7, 9, 1, 5, 0, 4, 6, 4, 9}));
    assertTrue(validateIsbn(new short[]{3, 7, 9, 1, 5, 0, 4, 5, 9, 2}));
    assertTrue(validateIsbn(new short[]{0, 8, 0, 4, 4, 2, 9, 5, 7, 10}));

    // Bad ISBNs
    assertFalse(validateIsbn(new short[]{3, 7, 9, 1, 5, 0, 4, 6, 5, 9}));
    assertFalse(validateIsbn(new short[]{3, 7, 9, 1, 5, 0, 4, 5, 9, 1}));
    assertFalse(validateIsbn(new short[]{0, 8, 0, 4, 4, 2, 9, 5, 7, 11}));
    assertFalse(validateIsbn(new short[]{0, 8, 0, 4, 4, 2, 9, 5, 7}));
    assertFalse(validateIsbn(new short[]{0, 8, 0, 4, 4, 2, 9, 5, 7, 11, 5}));
  }

  private boolean validateIsbn(short[] digits) {
    return isbn10Validator.isValidIsbn(new Isbn(digits, IsbnType.ISBN_10));
  }
}