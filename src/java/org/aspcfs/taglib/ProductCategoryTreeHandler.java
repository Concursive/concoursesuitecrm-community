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

import org.aspcfs.modules.products.base.ProductCategory;
import org.aspcfs.modules.products.base.ProductCategoryList;
import org.aspcfs.utils.StringUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;
import java.util.Iterator;

/**
 * This Class evaluates whether a SystemStatus preference exists for the
 * supplied label.
 *
 * @author Olga.Kaptyug
 * @created Jun 20, 2006
 */
public class ProductCategoryTreeHandler extends TagSupport implements TryCatchFinally {

  protected String items = null;
  protected String checked = null;
  protected String parentItemProperty = null;
  protected String path = null;
  protected String home = null;

  protected ProductCategoryList pcList = null;
  protected ProductCategoryList pcChecked = null;

  public void doCatch(Throwable throwable) throws Throwable {
    // Required but not needed
  }

  public void doFinally() {
    // Reset each property or else the value gets reused
    items = null;
    checked = null;
    parentItemProperty = null;
    path = null;
    home = null;
    pcList = null;
    pcChecked = null;
  }

  /**
   * Output stream.
   */
  protected JspWriter out;

  /**
   * Gets the home attribute of the ProductCategoryTreeHandler object
   *
   * @return home The home value
   */
  public String getHome() {
    return this.home;
  }

  /**
   * Sets the home attribute of the ProductCategoryTreeHandler object
   *
   * @param home The new home value
   */
  public void setHome(String home) {
    this.home = home;
  }

  /**
   * Gets the path attribute of the ProductCategoryTreeHandler object
   *
   * @return path The path value
   */
  public String getPath() {
    return this.path;
  }

  /**
   * Sets the path attribute of the ProductCategoryTreeHandler object
   *
   * @param path The new path value
   */
  public void setPath(String path) {
    this.path = path;
  }

  /**
   * Gets the parentItemProperty attribute of the ProductCategoryTreeHandler
   * object
   *
   * @return parentItemProperty The parentItemProperty value
   */
  public String getParentItemProperty() {
    return this.parentItemProperty;
  }

  /**
   * Sets the parentItemProperty attribute of the ProductCategoryTreeHandler
   * object
   *
   * @param parentItemProperty The new parentItemProperty value
   */
  public void setParentItemProperty(String parentItemProperty) {
    this.parentItemProperty = parentItemProperty;
  }

  /**
   * Gets the pcChecked attribute of the ProductCategoryTreeHandler object
   *
   * @return pcChecked The pcChecked value
   */
  public ProductCategoryList getPcChecked() {
    return this.pcChecked;
  }

  /**
   * Sets the pcChecked attribute of the ProductCategoryTreeHandler object
   *
   * @param pcChecked The new pcChecked value
   */
  public void setPcChecked(ProductCategoryList pcChecked) {
    this.pcChecked = pcChecked;
  }

  /**
   * Gets the pcList attribute of the ProductCategoryTreeHandler object
   *
   * @return pcList The pcList value
   */
  public ProductCategoryList getPcList() {
    return this.pcList;
  }

  /**
   * Sets the pcList attribute of the ProductCategoryTreeHandler object
   *
   * @param pcList The new pcList value
   */
  public void setPcList(ProductCategoryList pcList) {
    this.pcList = pcList;
  }

  /**
   * Gets the checked attribute of the ProductCategoryTreeHandler object
   *
   * @return checked The checked value
   */
  public String getChecked() {
    return checked;
  }

  /**
   * Sets the checked attribute of the ProductCategoryTreeHandler object
   *
   * @param checked The new checked value
   */
  public void setChecked(String checked) {
    this.checked = checked;
  }

  /**
   * Gets the items attribute of the ProductCategoryTreeHandler object
   *
   * @return items The items value
   */
  public String getItems() {
    return items;
  }

  /**
   * Sets the items attribute of the ProductCategoryTreeHandler object
   *
   * @param items The new items value
   */
  public void setItems(String items) {
    this.items = items;
  }

  /**
   * Checks to see if the SystemStatus has a preference set for this label. If
   * so, the found label will be used, otherwise the body tag will be used.
   *
   * @return Description of the Returned Value
   * @throws JspException
   * @since 1.1
   */
  public final int doStartTag() throws JspException {
    out = pageContext.getOut();
    try {
      if (items != null) {
        pcList = (ProductCategoryList) pageContext.getRequest().getAttribute(items);
      }
      if (checked != null) {
        pcChecked = (ProductCategoryList) pageContext.getRequest().getAttribute(checked);
      }
      if (pcChecked == null) {
        pcChecked = new ProductCategoryList();
      }

      String parentId = (String) (pageContext.getRequest().getAttribute(parentItemProperty) != null ? pageContext.getRequest().getAttribute(
          parentItemProperty) : null);
      out.println("<div>");
      drawTree(parentId);
      out.println("</div>");
    } catch (Exception e) {

      e.printStackTrace();
    }
    return SKIP_BODY;
  }

