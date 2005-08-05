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

import com.darkhorseventures.database.ConnectionElement;
import org.aspcfs.controller.SystemStatus;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.Hashtable;
import java.util.StringTokenizer;

/**
 * Description of the Class
 *
 * @author matt rajkowski
 * @version $Id: TabbedMenuHandler.java,v 1.1.2.1 2004/03/19 21:00:49 rvasista
 *          Exp $
 * @created June 15, 2003
 */
public class TabbedMenuHandler extends TagSupport {

  private String text;
  private String url;
  private String key;
  private String value;
  private String display;
  private String type;


  /**
   * Sets the display attribute of the TabbedMenuHandler object
   *
   * @param tmp The new display value
   */
  public void setDisplay(String tmp) {
    this.display = tmp;
  }


  /**
   * Sets the type attribute of the TabbedMenuHandler object
   *
   * @param tmp The new type value
   */
  public void setType(String tmp) {
    this.type = tmp;
  }


  /**
   * Sets the text attribute of the TabbedMenuHandler object
   *
   * @param tmp The new text value
   */
  public void setText(String tmp) {
    this.text = tmp;
  }


  /**
   * Sets the url attribute of the TabbedMenuHandler object
   *
   * @param tmp The new url value
   */
  public void setUrl(String tmp) {
    this.url = tmp;
  }


  /**
   * Sets the key attribute of the TabbedMenuHandler object
   *
   * @param tmp The new key value
   */
  public void setKey(String tmp) {
    this.key = tmp.toLowerCase();
  }


  /**
   * Sets the value attribute of the TabbedMenuHandler object
   *
   * @param tmp The new value value
   */
  public void setValue(String tmp) {
    this.value = tmp.toLowerCase();
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   * @throws JspException Description of the Exception
   */
  public int doStartTag() throws JspException {
    ConnectionElement ce = (ConnectionElement) pageContext.getSession().getAttribute(
        "ConnectionElement");
    if (ce == null) {
      System.out.println("TabbedMenuHandler-> ConnectionElement is null");
    }
    SystemStatus systemStatus = (SystemStatus) ((Hashtable) pageContext.getServletContext().getAttribute(
        "SystemStatus")).get(ce.getUrl());
    if (systemStatus == null) {
      System.out.println("TabbedMenuHandler-> SystemStatus is null");
    }

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
      if (systemStatus != null) {
        /**
         If the 'text' value being displayed is the same as the 'display' value, then get the
         translated version based on type to display the final tab value
         */
        if (type != null && display != null) {
          if (display.equals(text) && systemStatus.getLabel(type) != null) {
            //User has no preference for this tab's display in the database. Display translated version.
            out.write(
                "<a href=\"" + url + "\">" + systemStatus.getLabel(type) + "</a>");
          } else {
            out.write("<a href=\"" + url + "\">" + text + "</a>");
          }
        } else {
          out.write("<a href=\"" + url + "\">" + text + "</a>");
        }
      } else {
        out.write("<a href=\"" + url + "\">" + text + "</a>");
      }
      out.write("</" + tag + ">");
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

