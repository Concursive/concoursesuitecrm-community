package org.aspcfs.taglib;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.DatabaseUtils;

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
  private String object = null;
  private String bgColor = null;
  private String fontColor = "#666666";
  private String tdClass = null;
  private boolean showHiddenParams = false;
  private boolean resetList = true;
  private boolean showExpandLink = false;
  private String title = "&nbsp;";
  private boolean showRefresh = true;
  private boolean showControlOnly = false;
  private boolean enableJScript = false;


  /**
   *  Sets the name attribute of the PagedListStatusHandler object
   *
   *@param  tmp  The new name value
   */
  public final void setName(String tmp) {
    name = tmp;
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
        out.write("<table align=\"center\" width=\"100%\" cellpadding=\"4\" cellspacing=\"0\" border=\"0\">");
        out.write("<tr>");
        //Display the title
        if (!showControlOnly) {
          out.write("<td width=\"100%\" valign=\"bottom\" " +
              "align=\"left\"" +
              ((bgColor != null) ? " bgColor=\"" + bgColor + "\"" : "") +
              ((tdClass != null) ? " class=\"" + tdClass + "\"" : "") +
              ">");
          out.write(title);

          //show hidden values only if showform is false
          if (showHiddenParams) {
            out.write("<input type=\"hidden\" name=\"offset\" value=\"\">");
            out.write("<input type=\"hidden\" name=\"pagedListInfoId\" value=\"" + object + "\">");
          }
          //Display expansion link
          if (showExpandLink) {
            out.write(" (" + pagedListInfo.getExpandLink("Show more", "Return to overview") + ")");
          }
          out.write("</td>");
        }

        //The status cell on the right
        out.write("<td valign=\"bottom\" align=\"" + (showControlOnly ? "center" : "right") + "\" nowrap>");

        //Display record count
        if (pagedListInfo.getMaxRecords() > 0) {
          out.write("Records " + (pagedListInfo.getCurrentOffset() + 1) + " to ");
          if ((pagedListInfo.getCurrentOffset() + pagedListInfo.getItemsPerPage()) < pagedListInfo.getMaxRecords()) {
            out.write(String.valueOf(pagedListInfo.getCurrentOffset() + pagedListInfo.getItemsPerPage()));
          } else {
            out.write(String.valueOf(pagedListInfo.getMaxRecords()));
          }
          out.write(" of " + pagedListInfo.getMaxRecords() + " total");
        } else {
          out.write("No records to display");
        }

        //Display next/previous buttons
        if (pagedListInfo.getExpandedSelection() || !showExpandLink) {
          out.write(" [" +
              pagedListInfo.getPreviousPageLink("<font class='underline'>Previous</font>", "Previous") +
              "|" +
              pagedListInfo.getNextPageLink("<font class='underline'>Next</font>", "Next") +
              "]");
          out.write(" ");
        }

        //Display refresh icon
        if (pagedListInfo.hasLink() && showRefresh) {
          out.write(" " + pagedListInfo.getRefreshTag("<img src=\"images/refresh.gif\" border=\"0\" align=\"absbottom\">"));
        }

        //Close the cell
        out.write("</td></tr>");
        out.write("</table>");
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

