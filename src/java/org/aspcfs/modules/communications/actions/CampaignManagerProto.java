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
package org.aspcfs.modules.communications.actions;

import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.framework.actions.*;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.utils.*;
import org.aspcfs.utils.web.*;
import org.aspcfs.modules.communications.base.*;
import org.aspcfs.modules.base.DependencyList;
import java.sql.*;
import java.util.Vector;

/**
 *  Action class for the Campaign Module
 *
 *@author
 *@created    October 18, 2001
 *@version    $Id: CampaignManager.java,v 1.1 2001/10/18 12:45:57 mrajkowski Exp
 *      $
 */
public final class CampaignManagerProto extends CFSModule {

  /**
   *  Used for Html pages and prototypes
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since           1.2
   */
  public String executeCommandDefault(ActionContext context) {
    String module = context.getRequest().getParameter("module");
    String submenu = context.getRequest().getParameter("submenu");
    String includePage = context.getRequest().getParameter("include");
    context.getRequest().setAttribute("IncludePage", includePage);
    if (submenu != null && !submenu.equals("")) {
      addModuleBean(context, module, submenu);
    } else {
      addModuleBean(context, module, module);
    }
    return ("IncludeOK");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandDashboard(ActionContext context) {
    addModuleBean(context, "Dashboard", "Dashboard");
    return ("DashboardOK");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since           1.1
   */
  public String executeCommandListManager(ActionContext context) {
    Exception errorMessage = null;

    PagedListInfo campaignListInfo = this.getPagedListInfo(context, "CampaignListInfo");
    campaignListInfo.setLink("CampaignManager.do?command=Dashboard");

    Connection db = null;
    //RoleList roleList = new RoleList();
    try {
      db = this.getConnection(context);
      //roleList.setPagedListInfo(roleInfo);
      //roleList.setEnabledState(Constants.TRUE);
      //roleList.buildList(db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "ListManager", "List of Items");
    if (errorMessage == null) {
      //context.getRequest().setAttribute("CampaignList", roleList);
      return ("ListOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandMessageBuilder(ActionContext context) {
    addModuleBean(context, "MessageBuilder", "List of Messages");
    return ("MessageBuilderOK");
  }

}

