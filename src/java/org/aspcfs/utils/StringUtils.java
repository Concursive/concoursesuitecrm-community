package org.aspcfs.utils;

import java.util.ArrayList;
import java.util.zip.*;
import java.util.regex.*;
import java.io.*;
import java.util.*;
import javax.servlet.ServletContext;
import sun.misc.*;

/**
 *  Variety of methods for Strings
 *
 *@author     mrajkowski
 *@created    May 21, 2002
 *@version    $Id: StringUtils.java,v 1.15.14.1 2003/08/22 19:25:51 mrajkowski
 *      Exp $
 */
public class StringUtils {

  public static String allowed = "-0123456789.";
  private static Random rn = new Random();


  /**
   *  Gets the integerNumber attribute of the StringUtils class
   *
   *@param  in  Description of Parameter
   *@return     The integerNumber value
   */
  public static int getIntegerNumber(String in) {
    return Integer.parseInt(getNumber(in));
  }


  /**
   *  Gets the doubleNumber attribute of the StringUtils class
   *
   *@param  in  Description of Parameter
   *@return     The doubleNumber value
   */
  public static double getDoubleNumber(String in) {
    return Double.parseDouble(getNumber(in));
  }


  /**
   *  Description of the Method
   *
   *@param  str  Description of Parameter
   *@param  o    Description of Parameter
   *@param  n    Description of Parameter
   *@return      Description of the Returned Value
   */
  public static String replace(String str, String o, String n) {
    boolean all = true;
    if (str != null && o != null && o.length() > 0 && n != null) {
      StringBuffer result = null;
      int oldpos = 0;
      do {
        int pos = str.indexOf(o, oldpos);
        if (pos < 0) {
          break;
        }
        if (result == null) {
          result = new StringBuffer();
        }
        result.append(str.substring(oldpos, pos));
        result.append(n);
        pos += o.length();
        oldpos = pos;
      } while (all);
      if (oldpos == 0) {
        return str;
      } else {
        result.append(str.substring(oldpos));
        return result.toString();
      }
    } else {
      return str;
    }
  }


