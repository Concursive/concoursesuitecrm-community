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

/**
 *  Displays an HTML widget called a Spinner, basically a text field and arrows
 *  to increase and decrease a value
 *
 *@author     matt rajkowski
 *@created    July 23, 2003
 *@version    $Id$
 */
public class HtmlSpinner extends TagSupport {

  private String name = null;
  private String value = "0";
  private int min = -1;
  private int max = -1;


  /**
   *  Sets the name attribute of the HtmlSpinner object
   *
   *@param  tmp  The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   *  Sets the value attribute of the HtmlSpinner object
   *
   *@param  tmp  The new value value
   */
  public void setValue(String tmp) {
    this.value = tmp;
  }


  /**
   *  Sets the value attribute of the HtmlSpinner object
   *
   *@param  tmp  The new value value
   */
  public void setValue(int tmp) {
    this.value = String.valueOf(tmp);
  }


  /**
   *  Sets the min attribute of the HtmlSpinner object
   *
   *@param  tmp  The new min value
   */
  public void setMin(int tmp) {
    this.min = tmp;
  }


  /**
   *  Sets the min attribute of the HtmlSpinner object
   *
   *@param  tmp  The new min value
   */
  public void setMin(String tmp) {
    this.min = Integer.parseInt(tmp);
  }


  /**
   *  Sets the max attribute of the HtmlSpinner object
   *
   *@param  tmp  The new max value
   */
  public void setMax(int tmp) {
    this.max = tmp;
  }


  /**
   *  Sets the max attribute of the HtmlSpinner object
   *
   *@param  tmp  The new max value
   */
  public void setMax(String tmp) {
    this.max = Integer.parseInt(tmp);
  }


  /**
   *  Description of the Method
   *
   *@return                   Description of the Return Value
   *@exception  JspException  Description of the Exception
   */
  public int doStartTag() throws JspException {
    try {
      this.pageContext.getOut().write(
          "<script language=\"JavaScript\" type=\"text/javascript\">var spinMin=" + min + "; var spinMax=" + max + ";</script>\n");
      this.pageContext.getOut().write(
          "<script language=\"JavaScript\" type=\"text/javascript\" src=\"javascript/spinner.js\"></script>\n");
      this.pageContext.getOut().write(
          "<input type=\"text\" name=\"" + name + "\" id=\"" + name + "\" value=\"" + value + "\" size=\"4\" maxlength=\"" + String.valueOf(max).length() + "\"> ");
      this.pageContext.getOut().write(
          "<a href=\"javascript:spinLeft('" + name + "');\">" +
          "<img alt=\"Unindent\" src=\"images/icons/stock_left-16.gif\" border=\"0\" align=\"absmiddle\" height=\"16\" width=\"16\"/>" +
          "</a>");
      this.pageContext.getOut().write(
          "<a href=\"javascript:spinRight('" + name + "');\">" +
          "<img alt=\"Indent\" src=\"images/icons/stock_right-16.gif\" border=\"0\" align=\"absmiddle\" height=\"16\" width=\"16\"/>" +
          "</a>\n");
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

