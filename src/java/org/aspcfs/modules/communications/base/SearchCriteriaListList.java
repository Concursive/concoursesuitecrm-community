//Copyright 2001 Dark Horse Ventures
//The createFilter method and the prepareFilter method need to have the same
//number of parameters if modified.

package org.aspcfs.modules.communications.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Hashtable;
import java.sql.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.modules.base.Constants;

/**
 *  Description of the Class
 *
 *@author     matt
 *@created    March 26, 2002
 *@version    $Id$
 */
public class SearchCriteriaListList extends ArrayList {

  private PagedListInfo pagedListInfo = null;
  private int owner = -1;
  private String ownerIdRange = null;
  private int campaignId = -1;
  private int enabled = Constants.TRUE;
  
  public final static String tableName = "saved_criterialist";
  public final static String uniqueField = "id";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;

  /**
   *  Constructor for the SearchCriteriaListList object
   */
  public SearchCriteriaListList() { }


  /**
   *  Sets the pagedListInfo attribute of the SearchCriteriaListList object
   *
   *@param  tmp  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Sets the owner attribute of the SearchCriteriaListList object
   *
   *@param  tmp  The new owner value
   */
  public void setOwner(int tmp) {
    this.owner = tmp;
  }


  /**
   *  Sets the owner attribute of the SearchCriteriaListList object
   *
   *@param  tmp  The new owner value
   */
  public void setOwner(String tmp) {
    this.owner = Integer.parseInt(tmp);
  }


  /**
   *  Sets the ownerIdRange attribute of the SearchCriteriaListList object
   *
   *@param  tmp  The new ownerIdRange value
   */
  public void setOwnerIdRange(String tmp) {
    this.ownerIdRange = tmp;
  }

public String getTableName() { return tableName; }
public String getUniqueField() { return uniqueField; }
public java.sql.Timestamp getLastAnchor() { return lastAnchor; }
public java.sql.Timestamp getNextAnchor() { return nextAnchor; }
public int getSyncType() { return syncType; }
public void setLastAnchor(java.sql.Timestamp tmp) { this.lastAnchor = tmp; }
public void setNextAnchor(java.sql.Timestamp tmp) { this.nextAnchor = tmp; }
public void setSyncType(int tmp) { this.syncType = tmp; }

  /**
   *  Sets the campaignId attribute of the SearchCriteriaListList object
   *
   *@param  tmp  The new campaignId value
   */
  public void setCampaignId(int tmp) {
    this.campaignId = tmp;
  }
  
  public void setCampaignId(String tmp) {
    this.campaignId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the pagedListInfo attribute of the SearchCriteriaListList object
   *
   *@return    The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *  Gets the owner attribute of the SearchCriteriaListList object
   *
   *@return    The owner value
   */
  public int getOwner() {
    return owner;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
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
        "FROM saved_criterialist scl " +
        "WHERE scl.id > -1 AND enabled = " + DatabaseUtils.getTrue(db) + " ");

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
            "AND name < ? ");
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
      pagedListInfo.setDefaultSort("name", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY name ");
    }

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "scl.* " +
        "FROM saved_criterialist scl " +
        "WHERE scl.id > -1 AND enabled = " + DatabaseUtils.getTrue(db) + " ");
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
      SearchCriteriaList thisList = new SearchCriteriaList(rs);
      this.add(thisList);
    }
    rs.close();
    pst.close();

    Iterator i = this.iterator();
    while (i.hasNext()) {
      SearchCriteriaList thisList = (SearchCriteriaList) i.next();
      thisList.buildResources(db);
    }
  }


  /**
   *  Builds a base SQL where statement for filtering records to be used by
   *  sqlSelect and sqlCount
   *
   *@param  sqlFilter  Description of Parameter
   *@since             1.3
   */
  private void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }

    if (owner != -1) {
      sqlFilter.append("AND scl.owner = ? ");
    }

    if (ownerIdRange != null) {
      sqlFilter.append("AND scl.owner IN (" + ownerIdRange + ") ");
    }

    if (campaignId != -1) {
      sqlFilter.append("AND id in (SELECT group_id FROM campaign_list_groups WHERE campaign_id = " + campaignId + ") ");
    }
    /**
    if (enabled == Constants.TRUE || enabled == Constants.FALSE) {
      sqlFilter.append("AND enabled = ? ");
    }
    */
  }


  /**
   *  Sets the parameters for the preparedStatement - these items must
   *  correspond with the createFilter statement
   *
   *@param  pst               Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since                    1.3
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;

    if (owner != -1) {
      pst.setInt(++i, owner);
    }
    
    /**
    if (enabled == Constants.TRUE || enabled == Constants.FALSE) {
      switch (enabled) {
        case Constants.TRUE: pst.setBoolean(++i, true); break;
        case Constants.FALSE: pst.setBoolean(++i, false); break;
        default: break;
      }
    }
    */
    
    return i;
  }
  
  public boolean containsItem(SearchCriteriaList listB) {
    //Similar to contains, but will find copies as well
    Iterator i = this.iterator();
    while (i.hasNext()) {
      SearchCriteriaList listA = (SearchCriteriaList)i.next();
      if (listA.getId() == listB.getId()) {
        return true;
      }
    }
    return false;
  }
  
  public void removeItem(SearchCriteriaList listB) {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      SearchCriteriaList listA = (SearchCriteriaList)i.next();
      if (listA.getId() == listB.getId()) {
        i.remove();
      }
    }
  }

}

