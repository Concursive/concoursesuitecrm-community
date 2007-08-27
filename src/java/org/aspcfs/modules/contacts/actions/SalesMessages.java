/*
 *  Copyright(c) 2007 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
package org.aspcfs.modules.contacts.actions;

import java.sql.Connection;

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.communications.base.Campaign;
import org.aspcfs.modules.communications.base.CampaignList;
import org.aspcfs.modules.communications.base.ContactMessageList;
import org.aspcfs.modules.communications.base.InstantCampaign;
import org.aspcfs.modules.communications.base.Message;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.RequestUtils;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.modules.actions.CFSModule;

public class SalesMessages extends CFSModule {

  /**
   *  Description of the Method
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  public String executeCommandViewMessages(ActionContext context) {
    int MINIMIZED_ITEMS_PER_PAGE = 5;

    Connection db = null;
    Contact thisContact = null;
    //sent messages
    CampaignList campaignList = new CampaignList();

    //parameters
    String contactId = context.getRequest().getParameter("contactId");
    //Add the default view
    if (context.getSession().getAttribute("contactMessageListView") == null) {
      context.getSession().setAttribute("contactMessageListView", "all");
    }
    String from = (String) context.getRequest().getParameter("from");
    context.getRequest().setAttribute("from", from);
    String listForm = (String) context.getRequest().getParameter("listForm");
    if (listForm != null && !"".equals(listForm)) {
      context.getRequest().setAttribute("listForm", listForm);
    }

    //check to see if the view needs to be changed
    String selected = context.getRequest().getParameter("listView");
    if (selected != null && !"".equals(selected)) {
      context.getSession().setAttribute("contactMessageListView", selected);
    }

    String view = (String) context.getSession().getAttribute("contactMesssageListView");
    if (view != null && !"".equals(view)) {
      context.getRequest().setAttribute("selected", view);
    } else if (selected != null && !"".equals(selected)) {
      context.getRequest().setAttribute("selected", selected);
    }

    //reset the paged lists
    if ("true".equals(context.getRequest().getParameter("resetList"))) {
      context.getSession().removeAttribute("contactSentMessageListInfo");
      context.getSession().removeAttribute("contactReceivedMessageListInfo");
    }

    //Determine the sections to view
    String sectionId = null;
    if (context.getRequest().getParameter("pagedListSectionId") != null) {
      sectionId = context.getRequest().getParameter("pagedListSectionId");
    }

    String sentPagedListId = "contactSentMessageListInfo";

    if (sectionId == null || sentPagedListId.equals(sectionId)) {
      PagedListInfo sentMessageListInfo = this.getPagedListInfo(
          context, "contactSentMessageListInfo");
      sentMessageListInfo.setLink(
          "SalesMessages.do?command=ViewMessages&contactId=" + contactId + RequestUtils.addLinkParams(
          context.getRequest(), "popup|popupType|actionId|from|listForm"));
      if (sectionId == null) {
        if (!sentMessageListInfo.getExpandedSelection()) {
          if (sentMessageListInfo.getItemsPerPage() != MINIMIZED_ITEMS_PER_PAGE) {
            sentMessageListInfo.setItemsPerPage(MINIMIZED_ITEMS_PER_PAGE);
          }
        } else {
          if (sentMessageListInfo.getItemsPerPage() == MINIMIZED_ITEMS_PER_PAGE) {
            sentMessageListInfo.setItemsPerPage(PagedListInfo.DEFAULT_ITEMS_PER_PAGE);
          }
        }
      } else if (sectionId.equals(sentMessageListInfo.getId())) {
        sentMessageListInfo.setExpandedSelection(true);
      }
      //build a list of sent messages
      campaignList.setPagedListInfo(sentMessageListInfo);
      campaignList.setCompleteOnly(true);
      campaignList.setContactId(Integer.parseInt(contactId));
      //Check the dropdown
      if ("all".equals(view)) {
        campaignList.setOwnerIdRange(this.getUserRange(context));
        campaignList.setUserGroupUserId(this.getUserId(context));
      } else {
        campaignList.setOwner(this.getUserId(context));
      }
    }

    //Received Message List
    //build a list of messages received from the contact by the
    //current logged in user and/or users in his hierarchy
    ContactMessageList receivedList = new ContactMessageList();

    String receivedPagedListId = "contactReceivedMessageListInfo";

    if (sectionId == null || receivedPagedListId.equals(sectionId)) {
      PagedListInfo receivedMessageListInfo = this.getPagedListInfo(
          context, "contactReceivedMessageListInfo");
      receivedMessageListInfo.setLink(
          "SalesMessages.do?command=ViewMessages&contactId=" + contactId + RequestUtils.addLinkParams(
          context.getRequest(), "popup|popupType|actionId|from|listForm"));
      if (sectionId == null) {
        if (!receivedMessageListInfo.getExpandedSelection()) {
          if (receivedMessageListInfo.getItemsPerPage() != MINIMIZED_ITEMS_PER_PAGE) {
            receivedMessageListInfo.setItemsPerPage(MINIMIZED_ITEMS_PER_PAGE);
          }
        } else {
          if (receivedMessageListInfo.getItemsPerPage() == MINIMIZED_ITEMS_PER_PAGE) {
            receivedMessageListInfo.setItemsPerPage(
                PagedListInfo.DEFAULT_ITEMS_PER_PAGE);
          }
        }
      } else if (sectionId.equals(receivedMessageListInfo.getId())) {
        receivedMessageListInfo.setExpandedSelection(true);
      }

      receivedList.setPagedListInfo(receivedMessageListInfo);
      if ("all".equals(view)) {
        receivedList.setReceivedByRange(this.getUserRange(context));
      } else {
        receivedList.setReceivedBy(this.getUserId(context));
      }
      receivedList.setBuildMessage(true);
    }

    try {
      db = this.getConnection(context);

      thisContact = new Contact(db, contactId);
      if (!(hasPermission(context, "sales-leads-messages-view")) || (thisContact.getOrgId() > 0 && !(hasPermission(
          context, "accounts-accounts-contacts-view")))) {
        return ("PermissionError");
      }
      if (!hasAuthority(db, context, thisContact)) {
        return ("PermissionError");
      }
      if (!isRecordAccessPermitted(context, thisContact)) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("ContactDetails", thisContact);

      if (sectionId == null || sentPagedListId.equals(sectionId)) {
        campaignList.buildList(db);
      }
      if (sectionId == null || receivedPagedListId.equals(sectionId)) {
        receivedList.setReceivedFrom(thisContact.getId());
        receivedList.buildList(db);
      }
      context.getRequest().setAttribute("campList", campaignList);
      context.getRequest().setAttribute("receivedList", receivedList);
    } catch (Exception errorMessage) {
      errorMessage.printStackTrace(System.out);
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    boolean popup = "true".equals(context.getRequest().getParameter("popup"));
    addModuleBean(context, "External Contacts", "Messages");
    if (popup) {
      return ("ViewMessagesPopupOK");
    }
    return ("ViewMessagesOK");
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandPrepareMessage(ActionContext context) {
    String actionSource = context.getRequest().getParameter("actionSource");
    boolean hasAccountContactsMessagesPermission = hasPermission(
        context, "sales-leads-messages-view");
    boolean hasExternalContactsMessagesPermission = hasPermission(
        context, "sales-leads-messages-view");
    if (actionSource != null && "AccountContactsMessages".equals(actionSource)) {
      if (!hasAccountContactsMessagesPermission) {
        return ("PermissionError");
      }
    } else {
      if (!hasExternalContactsMessagesPermission) {
        return ("PermissionError");
      }
    }
    String from = (String) context.getRequest().getParameter("from");
    context.getRequest().setAttribute("from", from);
    String listForm = (String) context.getRequest().getParameter("listForm");
    if (listForm != null && !"".equals(listForm)) {
      context.getRequest().setAttribute("listForm", listForm);
    }
    SystemStatus systemStatus = this.getSystemStatus(context);
    String msgId = context.getRequest().getParameter("messageId");
    String contactId = context.getRequest().getParameter("contactId");
    String bcc = context.getRequest().getParameter("bcc");
    String cc = context.getRequest().getParameter("cc");
    String[] attachments = context.getRequest().getParameterValues("selectedList");
    Message thisMessage = null;
    Connection db = null;
    try {
      db = getConnection(context);
      Contact recipient = new Contact(db, Integer.parseInt(contactId));
      context.getRequest().setAttribute("ContactDetails", recipient);

      if (msgId != null && !"0".equals(msgId) && !"".equals(msgId)) {
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
      if (bcc != null && !"".equals(bcc)) {
        context.getRequest().setAttribute("bcc", bcc);
      }
      if (cc != null && !"".equals(cc)) {
        context.getRequest().setAttribute("cc", cc);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "PrepareMessageOK";
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandSendMessage(ActionContext context) {
    String actionSource = context.getRequest().getParameter("actionSource");
    boolean hasAccountContactsMessagesPermission = hasPermission(
        context, "accounts-accounts-contacts-messages-view");
    boolean hasExternalContactsMessagesPermission = hasPermission(
        context, "sales-leads-messages-view");
    if (actionSource != null && "AccountContactsMessages".equals(actionSource)) {
      if (!hasAccountContactsMessagesPermission) {
        return ("PermissionError");
      }
    } else {
      if (!hasExternalContactsMessagesPermission) {
        return ("PermissionError");
      }
    }
    String from = (String) context.getRequest().getParameter("from");
    context.getRequest().setAttribute("from", from);
    String listForm = (String) context.getRequest().getParameter("listForm");
    if (listForm != null && !"".equals(listForm)) {
      context.getRequest().setAttribute("listForm", listForm);
    }
    String msgId = context.getRequest().getParameter("id");
    String contactId = context.getRequest().getParameter("contactId");
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
      context.getRequest().setAttribute("ContactDetails", recipient);
      if ("".equals(recipient.getPrimaryEmailAddress())) {
        context.getRequest().setAttribute(
            "actionError", systemStatus.getLabel(
            "object.validation.actionError.contactNoEmail"));
        isValid = false;
      }
      //check if message if valid
      if (isValid) {

        //build the Action List
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
        actionCampaign.setName(thisMessage.getMessageSubject());
        actionCampaign.setEnteredBy(this.getUserId(context));
        actionCampaign.setModifiedBy(this.getUserId(context));
        actionCampaign.addRecipient(db, Integer.parseInt(contactId));
        actionCampaign.setMessage(thisMessage);
        actionCampaign.setAttachmentList(context.getRequest().getParameterValues("selectedList"));
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
          messageSent = true;
          actionCampaign.setContactList(actionCampaign.getRecipients());
          this.processInsertHook(context, actionCampaign);
          //build the contact for confirming message
          context.getRequest().setAttribute(
              "ContactDetails", new Contact(db, Integer.parseInt(contactId)));
        }
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (messageSent) {
      if (actionSource != null && !"".equals(actionSource)) {
        return getReturn(context, "SendMessage" + actionSource);
      }
      return getReturn(context, "SendMessage");
    }
    if (actionSource != null && !"".equals(actionSource)) {
      return "SendMessage" + actionSource + "ERROR";
    }
    return executeCommandPrepareMessage(context);
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandMessageDetails(ActionContext context) {
    addModuleBean(context, "External Contacts", "Message Details");
    Connection db = null;
    Contact thisContact = null;
    Campaign campaign = null;
    String from = (String) context.getRequest().getParameter("from");
    context.getRequest().setAttribute("from", from);
    String listForm = (String) context.getRequest().getParameter("listForm");
    if (listForm != null && !"".equals(listForm)) {
      context.getRequest().setAttribute("listForm", listForm);
    }
    String campaignId = context.getRequest().getParameter("id");
    try {
      db = this.getConnection(context);
      String contactId = context.getRequest().getParameter("contactId");
      thisContact = new Contact(db, contactId);
      if (!(hasPermission(context, "sales-leads-messages-view")) || (thisContact.getOrgId() > 0 && !(hasPermission(
          context, "accounts-accounts-contacts-view")))) {
        return ("PermissionError");
      }
      if (!isRecordAccessPermitted(context, thisContact)) {
        return ("PermissionError");
      }
      campaign = new Campaign(db, campaignId);
      campaign.setBuildGroupMaps(true);
      campaign.buildUserGroupMaps(db);
      if (!hasAuthority(context, campaign.getEnteredBy()) && !hasCampaignUserGroupAccess(db, campaign.getId(), this.getUserId(context))) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("Campaign", campaign);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    boolean popup = "true".equals(context.getRequest().getParameter("popup"));
    addModuleBean(context, "External Contacts", "Messages");
    context.getRequest().setAttribute("ContactDetails", thisContact);
    if (popup) {
      return ("MessageDetailsPopupOK");
    }
    return ("MessageDetailsOK");
  }

}

