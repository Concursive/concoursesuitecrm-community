package org.aspcfs.modules.actionlist.base;

import java.util.*;
import java.text.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.aspcfs.modules.base.*;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.modules.contacts.base.Contact;

/**
 *  List of Action Items
 *
 * @author     akhi_m
 * @created    April 24, 2003
 */
public class ActionItemLogList extends ArrayList {
  private int itemId = -1;
  private PagedListInfo pagedListInfo = null;
  private boolean buildDetails = false;


  /**
   *  Sets the pagedListInfo attribute of the ActionItemLogList object
   *
   * @param  pagedListInfo  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo pagedListInfo) {
    this.pagedListInfo = pagedListInfo;
  }


  /**
   *  Sets the buildDetails attribute of the ActionItemLogList object
   *
   * @param  buildDetails  The new buildDetails value
   */
  public void setBuildDetails(boolean buildDetails) {
    this.buildDetails = buildDetails;
  }


  /**
   *  Sets the itemId attribute of the ActionItemLogList object
   *
   * @param  itemId  The new itemId value
   */
  public void setItemId(int itemId) {
    this.itemId = itemId;
  }


  /**
   *  Gets the itemId attribute of the ActionItemLogList object
   *
   * @return    The itemId value
   */
  public int getItemId() {
    return itemId;
  }


  /**
   *  Gets the buildDetails attribute of the ActionItemLogList object
   *
   * @return    The buildDetails value
   */
  public boolean getBuildDetails() {
    return buildDetails;
  }


  /**
   *  Gets the pagedListInfo attribute of the ActionItemLogList object
   *
   * @return    The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
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
        "FROM action_item_log al " +
        "WHERE al.log_id > -1 ");
    createFilter(sqlFilter);
    if (pagedListInfo == null) {
      pagedListInfo = new PagedListInfo();
      pagedListInfo.setItemsPerPage(0);
    }

    //Get the total number of records matching filter
    pst = db.prepareStatement(sqlCount.toString() + sqlFilter.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    if (rs.next()) {
      int maxRecords = rs.getInt("recordcount");
      pagedListInfo.setMaxRecords(maxRecords);
    }
    rs.close();
    pst.close();

    //Determine column to sort by
    pagedListInfo.setDefaultSort("al.entered", "DESC");
    pagedListInfo.appendSqlTail(db, sqlOrder);

    //Need to build a base SQL statement for returning records
    pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    sqlSelect.append(
        "al.log_id, al.item_id, al.link_item_id, al.type, " +
        "al.enteredby, al.entered, al.modifiedby, al.modified " +
        "FROM action_item_log al " +
        "WHERE al.log_id > -1 ");

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
      ActionItemLog thisList = new ActionItemLog(rs);
      this.add(thisList);
    }
    rs.close();
    pst.close();
    if (buildDetails) {
      Iterator i = this.iterator();
      while (i.hasNext()) {
        ActionItemLog thisLog = (ActionItemLog) i.next();
        thisLog.buildDescription(db);
      }
    }
  }


  /**
   *  Description of the Method
   *
   * @param  sqlFilter  Description of the Parameter
   */
  private void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (itemId > -1) {
      sqlFilter.append("AND (al.item_id = ?) ");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  pst               Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (itemId > -1) {
      pst.setInt(++i, itemId);
    }
    return i;
  }


  /**
   *  Gets the itemLinked attribute of the ActionItemLogList class
   *
   * @param  db                Description of the Parameter
   * @param  linkItemId        Description of the Parameter
   * @return                   The itemLinked value
   * @exception  SQLException  Description of the Exception
   * @deprecated
   */
  public static ActionList isItemLinked(Connection db, int linkItemId, int type) throws SQLException {
    ActionList thisList = null;
    int actionListId = -1;
    PreparedStatement pst = db.prepareStatement(
        "SELECT action_id " +
        "FROM action_item ai, action_item_log al " +
        "WHERE  al.link_item_id = ? AND al.type =? AND ai.item_id = al.item_id ");
    pst.setInt(1, linkItemId);
    pst.setInt(2, type);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      actionListId = rs.getInt("action_id");
    }
    rs.close();
    pst.close();

    if (actionListId > 0) {
      thisList = new ActionList();
      thisList.queryRecord(db, actionListId);
    }
    return thisList;
  }
}

