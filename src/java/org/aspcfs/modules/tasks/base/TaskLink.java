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
package org.aspcfs.modules.tasks.base;

import org.aspcfs.modules.base.CFSLinks;
import org.aspcfs.modules.base.Constants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Builds the details of the component a Task is linked to.
 *
 * @author akhi_m
 * @version $Id$
 * @created May 19, 2003
 */
public class TaskLink extends CFSLinks {
  private int taskId = -1;


  /**
   * Sets the taskId attribute of the TaskLink object
   *
   * @param taskId The new taskId value
   */
  public void setTaskId(int taskId) {
    this.taskId = taskId;
  }


  /**
   * Gets the taskId attribute of the TaskLink object
   *
   * @return The taskId value
   */
  public int getTaskId() {
    return taskId;
  }


  /**
   * Build the details of the component a Task is linked to.<br>
   * TODO: If there are any extra parameters to be added other than the
   * linkItemId then add them to the HashMap defined and process it in the
   * getLink function
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void build(Connection db) throws SQLException {
    ResultSet rs = null;
    String sql = null;
    if (taskId == -1) {
      throw new SQLException(
          "Task ID not specified for building link details");
    }
    try {
      switch (this.getType()) {
        case Constants.TICKET_OBJECT:
          sql = "SELECT ticket_id " +
              "FROM tasklink_ticket " +
              "WHERE task_id = ? ";
          break;
        default:
          sql = "";
          break;
      }

      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql);
      pst.setInt(++i, this.getTaskId());
      rs = pst.executeQuery();
      if (rs.next()) {
        this.setLinkItemId(rs.getInt("ticket_id"));
      }
      rs.close();
      pst.close();
    } catch (SQLException e) {
      throw new SQLException(e.getMessage());
    }
  }
}

