/*
 *  Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.*;
import org.aspcfs.controller.*;
import com.darkhorseventures.database.*;
import java.util.*;

/**
 *  Displays the status of the PagedListInfo specified with record counts, next
 *  and previous buttons, and optionally other items.
 *
 *@author     chris
 *@created    October, 2002
 *@version    $Id: PagedListStatusHandler.java,v 1.9 2003/03/21 13:50:06
 *      mrajkowski Exp $
 */
public class PagedListStatusHandler extends TagSupport {
  private String name = "statusProperties";
  private String label = "Records";
  private String object = null;
  private String bgColor = null;
  private String fontColor = "#666666";
  private String tdClass = null;
  private String tableClass = null;
  private boolean showHiddenParams = false;
  private boolean showForm = true;
  private boolean resetList = true;
  private boolean showExpandLink = false;
  private String title = "&nbsp;";
  private boolean showRefresh = true;
  private boolean showControlOnly = false;
  private boolean scrollReload = false;
  private boolean enableJScript = false;
  private String type = null;


  /**
   *  Sets the name attribute of the PagedListStatusHandler object
   *
   *@param  tmp  The new name value
   */
  public final void setName(String tmp) {
    name = tmp;
  }


  /**
   *  Sets the label attribute of the PagedListStatusHandler object
   *
   *@param  tmp  The new label value
   */
  public void setLabel(String tmp) {
    this.label = tmp;
  }


  /**
   *  Sets the object attribute of the PagedListStatusHandler object
   *
   *@param  tmp  The new object value
   */
  public final void setObject(String tmp) {
    object = tmp;
  }


  /**
   *  Sets the type attribute of the PagedListStatusHandler object
   *
   *@param  tmp  The new type value
   */
  public void setType(String tmp) {
    this.type = tmp;
  }


  /**
   *  Gets the type attribute of the PagedListStatusHandler object
   *
   *@return    The type value
   */
  public String getType() {
    return type;
  }


  /**
   *  Gets the showExpandLink attribute of the PagedListStatusHandler object
   *
   *@return    The showExpandLink value
   */
  public boolean getShowExpandLink() {
    return showExpandLink;
  }


  /**
   *  Sets the showExpandLink attribute of the PagedListStatusHandler object
   *
   *@param  showExpandLink  The new showExpandLink value
   */
  public void setShowExpandLink(boolean showExpandLink) {
    this.showExpandLink = showExpandLink;
  }


  /**
   *  Sets the bgColor attribute of the PagedListStatusHandler object
   *
   *@param  tmp  The new bgColor value
   */
  public final void setBgColor(String tmp) {
    bgColor = tmp;
  }


  /**
   *  Sets the fontColor attribute of the PagedListStatusHandler object
   *
   *@param  tmp  The new fontColor value
   */
  public final void setFontColor(String tmp) {
    fontColor = tmp;
  }


  /**
   *  Gets the title attribute of the PagedListStatusHandler object
   *
   *@return    The title value
   */
  public String getTitle() {
    return title;
  }


  /**
   *  Sets the title attribute of the PagedListStatusHandler object
   *
   *@param  title  The new title value
   */
  public void setTitle(String title) {
    this.title = title;
  }


  /**
   *  Sets the tdClass attribute of the PagedListStatusHandler object
   *
   *@param  tmp  The new tdClass value
   */
  public final void setTdClass(String tmp) {
    tdClass = tmp;
  }


  /**
   *  Sets the tableClass attribute of the PagedListStatusHandler object
   *
   *@param  tmp  The new tableClass value
   */
  public final void setTableClass(String tmp) {
    tableClass = tmp;
  }


