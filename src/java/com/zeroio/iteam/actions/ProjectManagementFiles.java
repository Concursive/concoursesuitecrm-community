/*
 *  Copyright(c) 2004 Team Elements LLC (http://www.teamelements.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Team Elements LLC. Permission to use, copy, and modify this
 *  material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. TEAM
 *  ELEMENTS MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL TEAM ELEMENTS LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR ANY
 *  DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package com.zeroio.iteam.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import com.isavvix.tools.FileInfo;
import com.isavvix.tools.HttpMultiPartParser;
import com.zeroio.iteam.base.*;
import com.zeroio.webutils.FileDownload;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.ImageUtils;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;

/**
 * Description of the Class
 *
 * @author mrajkowski
 * @version $Id: ProjectManagementFiles.java,v 1.14.82.1 2004/07/07 15:12:07
 *          mrajkowski Exp $
 * @created December 4, 2001
 */
public final class ProjectManagementFiles extends CFSModule {

  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandAdd(ActionContext context) {
    String projectId = (String) context.getRequest().getParameter("pid");
    if (projectId == null) {
      projectId = (String) context.getRequest().getAttribute("pid");
    }
    Connection db = null;
    try {
      db = getConnection(context);
      // Load the project
      Project thisProject = loadProject(
          db, Integer.parseInt(projectId), context);
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(
          context, db, thisProject, "project-documents-files-upload")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("Project", thisProject);
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
      ProjectManagementFileFolders.buildHierarchy(db, context);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("ProjectCenterOK");
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
    String projectId = "";
    String subject = "";
    String folderId = "";
    String itemId = "";
    String versionId = "";
    try {
      String filePath = this.getPath(context, "projects");
      //Process the form data
      HttpMultiPartParser multiPart = new HttpMultiPartParser();
      multiPart.setUsePathParam(false);
      multiPart.setUseUniqueName(true);
      multiPart.setUseDateForFolder(true);
      multiPart.setExtensionId(getUserId(context));
      HashMap parts = multiPart.parseData(context.getRequest(), filePath);
      projectId = (String) parts.get("pid");
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
      Project thisProject = loadProject(
          db, Integer.parseInt(projectId), context);
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(
          context, db, thisProject, "project-documents-files-upload")) {
        //TODO: Should delete the uploads, then exit
        return "PermissionError";
      }
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute(
          "IncludeSection", ("file_library").toLowerCase());
      //Update the database with the resulting file
      if ((Object) parts.get("id" + projectId) instanceof FileInfo) {
        FileInfo newFileInfo = (FileInfo) parts.get("id" + projectId);

        FileItem thisItem = new FileItem();
        thisItem.setLinkModuleId(Constants.PROJECTS_FILES);
        thisItem.setLinkItemId(thisProject.getId());
        thisItem.setEnteredBy(getUserId(context));
        thisItem.setModifiedBy(getUserId(context));
        thisItem.setFolderId(Integer.parseInt(folderId));
        thisItem.setSubject(subject);
        thisItem.setClientFilename(newFileInfo.getClientFileName());
        thisItem.setFilename(newFileInfo.getRealFilename());
        thisItem.setSize(newFileInfo.getSize());
        if (itemId == null || "-1".equals(itemId)) {
          // this is a new document
          thisItem.setVersion(1.0);
          isValid = this.validateObject(context, db, thisItem);
          if (isValid) {
            recordInserted = thisItem.insert(db);
          }
          thisItem.setDirectory(filePath);
          indexAddItem(context, thisItem);
        } else {
          // this is a new version of an existing document
          thisItem.setId(Integer.parseInt(itemId));
          thisItem.setVersion(Double.parseDouble(versionId));
          isValid = this.validateObject(context, db, thisItem);
          if (isValid) {
            recordInserted = thisItem.insertVersion(db);
          }
          thisItem.setDirectory(filePath);
          indexAddItem(context, thisItem);
        }
        if (recordInserted) {
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
            recordInserted = thumbnail.insert(db);
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
      context.getRequest().setAttribute("pid", projectId);
      context.getRequest().setAttribute("folderId", folderId);
      context.getRequest().setAttribute("fid", itemId);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      freeConnection(context, db);
    }
    if (recordInserted) {
      return ("AddOK");
    } else {
      HashMap errors = new HashMap();
      SystemStatus systemStatus = this.getSystemStatus(context);
      errors.put(
          "actionError", systemStatus.getLabel(
              "object.validation.incorrectFileName"));
      processErrors(context, errors);
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
    String projectId = (String) context.getRequest().getParameter("pid");
    if (projectId == null) {
      projectId = (String) context.getRequest().getAttribute("pid");
    }
    String itemId = (String) context.getRequest().getParameter("fid");
    if (itemId == null) {
      itemId = (String) context.getRequest().getAttribute("fid");
    }
    Connection db = null;
    try {
      db = getConnection(context);
      // Load the project
      Project thisProject = loadProject(
          db, Integer.parseInt(projectId), context);
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(
          context, db, thisProject, "project-documents-files-upload")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute(
          "IncludeSection", ("file_upload").toLowerCase());
      // Load the file item
      FileItem thisFile = new FileItem(
          db, Integer.parseInt(itemId), Integer.parseInt(projectId), Constants.PROJECTS_FILES);
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
      ProjectManagementFileFolders.buildHierarchy(db, context);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("ProjectCenterOK");
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
      String filePath = this.getPath(context, "projects");
      //Process the form data
      HttpMultiPartParser multiPart = new HttpMultiPartParser();
      multiPart.setUsePathParam(false);
      multiPart.setUseUniqueName(true);
      multiPart.setUseDateForFolder(true);
      multiPart.setExtensionId(getUserId(context));
      HashMap parts = multiPart.parseData(context.getRequest(), filePath);
      String projectId = (String) parts.get("pid");
      String itemId = (String) parts.get("fid");
      String subject = (String) parts.get("subject");
      String versionId = (String) parts.get("versionId");
      //Update the database with the resulting file
      FileInfo newFileInfo = (FileInfo) parts.get("id" + projectId);
      db = getConnection(context);
      Project thisProject = loadProject(
          db, Integer.parseInt(projectId), context);
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(
          context, db, thisProject, "project-documents-files-upload")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute(
          "IncludeSection", ("file_library").toLowerCase());

      FileItem thisItem = new FileItem();
      thisItem.setLinkModuleId(Constants.PROJECTS_FILES);
      thisItem.setLinkItemId(thisProject.getId());
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
        if (!recordInserted) {
          processErrors(context, thisItem.getErrors());
        }
      }
      context.getRequest().setAttribute("pid", projectId);
      context.getRequest().setAttribute("fid", itemId);
      //Build array of folder trails
      ProjectManagementFileFolders.buildHierarchy(db, context);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      freeConnection(context, db);
    }
    if (recordInserted) {
      return ("AddOK");
    }
    return (executeCommandAddVersion(context));
  }


  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandDetails(ActionContext context) {
    Exception errorMessage = null;
    String projectId = (String) context.getRequest().getParameter("pid");
    if (projectId == null) {
      projectId = (String) context.getRequest().getAttribute("pid");
    }
    String itemId = (String) context.getRequest().getParameter("fid");
    Connection db = null;
    try {
      db = getConnection(context);
      //Load the project info
      Project thisProject = loadProject(
          db, Integer.parseInt(projectId), context);
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(
          context, db, thisProject, "project-documents-view")) {
        return "PermissionError";
      }
      thisProject.buildFileItemList(db);
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute(
          "IncludeSection", ("file_details").toLowerCase());
      //Load the details for the selected file item
      FileItem thisItem = new FileItem(
          db, Integer.parseInt(itemId), thisProject.getId(), Constants.PROJECTS_FILES);
      thisItem.buildVersionList(db);
      context.getRequest().setAttribute("FileItem", thisItem);
      //Load the current folder state information
      ProjectManagementFileFolders.buildHierarchy(db, context);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      return ("ProjectCenterOK");
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
    String projectId = (String) context.getRequest().getParameter("pid");
    String itemId = (String) context.getRequest().getParameter("fid");
    String version = (String) context.getRequest().getParameter("ver");
    String view = (String) context.getRequest().getParameter("view");
    FileItem thisItem = null;
    Connection db = null;
    try {
      db = getConnection(context);
      Project thisProject = loadProject(
          db, Integer.parseInt(projectId), context);
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(
          context, db, thisProject, "project-documents-files-download")) {
        return "PermissionError";
      }
      thisItem = new FileItem(
          db, Integer.parseInt(itemId), thisProject.getId(), Constants.PROJECTS_FILES);
      if (version != null) {
        thisItem.buildVersionList(db);
      }
      context.getRequest().setAttribute("Project", thisProject);
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
        String filePath = this.getPath(context, "projects") + getDatePath(
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
          System.err.println(
              "PMF-> Trying to send a file that does not exist");
        }
      } else {
        FileItemVersion itemToDownload = thisItem.getVersion(
            Double.parseDouble(version));
        itemToDownload.setEnteredBy(this.getUserId(context));
        String filePath = this.getPath(context, "projects") + getDatePath(
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
          System.err.println(
              "PMF-> Trying to send a file that does not exist");
        }
      }
    } catch (java.net.SocketException se) {
      //User either canceled the download or lost connection
    } catch (Exception e) {
      errorMessage = e;
      System.out.println(e.toString());
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
    String projectId = (String) context.getRequest().getParameter("pid");
    String itemId = (String) context.getRequest().getParameter("fid");
    Connection db = null;
    try {
      db = getConnection(context);
      //Load the project
      Project thisProject = loadProject(
          db, Integer.parseInt(projectId), context);
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(
          context, db, thisProject, "project-documents-files-rename")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute(
          "IncludeSection", ("file_modify").toLowerCase());
      //Load the file item to be modified
      FileItem thisItem = new FileItem(
          db, Integer.parseInt(itemId), thisProject.getId(), Constants.PROJECTS_FILES);
      thisItem.buildVersionList(db);
      context.getRequest().setAttribute("FileItem", thisItem);
      //Build array of folder trails
      ProjectManagementFileFolders.buildHierarchy(db, context);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      return ("ProjectCenterOK");
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
    boolean recordInserted = false;
    boolean isValid = false;
    String projectId = (String) context.getRequest().getParameter("pid");
    String itemId = (String) context.getRequest().getParameter("fid");
    String subject = (String) context.getRequest().getParameter("subject");
    String filename = (String) context.getRequest().getParameter(
        "clientFilename");

    Connection db = null;
    try {
      db = getConnection(context);
      //Load the project
      Project thisProject = loadProject(
          db, Integer.parseInt(projectId), context);
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(
          context, db, thisProject, "project-documents-files-rename")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("Project", thisProject);
      String filePath = this.getPath(context, "projects");
      //Load the file item
      FileItem thisItem = new FileItem(
          db, Integer.parseInt(itemId), thisProject.getId(), Constants.PROJECTS_FILES);
      thisItem.setClientFilename(filename);
      thisItem.setSubject(subject);
      isValid = this.validateObject(context, db, thisItem);
      if (isValid) {
        recordInserted = thisItem.update(db);
      }
      thisItem.setDirectory(filePath);
      indexAddItem(context, thisItem);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (recordInserted && isValid) {
      return ("UpdateOK");
    } else {
      context.getRequest().setAttribute("pid", projectId);
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
    boolean recordDeleted = false;
    String projectId = (String) context.getRequest().getParameter("pid");
    String itemId = (String) context.getRequest().getParameter("fid");
    String version = (String) context.getRequest().getParameter("ver");
    Connection db = null;
    boolean isVersion = false;
    try {
      db = getConnection(context);
      //Load the project
      Project thisProject = loadProject(
          db, Integer.parseInt(projectId), context);
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(
          context, db, thisProject, "project-documents-files-delete")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("pid", projectId);
      // Load the file
      FileItem thisItem = new FileItem(
          db, Integer.parseInt(itemId), thisProject.getId(), Constants.PROJECTS_FILES);
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
                0)).delete(db, this.getPath(context, "projects"));
            thisItem.updateVersion(
                db, (FileItemVersion) thisItem.getVersionList().get(1));
          } else {
            // Just delete the version
            recordDeleted = thisItem.getVersion(Double.parseDouble(version)).delete(
                db, this.getPath(context, "projects"));
          }
        } else {
          // Delete the only version in the list
          recordDeleted = thisItem.delete(
              db, this.getPath(context, "projects"));
          indexDeleteItem(context, thisItem);
        }
      } else {
        // All versions are being deleted
        recordDeleted = thisItem.delete(db, this.getPath(context, "projects"));
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
    String projectId = (String) context.getRequest().getParameter("pid");
    String itemId = (String) context.getRequest().getParameter("fid");
    try {
      db = getConnection(context);
      //Load the project
      Project thisProject = loadProject(
          db, Integer.parseInt(projectId), context);
      if (thisProject.getId() == -1) {
        throw new Exception("Invalid access to project");
      }
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(
          context, db, thisProject, "project-documents-files-rename")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("Project", thisProject);
      //Load the file
      FileItem thisItem = new FileItem(
          db, Integer.parseInt(itemId), thisProject.getId(), Constants.PROJECTS_FILES);
      context.getRequest().setAttribute("FileItem", thisItem);
      //Load the folders
      FileFolderHierarchy hierarchy = new FileFolderHierarchy();
      hierarchy.setLinkModuleId(Constants.PROJECTS_FILES);
      hierarchy.setLinkItemId(thisProject.getId());
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
    String projectId = (String) context.getRequest().getParameter("pid");
    String newFolderId = (String) context.getRequest().getParameter(
        "folderId");
    String itemId = (String) context.getRequest().getParameter("fid");
    try {
      db = getConnection(context);
      //Load the project
      Project thisProject = loadProject(
          db, Integer.parseInt(projectId), context);
      if (thisProject.getId() == -1) {
        throw new Exception("Invalid access to project");
      }
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(
          context, db, thisProject, "project-documents-files-rename")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("Project", thisProject);
      //Load the file
      FileItem thisItem = new FileItem(
          db, Integer.parseInt(itemId), thisProject.getId(), Constants.PROJECTS_FILES);
      thisItem.updateFolderId(db, Integer.parseInt(newFolderId));
      indexAddItem(context, thisItem);
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
    String projectId = (String) context.getRequest().getParameter("p");
    String itemId = (String) context.getRequest().getParameter("i");
    FileItem thisItem = null;
    Connection db = null;
    try {
      db = getConnection(context);
      //Load the project and check permissions
      Project thisProject = loadProject(
          db, Integer.parseInt(projectId), context);
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(
          context, db, thisProject, "project-documents-files-download")) {
        return "PermissionError";
      }
      //Load the file
      thisItem = new FileItem(
          db, Integer.parseInt(itemId), thisProject.getId(), Constants.PROJECTS_FILES);
    } catch (Exception e) {
    } finally {
      this.freeConnection(context, db);
    }
    //Start the download
    try {
      String filePath = null;
      if (context.getRequest().getParameter("s") != null) {
        filePath = this.getPath(context, "projects") + getDatePath(
            thisItem.getModified()) + thisItem.getFilename();
      } else {
        filePath = this.getPath(context, "projects") + getDatePath(
            thisItem.getModified()) + thisItem.getThumbnailFilename();
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


