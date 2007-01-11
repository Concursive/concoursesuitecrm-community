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
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.StringUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;
import java.util.Hashtable;
import java.util.LinkedHashMap;

/**
 * Description of the Class
 *
 * @author Ananth
 * @created February 24, 2005
 */
public class ProductCategoryHierarchyHandler extends TagSupport implements TryCatchFinally {
  private boolean showLastLink = false;
  private boolean displayJS = false;
  private String link = null;

  public void doCatch(Throwable throwable) throws Throwable {
    // Required but not needed
  }

  public void doFinally() {
    // Reset each property or else the value gets reused
    showLastLink = false;
    displayJS = false;
    link = null;
  }

  /**
   * Sets the displayJS attribute of the ProductCategoryHierarchyHandler object
   *
   * @param tmp The new displayJS value
   */
  public void setDisplayJS(boolean tmp) {
    this.displayJS = tmp;
  }


  /**
   * Sets the displayJS attribute of the ProductCategoryHierarchyHandler object
   *
   * @param tmp The new displayJS value
   */
  public void setDisplayJS(String tmp) {
    this.displayJS = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the showLastLink attribute of the ProductCategoryHierarchyHandler
   * object
   *
   * @param tmp The new showLastLink value
   */
  public void setShowLastLink(boolean tmp) {
    this.showLastLink = tmp;
  }


  /**
   * Sets the showLastLink attribute of the ProductCategoryHierarchyHandler
   * object
   *
   * @param tmp The new showLastLink value
   */
  public void setShowLastLink(String tmp) {
    this.showLastLink = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the link attribute of the ProductCategoryHierarchyHandler object
   *
   * @param tmp The new link value
   */
  public void setLink(String tmp) {
    this.link = tmp;
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   * @throws JspException Description of the Exception
   */
  public int doStartTag() throws JspException {
    try {
      SystemStatus thisSystem = null;
      ConnectionElement ce = (ConnectionElement) pageContext.getSession().getAttribute(
          "ConnectionElement");
      if (ce != null) {
        thisSystem = (SystemStatus) ((Hashtable) pageContext.getServletContext().getAttribute(
            "SystemStatus")).get(ce.getUrl());
      }
      //Show the open category image
      this.pageContext.getOut().write(
          "<img border=\"0\" src=\"images/icons/stock_home-16.gif\" align=\"absmiddle\"> ");
      //Generate the category path
      LinkedHashMap categoryLevels = (LinkedHashMap) pageContext.getRequest().getAttribute(
          "categoryLevels");
      if (categoryLevels == null) {
        if (showLastLink) {
          if (displayJS) {
            this.pageContext.getOut().write(
                "<a href=\"javascript:submitForm('-1');\">");
          } else {
            this.pageContext.getOut().write(
                "<a href=\"" + link + "&categoryId=-1\">");
          }
        }
        if (thisSystem != null) {
          this.pageContext.getOut().write(
              thisSystem.getLabel("product.category.topCategories"));
        } else {
          this.pageContext.getOut().write("Top Categories");
        }
        if (showLastLink) {
          this.pageContext.getOut().write("</a>");
        }
      } else {
        Object[] hierarchy = categoryLevels.keySet().toArray();
        if (hierarchy.length > 0) {
          String categoryId = (String) pageContext.getRequest().getParameter(
              "categoryId");
          //Show a Home link
          if (displayJS) {
            this.pageContext.getOut().write(
                "<a href=\"javascript:submitForm('-1')\">");
          } else {
            this.pageContext.getOut().write(
                "<a href=\"" + link + "&categoryId=-1\">");
          }
          if (thisSystem != null) {
            this.pageContext.getOut().write(
                thisSystem.getLabel("product.category.topCategories"));
          } else {
            this.pageContext.getOut().write("Top Categories");
          }
          this.pageContext.getOut().write("</a>");
          //Show the rest of the links, except the last, unless specified
          this.pageContext.getOut().write(" > ");
          for (int i = hierarchy.length - 1; i >= 0; i--) {
            Integer thisId = (Integer) hierarchy[i];
            String[] sa = (String[]) categoryLevels.get(thisId);
            String name = sa[0];
            if (i > 0 || showLastLink) {
              if (displayJS) {
                this.pageContext.getOut().write(
                    "<a href=\"javascript:submitForm('" + thisId.intValue() + "');\">");
              } else {
                this.pageContext.getOut().write(
                    "<a href=\"" + link + "&categoryId=" + thisId.intValue() + "\">");
              }
            }
            this.pageContext.getOut().write(StringUtils.toHtml(name));
            if (i > 0 || showLastLink) {
              this.pageContext.getOut().write("</a>");
            }
            if (i > 0) {
              this.pageContext.getOut().write(" > ");
            }
          }
        } else {
          //Show home link
          if (showLastLink) {
            if (displayJS) {
              this.pageContext.getOut().write(
                  "<a href=\"javascript:submitForm('-1');\">");
            } else {
              this.pageContext.getOut().write(
                  "<a href=\"" + link + "&categoryId=-1\">");
            }
          }
          if (thisSystem != null) {
            this.pageContext.getOut().write(
                thisSystem.getLabel("product.category.topCategories"));
          } else {
            this.pageContext.getOut().write("Top Categories");
          }
          if (showLastLink) {
            this.pageContext.getOut().write("</a>");
          }
        }
      }
    } catch (Exception e) {
      throw new JspException(
          "ProductCategoryHierarchyHandler Error: " + e.getMessage());
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

