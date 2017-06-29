package me.ialistannen.isbnlookuplib.i18n;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle.Control;

/**
 * A category of language files.
 */
public class Category {

  private String path;
  private Map<Locale, Language> localeMap = new HashMap<>();

  /**
   * @param path The Path to the resources for this category
   * @see java.util.ResourceBundle#getBundle(String, Locale, ClassLoader, Control)
   */
  Category(String path) {
    this.path = path;
  }

  /**
   * Returns a {@link Language} for the given {@link Locale}.
   *
   * @param locale The {@link Locale} to get
   * @return The assigned {@link Language}, if any
   */
  public Optional<Language> getLanguage(Locale locale) {
    if (localeMap.containsKey(locale)) {
      return Optional.ofNullable(localeMap.get(locale));
    }

    try {
      Language language = new Language(locale, this);
      localeMap.put(locale, language);
      return Optional.of(language);
    } catch (UnknownLanguageException e) {
      return Optional.empty();
    }
  }

  /**
   * @return The path of the {@link Category}
   */
  String getPath() {
    return path;
  }
}
