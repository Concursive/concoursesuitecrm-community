package org.aspcfs.modules.communications.actions;

import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.framework.actions.*;
import java.sql.*;
import java.util.*;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.communications.base.*;
import org.aspcfs.modules.contacts.base.*;
import org.aspcfs.utils.*;
import org.aspcfs.utils.web.*;
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
    
    if("list".equals(context.getRequest().getParameter("source"))){
      addModuleBean(context, "ManageCampaigns", "Build New Campaign");
    }else{
      addModuleBean(context, "Add Campaign", "Build New Campaign");
    }

    

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
    Campaign campaign = null;

    String campaignId = context.getRequest().getParameter("id");

    try {
      db = this.getConnection(context);
      campaign = new Campaign(db, campaignId);
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
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (!hasAuthority(context, campaign.getEnteredBy())) {
        return ("PermissionError");
      }
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
    Campaign campaign = null;

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
    if (!hasPermission(context, "campaign-campaigns-view")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    Connection db = null;
    Campaign campaign = null;
    addModuleBean(context, "Dashboard", "View Groups");
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
    if (!hasPermission(context, "campaign-campaigns-view")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    Connection db = null;
    Campaign campaign = null;
    try {
      db = this.getConnection(context);
      //Show the campaign name
      campaign = new Campaign(db, context.getRequest().getParameter("id"));
      context.getRequest().setAttribute("Campaign", campaign);
      //Load the criteria for the contacts query
      SearchCriteriaList thisSCL = new SearchCriteriaList(db, context.getRequest().getParameter("scl"));
      context.getRequest().setAttribute("SCL", thisSCL);
      //Reset the pagedList if this is the first time being opened
      if ("true".equals(context.getRequest().getParameter("reset"))) {
        context.getSession().removeAttribute("CampaignCenterPreviewInfo");
      }
      PagedListInfo pagedListInfo = this.getPagedListInfo(context, "CampaignCenterPreviewInfo");
      pagedListInfo.setLink("CampaignManager.do?command=PreviewGroups&id=" + campaign.getId() + "&scl=" + thisSCL.getId() + "&popup=true");
      //Generate the contacts for this page
      ContactList contacts = new ContactList();
      contacts.setScl(thisSCL, campaign.getEnteredBy(), this.getUserRange(context, campaign.getEnteredBy()), this.getUserId(context));
      contacts.setPagedListInfo(pagedListInfo);
      contacts.setCheckExcludedFromCampaign(campaign.getId());
      contacts.setBuildDetails(true);
      contacts.setBuildTypes(false);
      contacts.buildList(db);
      context.getRequest().setAttribute("ContactList", contacts);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      if (!hasAuthority(context, campaign.getEnteredBy())) {
        return ("PermissionError");
      }
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
    if (!hasPermission(context, "campaign-campaigns-edit")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    Connection db = null;
    boolean result = true;
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
      context.getRequest().setAttribute("recipientText", 
        thisContact.getExcludedFromCampaign()?"No":"Yes");
    } catch (Exception e) {
      errorMessage = e;
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
    Campaign campaign = null;

    try {
      db = this.getConnection(context);
      campaign = new Campaign(db, campaignId);
      context.getRequest().setAttribute("Campaign", campaign);
      //Build my messages
      MessageList messageList = new MessageList();
      messageList.setOwner(this.getUserId(context));
      messageList.buildList(db);
      //Message is not in my messages, so build hierarchy of messages
      if (campaign.getMessageId() > 0 && !messageList.hasId(campaign.getMessageId())) {
        messageList.clear();
        messageList.setOwner(-1);
        messageList.setOwnerIdRange(this.getUserRange(context));
        messageList.buildList(db);
        context.getRequest().setAttribute("listView", "all");
      } else {
        context.getRequest().setAttribute("listView", "my");
      }
      messageList.addItem(0, "--None--");
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
  public String executeCommandPreviewMessage(ActionContext context) {

    if (!(hasPermission(context, "campaign-campaigns-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    addModuleBean(context, "Dashboard", "Build New Campaign");
    Connection db = null;
    Campaign campaign = null;

    String campaignId = context.getRequest().getParameter("id");

    try {
      db = this.getConnection(context);
      campaign = new Campaign(db, campaignId);
      FileItemList documents = new FileItemList();
      documents.setLinkModuleId(Constants.COMMUNICATIONS_FILE_ATTACHMENTS);
      documents.setLinkItemId(Integer.parseInt(campaignId));
      documents.buildList(db);
      context.getRequest().setAttribute("FileItemList", documents);
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
      return ("PreviewMessageOK");
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
  public String executeCommandDownloadMessage(ActionContext context) {

    if (!(hasPermission(context, "campaign-dashboard-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;

    String itemId = (String) context.getRequest().getParameter("fid");
    String version = (String) context.getRequest().getParameter("ver");
    FileItem thisItem = null;

    Connection db = null;
    int id = Integer.parseInt(context.getRequest().getParameter("id"));
    try {
      db = getConnection(context);
      thisItem = new FileItem(db, Integer.parseInt(itemId), id, Constants.COMMUNICATIONS_FILE_ATTACHMENTS);
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
        String filePath = this.getPath(context, "communications", id) + getDatePath(itemToDownload.getModified()) + itemToDownload.getFilename();
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
          System.err.println("CampaignDocuments-> Trying to send a file that does not exist");
          context.getRequest().setAttribute("actionError", "The requested download no longer exists on the system");
          return (executeCommandPreviewMessage(context));
        }
      } else {
        FileItemVersion itemToDownload = thisItem.getVersion(Double.parseDouble(version));
        itemToDownload.setEnteredBy(this.getUserId(context));
        String filePath = this.getPath(context, "campaign", id) + getDatePath(itemToDownload.getModified()) + itemToDownload.getFilename();
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
          System.err.println("CampaignMessage Documents -> Trying to send a file that does not exist");
          context.getRequest().setAttribute("actionError", "The requested download no longer exists on the system");
          return (executeCommandPreviewMessage(context));
        }
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
      addModuleBean(context, "Dashboard", "");
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
    Campaign campaign = null;

    try {
      db = this.getConnection(context);
      campaign = new Campaign(db, campaignId);
      context.getRequest().setAttribute("Campaign", campaign);

      SurveyList surveyList = new SurveyList();
      surveyList.setEnteredBy(this.getUserId(context));
      surveyList.buildList(db);
      //Survey is not in my surveys, so build hierarchy of surveys
      if (campaign.getSurveyId() > 0 && !surveyList.hasId(campaign.getSurveyId())) {
        surveyList.clear();
        surveyList.setEnteredBy(-1);
        surveyList.setEnteredByIdRange(this.getUserRange(context));
        surveyList.buildList(db);
        context.getRequest().setAttribute("listView", "all");
      } else {
        context.getRequest().setAttribute("listView", "my");
      }
      surveyList.addItem(-1, "--None--");
      context.getRequest().setAttribute("SurveyList", surveyList);

    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (!hasAuthority(context, campaign.getEnteredBy())) {
        return ("PermissionError");
      }
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
      context.getRequest().setAttribute("Campaign", campaign);
      //Prepare the list of available delivery types
      LookupList deliveryList = new LookupList(db, "lookup_delivery_options");
      context.getRequest().setAttribute("DeliveryList", deliveryList);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      if (!hasAuthority(context, campaign.getEnteredBy())) {
        return ("PermissionError");
      }
      return ("ViewScheduleOK");
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
  public String executeCommandPreviewSchedule(ActionContext context) {
    if (!hasPermission(context, "campaign-campaigns-view")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    addModuleBean(context, "Dashboard", "Preview Schedule");
    Connection db = null;
    Campaign campaign = null;
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
      return ("PreviewScheduleOK");
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
  public String executeCommandPreviewSurvey(ActionContext context) {

    if (!(hasPermission(context, "campaign-campaigns-surveys-view"))) {
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
    if (context.getRequest().getParameter("listView") == null) {
      //TODO... reset the list somehow....
      //context.getRequest().setAttribute("resetList", "true");
    }
    PagedListInfo pagedListInfo = this.getPagedListInfo(context, "CampaignCenterGroupInfo");
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
    Campaign campaign = null;

    addModuleBean(context, "ManageCampaigns", "Campaign: Add Message");

    String campaignId = context.getRequest().getParameter("id");

    try {
      db = this.getConnection(context);
      campaign = new Campaign(db, campaignId);
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
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
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
        if (!hasAuthority(context, campaign.getEnteredBy())) {
          return ("PermissionError");
        }
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
      if (!hasAuthority(context, campaign.getEnteredBy())) {
        return ("PermissionError");
      }
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
      if (!hasAuthority(context, campaign.getEnteredBy())) {
        return ("PermissionError");
      }
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
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since           1.26
   */
  public String executeCommandUpdate(ActionContext context) {

    if (!hasPermission(context, "campaign-campaigns-edit")) {
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
      int enteredBy = Campaign.queryEnteredBy(db, campaign.getId());
      if (hasAuthority(context, enteredBy)) {
        campaign.setModifiedBy(getUserId(context));
        resultCount = campaign.updateDetails(db);
      } else {
        resultCount = -1;
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (resultCount == -1) {
        return ("PermissionError");
      } else if (resultCount == 1) {
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
      if (!hasAuthority(context, campaign.getEnteredBy())) {
        return "PermissionError";
      }
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
      if (!hasAuthority(context, campaign.getEnteredBy())) {
        return "PermissionError";
      }
      campaign.setModifiedBy(getUserId(context));
      campaign.setModified(modified);
      campaign.setServerName(context.getRequest().getServerName());
      resultCount = campaign.activate(db, campaign.getEnteredBy(), this.getUserRange(context, campaign.getEnteredBy()), this.getUserId(context));
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
    Campaign campaign = null;

    try {
      db = this.getConnection(context);
      campaign = new Campaign(db, id);
      context.getRequest().setAttribute("Campaign", campaign);

      int surveyId = -1;
      if ((surveyId = ActiveSurvey.getId(db, campaign.getId())) > 0) {
        ActiveSurvey thisSurvey = new ActiveSurvey(db, surveyId);
        context.getRequest().setAttribute("ActiveSurvey", thisSurvey);
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (!hasAuthority(context, campaign.getEnteredBy())) {
        return ("PermissionError");
      }
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
    PagedListInfo pagedListInfo = this.getPagedListInfo(context, "SurveyQuestionListInfo");
    pagedListInfo.setLink("CampaignManager.do?command=ViewResults&id=" + id);
    try {
      db = this.getConnection(context);
      campaign = new Campaign(db, id);
      context.getRequest().setAttribute("Campaign", campaign);

      int surveyId = -1;
      if ((surveyId = ActiveSurvey.getId(db, campaign.getId())) > 0) {
        ActiveSurveyQuestionList thisList = new ActiveSurveyQuestionList();
        thisList.setActiveSurveyId(surveyId);
        thisList.setPagedListInfo(pagedListInfo);
        thisList.setBuildResults(true);
        thisList.buildList(db);
        context.getRequest().setAttribute("SurveyQuestionList", thisList);
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (!hasAuthority(context, campaign.getEnteredBy())) {
        return ("PermissionError");
      }
      addModuleBean(context, "Dashboard", "Campaign: Results");
      return ("ResultsOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  View the survey response based on the recipients.
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
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
    }
    PagedListInfo pagedListInfo = this.getPagedListInfo(context, "SurveyResponseListInfo");
    pagedListInfo.setLink("CampaignManager.do?command=ViewResponse&id=" + id);
    try {
      db = this.getConnection(context);
      campaign = new Campaign(db, id);
      context.getRequest().setAttribute("Campaign", campaign);

      int surveyId = -1;
      if ((surveyId = ActiveSurvey.getId(db, campaign.getId())) > 0) {
        SurveyResponseList thisList = new SurveyResponseList();
        thisList.setSurveyId(surveyId);
        thisList.setPagedListInfo(pagedListInfo);
        thisList.buildList(db);
        context.getRequest().setAttribute("SurveyResponseList", thisList);
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (!hasAuthority(context, campaign.getEnteredBy())) {
        return ("PermissionError");
      }
      addModuleBean(context, "Dashboard", "Campaign: Response");
      return ("ResponseOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  public String executeCommandResponseDetails(ActionContext context) {

    if (!(hasPermission(context, "campaign-campaigns-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;
    Campaign campaign = null;

    String id = context.getRequest().getParameter("id");
    String contactId = context.getRequest().getParameter("contactId");
    String responseId = context.getRequest().getParameter("responseId");
    if ("true".equals(context.getRequest().getParameter("reset"))) {
      context.getSession().removeAttribute("ResponseDetailsListInfo");
    }
    PagedListInfo pagedListInfo = this.getPagedListInfo(context, "ResponseDetailsListInfo");
    pagedListInfo.setLink("CampaignManager.do?command=ResponseDetails&id=" + id);
    try {
      db = this.getConnection(context);
      campaign = new Campaign(db, id);
      context.getRequest().setAttribute("Campaign", campaign);

      int surveyId = -1;
      if ((surveyId = ActiveSurvey.getId(db, campaign.getId())) > 0) {
        ActiveSurveyQuestionList thisList = new ActiveSurveyQuestionList();
        thisList.setActiveSurveyId(surveyId);
        thisList.setPagedListInfo(pagedListInfo);
        thisList.buildList(db);
        thisList.buildResponse(db, Integer.parseInt(contactId), Integer.parseInt(responseId));
        context.getRequest().setAttribute("ResponseDetails", thisList);
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (!hasAuthority(context, campaign.getEnteredBy())) {
        return ("PermissionError");
      }
      addModuleBean(context, "Dashboard", "Campaign: Response Details");
      return ("ResponseDetailsOK");
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
    PagedListInfo pagedListInfo = this.getPagedListInfo(context, "CampaignDashboardRecipientInfo");
    pagedListInfo.setLink("CampaignManager.do?command=PreviewRecipients&id=" + id);
    try {
      db = this.getConnection(context);
      campaign = new Campaign(db, id);
      if (!hasAuthority(context, campaign.getEnteredBy())) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("Campaign", campaign);

      int surveyId = -1;

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
      addModuleBean(context, "Dashboard", "Campaign: Recipients");
      return ("PreviewRecipientsOK");
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
   *  Show all contacts who responded to a specific item in the Item List
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
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
    PagedListInfo pagedListInfo = this.getPagedListInfo(context, "ItemDetailsListInfo");
    pagedListInfo.setLink("CampaignManager.do?command=ShowItemDetails&itemId=" + itemId + "&questionId=" + questionId);

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
      if (!hasAuthority(context, campaign.getEnteredBy())) {
        return "PermissionError";
      }
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
      if (!hasAuthority(context, thisCampaign.getEnteredBy())) {
        return ("PermissionError");
      }
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
   *  Builds comments for a SurveyQuestion Question could be either a Open Ended
   *  or Quantitative with comments
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandShowComments(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    String questionId = context.getRequest().getParameter("questionId");
    String type = context.getRequest().getParameter("type");
    if ("true".equals(context.getRequest().getParameter("reset"))) {
      context.getSession().removeAttribute("CommentListInfo");
    }
    PagedListInfo pagedListInfo = this.getPagedListInfo(context, "CommentListInfo");
    pagedListInfo.setLink("CampaignManager.do?command=ShowComments&questionId=" + questionId + "&type=" + type);
    try {
      SurveyAnswerList answerList = new SurveyAnswerList();
      answerList.setQuestionId(Integer.parseInt(questionId));
      answerList.setHasComments(Constants.TRUE);
      answerList.setPagedListInfo(pagedListInfo);
      db = getConnection(context);
      answerList.buildList(db);
      context.getRequest().setAttribute("SurveyAnswerList", answerList);
      context.getRequest().setAttribute("SurveyContactList", answerList.getContacts());
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
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
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
      if (!hasAuthority(context, campaign.getEnteredBy())) {
        return ("PermissionError");
      }
      return ("ViewAttachmentsOverviewOK");
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

      if (!hasAuthority(context, campaign.getEnteredBy())) {
        return ("PermissionError");
      }

      return ("ManageFileAttachmentsOK");
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
      if (!hasAuthority(context, campaign.getEnteredBy())) {
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


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
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
      Campaign campaign = new Campaign(db, Integer.parseInt(campaignId));
      if (!hasAuthority(context, campaign.getEnteredBy())) {
        return "PermissionError";
      }
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


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
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
      Campaign campaign = new Campaign(db, Integer.parseInt(campaignId));
      if (!hasAuthority(context, campaign.getEnteredBy())) {
        return "PermissionError";
      }
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


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandMessageJSList(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    String listView = context.getRequest().getParameter("listView");
    try {
      MessageList messageList = new MessageList();
      if ("all".equals(listView)) {
        messageList.setOwnerIdRange(this.getUserRange(context));
      } else {
        messageList.setOwner(this.getUserId(context));
      }
      db = this.getConnection(context);
      messageList.buildList(db);
      context.getRequest().setAttribute("messageList", messageList);
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    return ("MessageJSListOK");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandSurveyJSList(ActionContext context) {
    Exception errorMessage = null;
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
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    return ("SurveyJSListOK");
  }
}

