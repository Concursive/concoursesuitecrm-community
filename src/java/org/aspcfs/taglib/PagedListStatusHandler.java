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
import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.Template;
import org.aspcfs.utils.web.PagedListInfo;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;
import java.util.HashMap;
import java.util.Hashtable;


/**
 * Displays the status of the PagedListInfo specified with record counts, next
 * and previous buttons, and optionally other items.
 *
 * @author chris
 * @version $Id: PagedListStatusHandler.java,v 1.9 2003/03/21 13:50:06
 *          mrajkowski Exp $
 * @created October, 2002
 */
public class PagedListStatusHandler extends BodyTagSupport implements TryCatchFinally {
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
  private String form = "0";
  private String externalJScript = null;
  private String externalText = null;
  private String params = "";

  public void doCatch(Throwable throwable) throws Throwable {
    // Required but not needed
  }

  public void doFinally() {
    // Reset each property or else the value gets reused
    name = "statusProperties";
    label = "Records";
    object = null;
    bgColor = null;
    fontColor = "#666666";
    tdClass = null;
    tableClass = null;
    showHiddenParams = false;
    showForm = true;
    resetList = true;
    showExpandLink = false;
    title = "&nbsp;";
    showRefresh = true;
    showControlOnly = false;
    scrollReload = false;
    enableJScript = false;
    type = null;
    form = "0";
    externalJScript = null;
    externalText = null;
    params = "";
  }


  /**
   * Sets the name attribute of the PagedListStatusHandler object
   *
   * @param tmp The new name value
   */
  public final void setName(String tmp) {
    name = tmp;
  }


  /**
   * Sets the label attribute of the PagedListStatusHandler object
   *
   * @param tmp The new label value
   */
  public void setLabel(String tmp) {
    this.label = tmp;
  }


  /**
   * Sets the object attribute of the PagedListStatusHandler object
   *
   * @param tmp The new object value
   */
  public final void setObject(String tmp) {
    object = tmp;
  }


  /**
   * Sets the type attribute of the PagedListStatusHandler object
   *
   * @param tmp The new type value
   */
  public void setType(String tmp) {
    this.type = tmp;
  }


  /**
   * Gets the type attribute of the PagedListStatusHandler object
   *
   * @return The type value
   */
  public String getType() {
    return type;
  }


  /**
   * Gets the showExpandLink attribute of the PagedListStatusHandler object
   *
   * @return The showExpandLink value
   */
  public boolean getShowExpandLink() {
    return showExpandLink;
  }


  /**
   * Sets the showExpandLink attribute of the PagedListStatusHandler object
   *
   * @param showExpandLink The new showExpandLink value
   */
  public void setShowExpandLink(boolean showExpandLink) {
    this.showExpandLink = showExpandLink;
  }


  /**
   * Sets the bgColor attribute of the PagedListStatusHandler object
   *
   * @param tmp The new bgColor value
   */
  public final void setBgColor(String tmp) {
    bgColor = tmp;
  }


  /**
   * Sets the fontColor attribute of the PagedListStatusHandler object
   *
   * @param tmp The new fontColor value
   */
  public final void setFontColor(String tmp) {
    fontColor = tmp;
  }


  /**
   * Gets the title attribute of the PagedListStatusHandler object
   *
   * @return The title value
   */
  public String getTitle() {
    return title;
  }


  /**
   * Sets the title attribute of the PagedListStatusHandler object
   *
   * @param title The new title value
   */
  public void setTitle(String title) {
    this.title = title;
  }


  /**
   * Sets the tdClass attribute of the PagedListStatusHandler object
   *
   * @param tmp The new tdClass value
   */
  public final void setTdClass(String tmp) {
    tdClass = tmp;
  }


  /**
   * Sets the tableClass attribute of the PagedListStatusHandler object
   *
   * @param tmp The new tableClass value
   */
  public final void setTableClass(String tmp) {
    tableClass = tmp;
  }


