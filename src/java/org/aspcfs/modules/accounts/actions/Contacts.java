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
package org.aspcfs.modules.accounts.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.accounts.base.OrganizationHistoryList;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.AccessType;
import org.aspcfs.modules.admin.base.AccessTypeList;
import org.aspcfs.modules.base.*;
import org.aspcfs.modules.communications.base.Campaign;
import org.aspcfs.modules.communications.base.CampaignList;
import org.aspcfs.modules.communications.base.CommunicationsPreference;
import org.aspcfs.modules.contacts.base.*;
import org.aspcfs.modules.pipeline.base.OpportunityHeaderList;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.StateSelect;
import org.aspcfs.utils.web.RequestUtils;

import java.sql.Connection;
import java.util.Iterator;

/**
 *  Web actions for the Accounts->Contacts module
 *
 * @author     chris
 * @created    August 29, 2001
 * @version    $Id: Contacts.java 14240 2006-02-14 13:28:00 -0500 (Tue, 14 Feb
 *      2006) partha@darkhorseventures.com $
 */
public final class Contacts extends CFSModule {

  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandPrepare(ActionContext context) {
    Connection db = null;
    // Parse parameters
    Contact thisContact = (Contact) context.getFormBean();
    Organization thisOrganization = (Organization) context.getRequest().getAttribute(
        "OrgDetails");
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
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      db = this.getConnection(context);
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, Integer.parseInt(orgId))) {
        return ("PermissionError");
      }

      //prepare org
      if (thisOrganization == null) {
        thisOrganization = new Organization(db, Integer.parseInt(orgId));
        context.getRequest().setAttribute("OrgDetails", thisOrganization);
      }
      //prepare contact type list

      LookupList salutationList = new LookupList(db, "lookup_title");
      salutationList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("SalutationList", salutationList);

      ContactTypeList ctl = new ContactTypeList();
      ctl.setIncludeDefinedByUser(this.getUserId(context));
      ctl.setCategory(ContactType.ACCOUNT);
      ctl.buildList(db);
      ctl.addItem(0, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("ContactTypeList", ctl);

      if ("add_contact".equals(context.getRequest().getParameter("target"))) {
        if ("true".equals(context.getRequest().getParameter("copyAddress"))) {
          thisContact.setRequestItems(context);
        }
      }

      context.getRequest().setAttribute("systemStatus", systemStatus);
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
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
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
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, cloneContact.getOrgId())) {
        return ("PermissionError");
      }
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
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
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
    Contact oldContact = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, thisContact.getOrgId())) {
        return ("PermissionError");
      }
      boolean newContact = (thisContact.getId() == -1);
      thisContact.setRequestItems(context);
      thisContact.setTypeList(
          context.getRequest().getParameterValues("selectedList"));
      thisContact.setModifiedBy(getUserId(context));
      thisContact.setEnteredBy(getUserId(context));

      //make sure all can see the contact
      AccessTypeList accessTypes = this.getSystemStatus(context).getAccessTypeList(
          db, AccessType.ACCOUNT_CONTACTS);
      thisContact.setAccessType(accessTypes.getDefaultItem());
      if (context.getRequest().getParameter("primaryContact") != null &&
          "true".equals(context.getRequest().getParameter("primaryContact"))) {
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
          oldContact = new Contact(db, thisContact.getId());
          resultCount = thisContact.update(db, context);
        }
      }
      // see what happened with the insert/update
      if (recordInserted || resultCount == 1) {
        if (recordInserted) {
          processInsertHook(context, thisContact);
        } else if (resultCount == 1) {
          processUpdateHook(context, oldContact, thisContact);
        }
        thisContact = new Contact(db, thisContact.getId());
        context.getRequest().setAttribute("ContactDetails", thisContact);
        if (resultCount == 1) {
          thisContact.checkUserAccount(db);
          this.updateUserContact(db, context, thisContact.getUserId());
        }
        thisOrganization = new Organization(db, thisContact.getOrgId());
        context.getRequest().setAttribute("OrgDetails", thisOrganization);
        context.getRequest().setAttribute("id", String.valueOf(thisContact.getId()));
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("orgId", "" + thisContact.getOrgId());
    if (recordInserted) {
      this.addRecentItem(context, thisContact);
      if ("true".equals(context.getRequest().getParameter("saveAndClone"))) {
        thisContact.resetBaseInfo();
        context.getRequest().setAttribute("ContactDetails", thisContact);
        return (executeCommandPrepare(context));
      }
      String hiddensource = context.getParameter("hiddensource");
      if (context.getRequest().getParameter("popup") != null && hiddensource != null && !"".equals(hiddensource.trim())) {
        return ("CloseAddPopup");
      }
      if ("true".equals(
          context.getRequest().getParameter("providePortalAccess"))) {
        return ("AddPortalOK");
      }
      return getReturn(context, "Details");
    } else if (resultCount == 1) {
      String source = context.getRequest().getParameter("source");
      if (context.getRequest().getParameter("popup") != null && source != null && "attachplan".equals(source.trim())) {
        return ("CloseAddPopup");
      }
      if (context.getRequest().getParameter("return") != null && context.getRequest().getParameter(
          "return").equals("list")) {
        return (executeCommandView(context));
      } else {
        return "UpdateOK";
      }
    } else {
      context.getRequest().setAttribute("TypeList", thisContact.getTypeList());
      return (executeCommandPrepare(context));
    }
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
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
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, thisContact.getOrgId())) {
        return ("PermissionError");
      }
      thisContact.checkUserAccount(db);
      DependencyList dependencies = thisContact.processDependencies(db);
      dependencies.setSystemStatus(systemStatus);
      htmlDialog.addMessage(
          systemStatus.getLabel("confirmdelete.caution") + "\n" + dependencies.getHtmlString());
      if (thisContact.getPrimaryContact()) {
        htmlDialog.setHeader(
            systemStatus.getLabel(
            "confirmdelete.contactIndividualAccountHeader"));
      } else {
        htmlDialog.setHeader(systemStatus.getLabel("confirmdelete.header"));
      }
      htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
      htmlDialog.addButton(
          systemStatus.getLabel("button.delete"), "javascript:window.location.href='Contacts.do?command=Trash&orgId=" + orgId + "&id=" + id + RequestUtils.addLinkParams(
                context.getRequest(), "popup|accountpopup") + "'");
      htmlDialog.addButton(
          systemStatus.getLabel("button.cancel"), "javascript:parent.window.close()");
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
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  public String executeCommandDetails(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-contacts-view")) {
      return ("PermissionError");
    }
    addModuleBean(context, "View Accounts", "View Contact Details");
    String contactId = context.getRequest().getParameter("id");
    if (contactId == null) {
      contactId = (String) context.getRequest().getAttribute("id");
    }
    Connection db = null;
    Contact newContact = null;
    Organization thisOrganization = null;
    try {
      db = this.getConnection(context);
      newContact = new Contact(db, contactId);
      thisOrganization = new Organization(db, newContact.getOrgId());

      //find record permissions for portal users
      if (!isRecordAccessPermitted(context, db, newContact.getOrgId())) {
        return ("PermissionError");
      }

      SystemStatus systemStatus = this.getSystemStatus(context);
      addFormElements(context, db, systemStatus, newContact);
      this.addRecentItem(context, newContact);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("ContactDetails", newContact);
    context.getRequest().setAttribute("OrgDetails", thisOrganization);
    // context.getRequest().setAttribute("lastAction", context.getRequest().getAttribute("moduleAction"));
    return getReturn(context, "Details");
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  public String executeCommandDelete(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-contacts-delete")) {
      return ("PermissionError");
    }
    boolean recordDeleted = false;
    boolean accountpopup = false;
    if (context.getRequest().getParameter("accountpopup") != null && "true".equals(context.getRequest().getParameter("accountpopup"))) {
      accountpopup = true;
    }
    Contact thisContact = null;
    Organization thisOrganization = null;
    String orgId = null;
    if (context.getRequest().getParameter("orgId") != null) {
      orgId = context.getRequest().getParameter("orgId");
    }
    Connection db = null;
    try {
      db = this.getConnection(context);
      thisContact = new Contact(db, context.getRequest().getParameter("id"));
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, thisContact.getOrgId())) {
        return ("PermissionError");
      }
      //thisContact.setForceDelete(DatabaseUtils.parseBoolean((String) context.getRequest().getParameter("forceDelete")));
      recordDeleted = thisContact.delete(db, getDbNamePath(context));
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
      context.getRequest().setAttribute(
          "refreshUrl", "Contacts.do?command=View&orgId=" + orgId+ (accountpopup?"&popup=true":""));
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
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandTrash(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-contacts-delete")) {
      return ("PermissionError");
    }
    boolean recordUpdated = false;
    Contact thisContact = null;
    Organization thisOrganization = null;
    String orgId = null;
    if (context.getRequest().getParameter("orgId") != null) {
      orgId = context.getRequest().getParameter("orgId");
    }
    boolean accountpopup = false;
    if (context.getRequest().getParameter("accountpopup") != null && "true".equals(context.getRequest().getParameter("accountpopup"))) {
      accountpopup = true;
    }
    Connection db = null;
    try {
      db = this.getConnection(context);
      thisContact = new Contact(db, context.getRequest().getParameter("id"));
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, thisContact.getOrgId())) {
        return ("PermissionError");
      }
      recordUpdated = thisContact.updateStatus(
          db, context, true, this.getUserId(context));
      this.invalidateUserData(context, this.getUserId(context));
      this.invalidateUserData(context, thisContact.getOwner());

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
    if (recordUpdated) {
      context.getRequest().setAttribute(
          "refreshUrl", "Contacts.do?command=View&orgId=" + orgId+ (accountpopup?"&popup=true":""));
      deleteRecentItem(context, thisContact);
    }
    return ("DeleteOK");
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandRestore(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-contacts-delete")) {
      return ("PermissionError");
    }
    boolean recordUpdated = false;
    Contact thisContact = null;
    Organization thisOrganization = null;
    String orgId = null;
    if (context.getRequest().getParameter("orgId") != null) {
      orgId = context.getRequest().getParameter("orgId");
    }
    Connection db = null;
    try {
      db = this.getConnection(context);
      thisContact = new Contact(db, context.getRequest().getParameter("id"));
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, thisContact.getOrgId())) {
        return ("PermissionError");
      }
      recordUpdated = thisContact.updateStatus(
          db, context, false, this.getUserId(context));
      this.invalidateUserData(context, this.getUserId(context));
      this.invalidateUserData(context, thisContact.getOwner());

      thisOrganization = new Organization(db, Integer.parseInt(orgId));
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
      context.getRequest().setAttribute("orgId", orgId);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if ((recordUpdated) && (context.getRequest().getParameter("return") != null && context.getRequest().getParameter(
        "return").equals("list"))) {
      return (executeCommandView(context));
    } else {
      return ("UpdateOK");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandModify(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-contacts-edit")) {
      return ("PermissionError");
    }
    addModuleBean(context, "View Accounts", "Modify Contact");
    String passedId = context.getRequest().getParameter("id");
    Connection db = null;
    Contact newContact = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      db = this.getConnection(context);
      newContact = (Contact) context.getFormBean();
      newContact.queryRecord(db, Integer.parseInt(passedId));
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, newContact.getOrgId())) {
        return ("PermissionError");
      }
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
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
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
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, thisContact.getOrgId())) {
        return ("PermissionError");
      }
      thisContact.checkUserAccount(db);
      if (thisContact.getPrimaryContact()) {
        DependencyList dependencies = thisContact.processDependencies(db);
        dependencies.setSystemStatus(systemStatus);
        htmlDialog.addMessage(
            systemStatus.getLabel("confirmdelete.caution") + "\n" + dependencies.getHtmlString());
        htmlDialog.setHeader(
            systemStatus.getLabel(
            "accounts.contacts.contactIndividualAccountHeader"));
        htmlDialog.addButton(
            systemStatus.getLabel("button.ok"), "javascript:parent.window.close()");
        context.getSession().setAttribute("Dialog", htmlDialog);
        return ("ConfirmDeleteOK");
      } else if (thisContact.getOrgId() > 0) {
        int orgId = Integer.parseInt(
            context.getRequest().getParameter("neworgId"));
        Organization organization = new Organization(db, orgId);
        Contact.move(
            db, contactId, orgId, organization.getName(), this.getUserId(
            context));
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
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
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
    String isPopup = context.getRequest().getParameter("popup");
    PagedListInfo companyDirectoryInfo = this.getPagedListInfo(
        context, "ContactListInfo");
    companyDirectoryInfo.setLink("Contacts.do?command=View&orgId=" + orgid+(isPopup != null && "true".equals(isPopup.trim()) ? "&popup=true":""));
    Connection db = null;
    ContactList contactList = new ContactList();
    Organization thisOrganization = null;
    this.resetPagedListInfo(context);
    try {
      db = this.getConnection(context);
      //find record permissions for portal users
      if (!isRecordAccessPermitted(context, db, Integer.parseInt(orgid))) {
        return ("PermissionError");
      }
      thisOrganization = new Organization(db, Integer.parseInt(orgid));

      contactList.setPagedListInfo(companyDirectoryInfo);
      contactList.setOrgId(Integer.parseInt(orgid));
      contactList.setBuildDetails(true);
      contactList.setBuildTypes(false);
      if (thisOrganization.getTrashedDate() != null) {
        contactList.setIncludeOnlyTrashed(true);
      }
      contactList.buildList(db);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("ContactList", contactList);
    context.getRequest().setAttribute("OrgDetails", thisOrganization);
    return getReturn(context, "List");
  }


  /**
   *  View Message Details of an Account Contact
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
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
      if (!hasAuthority(context, campaign.getEnteredBy()) && !hasCampaignUserGroupAccess(db, campaign.getId(), this.getUserId(context))) {
        return ("PermissionError");
      }
      thisOrganization = new Organization(db, thisContact.getOrgId());
      if (!isRecordAccessPermitted(context, db, thisContact.getOrgId())) {
        return ("PermissionError");
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }

    context.getRequest().setAttribute("ContactDetails", thisContact);
    context.getRequest().setAttribute("OrgDetails", thisOrganization);
    return getReturn(context, "MessageDetails");
  }


  /**
   *  View Account Contact Messages
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
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
    String popup = context.getRequest().getParameter("popup");
    PagedListInfo pagedListInfo = this.getPagedListInfo(
        context, "AccountContactMessageListInfo");
    pagedListInfo.setLink(
        "Contacts.do?command=ViewMessages&contactId=" + contactId+
        (popup != null && "true".equals(popup.trim())?"&popup=true":""));
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
        campaignList.setUserGroupUserId(this.getUserId(context));
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
      if (!isRecordAccessPermitted(context, db, thisContact.getOrgId())) {
        return ("PermissionError");
      }
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
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
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
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, thisContact.getOrgId())) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("ContactDetails", thisContact);

      SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList list = systemStatus.getLookupList(
          db, "lookup_communication_type");
      context.getRequest().setAttribute("typeSelect", list);
      context.getRequest().setAttribute(
          "User", this.getUser(context, this.getUserId(context)));
      thisOrganization = new Organization(db, thisContact.getOrgId());
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(
        context, "View Accounts", "Add Contact Communication Preference");
    context.getRequest().setAttribute("OrgDetails", thisOrganization);
    context.getRequest().setAttribute(
        "systemStatus", this.getSystemStatus(context));
    return "AddCommunicationsPreferenceOK";
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
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
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, thisContact.getOrgId())) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("ContactDetails", thisContact);

      SystemStatus systemStatus = this.getSystemStatus(context);
      systemStatus.getLookupList(db, "lookup_communication_type");

      thisOrganization = new Organization(db, thisContact.getOrgId());
      CommunicationsPreference commPref = new CommunicationsPreference();
      commPref.setStartDay(
          (String) context.getRequest().getParameter("startDay"));
      commPref.setEndDay((String) context.getRequest().getParameter("endDay"));
      commPref.setStartTimeHour(
          (String) context.getRequest().getParameter("startTimeHour"), (String) context.getRequest().getParameter(
          "startTimeAMPM"));
      commPref.setEndTimeHour(
          (String) context.getRequest().getParameter("endTimeHour"), (String) context.getRequest().getParameter(
          "endTimeAMPM"));
      commPref.setStartTimeMinute(
          (String) context.getRequest().getParameter("startTimeMinute"));
      commPref.setEndTimeMinute(
          (String) context.getRequest().getParameter("endTimeMinute"));
      commPref.setTimeZone(
          (String) context.getRequest().getParameter("timeZone"));
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
    addModuleBean(
        context, "View Accounts", "Add Contact Communication Preference");
    context.getRequest().setAttribute("OrgDetails", thisOrganization);
    return "SaveCommunicationsPreferenceOK";
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
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
    String preferenceId = (String) context.getRequest().getParameter(
        "preferenceId");
    if (preferenceId == null) {
      preferenceId = (String) context.getRequest().getAttribute(
          "preferenceId");
    }
    try {
      db = this.getConnection(context);
      thisContact = new Contact(db, contactId);
      if (!hasAuthority(db, context, thisContact)) {
        return ("PermissionError");
      }
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, thisContact.getOrgId())) {
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
    addModuleBean(
        context, "View Accounts", "Add Contact Communication Preference");
    context.getRequest().setAttribute("OrgDetails", thisOrganization);
    return "DeleteCommunicationsPreferenceOK";
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
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
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
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
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, thisContact.getOrgId())) {
        return ("PermissionError");
      }
      thisContact.checkUserAccount(db);
      DependencyList dependencies = thisContact.processDependencies(db);
      dependencies.setSystemStatus(systemStatus);
      htmlDialog.addMessage(
          systemStatus.getLabel("confirmdelete.caution") + "\n" + thisContact.getHtmlString(
          dependencies, systemStatus));
      if (thisContact.getPrimaryContact()) {
        htmlDialog.setHeader(
            systemStatus.getLabel(
            "accounts.contacts.contactIndividualAccount.title"));
        htmlDialog.addButton(
            systemStatus.getLabel("button.ok"), "javascript:parent.window.close()");
        context.getSession().setAttribute("Dialog", htmlDialog);
        return ("ConfirmDeleteOK");
      } else if (!thisContact.canMoveContact(dependencies)) {
        htmlDialog.setHeader(
            systemStatus.getLabel("accounts.contacts.unableToMoveHeader"));
        htmlDialog.addButton(
            systemStatus.getLabel("button.ok"), "javascript:parent.window.close()");
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
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandMoveContact(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-contacts-move-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    Contact thisContact = null;
    Organization organization = null;
    int contactId = Integer.parseInt(context.getRequest().getParameter("id"));
    int moveOpportunities = Integer.parseInt(
        context.getRequest().getParameter("moveOpportunities"));
    int moveFolders = Integer.parseInt(
        context.getRequest().getParameter("moveFolders"));
    int moveActivities = Integer.parseInt(
        context.getRequest().getParameter("moveActivities"));
    int orgId = Integer.parseInt(
        context.getRequest().getParameter("neworgId"));
    try {
      db = this.getConnection(context);
      thisContact = new Contact(db, contactId);
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, thisContact.getOrgId())) {
        return ("PermissionError");
      }
      thisContact.setModifiedBy(this.getUserId(context));
      thisContact.moveContact(db, context, getDbNamePath(context), orgId, moveOpportunities, moveFolders, moveActivities);
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


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandPrepareMessage(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-contacts-messages-view")) {
      return ("PermissionError");
    }
    String contactId = context.getRequest().getParameter("contactId");
    Contact contact = null;
    Organization orgDetails = null;
    Connection db = null;
    try {
      db = getConnection(context);
      contact = new Contact(db, Integer.parseInt(contactId));
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, contact.getOrgId())) {
        return ("PermissionError");
      }
      orgDetails = new Organization(db, contact.getOrgId());
      context.getRequest().setAttribute("OrgDetails", orgDetails);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "CreateMessageOK";
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandSendMessage(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-contacts-messages-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    Contact recipient = null;
    Organization orgDetails = null;
    String contactId = context.getRequest().getParameter("contactId");
    try {
      db = getConnection(context);
      recipient = new Contact(db, Integer.parseInt(contactId));
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, recipient.getOrgId())) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("ContactDetails", recipient);
      orgDetails = new Organization(db, recipient.getOrgId());
      context.getRequest().setAttribute("OrgDetails", orgDetails);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "SendMessageOK";
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandAddFolderRecord(ActionContext context) {
    Connection db = null;
    Contact thisContact = null;
    Organization thisOrganization = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    context.getRequest().setAttribute("systemStatus", systemStatus);
    try {
      String contactId = context.getRequest().getParameter("contactId");
      db = this.getConnection(context);
      thisContact = new Contact(db, contactId);

      if (!(hasPermission(context, "accounts-accounts-contacts-folders-add")) || (thisContact.getOrgId() > 0 && !(hasPermission(context, "accounts-accounts-contacts-edit")))) {
        return ("PermissionError");
      }
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, thisContact.getOrgId())) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("ContactDetails", thisContact);

      String selectedCatId = (String) context.getRequest().getParameter("catId");
      CustomFieldCategory thisCategory = new CustomFieldCategory(
          db,
          Integer.parseInt(selectedCatId));
      thisCategory.setLinkModuleId(Constants.CONTACTS);
      thisCategory.setLinkItemId(thisContact.getId());
      thisCategory.setIncludeEnabled(Constants.TRUE);
      thisCategory.setIncludeScheduled(Constants.TRUE);
      thisCategory.setBuildResources(true);
      thisCategory.buildResources(db);
      context.getRequest().setAttribute("Category", thisCategory);

      thisOrganization = new Organization(db, thisContact.getOrgId());
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "External Contacts", "Add Folder Record");
    return this.getReturn(context, "AddFolderRecord");
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandFolderList(ActionContext context) {
    if (!(hasPermission(context, "accounts-accounts-contacts-folders-view"))) {
      return ("PermissionError");
    }
    Connection db = null;
    Contact thisContact = null;
    Organization thisOrganization = null;
    try {
      String contactId = context.getRequest().getParameter("contactId");
      db = this.getConnection(context);
      thisContact = new Contact(db, Integer.parseInt(contactId));
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, thisContact.getOrgId())) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("ContactDetails", thisContact);
      //Show a list of the different folders available in Accounts
      CustomFieldCategoryList thisList = new CustomFieldCategoryList();
      thisList.setLinkModuleId(Constants.CONTACTS);
      thisList.setLinkItemId(thisContact.getId());
      thisList.setIncludeEnabled(Constants.TRUE);
      thisList.setIncludeScheduled(Constants.TRUE);
      thisList.setBuildResources(false);
      thisList.setBuildTotalNumOfRecords(true);
      thisList.buildList(db);
      context.getRequest().setAttribute("CategoryList", thisList);

      //load the account
      thisOrganization = new Organization(db, thisContact.getOrgId());
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Contacts", "Custom Fields Details");

    return getReturn(context, "FolderList");
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandFields(ActionContext context) {
    Connection db = null;
    Contact thisContact = null;
    String recordId = null;
    boolean showRecords = true;
    String selectedCatId = null;
    Organization thisOrganization = null;

    try {
      String contactId = context.getRequest().getParameter("contactId");
      db = this.getConnection(context);
      thisContact = new Contact(db, contactId);

      if (!(hasPermission(context, "accounts-accounts-contacts-folders-view")) || (thisContact.getOrgId() > 0 && !(hasPermission(context, "accounts-accounts-contacts-view")))) {
        return ("PermissionError");
      }
      if (!hasAuthority(db, context, thisContact)) {
        return ("PermissionError");
      }
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, thisContact.getOrgId())) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("ContactDetails", thisContact);

      //Show a list of the different folders available in Contacts
      CustomFieldCategoryList thisList = new CustomFieldCategoryList();
      thisList.setLinkModuleId(Constants.CONTACTS);
      thisList.setIncludeEnabled(Constants.TRUE);
      thisList.setIncludeScheduled(Constants.TRUE);
      thisList.setBuildResources(false);
      thisList.buildList(db);
      context.getRequest().setAttribute("CategoryList", thisList);

      //See which one is currently selected or use the default
      selectedCatId = (String) context.getRequest().getParameter("catId");
      if (selectedCatId == null) {
        selectedCatId = (String) context.getRequest().getAttribute("catId");
      }
      if (selectedCatId == null) {
        selectedCatId = String.valueOf(thisList.getDefaultCategoryId());
      }
      context.getRequest().setAttribute("catId", selectedCatId);

      if (Integer.parseInt(selectedCatId) > 0) {
        //See if a specific record has been chosen from the list
        recordId = context.getRequest().getParameter("recId");
        String recordDeleted = (String) context.getRequest().getAttribute("recordDeleted");
        if (recordDeleted != null) {
          recordId = null;
        }

        //Now build the specified or default category
        CustomFieldCategory thisCategory = thisList.getCategory(Integer.parseInt(selectedCatId));
        if (recordId == null && thisCategory.getAllowMultipleRecords()) {
          //The user didn't request a specific record, so show a list
          //of records matching this category that the user can choose from
          PagedListInfo folderListInfo = this.getPagedListInfo(context, "ContactFolderInfo");
          folderListInfo.setLink("ExternalContacts.do?command=Fields&contactId=" + contactId + "&catId=" + selectedCatId);

          CustomFieldRecordList recordList = new CustomFieldRecordList();
          recordList.setLinkModuleId(Constants.CONTACTS);
          recordList.setLinkItemId(thisContact.getId());
          recordList.setCategoryId(thisCategory.getId());
          recordList.buildList(db);
          recordList.buildRecordColumns(db, thisCategory);
          context.getRequest().setAttribute("Records", recordList);
        } else {
          //The user requested a specific record, or this category only
          //allows a single record.
          thisCategory.setLinkModuleId(Constants.CONTACTS);
          thisCategory.setLinkItemId(thisContact.getId());
          if (recordId != null) {
            thisCategory.setRecordId(Integer.parseInt(recordId));
          } else {
            thisCategory.buildRecordId(db);
            recordId = String.valueOf(thisCategory.getRecordId());
          }
          thisCategory.setIncludeEnabled(Constants.TRUE);
          thisCategory.setIncludeScheduled(Constants.TRUE);
          thisCategory.setBuildResources(true);
          thisCategory.buildResources(db);
          showRecords = false;

          if (thisCategory.getRecordId() > -1) {
            CustomFieldRecord thisRecord = new CustomFieldRecord(db, thisCategory.getRecordId());
            context.getRequest().setAttribute("Record", thisRecord);
          }
        }
        context.getRequest().setAttribute("Category", thisCategory);
      }

      //load the account
      thisOrganization = new Organization(db, thisContact.getOrgId());
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "External Contacts", "Custom Fields Details");
    if (Integer.parseInt(selectedCatId) <= 0) {
      return this.getReturn(context, "FieldsEmpty");
    } else if (recordId == null && showRecords) {
      return this.getReturn(context, "FieldRecordList");
    } else {
      return this.getReturn(context, "Fields");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandModifyFields(ActionContext context) {
    Connection db = null;
    Contact thisContact = null;
    Organization thisOrganization = null;
    String selectedCatId = (String) context.getRequest().getParameter("catId");
    String recordId = (String) context.getRequest().getParameter("recId");
    SystemStatus systemStatus = this.getSystemStatus(context);
    context.getRequest().setAttribute("systemStatus", systemStatus);

    try {
      String contactId = context.getRequest().getParameter("contactId");
      db = this.getConnection(context);
      thisContact = new Contact(db, contactId);
      if (!(hasPermission(context, "accounts-accounts-contacts-folders-edit")) || (thisContact.getOrgId() > 0 && !(hasPermission(context, "accounts-accounts-contacts-edit")))) {
        return ("PermissionError");
      }
      if (!hasAuthority(db, context, thisContact)) {
        return ("PermissionError");
      }
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, thisContact.getOrgId())) {
        return ("PermissionError");
      }

      context.getRequest().setAttribute("ContactDetails", thisContact);

      CustomFieldCategory thisCategory = new CustomFieldCategory(
          db,
          Integer.parseInt(selectedCatId));
      thisCategory.setLinkModuleId(Constants.CONTACTS);
      thisCategory.setLinkItemId(thisContact.getId());
      thisCategory.setRecordId(Integer.parseInt(recordId));
      thisCategory.setIncludeEnabled(Constants.TRUE);
      thisCategory.setIncludeScheduled(Constants.TRUE);
      thisCategory.setBuildResources(true);
      thisCategory.buildResources(db);
      context.getRequest().setAttribute("Category", thisCategory);

      //load the account
      thisOrganization = new Organization(db, thisContact.getOrgId());
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "External Contacts", "Modify Custom Fields");
    if (recordId.equals("-1")) {
      return this.getReturn(context, "AddFolderRecord");
    } else {
      return this.getReturn(context, "ModifyFields");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandUpdateFields(ActionContext context) {
    Connection db = null;
    Contact thisContact = null;
    Organization thisOrganization = null;
    int resultCount = 0;
    SystemStatus systemStatus = this.getSystemStatus(context);
    context.getRequest().setAttribute("systemStatus", systemStatus);
    boolean isValid = false;
    try {
      String contactId = context.getRequest().getParameter("contactId");
      db = this.getConnection(context);
      thisContact = new Contact(db, contactId);
      if (!(hasPermission(context, "accounts-accounts-contacts-folders-edit")) || (thisContact.getOrgId() > 0 && !(hasPermission(context, "accounts-accounts-contacts-edit")))) {
        return ("PermissionError");
      }
      if (!hasAuthority(db, context, thisContact)) {
        return ("PermissionError");
      }
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, thisContact.getOrgId())) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("ContactDetails", thisContact);

      CustomFieldCategoryList thisList = new CustomFieldCategoryList();
      thisList.setLinkModuleId(Constants.CONTACTS);
      thisList.setIncludeEnabled(Constants.TRUE);
      thisList.setIncludeScheduled(Constants.TRUE);
      thisList.setBuildResources(false);
      thisList.buildList(db);
      context.getRequest().setAttribute("CategoryList", thisList);

      String selectedCatId = (String) context.getRequest().getParameter("catId");
      String recordId = (String) context.getRequest().getParameter("recId");

      context.getRequest().setAttribute("catId", selectedCatId);
      CustomFieldCategory thisCategory = new CustomFieldCategory(
          db,
          Integer.parseInt(selectedCatId));
      thisCategory.setLinkModuleId(Constants.CONTACTS);
      thisCategory.setLinkItemId(thisContact.getId());
      thisCategory.setRecordId(Integer.parseInt(recordId));
      thisCategory.setIncludeEnabled(Constants.TRUE);
      thisCategory.setIncludeScheduled(Constants.TRUE);
      thisCategory.setBuildResources(true);
      thisCategory.buildResources(db);
      thisCategory.setParameters(context);
      thisCategory.setModifiedBy(this.getUserId(context));
      if (!thisCategory.getReadOnly()) {
        thisCategory.setCanNotContinue(true);
        isValid = this.validateObject(context, db, thisCategory);
        if (isValid) {
          Iterator groups = (Iterator) thisCategory.iterator();
          while (groups.hasNext()) {
            CustomFieldGroup group = (CustomFieldGroup) groups.next();
            Iterator fields = (Iterator) group.iterator();
            while (fields.hasNext()) {
              CustomField field = (CustomField) fields.next();
              field.setValidateData(true);
              field.setRecordId(thisCategory.getRecordId());
              isValid = this.validateObject(context, db, field) && isValid;
            }
          }
        }
        if (isValid && resultCount != -1) {
          thisCategory.setCanNotContinue(true);
          resultCount = thisCategory.update(db);
          thisCategory.setCanNotContinue(false);
          resultCount = thisCategory.insertGroup(
              db, thisCategory.getRecordId());
        }
      }
      context.getRequest().setAttribute("Category", thisCategory);
      //load the account
      thisOrganization = new Organization(db, thisContact.getOrgId());
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
      if (resultCount != -1 && isValid) {
        thisCategory.buildResources(db);
        CustomFieldRecord thisRecord = new CustomFieldRecord(db, thisCategory.getRecordId());
        context.getRequest().setAttribute("Record", thisRecord);
      } else {
        if (System.getProperty("DEBUG") != null) {
          System.out.println("Contacts-> ModifyField validation error");
        }
        context.getRequest().setAttribute(
            "systemStatus", this.getSystemStatus(context));
        return getReturn(context, "ModifyFields");
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (resultCount == 1) {
      return this.getReturn(context, "UpdateFields");
    }
    context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
    return ("UserError");
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandInsertFields(ActionContext context) {
    Connection db = null;
    int resultCode = -1;
    Contact thisContact = null;
    Organization thisOrganization = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    context.getRequest().setAttribute("systemStatus", systemStatus);
    boolean isValid = false;

    try {
      String contactId = context.getRequest().getParameter("contactId");
      db = this.getConnection(context);
      thisContact = new Contact(db, contactId);
      if (!(hasPermission(context, "accounts-accounts-contacts-folders-add")) || (thisContact.getOrgId() > 0 && !(hasPermission(context, "accounts-accounts-contacts-edit")))) {
        return ("PermissionError");
      }
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, thisContact.getOrgId())) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("ContactDetails", thisContact);

      CustomFieldCategoryList thisList = new CustomFieldCategoryList();
      thisList.setLinkModuleId(Constants.CONTACTS);
      thisList.setIncludeEnabled(Constants.TRUE);
      thisList.setIncludeScheduled(Constants.TRUE);
      thisList.setBuildResources(false);
      thisList.buildList(db);
      context.getRequest().setAttribute("CategoryList", thisList);

      String selectedCatId = (String) context.getRequest().getParameter("catId");
      context.getRequest().setAttribute("catId", selectedCatId);
      CustomFieldCategory thisCategory = new CustomFieldCategory(
          db,
          Integer.parseInt(selectedCatId));
      thisCategory.setLinkModuleId(Constants.CONTACTS);
      thisCategory.setLinkItemId(thisContact.getId());
      thisCategory.setIncludeEnabled(Constants.TRUE);
      thisCategory.setIncludeScheduled(Constants.TRUE);
      thisCategory.setBuildResources(true);
      thisCategory.buildResources(db);
      thisCategory.setParameters(context);
      thisCategory.setEnteredBy(this.getUserId(context));
      thisCategory.setModifiedBy(this.getUserId(context));
      if (!thisCategory.getReadOnly()) {
        thisCategory.setCanNotContinue(true);
        isValid = this.validateObject(context, db, thisCategory);
        if (isValid) {
          resultCode = thisCategory.insert(db);
          Iterator groups = (Iterator) thisCategory.iterator();
          while (groups.hasNext()) {
            CustomFieldGroup group = (CustomFieldGroup) groups.next();
            Iterator fields = (Iterator) group.iterator();
            while (fields.hasNext()) {
              CustomField field = (CustomField) fields.next();
              field.setValidateData(true);
              field.setRecordId(thisCategory.getRecordId());
              isValid = this.validateObject(context, db, field) && isValid;
            }
          }
        }
        thisCategory.setCanNotContinue(false);
        if (isValid && resultCode != -1) {
          resultCode = thisCategory.insertGroup(
              db, thisCategory.getRecordId());
        }
      }
      if (resultCode == -1) {
        if (System.getProperty("DEBUG") != null) {
          System.out.println("Contacts-> InsertField validation error");
        }
      }
      context.getRequest().setAttribute("Category", thisCategory);
      if (resultCode != -1 && isValid) {
        processInsertHook(context, thisCategory);
      } else {
        if (thisCategory.getRecordId() != -1) {
          CustomFieldRecord record = new CustomFieldRecord(
              db, thisCategory.getRecordId());
          record.delete(db);
        }
        if (System.getProperty("DEBUG") != null) {
          System.out.println("Contacts-> InsertField validation error");
        }
        context.getRequest().setAttribute(
            "systemStatus", this.getSystemStatus(context));
        return getReturn(context, "AddFolderRecord");
      }

      //load the account
      thisOrganization = new Organization(db, thisContact.getOrgId());
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (resultCode == -1) {
      return this.getReturn(context, "AddFolderRecord");
    } else {
      return (this.executeCommandFields(context));
    }
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandDeleteFields(ActionContext context) {
    Connection db = null;
    boolean recordDeleted = false;
    Organization thisOrganization = null;
    try {
      db = this.getConnection(context);
      String selectedCatId = context.getRequest().getParameter("catId");
      String recordId = context.getRequest().getParameter("recId");
      String contactId = context.getRequest().getParameter("contactId");

      Contact thisContact = new Contact(db, Integer.parseInt(contactId));
      if (!(hasPermission(context, "accounts-accounts-contacts-folders-delete")) || (thisContact.getOrgId() > 0 && !(hasPermission(context, "accounts-accounts-contacts-edit")))) {
        return ("PermissionError");
      }
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, thisContact.getOrgId())) {
        return ("PermissionError");
      }

      CustomFieldCategory thisCategory = new CustomFieldCategory(
          db,
          Integer.parseInt(selectedCatId));

      CustomFieldRecord thisRecord = new CustomFieldRecord(db, Integer.parseInt(recordId));
      thisRecord.setLinkModuleId(Constants.CONTACTS);
      thisRecord.setLinkItemId(Integer.parseInt(contactId));
      thisRecord.setCategoryId(Integer.parseInt(selectedCatId));
      if (!thisCategory.getReadOnly()) {
        recordDeleted = thisRecord.delete(db);
      }
      context.getRequest().setAttribute("recordDeleted", "true");

      //load the account
      thisOrganization = new Organization(db, thisContact.getOrgId());
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("DeleteFieldsOK");
  }

  /**
   *  Adds a feature to the FormElements attribute of the Contacts object
   *
   * @param  context        The feature to be added to the FormElements
   *      attribute
   * @param  db             The feature to be added to the FormElements
   *      attribute
   * @param  systemStatus   The feature to be added to the FormElements
   *      attribute
   * @param  tmpContact     The feature to be added to the FormElements
   *      attribute
   * @exception  Exception  Description of the Exception
   */
  private void addFormElements(ActionContext context, Connection db, SystemStatus systemStatus, Contact tmpContact) throws Exception {

    LookupList siteid = new LookupList(db, "lookup_site_id");
    siteid.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
    context.getRequest().setAttribute("SiteIdList", siteid);

    LookupList contactTypeList = new LookupList(db, "lookup_contact_types");
    contactTypeList.addItem(0, systemStatus.getLabel("calendar.none.4dashes"));
    context.getRequest().setAttribute("ContactTypeList2", contactTypeList);

    LookupList contactSourceList = new LookupList(db, "lookup_contact_source");
    //contactSourceList.addItem(0, systemStatus.getLabel("calendar.none.4dashes"));
    context.getRequest().setAttribute("ContactSourceList", contactSourceList);

    LookupList salutationList = new LookupList(db, "lookup_title");
    salutationList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
    context.getRequest().setAttribute("SalutationList", salutationList);

    Organization thisOrganization = null;
    thisOrganization = new Organization(db, tmpContact.getOrgId());
    context.getRequest().setAttribute("OrgDetails", thisOrganization);
  }
}

