package me.ialistannen.isbnlookuplib.i18n;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

/**
 * A Language. Used to translate keys to their value, depending on the locale.
 */
public class Language {

  private LoadingCache<String, MessageFormat> formatCache = Caffeine.newBuilder()
      .expireAfterAccess(5, TimeUnit.MINUTES) // // TODO: 26.06.17 Why do I even use a cache?
      .build(MessageFormat::new);

  private Locale locale;
  private ResourceBundle bundle;

  /**
   * @param locale The {@link Locale} to use
   * @param category The {@link Category} this belongs to
   * @throws UnknownLanguageException if the passed locale does not equal the actual language
   * returned by the {@link ResourceBundle}. <em>No control flow, yada, yada...</em>
   */
  public Language(Locale locale, Category category) throws UnknownLanguageException {
    this.locale = locale;
    bundle = ResourceBundle.getBundle(category.getPath(), locale);

    if (!bundle.getLocale().equals(getLocale())) {
      throw new UnknownLanguageException(String.format(
          "Unknown locale '%s' for category '%s'. Closest match was '%s'",
          locale.getDisplayName(), category.getPath(), bundle.getLocale().getDisplayName()
      ));
    }
  }

  /**
   * @return The {@link Locale} of this {@link Language}.
   */
  public Locale getLocale() {
    return locale;
  }

  /**
   * Translates a String.
   *
   * @param key The key to get the value for
   * @param formattingObjects The objects to use for formatting
   * @return The translated String
   */
  public String translate(String key, Object... formattingObjects) {
    String value = getValue(key);

    MessageFormat messageFormat = formatCache.get(value);
    if (messageFormat == null) {
      return "An error occurred while loading key '" + key + "'";
    }
    return messageFormat.format(formattingObjects);
  }

  private String getValue(String key) {
    try {
      return bundle.getString(key);
    } catch (MissingResourceException e) {
      return "Key '" + key + "' not found.";
    }
  }
}
