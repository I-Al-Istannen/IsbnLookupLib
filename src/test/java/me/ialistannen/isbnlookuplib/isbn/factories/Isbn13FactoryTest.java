package me.ialistannen.isbnlookuplib.isbn.factories;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import me.ialistannen.isbnlookuplib.isbn.validators.Isbn13Validator;
import org.junit.jupiter.api.Test;

/**
 *
 */
class Isbn13FactoryTest {

  private Isbn13Factory factory = new Isbn13Factory(new Isbn13Validator());

  @Test
  void generate() {
    // Valid ISBNs
    assertTrue(isGenerated("9783596800643"));
    assertTrue(isGenerated("9783791504452"));
    assertTrue(isGenerated("978-1-86197-876-9"));

    // Invalid ISBNs
    assertFalse(isGenerated("9783596800644"));
    assertFalse(isGenerated("9783791514452"));
    assertFalse(isGenerated("978-1-86197-877-9"));
    assertFalse(isGenerated("978-1-86197-8764-9"));
    assertFalse(isGenerated("978-1-86197-9"));
  }

  private boolean isGenerated(String isbn) {
    return factory.generate(isbn).isPresent();
  }
}