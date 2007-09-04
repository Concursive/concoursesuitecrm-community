/*
 *  Copyright(c) 2007 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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

package org.aspcfs.taglib;

import org.aspcfs.utils.web.PagedListInfo;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * This tag returns popup window with a textbox and a "Add Choices" link,
 * for multiple selection for any field
 *
 * @author
 * @version $Id: SearchTextSelectHandler.java,v 1.4 2006/08/04 10:19:37
 *          Exp $
 * @created August 04, 2006
 */
public class SearchTextSelectHandler extends TagSupport {
  private String pagedListInfo = null;
  private String formName = null;
  private String widgetName = null;

  /**
   * Returns the value of pagedListInfo.
   */
  public String getPagedListInfo() {
    return pagedListInfo;
  }

  /**
   * Sets the value of pagedListInfo.
   *
   * @param pagedListInfo The value to assign pagedListInfo.
   */
  public void setPagedListInfo(String pagedListInfo) {
    this.pagedListInfo = pagedListInfo;
  }

  /**
   * Returns the value of formName.
   */
  public String getFormName() {
    return formName;
  }

  /**
   * Sets the value of formName.
   *
   * @param formName The value to assign formName.
   */
  public void setFormName(String formName) {
    this.formName = formName;
  }

  /**
   * Returns the value of widgetName.
   */
  public String getWidgetName() {
    return widgetName;
  }

  /**
   * Sets the value of widgetName.
   *
   * @param widgetName The value to assign widgetName.
   */
  public void setWidgetName(String widgetName) {
    this.widgetName = widgetName;
  }

  /**
   * This method creates a text box with the widget name and a hyperlink
   * which calls a javascript function.
   *
   * @return SKIP_BODY - Ignore body text. Any text between the start and end tags is not evaluated or displayed.
   * @throws JspException
   */
  public int doStartTag() throws JspException {
    try {
      PagedListInfo searchListInfo = (PagedListInfo) pageContext.getSession().getAttribute(pagedListInfo);
      if (searchListInfo != null) {
        this.pageContext.getOut().write(
            "<input type=\"text\" size=\"23\" name=\"" + widgetName + "\" id=\"" + widgetName + "\" value=\"" + searchListInfo.getSearchOptionValue(widgetName) + "\" >&nbsp[<a href=\"javascript:popList(\'" + formName + "\',\'" + widgetName + "\');\">Add Choice</a>]"
        );
      }
    }
    catch (Exception e) {
      e.printStackTrace(System.out);
    }
    return SKIP_BODY;
  }

  /**
   * Description of the Method
   *
   * @return EVAL_PAGE - Continue evaluating the page
   * @throws JspException
   */
  public int doEndTag() {
    return EVAL_PAGE;
	}
	
 }