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
package org.aspcfs.modules.contacts.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import com.zeroio.iteam.base.FileItem;
import com.zeroio.iteam.base.FileItemList;
import com.zeroio.webdav.utils.VCard;
import com.zeroio.webutils.FileDownload;
import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.*;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.AccessType;
import org.aspcfs.modules.admin.base.AccessTypeList;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.base.*;
import org.aspcfs.modules.communications.base.*;
import org.aspcfs.modules.contacts.base.*;
import org.aspcfs.modules.login.base.AuthenticationItem;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.pipeline.base.OpportunityHeaderList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.PrivateString;
import org.aspcfs.utils.SMTPMessage;
import org.aspcfs.utils.Template;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.RequestUtils;
import org.aspcfs.utils.web.StateSelect;

import java.io.File;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 * Description of the Class
 *
 * @author mrajkowski
 * @version $Id: ExternalContacts.java,v 1.2 2001/10/03 15:40:42 mrajkowski
 *          Exp $
 * @created September 24, 2001
 */
public final class ExternalContacts extends CFSModule {

  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    if (!hasPermission(context, "contacts-external_contacts-view")) {
      return ("DefaultError");
    }
    return executeCommandDashboards(context);
  }


  /**
   * Show reports page
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandReports(ActionContext context) {
    if (!(hasPermission(context, "contacts-external_contacts-reports-view"))) {
      return ("PermissionError");
    }
    Connection db = null;
    FileItemList files = new FileItemList();
    files.setLinkModuleId(Constants.DOCUMENTS_CONTACTS_REPORTS);
    files.setLinkItemId(-1);

    PagedListInfo rptListInfo = this.getPagedListInfo(
        context, "ContactRptListInfo");
    rptListInfo.setLink("ExternalContacts.do?command=Reports");
    files.setPagedListInfo(rptListInfo);

    if ("all".equals(rptListInfo.getListView())) {
      files.setOwnerIdRange(this.getUserRange(context));
    } else {
      files.setOwner(this.getUserId(context));
    }
    try {
      db = this.getConnection(context);
      files.buildList(db);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Reports", "ViewReports");
    context.getRequest().setAttribute("FileList", files);
    return ("ReportsOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDownloadVCard(ActionContext context) {
    Connection db = null;
    try {
      db = this.getConnection(context);
      String id = context.getRequest().getParameter("id");
      Contact thisContact = new Contact();
      thisContact.setBuildDetails(true);
      thisContact.setBuildTypes(false);
      thisContact.queryRecord(db, Integer.parseInt(id));
      if (!isRecordAccessPermitted(context, thisContact)) {
        return ("PermissionError");
      }
      // Check permissions
      if ((thisContact.getOrgId() > 0 && !hasPermission(
          context, "accounts-accounts-contacts-view")) ||
          (thisContact.getOrgId() == -1 && !hasPermission(
              context, "contacts-external_contacts-view") && !hasAuthority(
              db, context, thisContact)) ||
          (thisContact.getOrgId() == 0 && !hasPermission(
              context, "contacts-internal_contacts-view"))) {
        return ("PermissionError");
      }
      VCard card = new VCard(thisContact);
      byte[] bytes = card.getBytes();
      if (bytes != null) {
        FileDownload download = new FileDownload();
        download.setDisplayName(card.getFormattedName() + ".vcf");
        download.sendFile(context, bytes, "application/x-vcard");
      } else {
        return "SystemError";
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("-none-");
  }


  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandDownloadCSVReport(ActionContext context) {
    if (!(hasPermission(context, "contacts-external_contacts-reports-view"))) {
      return ("PermissionError");
    }
    String itemId = (String) context.getRequest().getParameter("fid");
    FileItem thisItem = null;
    Connection db = null;
    try {
      db = getConnection(context);
      thisItem = new FileItem(
          db, Integer.parseInt(itemId), -1, Constants.DOCUMENTS_CONTACTS_REPORTS);
      if (!hasAuthority(context, thisItem.getEnteredBy())) {
        return ("PermissionError");
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    //Start the download
    try {
      FileItem itemToDownload = null;
      itemToDownload = thisItem;
      //itemToDownload.setEnteredBy(this.getUserId(context));
      String filePath = this.getPath(context, "contact-reports") + getDatePath(
          itemToDownload.getEntered()) + itemToDownload.getFilename() + ".csv";
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
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("-none-");
  }


  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandDeleteReport(ActionContext context) {
    if (!(hasPermission(context, "contacts-external_contacts-reports-delete")))
    {
      return ("PermissionError");
    }
    boolean recordDeleted = false;
    String itemId = (String) context.getRequest().getParameter("fid");
    Connection db = null;
    try {
      db = getConnection(context);
      FileItem thisItem = new FileItem(
          db, Integer.parseInt(itemId), -1, Constants.DOCUMENTS_CONTACTS_REPORTS);
      if (!hasAuthority(context, thisItem.getEnteredBy())) {
        return ("PermissionError");
      }
      if (thisItem.getEnteredBy() == this.getUserId(context)) {
        recordDeleted = thisItem.delete(
            db, this.getPath(context, "contact-reports"));

        String filePath1 = this.getPath(context, "contact-reports") + getDatePath(
            thisItem.getEntered()) + thisItem.getFilename() + ".csv";
        java.io.File fileToDelete1 = new java.io.File(filePath1);
        if (!fileToDelete1.delete()) {
          System.err.println("FileItem-> Tried to delete file: " + filePath1);
        }

        String filePath2 = this.getPath(context, "contact-reports") + getDatePath(
            thisItem.getEntered()) + thisItem.getFilename() + ".html";
        java.io.File fileToDelete2 = new java.io.File(filePath2);
        if (!fileToDelete2.delete()) {
          System.err.println("FileItem-> Tried to delete file: " + filePath2);
        }
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Reports", "Reports del");
    if (recordDeleted) {
      return ("DeleteReportOK");
    } else {
      return ("DeleteReportERROR");
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandGenerateForm(ActionContext context) {
    if (!hasPermission(context, "contacts-external_contacts-reports-add")) {
      return ("PermissionError");
    }
    Connection db = null;
    try {
      CustomFieldCategoryList thisList = new CustomFieldCategoryList();
      thisList.setLinkModuleId(Constants.CONTACTS);
      thisList.setIncludeEnabled(Constants.TRUE);
      thisList.setIncludeScheduled(Constants.TRUE);
      thisList.setAllSelectOption(true);
      thisList.setBuildResources(false);
      db = getConnection(context);
      thisList.buildList(db);
      context.getRequest().setAttribute("CategoryList", thisList);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Reports", "Generate new");
    return ("GenerateFormOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandShowReportHtml(ActionContext context) {
    if (!hasPermission(context, "contacts-external_contacts-reports-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    FileItem thisItem = null;
    String itemId = (String) context.getRequest().getParameter("fid");
    try {
      db = getConnection(context);
      thisItem = new FileItem(db, Integer.parseInt(itemId));
      String filePath = this.getPath(context, "contact-reports") + getDatePath(
          thisItem.getEntered()) + thisItem.getFilename() + ".html";
      String textToShow = includeFile(filePath);
      context.getRequest().setAttribute("ReportText", textToShow);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (!hasAuthority(context, thisItem.getEnteredBy())) {
      return ("PermissionError");
    }
    return ("ReportHtmlOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandExportReport(ActionContext context) {
    if (!hasPermission(context, "contacts-external_contacts-reports-add")) {
      return ("PermissionError");
    }
    Connection db = null;
    String subject = context.getRequest().getParameter("subject");
    String ownerCriteria = context.getRequest().getParameter("criteria1");
    String type = context.getRequest().getParameter("type");
    //setup file stuff
    String filePath = this.getPath(context, "contact-reports");
    SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy");
    String datePathToUse1 = formatter1.format(new java.util.Date());
    SimpleDateFormat formatter2 = new SimpleDateFormat("MMdd");
    String datePathToUse2 = formatter2.format(new java.util.Date());
    filePath += datePathToUse1 + fs + datePathToUse2 + fs;
    //Prepare the report
    ContactReport contactReport = new ContactReport();
    contactReport.setCriteria(
        context.getRequest().getParameterValues("selectedList"));
    contactReport.setFilePath(filePath);
    contactReport.setSubject(subject);
    contactReport.addIgnoreTypeId(Contact.EMPLOYEE_TYPE);
    contactReport.addIgnoreTypeId(Contact.LEAD_TYPE);
    //Prepare the pagedList to provide sorting
    PagedListInfo thisInfo = new PagedListInfo();
    thisInfo.setColumnToSortBy(context.getRequest().getParameter("sort"));
    thisInfo.setItemsPerPage(0);
    contactReport.setPagedListInfo(thisInfo);
    contactReport.setSiteId(this.getUserSiteId(context));
    contactReport.setExclusiveToSite(true);
    if (this.getUserSiteId(context) == -1) {
      contactReport.setIncludeAllSites(true);
    }
    int folderId = Integer.parseInt(
        context.getRequest().getParameter("catId"));
    if (type.equals("4") && folderId == 0) {
      contactReport.setIncludeFolders(true);
    } else if (type.equals("4") && folderId > 0) {
      contactReport.setFolderId(folderId);
    }
    //make sure user has access to view account contacts
    if (!(hasPermission(context, "accounts-accounts-contacts-view"))) {
      contactReport.setExcludeAccountContacts(true);
    }
    //Check the form selections and criteria
    if (ownerCriteria.equals("my")) {
      contactReport.setOwner(this.getUserId(context));
      //ignore personal check as all personal contacts are covered with the owner criteria
      contactReport.setPersonalId(ContactList.IGNORE_PERSONAL);
    } else if (ownerCriteria.equals("hierarchy")) {
      contactReport.setControlledHierarchyOnly(
          true, this.getUserRange(context));
      contactReport.setPersonalId(this.getUserId(context));
    } else if (ownerCriteria.equals("all")) {
      //get all contacts (including personal)
      contactReport.setAllContacts(
          true, this.getUserId(context), this.getUserRange(context));
    } else if (ownerCriteria.equals("public")) {
      //get public contacts only
      contactReport.setRuleId(AccessType.PUBLIC);
    } else if (ownerCriteria.equals("personal")) {
      //get personal contacts owned by me
      contactReport.setRuleId(AccessType.PERSONAL);
      contactReport.setPersonalId(this.getUserId(context));
    }
    try {
      db = this.getConnection(context);
      //builds list also
      contactReport.buildReportFull(db, this.getUserTable(context));
      contactReport.setEnteredBy(getUserId(context));
      contactReport.setModifiedBy(getUserId(context));
      //TODO: set owner, enteredby, and modified names
      contactReport.saveAndInsert(db);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return executeCommandReports(context);
  }


  /**
   * Searches for contacts and builds a contact list
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandSearchContacts(ActionContext context) {
    if (!hasPermission(context, "contacts-external_contacts-view")) {
      return ("PermissionError");
    }
    PagedListInfo searchContactsInfo = this.getPagedListInfo(
        context, "SearchContactsInfo");
    searchContactsInfo.setLink("ExternalContacts.do?command=SearchContacts");
    String source = (String) context.getRequest().getParameter("source");
    addModuleBean(context, "External Contacts", "Search Results");
    ContactList contactList = new ContactList();
    SystemStatus systemStatus = this.getSystemStatus(context);
    Connection db = null;
    try {
      db = this.getConnection(context);
      LookupList siteid = new LookupList(db, "lookup_site_id");
      siteid.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("SiteIdList", siteid);
      //return if no criteria is selected
      if ((searchContactsInfo.getListView() == null || "".equals(
          searchContactsInfo.getListView())) && !"searchForm".equals(source)) {
        return "ListContactsOK";
      }
      //set properties on contact list
      contactList.setPagedListInfo(searchContactsInfo);
      //set the searchcriteria
      searchContactsInfo.setSearchCriteria(contactList, context);
      //make sure user has access to view account contacts
      if (!(hasPermission(context, "accounts-accounts-contacts-view"))) {
        contactList.setExcludeAccountContacts(true);
      }
      contactList.setBuildDetails(true);
      contactList.setBuildTypes(false);
      contactList.setExclusiveToSite(true);
      if (contactList.getSiteId() == Constants.INVALID_SITE) {
        contactList.setIncludeAllSites(true);
      }
//      if (user.getSiteId() == -1) {
//        contactList.setIncludeAllSites(true);
//      }
      contactList.addIgnoreTypeId(Contact.EMPLOYEE_TYPE);
      contactList.addIgnoreTypeId(Contact.LEAD_TYPE);
      contactList.setTypeId(searchContactsInfo.getFilterKey("listFilter1"));

      //check if a filter is selected
      if ("all".equals(searchContactsInfo.getListView())) {
        contactList.setAllContacts(
            true, this.getUserId(context), this.getUserRange(context));
      } else if ("hierarchy".equals(searchContactsInfo.getListView())) {
        contactList.setControlledHierarchyOnly(
            true, this.getUserRange(context));
        contactList.setPersonalId(this.getUserId(context));
      } else if ("archived".equals(searchContactsInfo.getListView())) {
        contactList.setIncludeEnabled(Constants.FALSE);
        contactList.setPersonalId(this.getUserId(context));
      } else if ("my".equals(searchContactsInfo.getListView())) {
        contactList.setOwner(this.getUserId(context));
        contactList.setPersonalId(ContactList.IGNORE_PERSONAL);
      }
      contactList.buildList(db);
      context.getSession().removeAttribute("contactHistoryListInfo");
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("ContactList", contactList);
    return ("ListContactsOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandMessageDetails(ActionContext context) {
    addModuleBean(context, "External Contacts", "Message Details");
    Connection db = null;
    Contact thisContact = null;
    Campaign campaign = null;

    String campaignId = context.getRequest().getParameter("id");
    try {
      db = this.getConnection(context);
      String contactId = context.getRequest().getParameter("contactId");
      thisContact = new Contact(db, contactId);
      if (!(hasPermission(context, "contacts-external_contacts-messages-view")) || (thisContact.getOrgId() > 0 && !(hasPermission(
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


  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
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
          "ExternalContacts.do?command=ViewMessages&contactId=" + contactId + RequestUtils.addLinkParams(
              context.getRequest(), "popup|popupType|actionId"));
      if (sectionId == null) {
        if (!sentMessageListInfo.getExpandedSelection()) {
          if (sentMessageListInfo.getItemsPerPage() != MINIMIZED_ITEMS_PER_PAGE)
          {
            sentMessageListInfo.setItemsPerPage(MINIMIZED_ITEMS_PER_PAGE);
          }
        } else {
          if (sentMessageListInfo.getItemsPerPage() == MINIMIZED_ITEMS_PER_PAGE)
          {
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
          "ExternalContacts.do?command=ViewMessages&contactId=" + contactId + RequestUtils.addLinkParams(
              context.getRequest(), "popup|popupType|actionId"));
      if (sectionId == null) {
        if (!receivedMessageListInfo.getExpandedSelection()) {
          if (receivedMessageListInfo.getItemsPerPage() != MINIMIZED_ITEMS_PER_PAGE)
          {
            receivedMessageListInfo.setItemsPerPage(MINIMIZED_ITEMS_PER_PAGE);
          }
        } else {
          if (receivedMessageListInfo.getItemsPerPage() == MINIMIZED_ITEMS_PER_PAGE)
          {
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
      if (!(hasPermission(context, "contacts-external_contacts-messages-view")) || (thisContact.getOrgId() > 0 && !(hasPermission(
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
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandAddFolderRecord(ActionContext context) {
    Connection db = null;
    Contact thisContact = null;
    try {
      String contactId = context.getRequest().getParameter("contactId");
      db = this.getConnection(context);
      thisContact = new Contact(db, contactId);

      if (!(hasPermission(context, "contacts-external_contacts-folders-add")) || (thisContact.getOrgId() > 0 && !(hasPermission(
          context, "accounts-accounts-contacts-edit")))) {
        return ("PermissionError");
      }
      if (!hasAuthority(db, context, thisContact)) {
        return ("PermissionError");
      }
      if (!isRecordAccessPermitted(context, thisContact)) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("ContactDetails", thisContact);

      String selectedCatId = (String) context.getRequest().getParameter(
          "catId");
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
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "External Contacts", "Add Folder Record");
    context.getRequest().setAttribute(
        "systemStatus", this.getSystemStatus(context));
    return getReturn(context, "AddFolderRecord");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandFolderList(ActionContext context) {
    if (!(hasPermission(context, "contacts-external_contacts-folders-view"))) {
      return ("PermissionError");
    }
    Connection db = null;
    Contact thisContact = null;
    try {
      String empId = context.getRequest().getParameter("contactId");
      db = this.getConnection(context);
      thisContact = new Contact(db, Integer.parseInt(empId));
      if (!isRecordAccessPermitted(context, thisContact)) {
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
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "External Contacts", "Custom Fields Details");

    return getReturn(context, "FolderList");
  }


  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandFields(ActionContext context) {
    Connection db = null;
    Contact thisContact = null;
    String recordId = null;
    boolean showRecords = true;
    String selectedCatId = null;

    try {
      String contactId = context.getRequest().getParameter("contactId");
      db = this.getConnection(context);
      thisContact = new Contact(db, contactId);

      if (!(hasPermission(context, "contacts-external_contacts-folders-view")) || (thisContact.getOrgId() > 0 && !(hasPermission(
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
        String recordDeleted = (String) context.getRequest().getAttribute(
            "recordDeleted");
        if (recordDeleted != null) {
          recordId = null;
        }

        //Now build the specified or default category
        CustomFieldCategory thisCategory = thisList.getCategory(
            Integer.parseInt(selectedCatId));
        if (recordId == null && thisCategory.getAllowMultipleRecords()) {
          //The user didn't request a specific record, so show a list
          //of records matching this category that the user can choose from
          PagedListInfo folderListInfo = this.getPagedListInfo(
              context, "ContactFolderInfo");
          folderListInfo.setLink(
              "ExternalContacts.do?command=Fields&contactId=" + contactId + "&catId=" + selectedCatId + RequestUtils.addLinkParams(
                  context.getRequest(), "popup|popupType|actionId"));

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
            CustomFieldRecord thisRecord = new CustomFieldRecord(
                db, thisCategory.getRecordId());
            context.getRequest().setAttribute("Record", thisRecord);
          }
        }
        context.getRequest().setAttribute("Category", thisCategory);
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "External Contacts", "Custom Fields Details");
    if (Integer.parseInt(selectedCatId) <= 0) {
      return getReturn(context, "FieldsEmpty");
    } else if (recordId == null && showRecords) {
      return getReturn(context, "FieldRecordList");
    } else {
      return getReturn(context, "Fields");
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   * @since 1.2
   */
  public String executeCommandSearchContactsForm(ActionContext context) {
    if (!(hasPermission(context, "contacts-external_contacts-view"))) {
      return ("PermissionError");
    }

    Connection db = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    ContactTypeList contactTypeList = new ContactTypeList();
    try {
      db = this.getConnection(context);

      contactTypeList.setIncludeDefinedByUser(this.getUserId(context));
      contactTypeList.addItem(
          -1, this.getSystemStatus(context).getLabel(
          "contacts.typeList.allContactTypes"));
      contactTypeList.buildList(db);
      context.getRequest().setAttribute("ContactTypeList", contactTypeList);
      context.getRequest().setAttribute(
          "systemStatus", systemStatus);

      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      siteList.addItem(Constants.INVALID_SITE, systemStatus.getLabel("accounts.allSites"));
      context.getRequest().setAttribute("SiteList", siteList);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Search Contacts", "Contacts Search");
    return ("SearchContactsFormOK");
  }


  /**
   * This method retrieves the details of a single contact. The resulting
   * Contact object is added to the request if successful.<p>
   * <p/>
   * <p/>
   * <p/>
   * This method handles output for both viewing and modifying a contact.
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   * @since 1.1
   */
  public String executeCommandContactDetails(ActionContext context) {
    String contactId = context.getRequest().getParameter("id");
    Connection db = null;
    Contact thisContact = null;
    try {
      db = this.getConnection(context);
      thisContact = new Contact(db, contactId);
      if (!(hasPermission(context, "contacts-external_contacts-view")) || (thisContact.getOrgId() > 0 && !(hasPermission(
          context, "accounts-accounts-contacts-view")))) {
        if (System.getProperty("DEBUG") != null) {
          System.out.println("ExternalContacts-> permission error");
        }
        return ("PermissionError");
      }
      if (!(hasAuthority(db, context, thisContact) || OpportunityHeaderList.isComponentOwner(
          db, getUserId(context)))) {
        if (System.getProperty("DEBUG") != null) {
          System.out.println("ExternalContacts-> authority or isComponentOwner error");
        }
        return ("PermissionError");
      }
      if (!isRecordAccessPermitted(context, thisContact)) {
        if (System.getProperty("DEBUG") != null) {
          System.out.println("ExternalContacts-> isRecordAccessPermitted error");
        }
        return ("PermissionError");
      }
      SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList salutationList = new LookupList(db, "lookup_title");
      salutationList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("SalutationList", salutationList);

      //check whether or not the owner is an active User
      if (!thisContact.getEmployee()) {
        thisContact.checkEnabledOwnerAccount(db);
      }
      context.getRequest().setAttribute("ContactDetails", thisContact);
      addRecentItem(context, thisContact);
    } catch (Exception errorMessage) {
      errorMessage.printStackTrace();
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "External Contacts", "View Contact Details");
    context.getSession().removeAttribute("ContactMessageListInfo");
    if ("true".equals(context.getRequest().getParameter("popup"))) {
      return ("ContactDetailsPopupOK");
    }
    //context.getRequest().setAttribute("ContactDetails", thisContact);
    return ("ContactDetailsOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandModifyContact(ActionContext context) {
    String contactId = context.getRequest().getParameter("id");
    Contact thisContact = null;
    Connection db = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      db = this.getConnection(context);
      thisContact = (Contact) context.getFormBean();
      thisContact.queryRecord(db, Integer.parseInt(contactId));
      ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
      StateSelect stateSelect = new StateSelect(systemStatus, thisContact.getAddressList().getCountries()+","+prefs.get("SYSTEM.COUNTRY"));
      stateSelect.setPreviousStates(thisContact.getAddressList().getSelectedStatesHashMap());
      context.getRequest().setAttribute("StateSelect", stateSelect);
      if (!(hasPermission(context, "contacts-external_contacts-edit")) || (thisContact.getOrgId() > 0 && !(hasPermission(
          context, "accounts-accounts-contacts-edit")))) {
        return ("PermissionError");
      }
      if (!(hasAuthority(db, context, thisContact) || OpportunityHeaderList.isComponentOwner(
          db, getUserId(context)))) {
        return ("PermissionError");
      }
      if (!isRecordAccessPermitted(context, thisContact)) {
        return ("PermissionError");
      }
      //add access types
      AccessTypeList accessTypeList = null;
      if (thisContact.getOrgId() > 0) {
        accessTypeList = this.getSystemStatus(context).getAccessTypeList(
            db, AccessType.ACCOUNT_CONTACTS);
      } else {
        accessTypeList = this.getSystemStatus(context).getAccessTypeList(
            db, AccessType.GENERAL_CONTACTS);
      }
      context.getRequest().setAttribute("AccessTypeList", accessTypeList);
      LookupList salutationList = new LookupList(db, "lookup_title");
      salutationList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("SalutationList", salutationList);

      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("SiteList", siteList);

      //check whether or not the owner is an active User
      if (!thisContact.getEmployee()) {
        thisContact.checkEnabledOwnerAccount(db);
      }
      addRecentItem(context, thisContact);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "External Contacts", "Modify Contact Details");
    context.getSession().removeAttribute("ContactMessageListInfo");
    return executeCommandPrepare(context);
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandPrepare(ActionContext context) {
    Connection db = null;
    Contact thisContact = (Contact) context.getFormBean();
    SystemStatus systemStatus = this.getSystemStatus(context);
    StateSelect stateSelect = (StateSelect) context.getRequest().getAttribute("StateSelect");
    boolean addUser = "adduser".equals(
        context.getRequest().getParameter("source"));
    if (thisContact.getId() == -1) {
      if (!(hasPermission(context, "contacts-external_contacts-add"))) {
        return ("PermissionError");
      }
      addModuleBean(context, "Add Contact", "Add Contact to Account");
    }
    try {
      db = this.getConnection(context);
      ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
      if (stateSelect == null) {
        stateSelect = new StateSelect(systemStatus, prefs.get("SYSTEM.COUNTRY"));
      }
      context.getRequest().setAttribute("StateSelect", stateSelect);
      //prepare contact type list
      ContactTypeList contactTypeList = new ContactTypeList();
      contactTypeList.setIncludeDefinedByUser(this.getUserId(context));
      contactTypeList.buildList(db);
      context.getRequest().setAttribute("ContactTypeList", contactTypeList);
      //prepare the Department List if employee is being added.
      if (addUser) {
        LookupList departmentList = new LookupList(db, "lookup_department");
        departmentList.addItem(
            0, systemStatus.getLabel("calendar.none.4dashes"));
        context.getRequest().setAttribute("DepartmentList", departmentList);
      }
      //prepare userList for reassigning owner
      UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
      User thisRec = thisUser.getUserRecord();
      UserList shortChildList = thisRec.getShortChildList();
      UserList userList = thisRec.getFullChildList(
          shortChildList, new UserList());
      userList.setMyId(getUserId(context));
      userList.setMyValue(thisUser.getContact().getNameLastFirst());
      userList.setIncludeMe(true);
      userList.setExcludeDisabledIfUnselected(true);
      userList.setExcludeExpiredIfUnselected(true);
      context.getRequest().setAttribute("UserList", userList);

      //add access types
      AccessTypeList accessTypeList = null;
      if (context.getRequest().getAttribute("AccessTypeList") == null) {
        if (thisContact.getOrgId() > 0) {
          accessTypeList = this.getSystemStatus(context).getAccessTypeList(
              db, AccessType.ACCOUNT_CONTACTS);
        } else if (addUser) {
          accessTypeList = this.getSystemStatus(context).getAccessTypeList(
              db, AccessType.EMPLOYEES);
        } else {
          accessTypeList = this.getSystemStatus(context).getAccessTypeList(
              db, AccessType.GENERAL_CONTACTS);
        }
        context.getRequest().setAttribute("AccessTypeList", accessTypeList);
      }

      LookupList salutationList = new LookupList(db, "lookup_title");
      salutationList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("SalutationList", salutationList);

      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("SiteList", siteList);

      //prepare organization if needed
      if (thisContact.getOrgId() > -1) {
        Organization thisOrg = new Organization(db, thisContact.getOrgId());
        thisContact.setCompany(thisOrg.getName());
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("systemStatus", systemStatus);
    context.getSession().removeAttribute("ContactMessageListInfo");

    //if a different module reuses this action then do a explicit return
    if (context.getRequest().getParameter("actionSource") != null) {
        return getReturn(context, "PrepareContact");
    }

    return "PrepareOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandClone(ActionContext context) {
    if (!(hasPermission(context, "contacts-external_contacts-add"))) {
      return ("PermissionError");
    }
    addModuleBean(context, "Add Contact", "Clone Contact");
    Connection db = null;
    String contactId = context.getRequest().getParameter("id");
    Contact cloneContact = null;
    try {
      db = this.getConnection(context);
      cloneContact = new Contact(db, contactId);
      if (!isRecordAccessPermitted(context, cloneContact)) {
        return ("PermissionError");
      }
      cloneContact.resetBaseInfo();
      context.getRequest().setAttribute("ContactDetails", cloneContact);

      //add access types
      AccessTypeList accessTypeList = null;
      if (cloneContact.getOrgId() > 0) {
        accessTypeList = this.getSystemStatus(context).getAccessTypeList(
            db, AccessType.ACCOUNT_CONTACTS);
      } else {
        accessTypeList = this.getSystemStatus(context).getAccessTypeList(
            db, AccessType.GENERAL_CONTACTS);
      }
      context.getRequest().setAttribute("AccessTypeList", accessTypeList);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return executeCommandPrepare(context);
  }


  /**
   * Process the insert form
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   * @since 1.1
   */
  public String executeCommandSave(ActionContext context) {
    boolean recordInserted = false;
    int resultCount = 0;
    boolean isValid = false;
    String id = context.getRequest().getParameter("id");
    String permission = "contacts-external_contacts-add";
    Organization thisOrg = null;

    Contact thisContact = (Contact) context.getFormBean();
    thisContact.setRequestItems(context);
    thisContact.setTypeList(
        context.getRequest().getParameterValues("selectedList"));
    thisContact.setEnteredBy(getUserId(context));
    thisContact.setModifiedBy(getUserId(context));
    //decide on permissions based on account/general contact
    if (thisContact.getOrgId() == -1) {
      if (thisContact.getId() > 0) {
        permission = "contacts-external_contacts-edit";
      }
    } else {
      if (thisContact.getId() > 0) {
        permission = "accounts-accounts-contacts-edit";
      } else {
        permission = "accounts-accounts-contacts-add";
      }
    }

    if (!hasPermission(context, permission)) {
      return ("PermissionError");
    }

    if (context.getRequest().getParameter("primaryContact") != null) {
      if (context.getRequest().getParameter("primaryContact").equalsIgnoreCase(
          "true")) {
        thisContact.setPrimaryContact(true);
      }
    }

    Connection db = null;
    try {
      db = this.getConnection(context);
      if (thisContact.getOrgId() > 0) {
        thisOrg = new Organization(db, thisContact.getOrgId());
        thisContact.setOrgName(thisOrg.getName());
      }
      if (thisContact.getId() > 0) {
        // trying to update a contact
        addModuleBean(context, "External Contacts", "Update Contact");
        Contact oldContact = new Contact(db, id);

        if (oldContact.getOrgId() == -1 && thisContact.getOrgId() > 0) {
          if (!hasPermission(context, "accounts-accounts-contacts-add")) {
            return ("PermissionError");
          }
        }
        if (!hasAuthority(db, context, oldContact)) {
          return ("PermissionError");
        }
        isValid = validateObject(context, db, thisContact);
        if (isValid) {
          if (!isRecordAccessPermitted(context, thisContact)) {
            return ("PermissionError");
          }
          resultCount = thisContact.update(db, context);
        }
      } else {
        // trying to insert a contact
        addModuleBean(context, "External Contacts", "Add a new contact");
        thisContact.setOwner(getUserId(context));
        isValid = validateObject(context, db, thisContact);
        if (isValid) {
          if (!isRecordAccessPermitted(context, thisContact)) {
            return ("PermissionError");
          }
          recordInserted = thisContact.insert(db);
        }
      }
      if (recordInserted) {
        thisContact = new Contact(db, thisContact.getId());
        context.getRequest().setAttribute("ContactDetails", thisContact);
        addRecentItem(context, thisContact);
      } else if (resultCount == 1) {
        //If the user is in the cache, update the contact record
        thisContact.checkUserAccount(db);
        this.updateUserContact(db, context, thisContact.getUserId());
      } else {
        context.getRequest().setAttribute(
            "TypeList", thisContact.getTypeList());
      }
      //add access types
      if ("true".equals(
          (String) context.getRequest().getParameter("saveAndNew")) || (!recordInserted && resultCount != 1))
      {
        AccessTypeList accessTypeList = null;
        if ("true".equals(
            (String) context.getRequest().getParameter("saveAndNew")) || thisContact.getOrgId() == -1)
        {
          accessTypeList = this.getSystemStatus(context).getAccessTypeList(
              db, AccessType.GENERAL_CONTACTS);
        } else {
          accessTypeList = this.getSystemStatus(context).getAccessTypeList(
              db, AccessType.ACCOUNT_CONTACTS);
        }
        context.getRequest().setAttribute("AccessTypeList", accessTypeList);
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    // decide what happend with the processing
    if (recordInserted) {
      if ("true".equals(
          (String) context.getRequest().getParameter("saveAndNew"))) {
        context.getRequest().removeAttribute("ContactDetails");
        return (executeCommandPrepare(context));
      }
      if (context.getRequest().getParameter("popup") != null) {
        if (context.getRequest().getParameter("source") != null) {
          String source = context.getRequest().getParameter("source");
          if ("addactivity".equals(source)) {
            return ("CloseInsertContactPopup");
          }
        }
        return ("ClosePopup");
      }
      return ("ContactDetailsOK");
    } else if (resultCount == 1) {
      if (context.getRequest().getParameter("return") != null && context.getRequest().getParameter(
          "return").equals("list")) {
        return (executeCommandSearchContacts(context));
      } else {
        return ("ContactDetailsUpdateOK");
      }
    } else {
      if (thisContact.getId() > 0) {
        // Tried to update record
        if (resultCount == -1 || !isValid) {
          return (executeCommandPrepare(context));
        }
        context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
        return ("UserError");
      } else {
        // Tried to insert
        context.getRequest().setAttribute(
            "TypeList", thisContact.getTypeList());
        return (executeCommandPrepare(context));
      }
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   * @since 1.1
   */
  public String executeCommandDeleteContact(ActionContext context) {
    boolean recordDeleted = false;
    Contact thisContact = null;
    String popup = (String) context.getRequest().getParameter("sourcePopup");
    Connection db = null;
    try {
      db = this.getConnection(context);
      thisContact = new Contact(db, context.getRequest().getParameter("id"));
      thisContact.setForceDelete(
          DatabaseUtils.parseBoolean(
              (String) context.getRequest().getParameter("forceDelete")));
      if (!(hasPermission(context, "contacts-external_contacts-delete")) || (thisContact.getOrgId() > 0 && !(hasPermission(
          context, "accounts-accounts-contacts-delete")))) {
        return ("PermissionError");
      }
      if (!hasAuthority(db, context, thisContact)) {
        return ("PermissionError");
      }
      if (!isRecordAccessPermitted(context, thisContact)) {
        return ("PermissionError");
      }
      recordDeleted = thisContact.delete(db, context, getDbNamePath(context));
      processErrors(context, thisContact.getErrors());
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "External Contacts", "Delete a contact");
    if (recordDeleted) {
      context.getSession().removeAttribute("ContactMessageListInfo");
      deleteRecentItem(context, thisContact);
      if (popup != null && "true".equals(popup)) {
        context.getRequest().setAttribute("id", "" + thisContact.getId());
        return "ContactDeletePopupOK";
      }
      context.getRequest().setAttribute(
          "refreshUrl", "ExternalContacts.do?command=SearchContacts");
      return "ContactDeleteOK";
    } else {
      processErrors(context, thisContact.getErrors());
      return (executeCommandSearchContacts(context));
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandTrash(ActionContext context) {
    boolean recordUpdated = false;
    Contact thisContact = null;
    String popup = (String) context.getRequest().getParameter("sourcePopup");
    Connection db = null;
    try {
      db = this.getConnection(context);
      thisContact = new Contact(db, context.getRequest().getParameter("id"));
      if (!(hasPermission(context, "contacts-external_contacts-delete")) || (thisContact.getOrgId() > 0 && !(hasPermission(
          context, "accounts-accounts-contacts-delete")))) {
        return ("PermissionError");
      }
      if (!hasAuthority(db, context, thisContact)) {
        return ("PermissionError");
      }
      if (!isRecordAccessPermitted(context, thisContact)) {
        return ("PermissionError");
      }
      recordUpdated = thisContact.updateStatus(
          db, context, true, this.getUserId(context));
      this.invalidateUserData(context, this.getUserId(context));
      this.invalidateUserData(context, thisContact.getOwner());
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "External Contacts", "Delete a contact");
    if (recordUpdated) {
      context.getSession().removeAttribute("ContactMessageListInfo");
      deleteRecentItem(context, thisContact);
      if (popup != null && "true".equals(popup)) {
        context.getRequest().setAttribute("id", "" + thisContact.getId());
        return "ContactDeletePopupOK";
      }
      context.getRequest().setAttribute(
          "refreshUrl", "ExternalContacts.do?command=SearchContacts");
      return "ContactDeleteOK";
    } else {
      processErrors(context, thisContact.getErrors());
      return (executeCommandSearchContacts(context));
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandRestore(ActionContext context) {
    boolean recordUpdated = false;
    Contact thisContact = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      thisContact = new Contact(db, context.getRequest().getParameter("id"));
      if (!(hasPermission(context, "contacts-external_contacts-delete")) || (thisContact.getOrgId() > 0 && !(hasPermission(
          context, "accounts-accounts-contacts-delete")))) {
        return ("PermissionError");
      }
      if (!hasAuthority(db, context, thisContact)) {
        return ("PermissionError");
      }
      if (!isRecordAccessPermitted(context, thisContact)) {
        return ("PermissionError");
      }
      recordUpdated = thisContact.updateStatus(
          db, context, false, this.getUserId(context));
      this.invalidateUserData(context, this.getUserId(context));
      this.invalidateUserData(context, thisContact.getOwner());
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if ((recordUpdated) && (context.getRequest().getParameter("return") != null && "list".equals(
        context.getRequest().getParameter("return")))) {
      return (executeCommandSearchContacts(context));
    } else {
      return ("ContactDetailsUpdateOK");
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandModifyFields(ActionContext context) {
    Connection db = null;
    Contact thisContact = null;

    String selectedCatId = (String) context.getRequest().getParameter("catId");
    String recordId = (String) context.getRequest().getParameter("recId");

    try {
      String contactId = context.getRequest().getParameter("contactId");
      db = this.getConnection(context);
      thisContact = new Contact(db, contactId);
      if (!(hasPermission(context, "contacts-external_contacts-folders-edit")) || (thisContact.getOrgId() > 0 && !(hasPermission(
          context, "accounts-accounts-contacts-edit")))) {
        return ("PermissionError");
      }
      if (!hasAuthority(db, context, thisContact)) {
        return ("PermissionError");
      }
      if (!isRecordAccessPermitted(context, thisContact)) {
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

    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "External Contacts", "Modify Custom Fields");
    context.getRequest().setAttribute(
        "systemStatus", this.getSystemStatus(context));
    if (recordId.equals("-1")) {
      return getReturn(context, "AddFolderRecord");
    } else {
      return getReturn(context, "ModifyFields");
    }
  }


  /**
   * UpdateFields: Performs the actual update of the selected Custom Field
   * Record based on user-submitted information from the modify form.
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandUpdateFields(ActionContext context) {
    Connection db = null;
    Contact thisContact = null;
    int resultCount = 0;
    boolean isValid = false;
    context.getRequest().setAttribute(
        "systemStatus", this.getSystemStatus(context));
    try {
      String contactId = context.getRequest().getParameter("contactId");
      db = this.getConnection(context);
      thisContact = new Contact(db, contactId);
      if (!(hasPermission(context, "contacts-external_contacts-folders-edit")) || (thisContact.getOrgId() > 0 && !(hasPermission(
          context, "accounts-accounts-contacts-edit")))) {
        return ("PermissionError");
      }
      if (!hasAuthority(db, context, thisContact)) {
        return ("PermissionError");
      }
      if (!isRecordAccessPermitted(context, thisContact)) {
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

      String selectedCatId = (String) context.getRequest().getParameter(
          "catId");
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
      if (resultCount == -1 || !isValid) {
        if (System.getProperty("DEBUG") != null) {
          System.out.println("Contacts-> ModifyField validation error");
        }
        return getReturn(context, "ModifyFields");
      } else {
        thisCategory.buildResources(db);
        CustomFieldRecord thisRecord = new CustomFieldRecord(
            db, thisCategory.getRecordId());
        context.getRequest().setAttribute("Record", thisRecord);
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (resultCount == 1) {
      return getReturn(context, "UpdateFields");
    } else {
      context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
      return ("UserError");
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandInsertFields(ActionContext context) {
    Connection db = null;
    int resultCode = -1;
    Contact thisContact = null;
    boolean isValid = false;
    try {
      String contactId = context.getRequest().getParameter("contactId");
      db = this.getConnection(context);
      thisContact = new Contact(db, contactId);
      if (!(hasPermission(context, "contacts-external_contacts-folders-add")) || (thisContact.getOrgId() > 0 && !(hasPermission(
          context, "accounts-accounts-contacts-edit")))) {
        return ("PermissionError");
      }
      if (!isRecordAccessPermitted(context, thisContact)) {
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

      String selectedCatId = (String) context.getRequest().getParameter(
          "catId");
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
        resultCode = thisCategory.insert(db);
        Iterator groups = (Iterator) thisCategory.iterator();
        isValid = true;
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
        thisCategory.setCanNotContinue(false);
        if (isValid && resultCode != -1) {
          resultCode = thisCategory.insertGroup(
              db, thisCategory.getRecordId());
        }
      }
      context.getRequest().setAttribute("Category", thisCategory);
      if (resultCode == -1 || !isValid) {
        SystemStatus systemStatus = this.getSystemStatus(context);
        context.getRequest().setAttribute("systemStatus", systemStatus);
        if (thisCategory.getRecordId() != -1) {
          CustomFieldRecord record = new CustomFieldRecord(
              db, thisCategory.getRecordId());
          record.delete(db);
        }
        if (System.getProperty("DEBUG") != null) {
          System.out.println("Contacts-> InsertField validation error");
        }
        return getReturn(context, "AddFolderRecord");
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return (this.executeCommandFields(context));
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDeleteFields(ActionContext context) {
    Connection db = null;
    try {
      db = this.getConnection(context);
      String selectedCatId = context.getRequest().getParameter("catId");
      String recordId = context.getRequest().getParameter("recId");
      String contactId = context.getRequest().getParameter("contactId");

      Contact thisContact = new Contact(db, Integer.parseInt(contactId));
      if (!(hasPermission(
          context, "contacts-external_contacts-folders-delete")) || (thisContact.getOrgId() > 0 && !(hasPermission(
          context, "accounts-accounts-contacts-edit")))) {
        return ("PermissionError");
      }
      if (!isRecordAccessPermitted(context, thisContact)) {
        return ("PermissionError");
      }
      CustomFieldCategory thisCategory = new CustomFieldCategory(
          db,
          Integer.parseInt(selectedCatId));

      CustomFieldRecord thisRecord = new CustomFieldRecord(
          db, Integer.parseInt(recordId));
      thisRecord.setLinkModuleId(Constants.CONTACTS);
      thisRecord.setLinkItemId(Integer.parseInt(contactId));
      thisRecord.setCategoryId(Integer.parseInt(selectedCatId));
      if (!thisCategory.getReadOnly()) {
        thisRecord.delete(db);
      }
      context.getRequest().setAttribute("recordDeleted", "true");
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("DeleteFieldsOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandConfirmDelete(ActionContext context) {
    Connection db = null;
    Contact thisContact = null;
    HtmlDialog htmlDialog = new HtmlDialog();
    String id = null;

    if (context.getRequest().getParameter("id") != null) {
      id = context.getRequest().getParameter("id");
    }

    try {
      db = this.getConnection(context);
      SystemStatus systemStatus = this.getSystemStatus(context);
      thisContact = new Contact(db, id);
      if (!(hasPermission(context, "contacts-external_contacts-delete")) || (thisContact.getOrgId() > 0 && !(hasPermission(
          context, "accounts-accounts-contacts-edit")))) {
        return ("PermissionError");
      }
      if (!hasAuthority(db, context, thisContact)) {
        return ("PermissionError");
      }
      if (!isRecordAccessPermitted(context, thisContact)) {
        return ("PermissionError");
      }
      thisContact.checkUserAccount(db);
      DependencyList dependencies = thisContact.processDependencies(db);
      htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
      dependencies.setSystemStatus(systemStatus);
      htmlDialog.addMessage(
          systemStatus.getLabel("confirmdelete.caution") + "\n" + dependencies.getHtmlString());

      htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
      if (thisContact.getPrimaryContact()) {
        htmlDialog.setHeader(
            systemStatus.getLabel(
                "confirmdelete.contactIndividualAccountHeader"));
      } else {
        htmlDialog.setHeader(systemStatus.getLabel("confirmdelete.header"));
      }
      htmlDialog.addButton(
          systemStatus.getLabel("global.button.delete"), "javascript:window.location.href='ExternalContacts.do?command=Trash&id=" + id + RequestUtils.addLinkParams(
          context.getRequest(), "popupType|actionId|popup|sourcePopup") + "'");
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
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandPopupSelector(ActionContext context) {
    if (!(hasPermission(context, "contacts-external_contacts-view"))) {
      return ("PermissionError");
    }
    Connection db = null;
    String displayFieldId = null;
    ContactTypeList contactTypeList = null;

    if ("true".equals(context.getRequest().getParameter("reset"))) {
      context.getSession().removeAttribute("ContactTypeSelectorInfo");
    }
    PagedListInfo lookupSelectorInfo = this.getPagedListInfo(
        context, "ContactTypeSelectorInfo");

    HashMap selectedList = new HashMap();
    HashMap finalElementList = (HashMap) context.getSession().getAttribute(
        "finalElements");

    String category = context.getRequest().getParameter("category");
    String contactId = context.getRequest().getParameter("contactId");
    String previousSelection = context.getRequest().getParameter(
        "previousSelection");
    String previousSelectionDisplay = context.getRequest().getParameter(
        "previousSelectionDisplay");

    if (previousSelection != null) {
      StringTokenizer st = new StringTokenizer(previousSelection, "|");
      StringTokenizer st1 = new StringTokenizer(previousSelectionDisplay, "|");
      while (st.hasMoreTokens()) {
        selectedList.put(new Integer(st.nextToken()), st1.nextToken());
      }
    } else {
      //get selected list from the session
      selectedList = (HashMap) context.getSession().getAttribute(
          "selectedElements");
    }
    if (context.getRequest().getParameter("displayFieldId") != null) {
      displayFieldId = context.getRequest().getParameter("displayFieldId");
    }
    //Flush the selectedList if its a new selection
    if ("true".equals(context.getRequest().getParameter("reset"))) {
      if (context.getSession().getAttribute("finalElements") != null && context.getRequest().getParameter(
          "previousSelection") == null) {
        selectedList = (HashMap) ((HashMap) context.getSession().getAttribute(
            "finalElements")).clone();
      }
    }
    int rowCount = 1;
    while (context.getRequest().getParameter("hiddenelementid" + rowCount) != null)
    {
      int elementId = 0;
      String elementValue = "";
      elementId = Integer.parseInt(
          context.getRequest().getParameter("hiddenelementid" + rowCount));
      if (context.getRequest().getParameter("checkelement" + rowCount) != null)
      {
        if (context.getRequest().getParameter("elementvalue" + rowCount) != null)
        {
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

    if ("true".equals(context.getRequest().getParameter("finalsubmit"))) {
      finalElementList = (HashMap) selectedList;
      context.getSession().setAttribute("finalElements", finalElementList);
    }

    try {
      db = this.getConnection(context);
      contactTypeList = new ContactTypeList();
      contactTypeList.setPagedListInfo(lookupSelectorInfo);
      contactTypeList.setShowDisabled(false);
      contactTypeList.setIncludeDefinedByUser(this.getUserId(context));
      contactTypeList.setIncludeSelectedByUser(Integer.parseInt(contactId));
      if (previousSelection != null) {
        contactTypeList.setIncludeIds(previousSelection.replace('|', ','));
      }
      if ("accounts".equals(category)) {
        contactTypeList.setCategory(ContactType.ACCOUNT);
      } else {
        contactTypeList.setCategory(ContactType.GENERAL);
      }
      contactTypeList.buildList(db);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      context.getRequest().setAttribute("ContactTypeList", contactTypeList);
      this.freeConnection(context, db);
    }
    context.getSession().setAttribute("selectedElements", selectedList);
    context.getRequest().setAttribute("DisplayFieldId", displayFieldId);
    return ("PopupContactTypeOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandSendAddressUpdateRequest(ActionContext context) {
    if (!(hasPermission(context, "campaign-campaign-contact-updater-view"))) {
      return ("PermissionError");
    }
    Connection db = null;
    Contact thisContact = null;
    try {
      AuthenticationItem auth = new AuthenticationItem();
      db = auth.getConnection(context, false);
      String bcc = context.getRequest().getParameter("bcc");
      String cc = context.getRequest().getParameter("cc");

      int contactId = Integer.parseInt(
          context.getRequest().getParameter("id"));
      thisContact = new Contact(db, contactId);
      if (!isRecordAccessPermitted(context, thisContact)) {
        return ("PermissionError");
      }
      ContactEmailAddressList emailList = thisContact.getEmailAddressList();
      Iterator itr = emailList.iterator();
      ContactEmailAddress emailAddress = null;
      while (itr.hasNext()) {
        emailAddress = (ContactEmailAddress) itr.next();
        if (emailAddress.getPrimaryEmail()) {
          break;
        }
      }

      if (emailAddress == null) {
        throw new Exception("The contact does not have an email address");
      }

      SMTPMessage mail = new SMTPMessage();
      mail.setHost(
          ApplicationPrefs.getPref(context.getServletContext(), "MAILSERVER"));
      mail.setFrom(
          ApplicationPrefs.getPref(
              context.getServletContext(), "EMAILADDRESS"));
      mail.setType("text/html");
      mail.setTo(emailAddress.getEmail());
      mail.setSubject("Address update request");
      if (bcc != null && !"".equals(bcc)) {
        mail.addBcc(bcc);
        context.getRequest().setAttribute("bcc", bcc);
      }
      if (cc != null && !"".equals(cc)) {
        mail.addCc(cc);
        context.getRequest().setAttribute("cc", cc);
      }
      int tmpAddressSurveyId = Survey.getAddressSurveyId(db);
      String serverName = RequestUtils.getServerUrl(context.getRequest());
      Template template = new Template();
      template.setText(
          "<br>Please update your address at: <a href=\"http://" + serverName + "/ProcessAddressSurvey.do?id=${addressSurveyId=" + tmpAddressSurveyId + "}\">http://" + serverName + "/ProcessAddressSurvey.do?id=${addressSurveyId=" + tmpAddressSurveyId + "}</a>");

      //Get this database's key
      String filePath = getDbNamePath(context) + "keys" + fs;
      File f = new File(filePath);
      f.mkdirs();
      PrivateString thisKey = new PrivateString(filePath + "survey2.key");
      template.addParseElement(
          "${addressSurveyId=" + tmpAddressSurveyId + "}", java.net.URLEncoder.encode(
          PrivateString.encrypt(
              thisKey.getKey(), "addressSurveyId=" + tmpAddressSurveyId + ",cid=" + thisContact.getId()), "UTF-8"));
      mail.setBody(template.getParsedText());

      if (mail.send() == 2) {
        System.err.println(mail.getErrorMsg());
        throw new Exception("Error sending email");
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return this.executeCommandSearchContacts(context);
  }


  /**
   * Returns an access type list for repopulating a html select
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandAccessTypeJSList(ActionContext context) {
    String category = context.getRequest().getParameter("category");
    Connection db = null;
    try {
      db = getConnection(context);
      //get the access type list
      AccessTypeList accessTypeList = this.getSystemStatus(context).getAccessTypeList(
          db, Integer.parseInt(category));
      context.getRequest().setAttribute("AccessTypeList", accessTypeList);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "AccessTypeJSList");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandMoveToAccount(ActionContext context) {
    Connection db = null;
    Contact thisContact = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    HtmlDialog htmlDialog = new HtmlDialog();
    int contactId = Integer.parseInt(context.getRequest().getParameter("id"));
    try {
      db = this.getConnection(context);
      thisContact = new Contact(db, contactId);
      if (!(hasPermission(context, "contacts-external_contacts-delete")) || (thisContact.getOrgId() > 0 && !(hasPermission(
          context, "accounts-accounts-contacts-move-view")))) {
        return ("PermissionError");
      }
      if (!hasAuthority(db, context, thisContact)) {
        return ("PermissionError");
      }
      if (!isRecordAccessPermitted(context, thisContact)) {
        return ("PermissionError");
      }
      thisContact.checkUserAccount(db);
      DependencyList dependencies = thisContact.processDependencies(db);
      dependencies.setSystemStatus(systemStatus);
      htmlDialog.addMessage(
          systemStatus.getLabel("confirmdelete.caution") + "\n" + thisContact.getHtmlString(
              dependencies, systemStatus));

      if (thisContact.getPrimaryContact()) {
        htmlDialog.setTitle(
            systemStatus.getLabel("accounts.contacts.moveTitle"));
        htmlDialog.setHeader(
            systemStatus.getLabel(
                "accounts.contacts.contactIndividualAccountHeader"));
        htmlDialog.addButton(
            systemStatus.getLabel("button.ok"), "javascript:parent.window.close()");
        context.getSession().setAttribute("Dialog", htmlDialog);
        return ("ConfirmDeleteOK");
      } else if (!thisContact.canMoveContact(dependencies)) {
        htmlDialog.setTitle(
            systemStatus.getLabel("accounts.contacts.moveTitle"));
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
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandMoveContact(ActionContext context) {
    Connection db = null;
    Contact thisContact = null;
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
      if (!(hasPermission(context, "contacts-external_contacts-delete")) || (thisContact.getOrgId() > 0 && !(hasPermission(
          context, "accounts-accounts-contacts-move-view")))) {
        return ("PermissionError");
      }
      if (!hasAuthority(db, context, thisContact)) {
        return ("PermissionError");
      }
      if (!isRecordAccessPermitted(context, thisContact)) {
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
   * Prepare form for sending a message to an action contact
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandPrepareMessage(ActionContext context) {
    String actionSource = context.getRequest().getParameter("actionSource");
    boolean hasAccountContactsMessagesPermission = hasPermission(
        context, "accounts-accounts-contacts-messages-view");
    boolean hasExternalContactsMessagesPermission = hasPermission(
        context, "contacts-external_contacts-messages-view");
    if (actionSource != null && "AccountContactsMessages".equals(actionSource))
    {
      if (!hasAccountContactsMessagesPermission) {
        return ("PermissionError");
      }
    } else {
      if (!hasExternalContactsMessagesPermission) {
        return ("PermissionError");
      }
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
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandSendMessage(ActionContext context) {
    String actionSource = context.getRequest().getParameter("actionSource");
    boolean hasAccountContactsMessagesPermission = hasPermission(
        context, "accounts-accounts-contacts-messages-view");
    boolean hasExternalContactsMessagesPermission = hasPermission(
        context, "contacts-external_contacts-messages-view");
    if (actionSource != null && "AccountContactsMessages".equals(actionSource))
    {
      if (!hasAccountContactsMessagesPermission) {
        return ("PermissionError");
      }
    } else {
      if (!hasExternalContactsMessagesPermission) {
        return ("PermissionError");
      }
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
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandWorkAsAccount(ActionContext context) {
    if (!(hasPermission(context, "sales-leads-edit"))) {
      return ("PermissionError");
    }
    Connection db = null;
    addModuleBean(context, "Add Account", "Add Account");
    String contactId = (String) context.getRequest().getParameter("contactId");
    int orgId = -1;
    Organization thisOrg = null;
    try {
      db = getConnection(context);

      //insert an account if company name exists
      boolean status = false;
      Contact contact = new Contact(db, contactId);
      Contact oldContact = new Contact(db, contactId);

      if (contact.getCompany() != null && !"".equals(contact.getCompany().trim()))
      {
        orgId = Organization.lookupAccount(db, contact.getCompany(), -1, contact.getSiteId());
      } else {
        orgId = Organization.lookupAccount(db, contact.getNameFirst(), contact.getNameMiddle(), contact.getNameLast(), -1, contact.getSiteId());
      }

      if (orgId > 0) {
        thisOrg = new Organization(db, orgId);
      }
      if (orgId < 0) {
        thisOrg = new Organization();
        thisOrg.setEnteredBy(this.getUserId(context));
        if (contact.getCompany() == null || "".equals(contact.getCompany().trim()))
        {
          contact.setPrimaryContact(true);
          thisOrg.setNameFirst(contact.getNameFirst());
          thisOrg.setNameLast(contact.getNameLast());
          thisOrg.setNameMiddle(contact.getNameMiddle());
          thisOrg.setName(thisOrg.getNameLastFirstMiddle());
        } else {
          thisOrg.setName(contact.getCompany());
        }
      }
      thisOrg.setSiteId(contact.getSiteId());
      thisOrg.setModifiedBy(this.getUserId(context));
      if ((thisOrg.getId() > 0 && thisOrg.getAccountNumber() != null &&
          contact.getAccountNumber() != null &&
          !contact.getAccountNumber().equals(thisOrg.getAccountNumber()))
          || thisOrg.getOrgId() == -1) {
        thisOrg.setAccountNumber(contact.getAccountNumber());
      }
      if ((thisOrg.getId() > 0 && contact.getOwner() != thisOrg.getOwner())
          || thisOrg.getOrgId() == -1) {
        thisOrg.setOwner(contact.getOwner());
      }
      if ((thisOrg.getId() > 0 && contact.getPotential() != thisOrg.getPotential())
          || thisOrg.getOrgId() == -1) {
        thisOrg.setPotential(contact.getPotential());
      }
      if ((thisOrg.getId() > 0 && contact.getIndustryTempCode() != thisOrg.getIndustry())
          || thisOrg.getOrgId() == -1) {
        thisOrg.setIndustry(contact.getIndustryTempCode());
      }
      if ((thisOrg.getId() > 0 && contact.getSource() != thisOrg.getSource())
          || thisOrg.getOrgId() == -1) {
        thisOrg.setSource(contact.getSource());
      }
      if ((thisOrg.getId() > 0 && contact.getRating() != thisOrg.getRating())
          || thisOrg.getOrgId() == -1) {
        thisOrg.setRating(contact.getRating());
      }

      copyPropertiesFromContactToOrganization(contact, thisOrg);
      
      //Copy postal address from contact to organization
      ContactAddressList contactAddressList = new ContactAddressList();
      contactAddressList.setContactId(contact.getId());
      contactAddressList.buildList(db);
      Iterator addressIterator = contactAddressList.iterator();
      while (addressIterator.hasNext()) {
        //DEVELOPER NOTE: probably needs to be in  the OrganizationEmailAddress Constructor
        ContactAddress contactAddress = (ContactAddress) addressIterator.next();
        OrganizationAddress organizationAddress = new OrganizationAddress();
        organizationAddress.setStreetAddressLine1(contactAddress.getStreetAddressLine1());
        organizationAddress.setStreetAddressLine2(contactAddress.getStreetAddressLine2());
        organizationAddress.setStreetAddressLine3(contactAddress.getStreetAddressLine3());
        organizationAddress.setCity(contactAddress.getCity());
        organizationAddress.setState(contactAddress.getState());
        organizationAddress.setZip(contactAddress.getZip());
        organizationAddress.setCountry(contactAddress.getCountry());

        LookupList contactAddressTypeList = new LookupList(db, "lookup_contactaddress_types");
        LookupList orgAddressTypeList = null;
        String contactAddressType = contactAddressTypeList.getValueFromId(contactAddress.getType());
        int orgAddressType = -1;
        if (contactAddress.getType() != -1) {
          if (contactAddressType.indexOf("Business") > -1) {
            orgAddressTypeList = new LookupList(db, "lookup_orgaddress_types");
            orgAddressType = orgAddressTypeList.getIdFromValue("Primary");
          } else {
            orgAddressTypeList = new LookupList(db, "lookup_orgaddress_types");
            orgAddressType = orgAddressTypeList.getIdFromValue("Auxiliary");
          }
          organizationAddress.setType(orgAddressType);
        }
        organizationAddress.setPrimaryAddress(contactAddress.getPrimaryAddress());
        thisOrg.getAddressList().add(organizationAddress);
      }
      //Copy email address from contact to organization
      ContactEmailAddressList contactEmailAddressList = new ContactEmailAddressList();
      contactEmailAddressList.setContactId(contact.getId());
      contactEmailAddressList.buildList(db);
      Iterator emailAddressIterator = contactEmailAddressList.iterator();
      while (emailAddressIterator.hasNext()) {
        //DEVELOPER NOTE: probably needs to be in  the OrganizationEmailAddress Constructor
        ContactEmailAddress contactEmailAddress = (ContactEmailAddress) emailAddressIterator.next();
        OrganizationEmailAddress organizationEmailAddress = new OrganizationEmailAddress();
        organizationEmailAddress.setEmail(contactEmailAddress.getEmail());

        LookupList contactEmailAddressTypeList = new LookupList(db, "lookup_contactemail_types");
        LookupList orgEmailAddressTypeList = null;
        String contactEmailAddressType = contactEmailAddressTypeList.getValueFromId(contactEmailAddress.getType());
        int orgEmailAddressType = -1;
        if (contactEmailAddressType.indexOf("Business") > -1) {
          orgEmailAddressTypeList = new LookupList(db, "lookup_orgemail_types");
          orgEmailAddressType = orgEmailAddressTypeList.getIdFromValue("Primary");
        } else {
          orgEmailAddressTypeList = new LookupList(db, "lookup_orgemail_types");
          orgEmailAddressType = orgEmailAddressTypeList.getIdFromValue("Auxiliary");
        }

        organizationEmailAddress.setType(orgEmailAddressType);
        organizationEmailAddress.setPrimaryEmail(contactEmailAddress.getPrimaryEmail());
        thisOrg.getEmailAddressList().add(organizationEmailAddress);
      }
      //Copy phone numbers from contact to organization
      ContactPhoneNumberList contactPhoneNumberList = new ContactPhoneNumberList();
      contactPhoneNumberList.setContactId(contact.getId());
      contactPhoneNumberList.buildList(db);
      Iterator phoneNumberIterator = contactPhoneNumberList.iterator();
      while (phoneNumberIterator.hasNext()) {
        //DEVELOPER NOTE: probably needs to be in  the OrganizationPhoneNumber Constructor
        ContactPhoneNumber contactPhoneNumber = (ContactPhoneNumber) phoneNumberIterator.next();
        OrganizationPhoneNumber organizationPhoneNumber = new OrganizationPhoneNumber();
        organizationPhoneNumber.setNumber(contactPhoneNumber.getNumber());
        organizationPhoneNumber.setExtension(contactPhoneNumber.getExtension());

        LookupList contactPhoneNumberTypeList = new LookupList(db, "lookup_contactphone_types");
        LookupList orgPhoneNumberTypeList = null;
        String contactPhoneNumberType = contactPhoneNumberTypeList.getValueFromId(contactPhoneNumber.getType());
        int orgPhoneNumberType = -1;
        if (contactPhoneNumberType.indexOf("Fax") > -1) {
          orgPhoneNumberTypeList = new LookupList(db, "lookup_orgphone_types");
          orgPhoneNumberType = orgPhoneNumberTypeList.getIdFromValue("Fax");
        } else {
          orgPhoneNumberTypeList = new LookupList(db, "lookup_orgphone_types");
          orgPhoneNumberType = orgPhoneNumberTypeList.getIdFromValue("Main");
        }

        organizationPhoneNumber.setType(orgPhoneNumberType);
        organizationPhoneNumber.setPrimaryNumber(contactPhoneNumber.getPrimaryNumber());
        thisOrg.getPhoneNumberList().add(organizationPhoneNumber);
      }

      if (thisOrg.getOrgId() > 0) {
        status = (thisOrg.update(db) == 1);
      } else {
        thisOrg.setInsertPrimaryContact(false);
        status = thisOrg.insert(db);
      }
      if (status) {
        context.getRequest().setAttribute("orgId", String.valueOf(thisOrg.getOrgId()));
      }

      contact.setOrgId(thisOrg.getOrgId());
      contact.setOrgName(thisOrg.getName());

      if (status) {
        contact.update(db, context);
        if ((contact.getNameLast() == null) || "".equals(contact.getNameLast().trim()))
        {
          contact.disable(db);
        }
        this.addRecentItem(context, thisOrg);
        this.deleteRecentItem(context, oldContact);
        processUpdateHook(context, oldContact, contact);
        processInsertHook(context, thisOrg);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace();
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if ("true".equals(context.getRequest().getParameter("popup"))) {
      return "CloseAndReloadOK";
    }
    return "WorkAccountOK";
  }
  
  private static void copyPropertiesFromContactToOrganization(Contact from, Organization to){
    to.setRevenue( from.getRevenue() );
    to.setEmployees( from.getEmployees() );
    to.setDunsType( from.getDunsType() );
    to.setYearStarted( from.getYearStarted() );
    to.setDunsNumber( from.getDunsNumber() );
    to.setBusinessNameTwo( from.getBusinessNameTwo() );
    to.setSicDescription( from.getSicDescription() );
    to.setSicCode( from.getSicCode() );
  }

  public String executeCommandStates(ActionContext context) {
    String country = context.getRequest().getParameter("country");
    String form = context.getRequest().getParameter("form");
    String obj = context.getRequest().getParameter("obj");
    String stateObj = context.getRequest().getParameter("stateObj");
    String defaultValue = context.getRequest().getParameter("selected");
    SystemStatus systemStatus = this.getSystemStatus(context);
    StateSelect stateSelect = new StateSelect(systemStatus, country);
    context.getRequest().setAttribute("stateSelect", stateSelect.getHtmlSelectObj(country));
    context.getRequest().setAttribute("form", form);
    context.getRequest().setAttribute("obj", obj);
    context.getRequest().setAttribute("stateObj", stateObj);
    if (defaultValue != null) {
      context.getRequest().setAttribute("selected", defaultValue);
    }
    return "StatesOK";
  }
  
  public String executeCommandDashboards(ActionContext context) {
    if (!hasPermission(context, "contacts-external_contacts-dashboards-view")) {
    	return executeCommandSearchContactsForm(context);
    }
  	String moduleId = Integer.toString(Constants.CONTACTS);
  	String dashboardsContainerName = "dashboards" + moduleId;
  	context.getRequest().setAttribute("moduleId", moduleId);
  	context.getRequest().setAttribute("dashboardsContainerName", dashboardsContainerName);
  	context.getRequest().setAttribute("action", context.getRequest().getAttribute("moduleAction"));
  	return ("DashboardsViewOK");
  }

}
