package com.darkhorseventures.taglib;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import com.darkhorseventures.webutils.PagedListInfo;

public class PagedListStatusHandler extends TagSupport {
  private String name = "statusProperties";
  private String object = null;
  private String bgColor = null;
  private String fontColor = "#666666";
  private String tdClass = null;
  private boolean showForm = true;
  private boolean resetList = true;
  private boolean showExpandLink = false;
  private String title = "&nbsp;";

  public final void setName(String tmp) {
    name = tmp;
  }


  public final void setObject(String tmp) {
    object = tmp;
  }
  
  public boolean getShowExpandLink() {
    return showExpandLink;
  }
  public void setShowExpandLink(boolean showExpandLink) {
    this.showExpandLink = showExpandLink;
  }

  public final void setBgColor(String tmp) {
    bgColor = tmp;
  }
  
  public final void setFontColor(String tmp) {
    fontColor = tmp;
  }

  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }

  public final void setTdClass(String tmp) {
    tdClass = tmp;
  }

  public void setShowForm(String showForm) {
    this.showForm = "true".equalsIgnoreCase(showForm);
  }


  public void setResetList(String resetList) {
    this.resetList = "true".equalsIgnoreCase(resetList);
  }

  public final int doStartTag() throws JspException {
    try {
      PagedListInfo pagedListInfo = (PagedListInfo) pageContext.getSession().getAttribute(object);

      if (pagedListInfo != null) {
        JspWriter out = this.pageContext.getOut();
        
        //Draw the header of the PagedList table
        out.write("<table align=\"center\" width=\"100%\" cellpadding=\"4\" cellspacing=\"0\" border=\"0\">");
        out.write("<tr>");
        //Display the title
        out.write("<td width=\"50%\" valign=\"bottom\" " +
            "align=\"left\"" +
            ((bgColor != null) ? " bgColor=\"" + bgColor + "\"" : "") +
            ((tdClass != null) ? " class=\"" + tdClass + "\"" : "") +
            ">");
        out.write(title);
        
        //Display expansion link
        if (showExpandLink) {
          out.write(" (" +pagedListInfo.getExpandLink("Show more", "Return to multiple list") + ")");
        }
        out.write("</td>");
        
        //The status cell on the right
        out.write("<td valign=\"top\" align=\"right\">");
        out.write("<input type=\"hidden\" name=\"offset\" value=\"\">");
        out.write("<input type=\"hidden\" name=\"pagedListInfoId\" value=\"" + object + "\">");
        
        //Display next/previous buttons
        if (pagedListInfo.getExpandedSelection() || !showExpandLink) {
          out.write(" [" +
            pagedListInfo.getPreviousPageLink("<font class='underline'>Previous</font>", "Previous") +
            "|" +
            pagedListInfo.getNextPageLink("<font class='underline'>Next</font>", "Next") +
            "] ");
          out.write("<br>");
        }
        
        //Display record count
        if (pagedListInfo.getMaxRecords() > 0) {
          out.write("Records " + (pagedListInfo.getCurrentOffset()+1) + " to ");
          if ((pagedListInfo.getCurrentOffset() + pagedListInfo.getItemsPerPage()) < pagedListInfo.getMaxRecords()) {
            out.write(""+(pagedListInfo.getCurrentOffset() + pagedListInfo.getItemsPerPage()));
          } else {
            out.write(""+pagedListInfo.getMaxRecords());
          }
          out.write(" of " + pagedListInfo.getMaxRecords() + " total");
        } else {
          out.write("No records to display");
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


  public int doEndTag() {
    return EVAL_PAGE;
  }
}

