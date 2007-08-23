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
import com.isavvix.tools.FileInfo;
import com.isavvix.tools.HttpMultiPartParser;
import com.zeroio.iteam.base.FileItem;
import com.zeroio.iteam.base.FileItemList;
import com.zeroio.iteam.base.FileItemVersion;
import com.zeroio.webutils.FileDownload;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.AccessType;
import org.aspcfs.modules.admin.base.AccessTypeList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.base.Notification;
import org.aspcfs.modules.communications.base.*;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.contacts.base.ContactList;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.RequestUtils;
import org.aspcfs.utils.StringUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

/**
 *  Actions for dealing with Campaigns in the Communications Module, including
 *  the dashboard and campaign center.
 *
 * @author     w. gillette
 * @created    October 18, 2001
 * @version    $Id: CampaignManager.java,v 1.4 2002/03/12 21:42:27 mrajkowski
 *      Exp $
 */
public final class CampaignManager extends CFSModule {

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
   *  Generates the Dashboard List
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   * @since           1.1
   */
  public String executeCommandDashboard(ActionContext context) {
    if (!hasPermission(context, "campaign-dashboard-view")) {
      return ("PermissionError");
    }
    this.resetPagedListInfo(context);
    Connection db = null;
    PagedListInfo pagedListInfo = this.getPagedListInfo(
        context, "CampaignDashboardListInfo");
    pagedListInfo.setLink("CampaignManager.do?command=Dashboard");
    try {
      db = this.getConnection(context);
      CampaignList campaignList = new CampaignList();
      campaignList.setPagedListInfo(pagedListInfo);
      campaignList.setCompleteOnly(true);
      if ("all".equals(pagedListInfo.getListView())) {
        campaignList.setOwnerIdRange(this.getUserRange(context));
        campaignList.setUserGroupUserId(this.getUserId(context));
        campaignList.setType(Campaign.GENERAL);
      } else if ("instant".equals(pagedListInfo.getListView())) {
        campaignList.setOwnerIdRange(this.getUserRange(context));
        campaignList.setUserGroupUserId(this.getUserId(context));
        campaignList.setType(Campaign.INSTANT);
      } else if ("trashed".equals(pagedListInfo.getListView())) {
        campaignList.setOwnerIdRange(this.getUserRange(context));
        campaignList.setIncludeOnlyTrashed(true);
      } else {
        campaignList.setType(Campaign.GENERAL);
        campaignList.setOwner(this.getUserId(context));
      }
      campaignList.buildList(db);
      context.getRequest().setAttribute("campList", campaignList);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    String submenu = context.getRequest().getParameter("submenu");
    if (submenu == null) {
      submenu = (String) context.getRequest().getAttribute("submenu");
    }
    if (submenu == null) {
      submenu = "Dashboard";
    }
    context.getRequest().setAttribute("submenu", submenu);
    addModuleBean(context, submenu, "Campaign Dashboard");
    return ("DashboardOK");
  }


  /**
   *  Generates a list of Incomplete Campaigns
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   * @since           1.1
   */
  public String executeCommandView(ActionContext context) {
    if (!hasPermission(context, "campaign-campaigns-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    PagedListInfo pagedListInfo = this.getPagedListInfo(
        context, "CampaignListInfo");
    pagedListInfo.setLink("CampaignManager.do?command=View");
    deletePagedListInfo(context, "CampaignCenterGroupInfo");
    try {
      db = this.getConnection(context);
      CampaignList campaignList = new CampaignList();
      campaignList.setPagedListInfo(pagedListInfo);
      campaignList.setIncompleteOnly(true);
      if ("all".equals(pagedListInfo.getListView())) {
        campaignList.setOwnerIdRange(this.getUserRange(context));
      } else {
        campaignList.setOwner(this.getUserId(context));
      }
      campaignList.buildList(db);
      context.getRequest().setAttribute("campList", campaignList);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    String submenu = context.getRequest().getParameter("submenu");
    if (submenu == null) {
      submenu = (String) context.getRequest().getAttribute("submenu");
    }
    if (submenu == null) {
      submenu = "ManageCampaigns";
    }
    context.getRequest().setAttribute("submenu", submenu);
    addModuleBean(context, submenu, "Build New Campaign");
    return ("ViewOK");
  }


  /**
   *  Generates the items needed to Add a New Campaign
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   * @since           1.1
   */
  public String executeCommandAdd(ActionContext context) {
    if (!hasPermission(context, "campaign-campaigns-add")) {
      return ("PermissionError");
    }
    if ("list".equals(context.getRequest().getParameter("source"))) {
      addModuleBean(context, "ManageCampaigns", "Build New Campaign");
    } else {
      addModuleBean(context, "Add Campaign", "Build New Campaign");
    }
    try {
      context.getSession().removeAttribute("CampaignCenterGroupInfo");
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    }
    return ("AddOK");
  }


  /**
   *  Processes the Campaign Insert form
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   * @since           1.26
   */
  public String executeCommandInsert(ActionContext context) {
    if (!hasPermission(context, "campaign-campaigns-add")) {
      return ("PermissionError");
    }
    Connection db = null;
    boolean recordInserted = false;
    boolean isValid = false;
    Campaign campaign = (Campaign) context.getFormBean();
    try {
      db = this.getConnection(context);
      campaign.setEnteredBy(getUserId(context));
      campaign.setModifiedBy(getUserId(context));
      isValid = this.validateObject(context, db, campaign);
      if (isValid) {
        recordInserted = campaign.insert(db);
      }
      if (recordInserted) {
        campaign = new Campaign(db, campaign.getId());
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "ManageCampaigns", "Build New Campaign");
    if (recordInserted) {
      context.getRequest().setAttribute("Campaign", campaign);
      return ("InsertOK");
    }
    return (executeCommandAdd(context));
  }


  /**
   *  Campaign Center: Details of a Campaign
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   * @since           1.26
   */
  public String executeCommandViewDetails(ActionContext context) {
    if (!hasPermission(context, "campaign-campaigns-view")) {
      return ("PermissionError");
    }
    addModuleBean(context, "ManageCampaigns", "Campaign Details");
    Connection db = null;
    Campaign campaign = null;
    String campaignId = context.getRequest().getParameter("id");
    try {
      db = this.getConnection(context);
      campaign = new Campaign(db, campaignId);
      campaign.setBuildGroupMaps(true);
      campaign.buildUserGroupMaps(db);
      if (!hasAuthority(context, campaign.getEnteredBy()) && !hasCampaignUserGroupAccess(db, campaign.getId(), this.getUserId(context))) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("Campaign", campaign);

      FileItemList files = new FileItemList();
      files.setLinkModuleId(Constants.COMMUNICATIONS_FILE_ATTACHMENTS);
      files.setLinkItemId(campaign.getId());
      files.buildList(db);
      context.getRequest().setAttribute("fileItemList", files);

      if ("true".equals(context.getRequest().getParameter("reset"))) {
        context.getSession().removeAttribute("CampaignCenterGroupInfo");
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("ViewDetailsOK");
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandModify(ActionContext context) {
    if (!hasPermission(context, "campaign-campaigns-edit")) {
      return ("PermissionError");
    }
    addModuleBean(context, "ManageCampaigns", "Edit Campaign Details");
    Connection db = null;
    Campaign campaign = null;
    String campaignId = context.getRequest().getParameter("id");
    try {
      db = this.getConnection(context);
      campaign = new Campaign();
      campaign.setBuildGroupMaps(true);
      campaign.queryRecord(db, Integer.parseInt(campaignId));
      if (!hasAuthority(context, campaign.getEnteredBy()) && !hasCampaignUserGroupAccess(db, campaign.getId(), this.getUserId(context))) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("Campaign", campaign);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("ModifyOK");
  }


  /**
   *  Campaign Center: Groups of a Campaign
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   * @since           1.26
   */
  public String executeCommandViewGroups(ActionContext context) {
    if (!hasPermission(context, "campaign-campaigns-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    Campaign campaign = null;
    addModuleBean(context, "Dashboard", "View Groups");
    String campaignId = context.getRequest().getParameter("id");
    try {
      db = this.getConnection(context);
      campaign = new Campaign(db, campaignId);
      campaign.setBuildGroupMaps(true);
      campaign.buildUserGroupMaps(db);
      if (!hasAuthority(context, campaign.getEnteredBy()) && !hasCampaignUserGroupAccess(db, campaign.getId(), this.getUserId(context))) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("Campaign", campaign);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("ViewGroupsOK");
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  public String executeCommandPreviewGroups(ActionContext context) {
    if (!hasPermission(context, "campaign-campaigns-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    Campaign campaign = null;
    try {
      db = this.getConnection(context);
      //Show the campaign name
      campaign = new Campaign(db, context.getRequest().getParameter("id"));
      campaign.setBuildGroupMaps(true);
      campaign.buildUserGroupMaps(db);
      if (!hasAuthority(context, campaign.getEnteredBy()) && !hasCampaignUserGroupAccess(db, campaign.getId(), this.getUserId(context))) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("Campaign", campaign);
      //Load the criteria for the contacts query
      SearchCriteriaList thisSCL = new SearchCriteriaList(
          db, context.getRequest().getParameter("scl"));
      context.getRequest().setAttribute("SCL", thisSCL);
      //Reset the pagedList if this is the first time being opened
      if ("true".equals(context.getRequest().getParameter("reset"))) {
        context.getSession().removeAttribute("CampaignCenterPreviewInfo");
      }
      PagedListInfo pagedListInfo = this.getPagedListInfo(
          context, "CampaignCenterPreviewInfo");
      pagedListInfo.setLink(
          "CampaignManager.do?command=PreviewGroups&id=" + campaign.getId() + "&scl=" + thisSCL.getId() + "&popup=true");
      //Generate the contacts for this page
      ContactList contacts = new ContactList();
      contacts.setScl(
          thisSCL, campaign.getEnteredBy(), this.getUserRange(
          context, campaign.getEnteredBy()));
      contacts.setPagedListInfo(pagedListInfo);
      contacts.setCheckExcludedFromCampaign(campaign.getId());
      contacts.setIncludeAllSites(true);
      contacts.setBuildDetails(true);
      contacts.setBuildTypes(false);
      AccessTypeList accessTypeList = this.getSystemStatus(context).getAccessTypeList(db, AccessType.GENERAL_CONTACTS);
      contacts.setGeneralContactAccessTypes(accessTypeList);
      contacts.buildList(db);
      context.getRequest().setAttribute("ContactList", contacts);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("PreviewGroupsOK");
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  public String executeCommandToggleRecipient(ActionContext context) {
    if (!hasPermission(context, "campaign-campaigns-edit")) {
      return ("PermissionError");
    }
    Connection db = null;
    String campaignId = context.getRequest().getParameter("id");
    String contactId = context.getRequest().getParameter("contactId");
    try {
      db = this.getConnection(context);
      //TODO: A whole contact doesn't need to be created for this, it would
      //be nice to optimize this
      Contact thisContact = new Contact();
      thisContact.setBuildDetails(false);
      thisContact.setBuildTypes(false);
      thisContact.setId(Integer.parseInt(contactId));
      thisContact.build(db);
      thisContact.checkExcludedFromCampaign(db, Integer.parseInt(campaignId));
      thisContact.toggleExcluded(db, Integer.parseInt(campaignId));
      context.getRequest().setAttribute(
          "recipientText",
          thisContact.getExcludedFromCampaign() ? "No" : "Yes");
    } catch (Exception e) {
      System.out.println(e.toString());
      return ("ToggleERROR");
    } finally {
      this.freeConnection(context, db);
    }
    return ("ToggleOK");
  }


  /**
   *  Campaign Center: Message of a Campaign
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   * @since           1.26
   */
  public String executeCommandViewMessage(ActionContext context) {
    if (!hasPermission(context, "campaign-campaigns-view")) {
      return ("PermissionError");
    }
    addModuleBean(context, "ManageCampaigns", "Build New Campaign");
    Connection db = null;
    String campaignId = context.getRequest().getParameter("id");
    Campaign campaign = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      db = this.getConnection(context);
      campaign = new Campaign(db, campaignId);
      campaign.setBuildGroupMaps(true);
      campaign.buildUserGroupMaps(db);
      if (!hasAuthority(context, campaign.getEnteredBy()) && !hasCampaignUserGroupAccess(db, campaign.getId(), this.getUserId(context))) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("Campaign", campaign);
      //Build my messages
      MessageList messageList = new MessageList();
      messageList.setOwner(this.getUserId(context));
      messageList.setPersonalId(MessageList.IGNORE_PERSONAL);
      messageList.buildList(db);
      //Message is not in my messages, so build hierarchy of messages
      if (campaign.getMessageId() > 0 && !messageList.hasId(
          campaign.getMessageId())) {
        messageList.clear();
        messageList.setOwner(-1);
        messageList.setControlledHierarchyOnly(
            true, this.getUserRange(context));
        messageList.buildList(db);
        context.getRequest().setAttribute("listView", "all");
      } else {
        context.getRequest().setAttribute("listView", "my");
      }
      messageList.addItem(0, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("MessageList", messageList);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("ViewMessageOK");
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandPreviewMessage(ActionContext context) {
    if (!hasPermission(context, "campaign-campaigns-view")) {
      return ("PermissionError");
    }
    addModuleBean(context, "Dashboard", "Build New Campaign");
    Connection db = null;
    Campaign campaign = null;
    String campaignId = context.getRequest().getParameter("id");
    try {
      db = this.getConnection(context);
      campaign = new Campaign(db, campaignId);
      campaign.setBuildGroupMaps(true);
      campaign.buildUserGroupMaps(db);
      if (!hasAuthority(context, campaign.getEnteredBy()) && !hasCampaignUserGroupAccess(db, campaign.getId(), this.getUserId(context))) {
        return ("PermissionError");
      }
      FileItemList documents = new FileItemList();
      documents.setLinkModuleId(Constants.COMMUNICATIONS_FILE_ATTACHMENTS);
      documents.setLinkItemId(Integer.parseInt(campaignId));
      documents.buildList(db);
      context.getRequest().setAttribute("FileItemList", documents);
      context.getRequest().setAttribute("Campaign", campaign);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("PreviewMessageOK");
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandDownloadMessage(ActionContext context) {

    if (!(hasPermission(context, "campaign-dashboard-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    String itemId = (String) context.getRequest().getParameter("fid");
    String version = (String) context.getRequest().getParameter("ver");
    FileItem thisItem = null;

    Connection db = null;
    int id = Integer.parseInt(context.getRequest().getParameter("id"));
    try {
      db = getConnection(context);
      thisItem = new FileItem(
          db, Integer.parseInt(itemId), id, Constants.COMMUNICATIONS_FILE_ATTACHMENTS);
      if (version != null) {
        thisItem.buildVersionList(db);
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    //Start the download

    try {
      if (version == null) {
        FileItem itemToDownload = thisItem;
        itemToDownload.setEnteredBy(this.getUserId(context));
        String filePath = this.getPath(context, "communications") + getDatePath(
            itemToDownload.getModified()) + itemToDownload.getFilename();
        FileDownload fileDownload = new FileDownload();
        fileDownload.setFullPath(filePath);
        fileDownload.setDisplayName(itemToDownload.getClientFilename());
        if (fileDownload.fileExists()) {
          fileDownload.sendFile(context);
          //Get a db connection now that the download is complete
          db = getConnection(context);
          itemToDownload.updateCounter(db);
        } else {
          db = null;
          System.err.println(
              "CampaignDocuments-> Trying to send a file that does not exist");
          context.getRequest().setAttribute(
              "actionError", systemStatus.getLabel(
              "object.validation.actionError.downloadDoesNotExist"));
          return (executeCommandPreviewMessage(context));
        }
      } else {
        FileItemVersion itemToDownload = thisItem.getVersion(
            Double.parseDouble(version));
        itemToDownload.setEnteredBy(this.getUserId(context));
        String filePath = this.getPath(context, "campaign") + getDatePath(
            itemToDownload.getModified()) + itemToDownload.getFilename();
        FileDownload fileDownload = new FileDownload();
        fileDownload.setFullPath(filePath);
        fileDownload.setDisplayName(itemToDownload.getClientFilename());
        if (fileDownload.fileExists()) {
          fileDownload.sendFile(context);
          //Get a db connection now that the download is complete
          db = getConnection(context);
          itemToDownload.updateCounter(db);
        } else {
          db = null;
          System.err.println(
              "CampaignMessage Documents -> Trying to send a file that does not exist");
          context.getRequest().setAttribute(
              "actionError", systemStatus.getLabel(
              "object.validation.actionError.downloadDoesNotExist"));
          return (executeCommandPreviewMessage(context));
        }
      }
    } catch (java.net.SocketException se) {
      //User either canceled the download or lost connection
      if (System.getProperty("DEBUG") != null) {
        System.out.println(se.toString());
      }
    } catch (Exception e) {
      errorMessage = e;
      System.out.println(e.toString());
    } finally {
      if (db != null) {
        this.freeConnection(context, db);
      }
    }

    if (errorMessage == null) {
      return ("-none-");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      addModuleBean(context, "Dashboard", "");
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandViewAttachment(ActionContext context) {
    if (!hasPermission(context, "campaign-campaigns-view")) {
      return ("PermissionError");
    }
    addModuleBean(context, "ManageCampaigns", "Build New Campaign");
    SystemStatus systemStatus = this.getSystemStatus(context);
    Connection db = null;
    String campaignId = context.getRequest().getParameter("id");
    Campaign campaign = null;
    try {
      db = this.getConnection(context);
      campaign = new Campaign(db, campaignId);
      campaign.setBuildGroupMaps(true);
      campaign.buildUserGroupMaps(db);
      if (!hasAuthority(context, campaign.getEnteredBy()) && !hasCampaignUserGroupAccess(db, campaign.getId(), this.getUserId(context))) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("Campaign", campaign);

      SurveyList surveyList = new SurveyList();
      surveyList.setEnteredBy(this.getUserId(context));
      surveyList.buildList(db);
      //Survey is not in my surveys, so build hierarchy of surveys
      if (campaign.getSurveyId() > 0 && !surveyList.hasId(
          campaign.getSurveyId())) {
        surveyList.clear();
        surveyList.setEnteredBy(-1);
        surveyList.setEnteredByIdRange(this.getUserRange(context));
        surveyList.buildList(db);
        context.getRequest().setAttribute("listView", "all");
      } else {
        context.getRequest().setAttribute("listView", "my");
      }
      surveyList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("SurveyList", surveyList);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("ViewAttachmentOK");
  }


  /**
   *  Campaign Center: Schedule of a Campaign
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   * @since           1.26
   */
  public String executeCommandViewSchedule(ActionContext context) {
    if (!hasPermission(context, "campaign-campaigns-view")) {
      return ("PermissionError");
    }
    addModuleBean(context, "ManageCampaigns", "Build New Campaign");
    Connection db = null;
    String campaignId = context.getRequest().getParameter("id");
    Campaign campaign = null;
    try {
      db = this.getConnection(context);
      campaign = new Campaign(db, campaignId);
      campaign.setBuildGroupMaps(true);
      campaign.buildUserGroupMaps(db);
      if (!hasAuthority(context, campaign.getEnteredBy()) && !hasCampaignUserGroupAccess(db, campaign.getId(), this.getUserId(context))) {
        return ("PermissionError");
      }
      //If request redirected due to form error, make sure to set them
      String activeDate = context.getRequest().getParameter("activeDate");
      String activeDateTimeZone = context.getRequest().getParameter(
          "activeDateTimeZone");
      String sendMethodId = context.getRequest().getParameter("sendMethodId");
      if (activeDate != null && activeDateTimeZone != null) {
        campaign.setActiveDateTimeZone(activeDateTimeZone);
        campaign.setTimeZoneForDateFields(
            context.getRequest(), activeDate, "activeDate");
      }
      if (sendMethodId != null) {
        campaign.setSendMethodId(
            Integer.parseInt(sendMethodId));
      }
      context.getRequest().setAttribute("Campaign", campaign);
      //Prepare the list of available delivery types
      LookupList deliveryList = new LookupList(db, "lookup_delivery_options");
      context.getRequest().setAttribute("DeliveryList", deliveryList);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("ViewScheduleOK");
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandPreviewSchedule(ActionContext context) {
    if (!hasPermission(context, "campaign-campaigns-view")) {
      return ("PermissionError");
    }
    addModuleBean(context, "Dashboard", "Preview Schedule");
    Connection db = null;
    Campaign campaign = null;
    String campaignId = context.getRequest().getParameter("id");
    try {
      db = this.getConnection(context);
      campaign = new Campaign(db, campaignId);
      campaign.setBuildGroupMaps(true);
      campaign.buildUserGroupMaps(db);
      if (!hasAuthority(context, campaign.getEnteredBy()) && !hasCampaignUserGroupAccess(db, campaign.getId(), this.getUserId(context))) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("Campaign", campaign);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("PreviewScheduleOK");
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandPreviewSurvey(ActionContext context) {
    if (!hasPermission(context, "campaign-campaigns-surveys-view")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    addModuleBean(context, "ManageCampaigns", "Build New Campaign");
    Connection db = null;
    Survey thisSurvey = null;

    try {
      db = this.getConnection(context);
      int surveyId = Integer.parseInt(context.getRequest().getParameter("id"));
      if (surveyId > 0) {
        thisSurvey = new Survey(db, surveyId);
      } else {
        thisSurvey = new Survey();
      }
      context.getRequest().setAttribute("Survey", thisSurvey);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      return ("PreviewSurveyOK");
    } else {
      return ("PreviewSurveyMISSING");
    }
  }


  /**
   *  Campaign Center: Add groups to the Campaign
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   * @since           1.26
   */
  public String executeCommandAddGroups(ActionContext context) {
    if (!(hasPermission(context, "campaign-campaigns-edit"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    Connection db = null;
    addModuleBean(context, "ManageCampaigns", "Campaign: Add Groups");
    if (context.getRequest().getParameter("listView") == null) {
      //TODO... reset the list somehow....
      //context.getRequest().setAttribute("resetList", "true");
    }
    PagedListInfo pagedListInfo = this.getPagedListInfo(
        context, "CampaignCenterGroupInfo");
    pagedListInfo.setLink("CampaignManager.do?command=AddGroups");
    String campaignId = context.getRequest().getParameter("id");
    Campaign campaign = null;
    try {
      db = this.getConnection(context);
      campaign = new Campaign(db, campaignId);
      context.getRequest().setAttribute("Campaign", campaign);
      //Complete List
      SearchCriteriaListList sclList = new SearchCriteriaListList();
      if ("all".equals(pagedListInfo.getListView())) {
        sclList.setOwnerIdRange(getUserRange(context));
      } else {
        sclList.setOwner(getUserId(context));
      }
      sclList.setPagedListInfo(pagedListInfo);
      sclList.buildList(db);
      context.getRequest().setAttribute("sclList", sclList);
      //Selected List
      SearchCriteriaListList selectedList = new SearchCriteriaListList();
      selectedList.setCampaignId(campaignId);
      selectedList.buildList(db);
      processListCheckBoxes(selectedList, context);
      context.getRequest().setAttribute("selectedList", selectedList);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      if (!hasAuthority(context, campaign.getEnteredBy())) {
        return ("PermissionError");
      }
      return ("AddGroupsOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Campaign Center: Processes the selected groups
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   * @since           1.26
   */
  public String executeCommandInsertGroups(ActionContext context) {

    if (!(hasPermission(context, "campaign-campaigns-edit"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;
    boolean recordInserted = false;
    Campaign campaign = null;

    String campaignId = context.getRequest().getParameter("id");

    try {
      db = this.getConnection(context);
      campaign = new Campaign(db, campaignId);
      if (!hasAuthority(context, campaign.getEnteredBy())) {
        return ("PermissionError");
      }
      campaign.setGroups(context.getRequest());
      campaign.setModifiedBy(this.getUserId(context));
      recordInserted = campaign.insertGroups(db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (recordInserted) {
        context.getRequest().setAttribute("Campaign", campaign);
        return ("InsertGroupsOK");
      } else {
        return (executeCommandAddGroups(context));
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Campaign Center: Generates a list of Messages to choose from
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   * @since           1.26
   */
  public String executeCommandAddMessage(ActionContext context) {

    if (!(hasPermission(context, "campaign-campaigns-edit"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;
    Campaign campaign = null;

    addModuleBean(context, "ManageCampaigns", "Campaign: Add Message");

    String campaignId = context.getRequest().getParameter("id");

    try {
      db = this.getConnection(context);
      campaign = new Campaign(db, campaignId);
      context.getRequest().setAttribute("Campaign", campaign);

      MessageList messageList = new MessageList();
      messageList.setOwner(this.getUserId(context));
      messageList.setPersonalId(MessageList.IGNORE_PERSONAL);
      messageList.buildList(db);
      context.getRequest().setAttribute("MessageList", messageList);

    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (!hasAuthority(context, campaign.getEnteredBy())) {
        return ("PermissionError");
      }
      return ("AddMessageOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandAddAttachment(ActionContext context) {

    if (!(hasPermission(context, "campaign-campaigns-edit"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;
    Campaign campaign = null;
    addModuleBean(context, "ManageCampaigns", "Campaign: Add Attachment");

    String campaignId = context.getRequest().getParameter("id");

    try {
      db = this.getConnection(context);
      campaign = new Campaign(db, campaignId);
      context.getRequest().setAttribute("Campaign", campaign);

    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (!hasAuthority(context, campaign.getEnteredBy())) {
        return ("PermissionError");
      }
      return ("AddMessageOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Campaign Center: Saves the selected message to the Campaign
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   * @since           1.26
   */
  public String executeCommandInsertMessage(ActionContext context) {
    if (!(hasPermission(context, "campaign-campaigns-edit"))) {
      return ("PermissionError");
    }
    Connection db = null;
    int resultCount = 0;
    Campaign campaign = null;

    String campaignId = context.getRequest().getParameter("id");
    String messageId = context.getRequest().getParameter("messageId");

    if (messageId != null) {
      try {
        db = this.getConnection(context);
        campaign = new Campaign(db, campaignId);
        if (!hasAuthority(context, campaign.getEnteredBy())) {
          return ("PermissionError");
        }
        campaign.setMessageId(Integer.parseInt(messageId));
        campaign.setModifiedBy(this.getUserId(context));
        resultCount = campaign.updateMessage(db);
      } catch (Exception e) {
        context.getRequest().setAttribute("Error", e);
        return ("SystemError");
      } finally {
        this.freeConnection(context, db);
      }
    }
    if (resultCount == 1) {
      return ("InsertMessageOK");
    } else {
      return executeCommandAddMessage(context);
    }
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandInsertAttachment(ActionContext context) {
    if (!hasPermission(context, "campaign-campaigns-edit")) {
      return ("PermissionError");
    }
    Connection db = null;
    int resultCount = 0;
    Campaign campaign = null;
    int surveyId = -1;

    String campaignId = context.getRequest().getParameter("id");

    try {
      //TODO: surveyId is not being set!!!
      surveyId = Integer.parseInt(
          context.getRequest().getParameter("surveyId"));
      db = this.getConnection(context);
      campaign = new Campaign(db, campaignId);
      if (!hasAuthority(context, campaign.getEnteredBy())) {
        return ("PermissionError");
      }
      campaign.setSurveyId(surveyId);
      campaign.setModifiedBy(this.getUserId(context));
      resultCount = campaign.updateSurvey(db);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (resultCount == 1) {
      return ("InsertAttachmentOK");
    } else {
      return executeCommandAddAttachment(context);
    }
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandUpdateAddressRequest(ActionContext context) {

    if (!(hasPermission(context, "campaign-campaign-contact-updater-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;
    int resultCount = 0;
    Campaign campaign = null;
    int surveyId = -1;
    String addAddressRequest = null;

    String campaignId = context.getRequest().getParameter("id");

    try {
      addAddressRequest = context.getRequest().getParameter("insert");
      db = this.getConnection(context);
      campaign = new Campaign(db, campaignId);
      if (!hasAuthority(context, campaign.getEnteredBy())) {
        return ("PermissionError");
      }
      surveyId = Survey.getAddressSurveyId(db);
      campaign.setSurveyId(surveyId);
      campaign.setModifiedBy(this.getUserId(context));
      campaign.setHasAddressRequest(addAddressRequest);
      resultCount = campaign.updateAddressRequest(db);
      context.getRequest().setAttribute("Campaign", campaign);
      context.getRequest().setAttribute(
          "addressRequestChanged", ((resultCount == 1) ? "YES" : "NO"));
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (resultCount == 1) {
        return ("InsertAttachmentOK");
      } else {
        return executeCommandAddAttachment(context);
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Campaign Center: Saves the schedule details to the Campaign
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   * @since           1.26
   */
  public String executeCommandInsertSchedule(ActionContext context) {
    if (!hasPermission(context, "campaign-campaigns-edit")) {
      return ("PermissionError");
    }
    int resultCount = -1;
    boolean isValid = false;
    
    String campaignId = context.getRequest().getParameter("id");
    String activeDate = context.getRequest().getParameter("activeDate");
    String activeDateTimeZone = context.getRequest().getParameter(
        "activeDateTimeZone");
    
    Campaign campaign = new Campaign();
    if (!StringUtils.hasText(activeDate)) {
      //active date has not been specified. return form back to user
      campaign.getErrors().put("activeDateError", 
          this.getSystemStatus(context).getLabel("object.validation.required"));
      processErrors(context, campaign.getErrors());
      return executeCommandViewSchedule(context);
    }
    
    Connection db = null;
    try {
      db = this.getConnection(context);
      campaign.queryRecord(db, Integer.parseInt(campaignId));
      if (!hasAuthority(context, campaign.getEnteredBy())) {
        return ("PermissionError");
      }
      campaign.setActiveDateTimeZone(activeDateTimeZone);
      campaign.setTimeZoneForDateFields(
          context.getRequest(), activeDate, "activeDate");
      if (context.getRequest().getParameter("active") != null) {
        campaign.setActive(context.getRequest().getParameter("active"));
      }

      campaign.setModifiedBy(this.getUserId(context));
      campaign.setSendMethodId(
          Integer.parseInt(context.getRequest().getParameter("sendMethodId")));
      isValid = this.validateObject(context, db, campaign);
      if (isValid) {
        resultCount = campaign.updateSchedule(db);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (resultCount == 1) {
      return ("InsertScheduleOK");
    }
    return executeCommandViewSchedule(context);
  }


  /**
   *  Campaign Center: Processes the groups and removes them from a Campaign
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   * @since           1.26
   */
  public String executeCommandRemoveGroups(ActionContext context) {

    if (!(hasPermission(context, "campaign-campaigns-edit"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;
    boolean recordDeleted = false;
    Campaign campaign = null;

    String campaignId = context.getRequest().getParameter("id");

    try {
      db = this.getConnection(context);
      campaign = new Campaign(db, campaignId);
      if (!hasAuthority(context, campaign.getEnteredBy())) {
        return "PermissionError";
      }
      campaign.setGroups(context.getRequest());
      campaign.setModifiedBy(this.getUserId(context));
      recordDeleted = campaign.deleteGroups(db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (recordDeleted) {
        context.getRequest().setAttribute("Campaign", campaign);
        return ("RemoveGroupsOK");
      } else {
        return ("RemoveGroupsOK");
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Campaign Center: Updates the details of a Campaign
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   * @since           1.26
   */
  public String executeCommandUpdate(ActionContext context) {
    if (!hasPermission(context, "campaign-campaigns-edit")) {
      return ("PermissionError");
    }
    Connection db = null;
    int resultCount = 0;
    Campaign campaign = null;
    try {
      db = this.getConnection(context);
      campaign = (Campaign) context.getFormBean();
      int enteredBy = Campaign.queryEnteredBy(db, campaign.getId());
      if (hasAuthority(context, enteredBy) && (this.validateObject(
          context, db, campaign))) {
        campaign.setModifiedBy(getUserId(context));
        campaign.setBuildGroupMaps(true);
        campaign.buildUserGroupMaps(db);
        resultCount = campaign.updateDetails(db);
      } else {
        resultCount = -1;
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (resultCount == -1) {
      return ("PermissionError");
    } else if (resultCount == 1) {
      return ("UpdateDetailsOK");
    } else {
      context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
      return ("UserError");
    }
  }


  /**
   *  From the Dashboard, processes a Cancel Campaign request
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   * @since           1.26
   */
  public String executeCommandCancel(ActionContext context) {

    if (!(hasPermission(context, "campaign-campaigns-edit"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;
    int resultCount = 0;

    Campaign campaign = null;
    Campaign previousCampaign = null;
    String id = context.getRequest().getParameter("id");

    try {
      db = this.getConnection(context);
      campaign = new Campaign(db, id);
      previousCampaign = new Campaign(db, id);
      if (!hasAuthority(context, campaign.getEnteredBy())) {
        return "PermissionError";
      }
      campaign.setModifiedBy(getUserId(context));
      resultCount = campaign.cancel(db);
      campaign.queryRecord(db, campaign.getId());
      if (resultCount == 1) {
        campaign.setContacts(
            db, campaign.getEnteredBy(), this.getUserRange(
            context, campaign.getEnteredBy()));
        this.processUpdateHook(context, previousCampaign, campaign);
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (resultCount == 1) {
        return ("CancelOK");
      } else {
        context.getRequest().setAttribute(
            "Error",
            "<p><b>This campaign could not be canceled because it has already started processing or has completed.</b></p>" +
            "<p>Once the server starts sending the messages, the campaign cannot be stopped.</p>" +
            "<p><a href=\"CampaignManager.do?command=Dashboard\">Back to Dashboard</a></p>");
        return ("UserError");
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  From the Incomplete Campaign list or the Campaign center, processes an
   *  Activate Campaign request
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   * @since           1.26
   */
  public String executeCommandActivate(ActionContext context) {
    if (!hasPermission(context, "campaign-campaigns-edit")) {
      return ("PermissionError");
    }
    Connection db = null;
    int resultCount = 0;
    Campaign campaign = null;
    Campaign previousCampaign = null;
    String id = context.getRequest().getParameter("id");
    String modified = context.getRequest().getParameter("modified");
    try {
      db = this.getConnection(context);
      campaign = new Campaign(db, id);
      previousCampaign = new Campaign(db, id);
      if ((!hasAuthority(context, campaign.getEnteredBy())) || (campaign.getDeliveryType() == Notification.BROADCAST)) {
        return "PermissionError";
      }
      campaign.setModifiedBy(getUserId(context));
      campaign.setModified(modified);
      campaign.setServerName(RequestUtils.getServerUrl(context.getRequest()));
      resultCount = campaign.activate(
          db, campaign.getEnteredBy(), this.getUserRange(
          context, campaign.getEnteredBy()));
      campaign.queryRecord(db, campaign.getId());
      if (resultCount == 1) {
        campaign.setContacts(
            db, campaign.getEnteredBy(), this.getUserRange(
            context, campaign.getEnteredBy()));
        processUpdateHook(context, previousCampaign, campaign);
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (resultCount == 1) {
      return ("ActivateOK");
    } else {
      context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
      return ("UserError");
    }
  }


  /**
   *  Read in an activated Campaign -- ready only, with stats.
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   * @since           1.26
   */
  public String executeCommandDetails(ActionContext context) {

    if (!(hasPermission(context, "campaign-campaigns-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;

    String id = context.getRequest().getParameter("id");
    Campaign campaign = null;

    try {
      db = this.getConnection(context);
      campaign = new Campaign(db, id);
      campaign.setBuildGroupMaps(true);
      campaign.buildUserGroupMaps(db);
      if (!hasAuthority(context, campaign.getEnteredBy()) && !hasCampaignUserGroupAccess(db, campaign.getId(), this.getUserId(context))) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("Campaign", campaign);

      int surveyId = -1;
      if ((surveyId = ActiveSurvey.getId(
          db, campaign.getId(), Constants.SURVEY_REGULAR)) > 0) {
        ActiveSurvey thisSurvey = new ActiveSurvey(db, surveyId);
        context.getRequest().setAttribute("ActiveSurvey", thisSurvey);
      }
      int addressSurveyId = -1;
      if ((addressSurveyId = ActiveSurvey.getId(
          db, campaign.getId(), Constants.SURVEY_ADDRESS_REQUEST)) > 0) {
        ActiveSurvey thisSurvey = new ActiveSurvey(db, addressSurveyId);
        context.getRequest().setAttribute("AddressSurvey", thisSurvey);
      }
      context.getRequest().setAttribute(
          "User", this.getUser(context, this.getUserId(context)));
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Dashboard", "Campaign: Details");
    return ("DetailsOK");
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandViewResults(ActionContext context) {

    if (!(hasPermission(context, "campaign-campaigns-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;
    Campaign campaign = null;

    String id = context.getRequest().getParameter("id");
    if ("true".equals(context.getRequest().getParameter("reset"))) {
      context.getSession().removeAttribute("SurveyQuestionListInfo");
    }
    PagedListInfo pagedListInfo = this.getPagedListInfo(
        context, "SurveyQuestionListInfo");
    pagedListInfo.setLink("CampaignManager.do?command=ViewResults&id=" + id);

    this.deletePagedListInfo(context, "YesResponseDetailsListInfo");
    this.deletePagedListInfo(context, "NoResponseDetailsListInfo");
    this.deletePagedListInfo(context, "CampaignRecipientInfo");

    try {
      db = this.getConnection(context);
      campaign = new Campaign(db, id);
      campaign.setBuildGroupMaps(true);
      campaign.buildUserGroupMaps(db);
      if (!hasAuthority(context, campaign.getEnteredBy()) && !hasCampaignUserGroupAccess(db, campaign.getId(), this.getUserId(context))) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("Campaign", campaign);

      int surveyId = -1;
      if ((surveyId = ActiveSurvey.getId(
          db, campaign.getId(), Constants.SURVEY_REGULAR)) > 0) {
        ActiveSurveyQuestionList thisList = new ActiveSurveyQuestionList();
        thisList.setActiveSurveyId(surveyId);
        thisList.setBuildResults(true);
        thisList.buildList(db);
        context.getRequest().setAttribute("SurveyQuestionList", thisList);
      }
      int addressSurveyId = -1;
      if ((addressSurveyId = ActiveSurvey.getId(
          db, campaign.getId(), Constants.SURVEY_ADDRESS_REQUEST)) > 0) {
        SurveyResponseList thisYesList = new SurveyResponseList();
        thisYesList.setSurveyId(addressSurveyId);
        thisYesList.setAddressUpdated(SurveyResponse.ADDRESS_UPDATED);
        thisYesList.buildList(db);
        context.getRequest().setAttribute(
            "yesAddressUpdateResponseList", thisYesList);

        SurveyResponseList thisNoList = new SurveyResponseList();
        thisNoList.setSurveyId(addressSurveyId);
        thisNoList.setOnlyNotUpdated(true);
        thisNoList.buildList(db);
        context.getRequest().setAttribute(
            "noAddressUpdateResponseList", thisNoList);

        RecipientList recipients = new RecipientList();
        recipients.setCampaignId(campaign.getId());
        recipients.setBuildContact(true);
        recipients.setOnlyResponded(true);
        recipients.setSurveyId(addressSurveyId);
        recipients.buildList(db);
        context.getRequest().setAttribute("recipientList", recipients);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Dashboard", "Campaign: Results");
    return ("ResultsOK");
  }


  /**
   *  View the survey response based on the recipients.
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandViewResponse(ActionContext context) {

    if (!(hasPermission(context, "campaign-campaigns-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;
    Campaign campaign = null;

    String id = context.getRequest().getParameter("id");
    if ("true".equals(context.getRequest().getParameter("reset"))) {
      context.getSession().removeAttribute("SurveyResponseListInfo");
      context.getSession().removeAttribute("AddressUpdateResponseListInfo");
    }
    PagedListInfo surveyResponseListInfo = this.getPagedListInfo(
        context, "SurveyResponseListInfo");
    surveyResponseListInfo.setLink(
        "CampaignManager.do?command=ViewResponse&id=" + id);

    PagedListInfo addressUpdateResponseListInfo = this.getPagedListInfo(
        context, "AddressUpdateResponseListInfo");
    addressUpdateResponseListInfo.setLink(
        "CampaignManager.do?command=ViewResponse&id=" + id);

    try {
      db = this.getConnection(context);
      campaign = new Campaign(db, id);
      campaign.setBuildGroupMaps(true);
      campaign.buildUserGroupMaps(db);
      if (!hasAuthority(context, campaign.getEnteredBy()) && !hasCampaignUserGroupAccess(db, campaign.getId(), this.getUserId(context))) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("Campaign", campaign);

      int surveyId = -1;
      if ((surveyId = ActiveSurvey.getId(
          db, campaign.getId(), Constants.SURVEY_REGULAR)) > 0) {
        SurveyResponseList thisList = new SurveyResponseList();
        thisList.setSurveyId(surveyId);
        thisList.setPagedListInfo(surveyResponseListInfo);
        thisList.buildList(db);
        context.getRequest().setAttribute("SurveyResponseList", thisList);
      }
      int addressSurveyId = -1;
      if ((addressSurveyId = ActiveSurvey.getId(
          db, campaign.getId(), Constants.SURVEY_ADDRESS_REQUEST)) > 0) {
        SurveyResponseList thisList = new SurveyResponseList();
        thisList.setSurveyId(addressSurveyId);
        thisList.setPagedListInfo(addressUpdateResponseListInfo);
        thisList.buildList(db);
        context.getRequest().setAttribute(
            "AddressUpdateResponseList", thisList);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "Dashboard", "Campaign: Response");
    return ("ResponseOK");
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandAddressUpdateResponseDetails(ActionContext context) {
    if (!hasPermission(context, "campaign-campaigns-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    Campaign campaign = null;
    //Parameters
    String id = context.getRequest().getParameter("id");
    String section = context.getRequest().getParameter("section");
    //Paged List
    if ("true".equals(context.getRequest().getParameter("reset"))) {
      context.getSession().removeAttribute("ResponseDetailsListInfo");
    }
    try {
      db = this.getConnection(context);
      //Load the campaign
      campaign = new Campaign(db, id);
      campaign.setBuildGroupMaps(true);
      campaign.buildUserGroupMaps(db);
      if (!hasAuthority(context, campaign.getEnteredBy()) && !hasCampaignUserGroupAccess(db, campaign.getId(), this.getUserId(context))) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("Campaign", campaign);
      //Load the answers for this contact
      int addressSurveyId = -1;
      if ((addressSurveyId = ActiveSurvey.getId(
          db, campaign.getId(), Constants.SURVEY_ADDRESS_REQUEST)) > 0) {
        if ((section == null) || (String.valueOf(
            SurveyResponse.ADDRESS_UPDATED)).equals(section)) {
          PagedListInfo yesResponseDetailsListInfo = this.getPagedListInfo(
              context, "YesResponseDetailsListInfo");
          yesResponseDetailsListInfo.setLink(
              "CampaignManager.do?command=AddressUpdateResponseDetails&id=" + id + "&section=" + SurveyResponse.ADDRESS_UPDATED);
          SurveyResponseList thisYesList = new SurveyResponseList();
          thisYesList.setSurveyId(addressSurveyId);
          thisYesList.setPagedListInfo(yesResponseDetailsListInfo);
          thisYesList.setAddressUpdated(SurveyResponse.ADDRESS_UPDATED);
          thisYesList.buildList(db);
          context.getRequest().setAttribute(
              "yesAddressUpdateResponseList", thisYesList);
        }
        if ((section == null) || (String.valueOf(SurveyResponse.ADDRESS_VALID)).equals(
            section)) {
          PagedListInfo noResponseDetailsListInfo = this.getPagedListInfo(
              context, "NoResponseDetailsListInfo");
          noResponseDetailsListInfo.setLink(
              "CampaignManager.do?command=AddressUpdateResponseDetails&id=" + id + "&section=" + SurveyResponse.ADDRESS_VALID);
          SurveyResponseList thisNoList = new SurveyResponseList();
          thisNoList.setSurveyId(addressSurveyId);
          thisNoList.setPagedListInfo(noResponseDetailsListInfo);
          thisNoList.setOnlyNotUpdated(true);
          thisNoList.buildList(db);
          context.getRequest().setAttribute(
              "noAddressUpdateResponseList", thisNoList);
        }
        if ((section == null) || (String.valueOf(
            SurveyResponse.ADDRESS_NO_RESPONSE)).equals(section)) {
          PagedListInfo recipientListInfo = this.getPagedListInfo(
              context, "CampaignRecipientInfo");
          recipientListInfo.setLink(
              "CampaignManager.do?command=AddressUpdateResponseDetails&id=" + id + "&section=" + SurveyResponse.ADDRESS_NO_RESPONSE);
          RecipientList recipients = new RecipientList();
          recipients.setCampaignId(campaign.getId());
          recipients.setBuildContact(true);
          recipients.setPagedListInfo(recipientListInfo);
          recipients.setOnlyResponded(true);
          recipients.setSurveyId(addressSurveyId);
          recipients.buildList(db);
          context.getRequest().setAttribute("recipientList", recipients);
        }
      }
      context.getRequest().setAttribute("section", section);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("AddressResponseDetailsOK");
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandResponseDetails(ActionContext context) {
    if (!hasPermission(context, "campaign-campaigns-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    Campaign campaign = null;
    //Parameters
    String id = context.getRequest().getParameter("id");
    String contactId = context.getRequest().getParameter("contactId");
    String responseId = context.getRequest().getParameter("responseId");
    //Paged List
    if ("true".equals(context.getRequest().getParameter("reset"))) {
      context.getSession().removeAttribute("ResponseDetailsListInfo");
    }
    PagedListInfo pagedListInfo = this.getPagedListInfo(
        context, "ResponseDetailsListInfo");
    pagedListInfo.setLink(
        "CampaignManager.do?command=ResponseDetails&id=" + id);
    try {
      db = this.getConnection(context);
      //Load the campaign
      campaign = new Campaign(db, id);
      campaign.setBuildGroupMaps(true);
      campaign.buildUserGroupMaps(db);
      if (!hasAuthority(context, campaign.getEnteredBy()) && !hasCampaignUserGroupAccess(db, campaign.getId(), this.getUserId(context))) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("Campaign", campaign);
      //Load the contact
      Contact thisContact = new Contact(db, Integer.parseInt(contactId));
      context.getRequest().setAttribute("contact", thisContact);
      //Load the answers for this contact
      int surveyId = -1;
      if ((surveyId = ActiveSurvey.getId(
          db, campaign.getId(), Constants.SURVEY_REGULAR)) > 0) {
        ActiveSurveyQuestionList thisList = new ActiveSurveyQuestionList();
        thisList.setActiveSurveyId(surveyId);
        thisList.buildList(db);
        thisList.buildResponse(
            db, Integer.parseInt(contactId), Integer.parseInt(responseId));
        context.getRequest().setAttribute("ResponseDetails", thisList);
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Dashboard", "Campaign: Response Details");
    return ("ResponseDetailsOK");
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandPreviewRecipients(ActionContext context) {
    if (!hasPermission(context, "campaign-campaigns-view")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    Connection db = null;
    Campaign campaign = null;
    String id = context.getRequest().getParameter("id");
    if ("true".equals(context.getRequest().getParameter("reset"))) {
      context.getSession().removeAttribute("CampaignDashboardRecipientInfo");
    }
    PagedListInfo pagedListInfo = this.getPagedListInfo(
        context, "CampaignDashboardRecipientInfo");
    pagedListInfo.setLink(
        "CampaignManager.do?command=PreviewRecipients&id=" + id);
    try {
      db = this.getConnection(context);
      campaign = new Campaign(db, id);
      campaign.setBuildGroupMaps(true);
      campaign.buildUserGroupMaps(db);
      if (!hasAuthority(context, campaign.getEnteredBy()) && !hasCampaignUserGroupAccess(db, campaign.getId(), this.getUserId(context))) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("Campaign", campaign);

      RecipientList recipients = new RecipientList();
      recipients.setCampaignId(campaign.getId());
      recipients.setBuildContact(true);
      recipients.setPagedListInfo(pagedListInfo);
      pagedListInfo.setSearchCriteria(recipients, context);
      recipients.buildList(db);
      context.getRequest().setAttribute("RecipientList", recipients);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Dashboard", "Campaign: Recipients");
    return ("PreviewRecipientsOK");
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandShowItems(ActionContext context) {

    if (!(hasPermission(context, "campaign-campaigns-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;
    ActiveSurveyQuestionItemList itemList = null;

    int questionId = Integer.parseInt(
        context.getRequest().getParameter("questionId"));

    try {
      db = this.getConnection(context);
      itemList = new ActiveSurveyQuestionItemList();
      itemList.setQuestionId(questionId);
      itemList.buildList(db);
      itemList.updateResponse(db, questionId);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {

      context.getRequest().setAttribute("ItemList", itemList);
      return ("ItemListOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Show all contacts who responded to a specific item in the Item List
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandShowItemDetails(ActionContext context) {

    if (!(hasPermission(context, "campaign-campaigns-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;
    ActiveSurveyAnswerItemList itemDetails = null;

    if ("true".equals(context.getRequest().getParameter("reset"))) {
      context.getSession().removeAttribute("CommentListInfo");
    }
    int itemId = Integer.parseInt(context.getRequest().getParameter("itemId"));
    String questionId = context.getRequest().getParameter("questionId");
    PagedListInfo pagedListInfo = this.getPagedListInfo(
        context, "ItemDetailsListInfo");
    pagedListInfo.setLink(
        "CampaignManager.do?command=ShowItemDetails&itemId=" + itemId + "&questionId=" + questionId);

    try {
      db = this.getConnection(context);
      itemDetails = new ActiveSurveyAnswerItemList();
      itemDetails.setItemId(itemId);
      itemDetails.setPagedListInfo(pagedListInfo);
      itemDetails.buildList(db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      context.getRequest().setAttribute("ItemDetails", itemDetails);
      return ("ItemDetailsOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandConfirmDelete(ActionContext context) {
    if (!hasPermission(context, "campaign-campaigns-delete")) {
      return ("PermissionError");
    }
    Connection db = null;
    Exception errorMessage = null;
    DependencyList dependencies = null;
    Campaign campaign = null;
    String passedId = context.getRequest().getParameter("id");
    HtmlDialog htmlDialog = new HtmlDialog();

    try {
      SystemStatus systemStatus = this.getSystemStatus(context);
      db = this.getConnection(context);
      campaign = new Campaign(db, passedId);
      if (!hasAuthority(context, campaign.getEnteredBy())) {
        return "PermissionError";
      }
      dependencies = campaign.processDependencies(db);
      dependencies.setSystemStatus(systemStatus);
      htmlDialog.addMessage(
          systemStatus.getLabel("confirmdelete.caution") + "\n" + dependencies.getHtmlString());
      htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
      htmlDialog.setHeader(systemStatus.getLabel("confirmdelete.header"));
      htmlDialog.addButton(
          systemStatus.getLabel("global.button.delete"), "javascript:window.location.href='CampaignManager.do?command=Trash&action=delete&id=" + passedId + "&forceDelete=true'");
      htmlDialog.addButton(
          systemStatus.getLabel("button.cancel"), "javascript:parent.window.close()");
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      context.getSession().setAttribute("Dialog", htmlDialog);
      return ("ConfirmDeleteOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Processes a Campaign Delete request
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   * @since           1.26
   */
  public String executeCommandDelete(ActionContext context) {
    if (!hasPermission(context, "campaign-campaigns-delete")) {
      return ("PermissionError");
    }
    boolean recordDeleted = false;
    Campaign campaign = null;
    String passedId = context.getRequest().getParameter("id");
    Connection db = null;
    boolean activeCampaign = false;
    try {
      db = this.getConnection(context);
      campaign = new Campaign(db, passedId);
      activeCampaign = campaign.getActive();
      if (!hasAuthority(context, campaign.getEnteredBy())) {
        return "PermissionError";
      }
      recordDeleted = campaign.delete(db, this.getDbNamePath(context));
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace();
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (recordDeleted) {
      if (activeCampaign) {
        context.getRequest().setAttribute(
            "refreshUrl", "CampaignManager.do?command=Dashboard");
        return ("DeleteActiveCampaignOK");
      } else {
        return ("DeleteOK");
      }
    } else {
      processErrors(context, campaign.getErrors());
      return (executeCommandView(context));
    }
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandTrash(ActionContext context) {
    if (!hasPermission(context, "campaign-campaigns-delete")) {
      return ("PermissionError");
    }
    boolean recordUpdated = false;
    Campaign campaign = null;
    String passedId = context.getRequest().getParameter("id");
    Connection db = null;
    boolean activeCampaign = false;
    try {
      db = this.getConnection(context);
      campaign = new Campaign(db, passedId);
      activeCampaign = campaign.getActive();
      if (!hasAuthority(context, campaign.getEnteredBy())) {
        return "PermissionError";
      }
      recordUpdated = campaign.updateStatus(db, true, this.getUserId(context));
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace();
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (recordUpdated) {
      if (activeCampaign) {
        context.getRequest().setAttribute(
            "refreshUrl", "CampaignManager.do?command=Dashboard");
        return ("DeleteActiveCampaignOK");
      } else {
        return ("DeleteOK");
      }
    } else {
      processErrors(context, campaign.getErrors());
      return (executeCommandView(context));
    }
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandDownload(ActionContext context) {
    if (!hasPermission(context, "campaign-campaigns-view")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;

    String linkItemId = (String) context.getRequest().getParameter("id");
    String fileId = (String) context.getRequest().getParameter("fid");
    FileItem itemToDownload = null;

    Connection db = null;
    try {
      db = getConnection(context);
      itemToDownload = new FileItem(
          db, Integer.parseInt(fileId), Integer.parseInt(linkItemId), Constants.COMMUNICATIONS_DOCUMENTS);
    } catch (Exception e) {
      errorMessage = e;
      e.printStackTrace(System.out);
    } finally {
      this.freeConnection(context, db);
    }

    //Start the download
    try {
      String filePath = this.getPath(context, "communications") + getDatePath(
          itemToDownload.getModified()) + itemToDownload.getFilename();

      FileDownload fileDownload = new FileDownload();
      fileDownload.setFullPath(filePath);
      fileDownload.setDisplayName(itemToDownload.getClientFilename());
      if (fileDownload.fileExists()) {
        fileDownload.sendFile(context);
        //Get a db connection now that the download is complete
        db = getConnection(context);
        itemToDownload.updateCounter(db);
      } else {
        System.err.println("PMF-> Trying to send a file that does not exist");
      }
    } catch (java.net.SocketException se) {
      //User either canceled the download or lost connection
    } catch (Exception e) {
      errorMessage = e;
      System.out.println(e.toString());
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      return ("-none-");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Builds comments for a SurveyQuestion Question could be either a Open Ended
   *  or Quantitative with comments
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandShowComments(ActionContext context) {
    Connection db = null;
    String questionId = context.getRequest().getParameter("questionId");
    String type = context.getRequest().getParameter("type");
    if ("true".equals(context.getRequest().getParameter("reset"))) {
      context.getSession().removeAttribute("CommentListInfo");
    }
    PagedListInfo pagedListInfo = this.getPagedListInfo(
        context, "CommentListInfo");
    pagedListInfo.setLink(
        "CampaignManager.do?command=ShowComments&questionId=" + questionId + "&type=" + type);
    try {
      SurveyAnswerList answerList = new SurveyAnswerList();
      answerList.setQuestionId(Integer.parseInt(questionId));
      answerList.setHasComments(Constants.TRUE);
      answerList.setPagedListInfo(pagedListInfo);
      db = getConnection(context);
      answerList.buildList(db);
      context.getRequest().setAttribute("SurveyAnswerList", answerList);
      context.getRequest().setAttribute(
          "SurveyContactList", answerList.getContacts());
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("PopupCommentsOK");
  }


  /**
   *  Description of the Method
   *
   * @param  selectedList  Description of the Parameter
   * @param  context       Description of the Parameter
   */
  private static void processListCheckBoxes(SearchCriteriaListList selectedList, ActionContext context) {
    int count = 0;
    while (context.getRequest().getParameter("select" + (++count)) != null) {
      SearchCriteriaList scl = new SearchCriteriaList();
      scl.setId(context.getRequest().getParameter("select" + count));
      if ("on".equalsIgnoreCase(
          context.getRequest().getParameter("select" + count + "check"))) {
        if (!selectedList.containsItem(scl)) {
          selectedList.add(scl);
        }
      } else {
        selectedList.removeItem(scl);
      }
    }
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandViewAttachmentsOverview(ActionContext context) {
    if (!hasPermission(context, "campaign-campaigns-view")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    addModuleBean(context, "ManageCampaigns", "Build New Campaign");
    Connection db = null;
    String campaignId = context.getRequest().getParameter("id");
    Campaign campaign = null;
    try {
      db = this.getConnection(context);
      campaign = new Campaign(db, campaignId);
      campaign.setBuildGroupMaps(true);
      campaign.buildUserGroupMaps(db);
      if (!hasAuthority(context, campaign.getEnteredBy()) && !hasCampaignUserGroupAccess(db, campaign.getId(), this.getUserId(context))) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("Campaign", campaign);
      if (campaign.hasSurvey()) {
        Survey survey = new Survey(db, campaign.getSurveyId());
        context.getRequest().setAttribute("Survey", survey);
      }
      FileItemList files = new FileItemList();
      files.setLinkModuleId(Constants.COMMUNICATIONS_FILE_ATTACHMENTS);
      files.setLinkItemId(campaign.getId());
      files.buildList(db);
      context.getRequest().setAttribute("fileItemList", files);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("ViewAttachmentsOverviewOK");
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandManageFileAttachments(ActionContext context) {

    if (!(hasPermission(context, "campaign-campaigns-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    addModuleBean(context, "ManageCampaigns", "Build New Campaign");
    Connection db = null;

    String campaignId = context.getRequest().getParameter("id");
    Campaign campaign = null;

    try {
      db = this.getConnection(context);
      campaign = new Campaign(db, campaignId);
      campaign.setBuildGroupMaps(true);
      campaign.buildUserGroupMaps(db);
      if (!hasAuthority(context, campaign.getEnteredBy()) && !hasCampaignUserGroupAccess(db, campaign.getId(), this.getUserId(context))) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("Campaign", campaign);

      FileItemList files = new FileItemList();
      files.setLinkModuleId(Constants.COMMUNICATIONS_FILE_ATTACHMENTS);
      files.setLinkItemId(campaign.getId());
      files.buildList(db);
      context.getRequest().setAttribute("fileItemList", files);

    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("ManageFileAttachmentsOK");
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandUploadFile(ActionContext context) {
    if (!(hasPermission(context, "campaign-campaigns-edit"))) {
      return ("PermissionError");
    }
    Connection db = null;
    boolean isValid = false;
    boolean recordInserted = false;
    try {
      String filePath = this.getPath(context, "communications");
      //Process the form data
      HttpMultiPartParser multiPart = new HttpMultiPartParser();
      multiPart.setUsePathParam(false);
      multiPart.setUseUniqueName(true);
      multiPart.setUseDateForFolder(true);
      multiPart.setExtensionId(getUserId(context));
      HashMap parts = multiPart.parseData(context.getRequest(), filePath);
      db = getConnection(context);
      String id = context.getRequest().getParameter("id");
      //String id = (String) parts.get("id");
      String subject = "Attachment";
      //String subject = (String) parts.get("subject");
      //String folderId = (String) parts.get("folderId");
      Campaign campaign = new Campaign(db, Integer.parseInt(id));
      campaign.setBuildGroupMaps(true);
      campaign.buildUserGroupMaps(db);
      if (!hasAuthority(context, campaign.getEnteredBy()) && !hasCampaignUserGroupAccess(db, campaign.getId(), this.getUserId(context))) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("Campaign", campaign);

      if ((Object) parts.get("id" + id) instanceof FileInfo) {
        //Update the database with the resulting file
        FileInfo newFileInfo = (FileInfo) parts.get("id" + id);

        FileItem thisItem = new FileItem();
        thisItem.setLinkModuleId(Constants.COMMUNICATIONS_FILE_ATTACHMENTS);
        thisItem.setLinkItemId(campaign.getId());
        thisItem.setEnteredBy(getUserId(context));
        thisItem.setModifiedBy(getUserId(context));
        thisItem.setFolderId(-1);
        thisItem.setSubject(subject);
        thisItem.setClientFilename(newFileInfo.getClientFileName());
        thisItem.setFilename(newFileInfo.getRealFilename());
        thisItem.setVersion(1.0);
        thisItem.setSize(newFileInfo.getSize());
        isValid = this.validateObject(context, db, thisItem);
        if (isValid) {
          recordInserted = thisItem.insert(db);
        }
      } else {
        recordInserted = false;
        HashMap errors = new HashMap();
        SystemStatus systemStatus = this.getSystemStatus(context);
        errors.put(
            "actionError", systemStatus.getLabel(
            "object.validation.incorrectFileName"));
        processErrors(context, errors);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      freeConnection(context, db);
    }
    return (executeCommandManageFileAttachments(context));
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandRemoveFile(ActionContext context) {
    if (!(hasPermission(context, "campaign-campaigns-edit"))) {
      return ("PermissionError");
    }
    Connection db = null;
    boolean recordDeleted = false;
    try {
      String itemId = (String) context.getRequest().getParameter("fid");
      String campaignId = (String) context.getRequest().getParameter("id");
      db = getConnection(context);
      Campaign campaign = new Campaign(db, Integer.parseInt(campaignId));
      if (!hasAuthority(context, campaign.getEnteredBy())) {
        return "PermissionError";
      }
      FileItem thisItem = new FileItem(
          db, Integer.parseInt(itemId), Integer.parseInt(campaignId), Constants.COMMUNICATIONS_FILE_ATTACHMENTS);
      recordDeleted = thisItem.delete(
          db, this.getPath(context, "communications"));
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      freeConnection(context, db);
    }
    return (executeCommandManageFileAttachments(context));
  }


  /**
   *  Export a Campaign Report
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandExportReport(ActionContext context) {
    if (!hasPermission(context, "campaign-campaigns-view")) {
      return ("PermissionError");
    }
    String campaignId = (String) context.getRequest().getParameter("id");
    //setup file stuff
    String filePath = this.getPath(context, "campaign") + getDatePath(
        new java.util.Date());
    CampaignReport thisReport = new CampaignReport();
    thisReport.setFilePath(filePath);
    thisReport.setCampaignId(Integer.parseInt(campaignId));
    thisReport.setEnteredBy(getUserId(context));
    thisReport.setModifiedBy(getUserId(context));
    Connection db = null;
    try {
      db = getConnection(context);
      thisReport.build(db);
      thisReport.saveAndInsert(db);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      freeConnection(context, db);
    }
    return "ExportReportOK";
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandDownloadFile(ActionContext context) {
    if (!(hasPermission(context, "campaign-campaigns-view"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    String itemId = (String) context.getRequest().getParameter("fid");
    String campaignId = (String) context.getRequest().getParameter("id");
    FileItem thisItem = null;
    Connection db = null;
    try {
      db = getConnection(context);
      Campaign campaign = new Campaign(db, Integer.parseInt(campaignId));
      if (!hasAuthority(context, campaign.getEnteredBy())) {
        return "PermissionError";
      }
      thisItem = new FileItem(
          db, Integer.parseInt(itemId), Integer.parseInt(campaignId), Constants.COMMUNICATIONS_FILE_ATTACHMENTS);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    //Start the download
    try {
      FileItem itemToDownload = thisItem;
      itemToDownload.setEnteredBy(this.getUserId(context));
      String filePath = this.getPath(context, "communications") + getDatePath(
          itemToDownload.getModified()) + itemToDownload.getFilename();
      FileDownload fileDownload = new FileDownload();
      fileDownload.setFullPath(filePath);
      fileDownload.setDisplayName(itemToDownload.getClientFilename());
      if (fileDownload.fileExists()) {
        fileDownload.sendFile(context);
        //Get a db connection now that the download is complete
        db = getConnection(context);
        itemToDownload.updateCounter(db);
      } else {
        db = null;
        System.err.println(
            "CampaignManager-> Trying to send a file that does not exist");
        context.getRequest().setAttribute(
            "actionError", systemStatus.getLabel(
            "object.validation.actionError.downloadDoesNotExist"));
        return (executeCommandView(context));
      }
    } catch (java.net.SocketException se) {
      //User either canceled the download or lost connection
      if (System.getProperty("DEBUG") != null) {
        System.out.println(se.toString());
      }
    } catch (Exception e) {
      errorMessage = e;
      System.out.println(e.toString());
    } finally {
      if (db != null) {
        this.freeConnection(context, db);
      }
    }

    if (errorMessage == null) {
      return ("-none-");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      addModuleBean(context, "ManageCampaigns", "");
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandMessageJSList(ActionContext context) {
    Connection db = null;
    String listView = context.getRequest().getParameter("listView");
    try {
      MessageList messageList = new MessageList();

      //check if a filter is selected
      if ("all".equals(listView)) {
        messageList.setAllMessages(
            true, this.getUserId(context), this.getUserRange(context));
      } else if ("hierarchy".equals(listView)) {
        messageList.setControlledHierarchyOnly(
            true, this.getUserRange(context));
        messageList.setPersonalId(this.getUserId(context));
      } else if ("personal".equals(listView)) {
        messageList.setOwner(this.getUserId(context));
        messageList.setRuleId(AccessType.PERSONAL);
        messageList.setPersonalId(MessageList.IGNORE_PERSONAL);
      } else {
        messageList.setOwner(this.getUserId(context));
        messageList.setPersonalId(MessageList.IGNORE_PERSONAL);
      }

      db = this.getConnection(context);
      messageList.buildList(db);
      context.getRequest().setAttribute("messageList", messageList);
    } catch (SQLException e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    String commandName =  (String)context.getRequest().getAttribute("commandName");
    if(commandName != null && !"".equals(commandName) && commandName.equals("executeCommandPrepareQuickMessage")) {      
      return getReturn(context, "HomePageMessageJSList");
    }
    return getReturn(context, "MessageJSList");
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandSurveyJSList(ActionContext context) {
    Connection db = null;
    String listView = context.getRequest().getParameter("listView");
    try {
      SurveyList surveyList = new SurveyList();
      if ("all".equals(listView)) {
        surveyList.setEnteredByIdRange(this.getUserRange(context));
      } else {
        surveyList.setEnteredBy(this.getUserId(context));
      }
      db = this.getConnection(context);
      surveyList.buildList(db);
      context.getRequest().setAttribute("surveyList", surveyList);
    } catch (SQLException e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("SurveyJSListOK");
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandBroadcastAuthenticationForm(ActionContext context) {
    String campaignId = context.getRequest().getParameter("id");
    Campaign campaign = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      campaign = new Campaign(db, Integer.parseInt(campaignId));
      context.getRequest().setAttribute("Campaign", campaign);
      Message message = new Message(db, campaign.getMessageId());
      context.getRequest().setAttribute("Message", message);
    } catch (SQLException e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "BroadcastAuthenticationFormOK";
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandBroadcastCampaign(ActionContext context) {
    if (!hasPermission(context, "campaign-campaigns-edit")) {
      return ("PermissionError");
    }
    String password = context.getRequest().getParameter("broadcastPassword");
    String campaignId = context.getRequest().getParameter("id");
    String modified = context.getRequest().getParameter("modified");
    Campaign campaign = null;
    Connection db = null;
    boolean validUser = false;
    int resultCount = -1;
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      db = this.getConnection(context);
      int userId = this.getUserId(context);
      validUser = Campaign.authenticateForBroadcast(db, userId, password);
      if (validUser) {
        campaign = new Campaign(db, Integer.parseInt(campaignId));
        context.getRequest().setAttribute("Campaign", campaign);
        if (!hasAuthority(context, campaign.getEnteredBy())) {
          return "PermissionError";
        }
        campaign.setModifiedBy(userId);
        campaign.setModified(modified);
        campaign.setServerName(
            RequestUtils.getServerUrl(context.getRequest()));
        resultCount = campaign.activate(
            db, campaign.getEnteredBy(),
            this.getUserRange(context, campaign.getEnteredBy()));
      }
    } catch (SQLException e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (!validUser) {
      context.getRequest().setAttribute(
          "Error", systemStatus.getLabel(
          "communications.campaign.broadCastInvalidPasswordMessage"));
      return executeCommandBroadcastAuthenticationForm(context);
    }
    if (resultCount == 1) {
      context.getRequest().setAttribute("finalsubmit", "true");
    } else {
      context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
      return ("UserError");
    }
    return "BroadcastCampaignOK";
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandRestart(ActionContext context) {
    if (!hasPermission(context, "campaign-campaigns-edit")) {
      return ("PermissionError");
    }
    addModuleBean(context, "ManageCampaigns", "Restart Campaign");
    Connection db = null;
    Campaign campaign = null;
    String campaignId = context.getRequest().getParameter("id");
    if (campaignId == null || "".equals(campaignId.trim())) {
      campaignId = (String) context.getRequest().getAttribute("id");
    }
    try {
      db = this.getConnection(context);
      campaign = new Campaign(db, campaignId);
      campaign.setBuildGroupMaps(true);
      campaign.buildUserGroupMaps(db);
      if (!hasAuthority(context, campaign.getEnteredBy()) && !hasCampaignUserGroupAccess(db, campaign.getId(), this.getUserId(context))) {
        return ("PermissionError");
      }
      campaign.restartCampaign(db);
      context.getRequest().setAttribute("Campaign", campaign);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("ResetOK");
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandAddRecipient(ActionContext context) {
    if (!hasPermission(context, "campaign-campaigns-edit")) {
      return ("PermissionError");
    }
    boolean recipientAdded = false;
    Exception errorMessage = null;
    Connection db = null;
    Campaign campaign = null;
    Campaign oldCampaign = null;
    String contactId = context.getRequest().getParameter("contactId");
    String allowDuplicates = context.getRequest().getParameter("allowDuplicates");
    String id = context.getRequest().getParameter("id");
    try {
      db = this.getConnection(context);
      campaign = new Campaign(db, id);
      oldCampaign = new Campaign(db, id);
      campaign.setBuildGroupMaps(true);
      campaign.buildUserGroupMaps(db);
      if (!hasAuthority(context, campaign.getEnteredBy()) && !hasCampaignUserGroupAccess(db, campaign.getId(), this.getUserId(context))) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("Campaign", campaign);

      RecipientList recipients = new RecipientList();
      recipients.setCampaignId(campaign.getId());
      recipients.setBuildContact(true);
      recipients.buildList(db);
      if (allowDuplicates != null && !"".equals(allowDuplicates.trim())) {
        recipients.setAllowDuplicates(allowDuplicates);
      }
      recipientAdded = recipients.addRecipient(db, contactId);
      if (recipientAdded) {
        String str = executeCommandRestart(context);
        campaign = new Campaign(db, id);
        Contact contact = new Contact(db, Integer.parseInt(contactId));
        ContactList contacts = new ContactList();
        contacts.add(contact);
        campaign.setContactList(contacts);
        this.processUpdateHook(context, oldCampaign, campaign);
      }
      context.getRequest().setAttribute("recipientAdded", String.valueOf(recipientAdded));
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("AddRecipientOK");
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   */
  private void resetPagedListInfo(ActionContext context) {
    this.deletePagedListInfo(context, "CampaignListInfo");
    this.deletePagedListInfo(context, "CampaignCenterPreviewInfo");
    this.deletePagedListInfo(context, "CampaignCenterGroupInfo");
    this.deletePagedListInfo(context, "SurveyQuestionListInfo");
    this.deletePagedListInfo(context, "SurveyResponseListInfo");
    this.deletePagedListInfo(context, "ResponseDetailsListInfo");
    this.deletePagedListInfo(context, "CampaignDashboardRecipientInfo");
    this.deletePagedListInfo(context, "ItemDetailsListInfo");
    this.deletePagedListInfo(context, "CommentListInfo");
    this.deletePagedListInfo(context, "campaignUserGroupListInfo");
  }
}

