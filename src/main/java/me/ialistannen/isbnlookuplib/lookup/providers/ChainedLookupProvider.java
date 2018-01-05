package me.ialistannen.isbnlookuplib.lookup.providers;

import java.util.ArrayList;
import java.util.List;
import me.ialistannen.isbnlookuplib.book.Book;
import me.ialistannen.isbnlookuplib.isbn.Isbn;
import me.ialistannen.isbnlookuplib.lookup.IsbnLookupProvider;
import me.ialistannen.isbnlookuplib.util.Consumer;
import me.ialistannen.isbnlookuplib.util.Optional;

/**
 * Checks all passed providers in the order they are passed.
 */
public class ChainedLookupProvider implements IsbnLookupProvider {

  private List<IsbnLookupProvider> providerList;
  private Consumer<Throwable> errorHandler;

  public ChainedLookupProvider(List<IsbnLookupProvider> providerList) {
    this(providerList, new Consumer<Throwable>() {
      @Override
      public void accept(Throwable throwable) {
        throwable.printStackTrace();
      }
    });
  }

  public ChainedLookupProvider(List<IsbnLookupProvider> providerList,
      Consumer<Throwable> errorHandler) {
    this.providerList = new ArrayList<>(providerList);
    this.errorHandler = errorHandler;
  }

  @Override
  public Optional<Book> lookup(Isbn isbn) {
    for (IsbnLookupProvider provider : providerList) {
      try {
        Optional<Book> bookOptional = provider.lookup(isbn);

        if (bookOptional.isPresent()) {
          return bookOptional;
        }

      } catch (Exception e) {
        errorHandler.accept(e);
      }
    }

    return Optional.empty();
  }
}
