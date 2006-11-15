/*
 *  Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. DARK HORSE
 *  VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.utils;

import org.apache.commons.codec.binary.Base64;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Variety of methods for Strings
 *
 * @author mrajkowski
 * @version $Id: StringUtils.java,v 1.15.14.1 2003/08/22 19:25:51 mrajkowski
 *          Exp $
 * @created May 21, 2002
 */
public class StringUtils {

  public static String allowed = "-0123456789.";
  private static Random rn = new Random();


  /**
   * Gets the integerNumber attribute of the StringUtils class
   *
   * @param in Description of Parameter
   * @return The integerNumber value
   */
  public static int getIntegerNumber(String in) {
    return Integer.parseInt(getNumber(in));
  }


  /**
   * Gets the doubleNumber attribute of the StringUtils class
   *
   * @param in Description of Parameter
   * @return The doubleNumber value
   */
  public static double getDoubleNumber(String in) {
    return Double.parseDouble(getNumber(in));
  }


  /**
   * Description of the Method
   *
   * @param str Description of Parameter
   * @param o   Description of Parameter
   * @param n   Description of Parameter
   * @return Description of the Returned Value
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
   * Description of the Method
   *
   * @param s Description of Parameter
   * @return Description of the Returned Value
   */
  public static String toString(String s) {
    if (s != null) {
      return (s);
    } else {
      return ("");
    }
  }


  /**
   * Description of the Method
   *
   * @param s Description of Parameter
   * @return Description of the Returned Value
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
   * Description of the Method
   *
   * @param s Description of Parameter
   * @return Description of the Returned Value
   */
  public static String toHtmlValue(String s) {
    if (s != null) {
      String htmlReady = s.trim();
      htmlReady = replace(htmlReady, "&", "&amp;");
      htmlReady = replace(htmlReady, "\"", "&quot;");
      htmlReady = replace(htmlReady, "<", "&lt;");
      htmlReady = replace(htmlReady, ">", "&gt;");
      htmlReady = replace(htmlReady, "\r\n", "<br>");
      htmlReady = replace(htmlReady, "\n\r", "<br>");
      htmlReady = replace(htmlReady, "\n", "<br>");
      htmlReady = replace(htmlReady, "\r", "<br>");
      htmlReady = replace(htmlReady, "//lt;", "<");
      htmlReady = replace(htmlReady, "//gt;", ">");

      //a second call to the function (done in surveys)
      //changes <br> to &lt;br&gt;, this needs to be reverted
      htmlReady = replace(htmlReady, "&lt;br&gt;", "<br>");

      htmlReady = toHtmlChars(htmlReady);
      return (htmlReady);
    } else {
      return ("");
    }
  }


