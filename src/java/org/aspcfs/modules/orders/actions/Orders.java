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
package org.aspcfs.modules.orders.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.orders.base.Order;
import org.aspcfs.modules.orders.base.OrderList;
import org.aspcfs.modules.orders.base.OrderPaymentList;
import org.aspcfs.modules.orders.beans.StatusBean;
import org.aspcfs.modules.products.base.ProductCategoryList;
import org.aspcfs.modules.products.base.ProductOptionList;
import org.aspcfs.modules.products.base.ProductOptionValuesList;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * Description of the Class
 *
 * @author ananth
 * @version $Id$
 * @created April 20, 2004
 */
public final class Orders extends CFSModule {

  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    return executeCommandSearchForm(context);
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */

  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandSearchForm(ActionContext context) {
    if (!(hasPermission(context, "orders-view"))) {
      return ("PermissionError");
    }
    //Bypass search form for portal users
    if (isPortalUser(context)) {
      return (executeCommandSearch(context));
    }

    Connection db = null;
    try {
      db = getConnection(context);
      //Order type lookup
      SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList statusSelect = systemStatus.getLookupList(
          db, "lookup_order_status");
      statusSelect.addItem(-1, "All Open Orders");
      context.getRequest().setAttribute("statusSelect", statusSelect);
      //Category lookup
      LookupList list = new LookupList(db, "lookup_product_category_type");
      ProductCategoryList categoryList = new ProductCategoryList();
      categoryList.buildList(db);
      HtmlSelect select = categoryList.getHtmlSelect(
          list.getIdFromValue("Publication"));
      context.getRequest().setAttribute("categorySelect", select);
      //reset the offset and current letter of the paged list in order to make sure we search ALL orders
      PagedListInfo orderListInfo = this.getPagedListInfo(
          context, "searchOrderListInfo");
      orderListInfo.setCurrentLetter("");
      orderListInfo.setCurrentOffset(0);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Search Orders", "Orders Search");
    return ("SearchOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandSearch(ActionContext context) {
    if (!(hasPermission(context, "orders-view"))) {
      return ("PermissionError");
    }
    String source = (String) context.getRequest().getParameter("source");
    OrderList orderList = new OrderList();
    addModuleBean(context, "View Orders", "Search Results");

    //Prepare pagedListInfo
    PagedListInfo searchListInfo = this.getPagedListInfo(
        context, "searchOrderListInfo");
    searchListInfo.setLink("Orders.do?command=Search");
    Connection db = null;
    try {
      //For portal usr set source as 'searchForm' explicitly since
      //the search form is bypassed.
      //temporary solution for page redirection for portal user.
      if (isPortalUser(context)) {
        source = "searchForm";
      }
      //return if no criteria is selected
      /*
       *  if ((searchListInfo.getListView() == null || "".equals(searchListInfo.getListView())) && !"searchForm".equals(source)) {
       *  return "ListOK";
       *  }
       */
      db = this.getConnection(context);
      SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList typeSelect = systemStatus.getLookupList(
          db, "lookup_order_type");
      context.getRequest().setAttribute("typeSelect", typeSelect);

      LookupList statusSelect = systemStatus.getLookupList(
          db, "lookup_order_status");
      context.getRequest().setAttribute("statusSelect", statusSelect);
      statusSelect.addItem(-1, "All Open Orders");

      //Build the order list
      orderList.setPagedListInfo(searchListInfo);
      orderList.setStatusId(searchListInfo.getFilterKey("listFilter1"));
      if (orderList.getStatusId() == -1) {
        orderList.setClosedOnly(Constants.FALSE);
      }
      orderList.setCategoryId(searchListInfo.getFilterKey("listFilter2"));
      searchListInfo.setSearchCriteria(orderList, context);
      if (isPortalUser(context)) {
        orderList.setOrgId(getPortalUserPermittedOrgId(context));
      }
      orderList.buildList(db);
      context.getRequest().setAttribute("orderList", orderList);

      return ("ListOK");
    } catch (Exception e) {
      //Go through the SystemError process
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDetails(ActionContext context) {
    if (!(hasPermission(context, "orders-view"))) {
      return ("PermissionError");
    }
    Connection db = null;
    Order newOrder = null;
    ProductOptionList optionList = null;
    ProductOptionValuesList optionValuesList = null;
    int tempid = -1;
    try {
      tempid = Integer.parseInt(context.getRequest().getParameter("id"));
    } catch (Exception e) {
      context.getRequest().setAttribute(
          "actionError",
          "Invalid criteria, please review and make necessary changes before submitting");
      return "SearchCriteriaError";
    }
    try {
      //TODO: code for RecordAccessPermission
      db = this.getConnection(context);
      newOrder = new Order();
      newOrder.setBuildProducts(true);
      newOrder.queryRecord(db, tempid);
      optionList = new ProductOptionList();
      optionList.buildList(db);
      context.getRequest().setAttribute("productOptionList", optionList);

      optionValuesList = new ProductOptionValuesList();
      optionValuesList.buildList(db);
      context.getRequest().setAttribute(
          "productOptionValuesList", optionValuesList);

      OrderPaymentList paymentList = new OrderPaymentList();
      paymentList.setOrderId(newOrder.getId());
      paymentList.buildList(db);
      context.getRequest().setAttribute("paymentList", paymentList);

      SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList typeSelect = systemStatus.getLookupList(
          db, "lookup_order_type");
      context.getRequest().setAttribute("typeSelect", typeSelect);
      LookupList statusSelect = systemStatus.getLookupList(
          db, "lookup_order_status");
      context.getRequest().setAttribute("statusSelect", statusSelect);
      LookupList paymentSelect = systemStatus.getLookupList(
          db, "lookup_payment_status");
      context.getRequest().setAttribute("paymentSelect", paymentSelect);

    } catch (Exception e) {
      context.getRequest().setAttribute(
          "actionError",
          "The specified order could not be found");
      return "SearchCriteriaError";
    } finally {
      this.freeConnection(context, db);
    }
    String action = context.getRequest().getParameter("action");
    if (action != null && action.equals("modify")) {
      //If user is going to the modify form
      addModuleBean(context, "Orders", "Modify Order Details");
      return ("DetailsOK");
    } else {
      //If user is going to the detail screen
      addModuleBean(context, "View Orders", "View Order Details");
      context.getRequest().setAttribute("OrderDetails", newOrder);
      return ("DetailsOK");
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandModifyStatus(ActionContext context) {
    if (!(hasPermission(context, "orders-view"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    Connection db = null;
    Order newOrder = null;
    ProductOptionList optionList = null;
    ProductOptionValuesList optionValuesList = null;
    try {
      String tempOrderId = context.getRequest().getParameter("id");

      //TODO: code for RecordAccessPermission
      int tempid = Integer.parseInt(tempOrderId);
      db = this.getConnection(context);
      newOrder = new Order();
      newOrder.setBuildProducts(true);
      newOrder.queryRecord(db, tempid);
      optionList = new ProductOptionList();
      optionList.buildList(db);
      context.getRequest().setAttribute("productOptionList", optionList);

      optionValuesList = new ProductOptionValuesList();
      optionValuesList.buildList(db);
      context.getRequest().setAttribute(
          "productOptionValuesList", optionValuesList);

      OrderPaymentList paymentList = new OrderPaymentList();
      paymentList.setOrderId(newOrder.getId());
      paymentList.buildList(db);
      context.getRequest().setAttribute("paymentList", paymentList);

      SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList typeSelect = systemStatus.getLookupList(
          db, "lookup_order_type");
      context.getRequest().setAttribute("typeSelect", typeSelect);

      LookupList statusSelect = systemStatus.getLookupList(
          db, "lookup_order_status");
      statusSelect.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("statusSelect", statusSelect);

      StatusBean statusBean = new StatusBean();
      statusBean.setStatusId(newOrder.getStatusId());
      statusBean.setAuthorizationCode(newOrder.getNotes());
      context.getRequest().setAttribute("statusBean", statusBean);

    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("OrderDetails", newOrder);
    return ("ModifyStatusOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandSaveStatus(ActionContext context) {
    if (!(hasPermission(context, "orders-view"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    Connection db = null;
    Order newOrder = null;
    ProductOptionList optionList = null;
    ProductOptionValuesList optionValuesList = null;
    try {
      String tempOrderId = context.getRequest().getParameter("id");

      //TODO: code for RecordAccessPermission
      int tempid = Integer.parseInt(tempOrderId);
      db = this.getConnection(context);
      newOrder = new Order();
      newOrder.setBuildProducts(true);
      newOrder.queryRecord(db, tempid);
      optionList = new ProductOptionList();
      optionList.buildList(db);
      context.getRequest().setAttribute("productOptionList", optionList);

      Calendar now = Calendar.getInstance();
      Timestamp rightNow = new Timestamp(now.getTimeInMillis());
      StatusBean statusBean = (StatusBean) context.getFormBean();
      if (statusBean.getStatusId() != -1 && statusBean.getStatusId() != 0) {
        newOrder.setStatusId(statusBean.getStatusId());
      }
      newOrder.setNotes(statusBean.getAuthorizationCode());
      newOrder.setModified(rightNow);
      newOrder.update(db);

    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("OrderDetails", newOrder);
    return ("SaveStatusOK");
  }

}

