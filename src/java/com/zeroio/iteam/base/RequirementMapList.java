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

import java.sql.*;
import java.util.*;

/**
 *  A tree of items for displaying
 *
 *@author     matt rajkowski
 *@created    March 2003
 *@version    $Id: RequirementMapList.java,v 1.2 2004/07/21 19:00:43 mrajkowski
 *      Exp $
 */
public class RequirementMapList extends ArrayList {
  public final static int FILTER_PRIORITY = 1;

  private int projectId = -1;
  private int requirementId = -1;


  /**
   *  Constructor for the RequirementMapList object
   */
  public RequirementMapList() { }


  /**
   *  Sets the projectId attribute of the RequirementMapList object
   *
   *@param  tmp  The new projectId value
   */
  public void setProjectId(int tmp) {
    this.projectId = tmp;
  }


  /**
   *  Sets the projectId attribute of the RequirementMapList object
   *
   *@param  tmp  The new projectId value
   */
  public void setProjectId(String tmp) {
    this.projectId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the requirementId attribute of the RequirementMapList object
   *
   *@param  tmp  The new requirementId value
   */
  public void setRequirementId(int tmp) {
    this.requirementId = tmp;
  }


  /**
   *  Sets the requirementId attribute of the RequirementMapList object
   *
   *@param  tmp  The new requirementId value
   */
  public void setRequirementId(String tmp) {
    this.requirementId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the projectId attribute of the RequirementMapList object
   *
   *@return    The projectId value
   */
  public int getProjectId() {
    return projectId;
  }


  /**
   *  Gets the requirementId attribute of the RequirementMapList object
   *
   *@return    The requirementId value
   */
  public int getRequirementId() {
    return requirementId;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    //All the items are in order by position
    PreparedStatement pst = db.prepareStatement(
        "SELECT * " +
        "FROM project_requirements_map " +
        "WHERE project_id = ? " +
        "AND requirement_id = ? " +
        "ORDER BY position ");
    pst.setInt(1, projectId);
    pst.setInt(2, requirementId);
    ResultSet rs = pst.executeQuery();
    RequirementMapItem previousItem = null;
    HashMap indents = new HashMap();
    while (rs.next()) {
      RequirementMapItem thisItem = new RequirementMapItem(rs);
      this.add(thisItem);
      //Find the parent
      if (previousItem != null) {
        if (thisItem.getIndent() == 0) {
          //Top level
          ((RequirementMapItem) indents.get(new Integer(thisItem.getIndent()))).setFinalNode(false);
        } else {
          if (previousItem.getIndent() < thisItem.getIndent()) {
            //The parent was the previous item
            thisItem.setParent(previousItem);
            previousItem.getChildren().add(thisItem);
          } else if (previousItem.getIndent() >= thisItem.getIndent()) {
            //The parent is somewhere back...
            thisItem.setParent((RequirementMapItem) indents.get(new Integer(thisItem.getIndent() - 1)));
            ((RequirementMapItem) indents.get(new Integer(thisItem.getIndent() - 1))).getChildren().add(thisItem);
            ((RequirementMapItem) indents.get(new Integer(thisItem.getIndent()))).setFinalNode(false);
          }
        }
      }
      RequirementMapItem previousIndent = (RequirementMapItem) indents.get(new Integer(thisItem.getIndent()));
      if (previousIndent != null) {
        thisItem.setPreviousSameIndent(previousIndent);
        previousIndent.setNextSameIndent(thisItem);
      }
      indents.put(new Integer(thisItem.getIndent()), thisItem);
      previousItem = thisItem;
    }
    rs.close();
    pst.close();
  }


  /**
   *  Gets the item attribute of the RequirementMapList object
   *
   *@param  position  Description of the Parameter
   *@return           The item value
   */
  public RequirementMapItem getItem(int position) {
    return (RequirementMapItem) this.get(position - 1);
    /*
     *  Iterator i = this.iterator();
     *  while (i.hasNext()) {
     *  RequirementMapItem thisItem = (RequirementMapItem) i.next();
     *  if (thisItem.getPosition() == position) {
     *  return thisItem;
     *  }
     *  }
     *  return null;
     */
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
        "DELETE FROM project_requirements_map " +
        "WHERE requirement_id = ? ");
    pst.setInt(1, requirementId);
    pst.execute();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   *@param  assignments  Description of the Parameter
   *@param  filterType   Description of the Parameter
   *@param  value        Description of the Parameter
   *@return              Description of the Return Value
   */
  public boolean filter(AssignmentList assignments, int filterType, String value) {
    if (value != null) {
      int id = Integer.parseInt(value);
      if (id == -1) {
        return false;
      }
      //Go through list from the bottom, if item has no children and doesn't meet the value, then
      //remove it from the parent and remove it from the iterator
      ListIterator list = this.listIterator(this.size());
      while (list.hasPrevious()) {
        RequirementMapItem thisItem = (RequirementMapItem) list.previous();
        RequirementMapItem thisParent = thisItem.getParent();
        if (thisItem.getChildren().isEmpty() && check(thisItem, assignments, filterType, id)) {
          //Remove this item because it's not valid
          if (thisParent != null) {
            thisParent.getChildren().remove(thisItem);
            if (thisParent.getChildren().isEmpty()) {
              thisParent.setFinalNode(true);
            }
          }
          list.remove();
        } else {
          //Check to see if this item has visually changed because of other items
          if (thisParent != null) {
            if (!thisItem.getFinalNode() && thisParent.getChildren().indexOf(thisItem) == thisParent.getChildren().size() - 1) {
              thisItem.setFinalNode(true);
            }
          }
        }
      }
    }
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  thisItem     Description of the Parameter
   *@param  assignments  Description of the Parameter
   *@param  filterType   Description of the Parameter
   *@param  value        Description of the Parameter
   *@return              Description of the Return Value
   */
  private boolean check(RequirementMapItem thisItem, AssignmentList assignments, int filterType, int value) {
    if (thisItem.getFolderId() != -1) {
      return true;
    }
    if (filterType == FILTER_PRIORITY &&
        assignments.getAssignment(thisItem.getAssignmentId()).getPriorityId() != value) {
      return true;
    }
    return false;
  }
}

