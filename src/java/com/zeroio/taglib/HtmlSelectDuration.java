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
import java.util.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.StringUtils;
import org.aspcfs.utils.web.HtmlSelectDurationHours;
import org.aspcfs.utils.web.HtmlSelectDurationMinutesFives;
import org.aspcfs.utils.web.HtmlSelectDurationAMPM;

/**
 *  Description of the Class
 *
 *@author     kbhoopal
 *@created    March 2, 2004
 *@version    $Id: HtmlSelectDuration.java,v 1.2 2004/04/01 16:14:05 mrajkowski
 *      Exp $
 */
public class HtmlSelectDuration extends TagSupport {

  private String baseName = null;
  private String hours = null;
  private String minutes = null;
  private String AMPM = null;
  private String count = null;


  /**
   *  Sets the baseName attribute of the HtmlSelectDuration object
   *
   *@param  tmp  The new baseName value
   */
  public void setBaseName(String tmp) {
    this.baseName = tmp;
  }


  /**
   *  Sets the hours attribute of the HtmlSelectDuration object
   *
   *@param  tmp  The new hours value
   */
  public void setHours(int tmp) {
    this.hours = String.valueOf(tmp);
  }


  /**
   *  Sets the hours attribute of the HtmlSelectDuration object
   *
   *@param  tmp  The new hours value
   */
  public void setHours(String tmp) {
    this.hours = tmp;
  }


  /**
   *  Sets the minutes attribute of the HtmlSelectDuration object
   *
   *@param  tmp  The new minutes value
   */
  public void setMinutes(int tmp) {
    this.minutes = String.valueOf(tmp);
  }


  /**
   *  Sets the minute attribute of the HtmlSelectDuration object
   *
   *@param  tmp  The new minute value
   */
  public void setMinutes(String tmp) {
    this.minutes = tmp;
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
   *  Sets the count attribute of the HtmlSelectDuration object
   *
   *@param  tmp  The new count value
   */
  public void setCount(String tmp) {
    this.count = tmp;
  }


  /**
   *  Sets the count attribute of the HtmlSelectDuration object
   *
   *@param  tmp  The new count value
   */
  public void setCount(int tmp) {
    this.count = String.valueOf(tmp);
  }


  /**
   *  Sets the aMPM attribute of the HtmlSelectTime object
   *
   *@param  AMPM  The new aMPM value
   */
  public void setAMPM(String AMPM) {
    this.AMPM = AMPM;
  }


  /**
   *  Sets the aMPM attribute of the HtmlSelectTime object
   *
   *@param  AMPM  The new aMPM value
   */
  public void setAMPM(int AMPM) {
    this.AMPM = String.valueOf(AMPM);
  }


  /**
   *  Sets the value attribute of the HtmlSelectTime object
   *
   *@param  tmp  The new value value
   */
  public void setValue(java.sql.Timestamp tmp) {
    try {
      Calendar cal = Calendar.getInstance();
      cal.setTimeInMillis(tmp.getTime());
      hours = String.valueOf(cal.get(Calendar.HOUR));
      minutes = String.valueOf(cal.get(Calendar.MINUTE));
      AMPM = String.valueOf(cal.get(Calendar.AM_PM));
    } catch (Exception e) {
      if (!"".equals(StringUtils.toString(hours)) && Integer.parseInt(hours) < 0) {
        hours = "12";
      }
      if (!"".equals(StringUtils.toString(minutes)) && Integer.parseInt(minutes) < 0) {
        minutes = "0";
      }
      if (!"".equals(StringUtils.toString(AMPM)) && Integer.parseInt(AMPM) < 0) {
        AMPM = String.valueOf(Calendar.PM);
      }
    }
  }
  
  
  /**
   *  Sets the value attribute of the HtmlSelectTime object
   *
   *@param  cal  The new value value
   */
  public void setValue(Calendar cal) {
    try {
      hours = String.valueOf(cal.get(Calendar.HOUR));
      minutes = String.valueOf(cal.get(Calendar.MINUTE));
      AMPM = String.valueOf(cal.get(Calendar.AM_PM));
    } catch (Exception e) {
      
      if (!"".equals(StringUtils.toString(hours)) && Integer.parseInt(hours) < 0) {
        hours = "12";
      }
      if (!"".equals(StringUtils.toString(minutes)) && Integer.parseInt(minutes) < 0) {
        minutes = "0";
      }
      if (!"".equals(StringUtils.toString(AMPM)) && Integer.parseInt(AMPM) < 0) {
        AMPM = String.valueOf(Calendar.PM);
      }
    }
  }
  
  
  /**
   *  Description of the Method
   *
   *@return                   Description of the Return Value
   *@exception  JspException  Description of the Exception
   */
  public int doStartTag() throws JspException {
    try {
      if (count == null) {
        count = "";
      }
      //System.out.println("hours --> " + hours + "     Minutes --> " + minutes);
      this.pageContext.getOut().write(
          HtmlSelectDurationHours.getSelect(baseName + "Hours" + count, hours).toString());
      this.pageContext.getOut().write("hrs ");
      this.pageContext.getOut().write(
          HtmlSelectDurationMinutesFives.getSelect(baseName + "Minutes" + count, minutes).toString());
      this.pageContext.getOut().write("min");
      if (AMPM == String.valueOf(Calendar.AM)) {
        this.pageContext.getOut().write(
            HtmlSelectDurationAMPM.getSelect(baseName + "AMPM", "AM").toString());
      } else {
        this.pageContext.getOut().write(
            HtmlSelectDurationAMPM.getSelect(baseName + "AMPM", "PM").toString());
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

