/*
 *  Copyright(c) 2004 Team Elements LLC (http://www.teamelements.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Team Elements LLC. Permission to use, copy, and modify this
 *  material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. TEAM
 *  ELEMENTS MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL TEAM ELEMENTS LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR ANY
 *  DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package com.zeroio.taglib;

import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * This Class formats the specified date/time with the timezone for the current
 * UserBean session.
 *
 * @author matt rajkowski
 * @version $Id: DateTimeHandler.java,v 1.2 2003/09/26 18:49:25 mrajkowski Exp
 *          $
 * @created September 3, 2003
 */
public class DateTimeHandler extends TagSupport implements TryCatchFinally {

  private Timestamp timestamp = null;
  private boolean dateOnly = false;
  private boolean timeOnly = false;
  private int timeFormat = DateFormat.SHORT;
  private int dateFormat = DateFormat.SHORT;
  private String pattern = null;
  private String defaultValue = "";
  private String timeZone = null;
  private boolean showTimeZone = false;
  private boolean userTimeZone = true;

  public void doCatch(Throwable throwable) throws Throwable {
    // Required but not needed
  }

  public void doFinally() {
    // Reset each property or else the value gets reused
    timestamp = null;
    dateOnly = false;
    timeOnly = false;
    timeFormat = DateFormat.SHORT;
    dateFormat = DateFormat.SHORT;
    pattern = null;
    defaultValue = "";
    timeZone = null;
    showTimeZone = false;
    userTimeZone = true;
  }


  /**
   * The date to be formatted
   *
   * @param tmp The new timestamp value
   */
  public void setTimestamp(Timestamp tmp) {
    this.timestamp = tmp;
  }


  /**
   * The date to be formatted
   *
   * @param tmp The new timestamp value
   */
  public void setTimestamp(java.util.Date tmp) {
    this.timestamp = new java.sql.Timestamp(tmp.getTime());
  }


  /**
   * Sets the timestamp attribute of the DateTimeHandler object
   *
   * @param tmp The new timestamp value
   */
  public void setTimestamp(java.sql.Date tmp) {
    this.timestamp = new java.sql.Timestamp(tmp.getTime());
  }


  /**
   * Sets the timestamp attribute of the DateTimeHandler object
   *
   * @param tmp The new timestamp value
   */
  public void setTimestamp(java.util.Calendar tmp) {
    this.timestamp = new java.sql.Timestamp(tmp.getTimeInMillis());
  }


  /**
   * Sets the timeZone attribute of the DateTimeHandler object
   *
   * @param tmp The new timeZone value
   */
  public void setTimeZone(String tmp) {
    timeZone = tmp;
  }


