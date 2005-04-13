package org.aspcfs.taglib;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.controller.*;
import com.darkhorseventures.database.*;
import java.util.*;

/**
 *  Description of the Class
 *
 *@author     partha
 *@created    September 16, 2004
 *@version    $Id$
 */
public class PagedListAlphabeticalLinksHandler extends TagSupport {
  private String name = "alphabeticalLinksProperties";
  private String object = null;


  /**
   *  Sets the object attribute of the PagedListAlphabeticalLinksHandler object
   *
   *@param  tmp  The new object value
   */
  public void setObject(String tmp) {
    this.object = tmp;
  }


  /**
   *  Sets the name attribute of the PagedListAlphabeticalLinksHandler object
   *
   *@param  tmp  The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   *  Gets the name attribute of the PagedListAlphabeticalLinksHandler object
   *
   *@return    The name value
   */
  public String getName() {
    return name;
  }


  /**
   *  Gets the object attribute of the PagedListAlphabeticalLinksHandler object
   *
   *@return    The object value
   */
  public String getObject() {
    return object;
  }


  /**
   *  Description of the Method
   *
   *@return                   Description of the Return Value
   *@exception  JspException  Description of the Exception
   */
  public final int doStartTag() throws JspException {
    try {
      PagedListInfo pagedListInfo = (PagedListInfo) pageContext.getSession().getAttribute(object);
      ConnectionElement ce = (ConnectionElement) pageContext.getSession().getAttribute("ConnectionElement");
      if (ce == null) {
        System.out.println("PagedListStatusHandler-> ConnectionElement is null");
      }
      SystemStatus systemStatus = (SystemStatus) ((Hashtable) pageContext.getServletContext().getAttribute("SystemStatus")).get(ce.getUrl());
      if (systemStatus == null) {
        System.out.println("PagedListStatusHandler-> SystemStatus is null");
      }
      if (systemStatus != null) {
        pagedListInfo.setLettersArray(systemStatus.getLettersArray("language.alphabet"));
      }

      JspWriter out = this.pageContext.getOut();
      out.write(pagedListInfo.getAlphabeticalPageLinks());
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }
    return SKIP_BODY;
  }
}

