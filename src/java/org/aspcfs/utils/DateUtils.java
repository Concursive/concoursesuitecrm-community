package org.aspcfs.utils;

import java.sql.*;
import java.text.*;
import java.util.*;

/**
 *  Description of the Class
 *
 *@author     matt rajkowski
 *@created    January 13, 2003
 *@version    $Id$
 */
public class DateUtils {

  /**
   *  Description of the Method
   *
   *@param  tmp  Description of the Parameter
   *@return      Description of the Return Value
   */
  public static java.sql.Date parseDateString(String tmp) {
    java.sql.Date dateValue = null;
    try {
      java.util.Date tmpDate = DateFormat.getDateInstance(DateFormat.SHORT).parse(tmp);
      dateValue = new java.sql.Date(new java.util.Date().getTime());
      dateValue.setTime(tmpDate.getTime());
      return dateValue;
    } catch (Exception e) {
      try {
        return java.sql.Date.valueOf(tmp);
      } catch (Exception e2) {
      }
    }
    return null;
  }


  /**
   *  Description of the Method
   *
   *@param  tmp  Description of the Parameter
   *@return      Description of the Return Value
   */
  public static java.sql.Timestamp parseTimestampString(String tmp) {
    java.sql.Timestamp timestampValue = null;
    try {
      java.util.Date tmpDate = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).parse(tmp);
      timestampValue = new java.sql.Timestamp(new java.util.Date().getTime());
      timestampValue.setTime(tmpDate.getTime());
      return timestampValue;
    } catch (Exception e) {
      try {
        return java.sql.Timestamp.valueOf(tmp);
      } catch (Exception e2) {
      }
    }
    return null;
  }


  /**
   *  parses special String formats into Timestamps
   *
   *@param  tmp     Description of the Parameter
   *@param  format  Description of the Parameter
   *@return         Description of the Return Value
   */
  public static java.sql.Timestamp parseTimestampString(String tmp, String format) {
    java.sql.Timestamp timestampValue = null;
    SimpleDateFormat df = null;
    df = new SimpleDateFormat(format);
    try {
      //java.util.Date tmpDate = DateFormat.getDateTimeInstance().parse(tmp);
      java.util.Date tmpDate = df.parse(tmp, new ParsePosition(0));
      timestampValue = new java.sql.Timestamp(new java.util.Date().getTime());
      timestampValue.setTime(tmpDate.getTime());
      return timestampValue;
    } catch (Exception e) {
      try {
        return java.sql.Timestamp.valueOf(tmp);
      } catch (Exception e2) {
      }
    }
    return null;
  }


  /**
   *  parses special String formats into Dates
   *
   *@param  tmp     Description of the Parameter
   *@param  format  Description of the Parameter
   *@return         Description of the Return Value
   */
  public static java.sql.Date parseDateString(String tmp, String format) {
    java.sql.Date dateValue = null;
    SimpleDateFormat df = null;
    df = new SimpleDateFormat(format);
    try {
      java.util.Date tmpDate = df.parse(tmp, new ParsePosition(0));
      dateValue = new java.sql.Date(new java.util.Date().getTime());
      dateValue.setTime(tmpDate.getTime());
      return dateValue;
    } catch (Exception e) {
      try {
        return java.sql.Date.valueOf(tmp);
      } catch (Exception e2) {
      }
    }
    return null;
  }
}

