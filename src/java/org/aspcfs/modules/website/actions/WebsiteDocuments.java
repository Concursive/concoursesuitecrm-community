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
package org.aspcfs.modules.website.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import com.isavvix.tools.FileInfo;
import com.isavvix.tools.HttpMultiPartParser;
import com.zeroio.iteam.base.FileFolderList;
import com.zeroio.iteam.base.FileItem;
import com.zeroio.iteam.base.FileItemList;
import com.zeroio.iteam.base.Thumbnail;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.documents.actions.DocumentStoreManagementFileFolders;
import org.aspcfs.modules.documents.base.DocumentStore;
import org.aspcfs.modules.documents.base.DocumentStoreList;
import org.aspcfs.modules.documents.base.DocumentStoreTeamMember;
import org.aspcfs.utils.ImageUtils;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;

/**
 * Description of the Class
 * 
 * @author Olga.Kaptyug
 * @version $Id: $
 * @created Jan 22, 2007
 */
public final class WebsiteDocuments extends CFSModule {

  /**
   * Description of the Method
   * 
   * @param context
   *          Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandView(ActionContext context) {

    Exception errorMessage = null;
    Connection db = null;

    try {
      db = getConnection(context);

      // Build the folder list
      DocumentStoreList documentStoreList = new DocumentStoreList();
      PagedListInfo documentStoreListInfo = this.getPagedListInfo(context,
          "documentStoreListInfo");
      String link="WebsiteDocuments.do?command=View"+("true".equals(context.getRequest().getParameter("popup"))?"&popup=true":"");
      documentStoreListInfo.setLink(link);
      if (documentStoreListInfo.getListView() == null) {
        // My Open Document Stores
        documentStoreListInfo.setListView("Open");
      }
      if (documentStoreListInfo.getListView().equals("Open")) {
        documentStoreList.setOpenDocumentStoresOnly(true);
      } else if (documentStoreListInfo.getListView().equals("Archived")) {
        documentStoreList.setClosedDocumentStoresOnly(true);
      } else if (documentStoreListInfo.getListView().equals("Trashed")) {
        documentStoreList.setIncludeOnlyTrashed(true);
      }

      documentStoreList.setPublicOnly(true);
      documentStoreList.setPagedListInfo(documentStoreListInfo);
      documentStoreList.buildList(db);
      getDocumentStoreUserLevel(context, db, DocumentStoreTeamMember.GUEST);
      context.getRequest().setAttribute("documentStoreList", documentStoreList);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "View Website", "View Documents");
    if (errorMessage == null) {
      return getReturn(context, "View");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }

  /**
   * Description of the Method
   * 
   * @param context
   *          Description of the Parameter
   * @return Description of the Return Value
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
     
      // Build array of folder trails
      DocumentStoreManagementFileFolders.buildHierarchy(db, context);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "Add");
  }

  /**
   * Description of the Method
   * 
   * @param context
   *          Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandUpload(ActionContext context) {
    Connection db = null;
    boolean recordInserted = false;
    boolean isValid = false;
    String documentStoreId = "";
    String subject = "";
    String folderId = "";
    String id = "";
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
      id = (String) parts.get("id");
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
      if ((Object) parts.get("id" + id) instanceof FileInfo) {
        FileInfo newFileInfo = (FileInfo) parts.get("id" + id);

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
        
          thisItem.setVersion(1.0);
          isValid = this.validateObject(context, db, thisItem);
          if (isValid) {
            recordInserted = thisItem.insert(db);
            thisItem.setDirectory(filePath);
            indexAddItem(context, thisItem);
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
            context.getRequest().setAttribute("fileItem", thisItem);
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
      context.getRequest().setAttribute("subject", "");
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      freeConnection(context, db);
    }
    if (recordInserted && isValid) {
      return getReturn(context, "Upload");
    } else {
        return executeCommandAdd(context);
    }
  }

  /**
   * Description of the Method
   * 
   * @param context
   *          Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDocumentStoreCenter(ActionContext context) {
    Connection db = null;
    DocumentStore thisDocumentStore = null;
    // Parameters
    String documentStoreId = (String) context.getRequest().getParameter(
        "documentStoreId");
    if (documentStoreId == null) {
      documentStoreId = (String) context.getRequest().getAttribute(
          "documentStoreId");
    }
    SystemStatus systemStatus = this.getSystemStatus(context);
    String section = (String) context.getRequest().getParameter("section");
    // Determine the section to display
    try {
      db = getConnection(context);
      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("SiteIdList", siteList);
      thisDocumentStore = new DocumentStore(db, Integer
          .parseInt(documentStoreId));

      section = "File_Library";

      String folderId = context.getRequest().getParameter("folderId");
      if (folderId == null) {
        folderId = (String) context.getRequest().getAttribute("folderId");
      }
      // Build the folder list
      FileFolderList folders = new FileFolderList();
      if (folderId == null || "-1".equals(folderId) || "0".equals(folderId)) {
        folders.setTopLevelOnly(true);
      } else {
        folders.setParentId(Integer.parseInt(folderId));
        // Build array of folder trails
        DocumentStoreManagementFileFolders.buildHierarchy(db, context);
      }
      folders.setLinkModuleId(Constants.DOCUMENTS_DOCUMENTS);
      folders.setLinkItemId(thisDocumentStore.getId());
      folders.setBuildItemCount(true);
      folders.buildList(db);
      context.getRequest().setAttribute("fileFolderList", folders);
      // Build the file item list
      FileItemList files = new FileItemList();
      if (folderId == null || "-1".equals(folderId) || "0".equals(folderId)) {
        files.setTopLevelOnly(true);
        // Reset the pagedListInfo
        deletePagedListInfo(context, "documentStoreDocumentsGalleryInfo");
      } else {
        files.setFolderId(Integer.parseInt(folderId));
      }
      files.setLinkModuleId(Constants.DOCUMENTS_DOCUMENTS);
      files.setLinkItemId(thisDocumentStore.getId());
      files.buildList(db);
      thisDocumentStore.setFiles(files);

      context.getRequest().setAttribute("documentStore", thisDocumentStore);
      context.getRequest()
          .setAttribute("IncludeSection", section.toLowerCase());
      // The user has access, so show that they accessed the Document Store
      User user = this.getUser(context, this.getUserId(context));
      DocumentStoreTeamMember.updateLastAccessed(db, thisDocumentStore.getId(),
          user.getId(), user.getSiteId());
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "DocumentStoreCenter");
  }

}
