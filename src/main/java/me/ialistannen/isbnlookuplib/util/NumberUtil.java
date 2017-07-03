package me.ialistannen.isbnlookuplib.util;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

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
  public static Optional<Integer> parseInt(String input) {
    try {
      return Optional.of(Integer.parseInt(input));
    } catch (NumberFormatException e) {
      return Optional.empty();
    }
  }

  /**
   * Parses a String to a double.
   *
   * @param input The input string
   * @return The parsed double, if any
   */
  public static Optional<Double> parseDouble(String input) {
    try {
      return Optional.of(Double.parseDouble(input));
    } catch (NumberFormatException e) {
      return Optional.empty();
    }
  }

  /**
   * Parses a String to a double.
   *
   * @param input The input string
   * @param locale The locale to use
   * @return The parsed double, if any
   */
  public static Optional<Double> parseDouble(String input, Locale locale) {
    try {
      return Optional.of(NumberFormat.getInstance(locale).parse(input).doubleValue());
    } catch (ParseException e) {
      return Optional.empty();
    }
  }
}
