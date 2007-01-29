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

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.modules.login.beans.UserBean;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Useful methods for working with dates and date fields
 *
 * @author matt rajkowski
 * @version $Id$
 * @created January 13, 2003
 */
public class DateUtils {

  /**
   * Takes a string and tries to convert it to a Date
   *
   * @param tmp Description of the Parameter
   * @return Description of the Return Value
   */
  public static java.sql.Date parseDateString(String tmp) {
    java.sql.Date dateValue = null;
    try {
      java.util.Date tmpDate = DateFormat.getDateInstance(DateFormat.SHORT).parse(
          tmp);
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
   * Takes a string and tries to convert it to a Timestamp
   *
   * @param tmp Description of the Parameter
   * @return Description of the Return Value
   */
  public static java.sql.Timestamp parseTimestampString(String tmp) {
    java.sql.Timestamp timestampValue = null;
    try {
      java.util.Date tmpDate = DateFormat.getDateTimeInstance(
          DateFormat.SHORT, DateFormat.LONG).parse(tmp);
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
   * Takes a string and tries to convert it to a Timestamp based on the
   * specified formatting
   *
   * @param tmp    Description of the Parameter
   * @param format Description of the Parameter
   * @return Description of the Return Value
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
   * Takes a string and tries to convert it to a Date based on the specified
   * formatting
   *
   * @param tmp    Description of the Parameter
   * @param format Description of the Parameter
   * @return Description of the Return Value
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


  /**
   * Gets the dateString attribute of the DateUtils object
   *
   * @param date       Description of the Parameter
   * @param timeZone   Description of the Parameter
   * @param dateFormat Description of the Parameter
   * @param timeFormat Description of the Parameter
   * @return The dateString value
   */
  public static String getUserToServerDateTimeString(TimeZone timeZone, int dateFormat, int timeFormat, String date) {
    return getUserToServerDateTimeString(
        timeZone, dateFormat, timeFormat, date, Locale.getDefault());
  }


  /**
   * Gets the userToServerDateTimeString attribute of the DateUtils class
   *
   * @param timeZone   Description of the Parameter
   * @param dateFormat Description of the Parameter
   * @param timeFormat Description of the Parameter
   * @param date       Description of the Parameter
   * @param locale     Description of the Parameter
   * @return The userToServerDateTimeString value
   */
  public static String getUserToServerDateTimeString(TimeZone timeZone, int dateFormat, int timeFormat, String date, Locale locale) {
    String convertedDate = null;
    try {
      DateFormat localeFormatter = DateFormat.getDateInstance(
          dateFormat, locale);
      if (timeZone != null) {
        localeFormatter.setTimeZone(timeZone);
      }
      DateFormat serverFormatter = DateFormat.getDateTimeInstance(
          dateFormat, timeFormat);
      //convertedDate = serverFormatter.format(localeFormatter.parse(date));
      convertedDate = serverFormatter.format(
          new java.util.Date(localeFormatter.parse(date).getTime()));
    } catch (Exception e) {
      if (date != null && !"".equals(date)) {
        System.err.println("EXCEPTION: DateUtils-> Timestamp " + e);
      }
    }
    return convertedDate;
  }


  /**
   * Gets the userToServerDateTime attribute of the DateUtils class
   *
   * @param timeZone   Description of the Parameter
   * @param dateFormat Description of the Parameter
   * @param timeFormat Description of the Parameter
   * @param date       Description of the Parameter
   * @param locale     Description of the Parameter
   * @return The userToServerDateTime value
   */
  public static Timestamp getUserToServerDateTime(TimeZone timeZone, int dateFormat, int timeFormat, String date, Locale locale) {
    try {
      DateFormat localeFormatter = DateFormat.getDateInstance(
          dateFormat, locale);
      if (timeZone != null) {
        localeFormatter.setTimeZone(timeZone);
      }
      localeFormatter.setLenient(false);
      return new Timestamp(localeFormatter.parse(date).getTime());
    } catch (Exception e) {
      if (date != null && !"".equals(date)) {
        System.err.println("EXCEPTION: DateUtils-> Timestamp " + e);
      }
    }
    return null;
  }


  /**
   * Returns the converted server time based on the current calendar time and
   * timezone of the user
   *
   * @param cal      Description of the Parameter
   * @param timeZone Description of the Parameter
   * @return The userToServerDateTime value
   */
  public static java.sql.Timestamp getUserToServerDateTime(Calendar cal, TimeZone timeZone) {
    java.sql.Timestamp timestampValue = null;
    try {
      String date = getDateString(cal);
      DateFormat localFormatter = DateFormat.getDateInstance(DateFormat.SHORT);
      if (timeZone != null) {
        localFormatter.setTimeZone(timeZone);
      }
      timestampValue = new java.sql.Timestamp(
          localFormatter.parse(date).getTime());
    } catch (Exception e) {
      System.out.println(
          "DateUtils-> getUserToServerDateTime Exception" + e.toString());
    }
    return timestampValue;
  }


  /**
   * Gets the serverToUserDateString attribute of the DateUtils class
   *
   * @param timeZone   Description of the Parameter
   * @param dateFormat Description of the Parameter
   * @param date       Description of the Parameter
   * @return The serverToUserDateString value
   */
  public static String getServerToUserDateString(TimeZone timeZone, int dateFormat, java.sql.Timestamp date) {
    SimpleDateFormat formatter = null;
    try {
      // Used by the Calendar for storing events in an array
      formatter = (SimpleDateFormat) SimpleDateFormat.getDateInstance(
          dateFormat);
      formatter.applyPattern("M/d/yyyy");
      formatter.setTimeZone(timeZone);
    } catch (Exception e) {
      System.err.println("EXCEPTION: DateUtils -> Timestamp " + date);
    }
    return formatter.format((java.util.Date) date);
  }


  /**
   * Gets the serverToUserDateTimeString attribute of the DateUtils class
   *
   * @param timeZone   Description of the Parameter
   * @param dateFormat Description of the Parameter
   * @param timeFormat Description of the Parameter
   * @param date       Description of the Parameter
   * @return The serverToUserDateTimeString value
   */
  public static String getServerToUserDateTimeString(TimeZone timeZone, int dateFormat, int timeFormat, java.sql.Timestamp date) {
    SimpleDateFormat formatter = null;
    try {
      //TODO: combine the Locale when User Locale that has been implemented
      formatter = (SimpleDateFormat) SimpleDateFormat.getDateTimeInstance(
          dateFormat, timeFormat);
      if (timeZone != null) {
        formatter.setTimeZone(timeZone);
      }
    } catch (Exception e) {
      System.err.println("EXCEPTION: DateUtils -> Timestamp " + date);
    }
    return formatter.format(date);
  }


  /**
   * Returns the current time from a calendar object
   *
   * @param cal Description of the Parameter
   * @return The date value
   */
  public static java.util.Date getDate(Calendar cal) {
    java.util.Date convertedDate = null;
    try {
      SimpleDateFormat formatter = (SimpleDateFormat) SimpleDateFormat.getDateInstance(
          DateFormat.SHORT);
      formatter.applyPattern("M/d/yyyy");
      String tmpDate = (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(
          Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.YEAR);
      convertedDate = formatter.parse(tmpDate);
    } catch (Exception e) {
      System.err.println("EXCEPTION: DateUtils -> Timestamp ");
    }
    return convertedDate;
  }


  /**
   * Returns the current date of the calendar in the m/d/yyyy format as a
   * string
   *
   * @param cal Description of the Parameter
   * @return The dateString value
   */
  public static String getDateString(Calendar cal) {
    return (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(
        Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.YEAR);
  }


  /**
   * Returns a directory structure based on the date supplied
   *
   * @param fileDate Description of the Parameter
   * @return The datePath value
   */
  public static String getDatePath(java.util.Date fileDate) {
    return getDatePath(new java.sql.Timestamp(fileDate.getTime()));
  }


  /**
   * Returns a directory structure based on the timestamp supplied, used for
   * the fileLibrary: yyyy/MMdd/
   *
   * @param fileDate Description of Parameter
   * @return The DatePath value
   */
  public static String getDatePath(java.sql.Timestamp fileDate) {
    if(fileDate == null){
      return "0000" + System.getProperty("file.separator") + "0000" + System.getProperty("file.separator");
    }
    SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy");
    String datePathToUse1 = formatter1.format(fileDate);
    SimpleDateFormat formatter2 = new SimpleDateFormat("MMdd");
    String datePathToUse2 = formatter2.format(fileDate);
    return datePathToUse1 + System.getProperty("file.separator") + datePathToUse2 + System.getProperty(
        "file.separator");
  }


  /**
   * Returns a string that is suitable for a filename based on a timestamp
   *
   * @return The filename value
   */
  public static String getFilename() {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    return formatter.format(new java.util.Date());
  }


  /**
   * The HTML date/time selector works on 5's so round up to next 5
   *
   * @param millis Description of the Parameter
   * @return Description of the Return Value
   */
  public static Timestamp roundUpToNextFive(long millis) {
    Calendar cal = Calendar.getInstance();
    while (cal.get(Calendar.MINUTE) % 5 != 0) {
      cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE) + 1);
    }
    return new Timestamp(cal.getTimeInMillis());
  }


  /**
   * Calculates the number of days between two calendar days in a manner which
   * is independent of the Calendar type used.
   *
   * @param d1 The first date.
   * @param d2 The second date.
   * @return The number of days between the two dates. Zero is returned if
   *         the dates are the same, one if the dates are adjacent, etc. The order
   *         of the dates does not matter, the value returned is always >= 0. If
   *         Calendar types of d1 and d2 are different, the result may not be
   *         accurate.
   */
  static int getDaysBetween(java.util.Calendar d1, java.util.Calendar d2) {
    if (d1.after(d2)) {
      // swap dates so that d1 is start and d2 is end
      java.util.Calendar swap = d1;
      d1 = d2;
      d2 = swap;
    }
    int days = d2.get(java.util.Calendar.DAY_OF_YEAR) -
        d1.get(java.util.Calendar.DAY_OF_YEAR);
    int y2 = d2.get(java.util.Calendar.YEAR);
    if (d1.get(java.util.Calendar.YEAR) != y2) {
      d1 = (java.util.Calendar) d1.clone();
      do {
        days += d1.getActualMaximum(java.util.Calendar.DAY_OF_YEAR);
        d1.add(java.util.Calendar.YEAR, 1);
      } while (d1.get(java.util.Calendar.YEAR) != y2);
    }
    return days;
  }


  /**
   * Accesses a valid user to return a date in the user's locale
   *
   * @param context
   * @param date
   * @return
   */
  public static String getDateAsString(java.sql.Timestamp date, ActionContext context) {
    Locale locale = null;
    UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
    if (thisUser != null) {
      locale = thisUser.getLocale();
    }
    if (locale == null) {
      locale = Locale.getDefault();
    }
    return getDateAsString(date, locale);
  }


  /**
   * Gets the dateAsString attribute of the DateUtils class
   *
   * @param tmpTimestamp Description of the Parameter
   * @param locale       Description of the Parameter
   * @return The dateAsString value
   */
  public static String getDateAsString(Timestamp tmpTimestamp, Locale locale) {
    String dateAsString = "";
    try {
      SimpleDateFormat formatter = (SimpleDateFormat) SimpleDateFormat.getDateInstance(
          DateFormat.SHORT, locale);
      formatter.applyPattern(get4DigitYearDateFormat(formatter.toPattern()));
      dateAsString = formatter.format(tmpTimestamp);
    } catch (Exception e) {
    }
    return dateAsString;
  }


  /**
   * Returns a date format with four digit year pattern
   *
   * @param tmpDateFormat Description of the Parameter
   * @return Description of the Return Value
   */
  public static String get4DigitYearDateFormat(String tmpDateFormat) {
    if (tmpDateFormat.indexOf("yy") > -1 && tmpDateFormat.indexOf("yyyy") == -1) {
      return StringUtils.replace(tmpDateFormat, "yy", "yyyy");
    } else {
      return tmpDateFormat;
    }
  }

  public static boolean isWeekend(Calendar cal) {
    return (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
        cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY);
  }
}

