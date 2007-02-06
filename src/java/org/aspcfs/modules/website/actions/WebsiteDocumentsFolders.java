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
import com.zeroio.iteam.base.FileFolder;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.documents.actions.DocumentStoreManagementFileFolders;
import org.aspcfs.modules.documents.base.DocumentStore;

import java.sql.Connection;

/**
 * Description of the Class
 *
 * @author Olga.Kaptyug
 * @version $Id:  $
 * @created Jan 24, 2007
 */
public final class WebsiteDocumentsFolders extends CFSModule {

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
      context.getRequest().setAttribute("fileFolder", thisFolder);
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
}

