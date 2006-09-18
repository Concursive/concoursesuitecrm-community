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

import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Object that represents an item in an outline, used for moving items around
 * and inserting new items.
 *
 * @author matt rajkowski
 * @version $Id: RequirementMapItem.java,v 1.1.2.1 2004/03/19 21:00:50
 *          rvasista Exp $
 * @created July 15, 2003
 */
public class RequirementMapItem {

  public final static int LEFT = 1;
  public final static int RIGHT = 2;
  public final static int UP = 3;
  public final static int DOWN = 4;

  private int id = -1;
  private int projectId = -1;
  private int requirementId = -1;
  private int position = -1;
  private int indent = -1;
  private int folderId = -1;
  private int assignmentId = -1;
  private RequirementMapItem parent = null;
  private ArrayList children = new ArrayList();
  private RequirementMapItem previousSameIndent = null;
  private RequirementMapItem nextSameIndent = null;
  private boolean finalNode = true;
  private int prevIndent = -1;
  private int prevMapId = -1;


  /**
   * Constructor for the RequirementMapItem object
   */
  public RequirementMapItem() {
  }


  /**
   * Constructor for the RequirementMapItem object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public RequirementMapItem(ResultSet rs) throws SQLException {
    id = rs.getInt("map_id");
    projectId = rs.getInt("project_id");
    requirementId = rs.getInt("requirement_id");
    position = rs.getInt("position");
    indent = rs.getInt("indent");
    folderId = DatabaseUtils.getInt(rs, "folder_id");
    assignmentId = DatabaseUtils.getInt(rs, "assignment_id");
  }


  /**
   * Sets the id attribute of the RequirementMapItem object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the projectId attribute of the RequirementMapItem object
   *
   * @param tmp The new projectId value
   */
  public void setProjectId(int tmp) {
    this.projectId = tmp;
  }


  /**
   * Sets the projectId attribute of the RequirementMapItem object
   *
   * @param tmp The new projectId value
   */
  public void setProjectId(String tmp) {
    this.projectId = Integer.parseInt(tmp);
  }


  /**
   * Sets the requirementId attribute of the RequirementMapItem object
   *
   * @param tmp The new requirementId value
   */
  public void setRequirementId(int tmp) {
    this.requirementId = tmp;
  }


  /**
   * Sets the requirementId attribute of the RequirementMapItem object
   *
   * @param tmp The new requirementId value
   */
  public void setRequirementId(String tmp) {
    this.requirementId = Integer.parseInt(tmp);
  }


  /**
   * Sets the position attribute of the RequirementMapItem object
   *
   * @param tmp The new position value
   */
  public void setPosition(int tmp) {
    this.position = tmp;
  }


  /**
   * Sets the position attribute of the RequirementMapItem object
   *
   * @param tmp The new position value
   */
  public void setPosition(String tmp) {
    this.position = Integer.parseInt(tmp);
  }


  /**
   * Sets the indent attribute of the RequirementMapItem object
   *
   * @param tmp The new indent value
   */
  public void setIndent(int tmp) {
    this.indent = tmp;
  }


  /**
   * Sets the indent attribute of the RequirementMapItem object
   *
   * @param tmp The new indent value
   */
  public void setIndent(String tmp) {
    this.indent = Integer.parseInt(tmp);
  }


  /**
   * Sets the folderId attribute of the RequirementMapItem object
   *
   * @param tmp The new folderId value
   */
  public void setFolderId(int tmp) {
    this.folderId = tmp;
  }


  /**
   * Sets the folderId attribute of the RequirementMapItem object
   *
   * @param tmp The new folderId value
   */
  public void setFolderId(String tmp) {
    this.folderId = Integer.parseInt(tmp);
  }


  /**
   * Sets the assignmentId attribute of the RequirementMapItem object
   *
   * @param tmp The new assignmentId value
   */
  public void setAssignmentId(int tmp) {
    this.assignmentId = tmp;
  }


