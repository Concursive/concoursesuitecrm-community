package com.darkhorseventures.cfsmodule;

import javax.servlet.*;
import javax.servlet.http.*;
import org.theseus.actions.*;
import java.sql.*;
import java.util.Vector;
import com.darkhorseventures.utils.*;
import com.darkhorseventures.cfsbase.*;
import com.darkhorseventures.webutils.*;

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
    campaignListInfo.setLink("/CampaignManager.do?command=Dashboard");

    Connection db = null;
    //RoleList roleList = new RoleList();
    try {
      db = this.getConnection(context);
      //roleList.setPagedListInfo(roleInfo);
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

