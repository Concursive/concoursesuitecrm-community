package org.aspcfs.utils;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.InputStream;

/**
 * Description of Class
 *
 * @author mrajkowski
 * @version $Id$
 * @created Oct 23, 2006
 */
public class ServletContextUtils {
  /**
   * Load text into a string from the context resource
   *
   * @param context  Description of the Parameter
   * @param filename Description of the Parameter
   * @return Description of the Return Value
   * @throws java.io.IOException Description of the Exception
   */
  public static String loadText(ServletContext context, String filename) throws IOException {
    InputStream in = context.getResourceAsStream(filename);
    StringBuffer text = new StringBuffer();
    byte b[] = new byte[1];
    while (in.read(b) != -1) {
      text.append(new String(b));
    }
    in.close();
    return text.toString();
  }

}
