//Copyright 2001-2002 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import java.util.Vector;
import java.util.Iterator;
import java.sql.*;
import com.darkhorseventures.webutils.PagedListInfo;
import com.darkhorseventures.webutils.HtmlSelect;
import com.darkhorseventures.utils.DatabaseUtils;
import com.darkhorseventures.utils.ObjectUtils;


public class NewsArticleList extends Vector {

  public final static String tableName = "news";
  public final static String uniqueField = "rec_id";
  protected java.sql.Timestamp lastAnchor = null;
  protected java.sql.Timestamp nextAnchor = null;
  protected int syncType = Constants.NO_SYNC;
  protected PagedListInfo pagedListInfo = null;
  
  protected boolean minerOnly = true;
  protected int enteredBy = -1;
  protected int industryCode = -1;
  
  public NewsArticleList() { }


  public void setLastAnchor(java.sql.Timestamp tmp) {
    this.lastAnchor = tmp;
  }

  public void setLastAnchor(String tmp) {
    this.lastAnchor = java.sql.Timestamp.valueOf(tmp);
  }

  public void setNextAnchor(java.sql.Timestamp tmp) {
    this.nextAnchor = tmp;
  }
  
  public void setNextAnchor(String tmp) {
    this.nextAnchor = java.sql.Timestamp.valueOf(tmp);
  }

  public void setSyncType(int tmp) {
    this.syncType = tmp;
  }

  public void setSyncType(String tmp) {
    this.syncType = Integer.parseInt(tmp);
  }

  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }

  public boolean getMinerOnly() { return minerOnly; }
  public int getEnteredBy() { return enteredBy; }
  public int getIndustryCode() { return industryCode; }
  
  public void setMinerOnly(boolean tmp) { this.minerOnly = tmp; }
  
  public void setEnteredBy(int tmp) { this.enteredBy = tmp; }
  public void setIndustryCode(int tmp) { this.industryCode = tmp; }
  public void setEnteredBy(String tmp) { this.enteredBy = Integer.parseInt(tmp); }
  public void setIndustryCode(String tmp) { this.industryCode = Integer.parseInt(tmp); }

  
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
        "FROM news n " +
        "LEFT JOIN organization o ON (n.org_id = o.org_id) " +
        "WHERE n.rec_id >= 0 ");
    
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
      /**
      if (!pagedListInfo.getCurrentLetter().equals("")) {
        pst = db.prepareStatement(sqlCount.toString() +
        sqlFilter.toString() +
        "AND c.namelast < ? ");
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
      */
      
      //Determine column to sort by
      pagedListInfo.setDefaultSort("n.dateentered DESC ", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY n.dateentered DESC ");
    }
    
    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "n.* " +
        //"o.name as org_name, o.industry_temp_code as org_industry, o.miner_only as org_mineronly " +
        "FROM news n " +
        "LEFT JOIN organization o ON (n.org_id = o.org_id) " +
        "WHERE o.org_id >= 0 ");
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
      NewsArticle thisArticle = new NewsArticle(rs);
      this.addElement(thisArticle);
    }
    rs.close();
    pst.close();
  }

  protected void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }

    if (enteredBy > -1) {
      sqlFilter.append("AND o.enteredby = ? ");
    }

    if (industryCode > -1) {
      sqlFilter.append("AND o.industry_temp_code = ? ");
    }
    
    if (minerOnly) {
      sqlFilter.append("AND o.miner_only = ? ");
    }
  }
  
  protected int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;

    if (enteredBy > -1) {
      pst.setInt(++i, enteredBy);
    }

    if (industryCode > -1) {
      pst.setInt(++i, industryCode);
    }
    
    if (minerOnly) {
      pst.setBoolean(++i, minerOnly);
    }

    return i;
  }

}