  /**
   * Description of the Method
   *
   * @param htmlReady Description of the Parameter
   * @return Description of the Return Value
   */
  public static String toHtmlChars(String htmlReady) {
    if (htmlReady != null) {
      htmlReady = replace(htmlReady, "\u00A1", "&iexcl;");
      htmlReady = replace(htmlReady, "\u00A2", "&cent;");
      htmlReady = replace(htmlReady, "\u00A3", "&pound;");
      htmlReady = replace(htmlReady, "\u00A4", "&curren;");
      htmlReady = replace(htmlReady, "\u00A5", "&yen;");
      htmlReady = replace(htmlReady, "\u00A6", "&brvbar;");
      htmlReady = replace(htmlReady, "\u00A7", "&sect;");
      htmlReady = replace(htmlReady, "\u00A8", "&uml;");
      htmlReady = replace(htmlReady, "\u00A9", "&copy;");
      htmlReady = replace(htmlReady, "\u00AA", "&ordf;");
      htmlReady = replace(htmlReady, "\u00AB", "&laquo;");
      htmlReady = replace(htmlReady, "\u00AC", "&not;");
      htmlReady = replace(htmlReady, "\u00AD", "&shy;");
      htmlReady = replace(htmlReady, "\u00AE", "&reg;");
      htmlReady = replace(htmlReady, "\u00AF", "&macr;");
      htmlReady = replace(htmlReady, "\u00B0", "&deg;");
      htmlReady = replace(htmlReady, "\u00B1", "&plusmn;");
      htmlReady = replace(htmlReady, "\u00B2", "&sup2;");
      htmlReady = replace(htmlReady, "\u00B3", "&sup3;");
      htmlReady = replace(htmlReady, "\u00B4", "&acute;");
      htmlReady = replace(htmlReady, "\u00B5", "&micro;");
      htmlReady = replace(htmlReady, "\u00B6", "&para;");
      htmlReady = replace(htmlReady, "\u00B7", "&middot;");
      htmlReady = replace(htmlReady, "\u00B8", "&cedil;");
      htmlReady = replace(htmlReady, "\u00B9", "&sup1;");
      htmlReady = replace(htmlReady, "\u00BA", "&ordm;");
      htmlReady = replace(htmlReady, "\u00BB", "&raquo;");
      htmlReady = replace(htmlReady, "\u00BC", "&frac14;");
      htmlReady = replace(htmlReady, "\u00BD", "&frac12;");
      htmlReady = replace(htmlReady, "\u00BE", "&frac34;");
      htmlReady = replace(htmlReady, "\u00BF", "&iquest;");
      htmlReady = replace(htmlReady, "\u00C0", "&Agrave;");
      htmlReady = replace(htmlReady, "\u00C1", "&Aacute;");
      htmlReady = replace(htmlReady, "\u00C2", "&Acirc;");
      htmlReady = replace(htmlReady, "\u00C3", "&Atilde;");
      htmlReady = replace(htmlReady, "\u00C4", "&Auml;");
      htmlReady = replace(htmlReady, "\u00C5", "&Aring;");
      htmlReady = replace(htmlReady, "\u00C6", "&AElig;");
      htmlReady = replace(htmlReady, "\u00C7", "&Ccedil;");
      htmlReady = replace(htmlReady, "\u00C8", "&Egrave;");
      htmlReady = replace(htmlReady, "\u00C9", "&Eacute;");
      htmlReady = replace(htmlReady, "\u00CA", "&Ecirc;");
      htmlReady = replace(htmlReady, "\u00CB", "&Euml;");
      htmlReady = replace(htmlReady, "\u00CC", "&Igrave;");
      htmlReady = replace(htmlReady, "\u00CD", "&Iacute;");
      htmlReady = replace(htmlReady, "\u00CE", "&Icirc;");
      htmlReady = replace(htmlReady, "\u00CF", "&Iuml;");
      htmlReady = replace(htmlReady, "\u00D0", "&ETH;");
      htmlReady = replace(htmlReady, "\u00D1", "&Ntilde;");
      htmlReady = replace(htmlReady, "\u00D2", "&Ograve;");
      htmlReady = replace(htmlReady, "\u00D3", "&Oacute;");
      htmlReady = replace(htmlReady, "\u00D4", "&Ocirc;");
      htmlReady = replace(htmlReady, "\u00D5", "&Otilde;");
      htmlReady = replace(htmlReady, "\u00D6", "&Ouml;");
      htmlReady = replace(htmlReady, "\u00D7", "&times;");
      htmlReady = replace(htmlReady, "\u00D8", "&Oslash;");
      htmlReady = replace(htmlReady, "\u00D9", "&Ugrave;");
      htmlReady = replace(htmlReady, "\u00DA", "&Uacute;");
      htmlReady = replace(htmlReady, "\u00DB", "&Ucirc;");
      htmlReady = replace(htmlReady, "\u00DC", "&Uuml;");
      htmlReady = replace(htmlReady, "\u00DD", "&Yacute;");
      htmlReady = replace(htmlReady, "\u00DE", "&THORN;");
      htmlReady = replace(htmlReady, "\u00DF", "&szlig;");
      htmlReady = replace(htmlReady, "\u00E0", "&agrave;");
      htmlReady = replace(htmlReady, "\u00E1", "&aacute;");
      htmlReady = replace(htmlReady, "\u00E2", "&acirc;");
      htmlReady = replace(htmlReady, "\u00E3", "&atilde;");
      htmlReady = replace(htmlReady, "\u00E4", "&auml;");
      htmlReady = replace(htmlReady, "\u00E5", "&aring;");
      htmlReady = replace(htmlReady, "\u00E6", "&aelig;");
      htmlReady = replace(htmlReady, "\u00E7", "&ccedil;");
      htmlReady = replace(htmlReady, "\u00E8", "&egrave;");
      htmlReady = replace(htmlReady, "\u00E9", "&eacute;");
      htmlReady = replace(htmlReady, "\u00EA", "&ecirc;");
      htmlReady = replace(htmlReady, "\u00EB", "&euml;");
      htmlReady = replace(htmlReady, "\u00EC", "&igrave;");
      htmlReady = replace(htmlReady, "\u00ED", "&iacute;");
      htmlReady = replace(htmlReady, "\u00EE", "&icirc;");
      htmlReady = replace(htmlReady, "\u00EF", "&iuml;");
      htmlReady = replace(htmlReady, "\u00F0", "&eth;");
      htmlReady = replace(htmlReady, "\u00F1", "&ntilde;");
      htmlReady = replace(htmlReady, "\u00F2", "&ograve;");
      htmlReady = replace(htmlReady, "\u00F3", "&oacute;");
      htmlReady = replace(htmlReady, "\u00F4", "&ocirc;");
      htmlReady = replace(htmlReady, "\u00F5", "&otilde;");
      htmlReady = replace(htmlReady, "\u00F6", "&ouml;");
      htmlReady = replace(htmlReady, "\u00F7", "&divide;");
      htmlReady = replace(htmlReady, "\u00F8", "&oslash;");
      htmlReady = replace(htmlReady, "\u00F9", "&ugrave;");
      htmlReady = replace(htmlReady, "\u00FA", "&uacute;");
      htmlReady = replace(htmlReady, "\u00FB", "&ucirc;");
      htmlReady = replace(htmlReady, "\u00FC", "&uuml;");
      htmlReady = replace(htmlReady, "\u00FD", "&yacute;");
      htmlReady = replace(htmlReady, "\u00FE", "&thorn;");
      htmlReady = replace(htmlReady, "\u00FF", "&yuml;");
      htmlReady = replace(htmlReady, "\u20AC", "&euro;");
      return (htmlReady);
    } else {
      return ("");
    }
  }


