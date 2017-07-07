package me.ialistannen.isbnlookuplib.util;

/**
 * A price. A combination of a currency and a value.
 *
 * <p><em>The class is meant for <strong>displaying prices only</strong>. No format is enforced by
 * this class.</em>
 */
public class Price {

  private double price; // good enough. Not doing arithmetic here
  private String currencyIdentifier;

  /**
   * @param price The price as a double
   * @param currencyIdentifier The currency identifier. Not required to be anything automatically
   * convertible.
   */
  public Price(double price, String currencyIdentifier) {
    this.price = price;
    this.currencyIdentifier = currencyIdentifier;
  }

  /**
   * <em>I return a double! This is not enough if you want to do any kind of arithmetic with
   * currency.</em>
   *
   * @return The price.
   */
  public double getPrice() {
    return price;
  }

  /**
   * <em>The identifier is <strong>not</strong> required to be convertible to a real currency. It is
   * designed to help a human when reading it.</em>
   *
   * @return The currency identifier.
   */
  public String getCurrencyIdentifier() {
    return currencyIdentifier;
  }

  @Override
  public String toString() {
    return "Price{"
        + "price=" + price
        + ", currencyIdentifier='" + currencyIdentifier + '\''
        + '}';
  }
}
