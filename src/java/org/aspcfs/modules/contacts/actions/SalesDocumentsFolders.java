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
package org.aspcfs.modules.contacts.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import com.zeroio.iteam.actions.ProjectManagementFileFolders;
import com.zeroio.iteam.base.FileFolder;
import com.zeroio.iteam.base.FileFolderHierarchy;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.contacts.base.Contact;

import java.sql.Connection;
import java.util.Iterator;

/**
 * Description of the Class
 *
 * @author vadim.vishnevsky@corratech.com
 * @version $Id: ExternalContactsDocumentsFolders.java ,v 1.0 2006/12/11  vadim.vishnevsky@corratech.com
 */
public final class SalesDocumentsFolders extends CFSModule {

  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandAdd(ActionContext context) {
    if (!hasPermission(context, "sales-leads-documents-add")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    //Load project
    String contactId = (String) context.getRequest().getParameter("contactId");
    String from = (String) context.getRequest().getParameter("from");
    context.getRequest().setAttribute("from", from);
    String listForm = (String) context.getRequest().getParameter("listForm");
    if (listForm != null && !"".equals(listForm)) {
      context.getRequest().setAttribute("listForm", listForm);
    }
    Connection db = null;
    try {
      FileFolder thisFolder = (FileFolder) context.getFormBean();
      thisFolder.setParentId(context.getRequest().getParameter("parentId"));
      db = getConnection(context);
      if (!isRecordAccessPermitted(context, db, Integer.parseInt(contactId))) {
        return ("PermissionError");
      }
      //Load the project
      Contact contactDetails = new Contact(db, Integer.parseInt(contactId));
      context.getRequest().setAttribute("ContactDetails", contactDetails);
      //Build array of folder trails
      ProjectManagementFileFolders.buildHierarchy(db, context);
      context.getRequest().setAttribute("fileFolder", thisFolder);
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
    if (!hasPermission(context, "sales-leads-documents-edit")) {
      return ("PermissionError");
    }
    String from = (String) context.getRequest().getParameter("from");
    context.getRequest().setAttribute("from", from);
    String listForm = (String) context.getRequest().getParameter("listForm");
    if (listForm != null && !"".equals(listForm)) {
      context.getRequest().setAttribute("listForm", listForm);
    }
    Connection db = null;
    int resultCount = 0;
    boolean recordInserted = false;
    boolean isValid = false;
    String contactId = (String) context.getRequest().getParameter("contactId");
    Contact contactDetails = null;
    try {
      db = this.getConnection(context);
      if (!isRecordAccessPermitted(context, db, Integer.parseInt(contactId))) {
        return ("PermissionError");
      }
      contactDetails = new Contact(db, Integer.parseInt(contactId));
      //Insert or update the folder
      FileFolder thisFolder = (FileFolder) context.getFormBean();
      boolean newFolder = (thisFolder.getId() == -1);
      if (newFolder) {
        thisFolder.setEnteredBy(getUserId(context));
      }
      thisFolder.setModifiedBy(getUserId(context));
      thisFolder.setLinkModuleId(Constants.CONTACTS);
      thisFolder.setLinkItemId(contactDetails.getId());
      isValid = this.validateObject(context, db, thisFolder);
      if (newFolder) {
        if (isValid) {
          recordInserted = thisFolder.insert(db);
        }
      } else {
        if (isValid) {
          resultCount = thisFolder.update(db);
        }
      }
      //Build array of folder trails
      ProjectManagementFileFolders.buildHierarchy(db, context);
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
    }
    return (executeCommandAdd(context));
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDelete(ActionContext context) {
    if (!hasPermission(context, "sales-leads-documents-delete")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    boolean recordDeleted = false;
    String contactId = (String) context.getRequest().getParameter("contactId");
    //Delete the itemId, and the folderId will be the location to return to
    String itemId = (String) context.getRequest().getParameter("id");
    String folderId = (String) context.getRequest().getParameter("folderId");
    Contact contactDetails = null;
    String from = (String) context.getRequest().getParameter("from");
    context.getRequest().setAttribute("from", from);
    String listForm = (String) context.getRequest().getParameter("listForm");
    if (listForm != null && !"".equals(listForm)) {
      context.getRequest().setAttribute("listForm", listForm);
    }
    Connection db = null;
    try {
      db = getConnection(context);
      if (!isRecordAccessPermitted(context, db, Integer.parseInt(contactId))) {
        return ("PermissionError");
      }
      //Load the project
      contactDetails = new Contact(db, Integer.parseInt(contactId));
      context.getRequest().setAttribute("ContactDetails", contactDetails);
      //Load the file folder
      FileFolder thisFolder = new FileFolder(db, Integer.parseInt(itemId));
//      thisFolder.setParentId(Integer.parseInt(folderId));
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
  public String executeCommandModify(ActionContext context) {
    if (!hasPermission(context, "sales-leads-documents-edit")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    boolean recordDeleted = false;
    String contactId = (String) context.getRequest().getParameter("contactId");
    //TODO: Add some permissions to get here!
    //Modify the itemId, and the folderId will be the location to return to
    String itemId = (String) context.getRequest().getParameter("id");
    String folderId = (String) context.getRequest().getParameter("folderId");
    String from = (String) context.getRequest().getParameter("from");
    context.getRequest().setAttribute("from", from);
    String listForm = (String) context.getRequest().getParameter("listForm");
    if (listForm != null && !"".equals(listForm)) {
      context.getRequest().setAttribute("listForm", listForm);
    }
    Connection db = null;
    try {
      db = getConnection(context);
      if (!isRecordAccessPermitted(context, db, Integer.parseInt(contactId))) {
        return ("PermissionError");
      }
      //Load the file folder to be modified
      FileFolder thisFolder = (FileFolder) context.getFormBean();
      thisFolder.setId(Integer.parseInt(itemId));
      thisFolder.queryRecord(db, Integer.parseInt(itemId));
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      return (executeCommandAdd(context));
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
  public String executeCommandMove(ActionContext context) {
    if (!hasPermission(context, "sales-leads-documents-edit")) {
      return ("PermissionError");
    }
    //Parameters
    String contactId = (String) context.getRequest().getParameter("contactId");
    String itemId = (String) context.getRequest().getParameter("id");
    Contact contactDetails = null;
    String from = (String) context.getRequest().getParameter("from");
    context.getRequest().setAttribute("from", from);
    String listForm = (String) context.getRequest().getParameter("listForm");
    if (listForm != null && !"".equals(listForm)) {
      context.getRequest().setAttribute("listForm", listForm);
    }
    Connection db = null;
    try {
      db = getConnection(context);
      if (!isRecordAccessPermitted(context, db, Integer.parseInt(contactId))) {
        return ("PermissionError");
      }
      //Load the Contact
      contactDetails = new Contact(db, Integer.parseInt(contactId));
      context.getRequest().setAttribute("ContactDetails", contactDetails);
      //Load the folder
      FileFolder thisFolder = new FileFolder(db, Integer.parseInt(itemId));
      context.getRequest().setAttribute("FileFolder", thisFolder);
      //Load the current folders
      FileFolderHierarchy hierarchy = new FileFolderHierarchy();
      hierarchy.setLinkModuleId(Constants.CONTACTS);
      hierarchy.setLinkItemId(contactDetails.getId());
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
    if (!hasPermission(context, "sales-leads-documents-edit")) {
      return ("PermissionError");
    }
    //Parameters
    String contactId = (String) context.getRequest().getParameter("contactId");
    String newFolderId = (String) context.getRequest().getParameter(
        "folderId");
    String itemId = (String) context.getRequest().getParameter("id");
    Contact contactDetails = null;
    String from = (String) context.getRequest().getParameter("from");
    context.getRequest().setAttribute("from", from);
    String listForm = (String) context.getRequest().getParameter("listForm");
    if (listForm != null && !"".equals(listForm)) {
      context.getRequest().setAttribute("listForm", listForm);
    }
    Connection db = null;
    try {
      db = getConnection(context);
      if (!isRecordAccessPermitted(context, db, Integer.parseInt(contactId))) {
        return ("PermissionError");
      }
      //Load the Contact
      contactDetails = new Contact(db, Integer.parseInt(contactId));
      context.getRequest().setAttribute("ContactDetails", contactDetails);
      //Load the current folder
      FileFolder thisFolder = new FileFolder(db, Integer.parseInt(itemId));
      int folderId = Integer.parseInt(newFolderId);
      if (folderId != 0 && folderId != -1) {
        FileFolder newParent = new FileFolder(db, folderId);
        FileFolderHierarchy thisHierarchy = new FileFolderHierarchy();
        thisHierarchy.setLinkModuleId(Constants.DOCUMENTS_CONTACTS);
        thisHierarchy.setLinkItemId(contactDetails.getId());
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

