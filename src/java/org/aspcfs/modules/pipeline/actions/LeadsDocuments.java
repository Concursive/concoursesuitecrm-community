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
package org.aspcfs.modules.pipeline.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import com.isavvix.tools.FileInfo;
import com.isavvix.tools.HttpMultiPartParser;
import com.zeroio.iteam.actions.ProjectManagementFileFolders;
import com.zeroio.iteam.base.*;
import com.zeroio.webutils.FileDownload;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.pipeline.base.OpportunityHeader;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Description of the Class
 *
 * @author Mathur
 * @version $Id: LeadsDocuments.java,v 1.17 2004/08/05 20:37:41 mrajkowski Exp
 *          $
 * @created January 15, 2003
 */
public final class LeadsDocuments extends CFSModule {

  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandView(ActionContext context) {
    if (!hasPermission(context, "pipeline-opportunities-documents-view")) {
      return ("PermissionError");
    }
    String folderId = context.getRequest().getParameter("folderId");
    if (folderId == null) {
      folderId = (String) context.getRequest().getAttribute("folderId");
    }
    Exception errorMessage = null;
    Connection db = null;
    try {
      db = getConnection(context);
      int opportunityId = addOpportunity(context, db);

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
      folders.setLinkModuleId(Constants.DOCUMENTS_OPPORTUNITIES);
      folders.setLinkItemId(opportunityId);
      folders.setBuildItemCount(true);
      folders.buildList(db);

      FileItemList documents = new FileItemList();
      if (folderId == null || "-1".equals(folderId) || "0".equals(folderId) || "".equals(
          folderId)) {
        documents.setTopLevelOnly(true);
      } else {
        documents.setFolderId(Integer.parseInt(folderId));
      }
      documents.setLinkModuleId(Constants.DOCUMENTS_OPPORTUNITIES);
      documents.setLinkItemId(opportunityId);
      documents.buildList(db);
      context.getRequest().setAttribute("fileItemList", documents);
      context.getRequest().setAttribute("fileFolderList", folders);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "View Opportunities", "View Documents");
    if (errorMessage == null) {
      return ("ViewOK");
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
    if (!hasPermission(context, "pipeline-opportunities-documents-add")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    Connection db = null;
    try {
      db = getConnection(context);
      int opportunityId = addOpportunity(context, db);
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
    addModuleBean(context, "View Opportunities", "Upload Document");
    if (errorMessage == null) {
      return ("AddOK");
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
    if (!hasPermission(context, "pipeline-opportunities-documents-add")) {
      return ("PermissionError");
    }
    Connection db = null;
    boolean recordInserted = false;
    boolean isValid = false;
    try {
      String filePath = this.getPath(context, "opportunities");
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
      if (folderId != null) {
        context.getRequest().setAttribute("folderId", folderId);
      }
      if (subject != null) {
        context.getRequest().setAttribute("subject", subject);
      }
      if ((Object) parts.get("id" + (String) parts.get("id")) instanceof FileInfo) {

        //Update the database with the resulting file
        FileInfo newFileInfo = (FileInfo) parts.get("id" + id);

        db = getConnection(context);
        int opportunityId = addOpportunity(context, db, id);

        FileItem thisItem = new FileItem();
        thisItem.setLinkModuleId(Constants.DOCUMENTS_OPPORTUNITIES);
        thisItem.setLinkItemId(opportunityId);
        thisItem.setEnteredBy(getUserId(context));
        thisItem.setModifiedBy(getUserId(context));
        thisItem.setFolderId(Integer.parseInt(folderId));
        thisItem.setSubject(subject);
        thisItem.setClientFilename(newFileInfo.getClientFileName());
        thisItem.setFilename(newFileInfo.getRealFilename());
        thisItem.setVersion(1.0);
        thisItem.setSize(newFileInfo.getSize());
        isValid = this.validateObject(context, db, thisItem);
        if (isValid) {
          recordInserted = thisItem.insert(db);
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
        context.getRequest().setAttribute("headerId", id);
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
    if (!hasPermission(context, "pipeline-opportunities-documents-add")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    String itemId = (String) context.getRequest().getParameter("fid");
    if (itemId == null) {
      itemId = (String) context.getRequest().getAttribute("fid");
    }
    String folderId = context.getRequest().getParameter("folderId");
    if (folderId == null) {
      folderId = (String) context.getRequest().getAttribute("folderId");
    }
    if (folderId != null) {
      context.getRequest().setAttribute("folderId", folderId);
    }
    Connection db = null;
    try {
      db = getConnection(context);
      int opportunityId = addOpportunity(context, db);
      FileItem thisFile = new FileItem(
          db, Integer.parseInt(itemId), opportunityId, Constants.DOCUMENTS_OPPORTUNITIES);
      context.getRequest().setAttribute("FileItem", thisFile);

      //Build array of folder trails
      ProjectManagementFileFolders.buildHierarchy(db, context);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(
        context, "View Opportunities", "Upload New Document Version");
    if (errorMessage == null) {
      return ("AddVersionOK");
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
    if (!hasPermission(context, "pipeline-opportunities-documents-add")) {
      return ("PermissionError");
    }
    Connection db = null;
    boolean recordInserted = false;
    boolean isValid = false;
    try {
      String filePath = this.getPath(context, "opportunities");
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
      if (folderId != null) {
        context.getRequest().setAttribute("folderId", folderId);
      }
      if (subject != null) {
        context.getRequest().setAttribute("subject", subject);
      }
      db = getConnection(context);
      int opportunityId = addOpportunity(context, db, id);
      if ((Object) parts.get("id" + (String) parts.get("id")) instanceof FileInfo) {
        //Update the database with the resulting file
        FileInfo newFileInfo = (FileInfo) parts.get("id" + id);
        FileItem thisItem = new FileItem();
        thisItem.setLinkModuleId(Constants.DOCUMENTS_OPPORTUNITIES);
        thisItem.setLinkItemId(opportunityId);
        thisItem.setId(Integer.parseInt(itemId));
        thisItem.setEnteredBy(getUserId(context));
        thisItem.setModifiedBy(getUserId(context));
        thisItem.setSubject(subject);
        thisItem.setClientFilename(newFileInfo.getClientFileName());
        thisItem.setFilename(newFileInfo.getRealFilename());
        thisItem.setVersion(Double.parseDouble(versionId));
        thisItem.setSize(newFileInfo.getSize());
        isValid = this.validateObject(context, db, thisItem);
        if (isValid) {
          recordInserted = thisItem.insertVersion(db);
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
      e.printStackTrace();
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
    if (!hasPermission(context, "pipeline-opportunities-documents-view")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    Connection db = null;
    String folderId = context.getRequest().getParameter("folderId");
    if (folderId != null) {
      context.getRequest().setAttribute("folderId", folderId);
    }
    String itemId = (String) context.getRequest().getParameter("fid");
    try {
      db = getConnection(context);
      int opportunityId = addOpportunity(context, db);
      FileItem thisItem = new FileItem(
          db, Integer.parseInt(itemId), opportunityId, Constants.DOCUMENTS_OPPORTUNITIES);
      thisItem.buildVersionList(db);
      if (folderId != null && !"-1".equals(folderId) && !"0".equals(folderId) && !"".equals(
          folderId) && !" ".equals(folderId)) {
        //Build array of folder trails
        ProjectManagementFileFolders.buildHierarchy(db, context);
      }
      context.getRequest().setAttribute("FileItem", thisItem);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "View Opportunities", "Document Details");
    if (errorMessage == null) {
      return ("DetailsOK");
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
    if (!hasPermission(context, "pipeline-opportunities-documents-view")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    String itemId = (String) context.getRequest().getParameter("fid");
    String version = (String) context.getRequest().getParameter("ver");
    String view = (String) context.getRequest().getParameter("view");
    FileItem thisItem = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    Connection db = null;
    int opportunityId = -1;
    try {
      db = getConnection(context);
      opportunityId = addOpportunity(context, db);
      thisItem = new FileItem(
          db, Integer.parseInt(itemId), opportunityId, Constants.DOCUMENTS_OPPORTUNITIES);
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
        String filePath = this.getPath(context, "opportunities") + getDatePath(
            itemToDownload.getModified()) + itemToDownload.getFilename();
        FileDownload fileDownload = new FileDownload();
        fileDownload.setFullPath(filePath);
        fileDownload.setDisplayName(itemToDownload.getClientFilename());
        if (fileDownload.fileExists()) {
          if (view != null && "true".equals(view)) {
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
              "LeadsDocuments-> Trying to send a file that does not exist");
          context.getRequest().setAttribute(
              "actionError", systemStatus.getLabel(
                  "object.validation.actionError.downloadDoesNotExist"));
          return (executeCommandView(context));
        }
      } else {
        FileItemVersion itemToDownload = thisItem.getVersion(
            Double.parseDouble(version));
        itemToDownload.setEnteredBy(this.getUserId(context));
        String filePath = this.getPath(context, "opportunities") + getDatePath(
            itemToDownload.getModified()) + itemToDownload.getFilename();
        FileDownload fileDownload = new FileDownload();
        fileDownload.setFullPath(filePath);
        fileDownload.setDisplayName(itemToDownload.getClientFilename());
        if (fileDownload.fileExists()) {
          if (view != null && "true".equals(view)) {
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
              "LeadsDocuments-> Trying to send a file that does not exist");
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
    addModuleBean(context, "View Opportunities", "");
    if (errorMessage == null) {
      return ("-none-");
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
  public String executeCommandModify(ActionContext context) {
    if (!hasPermission(context, "pipeline-opportunities-documents-edit")) {
      return ("PermissionError");
    }
    String itemId = (String) context.getRequest().getParameter("fid");
    String folderId = context.getRequest().getParameter("folderId");
    if (folderId != null) {
      context.getRequest().setAttribute("folderId", folderId);
    }
    Connection db = null;
    try {
      db = getConnection(context);
      int opportunityId = addOpportunity(context, db);
      FileItem thisItem = new FileItem(
          db, Integer.parseInt(itemId), opportunityId, Constants.DOCUMENTS_OPPORTUNITIES);
      thisItem.buildVersionList(db);
      context.getRequest().setAttribute("FileItem", thisItem);

      //Build array of folder trails
      ProjectManagementFileFolders.buildHierarchy(db, context);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(
        context, "View Opportunities", "Modify Document Information");
    return ("ModifyOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandUpdate(ActionContext context) {
    if (!hasPermission(context, "pipeline-opportunities-documents-edit")) {
      return ("PermissionError");
    }
    boolean recordInserted = false;
    boolean isValid = false;
    String itemId = (String) context.getRequest().getParameter("fid");
    String subject = (String) context.getRequest().getParameter("subject");
    String filename = (String) context.getRequest().getParameter(
        "clientFilename");
    Connection db = null;
    int opportunityId = -1;
    try {
      db = getConnection(context);
      opportunityId = addOpportunity(context, db);
      FileItem thisItem = new FileItem(
          db, Integer.parseInt(itemId), opportunityId, Constants.DOCUMENTS_OPPORTUNITIES);
      thisItem.setClientFilename(filename);
      thisItem.setSubject(subject);
      isValid = this.validateObject(context, db, thisItem);
      if (isValid) {
        recordInserted = thisItem.update(db);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "View Opportunities", "");
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
    if (!hasPermission(context, "pipeline-opportunities-documents-delete")) {
      return ("PermissionError");
    }
    boolean recordDeleted = false;
    String itemId = (String) context.getRequest().getParameter("fid");
    Connection db = null;
    try {
      db = getConnection(context);
      int opportunityId = addOpportunity(context, db);
      FileItem thisItem = new FileItem(
          db, Integer.parseInt(itemId), opportunityId, Constants.DOCUMENTS_OPPORTUNITIES);
      recordDeleted = thisItem.delete(
          db, this.getPath(context, "opportunities"));
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "View Opportunities", "Delete Document");
    if (recordDeleted) {
      return ("DeleteOK");
    } else {
      return ("DeleteERROR");
    }
  }


  /**
   * Adds a feature to the Opportunity attribute of the LeadsDocuments object
   *
   * @param context The feature to be added to the Opportunity
   *                attribute
   * @param db      The feature to be added to the Opportunity
   *                attribute
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  private int addOpportunity(ActionContext context, Connection db) throws SQLException {
    String opportunityId = (String) context.getRequest().getParameter(
        "headerId");
    if (opportunityId == null) {
      opportunityId = (String) context.getRequest().getAttribute("headerId");
    }
    return addOpportunity(context, db, opportunityId);
  }


  /**
   * Adds a feature to the Opportunity attribute of the LeadsDocuments object
   *
   * @param context       The feature to be added to the Opportunity
   *                      attribute
   * @param db            The feature to be added to the Opportunity
   *                      attribute
   * @param opportunityId The feature to be added to the Opportunity
   *                      attribute
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  private int addOpportunity(ActionContext context, Connection db, String opportunityId) throws SQLException {
    context.getRequest().setAttribute("headerId", opportunityId);
    OpportunityHeader thisOpportunity = new OpportunityHeader(
        db, opportunityId);
    context.getRequest().setAttribute("opportunityHeader", thisOpportunity);
    return thisOpportunity.getId();
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandMove(ActionContext context) {
    if (!hasPermission(context, "pipeline-opportunities-documents-edit")) {
      return ("PermissionError");
    }
    Connection db = null;
    //Parameters
    String itemId = (String) context.getRequest().getParameter("fid");
    try {
      db = getConnection(context);
      int opportunityId = addOpportunity(context, db);
      //Load the file
      FileItem thisItem = new FileItem(
          db, Integer.parseInt(itemId), opportunityId, Constants.DOCUMENTS_OPPORTUNITIES);
      thisItem.buildVersionList(db);
      context.getRequest().setAttribute("FileItem", thisItem);
      //Load the folders
      FileFolderHierarchy hierarchy = new FileFolderHierarchy();
      hierarchy.setLinkModuleId(Constants.DOCUMENTS_OPPORTUNITIES);
      hierarchy.setLinkItemId(opportunityId);
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
    if (!hasPermission(context, "pipeline-opportunities-documents-edit")) {
      return ("PermissionError");
    }
    Connection db = null;
    //Parameters
    String newFolderId = (String) context.getRequest().getParameter(
        "folderId");
    String itemId = (String) context.getRequest().getParameter("fid");
    try {
      db = getConnection(context);
      int opportunityId = addOpportunity(context, db);
      //Load the file
      FileItem thisItem = new FileItem(
          db, Integer.parseInt(itemId), opportunityId, Constants.DOCUMENTS_OPPORTUNITIES);
      thisItem.buildVersionList(db);
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

