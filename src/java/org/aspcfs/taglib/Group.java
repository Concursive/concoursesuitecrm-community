package org.aspcfs.taglib;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import org.aspcfs.controller.*;
import org.aspcfs.utils.*;
import org.aspcfs.modules.base.*;
import org.aspcfs.utils.web.*;
import java.util.*;

/**
 *  Description of the Class
 *
 *@author     akhi_m
 *@created    October 22, 2002
 *@version    $Id$
 */
public class Group extends TagSupport {
  private String object = null;
  private String rowClass = null;
  private int page = -1;


  /**
   *  Sets the object attribute of the Group object
   *
   *@param  tmp  The new object value
   */
  public void setObject(String tmp) {
    this.object = tmp;
  }


  /**
   *  Sets the rowClass attribute of the Group object
   *
   *@param  tmp  The new rowClass value
   */
  public void setRowClass(String tmp) {
    this.rowClass = tmp;
  }


  /**
   *  Sets the page attribute of the Group object
   *
   *@param  page  The new page value
   */
  public void setPage(int page) {
    this.page = page;
  }


  /**
   *  Gets the page attribute of the Group object
   *
   *@return    The page value
   */
  public int getPage() {
    return page;
  }


  /**
   *  Gets the name attribute of the Group object
   *
   *@return    The name value
   */
  public String getObject() {
    return object;
  }


  /**
   *  Gets the rowClass attribute of the Group object
   *
   *@return    The rowClass value
   */
  public String getRowClass() {
    return rowClass;
  }


  /**
   *  Description of the Method
   *
   *@return                   Description of the Return Value
   *@exception  JspException  Description of the Exception
   */
  public final int doStartTag() throws JspException {
    try {
      CustomForm thisForm = (CustomForm) pageContext.getRequest().getAttribute(object);

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
              if (!thisGroup.getName().equals("")) {
                out.write("<table cellpadding=\"4\" cellspacing=\"0\" border=\"1\" width=\"100%\" bordercolorlight=\"#000000\" bordercolor=\"#FFFFFF\">");
              } else {
                out.write("<table cellpadding=\"4\" cellspacing=\"0\" border=\"0\" width=\"100%\" >");
              }
              //TODO : make a Table Class and get header from object attributes
              if (!thisGroup.getName().equals("")) {
                out.write("<tr class=\"title\">");
                out.write("<td colspan=\"3\" valign=\"center\" align=\"left\">");
                out.write("<strong> " + thisGroup.getName() + "</strong>");
                out.write("</td>");
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
                      System.out.println("Group --> Printing Row " + thisRow.getStartTag());
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
                        System.out.println("Group --> Printing Column " + thisColumn.getStartTag());
                      }
                      out.write(thisColumn.getStartTag());
                    }

                    //fill in data from fields
                    Iterator fields = thisColumn.iterator();
                    while (fields.hasNext()) {
                      CustomField thisField = (CustomField) fields.next();
                      if (System.getProperty("DEBUG") != null) {
                        System.out.println("Group --> Printing Field " + thisField.getHtmlElement());
                      }
                      out.write(StringUtils.toHtmlTextBlank(thisField.getHtmlElement()) + (thisField.getRequired() ? "<font color=\"red\">*</font>" : ""));
                    }
                    if (!hiddenColumn) {
                      out.write(thisColumn.getEndTag());
                    }
                  }
                  if (!hiddenRow) {
                    out.write(thisRow.getEndTag());
                  }
                } else {
                  processRowList(thisRow, out);
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
   *  Description of the Method
   *
   *@param  out               Description of the Parameter
   *@param  listRow           Description of the Parameter
   *@exception  JspException  Description of the Exception
   */
  private void processRowList(CustomRow listRow, JspWriter out) throws JspException {

    try {
      Iterator rowList = ((Collection) listRow.getListObject()).iterator();
      if (System.getProperty("DEBUG") != null) {
        System.out.println("Group -> Processing RowList -- > size : " + ((ArrayList) listRow.getListObject()).size());
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
              thisField.setEnteredValue(ObjectUtils.getParam(tmp, thisField.getName()));
              processJsEvent(tmp, thisField);
              out.write(thisField.getHtmlElement());
            }
            out.write(thisColumn.getEndTag());
          }
          out.write(thisRow.getEndTag());
        }
      } else {
        out.write("<tr><td colspan=\"3\" align=\"left\"> No Items Found. </td></tr>");
      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  tmp    Description of the Parameter
   *@param  field  Description of the Parameter
   */
  private void processJsEvent(Object tmp, CustomField field) {
    String jsEvent = (String) field.getJsEvent();
    if (jsEvent != null && !jsEvent.equals("")) {
      String params = jsEvent.substring(jsEvent.indexOf("(") + 1, jsEvent.lastIndexOf(")"));
      StringTokenizer st = new StringTokenizer(params, ",");
      StringBuffer values = new StringBuffer();
      while (st.hasMoreTokens()) {
        String param = st.nextToken();
        if (param.startsWith("$")) {
          values.append("'" + ObjectUtils.getParam(tmp, param.substring(1)) + "'");

        } else {
          values.append(param);
        }
        if (st.hasMoreTokens()) {
          values.append(",");
        }
      }
      field.setJsEvent(jsEvent.substring(0, jsEvent.indexOf("(")) + "(" + values.toString() + ")");
    }
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

