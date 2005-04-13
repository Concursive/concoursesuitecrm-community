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
package org.aspcfs.modules.documents.base;

import java.sql.*;
import java.text.DateFormat;
import org.aspcfs.utils.DatabaseUtils;

/**
 *  Represents a member of a document store
 *
 *@author
 *@created
 *@version    $Id: DocumentStoreTeamMember.java,v 1.1.4.1.2.2 2004/12/08
 *      15:42:43 kbhoopal Exp $
 */
public class DocumentStoreTeamMember {
//Constants that control permissions within a document store
  public final static int DOCUMENTSTORE_MANAGER = 1;
  public final static int CONTRIBUTOR_LEVEL3 = 2;
  public final static int CONTRIBUTOR_LEVEL2 = 3;
  public final static int CONTRIBUTOR_LEVEL1 = 4;
  public final static int GUEST = 5;
  //Constants that control invitations
  public final static int STATUS_ADDED = -1;
  //References
  private DocumentStore documentStore = null;
  private Object contact = null;
  private Object user = null;
  //Team Member Properties
  private int documentStoreId = -1;
  private int itemId = -1;
  private int userLevel = -1;
  private java.sql.Timestamp entered = null;
  private int enteredBy = -1;
  private java.sql.Timestamp modified = null;
  private int modifiedBy = -1;
  private int roleId = -1;
  private int status = STATUS_ADDED;
  private java.sql.Timestamp lastAccessed = null;


  /**
   *  Constructor for the DoucmentStoreTeamMember object
   */
  public DocumentStoreTeamMember() { }


  /**
   *  Constructor for the DocumentStoreTeamMember object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public DocumentStoreTeamMember(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Constructor for the DocumentTeamMember object
   *
   *@param  db                  Description of the Parameter
   *@param  tmpDocumentStoreId  Description of the Parameter
   *@param  tmpTeamId           Description of the Parameter
   *@param  tmpRoleId           Description of the Parameter
   *@param  tmpDepartmentId     Description of the Parameter
   *@exception  SQLException    Description of the Exception
   */
  public DocumentStoreTeamMember(Connection db, int tmpDocumentStoreId, int tmpTeamId, int tmpRoleId, int tmpDepartmentId) throws SQLException {
    queryRecord(db, tmpDocumentStoreId, tmpTeamId, tmpRoleId, tmpDepartmentId);
  }


  /**
   *  Sets the documentStore attribute of the TeamMember object
   *
   *@param  tmp  The new documentStore value
   */
  public void setDocumentStore(DocumentStore tmp) {
    this.documentStore = tmp;
  }


  /**
   *  Sets the contact attribute of the TeamMember object
   *
   *@param  tmp  The new contact value
   */
  public void setContact(Object tmp) {
    this.contact = tmp;
  }


  /**
   *  Sets the user attribute of the TeamMember object
   *
   *@param  tmp  The new user value
   */
  public void setUser(Object tmp) {
    this.user = tmp;
  }


  /**
   *  Sets the documentStoreId attribute of the TeamMember object
   *
   *@param  tmp  The new documentStoreId value
   */
  public void setDocumentStoreId(int tmp) {
    this.documentStoreId = tmp;
  }


