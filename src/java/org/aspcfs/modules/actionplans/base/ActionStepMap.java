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

import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

/**
 * Description of the Class
 *
 * @author mrajkowski
 * @version $Id: ActionStep.java,v 1.1.2.17.2.2 2005/09/16 21:12:13 partha
 *          Exp $
 * @created January 25, 2006
 */
public class ActionStepMap {
  private int mapId = -1;
  private int constantId = -1;
  private int actionConstantId = -1;

  public ActionStepMap() {}

  public int getMapId() {
    return mapId;
  }

  public void setMapId(int mapId) {
    this.mapId = mapId;
  }

  public int getConstantId() {
    return constantId;
  }

  public void setConstantId(int constantId) {
    this.constantId = constantId;
  }

  public int getActionConstantId() {
    return actionConstantId;
  }

  public void setActionConstantId(int actionConstantId) {
    this.actionConstantId = actionConstantId;
  }

  public void insert(Connection db) throws SQLException {
    mapId = DatabaseUtils.getNextSeq(db, "step_action_map_map_id_seq");
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO step_action_map (" +
            (mapId > -1 ? "map_id, " : "") + "constant_id, action_constant_id) " +
            "VALUES (" +
            (mapId > -1 ? "?, " : "") + "?, ?) ");
    int i = 0;
    if (mapId > -1) {
      pst.setInt(++i, mapId);
    }
    pst.setInt(++i, constantId);
    pst.setInt(++i, actionConstantId);
    pst.execute();
    pst.close();
    mapId = DatabaseUtils.getCurrVal(db, "step_action_map_map_id_seq", mapId);
  }
  
  public void delete(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
      "DELETE FROM step_action_map WHERE constant_id = ? AND action_constant_id = ? ");
    pst.setInt(1, constantId);
    pst.setInt(2, actionConstantId);
    pst.execute();
    pst.close();
  }

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