  /**
   *  Sets the showForm attribute of the PagedListStatusHandler object
   *
   *@param  tmp  The new showForm value
   */
  public void setShowForm(String tmp) {
    this.showForm = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the showHiddenParams attribute of the PagedListStatusHandler object
   *
   *@param  tmp  The new showHiddenParams value
   */
  public void setShowHiddenParams(String tmp) {
    this.showHiddenParams = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the resetList attribute of the PagedListStatusHandler object
   *
   *@param  resetList  The new resetList value
   */
  public void setResetList(String resetList) {
    this.resetList = "true".equalsIgnoreCase(resetList);
  }


  /**
   *  Sets the showRefresh attribute of the PagedListStatusHandler object
   *
   *@param  tmp  The new showRefresh value
   */
  public void setShowRefresh(boolean tmp) {
    this.showRefresh = tmp;
  }


  /**
   *  Sets the showRefresh attribute of the PagedListStatusHandler object
   *
   *@param  tmp  The new showRefresh value
   */
  public void setShowRefresh(String tmp) {
    this.showRefresh = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the showRefresh attribute of the PagedListStatusHandler object
   *
   *@return    The showRefresh value
   */
  public boolean getShowRefresh() {
    return showRefresh;
  }


  /**
   *  Sets the showControlOnly attribute of the PagedListStatusHandler object
   *
   *@param  tmp  The new showControlOnly value
   */
  public void setShowControlOnly(boolean tmp) {
    this.showControlOnly = tmp;
  }


  /**
   *  Sets the showControlOnly attribute of the PagedListStatusHandler object
   *
   *@param  tmp  The new showControlOnly value
   */
  public void setShowControlOnly(String tmp) {
    this.showControlOnly = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the scrollReload attribute of the PagedListStatusHandler object
   *
   *@param  tmp  The new scrollReload value
   */
  public void setScrollReload(boolean tmp) {
    this.scrollReload = tmp;
  }


  /**
   *  Sets the scrollReload attribute of the PagedListStatusHandler object
   *
   *@param  tmp  The new scrollReload value
   */
  public void setScrollReload(String tmp) {
    this.scrollReload = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the enableJScript attribute of the PagedListStatusHandler object
   *
   *@param  enableJScript  The new enableJScript value
   */
  public void setEnableJScript(boolean enableJScript) {
    this.enableJScript = enableJScript;
  }


  /**
   *  Sets the enableJScript attribute of the PagedListStatusHandler object
   *
   *@param  tmp  The new enableJScript value
   */
  public void setEnableJScript(String tmp) {
    this.enableJScript = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Description of the Method
   *
   *@return                   Description of the Return Value
   *@exception  JspException  Description of the Exception
   */
  public final int doStartTag() throws JspException {
    try {
      PagedListInfo pagedListInfo = (PagedListInfo) pageContext.getSession().getAttribute(object);
      if (pagedListInfo != null) {
        pagedListInfo.setEnableJScript(enableJScript);
        JspWriter out = this.pageContext.getOut();
        //include java scripts if any
        if (enableJScript) {
          out.write("<SCRIPT LANGUAGE=\"JavaScript\" TYPE=\"text/javascript\" SRC=\"javascript/pageListInfo.js\"></SCRIPT>");
        }
        //Draw the header of the PagedList table
        out.write("<table " +
            ((tableClass != null) ? "class=\"" + tableClass + "\" " : "") +
            "align=\"center\" width=\"100%\" cellpadding=\"4\" cellspacing=\"0\" border=\"0\">");
        out.write("<tr>");
        //Display the title
        if (!showControlOnly) {
          //Display the title
          if (showExpandLink) {
            out.write("<th ");
          } else {
            out.write("<td ");
          }
          out.write("nowrap valign=\"bottom\" " +
              "align=\"left\"" +
              ((bgColor != null) ? " bgColor=\"" + bgColor + "\"" : "") +
              ((tdClass != null) ? " class=\"" + tdClass + "\"" : "") +
              ">");
          String newLabel = null;
          if (type != null && !"".equals(type)) {
            ConnectionElement ce = (ConnectionElement) pageContext.getSession().getAttribute("ConnectionElement");
            if (ce == null) {
              System.out.println("PagedListStatusHandler-> ConnectionElement is null");
            }
            SystemStatus systemStatus = (SystemStatus) ((Hashtable) pageContext.getServletContext().getAttribute("SystemStatus")).get(ce.getUrl());
            if (systemStatus == null) {
              System.out.println("PagedListStatusHandler-> SystemStatus is null");
            }
            if (systemStatus != null) {
              newLabel = systemStatus.getLabel(type);
            }
          }
          if (newLabel == null) {
            out.write(title);
          } else {
            out.write(newLabel);
          }
          //show hidden values only if showform is false
          if (showHiddenParams) {
            out.write("<input type=\"hidden\" name=\"offset\" value=\"\" />");
            out.write("<input type=\"hidden\" name=\"pagedListInfoId\" value=\"" + object + "\" />");
          }
          if (showExpandLink) {
            out.write("</th>");
          } else {
            out.write("</td>");
          }
          //Display expansion link
          if (showExpandLink) {
            out.write("<td nowrap width=\"100%\" valign=\"bottom\" " +
                "align=\"left\"" +
                ">");
            out.write(" (" + pagedListInfo.getExpandLink("Show more", "Return to overview") + ")");
            out.write("</td>");
          }
        }

        //The status cell on the right
        out.write("<td valign=\"bottom\" align=\"" + (showControlOnly ? "center" : "right") + "\" nowrap>");
        //Display record count
        if (pagedListInfo.getMaxRecords() > 0) {
          if (pagedListInfo.getItemsPerPage() == 1) {
            //1 of 20 [Previous|Next]
            out.write(String.valueOf(pagedListInfo.getCurrentOffset() + 1));
          } else {
            //Items 1 to 10 of 20 total [Previous|Next]
            out.write(label + " " + (pagedListInfo.getCurrentOffset() + 1) + " to ");
            if (pagedListInfo.getItemsPerPage() <= 0) {
              out.write(String.valueOf(pagedListInfo.getMaxRecords()));
            } else if ((pagedListInfo.getCurrentOffset() + pagedListInfo.getItemsPerPage()) < pagedListInfo.getMaxRecords()) {
              out.write(String.valueOf(pagedListInfo.getCurrentOffset() + pagedListInfo.getItemsPerPage()));
            } else {
              out.write(String.valueOf(pagedListInfo.getMaxRecords()));
            }
          }
          out.write(" of " + pagedListInfo.getMaxRecords());
          if (pagedListInfo.getItemsPerPage() != 1) {
            out.write(" total");
          }
        } else {
          out.write("No " + label.toLowerCase() + " to display");
        }
        //Display next/previous buttons
        if (pagedListInfo.getItemsPerPage() > 0) {
          if (pagedListInfo.getExpandedSelection() || !showExpandLink) {
            pagedListInfo.setScrollReload(scrollReload);
            out.write(" [" +
                pagedListInfo.getPreviousPageLink("<font class='underline'>Previous</font>", "Previous") +
                "|" +
                pagedListInfo.getNextPageLink("<font class='underline'>Next</font>", "Next") +
                "]");
            out.write(" ");
          }
        }
        //Display refresh icon
        if (pagedListInfo.hasLink() && showRefresh) {
          out.write(" " + pagedListInfo.getRefreshTag("<img src=\"images/refresh.gif\" border=\"0\" align=\"absbottom\" />"));
        }
        //Close the cell
        out.write("</td>");
        //Close the table
        out.write("</tr></table>");
      } else {
        System.out.println("PagedListStatusHandler-> Status not found in request: " + object);
      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
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

