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
package org.aspcfs.modules.contacts.base;

import com.darkhorseventures.framework.beans.*;
import com.darkhorseventures.framework.actions.*;
import java.sql.*;
import org.aspcfs.utils.DatabaseUtils;

/**
 *  Description of the Class
 *
 *@author     matt rajkowski
 *@created    September 16, 2004
 *@version    $Id$
 */
public class CallResult extends GenericBean {

  private int id = -1;
  private String description = null;
  private int level = -1;
  private boolean enabled = false;
  private boolean nextRequired = false;
  private int nextDays = -1;
  private int nextCallTypeId = -1;
  private boolean canceledType = false;


  /**
   *  Sets the id attribute of the CallResult object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the CallResult object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the description attribute of the CallResult object
   *
   *@param  tmp  The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   *  Sets the level attribute of the CallResult object
   *
   *@param  tmp  The new level value
   */
  public void setLevel(int tmp) {
    this.level = tmp;
  }


  /**
   *  Sets the level attribute of the CallResult object
   *
   *@param  tmp  The new level value
   */
  public void setLevel(String tmp) {
    this.level = Integer.parseInt(tmp);
  }


  /**
   *  Sets the enabled attribute of the CallResult object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the enabled attribute of the CallResult object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the nextRequired attribute of the CallResult object
   *
   *@param  tmp  The new nextRequired value
   */
  public void setNextRequired(boolean tmp) {
    this.nextRequired = tmp;
  }


  /**
   *  Sets the nextRequired attribute of the CallResult object
   *
   *@param  tmp  The new nextRequired value
   */
  public void setNextRequired(String tmp) {
    this.nextRequired = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the nextDays attribute of the CallResult object
   *
   *@param  tmp  The new nextDays value
   */
  public void setNextDays(int tmp) {
    this.nextDays = tmp;
  }


  /**
   *  Sets the nextDays attribute of the CallResult object
   *
   *@param  tmp  The new nextDays value
   */
  public void setNextDays(String tmp) {
    this.nextDays = Integer.parseInt(tmp);
  }


  /**
   *  Sets the nextCallTypeId attribute of the CallResult object
   *
   *@param  tmp  The new nextCallTypeId value
   */
  public void setNextCallTypeId(int tmp) {
    this.nextCallTypeId = tmp;
  }


  /**
   *  Sets the nextCallTypeId attribute of the CallResult object
   *
   *@param  tmp  The new nextCallTypeId value
   */
  public void setNextCallTypeId(String tmp) {
    this.nextCallTypeId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the canceledType attribute of the CallResult object
   *
   *@param  tmp  The new canceledType value
   */
  public void setCanceledType(boolean tmp) {
    this.canceledType = tmp;
  }


  /**
   *  Sets the canceledType attribute of the CallResult object
   *
   *@param  tmp  The new canceledType value
   */
  public void setCanceledType(String tmp) {
    this.canceledType = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the id attribute of the CallResult object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the description attribute of the CallResult object
   *
   *@return    The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   *  Gets the level attribute of the CallResult object
   *
   *@return    The level value
   */
  public int getLevel() {
    return level;
  }


  /**
   *  Gets the enabled attribute of the CallResult object
   *
   *@return    The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   *  Gets the nextRequired attribute of the CallResult object
   *
   *@return    The nextRequired value
   */
  public boolean getNextRequired() {
    return nextRequired;
  }


  /**
   *  Gets the nextDays attribute of the CallResult object
   *
   *@return    The nextDays value
   */
  public int getNextDays() {
    return nextDays;
  }


  /**
   *  Gets the nextCallTypeId attribute of the CallResult object
   *
   *@return    The nextCallTypeId value
   */
  public int getNextCallTypeId() {
    return nextCallTypeId;
  }


  /**
   *  Gets the canceledType attribute of the CallResult object
   *
   *@return    The canceledType value
   */
  public boolean getCanceledType() {
    return canceledType;
  }


  /**
   *  Constructor for the CallResult object
   */
  public CallResult() { }


  /**
   *  Constructor for the CallResult object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public CallResult(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Constructor for the CallResult object
   *
   *@param  db                Description of the Parameter
   *@param  resultId          Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public CallResult(Connection db, int resultId) throws SQLException {
    queryRecord(db, resultId);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  resultId          Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void queryRecord(Connection db, int resultId) throws SQLException {
    if (resultId == -1) {
      throw new SQLException("Result ID not specified.");
    }
    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT r.* " +
        "FROM lookup_call_result r " +
        "WHERE result_id = ? ");
    PreparedStatement pst = db.prepareStatement(sql.toString());
    pst.setInt(1, resultId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (id == -1) {
      throw new SQLException("Result record not found.");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  private void buildRecord(ResultSet rs) throws SQLException {
    //lookup_call_result table
    id = rs.getInt("result_id");
    description = rs.getString("description");
    level = rs.getInt("level");
    enabled = rs.getBoolean("enabled");
    nextRequired = rs.getBoolean("next_required");
    nextDays = rs.getInt("next_days");
    nextCallTypeId = DatabaseUtils.getInt(rs, "next_call_type_id");
    canceledType = rs.getBoolean("canceled_type");
  }
}

