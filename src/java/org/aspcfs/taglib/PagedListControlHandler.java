package org.aspcfs.taglib;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import org.aspcfs.utils.web.PagedListInfo;

/**
 *  Provides a visual control panel (html form) that allows the user to jump to
 *  another page, change the number of entries per page, etc.
 *
 *@author     matt rajkowski
 *@created    June 12, 2002
 *@version    $Id: PagedListControlHandler.java,v 1.2 2002/08/06 21:03:07 akhi_m
 *      Exp $
 */
public class PagedListControlHandler extends TagSupport {
  private String name = "controlProperties";
  private String object = null;
  private String bgColor = null;
  private String fontColor = "#666666";
  private String tdClass = null;
  private boolean showForm = true;
  private boolean resetList = true;
  private boolean abbreviate = false;


  /**
   *  Sets the name attribute of the PagedListControlHandler object
   *
   *@param  tmp  The new name value
   */
  public final void setName(String tmp) {
    name = tmp;
  }


  /**
   *  Sets the object attribute of the PagedListControlHandler object
   *
   *@param  tmp  The new object value
   */
  public final void setObject(String tmp) {
    object = tmp;
  }


  /**
   *  Sets the bgColor attribute of the PagedListControlHandler object
   *
   *@param  tmp  The new bgColor value
   */
  public final void setBgColor(String tmp) {
    bgColor = tmp;
  }


  /**
   *  Sets the fontColor attribute of the PagedListControlHandler object
   *
   *@param  tmp  The new fontColor value
   */
  public final void setFontColor(String tmp) {
    fontColor = tmp;
  }


  /**
   *  Sets the tdClass attribute of the PagedListControlHandler object
   *
   *@param  tmp  The new tdClass value
   */
  public final void setTdClass(String tmp) {
    tdClass = tmp;
  }

  /**
   *  Sets the showForm attribute of the PagedListControlHandler object
   *
   *@param  showForm  The new showForm value
   */

  public void setShowForm(String showForm) {
    this.showForm = "true".equalsIgnoreCase(showForm);
  }


  /**
   *  Sets the resetList attribute of the PagedListControlHandler object
   *
   *@param  resetList  The new resetList value
   */
  public void setResetList(String resetList) {
    this.resetList = "true".equalsIgnoreCase(resetList);
  }

  public boolean getAbbreviate() {
    return abbreviate;
  }
  public void setAbbreviate(boolean abbreviate) {
    this.abbreviate = abbreviate;
  }

  /**
   *  Description of the Method
   *
   *@return                   Description of the Returned Value
   *@exception  JspException  Description of Exception
   */
  public final int doStartTag() throws JspException {
    try {
      PagedListInfo pagedListInfo = (PagedListInfo) pageContext.getSession().getAttribute(object);

      if (pagedListInfo != null) {
        pagedListInfo.setShowForm(showForm);
        pagedListInfo.setResetList(resetList);
        JspWriter out = this.pageContext.getOut();
        out.write("<SCRIPT LANGUAGE=\"JavaScript\" TYPE=\"text/javascript\" SRC=\"javascript/pageListInfo.js\"></SCRIPT>");
        out.write("<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">");
        out.write(pagedListInfo.getListPropertiesHeader(name));
        out.write("<tr>");
        out.write("<td valign=\"middle\" " +
            "align=\"center\"" +
            ((bgColor != null) ? " bgColor=\"" + bgColor + "\"" : "") +
            ((tdClass != null) ? " class=\"" + tdClass + "\"" : "") +
            ">");
        out.write("<input type=\"hidden\" name=\"offset\" value=\"\">");
        out.write("<input type=\"hidden\" name=\"pagedListInfoId\" value=\"" + object + "\">");

        out.write("[" +
            pagedListInfo.getPreviousPageLink("<font class='underline'>Previous</font>", "Previous") +
            "|" +
            pagedListInfo.getNextPageLink("<font class='underline'>Next</font>", "Next") +
            "] ");

        out.write("<font color=\"" + fontColor + "\">");
        out.write("Page " + pagedListInfo.getNumericalPageEntry() + " ");
        
        if (!abbreviate) {
          out.write("of " + ((pagedListInfo.getNumberOfPages() == 0) ? "1" : String.valueOf(pagedListInfo.getNumberOfPages())) + ", ");
        } else {
          out.write("of " + ((pagedListInfo.getNumberOfPages() == 0) ? "1" : String.valueOf(pagedListInfo.getNumberOfPages())));
        }
        
        if (!abbreviate) {
          out.write("Items per page: " + pagedListInfo.getItemsPerPageEntry() + " ");
        } else {
          out.write("&nbsp;&nbsp;");
        }
        
        out.write("<input type=\"submit\" value=\"go\">");
        out.write("</font>");
        out.write("</td>");
        out.write("</tr>");
        out.write(pagedListInfo.getListPropertiesFooter());
        out.write("</table>");
      } else {
        System.out.println("PagedListControlHandler-> Control not found in request: " + object);
      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }
    return SKIP_BODY;
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Returned Value
   */
  public int doEndTag() {
    return EVAL_PAGE;
  }
}

