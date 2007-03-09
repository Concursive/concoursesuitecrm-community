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

import com.darkhorseventures.framework.beans.GenericBean;
import com.zeroio.iteam.base.FileFolderList;
import com.zeroio.iteam.base.FileItemList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.*;
import java.util.ArrayList;

/**
 * Description of the Class
 *
 * @author kbhoopal
 * @version $Id: DocumentStore.java,v 1.1.2.1 2004/11/09 22:25:30 kbhoopal Exp
 *          $
 * @created November 8, 2004
 */
public class DocumentStore extends GenericBean {

  private int id = -1;
  private int templateId = -1;
  private String title = "";
  private String shortDescription = "";
  private String requestedBy = "";
  private String requestedDept = "";
  private Timestamp requestDate = null;
  private String requestDateTimeZone = null;
  private boolean approved = false;
  private Timestamp approvalDate = null;
  private int approvalBy = -1;
  private boolean closed = false;
  private Timestamp closeDate = null;
  private boolean publicStore = false;
  
  private Timestamp entered = null;
  private int enteredBy = -1;
  private Timestamp modified = null;
  private int modifiedBy = -1;
  private Timestamp trashedDate = null;

  private DocumentStoreTeamMemberList userTeam = new DocumentStoreTeamMemberList();
  private DocumentStoreTeamMemberList roleTeam = new DocumentStoreTeamMemberList();
  private DocumentStoreTeamMemberList departmentTeam = new DocumentStoreTeamMemberList();
  private DocumentStoreTeamMemberList employeeTeam = new DocumentStoreTeamMemberList();
  private DocumentStoreTeamMemberList accountContactTeam = new DocumentStoreTeamMemberList();
  private DocumentStoreTeamMemberList portalUserTeam = new DocumentStoreTeamMemberList();
  private FileItemList files = new FileItemList();

  private DocumentStorePermissionList permissions = new DocumentStorePermissionList();


  /**
   * Constructor for the DocumentStore object
   */
  public DocumentStore() {
  }


  /**
   * Constructor for the DocumentStore object
   *
   * @param db    Description of the Parameter
   * @param tmpId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public DocumentStore(Connection db, int tmpId) throws SQLException {
    this.queryRecord(db, tmpId);
  }


  /**
   * Constructor for the DocumentStore object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public DocumentStore(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Sets the id attribute of the DocumentStore object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the DocumentStore object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }
 
  /**
   * Gets the publicStore attribute of the DocumentStore object
   *
   * @return publicStore The publicStore value
   */
  public boolean getPublicStore() {
    return this.publicStore;
  }


  /**
   * Sets the pablicStore attribute of the DocumentStore object
   *
   * @param publicStore The new publicStore value
   */
  public void setPublicStore(boolean publicStore) {
    this.publicStore = publicStore;
  }

  /**
   * Sets the closed attribute of the DocumentStore object
   *
   * @param tmp The new closed value
   */
  public void setPublicStore(String tmp) {
    this.publicStore = DatabaseUtils.parseBoolean(tmp);
  }
  /**
   * Sets the templateId attribute of the DocumentStore object
   *
   * @param tmp The new templateId value
   */
  public void setTemplateId(int tmp) {
    this.templateId = tmp;
  }


  /**
   * Sets the templateId attribute of the DocumentStore object
   *
   * @param tmp The new templateId value
   */
  public void setTemplateId(String tmp) {
    this.templateId = Integer.parseInt(tmp);
  }


  /**
   * Sets the title attribute of the DocumentStore object
   *
   * @param tmp The new title value
   */
  public void setTitle(String tmp) {
    this.title = tmp;
  }


  /**
   * Sets the shortDescription attribute of the DocumentStore object
   *
   * @param tmp The new shortDescription value
   */
  public void setShortDescription(String tmp) {
    this.shortDescription = tmp;
  }