  /**
   *  Sets the documentStoreId attribute of the TeamMember object
   *
   *@param  tmp  The new documentStoreId value
   */
  public void setDocumentStoreId(String tmp) {
    this.documentStoreId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the itemId attribute of the TeamMember object
   *
   *@param  tmp  The new itemId value
   */
  public void setItemId(int tmp) {
    this.itemId = tmp;
  }


  /**
   *  Sets the itemId attribute of the TeamMember object
   *
   *@param  tmp  The new itemId value
   */
  public void setItemId(String tmp) {
    this.itemId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the userLevel attribute of the TeamMember object
   *
   *@param  tmp  The new userLevel value
   */
  public void setUserLevel(int tmp) {
    this.userLevel = tmp;
  }


  /**
   *  Sets the userLevel attribute of the TeamMember object
   *
   *@param  tmp  The new userLevel value
   */
  public void setUserLevel(String tmp) {
    this.userLevel = Integer.parseInt(tmp);
  }


  /**
   *  Sets the entered attribute of the TeamMember object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the entered attribute of the TeamMember object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the enteredBy attribute of the TeamMember object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the TeamMember object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the modified attribute of the TeamMember object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the modified attribute of the TeamMember object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the modifiedBy attribute of the TeamMember object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the TeamMember object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the roleId attribute of the TeamMember object
   *
   *@param  tmp  The new roleId value
   */
  public void setRoleId(int tmp) {
    this.roleId = tmp;
  }


  /**
   *  Sets the roleId attribute of the TeamMember object
   *
   *@param  tmp  The new roleId value
   */
  public void setRoleId(String tmp) {
    this.roleId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the status attribute of the TeamMember object
   *
   *@param  tmp  The new status value
   */
  public void setStatus(int tmp) {
    this.status = tmp;
  }


  /**
   *  Sets the status attribute of the TeamMember object
   *
   *@param  tmp  The new status value
   */
  public void setStatus(String tmp) {
    this.status = Integer.parseInt(tmp);
  }


  /**
   *  Sets the lastAccessed attribute of the TeamMember object
   *
   *@param  tmp  The new lastAccessed value
   */
  public void setLastAccessed(java.sql.Timestamp tmp) {
    this.lastAccessed = tmp;
  }


  /**
   *  Sets the lastAccessed attribute of the TeamMember object
   *
   *@param  tmp  The new lastAccessed value
   */
  public void setLastAccessed(String tmp) {
    this.lastAccessed = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Gets the documentStore attribute of the TeamMember object
   *
   *@return    The documentStore value
   */
  public DocumentStore getDocumentStore() {
    return documentStore;
  }


  /**
   *  Gets the contact attribute of the TeamMember object
   *
   *@return    The contact value
   */
  public Object getContact() {
    return contact;
  }


  /**
   *  Gets the user attribute of the TeamMember object
   *
   *@return    The user value
   */
  public Object getUser() {
    return user;
  }


  /**
   *  Gets the documentStoreId attribute of the TeamMember object
   *
   *@return    The documentStoreId value
   */
  public int getDocumentStoreId() {
    return documentStoreId;
  }


  /**
   *  Gets the itemId attribute of the TeamMember object
   *
   *@return    The itemId value
   */
  public int getItemId() {
    return itemId;
  }


  /**
   *  Gets the userLevel attribute of the TeamMember object
   *
   *@return    The userLevel value
   */
  public int getUserLevel() {
    return userLevel;
  }


  /**
   *  Gets the entered attribute of the TeamMember object
   *
   *@return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the enteredBy attribute of the TeamMember object
   *
   *@return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the modified attribute of the TeamMember object
   *
   *@return    The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the modifiedBy attribute of the TeamMember object
   *
   *@return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Gets the roleId attribute of the TeamMember object
   *
   *@return    The roleId value
   */
  public int getRoleId() {
    return roleId;
  }


  /**
   *  Gets the status attribute of the TeamMember object
   *
   *@return    The status value
   */
  public int getStatus() {
    return status;
  }


  /**
   *  Gets the lastAccessed attribute of the TeamMember object
   *
   *@return    The lastAccessed value
   */
  public java.sql.Timestamp getLastAccessed() {
    return lastAccessed;
  }



  /**
   *  Description of the Method
   *
   *@param  db                  Description of the Parameter
   *@param  tmpDocumentStoreId  Description of the Parameter
   *@param  tmpTeamId           Description of the Parameter
   *@param  tmpUserRoleId       Description of the Parameter
   *@param  tmpDepartmentId     Description of the Parameter
   *@exception  SQLException    Description of the Exception
   */
  private void queryRecord(Connection db, int tmpDocumentStoreId, int tmpTeamId, int tmpUserRoleId, int tmpDepartmentId) throws SQLException {

    //fetch the role explicitly assigned to the user for the document store
    PreparedStatement pst = null;
    int tmpDocumentStoreRoleId = -1;
    int tmpDocumentStoreUserLevel = -1;
    pst = db.prepareStatement(
        "SELECT m.*, r.level " +
        "FROM document_store_user_member m, lookup_document_store_role r " +
        "WHERE m.document_store_id = ? " +
        "AND m.item_id = ? " +
        "AND m.userlevel = r.code ");
    pst.setInt(1, tmpDocumentStoreId);
    pst.setInt(2, tmpTeamId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
      tmpDocumentStoreRoleId = this.getRoleId();
      tmpDocumentStoreUserLevel = this.getUserLevel();
    }
    rs.close();
    pst.close();
    if (tmpDocumentStoreRoleId == -1) {
      //get  User Role - Document store role mapping if user is not explicitly assigned
      //to the document store
      pst = db.prepareStatement(
          "SELECT m.*, r.level " +
          "FROM document_store_role_member m, lookup_document_store_role r " +
          "WHERE m.document_store_id = ? " +
          "AND m.item_id = ? " +
          "AND m.userlevel = r.code ");
      pst.setInt(1, tmpDocumentStoreId);
      pst.setInt(2, tmpUserRoleId);
      rs = pst.executeQuery();
      if (rs.next()) {
        buildRecord(rs);
        tmpDocumentStoreRoleId = this.getRoleId();
        tmpDocumentStoreUserLevel = this.getUserLevel();
      }
      rs.close();
      pst.close();

      //get  Department - Document store role mapping if user is not explicitly assigned
      //to the document store
      if (tmpDepartmentId != -1) {
        pst = db.prepareStatement(
            "SELECT m.*, r.level " +
            "FROM document_store_department_member m, lookup_document_store_role r " +
            "WHERE m.document_store_id = ? " +
            "AND m.item_id = ? " +
            "AND m.userlevel = r.code ");
        pst.setInt(1, tmpDocumentStoreId);
        pst.setInt(2, tmpDepartmentId);
        rs = pst.executeQuery();
        if (rs.next()) {
          buildRecord(rs);
        }
        rs.close();
        pst.close();
        //assign the higher of the roles (i.e., 1 is higher than 2!)
        if (tmpDocumentStoreRoleId > this.getRoleId()) {
          tmpDocumentStoreRoleId = this.getRoleId();
          tmpDocumentStoreUserLevel = this.getUserLevel();
        }
      }
    }
    if (tmpDocumentStoreRoleId == -1) {
      throw new SQLException("Member record not found.");
    }
    this.setRoleId(tmpDocumentStoreRoleId);
    this.setUserLevel(tmpDocumentStoreUserLevel);
  }


  /**
   *  Description of the Method
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  private void buildRecord(ResultSet rs) throws SQLException {
    this.documentStoreId = rs.getInt("document_store_id");
    this.itemId = rs.getInt("item_id");
    this.userLevel = rs.getInt("userlevel");
    this.status = DatabaseUtils.getInt(rs, "status");
    this.lastAccessed = rs.getTimestamp("last_accessed");
    this.entered = rs.getTimestamp("entered");
    this.enteredBy = rs.getInt("enteredby");
    this.modified = rs.getTimestamp("modified");
    this.modifiedBy = rs.getInt("modifiedby");

    //lookup_document_store_role
    roleId = rs.getInt("level");
  }


  /**
   *  Gets the lastAccessedString attribute of the TeamMember object
   *
   *@return    The lastAccessedString value
   */
  public String getLastAccessedString() {
    try {
      return DateFormat.getDateInstance(3).format(lastAccessed);
    } catch (NullPointerException e) {
    }
    return ("--");
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean insert(Connection db, String tmpMemberType) throws SQLException {
    StringBuffer sql = new StringBuffer();
    String tableName = DocumentStoreTeamMember.getTableName(tmpMemberType);
    sql.append("INSERT INTO " + tableName);
    sql.append(" (document_store_id, item_id, userlevel, ");
    if (entered != null) {
      sql.append("entered, ");
    }
    if (modified != null) {
      sql.append("modified, ");
    }
    if (lastAccessed != null) {
      sql.append("last_accessed, ");
    }
    sql.append("enteredby, modifiedby, status) ");
    sql.append("VALUES (?, ?, ?, ");
    if (entered != null) {
      sql.append("?, ");
    }
    if (modified != null) {
      sql.append("?, ");
    }
    if (lastAccessed != null) {
      sql.append("?, ");
    }
    sql.append("?, ?, ?) ");
    PreparedStatement pst = db.prepareStatement(sql.toString());
    int i = 0;
    pst.setInt(++i, documentStoreId);
    pst.setInt(++i, itemId);
    pst.setInt(++i, userLevel);
    if (entered != null) {
      pst.setTimestamp(++i, entered);
    }
    if (modified != null) {
      pst.setTimestamp(++i, modified);
    }
    if (lastAccessed != null) {
      pst.setTimestamp(++i, lastAccessed);
    }
    pst.setInt(++i, enteredBy);
    pst.setInt(++i, modifiedBy);
    pst.setInt(++i, status);
    pst.execute();
    pst.close();
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  tmpMemberType     Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void delete(Connection db, String tmpMemberType) throws SQLException {

    String tableName = DocumentStoreTeamMember.getTableName(tmpMemberType);
    if (tmpMemberType.equals(DocumentStoreTeamMemberList.ROLE)) {
      tableName = "document_store_role_member";
    } else if (tmpMemberType.equals(DocumentStoreTeamMemberList.DEPARTMENT)) {
      tableName = "document_store_department_member";
    }

    PreparedStatement pst = db.prepareStatement(
        " DELETE FROM " + tableName +
        " WHERE document_store_id = ? " +
        " AND item_id = ? ");
    pst.setInt(1, documentStoreId);
    pst.setInt(2, itemId);
    pst.execute();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   *@param  db                  Description of the Parameter
   *@param  tmpDocumentStoreId  Description of the Parameter
   *@param  tmpItemId           Description of the Parameter
   *@param  tmpUserLevel        Description of the Parameter
   *@param  tmpMemberType       Description of the Parameter
   *@return                     Description of the Return Value
   *@exception  SQLException    Description of the Exception
   */
  public static boolean changeRole(Connection db, int tmpDocumentStoreId, int tmpItemId, int tmpUserLevel, String tmpMemberType) throws SQLException {

    String tableName = DocumentStoreTeamMember.getTableName(tmpMemberType);

    //Check current level, if user is not a leader than it doesn't matter what the change is
    PreparedStatement pst = db.prepareStatement(
        " SELECT level " +
        " FROM lookup_document_store_role " +
        " WHERE code IN (SELECT userlevel FROM " + tableName + " WHERE document_store_id = ? AND item_id = ?) ");
    pst.setInt(1, tmpDocumentStoreId);
    pst.setInt(2, tmpItemId);
    ResultSet rs = pst.executeQuery();
    int previousLevel = -1;
    while (rs.next()) {
      previousLevel = rs.getInt("level");
      if (previousLevel <= DocumentStoreTeamMember.DOCUMENTSTORE_MANAGER) {
        break;
      }
    }
    rs.close();
    pst.close();
    if (previousLevel <= DocumentStoreTeamMember.DOCUMENTSTORE_MANAGER) {
      //Make sure that there is at least 1 other user with lead status before changing
      pst = db.prepareStatement(
          " SELECT count(item_id) AS other " +
          " FROM " + tableName +
          " WHERE document_store_id = ? " +
          " AND userlevel IN (SELECT code FROM lookup_document_store_role WHERE level <= ?) " +
          " AND item_id <> ? ");
      pst.setInt(1, tmpDocumentStoreId);
      pst.setInt(2, DocumentStoreTeamMember.DOCUMENTSTORE_MANAGER);
      pst.setInt(3, tmpItemId);
      rs = pst.executeQuery();
      int otherCount = -1;
      if (rs.next()) {
        otherCount = rs.getInt("other");
      }
      rs.close();
      pst.close();
      if (otherCount == 0) {
        return false;
      }
    }
    //Now update the team
    pst = db.prepareStatement(
        " UPDATE " + tableName +
        " SET userlevel = ? " +
        " WHERE document_store_id = ? " +
        " AND item_id = ? ");
    pst.setInt(1, tmpUserLevel);
    pst.setInt(2, tmpDocumentStoreId);
    pst.setInt(3, tmpItemId);
    pst.executeUpdate();
    pst.close();
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void updateStatus(Connection db, String tmpMemberType) throws SQLException {
    String tableName = DocumentStoreTeamMember.getTableName(tmpMemberType);
    PreparedStatement pst = db.prepareStatement(
        "UPDATE " + tableName + " " +
        "SET status = ?, modified = " + DatabaseUtils.getCurrentTimestamp(db) + " " +
        "WHERE document_store_id = ? " +
        "AND item_id = ? ");
    DatabaseUtils.setInt(pst, 1, status);
    pst.setInt(2, documentStoreId);
    pst.setInt(3, itemId);
    pst.execute();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   *@param  db                  Description of the Parameter
   *@param  tmpDocumentStoreId  Description of the Parameter
   *@param  tmpItemId           Description of the Parameter
   *@exception  SQLException    Description of the Exception
   */
  public static void updateLastAccessed(Connection db, int tmpDocumentStoreId, int tmpItemId) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "UPDATE document_store_user_member " +
        "SET last_accessed = " + DatabaseUtils.getCurrentTimestamp(db) + " " +
        "WHERE document_store_id = ? " +
        "AND item_id = ? ");
    pst.setInt(1, tmpDocumentStoreId);
    pst.setInt(2, tmpItemId);
    pst.execute();
    pst.close();
  }


  /**
   *  Gets the tableName attribute of the DocumentStoreTeamMember object
   *
   *@return    The tableName value
   */
  public static String getTableName(String tmpMemberType) {
    String tableName = "document_store_user_member";
    if (tmpMemberType.equals(DocumentStoreTeamMemberList.ROLE)) {
      tableName = "document_store_role_member";
    } else if (tmpMemberType.equals(DocumentStoreTeamMemberList.DEPARTMENT)) {
      tableName = "document_store_department_member";
    }
    return tableName;
  }
}

