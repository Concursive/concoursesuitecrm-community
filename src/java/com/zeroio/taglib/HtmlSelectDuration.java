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
import org.aspcfs.utils.web.HtmlSelectDurationHours;
import org.aspcfs.utils.web.HtmlSelectDurationMinutesFives;

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