  /**
   * Description of the Method
   *
   * @param s Description of the Parameter
   * @return Description of the Return Value
   */
  public static String toPseudoHtmlValue(String s) {
    String htmlReady = toHtmlValue(s);
    htmlReady = replace(htmlReady, "<br>", "\r\n");
    return htmlReady;
  }


  /**
   * This method converts HTML back to text. Used especially for converting a
   * hidden html form element into a text area
   *
   * @param s Description of the Parameter
   * @return Description of the Return Value
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
   * Description of the Method
   *
   * @param s Description of the Parameter
   * @return Description of the Return Value
   */
  public static String toHtmlText(String s) {
    s = replace(s, "<br>\r\n", "<br>");
    s = replace(s, "\r\n", "<br>");
    return s;
  }


  /**
   * Description of the Method
   *
   * @param s Description of the Parameter
   * @return Description of the Return Value
   */
  public static String toHtmlTextValue(String s) {
    s = replace(s, "<br>\r\n", "\r\n");
    s = replace(s, "<br />\r\n", "\r\n");
    s = replace(s, "<br>", "\r\n");
    s = replace(s, "<br />", "\r\n");
    return s;
  }


  /**
   * Description of the Method
   *
   * @param s Description of the Parameter
   * @return Description of the Return Value
   */
  public static String toHtmlTextBlank(String s) {
    String htmlReady = replace(s, "<br>", "");
    return htmlReady;
  }


