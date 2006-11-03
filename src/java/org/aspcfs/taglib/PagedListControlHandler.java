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

import com.darkhorseventures.database.ConnectionElement;

import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.Template;
import org.aspcfs.utils.web.PagedListInfo;

import javax.portlet.PortletRequest;
import javax.portlet.RenderResponse;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.HashMap;
import java.util.Hashtable;

/**
 * Provides a visual control panel (html form) that allows the user to jump to
 * another page, change the number of entries per page, etc.
 * 
 * @author matt rajkowski
 * @version $Id: PagedListControlHandler.java,v 1.2 2002/08/06 21:03:07 akhi_m
 *          Exp $
 * @created June 12, 2002
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
  private boolean enableJScript = false;
  private String form = "0";

  /**
   * Sets the name attribute of the PagedListControlHandler object
   * 
   * @param tmp
   *          The new name value
   */
  public final void setName(String tmp) {
    name = tmp;
  }

  /**
   * Sets the object attribute of the PagedListControlHandler object
   * 
   * @param tmp
   *          The new object value
   */
  public final void setObject(String tmp) {
    object = tmp;
  }

  /**
   * Sets the bgColor attribute of the PagedListControlHandler object
   * 
   * @param tmp
   *          The new bgColor value
   */
  public final void setBgColor(String tmp) {
    bgColor = tmp;
  }

  /**
   * Sets the fontColor attribute of the PagedListControlHandler object
   * 
   * @param tmp
   *          The new fontColor value
   */
  public final void setFontColor(String tmp) {
    fontColor = tmp;
  }

  /**
   * Sets the tdClass attribute of the PagedListControlHandler object
   * 
   * @param tmp
   *          The new tdClass value
   */
  public final void setTdClass(String tmp) {
    tdClass = tmp;
  }

  /**
   * Sets the showForm attribute of the PagedListControlHandler object
   * 
   * @param showForm
   *          The new showForm value
   */

  public void setShowForm(String showForm) {
    this.showForm = "true".equalsIgnoreCase(showForm);
  }

  /**
   * Sets the resetList attribute of the PagedListControlHandler object
   * 
   * @param resetList
   *          The new resetList value
   */
  public void setResetList(String resetList) {
    this.resetList = "true".equalsIgnoreCase(resetList);
  }

  /**
   * Gets the abbreviate attribute of the PagedListControlHandler object
   * 
   * @return The abbreviate value
   */
  public boolean getAbbreviate() {
    return abbreviate;
  }

  /**
   * Sets the abbreviate attribute of the PagedListControlHandler object
   * 
   * @param abbreviate
   *          The new abbreviate value
   */
  public void setAbbreviate(boolean abbreviate) {
    this.abbreviate = abbreviate;
  }

  /**
   * Sets the attribute of the PagedListControlHandler object
   * 
   * @param enableJScript
   *          The new enableJScript value
   */
  public void setEnableJScript(boolean enableJScript) {
    this.enableJScript = enableJScript;
  }

  /**
   * Sets the enableJScript attribute of the PagedListControlHandler object
   * 
   * @param tmp
   *          The new enableJScript value
   */
  public void setEnableJScript(String tmp) {
    this.enableJScript = DatabaseUtils.parseBoolean(tmp);
  }

  public void setForm(String form) {
    this.form = form;
  }

  /**
   * Description of the Method
   * 
   * @return Description of the Returned Value
   */
  public final int doStartTag() {
    return EVAL_BODY_INCLUDE;
  }

  /**
   * Description of the Method
   * 
   * @return Description of the Returned Value
   */
  public int doEndTag() {
    ApplicationPrefs prefs = (ApplicationPrefs) pageContext.getServletContext().getAttribute("applicationPrefs");
    ConnectionElement ce = (ConnectionElement) pageContext.getSession().getAttribute("ConnectionElement");
    SystemStatus systemStatus = null;
    if (ce != null) {
      Hashtable statusList = (Hashtable) pageContext.getServletContext().getAttribute("SystemStatus");
      systemStatus = (SystemStatus) statusList.get(ce.getUrl());
    }
    try {
      PagedListInfo pagedListInfo = (PagedListInfo) pageContext.getSession().getAttribute(object);

      // To handle PortletSession
      if (pagedListInfo == null) {
        PortletRequest renderRequest = (PortletRequest) pageContext.getRequest().getAttribute(org.apache.pluto.tags.Constants.PORTLET_REQUEST);        
        if (renderRequest != null) {
          pagedListInfo = (PagedListInfo) renderRequest.getPortletSession().getAttribute(object);
        }
      }

      RenderResponse renderResponse = (RenderResponse) pageContext.getRequest().getAttribute(org.apache.pluto.tags.Constants.PORTLET_RESPONSE);

      if (pagedListInfo != null) {
        pagedListInfo.setShowForm(showForm);
        pagedListInfo.setResetList(resetList);
        pagedListInfo.setEnableJScript(enableJScript);
        JspWriter out = this.pageContext.getOut();

        String batchHTML = (String) this.getValue("batchHTML");
        if (enableJScript) {
          out.write("<SCRIPT LANGUAGE=\"JavaScript\" TYPE=\"text/javascript\" SRC=\"javascript/pageListInfo.js\"></SCRIPT>");
        }
        out.write("<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">");
        out.write(pagedListInfo.getListPropertiesHeader(name));
        out.write("<tr>");
        out.write("<td valign=\"middle\" " + "align=\"center\"" + ((bgColor != null) ? " bgColor=\"" + bgColor + "\"" : "")
            + ((tdClass != null) ? " class=\"" + tdClass + "\"" : "") + " width=\"100%\">");
        out.write("<input type=\"hidden\" name=\"offset\" value=\"\">");
        out.write("<input type=\"hidden\" name=\"pagedListInfoId\" value=\"" + object + "\">");
        if (systemStatus != null) {
          HashMap map = new HashMap();
          map.put("${pagedListInfo.numericalPageEntry}", "" + pagedListInfo.getNumericalPageEntry());
          map.put("${pagedListInfo.numberOfPages}", ((pagedListInfo.getNumberOfPages() == 0) ? "1" : String.valueOf(pagedListInfo.getNumberOfPages())));
          out.write("["
              + pagedListInfo.getPreviousPageLink("<font class='underline'>" + systemStatus.getLabel("label.previous") + "</font>", systemStatus
                  .getLabel("label.previous"), form,renderResponse)
              + "|"
              + pagedListInfo.getNextPageLink("<font class='underline'>" + systemStatus.getLabel("label.next") + "</font>",
                  systemStatus.getLabel("label.next"), form,renderResponse) + "] ");
          out.write("<font color=\"" + fontColor + "\">");
          if (!abbreviate) {
            map.put("${pagedListInfo.itemsPerPageEntry}", pagedListInfo.getItemsPerPageEntry(systemStatus.getLabel("quotes.all", "All")));
            out.write(getLabel(map, systemStatus.getLabel("pagedListInfo.pagedListControlHandler.notAbbreviation")));
          } else {
            out.write(getLabel(map, systemStatus.getLabel("pagedListInfo.pagedListControlHandler.abbreviation")));
            out.write("&nbsp;&nbsp;");
          }
          out.write("<input type=\"submit\" value=\"" + systemStatus.getLabel("button.go") + "\">");
        } else {          
          out.write("[" + pagedListInfo.getPreviousPageLink("<font class='underline'>Previous</font>", "Previous", form, renderResponse) + "|"
              + pagedListInfo.getNextPageLink("<font class='underline'>Next</font>", "Next", form, renderResponse) + "] ");
          out.write("<font color=\"" + fontColor + "\">");
          out.write("Page " + pagedListInfo.getNumericalPageEntry() + " ");
          if (!abbreviate) {
            out.write("of " + ((pagedListInfo.getNumberOfPages() == 0) ? "1" : String.valueOf(pagedListInfo.getNumberOfPages())) + ", ");

            String quotesAll = prefs.getLabel("quotes.all", prefs.get("SYSTEM.LANGUAGE"));
            if (quotesAll == null) {
              quotesAll = "All";
            }
            out.write("Items per page: " + pagedListInfo.getItemsPerPageEntry(quotesAll + " "));
          } else {
            out.write("of " + ((pagedListInfo.getNumberOfPages() == 0) ? "1" : String.valueOf(pagedListInfo.getNumberOfPages())));
            out.write("&nbsp;&nbsp;");
          }
          out.write("<input type=\"submit\" value=\"go\">");
        }
        out.write("</font>");
        out.write("</td>");
        out.write("</tr>");
        if (batchHTML != null) {
          out.write("<tr><td valign=\"middle\" align=\"center\"" + ((bgColor != null) ? " bgColor=\"" + bgColor + "\"" : "")
              + ((tdClass != null) ? " class=\"" + tdClass + "\"" : "") + " nowrap>");
          out.write(batchHTML);
          out.write("</td></tr>");
        }
        out.write(pagedListInfo.getListPropertiesFooter());
        out.write("</table>");
      } else {
        System.out.println("PagedListControlHandler-> Control not found in request: " + object);
      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }
    return EVAL_PAGE;
  }

  /**
   * Gets the label attribute of the PagedListControlHandler object
   * 
   * @param map
   *          Description of the Parameter
   * @param input
   *          Description of the Parameter
   * @return The label value
   */
  public String getLabel(HashMap map, String input) {
    Template template = new Template(input);
    template.setParseElements(map);
    return template.getParsedText();
  }
}
