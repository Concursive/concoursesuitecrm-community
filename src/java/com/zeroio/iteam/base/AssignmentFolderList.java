/*
 *  Copyright 2000-2004 Matt Rajkowski
 *  matt.rajkowski@teamelements.com
 *  http://www.teamelements.com
 *  This source code cannot be modified, distributed or used without
 *  permission from Matt Rajkowski
 */
package com.zeroio.iteam.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.sql.*;
import org.aspcfs.utils.DatabaseUtils;

/**
 *  Description of the Class
 *
 *@author     matt rajkowski
 *@created    February 25, 2003
 *@version    $Id: AssignmentFolderList.java,v 1.1 2003/02/26 05:41:38 matt Exp
 *      $
 */
public class AssignmentFolderList extends ArrayList {

  private int requirementId = -1;
  private int parentId = -1;


  /**
   *  Constructor for the AssignmentFolderList object
   */
  public AssignmentFolderList() { }


  /**
   *  Sets the requirementId attribute of the AssignmentFolderList object
   *
   *@param  tmp  The new requirementId value
   */
  public void setRequirementId(int tmp) {
    requirementId = tmp;
  }


  /**
   *  Gets the requirementId attribute of the AssignmentFolderList object
   *
   *@return    The requirementId value
   */
  public int getRequirementId() {
    return requirementId;
  }


  /**
   *  Sets the parentId attribute of the AssignmentFolderList object
   *
   *@param  tmp  The new parentId value
   */
  public void setParentId(int tmp) {
    parentId = tmp;
  }


  /**
   *  Gets the parentId attribute of the AssignmentFolderList object
   *
   *@return    The parentId value
   */
  public int getParentId() {
    return parentId;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT * " +
        "FROM project_assignments_folder " +
        "WHERE requirement_id = ? ");
    if (parentId > -1) {
      if (parentId == 0) {
        sql.append("AND parent_id IS NULL ");
      } else {
        sql.append("AND parent_id = ? ");
      }
    }
    PreparedStatement pst = db.prepareStatement(sql.toString());
    int i = 0;
    pst.setInt(++i, requirementId);
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
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void delete(Connection db) throws SQLException {
    Iterator folders = this.iterator();
    while (folders.hasNext()) {
      AssignmentFolder thisFolder = (AssignmentFolder) folders.next();
      thisFolder.delete(db);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  requirementId     Description of the Parameter
   *@exception  SQLException  Description of the Exception
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
   *  Gets the assignmentFolder attribute of the AssignmentFolderList object
   *
   *@param  id  Description of the Parameter
   *@return     The assignmentFolder value
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

