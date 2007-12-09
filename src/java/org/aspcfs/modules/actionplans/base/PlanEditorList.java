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
package org.aspcfs.modules.actionplans.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.SyncableList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;

/**
 * Description of the Class
 *
 * @author partha
 * @version $Id$
 * @created September 9, 2005
 */
public class PlanEditorList extends ArrayList implements SyncableList {
  protected int moduleId = -1;
  public final static String tableName = "action_plan_editor_lookup";
  public final static String uniqueField = "module_id";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;
  private PagedListInfo pagedListInfo = null;

  /**
   * Constructor for the PlanEditorList object
   */
  public PlanEditorList() {
  }

  /**
   * Sets the lastAnchor attribute of the PlanEditorList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(java.sql.Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   * Sets the lastAnchor attribute of the PlanEditorList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(String tmp) {
    this.lastAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the nextAnchor attribute of the PlanEditorList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(java.sql.Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   * Sets the nextAnchor attribute of the PlanEditorList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(String tmp) {
    this.nextAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the syncType attribute of the PlanEditorList object
   *
   * @param tmp The new syncType value
   */
  public void setSyncType(int tmp) {
    this.syncType = tmp;
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#setSyncType(String)
   */
  public void setSyncType(String syncType) {
    this.syncType = Integer.parseInt(syncType);
  }
  
  /**
   * Sets the PagedListInfo attribute of the PlanEditorList object. <p>
   * <p/>
   * The query results will be constrained to the PagedListInfo parameters.
   *
   * @param tmp The new PagedListInfo value
   * @since 1.1
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }

  /**
   * Gets the tableName attribute of the PlanEditorList object
   *
   * @return The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   * Gets the uniqueField attribute of the PlanEditorList object
   *
   * @return The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
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
    StringBuffer sqlFilter = new StringBuffer();
    createFilter(db, sqlFilter);
    pst = prepareList(db, sqlFilter.toString(), "");
    rs = DatabaseUtils.executeQuery(db, pst, pagedListInfo);
    while (rs.next()) {
      PlanEditor thisEditor = new PlanEditor(rs);
      this.add(thisEditor);
    }
    rs.close();
    if (pst != null) {
      pst.close();
    }
  }


  /**
   * Gets the moduleId attribute of the PlanEditorList object
   *
   * @return The moduleId value
   */
  public int getModuleId() {
    return moduleId;
  }


  /**
   * Sets the moduleId attribute of the PlanEditorList object
   *
   * @param tmp The new moduleId value
   */
  public void setModuleId(int tmp) {
    this.moduleId = tmp;
  }


  /**
   * Sets the moduleId attribute of the PlanEditorList object
   *
   * @param tmp The new moduleId value
   */
  public void setModuleId(String tmp) {
    this.moduleId = Integer.parseInt(tmp);
  }
  
  /**
   *  Gets the object attribute of the PlanEditorList object
   *
   * @param  rs                Description of the Parameter
   * @return                   The object value
   * @exception  SQLException  Description of the Exception
   */
  public PlanEditor getObject(ResultSet rs) throws SQLException {
  	PlanEditor obj = new PlanEditor(rs);
    return obj;
  }
  
  public PreparedStatement prepareList(Connection db, String sqlFilter, String sqlOrder) throws SQLException {
    StringBuffer sqlSelect = new StringBuffer();
    sqlSelect.append( "SELECT apel.* " +
        "FROM action_plan_editor_lookup apel " +
        "LEFT JOIN action_plan_constants apc ON (apel.constant_id = apc.map_id)");
  	
    if(sqlFilter == null || sqlFilter.length() == 0){
    	StringBuffer buff = new StringBuffer();
    	createFilter(db, buff);
    	sqlFilter = buff.toString();
    }
    PreparedStatement pst = db.prepareStatement(sqlSelect + sqlFilter +
            "ORDER BY " + DatabaseUtils.addQuotes(db, "level") + " ");
    prepareFilter(pst);
    return  pst;
  }
  
  /**
   * Description of the Method
   *
   * @param db
   * @return
   * @throws SQLException Description of the Returned Value
   */
  public PreparedStatement prepareList(Connection db) throws SQLException {
    return prepareList(db, "", "");
  }

  /**
   * Description of the Method
   * @param db        Description of the Parameter
   * @param sqlFilter Description of the Parameter
   */
  private void createFilter(Connection db, StringBuffer sqlFilter) {
  	Set tokens = new HashSet();
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    
    if(moduleId > -1){ 
    	tokens.add(" module_id = ? ");
    }
    
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
      	tokens.add(" apel.entered > ? ");
      }
      tokens.add(" apel.entered < ? ");
    } else if (syncType == Constants.SYNC_UPDATES) {
    	tokens.add(" apel.modified > ? ");
    	tokens.add(" apel.entered < ? ");
    	tokens.add(" apel.modified < ? ");
    }
    
    Iterator it = tokens.iterator();
    if(it.hasNext()){
    	String token = (String)it.next();
    	sqlFilter.append(" WHERE " + token);
    }
    while(it.hasNext()){
    	String token = (String)it.next();
    	sqlFilter.append(" AND " + token);
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
    if (moduleId > -1) {
      pst.setInt(++i, moduleId);
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

