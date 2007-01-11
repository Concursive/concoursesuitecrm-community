package org.aspcfs.taglib;

import org.aspcfs.modules.website.base.Page;
import org.aspcfs.modules.website.base.PageGroup;
import org.aspcfs.modules.website.base.Site;
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
public class PortalPageURLHandler extends TagSupport implements TryCatchFinally {

  private String page = null;
  private String pageGroup = null;

  public void doCatch(Throwable throwable) throws Throwable {
    // Required but not needed
  }

  public void doFinally() {
    // Reset each property or else the value gets reused
    page = null;
    pageGroup = null;
  }

  public void setPage(String page) {
    this.page = page;
  }

  public void setPageGroup(String pageGroup) {
    this.pageGroup = pageGroup;
  }

  public String getPage() {
    return page;
  }

  public String getPageGroup() {
    return pageGroup;
  }

  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   * @throws javax.servlet.jsp.JspException Description of the Exception
   */
  public int doStartTag() throws JspException {
    try {

      Site site = (Site) pageContext.getRequest().getAttribute("site");
      int siteId = site.getId();
      int tabId = site.getTabToDisplay().getId();

      PageGroup pageGroup = null;
      if (this.getPageGroup() != null) {
        pageGroup = (PageGroup) pageContext.getAttribute(this.getPageGroup());
      } else {
        pageGroup = (PageGroup) pageContext.getAttribute("pageGroup");
      }

      Page page = null;
      if (this.getPage() != null) {
        page = (Page) pageContext.getAttribute(this.getPage());
      } else {
        page = (Page) pageContext.getAttribute("page");
      }

      String portal = (String) pageContext.getRequest().getAttribute("portal");

      String popup = pageContext.getRequest().getParameter("popup");

      StringBuffer buffer = new StringBuffer();
      buffer.append("<a href=\"");
      if (portal != null && "true".equals(portal)) {
        buffer.append("Portal.do?command=Default");
      } else {
        buffer.append("Sites.do?command=Details");
      }
      buffer.append("&siteId=" + siteId + "&tabId=" + tabId + "&pageId=" + page.getId() + ((popup != null && "true".equals(popup)) ? "&popup=true" : "") + "\">" + StringUtils.toHtml(page.getName()) + "</a>");
      if (portal != null && "true".equals(portal)) {
        //do nothing
      } else {
        buffer.append("&nbsp;<a href=\"javascript:displayMenuPage('selectpage" + page.getId() + "','menuPage','" + page.getId() + "','" + pageGroup.getId() + "','" + site.getId() + "','" + tabId + "','" + site.getTabToDisplay().getThisPageToBuild().getId() + "');\"");
        buffer.append("onMouseOver=\"over(0, 'page" + page.getId() + "')\" onmouseout=\"out(0, 'page" + page.getId() + "'); hideMenu('menuPage');\">");
        buffer.append("<img src=\"images/select.gif\" name=\"selectpage" + page.getId() + "\" id=\"selectpage" + page.getId() + "\" align=\"absmiddle\" border=\"0\"></a>");
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
