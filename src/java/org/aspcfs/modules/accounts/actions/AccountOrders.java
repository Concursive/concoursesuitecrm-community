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
package org.aspcfs.modules.accounts.actions;

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;
import java.sql.*;
import com.darkhorseventures.framework.actions.*;
import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.utils.*;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.accounts.base.*;
import org.aspcfs.modules.orders.base.*;
import org.aspcfs.modules.admin.base.AccessType;
import org.aspcfs.modules.admin.base.AccessTypeList;
import org.aspcfs.modules.admin.base.RoleList;
import org.aspcfs.modules.admin.base.Role;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.base.*;
import org.aspcfs.utils.web.*;

/**
 *  Description of the Class
 *
 * @author     ananth
 * @created    April 23, 2004
 * @version    $Id$
 */
public final class AccountOrders extends CFSModule {

  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
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
    PagedListInfo orderListInfo = this.getPagedListInfo(context, "OrderListInfo");
    orderListInfo.setLink("AccountOrders.do?command=View&orgId=" + orgid);
    Connection db = null;
    OrderList orderList = new OrderList();
    Organization thisOrganization = null;
    this.resetPagedListInfo(context);
    try {
      db = this.getConnection(context);
      orderList.setPagedListInfo(orderListInfo);
      orderList.setOrgId(Integer.parseInt(orgid));
      //orderList.setBuildDetails(true);
      //orderList.setBuildTypes(false);
      orderList.buildList(db);
      thisOrganization = new Organization(db, Integer.parseInt(orgid));
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
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
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
    Organization thisOrganization = null;
    try {
      db = this.getConnection(context);
      newOrder = new Order(db, Integer.parseInt(orderId));
      newOrder.buildProducts(db);
      thisOrganization = new Organization(db, newOrder.getOrgId());

      /*
      //find record permissions for portal users
      if (!isRecordAccessPermitted(context, newOrder.getOrgId())) {
        return ("PermissionError");
      }
      */
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


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   */
  private void resetPagedListInfo(ActionContext context) {
    this.deletePagedListInfo(context, "ContactListInfo");
    this.deletePagedListInfo(context, "AccountFolderInfo");
    this.deletePagedListInfo(context, "AccountTicketInfo");
    this.deletePagedListInfo(context, "AccountDocumentInfo");
    this.deletePagedListInfo(context, "QuoteListInfo");
  }
}

