package org.aspcfs.modules.actions;

import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.framework.actions.*;
import java.sql.*;
import java.util.*;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.modules.servicecontracts.base.*;
import org.aspcfs.modules.base.FilterList;
import org.aspcfs.modules.base.Filter;
import org.aspcfs.modules.base.Constants;

/**
 *  Description of the Class
 *
 *@author     kbhoopal
 *@created    May 7, 2004
 *@version    $Id$
 */
public final class ProductSelector extends CFSModule {

  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandListProducts(ActionContext context) {

    Exception errorMessage = null;
    Connection db = null;
    boolean listDone = false;
    String listType = context.getRequest().getParameter("listType");
    int contractId = -1;
    ServiceContractProductList productList = null;
    ServiceContractProductList finalProducts = null;
    ArrayList selectedList = (ArrayList) context.getSession().getAttribute("selectedList");
    ArrayList selectedProductList = (ArrayList) context.getSession().getAttribute("selectedProductList");

    contractId = Integer.parseInt(context.getRequest().getParameter("contractId"));
    if (selectedList == null || "true".equals(context.getRequest().getParameter("reset"))) {
      selectedList = new ArrayList();
    }
    if (selectedProductList == null || "true".equals(context.getRequest().getParameter("reset"))) {
      selectedProductList = new ArrayList();
    }

    if (context.getRequest().getParameter("previousSelection") != null) {
      StringTokenizer st = new StringTokenizer(context.getRequest().getParameter("previousSelection"), "|");
      while (st.hasMoreTokens()) {
        selectedProductList.add(String.valueOf(st.nextToken()));
      }
    }

    try {
      db = this.getConnection(context);
      int rowCount = 1;
      productList = new ServiceContractProductList();

      if ("true".equals((String) context.getRequest().getParameter("finalsubmit"))) {
        //Handle single selection case
        if ("single".equals(listType)) {
          rowCount = Integer.parseInt(context.getRequest().getParameter("rowcount"));
          int id = Integer.parseInt(context.getRequest().getParameter("hiddenId" + rowCount));
          int productId = Integer.parseInt(context.getRequest().getParameter("hiddenProductId" + rowCount));
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
          ServiceContractProduct thisProduct = new ServiceContractProduct(db, id);
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
      context.getSession().setAttribute("selectedProductList", selectedProductList);
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
   *  Sets the pagedListInfo
   *
   *@param  productList  The new parameters value
   *@param  context      The new parameters value
   */
  private void setParameters(ServiceContractProductList productList, ActionContext context) {

    PagedListInfo serviceContractProductListInfo = this.getPagedListInfo(context, "ServiceContractProductListInfo");
    String contractId = context.getRequest().getParameter("contractId");
    serviceContractProductListInfo.setLink("ProductSelector.do?command=ListProducts&contractId=" + contractId);
    productList.setPagedListInfo(serviceContractProductListInfo);
    context.getRequest().setAttribute("contractId", contractId);

  }
}


