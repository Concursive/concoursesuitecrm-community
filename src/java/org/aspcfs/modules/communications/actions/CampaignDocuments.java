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
package org.aspcfs.modules.communications.actions;

import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.framework.actions.*;
import java.sql.*;
import java.util.*;
import org.aspcfs.utils.web.*;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.communications.base.*;
import com.zeroio.iteam.base.*;
import com.zeroio.webutils.*;
import com.isavvix.tools.*;
import java.io.*;

/**
 * Campaign/Document actions
 *
 *@author     mrajkowski
 *@created    January 15, 2003
 *@version    $Id: CampaignDocuments.java,v 1.9 2004/08/04 20:01:56 mrajkowski
 *      Exp $
 */
public final class CampaignDocuments extends CFSModule {

  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandView(ActionContext context) {
    if (!hasPermission(context, "campaign-dashboard-view")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    Connection db = null;
    try {
      db = getConnection(context);
      Campaign thisCampaign = addCampaign(context, db);
      if (!hasAuthority(context, thisCampaign.getEnteredBy())) {
        return "PermissionError";
      }
      FileItemList documents = new FileItemList();
      documents.setLinkModuleId(Constants.COMMUNICATIONS_DOCUMENTS);
      documents.setLinkItemId(thisCampaign.getId());
      PagedListInfo CampaignDocListInfo = this.getPagedListInfo(context, "CampaignDocListInfo");
      CampaignDocListInfo.setLink("CampaignDocuments.do?command=View&id=" + thisCampaign.getId());
      //TODO: Not implemented in the JSP, so not implemented here
      //PagedListInfo documentListInfo = this.getPagedListInfo(context, "AccountDocumentInfo");
      //documentListInfo.setLink("CampaignDocuments.do?command=View&id=" + id);
      //documents.setPagedListInfo(documentListInfo);
      documents.setPagedListInfo(CampaignDocListInfo);
      documents.buildList(db);
      context.getRequest().setAttribute("FileItemList", documents);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Dashboard", "View Documents");
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
    if (!hasPermission(context, "campaign-campaigns-edit")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    Connection db = null;
    try {
      db = getConnection(context);
      Campaign thisCampaign = addCampaign(context, db);
      if (!hasAuthority(context, thisCampaign.getEnteredBy())) {
        return "PermissionError";
      }
      String folderId = context.getRequest().getParameter("folderId");
      if (folderId != null) {
        context.getRequest().setAttribute("folderId", folderId);
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Dashboard", "Upload Document");
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
    if (!hasPermission(context, "campaign-campaigns-edit")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    Connection db = null;
    boolean recordInserted = false;
    try {
      String filePath = this.getPath(context, "campaign");
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
      db = getConnection(context);
      Campaign thisCampaign = addCampaign(context, db, id);
      if (!hasAuthority(context, thisCampaign.getEnteredBy())) {
        return "PermissionError";
      }
      if ((Object) parts.get("id" + (String) parts.get("id")) instanceof FileInfo) {
        //Update the database with the resulting file
        FileInfo newFileInfo = (FileInfo) parts.get("id" + thisCampaign.getId());
        FileItem thisItem = new FileItem();
        thisItem.setLinkModuleId(Constants.COMMUNICATIONS_DOCUMENTS);
        thisItem.setLinkItemId(thisCampaign.getId());
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
    if (!hasPermission(context, "campaign-campaigns-edit")) {
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
      Campaign thisCampaign = addCampaign(context, db);
      if (!hasAuthority(context, thisCampaign.getEnteredBy())) {
        return "PermissionError";
      }
      FileItem thisFile = new FileItem(db, Integer.parseInt(itemId), thisCampaign.getId(), Constants.COMMUNICATIONS_DOCUMENTS);
      context.getRequest().setAttribute("FileItem", thisFile);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Dashboard", "Upload New Document Version");
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
    if (!hasPermission(context, "campaign-campaigns-edit")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    Connection db = null;
    boolean recordInserted = false;
    try {
      String filePath = this.getPath(context, "campaign");
      //Process the form data
      HttpMultiPartParser multiPart = new HttpMultiPartParser();
      multiPart.setUsePathParam(false);
      multiPart.setUseUniqueName(true);
      multiPart.setUseDateForFolder(true);
      multiPart.setExtensionId(getUserId(context));
      HashMap parts = multiPart.parseData(context.getRequest(), filePath);
      db = getConnection(context);
      String id = (String) parts.get("id");
      String itemId = (String) parts.get("fid");
      String subject = (String) parts.get("subject");
      String versionId = (String) parts.get("versionId");
      Campaign thisCampaign = addCampaign(context, db, id);
      if (!hasAuthority(context, thisCampaign.getEnteredBy())) {
        return "PermissionError";
      }
      if ((Object) parts.get("id" + (String) parts.get("id")) instanceof FileInfo) {
        //Update the database with the resulting file
        FileInfo newFileInfo = (FileInfo) parts.get("id" + id);
        FileItem thisItem = new FileItem();
        thisItem.setLinkModuleId(Constants.COMMUNICATIONS_DOCUMENTS);
        thisItem.setLinkItemId(thisCampaign.getId());
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

    if (!(hasPermission(context, "campaign-dashboard-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;

    String itemId = (String) context.getRequest().getParameter("fid");

    try {
      db = getConnection(context);
      Campaign thisCampaign = addCampaign(context, db);
      if (!hasAuthority(context, thisCampaign.getEnteredBy())) {
        return "PermissionError";
      }

      FileItem thisItem = new FileItem(db, Integer.parseInt(itemId), thisCampaign.getId(), Constants.COMMUNICATIONS_DOCUMENTS);
      thisItem.buildVersionList(db);
      context.getRequest().setAttribute("FileItem", thisItem);

    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "Dashboard", "Document Details");
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

    if (!(hasPermission(context, "campaign-dashboard-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;

    String itemId = (String) context.getRequest().getParameter("fid");
    String version = (String) context.getRequest().getParameter("ver");
    FileItem thisItem = null;

    Connection db = null;
    int id = -1;
    try {
      db = getConnection(context);
      Campaign thisCampaign = addCampaign(context, db);
      if (!hasAuthority(context, thisCampaign.getEnteredBy())) {
        return "PermissionError";
      }
      thisItem = new FileItem(db, Integer.parseInt(itemId), thisCampaign.getId(), Constants.COMMUNICATIONS_DOCUMENTS);
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
        String filePath = this.getPath(context, "campaign") + getDatePath(itemToDownload.getModified()) + itemToDownload.getFilename();
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
          return (executeCommandView(context));
        }
      } else {
        FileItemVersion itemToDownload = thisItem.getVersion(Double.parseDouble(version));
        itemToDownload.setEnteredBy(this.getUserId(context));
        String filePath = this.getPath(context, "campaign") + getDatePath(itemToDownload.getModified()) + itemToDownload.getFilename();
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
          System.err.println("LeadsDocuments-> Trying to send a file that does not exist");
          context.getRequest().setAttribute("actionError", "The requested download no longer exists on the system");
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
  public String executeCommandModify(ActionContext context) {

    if (!(hasPermission(context, "campaign-campaigns-edit"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;

    String itemId = (String) context.getRequest().getParameter("fid");

    Connection db = null;
    try {
      db = getConnection(context);
      Campaign thisCampaign = addCampaign(context, db);
      if (!hasAuthority(context, thisCampaign.getEnteredBy())) {
        return "PermissionError";
      }

      FileItem thisItem = new FileItem(db, Integer.parseInt(itemId), thisCampaign.getId(), Constants.COMMUNICATIONS_DOCUMENTS);
      thisItem.buildVersionList(db);
      context.getRequest().setAttribute("FileItem", thisItem);

    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "Dashboard", "Modify Document Information");
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

    if (!(hasPermission(context, "campaign-campaigns-edit"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    boolean recordInserted = false;

    String itemId = (String) context.getRequest().getParameter("fid");
    String subject = (String) context.getRequest().getParameter("subject");
    String filename = (String) context.getRequest().getParameter("clientFilename");

    Connection db = null;
    int id = -1;
    try {
      db = getConnection(context);
      Campaign thisCampaign = addCampaign(context, db);
      if (!hasAuthority(context, thisCampaign.getEnteredBy())) {
        return "PermissionError";
      }

      FileItem thisItem = new FileItem(db, Integer.parseInt(itemId), thisCampaign.getId(), Constants.COMMUNICATIONS_DOCUMENTS);
      thisItem.setClientFilename(filename);
      thisItem.setSubject(subject);
      recordInserted = thisItem.update(db);

    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "Dashboard", "");
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

    if (!(hasPermission(context, "campaign-campaigns-edit"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    boolean recordDeleted = false;

    String itemId = (String) context.getRequest().getParameter("fid");

    Connection db = null;
    try {
      db = getConnection(context);
      Campaign thisCampaign = addCampaign(context, db);
      if (!hasAuthority(context, thisCampaign.getEnteredBy())) {
        return "PermissionError";
      }

      FileItem thisItem = new FileItem(db, Integer.parseInt(itemId), thisCampaign.getId(), Constants.COMMUNICATIONS_DOCUMENTS);
      if (thisItem.getEnteredBy() == this.getUserId(context)) {
        recordDeleted = thisItem.delete(db, this.getPath(context, "campaign"));
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "Dashboard", "Delete Document");
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
   *  Adds a feature to the Organization attribute of the CampaignDocuments
   *  object
   *
   *@param  context           The feature to be added to the Organization
   *      attribute
   *@param  db                The feature to be added to the Organization
   *      attribute
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  private Campaign addCampaign(ActionContext context, Connection db) throws SQLException {
    String campaignId = (String) context.getRequest().getParameter("id");
    if (campaignId == null) {
      campaignId = (String) context.getRequest().getAttribute("id");
    }
    return addCampaign(context, db, campaignId);
  }


  /**
   *  Adds a feature to the Organization attribute of the CampaignDocuments
   *  object
   *
   *@param  context           The feature to be added to the Organization
   *      attribute
   *@param  db                The feature to be added to the Organization
   *      attribute
   *@param  campaignId        The feature to be added to the Campaign attribute
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  private Campaign addCampaign(ActionContext context, Connection db, String campaignId) throws SQLException {
    context.getRequest().setAttribute("id", campaignId);
    Campaign thisCampaign = new Campaign(db, Integer.parseInt(campaignId));
    context.getRequest().setAttribute("Campaign", thisCampaign);
    return thisCampaign;
  }
}

