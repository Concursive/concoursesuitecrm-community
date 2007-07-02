/*
 *  Copyright(c) 2007 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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


import org.aspcfs.modules.website.base.Page;
import org.aspcfs.modules.website.base.Site;
import org.aspcfs.modules.website.base.Tab;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;

/**
 * Description of the Class
 *
 * @author zhenya.zhidok
 * @version $Id: Exp$
 * @created 03.04.2007
 */
public class MetaTagOutputHandler extends TagSupport implements TryCatchFinally {

  private String name = null;
  private String content = null;

  public void doCatch(Throwable throwable) throws Throwable {
    // Required but not needed
  }

  public void doFinally() {
    // Reset each property or else the value gets reused
    name = null;
    content = null;
  }


  /**
   * Sets the name attribute of the MetaTagOutputHandler object
   *
   * @param tmp The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   * Sets the content attribute of the MetaTagOutputHandler object
   *
   * @param tmp The new content value
   */
  public void setContent(String tmp) {
    this.content = tmp;
  }


  /**
   * Gets the name attribute of the MetaTagOutputHandler object
   *
   * @return The name value
   */
  public String getName() {
    return name;
  }


  /**
   * Gets the content attribute of the MetaTagOutputHandler object
   *
   * @return The content value
   */
  public String getContent() {
    return content;
  }

  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   * @throws JspException Description of the Exception
   */
  public int doStartTag() throws JspException {
    try {
      Site site = (Site) pageContext.getRequest().getAttribute("site");
      if (site != null) {
        Tab tab = site.getTabToDisplay();
        StringBuffer buffer = new StringBuffer();
        if (name != null && content != null) {
          buffer.append("<meta name=\"" + name + "\" content=\"" + content + "\">");
        } else {
          if (tab != null) {
            Page page = tab.getThisPageToBuild();
            if (page != null) {
              if (page.getName() != null && !"".equals(page.getName())) {
                buffer.append("<meta name=\"title\" content=\"" + page.getName() + "\" >");
              } else {
                if (tab.getDisplayText() != null && !"".equals(tab.getDisplayText())) {
                  buffer.append("<meta name=\"title\" content=\"" + tab.getDisplayText() + "\" >");
                }
              }

              if (page.getNotes() != null && !"".equals(page.getNotes())) {
                buffer.append("\n  <meta name=\"description\" content=\"" + page.getNotes() + "\" >");
              } else {
                if (tab.getInternalDescription() != null && !"".equals(tab.getInternalDescription())) {
                  buffer.append("\n  <meta name=\"description\" content=\"" + tab.getInternalDescription() + "\" >");
                }
              }

              if (page.getKeywords() != null && !"".equals(page.getKeywords())) {
                buffer.append("\n  <meta name=\"keywords\" content=\"" + page.getKeywords() + "\" >");
              } else {
                if (tab.getKeywords() != null && !"".equals(tab.getKeywords())) {
                  buffer.append("\n  <meta name=\"keywords\" content=\"" + tab.getKeywords() + "\" >");
                }
              }
            }
          }
        }
        this.pageContext.getOut().write(buffer.toString());
      }
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

