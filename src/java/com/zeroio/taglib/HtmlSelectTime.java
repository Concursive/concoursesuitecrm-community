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

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import java.util.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.HtmlSelectHours;
import org.aspcfs.utils.web.HtmlSelectHours24;
import org.aspcfs.utils.web.HtmlSelectMinutesFives;
import org.aspcfs.utils.web.HtmlSelectAMPM;
import org.aspcfs.utils.web.HtmlSelectTimeZone;
import java.sql.Timestamp;
import java.text.DateFormat;
import org.aspcfs.modules.login.beans.UserBean;

/**
 *  Description of the Class
 *
 *@author     matt rajkowski
 *@created    July 2, 2003
 *@version    $Id: HtmlSelectTime.java,v 1.1.4.1 2004/03/19 21:00:49 rvasista
 *      Exp $
 */
public class HtmlSelectTime extends TagSupport {

  private String baseName = null;
  private Timestamp value = null;
  private String timeZone = null;
  private boolean hidden = false;
  private boolean showTimeZone = false;


  /**
   *  Sets the baseName attribute of the HtmlSelectTime object
   *
   *@param  tmp  The new baseName value
   */
  public void setBaseName(String tmp) {
    this.baseName = tmp;
  }


  /**
   *  Sets the value attribute of the HtmlSelectTime object
   *
   *@param  tmp  The new value value
   */
  public void setValue(String tmp) {
    this.setValue(DatabaseUtils.parseTimestamp(tmp));
  }


  /**
   *  Sets the value attribute of the HtmlSelectTime object
   *
   *@param  tmp  The new value value
   */
  public void setValue(java.sql.Timestamp tmp) {
    value = tmp;
  }


  /**
   *  Sets the timeZone attribute of the HtmlSelectTime object
   *
   *@param  tmp  The new timeZone value
   */
  public void setTimeZone(String tmp) {
    timeZone = tmp;
  }


  /**
   *  Sets the hidden attribute of the HtmlSelectTime object
   *
   *@param  tmp  The new hidden value
   */
  public void setHidden(boolean tmp) {
    this.hidden = tmp;
  }


  /**
   *  Sets the hidden attribute of the HtmlSelectTime object
   *
   *@param  tmp  The new hidden value
   */
  public void setHidden(String tmp) {
    this.hidden = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the showTimeZone attribute of the HtmlSelectTime object
   *
   *@param  tmp  The new showTimeZone value
   */
  public void setShowTimeZone(boolean tmp) {
    this.showTimeZone = tmp;
  }


  /**
   *  Sets the showTimeZone attribute of the HtmlSelectTime object
   *
   *@param  tmp  The new showTimeZone value
   */
  public void setShowTimeZone(String tmp) {
    this.showTimeZone = "yes".equals(tmp);
  }


  /**
   *  Description of the Method
   *
   *@return                   Description of the Return Value
   *@exception  JspException  Description of the Exception
   */
  public int doStartTag() throws JspException {
    try {
      Locale locale = Locale.getDefault();
      // Retrieve the user's locale from their session
      UserBean thisUser = (UserBean) pageContext.getSession().getAttribute("User");
      if (thisUser != null) {
        locale = thisUser.getUserRecord().getLocale();
      }
      if (locale == null) {
        locale = Locale.getDefault();
      }
      // Calculate the date and time
      boolean is24Hour = false;
      int hour = -1;
      int minute = -1;
      int AMPM = -1;
      Calendar cal = Calendar.getInstance();
      if (timeZone == null) {
        timeZone = thisUser.getUserRecord().getTimeZone();
      }
      cal.setTimeZone(TimeZone.getTimeZone(timeZone));

      try {
        cal.setTimeInMillis(value.getTime());
      } catch (Exception e) {
        cal.set(Calendar.HOUR, 12);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.AM_PM, Calendar.PM);
      }
      hour = cal.get(Calendar.HOUR);
      minute = cal.get(Calendar.MINUTE);
      AMPM = cal.get(Calendar.AM_PM);
      // Check if parsing has AM/PM else show 24 hour time
      DateFormat formatter = (DateFormat) DateFormat.getTimeInstance(
          DateFormat.SHORT, locale);
      if (formatter.format(cal.getTime()).indexOf("M") == -1) {
        is24Hour = true;
        hour = cal.get(Calendar.HOUR_OF_DAY);
      }
      //System.out.println(formatter.format(cal.getTime()));
      // output the results
      if (!hidden) {
        if (is24Hour) {
          // Show 24 hour selector
          this.pageContext.getOut().write(
              HtmlSelectHours24.getSelect(baseName + "Hour", (hour < 10 ? String.valueOf("0" + hour) : String.valueOf(hour))).toString());
        } else {
          // Show 12 hour selector
          this.pageContext.getOut().write(
              HtmlSelectHours.getSelect(baseName + "Hour", (hour < 10 ? String.valueOf("0" + hour) : String.valueOf(hour))).toString());
        }
        this.pageContext.getOut().write(":");
        this.pageContext.getOut().write(
            HtmlSelectMinutesFives.getSelect(baseName + "Minute",
            (minute < 10 ? String.valueOf("0" + minute) : String.valueOf(minute))).toString());
        if (is24Hour) {
          // Do not show AM/PM
        } else {
          if (AMPM == Calendar.AM) {
            this.pageContext.getOut().write(
                HtmlSelectAMPM.getSelect(baseName + "AMPM", "AM").toString());
          } else {
            this.pageContext.getOut().write(
                HtmlSelectAMPM.getSelect(baseName + "AMPM", "PM").toString());
          }
        }
      } else {
        this.pageContext.getOut().write(
            "<input type=\"hidden\" name=\"" + baseName + "Hour" + "\" value=\"" + String.valueOf(hour) + "\" />");
        this.pageContext.getOut().write(
            "<input type=\"hidden\" name=\"" + baseName + "Minute" + "\" value=\"" + String.valueOf(minute) + "\" />");
        this.pageContext.getOut().write(
            "<input type=\"hidden\" name=\"" + baseName + "AMPM" + "\" value=\"" + String.valueOf(AMPM) + "\" />");
      }

      if (showTimeZone) {
        this.pageContext.getOut().write(HtmlSelectTimeZone.getSelect(baseName + "TimeZone", timeZone).getHtml());
      }
    } catch (Exception e) {
      throw new JspException("HtmlSelectTime Error: " + e.getMessage());
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

