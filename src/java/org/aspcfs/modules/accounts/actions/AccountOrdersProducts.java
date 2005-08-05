package org.aspcfs.modules.accounts.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.orders.base.Order;
import org.aspcfs.modules.orders.base.OrderPaymentList;
import org.aspcfs.modules.orders.base.OrderProduct;
import org.aspcfs.modules.orders.base.OrderProductStatusList;
import org.aspcfs.modules.orders.beans.StatusBean;
import org.aspcfs.modules.products.base.CustomerProduct;
import org.aspcfs.modules.products.base.CustomerProductHistoryList;
import org.aspcfs.modules.products.base.ProductOptionList;
import org.aspcfs.modules.products.base.ProductOptionValuesList;
import org.aspcfs.utils.web.LookupList;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Calendar;

public final class AccountOrdersProducts extends CFSModule {
  public String executeCommandDefault(ActionContext context) {
    return "OK";
  }

  public String executeCommandDetails(ActionContext context) {
    int productId = Integer.parseInt(
        (String) context.getRequest().getParameter("productId"));
    OrderProduct orderProduct = null;
    OrderProductStatusList productStatusList = null;
    OrderPaymentList paymentList = null;
    Order order = null;
    Connection db = null;
    try {
      db = getConnection(context);
      orderProduct = new OrderProduct();
      orderProduct.setBuildProduct(true);
      orderProduct.setBuildProductOptions(true);
      orderProduct.setBuildProductStatus(true);
      orderProduct.queryRecord(db, productId);
      context.getRequest().setAttribute("orderProduct", orderProduct);

      order = new Order();
      order.setBuildProducts(true);
      order.queryRecord(db, orderProduct.getOrderId());
      context.getRequest().setAttribute("order", order);

      SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList statusSelect = systemStatus.getLookupList(
          db, "lookup_order_status");
      context.getRequest().setAttribute("statusSelect", statusSelect);

      LookupList paymentSelect = systemStatus.getLookupList(
          db, "lookup_payment_status");
      context.getRequest().setAttribute("paymentSelect", paymentSelect);

      productStatusList = orderProduct.getProductStatusList();
      context.getRequest().setAttribute(
          "productStatusList", productStatusList);

      paymentList = new OrderPaymentList();
      paymentList.setOrderId(orderProduct.getOrderId());
      paymentList.setOrderItemId(orderProduct.getId());
      paymentList.buildList(db);
      context.getRequest().setAttribute("paymentList", paymentList);

      ProductOptionList optionList = new ProductOptionList();
      optionList.buildList(db);
      context.getRequest().setAttribute("productOptionList", optionList);

      ProductOptionValuesList valuesList = new ProductOptionValuesList();
      valuesList.buildList(db);
      context.getRequest().setAttribute("productOptionValuesList", valuesList);

      Organization thisOrganization = null;
      thisOrganization = new Organization(db, order.getOrgId());
      context.getRequest().setAttribute("OrgDetails", thisOrganization);

      CustomerProductHistoryList historyList = new CustomerProductHistoryList();
      historyList.setOrderId(order.getId());
      historyList.setOrderItemId(orderProduct.getId());
      historyList.buildList(db);
      context.getRequest().setAttribute("historyList", historyList);

    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "OrdersProducts", "OrdersProducts Details");
    return ("DetailsOK");

  }

  public String executeCommandModify(ActionContext context) {
    int productId = Integer.parseInt(
        (String) context.getRequest().getParameter("productId"));
    OrderProduct orderProduct = null;
    OrderProductStatusList productStatusList = null;
    OrderPaymentList paymentList = null;
    Order order = null;
    Connection db = null;
    try {
      db = getConnection(context);

      orderProduct = new OrderProduct();
      orderProduct.setBuildProduct(true);
      orderProduct.setBuildProductOptions(true);
      orderProduct.setBuildProductStatus(true);
      orderProduct.queryRecord(db, productId);
      context.getRequest().setAttribute("orderProduct", orderProduct);

      StatusBean productBean = new StatusBean();
      productBean.setStatusId(orderProduct.getStatusId());
      context.getRequest().setAttribute("statusBean", productBean);

      SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList statusSelect = systemStatus.getLookupList(
          db, "lookup_order_status");
      context.getRequest().setAttribute("statusSelect", statusSelect);

      order = new Order();
      order.setBuildProducts(true);
      order.queryRecord(db, orderProduct.getOrderId());
      context.getRequest().setAttribute("order", order);

      productStatusList = orderProduct.getProductStatusList();
      context.getRequest().setAttribute(
          "productStatusList", productStatusList);

      paymentList = new OrderPaymentList();
      paymentList.setOrderId(orderProduct.getOrderId());
      paymentList.setOrderItemId(orderProduct.getId());
      paymentList.buildList(db);
      context.getRequest().setAttribute("paymentList", paymentList);

      ProductOptionList optionList = new ProductOptionList();
      optionList.buildList(db);
      context.getRequest().setAttribute("productOptionList", optionList);

      ProductOptionValuesList valuesList = new ProductOptionValuesList();
      valuesList.buildList(db);
      context.getRequest().setAttribute("productOptionValuesList", valuesList);

      Organization thisOrganization = null;
      thisOrganization = new Organization(db, order.getOrgId());
      context.getRequest().setAttribute("OrgDetails", thisOrganization);

    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "OrdersProducts", "OrdersProducts Details");
    return ("ModifyOK");
  }

  public String executeCommandSave(ActionContext context) {
    int productId = Integer.parseInt(
        (String) context.getRequest().getParameter("productId"));
    OrderProduct orderProduct = null;
    OrderProductStatusList productStatusList = null;
    OrderPaymentList paymentList = null;
    Order order = null;
    Connection db = null;
    try {
      db = getConnection(context);

      orderProduct = new OrderProduct();
      orderProduct.setBuildProduct(true);
      orderProduct.setBuildProductOptions(true);
      orderProduct.setBuildProductStatus(true);
      orderProduct.queryRecord(db, productId);
      orderProduct.setModifiedBy(this.getUserId(context));
      context.getRequest().setAttribute("orderProduct", orderProduct);

      Calendar now = Calendar.getInstance();
      Timestamp rightNow = new Timestamp(now.getTimeInMillis());
      StatusBean statusBean = (StatusBean) context.getFormBean();
      if (statusBean.getStatusId() != -1 && statusBean.getStatusId() != 0) {
        orderProduct.setStatusId(statusBean.getStatusId());
      }
      orderProduct.setStatusDate(rightNow);
      orderProduct.update(db);

    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "OrdersProducts", "OrdersProducts Details");
    return "SaveOK";
  }

  public String executeCommandDisplayCustomerProduct(ActionContext context) {
    Connection db = null;
    int productId = Integer.parseInt(
        (String) context.getRequest().getParameter("productId"));
    CustomerProduct customerProduct = null;
    try {
      db = getConnection(context);
      OrderProduct product = new OrderProduct();
      product.setBuildProduct(true);
      product.setBuildProductOptions(true);
      product.queryRecord(db, productId);
      context.getRequest().setAttribute("orderProduct", product);

      // build the customer Product
      customerProduct = new CustomerProduct();
      customerProduct.setBuildFileList(true);
      customerProduct.queryRecordFromItemId(db, product.getId());
      context.getRequest().setAttribute("customerProduct", customerProduct);

    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return "SystemError";
    } finally {
      this.freeConnection(context, db);
    }
    return "DisplayCustomerProductOK";
  }
}

