package org.theseus.beans;

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;
import java.io.*;
import org.theseus.actions.*;
import java.text.*;

/**
 *@author     Kevin Duffey
 *@created    Novemeber 19, 2000
 *@version    $Id$
 */

public class GenericBean implements Serializable {
  protected HashMap errors = new HashMap();

  protected SimpleDateFormat shortDateFormat = new SimpleDateFormat("M/d/yyyy");
  protected SimpleDateFormat shortTimeFormat = new SimpleDateFormat("h:mm a");
  protected SimpleDateFormat shortDateTimeFormat = new SimpleDateFormat("M/d/yyyy h:mm:ss a");
  protected SimpleDateFormat longDateTimeFormat = new SimpleDateFormat("MMMMM d, yyyy hh:mm a");
  protected SimpleDateFormat longDateFormat = new SimpleDateFormat("MMMMM d, yyyy");
  protected SimpleDateFormat longTimeFormat = new SimpleDateFormat("hh:mm a");
  private String message = "";
  private boolean displayMessage = false;
  private Object entity = null;
  private Object ejbRef = null;
  final static long serialVersionUID = 8345648404174283569L;
  private final static String[] HTMLChars = new String[]{"&", "\"", "<", ">"};
  private final static String[] HTMLRepl = new String[]{"&amp;", "&quot;", "&lt;", "&gt;"};


  /**
   *  Creates new DefaultBean
   *
   *@since    1.1
   */
  public GenericBean() { }


  /**
   *  Sets the entity that this bean (or descendant of this bean) will use as
   *  its internal entity type
   *
   *@param  value  The new Entity value
   *@since         1.1
   */
  public void setEntity(Object value) {
    entity = value;
  }


  /**
   *  Set the EJB Reference
   *
   *@param  o  The new EjbRef value
   *@since     1.1
   */
  public void setEjbRef(Object o) {
    ejbRef = o;
  }


  /**
   *  Used to set a bean message that can be used in JSP pages that are being
   *  used as part of an XSL transformation. This is done because request
   *  attributes are not passed on to the JSP page. Therefore the bean must keep
   *  track of the message.
   *
   *@param  value  The new Message value
   *@since         1.1
   */
  public void setMessage(String value) {
    displayMessage = true;
    // lets the getMessage() method know its ok to display this message
    message = value;
  }



  /**
   *  Returns this bean's (or descendant bean's) entity reference (Whether it be
   *  EJB or a core class)
   *
   *@return    The Entity value
   *@since     1.1
   */
  public Object getEntity() {
    return entity;
  }



  /**
   *  Returns the EJB reference if one is used
   *
   *@return    The EjbRef value
   *@since     1.1
   */
  public Object getEjbRef() {
    return ejbRef;
  }



  /**
   *  Returns a message if one exists, otherwise its an empty string. Because
   *  this is a session scope object, we need a way to return this message only
   *  once, unless the flag is set again. Every time the flag is set, we return
   *  the message and also reset the flag. If its not set, we don't return the
   *  message, even if a call to getMessage() is made. This way, only one
   *  message per request is displayed. Also, we reset the message back to "" if
   *  the flag is not set and the getMessage() call is made.
   *
   *@return    The Message value
   *@since     1.1
   */
  public String getMessage() {
    if (displayMessage) {
      displayMessage = false;
    } else {
      message = "";
    }

    return message;
  }


  /**
   *  Gets the Errors attribute of the GenericBean object<p>
   *
   *  The HashMap is used for form validation when errors need to be presented
   *  in the resulting JSP
   *
   *@return    The Errors value
   *@since     1.4
   */
  public HashMap getErrors() {
    return errors;
  }


  /**
   *  Returns whether the object has added any errors to the error HashMap
   *
   *@return    Description of the Returned Value
   *@since     1.4
   */
  public boolean hasErrors() {
    return (errors.size() > 0);
  }


  /**
   *  Returns a decoded HTML string that replaces & with &amp; < with &lt; >
   *  with &gt; and " with &quot; Just make sure if you want to replace a single
   *  character that also appears in another position of a replacement string,
   *  you will end up replacing the replaced string unless it is first in the
   *  list. For example, & = &amp; " = &quot; If you replace " first, then &,
   *  all the &quot; that were replaced will now appear in the final string as
   *  &amp;quot; because the & of the &quot; was replaced with &amp; since the &
   *  replacement started "after" the " replacement did.
   *
   *@param  value  Description of Parameter
   *@return        Description of the Returned Value
   *@since         1.1
   */
  public final static String decodeHtml(String value) {
    return decode(value, HTMLChars, HTMLRepl);
  }