  /**
   * Sets the assignmentId attribute of the RequirementMapItem object
   *
   * @param tmp The new assignmentId value
   */
  public void setAssignmentId(String tmp) {
    this.assignmentId = Integer.parseInt(tmp);
  }


  /**
   * Sets the children attribute of the RequirementMapItem object
   *
   * @param tmp The new children value
   */
  public void setChildren(ArrayList tmp) {
    this.children = tmp;
  }


  /**
   * Sets the finalNode attribute of the RequirementMapItem object
   *
   * @param tmp The new finalNode value
   */
  public void setFinalNode(boolean tmp) {
    this.finalNode = tmp;
  }


  /**
   * Sets the finalNode attribute of the RequirementMapItem object
   *
   * @param tmp The new finalNode value
   */
  public void setFinalNode(String tmp) {
    this.finalNode = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the parent attribute of the RequirementMapItem object
   *
   * @param tmp The new parent value
   */
  public void setParent(RequirementMapItem tmp) {
    this.parent = tmp;
  }


  /**
   * Sets the previousSameIndent attribute of the RequirementMapItem object
   *
   * @param tmp The new previousSameIndent value
   */
  public void setPreviousSameIndent(RequirementMapItem tmp) {
    this.previousSameIndent = tmp;
  }


  /**
   * Sets the nextSameIndent attribute of the RequirementMapItem object
   *
   * @param tmp The new nextSameIndent value
   */
  public void setNextSameIndent(RequirementMapItem tmp) {
    this.nextSameIndent = tmp;
  }


  /**
   * Sets the prevIndent attribute of the RequirementMapItem object
   *
   * @param tmp The new prevIndent value
   */
  public void setPrevIndent(int tmp) {
    this.prevIndent = tmp;
  }


  /**
   * Sets the prevIndent attribute of the RequirementMapItem object
   *
   * @param tmp The new prevIndent value
   */
  public void setPrevIndent(String tmp) {
    this.prevIndent = Integer.parseInt(tmp);
  }


  /**
   * Sets the prevMapId attribute of the RequirementMapItem object
   *
   * @param tmp The new prevMapId value
   */
  public void setPrevMapId(int tmp) {
    this.prevMapId = tmp;
  }


  /**
   * Sets the prevMapId attribute of the RequirementMapItem object
   *
   * @param tmp The new prevMapId value
   */
  public void setPrevMapId(String tmp) {
    this.prevMapId = Integer.parseInt(tmp);
  }


  /**
   * Gets the id attribute of the RequirementMapItem object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the projectId attribute of the RequirementMapItem object
   *
   * @return The projectId value
   */
  public int getProjectId() {
    return projectId;
  }


  /**
   * Gets the requirementId attribute of the RequirementMapItem object
   *
   * @return The requirementId value
   */
  public int getRequirementId() {
    return requirementId;
  }


  /**
   * Gets the position attribute of the RequirementMapItem object
   *
   * @return The position value
   */
  public int getPosition() {
    return position;
  }


  /**
   * Gets the indent attribute of the RequirementMapItem object
   *
   * @return The indent value
   */
  public int getIndent() {
    return indent;
  }


  /**
   * Gets the folderId attribute of the RequirementMapItem object
   *
   * @return The folderId value
   */
  public int getFolderId() {
    return folderId;
  }


  /**
   * Gets the assignmentId attribute of the RequirementMapItem object
   *
   * @return The assignmentId value
   */
  public int getAssignmentId() {
    return assignmentId;
  }


  /**
   * Gets the children attribute of the RequirementMapItem object
   *
   * @return The children value
   */
  public ArrayList getChildren() {
    return children;
  }


  /**
   * Gets the finalNode attribute of the RequirementMapItem object
   *
   * @return The finalNode value
   */
  public boolean getFinalNode() {
    return finalNode;
  }


  /**
   * Gets the parent attribute of the RequirementMapItem object
   *
   * @return The parent value
   */
  public RequirementMapItem getParent() {
    return parent;
  }


  /**
   * Gets the previousSameIndent attribute of the RequirementMapItem object
   *
   * @return The previousSameIndent value
   */
  public RequirementMapItem getPreviousSameIndent() {
    return previousSameIndent;
  }


  /**
   * Gets the nextSameIndent attribute of the RequirementMapItem object
   *
   * @return The nextSameIndent value
   */
  public RequirementMapItem getNextSameIndent() {
    return nextSameIndent;
  }


  /**
   * Gets the prevIndent attribute of the RequirementMapItem object
   *
   * @return The prevIndent value
   */
  public int getPrevIndent() {
    return prevIndent;
  }


  /**
   * Gets the prevMapId attribute of the RequirementMapItem object
   *
   * @return The prevMapId value
   */
  public int getPrevMapId() {
    return prevMapId;
  }


  /**
   * Appends this item to the bottom of the requirement map list
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public synchronized void append(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    if (prevMapId > -1) {
      //Get the next position to store the item if a previous mapId specified
      pst = db.prepareStatement(
          "SELECT " + DatabaseUtils.addQuotes(db, "position")+ " AS reqposition " +
          "FROM project_requirements_map " +
          "WHERE project_id = ? " +
          "AND requirement_id = ? " +
          "AND map_id = ? ");
      pst.setInt(1, projectId);
      pst.setInt(2, requirementId);
      pst.setInt(3, prevMapId);
    } else {
      //Get the next position to store the item if no previous mapId specified
      pst = db.prepareStatement(
          "SELECT max(" + DatabaseUtils.addQuotes(db, "position")+ ") AS reqposition " +
          "FROM project_requirements_map " +
          "WHERE project_id = ? " +
          "AND requirement_id = ? ");
      pst.setInt(1, projectId);
      pst.setInt(2, requirementId);
    }
    rs = pst.executeQuery();
    if (rs.next()) {
      position = rs.getInt("reqposition");
      if (rs.wasNull()) {
        position = 0;
      }
    } else {
      position = 0;
    }
    rs.close();
    pst.close();
    if (indent == -1) {
      //Get the ident of the last item since a new one is not specified
      if (position > 0) {
        pst = db.prepareStatement(
            "SELECT indent " +
            "FROM project_requirements_map " +
            "WHERE project_id = ? " +
            "AND requirement_id = ? " +
            "AND " + DatabaseUtils.addQuotes(db, "position")+ " = ? ");
        pst.setInt(1, projectId);
        pst.setInt(2, requirementId);
        pst.setInt(3, position);
        rs = pst.executeQuery();
        if (rs.next()) {
          indent = rs.getInt("indent");
        } else {
          indent = 0;
        }
        rs.close();
        pst.close();
      } else {
        indent = 0;
      }
    }
    if (prevMapId > -1) {
      //Move the items below, down...
      //TODO: What if the item below was indented? should be ok?
      pst = db.prepareStatement(
          "UPDATE project_requirements_map " +
          "SET " + DatabaseUtils.addQuotes(db, "position")+ " = " + DatabaseUtils.addQuotes(db, "position")+ " + 1 " +
          "WHERE project_id = ? " +
          "AND requirement_id = ? " +
          "AND " + DatabaseUtils.addQuotes(db, "position")+ " > ? ");
      pst.setInt(1, projectId);
      pst.setInt(2, requirementId);
      pst.setInt(3, position);
      pst.executeUpdate();
    }
    //Append the item
    ++position;
    id = DatabaseUtils.getNextSeq(db, "project_requirements_map_map_id_seq");
    pst = db.prepareStatement(
        "INSERT INTO project_requirements_map " +
        "(" + (id > -1 ? "map_id, " : "") + "project_id, requirement_id, " + DatabaseUtils.addQuotes(db, "position")+ ", indent, folder_id, assignment_id) " +
        "VALUES (" + (id > -1 ? "?, " : "") + "?, ?, ?, ?, ?, ?) ");
    int i = 0;
    if (id > -1) {
      pst.setInt(++i, id);
    }
    pst.setInt(++i, projectId);
    pst.setInt(++i, requirementId);
    pst.setInt(++i, position);
    pst.setInt(++i, indent);
    DatabaseUtils.setInt(pst, ++i, folderId);
    DatabaseUtils.setInt(pst, ++i, assignmentId);
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(
        db, "project_requirements_map_map_id_seq", id);
  }


  /**
   * Removes this item from the requirement map list
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public synchronized void remove(Connection db) throws SQLException {
    boolean commit = db.getAutoCommit();
    try {
      if (commit) {
        db.setAutoCommit(false);
      }
      PreparedStatement pst;
      ResultSet rs;
      //Get the current position of the item to remove if not known
      if (position == -1) {
        buildRecord(db);
      }
      RequirementMapList mapList = new RequirementMapList();
      mapList.setProjectId(projectId);
      mapList.setRequirementId(requirementId);
      mapList.buildList(db);
      RequirementMapItem thisItem = mapList.getItem(position);
      //Move the children left
      Iterator i = thisItem.getChildren().iterator();
      while (i.hasNext()) {
        RequirementMapItem child = (RequirementMapItem) i.next();
        child.move(db, LEFT, false, mapList);
      }
      //Delete the requested item
      if (position > -1) {
        pst = db.prepareStatement(
            "DELETE FROM project_requirements_map " +
            "WHERE project_id = ? " +
            "AND requirement_id = ? " +
            "AND " + DatabaseUtils.addQuotes(db, "position")+ " = ? ");
        pst.setInt(1, projectId);
        pst.setInt(2, requirementId);
        pst.setInt(3, thisItem.getPosition());
        pst.execute();
        pst.close();
      }
      //Shift all the other items up
      if (position > -1) {
        pst = db.prepareStatement(
            "UPDATE project_requirements_map " +
            "SET " + DatabaseUtils.addQuotes(db, "position")+ " = " + DatabaseUtils.addQuotes(db, "position")+ " - 1 " +
            "WHERE project_id = ? " +
            "AND requirement_id = ? " +
            "AND " + DatabaseUtils.addQuotes(db, "position")+ " > ? ");
        pst.setInt(1, projectId);
        pst.setInt(2, requirementId);
        pst.setInt(3, position);
        pst.execute();
        pst.close();
      }
      if (commit) {
        db.commit();
      }
    } catch (Exception e) {
      if (commit) {
        db.rollback();
      }
    } finally {
      if (commit) {
        db.setAutoCommit(true);
      }
    }
  }


  /**
   * Moves this item right in the requirement map list
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void moveRight(Connection db) throws SQLException {
    this.move(db, RequirementMapItem.RIGHT, true, null);
  }


  /**
   * Moves this item left in the requirement map list
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void moveLeft(Connection db) throws SQLException {
    this.move(db, RequirementMapItem.LEFT, true, null);
  }


  /**
   * Handles the complexity of moving items LEFT and RIGHT
   *
   * @param db        Description of the Parameter
   * @param direction Description of the Parameter
   * @param isRoot    Description of the Parameter
   * @param mapList   Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  private void move(Connection db, int direction, boolean isRoot, RequirementMapList mapList) throws SQLException {
    if (position == -1) {
      buildRecord(db);
    }
    int moveCount = 0;
    //Load the current map once to work on child operations
    if (mapList == null) {
      mapList = new RequirementMapList();
      mapList.setProjectId(projectId);
      mapList.setRequirementId(requirementId);
      mapList.buildList(db);
    }
    RequirementMapItem thisItem = mapList.getItem(position);
    //Move the item right if NOT 1st in node
    if (direction == RIGHT && thisItem.getPosition() > 1) {
      //Ask parent to make sure this position is not the first child
      if ((thisItem.getParent() != null && thisItem.getParent().getChildren().indexOf(
          thisItem) > 0) ||
          (thisItem.getParent() == null && thisItem.getPosition() > 1) ||
          (!isRoot)) {
        //Good, now move it right and its children
        PreparedStatement pst = db.prepareStatement(
            "UPDATE project_requirements_map " +
            "SET indent = indent + 1 " +
            "WHERE project_id = ? AND requirement_id = ? AND " + DatabaseUtils.addQuotes(db, "position")+ " = ? ");
        int i = 0;
        pst.setInt(++i, projectId);
        pst.setInt(++i, requirementId);
        pst.setInt(++i, thisItem.getPosition());
        moveCount = pst.executeUpdate();
        pst.close();
      }
    }
    //Move the item left if it has a parent
    if (direction == LEFT && position > 1 && indent > 0 && thisItem.getParent() != null) {
      PreparedStatement pst = db.prepareStatement(
          "UPDATE project_requirements_map " +
          "SET indent = indent - 1 " +
          "WHERE project_id = ? AND requirement_id = ? AND " + DatabaseUtils.addQuotes(db, "position")+ " = ? " +
          "AND indent > 0 ");
      int i = 0;
      pst.setInt(++i, projectId);
      pst.setInt(++i, requirementId);
      pst.setInt(++i, thisItem.getPosition());
      moveCount = pst.executeUpdate();
      pst.close();
    }
    //Now move the children
    if (moveCount == 1) {
      Iterator i = thisItem.getChildren().iterator();
      while (i.hasNext()) {
        RequirementMapItem child = (RequirementMapItem) i.next();
        child.move(db, direction, false, mapList);
      }
    }
  }


  /**
   * Moves this item up in the requirement map list
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void moveUp(Connection db) throws SQLException {
    if (position == -1) {
      buildRecord(db);
    }
    //Load the current map once to work on child operations
    RequirementMapList mapList = new RequirementMapList();
    mapList.setProjectId(projectId);
    mapList.setRequirementId(requirementId);
    mapList.buildList(db);
    //Find the next item
    RequirementMapItem thisItem = mapList.getItem(position);
    //Ask parent to make sure this position is not the last child
    if ((thisItem.getParent() != null && thisItem.getParent().getChildren().indexOf(
        thisItem) > 0) ||
        (thisItem.getParent() == null && thisItem.getPosition() > 1)) {
      thisItem.moveUp(db, true, mapList, new MoveCounter());
    }
  }


  /**
   * Handles the complexity of moving an item up in the requirement map list
   *
   * @param db      Description of the Parameter
   * @param isRoot  Description of the Parameter
   * @param mapList Description of the Parameter
   * @param counter Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  private void moveUp(Connection db, boolean isRoot, RequirementMapList mapList, MoveCounter counter) throws SQLException {
    int moveCount = 0;
    RequirementMapItem thisItem = mapList.getItem(position);
    //Ask parent to make sure this position is not the first child
    if ((thisItem.getParent() != null && thisItem.getParent().getChildren().indexOf(
        thisItem) > 0) ||
        (thisItem.getParent() == null && thisItem.getPosition() > 1) ||
        (!isRoot)) {
      PreparedStatement pst = db.prepareStatement(
          "UPDATE project_requirements_map " +
          "SET " + DatabaseUtils.addQuotes(db, "position")+ " = ? " +
          "WHERE map_id = ? ");
      int i = 0;
      if (isRoot) {
        counter.setAmount(
            thisItem.getPosition() - thisItem.getPreviousSameIndent().getPosition());
      }
      pst.setInt(++i, thisItem.getPosition() - counter.getAmount());
      pst.setInt(++i, id);
      moveCount = pst.executeUpdate();
      pst.close();
    }
    //Now move the children
    if (moveCount == 1) {
      counter.add(moveCount);
      Iterator i = thisItem.getChildren().iterator();
      while (i.hasNext()) {
        RequirementMapItem child = (RequirementMapItem) i.next();
        child.moveUp(db, false, mapList, counter);
      }
    }
    //Now move the items that were above, underneath all of these
    if (isRoot && counter.getAmount() > 0) {
      counter.setAmount(counter.getTotal());
      thisItem.getPreviousSameIndent().moveDown(db, false, mapList, counter);
    }
  }


  /**
   * Moves this item down in the requirement map list
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void moveDown(Connection db) throws SQLException {
    if (position == -1) {
      buildRecord(db);
    }
    //Load the current map once to work on child operations
    RequirementMapList mapList = new RequirementMapList();
    mapList.setProjectId(projectId);
    mapList.setRequirementId(requirementId);
    mapList.buildList(db);
    //Find the next item
    RequirementMapItem thisItem = mapList.getItem(position);
    //Ask parent to make sure this position is not the last child
    if ((thisItem.getParent() != null && thisItem.getParent().getChildren().indexOf(
        thisItem) < thisItem.getParent().getChildren().size() - 1) ||
        (thisItem.getParent() == null && thisItem.getNextSameIndent() != null)) {
      mapList.getItem(thisItem.getNextSameIndent().getPosition()).moveUp(
          db, true, mapList, new MoveCounter());
    }
  }


  /**
   * Handles the complexity of moving an item down in the requirement map list
   *
   * @param db      Description of the Parameter
   * @param isRoot  Description of the Parameter
   * @param mapList Description of the Parameter
   * @param counter Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  private void moveDown(Connection db, boolean isRoot, RequirementMapList mapList, MoveCounter counter) throws SQLException {
    int moveCount = 0;
    //Load the current map once to work on child operations
    RequirementMapItem thisItem = mapList.getItem(position);
    //Ask parent to make sure this position is not the last child
    if ((thisItem.getParent() != null && thisItem.getParent().getChildren().indexOf(
        thisItem) < thisItem.getParent().getChildren().size() - 1) ||
        (thisItem.getParent() == null && thisItem.getNextSameIndent() != null) ||
        (!isRoot)) {
      PreparedStatement pst = db.prepareStatement(
          "UPDATE project_requirements_map " +
          "SET " + DatabaseUtils.addQuotes(db, "position")+ " = ? " +
          "WHERE map_id = ? ");
      int i = 0;
      if (isRoot) {
        counter.setAmount(
            thisItem.getNextSameIndent().getPosition() - thisItem.getPosition());
      }
      pst.setInt(++i, thisItem.getPosition() + counter.getAmount());
      pst.setInt(++i, id);
      moveCount = pst.executeUpdate();
      pst.close();
    }
    //Now move the children
    if (moveCount == 1) {
      counter.add(moveCount);
      Iterator i = thisItem.getChildren().iterator();
      while (i.hasNext()) {
        RequirementMapItem child = (RequirementMapItem) i.next();
        child.moveDown(db, false, mapList, counter);
      }
    }
    //Now move the items that were below, above all of these
    if (isRoot && counter.getAmount() > 0) {
      counter.setAmount(counter.getTotal());
      thisItem.getNextSameIndent().moveUp(db, false, mapList, counter);
    }
  }


  /**
   * Reads in missing requirement map item data
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildRecord(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "SELECT map_id, project_id, " + DatabaseUtils.addQuotes(db, "position")+ ", indent " +
        "FROM project_requirements_map " +
        "WHERE requirement_id = ? " +
        "AND " + (folderId > -1 ? "folder_id" : "assignment_id") + " = ? ");
    int i = 0;
    pst.setInt(++i, requirementId);
    if (folderId > -1) {
      pst.setInt(++i, folderId);
    } else {
      pst.setInt(++i, assignmentId);
    }
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      id = rs.getInt("map_id");
      projectId = rs.getInt("project_id");
      position = rs.getInt("position");
      indent = rs.getInt("indent");
    }
    rs.close();
    pst.close();
  }


  /**
   * Description of the Method
   */
  public void printObject() {
    System.out.println("=== RequirementMapItem ===");
    System.out.println("Id: " + id);
    System.out.println("ProjectId: " + projectId);
    System.out.println("RequirementId: " + requirementId);
    System.out.println("AssignmentId: " + assignmentId);
    System.out.println("FolderId: " + folderId);
    System.out.println("Position: " + position);
    System.out.println("Indent: " + indent);
  }
}

