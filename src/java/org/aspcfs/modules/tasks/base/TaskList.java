//Copyright 2002 Dark Horse Ventures
//The createFilter method and the prepareFilter method need to have the same
//number of parameters if modified.

package org.aspcfs.modules.tasks.base;

import java.sql.*;
import java.text.*;
import java.util.*;
import java.text.DateFormat;
import javax.servlet.http.HttpServletRequest;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.modules.base.Constants;

/**
 *  Description of the Class
 *
 *@author     akhi_m
 *@created    August 15, 2002
 *@version    $Id$
 */
public class TaskList extends ArrayList {
  protected int enteredBy = -1;
  protected PagedListInfo pagedListInfo = null;
  protected int owner = -1;
  protected int complete = -1;
  protected int tasksAssignedByUser = -1;
  protected java.sql.Timestamp alertRangeStart = null;
  protected java.sql.Timestamp alertRangeEnd = null;
  protected int categoryId = -1;
  protected int projectId = -1;
  protected int ticketId = -1;


  /**
   *  Constructor for the TaskList object
   */
  public TaskList() { }



  /**
   *  Sets the enteredBy attribute of the TaskList object
   *
   *@param  enteredBy  The new enteredBy value
   */
  public void setEnteredBy(int enteredBy) {
    this.enteredBy = enteredBy;
  }


  /**
   *  Sets the pagedListInfo attribute of the TaskList object
   *
   *@param  tmp  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Sets the complete attribute of the TaskList object
   *
   *@param  tmp  The new complete value
   */
  public void setComplete(int tmp) {
    this.complete = tmp;
  }


  /**
   *  Sets the owner attribute of the TaskList object
   *
   *@param  owner  The new owner value
   */
  public void setOwner(int owner) {
    this.owner = owner;
  }


  /**
   *  Sets the tasksAssignedByUser attribute of the TaskList object
   *
   *@param  tmp  The new tasksAssignedByUser value
   */
  public void setTasksAssignedByUser(int tmp) {
    this.tasksAssignedByUser = tmp;
  }


  /**
   *  Sets the alertRangeStart attribute of the Task object
   *
   *@param  alertRangeStart  The new alertRangeStart value
   */
  public void setAlertRangeStart(java.sql.Timestamp alertRangeStart) {
    this.alertRangeStart = alertRangeStart;
  }


  /**
   *  Sets the ticketId attribute of the TaskList object
   *
   *@param  ticketId  The new ticketId value
   */
  public void setTicketId(int ticketId) {
    this.ticketId = ticketId;
  }


  /**
   *  Gets the ticketId attribute of the TaskList object
   *
   *@return    The ticketId value
   */
  public int getTicketId() {
    return ticketId;
  }


  /**
   *  Sets the alertRangeEnd attribute of the Task object
   *
   *@param  alertRangeEnd  The new alertRangeEnd value
   */
  public void setAlertRangeEnd(java.sql.Timestamp alertRangeEnd) {
    this.alertRangeEnd = alertRangeEnd;
  }


  /**
   *  Sets the categoryId attribute of the TaskList object
   *
   *@param  tmp  The new categoryId value
   */
  public void setCategoryId(int tmp) {
    this.categoryId = tmp;
  }


  /**
   *  Sets the projectId attribute of the TaskList object
   *
   *@param  tmp  The new projectId value
   */
  public void setProjectId(int tmp) {
    this.projectId = tmp;
  }


