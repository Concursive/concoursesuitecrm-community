package org.aspcfs.utils;

import java.util.zip.*;
import java.io.*;

/**
 *  Variety of methods for Strings
 *
 *@author     mrajkowski
 *@created    May 21, 2002
 *@version    $Id$
 */
public class StringUtils {

  public static String allowed = "-0123456789.";


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
   *  Description of the Method
   *
   *@param  s  Description of the Parameter
   *@return    Description of the Return Value
   */
  public static String toHtmlText(String s) {
    String htmlReady = replace(s, "\r\n", "<br>");
    return htmlReady;
  }


  /**
   *  Description of the Method
   *
   *@param  s  Description of the Parameter
   *@return    Description of the Return Value
   */
  public static String toHtmlTextValue(String s) {
    String htmlReady = replace(s, "<br>", "\r\n");
    return htmlReady;
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
   *  Description of the Method
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
    return text.toString();
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
}

