/*
 *  Copyright(c) 2004 Team Elements LLC (http://www.teamelements.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Team Elements LLC. Permission to use, copy, and modify this
 *  material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. TEAM
 *  ELEMENTS MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL TEAM ELEMENTS LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR ANY
 *  DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.taglib;

import org.aspcfs.modules.website.base.Site;
import org.aspcfs.modules.website.base.Tab;
import org.aspcfs.utils.StringUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * This Class outs a calendar field based on the user's locale information for
 * the current UserBean session.
 *
 * @author Kailash Bhoopalam
 * @version $Id: Exp $
 * @created May 19, 2006
 */
public class PortalTabURLHandler extends TagSupport {

  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   * @throws JspException Description of the Exception
   */
  public int doStartTag() throws JspException {
    try {

      Site site = (Site) pageContext.getRequest().getAttribute("site");
      int siteId = site.getId();

      Tab tab = (Tab) pageContext.getAttribute("tab");
      int tabId = tab.getId();
      String displayText = tab.getDisplayText();

      String portal = (String) pageContext.getRequest().getAttribute("portal");

      String popup = (String) pageContext.getRequest().getParameter("popup");

      if (System.getProperty("DEBUG") != null) {
        System.out.println("popup ==> " + popup);
        if (tab != null) {
          System.out.println("Tab ==> " + tab.getId());
        }
        System.out.println("Portal ==> " + portal);
        if (site != null) {
          System.out.println("site ==> " + site.getId());
        }
      }

      StringBuffer buffer = new StringBuffer();
      buffer.append("<a href=");
      if (portal != null && "true".equals(portal)) {
        buffer.append("Portal.do?command=Default");
      } else {
        buffer.append("Sites.do?command=Details");
      }
      buffer.append("&siteId=" + siteId + "&tabId=" + tabId + ((popup != null && "true".equals(popup)) ? "&popup=true" : "") + ">" + StringUtils.toHtml(displayText) + "</a>");
      if (portal != null && "true".equals(portal)) {
        //do nothing
      } else {
        buffer.append("&nbsp;<a href=\"javascript:displayMenuTab('selecttab" + tabId + "','menuTab','" + tabId + "','" + siteId + "');\"");
        buffer.append("onMouseOver=\"over(0, 'tab" + tabId + "')\" onmouseout=\"out(0, 'tab" + tabId + "'); hideMenu('menuTab');\">");
        buffer.append("<img src=\"images/select.gif\" name=\"selecttab" + tabId + "\" id=\"selecttab" + tabId + "\" align=\"absmiddle\" border=\"0\"></a>");
      }
      this.pageContext.getOut().write(buffer.toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return SKIP_BODY;
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

