//Copyright 2001 Dark Horse Ventures
//The createFilter method and the prepareFilter method need to have the same
//number of parameters if modified.

package com.darkhorseventures.cfsbase;

import java.sql.*;
import java.text.*;
import java.util.*;
import com.darkhorseventures.webutils.PagedListInfo;
import com.darkhorseventures.webutils.HtmlSelect;
import com.darkhorseventures.utils.DatabaseUtils;

public class RevenueList extends Vector {

  private PagedListInfo pagedListInfo = null;
  private int orgId = -1;
  private int type = 0;
  private String ownerIdRange = null;
  private int owner = -1;
  private int year = -1;

  public RevenueList() { }

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
      pagedListInfo.setDefaultSort("r.description", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY r.description ");
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
  
  public void delete(Connection db) throws SQLException {
    Iterator revenue = this.iterator();
    while (revenue.hasNext()) {
      Revenue thisRevenue = (Revenue) revenue.next();
      thisRevenue.delete(db);
    }
  }
    
  public int getOrgId() {
	return orgId;
}
public void setOrgId(int orgId) {
	this.orgId = orgId;
}
public int getType() {
	return type;
}
public void setType(int type) {
	this.type = type;
}
public int getYear() {
	return year;
}
public void setYear(int year) {
	this.year = year;
}

public void setPagedListInfo(PagedListInfo pagedListInfo) {
	this.pagedListInfo = pagedListInfo;
}
public PagedListInfo getPagedListInfo() {
	return pagedListInfo;
}

public String getOwnerIdRange() {
	return ownerIdRange;
}
public void setOwnerIdRange(String ownerIdRange) {
	this.ownerIdRange = ownerIdRange;
}
public int getOwner() {
	return owner;
}
public void setOwner(int owner) {
	this.owner = owner;
}

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
}

