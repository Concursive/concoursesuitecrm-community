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
package org.aspcfs.modules.documents.search;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.documents.base.DocumentStorePermissionList;
import org.aspcfs.modules.documents.beans.DocumentsSearchBean;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Description of the Class
 *
 * @author kbhoopal
 * @version $Id$
 * @created December 6, 2004
 */
public class DocumentsSearchQuery {

  /**
   * Description of the Method
   *
   * @param context                 Description of the Parameter
   * @param search                  Description of the Parameter
   * @param db                      Description of the Parameter
   * @param userId                  Description of the Parameter
   * @param specificDocumentStoreId Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public static String buildDocumentStoreSearchQuery(ActionContext context, DocumentsSearchBean search, Connection db, int userId, int specificDocumentStoreId) throws SQLException {
    // get the document stores for the user
    // get the permissions for each document store
    // if user has access to the data, then add to query
    HashMap documentStoreListForUser = new HashMap();
    PreparedStatement pst = db.prepareStatement(
        "SELECT document_store_id, userlevel " +
        "FROM document_store_user_member " +
        "WHERE item_id = ? " +
        (specificDocumentStoreId > -1 ? "AND document_store_id = ? " : ""));
    pst.setInt(1, userId);
    if (specificDocumentStoreId > -1) {
      pst.setInt(2, specificDocumentStoreId);
    }
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      int documentStoreId = rs.getInt("document_store_id");
      int roleId = rs.getInt("userlevel");
      documentStoreListForUser.put(
          new Integer(documentStoreId), new Integer(roleId));
    }
    rs.close();
    pst.close();

    // get the document stores for the user's role
    // get the permissions for each document store
    // if role has access to the data, then add to query
    HashMap documentStoreListForGroup = new HashMap();
    UserBean tmpUser = (UserBean) context.getRequest().getSession().getAttribute(
        "User");
    //User tmpUser = getUser(context, userId);
    int tmpUserRoleId = tmpUser.getRoleId();
    pst = db.prepareStatement(
        "SELECT document_store_id, userlevel " +
        "FROM document_store_role_member " +
        "WHERE item_id = ? " +
        (specificDocumentStoreId > -1 ? "AND document_store_id = ? " : ""));
    pst.setInt(1, tmpUserRoleId);
    if (specificDocumentStoreId > -1) {
      pst.setInt(2, specificDocumentStoreId);
    }
    rs = pst.executeQuery();
    while (rs.next()) {
      int documentStoreId = rs.getInt("document_store_id");
      int roleId = rs.getInt("userlevel");
      documentStoreListForGroup.put(
          new Integer(documentStoreId), new Integer(roleId));
    }
    rs.close();
    pst.close();
    // get the document stores for the user's department
    // get the permissions for each document store
    // if department has access to the data, then add to query
    Contact tmpContact = new Contact(db, tmpUser.getContact().getId());
    int tmpDepartmentId = tmpContact.getDepartment();
    pst = db.prepareStatement(
        "SELECT document_store_id, userlevel " +
        "FROM " + DatabaseUtils.getTableName(db, "document_store_department_member") + " " +
        "WHERE item_id = ? " +
        (specificDocumentStoreId > -1 ? "AND document_store_id = ? " : ""));
    pst.setInt(1, tmpDepartmentId);
    if (specificDocumentStoreId > -1) {
      pst.setInt(2, specificDocumentStoreId);
    }
    rs = pst.executeQuery();
    while (rs.next()) {
      int documentStoreId = rs.getInt("document_store_id");
      int roleId = rs.getInt("userlevel");
      if (!documentStoreListForGroup.containsKey(new Integer(documentStoreId))) {
        documentStoreListForGroup.put(
            new Integer(documentStoreId), new Integer(roleId));
      } else {
        int tmpUserLevel = ((Integer) documentStoreListForGroup.get(
            new Integer(documentStoreId))).intValue();
        if (tmpUserLevel > roleId) {
          documentStoreListForGroup.put(
              new Integer(documentStoreId), new Integer(roleId));
        }
      }
    }
    rs.close();
    pst.close();

    //prepare consolidated hashmap of userlevels
    HashMap documentStoreList = new HashMap();
    //copy userlevel assignements for groups (roles, departments)
    documentStoreList.putAll(documentStoreListForGroup);
    //overwrite userlevel assignements with that assigned explictly for individuals
    documentStoreList.putAll(documentStoreListForUser);

    // build query string
    StringBuffer documentStoreBuffer = new StringBuffer();
    // scan for permissions
    Iterator documentStores = documentStoreList.keySet().iterator();
    while (documentStores.hasNext()) {
      StringBuffer permissionBuffer = new StringBuffer();
      Integer documentStoreId = (Integer) documentStores.next();
      Integer roleId = (Integer) documentStoreList.get(documentStoreId);
      // for each project check the available user permissions
      DocumentStorePermissionList permissionList = new DocumentStorePermissionList();
      permissionList.setDocumentStoreId(documentStoreId.intValue());
      permissionList.buildList(db);

      if (search.getSection() == DocumentsSearchBean.DETAILS || search.getSection() == DocumentsSearchBean.UNDEFINED) {
        // Check for project permissions
        if (permissionList.getAccessLevel("documentcenter-details-view") >= roleId.intValue()) {
          if (permissionBuffer.length() > 0) {
            permissionBuffer.append(" OR ");
          }
          permissionBuffer.append("type:documentstoredetails");
        }
      }
      if (search.getSection() == DocumentsSearchBean.DOCUMENTS || search.getSection() == DocumentsSearchBean.UNDEFINED) {
        // Check for file item permissions
        if (permissionList.getAccessLevel("documentcenter-documents-view") >= roleId.intValue()) {
          if (permissionBuffer.length() > 0) {
            permissionBuffer.append(" OR ");
          }
          permissionBuffer.append("type:file");
        }
      }
      // piece together
      if (permissionBuffer.length() > 0) {
        if (documentStoreBuffer.length() > 0) {
          documentStoreBuffer.append(" OR ");
        }
        documentStoreBuffer.append(
            "(documentStoreId:" + documentStoreId.intValue() + " AND (" + permissionBuffer.toString() + ")) ");
      }
      // debuging
      if (System.getProperty("DEBUG") != null) {
        if (permissionBuffer.length() == 0) {
          System.out.println(
              "NO PERMISSIONS FOR DOCUMENT STORE: " + documentStoreId.intValue());
        }
      }
    }
    // user does not have any document stores, so lock them into a non-existent project
    // for security
    if (documentStoreBuffer.length() == 0) {
      return "documentStoreId:-1";
    } else {
      return documentStoreBuffer.toString();
    }
  }
}