  /**
   * The date to be formatted
   *
   * @param tmp The new timestamp value
   */
  public void setTimestamp(String tmp) {
    this.timestamp = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Default in case the date is empty/null
   *
   * @param tmp The new default value
   */
  public void setDefault(String tmp) {
    this.defaultValue = tmp;
  }


  /**
   * Gets the date only without the time
   *
   * @param dateOnly The new dateOnly value
   */
  public void setDateOnly(boolean dateOnly) {
    this.dateOnly = dateOnly;
  }


  /**
   * Gets the date without the time
   *
   * @param dateOnly The new dateOnly value
   */
  public void setDateOnly(String dateOnly) {
    this.dateOnly = "true".equals(dateOnly);
  }


  /**
   * Sets the timeOnly attribute of the DateTimeHandler object
   *
   * @param timeOnly The new timeOnly value
   */
  public void setTimeOnly(boolean timeOnly) {
    this.timeOnly = timeOnly;
  }


  /**
   * Sets the timeOnly attribute of the DateTimeHandler object
   *
   * @param timeOnly The new timeOnly value
   */
  public void setTimeOnly(String timeOnly) {
    this.timeOnly = "true".equals(timeOnly);
  }


  /**
   * Sets a pattern
   *
   * @param pattern The new pattern value
   */
  public void setPattern(String pattern) {
    this.pattern = pattern;
  }


  /**
   * Sets a time format
   *
   * @param timeFormat The new timeFormat value
   */
  public void setTimeFormat(int timeFormat) {
    this.timeFormat = timeFormat;
  }


  /**
   * Sets a time format
   *
   * @param timeFormat The new timeFormat value
   */
  public void setTimeFormat(String timeFormat) {
    this.timeFormat = Integer.parseInt(timeFormat);
  }


  /**
   * Sets the date format
   *
   * @param dateFormat The new dateFormat value
   */
  public void setDateFormat(int dateFormat) {
    this.dateFormat = dateFormat;
  }


  /**
   * Sets the date format
   *
   * @param dateFormat The new dateFormat value
   */
  public void setDateFormat(String dateFormat) {
    this.dateFormat = Integer.parseInt(dateFormat);
  }


  /**
   * Sets the showTimeZone attribute of the DateTimeHandler object
   *
   * @param tmp The new showTimeZone value
   */
  public void setShowTimeZone(boolean tmp) {
    this.showTimeZone = tmp;
  }


  /**
   * Sets the showTimeZone attribute of the DateTimeHandler object
   *
   * @param tmp The new showTimeZone value
   */
  public void setShowTimeZone(String tmp) {
    this.showTimeZone = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the userTimeZone attribute of the DateTimeHandler object
   *
   * @param tmp The new userTimeZone value
   */
  public void setUserTimeZone(boolean tmp) {
    this.userTimeZone = tmp;
  }


  /**
   * Sets the userTimeZone attribute of the DateTimeHandler object
   *
   * @param tmp The new userTimeZone value
   */
  public void setUserTimeZone(String tmp) {
    this.userTimeZone = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   * @throws JspException Description of the Exception
   */
  public int doStartTag() throws JspException {
    try {
      if (timestamp != null && !"".equals(timestamp)) {
        Locale locale = null;
        // Retrieve the user's timezone from their session
        UserBean thisUser = (UserBean) pageContext.getSession().getAttribute(
            "User");
        if (thisUser != null) {
          if (userTimeZone) {
            if (timeZone == null) {
              timeZone = thisUser.getUserRecord().getTimeZone();
            }
          }
          // Still use the user's locale
          locale = thisUser.getUserRecord().getLocale();
        }
        if (locale == null) {
          locale = Locale.getDefault();
        }

        //Format the specified timestamp with the retrieved timezone
        SimpleDateFormat formatter = null;
        if (dateOnly) {
          formatter = (SimpleDateFormat) SimpleDateFormat.getDateInstance(
              dateFormat, locale);
        } else if (timeOnly) {
          formatter = (SimpleDateFormat) SimpleDateFormat.getTimeInstance(
              timeFormat, locale);
        } else {
          formatter = (SimpleDateFormat) SimpleDateFormat.getDateTimeInstance(
              dateFormat, timeFormat, locale);
        }

        //set the pattern
        if (pattern != null) {
          formatter.applyPattern(pattern);
        } else {
          if (dateOnly) {
            if ((showTimeZone) && (timeZone != null)) {
              formatter.applyPattern(
                  DateUtils.get4DigitYearDateFormat(formatter.toPattern()) + " z");
            } else {
              formatter.applyPattern(
                  DateUtils.get4DigitYearDateFormat(formatter.toPattern()));
            }
          } else if (timeOnly) {
            if ((showTimeZone) && (timeZone != null)) {
              formatter.applyPattern(
                  DateUtils.get4DigitYearDateFormat(formatter.toPattern()) + " z");
            } else {
              formatter.applyPattern(
                  DateUtils.get4DigitYearDateFormat(formatter.toPattern()));
            }
          } else {
            SimpleDateFormat dateFormatter = (SimpleDateFormat) SimpleDateFormat.getDateInstance(
                dateFormat, locale);
            dateFormatter.applyPattern(
                DateUtils.get4DigitYearDateFormat(dateFormatter.toPattern()));

            SimpleDateFormat timeFormatter = (SimpleDateFormat) SimpleDateFormat.getTimeInstance(
                timeFormat, locale);

            if ((showTimeZone) && (timeZone != null)) {
              formatter.applyPattern(
                  dateFormatter.toPattern() + " " + timeFormatter.toPattern() + " z");
            } else {
              formatter.applyPattern(
                  dateFormatter.toPattern() + " " + timeFormatter.toPattern());
            }
          }
        }
        //set the timezone
        if (timeZone != null) {
          java.util.TimeZone tz = java.util.TimeZone.getTimeZone(timeZone);
          formatter.setTimeZone(tz);
        }
        this.pageContext.getOut().write(formatter.format(timestamp));
      } else {
        //no date found, output default
        this.pageContext.getOut().write(defaultValue);
      }
    } catch (Exception e) {
    }
    return SKIP_BODY;
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   */
  public int doEndTag() {
    return EVAL_PAGE;
  }
}

