package me.ialistannen.isbnlookuplib.isbn.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import me.ialistannen.isbnlookuplib.isbn.Isbn;
import me.ialistannen.isbnlookuplib.isbn.IsbnConverter;
import me.ialistannen.isbnlookuplib.isbn.IsbnType;
import me.ialistannen.isbnlookuplib.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 */
class IsbnConverterTest {

  private IsbnConverter isbnConverter = new IsbnConverter();

  @Test
  void fromString() {
    // VALID ISBN 10
    Assertions.assertEquals(IsbnType.ISBN_10, getIsbnType("080442957X"));
    assertEquals(IsbnType.ISBN_10, getIsbnType("3791504649"));
    assertEquals(IsbnType.ISBN_10, getIsbnType("3791504533"));

    // VALID ISBN 13
    assertEquals(IsbnType.ISBN_13, getIsbnType("9783596800643"));
    assertEquals(IsbnType.ISBN_13, getIsbnType("9783791504452"));
    assertEquals(IsbnType.ISBN_13, getIsbnType("978-1-86197-876-9"));

    // WRONG ISBN_10
    assertNull(getIsbnType("0804429579"));
    assertNull(getIsbnType("080442956X"));
    assertNull(getIsbnType("080442957X1"));
    assertNull(getIsbnType("08044295"));
    assertNull(getIsbnType("08044295adsdfs"));

    // WRONG ISBN_13
    assertNull(getIsbnType("978-1-86197-876-9-8"));
    assertNull(getIsbnType("dad978-1-86197-876-9-8"));
    assertNull(getIsbnType("978-1-86197-8-8"));
    assertNull(getIsbnType("978-1-86197-877-9"));
    assertNull(getIsbnType("978-1-86197-876-8"));
  }

  private IsbnType getIsbnType(String isbnString) {
    Optional<Isbn> isbnOptional = isbnConverter.fromString(isbnString);
    return isbnOptional.isPresent() ? isbnOptional.get().getType() : null;
  }

}