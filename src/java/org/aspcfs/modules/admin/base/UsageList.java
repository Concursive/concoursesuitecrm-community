package org.aspcfs.modules.admin.base;

import java.sql.*;
import org.aspcfs.utils.web.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.modules.admin.base.Usage;

/**
 *  Represents a list of Usage objects. Can be used for generating the list by
 *  specifying parameters and then initiating buildList to query the database.
 *  Can also be used for just querying the count of objects and the sum of
 *  record objects.
 *
 *@author     matt rajkowski
 *@created    December 6, 2002
 *@version    $Id$
 */
public class UsageList {
  private int action = -1;
  private java.sql.Timestamp enteredRangeStart = null;
  private java.sql.Timestamp enteredRangeEnd = null;

  private long count = -1;
  private long size = -1;


  /**
   *  Constructor for the UsageList object
   */
  public UsageList() { }


  /**
   *  Sets the action attribute of the UsageList object
   *
   *@param  tmp  The new action value
   */
  public void setAction(int tmp) {
    this.action = tmp;
  }


  /**
   *  Sets the action attribute of the UsageList object
   *
   *@param  tmp  The new action value
   */
  public void setAction(String tmp) {
    this.action = Integer.parseInt(tmp);
  }


  /**
   *  Sets the enteredRangeStart attribute of the UsageList object
   *
   *@param  tmp  The new enteredRangeStart value
   */
  public void setEnteredRangeStart(java.sql.Timestamp tmp) {
    this.enteredRangeStart = tmp;
  }


  /**
   *  Sets the enteredRangeStart attribute of the UsageList object
   *
   *@param  tmp  The new enteredRangeStart value
   */
  public void setEnteredRangeStart(String tmp) {
    this.enteredRangeStart = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the enteredRangeEnd attribute of the UsageList object
   *
   *@param  tmp  The new enteredRangeEnd value
   */
  public void setEnteredRangeEnd(java.sql.Timestamp tmp) {
    this.enteredRangeEnd = tmp;
  }


  /**
   *  Sets the enteredRangeEnd attribute of the UsageList object
   *
   *@param  tmp  The new enteredRangeEnd value
   */
  public void setEnteredRangeEnd(String tmp) {
    this.enteredRangeEnd = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the count attribute of the UsageList object
   *
   *@param  tmp  The new count value
   */
  public void setCount(long tmp) {
    this.count = tmp;
  }


  /**
   *  Sets the size attribute of the UsageList object
   *
   *@param  tmp  The new size value
   */
  public void setSize(long tmp) {
    this.size = tmp;
  }


  /**
   *  Gets the action attribute of the UsageList object
   *
   *@return    The action value
   */
  public int getAction() {
    return action;
  }


  /**
   *  Gets the enteredRangeStart attribute of the UsageList object
   *
   *@return    The enteredRangeStart value
   */
  public java.sql.Timestamp getEnteredRangeStart() {
    return enteredRangeStart;
  }


  /**
   *  Gets the enteredRangeEnd attribute of the UsageList object
   *
   *@return    The enteredRangeEnd value
   */
  public java.sql.Timestamp getEnteredRangeEnd() {
    return enteredRangeEnd;
  }


  /**
   *  Gets the count attribute of the UsageList object
   *
   *@return    The count value
   */
  public long getCount() {
    return count;
  }


  /**
   *  Gets the size attribute of the UsageList object
   *
   *@return    The size value
   */
  public long getSize() {
    return size;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    throw new SQLException("Not implemented");
  }


  /**
   *  Queries the count of records and the sum of recordSizes based on the
   *  specified parameters.
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildUsage(Connection db) throws SQLException {
    StringBuffer sqlFilter = new StringBuffer();
    String sqlCount =
        "SELECT COUNT(*) AS recordcount, SUM(record_size) AS recordsize " +
        "FROM usage_log u " +
        "WHERE u.usage_id > -1 ";
    createFilter(sqlFilter);
    PreparedStatement pst = db.prepareStatement(sqlCount + sqlFilter.toString());
    int items = prepareFilter(pst);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      count = DatabaseUtils.getInt(rs, "recordcount", 0);
      size = DatabaseUtils.getInt(rs, "recordsize", 0);
    }
    pst.close();
    rs.close();
  }


  /**
   *  Description of the Method
   *
   *@param  sqlFilter  Description of the Parameter
   */
  private void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (action > -1) {
      sqlFilter.append("AND action = ? ");
    }
    if (enteredRangeStart != null) {
      sqlFilter.append("AND entered >= ? ");
    }
    if (enteredRangeEnd != null) {
      sqlFilter.append("AND entered <= ? ");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  pst               Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (action > -1) {
      pst.setInt(++i, action);
    }
    if (enteredRangeStart != null) {
      pst.setTimestamp(++i, enteredRangeStart);
    }
    if (enteredRangeEnd != null) {
      pst.setTimestamp(++i, enteredRangeEnd);
    }
    return i;
  }

}

