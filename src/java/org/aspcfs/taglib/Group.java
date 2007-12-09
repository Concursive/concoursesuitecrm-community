/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
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
import org.aspcfs.modules.base.CustomField;
import org.aspcfs.utils.ObjectUtils;
import org.aspcfs.utils.web.*;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;
import java.util.*;

/**
 * Description of the Class
 *
 * @author akhi_m
 * @version $Id$
 * @created October 22, 2002
 */
public class Group extends TagSupport implements TryCatchFinally {
  private String object = null;
  private String rowClass = null;
  private int page = -1;

  public void doCatch(Throwable throwable) throws Throwable {
    // Required but not needed
  }

  public void doFinally() {
    // Reset each property or else the value gets reused
    object = null;
    rowClass = null;
    page = -1;
  }

  /**
   * Sets the object attribute of the Group object
   *
   * @param tmp The new object value
   */
  public void setObject(String tmp) {
    this.object = tmp;
  }


  /**
   * Sets the rowClass attribute of the Group object
   *
   * @param tmp The new rowClass value
   */
  public void setRowClass(String tmp) {
    this.rowClass = tmp;
  }


  /**
   * Sets the page attribute of the Group object
   *
   * @param page The new page value
   */
  public void setPage(int page) {
    this.page = page;
  }


  /**
   * Gets the page attribute of the Group object
   *
   * @return The page value
   */
  public int getPage() {
    return page;
  }


  /**
   * Gets the name attribute of the Group object
   *
   * @return The name value
   */
  public String getObject() {
    return object;
  }


