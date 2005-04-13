package org.aspcfs.modules.orders.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.sql.*;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.modules.troubletickets.base.*;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.SyncableList;
import java.util.Calendar;

/**
 *  Description of the Class
 *
 *@author     partha
 *@created    May 12, 2004
 *@version    $Id$
 */
public class OrderPaymentStatusList extends ArrayList implements SyncableList {
  //sync api
  /**
   *  Description of the Field
   */
  public final static String tableName = "order_payment_status";
  /**
   *  Description of the Field
   */
  public final static String uniqueField = "payment_status_id";
  private Timestamp lastAnchor = null;
  private Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;
  //filters
  private PagedListInfo pagedListInfo = null;
  private int enteredBy = -1;
  private int id = -1;
  private int statusId = -1;
  private int paymentId = -1;
  private String status = null;


  /**
   *  Sets the lastAnchor attribute of the OrderPaymentStatusList object
   *
   *@param  tmp  The new lastAnchor value
   */
  public void setLastAnchor(Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   *  Sets the lastAnchor attribute of the OrderPaymentStatusList object
   *
   *@param  tmp  The new lastAnchor value
   */
  public void setLastAnchor(String tmp) {
    this.lastAnchor = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the nextAnchor attribute of the OrderPaymentStatusList object
   *
   *@param  tmp  The new nextAnchor value
   */
  public void setNextAnchor(Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   *  Sets the nextAnchor attribute of the OrderPaymentStatusList object
   *
   *@param  tmp  The new nextAnchor value
   */
  public void setNextAnchor(String tmp) {
    this.nextAnchor = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the syncType attribute of the OrderPaymentStatusList object
   *
   *@param  tmp  The new syncType value
   */
  public void setSyncType(int tmp) {
    this.syncType = tmp;
  }


  /**
   *  Sets the syncType attribute of the OrderPaymentStatusList object
   *
   *@param  tmp  The new syncType value
   */
  public void setSyncType(String tmp) {
    this.syncType = Integer.parseInt(tmp);
  }


  /**
   *  Sets the pagedListInfo attribute of the OrderPaymentStatusList object
   *
   *@param  tmp  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the OrderPaymentStatusList object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the OrderPaymentStatusList object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the id attribute of the OrderPaymentStatusList object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the OrderPaymentStatusList object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the statusId attribute of the OrderPaymentStatusList object
   *
   *@param  tmp  The new statusId value
   */
  public void setStatusId(int tmp) {
    this.statusId = tmp;
  }


  /**
   *  Sets the statusId attribute of the OrderPaymentStatusList object
   *
   *@param  tmp  The new statusId value
   */
  public void setStatusId(String tmp) {
    this.statusId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the paymentId attribute of the OrderPaymentStatusList object
   *
   *@param  tmp  The new paymentId value
   */
  public void setPaymentId(int tmp) {
    this.paymentId = tmp;
  }


  /**
   *  Sets the paymentId attribute of the OrderPaymentStatusList object
   *
   *@param  tmp  The new paymentId value
   */
  public void setPaymentId(String tmp) {
    this.paymentId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the status attribute of the OrderPaymentStatusList object
   *
   *@param  tmp  The new status value
   */
  public void setStatus(String tmp) {
    this.status = tmp;
  }


  /**
   *  Gets the tableName attribute of the OrderPaymentStatusList object
   *
   *@return    The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   *  Gets the uniqueField attribute of the OrderPaymentStatusList object
   *
   *@return    The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }


  /**
   *  Gets the status attribute of the OrderPaymentStatusList object
   *
   *@return    The status value
   */
  public String getStatus() {
    return status;
  }


  /**
   *  Gets the lastAnchor attribute of the OrderPaymentStatusList object
   *
   *@return    The lastAnchor value
   */
  public Timestamp getLastAnchor() {
    return lastAnchor;
  }


  /**
   *  Gets the nextAnchor attribute of the OrderPaymentStatusList object
   *
   *@return    The nextAnchor value
   */
  public Timestamp getNextAnchor() {
    return nextAnchor;
  }


  /**
   *  Gets the syncType attribute of the OrderPaymentStatusList object
   *
   *@return    The syncType value
   */
  public int getSyncType() {
    return syncType;
  }


  /**
   *  Gets the pagedListInfo attribute of the OrderPaymentStatusList object
   *
   *@return    The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *  Gets the enteredBy attribute of the OrderPaymentStatusList object
   *
   *@return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the id attribute of the OrderPaymentStatusList object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the statusId attribute of the OrderPaymentStatusList object
   *
   *@return    The statusId value
   */
  public int getStatusId() {
    return statusId;
  }


  /**
   *  Gets the paymentId attribute of the OrderPaymentStatusList object
   *
   *@return    The paymentId value
   */
  public int getPaymentId() {
    return paymentId;
  }


  /**
   *  Constructor for the OrderPaymentStatusList object
   *
   *@exception  SQLException  Description of the Exception
   */
  public OrderPaymentStatusList() throws SQLException { }


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
        " SELECT COUNT(ops.*) AS recordcount " +
        " FROM order_payment_status ops " +
        " LEFT JOIN lookup_payment_status ps " +
        " ON ( ops.status_id = ps.code ) " +
        " WHERE ops.payment_status_id > 0 "
        );

    createFilter(sqlFilter, db);
    sqlOrder.append("ORDER BY ops.entered ");
    if (pagedListInfo != null) {
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
    }
    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append(" SELECT ");
    }
    sqlSelect.append(
        " ops.*, ps.description AS status_description " +
        " FROM order_payment_status ops " +
        " LEFT JOIN lookup_payment_status ps " +
        " ON ( ops.status_id = ps.code ) " +
        " WHERE ops.payment_status_id > 0 "
        );
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
      OrderPaymentStatus orderPaymentStatus = new OrderPaymentStatus(rs);
      this.add(orderPaymentStatus);
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
    Iterator statusIterator = this.iterator();
    while (statusIterator.hasNext()) {
      OrderPaymentStatus orderPaymentStatus = (OrderPaymentStatus) statusIterator.next();
      orderPaymentStatus.delete(db);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  sqlFilter  Description of the Parameter
   *@param  db         Description of the Parameter
   */
  private void createFilter(StringBuffer sqlFilter, Connection db) {

    if (enteredBy > -1) {
      sqlFilter.append(" AND ops.enteredby = ? ");
    }

    if (id > -1) {
      sqlFilter.append(" AND ops.payment_status_id = ? ");
    }

    if (paymentId > -1) {
      sqlFilter.append(" AND ops.payment_id = ? ");
    }
    if (statusId > -1) {
      sqlFilter.append(" AND ops.status_id = ? ");
    }
    if (status != null) {
      sqlFilter.append(" AND ps.description = ? ");
    }

    //Sync API
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        sqlFilter.append("AND pctlg.entered >= ? ");
      }
      sqlFilter.append(" AND pctlg.entered < ? ");
    } else if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append(" AND pctlg.modified >= ? ");
      sqlFilter.append(" AND pctlg.entered < ? ");
      sqlFilter.append(" AND pctlg.modified < ? ");
    } else if (syncType == Constants.SYNC_QUERY) {
      if (lastAnchor != null) {
        sqlFilter.append("AND pctlg.entered >= ? ");
      }
      if (nextAnchor != null) {
        sqlFilter.append("AND pctlg.entered < ? ");
      }
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

    if (enteredBy > -1) {
      pst.setInt(++i, enteredBy);
    }

    if (id > -1) {
      pst.setInt(++i, id);
    }
    if (paymentId > -1) {
      pst.setInt(++i, this.getPaymentId());
    }
    if (statusId > -1) {
      pst.setInt(++i, this.getStatusId());
    }

    if (status != null) {
      pst.setString(++i, this.getStatus());
    }

    //Sync API
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        pst.setTimestamp(++i, lastAnchor);
      }
      pst.setTimestamp(++i, nextAnchor);
    } else if (syncType == Constants.SYNC_UPDATES) {
      pst.setTimestamp(++i, lastAnchor);
      pst.setTimestamp(++i, lastAnchor);
      pst.setTimestamp(++i, nextAnchor);
    } else if (syncType == Constants.SYNC_QUERY) {
      if (lastAnchor != null) {
        pst.setTimestamp(++i, lastAnchor);
      }
      if (nextAnchor != null) {
        pst.setTimestamp(++i, nextAnchor);
      }
    }
    return i;
  }

}

