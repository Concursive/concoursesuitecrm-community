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

/**
 *  Description of the Class
 *
 *@author     ananth
 *@created    April 20, 2004
 *@version    $Id$
 */
public final class Orders extends CFSModule {

  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
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
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandSearchForm(ActionContext context) {
    /*
     *  TODO: uncomment this code when the permission is created
     *  if (!(hasPermission(context, "orders-orders-view"))) {
     *  return ("PermissionError");
     *  }
     */
    //Bypass search form for portal users
    if (isPortalUser(context)) {
      return (executeCommandSearch(context));
    }

    Connection db = null;
    try {
      db = getConnection(context);
      //Order type lookup
      LookupList typeSelect = new LookupList(db, "lookup_order_type");
      typeSelect.addItem(0, "All Types");
      context.getRequest().setAttribute("TypeSelect", typeSelect);
      //reset the offset and current letter of the paged list in order to make sure we search ALL orders
      PagedListInfo orderListInfo = this.getPagedListInfo(context, "SearchOrderListInfo");
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
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandSearch(ActionContext context) {
    /*
     *  if (!hasPermission(context, "orders-orders-view")) {
     *  return ("PermissionError");
     *  }
     */
    String source = (String) context.getRequest().getParameter("source");
    OrderList orderList = new OrderList();
    addModuleBean(context, "View Orders", "Search Results");

    //Prepare pagedListInfo
    PagedListInfo searchListInfo = this.getPagedListInfo(context, "SearchOrderListInfo");
    searchListInfo.setLink("Orders.do?command=Search");
    //Need to reset any sub PagedListInfos since this is a new acccount
    this.resetPagedListInfo(context);
    Connection db = null;
    try {
      db = this.getConnection(context);

      //For portal usr set source as 'searchForm' explicitly since
      //the search form is bypassed.
      //temporary solution for page redirection for portal user.
      if (isPortalUser(context)) {
        source = "searchForm";
      }
      //return if no criteria is selected
      if ((searchListInfo.getListView() == null || "".equals(searchListInfo.getListView())) && !"searchForm".equals(source)) {
        return "ListOK";
      }

      //Build the order list
      orderList.setPagedListInfo(searchListInfo);
      //orderList.setTypeId(searchListInfo.getFilterKey("listFilter1"));
      searchListInfo.setSearchCriteria(orderList, UserUtils.getUserLocale(context.getRequest()));
      /*
       *  if ("my".equals(searchListInfo.getListView())) {
       *  orderList.setOwnerId(this.getUserId(context));
       *  }
       */
      /*
       *  if ("disabled".equals(searchListInfo.getListView())) {
       *  orderList.setIncludeEnabled(0);
       *  }
       *  if ("all".equals(searchListInfo.getListView())) {
       *  orderList.setIncludeEnabled(-1);
       *  }
       */
      if (isPortalUser(context)) {
        orderList.setOrgId(getPortalUserPermittedOrgId(context));
      }
      orderList.buildList(db);
      System.out.println("order list length : " + orderList.size());
      context.getRequest().setAttribute("OrderList", orderList);
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
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDetails(ActionContext context) {
    /*
     *  if (!(hasPermission(context, "orders-orders-view"))) {
     *  return ("PermissionError");
     *  }
     */
    Exception errorMessage = null;
    Connection db = null;
    Order newOrder = null;

    try {
      String tempOrderId = context.getRequest().getParameter("id");

      //TODO: code for RecordAccessPermission
      int tempid = Integer.parseInt(tempOrderId);
      db = this.getConnection(context);
      newOrder = new Order(db, tempid);
      // populate the order's products
      newOrder.buildProducts(db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
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
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   */
  private void resetPagedListInfo(ActionContext context) {
    this.deletePagedListInfo(context, "ContactListInfo");
    this.deletePagedListInfo(context, "AccountFolderInfo");
    this.deletePagedListInfo(context, "AccountTicketInfo");
    this.deletePagedListInfo(context, "AccountDocumentInfo");
    this.deletePagedListInfo(context, "QuoteListInfo");
  }
}

