package org.aspcfs.modules.tasks.base;

import java.util.*;
import java.sql.*;
import java.text.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.base.CFSLinks;
import org.aspcfs.modules.base.Constants;

/**
 *  Builds the details of the component a Task is linked to.
 *
 *@author     akhi_m
 *@created    May 19, 2003
 *@version    $Id$
 */
public class TaskLink extends CFSLinks {
  private int taskId = -1;


  /**
   *  Sets the taskId attribute of the TaskLink object
   *
   *@param  taskId  The new taskId value
   */
  public void setTaskId(int taskId) {
    this.taskId = taskId;
  }


  /**
   *  Gets the taskId attribute of the TaskLink object
   *
   *@return    The taskId value
   */
  public int getTaskId() {
    return taskId;
  }


  /**
   *  Build the details of the component a Task is linked to.<br>
   *  TODO: If there are any extra parameters to be added other than the
   *  linkItemId then add them to the HashMap defined and process it in the
   *  getLink function
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void build(Connection db) throws SQLException {
    ResultSet rs = null;
    String sql = null;
    if (taskId == -1) {
      throw new SQLException("Task ID not specified for building link details");
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

