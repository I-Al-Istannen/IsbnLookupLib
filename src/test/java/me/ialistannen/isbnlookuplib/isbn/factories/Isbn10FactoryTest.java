package me.ialistannen.isbnlookuplib.isbn.factories;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import me.ialistannen.isbnlookuplib.isbn.validators.Isbn10Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Some tests for the {@link Isbn10Factory}
 */
class Isbn10FactoryTest {

  private Isbn10Factory factory;

  @BeforeEach
  private void setup() {
    factory = new Isbn10Factory(new Isbn10Validator());
  }

  @Test
  void generate() {
    // Valid ISBNs
    assertTrue(isGenerated("080442957X"));
    assertTrue(isGenerated("3791504649"));
    assertTrue(isGenerated("3596800641"));
    assertTrue(isGenerated("3791504533"));

    // Invalid ISBNs
    assertFalse(isGenerated("080442957Y"));
    assertFalse(isGenerated("0804429574"));
    assertFalse(isGenerated("3791504"));
    assertFalse(isGenerated("359680000641"));
    assertFalse(isGenerated("3791504534"));
  }

  private boolean isGenerated(String isbn) {
    return factory.generate(isbn).isPresent();
  }

}