  /**
   * Description of the Method
   *
   * @param inDate Description of Parameter
   * @return Description of the Returned Value
   */
  public static String toDateTimeString(java.sql.Timestamp inDate) {
    try {
      return java.text.DateFormat.getDateTimeInstance(
          java.text.DateFormat.SHORT, java.text.DateFormat.LONG).format(
          inDate);
    } catch (NullPointerException e) {
    }
    return "";
  }


  /**
   * Description of the Method
   *
   * @param inDate Description of the Parameter
   * @return Description of the Return Value
   */
  public static String toDateTimeString(java.util.Date inDate) {
    try {
      return java.text.DateFormat.getDateTimeInstance(
          java.text.DateFormat.SHORT, java.text.DateFormat.LONG).format(
          inDate);
    } catch (NullPointerException e) {
    }
    return "";
  }


  /**
   * Description of the Method
   *
   * @param inDate Description of Parameter
   * @return Description of the Returned Value
   */
  public static String toDateString(java.sql.Timestamp inDate) {
    try {
      return java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT).format(
          inDate);
    } catch (NullPointerException e) {
    }
    return "";
  }


  /**
   * Description of the Method
   *
   * @param inDate Description of Parameter
   * @return Description of the Returned Value
   */
  public static String toDateString(java.sql.Date inDate) {
    try {
      return java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT).format(
          inDate);
    } catch (NullPointerException e) {
    }
    return "";
  }


  /**
   * Description of the Method
   *
   * @param inDate Description of Parameter
   * @return Description of the Returned Value
   */
  public static String toLongDateString(java.util.Date inDate) {
    try {
      return java.text.DateFormat.getDateInstance(java.text.DateFormat.LONG).format(
          inDate);
    } catch (NullPointerException e) {
    }
    return "";
  }


  /**
   * Description of the Method
   *
   * @param inDate Description of Parameter
   * @return Description of the Returned Value
   */
  public static String toFullDateString(java.util.Date inDate) {
    try {
      return java.text.DateFormat.getDateInstance(java.text.DateFormat.FULL).format(
          inDate);
    } catch (NullPointerException e) {
    }
    return "";
  }


  /**
   * Description of the Method
   *
   * @param s Description of Parameter
   * @return Description of the Returned Value
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
   * Description of the Method
   *
   * @param in Description of Parameter
   * @return Description of the Returned Value
   */
  public static boolean hasText(String in) {
    return (in != null && !(("".equals(in.trim()) || "null".equals(in))));
  }


  /**
   * Gets the number attribute of the StringUtils class
   *
   * @param in Description of Parameter
   * @return The number value
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
   * Gets the numbersOnly attribute of the StringUtils class
   *
   * @param in Description of the Parameter
   * @return The numbersOnly value
   */
  public static String getNumbersOnly(String in) {
    String allowed = "0123456789";
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < in.length(); i++) {
      if (allowed.indexOf(in.charAt(i)) > -1) {
        sb.append(in.charAt(i));
      }
    }
    return sb.toString();
  }


  /**
   * Loads text into a string from a file
   *
   * @param file Description of the Parameter
   * @return Description of the Return Value
   * @throws java.io.IOException Description of the Exception
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
   * Loads text into a string from a file
   *
   * @param filename Description of the Parameter
   * @return Description of the Return Value
   * @throws java.io.IOException Description of the Exception
   */
  public static String loadText(String filename) throws java.io.IOException {
    String ls = System.getProperty("line.separator");
    StringBuffer text = new StringBuffer();
    BufferedReader in = new BufferedReader(new FileReader(filename));
    String line = null;
    boolean hasLine = false;
    while ((line = in.readLine()) != null) {
      if (hasLine) {
        text.append(ls);
      }
      text.append(line);
      hasLine = true;
    }
    in.close();
    return text.toString();
  }


  /**
   * Description of the Method
   *
   * @param filename       Description of the Parameter
   * @param lines          Description of the Parameter
   * @param ignoreComments Description of the Parameter
   * @throws java.io.IOException Description of the Exception
   */
  public static void loadText(String filename, ArrayList lines, boolean ignoreComments) throws java.io.IOException {
    BufferedReader in = new BufferedReader(new FileReader(filename));
    String line = null;
    while ((line = in.readLine()) != null) {
      if (!ignoreComments || (ignoreComments && !line.startsWith("#") && !"".equals(
          line.trim()))) {
        lines.add(line);
      }
    }
    in.close();
  }


  /**
   * Writes a string of text to a file
   *
   * @param filename Description of the Parameter
   * @param data     Description of the Parameter
   * @throws java.io.IOException Description of the Exception
   */
  public static void saveText(String filename, String data) throws java.io.IOException {
    BufferedWriter out = new BufferedWriter(new FileWriter(filename));
    out.write(data);
    out.close();
  }


  /**
   * Adds a feature to the S attribute of the StringUtils class
   *
   * @param count The feature to be added to the S attribute
   * @return Description of the Return Value
   */
  public static String addS(long count) {
    if (count == 1) {
      return "";
    } else {
      return "s";
    }
  }


  /**
   * Adds a feature to the ES attribute of the StringUtils class
   *
   * @param count The feature to be added to the ES attribute
   * @return Description of the Return Value
   */
  public static String addES(long count) {
    if (count == 1) {
      return "";
    } else {
      return "es";
    }
  }


  /**
   * Convert the string to an int, or if not an int, return the specified
   * default value
   *
   * @param tmp          Description of the Parameter
   * @param defaultValue Description of the Parameter
   * @return Description of the Return Value
   */
  public static int parseInt(String tmp, int defaultValue) {
    try {
      return Integer.parseInt(tmp);
    } catch (Exception e) {
      return defaultValue;
    }
  }


  /**
   * Description of the Method
   *
   * @param tmp          Description of the Parameter
   * @param defaultValue Description of the Parameter
   * @return Description of the Return Value
   */
  public static double parseDouble(String tmp, double defaultValue) {
    try {
      return Double.parseDouble(tmp);
    } catch (Exception e) {
      return defaultValue;
    }
  }


  /**
   * Reads a line of text in the Excel CSV format<br>
   * Each field is separated by a comma, except some fields have quotes around
   * them because the field has commas or spaces.<br>
   * TODO: Test to see how quotes within fields are treated
   *
   * @param line Description of the Parameter
   * @return Description of the Return Value
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
   * Converts a delimited String to an ArrayList
   *
   * @param str       Description of the Parameter
   * @param tokenizer Description of the Parameter
   * @return Description of the Return Value
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
   * Replaces all occurances of the matching pattern in the source string
   *
   * @param source      Description of the Parameter
   * @param replacement Description of the Parameter
   * @param thisPattern Description of the Parameter
   * @return Description of the Return Value
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
   * Generates a random string of letters with the resulting length being
   * between the specified lo and hi range
   *
   * @param lo Description of the Parameter
   * @param hi Description of the Parameter
   * @return Description of the Return Value
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
   * Returns a random number that falls within the specified range
   *
   * @param lo Description of the Parameter
   * @param hi Description of the Parameter
   * @return Description of the Return Value
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
   * Mimics the Javascript ESCAPE function
   *
   * @param s Description of the Parameter
   * @return Description of the Return Value
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
   * Used to escape special symbols within a JavaScript quoted block
   *
   * @param s Description of the Parameter
   * @return Description of the Return Value
   */
  public static String jsStringEscape(String s) {
    if (s != null) {
      String jsReady = s.trim();
      jsReady = replace(jsReady, "\"", "\\\"");
      jsReady = replace(jsReady, "\\", "\\\\");
      jsReady = replace(jsReady, "\r", "\\r");
      jsReady = replace(jsReady, "\n", "\\n");
      jsReady = replace(jsReady, "\t", "\\t");
      jsReady = replace(jsReady, "'", "\\'");
      return (jsReady);
    } else {
      return ("");
    }
  }


  /**
   * Description of the Method
   *
   * @param inString Description of the Parameter
   * @return Description of the Return Value
   */
  public static String toBase64(String inString) {
    try {
      return new String(Base64.encodeBase64(inString.getBytes("UTF8"), true));
    } catch (Exception e) {
      return "";
    }
  }


  /**
   * Description of the Method
   *
   * @param text Description of the Parameter
   * @param size Description of the Parameter
   * @return Description of the Return Value
   */
  public static String trimToSize(String text, int size) {
    if (text != null && text.length() > size) {
      return (text.substring(0, (size - 1)) + "...");
    } else {
      return text;
    }
  }


  /**
   * Description of the Method
   *
   * @param text Description of the Parameter
   * @param size Description of the Parameter
   * @return Description of the Return Value
   */
  public static String trimToSizeNoDots(String text, int size) {
    if (text != null && text.length() > size) {
      return (text.substring(0, (size)));
    } else {
      return text;
    }
  }


  /**
   * Description of the Method
   *
   * @param str Description of the Parameter
   * @return Description of the Return Value
   */
  public static String parseToDbString(String str) {
    return parseToDbString(str, ",");
  }


  /**
   * Description of the Method
   *
   * @param str        Description of the Parameter
   * @param delimiters Description of the Parameter
   * @return Description of the Return Value
   */
  public static String parseToDbString(String str, String delimiters) {
    StringBuffer result = new StringBuffer("");
    StringTokenizer tokenizer = new StringTokenizer(str, delimiters);
    for (int i = 0; tokenizer.hasMoreTokens(); i++) {
      String token = tokenizer.nextToken();
      if (i > 0) {
        result.append(", ");
      }
      result.append("'" + token.trim() + "'");
    }
    return result.toString();
  }


  /**
   * Gets the lineSeparated attribute of the StringUtils class
   *
   * @param displayList Description of the Parameter
   * @return The lineSeparated value
   */
  public static String getLineSeparated(ArrayList displayList) {
    return getSeparatedBy(displayList, "\r\n");
  }


  /**
   * Gets the commaSeparated attribute of the StringUtils class
   *
   * @param displayList Description of the Parameter
   * @return The commaSeparated value
   */
  public static String getCommaSeparated(ArrayList displayList) {
    return getSeparatedBy(displayList, ",");
  }


  /**
   * Gets the getSeparatedBy attribute of the StringUtils object
   *
   * @param displayList Description of the Parameter
   * @param separator   Description of the Parameter
   * @return The lineSeparated value
   */
  public static String getSeparatedBy(ArrayList displayList, String separator) {
    StringBuffer sb = new StringBuffer();
    try {
      Iterator j = displayList.iterator();
      while (j.hasNext()) {
        String display = (String) j.next();
        if (j.hasNext()) {
          sb.append(display + separator);
        } else {
          sb.append(display);
        }
      }
    } catch (Exception e) {
    }
    return sb.toString().trim();
  }


  /**
   * Description of the Method
   *
   * @param tmp Description of the Parameter
   * @return Description of the Return Value
   */
  public static String valueOf(boolean tmp) {
    if (tmp) {
      return "true";
    } else {
      return "false";
    }
  }

  public static String[] getFirstLastNames(String name) {
    String[] result = new String[2];
    String nameFirst = null;
    String nameLast = null;
    boolean lastFirstFormat = (name.indexOf(",") != -1);
    if (lastFirstFormat) {
      StringTokenizer str = new StringTokenizer(name, ",");
      nameLast = str.nextToken();
      if (str.hasMoreTokens()) {
        nameFirst = str.nextToken();
        if ("".equals(nameFirst.trim())) {
          nameFirst = null;
        }
      }
    } else {
      StringTokenizer str = new StringTokenizer(name);
      nameFirst = str.nextToken();
      if (str.hasMoreTokens()) {
        nameLast = str.nextToken();
      } else {
        nameLast = nameFirst;
        nameFirst = null;
      }
    }
    result[0] = nameFirst;
    result[1] = nameLast;
    return result;
  }

  /**
   * Gets the true attribute of the StringUtils class
   *
   * @param tmp Description of the Parameter
   * @return The true value
   */
  public static boolean isTrue(String tmp) {
    if (tmp != null) {
      return "true".equals(tmp.trim());
    }
    return false;
  }
}

