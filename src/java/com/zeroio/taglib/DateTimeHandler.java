/*
 *  Copyright 2000-2004 Matt Rajkowski
 *  matt.rajkowski@teamelements.com
 *  http://www.teamelements.com
 *  This source code cannot be modified, distributed or used without
 *  permission from Matt Rajkowski
 */
package com.zeroio.taglib;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import java.sql.Timestamp;
import java.sql.Date;
import org.aspcfs.utils.DatabaseUtils;
import java.text.*;
import java.util.Locale;
import org.aspcfs.modules.login.beans.UserBean;

/**
 *  This Class formats the specified date/time with the timezone for the current
 *  UserBean session.
 *
 *@author     matt rajkowski
 *@created    September 3, 2003
 *@version    $Id: DateTimeHandler.java,v 1.2 2003/09/26 18:49:25 mrajkowski Exp
 *      $
 */
public class DateTimeHandler extends TagSupport {

  private Timestamp timestamp = null;
  private boolean dateOnly = false;
  private boolean timeOnly = false;
  private int timeFormat = DateFormat.SHORT;
  private int dateFormat = DateFormat.SHORT;
  private String pattern = null;
  private String defaultValue = "";
  private String timeZone = null;
  private boolean showTimeZone = false;


  /**
   *  The date to be formatted
   *
   *@param  tmp  The new timestamp value
   */
  public void setTimestamp(Timestamp tmp) {
    this.timestamp = tmp;
  }


  /**
   *  The date to be formatted
   *
   *@param  tmp  The new timestamp value
   */
  public void setTimestamp(java.util.Date tmp) {
    this.timestamp = new java.sql.Timestamp(tmp.getTime());
  }


  /**
   *  Sets the timestamp attribute of the DateTimeHandler object
   *
   *@param  tmp  The new timestamp value
   */
  public void setTimestamp(java.sql.Date tmp) {
    this.timestamp = new java.sql.Timestamp(tmp.getTime());
  }


  /**
   *  Sets the timestamp attribute of the DateTimeHandler object
   *
   *@param  tmp  The new timestamp value
   */
  public void setTimestamp(java.util.Calendar tmp) {
    this.timestamp = new java.sql.Timestamp(tmp.getTimeInMillis());
  }


  /**
   *  Sets the timeZone attribute of the DateTimeHandler object
   *
   *@param  tmp  The new timeZone value
   */
  public void setTimeZone(String tmp) {
    timeZone = tmp;
  }


  /**
   *  The date to be formatted
   *
   *@param  tmp  The new timestamp value
   */
  public void setTimestamp(String tmp) {
    this.timestamp = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Default in case the date is empty/null
   *
   *@param  tmp  The new default value
   */
  public void setDefault(String tmp) {
    this.defaultValue = tmp;
  }


  /**
   *  Gets the date only without the time
   *
   *@param  dateOnly  The new dateOnly value
   */
  public void setDateOnly(boolean dateOnly) {
    this.dateOnly = dateOnly;
  }


  /**
   *  Gets the date without the time
   *
   *@param  dateOnly  The new dateOnly value
   */
  public void setDateOnly(String dateOnly) {
    this.dateOnly = "true".equals(dateOnly);
  }


  /**
   *  Sets the timeOnly attribute of the DateTimeHandler object
   *
   *@param  timeOnly  The new timeOnly value
   */
  public void setTimeOnly(boolean timeOnly) {
    this.timeOnly = timeOnly;
  }


  /**
   *  Sets the timeOnly attribute of the DateTimeHandler object
   *
   *@param  timeOnly  The new timeOnly value
   */
  public void setTimeOnly(String timeOnly) {
    this.timeOnly = "true".equals(timeOnly);
  }


  /**
   *  Sets a pattern
   *
   *@param  pattern  The new pattern value
   */
  public void setPattern(String pattern) {
    this.pattern = pattern;
  }


  /**
   *  Sets a time format
   *
   *@param  timeFormat  The new timeFormat value
   */
  public void setTimeFormat(int timeFormat) {
    this.timeFormat = timeFormat;
  }


  /**
   *  Sets a time format
   *
   *@param  timeFormat  The new timeFormat value
   */
  public void setTimeFormat(String timeFormat) {
    this.timeFormat = Integer.parseInt(timeFormat);
  }


  /**
   *  Sets the date format
   *
   *@param  dateFormat  The new dateFormat value
   */
  public void setDateFormat(int dateFormat) {
    this.dateFormat = dateFormat;
  }


  /**
   *  Sets the date format
   *
   *@param  dateFormat  The new dateFormat value
   */
  public void setDateFormat(String dateFormat) {
    this.dateFormat = Integer.parseInt(dateFormat);
  }


  /**
   *  Sets the showTimeZone attribute of the DateTimeHandler object
   *
   *@param  tmp  The new showTimeZone value
   */
  public void setShowTimeZone(boolean tmp) {
    this.showTimeZone = tmp;
  }


  /**
   *  Sets the showTimeZone attribute of the DateTimeHandler object
   *
   *@param  tmp  The new showTimeZone value
   */
  public void setShowTimeZone(String tmp) {
    this.showTimeZone = ("yes".equals(tmp) ? true : false);
  }


  /**
   *  Description of the Method
   *
   *@return                   Description of the Return Value
   *@exception  JspException  Description of the Exception
   */
  public int doStartTag() throws JspException {
    try {
      if (timestamp != null && !"".equals(timestamp)) {
        Locale locale = null;
        // Retrieve the user's timezone from their session
        UserBean thisUser = (UserBean) pageContext.getSession().getAttribute("User");
        if (thisUser != null) {
          if (timeZone == null) {
            timeZone = thisUser.getUserRecord().getTimeZone();
          }
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
              formatter.applyPattern(formatter.toPattern() + "yy z");
            } else {
              formatter.applyPattern(formatter.toPattern() + "yy");
            }
          } else {
            SimpleDateFormat dateFormatter = (SimpleDateFormat) SimpleDateFormat.getDateInstance(
                dateFormat, locale);
            dateFormatter.applyPattern(dateFormatter.toPattern() + "yy");

            SimpleDateFormat timeFormatter = (SimpleDateFormat) SimpleDateFormat.getTimeInstance(
                timeFormat, locale);

            if ((showTimeZone) && (timeZone != null)) {
              formatter.applyPattern(dateFormatter.toPattern() + " " + timeFormatter.toPattern() + " z");
            } else {
              formatter.applyPattern(dateFormatter.toPattern() + " " + timeFormatter.toPattern());
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
   *  Description of the Method
   *
   *@return    Description of the Return Value
   */
  public int doEndTag() {
    return EVAL_PAGE;
  }

}