  /**
   * Gets the rowClass attribute of the Group object
   *
   * @return The rowClass value
   */
  public String getRowClass() {
    return rowClass;
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   * @throws JspException Description of the Exception
   */
  public final int doStartTag() throws JspException {
    try {

      // Use the system status if available
      ConnectionElement ce = (ConnectionElement) pageContext.getSession().getAttribute(
          "ConnectionElement");
      SystemStatus systemStatus = null;
      if (ce != null) {
        systemStatus = (SystemStatus) ((Hashtable) pageContext.getServletContext().getAttribute(
            "SystemStatus")).get(ce.getUrl());
        if (systemStatus == null) {
          System.out.println("Group-> SystemStatus is null");
        }
      }

      CustomForm thisForm = (CustomForm) pageContext.getRequest().getAttribute(
          object);

      if (thisForm != null) {
        JspWriter out = this.pageContext.getOut();
        Iterator tabs = thisForm.iterator();
        while (tabs.hasNext()) {
          CustomFormTab thisTab = (CustomFormTab) tabs.next();
          if (page == thisTab.getId()) {
            Iterator groups = thisTab.iterator();
            while (groups.hasNext()) {
              boolean tableClosed = false;
              CustomFormGroup thisGroup = (CustomFormGroup) groups.next();
              
              //Translate this group based on the language available
              if (systemStatus != null) {
                thisGroup.parseTemplateText(systemStatus);
              }

              if (!thisGroup.getName().equals("")) {
                out.write(
                    "<table cellpadding=\"4\" cellspacing=\"0\" border=\"0\" width=\"100%\" class=\"details\">");
              } else {
                out.write(
                    "<table cellpadding=\"4\" cellspacing=\"0\" border=\"0\" width=\"100%\" >");
              }
              //TODO : make a Table Class and get header from object attributes
              if (!thisGroup.getName().equals("")) {
                out.write("<tr>");
                out.write("<th colspan=\"3\">");
                out.write("<strong> " + thisGroup.getName() + "</strong>");
                out.write("</th>");
                out.write("</tr>");
              }

              //create rows
              Iterator rows = thisGroup.iterator();
              while (rows.hasNext()) {
                CustomRow thisRow = (CustomRow) rows.next();
                if (!thisRow.getMultiple()) {
                  boolean hiddenRow = false;
                  if (thisRow.getType() != null) {
                    if (thisRow.getType().equalsIgnoreCase("hidden")) {
                      hiddenRow = true;
                    }
                  }
                  if (!hiddenRow) {
                    thisRow.build();
                    if (System.getProperty("DEBUG") != null) {
                      System.out.println(
                          "Group --> Printing Row " + thisRow.getStartTag());
                    }
                    out.write(thisRow.getStartTag());
                  }

                  //create columns
                  Iterator columns = thisRow.iterator();
                  while (columns.hasNext()) {
                    CustomColumn thisColumn = (CustomColumn) columns.next();
                    boolean hiddenColumn = false;
                    if (thisColumn.getType() != null) {
                      if (thisColumn.getType().equalsIgnoreCase("hidden")) {
                        hiddenColumn = true;
                      }
                    }

                    if (!hiddenColumn) {
                      thisColumn.build();
                      if (System.getProperty("DEBUG") != null) {
                        System.out.println(
                            "Group --> Printing Column " + thisColumn.getStartTag());
                      }
                      out.write(thisColumn.getStartTag());
                    }

                    //fill in data from fields
                    Iterator fields = thisColumn.iterator();
                    while (fields.hasNext()) {
                      CustomField thisField = (CustomField) fields.next();
                      
                      //Translate this field display text based on the language available
                      if (systemStatus != null) {
                        thisField.parseTemplateText(systemStatus);
                      }

                      if (System.getProperty("DEBUG") != null) {
                        System.out.println(
                            "Group-> Printing Field " + thisField.getHtmlElement(
                                systemStatus));
                      }
                      out.write(
                          thisField.getHtmlElement(systemStatus) + (thisField.getRequired() ? "<font color=\"red\">*</font>" : ""));
                      if (pageContext.getRequest().getAttribute(
                          thisField.getName() + "Error") != null) {
                        out.write(
                            "&nbsp;<font color='#006699'>" + (String) pageContext.getRequest().getAttribute(
                                thisField.getName() + "Error") + "</font>");
                      }
                    }
                    if (!hiddenColumn) {
                      out.write(thisColumn.getEndTag());
                    }
                  }
                  if (!hiddenRow) {
                    out.write(thisRow.getEndTag());
                  }
                } else {
                  processRowList(systemStatus, thisRow, out);
                }
              }
              out.write("</table>");
            }
          }
        }
      } else {
        if (System.getProperty("DEBUG") != null) {
          System.out.println("Group -> CustomForm not found in object ");
        }
      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }
    return SKIP_BODY;
  }


  /**
   * Description of the Method
   *
   * @param out     Description of the Parameter
   * @param listRow Description of the Parameter
   * @throws JspException Description of the Exception
   */
  private void processRowList(SystemStatus thisSystem, CustomRow listRow, JspWriter out) throws JspException {

    try {
      Iterator rowList = ((Collection) listRow.getListObject()).iterator();
      if (System.getProperty("DEBUG") != null) {
        System.out.println(
            "Group -> Processing RowList -- > size : " + ((ArrayList) listRow.getListObject()).size());
      }
      if (rowList.hasNext()) {
        while (rowList.hasNext()) {
          Object tmp = rowList.next();
          CustomRow thisRow = (CustomRow) listRow.clone();
          thisRow.build();
          out.write(thisRow.getStartTag());
          Iterator columns = thisRow.iterator();
          while (columns.hasNext()) {
            CustomColumn thisColumn = (CustomColumn) columns.next();
            thisColumn.build();
            out.write(thisColumn.getStartTag());
            Iterator fields = thisColumn.iterator();
            while (fields.hasNext()) {
              CustomField thisField = (CustomField) (((CustomField) fields.next()).duplicate());
              
              //Translate this field display text based on the language available
              if (thisSystem != null) {
                thisField.parseTemplateText(thisSystem);
              }

              thisField.setEnteredValue(
                  ObjectUtils.getParam(tmp, thisField.getName()));
              processJsEvent(tmp, thisField);
              out.write(thisField.getHtmlElement(thisSystem));
            }
            out.write(thisColumn.getEndTag());
          }
          out.write(thisRow.getEndTag());
        }
      } else {
        out.write(
            "<tr><td colspan=\"3\" align=\"left\"> No Items Found. </td></tr>");
      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }
  }


  /**
   * Description of the Method
   *
   * @param tmp   Description of the Parameter
   * @param field Description of the Parameter
   */
  private void processJsEvent(Object tmp, CustomField field) {
    String jsEvent = (String) field.getJsEvent();
    if (jsEvent != null && !jsEvent.equals("")) {
      String params = jsEvent.substring(
          jsEvent.indexOf("(") + 1, jsEvent.lastIndexOf(")"));
      StringTokenizer st = new StringTokenizer(params, ",");
      StringBuffer values = new StringBuffer();
      while (st.hasMoreTokens()) {
        String param = st.nextToken();
        if (param.startsWith("$")) {
          values.append(
              "'" + ObjectUtils.getParam(tmp, param.substring(1)) + "'");

        } else {
          values.append(param);
        }
        if (st.hasMoreTokens()) {
          values.append(",");
        }
      }
      field.setJsEvent(
          jsEvent.substring(0, jsEvent.indexOf("(")) + "(" + values.toString() + ")");
    }
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

