//Copyright 2001 Dark Horse Ventures
//The createFilter method and the prepareFilter method need to have the same
//number of parameters if modified.

package org.aspcfs.modules.servicecontracts.base;

import java.sql.*;
import java.text.*;
import java.util.*;
import org.aspcfs.utils.web.*;

/**
 *  Description of the Class
 *
 *@author     kbhoopal
 *@created    December 23, 2003
 *@version    $Id: ServiceContractList.java,v 1.1.2.4 2004/01/14 22:55:03
 *      kbhoopal Exp $
 */
public class ServiceContractHoursList extends ArrayList {

  private PagedListInfo pagedListInfo = null;
  private int id = -1;
  private int contractId = -1;


  /**
   *  Constructor for the ServiceContractHoursList object
   */
  public ServiceContractHoursList() { }


  /**
   *  Constructor for the ServiceContractHoursList object
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public ServiceContractHoursList(Connection db) throws SQLException { }


  /**
   *  Sets the pagedListInfo attribute of the ServiceContractList object
   *
   *@param  tmp  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Sets the id attribute of the ServiceContractList object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the ServiceContractList object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the contractId attribute of the ServiceContractHoursList object
   *
   *@param  tmp  The new contractId value
   */
  public void setContractId(int tmp) {
    this.contractId = tmp;
  }


  /**
   *  Sets the contractId attribute of the ServiceContractHoursList object
   *
   *@param  tmp  The new contractId value
   */
  public void setContractId(String tmp) {
    this.contractId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the pagedListInfo attribute of the ServiceContractList object
   *
   *@return    The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *  Gets the id attribute of the ServiceContractList object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the contractId attribute of the ServiceContractList object
   *
   *@return    The contractId value
   */
  public int getContractId() {
    return contractId;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = queryList(db, pst);
    while (rs.next()) {
      if (pagedListInfo != null && pagedListInfo.isEndOfOffset(db)) {
        break;
      }
      ServiceContractHours thisContractHours = this.getObject(rs);
      this.add(thisContractHours);
    }
    rs.close();
    if (pst != null) {
      pst.close();
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  pst               Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
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
        "FROM service_contract_hours " +
        "WHERE history_id > -1 ");

    createFilter(sqlFilter, db);

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

      //Determine column to sort by
      pagedListInfo.setDefaultSort("entered", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY entered ");
    }

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        " * " +
        "FROM service_contract_hours " +
        "WHERE history_id > -1 ");
    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }
    return rs;
  }


  /**
   *  Description of the Method
   *
   *@param  sqlFilter         Description of the Parameter
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  private void createFilter(StringBuffer sqlFilter, Connection db) throws SQLException {

    if (contractId > -1) {
      sqlFilter.append("AND link_contract_id = ? ");
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

    if (contractId > -1) {
      pst.setInt(++i, contractId);
    }

    return i;
  }


  /**
   *  Gets the object attribute of the ServiceContractList object
   *
   *@param  rs                Description of the Parameter
   *@return                   The object value
   *@exception  SQLException  Description of the Exception
   */
  public ServiceContractHours getObject(ResultSet rs) throws SQLException {
    ServiceContractHours thisContractHours = new ServiceContractHours(rs);
    return thisContractHours;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void delete(Connection db) throws SQLException {
    Iterator hours = this.iterator();
    while (hours.hasNext()) {
      ServiceContractHours thisHours = (ServiceContractHours) hours.next();
      thisHours.delete(db);
    }
  }

}

