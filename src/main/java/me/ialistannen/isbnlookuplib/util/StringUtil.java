package me.ialistannen.isbnlookuplib.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Some utility functions for Strings.
 */
class StringUtil {

  private static Set<Character> SPACE_EQUIVALENTS = new HashSet<>(Arrays.asList(
      '\u00A0', // no break space
      '\u2003', // EM space
      '\u200B' // 0 width space
  ));

  /**
   * Converts all spaces to normal ones.
   *
   * @param string The string to sanitize spaces in
   * @return The same string but all spaces converted to normal ones
   */
  static String sanitizeSpaces(String string) {
    String result = string;
    for (Character spaceEquivalent : SPACE_EQUIVALENTS) {
      result = result.replace(spaceEquivalent, ' ');
    }
    return result;
  }

}
