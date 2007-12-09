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
package org.aspcfs.modules.accounts.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.orders.base.Order;
import org.aspcfs.modules.orders.base.OrderList;
import org.aspcfs.modules.orders.base.OrderPaymentList;
import org.aspcfs.modules.orders.beans.StatusBean;
import org.aspcfs.modules.products.base.ProductOptionList;
import org.aspcfs.modules.products.base.ProductOptionValuesList;
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
 * @created April 23, 2004
 */
public final class AccountOrders extends CFSModule {

  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandView(ActionContext context) {
    /*
     *  if (!(hasPermission(context, "accounts-accounts-orders-view"))) {
     *  return ("PermissionError");
     *  }
     */
    addModuleBean(context, "View Orders", "View Order Details");
    String orgid = context.getRequest().getParameter("orgId");
    if (orgid == null) {
      orgid = (String) context.getRequest().getAttribute("orgId");
    }

    //find record permissions for portal users
    /*
     *  if (!isRecordAccessPermitted(context, Integer.parseInt(orgid))) {
     *  return ("PermissionError");
     *  }
     */
    PagedListInfo orderListInfo = this.getPagedListInfo(
        context, "OrderListInfo");
    orderListInfo.setLink("AccountOrders.do?command=View&orgId=" + orgid);
    Connection db = null;
    OrderList orderList = new OrderList();
    Organization thisOrganization = null;
    this.resetPagedListInfo(context);
    try {
      db = this.getConnection(context);
      orderList.setPagedListInfo(orderListInfo);
      orderList.setOrgId(Integer.parseInt(orgid));
      orderList.setClosedOnly(Constants.FALSE);
      //orderList.setBuildDetails(true);
      //orderList.setBuildTypes(false);
      orderList.buildList(db);
      thisOrganization = new Organization(db, Integer.parseInt(orgid));

      SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList typeSelect = systemStatus.getLookupList(
          db, "lookup_order_type");
      context.getRequest().setAttribute("typeSelect", typeSelect);
      LookupList statusSelect = systemStatus.getLookupList(
          db, "lookup_order_status");
      context.getRequest().setAttribute("statusSelect", statusSelect);

    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("OrderList", orderList);
    context.getRequest().setAttribute("OrgDetails", thisOrganization);
    return ("ListOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDetails(ActionContext context) {
    /*
    if (!hasPermission(context, "accounts-accounts-orders-view")) {
      return ("PermissionError");
    }
    */
    addModuleBean(context, "View Orders", "View Order Details");
    String orderId = context.getRequest().getParameter("id");
    Connection db = null;
    Order newOrder = null;
    ProductOptionList optionList = null;
    ProductOptionValuesList optionValuesList = null;
    Organization thisOrganization = null;
    try {
      db = this.getConnection(context);
      newOrder = new Order();
      newOrder.setBuildProducts(true);
      newOrder.queryRecord(db, Integer.parseInt(orderId));

      optionList = new ProductOptionList();
      optionList.buildList(db);
      context.getRequest().setAttribute("productOptionList", optionList);

      optionValuesList = new ProductOptionValuesList();
      optionValuesList.buildList(db);
      context.getRequest().setAttribute(
          "productOptionValuesList", optionValuesList);

      thisOrganization = new Organization(db, newOrder.getOrgId());

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

    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("OrderDetails", newOrder);
    context.getRequest().setAttribute("OrgDetails", thisOrganization);
    return ("DetailsOK");
  }

  public String executeCommandModifyStatus(ActionContext context) {
    /*
     *  if (!(hasPermission(context, "orders-orders-view"))) {
     *  return ("PermissionError");
     *  }
     */
    Exception errorMessage = null;
    Connection db = null;
    Order newOrder = null;
    ProductOptionList optionList = null;
    ProductOptionValuesList optionValuesList = null;
    Organization thisOrganization = null;
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
      context.getRequest().setAttribute("statusSelect", statusSelect);

      StatusBean statusBean = new StatusBean();
      statusBean.setStatusId(newOrder.getStatusId());
      context.getRequest().setAttribute("statusBean", statusBean);

      thisOrganization = new Organization(db, newOrder.getOrgId());
      context.getRequest().setAttribute("OrgDetails", thisOrganization);

    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("OrderDetails", newOrder);
    return ("ModifyStatusOK");
  }

  public String executeCommandDelete(ActionContext context) {
    Connection db = null;
    try {
      String tempOrderId = context.getRequest().getParameter("id");

      //TODO: code for RecordAccessPermission
      int tempid = Integer.parseInt(tempOrderId);
      db = this.getConnection(context);
      Order newOrder = new Order();
      newOrder.setBuildProducts(true);
      newOrder.queryRecord(db, tempid);
      newOrder.delete(db);

    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "DeleteOK";
  }

  public String executeCommandSaveStatus(ActionContext context) {
    /*
     *  if (!(hasPermission(context, "orders-orders-view"))) {
     *  return ("PermissionError");
     *  }
     */
    Exception errorMessage = null;
    Connection db = null;
    Order newOrder = null;
    ProductOptionList optionList = null;
    Organization thisOrganization = null;
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
      newOrder.setModified(rightNow);
      newOrder.update(db);

      thisOrganization = new Organization(db, newOrder.getOrgId());
      context.getRequest().setAttribute("OrgDetails", thisOrganization);

    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("OrderDetails", newOrder);
    return ("SaveStatusOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   */
  private void resetPagedListInfo(ActionContext context) {
    this.deletePagedListInfo(context, "ContactListInfo");
    this.deletePagedListInfo(context, "AccountFolderInfo");
    this.deletePagedListInfo(context, "AccountTicketInfo");
    this.deletePagedListInfo(context, "AccountDocumentInfo");
    this.deletePagedListInfo(context, "QuoteListInfo");
  }
}

