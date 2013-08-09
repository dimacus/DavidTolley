package grid.SeleniumGridExtrasplugin;

/**
 * Created with IntelliJ IDEA. User: dima Date: 8/7/13 Time: 2:00 PM To change this template use
 * File | Settings | File Templates.
 */
public class StringWrapper {

  public static String sanitize(String input) {
    return input.replaceAll("\"", "");
  }

}
