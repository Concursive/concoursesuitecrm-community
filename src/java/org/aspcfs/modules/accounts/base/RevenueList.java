//Copyright 2001 Dark Horse Ventures
//The createFilter method and the prepareFilter method need to have the same
//number of parameters if modified.

package org.aspcfs.modules.accounts.base;

import java.sql.*;
import java.text.*;
import java.util.*;
import com.darkhorseventures.database.Connection;
import org.aspcfs.utils.web.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.modules.accounts.base.*;
import org.aspcfs.modules.base.Constants;

/**
 *  Description of the Class
 *
 *@author     chris
 *@created    2002
 *@version    $Id$
 */
public class RevenueList extends Vector {

  private int orgId = -1;
  private int type = 0;
  private String ownerIdRange = null;
  private int owner = -1;
  private int year = -1;

  public final static String tableName = "revenue";
  public final static String uniqueField = "id";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;
  private PagedListInfo pagedListInfo = null;


  /**
   *  Constructor for the RevenueList object
   */
  public RevenueList() { }


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
        "FROM revenue r " +
        "LEFT JOIN contact ct_eb ON (r.enteredby = ct_eb.user_id) " +
        "LEFT JOIN contact ct_mb ON (r.modifiedby = ct_mb.user_id) " +
        "LEFT JOIN contact ct_own ON (r.owner = ct_own.user_id) " +
        "LEFT JOIN lookup_revenue_types rt ON (r.type = rt.code) " +
        "LEFT JOIN organization o ON (r.org_id = o.org_id) " +
        "WHERE r.id > -1 ");
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
            "AND r.description < ? ");
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
      pagedListInfo.setDefaultSort("r.year,r.month", "desc");
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY r.year desc,r.month desc ");
    }

    //System.out.println(sqlOrder.toString());

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "r.*, " +
        "ct_eb.namelast as eb_namelast, ct_eb.namefirst as eb_namefirst, " +
        "ct_mb.namelast as mb_namelast, ct_mb.namefirst as mb_namefirst, ct_own.namelast as own_namelast, ct_own.namefirst as own_namefirst, rt.description as typename, o.name as orgname " +
        "FROM revenue r " +
        "LEFT JOIN contact ct_eb ON (r.enteredby = ct_eb.user_id) " +
        "LEFT JOIN contact ct_mb ON (r.modifiedby = ct_mb.user_id) " +
        "LEFT JOIN contact ct_own ON (r.owner = ct_own.user_id) " +
        "LEFT JOIN organization o ON (r.org_id = o.org_id) " +
        "LEFT JOIN lookup_revenue_types rt ON (r.type = rt.code) " +
        "WHERE r.id > -1 ");
    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    //System.out.println(pst.toString());
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
      Revenue thisRevenue = new Revenue(rs);
      this.addElement(thisRevenue);
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
  public void delete(Connection db) throws SQLException {
    Iterator revenue = this.iterator();
    while (revenue.hasNext()) {
      Revenue thisRevenue = (Revenue) revenue.next();
      thisRevenue.delete(db);
    }
  }


  /**
   *  Gets the tableName attribute of the RevenueList object
   *
   *@return    The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   *  Gets the uniqueField attribute of the RevenueList object
   *
   *@return    The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }


  /**
   *  Gets the lastAnchor attribute of the RevenueList object
   *
   *@return    The lastAnchor value
   */
  public java.sql.Timestamp getLastAnchor() {
    return lastAnchor;
  }


  /**
   *  Gets the nextAnchor attribute of the RevenueList object
   *
   *@return    The nextAnchor value
   */
  public java.sql.Timestamp getNextAnchor() {
    return nextAnchor;
  }


  /**
   *  Gets the syncType attribute of the RevenueList object
   *
   *@return    The syncType value
   */
  public int getSyncType() {
    return syncType;
  }


  /**
   *  Sets the lastAnchor attribute of the RevenueList object
   *
   *@param  tmp  The new lastAnchor value
   */
  public void setLastAnchor(java.sql.Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   *  Sets the nextAnchor attribute of the RevenueList object
   *
   *@param  tmp  The new nextAnchor value
   */
  public void setNextAnchor(java.sql.Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   *  Sets the syncType attribute of the RevenueList object
   *
   *@param  tmp  The new syncType value
   */
  public void setSyncType(int tmp) {
    this.syncType = tmp;
  }


  /**
   *  Gets the orgId attribute of the RevenueList object
   *
   *@return    The orgId value
   */
  public int getOrgId() {
    return orgId;
  }


  /**
   *  Sets the orgId attribute of the RevenueList object
   *
   *@param  orgId  The new orgId value
   */
  public void setOrgId(int orgId) {
    this.orgId = orgId;
  }


  /**
   *  Gets the type attribute of the RevenueList object
   *
   *@return    The type value
   */
  public int getType() {
    return type;
  }


  /**
   *  Sets the type attribute of the RevenueList object
   *
   *@param  type  The new type value
   */
  public void setType(int type) {
    this.type = type;
  }


  /**
   *  Gets the year attribute of the RevenueList object
   *
   *@return    The year value
   */
  public int getYear() {
    return year;
  }


  /**
   *  Sets the year attribute of the RevenueList object
   *
   *@param  year  The new year value
   */
  public void setYear(int year) {
    this.year = year;
  }


  /**
   *  Sets the pagedListInfo attribute of the RevenueList object
   *
   *@param  pagedListInfo  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo pagedListInfo) {
    this.pagedListInfo = pagedListInfo;
  }


  /**
   *  Gets the pagedListInfo attribute of the RevenueList object
   *
   *@return    The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *  Gets the ownerIdRange attribute of the RevenueList object
   *
   *@return    The ownerIdRange value
   */
  public String getOwnerIdRange() {
    return ownerIdRange;
  }


  /**
   *  Sets the ownerIdRange attribute of the RevenueList object
   *
   *@param  ownerIdRange  The new ownerIdRange value
   */
  public void setOwnerIdRange(String ownerIdRange) {
    this.ownerIdRange = ownerIdRange;
  }


  /**
   *  Gets the owner attribute of the RevenueList object
   *
   *@return    The owner value
   */
  public int getOwner() {
    return owner;
  }


  /**
   *  Sets the owner attribute of the RevenueList object
   *
   *@param  owner  The new owner value
   */
  public void setOwner(int owner) {
    this.owner = owner;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  newOwner          Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public int reassignElements(Connection db, int newOwner) throws SQLException {
    int total = 0;
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Revenue thisRevenue = (Revenue) i.next();
      if (thisRevenue.reassign(db, newOwner)) {
        total++;
      }
    }
    return total;
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
    if (orgId != -1) {
      sqlFilter.append("AND r.org_id = ? ");
    }
    if (type > 0) {
      sqlFilter.append("AND r.type = ? ");
    }
    if (owner > -1) {
      sqlFilter.append("AND r.owner = ? ");
    }
    if (year > -1) {
      sqlFilter.append("AND r.year = ? ");
    }
    if (ownerIdRange != null) {
      sqlFilter.append("AND r.owner in (" + this.ownerIdRange + ") ");
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

    if (orgId != -1) {
      pst.setInt(++i, orgId);
    }

    if (type > 0) {
      pst.setInt(++i, type);
    }
    if (owner > -1) {
      pst.setInt(++i, owner);
    }
    if (year > -1) {
      pst.setInt(++i, year);
    }

    return i;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  moduleId          Description of the Parameter
   *@param  itemId            Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public static int retrieveRecordCount(Connection db, int moduleId, int itemId) throws SQLException {
    int count = 0;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT COUNT(*) as itemcount " +
        "FROM revenue r " +
        "WHERE id > 0 ");
    if (moduleId == Constants.ACCOUNTS) {
      sql.append("AND r.org_id = ? ");
    }
    PreparedStatement pst = db.prepareStatement(sql.toString());
    if (moduleId == Constants.ACCOUNTS) {
      pst.setInt(1, itemId);
    }
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      count = rs.getInt("itemcount");
    }
    rs.close();
    pst.close();
    return count;
  }
}

