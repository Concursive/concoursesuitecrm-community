package com.darkhorseventures.cfsmodule;

import javax.servlet.*;
import javax.servlet.http.*;
import org.theseus.actions.*;
import java.sql.*;
import java.util.Vector;
import java.util.Iterator;
import com.darkhorseventures.utils.*;
import com.darkhorseventures.cfsbase.*;
import com.darkhorseventures.webutils.*;

/**
 *  Actions for dealing with Campaigns in the Communications Module, including
 *  the dashboard and campaign center.
 *
 *@author     w. gillette
 *@created    October 18, 2001
 *@version    $Id$
 */
public final class CampaignManager extends CFSModule {

  /**
   *  Generates the Dashboard List
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since           1.1
   */
  public String executeCommandDashboard(ActionContext context) {
    Connection db = null;
    Exception errorMessage = null;

    PagedListInfo pagedListInfo = this.getPagedListInfo(context, "CampaignDashboardListInfo");
    pagedListInfo.setLink("/CampaignManager.do?command=Dashboard");

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
      submenu = (String)context.getRequest().getAttribute("submenu");
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
    Connection db = null;
    Exception errorMessage = null;

    PagedListInfo pagedListInfo = this.getPagedListInfo(context, "CampaignListInfo");
    pagedListInfo.setLink("/CampaignManager.do?command=View");

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
      submenu = (String)context.getRequest().getAttribute("submenu");
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
    Exception errorMessage = null;
    Connection db = null;
    boolean recordInserted = false;

    Campaign campaign = (Campaign)context.getFormBean();

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
   *  Campaign Center: Groups of a Campaign
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since           1.26
   */
  public String executeCommandViewGroups(ActionContext context) {
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
  
  public String executeCommandPreviewGroups(ActionContext context) {
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
      }
      PagedListInfo pagedListInfo = this.getPagedListInfo(context, "CampaignCenterPreviewInfo");
      pagedListInfo.setLink("/CampaignManager.do?command=PreviewGroups&id=" + thisSCL.getId());

      ContactList contacts = new ContactList();
      contacts.setScl(thisSCL);
      contacts.setPagedListInfo(pagedListInfo);
      contacts.setOwner(campaign.getEnteredBy());
      contacts.addIgnoreTypeId(Contact.EMPLOYEE_TYPE);
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
      submenu = (String)context.getRequest().getAttribute("submenu");
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
  
  public String executeCommandToggleRecipient(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    boolean result = true;

    Campaign newCamp = (Campaign)context.getFormBean();
    
    String campaignId = context.getRequest().getParameter("id"); 
    String contactId = context.getRequest().getParameter("contactId");
    
    try {
      db = this.getConnection(context);
      Contact thisContact = new Contact(db, contactId);
      thisContact.checkExcludedFromCampaign(db,Integer.parseInt(campaignId));
      thisContact.toggleExcluded(db, Integer.parseInt(campaignId));
    } catch (Exception e) {
      errorMessage = e;
      System.out.println(e.toString());
    } finally {
      this.freeConnection(context, db);
    }
    
    context.getRequest().setAttribute("Campaign", newCamp);
    return("ToggleOK");
  }


  /**
   *  Campaign Center: Message of a Campaign
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since           1.26
   */
  public String executeCommandViewMessage(ActionContext context) {
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
   *  Campaign Center: Schedule of a Campaign
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since           1.26
   */
  public String executeCommandViewSchedule(ActionContext context) {
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

      SearchCriteriaListList sclList = new SearchCriteriaListList();
      sclList.setOwner(getUserId(context));
      sclList.buildList(db);
      context.getRequest().setAttribute("sclList", sclList);

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
   *  Campaign Center: Saves the selected message to the Campaign
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since           1.26
   */
  public String executeCommandInsertMessage(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    int resultCount = 0;
    Campaign campaign = null;

    String campaignId = context.getRequest().getParameter("id");
    String messageId = context.getRequest().getParameter("messageId");

    if (messageId != null) {
      try {
        int msgId = Integer.parseInt(messageId);
        db = this.getConnection(context);
        campaign = new Campaign(db, campaignId);
        campaign.setMessageId(msgId);
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
   *  Campaign Center: Saves the schedule details to the Campaign
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since           1.26
   */
  public String executeCommandInsertSchedule(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    int resultCount = 0;
    Campaign campaign = null;

    String campaignId = context.getRequest().getParameter("id");

    try {
      db = this.getConnection(context);
      campaign = new Campaign(db, campaignId);
      campaign.setActiveDate(context.getRequest().getParameter("activeDate"));
      campaign.setActive(context.getRequest().getParameter("active"));
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
    Exception errorMessage = null;
    Connection db = null;
    int resultCount = 0;

    Campaign campaign = null;
    String id = context.getRequest().getParameter("id");

    try {
      db = this.getConnection(context);
      campaign = (Campaign)context.getFormBean();
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
        context.getRequest().setAttribute("Error",
            "<b>This record could not be updated because someone else updated it first.</b><p>" +
            "You can hit the back button to review the changes that could not be committed, " +
            "but you must reload the record and make the changes again.");
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
            "<p><b>This campaign could not be cancelled because it has already started processing.</b></p>" +
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
      resultCount = campaign.activate(db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (resultCount == 1) {
        return ("ActivateOK");
      } else {
        context.getRequest().setAttribute("Error",
            "<b>This record could not be updated because someone else updated it first.</b><p>" +
            "You can hit the back button to review the changes that could not be committed, " +
            "but you must reload the record and make the changes again.");
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
   *  Processes a Campaign Delete request
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since           1.26
   */
  public String executeCommandDelete(ActionContext context) {
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
}

