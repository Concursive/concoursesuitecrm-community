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
import org.aspcfs.modules.quotes.base.*;
import org.aspcfs.modules.admin.base.AccessType;
import org.aspcfs.modules.admin.base.AccessTypeList;
import org.aspcfs.modules.admin.base.RoleList;
import org.aspcfs.modules.admin.base.Role;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.base.*;
import org.aspcfs.utils.web.*;
import org.aspcfs.modules.products.*;
import org.aspcfs.controller.*;
import org.aspcfs.modules.quotes.base.*;
/**
 *  Description of the Class
 *
 * @author     ananth
 * @created    April 23, 2004
 * @version    $Id$
 */
public final class AccountQuotes extends CFSModule {

  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandView(ActionContext context) {
    /*
     *  if (!(hasPermission(context, "accounts-accounts-quotes-view"))) {
     *  return ("PermissionError");
     *  }
     */
    addModuleBean(context, "View Quotes", "View Quote Details");
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
    PagedListInfo quoteListInfo = this.getPagedListInfo(context, "QuoteListInfo");
    quoteListInfo.setLink("AccountQuotes.do?command=View&orgId=" + orgid);
    Connection db = null;
    QuoteList quoteList = new QuoteList();
    Organization thisOrganization = null;
    this.resetPagedListInfo(context);
    try {
      db = this.getConnection(context);
      quoteList.setPagedListInfo(quoteListInfo);
      quoteList.setOrgId(Integer.parseInt(orgid));
      //quoteList.setBuildDetails(true);
      //quoteList.setBuildTypes(false);
      quoteList.buildList(db);
      thisOrganization = new Organization(db, Integer.parseInt(orgid));

      SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList list = systemStatus.getLookupList(db, "lookup_quote_status");
      context.getRequest().setAttribute("quoteStatusList", list);

    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      errorMessage.printStackTrace();
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("quoteList", quoteList);
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
    if (!hasPermission(context, "accounts-accounts-quotes-view")) {
      return ("PermissionError");
    }
    */
    addModuleBean(context, "View Quotes", "View Quote Details");
    String quoteId = context.getRequest().getParameter("id");
    Connection db = null;
    Quote quote = null;
    Organization thisOrganization = null;
    try {
      db = this.getConnection(context);

      quote = new Quote(db, Integer.parseInt(quoteId));
      quote.buildProducts(db);
      quote.retrieveTicket(db);
      context.getRequest().setAttribute("quote", quote);

      thisOrganization = new Organization(db, quote.getOrgId());

      SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList list = systemStatus.getLookupList(db, "lookup_quote_status");
      context.getRequest().setAttribute("quoteStatusList", list);
    } catch (Exception errorMessage) {
      errorMessage.printStackTrace();
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("quote", quote);
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

