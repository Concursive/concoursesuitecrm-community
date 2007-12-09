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
package org.aspcfs.modules.actionlist.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import com.zeroio.webutils.FileDownload;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actionlist.base.*;
import org.aspcfs.modules.admin.base.AccessTypeList;
import org.aspcfs.modules.admin.base.AccessType;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.communications.base.*;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.contacts.base.ContactList;
import org.aspcfs.modules.contacts.base.ContactTypeList;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.RequestUtils;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 * Represents Action Contacts on a list and possible actions that can be
 * performed on each action contact
 *
 * @author akhi_m
 * @version $id:exp$
 * @created April 23, 2003
 */
public final class MyActionContacts extends CFSModule {

  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-action-lists-view"))) {
      return ("PermissionError");
    }
    return executeCommandList(context);
  }


  /**
   * Lists action contacts on a list based on the filter.
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandList(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-action-lists-view"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    String actionId = context.getRequest().getParameter("actionId");
    String viewUserId = (String) context.getSession().getAttribute(
        "viewUserId");
    if (viewUserId == null || "".equals(viewUserId)) {
      viewUserId = String.valueOf(this.getUserId(context));
    }
    addModuleBean(context, "My Action Lists", "Action Contacts");
    if ("true".equals(context.getRequest().getParameter("reset"))) {
      context.getSession().removeAttribute("ContactActionListInfo");
    }
    SystemStatus systemStatus = this.getSystemStatus(context);
    PagedListInfo contactActionListInfo = this.getPagedListInfo(
        context, "ContactActionListInfo");
    contactActionListInfo.setLink(
        "MyActionContacts.do?command=List&actionId=" + actionId);
    Connection db = null;
    try {
      db = getConnection(context);
      LookupList siteIdList = new LookupList(db, "lookup_site_id");
      siteIdList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("SiteIdList", siteIdList);
  
      ActionContactsList thisList = new ActionContactsList();
      thisList.setPagedListInfo(contactActionListInfo);
      thisList.setActionId(Integer.parseInt(actionId));
      thisList.setBuildHistory(true);
      if (!"all".equals(contactActionListInfo.getListView())) {
        if ("complete".equals(contactActionListInfo.getListView())) {
          thisList.setCompleteOnly(true);
        } else {
          thisList.setInProgressOnly(true);
        }
      }
      thisList.buildList(db);
      context.getRequest().setAttribute("ActionContacts", thisList);

      ActionList actionList = new ActionList(db, Integer.parseInt(actionId));
      context.getRequest().setAttribute("ActionList", actionList);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      return "ListOK";
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   * Add new contacts to the list.
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandPrepare(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-action-lists-add"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    String actionId = (String) context.getRequest().getParameter("actionId");
    String viewUserId = (String) context.getSession().getAttribute(
        "viewUserId");
    if (viewUserId == null || "".equals(viewUserId)) {
      viewUserId = String.valueOf(this.getUserId(context));
    }
    ActionList actionList = null;
    addModuleBean(context, "My Action Lists", "Action Contacts");
    Connection db = null;
    SystemStatus thisSystem = this.getSystemStatus(context);
    try {
      db = getConnection(context);

      SearchFieldList searchFieldList = new SearchFieldList();
      SearchOperatorList stringOperatorList = new SearchOperatorList();
      SearchOperatorList dateOperatorList = new SearchOperatorList();
      SearchOperatorList numberOperatorList = new SearchOperatorList();

      HtmlSelect contactSource = new HtmlSelect();
      contactSource.addItem(
          SearchCriteriaList.SOURCE_MY_CONTACTS, "My General Contacts");
      contactSource.addItem(
          SearchCriteriaList.SOURCE_ALL_CONTACTS, "All General Contacts");
      contactSource.addItem(
          SearchCriteriaList.SOURCE_ALL_ACCOUNTS, "All Account Contacts");
      context.getRequest().setAttribute("ContactSource", contactSource);

      ContactTypeList typeList = new ContactTypeList(db);
      LookupList ctl = typeList.getLookupList(
          this.getSystemStatus(context), "typeId", 0);
      ctl.setJsEvent(
          "onChange = \"javascript:setText(document.searchForm.typeId)\"");
      context.getRequest().setAttribute("ContactTypeList", ctl);

      LookupList accountTypeList = new LookupList(db, "lookup_account_types");
      accountTypeList.setSelectSize(1);
      context.getRequest().setAttribute("AccountTypeList", accountTypeList);

      searchFieldList.buildFieldList(db);
      context.getRequest().setAttribute("SearchFieldList", searchFieldList);

      stringOperatorList.buildOperatorList(db, 0);
      context.getRequest().setAttribute(
          "StringOperatorList", stringOperatorList);

      dateOperatorList.buildOperatorList(db, 1);
      context.getRequest().setAttribute("DateOperatorList", dateOperatorList);

      numberOperatorList.buildOperatorList(db, 2);
      context.getRequest().setAttribute(
          "NumberOperatorList", numberOperatorList);

      LookupList siteValueList = new LookupList(db, "lookup_site_id");
      siteValueList.addItem(-1, thisSystem.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("SiteIdList", siteValueList);
  
  
      LookupList siteCriteriaList = new LookupList(db, "lookup_site_id");
      siteCriteriaList.addItem(-1, thisSystem.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("SiteCriteriaList", siteCriteriaList);

      if (actionId != null && !"-1".equals("actionId")) {
        actionList = new ActionList(db, Integer.parseInt(actionId));
        context.getRequest().setAttribute("ActionList", actionList);
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      if (context.getRequest().getParameter("actionSource") != null) {
        return "PrepareContactsOK";
      }
      return "PrepareOK";
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   * Save action contacts to the list based on criteria
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */                                                                    
  public String executeCommandSave(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-action-lists-edit"))) {
      return ("PermissionError");
    }
    SearchCriteriaList thisSCL = null;
    String viewUserId = (String) context.getSession().getAttribute(
        "viewUserId");
    boolean isValid = false;
    HashMap errors = new HashMap();
    if (viewUserId == null || "".equals(viewUserId)) {
      viewUserId = String.valueOf(this.getUserId(context));
    }
    String criteria = context.getRequest().getParameter("searchCriteriaText");
    String actionId = context.getRequest().getParameter("actionId");
    addModuleBean(context, "My Action Lists", "Action Contacts");
    Connection db = null;
    //If no criteria is specified, just return
    //Temporary fix for a larger problem
    if (criteria == null || "".equals(criteria)) {
      return "InsertOK";
    }
    try {
      if (criteria != null && !"".equals(criteria)) {
        //The criteria that makes up the contact list query
        thisSCL = new SearchCriteriaList(criteria);
        thisSCL.setGroupName("Action List");
        thisSCL.setEnteredBy(getUserId(context));
        thisSCL.setModifiedBy(getUserId(context));
        thisSCL.setOwner(getUserId(context));
        db = this.getConnection(context);
        thisSCL.buildRelatedResources(db);

        //Build the contactList
        ContactList contacts = new ContactList();
        contacts.setScl(
            thisSCL, this.getUserId(context), this.getUserRange(context));
        contacts.setBuildDetails(true);
        contacts.setBuildTypes(false);
        contacts.setIncludeAllSites(true);
        AccessTypeList accessTypeList = this.getSystemStatus(context).getAccessTypeList(db, AccessType.GENERAL_CONTACTS);
        contacts.setGeneralContactAccessTypes(accessTypeList);
        contacts.buildList(db);

        //save action contacts
        ActionContactsList thisList = new ActionContactsList();
        thisList.setActionId(Integer.parseInt(actionId));
        thisList.setEnteredBy(this.getUserId(context));
        thisList.insert(db, contacts);
        context.getRequest().setAttribute("ActionContacts", thisList);
        isValid = true;
      } else {
        SystemStatus systemStatus = this.getSystemStatus(context);
        errors.put(
            "criteriaError", systemStatus.getLabel(
                "object.validation.criteriaNotDefined"));
        processErrors(context, errors);
      }
      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1, this.getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("SiteIdList", siteList);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (!isValid) {
      return (executeCommandPrepare(context));
    }
    return "InsertOK";
  }


  /**
   * Update contacts on action list
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandUpdate(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-action-lists-edit"))) {
      return ("PermissionError");
    }
    addModuleBean(context, "My Action Lists", "");
    String viewUserId = (String) context.getSession().getAttribute(
        "viewUserId");
    if (viewUserId == null || "".equals(viewUserId)) {
      viewUserId = String.valueOf(this.getUserId(context));
    }
    String selectedContacts = context.getRequest().getParameter(
        "selectedContacts");
    //Checking for errors
    HashMap errors = new HashMap();
    if ((selectedContacts == null) || ("".equals(selectedContacts.trim()))) {
      SystemStatus systemStatus = getSystemStatus(context);
      errors.put(
          "oneContactRequired", systemStatus.getLabel(
              "object.validation.oneContactRequired"));
    }
    if (!errors.isEmpty()) {
      processErrors(context, errors);
      return executeCommandModify(context);
    }
    String actionId = context.getRequest().getParameter("actionId");
    Exception errorMessage = null;
    Connection db = null;
    try {
      db = getConnection(context);
      ActionContactsList thisList = new ActionContactsList();
      thisList.setActionId(Integer.parseInt(actionId));
      thisList.setEnteredBy(this.getUserId(context));
      thisList.update(db, selectedContacts);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      return executeCommandList(context);
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   * Modify contacts on action list
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandModify(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-action-lists-edit"))) {
      return ("PermissionError");
    }
    addModuleBean(context, "My Action Lists", "");
    String actionId = context.getRequest().getParameter("actionId");
    String viewUserId = (String) context.getSession().getAttribute(
        "viewUserId");
    if (viewUserId == null || "".equals(viewUserId)) {
      viewUserId = String.valueOf(this.getUserId(context));
    }
/*    String siteId = String.valueOf(this.getUser(context, Integer.parseInt(viewUserId)).getSiteId());
    context.getRequest().setAttribute("siteId", siteId);*/
    boolean buildContacts = true;
    if ("false".equals(context.getRequest().getParameter("doBuild"))) {
      buildContacts = false;
    }
    HashMap selectedList = null;
    Exception errorMessage = null;
    Connection db = null;
    try {
      if (buildContacts) {
        db = getConnection(context);
        selectedList = new HashMap();
        ActionContactsList thisList = new ActionContactsList();
        thisList.setActionId(Integer.parseInt(actionId));
        thisList.buildList(db);

        Iterator i = thisList.iterator();
        while (i.hasNext()) {
          ActionContact thisContact = (ActionContact) i.next();
          selectedList.put(new Integer(thisContact.getLinkItemId()), "");
        }
        context.getSession().setAttribute("selectedContacts", selectedList);
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      if (buildContacts) {
        return "ModifyOK";
      }
      return "ModifyNoBuildOK";
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandAdd(ActionContext context) {
    String viewUserId = (String) context.getSession().getAttribute(
        "viewUserId");
    if (viewUserId == null || "".equals(viewUserId)) {
      viewUserId = String.valueOf(this.getUserId(context));
    }
    if (!(hasPermission(context, "myhomepage-action-lists-edit"))) {
      return ("PermissionError");
    }
    addModuleBean(context, "My Action Lists", "");
    return "PrepareOK";
  }


  /**
   * View history of actions performed on a action contact
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandViewHistory(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-action-lists-view"))) {
      return ("PermissionError");
    }
    String viewUserId = (String) context.getSession().getAttribute(
        "viewUserId");
    if (viewUserId == null || "".equals(viewUserId)) {
      viewUserId = String.valueOf(this.getUserId(context));
    }
    addModuleBean(context, "My Action Lists", "");
    String itemId = context.getRequest().getParameter("itemId");
    Exception errorMessage = null;
    Connection db = null;
    try {
      db = getConnection(context);
      ActionItemLogList thisList = new ActionItemLogList();
      thisList.setItemId(Integer.parseInt(itemId));
      thisList.setBuildDetails(true);
      thisList.buildList(db);
      context.getRequest().setAttribute("History", thisList);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      return "ViewHistoryOK";
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   * Change the status of an action contact
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandProcessImage(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-action-lists-edit"))) {
      return ("PermissionError");
    }
    String viewUserId = (String) context.getSession().getAttribute(
        "viewUserId");
    if (viewUserId == null || "".equals(viewUserId)) {
      viewUserId = String.valueOf(this.getUserId(context));
    }
    boolean isValid = false;
    Connection db = null;
    int count = 0;

    String id = (String) context.getRequest().getParameter("id");

    //Start the download
    try {
      StringTokenizer st = new StringTokenizer(id, "|");
      String fileName = st.nextToken();
      String imageType = st.nextToken();
      int contactId = Integer.parseInt(st.nextToken());
      int status = Integer.parseInt(st.nextToken());
      db = this.getConnection(context);
      ActionContact thisContact = new ActionContact(db, contactId);
      isValid = this.validateObject(context, db, thisContact);
      if (isValid) {
        if (status == ActionContact.DONE) {
          thisContact.updateStatus(db, true);
        } else {
          thisContact.updateStatus(db, false);
        }
        this.freeConnection(context, db);
        if (count != -1) {
          FileDownload.sendFile(context, context.getServletContext().getResource("/images/" + fileName), "image/" + imageType, fileName);
        } else {
          processErrors(context, thisContact.getErrors());
        }
      }
    } catch (java.net.SocketException se) {
      //User either canceled the download or lost connection
      System.out.println(
          "MyActionContacts -> ProcessImage : Download canceled or connection lost");
    } catch (Exception e) {
      this.freeConnection(context, db);
      System.out.println(e.toString());
    }
    return ("-none-");
  }


  /**
   * Prepare form for sending a message to an action contact
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandPrepareMessage(ActionContext context) {
    if (!((hasPermission(context, "myhomepage-action-lists-edit")) ||
        (hasPermission(context, "accounts-accounts-contact-updater-view")) ||
        (hasPermission(context, "contacts-external-contact-updater-view")))) {
      return ("PermissionError");
    }
    String viewUserId = (String) context.getSession().getAttribute(
        "viewUserId");
    if (viewUserId == null || "".equals(viewUserId)) {
      viewUserId = String.valueOf(this.getUserId(context));
    }
    SystemStatus systemStatus = this.getSystemStatus(context);
    context.getRequest().setAttribute("systemStatus", systemStatus);
    String msgId = context.getRequest().getParameter("messageId");
    String contactId = context.getRequest().getParameter("contactId");
    String bcc = context.getRequest().getParameter("bcc");
    String cc = context.getRequest().getParameter("cc");
    Message thisMessage = null;
    Exception errorMessage = null;
    Connection db = null;
    try {
      db = getConnection(context);
      Contact recipient = new Contact(db, Integer.parseInt(contactId));

      if (msgId != null && !"0".equals(msgId)) {
        thisMessage = new Message(db, Integer.parseInt(msgId));
        context.getRequest().setAttribute("Message", thisMessage);
      }
      if ("".equals(recipient.getPrimaryEmailAddress())) {
        context.getRequest().setAttribute(
            "actionError", systemStatus.getLabel(
                "object.validation.actionError.contactNoEmail"));
      }
      String messageType = (String) context.getRequest().getAttribute(
          "messageType");
      if (messageType == null || "".equals(messageType)) {
        messageType = (String) context.getRequest().getParameter(
            "messageType");
      }
      if (messageType != null && !"".equals(messageType)) {
        context.getRequest().setAttribute("messageType", messageType);
      }
      String orgId = context.getRequest().getParameter("orgId");
      if (orgId != null) {
        context.getRequest().setAttribute("orgId", orgId);
      }
      if (bcc != null && !"".equals(bcc)) {
        context.getRequest().setAttribute("bcc", bcc);
      }
      if (cc != null && !"".equals(cc)) {
        context.getRequest().setAttribute("cc", cc);
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      return getReturn(context, "PrepareMessage");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   * Sends the entered message to the action contact.
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandSendMessage(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-action-lists-edit"))) {
      return ("PermissionError");
    }
    String viewUserId = (String) context.getSession().getAttribute(
        "viewUserId");
    if (viewUserId == null || "".equals(viewUserId)) {
      viewUserId = String.valueOf(this.getUserId(context));
    }
    String msgId = context.getRequest().getParameter("id");
    String contactId = context.getRequest().getParameter("contactId");
    String actionId = context.getRequest().getParameter("actionId");
    String actionListId = context.getRequest().getParameter("actionListId");
    String bcc = context.getRequest().getParameter("bcc");
    String cc = context.getRequest().getParameter("cc");
    boolean messageSent = false;
    boolean activated = false;
    Message thisMessage = (Message) context.getFormBean();
    Connection db = null;
    Contact recipient = null;
    boolean isValid = true;
    InstantCampaign actionCampaign = new InstantCampaign();
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      db = getConnection(context);
      recipient = new Contact(db, contactId);
      //build the Action List
      ActionList actionList = new ActionList(
          db, Integer.parseInt(actionListId));
      if ("".equals(recipient.getPrimaryEmailAddress())) {
        context.getRequest().setAttribute(
            "actionError", systemStatus.getLabel(
                "object.validation.actionError.contactNoEmail"));
        isValid = false;
      }
      //check if message if valid
      if (isValid) {
        thisMessage.setDisableNameValidation(true);
        isValid = this.validateObject(context, db, thisMessage);
        //insert the message if it is not inserted yet
        if (isValid) {
          if (msgId != null && !"".equals(msgId)) {
            thisMessage.setModifiedBy(this.getUserId(context));
            thisMessage.update(db);
          } else {
            LookupList list = systemStatus.getLookupList(
                db, "lookup_access_types");
            thisMessage.setAccessType(list.getIdFromValue("Public"));
            thisMessage.setModifiedBy(this.getUserId(context));
            thisMessage.setEnteredBy(this.getUserId(context));
          }
        }
        //create an instant campaign and activate it
        actionCampaign.setName(actionList.getDescription());
        actionCampaign.setEnteredBy(this.getUserId(context));
        actionCampaign.setModifiedBy(this.getUserId(context));
        actionCampaign.addRecipient(db, Integer.parseInt(contactId));
        actionCampaign.setActiveDateTimeZone(
            this.getUser(context, this.getUserId(context)).getTimeZone());
        actionCampaign.setMessage(thisMessage);
        if (bcc != null && !"".equals(bcc)) {
          actionCampaign.setBcc(bcc);
          context.getRequest().setAttribute("bcc", bcc);
        }
        if (cc != null && !"".equals(cc)) {
          actionCampaign.setCc(cc);
          context.getRequest().setAttribute("cc", cc);
        }
        isValid = this.validateObject(context, db, actionCampaign) && isValid;
        if (isValid) {
          activated = actionCampaign.activate(db);
        }
        //log the campaign in history
        if (activated) {
          ActionItemLog thisLog = new ActionItemLog();
          thisLog.setEnteredBy(this.getUserId(context));
          thisLog.setModifiedBy(this.getUserId(context));
          thisLog.setItemId(Integer.parseInt(actionId));
          thisLog.setLinkItemId(actionCampaign.getId());
          thisLog.setType(Constants.CAMPAIGN_OBJECT);
          thisLog.insert(db);
          messageSent = true;
          actionCampaign.setContactList(actionCampaign.getRecipients());
          this.processInsertHook(context, actionCampaign);
          //build the contact for confirming message
          context.getRequest().setAttribute(
              "Recipient", new Contact(db, Integer.parseInt(contactId)));
        }
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (messageSent) {
      return getReturn(context, "SendMessage");
    }
    return executeCommandPrepareMessage(context);
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandSendAddressRequest(ActionContext context) {
    String viewUserId = (String) context.getSession().getAttribute(
        "viewUserId");
    if (viewUserId == null || "".equals(viewUserId)) {
      viewUserId = String.valueOf(this.getUserId(context));
    }
    String messageType = (String) context.getRequest().getParameter(
        "messageType");
    if (messageType != null && !"".equals(messageType)) {
      context.getRequest().setAttribute("messageType", messageType);
    }
    String msgId = context.getRequest().getParameter("id");
    String contactId = context.getRequest().getParameter("contactId");
    boolean messageSent = false;
    boolean activated = false;
    String bcc = context.getRequest().getParameter("bcc");
    String cc = context.getRequest().getParameter("cc");

    Message thisMessage = (Message) context.getFormBean();
    Connection db = null;
    boolean isValid = false;
    InstantCampaign actionCampaign = new InstantCampaign();
    SystemStatus systemStatus = this.getSystemStatus(context);
    Contact contact = null;
    try {
      db = getConnection(context);
      
      //build contact record to determine the required permission
      contact = new Contact(db, Integer.parseInt(contactId));
      // if the contact is an account contact
      if ((contact.getOrgId() != -1) && (!(hasPermission(
          context, "accounts-accounts-contact-updater-view")))) {
        return ("PermissionError");
      }
      //if the contact is a general(external)contact
      if ((contact.getOrgId() == -1) && (!(hasPermission(
          context, "contacts-external-contact-updater-view")))) {
        return ("PermissionError");
      }
      
      //build the Action List
      thisMessage.setDisableNameValidation(true);
      isValid = this.validateObject(context, db, thisMessage);
      //check if message if valid
      if (isValid) {
        //insert the message if it is not inserted yet
        if (msgId != null && !"".equals(msgId)) {
          thisMessage.setModifiedBy(this.getUserId(context));
          thisMessage.update(db);
        } else {
          LookupList list = systemStatus.getLookupList(
              db, "lookup_access_types");
          thisMessage.setAccessType(list.getIdFromValue("Public"));
          thisMessage.setModifiedBy(this.getUserId(context));
          thisMessage.setEnteredBy(this.getUserId(context));
        }
      }
      //create an instant campaign and activate it
      actionCampaign.setName("Contact Information Update Request");
      actionCampaign.setEnteredBy(this.getUserId(context));
      actionCampaign.setModifiedBy(this.getUserId(context));
      actionCampaign.addRecipient(db, Integer.parseInt(contactId));
      actionCampaign.setMessage(thisMessage);
      if (bcc != null && !"".equals(bcc)) {
        actionCampaign.setBcc(bcc);
        context.getRequest().setAttribute("bcc", bcc);
      }
      if (cc != null && !"".equals(cc)) {
        actionCampaign.setCc(cc);
        context.getRequest().setAttribute("cc", cc);
      }
      isValid = this.validateObject(context, db, actionCampaign) && isValid;
      if (isValid) {
        activated = actionCampaign.activate(db);
      }
      //log the campaign in history
      if (activated) {
        //Add the address request survey to the instant campaign
        int addressSurveyId = Survey.getAddressSurveyId(db);
        actionCampaign.setSurveyId(addressSurveyId);
        actionCampaign.setModifiedBy(this.getUserId(context));
        actionCampaign.setHasAddressRequest(true);
        actionCampaign.updateAddressRequest(db);

        Survey thisSurvey = new Survey(db, Survey.getAddressSurveyId(db));
        ActiveSurvey activeSurvey = new ActiveSurvey(thisSurvey);
        activeSurvey.setEnteredBy(this.getUserId(context));
        activeSurvey.setModifiedBy(this.getUserId(context));
        activeSurvey.setCampaignId(actionCampaign.getId());
        activeSurvey.insert(db);
        addressSurveyId = activeSurvey.getId();
        String serverName = RequestUtils.getServerUrl(context.getRequest());

        thisMessage.setMessageText(
            thisMessage.getMessageText() + "<br />" +
            "${server_name=" + serverName + "}" + "<br />" +
            "${contact_address=" + addressSurveyId + "}" + "<br />" +
            "${survey_url_address=" + addressSurveyId + "}");
        actionCampaign.updateInstantCampaignMessage(db, thisMessage);

        ContactList tempList = new ContactList();
        tempList.add(contact);
        actionCampaign.setContactList(tempList);
        this.processInsertHook(context, actionCampaign);

        messageSent = true;

        //build the contact for confirming message
        context.getRequest().setAttribute(
            "Recipient", new Contact(db, Integer.parseInt(contactId)));
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (messageSent) {
      return getReturn(context, "SendMessage");
    }
    return executeCommandPrepareMessage(context);
  }
}

