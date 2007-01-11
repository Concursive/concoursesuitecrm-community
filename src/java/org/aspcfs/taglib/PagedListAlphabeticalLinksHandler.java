package org.aspcfs.taglib;

import com.darkhorseventures.database.ConnectionElement;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.utils.web.PagedListInfo;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;
import java.util.Hashtable;

/**
 * Description of the Class
 *
 * @author partha
 * @version $Id$
 * @created September 16, 2004
 */
public class PagedListAlphabeticalLinksHandler extends TagSupport implements TryCatchFinally {
  private String name = "alphabeticalLinksProperties";
  private String object = null;

  public void doCatch(Throwable throwable) throws Throwable {
    // Required but not needed
  }

  public void doFinally() {
    // Reset each property or else the value gets reused
    name = "alphabeticalLinksProperties";
    object = null;
  }

  /**
   * Sets the object attribute of the PagedListAlphabeticalLinksHandler object
   *
   * @param tmp The new object value
   */
  public void setObject(String tmp) {
    this.object = tmp;
  }


  /**
   * Sets the name attribute of the PagedListAlphabeticalLinksHandler object
   *
   * @param tmp The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   * Gets the name attribute of the PagedListAlphabeticalLinksHandler object
   *
   * @return The name value
   */
  public String getName() {
    return name;
  }


  /**
   * Gets the object attribute of the PagedListAlphabeticalLinksHandler object
   *
   * @return The object value
   */
  public String getObject() {
    return object;
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   * @throws JspException Description of the Exception
   */
  public final int doStartTag() throws JspException {
    try {
      PagedListInfo pagedListInfo = (PagedListInfo) pageContext.getSession().getAttribute(
          object);
      ConnectionElement ce = (ConnectionElement) pageContext.getSession().getAttribute(
          "ConnectionElement");
      if (ce == null) {
        System.out.println(
            "PagedListStatusHandler-> ConnectionElement is null");
      }
      SystemStatus systemStatus = (SystemStatus) ((Hashtable) pageContext.getServletContext().getAttribute(
          "SystemStatus")).get(ce.getUrl());
      if (systemStatus == null) {
        System.out.println("PagedListStatusHandler-> SystemStatus is null");
      }
      if (systemStatus != null) {
        pagedListInfo.setLettersArray(
            systemStatus.getLettersArray("language.alphabet"));
      }

      JspWriter out = this.pageContext.getOut();
      out.write(pagedListInfo.getAlphabeticalPageLinks());
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }
    return SKIP_BODY;
  }
}

