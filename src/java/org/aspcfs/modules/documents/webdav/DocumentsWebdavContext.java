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
package org.aspcfs.modules.documents.webdav;

import com.zeroio.webdav.context.BaseWebdavContext;
import com.zeroio.webdav.context.ItemContext;
import com.zeroio.webdav.context.ModuleContext;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.utils.DatabaseUtils;

import javax.naming.NamingException;
import java.io.FileNotFoundException;
import java.sql.*;


/**
 * Description of the Class
 *
 * @author
 * @version $Id: DocumentsWebdavContext.java,v 1.2.6.1 2005/05/10 18:01:53
 *          ananth Exp $
 * @created
 */
public class DocumentsWebdavContext
    extends BaseWebdavContext implements ModuleContext {

  private final static String DOCUMENTS = "documents";
  private int linkModuleId = Constants.DOCUMENTS_DOCUMENTS;
  private int userId = -1;
  private String fileLibraryPath = null;
  private String permission = "documents-view";


  /**
   * Constructor for the DocumentsWebdavContext object
   */
  public DocumentsWebdavContext() {
  }


  /**
   * Constructor for the DocumentsWebdavContext object
   *
   * @param name         Description of the Parameter
   * @param linkModuleId Description of the Parameter
   */
  public DocumentsWebdavContext(String name, int linkModuleId) {
    this.contextName = name;
    this.linkModuleId = linkModuleId;
  }


  /**
   * Sets the userId attribute of the DocumentsWebdavContext object
   *
   * @param tmp The new userId value
   */
  public void setUserId(int tmp) {
    this.userId = tmp;
  }


  /**
   * Sets the userId attribute of the DocumentsWebdavContext object
   *
   * @param tmp The new userId value
   */
  public void setUserId(String tmp) {
    this.userId = Integer.parseInt(tmp);
  }


  /**
   * Gets the userId attribute of the DocumentsWebdavContext object
   *
   * @return The userId value
   */
  public int getUserId() {
    return userId;
  }


  /**
   * Sets the linkModuleId attribute of the DocumentsWebdavContext object
   *
   * @param tmp The new linkModuleId value
   */
  public void setLinkModuleId(int tmp) {
    this.linkModuleId = tmp;
  }


  /**
   * Sets the linkModuleId attribute of the DocumentsWebdavContext object
   *
   * @param tmp The new linkModuleId value
   */
  public void setLinkModuleId(String tmp) {
    this.linkModuleId = Integer.parseInt(tmp);
  }


  /**
   * Sets the fileLibraryPath attribute of the DocumentsWebdavContext object
   *
   * @param tmp The new fileLibraryPath value
   */
  public void setFileLibraryPath(String tmp) {
    this.fileLibraryPath = tmp;
  }


  /**
   * Sets the permission attribute of the DocumentsWebdavContext object
   *
   * @param tmp The new permission value
   */
  public void setPermission(String tmp) {
    this.permission = tmp;
  }


  /**
   * Gets the linkModuleId attribute of the DocumentsWebdavContext object
   *
   * @return The linkModuleId value
   */
  public int getLinkModuleId() {
    return linkModuleId;
  }


  /**
   * Gets the fileLibraryPath attribute of the DocumentsWebdavContext object
   *
   * @return The fileLibraryPath value
   */
  public String getFileLibraryPath() {
    return fileLibraryPath;
  }


  /**
   * Gets the permission attribute of the DocumentsWebdavContext object
   *
   * @return The permission value
   */
  public String getPermission() {
    return permission;
  }


  /**
   * Description of the Method
   *
   * @param thisSystem      Description of the Parameter
   * @param db              Description of the Parameter
   * @param userId          Description of the Parameter
   * @param fileLibraryPath Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildResources(SystemStatus thisSystem, Connection db, int userId, String fileLibraryPath) throws SQLException {
    this.fileLibraryPath = fileLibraryPath;
    this.userId = userId;
    bindings.clear();
    if (hasPermission(thisSystem, userId, "documents-view")) {
      populateBindings(db);
    }
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void populateBindings(Connection db) throws SQLException {
    if (linkModuleId == -1) {
      throw new SQLException("Module ID not specified");
    }

    User user = new User(db, userId);
    Contact contact = new Contact(db, user.getContactId());

    PreparedStatement pst = db.prepareStatement(
        "SELECT document_store_id, title, entered, modified " +
        "FROM document_store " +
        "WHERE document_store_id > -1 " +
        "AND ((document_store_id in (SELECT DISTINCT document_store_id FROM document_store_user_member WHERE item_id = ? )) " +
        "OR (document_store_id in (SELECT DISTINCT document_store_id FROM document_store_role_member WHERE item_id = ? )) " +
        "OR (document_store_id in (SELECT DISTINCT document_store_id FROM " + DatabaseUtils.getTableName(db, "document_store_department_member") + " WHERE item_id = ? ))) " +
        "AND trashed_date IS NULL ");
    int i = 0;
    pst.setInt(++i, userId);
    pst.setInt(++i, user.getRoleId());
    pst.setInt(++i, contact.getDepartment());
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      ItemContext item = new ItemContext();
      item.setContextName(rs.getString("title"));
      item.setLinkModuleId(linkModuleId);
      item.setLinkItemId(rs.getInt("document_store_id"));
      item.setPath(fileLibraryPath + DOCUMENTS + fs);
      item.setUserId(userId);
      item.setPermission("documentcenter-documents");
      bindings.put(item.getContextName(), item);
      Timestamp entered = rs.getTimestamp("entered");
      Timestamp modified = rs.getTimestamp("modified");
      buildProperties(
          item.getContextName(), entered, modified, new Integer(0));
    }
    rs.close();
    pst.close();
  }


  /**
   * Description of the Method
   *
   * @param thisSystem Description of the Parameter
   * @param db         Description of the Parameter
   * @param folderName Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException          Description of the Exception
   * @throws FileNotFoundException Description of the Exception
   * @throws NamingException       Description of the Exception
   */
  public boolean createSubcontext(SystemStatus thisSystem, Connection db, String folderName) throws SQLException,
      FileNotFoundException, NamingException {
    //Not allowed
    return false;
  }

}

