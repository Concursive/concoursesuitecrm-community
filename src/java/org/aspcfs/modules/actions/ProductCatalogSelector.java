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
package org.aspcfs.modules.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.modules.products.base.ProductCatalog;
import org.aspcfs.modules.products.base.ProductCatalogList;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Creates a List of Products for display within a popup <br>
 * Can be used in two variants: Single/Multiple<br>
 * Single and Multiple define if multiple products can be selected or just a
 * single one
 *
 * @author ananth
 * @version $Id$
 * @created October 7, 2004
 */
public final class ProductCatalogSelector extends CFSModule {
  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandListProductCatalogs(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    boolean listDone = false;
    String listType = context.getRequest().getParameter("listType");
    ProductCatalogList productList = null;
    ProductCatalogList finalProducts = null;
    ArrayList selectedList = (ArrayList) context.getRequest().getAttribute(
        "SelectedProducts");

    if (selectedList == null || "true".equals(
        context.getRequest().getParameter("reset"))) {
      selectedList = new ArrayList();
    }

    String prevSelection = context.getRequest().getParameter(
        "previousSelection");
    if (prevSelection != null) {
      StringTokenizer st = new StringTokenizer(prevSelection, "|");
      while (st.hasMoreTokens()) {
        selectedList.add(String.valueOf(st.nextToken()));
      }
    }

    try {
      db = this.getConnection(context);
      int rowCount = 1;
      productList = new ProductCatalogList();

      if ("list".equals(listType)) {
        while (context.getRequest().getParameter("hiddenProductId" + rowCount) != null) {
          int productId = Integer.parseInt(
              context.getRequest().getParameter("hiddenProductId" + rowCount));
          if (context.getRequest().getParameter("product" + rowCount) != null) {
            if (!selectedList.contains(String.valueOf(productId))) {
              selectedList.add(String.valueOf(productId));
            }
          } else {
            selectedList.remove(String.valueOf(productId));
          }
          rowCount++;
        }
      }

      if ("true".equals(
          (String) context.getRequest().getParameter("finalsubmit"))) {
        //Handle single selection case
        if ("single".equals(listType)) {
          rowCount = Integer.parseInt(
              context.getRequest().getParameter("rowcount"));
          int productId = Integer.parseInt(
              context.getRequest().getParameter("hiddenProductId" + rowCount));
          selectedList.clear();
          selectedList.add(String.valueOf(productId));
        }
        listDone = true;
        if (finalProducts == null) {
          finalProducts = new ProductCatalogList();
        }
        for (int i = 0; i < selectedList.size(); i++) {
          int productId = Integer.parseInt((String) selectedList.get(i));
          finalProducts.add(new ProductCatalog(db, productId));
        }
      }

      //set ProductCatalogList parameters and build the list
      setParameters(productList, context);
      productList.buildList(db);
    } catch (Exception e) {
      e.printStackTrace(System.out);
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      context.getRequest().setAttribute("ProductList", productList);
      context.getRequest().setAttribute("SelectedProducts", selectedList);
      if (listDone) {
        context.getRequest().setAttribute("FinalProducts", finalProducts);
      }
      return ("ListProductsOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   * Sets the parameters attribute of the ProductCatalogSelector object
   *
   * @param productList The new parameters value
   * @param context     The new parameters value
   */
  private void setParameters(ProductCatalogList productList, ActionContext context) {
    //check if a text based filter was entered
    String productName = context.getRequest().getParameter("productName");
    String productSku = context.getRequest().getParameter("productSku");
    if (productName != null) {
      if (!"Product Name".equals(productName) && !"".equals(
          productName.trim())) {
        productList.setName("%" + productName + "%");
      }
    }
    if (productSku != null) {
      if (!"Product SKU".equals(productSku) && !"".equals(productSku.trim())) {
        productList.setSku(productSku + "%");
      }
    }

    if ("true".equals(context.getRequest().getParameter("reset"))) {
      context.getSession().removeAttribute("ProductListInfo");
    }

    PagedListInfo productListInfo = this.getPagedListInfo(
        context, "ProductListInfo");
    //add filters
    productList.setPagedListInfo(productListInfo);
  }
}