  /**
   * Sets the showForm attribute of the PagedListStatusHandler object
   *
   * @param tmp The new showForm value
   */
  public void setShowForm(String tmp) {
    this.showForm = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the showHiddenParams attribute of the PagedListStatusHandler object
   *
   * @param tmp The new showHiddenParams value
   */
  public void setShowHiddenParams(String tmp) {
    this.showHiddenParams = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the resetList attribute of the PagedListStatusHandler object
   *
   * @param resetList The new resetList value
   */
  public void setResetList(String resetList) {
    this.resetList = "true".equalsIgnoreCase(resetList);
  }


  /**
   * Sets the showRefresh attribute of the PagedListStatusHandler object
   *
   * @param tmp The new showRefresh value
   */
  public void setShowRefresh(boolean tmp) {
    this.showRefresh = tmp;
  }


  /**
   * Sets the showRefresh attribute of the PagedListStatusHandler object
   *
   * @param tmp The new showRefresh value
   */
  public void setShowRefresh(String tmp) {
    this.showRefresh = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the showRefresh attribute of the PagedListStatusHandler object
   *
   * @return The showRefresh value
   */
  public boolean getShowRefresh() {
    return showRefresh;
  }


  /**
   * Sets the showControlOnly attribute of the PagedListStatusHandler object
   *
   * @param tmp The new showControlOnly value
   */
  public void setShowControlOnly(boolean tmp) {
    this.showControlOnly = tmp;
  }


  /**
   * Sets the showControlOnly attribute of the PagedListStatusHandler object
   *
   * @param tmp The new showControlOnly value
   */
  public void setShowControlOnly(String tmp) {
    this.showControlOnly = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the scrollReload attribute of the PagedListStatusHandler object
   *
   * @param tmp The new scrollReload value
   */
  public void setScrollReload(boolean tmp) {
    this.scrollReload = tmp;
  }


  /**
   * Sets the scrollReload attribute of the PagedListStatusHandler object
   *
   * @param tmp The new scrollReload value
   */
  public void setScrollReload(String tmp) {
    this.scrollReload = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the enableJScript attribute of the PagedListStatusHandler object
   *
   * @param enableJScript The new enableJScript value
   */
  public void setEnableJScript(boolean enableJScript) {
    this.enableJScript = enableJScript;
  }


  /**
   * Sets the enableJScript attribute of the PagedListStatusHandler object
   *
   * @param tmp The new enableJScript value
   */
  public void setEnableJScript(String tmp) {
    this.enableJScript = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the form attribute of the PagedListStatusHandler object
   *
   * @param form The new form value
   */
  public void setForm(String form) {
    this.form = form;
  }


  /**
   * Gets the externalJScript attribute of the PagedListStatusHandler object
   *
   * @return The externalJScript value
   */
  public String getExternalJScript() {
    return externalJScript;
  }


  /**
   * Sets the externalJScript attribute of the PagedListStatusHandler object
   *
   * @param tmp The new externalJScript value
   */
  public void setExternalJScript(String tmp) {
    this.externalJScript = tmp;
  }


  /**
   * Gets the externalText attribute of the PagedListStatusHandler object
   *
   * @return The externalText value
   */
  public String getExternalText() {
    return externalText;
  }


  /**
   * Sets the externalText attribute of the PagedListStatusHandler object
   *
   * @param tmp The new externalText value
   */
  public void setExternalText(String tmp) {
    this.externalText = tmp;
  }


  /**
   * Sets the params attribute of the PagedListStatusHandler object
   *
   * @param tmp The new params value
   */
  public void setParams(String tmp) {
    this.params = tmp;
  }


  /**
   * Gets the params attribute of the PagedListStatusHandler object
   *
   * @return The params value
   */
  public String getParams() {
    return params;
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   * @throws JspException Description of the Exception
   */
  public final int doAfterBody() throws JspException {
    try {
      // Display the body
      BodyContent bodyContent = getBodyContent();
      if (bodyContent != null) {
        title = bodyContent.getString();
      }
    } catch (Exception e) {
    }
    return SKIP_BODY;
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   * @throws JspException Description of the Exception
   */
  public final int doEndTag() throws JspException {
    ApplicationPrefs prefs = (ApplicationPrefs) pageContext.getServletContext().getAttribute(
        "applicationPrefs");
    ConnectionElement ce = (ConnectionElement) pageContext.getSession().getAttribute(
        "ConnectionElement");
    if (ce == null) {
      System.out.println("PagedListStatusHandler-> ConnectionElement is null");
    }
    Hashtable statusList = (Hashtable) pageContext.getServletContext().getAttribute("SystemStatus");
    SystemStatus systemStatus = (SystemStatus) statusList.get(ce.getUrl());
    if (systemStatus == null) {
      System.out.println("PagedListStatusHandler-> SystemStatus is null");
    }
    try {
      PagedListInfo pagedListInfo = (PagedListInfo) pageContext.getSession().getAttribute(
          object);

      if (systemStatus != null) {
        if (systemStatus.getLabel(
            "pagedListInfo.pagedListStatus." + title) != null) {
          title = systemStatus.getLabel(
              "pagedListInfo.pagedListStatus." + title);
        }
        if (systemStatus.getLabel(
            "pagedListInfo.pagedListStatus." + externalText) != null) {
          externalText = systemStatus.getLabel(
              "pagedListInfo.pagedListStatus." + externalText);
        }
      } else {
        if (prefs.getLabel(
            "pagedListInfo.pagedListStatus." + title, prefs.get("SYSTEM.LANGUAGE")) != null) {
          title = prefs.getLabel(
              "pagedListInfo.pagedListStatus." + title, prefs.get("SYSTEM.LANGUAGE"));
        }
        if (prefs.getLabel(
            "pagedListInfo.pagedListStatus." + externalText, prefs.get("SYSTEM.LANGUAGE")) != null) {
          externalText = prefs.getLabel(
              "pagedListInfo.pagedListStatus." + externalText, prefs.get("SYSTEM.LANGUAGE"));
        }
      }
      if (pagedListInfo != null) {
        pagedListInfo.setEnableJScript(enableJScript);
        JspWriter out = this.pageContext.getOut();
        //include java scripts if any
        if (enableJScript) {
          out.write(
              "<SCRIPT LANGUAGE=\"JavaScript\" TYPE=\"text/javascript\" SRC=\"javascript/pageListInfo.js\"></SCRIPT>");
        }
        //Draw the header of the PagedList table
        out.write(
            "<table " +
                ((tableClass != null) ? "class=\"" + tableClass + "\" " : "") +
                "align=\"center\" width=\"100%\" cellpadding=\"4\" cellspacing=\"0\" border=\"0\">");
        out.write("<tr>");
        //Display the title
        if (!showControlOnly) {
          //Display the title
          if (showExpandLink || externalJScript != null) {
            out.write("<th ");
          } else {
            out.write("<td ");
          }
          out.write(
              "nowrap valign=\"bottom\" " +
                  "align=\"left\"" +
                  ((bgColor != null) ? " bgColor=\"" + bgColor + "\"" : "") +
                  ((tdClass != null) ? " class=\"" + tdClass + "\"" : "") +
                  ">");
          // Show the title
          String newLabel = null;
          if (type != null && !"".equals(type)) {
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
            out.write(
                "<input type=\"hidden\" name=\"pagedListInfoId\" value=\"" + object + "\" />");
          }
          if (showExpandLink || externalJScript != null) {
            out.write("</th>");
          } else {
            out.write("</td>");
          }
          //Display expansion link
          if (!showExpandLink && externalJScript != null) {
            out.write(
                "<td nowrap width=\"100%\" valign=\"bottom\" " +
                    "align=\"left\"" +
                    ">");
            if (systemStatus != null) {
              out.write(
                  " (<a href=\"javascript:" + externalJScript + "\">"
                      + (externalText != null && !"".equals(externalText) ? externalText : systemStatus.getLabel(
                      "pagedListInfo.showForm"))
                      + "</a>)");
            } else {
              out.write(
                  " (" + "<a href=\"javascript:" + externalJScript + "\">"
                      + (externalText != null && !"".equals(externalText) ? externalText : "Show Form")
                      + "</a>)");
            }
            out.write("</td>");
          } else if (showExpandLink) {
            out.write(
                "<td nowrap width=\"100%\" valign=\"bottom\" " +
                    "align=\"left\"" +
                    ">");
            if (systemStatus != null) {
              out.write(
                  " (" + pagedListInfo.getExpandLink(
                      systemStatus.getLabel("pagedListInfo.showMore"), systemStatus.getLabel(
                      "pagedListInfo.returnToOverview"), params) + ")");
            } else {
              out.write(
                  " (" + pagedListInfo.getExpandLink(
                      "Show more", "Return to overview", params) + ")");
            }
            out.write("</td>");
          } else {
            out.write(
                "<td nowrap width=\"100%\" valign=\"bottom\" align=\"left\" >&nbsp;</td>");
          }
        }
        String returnAction = pageContext.getRequest().getParameter("return");
        if (returnAction == null || !returnAction.equals("details")) {
          //The status cell on the right
          out.write(
              "<td valign=\"bottom\" align=\"" + (showControlOnly ? "center" : "right") + "\" nowrap>");
          //Display record count
          if (systemStatus != null) {
            HashMap map = new HashMap();
            if (pagedListInfo.getMaxRecords() > 0) {
              if ((pagedListInfo.getCurrentOffset() + 1) <= pagedListInfo.getMaxRecords()) {
                if (pagedListInfo.getItemsPerPage() == 1) {
                  //1 of 20 [Previous|Next]
                  map.put(
                      "${pagedListInfo.currentOffset}", String.valueOf(
                      pagedListInfo.getCurrentOffset() + 1));
                  map.put(
                      "${pagedListInfo.maxRecords}", "" + pagedListInfo.getMaxRecords());
                  out.write(
                      getLabel(
                          map, systemStatus.getLabel(
                          "pagedListInfo.pagedListStatus.oneItemsPerPageData")));
                } else {
                  //Items 1 to 10 of 20 total [Previous|Next]
                  if (systemStatus.getLabel("pagedListInfo.pagedListStatus." + label) != null) {
                    label = systemStatus.getLabel("pagedListInfo.pagedListStatus." + label);
                  }
                  map.put(
                      "${pagedListInfo.currentOffset}", String.valueOf(
                      pagedListInfo.getCurrentOffset() + 1));
                  map.put("${label}", "" + label);
                  if (pagedListInfo.getItemsPerPage() <= 0) {
                    map.put(
                        "${pagedListInfo.itemsPerPageDecision}", String.valueOf(
                        pagedListInfo.getMaxRecords()));
                  } else
                  if ((pagedListInfo.getCurrentOffset() + pagedListInfo.getItemsPerPage()) < pagedListInfo.getMaxRecords()) {
                    map.put(
                        "${pagedListInfo.itemsPerPageDecision}", String.valueOf(
                        pagedListInfo.getCurrentOffset() + pagedListInfo.getItemsPerPage()));
                  } else {
                    map.put(
                        "${pagedListInfo.itemsPerPageDecision}", String.valueOf(
                        pagedListInfo.getMaxRecords()));
                  }
                  map.put(
                      "${pagedListInfo.maxRecords}", "" + pagedListInfo.getMaxRecords());
                  out.write(
                      getLabel(
                          map, systemStatus.getLabel(
                          "pagedListInfo.pagedListStatus.itemsPerPageData")));
                }
              } else {
                out.write(
                    getLabel(
                        map, systemStatus.getLabel(
                        "pagedListInfo.pagedListStatus.endOfList")));
              }
            } else {
              /**
               'label' data member holds a string value that needs to be provided as a parameter to be included in the
               translated output. The string value in 'label' can be "Records", "Items" etc. The sting value itself needs to be
               translated first before the final translation output.
               */
              if (systemStatus.getLabel(
                  "pagedListInfo.pagedListStatus." + label) != null) {
                label = systemStatus.getLabel(
                    "pagedListInfo.pagedListStatus." + label);
              }
              map.put("${label}", label.toLowerCase());
              out.write(
                  getLabel(
                      map, systemStatus.getLabel(
                      "pagedListInfo.pagedListStatus.noneAvailable")));
            }
          } else {
            // The SystemStatus IS NULL here...
            if (pagedListInfo.getMaxRecords() > 0) {
              if ((pagedListInfo.getCurrentOffset() + 1) <= pagedListInfo.getMaxRecords()) {
                if (pagedListInfo.getItemsPerPage() == 1) {
                  //1 of 20 [Previous|Next]
                  out.write(
                      String.valueOf(pagedListInfo.getCurrentOffset() + 1));
                } else {
                  //Items 1 to 10 of 20 total [Previous|Next]
                  if (prefs.getLabel(
                      "pagedListInfo.pagedListStatus." + label, prefs.get("SYSTEM.LANGUAGE")) != null) {
                    label = prefs.getLabel(
                        "pagedListInfo.pagedListStatus." + label, prefs.get("SYSTEM.LANGUAGE"));
                  }
                  out.write(
                      label + " " + (pagedListInfo.getCurrentOffset() + 1) + " to ");
                  if (pagedListInfo.getItemsPerPage() <= 0) {
                    out.write(String.valueOf(pagedListInfo.getMaxRecords()));
                  } else
                  if ((pagedListInfo.getCurrentOffset() + pagedListInfo.getItemsPerPage()) < pagedListInfo.getMaxRecords()) {
                    out.write(
                        String.valueOf(
                            pagedListInfo.getCurrentOffset() + pagedListInfo.getItemsPerPage()));
                  } else {
                    out.write(String.valueOf(pagedListInfo.getMaxRecords()));
                  }
                }
                out.write(" of " + pagedListInfo.getMaxRecords());
                if (pagedListInfo.getItemsPerPage() != 1) {
                  out.write(" total");
                }
              } else {
                out.write("End of list");
              }
            } else {
              out.write("No " + label.toLowerCase() + " to display");
            }
          }
          //Display next/previous buttons
          if (pagedListInfo.getItemsPerPage() > 0 || pagedListInfo.getItemsPerPage() == -1) {
            if (pagedListInfo.getExpandedSelection() || !showExpandLink) {
              pagedListInfo.setScrollReload(scrollReload);
              if (systemStatus != null) {
                out.write(
                    " [" +
                        pagedListInfo.getPreviousPageLink(
                            "<font class='underline'>" + systemStatus.getLabel(
                                "label.previous") + "</font>", systemStatus.getLabel(
                            "label.previous"), form) +
                        "|" +
                        pagedListInfo.getNextPageLink(
                            "<font class='underline'>" + systemStatus.getLabel(
                                "label.next") + "</font>", systemStatus.getLabel(
                            "label.next"), form) +
                        "]");
              } else {
                out.write(
                    " [" +
                        pagedListInfo.getPreviousPageLink(
                            "<font class='underline'>" + prefs.getLabel(
                                "label.previous", prefs.get("SYSTEM.LANGUAGE")) + "</font>",
                            prefs.getLabel(
                                "label.previous", prefs.get("SYSTEM.LANGUAGE")), form) +
                        "|" +
                        pagedListInfo.getNextPageLink(
                            "<font class='underline'>" + prefs.getLabel(
                                "label.next", prefs.get("SYSTEM.LANGUAGE")) + "</font>",
                            prefs.getLabel(
                                "label.next", prefs.get("SYSTEM.LANGUAGE")), form) +
                        "]");
              }
              out.write(" ");
            }
          }
          //Display refresh icon
          if (pagedListInfo.hasLink() && showRefresh) {
            out.write(
                " " + pagedListInfo.getRefreshTag(
                    "<img src=\"images/refresh.gif\" border=\"0\" align=\"absbottom\" />"));
          }
          //Close the cell
          out.write("</td>");
        }
        //Close the table
        out.write("</tr></table>");
      } else {
        System.out.println(
            "PagedListStatusHandler-> Status not found in request: " + object);
      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }
    return EVAL_PAGE;
  }


  /**
   * Gets the label attribute of the PagedListStatusHandler object
   *
   * @param map   Description of the Parameter
   * @param input Description of the Parameter
   * @return The label value
   */
  public static String getLabel(HashMap map, String input) {
    Template template = new Template(input);
    template.setParseElements(map);
    return template.getParsedText();
  }

}

