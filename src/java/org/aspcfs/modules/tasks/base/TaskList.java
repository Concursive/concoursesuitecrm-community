//Copyright 2002 Dark Horse Ventures
//The createFilter method and the prepareFilter method need to have the same
//number of parameters if modified.

package com.darkhorseventures.cfsbase;

import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import com.darkhorseventures.webutils.PagedListInfo;
import com.darkhorseventures.webutils.HtmlSelect;
import com.darkhorseventures.utils.DatabaseUtils;

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
  protected boolean completeEnabled = false;
  protected boolean complete = false;
  protected boolean tasksAssignedToMeOnly = false;
  protected boolean tasksAssignedByMeOnly = false;
  protected java.sql.Date alertRangeStart = null;
  protected java.sql.Date alertRangeEnd = null;
  protected int categoryId = -1;
  protected int projectId = -1;


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
  public void setComplete(boolean tmp) {
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
   *  Sets the tasksAssignedToMeOnly attribute of the TaskList object
   *
   *@param  tmp  The new tasksAssignedToMeOnly value
   */
  public void setTasksAssignedToMeOnly(boolean tmp) {
    this.tasksAssignedToMeOnly = tmp;
  }


  /**
   *  Sets the tasksAssignedByMeOnly attribute of the TaskList object
   *
   *@param  tmp  The new tasksAssignedByMeOnly value
   */
  public void setTasksAssignedByMeOnly(boolean tmp) {
    this.tasksAssignedByMeOnly = tmp;
  }


  /**
   *  Sets the alertRangeStart attribute of the Task object
   *
   *@param  alertRangeStart  The new alertRangeStart value
   */
  public void setAlertRangeStart(java.sql.Date alertRangeStart) {
    this.alertRangeStart = alertRangeStart;
  }


  /**
   *  Sets the alertRangeEnd attribute of the Task object
   *
   *@param  alertRangeEnd  The new alertRangeEnd value
   */
  public void setAlertRangeEnd(java.sql.Date alertRangeEnd) {
    this.alertRangeEnd = alertRangeEnd;
  }

  public void setCategoryId(int tmp) { this.categoryId = tmp; }
  public void setProjectId(int tmp) { this.projectId = tmp; }


  /**
   *  Gets the enteredBy attribute of the TaskList object
   *
   *@return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
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

    //Need to build a base SQL statement for counting records
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
      pst.close();
      rs.close();

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

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "t.task_id, t.entered, t.enteredby, t.priority, t.description, " +
        "t.duedate, t.notes, t.sharing, t.complete, t.estimatedloe, " +
        "t.estimatedloetype, t.owner, t.completedate, t.modified, " +
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
      thisTask.buildResources(db);
      this.add(thisTask);
    }
    rs.close();
    pst.close();
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

    if (tasksAssignedToMeOnly) {
      sqlFilter.append("AND t.owner = ? ");
    } else if (tasksAssignedByMeOnly) {
      sqlFilter.append("AND t.owner != ? AND t.owner IS NOT NULL ");
    } else if (owner > 0) {
      sqlFilter.append("AND (t.owner IS NOT NULL OR t.owner = ?) ");
    }

    if (completeEnabled) {
      sqlFilter.append("AND t.complete = ? ");
    }

    if (alertRangeStart != null) {
      sqlFilter.append("AND t.duedate >= ? ");
    }

    if (alertRangeEnd != null) {
      sqlFilter.append("AND t.duedate <= ? ");
    }
   
    if (categoryId > 0) {
      sqlFilter.append("AND t.category_id = ? ");
    }
   
    if (projectId > 0) {
      sqlFilter.append("AND t.task_id IN (SELECT task_id FROM tasklink_project WHERE project_id = ?) ");
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

    if (tasksAssignedToMeOnly || tasksAssignedByMeOnly || owner > 0) {
      pst.setInt(++i, owner);
    }

    if (completeEnabled) {
      pst.setBoolean(++i, complete);
    }

    if (alertRangeStart != null) {
      pst.setDate(++i, alertRangeStart);
    }

    if (alertRangeEnd != null) {
      pst.setDate(++i, alertRangeEnd);
    }

    if (categoryId > 0) {
      pst.setInt(++i, categoryId);
    }
    
    if (projectId > 0) {
      pst.setInt(++i, projectId);
    }
    return i;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  myContactId       Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public static int pendingCount(Connection db, int myContactId) throws SQLException {
    int toReturn = 0;
    String sql = 
      "SELECT count(*) as taskcount " +
      "FROM task " +
      "WHERE owner = ? AND complete =" + DatabaseUtils.getFalse(db) + " ";
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql);
    pst.setInt(++i, myContactId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      toReturn = rs.getInt("taskcount");
    }
    rs.close();
    pst.close();
    return toReturn;
  }


  /**
   *  Sets the filterParams attribute of the TaskList object
   *
   *@param  userId     The new filterParams value
   *@param  contactId  The new filterParams value
   */
  public void setFilterParams(int userId, int contactId) {
    String filter1 = pagedListInfo.getFilterValue("listFilter1");
    String filter2 = pagedListInfo.getFilterValue("listFilter2");
    this.owner = contactId;
    if (filter1.equalsIgnoreCase("taskstome")) {
      tasksAssignedToMeOnly = true;
      enteredBy = -1;
    } else if (filter1.equalsIgnoreCase("tasksbyme")) {
      tasksAssignedByMeOnly = true;
      enteredBy = userId;
    }

    if (!filter2.equalsIgnoreCase("all")) {
      completeEnabled = true;
      complete = "true".equalsIgnoreCase(filter2);
    }
  }
}


