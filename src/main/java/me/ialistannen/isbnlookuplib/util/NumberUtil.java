package me.ialistannen.isbnlookuplib.util;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.OptionalDouble;
import java.util.OptionalInt;

/**
 * A util to deal with Numbers.
 */
public class NumberUtil {

  /**
   * Parses a String to an integer.
   *
   * @param input The input string
   * @return The parsed int, if any
   */
  public static OptionalInt parseInt(String input) {
    try {
      return OptionalInt.of(Integer.parseInt(input));
    } catch (NumberFormatException e) {
      return OptionalInt.empty();
    }
  }

  /**
   * Parses a String to a double.
   *
   * @param input The input string
   * @return The parsed double, if any
   */
  public static OptionalDouble parseDouble(String input) {
    try {
      return OptionalDouble.of(Double.parseDouble(input));
    } catch (NumberFormatException e) {
      return OptionalDouble.empty();
    }
  }

  /**
   * Parses a String to a double.
   *
   * @param input The input string
   * @param locale The locale to use
   * @return The parsed double, if any
   */
  public static OptionalDouble parseDouble(String input, Locale locale) {
    try {
      return OptionalDouble.of(NumberFormat.getInstance(locale).parse(input).doubleValue());
    } catch (ParseException e) {
      return OptionalDouble.empty();
    }
  }
}
