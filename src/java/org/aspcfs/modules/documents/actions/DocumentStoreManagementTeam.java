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

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.Role;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.documents.base.DocumentStore;
import org.aspcfs.modules.documents.base.DocumentStoreTeamMember;
import org.aspcfs.modules.documents.base.DocumentStoreTeamMemberList;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.LookupList;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;

/**
 * Document Store Management module for CFS
 *
 * @author
 * @version $Id$
 * @created
 */
public final class DocumentStoreManagementTeam extends CFSModule {

  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandModify(ActionContext context) {
    String documentStoreId = (String) context.getRequest().getParameter(
        "documentStoreId");
    String type = context.getRequest().getParameter("modifyTeam");
    SystemStatus systemStatus = this.getSystemStatus(context);
    Connection db = null;
    try {
      db = getConnection(context);
      //Load the document store
      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("SiteIdList", siteList);
      DocumentStore thisDocumentStore = new DocumentStore(
          db, Integer.parseInt(documentStoreId));
      if (thisDocumentStore.getId() == -1) {
        throw new Exception("Invalid access to document store");
      }
      thisDocumentStore.buildPermissionList(db);
      //verify permissions
      if (!hasDocumentStoreAccess(
          context, db, thisDocumentStore, "documentcenter-team-edit")) {
        return "PermissionError";
      }

      DocumentStoreTeamMemberList userTeam = new DocumentStoreTeamMemberList();
      DocumentStoreTeamMemberList roleTeam = new DocumentStoreTeamMemberList();
      DocumentStoreTeamMemberList departmentTeam = new DocumentStoreTeamMemberList();
      context.getRequest().setAttribute("documentStore", thisDocumentStore);
      if (type.equals(DocumentStoreTeamMemberList.USER)) {
        context.getRequest().setAttribute(
            "IncludeSection", ("user_membership_modify").toLowerCase());
        userTeam.setDocumentStoreId(thisDocumentStore.getId());
        userTeam.setMemberType(DocumentStoreTeamMemberList.USER);
        userTeam.buildList(db);
      } else if (type.equals(DocumentStoreTeamMemberList.GROUP)) {
        context.getRequest().setAttribute(
            "IncludeSection", ("group_membership_modify").toLowerCase());
        roleTeam.setDocumentStoreId(thisDocumentStore.getId());
        roleTeam.setMemberType(DocumentStoreTeamMemberList.ROLE);
        roleTeam.buildList(db);

        departmentTeam.setDocumentStoreId(thisDocumentStore.getId());
        departmentTeam.setMemberType(DocumentStoreTeamMemberList.DEPARTMENT);
        departmentTeam.buildList(db);
      }
      //Load the team members

      //Prepare the first array of those already on list, for javascript
      StringBuffer vectorUserId = new StringBuffer();
      StringBuffer vectorState = new StringBuffer();
      HtmlSelect selCurrentTeam = new HtmlSelect();
      Iterator iTeam = userTeam.iterator();
      if (type.equals(DocumentStoreTeamMemberList.USER)) {
        while (iTeam.hasNext()) {
          DocumentStoreTeamMember thisMember = (DocumentStoreTeamMember) iTeam.next();
          User tmpUser = getUser(context, thisMember.getItemId());
          if (tmpUser.getContact().getOrgId() == 0) {
            selCurrentTeam.addItem(
                thisMember.getItemId(), tmpUser.getContact().getNameFirstLast() + (!tmpUser.getEnabled() ? " (X)" : ""));
          } else {
            //Append organization name if this user is not a primary contact of his organization
            Organization organization = new Organization(
                db, tmpUser.getContact().getOrgId());
            String userNameForDisplay = tmpUser.getContact().getNameFirstLast() + " (" + organization.getName() + ")" +
                (!tmpUser.getEnabled() || !tmpUser.getContact().getEnabled() || tmpUser.getContact().isTrashed() ? " (X)" : "");
            if (organization.getPrimaryContact() != null) {
              if (organization.getPrimaryContact().getId() == tmpUser.getContact().getId()) {
                userNameForDisplay = tmpUser.getContact().getNameFirstLast() +
                    (!tmpUser.getEnabled() || !tmpUser.getContact().getEnabled() || tmpUser.getContact().isTrashed() ? " (X)" : "");
              }
            }
            selCurrentTeam.addItem(thisMember.getItemId(), userNameForDisplay);
          }
          vectorUserId.append(thisMember.getItemId());
          vectorState.append(DocumentStoreTeamMemberList.USER);
          if (iTeam.hasNext()) {
            vectorUserId.append("|");
            vectorState.append("|");
          }
        }
      } else if (type.equals(DocumentStoreTeamMemberList.GROUP)) {
        iTeam = roleTeam.iterator();
        if (iTeam.hasNext()) {
          while (iTeam.hasNext()) {
            DocumentStoreTeamMember thisMember = (DocumentStoreTeamMember) iTeam.next();
            Role role = new Role(db, thisMember.getItemId());
            selCurrentTeam.addItem(
                thisMember.getItemId() + "-R"+thisMember.getSiteId(), role.getRole() + " " + "(Role)"+(thisMember.getSiteId() != -1 ?" ("+siteList.getSelectedValue(thisMember.getSiteId())+")":""));
            vectorUserId.append(thisMember.getItemId() + "-R"+thisMember.getSiteId());
            vectorState.append(DocumentStoreTeamMemberList.ROLE);
            if (iTeam.hasNext()) {
              vectorUserId.append("|");
              vectorState.append("|");
            }
          }
        }
        iTeam = departmentTeam.iterator();
        if (iTeam.hasNext()) {
          vectorUserId.append("|");
          vectorState.append("|");
          while (iTeam.hasNext()) {
            DocumentStoreTeamMember thisMember = (DocumentStoreTeamMember) iTeam.next();
            LookupList departmentList = new LookupList(
                db, "lookup_department");
            selCurrentTeam.addItem(
                thisMember.getItemId() + "-D"+thisMember.getSiteId(), departmentList.getValueFromId(
                    thisMember.getItemId()) + " " + "(Dept)"+(thisMember.getSiteId() != -1 ?" ("+siteList.getSelectedValue(thisMember.getSiteId())+")":""));
            vectorUserId.append(thisMember.getItemId() + "-D"+thisMember.getSiteId());
            vectorState.append(DocumentStoreTeamMemberList.DEPARTMENT);
            if (iTeam.hasNext()) {
              vectorUserId.append("|");
              vectorState.append("|");
            }
          }
        }
      }
      context.getRequest().setAttribute("currentTeam", selCurrentTeam);
      context.getRequest().setAttribute(
          "vectorUserId", vectorUserId.toString());
      context.getRequest().setAttribute("vectorState", vectorState.toString());
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("DocumentStoreCenterOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandUpdateUserList(ActionContext context) {
    if (!"true".equals(
        (String) context.getServletContext().getAttribute("DEPARTMENT"))) {
      return "PermissionError";
    }
    Connection db = null;
    try {
      String deptId = context.getRequest().getParameter("deptId");
      db = this.getConnection(context);
      UserList userList = new UserList();
      if (deptId != null) {
        userList.setDepartment(Integer.parseInt(deptId));
      }
      userList.buildList(db);
      context.getRequest().setAttribute("UserList", userList);
    } catch (SQLException e) {

    } finally {
      this.freeConnection(context, db);
    }
    return ("MakeUserListOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandUpdate(ActionContext context) {

    Connection db = null;
    //Parameters
    String documentStoreId = (String) context.getRequest().getParameter(
        "documentStoreId");
    String memberType = (String) context.getRequest().getParameter(
        "memberType");
    boolean recordInserted = false;
    try {
      db = getConnection(context);
      //DocumentStore permissions
      DocumentStore thisDocumentStore = new DocumentStore(
          db, Integer.parseInt(documentStoreId));
      thisDocumentStore.buildPermissionList(db);
      //verify permissions

      context.getRequest().setAttribute("documentStore", thisDocumentStore);
      context.getRequest().setAttribute(
          "IncludeSection", ("team_modify").toLowerCase());
      context.getRequest().setAttribute("documentStoreId", documentStoreId);
      // TODO: Add the emailing feature when new users are added to a document store
      //Process the members
      DocumentStoreTeamMemberList thisTeam = (DocumentStoreTeamMemberList) context.getFormBean();
      thisTeam.setDocumentStoreId(thisDocumentStore.getId());
      thisTeam.setUserLevel(
          getDocumentStoreUserLevel(
              context, db, DocumentStoreTeamMember.GUEST));
      thisTeam.setEnteredBy(getUserId(context));
      thisTeam.setModifiedBy(getUserId(context));
      if ("user".equals(memberType)) {
        thisTeam.setSiteIdForMembers(this.getSystemStatus(context));
        recordInserted = thisTeam.updateUserMembership(db);
      } else {
        recordInserted = thisTeam.updateGroupMembership(db);
      }
      if (recordInserted) {
        return ("ModifyOK");
      } else {
        return ("ModifyERROR");
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      freeConnection(context, db);
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandChangeRole(ActionContext context) {
    Connection db = null;
    //Process the params
    String documentStoreId = context.getRequest().getParameter(
        "documentStoreId");
    String itemId = context.getRequest().getParameter("id");
    String newRole = context.getRequest().getParameter("role");
    String memberType = context.getRequest().getParameter("memberType");
    String siteId = context.getRequest().getParameter("siteId");
    try {
      db = this.getConnection(context);
      //Load the document store
      DocumentStore thisDocumentStore = new DocumentStore(
          db, Integer.parseInt(documentStoreId));
      if (thisDocumentStore.getId() == -1) {
        throw new Exception("Invalid access to document store");
      }
      thisDocumentStore.buildPermissionList(db);
      //verify permissions
      if (!hasDocumentStoreAccess(
          context, db, thisDocumentStore, "documentcenter-team-edit-role")) {
        return "PermissionError";
      }
      //Make sure user can change roles
      int tmpUserId = this.getUserId(context);
      User tmpUser = getUser(context, tmpUserId);
      int tmpUserRoleId = tmpUser.getRoleId();
      Contact tmpContact = new Contact(db, tmpUser.getContactId());
      int tmpDepartmentId = tmpContact.getDepartment();

      DocumentStoreTeamMember currentMember = new DocumentStoreTeamMember(
          db, thisDocumentStore.getId(), tmpUserId, tmpUserRoleId, tmpDepartmentId, tmpUser.getSiteId());
      //Allow role change only if the "role to change" is less privileged than the users role
      //e.g., if the current member (user's) role is 'contributor level 1' he would not be allowed 
      //to change members roles to any higher that of 'contributor level 1' 
      if (currentMember.getRoleId() <= Integer.parseInt(newRole)) {
        boolean changed = DocumentStoreTeamMember.changeRole(
            db, thisDocumentStore.getId(), Integer.parseInt(itemId), Integer.parseInt(
                newRole), memberType, Integer.parseInt(siteId));
        if (!changed) {
          return ("ChangeRoleERROR");
        }
        return "ChangeRoleOK";
      }
      return ("ChangeRoleERROR");
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      errorMessage.printStackTrace(System.out);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
  }
}

