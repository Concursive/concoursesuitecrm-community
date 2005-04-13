/*
 *  Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. DARK HORSE
 *  VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.servicecontracts.base;

import java.util.*;
import java.sql.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.framework.beans.*;
import com.darkhorseventures.database.*;
import com.darkhorseventures.framework.actions.*;
import org.aspcfs.utils.*;
import org.aspcfs.modules.base.*;

/**
 *  Description of the Class
 *
 *@author     kbhoopal
 *@created    December 31, 2003
 *@version    $Id: ServiceContract.java,v 1.1.2.2 2004/01/08 18:50:49 kbhoopal
 *      Exp $
 */
public class ServiceContractHours extends GenericBean {

  private int id = -1;
  private int serviceContractId = -1;
  private double adjustmentHours = 0;
  private int adjustmentReason = -1;
  private String adjustmentNotes = null;
  private java.sql.Timestamp entered = null;
  private int enteredBy = -1;
  private java.sql.Timestamp modified = null;
  private int modifiedBy = -1;


  /**
   *  Constructor for the ServiceContractHours object
   */
  public ServiceContractHours() { }


  /**
   *  Constructor for the ServiceContractHours object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public ServiceContractHours(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Sets the id attribute of the ServiceContractHours object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the ServiceContractHours object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the serviceContractId attribute of the ServiceContractHours object
   *
   *@param  tmp  The new serviceContractId value
   */
  public void setServiceContractId(int tmp) {
    this.serviceContractId = tmp;
  }


  /**
   *  Sets the serviceContractId attribute of the ServiceContractHours object
   *
   *@param  tmp  The new serviceContractId value
   */
  public void setServiceContractId(String tmp) {
    this.serviceContractId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the adjustmentHours attribute of the ServiceContractHours object
   *
   *@param  tmp  The new adjustmentHours value
   */
  public void setAdjustmentHours(double tmp) {
    this.adjustmentHours = tmp;
  }


  /**
   *  Sets the adjustmentHours attribute of the ServiceContractHours object
   *
   *@param  tmp  The new adjustmentHours value
   */
  public void setAdjustmentHours(String tmp) {
    this.adjustmentHours = Double.parseDouble(tmp);
  }


  /**
   *  Sets the adjustmentReason attribute of the ServiceContractHours object
   *
   *@param  tmp  The new adjustmentReason value
   */
  public void setAdjustmentReason(int tmp) {
    this.adjustmentReason = tmp;
  }


  /**
   *  Sets the adjustmentReason attribute of the ServiceContractHours object
   *
   *@param  tmp  The new adjustmentReason value
   */
  public void setAdjustmentReason(String tmp) {
    this.adjustmentReason = Integer.parseInt(tmp);
  }


  /**
   *  Sets the adjustmentNotes attribute of the ServiceContractHours object
   *
   *@param  tmp  The new adjustmentNotes value
   */
  public void setAdjustmentNotes(String tmp) {
    this.adjustmentNotes = tmp;
  }


  /**
   *  Sets the entered attribute of the ServiceContractHours object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the entered attribute of the ServiceContractHours object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the enteredBy attribute of the ServiceContractHours object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the ServiceContractHours object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the modified attribute of the ServiceContractHours object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the modified attribute of the ServiceContractHours object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the modifiedBy attribute of the ServiceContractHours object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the ServiceContractHours object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Gets the id attribute of the ServiceContractHours object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the serviceContractId attribute of the ServiceContractHours object
   *
   *@return    The serviceContractId value
   */
  public int getServiceContractId() {
    return serviceContractId;
  }


  /**
   *  Gets the adjustmentHours attribute of the ServiceContractHours object
   *
   *@return    The adjustmentHours value
   */
  public double getAdjustmentHours() {
    return round(adjustmentHours, 2);
  }


  /**
   *  Gets the adjustmentReason attribute of the ServiceContractHours object
   *
   *@return    The adjustmentReason value
   */
  public int getAdjustmentReason() {
    return adjustmentReason;
  }


  /**
   *  Gets the adjustmentNotes attribute of the ServiceContractHours object
   *
   *@return    The adjustmentNotes value
   */
  public String getAdjustmentNotes() {
    return adjustmentNotes;
  }


  /**
   *  Gets the entered attribute of the ServiceContractHours object
   *
   *@return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the enteredBy attribute of the ServiceContractHours object
   *
   *@return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the modified attribute of the ServiceContractHours object
   *
   *@return    The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the modifiedBy attribute of the ServiceContractHours object
   *
   *@return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void delete(Connection db) throws SQLException {
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "DELETE  " +
        "FROM service_contract_hours " +
        "WHERE history_id = ? ");

    pst = db.prepareStatement(sql.toString());
    int i = 0;
    pst.setInt(++i, id);
    pst.execute();
    pst.close();

  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void queryRecord(Connection db) throws SQLException {
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT * " +
        "FROM service_contract_hours " +
        "WHERE id = ? ");

    pst = db.prepareStatement(sql.toString());
    int i = 0;
    pst.setInt(++i, id);

    pst.executeQuery();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    int resultCount = -1;
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "INSERT INTO service_contract_hours " +
        "(link_contract_id, " +
        "adjustment_hours, " +
        "adjustment_reason, " +
        "adjustment_notes, " +
        "enteredby , " +
        "modifiedby ) " +
        "VALUES " +
        "(?,?,?,?,?,?)");

    pst = db.prepareStatement(sql.toString());

    int i = 0;
    pst.setInt(++i, serviceContractId);
    pst.setDouble(++i, adjustmentHours);
    DatabaseUtils.setInt(pst, ++i, adjustmentReason);
    pst.setString(++i, adjustmentNotes);
    pst.setInt(++i, enteredBy);
    pst.setInt(++i, modifiedBy);

    pst.execute();
    id = DatabaseUtils.getCurrVal(db, "service_contract_hours_history_id_seq");
    pst.close();

    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("history_id");
    serviceContractId = rs.getInt("link_contract_id");
    adjustmentHours = rs.getFloat("adjustment_hours");
    adjustmentReason = rs.getInt("adjustment_reason");
    adjustmentNotes = rs.getString("adjustment_notes");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
  }

}

