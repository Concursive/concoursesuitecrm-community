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
package com.zeroio.iteam.base;

import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Description of the Class
 *
 * @author matt rajkowski
 * @version $Id: AssignmentFolderList.java,v 1.1 2003/02/26 05:41:38 matt Exp
 *          $
 * @created February 25, 2003
 */
public class AssignmentFolderList extends ArrayList {
  public final static String tableName = "action_item_log";
  public final static String uniqueField = "log_id";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;

  private PagedListInfo pagedListInfo = null;
  private int requirementId = -1;
  private int parentId = -1;

  /**
   * Sets the lastAnchor attribute of the ActionItemList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(java.sql.Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   * Sets the lastAnchor attribute of the ActionItemList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(String tmp) {
    this.lastAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the nextAnchor attribute of the ActionItemList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(java.sql.Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   * Sets the nextAnchor attribute of the ActionItemList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(String tmp) {
    this.nextAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the syncType attribute of the ActionItemList object
   *
   * @param tmp The new syncType value
   */
  public void setSyncType(int tmp) {
    this.syncType = tmp;
  }

  /**
   * Gets the tableName attribute of the ActionItemList object
   *
   * @return The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   * Gets the uniqueField attribute of the ActionItemList object
   *
   * @return The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }

  /**
   * Sets the pagedListInfo attribute of the ActionItemLogList object
   *
   * @param pagedListInfo The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo pagedListInfo) {
    this.pagedListInfo = pagedListInfo;
  }


  /**
   * Constructor for the AssignmentFolderList object
   */
  public AssignmentFolderList() {
  }


  /**
   * Sets the requirementId attribute of the AssignmentFolderList object
   *
   * @param tmp The new requirementId value
   */
  public void setRequirementId(int tmp) {
    requirementId = tmp;
  }


  /**
   * Gets the requirementId attribute of the AssignmentFolderList object
   *
   * @return The requirementId value
   */
  public int getRequirementId() {
    return requirementId;
  }


  /**
   * Sets the parentId attribute of the AssignmentFolderList object
   *
   * @param tmp The new parentId value
   */
  public void setParentId(int tmp) {
    parentId = tmp;
  }


  /**
   * Gets the parentId attribute of the AssignmentFolderList object
   *
   * @return The parentId value
   */
  public int getParentId() {
    return parentId;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT * " +
            "FROM project_assignments_folder " +
            "WHERE folder_id > -1 ");
    if (requirementId > -1) {
      sql.append("AND requirement_id = ? ");
    }
    if (parentId > -1) {
      if (parentId == 0) {
        sql.append("AND parent_id IS NULL ");
      } else {
        sql.append("AND parent_id = ? ");
      }
    }
    PreparedStatement pst = db.prepareStatement(sql.toString());
    int i = 0;
    if (requirementId > -1) {
      pst.setInt(++i, requirementId);
    }
    if (parentId > 0) {
      pst.setInt(++i, parentId);
    }
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      AssignmentFolder thisFolder = new AssignmentFolder(rs);
      this.add(thisFolder);
    }
    rs.close();
    pst.close();
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void delete(Connection db) throws SQLException {
    Iterator folders = this.iterator();
    while (folders.hasNext()) {
      AssignmentFolder thisFolder = (AssignmentFolder) folders.next();
      thisFolder.delete(db);
    }
  }


  /**
   * Description of the Method
   *
   * @param db            Description of the Parameter
   * @param requirementId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public static void delete(Connection db, int requirementId) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "DELETE FROM project_assignments_folder " +
            "WHERE requirement_id = ? ");
    pst.setInt(1, requirementId);
    pst.execute();
    pst.close();
  }


  /**
   * Gets the assignmentFolder attribute of the AssignmentFolderList object
   *
   * @param id Description of the Parameter
   * @return The assignmentFolder value
   */
  public AssignmentFolder getAssignmentFolder(int id) {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      AssignmentFolder thisFolder = (AssignmentFolder) i.next();
      if (thisFolder.getId() == id) {
        return thisFolder;
      }
    }
    return null;
  }
}

