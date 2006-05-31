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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *  Description of the Class
 *
 * @author     partha
 * @created    September 9, 2005
 * @version    $Id$
 */
public class PlanEditorList extends ArrayList {
  protected int moduleId = -1;


  /**
   *  Constructor for the PlanEditorList object
   */
  public PlanEditorList() { }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    pst = db.prepareStatement(
        "SELECT apel.* " +
        "FROM action_plan_editor_lookup apel " +
        "LEFT JOIN action_plan_constants apc ON (apel.constant_id = apc.map_id) " +
        "WHERE module_id = ? " +
        "ORDER BY \"level\" ");
    pst.setInt(1, moduleId);
    rs = pst.executeQuery();
    while (rs.next()) {
      PlanEditor thisEditor = new PlanEditor(rs);
      this.add(thisEditor);
    }
    rs.close();
    pst.close();
  }


  /**
   *  Gets the moduleId attribute of the PlanEditorList object
   *
   * @return    The moduleId value
   */
  public int getModuleId() {
    return moduleId;
  }


  /**
   *  Sets the moduleId attribute of the PlanEditorList object
   *
   * @param  tmp  The new moduleId value
   */
  public void setModuleId(int tmp) {
    this.moduleId = tmp;
  }


  /**
   *  Sets the moduleId attribute of the PlanEditorList object
   *
   * @param  tmp  The new moduleId value
   */
  public void setModuleId(String tmp) {
    this.moduleId = Integer.parseInt(tmp);
  }
}

