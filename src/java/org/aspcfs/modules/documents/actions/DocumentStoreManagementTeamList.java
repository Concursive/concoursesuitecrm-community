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
package org.aspcfs.modules.documents.actions;

import org.aspcfs.modules.actions.CFSModule;
import com.darkhorseventures.framework.actions.*;
import org.aspcfs.modules.documents.base.*;
import java.sql.*;
import java.util.*;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.modules.admin.base.*;
import org.aspcfs.utils.web.LookupElement;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.base.Constants;
//import com.zeroio.controller.*;
/**
 *  Description of the Class
 *
 *@author     
 *@created    
 *@version    $Id$
 */
public final class DocumentStoreManagementTeamList extends CFSModule {

  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDocumentStore(ActionContext context) {
    //Parameters
    String value = context.getRequest().getParameter("source");
    StringTokenizer st = new StringTokenizer(value, "|");
    String source = st.nextToken();
    String status = st.nextToken();
    //Build the list
    Connection db = null;
    String memberType = context.getRequest().getParameter("memberType");
    DocumentStoreList documentStoreList = new DocumentStoreList();
    try {
      db = getConnection(context);
      context.getRequest().setAttribute("memberType", memberType);
      if ("my".equals(source) || "all".equals(source)) {
        documentStoreList.setDocumentStoresForUser(getUserId(context));
        if ("open".equals(status)) {
          //Check if open or closed
          documentStoreList.setOpenDocumentStoresOnly(true);
        } else {
          documentStoreList.setClosedDocumentStoresOnly(true);
        }
        documentStoreList.buildList(db);
        context.getRequest().setAttribute("documentStoreList", documentStoreList);
        return "DocumentStoreListOK";
      } else if ("dept".equals(source) && "all".equals(status)) {
        LookupList departmentList = new LookupList(db, "lookup_department");
        if (DocumentStoreTeamMemberList.USER.equals(memberType)){
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
        LookupList accountTypeList = new LookupList(db, "lookup_account_types");
        if (DocumentStoreTeamMemberList.USER.equals(memberType)){
          accountTypeList.addItem(0, "Without a type");
        }
        context.getRequest().setAttribute("accountTypes", accountTypeList);
        return "MakeAccountTypeListOK";
      }
    } catch (Exception e) {

    } finally {
      freeConnection(context, db);
    }
    return null;
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandItems(ActionContext context) {
    //Parameters
    String value = context.getRequest().getParameter("source");
    StringTokenizer st = new StringTokenizer(value, "|");
    String source = st.nextToken();
    String status = st.nextToken();
    String id = st.nextToken();
    Connection db = null;
    try {
      db = getConnection(context);
      if ("my".equals(source) || "all".equals(source)) {
        //Load the document store and check permissions
        DocumentStore thisDocumentStore = new DocumentStore(db, Integer.parseInt(id));
        thisDocumentStore.buildPermissionList(db);
        //Prepare list of team members
        DocumentStoreTeamMemberList team = new DocumentStoreTeamMemberList();
        team.setDocumentStoreId(Integer.parseInt(id));
        team.setMemberType(DocumentStoreTeamMemberList.USER);
        //Check permission first
        if (hasDocumentStoreAccess(context, db, thisDocumentStore, "documentcenter-team-view")) {
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
        users.buildList(db);
        context.getRequest().setAttribute("UserList", users);
        return ("MakeUserListOK");
      }
      if ("acct".equals(source) && "all".equals(status)) {
        //Load departments and get the contacts
        UserList allAccountUsers = new UserList();
        allAccountUsers.setBuildAccountUsersOnly(true);
        allAccountUsers.setRoleType(Constants.ROLETYPE_REGULAR);
        allAccountUsers.setBuildContactDetails(true);
        allAccountUsers.buildList(db);
        Iterator itr = allAccountUsers.iterator();
        UserList users = new UserList();
        
        while (itr.hasNext()){
          User thisUser = (User)itr.next();
          Organization organization = new Organization(db,thisUser.getContact().getOrgId());
          
          //Append organization name if this user is not a primary contact of this organization
          if (organization.getPrimaryContact() !=  null){
            if (organization.getPrimaryContact().getId() != thisUser.getContact().getId()){
              thisUser.getContact().setNameLast(thisUser.getContact().getNameLast() + " (" +organization.getName() + ")");
            }
          }else{
            thisUser.getContact().setNameLast(thisUser.getContact().getNameLast() + " (" +organization.getName() + ")");
          }
          
          //Filter the fetched user list based on the account type of the
          //account to which the user belongs to
          Iterator typesIterator = organization.getTypes().iterator();
          if ((organization.getTypes().size() == 0) &&
            (Integer.parseInt(id) == 0)){
                //include if the account does not have a type and "without type" is chosen
                users.add(thisUser);
          }else{
            while (typesIterator.hasNext()){
              LookupElement lookupElement = (LookupElement)typesIterator.next();
              //include if the account type is one of the chosen types
              if (lookupElement.getCode() == Integer.parseInt(id)){
                users.add(thisUser);
              }
            }
          }
        }
        context.getRequest().setAttribute("UserList", users);
        return ("MakeUserListOK");
      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
    } finally {
      freeConnection(context, db);
    }
    return null;
  }

}

