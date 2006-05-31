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

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.*;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.base.Notification;
import org.aspcfs.modules.communications.base.*;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.RequestUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

/**
 *  Description of the Class
 *
 * @author     partha
 * @created    March 10, 2006
 * @version    $Id: Exp$
 */
public final class CampaignUserGroups extends CFSModule {

  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    //Check to see if the user has a preference
    return "DefaultOK";
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandViewActive(ActionContext context) {
    if (!hasPermission(context, "campaign-dashboard-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    String campaignId = context.getRequest().getParameter("id");
    if (campaignId == null || "".equals(campaignId.trim())) {
      campaignId = (String) context.getRequest().getAttribute("id");
    }
    UserGroupList groups = null;
    UserGroupList completeGroupList = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    PagedListInfo pagedListInfo = this.getPagedListInfo(context, "campaignUserGroupListInfo");
    pagedListInfo.setLink("CampaignUserGroups.do?command=ViewActive&id=" + campaignId);
    try {
      db = this.getConnection(context);
      Campaign campaign = new Campaign();
      campaign.queryRecord(db, Integer.parseInt(campaignId));
      context.getRequest().setAttribute("campaign", campaign);

      groups = new UserGroupList();
      groups.setPagedListInfo(pagedListInfo);
      groups.setBuildResources(true);
      groups.setCampaignId(campaign.getId());
      groups.setIncludeAllSites(true);
      groups.buildList(db);
      context.getRequest().setAttribute("groupList", groups);
      completeGroupList = new UserGroupList();
      completeGroupList.setBuildResources(false);
      completeGroupList.setCampaignId(campaign.getId());
      completeGroupList.setIncludeAllSites(true);
      completeGroupList.buildList(db);
      context.getRequest().setAttribute("completeGroupList", completeGroupList);
      LookupList siteid = new LookupList(db, "lookup_site_id");
      siteid.addItem(-1, this.getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("SiteIdList", siteid);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace();
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "ViewActiveOK";
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandSaveUserGroups(ActionContext context) {
    if (!hasPermission(context, "campaign-dashboard-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    String campaignId = context.getRequest().getParameter("campaignId");
    if (campaignId == null || "".equals(campaignId.trim())) {
      campaignId = (String) context.getRequest().getAttribute("campaignId");
    }
    HashMap selectedList = (HashMap) context.getRequest().getAttribute("selectedList");
    try {
      db = this.getConnection(context);
      Campaign campaign = new Campaign();
      campaign.queryRecord(db, Integer.parseInt(campaignId));
      campaign.getUserGroupMaps().setCampaignId(campaign.getId());
      campaign.getUserGroupMaps().updateElements(selectedList);
      campaign.parseUserGroups(db);
      context.getRequest().setAttribute("Campaign", campaign);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace();
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
  public String executeCommandListGroups(ActionContext context) {
    if (!hasPermission(context, "campaign-dashboard-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    String groups = context.getRequest().getParameter("groups");
    try {
      db = this.getConnection(context);
      CampaignUserGroupMapList groupMaps = new CampaignUserGroupMapList();
      groupMaps.setElements(groups);
      context.getRequest().setAttribute("userGroupMaps", groupMaps.createMapOfElements());
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace();
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "ListGroupsOK";
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandDelete(ActionContext context) {
    if (!hasPermission(context, "campaign-dashboard-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    String campaignId = context.getRequest().getParameter("id");
    if (campaignId == null || "".equals(campaignId.trim())) {
      campaignId = (String) context.getRequest().getAttribute("id");
    }
    String userGroupId = context.getRequest().getParameter("userGroupId");
    try {
      db = this.getConnection(context);
      Campaign campaign = new Campaign();
      campaign.queryRecord(db, Integer.parseInt(campaignId));
      context.getRequest().setAttribute("Campaign", campaign);
      CampaignUserGroupMap map = new CampaignUserGroupMap(db, Integer.parseInt(campaignId), Integer.parseInt(userGroupId));
      map.delete(db);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace();
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return executeCommandViewActive(context);
  }
}

