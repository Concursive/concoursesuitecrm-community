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
        //out.write("<SCRIPT LANGUAGE=\"JavaScript\" TYPE=\"text/javascript\" SRC=\"/javascript/pageListInfo.js\"></SCRIPT>");
        
        out.write("<table align=\"center\" width=\"100%\" cellpadding=\"4\" cellspacing=\"0\" border=\"0\">");
        out.write("<tr>");
        
        out.write("<td width=\"50%\" rowspan=\"2\" valign=\"top\" " +
            "align=\"left\"" +
            ((bgColor != null) ? " bgColor=\"" + bgColor + "\"" : "") +
            ((tdClass != null) ? " class=\"" + tdClass + "\"" : "") +
            ">");
            
        out.write("<font size=\"-1\">" + title + "</font>");
        out.write("</td>");
        
        out.write("<td>");
        
        out.write("<table align=\"right\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">");
        out.write("<tr>");
        
        out.write("<td colspan=\"2\" valign=\"top\" " +
            "align=\"right\"" +
            ((bgColor != null) ? " bgColor=\"" + bgColor + "\"" : "") +
            ((tdClass != null) ? " class=\"" + tdClass + "\"" : "") +
            ">");
        out.write("<input type=\"hidden\" name=\"offset\" value=\"\">");
        out.write("<input type=\"hidden\" name=\"pagedListInfoId\" value=\"" + object + "\">");

        out.write("Displaying records " + (pagedListInfo.getCurrentOffset()+1) + " to ");
        if ((pagedListInfo.getCurrentOffset() + pagedListInfo.getItemsPerPage()) < pagedListInfo.getMaxRecords()) {
          out.write(""+(pagedListInfo.getCurrentOffset() + pagedListInfo.getItemsPerPage()));
        } else {
        out.write(""+pagedListInfo.getMaxRecords());
        }
        
        out.write(" of " + pagedListInfo.getMaxRecords() + " total");
        out.write("</td></tr>");
        
        out.write("<tr>");
        
        out.write("</td>");
        
        
        out.write("<td valign=\"center\" ");
        out.write("align=\"right\"");
        out.write(((bgColor != null) ? " bgColor=\"" + bgColor + "\"" : "") +
            ((tdClass != null) ? " class=\"" + tdClass + "\"" : "") +
            ">");
        
        if (pagedListInfo.getExpandedSelection() || !showExpandLink) {
            out.write("[" +
                pagedListInfo.getPreviousPageLink("<font class='underline'>Previous</font>", "Previous") +
                "|" +
                pagedListInfo.getNextPageLink("<font class='underline'>Next</font>", "Next") +
                "] ");          
        } else {
          out.write("&nbsp;");
        }
        
        if (showExpandLink) {
            //out.write(pagedListInfo.getExpandLink("<font class='underline'>Expand</font>", "Expand", "<font class='underline'>Collapse</font>"));
            out.write(pagedListInfo.getExpandLink("<img border=0 src=\"images/green-1.gif\">", "Expand", "<img border=0 src=\"images/red-1.gif\">"));
        }        
        
        
        out.write("</tr>");
        out.write("</table>");
        
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

