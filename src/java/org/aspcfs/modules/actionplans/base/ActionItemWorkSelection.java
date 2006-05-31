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
package org.aspcfs.modules.actionplans.base;

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Description of the Class
 *
 * @author Ananth
 * @created August 30, 2005
 */
public class ActionItemWorkSelection extends GenericBean {
  private int id = -1;
  private int itemWorkId = -1;
  private int selection = -1;
  //resources
  private String description = null;


  /**
   * Gets the description attribute of the ActionItemWorkSelection object
   *
   * @return The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   * Sets the description attribute of the ActionItemWorkSelection object
   *
   * @param tmp The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   * Gets the selection attribute of the ActionItemWorkSelection object
   *
   * @return The selection value
   */
  public int getSelection() {
    return selection;
  }


  /**
   * Sets the selection attribute of the ActionItemWorkSelection object
   *
   * @param tmp The new selection value
   */
  public void setSelection(int tmp) {
    this.selection = tmp;
  }


  /**
   * Sets the selection attribute of the ActionItemWorkSelection object
   *
   * @param tmp The new selection value
   */
  public void setSelection(String tmp) {
    this.selection = Integer.parseInt(tmp);
  }


  /**
   * Gets the id attribute of the ActionItemWorkSelection object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Sets the id attribute of the ActionItemWorkSelection object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the ActionItemWorkSelection object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Gets the itemWorkId attribute of the ActionItemWorkSelection object
   *
   * @return The itemWorkId value
   */
  public int getItemWorkId() {
    return itemWorkId;
  }


  /**
   * Sets the itemWorkId attribute of the ActionItemWorkSelection object
   *
   * @param tmp The new itemWorkId value
   */
  public void setItemWorkId(int tmp) {
    this.itemWorkId = tmp;
  }


  /**
   * Sets the itemWorkId attribute of the ActionItemWorkSelection object
   *
   * @param tmp The new itemWorkId value
   */
  public void setItemWorkId(String tmp) {
    this.itemWorkId = Integer.parseInt(tmp);
  }


  /**
   * Constructor for the ActionItemWorkSelection object
   */
  public ActionItemWorkSelection() { }


  /**
   * Constructor for the ActionItemWorkSelection object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public ActionItemWorkSelection(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Constructor for the ActionItemWorkSelection object
   *
   * @param db Description of the Parameter
   * @param id Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public ActionItemWorkSelection(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @param id Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void queryRecord(Connection db, int id) throws SQLException {
    if (id == -1) {
      throw new SQLException("Invalid Action Item Work Selection ID specified");
    }
    PreparedStatement pst = db.prepareStatement(
        "SELECT aiws.*, " +
            "asl.description " +
            "FROM action_item_work_selection aiws " +
            "LEFT JOIN action_step_lookup asl ON (aiws.selection = asl.code) " +
            "WHERE aiws.selection_id = ? ");
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();

    if (this.getId() == -1) {
      throw new SQLException(Constants.NOT_FOUND_ERROR);
    }
  }


  /**
   * Description of the Method
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("selection_id");
    itemWorkId = rs.getInt("item_work_id");
    selection = rs.getInt("selection");
    description = rs.getString("description");
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    id = DatabaseUtils.getNextSeq(db, "action_item_work_selection_selection_id_seq");
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO action_item_work_selection " +
            "(" + (id > -1 ? "selection_id, " : "") + "item_work_id, selection) " +
            "VALUES (" + (id > -1 ? "?, " : "") + "?, ?)");
    int i = 0;
    if (id > -1) {
      pst.setInt(++i, id);
    }
    pst.setInt(++i, itemWorkId);
    pst.setInt(++i, selection);
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "action_item_work_selection_selection_id_seq", id);
    return true;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean delete(Connection db) throws SQLException {
    boolean commit = db.getAutoCommit();
    try {
      if (commit) {
        db.setAutoCommit(false);
      }
      //remove any links to this object from action step work
      int i = 0;
      PreparedStatement pst = db.prepareStatement(
          "UPDATE action_item_work " +
              "SET link_item_id = ? " +
              "WHERE link_module_id = ? " +
              "AND link_item_id = ? ");
      DatabaseUtils.setInt(pst, ++i, -1);
      pst.setInt(++i, Constants.ACTION_ITEM_WORK_SELECTION_OBJECT);
      pst.setInt(++i, this.getId());
      pst.executeUpdate();
      pst.close();

      //delete the selection
      pst = db.prepareStatement(
          "DELETE FROM action_item_work_selection " +
              "WHERE selection_id = ? ");
      pst.setInt(1, id);
      pst.execute();
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
   * @param newList Description of the Parameter
   * @return Description of the Return Value
   */
  public boolean hasValue(ArrayList newList) {
    Iterator j = newList.iterator();
    while (j.hasNext()) {
      String param = (String) j.next();
      if (Integer.parseInt(param) == this.getSelection()) {
        return true;
      }
    }
    return false;
  }
}


