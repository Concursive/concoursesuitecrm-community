/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.modules.servicecontracts.base.ServiceContractProduct;
import org.aspcfs.modules.servicecontracts.base.ServiceContractProductList;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Description of the Class
 *
 * @author kbhoopal
 * @version $Id$
 * @created May 7, 2004
 */
public final class ProductSelector extends CFSModule {

  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandListProducts(ActionContext context) {

    Exception errorMessage = null;
    Connection db = null;
    boolean listDone = false;
    String listType = context.getRequest().getParameter("listType");
    int contractId = -1;
    ServiceContractProductList productList = null;
    ServiceContractProductList finalProducts = null;
    ArrayList selectedList = (ArrayList) context.getSession().getAttribute(
        "selectedList");
    ArrayList selectedProductList = (ArrayList) context.getSession().getAttribute(
        "selectedProductList");

    contractId = Integer.parseInt(
        context.getRequest().getParameter("contractId"));
    if (selectedList == null || "true".equals(
        context.getRequest().getParameter("reset"))) {
      selectedList = new ArrayList();
    }
    if (selectedProductList == null || "true".equals(
        context.getRequest().getParameter("reset"))) {
      selectedProductList = new ArrayList();
    }

    if (context.getRequest().getParameter("previousSelection") != null) {
      StringTokenizer st = new StringTokenizer(
          context.getRequest().getParameter("previousSelection"), "|");
      while (st.hasMoreTokens()) {
        selectedProductList.add(String.valueOf(st.nextToken()));
      }
    }

    try {
      db = this.getConnection(context);
      int rowCount = 1;
      productList = new ServiceContractProductList();

      if ("true".equals(
          (String) context.getRequest().getParameter("finalsubmit"))) {
        //Handle single selection case
        if ("single".equals(listType)) {
          rowCount = Integer.parseInt(
              context.getRequest().getParameter("rowcount"));
          int id = Integer.parseInt(
              context.getRequest().getParameter("hiddenId" + rowCount));
          int productId = Integer.parseInt(
              context.getRequest().getParameter("hiddenProductId" + rowCount));
          selectedList.clear();
          selectedList.add(String.valueOf(id));
          selectedProductList.clear();
          selectedProductList.add(String.valueOf(productId));
        }
        listDone = true;
        if (finalProducts == null) {
          finalProducts = new ServiceContractProductList();
        }
        for (int i = 0; i < selectedList.size(); i++) {
          int id = Integer.parseInt((String) selectedList.get(i));
          ServiceContractProduct thisProduct = new ServiceContractProduct(
              db, id);
          finalProducts.add(thisProduct);
        }
      }

      setParameters(productList, context);
      productList.setContractId(contractId);
      productList.buildList(db);

    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      context.getRequest().setAttribute("productList", productList);
      context.getSession().setAttribute("selectedList", selectedList);
      context.getSession().setAttribute(
          "selectedProductList", selectedProductList);
      if (listDone) {
        context.getRequest().setAttribute("finalProducts", finalProducts);
      }
      return ("ListProductsOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   * Sets the pagedListInfo
   *
   * @param productList The new parameters value
   * @param context     The new parameters value
   */
  private void setParameters(ServiceContractProductList productList, ActionContext context) {

    PagedListInfo serviceContractProductListInfo = this.getPagedListInfo(
        context, "ServiceContractProductListInfo");
    String contractId = context.getRequest().getParameter("contractId");
    serviceContractProductListInfo.setLink(
        "ProductSelector.do?command=ListProducts&contractId=" + contractId);
    productList.setPagedListInfo(serviceContractProductListInfo);
    context.getRequest().setAttribute("contractId", contractId);

  }
}


