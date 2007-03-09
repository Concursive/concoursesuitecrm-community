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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.DatabaseUtils;

import com.darkhorseventures.framework.beans.GenericBean;
import com.zeroio.iteam.base.FileFolderList;

/**
 * @author Achyutha
 * @version $Id:  Exp$
 * @created June 6, 2006
 */
public class AccountDocument extends GenericBean {
	
  private int id = -1;
  private int documentStoreId = -1;
  private int orgId = -1;
  private String documentStoreName = null;
  private Timestamp entered = null;
  private String accountName = null;

  /**
   * Constructor for the AccountDocument object
   */
  public AccountDocument() {
  }


  /**
   * Constructor for the AccountDocument object
   *
   * @param db    Description of the Parameter
   * @param tmpId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public AccountDocument(Connection db, int tmpId) throws SQLException {
    this.queryRecord(db, tmpId);
  }


  /**
   * Constructor for the AccountDocument object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public AccountDocument(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Gets the documentStoreId attribute of the AccountDocument object
   *
   * @return The requestDate value
   */
  public int getDocumentStoreId() {
		return documentStoreId;
	}

  /**
	 * @return Returns the accountName.
	 */
	public String getAccountName() {
		return accountName;
	}


	/**
	 * @param accountName The accountName to set.
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}


	/**
	 * @return Returns the documentStoreName.
	 */
	public String getDocumentStoreName() {
		return documentStoreName;
	}


	/**
	 * @param documentStoreName The documentStoreName to set.
	 */
	public void setDocumentStoreName(String documentStoreName) {
		this.documentStoreName = documentStoreName;
	}


	/**
   * Sets the documentStoreId attribute of the AccountDocument object
   *
   * @param tmp The new id value
   */
	public void setDocumentStoreId(int documentStoreId) {
		this.documentStoreId = documentStoreId;
	}

  /**
   * Gets the entered attribute of the AccountDocument object
   *
   * @return The requestDate value
   */
	public Timestamp getEntered() {
		return entered;
	}

  /**
   * Sets the entered attribute of the AccountDocument object
   *
   * @param tmp The new id value
   */
	public void setEntered(Timestamp entered) {
		this.entered = entered;
	}

  /**
   * Gets the id attribute of the AccountDocument object
   *
   * @return The requestDate value
   */
	public int getId() {
		return id;
	}
	
  /**
   * Sets the id attribute of the AccountDocument object
   *
   * @param tmp The new id value
   */
	public void setId(int id) {
		this.id = id;
	}

  /**
   * Gets the orgId attribute of the AccountDocument object
   *
   * @return The requestDate value
   */
	public int getOrgId() {
		return orgId;
	}

  /**
   * Sets the orgId attribute of the AccountDocument object
   *
   * @param tmp The new id value
   */
	public void setOrgId(int orgId) {
		this.orgId = orgId;
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
        "SELECT da.*, ds.title AS store_name, org.name AS org_name " +
        "FROM document_accounts da " +
        "LEFT JOIN document_store ds ON (da.document_store_id = ds.document_store_id) " +
        "LEFT JOIN organization org ON (da.org_id = org.org_id) " +
        "WHERE da.id = ? ");

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
          db, "document_accounts_id_seq");
      int i = 0;
      pst = db.prepareStatement(
          "INSERT INTO document_accounts " +
          "(" + (id > -1 ? "id, " : "") + "document_store_id , " +
          " org_id , " +
          " entered )" +
          "VALUES (" + (id > -1 ? "?," : "") + "?,?," +DatabaseUtils.getCurrentTimestamp(db) +")");
      if (id > -1) {
        pst.setInt(++i, id);
      }

      pst.setInt(++i, this.documentStoreId);
      pst.setInt(++i, this.orgId);
      pst.execute();
      pst.close();

      this.id = DatabaseUtils.getCurrVal(
          db, "document_accounts_id_seq", id);

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
  public boolean delete(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("ID was not specified");
    }
    int recordCount = 0;
    try {
      db.setAutoCommit(false);

      //Delete the actual document store details
      PreparedStatement pst = db.prepareStatement(
          " DELETE FROM document_accounts " +
          " WHERE id = ? ");
      pst.setInt(1, id);
      recordCount = pst.executeUpdate();
      pst.close();

      db.commit();
    } catch (Exception e) {
      db.rollback();
      e.printStackTrace(System.out);
    } finally {
      db.setAutoCommit(true);
    }
    if (recordCount == 0) {
      errors.put(
          "actionError", "Document Store Account could not be deleted because it no longer exists.");
      return false;
    } else {
      return true;
    }
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
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        " UPDATE document_accounts " +
        " SET " +
        " document_store_id = ? , " +
        " org_id = ? , " +
        " entered = ? " +
        " WHERE id = ? ");
    pst = db.prepareStatement(sql.toString());
    int i = 0;
    pst.setInt(++i, this.documentStoreId);
    pst.setInt(++i, this.orgId);
    pst.setTimestamp(++i, this.entered);
    pst.setInt(++i, this.id);
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
    this.id = rs.getInt("id");
    this.documentStoreId = rs.getInt("document_store_id");
    this.orgId = rs.getInt("org_id");
    this.entered = rs.getTimestamp("entered");
    this.documentStoreName = rs.getString("store_name"); 
    this.accountName = rs.getString("org_name");
  }


	/**
	 * @param b
	 */
	public void setHasAccess(boolean b) {
		// TODO Auto-generated method stub
		
	}



}
