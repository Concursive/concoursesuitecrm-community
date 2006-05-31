package org.aspcfs.taglib;

import org.aspcfs.modules.website.base.Site;
import org.aspcfs.modules.website.base.PageGroup;
import org.aspcfs.modules.website.base.Page;
import org.aspcfs.utils.StringUtils;

import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.JspException;

/**
 * This Class outs a calendar field based on the user's locale information for
 * the current UserBean session.
 *
 * @author Matt Rajkowski
 * @version $Id: Exp $
 * @created May 19, 2006
 */
public class PortalPageGroupURLHandler extends TagSupport {

  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   * @throws javax.servlet.jsp.JspException Description of the Exception
   */
  public int doStartTag() throws JspException {
    try {

      Site site = (Site) pageContext.getRequest().getAttribute("site");

      PageGroup pageGroup = (PageGroup) pageContext.getAttribute("pageGroup");

      String portal = (String) pageContext.getRequest().getAttribute("portal");

      String popup = (String) pageContext.getRequest().getParameter("popup");

      if (System.getProperty("DEBUG") != null) {
        System.out.println("popup ==> " + popup);
        if (pageGroup != null) {
          System.out.println("PageGroup ==> " + pageGroup.getId());
        }
        System.out.println("Portal ==> " + portal);
        if (site != null) {
          System.out.println("site ==> " + site.getId());
        }
      }

      StringBuffer buffer = new StringBuffer();
      buffer.append(StringUtils.toHtml(pageGroup.getName()) + "</a>");
      if (portal != null && "true".equals(portal)) {
        //do nothing
      } else {
        buffer.append("&nbsp;<a href=\"javascript:displayMenuPageGroup('selectpagegroup" + pageGroup.getId() + "','menuPageGroup','" + pageGroup.getId() + "','" + site.getId() + "','" + site.getTabToDisplay().getId() + "');\"");
        buffer.append("onMouseOver=\"over(0, pagegroup" + pageGroup.getId() + ")\" onmouseout=\"out(0, pagegroup" + pageGroup.getId() + "); hideMenu('menuPageGroup');\">");
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
