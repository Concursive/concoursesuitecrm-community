package com.darkhorseventures.cfsmodule;

import javax.servlet.*;
import javax.servlet.http.*;
import org.theseus.actions.*;
import java.sql.*;
import java.util.*;
import com.darkhorseventures.utils.*;
import com.darkhorseventures.cfsbase.*;
import com.darkhorseventures.webutils.*;
import com.zeroio.iteam.base.*;
import com.zeroio.webutils.*;
import com.isavvix.tools.*;
import java.io.*;


/**
 *  Actions for dealing with Campaigns in the Communications Module, including
 *  the dashboard and campaign center.
 *
 *@author     w. gillette
 *@created    October 18, 2001
 *@version    $Id: CampaignManager.java,v 1.4 2002/03/12 21:42:27 mrajkowski Exp
 *      $
 */
public final class CampaignManager extends CFSModule {

  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    //Check to see if the user has a preference
    return "DefaultOK";
    //Otherwise go to the specified module...
    /*
     *  if (hasPermission(context, "campaign-dashboard-view")) {
     *  return executeCommandDashboard(context);
     *  } else {
     *  return executeCommandView(context);
     *  }
     */
  }


  /**
   *  Generates the Dashboard List
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since           1.1
   */
  public String executeCommandDashboard(ActionContext context) {

    if (!(hasPermission(context, "campaign-dashboard-view"))) {
      return ("PermissionError");
    }

    Connection db = null;
    Exception errorMessage = null;

    PagedListInfo pagedListInfo = this.getPagedListInfo(context, "CampaignDashboardListInfo");
    pagedListInfo.setLink("CampaignManager.do?command=Dashboard");

    try {
      db = this.getConnection(context);
      CampaignList campaignList = new CampaignList();
      campaignList.setPagedListInfo(pagedListInfo);
      campaignList.setCompleteOnly(true);
      if ("all".equals(pagedListInfo.getListView())) {
        campaignList.setOwnerIdRange(this.getUserRange(context));
      } else {
        campaignList.setOwner(this.getUserId(context));
      }
      campaignList.buildList(db);
      context.getRequest().setAttribute("campList", campaignList);
    } catch (Exception e) {
      errorMessage = e;
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

    if (errorMessage == null) {
      return ("DashboardOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Generates a list of Incomplete Campaigns
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since           1.1
   */
  public String executeCommandView(ActionContext context) {

    if (!(hasPermission(context, "campaign-campaigns-view"))) {
      return ("PermissionError");
    }

    Connection db = null;
    Exception errorMessage = null;

    PagedListInfo pagedListInfo = this.getPagedListInfo(context, "CampaignListInfo");
    pagedListInfo.setLink("/CampaignManager.do?command=View");

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
      errorMessage = e;
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

    if (errorMessage == null) {
      return ("ViewOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Generates the items needed to Add a New Campaign
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since           1.1
   */
  public String executeCommandAdd(ActionContext context) {

    if (!(hasPermission(context, "campaign-campaigns-add"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;

    addModuleBean(context, "ManageCampaigns", "Build New Campaign");

    try {
      context.getSession().removeAttribute("CampaignCenterGroupInfo");
    } catch (Exception e) {
      errorMessage = e;
    }

    if (errorMessage == null) {
      return ("AddOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Processes the Campaign Insert form
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since           1.26
   */
  public String executeCommandInsert(ActionContext context) {

    if (!(hasPermission(context, "campaign-campaigns-add"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;
    boolean recordInserted = false;

    Campaign campaign = (Campaign) context.getFormBean();

    try {
      db = this.getConnection(context);
      campaign.setEnteredBy(getUserId(context));
      campaign.setModifiedBy(getUserId(context));
      recordInserted = campaign.insert(db);
      campaign = new Campaign(db, "" + campaign.getId());
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "ManageCampaigns", "Build New Campaign");

    if (errorMessage == null) {
      if (recordInserted) {
        context.getRequest().setAttribute("Campaign", campaign);
        return ("InsertOK");
      } else {
        return (executeCommandAdd(context));
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Campaign Center: Details of a Campaign
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since           1.26
   */
  public String executeCommandViewDetails(ActionContext context) {

    if (!(hasPermission(context, "campaign-campaigns-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    addModuleBean(context, "ManageCampaigns", "Campaign Details");
    Connection db = null;

    String campaignId = context.getRequest().getParameter("id");

    try {
      db = this.getConnection(context);
      Campaign campaign = new Campaign(db, campaignId);
      context.getRequest().setAttribute("Campaign", campaign);

      if ("true".equals(context.getRequest().getParameter("reset"))) {
        context.getSession().removeAttribute("CampaignCenterGroupInfo");
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      return ("ViewDetailsOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandModify(ActionContext context) {

    if (!(hasPermission(context, "campaign-campaigns-edit"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    addModuleBean(context, "ManageCampaigns", "Edit Campaign Details");
    Connection db = null;

    String campaignId = context.getRequest().getParameter("id");

    try {
      db = this.getConnection(context);
      Campaign campaign = new Campaign(db, campaignId);
      context.getRequest().setAttribute("Campaign", campaign);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      return ("ModifyOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Campaign Center: Groups of a Campaign
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since           1.26
   */
  public String executeCommandViewGroups(ActionContext context) {

    if (!(hasPermission(context, "campaign-campaigns-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    addModuleBean(context, "ManageCampaigns", "Build New Campaign");
    Connection db = null;

    String campaignId = context.getRequest().getParameter("id");

    try {
      db = this.getConnection(context);
      Campaign campaign = new Campaign(db, campaignId);
      context.getRequest().setAttribute("Campaign", campaign);

      SearchCriteriaListList sclList = new SearchCriteriaListList();
      sclList.setCampaignId(campaign.getId());
      sclList.buildList(db);
      context.getRequest().setAttribute("sclList", sclList);

    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      return ("ViewGroupsOK");
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
   */
  public String executeCommandPreviewGroups(ActionContext context) {

    if (!(hasPermission(context, "campaign-campaigns-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;

    try {
      db = this.getConnection(context);

      Campaign campaign = new Campaign(db, context.getRequest().getParameter("id"));
      context.getRequest().setAttribute("Campaign", campaign);

      SearchCriteriaList thisSCL = new SearchCriteriaList(db, context.getRequest().getParameter("scl"));
      context.getRequest().setAttribute("SCL", thisSCL);

      if ("true".equals(context.getRequest().getParameter("reset"))) {
        context.getSession().removeAttribute("CampaignCenterPreviewInfo");
        this.deletePagedListInfo(context, "CampaignCenterPreviewInfo");
      }
      
      PagedListInfo pagedListInfo = this.getPagedListInfo(context, "CampaignCenterPreviewInfo");
      pagedListInfo.setLink("CampaignManager.do?command=PreviewGroups&id=" + campaign.getId() + "&scl=" + thisSCL.getId());

      ContactList contacts = new ContactList();
      contacts.setScl(thisSCL, campaign.getEnteredBy(), this.getUserRange(context, campaign.getEnteredBy()));
      contacts.setPagedListInfo(pagedListInfo);
      contacts.setCheckExcludedFromCampaign(campaign.getId());
      contacts.buildList(db);
      context.getRequest().setAttribute("ContactList", contacts);

    } catch (Exception e) {
      errorMessage = e;
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
    addModuleBean(context, submenu, "Preview");

    if (errorMessage == null) {
      return ("PreviewGroupsOK");
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
   */
  public String executeCommandToggleRecipient(ActionContext context) {

    if (!(hasPermission(context, "campaign-campaigns-edit"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;
    boolean result = true;

    Campaign newCamp = (Campaign) context.getFormBean();

    String campaignId = context.getRequest().getParameter("id");
    String contactId = context.getRequest().getParameter("contactId");

    try {
      db = this.getConnection(context);
      Contact thisContact = new Contact(db, contactId);
      thisContact.checkExcludedFromCampaign(db, Integer.parseInt(campaignId));
      thisContact.toggleExcluded(db, Integer.parseInt(campaignId));
    } catch (Exception e) {
      errorMessage = e;
      System.out.println(e.toString());
    } finally {
      this.freeConnection(context, db);
    }

    context.getRequest().setAttribute("Campaign", newCamp);
    return ("ToggleOK");
  }


  /**
   *  Campaign Center: Message of a Campaign
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since           1.26
   */
  public String executeCommandViewMessage(ActionContext context) {

    if (!(hasPermission(context, "campaign-campaigns-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    addModuleBean(context, "ManageCampaigns", "Build New Campaign");
    Connection db = null;

    String campaignId = context.getRequest().getParameter("id");

    try {
      db = this.getConnection(context);
      Campaign campaign = new Campaign(db, campaignId);
      context.getRequest().setAttribute("Campaign", campaign);

      MessageList messageList = new MessageList();
      messageList.setOwner(this.getUserId(context));
      messageList.buildList(db);
      messageList.addItem(0, "--None--");
      context.getRequest().setAttribute("MessageList", messageList);

    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      return ("ViewMessageOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandViewAttachment(ActionContext context) {

    if (!(hasPermission(context, "campaign-campaigns-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    addModuleBean(context, "ManageCampaigns", "Build New Campaign");
    Connection db = null;

    String campaignId = context.getRequest().getParameter("id");

    try {
      db = this.getConnection(context);
      Campaign campaign = new Campaign(db, campaignId);
      context.getRequest().setAttribute("Campaign", campaign);

      SurveyList surveyList = new SurveyList();
      surveyList.setEnteredBy(this.getUserId(context));
      surveyList.buildList(db);
      surveyList.addItem(-1, "--None--");
      context.getRequest().setAttribute("SurveyList", surveyList);

    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      return ("ViewAttachmentOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Campaign Center: Schedule of a Campaign
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since           1.26
   */
  public String executeCommandViewSchedule(ActionContext context) {

    if (!(hasPermission(context, "campaign-campaigns-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    addModuleBean(context, "ManageCampaigns", "Build New Campaign");
    Connection db = null;

    String campaignId = context.getRequest().getParameter("id");

    try {
      db = this.getConnection(context);
      Campaign campaign = new Campaign(db, campaignId);
      context.getRequest().setAttribute("Campaign", campaign);

      LookupList deliveryList = new LookupList(db, "lookup_delivery_options");
      context.getRequest().setAttribute("DeliveryList", deliveryList);

      SearchCriteriaListList sclList = new SearchCriteriaListList();
      sclList.setCampaignId(campaign.getId());
      sclList.buildList(db);
      context.getRequest().setAttribute("sclList", sclList);

    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      return ("ViewScheduleOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Campaign Center: Add groups to the Campaign
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since           1.26
   */
  public String executeCommandAddGroups(ActionContext context) {

    if (!(hasPermission(context, "campaign-campaigns-edit"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;
    addModuleBean(context, "ManageCampaigns", "Campaign: Add Groups");

    PagedListInfo pagedListInfo = this.getPagedListInfo(context, "CampaignCenterGroupInfo");
    pagedListInfo.setLink("/CampaignManager.do?command=AddGroups");
    pagedListInfo.setItemsPerPage(0);
    String campaignId = context.getRequest().getParameter("id");

    try {
      db = this.getConnection(context);
      Campaign campaign = new Campaign(db, campaignId);
      context.getRequest().setAttribute("Campaign", campaign);

      //Complete List
      SearchCriteriaListList sclList = new SearchCriteriaListList();
      if ("all".equals(pagedListInfo.getListView())) {
        sclList.setOwnerIdRange(getUserRange(context));
      } else {
        sclList.setOwner(getUserId(context));
      }
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
      return ("AddGroupsOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Campaign Center: Processes the selected groups
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since           1.26
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
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since           1.26
   */
  public String executeCommandAddMessage(ActionContext context) {

    if (!(hasPermission(context, "campaign-campaigns-edit"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;
    addModuleBean(context, "ManageCampaigns", "Campaign: Add Message");

    String campaignId = context.getRequest().getParameter("id");

    try {
      db = this.getConnection(context);
      Campaign campaign = new Campaign(db, campaignId);
      context.getRequest().setAttribute("Campaign", campaign);

      MessageList messageList = new MessageList();
      messageList.setOwner(this.getUserId(context));
      messageList.buildList(db);
      context.getRequest().setAttribute("MessageList", messageList);

    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      return ("AddMessageOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandAddAttachment(ActionContext context) {

    if (!(hasPermission(context, "campaign-campaigns-edit"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;
    addModuleBean(context, "ManageCampaigns", "Campaign: Add Attachment");

    String campaignId = context.getRequest().getParameter("id");

    try {
      db = this.getConnection(context);
      Campaign campaign = new Campaign(db, campaignId);
      context.getRequest().setAttribute("Campaign", campaign);

    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      return ("AddMessageOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Campaign Center: Saves the selected message to the Campaign
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since           1.26
   */
  public String executeCommandInsertMessage(ActionContext context) {

    if (!(hasPermission(context, "campaign-campaigns-edit"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;
    int resultCount = 0;
    Campaign campaign = null;
    int surveyId = -1;

    String campaignId = context.getRequest().getParameter("id");
    String messageId = context.getRequest().getParameter("messageId");

    if (messageId != null) {
      try {
        db = this.getConnection(context);
        campaign = new Campaign(db, campaignId);
        campaign.setMessageId(Integer.parseInt(messageId));
        campaign.setModifiedBy(this.getUserId(context));
        resultCount = campaign.updateMessage(db);
      } catch (Exception e) {
        errorMessage = e;
      } finally {
        this.freeConnection(context, db);
      }
    }

    if (errorMessage == null) {
      if (resultCount == 1) {
        return ("InsertMessageOK");
      } else {
        return executeCommandAddMessage(context);
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandInsertAttachment(ActionContext context) {

    if (!(hasPermission(context, "campaign-campaigns-edit"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;
    int resultCount = 0;
    Campaign campaign = null;
    int surveyId = -1;

    String campaignId = context.getRequest().getParameter("id");

    try {
      surveyId = Integer.parseInt(context.getRequest().getParameter("surveyId"));
      db = this.getConnection(context);
      campaign = new Campaign(db, campaignId);
      campaign.setSurveyId(surveyId);
      campaign.setModifiedBy(this.getUserId(context));
      resultCount = campaign.updateSurvey(db);
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
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since           1.26
   */
  public String executeCommandInsertSchedule(ActionContext context) {

    if (!(hasPermission(context, "campaign-campaigns-edit"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;
    int resultCount = 0;
    Campaign campaign = null;

    String campaignId = context.getRequest().getParameter("id");

    try {
      db = this.getConnection(context);
      campaign = new Campaign(db, campaignId);
      campaign.setActiveDate(context.getRequest().getParameter("activeDate"));

      if (context.getRequest().getParameter("active") != null) {
        campaign.setActive(context.getRequest().getParameter("active"));
      }

      campaign.setModifiedBy(this.getUserId(context));
      campaign.setSendMethodId(Integer.parseInt(context.getRequest().getParameter("sendMethodId")));
      resultCount = campaign.updateSchedule(db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (resultCount == 1) {
        return ("InsertScheduleOK");
      } else {
        return executeCommandViewSchedule(context);
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Campaign Center: Processes the groups and removes them from a Campaign
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since           1.26
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
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since           1.26
   */
  public String executeCommandUpdate(ActionContext context) {

    if (!(hasPermission(context, "campaign-campaigns-edit"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;
    int resultCount = 0;

    Campaign campaign = null;
    String id = context.getRequest().getParameter("id");

    try {
      db = this.getConnection(context);
      campaign = (Campaign) context.getFormBean();
      campaign.setModifiedBy(getUserId(context));
      resultCount = campaign.updateDetails(db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (resultCount == 1) {
        return ("UpdateDetailsOK");
      } else {
        context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
        return ("UserError");
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  From the Dashboard, processes a Cancel Campaign request
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since           1.26
   */
  public String executeCommandCancel(ActionContext context) {

    if (!(hasPermission(context, "campaign-campaigns-edit"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;
    int resultCount = 0;

    Campaign campaign = null;
    String id = context.getRequest().getParameter("id");

    try {
      db = this.getConnection(context);
      campaign = new Campaign(db, id);
      campaign.setModifiedBy(getUserId(context));
      resultCount = campaign.cancel(db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (resultCount == 1) {
        return ("CancelOK");
      } else {
        context.getRequest().setAttribute("Error",
            "<p><b>This campaign could not be cancelled because it has already started processing or has completed.</b></p>" +
            "<p>Once the server starts sending the messages, the campaign cannot be stopped.</p>" +
            "<p><a href=\"/CampaignManager.do?command=Dashboard\">Back to Dashboard</a></p>");
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
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since           1.26
   */
  public String executeCommandActivate(ActionContext context) {

    if (!(hasPermission(context, "campaign-campaigns-edit"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;
    int resultCount = 0;

    Campaign campaign = null;
    String id = context.getRequest().getParameter("id");
    String modified = context.getRequest().getParameter("modified");

    try {
      db = this.getConnection(context);
      campaign = new Campaign(db, id);
      campaign.setModifiedBy(getUserId(context));
      campaign.setModified(modified);
      campaign.setServerName(context.getRequest().getServerName());
      resultCount = campaign.activate(db, campaign.getEnteredBy(), this.getUserRange(context, campaign.getEnteredBy()));
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (resultCount == 1) {
        return ("ActivateOK");
      } else {
        context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
        return ("UserError");
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Read in an activated Campaign -- ready only, with stats.
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since           1.26
   */
  public String executeCommandDetails(ActionContext context) {

    if (!(hasPermission(context, "campaign-campaigns-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;

    String id = context.getRequest().getParameter("id");

    if ("true".equals(context.getRequest().getParameter("reset"))) {
      context.getSession().removeAttribute("CampaignDashboardRecipientInfo");
    }
    PagedListInfo pagedListInfo = this.getPagedListInfo(context, "CampaignDashboardRecipientInfo");
    pagedListInfo.setLink("/CampaignManager.do?command=Details&id=" + id);

    try {
      db = this.getConnection(context);
      Campaign campaign = new Campaign(db, id);
      context.getRequest().setAttribute("Campaign", campaign);

      int surveyId = -1;
      if ((surveyId = ActiveSurvey.getId(db, campaign.getId())) > 0) {
        ActiveSurvey thisSurvey = new ActiveSurvey(db, surveyId);
        context.getRequest().setAttribute("ActiveSurvey", thisSurvey);
      }

      RecipientList recipients = new RecipientList();
      recipients.setCampaignId(campaign.getId());
      recipients.setBuildContact(true);
      recipients.setPagedListInfo(pagedListInfo);
      recipients.buildList(db);
      context.getRequest().setAttribute("RecipientList", recipients);

    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      addModuleBean(context, "Dashboard", "Campaign: Details");
      return ("DetailsOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandShowItems(ActionContext context) {

    if (!(hasPermission(context, "campaign-campaigns-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;
    ActiveSurveyQuestionItemList itemList = null;

    int questionId = Integer.parseInt(context.getRequest().getParameter("questionId"));

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
   *  Processes a Campaign Delete request
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since           1.26
   */
  public String executeCommandDelete(ActionContext context) {

    if (!(hasPermission(context, "campaign-campaigns-delete"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    boolean recordDeleted = false;

    Campaign campaign = null;
    String passedId = context.getRequest().getParameter("id");

    Connection db = null;
    try {
      db = this.getConnection(context);
      campaign = new Campaign(db, passedId);
      recordDeleted = campaign.delete(db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (recordDeleted) {
        return ("DeleteOK");
      } else {
        processErrors(context, campaign.getErrors());
        return (executeCommandView(context));
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Generate a list of Campaign files that can be downloaded.
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandPrepareDownload(ActionContext context) {

    if (!(hasPermission(context, "campaign-campaigns-view"))) {
      return ("PermissionError");
    }

    addModuleBean(context, "Dashboard", "Campaign: Downloads");
    Exception errorMessage = null;
    Connection db = null;

    try {
      String campaignId = context.getRequest().getParameter("id");
      db = this.getConnection(context);
      Campaign thisCampaign = new Campaign(db, campaignId);
      FileItemList files = new FileItemList();
      files.setLinkModuleId(Constants.COMMUNICATIONS);
      files.setLinkItemId(thisCampaign.getId());
      files.buildList(db);

      context.getRequest().setAttribute("Campaign", thisCampaign);
      context.getRequest().setAttribute("FileItemList", files);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      return ("PrepareDownloadOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }



  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDownload(ActionContext context) {

    if (!(hasPermission(context, "campaign-campaigns-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;

    String linkItemId = (String) context.getRequest().getParameter("id");
    String fileId = (String) context.getRequest().getParameter("fid");
    FileItem itemToDownload = null;

    Connection db = null;
    try {
      db = getConnection(context);
      itemToDownload = new FileItem(db, Integer.parseInt(fileId), Integer.parseInt(linkItemId), Constants.COMMUNICATIONS);
    } catch (Exception e) {
      errorMessage = e;
      e.printStackTrace(System.out);
    } finally {
      this.freeConnection(context, db);
    }

    //Start the download
    try {
      String filePath = this.getPath(context, "communications", Integer.parseInt(linkItemId)) + getDatePath(itemToDownload.getModified()) + itemToDownload.getFilename();

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
      //User either cancelled the download or lost connection
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
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandShowComments(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;

    try {
      SurveyAnswerList answerList = new SurveyAnswerList();
      answerList.setQuestionId(Integer.parseInt(context.getRequest().getParameter("questionId")));
      answerList.setHasComments(Constants.TRUE);
      db = getConnection(context);
      answerList.buildList(db);
      context.getRequest().setAttribute("SurveyAnswerList", answerList);
    } catch (Exception e) {
      errorMessage = e;
      e.printStackTrace(System.out);
    } finally {
      this.freeConnection(context, db);
    }

    return ("PopupCommentsOK");
  }


  /**
   *  Description of the Method
   *
   *@param  selectedList  Description of the Parameter
   *@param  context       Description of the Parameter
   */
  private static void processListCheckBoxes(SearchCriteriaListList selectedList, ActionContext context) {
    int count = 0;
    while (context.getRequest().getParameter("select" + (++count)) != null) {
      SearchCriteriaList scl = new SearchCriteriaList();
      scl.setId(context.getRequest().getParameter("select" + count));
      if ("on".equalsIgnoreCase(context.getRequest().getParameter("select" + count + "check"))) {
        selectedList.add(scl);
      } else {
        selectedList.removeItem(scl);
      }
    }
  }
  
  public String executeCommandViewAttachmentsOverview(ActionContext context) {

    if (!(hasPermission(context, "campaign-campaigns-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    addModuleBean(context, "ManageCampaigns", "Build New Campaign");
    Connection db = null;

    String campaignId = context.getRequest().getParameter("id");

    try {
      db = this.getConnection(context);
      Campaign campaign = new Campaign(db, campaignId);
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
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      return ("ViewAttachmentsOverviewOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
  
  public String executeCommandManageFileAttachments(ActionContext context) {

    if (!(hasPermission(context, "campaign-campaigns-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    addModuleBean(context, "ManageCampaigns", "Build New Campaign");
    Connection db = null;

    String campaignId = context.getRequest().getParameter("id");

    try {
      db = this.getConnection(context);
      Campaign campaign = new Campaign(db, campaignId);
      context.getRequest().setAttribute("Campaign", campaign);

      FileItemList files = new FileItemList();
      files.setLinkModuleId(Constants.COMMUNICATIONS_FILE_ATTACHMENTS);
      files.setLinkItemId(campaign.getId());
      files.buildList(db);
      context.getRequest().setAttribute("fileItemList", files);
      
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      return ("ManageFileAttachmentsOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
  
  public String executeCommandUploadFile(ActionContext context) {
    if (!(hasPermission(context, "campaign-campaigns-edit"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    Connection db = null;
    boolean recordInserted = false;
    try {
      String filePath = this.getPath(context, "communications");

      //Process the form data
      HttpMultiPartParser multiPart = new HttpMultiPartParser();
      multiPart.setUsePathParam(false);
      multiPart.setUseUniqueName(true);
      multiPart.setUseDateForFolder(true);
      multiPart.setExtensionId(getUserId(context));

      HashMap parts = multiPart.parseData(
          context.getRequest().getInputStream(), "---------------------------", filePath);

      db = getConnection(context);

      String id = context.getRequest().getParameter("id");
      //String id = (String) parts.get("id");
      String subject = "Attachment";
      //String subject = (String) parts.get("subject");
      //String folderId = (String) parts.get("folderId");
      Campaign campaign = new Campaign(db, Integer.parseInt(id));
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

        recordInserted = thisItem.insert(db);
        if (!recordInserted) {
          processErrors(context, thisItem.getErrors());
        }
      } else {
        recordInserted = false;
        HashMap errors = new HashMap();
        errors.put("actionError", "The file could not be sent by your computer, make sure the file exists");
        processErrors(context, errors);
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      freeConnection(context, db);
    }

    if (errorMessage == null) {
      return (executeCommandManageFileAttachments(context));
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
  
  public String executeCommandRemoveFile(ActionContext context) {
    if (!(hasPermission(context, "campaign-campaigns-edit"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    Connection db = null;
    boolean recordDeleted = false;
    try {
      String itemId = (String) context.getRequest().getParameter("fid");
      String campaignId = (String) context.getRequest().getParameter("id");
      db = getConnection(context);
      FileItem thisItem = new FileItem(db, Integer.parseInt(itemId), Integer.parseInt(campaignId), Constants.COMMUNICATIONS_FILE_ATTACHMENTS);
      recordDeleted = thisItem.delete(db, this.getPath(context, "communications", thisItem.getLinkItemId()));
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      freeConnection(context, db);
    }

    if (errorMessage == null) {
      return (executeCommandManageFileAttachments(context));
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
  
  public String executeCommandDownloadFile(ActionContext context) {
    if (!(hasPermission(context, "campaign-campaigns-view"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;

    String itemId = (String) context.getRequest().getParameter("fid");
    String campaignId = (String) context.getRequest().getParameter("id");
    FileItem thisItem = null;
    Connection db = null;
    try {
      db = getConnection(context);
      thisItem = new FileItem(db, Integer.parseInt(itemId), Integer.parseInt(campaignId), Constants.COMMUNICATIONS_FILE_ATTACHMENTS);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    
    //Start the download
    try {
      FileItem itemToDownload = thisItem;
      itemToDownload.setEnteredBy(this.getUserId(context));
      String filePath = this.getPath(context, "communications") + getDatePath(itemToDownload.getModified()) + itemToDownload.getFilename();
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
        System.err.println("CampaignManager-> Trying to send a file that does not exist");
        context.getRequest().setAttribute("actionError", "The requested download no longer exists on the system");
        return (executeCommandView(context));
      }
    } catch (java.net.SocketException se) {
      //User either cancelled the download or lost connection
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
}

