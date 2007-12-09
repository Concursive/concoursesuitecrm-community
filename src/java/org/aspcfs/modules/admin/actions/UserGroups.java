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
package org.aspcfs.modules.admin.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserGroup;
import org.aspcfs.modules.admin.base.UserGroupList;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.utils.web.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 *  Description of the Class
 *
 * @author     partha
 * @created    September 22, 2005
 * @version    $Id$
 */
public final class UserGroups extends CFSModule {

  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    if (!hasPermission(context, "admin-view")) {
      return ("PermissionError");
    }
    return executeCommandList(context);
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandList(ActionContext context) {
    if (!hasPermission(context, "admin-view")) {
      return ("PermissionError");
    }
    PagedListInfo listInfo = getPagedListInfo(context, "userGroupListInfo");
    listInfo.setLink("UserGroups.do?command=List");
    Connection db = null;
    User user = this.getUser(context, this.getUserId(context));
    UserGroupList groups = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      db = this.getConnection(context);
      LookupList siteid = new LookupList(db, "lookup_site_id");
      siteid.addItem(-1, this.getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("SiteIdList", siteid);
      groups = new UserGroupList();
      groups.setPagedListInfo(listInfo);
      groups.setBuildResources(true);
      if (user.getSiteId() > -1) {
        groups.setSiteId(user.getSiteId());
      } else {
        groups.setIncludeAllSites(true);
      }
      groups.buildList(db);
      context.getRequest().setAttribute("groupList", groups);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "ListOK";
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandAdd(ActionContext context) {
    Connection db = null;
    String status = null;
    try {
      db = this.getConnection(context);
      status = this.executeCommandAdd(context, db);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return status;
  }

  private String executeCommandAdd(ActionContext context, Connection db) throws SQLException {
    if (!hasPermission(context, "admin-view")) {
      return ("PermissionError");
    }
    UserGroup group = (UserGroup) context.getFormBean();
    if (group == null) {
      group = new UserGroup();
    }
    SystemStatus systemStatus = this.getSystemStatus(context);
    context.getRequest().setAttribute("userGroup", group);
    StringBuffer vectorUserId = new StringBuffer();
    StringBuffer vectorState = new StringBuffer();
    HtmlSelect selCurrentTeam = new HtmlSelect();
    LookupList siteid = new LookupList(db, "lookup_site_id");
    siteid.addItem(-1, this.getSystemStatus(context).getLabel("calendar.none.4dashes"));
    context.getRequest().setAttribute("SiteIdList", siteid);
    if (group.getGroupUsers() != null) {
      Iterator iter = group.getGroupUsers().iterator();
      while (iter.hasNext()) {
        User selUser = (User) iter.next();
        if (selUser.getContact().getOrgId() == 0) {
          selCurrentTeam.addItem(selUser.getId(), selUser.getContact().getNameFirstLast() + (!selUser.getEnabled() ? " (X)" : ""));
        } else {
          // Append organization name if this user is not a primary contact of
          // his organization
          Organization organization = new Organization(db, selUser.getContact().getOrgId());
          String userNameForDisplay = selUser.getContact().getNameFirstLast() + " (" + organization.getName() + ")"
              + (!selUser.getEnabled() || !selUser.getContact().getEnabled() || selUser.getContact().isTrashed() ? " (X)" : "");
          if (organization.getPrimaryContact() != null) {
            if (organization.getPrimaryContact().getId() == selUser.getContact().getId()) {
              userNameForDisplay = selUser.getContact().getNameFirstLast()
                  + (!selUser.getEnabled() || !selUser.getContact().getEnabled() || selUser.getContact().isTrashed() ? " (X)" : "");
            }
          }
          selCurrentTeam.addItem(selUser.getId(), userNameForDisplay);
        }
        vectorUserId.append(selUser.getId());
        vectorState.append("user");
        if (iter.hasNext()) {
          vectorUserId.append("|");
          vectorState.append("|");
        }
      }
    }
    context.getRequest().setAttribute("currentTeam", selCurrentTeam);
    context.getRequest().setAttribute("vectorUserId", vectorUserId.toString());
    context.getRequest().setAttribute("vectorState", vectorState.toString());
    return "AddOK";
  }


  /**
   * Description of the Method
   * 
   * @param context
   *          Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandSave(ActionContext context) {
    if (!hasPermission(context, "admin-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    UserGroup group = (UserGroup) context.getFormBean();
    boolean isValid = false;
    boolean recordInserted = false;
    int recordCount = -1;
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      db = this.getConnection(context);
      group.setModifiedBy(this.getUserId(context));
      if (group.getId() > -1) {
        isValid = this.validateObject(context, db, group);
        if (isValid) {
          recordCount = group.update(db);
          if (group.getInsertMembers() != null || group.getDeleteMembers() != null) {
            group.updateUserMembership(db);
          }
        }
      } else {
        isValid = this.validateObject(context, db, group);
        if (isValid) {
          recordInserted = group.insert(db);
          if (group.getInsertMembers() != null || group.getDeleteMembers() != null) {
            group.updateUserMembership(db);
          }
        }
      }
      context.getRequest().setAttribute("userGroup", group);
      if (!isValid || (!recordInserted && recordCount == -1)) {
        if (group.getId() > -1) {
          return executeCommandAdd(context,db);
        } else {
          return executeCommandModify(context,db);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "SaveOK";
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandModify(ActionContext context) {
    Connection db = null;
    String status = null;
    try {
      db = this.getConnection(context);
      status = this.executeCommandModify(context, db);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return status;
  }

  private String executeCommandModify(ActionContext context, Connection db) throws NumberFormatException, SQLException {
    if (!hasPermission(context, "admin-view")) {
      return ("PermissionError");
    }
    String groupId = context.getRequest().getParameter("groupId");
    UserGroup group = (UserGroup) context.getFormBean();
    SystemStatus systemStatus = this.getSystemStatus(context);
    if (group.getId() == -1) {
      group = new UserGroup();
      group.setBuildResources(true);
      group.queryRecord(db, Integer.parseInt(groupId));
    }
    LookupList siteid = new LookupList(db, "lookup_site_id");
    siteid.addItem(-1, this.getSystemStatus(context).getLabel("calendar.none.4dashes"));
    context.getRequest().setAttribute("SiteIdList", siteid);
    StringBuffer vectorUserId = new StringBuffer();
    StringBuffer vectorState = new StringBuffer();
    HtmlSelect selCurrentTeam = new HtmlSelect();
    if (group.getGroupUsers() != null) {
      Iterator iter = group.getGroupUsers().iterator();
      while (iter.hasNext()) {
        User selUser = (User) iter.next();
        if (selUser.getContact().getOrgId() == 0) {
          selCurrentTeam.addItem(selUser.getId(), selUser.getContact().getNameFirstLast() + (!selUser.getEnabled() ? " (X)" : ""));
        } else {
          // Append organization name if this user is not a primary contact of
          // his organization
          Organization organization = new Organization(db, selUser.getContact().getOrgId());
          String userNameForDisplay = selUser.getContact().getNameFirstLast() + " (" + organization.getName() + ")"
              + (!selUser.getEnabled() || !selUser.getContact().getEnabled() || selUser.getContact().isTrashed() ? " (X)" : "");
          if (organization.getPrimaryContact() != null) {
            if (organization.getPrimaryContact().getId() == selUser.getContact().getId()) {
              userNameForDisplay = selUser.getContact().getNameFirstLast()
                  + (!selUser.getEnabled() || !selUser.getContact().getEnabled() || selUser.getContact().isTrashed() ? " (X)" : "");
            }
          }
          selCurrentTeam.addItem(selUser.getId(), userNameForDisplay);
        }
        vectorUserId.append(selUser.getId());
        vectorState.append("user");
        if (iter.hasNext()) {
          vectorUserId.append("|");
          vectorState.append("|");
        }
      }
    }
    context.getRequest().setAttribute("currentTeam", selCurrentTeam);
    context.getRequest().setAttribute("vectorUserId", vectorUserId.toString());
    context.getRequest().setAttribute("vectorState", vectorState.toString());
    context.getRequest().setAttribute("userGroup", group);
    return "AddOK";
  }


  /**
   * Description of the Method
   * 
   * @param context
   *          Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDetails(ActionContext context) {
    if (!hasPermission(context, "admin-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    String groupId = context.getRequest().getParameter("groupId");
    UserGroup group = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      db = this.getConnection(context);
      group = new UserGroup();
      group.setBuildResources(true);
      group.queryRecord(db, Integer.parseInt(groupId));
      context.getRequest().setAttribute("userGroup", group);
      LookupList siteid = new LookupList(db, "lookup_site_id");
      siteid.addItem(-1, this.getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("SiteIdList", siteid);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "DetailsOK";
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandConfirmDelete(ActionContext context) {
    if (!(hasPermission(context, "admin-view"))) {
      return ("PermissionError");
    }
    String groupId = context.getRequest().getParameter("groupId");
    if (groupId == null || "".equals(groupId)) {
      groupId = (String) context.getRequest().getAttribute("groupId");
    }
    Connection db = null;
    UserGroup group = null;
    HtmlDialog htmlDialog = new HtmlDialog();
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      db = this.getConnection(context);
      group = new UserGroup();
      group.setBuildResources(true);
      group.queryRecord(db, Integer.parseInt(groupId));

      DependencyList dependencies = group.processDependencies(db);
      dependencies.setSystemStatus(systemStatus);
      htmlDialog.addMessage(
          systemStatus.getLabel("confirmdelete.caution") + "\n" + dependencies.getHtmlString());
      if (dependencies.canDelete()) {
        htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
        htmlDialog.setHeader(systemStatus.getLabel("usergroup.dependencies"));
        htmlDialog.addButton(
            systemStatus.getLabel("global.button.delete"), "javascript:window.location.href='UserGroups.do?command=Delete&groupId=" + group.getId() + "';");
        htmlDialog.addButton(
            systemStatus.getLabel("button.cancel"), "javascript:parent.window.close()");
      } else {
        htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
        htmlDialog.setHeader(systemStatus.getLabel("confirmdelete.unableHeader"));
        htmlDialog.addButton("OK", "javascript:parent.window.close()");
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getSession().setAttribute("Dialog", htmlDialog);
    return ("ConfirmDeleteOK");
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandDelete(ActionContext context) {
    if (!(hasPermission(context, "admin-view"))) {
      return ("PermissionError");
    }
    String groupId = (String) context.getRequest().getParameter("groupId");
    if (groupId != null && !"".equals(groupId)) {
      context.getRequest().setAttribute("groupId", groupId);
    }
    UserGroup group = null;
    Connection db = null;
    try {
      db = getConnection(context);
      group = new UserGroup();
      group.setBuildResources(true);
      group.queryRecord(db, Integer.parseInt(groupId));
      //delete the group
      group.delete(db);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute(
        "refreshUrl", "UserGroups.do?command=List");
    return "DeleteOK";
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandUsers(ActionContext context) {
    //Parameters
    String value = context.getRequest().getParameter("source");
    StringTokenizer st = new StringTokenizer(value, "|");
    String source = st.nextToken();
    String status = st.nextToken();
    //Build the list
    Connection db = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      db = getConnection(context);
      if ("dept".equals(source) && "all".equals(status)) {
        LookupList departmentList = new LookupList(db, "lookup_department");
        departmentList.addItem(0, systemStatus.getLabel("admin.group.withoutADepartment", "Without a department"));
        context.getRequest().setAttribute("departments", departmentList);
        return "MakeDepartmentListOK";
      } else if ("acct".equals(source) && "all".equals(status)) {
        LookupList accountTypeList = new LookupList(db, "lookup_account_types");
        accountTypeList.addItem(0, systemStatus.getLabel("admin.group.withoutAType", "Without a type"));
        context.getRequest().setAttribute("accountTypes", accountTypeList);
        return "MakeAccountTypeListOK";
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      freeConnection(context, db);
    }
    return null;
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandItems(ActionContext context) {
    //Parameters
    String value = context.getRequest().getParameter("source");
    String siteId = context.getRequest().getParameter("siteId");
    if (!isSiteAccessPermitted(context, siteId)) {
      return ("PermissionError");
    }
/*
    User user = this.getUser(context, this.getUserId(context));
    if (user.getSiteId() != -1 && user.getSiteId() != Integer.parseInt(siteId)) {
      return ("PermissionError");
    }
*/
    StringTokenizer st = new StringTokenizer(value, "|");
    String source = st.nextToken();
    String status = st.nextToken();
    String id = st.nextToken();
    Connection db = null;
    try {
      db = getConnection(context);
      if ("dept".equals(source) && "all".equals(status)) {
        //Load departments and get the contacts
        UserList users = new UserList();
        users.setDepartment(Integer.parseInt(id));
        users.setBuildEmployeeUsersOnly(true);
        users.setSiteId(siteId);
        users.setEnabled(Constants.TRUE);
        users.setIncludeUsersWithAccessToAllSites(true);
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
        allAccountUsers.setSiteId(siteId);
        allAccountUsers.setIncludeUsersWithAccessToAllSites(true);
        allAccountUsers.setEnabled(Constants.TRUE);
        allAccountUsers.buildList(db);
        Iterator itr = allAccountUsers.iterator();
        UserList users = new UserList();

        while (itr.hasNext()) {
          User thisUser = (User) itr.next();
          Organization organization = new Organization(
              db, thisUser.getContact().getOrgId());

          //Append organization name if this user is not a primary contact of this organization
          if (organization.getPrimaryContact() != null) {
            if (organization.getPrimaryContact().getId() != thisUser.getContact().getId()) {
              thisUser.getContact().setNameLast(
                  thisUser.getContact().getNameLast() + " (" + organization.getName() + ")");
            }
          } else {
            thisUser.getContact().setNameLast(
                thisUser.getContact().getNameLast() + " (" + organization.getName() + ")");
          }

          //Filter the fetched user list based on the account type of the
          //account to which the user belongs to
          Iterator typesIterator = organization.getTypes().iterator();
          if ((organization.getTypes().size() == 0) &&
              (Integer.parseInt(id) == 0)) {
            //include if the account does not have a type and "without type" is chosen
            users.add(thisUser);
          } else {
            while (typesIterator.hasNext()) {
              LookupElement lookupElement = (LookupElement) typesIterator.next();
              //include if the account type is one of the chosen types
              if (lookupElement.getCode() == Integer.parseInt(id)) {
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


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandGroupList(ActionContext context) {
    if (!hasPermission(context, "admin-view")) {
      return ("PermissionError");
    }
    String userId = context.getRequest().getParameter("userId");
    PagedListInfo listInfo = getPagedListInfo(context, "groupListInfo");
    listInfo.setLink("UserGroups.do?command=GroupList&userId=" + userId);
    Connection db = null;
    UserGroupList groups = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      db = this.getConnection(context);
      User user = this.getUser(context, Integer.parseInt(userId));
      context.getRequest().setAttribute("thisUser", user);
      UserGroupList completeGroupList = new UserGroupList();
      completeGroupList.setUserId(userId);
      if (user.getSiteId() > -1) {
        completeGroupList.setSiteId(user.getSiteId());
        completeGroupList.setExclusiveToSite(true);
      } else {
        completeGroupList.setIncludeAllSites(true);
      }
      completeGroupList.buildList(db);
      context.getRequest().setAttribute("completeGroupList", completeGroupList);
      groups = new UserGroupList();
      groups.setUserId(userId);
      if (user.getSiteId() > -1) {
        groups.setSiteId(user.getSiteId());
        groups.setExclusiveToSite(true);
      } else {
        groups.setIncludeAllSites(true);
      }
      groups.setBuildUserCount(true);
      groups.setPagedListInfo(listInfo);
      groups.buildList(db);
      context.getRequest().setAttribute("groupList", groups);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "GroupListOK";
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandPopupSelector(ActionContext context) {
    Connection db = null;
    User user = null;
    String userId = (String) context.getRequest().getParameter("userId");
    String campaignId = context.getRequest().getParameter("campaignId");
    if (campaignId == null || "".equals(campaignId.trim())) {
      campaignId = (String) context.getRequest().getAttribute("campaignId");
    }
    UserGroupList selectList = new UserGroupList();
    boolean listDone = false;
    String displayFieldId = null;
    String type = (String) context.getRequest().getParameter("type");
    if (type != null && !"".equals(type)) {
      context.getRequest().setAttribute("type", type);
    }
    PagedListInfo userGroupSelectorInfo = this.getPagedListInfo(context, "UserGroupSelectorInfo");
    String tableName = context.getRequest().getParameter("table");
    HashMap selectedList = new HashMap();
    try {
      HashMap finalElementList = (HashMap) context.getSession().getAttribute("finalElements");
      if (context.getRequest().getParameter("previousSelection") != null) {
        int j = 0;
        StringTokenizer st = new StringTokenizer(context.getRequest().getParameter("previousSelection"), "|");
        StringTokenizer st1 = new StringTokenizer(context.getRequest().getParameter("previousSelectionDisplay"), "|");
        while (st.hasMoreTokens()) {
          selectedList.put(new Integer(st.nextToken()), st1.nextToken());
          j++;
        }
      } else {
        //get selected list from the session
        selectedList = (HashMap) context.getSession().getAttribute("selectedElements");
      }
      if (context.getRequest().getParameter("displayFieldId") != null) {
        displayFieldId = context.getRequest().getParameter("displayFieldId");
      }
      //Flush the selectedList if its a new selection
      if (context.getRequest().getParameter("flushtemplist") != null) {
        if (((String) context.getRequest().getParameter("flushtemplist")).equalsIgnoreCase("true")) {
          if (context.getSession().getAttribute("finalElements") != null && context.getRequest().getParameter("previousSelection") == null) {
            selectedList = (HashMap) ((HashMap) context.getSession().getAttribute("finalElements")).clone();
          }
        }
      }
      int rowCount = 1;
      while (context.getRequest().getParameter("hiddenelementid" + rowCount) != null) {
        int elementId = 0;
        String elementValue = "";
        elementId = Integer.parseInt(
            context.getRequest().getParameter("hiddenelementid" + rowCount));
        if (context.getRequest().getParameter("checkelement" + rowCount) != null) {
          if (context.getRequest().getParameter("elementvalue" + rowCount) != null) {
            elementValue = context.getRequest().getParameter(
                "elementvalue" + rowCount);
          }
          if (selectedList.get(new Integer(elementId)) == null) {
            selectedList.put(new Integer(elementId), elementValue);
          } else {
            selectedList.remove(new Integer(elementId));
            selectedList.put(new Integer(elementId), elementValue);
          }
        } else {
          selectedList.remove(new Integer(elementId));
        }
        rowCount++;
      }
      context.getSession().setAttribute("selectedElements", selectedList);
      context.getRequest().setAttribute("DisplayFieldId", displayFieldId);
      context.getRequest().setAttribute("Table", tableName);
      db = this.getConnection(context);
      if (userId != null && !"".equals(userId.trim()) && !"-1".equals(userId.trim())) {
        user = this.getUser(context, Integer.parseInt(userId));
        if (user.getSiteId() > -1) {
          selectList.setExclusiveToSite(true);
          selectList.setSiteId(user.getSiteId());
        } else {
          selectList.setIncludeAllSites(true);
        }
        context.getRequest().setAttribute("thisUser", user);
      }
      if (context.getRequest().getParameter("finalsubmit") != null) {
        if (((String) context.getRequest().getParameter("finalsubmit")).equalsIgnoreCase("true")) {
          context.getSession().removeAttribute("selectedElements");
          context.getSession().removeAttribute("finalElements");
          if (displayFieldId != null && !"".equals(displayFieldId)) {
            if (displayFieldId.equals("groups")) {
              return saveUserGroups(context, db, selectedList, userId);
            } else if (displayFieldId.equals("campaign")) {
              context.getRequest().setAttribute("selectedList", selectedList);
              if (type != null && !"".equals(type) && "active".equals(type)) {
                return "ReturnSaveCampaignUserGroupsOK";
              }
              return "SaveCampaignUserGroupsOK";
            }
          }
        }
      }
      selectList.setPagedListInfo(userGroupSelectorInfo);
      if (userId != null && !"".equals(userId.trim()) && !"-1".equals(userId.trim())) {
        selectList.setGetEnabledForUser(Constants.TRUE, Integer.parseInt(userId));
      }
      if (campaignId != null && !"".equals(campaignId.trim()) && !"-1".equals(campaignId.trim())) {
        context.getRequest().setAttribute("campaignId", campaignId);
        selectList.setGetEnabledForCampaign(Constants.TRUE, Integer.parseInt(campaignId));
      }
      if (campaignId != null && !"".equals(campaignId.trim())) {
        user = this.getUser(context, this.getUserId(context));
        selectList.setIncludeAllSites(false);
        selectList.setExclusiveToSite(false);
        selectList.setSiteId(user.getSiteId());
        selectList.setEnabled(Constants.TRUE);
      }
      selectList.buildList(db);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      context.getRequest().setAttribute("baseList", selectList);
      this.freeConnection(context, db);
    }
    return "PopLookupOK";
  }


  /**
   *  Description of the Method
   *
   * @param  context           Description of the Parameter
   * @param  db                Description of the Parameter
   * @param  finalSelections   Description of the Parameter
   * @param  userId            Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public String saveUserGroups(ActionContext context, Connection db, HashMap finalSelections, String userId) throws SQLException {
    try {
      UserGroupList oldGroupList = new UserGroupList();
      oldGroupList.setUserId(userId);
      oldGroupList.setIncludeAllSites(true);
      oldGroupList.buildList(db);
      oldGroupList.addUsersForGroups(db, finalSelections, Integer.parseInt(userId));
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    }
    return "SaveUserGroupsOK";
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandPopupSingleSelector(ActionContext context) {
    Connection db = null;
    UserGroupList groupList = null;

    try {
      db = this.getConnection(context);
      String nonSiteSpecific = context.getRequest().getParameter("nonSiteSpecific");
      String userId = (String) context.getRequest().getParameter("userId");
      String displayFieldId = (String) context.getRequest().getParameter("displayFieldId");
      String hiddenFieldId = (String) context.getRequest().getParameter("hiddenFieldId");
      String siteId = context.getRequest().getParameter("siteId");
      String includeAllSites = context.getRequest().getParameter("includeAllSites");
      User user = null;
      if (userId != null && !"".equals(userId.trim())) {
        user = this.getUser(context, Integer.parseInt(userId));
      } else {
        user = this.getUser(context, this.getUserId(context));
      }
      if (!isSiteAccessPermitted(context, siteId)) {
        return ("PermissionError");
      }
/*
      if (user.getSiteId() != -1 && user.getSiteId() != Integer.parseInt(siteId)) {
        return ("PermissionError");
      }
*/
      context.getRequest().setAttribute("thisUser", user);

      PagedListInfo oppPagedInfo = this.getPagedListInfo(context, "groupUsersListInfo");
      oppPagedInfo.setLink("UserGroups.do?command=PopupSingleSelector&userId=" + (userId != null? userId:"") + "&siteId="+(siteId !=null?siteId:"")+"&hiddenFieldId=" + hiddenFieldId + "&displayFieldId=" + displayFieldId);

      groupList = new UserGroupList();
      groupList.setPagedListInfo(oppPagedInfo);
      if (siteId != null && !"".equals(siteId.trim())) {
        groupList.setSiteId(siteId);
      }
      if (includeAllSites != null && !"".equals(includeAllSites.trim())) {
        groupList.setIncludeAllSites(includeAllSites);
      }
      groupList.setEnabled(Constants.TRUE);
      groupList.setBuildResources(true);
      groupList.buildList(db);

      context.getRequest().setAttribute("displayFieldId", displayFieldId);
      context.getRequest().setAttribute("hiddenFieldId", hiddenFieldId);
      context.getRequest().setAttribute("userId", userId);

      LookupList siteid = new LookupList(db, "lookup_site_id");
      siteid.addItem(-1, this.getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("SiteIdList", siteid);
      context.getRequest().setAttribute("groupList", groupList);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("ListSingleOK");
  }
}