  /**
   * Sets the requestedBy attribute of the DocumentStore object
   *
   * @param tmp The new requestedBy value
   */
  public void setRequestedBy(String tmp) {
    this.requestedBy = tmp;
  }


  /**
   * Sets the requestedDept attribute of the DocumentStore object
   *
   * @param tmp The new requestedDept value
   */
  public void setRequestedDept(String tmp) {
    this.requestedDept = tmp;
  }


  /**
   * Sets the requestDate attribute of the DocumentStore object
   *
   * @param tmp The new requestDate value
   */
  public void setRequestDate(Timestamp tmp) {
    this.requestDate = tmp;
  }


  /**
   * Sets the requestDate attribute of the DocumentStore object
   *
   * @param tmp The new requestDate value
   */
  public void setRequestDate(String tmp) {
    this.requestDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the requestDateTimeZone attribute of the DocumentStore object
   *
   * @param tmp The new requestDateTimeZone value
   */
  public void setRequestDateTimeZone(String tmp) {
    this.requestDateTimeZone = tmp;
  }


  /**
   * Sets the approved attribute of the DocumentStore object
   *
   * @param tmp The new approved value
   */
  public void setApproved(boolean tmp) {
    this.approved = tmp;
  }


  /**
   * Sets the approved attribute of the DocumentStore object
   *
   * @param tmp The new approved value
   */
  public void setApproved(String tmp) {
    this.approved = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the approvalDate attribute of the DocumentStore object
   *
   * @param tmp The new approvalDate value
   */
  public void setApprovalDate(Timestamp tmp) {
    this.approvalDate = tmp;
  }


  /**
   * Sets the approvalDate attribute of the DocumentStore object
   *
   * @param tmp The new approvalDate value
   */
  public void setApprovalDate(String tmp) {
    this.approvalDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the approvalBy attribute of the DocumentStore object
   *
   * @param tmp The new approvalBy value
   */
  public void setApprovalBy(int tmp) {
    this.approvalBy = tmp;
  }


  /**
   * Sets the approvalBy attribute of the DocumentStore object
   *
   * @param tmp The new approvalBy value
   */
  public void setApprovalBy(String tmp) {
    this.approvalBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the closed attribute of the DocumentStore object
   *
   * @param tmp The new closed value
   */
  public void setClosed(boolean tmp) {
    this.closed = tmp;
  }


  /**
   * Sets the closed attribute of the DocumentStore object
   *
   * @param tmp The new closed value
   */
  public void setClosed(String tmp) {
    this.closed = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the closeDate attribute of the DocumentStore object
   *
   * @param tmp The new closeDate value
   */
  public void setCloseDate(Timestamp tmp) {
    this.closeDate = tmp;
  }


  /**
   * Sets the closeDate attribute of the DocumentStore object
   *
   * @param tmp The new closeDate value
   */
  public void setCloseDate(String tmp) {
    this.closeDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the entered attribute of the DocumentStore object
   *
   * @param tmp The new entered value
   */
  public void setEntered(Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   * Sets the entered attribute of the DocumentStore object
   *
   * @param tmp The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the enteredBy attribute of the DocumentStore object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   * Sets the enteredBy attribute of the DocumentStore object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the modified attribute of the DocumentStore object
   *
   * @param tmp The new modified value
   */
  public void setModified(Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   * Sets the modified attribute of the DocumentStore object
   *
   * @param tmp The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the modifiedBy attribute of the DocumentStore object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   * Sets the modifiedBy attribute of the DocumentStore object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the trashedDate attribute of the DocumentStore object
   *
   * @param tmp The new trashedDate value
   */
  public void setTrashedDate(Timestamp tmp) {
    this.trashedDate = tmp;
  }


  /**
   * Sets the trashedDate attribute of the DocumentStore object
   *
   * @param tmp The new trashedDate value
   */
  public void setTrashedDate(String tmp) {
    this.trashedDate = DatabaseUtils.parseTimestamp(tmp);
  }

  public boolean isTrashed() {
    return (trashedDate != null);
  }


  /**
   * Sets the userTeam attribute of the DocumentStore object
   *
   * @param tmp The new userTeam value
   */
  public void setUserTeam(DocumentStoreTeamMemberList tmp) {
    this.userTeam = tmp;
  }


  /**
   * Sets the roleTeam attribute of the DocumentStore object
   *
   * @param tmp The new roleTeam value
   */
  public void setRoleTeam(DocumentStoreTeamMemberList tmp) {
    this.roleTeam = tmp;
  }


  /**
   * Sets the departmentTeam attribute of the DocumentStore object
   *
   * @param tmp The new departmentTeam value
   */
  public void setDepartmentTeam(DocumentStoreTeamMemberList tmp) {
    this.departmentTeam = tmp;
  }


  /**
   * Sets the employeeTeam attribute of the DocumentStore object
   *
   * @param tmp The new employeeTeam value
   */
  public void setEmployeeTeam(DocumentStoreTeamMemberList tmp) {
    this.employeeTeam = tmp;
  }


  /**
   * Sets the accountContactTeam attribute of the DocumentStore object
   *
   * @param tmp The new accountContactTeam value
   */
  public void setAccountContactTeam(DocumentStoreTeamMemberList tmp) {
    this.accountContactTeam = tmp;
  }

  /**
   * Sets the portalUserTeam attribute of the DocumentStore object
   *
   * @param tmp The new portalUserTeam value
   */
  public void setPortalUserTeam(DocumentStoreTeamMemberList tmp) {
    this.portalUserTeam = tmp;
  }

  /**
   * Sets the files attribute of the DocumentStore object
   *
   * @param tmp The new files value
   */
  public void setFiles(FileItemList tmp) {
    this.files = tmp;
  }


  /**
   * Gets the id attribute of the DocumentStore object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the templateId attribute of the DocumentStore object
   *
   * @return The templateId value
   */
  public int getTemplateId() {
    return templateId;
  }


  /**
   * Gets the title attribute of the DocumentStore object
   *
   * @return The title value
   */
  public String getTitle() {
    return title;
  }


  /**
   * Gets the shortDescription attribute of the DocumentStore object
   *
   * @return The shortDescription value
   */
  public String getShortDescription() {
    return shortDescription;
  }


  /**
   * Gets the requestedBy attribute of the DocumentStore object
   *
   * @return The requestedBy value
   */
  public String getRequestedBy() {
    return requestedBy;
  }


  /**
   * Gets the requestedDept attribute of the DocumentStore object
   *
   * @return The requestedDept value
   */
  public String getRequestedDept() {
    return requestedDept;
  }


  /**
   * Gets the requestDate attribute of the DocumentStore object
   *
   * @return The requestDate value
   */
  public Timestamp getRequestDate() {
    return requestDate;
  }


  /**
   * Gets the requestDateTimeZone attribute of the DocumentStore object
   *
   * @return The requestDateTimeZone value
   */
  public String getRequestDateTimeZone() {
    return requestDateTimeZone;
  }


  /**
   * Gets the approved attribute of the DocumentStore object
   *
   * @return The approved value
   */
  public boolean getApproved() {
    return approved;
  }


  /**
   * Gets the approvalDate attribute of the DocumentStore object
   *
   * @return The approvalDate value
   */
  public Timestamp getApprovalDate() {
    return approvalDate;
  }


  /**
   * Gets the approvalBy attribute of the DocumentStore object
   *
   * @return The approvalBy value
   */
  public int getApprovalBy() {
    return approvalBy;
  }


  /**
   * Gets the closed attribute of the DocumentStore object
   *
   * @return The closed value
   */
  public boolean getClosed() {
    return closed;
  }


  /**
   * Gets the closeDate attribute of the DocumentStore object
   *
   * @return The closeDate value
   */
  public Timestamp getCloseDate() {
    return closeDate;
  }


  /**
   * Gets the entered attribute of the DocumentStore object
   *
   * @return The entered value
   */
  public Timestamp getEntered() {
    return entered;
  }


  /**
   * Gets the enteredBy attribute of the DocumentStore object
   *
   * @return The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   * Gets the modified attribute of the DocumentStore object
   *
   * @return The modified value
   */
  public Timestamp getModified() {
    return modified;
  }


  /**
   * Gets the modifiedBy attribute of the DocumentStore object
   *
   * @return The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   * Gets the trashedDate attribute of the DocumentStore object
   *
   * @return The trashedDate value
   */
  public Timestamp getTrashedDate() {
    return trashedDate;
  }


  /**
   * Gets the team attribute of the DocumentStore object
   *
   * @return The team value
   */
  public DocumentStoreTeamMemberList getUserTeam() {
    return userTeam;
  }


  /**
   * Gets the roleTeam attribute of the DocumentStore object
   *
   * @return The roleTeam value
   */
  public DocumentStoreTeamMemberList getRoleTeam() {
    return roleTeam;
  }


  /**
   * Gets the departmentTeam attribute of the DocumentStore object
   *
   * @return The departmentTeam value
   */
  public DocumentStoreTeamMemberList getDepartmentTeam() {
    return departmentTeam;
  }


  /**
   * Gets the employeeTeam attribute of the DocumentStore object
   *
   * @return The employeeTeam value
   */
  public DocumentStoreTeamMemberList getEmployeeTeam() {
    return employeeTeam;
  }


  /**
   * Gets the accountContactTeam attribute of the DocumentStore object
   *
   * @return The accountContactTeam value
   */
  public DocumentStoreTeamMemberList getAccountContactTeam() {
    return accountContactTeam;
  }


  /**
   * Gets the portalUserTeam attribute of the DocumentStore object
   *
   * @return The portalUserTeam value
   */
  public DocumentStoreTeamMemberList getPortalUserTeam() {
    return portalUserTeam;
  }


  /**
   * Gets the files attribute of the DocumentStore object
   *
   * @return The files value
   */
  public FileItemList getFiles() {
    return files;
  }


  /**
   * Description of the Method
   *
   * @param db Description of Parameter
   * @throws SQLException Description of Exception
   */
  public void buildTeamMemberList(Connection db) throws SQLException {
    userTeam.setDocumentStore(this);
    userTeam.setDocumentStoreId(this.getId());
    userTeam.setMemberType(DocumentStoreTeamMemberList.USER);
    userTeam.buildList(db);

    employeeTeam.setDocumentStore(this);
    employeeTeam.setDocumentStoreId(this.getId());
    employeeTeam.setMemberType(DocumentStoreTeamMemberList.USER);
    employeeTeam.setEmployeesOnly(true);
    employeeTeam.buildList(db);

    accountContactTeam.setDocumentStore(this);
    accountContactTeam.setDocumentStoreId(this.getId());
    accountContactTeam.setMemberType(DocumentStoreTeamMemberList.USER);
    accountContactTeam.setAccountContactsOnly(true);
    accountContactTeam.buildList(db);

    roleTeam.setDocumentStore(this);
    roleTeam.setDocumentStoreId(this.getId());
    roleTeam.setMemberType(DocumentStoreTeamMemberList.ROLE);
    roleTeam.buildList(db);

    departmentTeam.setDocumentStore(this);
    departmentTeam.setDocumentStoreId(this.getId());
    departmentTeam.setMemberType(DocumentStoreTeamMemberList.DEPARTMENT);
    departmentTeam.buildList(db);

    portalUserTeam.setDocumentStore(this);
    portalUserTeam.setDocumentStoreId(this.getId());
    portalUserTeam.setMemberType(DocumentStoreTeamMemberList.USER);
    portalUserTeam.setPortalUsersOnly(true);
    portalUserTeam.buildList(db);
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public int buildPermissionList(Connection db) throws SQLException {
    permissions.setDocumentStoreId(this.getId());
    permissions.buildList(db);
    return permissions.size();
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public int buildFileItemList(Connection db) throws SQLException {
    files.setLinkModuleId(Constants.DOCUMENTS_DOCUMENTS);
    files.setLinkItemId(this.getId());
    files.buildList(db);
    return files.size();
  }


  /**
   * Description of the Method
   *
   * @param db     Description of the Parameter
   * @param thisId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  private void queryRecord(Connection db, int thisId) throws SQLException {
    if ((thisId) == -1) {
      throw new SQLException("Invalid Document Store");
    }
    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT * " +
        "FROM document_store ds " +
        "WHERE ds.document_store_id = ? ");

    PreparedStatement pst = db.prepareStatement(sql.toString());
    int i = 0;
    pst.setInt(++i, thisId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (this.id == -1) {
      throw new SQLException(Constants.NOT_FOUND_ERROR);
    }
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    Exception errorMessage = null;
    boolean autoCommit = db.getAutoCommit();
    try {
      if (autoCommit) {
        db.setAutoCommit(false);
      }
      PreparedStatement pst = null;
      id = DatabaseUtils.getNextSeq(
          db, "document_store_document_store_id_seq");
      int i = 0;
      pst = db.prepareStatement(
          "INSERT INTO document_store " +
          "(" + (id > -1 ? "document_store_id, " : "") + "template_id , " +
          " title , " +
          " shortDescription , " +
          " requestedBy , " +
          " requestedDept , " +
          " requestDate , " +
          " requestDate_timezone , " +
          " approvalDate , " +
          " approvalBy , " +
          " closeDate , " +
          " enteredBy , " +
          " modifiedBy, public_store) " +
          "VALUES (" + (id > -1 ? "?," : "") + "?,?,?,?,?,?,?,?,?,?,?,?,?)");
      if (id > -1) {
        pst.setInt(++i, id);
      }
      DatabaseUtils.setInt(pst, ++i, this.templateId);
      pst.setString(++i, this.title);
      pst.setString(++i, this.shortDescription);
      pst.setString(++i, this.requestedBy);
      pst.setString(++i, this.requestedDept);
      pst.setTimestamp(++i, this.requestDate);
      pst.setString(++i, this.requestDateTimeZone);
      if (approved) {
        java.util.Date tmpDate = new java.util.Date();
        approvalDate = new java.sql.Timestamp(tmpDate.getTime());
        approvalDate.setNanos(0);
        pst.setTimestamp(++i, approvalDate);
        pst.setInt(++i, this.enteredBy);
      } else {
        pst.setNull(++i, java.sql.Types.DATE);
        DatabaseUtils.setInt(pst, ++i, -1);
      }
      if (closed) {
        DatabaseUtils.setInt(pst, ++i, this.enteredBy);
        java.util.Date tmpDate = new java.util.Date();
        closeDate = new java.sql.Timestamp(tmpDate.getTime());
        closeDate.setNanos(0);
        pst.setTimestamp(++i, this.closeDate);
      } else {
        pst.setTimestamp(++i, this.closeDate);
      }
      pst.setInt(++i, this.enteredBy);
      pst.setInt(++i, this.modifiedBy);
      pst.setBoolean(++i, this.publicStore);
      pst.execute();
      pst.close();

      this.id = DatabaseUtils.getCurrVal(
          db, "document_store_document_store_id_seq", id);

      //Insert the default permissions
      DocumentStorePermissionList.insertDefaultPermissions(db, id);

      if (autoCommit) {
        db.commit();
      }
    } catch (Exception e) {
      errorMessage = e;
      if (autoCommit) {
        db.rollback();
      }
    } finally {
      if (autoCommit) {
        db.setAutoCommit(true);
      }
    }
    if (errorMessage != null) {
      throw new SQLException(errorMessage.getMessage());
    }
    return true;
  }


  /**
   * Description of the Method
   *
   * @param db       Description of the Parameter
   * @param basePath Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean delete(Connection db, String basePath) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("ID was not specified");
    }
    int recordCount = 0;
    try {
      db.setAutoCommit(false);
      //Deleting document store

      //Deleting team members
      buildTeamMemberList(db);
      userTeam.delete(db, DocumentStoreTeamMemberList.USER);
      roleTeam.delete(db, DocumentStoreTeamMemberList.ROLE);
      departmentTeam.delete(db, DocumentStoreTeamMemberList.DEPARTMENT);

      //Deleting uploaded documents
      buildFileItemList(db);
      files.delete(db, getFileLibraryPath(basePath, "documents"));

      FileFolderList folders = new FileFolderList();
      folders.setLinkModuleId(Constants.DOCUMENTS_DOCUMENTS);
      folders.setLinkItemId(id);
      folders.buildList(db);
      folders.delete(db);

      //deleting permission mappings
      DocumentStorePermissionList.delete(db, id);

      //Delete the actual document store details
      PreparedStatement pst = db.prepareStatement(
          " DELETE FROM document_store " +
          " WHERE document_store_id = ? ");
      pst.setInt(1, id);
      recordCount = pst.executeUpdate();
      pst.close();

      db.commit();
    } catch (Exception e) {
      db.rollback();
      e.printStackTrace(System.out);
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
    if (recordCount == 0) {
      errors.put(
          "actionError", "Document Store could not be deleted because it no longer exists.");
      return false;
    } else {
      return true;
    }
  }


  /**
   * Description of the Method
   *
   * @param db        Description of the Parameter
   * @param toTrash   Description of the Parameter
   * @param tmpUserId Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean updateStatus(Connection db, boolean toTrash, int tmpUserId) throws SQLException {
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    boolean commit = true;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      sql.append(
          "UPDATE document_store " +
          "SET trashed_date = ? , " +
          "modified = " + DatabaseUtils.getCurrentTimestamp(db) + " , " +
          "modifiedby = ? " +
          "WHERE document_store_id = ? ");
      int i = 0;
      pst = db.prepareStatement(sql.toString());
      if (toTrash) {
        DatabaseUtils.setTimestamp(
            pst, ++i, new Timestamp(System.currentTimeMillis()));
      } else {
        DatabaseUtils.setTimestamp(pst, ++i, (Timestamp) null);
      }
      DatabaseUtils.setInt(pst, ++i, tmpUserId);
      pst.setInt(++i, this.id);
      pst.executeUpdate();
      pst.close();
      
      if (commit) {
        db.commit();
      }
    } catch (SQLException e) {
      if (commit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (commit) {
        db.setAutoCommit(true);
      }
    }
    return true;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public int update(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("ID was not specified");
    }

    int resultCount = 0;
    boolean previouslyApproved = false;
    java.sql.Timestamp previousApprovalDate = null;
    boolean previouslyClosed = false;
    java.sql.Timestamp previousCloseDate = null;
    PreparedStatement pst = db.prepareStatement(
        " SELECT * " +
        " FROM document_store " +
        " WHERE document_store_id = ? ");
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      previousApprovalDate = rs.getTimestamp("approvaldate");
      previouslyApproved = (previousApprovalDate != null);
      previousCloseDate = rs.getTimestamp("closedate");
      previouslyClosed = (previousCloseDate != null);
    }
    rs.close();
    pst.close();

    StringBuffer sql = new StringBuffer();
    sql.append(
        " UPDATE document_store " +
        " SET " +
        " template_id = ? , " +
        " title = ? , " +
        " shortDescription = ? , " +
        " requestedBy = ? , " +
        " requestedDept = ? , " +
        " requestDate = ? , " +
        " requestDate_timezone = ? , ");
    if (previouslyApproved && approved) {
      sql.append(" approvalDate = ? , ");
    } else if (!previouslyApproved && approved) {
      sql.append(" approvalDate = ? , ");
      sql.append(" approvalBy = ? , ");
    } else if (!approved) {
      sql.append(" approvalDate = ? , ");
      sql.append(" approvalBy = ? , ");
    }
    sql.append(
        " closeDate = ? , " +
        " public_store = ? , " +
        " modifiedby = ? , " +
        " modified = CURRENT_TIMESTAMP " +
        " WHERE document_store_id = ? " +
        " AND modified " + ((this.getModified() == null)?"IS NULL ":"= ? "));

    pst = db.prepareStatement(sql.toString());
    int i = 0;
    DatabaseUtils.setInt(pst, ++i, this.templateId);
    pst.setString(++i, this.title);
    pst.setString(++i, this.shortDescription);
    pst.setString(++i, this.requestedBy);
    pst.setString(++i, this.requestedDept);
    pst.setTimestamp(++i, this.requestDate);
    pst.setString(++i, this.requestDateTimeZone);
    if (previouslyApproved && approved) {
      pst.setTimestamp(++i, previousApprovalDate);
    } else if (!previouslyApproved && approved) {
      java.util.Date tmpDate = new java.util.Date();
      approvalDate = new java.sql.Timestamp(tmpDate.getTime());
      approvalDate.setNanos(0);
      pst.setTimestamp(++i, approvalDate);
      DatabaseUtils.setInt(pst, ++i, this.modifiedBy);
    } else if (!approved) {
      pst.setNull(++i, java.sql.Types.DATE);
      DatabaseUtils.setInt(pst, ++i, -1);
    }
    if (previouslyClosed && closed) {
      pst.setTimestamp(++i, previousCloseDate);
    } else if (!previouslyClosed && closed) {
      java.util.Date tmpDate = new java.util.Date();
      closeDate = new java.sql.Timestamp(tmpDate.getTime());
      closeDate.setNanos(0);
      pst.setTimestamp(++i, closeDate);
    } else if (!closed) {
      pst.setNull(++i, java.sql.Types.DATE);
    }
    pst.setBoolean(++i, this.publicStore);
    pst.setInt(++i, this.modifiedBy);
    pst.setInt(++i, this.id);
    if(this.getModified() != null){
      pst.setTimestamp(++i, this.modified);
    }

    resultCount = pst.executeUpdate();
    pst.close();
    
    return resultCount;
  }


  /**
   * Description of the Method
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  private void buildRecord(ResultSet rs) throws SQLException {
    this.id = rs.getInt("document_store_id");
    this.templateId = rs.getInt("template_id");
    this.title = rs.getString("title");
    this.shortDescription = rs.getString("shortDescription");
    this.requestedBy = rs.getString("requestedBy");
    this.requestedDept = rs.getString("requestedDept");
    this.requestDate = rs.getTimestamp("requestDate");
    this.requestDateTimeZone = rs.getString("requestDate_timezone");
    this.approvalDate = rs.getTimestamp("approvalDate");
    this.approvalBy = DatabaseUtils.getInt(rs, "approvalBy");
    this.closeDate = rs.getTimestamp("closeDate");
    this.entered = rs.getTimestamp("entered");
    this.enteredBy = rs.getInt("enteredBy");
    this.modified = rs.getTimestamp("modified");
    this.modifiedBy = rs.getInt("modifiedBy");
    this.trashedDate = rs.getTimestamp("trashed_date");
    this.publicStore = rs.getBoolean("public_store");
  }


  /**
   * Gets the accessUserLevel attribute of the DocumentStore object
   *
   * @param permission Description of the Parameter
   * @return The accessUserLevel value
   */
  public int getAccessUserLevel(String permission) {
    return permissions.getAccessLevel(permission);
  }


  /**
   * Gets the properties that are TimeZone sensitive
   *
   * @return The timeZoneParams value
   */
  public static ArrayList getTimeZoneParams() {
    ArrayList thisList = new ArrayList();
    thisList.add("requestDate");
    return thisList;
  }
}

