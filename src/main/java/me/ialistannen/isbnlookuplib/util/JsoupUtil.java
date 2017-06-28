package me.ialistannen.isbnlookuplib.util;

import java.util.Optional;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.jsoup.select.NodeTraversor;
import org.jsoup.select.NodeVisitor;

/**
 * Some utility classes for {@link org.jsoup.Jsoup}.
 */
public class JsoupUtil {

  /**
   * Converts an {@link Element} to its text, respecting line breaks.
   *
   * @param element The {@link Element} to convert
   * @return The element's text, respecting line breaks
   */
  public static String toStringRespectLinebreak(Element element) {
    StringBuilder builder = new StringBuilder();

    new NodeTraversor(new NodeVisitor() {
      @Override
      public void head(Node node, int depth) {
        if (node instanceof TextNode) {
          String text = ((TextNode) node).text();
          text = StringUtil.sanitizeSpaces(text);
          builder.append(text);
        } else if (node instanceof Element) {
          if (((Element) node).tagName().equals("br")) {
            builder.append("\n");
          }
        }
      }

      @Override
      public void tail(Node node, int depth) {
      }
    }).traverse(element);

    return builder.toString();
  }

  /**
   * Returns the first element of an {@link Elements} collection.
   *
   * @param elements The {@link Elements} to get the first one from
   * @return The first element if any
   */
  public static Optional<Element> getFirst(Elements elements) {
    return elements.isEmpty() ? Optional.empty() : Optional.of(elements.get(0));
  }
}
