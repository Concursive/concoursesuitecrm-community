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
 *@author     matt rajkowski
 *@created    March 26, 2002
 *@version    $Id: SearchCriteriaListList.java,v 1.10 2003/03/07 14:13:39
 *      mrajkowski Exp $
 */
public class SearchCriteriaListList extends ArrayList {
  //Properties to filter the resulting list
  private PagedListInfo pagedListInfo = null;
  private int owner = -1;
  private String ownerIdRange = null;
  private int campaignId = -1;
  private int enabled = Constants.TRUE;
  private boolean buildCriteria = false;
  //SyncXML API properties
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


  /**
   *  Gets the tableName attribute of the SearchCriteriaListList object
   *
   *@return    The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   *  Gets the uniqueField attribute of the SearchCriteriaListList object
   *
   *@return    The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }


  /**
   *  Gets the lastAnchor attribute of the SearchCriteriaListList object
   *
   *@return    The lastAnchor value
   */
  public java.sql.Timestamp getLastAnchor() {
    return lastAnchor;
  }


  /**
   *  Gets the nextAnchor attribute of the SearchCriteriaListList object
   *
   *@return    The nextAnchor value
   */
  public java.sql.Timestamp getNextAnchor() {
    return nextAnchor;
  }


  /**
   *  Gets the syncType attribute of the SearchCriteriaListList object
   *
   *@return    The syncType value
   */
  public int getSyncType() {
    return syncType;
  }


  /**
   *  Sets the lastAnchor attribute of the SearchCriteriaListList object
   *
   *@param  tmp  The new lastAnchor value
   */
  public void setLastAnchor(java.sql.Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   *  Sets the nextAnchor attribute of the SearchCriteriaListList object
   *
   *@param  tmp  The new nextAnchor value
   */
  public void setNextAnchor(java.sql.Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   *  Sets the syncType attribute of the SearchCriteriaListList object
   *
   *@param  tmp  The new syncType value
   */
  public void setSyncType(int tmp) {
    this.syncType = tmp;
  }


  /**
   *  Sets the campaignId attribute of the SearchCriteriaListList object
   *
   *@param  tmp  The new campaignId value
   */
  public void setCampaignId(int tmp) {
    this.campaignId = tmp;
  }


  /**
   *  Sets the campaignId attribute of the SearchCriteriaListList object
   *
   *@param  tmp  The new campaignId value
   */
  public void setCampaignId(String tmp) {
    this.campaignId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the buildCriteria attribute of the SearchCriteriaListList object
   *
   *@param  tmp  The new buildCriteria value
   */
  public void setBuildCriteria(boolean tmp) {
    this.buildCriteria = tmp;
  }


  /**
   *  Sets the buildCriteria attribute of the SearchCriteriaListList object
   *
   *@param  tmp  The new buildCriteria value
   */
  public void setBuildCriteria(String tmp) {
    this.buildCriteria = DatabaseUtils.parseBoolean(tmp);
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
        "WHERE scl.id > -1 ");
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
      rs.close();
      pst.close();
      //Determine the offset, based on the filter, for the first record to show
      if (!pagedListInfo.getCurrentLetter().equals("")) {
        pst = db.prepareStatement(sqlCount.toString() +
            sqlFilter.toString() +
            "AND lower(name) < ? ");
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
        "WHERE scl.id > -1 ");
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
    if (buildCriteria) {
      Iterator i = this.iterator();
      while (i.hasNext()) {
        SearchCriteriaList thisList = (SearchCriteriaList) i.next();
        thisList.buildResources(db);
      }
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
    if (enabled != Constants.UNDEFINED) {
      sqlFilter.append("AND enabled = ? ");
    }
    if (owner != -1) {
      sqlFilter.append("AND scl.owner = ? ");
    }
    if (ownerIdRange != null) {
      sqlFilter.append("AND scl.owner IN (" + ownerIdRange + ") ");
    }
    if (campaignId != -1) {
      sqlFilter.append("AND id in (SELECT group_id FROM campaign_list_groups WHERE campaign_id = ?) ");
    }
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
    if (enabled != Constants.UNDEFINED) {
      pst.setBoolean(++i, (enabled == Constants.TRUE ? true : false));
    }
    if (owner != -1) {
      pst.setInt(++i, owner);
    }
    if (campaignId != -1) {
      pst.setInt(++i, campaignId);
    }
    return i;
  }


  /**
   *  Description of the Method
   *
   *@param  listB  Description of the Parameter
   *@return        Description of the Return Value
   */
  public boolean containsItem(SearchCriteriaList listB) {
    //Similar to contains, but will find copies as well
    Iterator i = this.iterator();
    while (i.hasNext()) {
      SearchCriteriaList listA = (SearchCriteriaList) i.next();
      if (listA.getId() == listB.getId()) {
        return true;
      }
    }
    return false;
  }


  /**
   *  Description of the Method
   *
   *@param  listB  Description of the Parameter
   */
  public void removeItem(SearchCriteriaList listB) {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      SearchCriteriaList listA = (SearchCriteriaList) i.next();
      if (listA.getId() == listB.getId()) {
        i.remove();
      }
    }
  }

}

