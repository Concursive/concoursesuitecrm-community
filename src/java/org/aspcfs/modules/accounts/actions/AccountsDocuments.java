/*
 *  Copyright 2002 Dark Horse Ventures
 *  Uses iteam objects from matt@zeroio.com http://www.mavininteractive.com
 */
package com.darkhorseventures.cfsmodule;

import javax.servlet.*;
import javax.servlet.http.*;
import org.theseus.actions.*;
import java.sql.*;
import java.util.*;
import com.darkhorseventures.cfsbase.*;
import com.darkhorseventures.webutils.*;
import com.zeroio.iteam.base.*;
import com.zeroio.webutils.*;
import com.isavvix.tools.*;
import java.io.*;

/**
 *  Description of the Class
 *
 *@author     chris price
 *@created    August 9, 2002
 *@version    $Id$
 */
public final class AccountsDocuments extends CFSModule {

  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandView(ActionContext context) {

    if (!(hasPermission(context, "accounts-accounts-documents-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;

    try {
      db = getConnection(context);
      int orgId = addOrganization(context, db);
      FileItemList documents = new FileItemList();
      documents.setLinkModuleId(Constants.ACCOUNTS);
      documents.setLinkItemId(orgId);

      PagedListInfo docListInfo = this.getPagedListInfo(context, "DocListInfo");
      docListInfo.setLink("/AccountsDocuments.do?command=View&orgId=" + orgId);

      //TODO: Not implemented in the JSP, so not implemented here
      //PagedListInfo documentListInfo = this.getPagedListInfo(context, "AccountDocumentInfo");
      //documentListInfo.setLink("AccountsDocuments.do?command=View&orgId=" + orgId);
      //documents.setPagedListInfo(documentListInfo);

      documents.setPagedListInfo(docListInfo);
      documents.buildList(db);
      context.getRequest().setAttribute("FileItemList", documents);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "View Accounts", "View Documents");
    if (errorMessage == null) {
      return ("ViewOK");
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
  public String executeCommandAdd(ActionContext context) {

    if (!(hasPermission(context, "accounts-accounts-documents-add"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;

    try {
      db = getConnection(context);
      int orgId = addOrganization(context, db);
      String folderId = context.getRequest().getParameter("folderId");
      if (folderId != null) {
        context.getRequest().setAttribute("folderId", folderId);
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "View Accounts", "Upload Document");
    if (errorMessage == null) {
      return ("AddOK");
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
  public String executeCommandUpload(ActionContext context) {

    if (!(hasPermission(context, "accounts-accounts-documents-add"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;

    boolean recordInserted = false;
    try {
      String filePath = this.getPath(context, "accounts");

      //Process the form data
      HttpMultiPartParser multiPart = new HttpMultiPartParser();
      multiPart.setUsePathParam(true);
      multiPart.setUseUniqueName(true);
      multiPart.setUseDateForFolder(true);
      multiPart.setExtensionId(getUserId(context));

      HashMap parts = multiPart.parseData(
          context.getRequest().getInputStream(), "---------------------------", filePath);

      db = getConnection(context);

      String id = (String) parts.get("id");
      String subject = (String) parts.get("subject");
      String folderId = (String) parts.get("folderId");
      int orgId = addOrganization(context, db, id);

      if ((Object) parts.get("id" + (String) parts.get("id")) instanceof FileInfo) {

        //Update the database with the resulting file
        FileInfo newFileInfo = (FileInfo) parts.get("id" + id);

        FileItem thisItem = new FileItem();
        thisItem.setLinkModuleId(Constants.ACCOUNTS);
        thisItem.setLinkItemId(orgId);
        thisItem.setEnteredBy(getUserId(context));
        thisItem.setModifiedBy(getUserId(context));
        thisItem.setFolderId(Integer.parseInt(folderId));
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
        context.getRequest().setAttribute("subject", subject);
        context.getRequest().setAttribute("folderId", folderId);
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (recordInserted) {
        return ("UploadOK");
      } else {
        return (executeCommandAdd(context));
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
  public String executeCommandAddVersion(ActionContext context) {

    if (!(hasPermission(context, "accounts-accounts-documents-add"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;

    String itemId = (String) context.getRequest().getParameter("fid");
    if (itemId == null) {
      itemId = (String) context.getRequest().getAttribute("fid");
    }
    
    String folderId = context.getRequest().getParameter("folderId");
    if (folderId != null) {
      context.getRequest().setAttribute("folderId", folderId);
    }

    Connection db = null;
    try {
      db = getConnection(context);
      int orgId = addOrganization(context, db);

      FileItem thisFile = new FileItem(db, Integer.parseInt(itemId), orgId, Constants.ACCOUNTS);
      context.getRequest().setAttribute("FileItem", thisFile);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "View Accounts", "Upload New Document Version");
    if (errorMessage == null) {
      return ("AddVersionOK");
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
  public String executeCommandUploadVersion(ActionContext context) {

    if (!(hasPermission(context, "accounts-accounts-documents-add"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;

    boolean recordInserted = false;
    try {
      String filePath = this.getPath(context, "accounts");

      //Process the form data
      HttpMultiPartParser multiPart = new HttpMultiPartParser();
      multiPart.setUsePathParam(true);
      multiPart.setUseUniqueName(true);
      multiPart.setUseDateForFolder(true);
      multiPart.setExtensionId(getUserId(context));

      HashMap parts = multiPart.parseData(
          context.getRequest().getInputStream(), "---------------------------", filePath);

      db = getConnection(context);

      String id = (String) parts.get("id");
      String itemId = (String) parts.get("fid");
      String subject = (String) parts.get("subject");
      String versionId = (String) parts.get("versionId");
      int orgId = addOrganization(context, db, id);

      if ((Object) parts.get("id" + (String) parts.get("id")) instanceof FileInfo) {
        //Update the database with the resulting file
        FileInfo newFileInfo = (FileInfo) parts.get("id" + id);

        FileItem thisItem = new FileItem();
        thisItem.setLinkModuleId(Constants.ACCOUNTS);
        thisItem.setLinkItemId(orgId);
        thisItem.setId(Integer.parseInt(itemId));
        thisItem.setEnteredBy(getUserId(context));
        thisItem.setModifiedBy(getUserId(context));
        thisItem.setSubject(subject);
        thisItem.setClientFilename(newFileInfo.getClientFileName());
        thisItem.setFilename(newFileInfo.getRealFilename());
        thisItem.setVersion(Double.parseDouble(versionId));
        thisItem.setSize(newFileInfo.getSize());

        recordInserted = thisItem.insertVersion(db);
        if (!recordInserted) {
          processErrors(context, thisItem.getErrors());
        }
      } else {
        recordInserted = false;
        HashMap errors = new HashMap();
        errors.put("actionError", "The file could not be sent by your computer, make sure the file exists");
        processErrors(context, errors);
        context.getRequest().setAttribute("subject", subject);
      }
      context.getRequest().setAttribute("fid", itemId);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (recordInserted) {
        return ("UploadOK");
      } else {
        return (executeCommandAddVersion(context));
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
  public String executeCommandDetails(ActionContext context) {

    if (!(hasPermission(context, "accounts-accounts-documents-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;

    String itemId = (String) context.getRequest().getParameter("fid");

    try {
      db = getConnection(context);
      int orgId = addOrganization(context, db);

      FileItem thisItem = new FileItem(db, Integer.parseInt(itemId), orgId, Constants.ACCOUNTS);
      thisItem.buildVersionList(db);
      context.getRequest().setAttribute("FileItem", thisItem);

    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "View Accounts", "Document Details");
    if (errorMessage == null) {
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
  public String executeCommandDownload(ActionContext context) {

    if (!(hasPermission(context, "accounts-accounts-documents-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;

    String itemId = (String) context.getRequest().getParameter("fid");
    String version = (String) context.getRequest().getParameter("ver");
    FileItem thisItem = null;

    Connection db = null;
    int orgId = -1;
    try {
      db = getConnection(context);
      orgId = addOrganization(context, db);
      thisItem = new FileItem(db, Integer.parseInt(itemId), orgId, Constants.ACCOUNTS);
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
      FileItem itemToDownload = null;
      if (version == null) {
        itemToDownload = thisItem;
      } else {
        itemToDownload = thisItem.getVersion(Double.parseDouble(version));
      }

      itemToDownload.setEnteredBy(this.getUserId(context));
      String filePath = this.getPath(context, "accounts", orgId) + getDatePath(itemToDownload.getModified()) + itemToDownload.getFilename();

      FileDownload fileDownload = new FileDownload();
      fileDownload.setFullPath(filePath);
      fileDownload.setDisplayName(itemToDownload.getClientFilename());
      if (fileDownload.fileExists()) {
        fileDownload.sendFile(context);
        //Get a db connection now that the download is complete
        db = getConnection(context);
        itemToDownload.updateCounter(db);
      } else {
        System.err.println("LeadsDocuments-> Trying to send a file that does not exist");
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
      addModuleBean(context, "View Accounts", "");
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

    if (!(hasPermission(context, "accounts-accounts-documents-edit"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;

    String itemId = (String) context.getRequest().getParameter("fid");

    Connection db = null;
    try {
      db = getConnection(context);
      int orgId = addOrganization(context, db);

      FileItem thisItem = new FileItem(db, Integer.parseInt(itemId), orgId, Constants.ACCOUNTS);
      thisItem.buildVersionList(db);
      context.getRequest().setAttribute("FileItem", thisItem);

    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "View Accounts", "Modify Document Information");
    if (errorMessage == null) {
      return ("ModifyOK");
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
  public String executeCommandUpdate(ActionContext context) {

    if (!(hasPermission(context, "accounts-accounts-documents-edit"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    boolean recordInserted = false;

    String itemId = (String) context.getRequest().getParameter("fid");
    String subject = (String) context.getRequest().getParameter("subject");
    String filename = (String) context.getRequest().getParameter("clientFilename");

    Connection db = null;
    int orgId = -1;
    try {
      db = getConnection(context);
      orgId = addOrganization(context, db);

      FileItem thisItem = new FileItem(db, Integer.parseInt(itemId), orgId, Constants.ACCOUNTS);
      thisItem.setClientFilename(filename);
      thisItem.setSubject(subject);
      recordInserted = thisItem.update(db);

    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "View Accounts", "");
    if (errorMessage == null) {
      if (recordInserted) {
        return ("UpdateOK");
      } else {
        context.getRequest().setAttribute("fid", itemId);
        return (executeCommandModify(context));
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
  public String executeCommandDelete(ActionContext context) {

    if (!(hasPermission(context, "accounts-accounts-documents-delete"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    boolean recordDeleted = false;

    String itemId = (String) context.getRequest().getParameter("fid");

    Connection db = null;
    try {
      db = getConnection(context);
      int orgId = addOrganization(context, db);

      FileItem thisItem = new FileItem(db, Integer.parseInt(itemId), orgId, Constants.ACCOUNTS);
      if (thisItem.getEnteredBy() == this.getUserId(context)) {
        recordDeleted = thisItem.delete(db, this.getPath(context, "accounts", thisItem.getLinkItemId()));
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "View Accounts", "Delete Document");
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
   *  Adds a feature to the Organization attribute of the AccountsDocuments
   *  object
   *
   *@param  context           The feature to be added to the Organization
   *      attribute
   *@param  db                The feature to be added to the Organization
   *      attribute
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  private int addOrganization(ActionContext context, Connection db) throws SQLException {
    String organizationId = (String) context.getRequest().getParameter("orgId");
    if (organizationId == null) {
      organizationId = (String) context.getRequest().getAttribute("orgId");
    }
    return addOrganization(context, db, organizationId);
  }


  /**
   *  Adds a feature to the Organization attribute of the AccountsDocuments
   *  object
   *
   *@param  context           The feature to be added to the Organization
   *      attribute
   *@param  db                The feature to be added to the Organization
   *      attribute
   *@param  organizationId    The feature to be added to the Organization
   *      attribute
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  private int addOrganization(ActionContext context, Connection db, String organizationId) throws SQLException {
    context.getRequest().setAttribute("orgId", organizationId);
    Organization thisOrganization = new Organization(db, Integer.parseInt(organizationId));
    context.getRequest().setAttribute("OrgDetails", thisOrganization);
    return thisOrganization.getOrgId();
  }

}

