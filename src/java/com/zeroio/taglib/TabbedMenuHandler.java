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
import java.util.StringTokenizer;

/**
 *  Description of the Class
 *
 *@author     matt rajkowski
 *@created    June 15, 2003
 *@version    $Id: TabbedMenuHandler.java,v 1.1.2.1 2004/03/19 21:00:49 rvasista
 *      Exp $
 */
public class TabbedMenuHandler extends TagSupport {

  private String text;
  private String url;
  private String key;
  private String value;


  /**
   *  Sets the text attribute of the TabbedMenuHandler object
   *
   *@param  tmp  The new text value
   */
  public void setText(String tmp) {
    this.text = tmp;
  }


  /**
   *  Sets the url attribute of the TabbedMenuHandler object
   *
   *@param  tmp  The new url value
   */
  public void setUrl(String tmp) {
    this.url = tmp;
  }


  /**
   *  Sets the key attribute of the TabbedMenuHandler object
   *
   *@param  tmp  The new key value
   */
  public void setKey(String tmp) {
    this.key = tmp.toLowerCase();
  }


  /**
   *  Sets the value attribute of the TabbedMenuHandler object
   *
   *@param  tmp  The new value value
   */
  public void setValue(String tmp) {
    this.value = tmp.toLowerCase();
  }


  /**
   *  Description of the Method
   *
   *@return                   Description of the Return Value
   *@exception  JspException  Description of the Exception
   */
  public int doStartTag() throws JspException {
    JspWriter out = this.pageContext.getOut();
    String tag = "td";
    if (key.indexOf(",") > -1) {
      StringTokenizer st = new StringTokenizer(key, ",");
      while (st.hasMoreTokens()) {
        if (value.startsWith(st.nextToken())) {
          tag = "th";
          break;
        }
      }
    } else if (value.startsWith(key)) {
      tag = "th";
    }
    try {
      out.write("<" + tag + " nowrap>");
      out.write("<a href=\"" + url + "\">" + text + "</a>");
      out.write("</" + tag + ">");
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