  /**
   *  Description of the Method
   *
   *@param  value  Description of Parameter
   *@return        Description of the Returned Value
   *@since         1.1
   */
  public final static String decode(String value) {
    // if the input is null, no need to go further.
    if (value == null) {
      return null;
    }

    // if the input doesn't have any & " > <, no need to create a new object and go further
    if ((value.indexOf('&') > -1) || (value.indexOf('"') > -1) || (value.indexOf('<') > -1) || (value.indexOf('>') > -1)) {
      StringBuffer sb = new StringBuffer();
      int sze = value.length();

      for (int cntr = 0; cntr < sze; cntr++) {
        char c = value.charAt(cntr);

        switch (c) {
          case '&':
            sb.append("&amp;");
            break;
          case '"':
            sb.append("&quot;");
            break;
          case '<':
            sb.append("&lt;");
            break;
          case '>':
            sb.append("&gt;");
            break;
          default:
            sb.append(c);
        }
      }

      return sb.toString();
    }

    return value;
  }



  /**
   *  Returns a string that has any strings matching the chars[] array with
   *  those in the repl[] array element at the same element the match was found
   *  in the chars[] array
   *
   *@param  value  Description of Parameter
   *@param  chars  Description of Parameter
   *@param  repl   Description of Parameter
   *@return        Description of the Returned Value
   *@since         1.1
   */
  public final static String decode(String value, String[] chars, String[] repl) {
    // return null if the value, chars[], repl[] are null or the number
    // of elemetns of the chars[] and repl[] are not the same.
    if (value == null || chars == null || repl == null || chars.length != repl.length) {
      return null;
    }

    int sze = chars.length;
    StringBuffer sb = new StringBuffer(value);
    for (int cntr = 0; cntr < sze; cntr++) {
      int curPos = 0;
      int oldPos = 0;

      while ((curPos = sb.toString().indexOf(chars[cntr], oldPos)) > -1) {
        // found a match, so replace this occurrence of the string
        // with the same element in the repl[] array
        sb.replace(curPos, curPos + chars[cntr].length(), repl[cntr]);
        oldPos = curPos + chars[cntr].length() + 1;
      }
    }

    return sb.toString();
  }


  /**
   *  Converts a string to a date object
   *
   *@param  thisString  Description of Parameter
   *@return             Description of the Returned Value
   *@since              1.5
   */
  public final static java.util.Date convertStringToDate(String thisString, int dateFormat) {
    java.util.Date thisDate = null;
    try {
      thisDate = java.text.DateFormat.getDateInstance(dateFormat).parse(thisString);
    } catch (java.text.ParseException pe) {
    }
    return thisDate;
  }


  /**
   *  Converts a string to a SQLDate object
   *
   *@param  thisString  Description of Parameter
   *@return             Description of the Returned Value
   *@since              1.5
   */
  public final static java.sql.Date convertStringToSqlDate(String thisString, int dateFormat) {
    java.util.Date thisDate = null;
    try {
      thisDate = java.text.DateFormat.getDateInstance(dateFormat).parse(thisString);
      return new java.sql.Date(thisDate.getTime());
    } catch (java.text.ParseException pe) {
    }
    return null;
  }
  
  /**
   *  Rounds a float to the specified decimal places
   *
   *@param  thisPercent  Description of Parameter
   *@return              Description of the Returned Value
   *@since               1.6
   */
  public final static double round(double x, int decimals) {  
    // rounds to the nearest integer
    int factor = 1;
    for (int i = 0; i < Math.abs(decimals); i++) factor *= 10;
    if (decimals < 0) return factor * Math.rint(x / factor);
    else return Math.rint(factor * x) / factor;
  }
  
  public final static String replace(String str, String o, String n) {
    boolean all = true;
    if (str != null && o != null && o.length() > 0 && n != null) { 
      StringBuffer result = null;
      int oldpos = 0;
      do {
          int pos = str.indexOf(o, oldpos);
          if (pos < 0)
              break;
          if (result == null)
              result = new StringBuffer();
          result.append(str.substring(oldpos, pos));
          result.append(n);
          pos += o.length();
          oldpos = pos;
      } while (all);
      if (oldpos == 0) {
          return str;
      } else {
          result.append(str.substring(oldpos));
          return new String(result);
      }
    } else {
      return str;
    }
  }
  
  public final static String toString(String s) {
    if (s != null) {
      return(s);
    } else {
      return("");
    }
  }
  
  public final static String toHtml(String s) {
    if (s != null) {
      if (s.trim().equals("")) {
        return ("&nbsp;");
      } else {
        return toHtmlValue(s);
      }
    } else {
      return("&nbsp;");
    }
  }
  
  
  public final static String toHtmlValue(String s) {
    if (s != null) {
      String htmlReady = s.trim();
      htmlReady = replace(htmlReady, "\"", "&quot;");
      htmlReady = replace(htmlReady, "<", "&lt;");
      htmlReady = replace(htmlReady, ">", "&gt;");
      htmlReady = replace(htmlReady, "\r", "<br>");
      htmlReady = replace(htmlReady, "/&lt;", "<");
      htmlReady = replace(htmlReady, "/&gt;", ">");
      return(htmlReady);
    } else {
      return("");
    }
  }
}

