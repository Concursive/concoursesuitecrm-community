package org.aspcfs.taglib;

import org.aspcfs.modules.website.base.PageGroup;
import org.aspcfs.modules.website.base.Site;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.StringUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;

/**
 * This Class outs a calendar field based on the user's locale information for
 * the current UserBean session.
 *
 * @author Matt Rajkowski
 * @version $Id: Exp $
 * @created May 19, 2006
 */
public class PortalPageGroupURLHandler extends TagSupport implements TryCatchFinally {
  private boolean showLink = false;
  private String pageGroup = null;

  public void doCatch(Throwable throwable) throws Throwable {
    // Required but not needed
  }

  public void doFinally() {
    // Reset each property or else the value gets reused
    showLink = false;
    pageGroup = null;
  }

  /**
   * Gets the showLink attribute of the PortalPageGroupURLHandler object
   *
   * @return The showLink value
   */
  public boolean getShowLink() {
    return showLink;
  }


  /**
   * Sets the showLink attribute of the PortalPageGroupURLHandler object
   *
   * @param tmp The new showLink value
   */
  public void setShowLink(boolean tmp) {
    this.showLink = tmp;
  }


  /**
   * Sets the showLink attribute of the PortalPageGroupURLHandler object
   *
   * @param tmp The new showLink value
   */
  public void setShowLink(String tmp) {
    this.showLink = DatabaseUtils.parseBoolean(tmp);
  }

  public String getPageGroup() {
    return pageGroup;
  }

  public void setPageGroup(String pageGroup) {
    this.pageGroup = pageGroup;
  }

  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   * @throws JspException                   Description of the Exception
   */
  public int doStartTag() throws JspException {
    try {

      Site site = (Site) pageContext.getRequest().getAttribute("site");

      PageGroup pageGroup = null;
      if (this.getPageGroup() != null) {
        pageGroup = (PageGroup) pageContext.getAttribute(this.getPageGroup());
      } else {
        pageGroup = (PageGroup) pageContext.getAttribute("pageGroup");
      }

      String portal = (String) pageContext.getRequest().getAttribute("portal");

      String popup = (String) pageContext.getRequest().getParameter("popup");

      StringBuffer buffer = new StringBuffer();
      if (this.getShowLink()) {
        buffer.append("<a href=\"");
        if (portal != null && "true".equals(portal)) {
          buffer.append("Portal.do?command=Default");
        } else {
          buffer.append("Sites.do?command=Details");
        }
        buffer.append("&siteId=" + site.getId() + "&tabId=" + site.getTabToDisplay().getId() + "&pageId=" + pageGroup.getPageList().getDefaultPageId() + ((popup != null && "true".equals(popup)) ? "&popup=true" : "") + "\">" + pageGroup.getName() + "</a>");
      } else {
        buffer.append(StringUtils.toHtml(pageGroup.getName()));
      }

      if (portal != null && "true".equals(portal)) {
        //do nothing
      } else {
        buffer.append("&nbsp;<a href=\"javascript:displayMenuPageGroup('selectpagegroup" + pageGroup.getId() + "','menuPageGroup','" + pageGroup.getId() + "','" + site.getId() + "','" + site.getTabToDisplay().getId() + "');\"");
        buffer.append("onMouseOver=\"over(0, 'pagegroup" + pageGroup.getId() + "')\" onmouseout=\"out(0, 'pagegroup" + pageGroup.getId() + "'); hideMenu('menuPageGroup');\">");
        buffer.append("<img src=\"images/select.gif\" name=\"selectpagegroup" + pageGroup.getId() + "\" id=\"selectpagegroup" + pageGroup.getId() + "\" align=\"absmiddle\" border=\"0\"></a>");
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
