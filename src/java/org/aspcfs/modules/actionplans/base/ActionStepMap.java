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
import org.aspcfs.utils.DatabaseUtils;

import java.sql.*;

/**
 * Description of the Class
 *
 * @author mrajkowski
 * @version $Id: ActionStep.java,v 1.1.2.17.2.2 2005/09/16 21:12:13 partha
 *          Exp $
 * @created January 25, 2006
 */
public class ActionStepMap extends GenericBean {
  private int mapId = -1;
  private int constantId = -1;
  private int actionConstantId = -1;
  private Timestamp entered = null;
  private Timestamp modified = null;

  /**
   * Constructor for the ActionStepMap object
   */
  public ActionStepMap() {}

  /**
   * Constructor for the ActionStepMap object
   * @param rs
   * @throws SQLException
   */
  public ActionStepMap(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }

  /**
   * Description of the Method
   *
   * @return Description of the Returned Value
   */
  public int getId() {
    return mapId;
  }

  /**
   * Description of the Method
   *
   * @param mapId Description of the Returned Value
   */
  public void setId(int mapId) {
    this.mapId = mapId;
  }

  /**
   * Description of the Method
   *
   * @param mapId Description of the Returned Value
   */
  public void setId(String mapId) {
    this.mapId = Integer.parseInt(mapId);
  }

  /**
   * Description of the Method
   *
   * @return Description of the Returned Value
   */
  public int getConstantId() {
    return constantId;
  }

  /**
   * Description of the Method
   *
   * @param constantId Description of the Returned Value
   */
  public void setConstantId(int constantId) {
    this.constantId = constantId;
  }

  /**
   * Description of the Method
   *
   * @param constantId Description of the Returned Value
   */
  public void setConstantId(String constantId) {
    this.constantId = Integer.parseInt(constantId);
  }

  /**
   * Description of the Method
   *
   * @return Description of the Returned Value
   */
  public int getActionConstantId() {
    return actionConstantId;
  }

  /**
   * Description of the Method
   *
   * @param actionConstantId Description of the Returned Value
   */
  public void setActionConstantId(int actionConstantId) {
    this.actionConstantId = actionConstantId;
  }

  /**
   * Description of the Method
   *
   * @param actionConstantId Description of the Returned Value
   */
  public void setActionConstantId(String actionConstantId) {
    this.actionConstantId = Integer.parseInt(actionConstantId);
  }

  /**
   * @return the entered
   */
  public Timestamp getEntered() {
    return entered;
  }

  /**
   * @param entered the entered to set
   */
  public void setEntered(Timestamp entered) {
    this.entered = entered;
  }

  /**
   * @param entered the entered to set
   */
  public void setEntered(String entered) {
    this.entered = DatabaseUtils.parseTimestamp(entered);
  }

  /**
   * @return the modified
   */
  public Timestamp getModified() {
    return modified;
  }

  /**
   * @param modified the modified to set
   */
  public void setModified(Timestamp modified) {
    this.modified = modified;
  }

  /**
   * @param modified the modified to set
   */
  public void setModified(String modified) {
    this.modified = DatabaseUtils.parseTimestamp(modified);
  }

  /**
   * Description of the Method
   *
   * @param rs Description of the Returned Value
   * @throws SQLException 
   */
  private void buildRecord(ResultSet rs) throws SQLException {
    setId(rs.getInt("map_id"));
    setConstantId(rs.getInt("constant_id"));
    setActionConstantId(rs.getInt("action_constant_id"));
    setEntered(rs.getTimestamp("entered"));
    setModified(rs.getTimestamp("modified"));
  }

  /**
   * Description of the Method
   *
   * @param db
   * @throws SQLException Description of the Returned Value
   */
  public void insert(Connection db) throws SQLException {
    mapId = DatabaseUtils.getNextSeq(db, "step_action_map_map_id_seq");
    StringBuffer sql = new StringBuffer();
    sql.append(
        "INSERT INTO step_action_map (" +
        (mapId > -1 ? "map_id, " : "") + "constant_id, action_constant_id");
    sql.append(", entered, modified");
    sql.append(
        ") VALUES (" +
        (mapId > -1 ? "?, " : "") + "?, ?");
    if(this.getEntered() != null){
      sql.append(", ?");
    } else {
      sql.append(", " + DatabaseUtils.getCurrentTimestamp(db));
    }
    if(this.getModified() != null){
      sql.append(", ?");
    } else {
      sql.append(", " + DatabaseUtils.getCurrentTimestamp(db));
    }
    sql.append(") ");

    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql.toString());

    if (mapId > -1) {
      pst.setInt(++i, mapId);
    }
    pst.setInt(++i, constantId);
    pst.setInt(++i, actionConstantId);
    if(this.getEntered() != null){
      DatabaseUtils.setTimestamp(pst, ++i, this.getEntered());
    }
    if(this.getModified() != null){
      DatabaseUtils.setTimestamp(pst, ++i, this.getModified());
    }

    pst.execute();
    pst.close();
    mapId = DatabaseUtils.getCurrVal(db, "step_action_map_map_id_seq", mapId);
  }
  
  /**
   * Description of the Method
   *
   * @param db
   * @throws SQLException Description of the Returned Value
   */
  public void delete(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
      "DELETE FROM step_action_map WHERE constant_id = ? AND action_constant_id = ? ");
    pst.setInt(1, constantId);
    pst.setInt(2, actionConstantId);
    pst.execute();
    pst.close();
  }

  /**
   * Description of the Method
   *
   * @param db
   * @param constantId
   * @return
   * @throws SQLException Description of the Returned Value
   */
  public static int retrieveActionPlanMapIdFromConstantId(Connection db, int constantId) throws SQLException {
    int mapId = -1;
    PreparedStatement pst = db.prepareStatement(
        "SELECT map_id " +
            "FROM action_plan_constants " +
            "WHERE constant_id = ? "
    );
    pst.setInt(1, constantId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      mapId = rs.getInt("map_id");
    }
    rs.close();
    pst.close();
    return mapId;
  }
}
