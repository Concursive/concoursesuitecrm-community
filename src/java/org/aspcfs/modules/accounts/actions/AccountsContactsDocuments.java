/*
 *  Copyright(c) 2007 Concursive Corporation (http://www.concursive.com/) All
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

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.accounts.base.Organization;

import com.darkhorseventures.framework.actions.ActionContext;
import com.isavvix.tools.FileInfo;
import com.isavvix.tools.HttpMultiPartParser;
import com.zeroio.iteam.actions.ProjectManagementFileFolders;
import com.zeroio.iteam.base.FileFolderHierarchy;
import com.zeroio.iteam.base.FileFolderList;
import com.zeroio.iteam.base.FileItem;
import com.zeroio.iteam.base.FileItemList;
import com.zeroio.iteam.base.FileItemVersion;
import com.zeroio.webutils.FileDownload;

/**
 * Description of the Class
 *
 * @author zhenya.zhidok
 * @version $Id: AccountsContactsDocuments.java zhenya.zhidok $
 * @created 28.12.2006
 *
 */

public final class AccountsContactsDocuments extends CFSModule {

  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandView(ActionContext context) {
    if (!(hasPermission(context, "accounts-accounts-contacts-documents-view"))) {
      return ("PermissionError");
    }
    String contactId = (String) context.getRequest().getParameter("contactId");
    if (contactId == null && !"".equals(contactId)) {
      contactId = (String) context.getRequest().getAttribute("contactId");
    }
    String folderId = context.getRequest().getParameter("folderId");
    if (folderId == null) {
      folderId = (String) context.getRequest().getAttribute("folderId");
    }

    Exception errorMessage = null;
    Connection db = null;
    Contact thisContact = null;
    Organization thisOrganization = null;

    try {
      db = getConnection(context);
      //Check access permission to contact record
      if (!isRecordAccessPermitted(context, db, Integer.parseInt(contactId))) {
        return ("PermissionError");
      }
      thisContact = new Contact(db, Integer.parseInt(contactId));
      context.getRequest().setAttribute("ContactDetails", thisContact);
      thisOrganization = new Organization(db, thisContact.getOrgId());
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
      //Build the folder list
      FileFolderList folders = new FileFolderList();
      if (folderId == null || "-1".equals(folderId) || "0".equals(folderId) || "".equals(
          folderId)) {
        folders.setTopLevelOnly(true);
      } else {
        folders.setParentId(Integer.parseInt(folderId));
        //Build array of folder trails
        ProjectManagementFileFolders.buildHierarchy(db, context);
      }
      folders.setLinkModuleId(Constants.CONTACTS);
      folders.setLinkItemId(thisContact.getId());
      folders.setBuildItemCount(true);
      folders.buildList(db);

      //Build the file item list
      FileItemList files = new FileItemList();
      if (folderId == null || "-1".equals(folderId) || "0".equals(folderId) || "".equals(
          folderId)) {
        files.setTopLevelOnly(true);
      } else {
        files.setFolderId(Integer.parseInt(folderId));
      }
      
      files.setBuildPortalRecords(
          isPortalUser(context) ? Constants.TRUE : Constants.UNDEFINED);
      files.setLinkModuleId(Constants.CONTACTS);
      files.setLinkItemId(thisContact.getId());
      files.buildList(db);

      context.getRequest().setAttribute("ContactDetails", thisContact);
      context.getRequest().setAttribute("fileItemList", files);
      context.getRequest().setAttribute("fileFolderList", folders);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "View Contacts", "View Documents");
    if (errorMessage == null) {
      String contactfolders = context.getRequest().getParameter("contactfolders");
      if (contactfolders != null && "true".equals(contactfolders)) {
        return "ViewContactFolderPopupOK";
      }
      return (getReturn(context, "View"));
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
    
    if (!(hasPermission(context, "accounts-accounts-contacts-documents-add"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    Connection db = null;
    Contact thisContact = null;
    Organization thisOrganization = null;

    try {
      db = getConnection(context);
      thisContact = addContact(context, db);
      thisOrganization = new Organization(db, thisContact.getOrgId());
      //Check access permission to contact record
      if (!isRecordAccessPermitted(context, db, thisContact.getId())) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("ContactDetails", thisContact);
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
      String folderId = context.getRequest().getParameter("folderId");
      if (folderId != null) {
        context.getRequest().setAttribute("folderId", folderId);
      }
      //Build array of folder trails
      ProjectManagementFileFolders.buildHierarchy(db, context);

    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "View Contact", "Upload Document");
    if (errorMessage == null) {
      return getReturn(context, "Add");
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
  public String executeCommandUpload(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-contacts-documents-add")) {
      return ("PermissionError");
    }
    Connection db = null;
    Contact thisContact = null;
    Organization thisOrganization = null;
    boolean recordInserted = false;
    boolean isValid = false;
    try {
      String filePath = this.getPath(context, "contacts");
      //Process the form data
      HttpMultiPartParser multiPart = new HttpMultiPartParser();
      multiPart.setUsePathParam(false);
      multiPart.setUseUniqueName(true);
      multiPart.setUseDateForFolder(true);
      multiPart.setExtensionId(getUserId(context));
      HashMap parts = multiPart.parseData(context.getRequest(), filePath);
      String id = (String) parts.get("id");
      String subject = (String) parts.get("subject");
      String folderId = (String) parts.get("folderId");
      String allowPortalAccess = (String) parts.get("allowPortalAccess");
      if (folderId != null) {
        context.getRequest().setAttribute("folderId", folderId);
      }
      if (subject != null) {
        context.getRequest().setAttribute("subject", subject);
      }
      db = getConnection(context);
      thisContact = addContact(context, db, id);
      thisOrganization = new Organization(db, thisContact.getOrgId());
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
      //Check access permission to contact record
      if (!isRecordAccessPermitted(context, db, thisContact.getId())) {
        return ("PermissionError");
      }
      if ((Object) parts.get("id" + (String) parts.get("id")) instanceof FileInfo) {
        //Update the database with the resulting file
        FileInfo newFileInfo = (FileInfo) parts.get("id" + id);
        //Insert a file description record into the database
        FileItem thisItem = new FileItem();
        thisItem.setLinkModuleId(Constants.CONTACTS);
        thisItem.setLinkItemId(thisContact.getId());
        thisItem.setEnteredBy(getUserId(context));
        thisItem.setModifiedBy(getUserId(context));
        thisItem.setFolderId(Integer.parseInt(folderId));
        thisItem.setSubject(subject);
        thisItem.setClientFilename(newFileInfo.getClientFileName());
        thisItem.setFilename(newFileInfo.getRealFilename());
        thisItem.setVersion(1.0);
        thisItem.setSize(newFileInfo.getSize());
        thisItem.setAllowPortalAccess(allowPortalAccess);
        isValid = this.validateObject(context, db, thisItem);
        if (isValid) {
          recordInserted = thisItem.insert(db);
        }
        if (recordInserted) {
          this.processInsertHook(context, thisItem);
          context.getRequest().setAttribute("fileItem", thisItem);
          context.getRequest().setAttribute("subject", "");
        }
      } else {
        recordInserted = false;
        HashMap errors = new HashMap();
        SystemStatus systemStatus = this.getSystemStatus(context);
        errors.put(
            "actionError", systemStatus.getLabel(
                "object.validation.incorrectFileName"));
        if (subject != null && "".equals(subject.trim())) {
          errors.put(
              "subjectError", systemStatus.getLabel(
                  "object.validation.required"));
        }
        processErrors(context, errors);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      freeConnection(context, db);
    }

    if (recordInserted) {
      return ("UploadOK");
    }
    return (executeCommandAdd(context));
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandAddVersion(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-contacts-documents-add")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    Contact thisContact = null;
    Organization thisOrganization = null;
    String itemId = (String) context.getRequest().getParameter("fid");
    if (itemId == null) {
      itemId = (String) context.getRequest().getAttribute("fid");
    }
    String folderId = (String) context.getParameter("folderId");
    if (folderId == null) {
      folderId = (String) context.getRequest().getAttribute("folderId");
    }
    if (folderId != null) {
      context.getRequest().setAttribute("folderId", folderId);
    }
    Connection db = null;
    try {
      db = getConnection(context);
      thisContact = addContact(context, db);
      //Check access permission to contact record
      if (!isRecordAccessPermitted(context, db, thisContact.getId())) {
        return ("PermissionError");
      }
      FileItem thisFile = new FileItem(
          db, Integer.parseInt(itemId), thisContact.getId(), Constants.CONTACTS);
      context.getRequest().setAttribute("FileItem", thisFile);
      thisOrganization = new Organization(db, thisContact.getOrgId());
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
      //Build array of folder trails
      ProjectManagementFileFolders.buildHierarchy(db, context);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "View Contacts", "Upload New Document Version");
    if (errorMessage == null) {
      return getReturn(context, "AddVersion");
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
  public String executeCommandUploadVersion(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-contacts-documents-add")) {
      return ("PermissionError");
    }
    Connection db = null;
    Contact thisContact = null;
    Organization thisOrganization = null;
    boolean recordInserted = false;
    boolean isValid = false;
    try {
      String filePath = this.getPath(context, "contacts");
      //Process the form data
      HttpMultiPartParser multiPart = new HttpMultiPartParser();
      multiPart.setUsePathParam(false);
      multiPart.setUseUniqueName(true);
      multiPart.setUseDateForFolder(true);
      multiPart.setExtensionId(getUserId(context));
      HashMap parts = multiPart.parseData(context.getRequest(), filePath);
      String id = (String) parts.get("id");
      String itemId = (String) parts.get("fid");
      String subject = (String) parts.get("subject");
      String versionId = (String) parts.get("versionId");
      String folderId = (String) parts.get("folderId");
      String allowPortalAccess = (String) parts.get("allowPortalAccess");
      if (folderId != null) {
        context.getRequest().setAttribute("folderId", folderId);
      }
      if (subject != null) {
        context.getRequest().setAttribute("subject", subject);
      }
      db = getConnection(context);
      thisContact = addContact(context, db, id);
      thisOrganization = new Organization(db, thisContact.getOrgId());
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
      //Check access permission to contact record
      if (!isRecordAccessPermitted(context, db, thisContact.getId())) {
        return ("PermissionError");
      }
      if ((Object) parts.get("id" + (String) parts.get("id")) instanceof FileInfo) {
        //Update the database with the resulting file
        FileInfo newFileInfo = (FileInfo) parts.get("id" + id);

        FileItem previousItem = null;
        FileItem thisItem = new FileItem();
        thisItem.setLinkModuleId(Constants.CONTACTS);
        thisItem.setLinkItemId(thisContact.getId());
        thisItem.setId(Integer.parseInt(itemId));
        previousItem = new FileItem(
            db, Integer.parseInt(itemId),thisItem.getLinkItemId(), thisItem.getLinkModuleId());
        thisItem.setEnteredBy(getUserId(context));
        thisItem.setModifiedBy(getUserId(context));
        thisItem.setSubject(subject);
        thisItem.setClientFilename(newFileInfo.getClientFileName());
        thisItem.setFilename(newFileInfo.getRealFilename());
        thisItem.setVersion(Double.parseDouble(versionId));
        thisItem.setSize(newFileInfo.getSize());
        thisItem.setAllowPortalAccess(allowPortalAccess);
        isValid = this.validateObject(context, db, thisItem);
        if (isValid) {
          recordInserted = thisItem.insertVersion(db);
        }
        if (recordInserted) {
          this.processUpdateHook(context, previousItem, thisItem);
        }
      } else {
        recordInserted = false;
        HashMap errors = new HashMap();
        SystemStatus systemStatus = this.getSystemStatus(context);
        errors.put(
            "actionError", systemStatus.getLabel(
                "object.validation.incorrectFileName"));
        if (subject != null && "".equals(subject.trim())) {
          errors.put(
              "subjectError", systemStatus.getLabel(
                  "object.validation.required"));
        }
        processErrors(context, errors);
      }
      context.getRequest().setAttribute("fid", itemId);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      freeConnection(context, db);
    }

    if (recordInserted) {
      return ("UploadOK");
    }
    return (executeCommandAddVersion(context));
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDetails(ActionContext context) {

    if (!(hasPermission(context, "accounts-accounts-contacts-documents-view"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    Connection db = null;
    Organization thisOrganization = null;
    String folderId = context.getRequest().getParameter("folderId");
    String itemId = (String) context.getRequest().getParameter("fid");

    try {
      db = getConnection(context);
      Contact thisContact = addContact(context, db);
      thisOrganization = new Organization(db, thisContact.getOrgId());
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
      //Check access permission to contact record
      if (!isRecordAccessPermitted(context, db, thisContact.getId())) {
        return ("PermissionError");
      }

      FileItem thisItem = new FileItem(
          db, Integer.parseInt(itemId), thisContact.getId(), Constants.CONTACTS);
      thisItem.buildVersionList(db, isPortalUser(context));
      if (folderId == null) {
        folderId = "" + thisItem.getFolderId();
      }
      if (folderId != null) {
        context.getRequest().setAttribute("folderId", folderId);
      }
      if (folderId != null && !"-1".equals(folderId) && !"0".equals(folderId) && !"".equals(
          folderId.trim())) {
        //Build array of folder trails
        ProjectManagementFileFolders.buildHierarchy(db, context);
      }
      context.getRequest().setAttribute("FileItem", thisItem);

    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "View Contacts", "Document Details");
    if (errorMessage == null) {
      return getReturn(context, "Details");
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
  public String executeCommandDownload(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-contacts-documents-view")) {
      return ("PermissionError");
    }
    SystemStatus systemStatus = this.getSystemStatus(context);
    Exception errorMessage = null;
    String itemId = (String) context.getRequest().getParameter("fid");
    String version = (String) context.getRequest().getParameter("ver");
    String view = (String) context.getRequest().getParameter("view");
    FileItem thisItem = null;
    Connection db = null;
    Contact thisContact = null;
    Organization thisOrganization = null;
    try {
      db = getConnection(context);
      thisContact = addContact(context, db);
      thisOrganization = new Organization(db, thisContact.getOrgId());
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
      //Check access permission to contact record
      if (!isRecordAccessPermitted(context, db, thisContact.getId())) {
        return ("PermissionError");
      }
      thisItem = new FileItem(
          db, Integer.parseInt(itemId), thisContact.getId(), Constants.CONTACTS);
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
        String filePath = this.getPath(context, "contacts") + getDatePath(
            itemToDownload.getModified()) + itemToDownload.getFilename();
        FileDownload fileDownload = new FileDownload();
        fileDownload.setFullPath(filePath);
        fileDownload.setDisplayName(itemToDownload.getClientFilename());
        if (fileDownload.fileExists()) {
          if (view != null && "true".equals(view)) {
            fileDownload.setFileTimestamp(thisItem.getModificationDate().getTime());
            fileDownload.streamContent(context);
          } else {
            fileDownload.sendFile(context);
          }
          //Get a db connection now that the download is complete
          db = getConnection(context);
          itemToDownload.updateCounter(db);
        } else {
          db = null;
          System.err.println(
              "AccountsContactsDocuments-> Trying to send a file that does not exist");
          context.getRequest().setAttribute(
              "actionError", systemStatus.getLabel(
                  "object.validation.actionError.downloadDoesNotExist"));
          return (executeCommandView(context));
        }
      } else {
        FileItemVersion itemToDownload = thisItem.getVersion(
            Double.parseDouble(version));
        itemToDownload.setEnteredBy(this.getUserId(context));
        String filePath = this.getPath(context, "contacts") + getDatePath(
            itemToDownload.getModified()) + itemToDownload.getFilename();
        FileDownload fileDownload = new FileDownload();
        fileDownload.setFullPath(filePath);
        fileDownload.setDisplayName(itemToDownload.getClientFilename());
        if (fileDownload.fileExists()) {
          if (view != null && "true".equals(view)) {
            fileDownload.setFileTimestamp(itemToDownload.getModificationDate().getTime());
            fileDownload.streamContent(context);
          } else {
            fileDownload.sendFile(context);
          }
          //Get a db connection now that the download is complete
          db = getConnection(context);
          itemToDownload.updateCounter(db);
        } else {
          db = null;
          System.err.println(
              "AccountsContactsDocuments-> Trying to send a file that does not exist");
          context.getRequest().setAttribute(
              "actionError", systemStatus.getLabel(
                  "object.validation.actionError.downloadDoesNotExist"));
          return (executeCommandView(context));
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
      addModuleBean(context, "View Contacts", "");
      return ("SystemError");
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandModify(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-contacts-documents-edit")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    String itemId = (String) context.getRequest().getParameter("fid");
    String folderId = context.getRequest().getParameter("folderId");
    if (folderId != null) {
      context.getRequest().setAttribute("folderId", folderId);
    }
    Connection db = null;
    Contact thisContact = null;
    Organization thisOrganization = null;
    try {
      db = getConnection(context);
      thisContact = addContact(context, db);
      thisOrganization = new Organization(db, thisContact.getOrgId());
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
      //Check access permission to contact record
      if (!isRecordAccessPermitted(context, db, thisContact.getId())) {
        return ("PermissionError");
      }
      FileItem thisItem = new FileItem(
          db, Integer.parseInt(itemId), thisContact.getId(), Constants.CONTACTS);
      thisItem.buildVersionList(db);
      context.getRequest().setAttribute("FileItem", thisItem);

      //Build array of folder trails
      ProjectManagementFileFolders.buildHierarchy(db, context);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "View Contacts", "Modify Document Information");
    if (errorMessage == null) {
      return getReturn(context, "Modify");
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
  public String executeCommandUpdate(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-contacts-documents-edit")) {
      return ("PermissionError");
    }
    boolean recordInserted = false;
    boolean isValid = false;
    String itemId = (String) context.getRequest().getParameter("fid");
    String subject = (String) context.getRequest().getParameter("subject");
    String filename = (String) context.getRequest().getParameter(
        "clientFilename");
    String allowPortalAccess = (String) context.getRequest().getParameter(
        "allowPortalAccess");
    Connection db = null;
    Contact thisContact = null;
    Organization thisOrganization = null;
    try {
      db = getConnection(context);
      thisContact = addContact(context, db);
      thisOrganization = new Organization(db, thisContact.getOrgId());
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
      //Check access permission to contact record
      if (!isRecordAccessPermitted(context, db, thisContact.getId())) {
        return ("PermissionError");
      }
      FileItem thisItem = new FileItem(
          db, Integer.parseInt(itemId), thisContact.getId(), Constants.CONTACTS);
      FileItem previousItem = new FileItem(
          db, Integer.parseInt(itemId), thisContact.getId(), Constants.CONTACTS);
      thisItem.setClientFilename(filename);
      thisItem.setSubject(subject);
      thisItem.setAllowPortalAccess(allowPortalAccess);
      isValid = this.validateObject(context, db, thisItem);
      if (isValid) {
        recordInserted = thisItem.update(db);
      }
      if (recordInserted) {
        this.processUpdateHook(context, previousItem, thisItem);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "View Contacts", "");
    if (recordInserted && isValid) {
      return ("UpdateOK");
    } else {
      context.getRequest().setAttribute("fid", itemId);
      return (executeCommandModify(context));
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDelete(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-contacts-documents-delete")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    boolean recordDeleted = false;
    String itemId = (String) context.getRequest().getParameter("fid");
    Connection db = null;
    Contact thisContact = null;
    Organization thisOrganization = null;
    try {
      db = getConnection(context);
      thisContact = addContact(context, db);
      thisOrganization = new Organization(db, thisContact.getOrgId());
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
      //Check access permission to contact record
      if (!isRecordAccessPermitted(context, db, thisContact.getId())) {
        return ("PermissionError");
      }
      FileItem thisItem = new FileItem(
          db, Integer.parseInt(itemId), thisContact.getId(), Constants.CONTACTS);
      recordDeleted = thisItem.delete(db, this.getPath(context, "contacts"));
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "View Contacts", "Delete Document");
    if (errorMessage == null) {
      if (recordDeleted) {
        return ("DeleteOK");
      } else {
        return ("DeleteERROR");
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   * Adds a feature to the Contact attribute of the ContactsDocuments
   * object
   *
   * @param context The feature to be added to the Contact
   *                attribute
   * @param db      The feature to be added to the Contact
   *                attribute
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  private Contact addContact(ActionContext context, Connection db) throws SQLException {
    String contactId = (String) context.getRequest().getParameter(
        "contactId");
    if (contactId == null) {
      contactId = (String) context.getRequest().getAttribute("contactId");
    }
    return addContact(context, db, contactId);
  }


  /**
   * Adds a feature to the Contact attribute of the ContactsDocuments
   * object
   *
   * @param context        The feature to be added to the Contact
   *                       attribute
   * @param db             The feature to be added to the Contact
   *                       attribute
   * @param contactId The feature to be added to the Contact
   *                       attribute
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  private Contact addContact(ActionContext context, Connection db, String contactId) throws SQLException {
    context.getRequest().setAttribute("contactId", contactId);
    Contact thisContact = new Contact(
        db, Integer.parseInt(contactId));
    context.getRequest().setAttribute("ContactDetails", thisContact);
    return thisContact;
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandMove(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-contacts-documents-edit")) {
      return ("PermissionError");
    }
    Connection db = null;
    //Parameters
    String itemId = (String) context.getRequest().getParameter("fid");
    Contact thisContact = null;
    Organization thisOrganization = null;
    try {
      db = getConnection(context);
      thisContact = addContact(context, db);
      thisOrganization = new Organization(db, thisContact.getOrgId());
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
      //Check access permission to contact record
      if (!isRecordAccessPermitted(context, db, thisContact.getId())) {
        return ("PermissionError");
      }
      //Load the file
      FileItem thisItem = new FileItem(
          db, Integer.parseInt(itemId), thisContact.getId(), Constants.CONTACTS);
      context.getRequest().setAttribute("FileItem", thisItem);
      //Load the folders
      FileFolderHierarchy hierarchy = new FileFolderHierarchy();
      hierarchy.setLinkModuleId(Constants.CONTACTS);
      hierarchy.setLinkItemId(thisContact.getId());
      hierarchy.build(db);
      context.getRequest().setAttribute("folderHierarchy", hierarchy);
      return "MoveOK";
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandSaveMove(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-contacts-documents-edit")) {
      return ("PermissionError");
    }
    Connection db = null;
    //Parameters
    String newFolderId = (String) context.getRequest().getParameter(
        "folderId");
    String itemId = (String) context.getRequest().getParameter("fid");
    Contact thisContact = null;
    Organization thisOrganization = null;
    try {
      db = getConnection(context);
      thisContact = addContact(context, db);
      thisOrganization = new Organization(db, thisContact.getOrgId());
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
      //Check access permission to contact record
      if (!isRecordAccessPermitted(context, db, thisContact.getId())) {
        return ("PermissionError");
      }
      //Load the file
      FileItem thisItem = new FileItem(
          db, Integer.parseInt(itemId), thisContact.getId(), Constants.CONTACTS);
      thisItem.updateFolderId(db, Integer.parseInt(newFolderId));
      return "PopupCloseOK";
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
  }

}
