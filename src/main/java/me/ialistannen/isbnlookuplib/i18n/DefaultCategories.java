package me.ialistannen.isbnlookuplib.i18n;

/**
 * Some default categories
 */
public enum DefaultCategories {
  AMAZON_SCRAPER("i18n.amazon_scraper.Messages");

  private Category category;

  DefaultCategories(String category) {
    this.category = new Category(category);
  }

  /**
   * @return The {@link Category}
   */
  public Category getCategory() {
    return category;
  }
}