  /**
   *  Gets the enteredBy attribute of the TaskList object
   *
   *@return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Return a mapping of number of alerts for each alert category.
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public HashMap queryRecordCount(Connection db, TimeZone timeZone) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    HashMap events = new HashMap();
    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlTail = new StringBuffer();
    createFilter(sqlFilter);
    
    sqlSelect.append(
        "SELECT duedate, count(*) as nocols " +
        "FROM task t " +
        "WHERE t.task_id > -1 ");
    sqlFilter.append("AND duedate IS NOT NULL ");
    sqlTail.append("GROUP BY duedate ");
    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlTail.toString());
    prepareFilter(pst);
    rs = pst.executeQuery();
    if (System.getProperty("DEBUG") != null) {
      System.out.println("TaskList-> Building Record Count ");
    }
    while (rs.next()) {
      String dueDate = DateUtils.getServerToUserDateString(timeZone, DateFormat.SHORT, rs.getTimestamp("duedate"));
     int temp = rs.getInt("nocols");
      events.put(dueDate, new Integer(temp));
    }
    rs.close();
    pst.close();
    return events;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildShortList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    createFilter(sqlFilter);
    sqlSelect.append(
        "SELECT t.task_id, t.description, t.duedate, t.complete " +
        "FROM task t " +
        "WHERE t.task_id > -1 ");
    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString());
    prepareFilter(pst);
    rs = pst.executeQuery();
    while (rs.next()) {
      Task thisTask = new Task();
      thisTask.setId(rs.getInt("task_id"));
      thisTask.setDescription(rs.getString("description"));
      thisTask.setDueDate(rs.getTimestamp("duedate"));
      thisTask.setComplete(rs.getBoolean("complete"));
      this.add(thisTask);
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
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    int items = -1;
    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();
    //Build a base SQL statement for counting records
    sqlCount.append(
        "SELECT COUNT(*) AS recordcount " +
        "FROM task t " +
        "WHERE t.task_id > -1 ");
    createFilter(sqlFilter);
    if (pagedListInfo != null) {
      //Get the total number of records matching filter
      pst = db.prepareStatement(sqlCount.toString() + sqlFilter.toString());
      items = prepareFilter(pst);
      rs = pst.executeQuery();
      if (rs.next()) {
        int maxRecords = rs.getInt("recordcount");
        pagedListInfo.setMaxRecords(maxRecords);
      }
      rs.close();
      pst.close();
      //Determine the offset, based on the filter, for the first record to show
      if (!pagedListInfo.getCurrentLetter().equals("")) {
        pst = db.prepareStatement(sqlCount.toString() +
            sqlFilter.toString() +
            "AND t.priority > ? ");
        items = prepareFilter(pst);
        pst.setString(++items, pagedListInfo.getCurrentLetter().toLowerCase());
        rs = pst.executeQuery();
        if (rs.next()) {
          int offsetCount = rs.getInt("recordcount");
          pagedListInfo.setCurrentOffset(offsetCount);
        }
        rs.close();
        pst.close();
      }
      //Determine column to sort by
      pagedListInfo.setDefaultSort("t.priority", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY t.priority, description ");
    }
    //Build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "t.task_id, t.entered, t.enteredby, t.priority, t.description, " +
        "t.duedate, t.notes, t.sharing, t.complete, t.estimatedloe, " +
        "t.estimatedloetype, t.type, t.owner, t.completedate, t.modified, " +
        "t.modifiedby, t.category_id " +
        "FROM task t " +
        "WHERE t.task_id > -1 ");
    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }
    int count = 0;
    while (rs.next()) {
      if (pagedListInfo != null && pagedListInfo.getItemsPerPage() > 0 &&
          DatabaseUtils.getType(db) == DatabaseUtils.MSSQL &&
          count >= pagedListInfo.getItemsPerPage()) {
        break;
      }
      ++count;
      Task thisTask = new Task(rs);
      this.add(thisTask);
    }
    rs.close();
    pst.close();
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Task thisTask = (Task) i.next();
      thisTask.buildResources(db);
      if (thisTask.getType() != Task.GENERAL) {
        thisTask.buildLinkDetails(db);
      }
    }
  }


  /**
   *  Description of the Method
   *
   *@param  sqlFilter  Description of the Parameter
   */
  protected void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }

    if (enteredBy != -1) {
      sqlFilter.append("AND t.enteredby = ? ");
    }

    if (tasksAssignedByUser > 0) {
      sqlFilter.append("AND t.enteredby = ? AND t.owner NOT IN (SELECT user_id FROM contact WHERE user_id = ?) AND t.owner IS NOT NULL ");
    }

    if (owner > 0) {
      sqlFilter.append("AND t.owner = ? ");
    }

    if (complete != -1) {
      sqlFilter.append("AND t.complete = ? ");
    }

    if (alertRangeStart != null) {
      sqlFilter.append("AND t.duedate >= ? ");
    }

    if (alertRangeEnd != null) {
      sqlFilter.append("AND t.duedate < ? ");
    }

    if (categoryId > 0) {
      sqlFilter.append("AND t.category_id = ? ");
    }

    if (projectId > 0) {
      sqlFilter.append("AND t.task_id IN (SELECT task_id FROM tasklink_project WHERE project_id = ?) ");
    }

    if (ticketId > 0) {
      sqlFilter.append("AND t.task_id IN (SELECT task_id FROM tasklink_ticket WHERE ticket_id = ?) ");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  pst               Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  protected int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;

    if (enteredBy != -1) {
      pst.setInt(++i, enteredBy);
    }

    if (tasksAssignedByUser > 0) {
      pst.setInt(++i, tasksAssignedByUser);
      pst.setInt(++i, tasksAssignedByUser);
    }

    if (owner > 0) {
      pst.setInt(++i, owner);
    }

    if (complete != -1) {
      pst.setBoolean(++i, (complete == Constants.TRUE ? true : false));
    }

    if (alertRangeStart != null) {
      pst.setTimestamp(++i, alertRangeStart);
    }

    if (alertRangeEnd != null) {
      pst.setTimestamp(++i, alertRangeEnd);
    }

    if (categoryId > 0) {
      pst.setInt(++i, categoryId);
    }

    if (projectId > 0) {
      pst.setInt(++i, projectId);
    }

    if (ticketId > 0) {
      pst.setInt(++i, ticketId);
    }
    return i;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  userId            Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public static int queryPendingCount(Connection db, int userId) throws SQLException {
    int toReturn = 0;
    String sql =
        "SELECT count(*) as taskcount " +
        "FROM task " +
        "WHERE owner = ? AND complete = ? ";
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql);
    pst.setInt(++i, userId);
    pst.setBoolean(++i, false);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      toReturn = rs.getInt("taskcount");
    }
    rs.close();
    pst.close();
    return toReturn;
  }
}


