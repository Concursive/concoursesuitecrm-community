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

package org.aspcfs.modules.documents.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import com.isavvix.tools.FileInfo;
import com.isavvix.tools.HttpMultiPartParser;
import com.zeroio.iteam.base.FileFolderHierarchy;
import com.zeroio.iteam.base.FileItem;
import com.zeroio.iteam.base.FileItemVersion;
import com.zeroio.iteam.base.Thumbnail;
import com.zeroio.webutils.FileDownload;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.documents.base.DocumentStore;
import org.aspcfs.utils.ImageUtils;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;

/**
 * Description of the Class
 *
 * @author
 * @version $Id$
 * @created
 */
public final class DocumentStoreManagementFiles extends CFSModule {

  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandAdd(ActionContext context) {
    String documentStoreId = (String) context.getRequest().getParameter(
        "documentStoreId");
    if (documentStoreId == null) {
      documentStoreId = (String) context.getRequest().getAttribute(
          "documentStoreId");
    }
    String folderId = (String) context.getRequest().getParameter("folderId");
    if (folderId == null || "".equals(folderId)) {
      folderId = (String) context.getRequest().getAttribute("folderId");
    }
    context.getRequest().setAttribute("folderId", folderId);
    Connection db = null;
    try {
      db = getConnection(context);
      // Load the document store
      DocumentStore thisDocumentStore = new DocumentStore(
          db, Integer.parseInt(documentStoreId));
      thisDocumentStore.buildPermissionList(db);
      if (!hasDocumentStoreAccess(
          context, db, thisDocumentStore, "documentcenter-documents-files-upload")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("documentStore", thisDocumentStore);
      context.getRequest().setAttribute(
          "IncludeSection", ("file_upload").toLowerCase());
      // Check user's account size
      /*
       *  User thisUser = getUser(context);
       *  if (thisUser.getAccountSize() > -1) {
       *  / Refresh the user's account size
       *  thisUser.setCurrentAccountSize(FileItemVersionList.queryOwnerSize(db, thisUser.getId()));
       *  if (!thisUser.isWithinAccountSize()) {
       *  context.getRequest().setAttribute("IncludeSection", ("file_limit").toLowerCase());
       *  }
       *  }
       */
      // Build array of folder trails
      DocumentStoreManagementFileFolders.buildHierarchy(db, context);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("DocumentStoreCenterOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandUpload(ActionContext context) {
    Connection db = null;
    boolean recordInserted = false;
    boolean isValid = false;
    String documentStoreId = "";
    String subject = "";
    String folderId = "";
    String itemId = "";
    String versionId = "";
    try {
      String filePath = this.getPath(context, "documents");
      //Process the form data
      HttpMultiPartParser multiPart = new HttpMultiPartParser();
      multiPart.setUsePathParam(false);
      multiPart.setUseUniqueName(true);
      multiPart.setUseDateForFolder(true);
      multiPart.setExtensionId(getUserId(context));
      HashMap parts = multiPart.parseData(context.getRequest(), filePath);
      documentStoreId = (String) parts.get("documentStoreId");
      subject = (String) parts.get("subject");
      folderId = (String) parts.get("folderId");
      itemId = (String) parts.get("fid");
      versionId = (String) parts.get("versionId");
      if (folderId != null) {
        context.getRequest().setAttribute("folderId", folderId);
      }
      if (subject != null) {
        context.getRequest().setAttribute("subject", subject);
      }
      db = getConnection(context);
      DocumentStore thisDocumentStore = new DocumentStore(
          db, Integer.parseInt(documentStoreId));
      thisDocumentStore.buildPermissionList(db);
      if (!hasDocumentStoreAccess(
          context, db, thisDocumentStore, "documentcenter-documents-files-upload")) {
        //TODO: Should delete the uploads, then exit
        return "PermissionError";
      }
      context.getRequest().setAttribute("DocumentStore", thisDocumentStore);
      context.getRequest().setAttribute(
          "IncludeSection", ("file_library").toLowerCase());
      //Update the database with the resulting file
      if ((Object) parts.get("id" + documentStoreId) instanceof FileInfo) {
        FileInfo newFileInfo = (FileInfo) parts.get("id" + documentStoreId);

        FileItem thisItem = new FileItem();
        thisItem.setLinkModuleId(Constants.DOCUMENTS_DOCUMENTS);
        thisItem.setLinkItemId(thisDocumentStore.getId());
        thisItem.setEnteredBy(getUserId(context));
        thisItem.setModifiedBy(getUserId(context));
        thisItem.setFolderId(Integer.parseInt(folderId));
        thisItem.setSubject(subject);
        thisItem.setClientFilename(newFileInfo.getClientFileName());
        thisItem.setFilename(newFileInfo.getRealFilename());
        thisItem.setSize(newFileInfo.getSize());
        if (itemId == null || "-1".equals(itemId)) {
          thisItem.setVersion(1.0);
          isValid = this.validateObject(context, db, thisItem);
          if (isValid) {
            recordInserted = thisItem.insert(db);
            thisItem.setDirectory(filePath);
            indexAddItem(context, thisItem);
          }
        } else {
          thisItem.setId(Integer.parseInt(itemId));
          thisItem.setVersion(Double.parseDouble(versionId));
          isValid = this.validateObject(context, db, thisItem);
          if (isValid) {
            recordInserted = thisItem.insertVersion(db);
            thisItem.setDirectory(filePath);
            indexAddItem(context, thisItem);
          }
        }
        if (recordInserted && isValid) {
          if (thisItem.isImageFormat()) {
            //Create a thumbnail if this is an image
            File thumbnailFile = new File(
                newFileInfo.getLocalFile().getPath() + "TH");
            ImageUtils.saveThumbnail(
                newFileInfo.getLocalFile(), thumbnailFile, 133d, 133d);
            //Store thumbnail in database
            Thumbnail thumbnail = new Thumbnail();
            thumbnail.setId(thisItem.getId());
            thumbnail.setFilename(newFileInfo.getRealFilename() + "TH");
            thumbnail.setVersion(thisItem.getVersion());
            thumbnail.setSize((int) thumbnailFile.length());
            thumbnail.setEnteredBy(thisItem.getEnteredBy());
            thumbnail.setModifiedBy(thisItem.getModifiedBy());
            isValid = this.validateObject(context, db, thumbnail) && isValid;
            if (isValid) {
              recordInserted = thumbnail.insert(db);
            }
          }
        }
      } else {
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
      context.getRequest().setAttribute("documentStoreId", documentStoreId);
      context.getRequest().setAttribute("folderId", folderId);
      context.getRequest().setAttribute("fid", itemId);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      freeConnection(context, db);
    }
    if (recordInserted && isValid) {
      return ("AddOK");
    } else {
      if ((itemId == null) || "-1".equals(itemId)) {
        return executeCommandAdd(context);
      }
      return executeCommandAddVersion(context);
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandAddVersion(ActionContext context) {
    String documentStoreId = (String) context.getRequest().getParameter(
        "documentStoreId");
    if (documentStoreId == null) {
      documentStoreId = (String) context.getRequest().getAttribute(
          "documentStoreId");
    }
    String itemId = (String) context.getRequest().getParameter("fid");
    if (itemId == null) {
      itemId = (String) context.getRequest().getAttribute("fid");
    }
    Connection db = null;
    try {
      db = getConnection(context);
      // Load the document store
      DocumentStore thisDocumentStore = new DocumentStore(
          db, Integer.parseInt(documentStoreId));
      thisDocumentStore.buildPermissionList(db);
      if (!hasDocumentStoreAccess(
          context, db, thisDocumentStore, "documentcenter-documents-files-upload")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("documentStore", thisDocumentStore);
      context.getRequest().setAttribute(
          "IncludeSection", ("file_upload").toLowerCase());
      // Load the file item
      FileItem thisFile = new FileItem(
          db, Integer.parseInt(itemId), Integer.parseInt(documentStoreId), Constants.DOCUMENTS_DOCUMENTS);
      context.getRequest().setAttribute("FileItem", thisFile);
      // NOTE: This feature is not supported
      /*
       *  // Check user's account size
       *  User thisUser = getUser(context, this.getUserId(context));
       *  if (thisUser.getAccountSize() > -1) {
       *  / Refresh the user's account size
       *  thisUser.setCurrentAccountSize(FileItemVersionList.queryOwnerSize(db, thisUser.getId()));
       *  if (!thisUser.isWithinAccountSize()) {
       *  context.getRequest().setAttribute("IncludeSection", ("file_limit").toLowerCase());
       *  }
       *  }
       */
      // Build array of folder trails
      DocumentStoreManagementFileFolders.buildHierarchy(db, context);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("DocumentStoreCenterOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandUploadVersion(ActionContext context) {
    Connection db = null;
    boolean recordInserted = false;
    boolean isValid = false;
    try {
      String filePath = this.getPath(context, "documents");
      //Process the form data
      HttpMultiPartParser multiPart = new HttpMultiPartParser();
      multiPart.setUsePathParam(false);
      multiPart.setUseUniqueName(true);
      multiPart.setUseDateForFolder(true);
      multiPart.setExtensionId(getUserId(context));
      HashMap parts = multiPart.parseData(context.getRequest(), filePath);
      String documentStoreId = (String) parts.get("documentStoreId");
      String itemId = (String) parts.get("fid");
      String subject = (String) parts.get("subject");
      String versionId = (String) parts.get("versionId");
      if (subject != null) {
        context.getRequest().setAttribute("subject", subject);
      }
      //Update the database with the resulting file
      FileInfo newFileInfo = (FileInfo) parts.get("id" + documentStoreId);
      db = getConnection(context);
      DocumentStore thisDocumentStore = new DocumentStore(
          db, Integer.parseInt(documentStoreId));
      thisDocumentStore.buildPermissionList(db);
      if (!hasDocumentStoreAccess(
          context, db, thisDocumentStore, "documentcenter-documents-files-upload")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("documentStore", thisDocumentStore);
      context.getRequest().setAttribute(
          "IncludeSection", ("file_library").toLowerCase());

      FileItem thisItem = new FileItem();
      thisItem.setLinkModuleId(Constants.DOCUMENTS_DOCUMENTS);
      thisItem.setLinkItemId(thisDocumentStore.getId());
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
      context.getRequest().setAttribute("documentStoreId", documentStoreId);
      context.getRequest().setAttribute("fid", itemId);
      //Build array of folder trails
      DocumentStoreManagementFileFolders.buildHierarchy(db, context);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      freeConnection(context, db);
    }
    if (recordInserted && isValid) {
      return ("AddOK");
    } else {
      return (executeCommandAddVersion(context));
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandDetails(ActionContext context) {
    Exception errorMessage = null;
    String documentStoreId = (String) context.getRequest().getParameter(
        "documentStoreId");
    if (documentStoreId == null) {
      documentStoreId = (String) context.getRequest().getAttribute(
          "documentStoreId");
    }
    String itemId = (String) context.getRequest().getParameter("fid");
    Connection db = null;
    try {
      db = getConnection(context);
      //Load the document store info
      DocumentStore thisDocumentStore = new DocumentStore(
          db, Integer.parseInt(documentStoreId));
      thisDocumentStore.buildPermissionList(db);
      if (!hasDocumentStoreAccess(
          context, db, thisDocumentStore, "documentcenter-documents-view")) {
        return "PermissionError";
      }
      thisDocumentStore.buildFileItemList(db);
      context.getRequest().setAttribute("documentStore", thisDocumentStore);
      context.getRequest().setAttribute(
          "IncludeSection", ("file_details").toLowerCase());
      //Load the details for the selected file item
      FileItem thisItem = new FileItem(
          db, Integer.parseInt(itemId), thisDocumentStore.getId(), Constants.DOCUMENTS_DOCUMENTS);
      thisItem.buildVersionList(db);
      context.getRequest().setAttribute("FileItem", thisItem);
      //Load the current folder state information
      DocumentStoreManagementFileFolders.buildHierarchy(db, context);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      return ("DocumentStoreCenterOK");
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
    Exception errorMessage = null;
    String documentStoreId = (String) context.getRequest().getParameter(
        "documentStoreId");
    String itemId = (String) context.getRequest().getParameter("fid");
    String version = (String) context.getRequest().getParameter("ver");
    String view = (String) context.getRequest().getParameter("view");
    FileItem thisItem = null;
    Connection db = null;
    try {
      db = getConnection(context);
      DocumentStore thisDocumentStore = new DocumentStore(
          db, Integer.parseInt(documentStoreId));
      thisDocumentStore.buildPermissionList(db);
      if (!hasDocumentStoreAccess(
          context, db, thisDocumentStore, "documentcenter-documents-files-download")) {
        return "PermissionError";
      }
      thisItem = new FileItem(
          db, Integer.parseInt(itemId), thisDocumentStore.getId(), Constants.DOCUMENTS_DOCUMENTS);
      if (version != null) {
        thisItem.buildVersionList(db);
      }
      context.getRequest().setAttribute("documentStore", thisDocumentStore);
      context.getRequest().setAttribute(
          "IncludeSection", ("file_library").toLowerCase());
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
        String filePath = this.getPath(context, "documents") + getDatePath(
            itemToDownload.getEntered()) + itemToDownload.getFilename();
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
          System.err.println(
              "PMF-> Trying to send a file that does not exist");
        }
      } else {
        FileItemVersion itemToDownload = thisItem.getVersion(
            Double.parseDouble(version));
        itemToDownload.setEnteredBy(this.getUserId(context));
        String filePath = this.getPath(context, "documents") + getDatePath(
            itemToDownload.getEntered()) + itemToDownload.getFilename();
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
          System.err.println(
              "PMF-> Trying to send a file that does not exist");
        }
      }
    } catch (java.net.SocketException se) {
      //User either canceled the download or lost connection
    } catch (Exception e) {
      errorMessage = e;
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
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandModify(ActionContext context) {
    Exception errorMessage = null;
    String documentStoreId = (String) context.getRequest().getParameter(
        "documentStoreId");
    String itemId = (String) context.getRequest().getParameter("fid");
    Connection db = null;
    try {
      db = getConnection(context);
      //Load the document store
      DocumentStore thisDocumentStore = new DocumentStore(
          db, Integer.parseInt(documentStoreId));
      thisDocumentStore.buildPermissionList(db);
      if (!hasDocumentStoreAccess(
          context, db, thisDocumentStore, "documentcenter-documents-files-rename")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("documentStore", thisDocumentStore);
      context.getRequest().setAttribute(
          "IncludeSection", ("file_modify").toLowerCase());
      //Load the file item to be modified
      FileItem thisItem = new FileItem(
          db, Integer.parseInt(itemId), thisDocumentStore.getId(), Constants.DOCUMENTS_DOCUMENTS);
      thisItem.buildVersionList(db);
      context.getRequest().setAttribute("FileItem", thisItem);
      //Build array of folder trails
      DocumentStoreManagementFileFolders.buildHierarchy(db, context);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      return ("DocumentStoreCenterOK");
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
    Exception errorMessage = null;
    boolean recordInserted = false;

    String documentStoreId = (String) context.getRequest().getParameter(
        "documentStoreId");
    String itemId = (String) context.getRequest().getParameter("fid");
    String subject = (String) context.getRequest().getParameter("subject");
    String filename = (String) context.getRequest().getParameter(
        "clientFilename");

    Connection db = null;
    try {
      db = getConnection(context);
      //Load the document store
      DocumentStore thisDocumentStore = new DocumentStore(
          db, Integer.parseInt(documentStoreId));
      thisDocumentStore.buildPermissionList(db);
      if (!hasDocumentStoreAccess(
          context, db, thisDocumentStore, "documentcenter-documents-files-rename")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("documentStore", thisDocumentStore);
      String filePath = this.getPath(context, "documents");
      //Load the file item
      FileItem thisItem = new FileItem(
          db, Integer.parseInt(itemId), thisDocumentStore.getId(), Constants.DOCUMENTS_DOCUMENTS);
      thisItem.setClientFilename(filename);
      thisItem.setSubject(subject);
      recordInserted = thisItem.update(db);
      thisItem.setDirectory(filePath);
      indexAddItem(context, thisItem);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      if (recordInserted) {
        return ("UpdateOK");
      } else {
        context.getRequest().setAttribute("documentStoreId", documentStoreId);
        context.getRequest().setAttribute("fid", itemId);
        return (executeCommandModify(context));
      }
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
  public String executeCommandDelete(ActionContext context) {
    boolean recordDeleted = false;
    String documentStoreId = (String) context.getRequest().getParameter(
        "documentStoreId");
    String itemId = (String) context.getRequest().getParameter("fid");
    String version = (String) context.getRequest().getParameter("ver");
    Connection db = null;
    boolean isVersion = false;
    try {
      db = getConnection(context);
      //Load the document store
      DocumentStore thisDocumentStore = new DocumentStore(
          db, Integer.parseInt(documentStoreId));
      thisDocumentStore.buildPermissionList(db);
      if (!hasDocumentStoreAccess(
          context, db, thisDocumentStore, "documentcenter-documents-files-delete")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("documentStore", thisDocumentStore);
      context.getRequest().setAttribute("documentStoreId", documentStoreId);
      // Load the file
      FileItem thisItem = new FileItem(
          db, Integer.parseInt(itemId), thisDocumentStore.getId(), Constants.DOCUMENTS_DOCUMENTS);
      // Determine if just 1 version or the whole file is to be deleted
      if (version != null) {
        thisItem.buildVersionList(db);
        if (thisItem.getVersionList().size() > 1) {
          isVersion = true;
          if (Double.parseDouble(version) == ((FileItemVersion) thisItem.getVersionList().get(
              0)).getVersion()) {
            // The first entry in the list is being deleted
            // Delete the version, the next item will update the FileItem
            recordDeleted = ((FileItemVersion) thisItem.getVersionList().get(
                0)).delete(db, this.getPath(context, "documents"));
            thisItem.updateVersion(
                db, (FileItemVersion) thisItem.getVersionList().get(1));
          } else {
            // Just delete the version
            recordDeleted = thisItem.getVersion(Double.parseDouble(version)).delete(
                db, this.getPath(context, "documents"));
          }
        } else {
          // Delete the only version in the list
          recordDeleted = thisItem.delete(
              db, this.getPath(context, "documents"));
          indexDeleteItem(context, thisItem);
        }
      } else {
        // All versions are being deleted
        recordDeleted = thisItem.delete(
            db, this.getPath(context, "documents"));
        indexDeleteItem(context, thisItem);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (recordDeleted) {
      if (isVersion) {
        return ("DeleteVersionOK");
      } else {
        return ("DeleteOK");
      }
    } else {
      return ("DeleteERROR");
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandMove(ActionContext context) {
    Connection db = null;
    //Parameters
    String documentStoreId = (String) context.getRequest().getParameter(
        "documentStoreId");
    String itemId = (String) context.getRequest().getParameter("fid");
    try {
      db = getConnection(context);
      //Load the document store
      DocumentStore thisDocumentStore = new DocumentStore(
          db, Integer.parseInt(documentStoreId));
      if (thisDocumentStore.getId() == -1) {
        throw new Exception("Invalid access to document store");
      }
      thisDocumentStore.buildPermissionList(db);
      if (!hasDocumentStoreAccess(
          context, db, thisDocumentStore, "documentcenter-documents-files-rename")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("documentStore", thisDocumentStore);
      //Load the file
      FileItem thisItem = new FileItem(
          db, Integer.parseInt(itemId), thisDocumentStore.getId(), Constants.DOCUMENTS_DOCUMENTS);
      context.getRequest().setAttribute("FileItem", thisItem);
      //Load the folders
      FileFolderHierarchy hierarchy = new FileFolderHierarchy();
      hierarchy.setLinkModuleId(Constants.DOCUMENTS_DOCUMENTS);
      hierarchy.setLinkItemId(thisDocumentStore.getId());
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
    Connection db = null;
    //Parameters
    String documentStoreId = (String) context.getRequest().getParameter(
        "documentStoreId");
    String newFolderId = (String) context.getRequest().getParameter(
        "folderId");
    String itemId = (String) context.getRequest().getParameter("fid");
    try {
      db = getConnection(context);
      //Load the document store
      DocumentStore thisDocumentStore = new DocumentStore(
          db, Integer.parseInt(documentStoreId));
      if (thisDocumentStore.getId() == -1) {
        throw new Exception("Invalid access to document store");
      }
      thisDocumentStore.buildPermissionList(db);
      if (!hasDocumentStoreAccess(
          context, db, thisDocumentStore, "documentcenter-documents-files-rename")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("documentStore", thisDocumentStore);
      context.getRequest().setAttribute("return", "DocumentsFiles");
      //Load the file
      FileItem thisItem = new FileItem(
          db, Integer.parseInt(itemId), thisDocumentStore.getId(), Constants.DOCUMENTS_DOCUMENTS);
      thisItem.updateFolderId(db, Integer.parseInt(newFolderId));
      return "PopupCloseOK";
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
  public String executeCommandShowThumbnail(ActionContext context) {
    String documentStoreId = (String) context.getRequest().getParameter(
        "documentStoreId");
    String itemId = (String) context.getRequest().getParameter("i");
    String version = (String) context.getRequest().getParameter("v");
    FileItem thisItem = null;
    Connection db = null;
    try {
      db = getConnection(context);
      //Load the document store and check permissions
      DocumentStore thisDocumentStore = new DocumentStore(
          db, Integer.parseInt(documentStoreId));
      thisDocumentStore.buildPermissionList(db);
      if (!hasDocumentStoreAccess(
          context, db, thisDocumentStore, "documentcenter-documents-files-download")) {
        return "PermissionError";
      }
      //Load the file
      thisItem = new FileItem(
          db, Integer.parseInt(itemId), thisDocumentStore.getId(), Constants.DOCUMENTS_DOCUMENTS);
    } catch (Exception e) {
    } finally {
      this.freeConnection(context, db);
    }
    //Start the download
    try {
      String filePath = null;
      if (context.getRequest().getParameter("s") != null) {
        filePath = this.getPath(context, "documents") + getDatePath(
            thisItem.getEntered()) + thisItem.getFilename();
      } else {
        filePath = this.getPath(context, "documents") + getDatePath(
            thisItem.getEntered()) + thisItem.getThumbnailFilename();
      }
      FileDownload fileDownload = new FileDownload();
      fileDownload.setFullPath(filePath);
      fileDownload.setDisplayName(thisItem.getThumbnailFilename());
      if (fileDownload.fileExists()) {
        fileDownload.setFileTimestamp(thisItem.getModificationDate().getTime());
        fileDownload.streamContent(context);
        return "-none-";
      } else {
        return "SystemError";
      }
    } catch (java.net.SocketException se) {
      //User either canceled the download or lost connection
    } catch (Exception e) {
    }
    return ("-none-");
  }
}