  /**
   *  Description of the Method
   *
   *@param  s  Description of Parameter
   *@return    Description of the Returned Value
   */
  public static String toString(String s) {
    if (s != null) {
      return (s);
    } else {
      return ("");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  s  Description of Parameter
   *@return    Description of the Returned Value
   */
  public static String toHtml(String s) {
    if (s != null) {
      if (s.trim().equals("")) {
        return ("&nbsp;");
      } else {
        return toHtmlValue(s);
      }
    } else {
      return ("&nbsp;");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  s  Description of Parameter
   *@return    Description of the Returned Value
   */
  public static String toHtmlValue(String s) {
    if (s != null) {
      String htmlReady = s.trim();
      htmlReady = replace(htmlReady, "\"", "&quot;");
      htmlReady = replace(htmlReady, "<", "&lt;");
      htmlReady = replace(htmlReady, ">", "&gt;");
      htmlReady = replace(htmlReady, "\r\n", "<br>");
      htmlReady = replace(htmlReady, "\n\r", "<br>");
      htmlReady = replace(htmlReady, "\n", "<br>");
      htmlReady = replace(htmlReady, "\r", "<br>");
      htmlReady = replace(htmlReady, "/&lt;", "<");
      htmlReady = replace(htmlReady, "/&gt;", ">");
      return (htmlReady);
    } else {
      return ("");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  s  Description of the Parameter
   *@return    Description of the Return Value
   */
  public static String toPseudoHtmlValue(String s) {
    String htmlReady = toHtmlValue(s);
    htmlReady = replace(htmlReady, "<br>", "\r\n");
    return htmlReady;
  }


  /**
   *  This method converts HTML back to text. Used especially for converting a
   *  hidden html form element into a text area
   *
   *@param  s  Description of the Parameter
   *@return    Description of the Return Value
   */
  public static String fromHtmlValue(String s) {
    if (s != null) {
      String htmlReady = s.trim();
      htmlReady = replace(htmlReady, "&quot;", "\"");
      htmlReady = replace(htmlReady, "&lt;", "<");
      htmlReady = replace(htmlReady, "&gt;", ">");
      htmlReady = replace(htmlReady, "&nbsp;", " ");
      htmlReady = replace(htmlReady, "<br>", "\r\n");
      htmlReady = replace(htmlReady, "<br />", "\r\n");
      return (htmlReady);
    } else {
      return ("");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  s  Description of the Parameter
   *@return    Description of the Return Value
   */
  public static String toHtmlText(String s) {
    s = replace(s, "<br>\r\n", "<br>");
    s = replace(s, "\r\n", "<br>");
    return s;
  }


  /**
   *  Description of the Method
   *
   *@param  s  Description of the Parameter
   *@return    Description of the Return Value
   */
  public static String toHtmlTextValue(String s) {
    s = replace(s, "<br>\r\n", "\r\n");
    s = replace(s, "<br />\r\n", "\r\n");
    s = replace(s, "<br>", "\r\n");
    s = replace(s, "<br />", "\r\n");
    return s;
  }


  /**
   *  Description of the Method
   *
   *@param  s  Description of the Parameter
   *@return    Description of the Return Value
   */
  public static String toHtmlTextBlank(String s) {
    String htmlReady = replace(s, "<br>", "");
    return htmlReady;
  }


  /**
   *  Description of the Method
   *
   *@param  inDate  Description of Parameter
   *@return         Description of the Returned Value
   */
  public static String toDateTimeString(java.sql.Timestamp inDate) {
    try {
      return java.text.DateFormat.getDateTimeInstance(java.text.DateFormat.SHORT, java.text.DateFormat.LONG).format(inDate);
    } catch (NullPointerException e) {
    }
    return "";
  }


  /**
   *  Description of the Method
   *
   *@param  inDate  Description of the Parameter
   *@return         Description of the Return Value
   */
  public static String toDateTimeString(java.util.Date inDate) {
    try {
      return java.text.DateFormat.getDateTimeInstance(java.text.DateFormat.SHORT, java.text.DateFormat.LONG).format(inDate);
    } catch (NullPointerException e) {
    }
    return "";
  }


  /**
   *  Description of the Method
   *
   *@param  inDate  Description of Parameter
   *@return         Description of the Returned Value
   */
  public static String toDateString(java.sql.Timestamp inDate) {
    try {
      return java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT).format(inDate);
    } catch (NullPointerException e) {
    }
    return "";
  }


  /**
   *  Description of the Method
   *
   *@param  inDate  Description of Parameter
   *@return         Description of the Returned Value
   */
  public static String toDateString(java.sql.Date inDate) {
    try {
      return java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT).format(inDate);
    } catch (NullPointerException e) {
    }
    return "";
  }


  /**
   *  Description of the Method
   *
   *@param  inDate  Description of Parameter
   *@return         Description of the Returned Value
   */
  public static String toLongDateString(java.util.Date inDate) {
    try {
      return java.text.DateFormat.getDateInstance(java.text.DateFormat.LONG).format(inDate);
    } catch (NullPointerException e) {
    }
    return "";
  }


  /**
   *  Description of the Method
   *
   *@param  inDate  Description of Parameter
   *@return         Description of the Returned Value
   */
  public static String toFullDateString(java.util.Date inDate) {
    try {
      return java.text.DateFormat.getDateInstance(java.text.DateFormat.FULL).format(inDate);
    } catch (NullPointerException e) {
    }
    return "";
  }


  /**
   *  Description of the Method
   *
   *@param  s  Description of Parameter
   *@return    Description of the Returned Value
   */
  public static String sqlReplace(String s) {
    //s = replace(s, "<br>", "\r");

    String newString = "";
    char[] input = s.toCharArray();
    int arraySize = input.length;
    for (int i = 0; i < arraySize; i++) {
      if (input[i] == '\'') {
        newString += "\\\'";
      } else if (input[i] == '\\') {
        newString += "\\\\";
      } else {
        newString += input[i];
      }
    }
    return newString;
  }


  /**
   *  Description of the Method
   *
   *@param  in  Description of Parameter
   *@return     Description of the Returned Value
   */
  public static boolean hasText(String in) {
    return (in != null && !("".equals(in)));
  }


  /**
   *  Gets the number attribute of the StringUtils class
   *
   *@param  in  Description of Parameter
   *@return     The number value
   */
  private static String getNumber(String in) {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < in.length(); i++) {
      if (allowed.indexOf(in.charAt(i)) > -1) {
        sb.append(in.charAt(i));
      }
    }
    return sb.toString();
  }


  /**
   *  Loads text into a string from a file
   *
   *@param  file                     Description of the Parameter
   *@return                          Description of the Return Value
   *@exception  java.io.IOException  Description of the Exception
   */
  public static String loadText(File file) throws java.io.IOException {
    String ls = System.getProperty("line.separator");
    StringBuffer text = new StringBuffer();
    BufferedReader in = new BufferedReader(new FileReader(file));
    String line = null;
    while ((line = in.readLine()) != null) {
      text.append(line);
      text.append(ls);
    }
    in.close();
    return text.toString();
  }


  /**
   *  Loads text into a string from a file
   *
   *@param  filename                 Description of the Parameter
   *@return                          Description of the Return Value
   *@exception  java.io.IOException  Description of the Exception
   */
  public static String loadText(String filename) throws java.io.IOException {
    String ls = System.getProperty("line.separator");
    StringBuffer text = new StringBuffer();
    BufferedReader in = new BufferedReader(new FileReader(filename));
    String line = null;
    while ((line = in.readLine()) != null) {
      text.append(line);
      text.append(ls);
    }
    in.close();
    return text.toString();
  }


  /**
   *  Load text into a string from the context resource
   *
   *@param  context          Description of the Parameter
   *@param  filename         Description of the Parameter
   *@return                  Description of the Return Value
   *@exception  IOException  Description of the Exception
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


  /**
   *  Writes a string of text to a file
   *
   *@param  filename                 Description of the Parameter
   *@param  data                     Description of the Parameter
   *@exception  java.io.IOException  Description of the Exception
   */
  public static void saveText(String filename, String data) throws java.io.IOException {
    BufferedWriter out = new BufferedWriter(new FileWriter(filename));
    out.write(data);
    out.close();
  }


  /**
   *  Adds a feature to the S attribute of the StringUtils class
   *
   *@param  count  The feature to be added to the S attribute
   *@return        Description of the Return Value
   */
  public static String addS(long count) {
    if (count == 1) {
      return "";
    } else {
      return "s";
    }
  }


  /**
   *  Adds a feature to the ES attribute of the StringUtils class
   *
   *@param  count  The feature to be added to the ES attribute
   *@return        Description of the Return Value
   */
  public static String addES(long count) {
    if (count == 1) {
      return "";
    } else {
      return "es";
    }
  }


  /**
   *  Convert the string to an int, or if not an int, return the specified
   *  default value
   *
   *@param  tmp           Description of the Parameter
   *@param  defaultValue  Description of the Parameter
   *@return               Description of the Return Value
   */
  public static int parseInt(String tmp, int defaultValue) {
    try {
      return Integer.parseInt(tmp);
    } catch (Exception e) {
      return defaultValue;
    }
  }


  /**
   *  Description of the Method
   *
   *@param  tmp           Description of the Parameter
   *@param  defaultValue  Description of the Parameter
   *@return               Description of the Return Value
   */
  public static double parseDouble(String tmp, double defaultValue) {
    try {
      return Double.parseDouble(tmp);
    } catch (Exception e) {
      return defaultValue;
    }
  }


  /**
   *  Reads a line of text in the Excel CSV format<br>
   *  Each field is separated by a comma, except some fields have quotes around
   *  them because the field has commas or spaces.<br>
   *  TODO: Test to see how quotes within fields are treated
   *
   *@param  line  Description of the Parameter
   *@return       Description of the Return Value
   */
  public static ArrayList parseExcelCSVLine(String line) {
    if (line == null) {
      return null;
    }
    ArrayList thisRecord = new ArrayList();
    boolean quote = false;
    boolean completeField = false;
    StringBuffer value = new StringBuffer("");
    for (int i = 0; i < line.length(); i++) {
      char thisChar = line.charAt(i);
      if (thisChar == ',') {
        if (quote == false) {
          completeField = true;
        } else {
          value.append(thisChar);
        }
      } else if (thisChar == '\"') {
        if (quote) {
          quote = false;
        } else {
          quote = true;
        }
      } else {
        value.append(thisChar);
      }
      if (i == line.length() - 1) {
        completeField = true;
      }
      if (completeField) {
        thisRecord.add(value.toString());
        value = new StringBuffer("");
        quote = false;
        completeField = false;
      }
    }
    return thisRecord;
  }


  /**
   *  Converts a delimited String to an ArrayList
   *
   *@param  str        Description of the Parameter
   *@param  tokenizer  Description of the Parameter
   *@return            Description of the Return Value
   */
  public static ArrayList toArrayList(String str, String tokenizer) {
    ArrayList convertedList = null;
    StringTokenizer tokens = new StringTokenizer(str, tokenizer);
    while (tokens.hasMoreTokens()) {
      if (convertedList == null) {
        convertedList = new ArrayList();
      }
      convertedList.add(tokens.nextToken());
    }
    return convertedList;
  }


  /**
   *  Replaces all occurances of the matching pattern in the source string
   *
   *@param  source       Description of the Parameter
   *@param  replacement  Description of the Parameter
   *@param  thisPattern  Description of the Parameter
   *@return              Description of the Return Value
   */
  public static String replacePattern(String source, String thisPattern, String replacement) {
    Pattern pattern = Pattern.compile(thisPattern);
    Matcher match = pattern.matcher(source);
    if (match.find()) {
      return match.replaceAll(replacement);
    }
    return source;
  }


  /**
   *  Generates a random string of letters with the resulting length being
   *  between the specified lo and hi range
   *
   *@param  lo  Description of the Parameter
   *@param  hi  Description of the Parameter
   *@return     Description of the Return Value
   */
  public static String randomString(int lo, int hi) {
    int n = rand(lo, hi);
    byte b[] = new byte[n];
    for (int i = 0; i < n; i++) {
      b[i] = (byte) rand('a', 'z');
    }
    return new String(b);
  }


  /**
   *  Returns a random number that falls within the specified range
   *
   *@param  lo  Description of the Parameter
   *@param  hi  Description of the Parameter
   *@return     Description of the Return Value
   */
  public static int rand(int lo, int hi) {
    int n = hi - lo + 1;
    int i = rn.nextInt() % n;
    if (i < 0) {
      i = -i;
    }
    return lo + i;
  }


  /**
   *  Mimics the Javascript ESCAPE function
   *
   *@param  s  Description of the Parameter
   *@return    Description of the Return Value
   */
  public static String jsEscape(String s) {
    if (s != null) {
      String jsReady = s.trim();
      jsReady = replace(jsReady, "%", "%25");
      jsReady = replace(jsReady, "\r\n", "%0A");
      jsReady = replace(jsReady, "\r", "%0A");
      jsReady = replace(jsReady, "\n", "%0A");
      jsReady = replace(jsReady, "\"", "%22");
      jsReady = replace(jsReady, "\\", "%5C");
      jsReady = replace(jsReady, "!", "%21");
      jsReady = replace(jsReady, "@", "%40");
      jsReady = replace(jsReady, "#", "%23");
      jsReady = replace(jsReady, "$", "%24");
      jsReady = replace(jsReady, "^", "%5E");
      jsReady = replace(jsReady, "&", "%26");
      jsReady = replace(jsReady, "'", "%27");
      jsReady = replace(jsReady, "(", "%28");
      jsReady = replace(jsReady, ")", "%29");
      jsReady = replace(jsReady, "=", "%3D");
      jsReady = replace(jsReady, " ", "%20");
      jsReady = replace(jsReady, "|", "%7C");
      jsReady = replace(jsReady, ",", "%2C");
      jsReady = replace(jsReady, ":", "%3A");
      jsReady = replace(jsReady, ";", "%3B");
      jsReady = replace(jsReady, "<", "%3C");
      jsReady = replace(jsReady, ">", "%3E");
      jsReady = replace(jsReady, "?", "%3F");
      jsReady = replace(jsReady, "[", "%5B");
      jsReady = replace(jsReady, "]", "%5D");
      jsReady = replace(jsReady, "{", "%7B");
      jsReady = replace(jsReady, "}", "%7D");
      jsReady = replace(jsReady, "`", "%60");
      jsReady = replace(jsReady, "~", "%7E");
      return (jsReady);
    } else {
      return ("");
    }
  }
  
  
  /**
   *  Used to escape special symbols within a JavaScript quoted block
   *
   *@param  s  Description of the Parameter
   *@return    Description of the Return Value
   */
  public static String jsStringEscape(String s) {
    if (s != null) {
      String jsReady = s.trim();
      jsReady = replace(jsReady, "'", "\\'");
      jsReady = replace(jsReady, "\"", "\\\"");
      return (jsReady);
    } else {
      return ("");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  inString  Description of the Parameter
   *@return           Description of the Return Value
   */
  public static String toBase64(String inString) {
    try {
      BASE64Encoder encoder = new BASE64Encoder();
      return (encoder.encode(inString.getBytes("UTF8")));
    } catch (Exception e) {
      return "";
    }
  }


  /**
   *  Description of the Method
   *
   *@param  text  Description of the Parameter
   *@param  size  Description of the Parameter
   *@return       Description of the Return Value
   */
  public static String trimToSize(String text, int size) {
    if (text != null && text.length() > size) {
      return (text.substring(0, (size -1)) + "...");
    } else {
      return text;
    }
  }


  /**
   *  Description of the Method
   *
   *@param  text  Description of the Parameter
   *@param  size  Description of the Parameter
   *@return       Description of the Return Value
   */
  public static String trimToSizeNoDots(String text, int size) {
    if (text != null && text.length() > size) {
      return (text.substring(0, (size)));
    } else {
      return text;
    }
  }
}

