//Copyright 2001 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import java.util.Vector;
import java.util.Iterator;
import java.sql.*;
import com.darkhorseventures.webutils.PagedListInfo;
import com.darkhorseventures.utils.DatabaseUtils;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 *  Contains a list of email addresses... currently used to build the list from
 *  the database with any of the parameters to limit the results.
 *
 */
public class OpportunityNoteList extends NoteList {

  public OpportunityNoteList() { }

  public OpportunityNoteList(HttpServletRequest request) {
    int i = 0;
    while (request.getParameter("note" + (++i) + "subject") != null) {
      OpportunityNote thisNote = new OpportunityNote();
      thisNote.buildRecord(request, i);
      if (thisNote.isValid()) {
        this.addElement(thisNote);
      }
    }
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
        "SELECT COUNT(*) AS recordcount " +
        "FROM note n " +
        "WHERE subject != '' ");

    createFilter(sqlFilter);

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
      pst.close();
      rs.close();

      //Determine the offset, based on the filter, for the first record to show
      if (!pagedListInfo.getCurrentLetter().equals("")) {
        pst = db.prepareStatement(sqlCount.toString() +
            sqlFilter.toString() +
            "AND subject < ? ");
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
      pagedListInfo.setDefaultSort("subject", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY subject");
    }
    
    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "* " +
        "FROM note n " +
        "WHERE subject != '' ");
    pst = db.prepareStatement(
      sqlSelect.toString() + 
      sqlFilter.toString() + 
      sqlOrder.toString());
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
      OpportunityNote thisNote = new OpportunityNote(rs);
      this.addElement(thisNote);
    }
    rs.close();
    pst.close();
  }

}

