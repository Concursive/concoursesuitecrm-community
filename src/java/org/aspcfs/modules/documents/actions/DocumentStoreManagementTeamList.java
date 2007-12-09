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
package org.aspcfs.modules.documents.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.OrganizationList;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.RoleList;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.contacts.base.ContactList;
import org.aspcfs.modules.documents.base.AccountDocumentList;
import org.aspcfs.modules.documents.base.DocumentStore;
import org.aspcfs.modules.documents.base.DocumentStoreList;
import org.aspcfs.modules.documents.base.DocumentStoreTeamMemberList;
import org.aspcfs.utils.web.LookupList;

import java.sql.Connection;
import java.util.StringTokenizer;

//import com.zeroio.controller.*;

/**
 * Description of the Class
 *
 * @author
 * @version $Id$
 * @created
 */
public final class DocumentStoreManagementTeamList extends CFSModule {

  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDocumentStore(ActionContext context) {
    //Parameters
    String value = context.getRequest().getParameter("source");
    String search = context.getRequest().getParameter("search");
    StringTokenizer st = new StringTokenizer(value, "|");
    String source = st.nextToken();
    String status = st.nextToken();
    //Build the list
    Connection db = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    String memberType = context.getRequest().getParameter("memberType");
    DocumentStoreList documentStoreList = new DocumentStoreList();
    try {
      String siteId = "-1";
      if (st.hasMoreTokens()) {
        siteId = st.nextToken();
        context.getRequest().setAttribute("siteId", siteId);
      }
      db = getConnection(context);
      context.getRequest().setAttribute("memberType", memberType);
      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("SiteIdList", siteList);
      if ("my".equals(source) || "all".equals(source)) {
        documentStoreList.setDocumentStoresForUser(getUserId(context));
        if ("open".equals(status)) {
          //Check if open or closed
          documentStoreList.setOpenDocumentStoresOnly(true);
        } else {
          documentStoreList.setClosedDocumentStoresOnly(true);
        }
        documentStoreList.buildList(db);
        context.getRequest().setAttribute(
            "documentStoreList", documentStoreList);
        return "DocumentStoreListOK";
      } else if ("dept".equals(source) && "all".equals(status)) {
        LookupList departmentList = new LookupList(db, "lookup_department");
        if (DocumentStoreTeamMemberList.USER.equals(memberType)) {
          departmentList.addItem(0, "Without a department");
        }
        context.getRequest().setAttribute("departments", departmentList);
        return "MakeDepartmentListOK";
      } else if ("role".equals(source) && "all".equals(status)) {
        RoleList roleList = new RoleList();
        roleList.buildList(db);
        context.getRequest().setAttribute("roles", roleList);
        return "MakeRoleListOK";
      } else if ("acct".equals(source) && "all".equals(status)) {
        OrganizationList organizationList = new OrganizationList();
        organizationList.setName('%' + search + '%');
        organizationList.buildShortList(db);
        context.getRequest().setAttribute("orgList", organizationList);
        return "MakeOrgListOK";
      }
    } catch (Exception e) {

    } finally {
      freeConnection(context, db);
    }
    return null;
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandItems(ActionContext context) {
    //Parameters
    String value = context.getRequest().getParameter("source");
    StringTokenizer st = new StringTokenizer(value, "|");
    String source = st.nextToken();
    String status = st.nextToken();
    String siteId = String.valueOf(this.getUserSiteId(context));
    String id = st.nextToken();
    String orgId = null;
    if (st.hasMoreTokens()) {
      siteId = st.nextToken();
      orgId = siteId;
      context.getRequest().setAttribute("siteId", siteId);
    }
    Connection db = null;
    try {
      db = getConnection(context);
      if ("my".equals(source) || "all".equals(source)) {
        //Load the document store and check permissions
        DocumentStore thisDocumentStore = new DocumentStore(
            db, Integer.parseInt(id));
        thisDocumentStore.buildPermissionList(db);
        //Prepare list of team members
        DocumentStoreTeamMemberList team = new DocumentStoreTeamMemberList();
        team.setDocumentStoreId(Integer.parseInt(id));
        team.setMemberType(DocumentStoreTeamMemberList.USER);
        //Check permission first
        if (hasDocumentStoreAccess(
            context, db, thisDocumentStore, "documentcenter-team-view")) {
          team.buildList(db);
        }
        context.getRequest().setAttribute("team", team);
        return ("MakeTeamMemberListOK");
      }
      if ("dept".equals(source) && "all".equals(status)) {
        //Load departments and get the contacts
        UserList users = new UserList();
        users.setDepartment(Integer.parseInt(id));
        users.setBuildEmployeeUsersOnly(true);
        users.setSiteId(siteId);
        users.buildList(db);
        context.getRequest().setAttribute("UserList", users);
        return ("MakeUserListOK");
      }
      if ("acct".equals(source) && "all".equals(status)) {
        boolean nonPortalUsersOnly = false;
        AccountDocumentList accountDocumentList = new AccountDocumentList();
        accountDocumentList.setDocumentStoreId(id);
        accountDocumentList.setOrgId(orgId);
        accountDocumentList.buildList(db);
        if (accountDocumentList.isEmpty()) {
          nonPortalUsersOnly = true;
        }
        ContactList contactList = new ContactList();
        contactList.setOrgId(orgId);
        contactList.setWithAccountsOnly(true);
        contactList.setPortalUsersOnly(
            nonPortalUsersOnly ? Constants.FALSE : Constants.UNDEFINED);
        contactList.buildList(db);
        context.getRequest().setAttribute("contactList", contactList);
        return ("MakeContactListOK");
      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
    } finally {
      freeConnection(context, db);
    }
    return null;
  }

}

