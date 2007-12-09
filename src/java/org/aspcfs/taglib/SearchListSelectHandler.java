/*
 *  Copyright(c) 2007 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */

package org.aspcfs.taglib;

import com.darkhorseventures.database.ConnectionElement;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.utils.StringUtils;
import org.aspcfs.utils.web.PagedListInfo;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.StringTokenizer;

/**
 * This tag returns popup window with a select multiple box and a "Select" link,
 * for multiple selection for any field
 *
 * @author
 * @version $Id: SearchListSelectHandler.java,v 1.4 2006/08/04 10:19:37
 *          Exp $
 * @created August 04, 2006
 */

public class SearchListSelectHandler extends TagSupport {

  private String pagedListInfo = null;
  private String listName = null;
  private String formName = null;
  private String name = null;
  private String displayOption = null;
  private String widgetName = null;
  private String selectedList = null;
  private int hidId = 1;


  /**
   * Sets the pagedListInfoName attribute of the SearchListSelectHandler object
   *
   * @param pagedListInfo The new pagedListInfo value
   */
  public void setPagedListInfo(String pagedListInfo) {
    this.pagedListInfo = pagedListInfo;
  }

  /**
   * Sets the pagedListInfoName attribute of the SearchListSelectHandler object
   *
   * @param widgetName The new widgetName value
   */
  public void setWidgetName(String widgetName) {
    this.widgetName = widgetName;
  }


  /**
   * Sets the listName attribute of the SearchListSelectHandler object
   *
   * @param listName The new listName value
   */
  public void setListName(String listName) {
    this.listName = listName;
  }

  /**
   * Sets the listName attribute of the SearchListSelectHandler object
   *
   * @param formName The new formName value
   */
  public void setFormName(String formName) {
    this.formName = formName;
  }

  /**
   * Sets the Name attribute of the SearchListSelectHandler object
   *
   * @param Name The new listName value
   */
  public void setName(String Name) {
    this.listName = Name;
  }

  /**
   * Sets the listName attribute of the SearchListSelectHandler object
   *
   * @param displayOption The new listName value
   */
  public void setDisplayOption(String displayOption) {
    this.displayOption = displayOption;
  }

  /**
   * Gets the pagedListInfoName attribute of the SearchListSelectHandler object
   *
   * @return The listType value
   */
  public String getPagedListInfo() {
    return pagedListInfo;
  }

  /**
   * Gets the listType attribute of the SearchListSelectHandler object
   *
   * @return The listName value
   */
  public String getListName() {
    return listName;
  }

  /**
   * Gets the listType attribute of the SearchListSelectHandler object
   *
   * @return The formName value
   */
  public String getFormName() {
    return formName;
  }

  /**
   * Gets the Name attribute of the SearchListSelectHandler object
   *
   * @return The Name value
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the listType attribute of the SearchListSelectHandler object
   *
   * @return The displayOption value
   */
  public String getDisplayOption() {
    return displayOption;
  }


  /**
   * Gets the listType attribute of the SearchListSelectHandler object
   *
   * @return The displayOption value
   */
  public String getWidgetName() {
    return widgetName;
  }

  /**
   * This method creates a multiple select box and initilizes it with the values
   * from Pagedlistinfo. Name of the multi select box is the widget name prefix
   * with string "multi".Widget name is used to create a hidden type which is used
   * by the PagedListInfo.
   *
   * @return SKIP_BODY - Ignore body text. Any text between the start and end tags is not evaluated or displayed.
   * @throws JspException Description of the Exception
   */
  public int doStartTag() throws JspException {
    try {
      String paramList = null;


      PagedListInfo listInfo = (PagedListInfo) pageContext.getSession().getAttribute(pagedListInfo);

      paramList = listInfo.getSearchOptionValue(widgetName);

      this.pageContext.getOut().write(
          "<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" class=\"empty\">" +
              "<tr><td>" +
              "<select multiple name=\"multi" + widgetName + "\" id=\"multi" + widgetName + "\" size=\"5\">");

      if (paramList == null || paramList.equals("")) {
        this.pageContext.getOut().write("<option value=\"-1\"><dhv:label name=\"accounts.accounts_add.NoneSelected\">None Selected</dhv:label></option>");
      } else {
        ArrayList paramArray = getParamArray(paramList);
        for (int i = 0; i < paramArray.size(); i++) {
          pageContext.getOut().write("<option value=" + ((String) paramArray.get(i)).substring(0, ((String) paramArray.get(i)).indexOf(',')) + ">" + ((String) paramArray.get(i)).substring(((String) paramArray.get(i)).indexOf(',') + 1) + "</option>");
        }
      }
    }
    catch (Exception e) {
      throw new JspException(
          "SearchListSelectHandler - > Error: " + e.getMessage());
    }
    return SKIP_BODY;
  }


  /**
   * This method creates the radio buttons with the name of widget name
   * postfix with string "Option". It also creates a hyperlink which
   * calls a javascript function.
   *
   * @return EVAL_PAGE - Continue evaluating the page
   * @throws JspException Description of the Exception
   */
  public int doEndTag() {
    try {
      String matchALL = null;
      String matchANY = null;

      ConnectionElement ce = (ConnectionElement) pageContext.getSession().getAttribute("ConnectionElement");
      SystemStatus systemStatus = null;

      if (ce != null) {
        Hashtable statusList = (Hashtable) pageContext.getServletContext().getAttribute("SystemStatus");
        systemStatus = (SystemStatus) statusList.get(ce.getUrl());
      }

      matchALL = systemStatus.getLabel("accounts.MatchAllSpecified");

      matchANY = systemStatus.getLabel("accounts.MatchAnySpecified");

      PagedListInfo listInfo = (PagedListInfo) pageContext.getSession().getAttribute(pagedListInfo);

      pageContext.getOut().write(
          "     </select></td><td valign=\"top\">" +
              "<input type=\"hidden\" name=\"" + widgetName + "\" id=\"" + widgetName + "\" value=\"" + listInfo.getSearchOptionValue(widgetName) + "\"/>" +
              "&nbsp[<a href=\"javascript:popLookupSelectMultiple('multi" + widgetName + "','" + 1 + "','" + listName + "'" + ");\">Select</a>]" +
              "</td></tr></table>"
      );

      if (displayOption.toLowerCase().equals("true")) {
        pageContext.getOut().write(
            "<input type=\"radio\" name=" + widgetName + "Option value=\"ALL\" " +
                ("ALL".equals(listInfo.getSearchOptionValue(widgetName + "Option")) || !StringUtils.hasText(listInfo.getSearchOptionValue(widgetName + "Option")) ? " checked" : "") + ">" +
                matchALL + " </input>&nbsp" +
                "<input type=\"radio\" name=" + widgetName + "Option value=\"ANY\" " +
                ("ANY".equals(listInfo.getSearchOptionValue(widgetName + "Option")) ? " checked" : "") + " > " +
                matchANY + "</input>");
      }

    } catch (Exception e) {
      e.printStackTrace(System.out);
    }
    return EVAL_PAGE;
  }

  /**
   * This method creates an array of parameters
   *
   * @return ArrayList
   */
  private ArrayList getParamArray(String str) {
    StringTokenizer stringTokenizer = new StringTokenizer(str, ";");
    String tmp = null;
    ArrayList paramArray = new ArrayList();
    while (stringTokenizer.hasMoreTokens()) {
      tmp = stringTokenizer.nextToken();
      if (tmp != null && tmp.trim() != "") {
        paramArray.add(tmp);
        		}
        	}
        return paramArray;
        }
}