  public int drawTree(String parentId) throws JspException {
    try {

      if (pcList != null) {
        if (home != null) {
          out.println("<div>");
          out.println("<img src=\"images/openfoldericon.png\">");
          out.print("Home");
          out.println("</div>");
        }
        drawChildren(pcList, parentId);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return SKIP_BODY;
  }

  public int drawChildren(ProductCategoryList list, String parentId) {
    try {

      out.println("<ul id=\"ul_" + ((parentId == null) ? "root" : parentId.toString()) + "\" >");
      Iterator iter = list.iterator();
      while (iter.hasNext()) {
        ProductCategory category = (ProductCategory) iter.next();
        String filteredItemName = StringUtils.jsStringEscape(category.getName());
        if (category.getChildCount() > 0) {
          out.print("<li title=\"" + filteredItemName + "\">");
          out.print("<nobr>");
          if (category.getChildList().size() > 0) {
            out.print("<a onclick=\"expand('" + category.getId() + "',this);\" href=\"" + path + "&mode=chunk");
            out.print("&parentId=" + category.getId() + "\"><img id=\"img_" + category.getId() + "\" src=\"");
            out.print("images/tree7o.gif\"");
            out.print(" /></a>");
            out.print("<input type=\"checkbox\" name=\"categoryElt\" value=\"" + category.getId() + "\"");
            if (pcChecked.hasCategory(category.getId())) {
              out.print("checked = \"true\"");
            }
            out.print(" onClick=\"highlight(\'f_" + category.getId() + "\');\" >");
            out.print("<font id=\"f_" + category.getId() + "\" ");
            if (pcChecked.hasCategory(category.getId())) {
              out.print("class=\"SELECTED\"");
            }
            out.print(">");
            out.print(filteredItemName);
            out.print("</font>");

            out.print("</nobr>");
            drawChildren(category.getChildList(), Integer.toString(category.getId()));
          } else {
            out.print("<a onclick=\"expand('" + category.getId() + "',this);\" href=\"" + path + "&mode=chunk");
            out.print("&parentId=" + category.getId() + "\"><img id=\"img_" + category.getId() + "\" src=\"");
            out.print("images/tree7c.gif\" alt=\"Expand Folder\" title=\"Expand Folder\"");
            out.print(" /></a>");
            out.print("<input type=\"checkbox\" name=\"categoryElt\" value=\"" + category.getId() + "\"");
            if (pcChecked.hasCategory(category.getId())) {
              out.print("checked = \"true\"");
            }
            out.print(" onClick=\"highlight(\'f_" + category.getId() + "\');\" >");
            out.print("<font id=\"f_" + category.getId() + "\" ");
            if (pcChecked.hasCategory(category.getId())) {
              out.print("class=\"SELECTED\"");
            }
            out.print(">");
            out.print(filteredItemName);
            out.print("</font>");

            out.print("</nobr>");

            out.println("<iframe name=\"frame_" + category.getId() + "\"></iframe>");
            out.println("");
            out.println("<ul id=\"ul_" + category.getId() + "\" class=\"Hidden\"></ul>");
          }
        } else {
          out.print("<li title=\"" + filteredItemName + "\">");
          out.print("<nobr>");
          out.print("<img id=\"img_" + category.getId() + "\" src=\"");
          out.print("images/tree7.gif\" ");
          out.print(" />");
          out.print("<input type=\"checkbox\" name=\"categoryElt\" value=\"" + category.getId() + "\"");
          if (pcChecked.hasCategory(category.getId())) {
            out.print("checked = \"true\"");
          }
          out.print(" onClick=\"highlight(\'f_" + category.getId() + "\');\" >");

          out.print("<font id=\"f_" + category.getId() + "\" ");
          if (pcChecked.hasCategory(category.getId())) {
            out.print("class=\"SELECTED\"");
          }
          out.print(">");
          out.print(filteredItemName);
          out.print("</font>");

          out.print("</nobr>");
          out.println("");

        }

      }
      out.println("</ul>");
    } catch (Exception e) {
      e.printStackTrace();
    }
		return SKIP_BODY;
	}
}
