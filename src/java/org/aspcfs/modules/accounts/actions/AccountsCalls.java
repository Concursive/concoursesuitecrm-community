package org.aspcfs.modules.accounts.actions;

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;
import java.text.*;
import java.io.*;
import com.darkhorseventures.framework.actions.*;
import org.aspcfs.utils.*;
import org.aspcfs.utils.web.*;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.accounts.base.*;
import org.aspcfs.modules.base.*;
import org.aspcfs.modules.contacts.base.*;

/**
 *  Actions for the Account Activities
 *
 *@author     Mathur
 *@created    January 20, 2004
 *@version    $id:exp$
 */
public final class AccountsCalls extends CFSModule {
  /**
   *  Default Action: View Activities
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    return executeCommandView(context);
  }


  /**
   *   View Activities
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandView(ActionContext context) {
    int MINIMIZED_ITEMS_PER_PAGE = 5;
    if (!hasPermission(context, "accounts-accounts-contacts-calls-view")) {
      return ("PermissionError");
    }

    //parameters
    String orgId = context.getRequest().getParameter("orgId");
    
    //reset the paged lists
    if ("true".equals(context.getRequest().getParameter("resetList"))) {
      context.getSession().removeAttribute("AccountContactCallsListInfo");
      context.getSession().removeAttribute("AccountContactCompletedCallsListInfo");
    }
    //Determine the sections to view
    String sectionId = null;
    if (context.getRequest().getParameter("pagedListSectionId") != null) {
      sectionId = context.getRequest().getParameter("pagedListSectionId");
    }
    addModuleBean(context, "Activities", "View Activities");
    Connection db = null;
    //Pending Call List
    CallList callList = new CallList();

    String pendingPagedListId = "AccountContactCallsListInfo";

    if (sectionId == null || pendingPagedListId.equals(sectionId)) {
      PagedListInfo callListInfo = this.getPagedListInfo(context, pendingPagedListId, "c.alertdate", null);
      callListInfo.setLink("AccountsCalls.do?command=View&orgId=" + orgId +
          HTTPUtils.addLinkParams(context.getRequest(), "popup|popupType|actionId"));
      if (sectionId == null) {
        if (!callListInfo.getExpandedSelection()) {
          if (callListInfo.getItemsPerPage() != MINIMIZED_ITEMS_PER_PAGE) {
            callListInfo.setItemsPerPage(MINIMIZED_ITEMS_PER_PAGE);
          }
        } else {
          if (callListInfo.getItemsPerPage() == MINIMIZED_ITEMS_PER_PAGE) {
            callListInfo.setItemsPerPage(PagedListInfo.DEFAULT_ITEMS_PER_PAGE);
          }
        }
      } else if (sectionId.equals(callListInfo.getId())) {
        callListInfo.setExpandedSelection(true);
      }
      callList.setPagedListInfo(callListInfo);
      //set the account
      callList.setOnlyPending(true);
      callList.setAllContactsInAccount(true, Integer.parseInt(orgId));
    }

    //Completed Call List
    CallList completedCallList = new CallList();

    String completedPagedListId = "AccountContactCompletedCallsListInfo";

    if (sectionId == null || completedPagedListId.equals(sectionId)) {
      PagedListInfo completedCallListInfo = this.getPagedListInfo(context, completedPagedListId, "c.entered", "desc");
      completedCallListInfo.setLink("AccountsCalls.do?command=View&orgId=" + orgId +
          HTTPUtils.addLinkParams(context.getRequest(), "popup|popupType|actionId"));
      if (sectionId == null) {
        if (!completedCallListInfo.getExpandedSelection()) {
          if (completedCallListInfo.getItemsPerPage() != MINIMIZED_ITEMS_PER_PAGE) {
            completedCallListInfo.setItemsPerPage(MINIMIZED_ITEMS_PER_PAGE);
          }
        } else {
          if (completedCallListInfo.getItemsPerPage() == MINIMIZED_ITEMS_PER_PAGE) {
            completedCallListInfo.setItemsPerPage(PagedListInfo.DEFAULT_ITEMS_PER_PAGE);
          }
        }
      } else if (sectionId.equals(completedCallListInfo.getId())) {
        completedCallListInfo.setExpandedSelection(true);
      }
      completedCallList.setPagedListInfo(completedCallListInfo);
      completedCallList.setAllContactsInAccount(true, Integer.parseInt(orgId));
    }
    
    try {
      db = this.getConnection(context);
      if (sectionId == null || pendingPagedListId.equals(sectionId)) {
        callList.buildList(db);
      }
      if (sectionId == null || completedPagedListId.equals(sectionId)) {
        completedCallList.buildList(db);
      }

      //add account to the request
      addFormElements(context, db);

      SystemStatus systemStatus = this.getSystemStatus(context);
      //Need the call types for display purposes
      LookupList callTypeList = systemStatus.getLookupList(db, "lookup_call_types");
      context.getRequest().setAttribute("CallTypeList", callTypeList);

      //Need the result types for display purposes
      CallResultList resultList = new CallResultList();
      resultList.buildList(db);
      context.getRequest().setAttribute("callResultList", resultList);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("CallList", callList);
    context.getRequest().setAttribute("CompletedCallList", completedCallList);
    return this.getReturn(context, "View");
  }


  /**
   *  Adds a feature to the FormElements attribute of the AccountsCalls object
   *
   *@param  context           The feature to be added to the FormElements attribute
   *@param  db                The feature to be added to the FormElements attribute
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public Organization addFormElements(ActionContext context, Connection db) throws SQLException {
    String orgId = context.getRequest().getParameter("orgId");
    Organization thisOrg = new Organization(db, Integer.parseInt(orgId));
    context.getRequest().setAttribute("OrgDetails", thisOrg);
    return thisOrg;
  }
}


