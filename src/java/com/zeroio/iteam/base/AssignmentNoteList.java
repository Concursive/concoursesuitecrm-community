package com.zeroio.iteam.base;

import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Description
 *
 * @author mrajkowski
 * @version $Id$
 * @created Dec 16, 2004
 */

public class AssignmentNoteList extends ArrayList {

  public AssignmentNoteList() {
  }

  private int assignmentId = -1;
  private PagedListInfo pagedListInfo = null;

  public static void delete(Connection db, int assignmentId) throws SQLException {
    PreparedStatement pst = db.prepareStatement("DELETE FROM project_assignments_status " +
        "WHERE assignment_id = ? ");
    pst.setInt(1, assignmentId);
    pst.execute();
    pst.close();
  }

  public static void queryNoteCount(Connection db, Assignment thisAssignment) throws SQLException {
    int count = 0;
    PreparedStatement pst = db.prepareStatement("SELECT count(*) AS rec_count " +
        "FROM project_assignments_status " +
        "WHERE assignment_id = ? ");
    pst.setInt(1, thisAssignment.getId());
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      count = rs.getInt("rec_count");
    }
    rs.close();
    pst.close();
    thisAssignment.setNoteCount(count);
  }

  public int getAssignmentId() {
    return assignmentId;
  }

  public void setAssignmentId(int assignmentId) {
    this.assignmentId = assignmentId;
  }

  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }

  public void setPagedListInfo(PagedListInfo pagedListInfo) {
    this.pagedListInfo = pagedListInfo;
  }


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
        "SELECT COUNT(*) as recordcount " +
        "FROM project_assignments_status s " +
        "WHERE s.status_id > 0 ");
    createFilter(sqlFilter, db);
    if (pagedListInfo != null) {
      //Get the total number of records matching filter
      pst = db.prepareStatement(sqlCount.toString() +
          sqlFilter.toString());
      items = prepareFilter(pst);
      rs = pst.executeQuery();
      if (rs.next()) {
        int maxRecords = rs.getInt("recordcount");
        pagedListInfo.setMaxRecords(maxRecords);
      }
      rs.close();
      pst.close();
      //Determine column to sort by
      pagedListInfo.setDefaultSort("s.status_date", "desc");
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY s.status_date DESC ");
    }
    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "* " +
        "FROM project_assignments_status s " +
        "WHERE s.status_id > 0 ");
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
      AssignmentNote assignmentNote = new AssignmentNote(rs);
      this.add(assignmentNote);
    }
    rs.close();
    pst.close();
  }

  private void createFilter(StringBuffer sqlFilter, Connection db) {
    if (assignmentId > -1) {
      sqlFilter.append("AND assignment_id = ? ");
    }
  }

  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (assignmentId > -1) {
      pst.setInt(++i, assignmentId);
    }
    return i;
  }
}
