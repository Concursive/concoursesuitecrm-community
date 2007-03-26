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
package org.aspcfs.modules.relationships.base;

import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.SyncableList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Contains a list of relationship types built from the database
 *
 * @author Mathur
 * @version $id:exp$
 * @created August 11, 2004
 */
public class RelationshipTypeList extends ArrayList implements SyncableList {
  
  public final static String tableName = "lookup_relationship_types";
  public final static String uniqueField = "type_id";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;

  private int categoryIdMapsFrom = -1;
  private String jsEvent = "";
  private int defaultKey = -1;
  private int size = 1;
  private boolean showDisabled = true;
  protected PagedListInfo pagedListInfo = null;
  // other filters
  private int typeId = -1;


  /**
   * Constructor for the RelationshipTypeList object
   */
  public RelationshipTypeList() {
  }


  /**
   * Constructor for the RelationshipTypeList object
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public RelationshipTypeList(Connection db) throws SQLException {
    buildList(db);
  }

  /**
   * Description of the Method
   *
   * @param rs
   * @return
   * @throws SQLException Description of the Returned Value
   */
  public static RelationshipType getObject(ResultSet rs) throws SQLException {
    RelationshipType relationshipType = new RelationshipType(rs);
    return relationshipType;
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#getTableName()
   */
  public String getTableName() {
    return tableName;
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#getUniqueField()
   */
  public String getUniqueField() {
    return uniqueField;
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#setLastAnchor(java.sql.Timestamp)
   */
  public void setLastAnchor(Timestamp lastAnchor) {
    this.lastAnchor = lastAnchor;
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#setLastAnchor(java.lang.String)
   */
  public void setLastAnchor(String lastAnchor) {
    this.lastAnchor = java.sql.Timestamp.valueOf(lastAnchor);
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#setNextAnchor(java.sql.Timestamp)
   */
  public void setNextAnchor(Timestamp nextAnchor) {
    this.nextAnchor = nextAnchor;
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#setNextAnchor(java.lang.String)
   */
  public void setNextAnchor(String nextAnchor) {
    this.nextAnchor = java.sql.Timestamp.valueOf(nextAnchor);
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#setSyncType(int)
   */
  public void setSyncType(int syncType) {
    this.syncType = syncType;
  }
  
  public void setSyncType(String tmp) {
    this.syncType = Integer.parseInt(tmp);
  }

  /**
   * Sets the categoryIdMapsFrom attribute of the RelationshipTypeList object
   *
   * @param tmp The new categoryIdMapsFrom value
   */
  public void setCategoryIdMapsFrom(int tmp) {
    this.categoryIdMapsFrom = tmp;
  }


  /**
   * Sets the categoryIdMapsFrom attribute of the RelationshipTypeList object
   *
   * @param tmp The new categoryIdMapsFrom value
   */
  public void setCategoryIdMapsFrom(String tmp) {
    this.categoryIdMapsFrom = Integer.parseInt(tmp);
  }


  /**
   * Sets the jsEvent attribute of the RelationshipTypeList object
   *
   * @param tmp The new jsEvent value
   */
  public void setJsEvent(String tmp) {
    this.jsEvent = tmp;
  }


  /**
   * Sets the defaultKey attribute of the RelationshipTypeList object
   *
   * @param tmp The new defaultKey value
   */
  public void setDefaultKey(int tmp) {
    this.defaultKey = tmp;
  }


  /**
   * Sets the defaultKey attribute of the RelationshipTypeList object
   *
   * @param tmp The new defaultKey value
   */
  public void setDefaultKey(String tmp) {
    this.defaultKey = Integer.parseInt(tmp);
  }


  /**
   * Sets the size attribute of the RelationshipTypeList object
   *
   * @param tmp The new size value
   */
  public void setSize(int tmp) {
    this.size = tmp;
  }


  /**
   * Sets the size attribute of the RelationshipTypeList object
   *
   * @param tmp The new size value
   */
  public void setSize(String tmp) {
    this.size = Integer.parseInt(tmp);
  }


  /**
   * Sets the showDisabled attribute of the RelationshipTypeList object
   *
   * @param tmp The new showDisabled value
   */
  public void setShowDisabled(boolean tmp) {
    this.showDisabled = tmp;
  }


  /**
   * Sets the showDisabled attribute of the RelationshipTypeList object
   *
   * @param tmp The new showDisabled value
   */
  public void setShowDisabled(String tmp) {
    this.showDisabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the pagedListInfo attribute of the RelationshipTypeList object
   *
   * @param tmp The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   * Gets the pagedListInfo attribute of the RelationshipTypeList object
   *
   * @return The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   * Gets the categoryIdMapsFrom attribute of the RelationshipTypeList object
   *
   * @return The categoryIdMapsFrom value
   */
  public int getCategoryIdMapsFrom() {
    return categoryIdMapsFrom;
  }


  /**
   * Gets the jsEvent attribute of the RelationshipTypeList object
   *
   * @return The jsEvent value
   */
  public String getJsEvent() {
    return jsEvent;
  }


  /**
   * Gets the defaultKey attribute of the RelationshipTypeList object
   *
   * @return The defaultKey value
   */
  public int getDefaultKey() {
    return defaultKey;
  }


  /**
   * Gets the size attribute of the RelationshipTypeList object
   *
   * @return The size value
   */
  public int getSize() {
    return size;
  }


  /**
   * Gets the showDisabled attribute of the RelationshipTypeList object
   *
   * @return The showDisabled value
   */
  public boolean getShowDisabled() {
    return showDisabled;
  }

  public int getTypeId() {
    return typeId;
  }

  public void setTypeId(int tmp) {
    this.typeId = tmp;
  }

  public void setTypeId(String tmp) {
    this.typeId = Integer.parseInt(tmp);
  }

  /**
   * Gets the htmlSelect attribute of the RelationshipTypeList object
   *
   * @return The htmlSelect value
   */
  public HtmlSelect getHtmlSelect() {
    HtmlSelect relationshipTypeSelect = new HtmlSelect();
    relationshipTypeSelect.setJsEvent(jsEvent);
    relationshipTypeSelect.setSelectSize(this.getSize());
    Iterator i = this.iterator();
    while (i.hasNext()) {
      RelationshipType thisRelationshipType = (RelationshipType) i.next();
      relationshipTypeSelect.addItem(
          thisRelationshipType.getTypeId(), thisRelationshipType.getReciprocalName1());
      if (!thisRelationshipType.getReciprocalName1().equals(
          thisRelationshipType.getReciprocalName2())) {
        relationshipTypeSelect.addItem(
            thisRelationshipType.getTypeId() + "_reciprocal", thisRelationshipType.getReciprocalName2());
      }
    }
    return relationshipTypeSelect;
  }

  /**
   * Description of the Method
   *
   * @param db
   * @param pst
   * @return
   * @throws SQLException Description of the Returned Value
   */
  public ResultSet queryList(Connection db, PreparedStatement pst) throws SQLException {
    return queryList(db, pst, "", "");
  }

  /**
   * Description of the Method
   *
   * @param db
   * @param pst
   * @param sqlFilter
   * @param sqlOrder
   * @return
   * @throws SQLException Description of the Returned Value
   */
  public ResultSet queryList(Connection db, PreparedStatement pst, String sqlFilter, String sqlOrder) throws SQLException {
    StringBuffer sqlSelect = new StringBuffer();

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "lrt.* " +
        "FROM " + tableName + " lrt " +
        "WHERE lrt.type_id > -1 ");
    if(sqlFilter == null || sqlFilter.length() == 0){
      StringBuffer buff = new StringBuffer();
      createFilter(db, buff);
      sqlFilter = buff.toString();
    }
    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter + sqlOrder);
    prepareFilter(pst);

    return DatabaseUtils.executeQuery(db, pst, pagedListInfo);
  }

  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {

    PreparedStatement pst = null;
    ResultSet rs = null;
    int items = -1;

    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();

    StringBuffer sqlOrder = new StringBuffer();

    //Need to build a base SQL statement for counting records
    sqlCount.append(
        "SELECT COUNT(*) AS recordcount " +
        "FROM lookup_relationship_types lrt " +
        "WHERE code > -1 ");

    createFilter(db, sqlFilter);

    if (pagedListInfo != null) {
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

      //Determine the offset, based on the filter, for the first record to show
      if (!pagedListInfo.getCurrentLetter().equals("")) {
        pst = db.prepareStatement(
            sqlCount.toString() +
            sqlFilter.toString() +
            "AND lrt.reciprocal_name_1 < ? ");
        items = prepareFilter(pst);
        pst.setString(++items, pagedListInfo.getCurrentLetter().toLowerCase());
        rs = pst.executeQuery();
        if (rs.next()) {
          int offsetCount = rs.getInt("recordcount");
          pagedListInfo.setCurrentOffset(offsetCount);
        }
        rs.close();
        pst.close();
      }

      //Determine column to sort by
      pagedListInfo.setDefaultSort("lrt.type_id,category_id_maps_from", null);
    } else {
      sqlOrder.append("ORDER BY lrt.type_id,lrt.category_id_maps_from ");
    }

    rs = queryList(db, pst, sqlFilter.toString(), sqlOrder.toString());
    while (rs.next()) {
      RelationshipType thisRelationshipType = new RelationshipType(rs);
      this.add(thisRelationshipType);
    }
    rs.close();
    if (pst != null) {
      pst.close();
    }
  }


  /**
   * Description of the Method
   *
   * @param db        Description of the Parameter
   * @param sqlFilter Description of the Parameter
   */
  protected void createFilter(Connection db, StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }

    if (categoryIdMapsFrom != -1) {
      sqlFilter.append("AND lrt.category_id_maps_from = ? ");
    }
    if (typeId != -1) {
      sqlFilter.append("AND lrt.type_id = ? ");
    }
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        sqlFilter.append("AND lrt.entered > ? ");
      }
      sqlFilter.append("AND lrt.entered < ? ");
    }
    if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append("AND lrt.modified > ? ");
      sqlFilter.append("AND lrt.entered < ? ");
      sqlFilter.append("AND lrt.modified < ? ");
    }
  }


  /**
   * Description of the Method
   *
   * @param pst Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  protected int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;

    if (categoryIdMapsFrom != -1) {
      pst.setInt(++i, categoryIdMapsFrom);
    }
    if (typeId != -1) {
      pst.setInt(++i, typeId);
    }
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        pst.setTimestamp(++i, lastAnchor);
      }
      pst.setTimestamp(++i, nextAnchor);
    }
    if (syncType == Constants.SYNC_UPDATES) {
      pst.setTimestamp(++i, lastAnchor);
      pst.setTimestamp(++i, lastAnchor);
      pst.setTimestamp(++i, nextAnchor);
    }
    return i;
  }
}

