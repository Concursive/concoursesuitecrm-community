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

package org.aspcfs.modules.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import com.isavvix.tools.FileInfo;
import com.isavvix.tools.HttpMultiPartParser;
import com.zeroio.iteam.actions.ProjectManagementFileFolders;
import com.zeroio.iteam.base.*;
import com.zeroio.webutils.FileDownload;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.documents.base.DocumentStore;
import org.aspcfs.modules.documents.base.DocumentStoreList;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.FileUtils;
import org.aspcfs.utils.web.PagedListInfo;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 * Actions for the Message Attachments
 *
 * @author vadim.vishnevsky@corratech.com
 * @version $Id: DocumentSelector.java,v 1.0 2006/12/11 vadim.vishnevsky@corratech.com
 * @created December 11, 2006
 */
public final class DocumentSelector extends CFSModule {

  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDownload(ActionContext context) {
    Exception errorMessage = null;
    String itemId = (String) context.getRequest().getParameter("fid");
    String version = (String) context.getRequest().getParameter("ver");
    String view = (String) context.getRequest().getParameter("view");
    String moduleId = context.getRequest().getParameter("moduleId");
    String linkItemId = context.getRequest().getParameter("linkItemId");
    String fileDir = null;
    FileItem thisItem = null;
    Connection db = null;
    Contact thisContact = null;
    Organization thisOrg = null;
    DocumentStore thisDocumentStore = null;
    Project thisProject = null;
    try {
      db = getConnection(context);
      switch (Integer.parseInt(moduleId)) {
        case Constants.CONTACTS:
          if (!hasPermission(context,
              "accounts-accounts-contacts-documents-view")
              && !hasPermission(context,
              "contacts-external_contacts-documents-view")) {
            return ("PermissionError");
          }
          thisContact = new Contact(db, Integer.parseInt(linkItemId));
          //Check access permission to contact record
          if (!isRecordAccessPermitted(context, db, thisContact.getId())) {
            return ("PermissionError");
          }
          fileDir = "contacts";
          break;
        case Constants.ACCOUNTS:
          if (!hasPermission(context, "accounts-accounts-documents-view")) {
            return ("PermissionError");
          }
          thisOrg = new Organization(db, Integer.parseInt(linkItemId));
          //Check access permission to organization record
          if (!isRecordAccessPermitted(context, db, thisOrg.getOrgId())) {
            return ("PermissionError");
          }
          fileDir = "accounts";
          break;
        case Constants.DOCUMENTS_DOCUMENTS:
          thisDocumentStore = new DocumentStore(db, Integer
              .parseInt(linkItemId));
          thisDocumentStore.buildPermissionList(db);
          context.getRequest().setAttribute("documentStore",
              thisDocumentStore);
          if (!hasDocumentStoreAccess(context, db, thisDocumentStore,
              "documentcenter-documents-files-download")) {
            return "PermissionError";
          }
          fileDir = "documents";
          break;
        case Constants.PROJECTS_FILES:
          thisProject = loadProject(db, Integer.parseInt(linkItemId),
              context);
          thisProject.buildPermissionList(db);
          context.getRequest().setAttribute("Project", thisProject);
          if (!hasProjectAccess(context, db, thisProject,
              "project-documents-files-download")) {
            return "PermissionError";
          }
          fileDir = "projects";
          break;
        default:
          return "PermissionError";
      }
      ////

      thisItem = new FileItem(db, Integer.parseInt(itemId), Integer
          .parseInt(linkItemId), Integer.parseInt(moduleId));
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
        String filePath = this.getPath(context, fileDir)
            + getDatePath(itemToDownload.getModified())
            + itemToDownload.getFilename();
        FileDownload fileDownload = new FileDownload();
        fileDownload.setFullPath(filePath);
        fileDownload.setDisplayName(itemToDownload.getClientFilename());
        if (fileDownload.fileExists()) {
          if (view != null && "true".equals(view)) {
            fileDownload.setFileTimestamp(thisItem
                .getModificationDate().getTime());
            fileDownload.streamContent(context);
          } else {
            fileDownload.sendFile(context);
          }
          //Get a db connection now that the download is complete
          db = getConnection(context);
          itemToDownload.updateCounter(db);
        } else {
          db = null;
          System.err
              .println("MessagesAttachments-> Trying to send a file that does not exist");
        }
      } else {
        FileItemVersion itemToDownload = thisItem.getVersion(Double
            .parseDouble(version));
        itemToDownload.setEnteredBy(this.getUserId(context));
        String filePath = this.getPath(context, fileDir)
            + getDatePath(itemToDownload.getModified())
            + itemToDownload.getFilename();
        FileDownload fileDownload = new FileDownload();
        fileDownload.setFullPath(filePath);
        fileDownload.setDisplayName(itemToDownload.getClientFilename());
        if (fileDownload.fileExists()) {
          if (view != null && "true".equals(view)) {
            fileDownload.setFileTimestamp(itemToDownload
                .getModificationDate().getTime());
            fileDownload.streamContent(context);
          } else {
            fileDownload.sendFile(context);
          }
          //Get a db connection now that the download is complete
          db = getConnection(context);
          itemToDownload.updateCounter(db);
        } else {
          db = null;
          System.err
              .println("MessagesAttachments-> Trying to send a file that does not exist");
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
      return ("SystemError");
    }
  }

  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandListDocuments(ActionContext context) {

    Exception errorMessage = null;
    Connection db = null;
    boolean listDone = false;
    FileFolderList folders = null;
    FileItemList documents = null;
    FileItemList finalDocuments = null;

    String listType = context.getRequest().getParameter("listType");
    if (listType == null) {
      listType = (String) context.getRequest().getAttribute("listType");
    }
    String orgId = context.getRequest().getParameter("orgId");
    if (orgId == null) {
      orgId = (String) context.getRequest().getAttribute("orgId");
    }
    String contactId = context.getRequest().getParameter("contactId");
    if (contactId == null) {
      contactId = (String) context.getRequest().getAttribute("contactId");
    }

    if( (contactId.equals("-1") || contactId == null) ) {
          contactId = (String) context.getRequest().getParameter("contId");
    }    
    SystemStatus thisSystem = this.getSystemStatus(context);

    PagedListInfo pagedListInfo = this.getPagedListInfo(context,
        "documentListInfo");

    String moduleId = context.getRequest().getParameter("moduleId");
    if (moduleId == null) {
      moduleId = (String) context.getRequest().getAttribute("moduleId");
    }

    String linkItemId = context.getRequest().getParameter("linkItemId");
    if (linkItemId == null) {
      linkItemId = (String) context.getRequest().getAttribute(
          "linkItemId");
    }

    if (linkItemId == null) {
      if (Integer.parseInt(moduleId) == Constants.ACCOUNTS
          && !"".equals(orgId)) {
        linkItemId = orgId;
      } else if (Integer.parseInt(moduleId) == Constants.CONTACTS
          && !"".equals(contactId)) {
        linkItemId = contactId;
      } else {
        linkItemId = "-1";
      }
    }
    if (linkItemId == null) {
      linkItemId = "-1";
    }

    String folderId = context.getRequest().getParameter("folderId");
    if (folderId == null) {
      folderId = (String) context.getRequest().getAttribute("folderId");
    }
    String previousSelection = context.getRequest().getParameter(
        "previousSelection");
    if (previousSelection == null) {
      previousSelection = (String) context.getRequest().getAttribute(
          "previousSelection");
    }

    String hiddenFileId = context.getRequest().getParameter("hiddenFileId");
    if (hiddenFileId == null) {
      hiddenFileId = (String) context.getRequest().getAttribute(
          "hiddenFileId");
    }

    String displayFieldId = context.getRequest().getParameter(
        "displayFieldId");
    if (previousSelection == null) {
      previousSelection = (String) context.getRequest().getAttribute(
          "displayFieldId");
    }

    ArrayList selectedList = new ArrayList();

    if (!"true".equals(context.getRequest().getParameter("reset"))) {
      if (previousSelection != null) {
        StringTokenizer st = new StringTokenizer(previousSelection, "|");
        while (st.hasMoreTokens()) {
          selectedList.add(String.valueOf(st.nextToken()));
        }
      }
    }
    String pagedListLink = "";
    pagedListLink = "DocumentSelector.do?command=ListDocuments";
    if (moduleId != null) {
      pagedListLink += "&moduleId=" + moduleId;
    }
    if (folderId != null) {
      pagedListLink += "&folderId=" + folderId;
    }
    if (orgId != null) {
      pagedListLink += "&orgId=" + orgId;
    }
    if (contactId != null) {
      pagedListLink += "&contactId=" + contactId;
    }
    if (previousSelection != null) {
      pagedListLink += "&previousSelection=" + previousSelection;
    }
    if (linkItemId != null) {
      pagedListLink += "&linkItemId=" + linkItemId;
    }
    if (listType != null) {
      pagedListLink += "&listType=" + listType;
    }
    if (hiddenFileId != null) {
      pagedListLink += "&hiddenFileId=" + hiddenFileId;
    }
    if (displayFieldId != null) {
      pagedListLink += "&displayFieldId=" + displayFieldId;
    }

    pagedListInfo.setLink(pagedListLink);
    pagedListInfo.setItemsPerPage(0);

    try {
      db = this.getConnection(context);
      int rowCount = 1;

      if ("list".equals(listType)) {
        while (context.getRequest().getParameter(
            "hiddenFileId" + rowCount) != null) {
          int fileId = Integer.parseInt(context.getRequest()
              .getParameter("hiddenFileId" + rowCount));
          if (context.getRequest().getParameter("file" + rowCount) != null) {
            if (!selectedList.contains(String.valueOf(fileId))) {
              selectedList.add(String.valueOf(fileId));
            }
          } else {
            selectedList.remove(String.valueOf(fileId));
          }
          rowCount++;
        }
      }

      if ("true".equals((String) context.getRequest().getParameter(
          "finalsubmit"))) {
        // Handle single selection case
        if ("single".equals(listType)) {
          rowCount = Integer.parseInt(context.getRequest()
              .getParameter("rowcount"));
          int fileId = Integer.parseInt(context.getRequest()
              .getParameter("hiddenFileId" + rowCount));
          selectedList.clear();
          selectedList.add(String.valueOf(fileId));
        }
        listDone = true;
        if (finalDocuments == null) {
          finalDocuments = new FileItemList();
        }
        for (int i = 0; i < selectedList.size(); i++) {
          int fileId = Integer.parseInt((String) selectedList.get(i));
          finalDocuments.add(new FileItem(db, fileId));
        }
      }
      // Start Build document stores list
      if ("-1".equals(linkItemId)
          && Integer.parseInt(moduleId) == Constants.DOCUMENTS_DOCUMENTS) {
        if (!(hasPermission(context, "documents-view"))) {
          return ("PermissionError");
        }
        DocumentStoreList documentStoreList = new DocumentStoreList();
        int tmpUserId = this.getUserId(context);
        User tmpUser = getUser(context, tmpUserId);
        int tmpUserRoleId = tmpUser.getRoleId();
        Contact tmpContact = new Contact(db, tmpUser.getContactId());
        int tmpDepartmentId = tmpContact.getDepartment();
        documentStoreList.setDocumentStoresForUser(getUserId(context));
        documentStoreList.setUserRole(tmpUserRoleId);
        documentStoreList.setDepartmentId(tmpDepartmentId);
        documentStoreList.setSiteId(tmpUser.getSiteId());
        documentStoreList.buildList(db);
//				 See which document stores this user has access to...
        DocumentStoreList docStoreList = new DocumentStoreList();
        Iterator i = documentStoreList.iterator();
        while (i.hasNext()) {
          DocumentStore thisDocumentStore = (DocumentStore) i.next();
          thisDocumentStore.buildPermissionList(db);
          if (hasDocumentStoreAccess(context, db,
              thisDocumentStore,
              "documentcenter-documents-files-download")) {
            docStoreList.add(thisDocumentStore);
          }
        }
        context.getRequest().setAttribute("documentStoreList",
            docStoreList);
      } else {
        if ("-1".equals(linkItemId)
            && Integer.parseInt(moduleId) == Constants.PROJECTS_FILES) {
          if (!(hasPermission(context, "projects-view"))) {
            return ("PermissionError");
          }
          // End Build document stores list
          // Start Build projects list
          ProjectList projects = new ProjectList();
          // Project Info
          projects.setGroupId(-1);
          projects.setIncludeGuestProjects(true);
          projects.setPortalState(Constants.FALSE);
          projects.setBuildOverallProgress(true);
          projects.buildList(db);
          ProjectList projectList = new ProjectList();
          // See which projects this user has access to...
          Iterator i = projects.iterator();
          while (i.hasNext()) {
            Project thisProject = (Project) i.next();
            thisProject.buildPermissionList(db);
            if (hasProjectAccess(context, db, thisProject,
                "project-documents-files-download")) {
              projectList.add(thisProject);
            }
          }
          context.getRequest().setAttribute("projectList", projectList);
          // End Build project list
        } else {
          Contact thisContact = null;
          Organization thisOrg = null;
          DocumentStore thisDocumentStore = null;
          Project thisProject = null;
          switch (Integer.parseInt(moduleId)) {
            case Constants.CONTACTS:
              if (!hasPermission(context,
                  "accounts-accounts-contacts-documents-view")
                  && !hasPermission(context,
                  "contacts-external_contacts-documents-view")) {
                return ("PermissionError");
              }
              thisContact = new Contact(db, Integer
                  .parseInt(linkItemId));
              //Check access permission to contact record
              if (!isRecordAccessPermitted(context, db, thisContact
                  .getId())) {
                return ("PermissionError");
              }
              break;
            case Constants.ACCOUNTS:
              if (!hasPermission(context,
                  "accounts-accounts-documents-view")) {
                return ("PermissionError");
              }
              thisOrg = new Organization(db, Integer
                  .parseInt(linkItemId));
              //Check access permission to organization record
              if (!isRecordAccessPermitted(context, db, thisOrg
                  .getOrgId())) {
                return ("PermissionError");
              }
              break;
            case Constants.DOCUMENTS_DOCUMENTS:
              thisDocumentStore = new DocumentStore(db, Integer
                  .parseInt(linkItemId));
              thisDocumentStore.buildPermissionList(db);
              context.getRequest().setAttribute("documentStore",
                  thisDocumentStore);
              if (!hasDocumentStoreAccess(context, db,
                  thisDocumentStore,
                  "documentcenter-documents-files-download")) {
                return "PermissionError";
              }
              break;
            case Constants.PROJECTS_FILES:
              thisProject = loadProject(db, Integer
                  .parseInt(linkItemId), context);
              thisProject.buildPermissionList(db);
              context.getRequest().setAttribute("Project",
                  thisProject);
              if (!hasProjectAccess(context, db, thisProject,
                  "project-documents-files-download")) {
                return "PermissionError";
              }
              break;
            default:
              return "PermissionError";
          }
          folders = new FileFolderList();
          // Build the folder list
          if (folderId == null || "-1".equals(folderId)
              || "0".equals(folderId) || "".equals(folderId)) {
            folders.setTopLevelOnly(true);
          } else {
            folders.setParentId(Integer.parseInt(folderId));
            // Build array of folder trails
            ProjectManagementFileFolders
                .buildHierarchy(db, context);
          }
          folders.setLinkModuleId(Integer.parseInt(moduleId));
          folders.setLinkItemId(Integer.parseInt(linkItemId));
          folders.setBuildItemCount(true);
          folders.setPagedListInfo(pagedListInfo);
          folders.buildList(db);

          documents = new FileItemList();
          // build the filesList
          if (folderId == null || "-1".equals(folderId)
              || "0".equals(folderId) || "".equals(folderId)) {
            documents.setTopLevelOnly(true);
          } else {
            documents.setFolderId(Integer.parseInt(folderId));
          }
          documents.setLinkModuleId(Integer.parseInt(moduleId));
          documents.setLinkItemId(Integer.parseInt(linkItemId));
          documents.setPagedListInfo(pagedListInfo);
          documents.buildList(db);
        }
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "ViewDocuments", "View Documents");
    if (errorMessage == null) {
      context.getRequest().setAttribute("fileItemList", documents);
      context.getRequest().setAttribute("orgId", orgId);
      context.getRequest().setAttribute("contactId", contactId);
      context.getRequest().setAttribute("fileFolderList", folders);
      context.getRequest().setAttribute("linkItemId", linkItemId);
      context.getRequest()
          .setAttribute("selectedDocuments", selectedList);
      if (listDone) {
        context.getRequest().setAttribute("finalDocuments",
            finalDocuments);
      }
      return ("ListDocumentsOK");
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
  public String executeCommandUpload(ActionContext context) {
    Connection db = null;
    boolean isValid = false;
    boolean recordInserted = false;
    String filePath = null;
    String path = null;

    try {
      // Process the form data
      HttpMultiPartParser multiPart = new HttpMultiPartParser();
      multiPart.setUsePathParam(false);
      multiPart.setUseUniqueName(true);
      multiPart.setUseDateForFolder(true);
      multiPart.setExtensionId(getUserId(context));
      HashMap parts = multiPart.parseData(context.getRequest());
      db = getConnection(context);

      String subject = (String) parts.get("subject");
      String moduleId = (String) parts.get("moduleId");
      String linkItemId = (String) parts.get("linkItemId");
      String folderId = (String) parts.get("folderId");
      String orgId = (String) parts.get("orgId");
      String contactId = (String) parts.get("contactId");
      String selectedList = (String) parts.get("previousSelection");
      String fileDir = "";
      Contact thisContact = null;
      Organization thisOrg = null;
      DocumentStore thisDocumentStore = null;
      Project thisProject = null;

      switch (Integer.parseInt(moduleId)) {
        case Constants.CONTACTS:
          if (!hasPermission(context,
              "accounts-accounts-contacts-documents-add")
              && !hasPermission(context,
              "contacts-external_contacts-documents-add")) {
            return ("PermissionError");
          }
          thisContact = new Contact(db, Integer.parseInt(linkItemId));
          //Check access permission to contact record
          if (!isRecordAccessPermitted(context, db, thisContact.getId())) {
            return ("PermissionError");
          }
          fileDir = "contacts";
          break;
        case Constants.ACCOUNTS:
          if (!hasPermission(context, "accounts-accounts-documents-add")) {
            return ("PermissionError");
          }
          thisOrg = new Organization(db, Integer.parseInt(linkItemId));
          //Check access permission to organization record
          if (!isRecordAccessPermitted(context, db, thisOrg.getOrgId())) {
            return ("PermissionError");
          }
          fileDir = "accounts";
          break;
        case Constants.DOCUMENTS_DOCUMENTS:
          thisDocumentStore = new DocumentStore(db, Integer
              .parseInt(linkItemId));
          thisDocumentStore.buildPermissionList(db);
          context.getRequest().setAttribute("documentStore",
              thisDocumentStore);
          if (!hasDocumentStoreAccess(context, db, thisDocumentStore,
              "documentcenter-documents-files-upload")) {
            return "PermissionError";
          }
          fileDir = "documents";
          break;
        case Constants.PROJECTS_FILES:
          thisProject = loadProject(db, Integer.parseInt(linkItemId),
              context);
          thisProject.buildPermissionList(db);
          context.getRequest().setAttribute("Project", thisProject);
          if (!hasProjectAccess(context, db, thisProject,
              "project-documents-files-upload")) {
            return "PermissionError";
          }
          fileDir = "projects";
          break;
        default:
          return "PermissionError";
      }

      if ((Object) parts.get("attachment") instanceof FileInfo) {
        // Update the database with the resulting file
        FileInfo newFileInfo = (FileInfo) parts.get("attachment");
        FileItem thisItem = new FileItem();
        filePath = this.getPath(context, fileDir);
        thisItem.setLinkModuleId(moduleId);
        thisItem.setLinkItemId(linkItemId);
        filePath += this.getDatePath(new java.util.Date());

        // Create output directory in case it doesn't exist
        File f = new File(filePath);
        f.mkdirs();

        // Store files using a unique name, based on date
        String filenameToUse = DateUtils.getFilename();
        newFileInfo.setClientFileName(FileUtils.getFileName(newFileInfo
            .getClientFileName()));
        filenameToUse +=
            (newFileInfo.getVersion() == -1 ? "" : "^" + newFileInfo.getVersion()) +
                (newFileInfo.getExtensionId() == -1 ? "" : "-" + newFileInfo.getExtensionId());

        // Move the file from memory to the file system
        FileUtils.copyBytesToFile(newFileInfo.getFileContents(),
            new File(FileUtils.getFileName(filePath, filenameToUse)), true);

        File thisFile = new File(FileUtils.getFileName(filePath, filenameToUse));
        newFileInfo.setLocalFile(thisFile);
        newFileInfo.setSize((int) thisFile.length());

        thisItem.setEnteredBy(getUserId(context));
        thisItem.setModifiedBy(getUserId(context));
        if (folderId != null) {
          thisItem.setFolderId(Integer.parseInt(folderId));
        } else {
          thisItem.setFolderId(-1);
        }
        thisItem.setSubject(subject);
        thisItem.setClientFilename(newFileInfo.getClientFileName());
        thisItem.setFilename(newFileInfo.getRealFilename());
        thisItem.setVersion(1.0);
        thisItem.setSize(newFileInfo.getSize());
        isValid = this.validateObject(context, db, thisItem);
        if (isValid) {
          recordInserted = thisItem.insert(db);
          if (recordInserted) {
            this.processInsertHook(context, thisItem);
          }
        } else {
          recordInserted = false;
          HashMap errors = new HashMap();
          SystemStatus systemStatus = this.getSystemStatus(context);
          errors.put("actionError", systemStatus
              .getLabel("object.validation.incorrectFileName"));
          processErrors(context, errors);
        }
      }

      context.getRequest().setAttribute("moduleId", moduleId);
      context.getRequest().setAttribute("linkItemId", linkItemId);
      context.getRequest().setAttribute("orgId", orgId);
      context.getRequest().setAttribute("contactId", contactId);
      context.getRequest()
          .setAttribute("previousSelection", selectedList);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      freeConnection(context, db);
    }

    return (executeCommandListDocuments(context));
	}
}
