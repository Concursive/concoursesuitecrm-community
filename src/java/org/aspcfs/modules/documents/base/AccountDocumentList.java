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
import java.util.ArrayList;

import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.web.PagedListInfo;

/**
 * @author Achyutha
 * @version $Id:  Exp$
 * @created June 6, 2006
 */
public class AccountDocumentList extends ArrayList {
	
  private PagedListInfo pagedListInfo = null;  
  private int documentStoreId = -1;
  private int orgId = -1; 
  private int userId = -1;
  private int buildPortalRecords = Constants.UNDEFINED;
  
  /**
   *  Constructor for the AccountDocumentList object
   */
  public AccountDocumentList() { }

  /**
   *  Gets the documentStoreId attribute of the AccountDocumentList object
   *
   * @return    The groupId value
   */
	public int getDocumentStoreId() {
		return documentStoreId;
	}

  /**
   *  Sets the documentStoreId attribute of the AccountDocumentList object
   *
   * @param  tmp  The new alertRangeEnd value
   */
	public void setDocumentStoreId(int documentStoreId) {
		this.documentStoreId = documentStoreId;
	}

	/**
   *  Sets the documentStoreId attribute of the AccountDocumentList object
   *
   * @param  documentStoreId  The new documentStoreId value
   */
  public void setDocumentStoreId(String documentStoreId) {
    this.documentStoreId = Integer.parseInt(documentStoreId);
  }
  /**
	 * @return Returns the orgId.
	 */
	public int getOrgId() {
		return orgId;
	}

	/**
	 * @param orgId The orgId to set.
	 */
	public void setOrgId(int tmp) {
		this.orgId = tmp;
	}

  /**
   *  Sets the orgId attribute of the AccountDocumentList object
   *
   * @param  tmp  The new orgId value
   */
	public void setOrgId(String tmp) {
    this.orgId = Integer.parseInt(tmp);
  }

  /**
	 * @return Returns the permissionId.
	 */
	public int getBuildPortalRecords() {
		return buildPortalRecords;
	}

	/**
	 * @param permissionId The permissionId to set.
	 */
	public void setBuildPortalRecords(int buildPortalRecords) {
		this.buildPortalRecords = buildPortalRecords;
	}

  /**
	 * @return Returns the orgId.
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param orgId The orgId to set.
	 */
	public void setUserId(int tmp) {
		this.userId = tmp;
	}

  /**
   *  Sets the orgId attribute of the AccountDocumentList object
   *
   * @param  tmp  The new orgId value
   */
	/**
	 * @return Returns the pagedListInfo.
	 */
	public PagedListInfo getPagedListInfo() {
		return pagedListInfo;
	}

	/**
   *  Description of the Method
   *
   * @param  db             Description of Parameter
   * @throws  SQLException  Description of Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    int items = -1;

    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();

    //Need to build a base SQL statement for counting records
    sqlCount.append(
        "SELECT COUNT(*) AS recordcount " +
        "FROM document_accounts da " +
        "WHERE da.document_store_id > -1 ");
    createFilter(sqlFilter, db);
    if (pagedListInfo == null) {
      pagedListInfo = new PagedListInfo();
      pagedListInfo.setItemsPerPage(0);
    }

    //Get the total number of records matching filter
    pst = db.prepareStatement(sqlCount.toString() + sqlFilter.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    if (rs.next()) {
      int maxRecords = rs.getInt("recordcount");
      pagedListInfo.setMaxRecords(maxRecords);
    }
    rs.close();
    pst.close();

    //Determine column to sort by
    pagedListInfo.setDefaultSort("title", null);
    pagedListInfo.appendSqlTail(db, sqlOrder);

    //Need to build a base SQL statement for returning records
    pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    sqlSelect.append(
        "da.*, ds.title AS store_name, org.name AS org_name " +
        "FROM document_accounts da " +
        "LEFT JOIN document_store ds ON (da.document_store_id = ds.document_store_id) " +
        "LEFT JOIN organization org ON (da.org_id = org.org_id) " +
        "WHERE da.document_store_id > -1 "); 

    pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, pst);
    }
    rs = pst.executeQuery();
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }
    while (rs.next()) {
      AccountDocument thisAccountDocument = new AccountDocument(rs);
      this.add(thisAccountDocument);
    }
    rs.close();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   * @param  sqlFilter  Description of Parameter
   * @param  db         Description of the Parameter
   */
  private void createFilter(StringBuffer sqlFilter, Connection db) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (documentStoreId > -1) {
      sqlFilter.append("AND (da.document_store_id = ?) ");
    }
    if (orgId > -1) {
      sqlFilter.append("AND (da.org_id = ?) ");
    }
    if (buildPortalRecords == Constants.TRUE) {
    	sqlFilter.append("AND da.document_store_id IN " +
    			"(SELECT document_store_id FROM document_store_user_member " +
    			"WHERE item_id = ?)");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  pst            Description of Parameter
   * @return                Description of the Returned Value
   * @throws  SQLException  Description of Exception
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (documentStoreId > -1) {
      pst.setInt(++i, documentStoreId);
    }
    if (orgId > -1) {
      pst.setInt(++i, orgId);
    }
    if (buildPortalRecords == Constants.TRUE) {
    	pst.setInt(++i, userId);
    }
    return i;
  }

	/**
	 * @param documentStoreAccountInfo
	 */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }

}
