package org.aspcfs.modules.orders.actions;

import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.framework.actions.*;
import org.aspcfs.utils.*;
import org.aspcfs.utils.web.*;
import org.aspcfs.modules.accounts.base.*;
import org.aspcfs.modules.admin.base.*;
import org.aspcfs.modules.communications.base.CampaignList;
import org.aspcfs.modules.tasks.base.TaskList;
import org.aspcfs.modules.products.base.*;
import org.aspcfs.modules.orders.base.*;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.*;
import org.aspcfs.modules.login.beans.UserBean;
import com.zeroio.iteam.base.*;
import com.zeroio.webutils.*;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.lang.*;
import java.text.*;
import org.aspcfs.modules.contacts.base.*;
import org.aspcfs.modules.actionlist.base.*;
import org.aspcfs.controller.*;
import org.aspcfs.modules.orders.beans.*;

/**
 *  Description of the Class
 *
 *@author     ananth
 *@created    May 27, 2004
 *@version    $Id: OrdersPayments.java,v 1.1.2.9 2004/06/02 13:34:41 partha Exp
 *      $
 */
public final class OrdersPayments extends CFSModule {
  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    if (!(hasPermission(context, "orders-view"))) {
      return ("PermissionError");
    }
    return "OK";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDetails(ActionContext context) {
    if (!(hasPermission(context, "orders-view"))) {
      return ("PermissionError");
    }
    int paymentId = -1;
    try {
      paymentId = Integer.parseInt(context.getRequest().getParameter("paymentId"));
    } catch (Exception e) {
      context.getRequest().setAttribute("actionError",
          "Invalid criteria, please review and make necessary changes before submitting");
      return "SearchCriteriaError";
    }
    OrderProduct orderProduct = null;
    OrderPaymentStatusList paymentStatusList = null;
    OrderPayment orderPayment = null;
    Order order = null;
    Connection db = null;
    PaymentCreditCard creditCard = new PaymentCreditCard();
    try {
      db = getConnection(context);

      orderPayment = new OrderPayment();
      orderPayment.setBuildOrderPaymentStatusList(true);
      orderPayment.queryRecord(db, paymentId);
      context.getRequest().setAttribute("orderPayment", orderPayment);

      orderProduct = new OrderProduct();
      orderProduct.setBuildProduct(true);
      orderProduct.setBuildProductOptions(true);
      orderProduct.setBuildProductStatus(true);
      orderProduct.queryRecord(db, orderPayment.getOrderItemId());
      context.getRequest().setAttribute("orderProduct", orderProduct);

      order = new Order();
      order.setBuildProducts(true);
      order.setBuildAddressList(true);
      order.queryRecord(db, orderProduct.getOrderId());
      context.getRequest().setAttribute("order", order);
      
      ProductOptionList optionList = new ProductOptionList();
      optionList.buildList(db);
      context.getRequest().setAttribute("productOptionList", optionList);

      ProductOptionValuesList valuesList = new ProductOptionValuesList();
      valuesList.buildList(db);
      context.getRequest().setAttribute("productOptionValuesList", valuesList);

      SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList paymentMethod = systemStatus.getLookupList(db, "lookup_payment_methods");
      context.getRequest().setAttribute("paymentMethod", paymentMethod);

      LookupList paymentSelect = systemStatus.getLookupList(db, "lookup_payment_status");
      context.getRequest().setAttribute("paymentSelect", paymentSelect);

      LookupList creditCardType = systemStatus.getLookupList(db, "lookup_creditcard_types");
      context.getRequest().setAttribute("creditCardType", creditCardType);

      creditCard.queryRecord(db, orderPayment.getCreditCardId());
      context.getRequest().setAttribute("creditCard", creditCard);
      
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("actionError",
          "The specified payment could not be found");
      return "SearchCriteriaError";
    } finally {
      this.freeConnection(context, db);
    }
    // handle request to clear password
    String resetPassword = context.getRequest().getParameter("resetPassword");
    if ("true".equals(resetPassword)) {
      context.getSession().removeAttribute("creditCardPassword");
    }
    // Check the credit card password last so that the decoded number can be added
    String creditCardPassword = context.getRequest().getParameter("creditCardPassword");
    if (creditCardPassword == null) {
      creditCardPassword = (String) context.getSession().getAttribute("creditCardPassword");
    }
    if (creditCardPassword != null && !"".equals(creditCardPassword)) {
      try {
        creditCard.setCardNumber(SecurityKey.useEncodedKey(getPath(context, "keys") + "order_private.enc",
            creditCardPassword, creditCard.getCardNumber()));
        context.getSession().setAttribute("creditCardPassword", creditCardPassword);
      } catch (Exception e) {
        e.printStackTrace(System.out);
        context.getRequest().setAttribute("actionError", "Invalid Password, try again");
      }
    }
    addModuleBean(context, "OrdersProducts", "OrdersProducts Details");
    return ("DetailsOK");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandModify(ActionContext context) {
    if (!(hasPermission(context, "orders-view"))) {
      return ("PermissionError");
    }
    int paymentId = Integer.parseInt((String) context.getRequest().getParameter("paymentId"));
    OrderProduct orderProduct = null;
    OrderPaymentStatusList paymentStatusList = null;
    OrderPayment orderPayment = null;
    Order order = null;
    Connection db = null;
    try {
      db = getConnection(context);

      orderPayment = new OrderPayment();
      orderPayment.setBuildOrderPaymentStatusList(true);
      orderPayment.queryRecord(db, paymentId);
      context.getRequest().setAttribute("orderPayment", orderPayment);

      StatusBean paymentBean = new StatusBean();
      paymentBean.setStatusId(orderPayment.getStatusId());
      context.getRequest().setAttribute("statusBean", paymentBean);

      orderProduct = new OrderProduct();
      orderProduct.setBuildProduct(true);
      orderProduct.setBuildProductOptions(true);
      orderProduct.setBuildProductStatus(true);
      orderProduct.queryRecord(db, orderPayment.getOrderItemId());
      context.getRequest().setAttribute("orderProduct", orderProduct);

      order = new Order();
      order.setBuildProducts(true);
      order.setBuildAddressList(true);
      order.queryRecord(db, orderProduct.getOrderId());
      context.getRequest().setAttribute("order", order);

      ProductOptionList optionList = new ProductOptionList();
      optionList.buildList(db);
      context.getRequest().setAttribute("productOptionList", optionList);

      ProductOptionValuesList valuesList = new ProductOptionValuesList();
      valuesList.buildList(db);
      context.getRequest().setAttribute("productOptionValuesList", valuesList);

      SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList paymentMethod = systemStatus.getLookupList(db, "lookup_payment_methods");
      context.getRequest().setAttribute("paymentMethod", paymentMethod);

      LookupList paymentSelect = systemStatus.getLookupList(db, "lookup_payment_status");
      paymentSelect.addItem(-1, "-- None --");
      context.getRequest().setAttribute("paymentSelect", paymentSelect);

      LookupList creditCardType = systemStatus.getLookupList(db, "lookup_creditcard_types");
      context.getRequest().setAttribute("creditCardType", creditCardType);

      PaymentCreditCard creditCard = new PaymentCreditCard();
      creditCard.queryRecord(db, orderPayment.getCreditCardId());
      context.getRequest().setAttribute("creditCard", creditCard);

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


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandSave(ActionContext context) {
    if (!(hasPermission(context, "orders-view"))) {
      return ("PermissionError");
    }
    int paymentId = Integer.parseInt((String) context.getRequest().getParameter("paymentId"));
    OrderProduct orderProduct = null;
    OrderPaymentStatusList paymentStatusList = null;
    OrderPayment orderPayment = null;
    Order order = null;
    Connection db = null;
    try {
      db = getConnection(context);

      orderPayment = new OrderPayment();
      orderPayment.setBuildOrderPaymentStatusList(true);
      orderPayment.queryRecord(db, paymentId);
      context.getRequest().setAttribute("orderPayment", orderPayment);

      Calendar now = Calendar.getInstance();
      Timestamp rightNow = new Timestamp(now.getTimeInMillis());
      StatusBean statusBean = (StatusBean) context.getFormBean();
      if (statusBean.getStatusId() != -1 && statusBean.getStatusId() != 0) {
        orderPayment.setStatusId(statusBean.getStatusId());
      }
      if (statusBean.getAuthorizationRefNumber() != null) {
        orderPayment.setAuthorizationRefNumber(statusBean.getAuthorizationRefNumber());
      }
      if (statusBean.getAuthorizationCode() != null) {
        orderPayment.setAuthorizationCode(statusBean.getAuthorizationCode());
      }
      if (statusBean.getAuthorizationDate() != null) {
        orderPayment.setAuthorizationDate(statusBean.getAuthorizationDate());
      }

      // the enteredby field is being set to the current user.
      // the value should have been the user who is modifying this
      // payment

      orderPayment.setEnteredBy(this.getUserId(context));
      orderPayment.setModifiedBy(this.getUserId(context));
      orderPayment.update(db);

    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "OrdersProducts", "OrdersProducts Details");
    return ("SaveOK");
  }
}

