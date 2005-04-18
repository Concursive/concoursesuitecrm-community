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
package org.aspcfs.modules.accounts.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.AccessType;
import org.aspcfs.modules.admin.base.AccessTypeList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.CustomFieldRecordList;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.communications.base.Campaign;
import org.aspcfs.modules.communications.base.CampaignList;
import org.aspcfs.modules.communications.base.CommunicationsPreference;
import org.aspcfs.modules.communications.base.RecipientList;
import org.aspcfs.modules.contacts.base.*;
import org.aspcfs.modules.pipeline.base.OpportunityHeaderList;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;

/**
 *  Web actions for the Accounts->Contacts module
 *
 * @author     chris
 * @created    August 29, 2001
 * @version    $Id$
 */
public final class Contacts extends CFSModule {

  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandPrepare(ActionContext context) {
    Connection db = null;
    // Parse parameters
    Contact thisContact = (Contact) context.getFormBean();
    Organization thisOrganization = (Organization) context.getRequest().getAttribute("OrgDetails");
    String orgId = context.getRequest().getParameter("orgId");
    if (orgId == null) {
      // Passed from the account insert page
      if (thisOrganization != null) {
        orgId = String.valueOf(thisOrganization.getId());
        // TODO: This is a temporary fix because the field duplicates the org fieldname
        thisContact.setNotes(null);
      }
    }
    if (thisContact.getId() == -1) {
      if (!hasPermission(context, "accounts-accounts-contacts-add")) {
        return ("PermissionError");
      }
      addModuleBean(context, "View Accounts", "Add Contact to Account");
    }
    try {
      db = this.getConnection(context);
      //prepare org
      if (thisOrganization == null) {
        thisOrganization = new Organization(db, Integer.parseInt(orgId));
        context.getRequest().setAttribute("OrgDetails", thisOrganization);
      }
      //prepare contact type list
      SystemStatus systemStatus = this.getSystemStatus(context);
      ContactTypeList ctl = new ContactTypeList();
      ctl.setIncludeDefinedByUser(this.getUserId(context));
      ctl.setCategory(ContactType.ACCOUNT);
      ctl.buildList(db);
      ctl.addItem(0, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("ContactTypeList", ctl);

    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("PrepareOK");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandClone(ActionContext context) {
    if (!(hasPermission(context, "accounts-accounts-contacts-add"))) {
      return ("PermissionError");
    }
    addModuleBean(context, "View Accounts", "Clone Account Contact");
    Connection db = null;
    String contactId = context.getRequest().getParameter("id");
    Contact cloneContact = null;
    try {
      db = this.getConnection(context);
      cloneContact = new Contact(db, contactId);
      cloneContact.resetBaseInfo();
      context.getRequest().setAttribute("ContactDetails", cloneContact);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return executeCommandPrepare(context);
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandSave(ActionContext context) {
    boolean recordInserted = false;
    int resultCount = 0;
    boolean isValid = false;
    String permission = "accounts-accounts-contacts-add";
    Contact thisContact = (Contact) context.getFormBean();
    if (thisContact.getId() > 0) {
      permission = "accounts-accounts-contacts-edit";
    }
    if (!hasPermission(context, permission)) {
      return ("PermissionError");
    }
    addModuleBean(context, "View Accounts", "Save Contact to Account");
    Organization thisOrganization = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      boolean newContact = (thisContact.getId() == -1);
      thisContact.setRequestItems(context);
      thisContact.setTypeList(context.getRequest().getParameterValues("selectedList"));
      thisContact.setModifiedBy(getUserId(context));
      thisContact.setEnteredBy(getUserId(context));

      //make sure all can see the contact
      AccessTypeList accessTypes = this.getSystemStatus(context).getAccessTypeList(db, AccessType.ACCOUNT_CONTACTS);
      thisContact.setAccessType(accessTypes.getDefaultItem());

      if ("true".equals(context.getRequest().getParameter("primaryContact"))) {
        thisContact.setPrimaryContact(true);
      }
      isValid = validateObject(context, db, thisContact);
      if (newContact) {
        thisContact.setOwner(getUserId(context));
        if (isValid) {
          recordInserted = thisContact.insert(db);
        }
      } else {
        if (isValid) {
          resultCount = thisContact.update(db, context);
        }
      }
      // see what happened with the insert/update
      if (recordInserted || resultCount == 1) {
        thisContact = new Contact(db, thisContact.getId());
        context.getRequest().setAttribute("ContactDetails", thisContact);
        if (resultCount == 1) {
          thisContact.checkUserAccount(db);
          this.updateUserContact(db, context, thisContact.getUserId());
        }
        thisOrganization = new Organization(db, thisContact.getOrgId());
        context.getRequest().setAttribute("OrgDetails", thisOrganization);
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("orgId", "" + thisContact.getOrgId());
    if (recordInserted) {
      if ("true".equals(context.getRequest().getParameter("saveAndClone"))) {
        thisContact.resetBaseInfo();
        context.getRequest().setAttribute("ContactDetails", thisContact);
        return (executeCommandPrepare(context));
      }
      if (context.getRequest().getParameter("popup") != null) {
        return ("CloseAddPopup");
      }
      if ("true".equals(context.getRequest().getParameter("providePortalAccess"))) {
        return ("AddPortalOK");
      }
      return ("DetailsOK");
    } else if (resultCount == 1) {
      if (context.getRequest().getParameter("return") != null && context.getRequest().getParameter("return").equals("list")) {
        return (executeCommandView(context));
      } else {
        return ("UpdateOK");
      }
    } else {
      context.getRequest().setAttribute("TypeList", thisContact.getTypeList());
      return (executeCommandPrepare(context));
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandConfirmDelete(ActionContext context) {
    Connection db = null;
    Contact thisContact = null;
    HtmlDialog htmlDialog = new HtmlDialog();
    String id = null;
    String orgId = null;
    if (!(hasPermission(context, "accounts-accounts-contacts-delete"))) {
      return ("PermissionError");
    }
    if (context.getRequest().getParameter("id") != null) {
      id = context.getRequest().getParameter("id");
    }
    if (context.getRequest().getParameter("orgId") != null) {
      orgId = context.getRequest().getParameter("orgId");
    }
    try {
      db = this.getConnection(context);
      SystemStatus systemStatus = this.getSystemStatus(context);
      thisContact = new Contact(db, id);
      thisContact.checkUserAccount(db);
      DependencyList dependencies = thisContact.processDependencies(db);
      dependencies.setSystemStatus(systemStatus);
      htmlDialog.addMessage(systemStatus.getLabel("confirmdelete.caution") + "\n" + dependencies.getHtmlString());
      if (!thisContact.hasAccount()) {
        if (thisContact.getPrimaryContact()) {
          htmlDialog.setHeader(systemStatus.getLabel("confirmdelete.contactIndividualAccountHeader"));
          htmlDialog.addButton(systemStatus.getLabel("button.ok"), "javascript:parent.window.close()");
        } else if (dependencies.canDelete()) {
          htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
          htmlDialog.setHeader(systemStatus.getLabel("confirmdelete.header"));
          htmlDialog.addButton(systemStatus.getLabel("button.deleteAll"), "javascript:window.location.href='Contacts.do?command=Delete&orgId=" + orgId + "&id=" + id + "'");
          htmlDialog.addButton(systemStatus.getLabel("button.cancel"), "javascript:parent.window.close()");
        } else {
          htmlDialog.setHeader(systemStatus.getLabel("confirmdelete.unableHeader"));
          htmlDialog.addButton(systemStatus.getLabel("button.ok"), "javascript:parent.window.close()");
        }
      } else {
        htmlDialog.setHeader(systemStatus.getLabel("confirmdelete.contactUserAccountHeader"));
        htmlDialog.addButton(systemStatus.getLabel("button.ok"), "javascript:parent.window.close()");
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
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
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandDetails(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-contacts-view")) {
      return ("PermissionError");
    }
    addModuleBean(context, "View Accounts", "View Contact Details");
    String contactId = context.getRequest().getParameter("id");
    Connection db = null;
    Contact newContact = null;
    Organization thisOrganization = null;
    try {
      db = this.getConnection(context);
      newContact = new Contact(db, contactId);
      thisOrganization = new Organization(db, newContact.getOrgId());

      //find record permissions for portal users
      if (!isRecordAccessPermitted(context, newContact.getOrgId())) {
        return ("PermissionError");
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("ContactDetails", newContact);
    context.getRequest().setAttribute("OrgDetails", thisOrganization);
    return ("DetailsOK");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandDelete(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-contacts-delete")) {
      return ("PermissionError");
    }
    boolean recordDeleted = false;
    Contact thisContact = null;
    Organization thisOrganization = null;
    String orgId = null;
    if (context.getRequest().getParameter("orgId") != null) {
      orgId = context.getRequest().getParameter("orgId");
    }
    SystemStatus systemStatus = this.getSystemStatus(context);
    String actionError1 = systemStatus.getLabel("object.validation.actionError.contactDeletionActiveUser");
    String actionError2 = systemStatus.getLabel("object.validation.actionError.contactDisabledRelatedUser");
    String actionError3 = systemStatus.getLabel("object.validation.actionError.contactDisabledRelatedMessage");
    Connection db = null;
    try {
      db = this.getConnection(context);
      thisContact = new Contact(db, context.getRequest().getParameter("id"));
      recordDeleted = thisContact.delete(db);
      if (!recordDeleted) {
        if (thisContact.getHasAccess() && thisContact.getIsEnabled()) {
          thisContact.getErrors().put("actionError", actionError1);
        }
      } else {
        if ((thisContact.getHasAccess() && !thisContact.getIsEnabled()) || thisContact.hasRelatedRecords(db)) {
          thisContact.getErrors().put("actionError", actionError2);
        }
        if (RecipientList.retrieveRecordCount(db, Constants.CONTACTS, thisContact.getId()) > 0) {
          thisContact.getErrors().put("actionError", actionError3);
        }
      }
      processErrors(context, thisContact.getErrors());
      thisOrganization = new Organization(db, Integer.parseInt(orgId));
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "View Accounts", "Delete Contact");
    context.getRequest().setAttribute("orgId", orgId);
    if (recordDeleted) {
      context.getRequest().setAttribute("refreshUrl", "Contacts.do?command=View&orgId=" + orgId);
      deleteRecentItem(context, thisContact);
      return ("DeleteOK");
    } else {
      processErrors(context, thisContact.getErrors());
      return (executeCommandView(context));
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandModify(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-contacts-edit")) {
      return ("PermissionError");
    }
    addModuleBean(context, "View Accounts", "Modify Contact");
    String passedId = context.getRequest().getParameter("id");
    Connection db = null;
    Contact newContact = null;
    try {
      db = this.getConnection(context);
      newContact = (Contact) context.getFormBean();
      newContact.queryRecord(db, Integer.parseInt(passedId));
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return executeCommandPrepare(context);
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandMove(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-contacts-edit")) {
      return ("PermissionError");
    }
    Connection db = null;
    Contact thisContact = null;
    HtmlDialog htmlDialog = new HtmlDialog();
    int contactId = Integer.parseInt(context.getRequest().getParameter("id"));
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      db = this.getConnection(context);
      thisContact = new Contact(db, contactId);
      thisContact.checkUserAccount(db);
      if (thisContact.getPrimaryContact()) {
        DependencyList dependencies = thisContact.processDependencies(db);
        dependencies.setSystemStatus(systemStatus);
        htmlDialog.addMessage(systemStatus.getLabel("confirmdelete.caution") + "\n" + dependencies.getHtmlString());
        htmlDialog.setHeader(systemStatus.getLabel("accounts.contacts.contactIndividualAccountHeader"));
        htmlDialog.addButton(systemStatus.getLabel("button.ok"), "javascript:parent.window.close()");
        context.getSession().setAttribute("Dialog", htmlDialog);
        return ("ConfirmDeleteOK");
      } else if (thisContact.getOrgId() > 0) {
        int orgId = Integer.parseInt(context.getRequest().getParameter("neworgId"));
        Organization organization = new Organization(db, orgId);
        Contact.move(db, contactId, orgId, organization.getName(), this.getUserId(context));
      } else {
        return ("PermissionError");
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("MoveOK");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */

  public String executeCommandView(ActionContext context) {
    if (!(hasPermission(context, "accounts-accounts-contacts-view"))) {
      return ("PermissionError");
    }
    addModuleBean(context, "View Accounts", "View Contact Details");
    String orgid = context.getRequest().getParameter("orgId");
    if (orgid == null) {
      orgid = (String) context.getRequest().getAttribute("orgId");
    }

    //find record permissions for portal users
    if (!isRecordAccessPermitted(context, Integer.parseInt(orgid))) {
      return ("PermissionError");
    }

    PagedListInfo companyDirectoryInfo = this.getPagedListInfo(context, "ContactListInfo");
    companyDirectoryInfo.setLink("Contacts.do?command=View&orgId=" + orgid);
    Connection db = null;
    ContactList contactList = new ContactList();
    Organization thisOrganization = null;
    this.resetPagedListInfo(context);
    try {
      db = this.getConnection(context);
      contactList.setPagedListInfo(companyDirectoryInfo);
      contactList.setOrgId(Integer.parseInt(orgid));
      contactList.setBuildDetails(true);
      contactList.setBuildTypes(false);
      contactList.buildList(db);
      thisOrganization = new Organization(db, Integer.parseInt(orgid));
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("ContactList", contactList);
    context.getRequest().setAttribute("OrgDetails", thisOrganization);
    return ("ListOK");
  }


  /**
   *  View Message Details of an Account Contact
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandMessageDetails(ActionContext context) {

    if (!(hasPermission(context, "accounts-accounts-contacts-messages-view"))) {
      return ("PermissionError");
    }

    addModuleBean(context, "View Accounts", "View Contact Details");

    Connection db = null;
    Organization thisOrganization = null;
    Contact thisContact = null;
    Campaign campaign = null;

    String campaignId = context.getRequest().getParameter("id");

    try {
      db = this.getConnection(context);
      String contactId = context.getRequest().getParameter("contactId");
      thisContact = new Contact(db, contactId);
      campaign = new Campaign(db, campaignId);
      context.getRequest().setAttribute("Campaign", campaign);

      thisOrganization = new Organization(db, thisContact.getOrgId());
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }

    if (!hasAuthority(context, campaign.getEnteredBy())) {
      return ("PermissionError");
    }

    context.getRequest().setAttribute("ContactDetails", thisContact);
    context.getRequest().setAttribute("OrgDetails", thisOrganization);
    return getReturn(context, "MessageDetails");
  }


  /**
   *  View Account Contact Messages
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandViewMessages(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-contacts-messages-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    Organization thisOrganization = null;
    Contact thisContact = null;
    // Process the request
    String contactId = context.getRequest().getParameter("contactId");
    if ("true".equals(context.getRequest().getParameter("contactId"))) {
      context.getSession().removeAttribute("AccountContactMessageListInfo");
    }
    PagedListInfo pagedListInfo = this.getPagedListInfo(context, "AccountContactMessageListInfo");
    pagedListInfo.setLink("Contacts.do?command=ViewMessages&contactId=" + contactId);
    try {
      db = this.getConnection(context);
      thisContact = new Contact(db, contactId);
      if (!hasAuthority(db, context, thisContact)) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("ContactDetails", thisContact);

      CampaignList campaignList = new CampaignList();
      campaignList.setPagedListInfo(pagedListInfo);
      campaignList.setCompleteOnly(true);
      campaignList.setContactId(Integer.parseInt(contactId));

      if ("all".equals(pagedListInfo.getListView())) {
        campaignList.setOwnerIdRange(this.getUserRange(context));
      } else {
        campaignList.setOwner(this.getUserId(context));
      }

      campaignList.buildList(db);
      context.getRequest().setAttribute("CampaignList", campaignList);
      //TODO: Database not implemented in this version so skip this code
      /*
       *  CommunicationsPreferenceList commPrefList = new CommunicationsPreferenceList();
       *  commPrefList.setContactId(contactId);
       *  commPrefList.buildList(db);
       *  context.getRequest().setAttribute("communicationsList", commPrefList);
       *  SystemStatus systemStatus = this.getSystemStatus(context);
       *  LookupList list = systemStatus.getLookupList(db, "lookup_communication_type");
       *  context.getRequest().setAttribute("typeSelect", list);
       */
      thisOrganization = new Organization(db, thisContact.getOrgId());
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "View Accounts", "View Contact Details");
    context.getRequest().setAttribute("OrgDetails", thisOrganization);
    return getReturn(context, "ViewMessages");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandAddCommunicationsPreference(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-contacts-messages-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    Organization thisOrganization = null;
    Contact thisContact = null;
    // Process the request
    String contactId = context.getRequest().getParameter("contactId");
    if ("true".equals(context.getRequest().getParameter("contactId"))) {
      context.getSession().removeAttribute("AccountContactMessageListInfo");
    }
    try {
      db = this.getConnection(context);
      thisContact = new Contact(db, contactId);
      if (!hasAuthority(db, context, thisContact)) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("ContactDetails", thisContact);

      SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList list = systemStatus.getLookupList(db, "lookup_communication_type");
      context.getRequest().setAttribute("typeSelect", list);
      context.getRequest().setAttribute("User", this.getUser(context, this.getUserId(context)));
      thisOrganization = new Organization(db, thisContact.getOrgId());
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "View Accounts", "Add Contact Communication Preference");
    context.getRequest().setAttribute("OrgDetails", thisOrganization);
    return "AddCommunicationsPreferenceOK";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandSaveCommunicationsPreference(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-contacts-messages-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    Organization thisOrganization = null;
    Contact thisContact = null;
    // Process the request
    String contactId = context.getRequest().getParameter("contactId");
    if ("true".equals(context.getRequest().getParameter("contactId"))) {
      context.getSession().removeAttribute("AccountContactMessageListInfo");
    }
    try {
      db = this.getConnection(context);
      thisContact = new Contact(db, contactId);
      if (!hasAuthority(db, context, thisContact)) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("ContactDetails", thisContact);

      SystemStatus systemStatus = this.getSystemStatus(context);
      systemStatus.getLookupList(db, "lookup_communication_type");

      thisOrganization = new Organization(db, thisContact.getOrgId());
      CommunicationsPreference commPref = new CommunicationsPreference();
      commPref.setStartDay((String) context.getRequest().getParameter("startDay"));
      commPref.setEndDay((String) context.getRequest().getParameter("endDay"));
      commPref.setStartTimeHour((String) context.getRequest().getParameter("startTimeHour"), (String) context.getRequest().getParameter("startTimeAMPM"));
      commPref.setEndTimeHour((String) context.getRequest().getParameter("endTimeHour"), (String) context.getRequest().getParameter("endTimeAMPM"));
      commPref.setStartTimeMinute((String) context.getRequest().getParameter("startTimeMinute"));
      commPref.setEndTimeMinute((String) context.getRequest().getParameter("endTimeMinute"));
      commPref.setTimeZone((String) context.getRequest().getParameter("timeZone"));
      commPref.setTypeId((String) context.getRequest().getParameter("typeId"));
      commPref.setLevel((String) context.getRequest().getParameter("level"));
      commPref.setContactId(contactId);
      commPref.setEnteredBy(this.getUserId(context));
      commPref.setModifiedBy(this.getUserId(context));
      commPref.insert(db);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "View Accounts", "Add Contact Communication Preference");
    context.getRequest().setAttribute("OrgDetails", thisOrganization);
    return "SaveCommunicationsPreferenceOK";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDeleteCommunicationsPreference(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-contacts-messages-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    Organization thisOrganization = null;
    Contact thisContact = null;
    // Process the request
    String contactId = context.getRequest().getParameter("contactId");
    if ("true".equals(context.getRequest().getParameter("contactId"))) {
      context.getSession().removeAttribute("AccountContactMessageListInfo");
    }
    String preferenceId = (String) context.getRequest().getParameter("preferenceId");
    if (preferenceId == null) {
      preferenceId = (String) context.getRequest().getAttribute("preferenceId");
    }
    try {
      db = this.getConnection(context);
      thisContact = new Contact(db, contactId);
      if (!hasAuthority(db, context, thisContact)) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("ContactDetails", thisContact);

      SystemStatus systemStatus = this.getSystemStatus(context);
      systemStatus.getLookupList(db, "lookup_communication_type");

      thisOrganization = new Organization(db, thisContact.getOrgId());

      CommunicationsPreference commPref = new CommunicationsPreference();
      commPref.queryRecord(db, Integer.parseInt(preferenceId));
      commPref.delete(db);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "View Accounts", "Add Contact Communication Preference");
    context.getRequest().setAttribute("OrgDetails", thisOrganization);
    return "DeleteCommunicationsPreferenceOK";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   */
  private void resetPagedListInfo(ActionContext context) {
    this.deletePagedListInfo(context, "AccountContactCallsListInfo");
    this.deletePagedListInfo(context, "AccountContactOppsPagedListInfo");
    this.deletePagedListInfo(context, "AccountContactMessageListInfo");
    this.deletePagedListInfo(context, "AccountContactCommunicationsListInfo");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandMoveToAccount(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-contacts-move-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    Contact thisContact = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    HtmlDialog htmlDialog = new HtmlDialog();
    int contactId = Integer.parseInt(context.getRequest().getParameter("id"));
    try {
      db = this.getConnection(context);
      thisContact = new Contact(db, contactId);
      thisContact.checkUserAccount(db);
      DependencyList dependencies = thisContact.processDependencies(db);
      dependencies.setSystemStatus(systemStatus);
      htmlDialog.addMessage(systemStatus.getLabel("confirmdelete.caution") + "\n" + thisContact.getHtmlString(dependencies, systemStatus));
      if (thisContact.getPrimaryContact()) {
        htmlDialog.setHeader(systemStatus.getLabel("accounts.contacts.contactIndividualAccount.title"));
        htmlDialog.addButton(systemStatus.getLabel("button.ok"), "javascript:parent.window.close()");
        context.getSession().setAttribute("Dialog", htmlDialog);
        return ("ConfirmDeleteOK");
      } else if (!thisContact.canMoveContact(dependencies)) {
        htmlDialog.setHeader(systemStatus.getLabel("accounts.contacts.unableToMoveHeader"));
        htmlDialog.addButton(systemStatus.getLabel("button.ok"), "javascript:parent.window.close()");
        context.getSession().setAttribute("Dialog", htmlDialog);
        return ("ConfirmDeleteOK");
      } else {
        //Contact can be moved. Display the jsp to move the contact to a different account
        context.getRequest().setAttribute("ContactDetails", thisContact);
        context.getRequest().setAttribute("dependencies", dependencies);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("MoveToAccountOK");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandMoveContact(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-contacts-move-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    Contact thisContact = null;
    Organization organization = null;
    int contactId = Integer.parseInt(context.getRequest().getParameter("id"));
    int moveOpportunities = Integer.parseInt(context.getRequest().getParameter("moveOpportunities"));
    int moveFolders = Integer.parseInt(context.getRequest().getParameter("moveFolders"));
    int moveActivities = Integer.parseInt(context.getRequest().getParameter("moveActivities"));
    int orgId = Integer.parseInt(context.getRequest().getParameter("neworgId"));
    try {
      db = this.getConnection(context);
      thisContact = new Contact(db, contactId);
      organization = new Organization(db, orgId);
      OpportunityHeaderList opportunities = new OpportunityHeaderList();
      opportunities.setContactId(contactId);
      opportunities.buildList(db);
      if (opportunities.size() > 0) {
        if (moveOpportunities == Constants.FALSE) {
          opportunities.delete(db);
        } else if (moveOpportunities == Constants.TRUE) {
          opportunities.moveOpportunitiesToAccount(db, thisContact.getOrgId());
        }
      }
      if (moveFolders == Constants.FALSE) {
        CustomFieldRecordList folderList = new CustomFieldRecordList();
        folderList.setLinkModuleId(Constants.CONTACTS);
        folderList.setLinkItemId(thisContact.getId());
        folderList.buildList(db);
        if (folderList.size() > 0) {
          folderList.delete(db);
        }
      }
      CallList activities = new CallList();
      activities.setContactId(contactId);
      activities.buildList(db);
      if (activities.size() > 0) {
        if (moveActivities == Constants.TRUE ) {
          activities.reassignAccount(db, context, orgId);
        } else if (moveActivities == Constants.FALSE) {
          activities.delete(db);
        }
      }
      Contact.move(db, contactId, orgId, organization.getName(), this.getUserId(context));
      thisContact.queryRecord(db, contactId);
      context.getRequest().setAttribute("ContactDetails", thisContact);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("MoveContactOK");
  }

}

