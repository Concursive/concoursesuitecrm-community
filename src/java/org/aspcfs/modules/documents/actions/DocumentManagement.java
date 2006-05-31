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
package org.aspcfs.modules.documents.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import com.zeroio.iteam.base.FileFolderList;
import com.zeroio.iteam.base.FileItemList;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.Role;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.documents.base.*;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.util.Iterator;

/**
 * Docuement Management module
 *
 * @author
 * @version $Id: DocumentManagement.java,v 1.2 2005/04/13 20:04:34 mrajkowski
 *          Exp $
 * @created November 6, 2001
 */
public final class DocumentManagement extends CFSModule {

  /**
   * Show the Document Store List by default
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandDefault(ActionContext context) {
    //return "DocumentsOK";
    return executeCommandEnterpriseView(context);
  }


  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandEnterpriseView(ActionContext context) {
    if (getUserId(context) < 0) {
      return "PermissionError";
    }
    if (!(hasPermission(context, "documents-view"))) {
      return ("PermissionError");
    }
    Connection db = null;
    DocumentStoreList documentStoreList = new DocumentStoreList();
    PagedListInfo documentStoreListInfo = this.getPagedListInfo(
        context, "documentStoreListInfo");
    documentStoreListInfo.setLink(
        "DocumentManagement.do?command=EnterpriseView");
    if (documentStoreListInfo.getListView() == null) {
      //My Open Document Stores
      documentStoreListInfo.setListView("Open");
    }
    if (documentStoreListInfo.getListView().equals("Open")) {
      documentStoreList.setOpenDocumentStoresOnly(true);
    } else if (documentStoreListInfo.getListView().equals("Archived")) {
      documentStoreList.setClosedDocumentStoresOnly(true);
    } else if (documentStoreListInfo.getListView().equals("Trashed")) {
      documentStoreList.setIncludeOnlyTrashed(true);
    }
    try {
      db = getConnection(context);
      int tmpUserId = this.getUserId(context);
      User tmpUser = getUser(context, tmpUserId);
      int tmpUserRoleId = tmpUser.getRoleId();
      Contact tmpContact = new Contact(db, tmpUser.getContactId());
      int tmpDepartmentId = tmpContact.getDepartment();
      documentStoreList.setDocumentStoresForUser(getUserId(context));
      documentStoreList.setUserRole(tmpUserRoleId);
      documentStoreList.setDepartmentId(tmpDepartmentId);
      documentStoreList.setSiteId(tmpUser.getSiteId());
      documentStoreList.setPagedListInfo(documentStoreListInfo);
      documentStoreList.buildList(db);
      context.getRequest().setAttribute(
          "documentStoreList", documentStoreList);
      //cache permissions
      getDocumentStoreUserLevel(context, db, DocumentStoreTeamMember.GUEST);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("EnterpriseViewOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandAddDocumentStore(ActionContext context) {
    if (getUserId(context) < 0) {
      return "PermissionError";
    }
    if (!(hasPermission(context, "documents_documentstore-add"))) {
      return ("PermissionError");
    }
    try {
      DocumentStore thisDocumentStore = (DocumentStore) context.getFormBean();
      if (thisDocumentStore.getRequestDate() == null) {
        thisDocumentStore.setRequestDate(
            DateUtils.roundUpToNextFive(System.currentTimeMillis()));
      }
      context.getRequest().setAttribute("documentStore", thisDocumentStore);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
    }
    return ("AddDocumentStoreOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandInsertDocumentStore(ActionContext context) {
    if (getUserId(context) < 0) {
      return "PermissionError";
    }
    if (!(hasPermission(context, "documents_documentstore-add"))) {
      return ("PermissionError");
    }
    Connection db = null;
    boolean isValid = false;
    User user = this.getUser(context, this.getUserId(context));
    try {
      db = getConnection(context);
      DocumentStore thisDocumentStore = (DocumentStore) context.getFormBean();

      thisDocumentStore.setEnteredBy(this.getUserId(context));
      thisDocumentStore.setModifiedBy(this.getUserId(context));
      isValid = this.validateObject(context, db, thisDocumentStore);

      if (isValid) {
        thisDocumentStore.insert(db);
        updateDocumentStoreCache(
            context, thisDocumentStore.getId(), thisDocumentStore.getTitle());
        indexAddItem(context, thisDocumentStore);

        //Add the current user to the team TODO: Put in a transaction
        DocumentStoreTeamMember thisMember = new DocumentStoreTeamMember();
        thisMember.setDocumentStoreId(thisDocumentStore.getId());
        thisMember.setStatus(DocumentStoreTeamMember.STATUS_ADDED);
        thisMember.setItemId(this.getUserId(context));
        thisMember.setUserLevel(
            getDocumentStoreUserLevel(
                context, db, DocumentStoreTeamMember.DOCUMENTSTORE_MANAGER));
        thisMember.setSiteId(user.getSiteId());
        thisMember.setEnteredBy(this.getUserId(context));
        thisMember.setModifiedBy(this.getUserId(context));
        thisMember.insert(db, DocumentStoreTeamMemberList.USER);
        //Go to the document store
        context.getRequest().setAttribute(
            "documentStoreId", String.valueOf(thisDocumentStore.getId()));
        return (executeCommandDefault(context));
      } else {
        return (executeCommandAddDocumentStore(context));
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      freeConnection(context, db);
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandModifyDocumentStore(ActionContext context) {
    if (!(hasPermission(context, "documents_documentstore-edit"))) {
      return ("PermissionError");
    }
    Connection db = null;
    //Params
    String documentStoreId = (String) context.getRequest().getParameter(
        "documentStoreId");
    try {
      db = this.getConnection(context);
      DocumentStore thisDocumentStore = new DocumentStore(
          db, Integer.parseInt(documentStoreId));
      thisDocumentStore.buildPermissionList(db);
      if (!hasDocumentStoreAccess(
          context, db, thisDocumentStore, "documentcenter-details-edit")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("documentStore", thisDocumentStore);
      context.getRequest().setAttribute(
          "IncludeSection", ("modify_document_store").toLowerCase());
      //Category List
      //LookupList categoryList = new LookupList(db, "lookup_document_store_category");
      //categoryList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      //context.getRequest().setAttribute("categoryList", categoryList);
      return ("DocumentStoreCenterOK");
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
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
  public String executeCommandConfigurePermissions(ActionContext context) {
    Connection db = null;
    //Params
    String documentStoreId = (String) context.getRequest().getParameter(
        "documentStoreId");
    try {
      db = this.getConnection(context);
      DocumentStore thisDocumentStore = new DocumentStore(
          db, Integer.parseInt(documentStoreId));
      thisDocumentStore.buildPermissionList(db);
      if (!hasDocumentStoreAccess(
          context, db, thisDocumentStore, "documentcenter-setup-permissions")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("documentStore", thisDocumentStore);
      context.getRequest().setAttribute(
          "IncludeSection", ("setup_permissions").toLowerCase());
      //Load the possible permission categories and permissions
      DocumentStorePermissionCategoryLookupList categories = new DocumentStorePermissionCategoryLookupList();
      categories.setIncludeEnabled(Constants.TRUE);
      categories.buildList(db);
      categories.buildResources(db);
      context.getRequest().setAttribute("categories", categories);
      return ("DocumentStoreCenterOK");
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
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
  public String executeCommandUpdatePermissions(ActionContext context) {
    Connection db = null;
    String documentStoreId = (String) context.getRequest().getParameter(
        "documentStoreId");
    try {
      db = this.getConnection(context);
      DocumentStore thisDocumentStore = new DocumentStore(
          db, Integer.parseInt(documentStoreId));
      thisDocumentStore.buildPermissionList(db);
      //Make sure user can modify permissions
      if (!hasDocumentStoreAccess(
          context, db, thisDocumentStore, "documentcenter-setup-permissions")) {
        return "PermissionError";
      }
      DocumentStorePermissionList.updateDocumentStorePermissions(
          db, context.getRequest(), Integer.parseInt(documentStoreId));
      return "UpdatePermissionsOK";
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandUpdateDocumentStore(ActionContext context) {
    if (!(hasPermission(context, "documents_documentstore-edit"))) {
      return ("PermissionError");
    }
    boolean isValid = false;
    DocumentStore thisDocumentStore = (DocumentStore) context.getFormBean();
    //thisDocumentStore.setRequestItems(context.getRequest());
    Connection db = null;
    int resultCount = 0;
    try {
      db = this.getConnection(context);
      thisDocumentStore.buildPermissionList(db);
      if (!hasDocumentStoreAccess(
          context, db, thisDocumentStore, "documentcenter-details-edit")) {
        return "PermissionError";
      }
      thisDocumentStore.setModifiedBy(this.getUserId(context));
      isValid = this.validateObject(context, db, thisDocumentStore);
      if (isValid) {
        resultCount = thisDocumentStore.update(db);
      }
      if (resultCount == 1) {
        updateDocumentStoreCache(
            context, thisDocumentStore.getId(), thisDocumentStore.getTitle());
        indexAddItem(context, thisDocumentStore);
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    //Results
    if (resultCount == -1 || !isValid) {
      context.getRequest().setAttribute("documentStore", thisDocumentStore);
      context.getRequest().setAttribute(
          "IncludeSection", ("modify_document_store").toLowerCase());
      return ("DocumentStoreCenterOK");
    } else if (resultCount == 1) {
      context.getRequest().setAttribute(
          "documentStoreId", "" + thisDocumentStore.getId());
      return ("UpdateDocumentStoreOK");
    } else {
      context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
      return ("UserError");
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDocumentStoreCenter(ActionContext context) {
    if (!(hasPermission(context, "documents_documentstore-view"))) {
      return ("PermissionError");
    }
    Connection db = null;
    DocumentStore thisDocumentStore = null;
    //Parameters
    String documentStoreId = (String) context.getRequest().getParameter(
        "documentStoreId");
    if (documentStoreId == null) {
      documentStoreId = (String) context.getRequest().getAttribute(
          "documentStoreId");
    }
    SystemStatus systemStatus = this.getSystemStatus(context);
    String section = (String) context.getRequest().getParameter("section");
    //Determine the section to display
    try {
      db = getConnection(context);
      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("SiteIdList", siteList);
      thisDocumentStore = new DocumentStore(
          db, Integer.parseInt(documentStoreId));
      thisDocumentStore.buildPermissionList(db);

      if (section == null || "".equals(section) || "File_Library".equals(
          section)) {
        section = "File_Library";
        if (!hasDocumentStoreAccess(
            context, db, thisDocumentStore, "documentcenter-documents-view")) {
          return "PermissionError";
        }
        String folderId = context.getRequest().getParameter("folderId");
        if (folderId == null) {
          folderId = (String) context.getRequest().getAttribute("folderId");
        }
        //Build the folder list
        FileFolderList folders = new FileFolderList();
        if (folderId == null || "-1".equals(folderId) || "0".equals(folderId)) {
          folders.setTopLevelOnly(true);
        } else {
          folders.setParentId(Integer.parseInt(folderId));
          //Build array of folder trails
          DocumentStoreManagementFileFolders.buildHierarchy(db, context);
        }
        folders.setLinkModuleId(Constants.DOCUMENTS_DOCUMENTS);
        folders.setLinkItemId(thisDocumentStore.getId());
        folders.setBuildItemCount(true);
        folders.buildList(db);
        context.getRequest().setAttribute("fileFolderList", folders);
        //Build the file item list
        FileItemList files = new FileItemList();
        if (folderId == null || "-1".equals(folderId) || "0".equals(folderId)) {
          files.setTopLevelOnly(true);
          //Reset the pagedListInfo
          deletePagedListInfo(context, "documentStoreDocumentsGalleryInfo");
        } else {
          files.setFolderId(Integer.parseInt(folderId));
        }
        files.setLinkModuleId(Constants.DOCUMENTS_DOCUMENTS);
        files.setLinkItemId(thisDocumentStore.getId());
        files.buildList(db);
        thisDocumentStore.setFiles(files);
      } else if ("Team".equals(section)) {
        if (!hasDocumentStoreAccess(
            context, db, thisDocumentStore, "documentcenter-team-view")) {
          return "PermissionError";
        }
        //Check the pagedList filter
        PagedListInfo documentStoreUserTeamInfo = this.getPagedListInfo(
            context, "documentStoreUserTeamInfo");
        documentStoreUserTeamInfo.setLink(
            "DocumentManagement.do?command=DocumentStoreCenter&section=Team&documentStoreId=" + thisDocumentStore.getId());
        documentStoreUserTeamInfo.setItemsPerPage(0);

        PagedListInfo documentStoreEmployeeTeamInfo = this.getPagedListInfo(
            context, "documentStoreEmployeeTeamInfo");
        documentStoreEmployeeTeamInfo.setLink(
            "DocumentManagement.do?command=DocumentStoreCenter&section=Team&documentStoreId=" + thisDocumentStore.getId());
        documentStoreEmployeeTeamInfo.setItemsPerPage(0);

        PagedListInfo documentStoreAccountContactTeamInfo = this.getPagedListInfo(
            context, "documentStoreAccountContactTeamInfo");
        documentStoreAccountContactTeamInfo.setLink(
            "DocumentManagement.do?command=DocumentStoreCenter&section=Team&documentStoreId=" + thisDocumentStore.getId());
        documentStoreAccountContactTeamInfo.setItemsPerPage(0);

        PagedListInfo documentStoreRoleTeamInfo = this.getPagedListInfo(
            context, "documentStoreRoleTeamInfo");
        documentStoreRoleTeamInfo.setLink(
            "DocumentManagement.do?command=DocumentStoreCenter&section=Team&documentStoreId=" + thisDocumentStore.getId());
        documentStoreRoleTeamInfo.setItemsPerPage(0);

        PagedListInfo documentStoreDepartmentTeamInfo = this.getPagedListInfo(
            context, "documentStoreDepartmentTeamInfo");
        documentStoreDepartmentTeamInfo.setLink(
            "DocumentManagement.do?command=DocumentStoreCenter&section=Team&documentStoreId=" + thisDocumentStore.getId());
        documentStoreDepartmentTeamInfo.setItemsPerPage(0);

        //Generate the list
        thisDocumentStore.getUserTeam().setPagedListInfo(
            documentStoreUserTeamInfo);
        thisDocumentStore.getEmployeeTeam().setPagedListInfo(
            documentStoreEmployeeTeamInfo);
        thisDocumentStore.getAccountContactTeam().setPagedListInfo(
            documentStoreAccountContactTeamInfo);
        thisDocumentStore.getRoleTeam().setPagedListInfo(
            documentStoreRoleTeamInfo);
        thisDocumentStore.getDepartmentTeam().setPagedListInfo(
            documentStoreDepartmentTeamInfo);
        thisDocumentStore.buildTeamMemberList(db);

        Iterator i = thisDocumentStore.getUserTeam().iterator();
        while (i.hasNext()) {
          DocumentStoreTeamMember thisMember = (DocumentStoreTeamMember) i.next();
          User thisUser = new User();
          thisUser.setBuildContact(true);
          thisUser.setBuildContactDetails(true);
          thisUser.buildRecord(db, thisMember.getItemId());
          thisMember.setUser(thisUser);
        }
        i = thisDocumentStore.getEmployeeTeam().iterator();
        while (i.hasNext()) {
          DocumentStoreTeamMember thisMember = (DocumentStoreTeamMember) i.next();
          User thisUser = new User();
          thisUser.setBuildContact(true);
          thisUser.setBuildContactDetails(true);
          thisUser.buildRecord(db, thisMember.getItemId());
          thisMember.setUser(thisUser);
        }
        i = thisDocumentStore.getAccountContactTeam().iterator();
        while (i.hasNext()) {
          DocumentStoreTeamMember thisMember = (DocumentStoreTeamMember) i.next();
          User thisUser = new User();
          thisUser.setBuildContact(true);
          thisUser.setBuildContactDetails(true);
          thisUser.buildRecord(db, thisMember.getItemId());
          thisMember.setUser(thisUser);
        }
        i = thisDocumentStore.getRoleTeam().iterator();
        while (i.hasNext()) {
          DocumentStoreTeamMember thisMember = (DocumentStoreTeamMember) i.next();
          Role thisRole = new Role(db, thisMember.getItemId());
          thisMember.setUser(thisRole.getRole());
        }
        i = thisDocumentStore.getDepartmentTeam().iterator();
        while (i.hasNext()) {
          DocumentStoreTeamMember thisMember = (DocumentStoreTeamMember) i.next();
          LookupList departmentList = new LookupList(db, "lookup_department");
          thisMember.setUser(
              departmentList.getValueFromId(thisMember.getItemId()));
        }

      } else if ("Details".equals(section)) {
        //Just looking at the details
        if (!hasDocumentStoreAccess(
            context, db, thisDocumentStore, "documentcenter-details-view")) {
          return "PermissionError";
        }
      } else if ("Setup".equals(section)) {
        if (!hasDocumentStoreAccess(
            context, db, thisDocumentStore, "documentcenter-setup-permissions")) {
          return "PermissionError";
        }
      }
      context.getRequest().setAttribute("documentStore", thisDocumentStore);
      context.getRequest().setAttribute(
          "IncludeSection", section.toLowerCase());
      //The user has access, so show that they accessed the Document Store
      User user = this.getUser(context, this.getUserId(context));
      DocumentStoreTeamMember.updateLastAccessed(
          db, thisDocumentStore.getId(), user.getId(), user.getSiteId());
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
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
  public String executeCommandDeleteDocumentStore(ActionContext context) {
    Connection db = null;
    //Params
    String documentStoreId = (String) context.getRequest().getParameter(
        "documentStoreId");
    try {
      db = this.getConnection(context);
      DocumentStore thisDocumentStore = new DocumentStore(
          db, Integer.parseInt(documentStoreId));
      thisDocumentStore.buildPermissionList(db);
      if (!hasDocumentStoreAccess(
          context, db, thisDocumentStore, "documentcenter-details-delete")) {
        return "PermissionError";
      }
      thisDocumentStore.delete(db, getDbNamePath(context));
      updateDocumentStoreCache(context, thisDocumentStore.getId(), null);

      indexDeleteItem(context, thisDocumentStore);

      return "DeleteDocumentStoreOK";
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
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
  public String executeCommandTrashDocumentStore(ActionContext context) {
    Connection db = null;
    //Params
    String documentStoreId = (String) context.getRequest().getParameter(
        "documentStoreId");
    try {
      db = this.getConnection(context);
      DocumentStore thisDocumentStore = new DocumentStore(
          db, Integer.parseInt(documentStoreId));
      thisDocumentStore.buildPermissionList(db);
      if (!hasDocumentStoreAccess(
          context, db, thisDocumentStore, "documentcenter-details-delete")) {
        return "PermissionError";
      }
      thisDocumentStore.updateStatus(db, true, this.getUserId(context));
      updateDocumentStoreCache(context, thisDocumentStore.getId(), null);
      indexDeleteItem(context, thisDocumentStore);

      return "DeleteDocumentStoreOK";
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
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
  public String executeCommandRestoreDocumentStore(ActionContext context) {
    Connection db = null;
    //Params
    String documentStoreId = (String) context.getRequest().getParameter(
        "documentStoreId");
    try {
      db = this.getConnection(context);
      DocumentStore thisDocumentStore = new DocumentStore(
          db, Integer.parseInt(documentStoreId));
      thisDocumentStore.buildPermissionList(db);
      if (!hasDocumentStoreAccess(
          context, db, thisDocumentStore, "documentcenter-details-delete")) {
        return "PermissionError";
      }
      thisDocumentStore.updateStatus(db, false, this.getUserId(context));
      thisDocumentStore.setTrashedDate((java.sql.Timestamp) null);
      updateDocumentStoreCache(context, thisDocumentStore.getId(), null);
      indexAddItem(context, thisDocumentStore);

      return "DeleteDocumentStoreOK";
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
  }

}

