//Copyright 2001-2002 Dark Horse Ventures

package org.aspcfs.modules.healthcare.edit.base;

import java.util.Vector;
import java.util.Iterator;
import java.sql.*;
import org.aspcfs.utils.web.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.modules.healthcare.edit.base.TransactionRecord;
import java.text.DateFormat;

/**
 *  A list of EDIT TransactionRecords
 *
 * @author     chris
 * @created    February 6, 2003
 */
public class TransactionRecordList extends Vector {
  private PagedListInfo pagedListInfo = null;
  private java.sql.Date performed = null;


  /**
   *Constructor for the TransactionRecordList object
   */
  public TransactionRecordList() { }


  /**
   *  Gets the performed attribute of the TransactionRecordList object
   *
   * @return    The performed value
   */
  public java.sql.Date getPerformed() {
    return performed;
  }


  /**
   *  Sets the performed attribute of the TransactionRecordList object
   *
   * @param  performed  The new performed value
   */
  public void setPerformed(java.sql.Date performed) {
    this.performed = performed;
  }


  /**
   *  Gets the pagedListInfo attribute of the TransactionRecordList object
   *
   * @return    The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *  Sets the pagedListInfo attribute of the TransactionRecordList object
   *
   * @param  pagedListInfo  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo pagedListInfo) {
    this.pagedListInfo = pagedListInfo;
  }


  /**
   *  Gets the performedString attribute of the TransactionRecordList object
   *
   * @return    The performedString value
   */
  public String getPerformedString() {
    String tmp = "";
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(performed);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Build a list of TransactionRecords from the database
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = queryList(db, pst);
    while (rs.next()) {
      if (pagedListInfo != null && pagedListInfo.isEndOfOffset(db)) {
        break;
      }
      TransactionRecord thisTransaction = this.getObject(rs);
      this.add(thisTransaction);
    }
    rs.close();
    if (pst != null) {
      pst.close();
    }
  }


  /**
   *  Return a TransactionRecord from a resultset
   *
   * @param  rs                Description of the Parameter
   * @return                   The object value
   * @exception  SQLException  Description of the Exception
   */
  public TransactionRecord getObject(ResultSet rs) throws SQLException {
    TransactionRecord thisTransaction = new TransactionRecord(rs);
    return thisTransaction;
  }


  /**
   *  Query this list directly from the database, and build it
   *
   * @param  db                Description of the Parameter
   * @param  pst               Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public ResultSet queryList(Connection db, PreparedStatement pst) throws SQLException {
    ResultSet rs = null;
    int items = -1;

    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();

    //Need to build a base SQL statement for counting records
    sqlCount.append(
        "SELECT COUNT(*) AS recordcount " +
        "FROM billing_transaction bt " +
        "WHERE bt.id >= 0 ");

    createFilter(sqlFilter);
    //PagedListInfo stuff removed
    sqlOrder.append("ORDER BY bt.id ");

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "bt.* " +
        "FROM billing_transaction bt " +
        "WHERE bt.id > 0 ");
    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();

    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }
    return rs;
  }


  /**
   *  Create the Filter that limits the records returned based on specified parameters
   *
   * @param  sqlFilter  Description of the Parameter
   */
  protected void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (performed != null) {
      sqlFilter.append("AND date_performed = ? ");
    }
  }


  /**
   *  Prepare the Filter that limits the records returned based on specified parameters
   *
   * @param  pst               Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  protected int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (performed != null) {
      pst.setDate(++i, performed);
    }
    return i;
  }
}

