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

//import org.theseus.actions.ActionContext;

import com.darkhorseventures.framework.actions.ActionContext;
import com.zeroio.iteam.base.FileFolder;
import com.zeroio.iteam.base.FileFolderHierarchy;
import com.zeroio.iteam.base.FileFolderList;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.documents.base.DocumentStore;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * Description of the Class
 *
 * @author
 * @version $Id: DocumentStoreManagementFileFolders.java,v 1.1.4.1 2004/12/02
 *          21:33:40 mrajkowski Exp $
 * @created
 */
public final class DocumentStoreManagementFileFolders extends CFSModule {

  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandAdd(ActionContext context) {
    Exception errorMessage = null;
    //Load document store
    String documentStoreId = (String) context.getRequest().getParameter(
        "documentStoreId");
    Connection db = null;
    try {
      FileFolder thisFolder = (FileFolder) context.getFormBean();
      thisFolder.setParentId(context.getRequest().getParameter("parentId"));
      db = getConnection(context);
      //Load the document store
      DocumentStore thisDocumentStore = new DocumentStore(
          db, Integer.parseInt(documentStoreId));
      thisDocumentStore.buildPermissionList(db);
      if (!hasDocumentStoreAccess(
          context, db, thisDocumentStore, "documentcenter-documents-folders-add")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("documentStore", thisDocumentStore);
      context.getRequest().setAttribute(
          "IncludeSection", ("file_folder").toLowerCase());
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
  public String executeCommandSave(ActionContext context) {
    Connection db = null;
    int resultCount = 0;
    boolean recordInserted = false;
    boolean isValid = false;
    String documentStoreId = (String) context.getRequest().getParameter(
        "documentStoreId");
    try {
      db = this.getConnection(context);
      //Load the document store
      DocumentStore thisDocumentStore = new DocumentStore(
          db, Integer.parseInt(documentStoreId));
      thisDocumentStore.buildPermissionList(db);
      //Insert or update the folder
      FileFolder thisFolder = (FileFolder) context.getFormBean();
      boolean newFolder = (thisFolder.getId() == -1);
      if (newFolder) {
        thisFolder.setEnteredBy(getUserId(context));
      }
      thisFolder.setModifiedBy(getUserId(context));
      thisFolder.setLinkModuleId(Constants.DOCUMENTS_DOCUMENTS);
      thisFolder.setLinkItemId(thisDocumentStore.getId());
      if (newFolder) {
        if (!hasDocumentStoreAccess(
            context, db, thisDocumentStore, "documentcenter-documents-folders-add")) {
          return "PermissionError";
        }
        isValid = this.validateObject(context, db, thisFolder);
        if (isValid) {
          recordInserted = thisFolder.insert(db);
        }
        //indexAddItem(context, thisFolder);
      } else {
        if (!hasDocumentStoreAccess(
            context, db, thisDocumentStore, "documentcenter-documents-folders-edit")) {
          return "PermissionError";
        }
        isValid = this.validateObject(context, db, thisFolder);
        if (isValid) {
          resultCount = thisFolder.update(db);
        }
        //indexAddItem(context, thisFolder);
      }
      //Build array of folder trails
      DocumentStoreManagementFileFolders.buildHierarchy(db, context);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (recordInserted) {
      return ("InsertOK");
    } else if (resultCount == 1) {
      return ("UpdateOK");
    } else {
      return (executeCommandAdd(context));
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDelete(ActionContext context) {
    Exception errorMessage = null;
    boolean recordDeleted = false;
    String documentStoreId = (String) context.getRequest().getParameter(
        "documentStoreId");
    //Delete the itemId, and the folderId will be the location to return to
    String itemId = (String) context.getRequest().getParameter("id");
    String folderId = (String) context.getRequest().getParameter("folderId");
    Connection db = null;
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
          context, db, thisDocumentStore, "documentcenter-documents-folders-delete")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("documentStore", thisDocumentStore);
      context.getRequest().setAttribute("documentStoreId", documentStoreId);
      //Load the file folder
      FileFolder thisFolder = new FileFolder(db, Integer.parseInt(itemId));
      thisFolder.setParentId(Integer.parseInt(folderId));
      recordDeleted = thisFolder.delete(db);
      //indexDeleteItem(context, thisFolder);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
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
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDeleteRecursive(ActionContext context) {
    Exception errorMessage = null;
    boolean recordDeleted = false;
    String documentStoreId = (String) context.getRequest().getParameter(
        "documentStoreId");
    //Delete the itemId, and the folderId will be the location to return to
    String itemId = (String) context.getRequest().getParameter("id");
    String folderId = (String) context.getRequest().getParameter("folderId");
    Connection db = null;
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
          context, db, thisDocumentStore, "documentcenter-documents-folders-delete")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("documentStore", thisDocumentStore);
      context.getRequest().setAttribute("documentStoreId", documentStoreId);
      //Load the file folder
      FileFolder thisFolder = new FileFolder(db, Integer.parseInt(itemId));
      //Delete then indexed items for files in this folder
      this.indexDeleteItem(context, thisFolder);
      
      //Load all the sub folder
      FileFolderHierarchy thisFolderHierarchy = new FileFolderHierarchy();
      thisFolderHierarchy.setLinkModuleId(Constants.DOCUMENTS_DOCUMENTS);
      thisFolderHierarchy.setLinkItemId(Integer.parseInt(documentStoreId));
      //build folder hierarchy
      thisFolderHierarchy.build(db, Integer.parseInt(itemId));
      FileFolderList hierarchy = thisFolderHierarchy.getHierarchy();
      Iterator itr = hierarchy.iterator();
      //Deleting index entries
      while (itr.hasNext()) {
        FileFolder tmpFolder = (FileFolder) itr.next();
        indexDeleteItem(context, tmpFolder);
      }
      //Deleting sub folders and files
      recordDeleted = thisFolderHierarchy.deleteFolderHierarchy(
          db, "documents", Integer.parseInt(itemId));
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
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
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandModify(ActionContext context) {
    //TODO: Add some permissions to get here!
    //Modify the itemId, and the folderId will be the location to return to
    String itemId = (String) context.getRequest().getParameter("id");
    String folderId = (String) context.getRequest().getParameter("folderId");
    Connection db = null;
    try {
      db = getConnection(context);
      //Load the file folder to be modified
      FileFolder thisFolder = (FileFolder) context.getFormBean();
      thisFolder.setId(Integer.parseInt(itemId));
      thisFolder.queryRecord(db, Integer.parseInt(itemId));
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return (executeCommandAdd(context));
  }


  /**
   * Description of the Method
   *
   * @param db      Description of the Parameter
   * @param context Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public final static void buildHierarchy(Connection db, ActionContext context) throws SQLException {
    String folderId = context.getRequest().getParameter("folderId");
    if (folderId == null) {
      folderId = (String) context.getRequest().getAttribute("folderId");
    }
    if (folderId != null && !"-1".equals(folderId) && !"0".equals(folderId)) {
      LinkedHashMap folderLevels = new LinkedHashMap();
      FileFolder.buildHierarchy(db, folderLevels, Integer.parseInt(folderId));
      context.getRequest().setAttribute("folderLevels", folderLevels);
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
    String itemId = (String) context.getRequest().getParameter("id");
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
          context, db, thisDocumentStore, "documentcenter-documents-folders-edit")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("documentStore", thisDocumentStore);
      //Load the folder
      FileFolder thisFolder = new FileFolder(db, Integer.parseInt(itemId));
      context.getRequest().setAttribute("FileFolder", thisFolder);
      //Load the current folders
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
    String itemId = (String) context.getRequest().getParameter("id");
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
          context, db, thisDocumentStore, "documentcenter-documents-folders-edit")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("documentStore", thisDocumentStore);
      context.getRequest().setAttribute("return", "DocumentsFiles");
      //Load the current folder
      FileFolder thisFolder = new FileFolder(db, Integer.parseInt(itemId));
      int folderId = Integer.parseInt(newFolderId);
      if (folderId != 0 && folderId != -1) {
        FileFolder newParent = new FileFolder(db, folderId);
        FileFolderHierarchy thisHierarchy = new FileFolderHierarchy();
        thisHierarchy.setLinkModuleId(Constants.DOCUMENTS_DOCUMENTS);
        thisHierarchy.setLinkItemId(thisDocumentStore.getId());
        thisHierarchy.build(db, thisFolder.getId());
        if (thisHierarchy.getHierarchy().hasFolder(
            Integer.parseInt(newFolderId))) {
          thisFolder.buildSubFolders(db);
          Iterator iterator = (Iterator) thisFolder.getSubFolders().iterator();
          while (iterator.hasNext()) {
            FileFolder childFolder = (FileFolder) iterator.next();
            childFolder.updateParentId(db, thisFolder.getParentId());
          }
        }
      }
      thisFolder.updateParentId(db, folderId);
      return "PopupCloseOK";
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
  }
}

