/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.media.autoguide.base;

import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.SyncableList;
import org.aspcfs.utils.DatabaseUtils;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * A list of possible options a Vehicle can have
 *
 * @author matt rajkowski
 * @version $Id$
 * @created May 17, 2002
 */
public class OptionList extends ArrayList implements SyncableList {

  public static String tableName = "autoguide_options";
  public static String uniqueField = "option_id";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;
  private int inventoryId = -1;


  /**
   * Constructor for the OptionList object
   */
  public OptionList() {
  }


  /**
   * Constructor for the OptionList object
   *
   * @param db Description of Parameter
   * @throws SQLException Description of Exception
   */
  public OptionList(Connection db) throws SQLException {
    buildList(db);
  }


  /**
   * Constructor for the OptionList object
   *
   * @param request Description of Parameter
   */
  public OptionList(HttpServletRequest request) {
    int i = 0;
    String thisId = null;
    while ((thisId = request.getParameter("option" + (++i) + "id")) != null) {
      String checked = request.getParameter("option" + thisId);
      if ("on".equalsIgnoreCase(checked)) {
        Option thisOption = new Option();
        thisOption.setId(Integer.parseInt(thisId));
        this.add(thisOption);
      }
    }
  }


  /**
   * Sets the lastAnchor attribute of the OptionList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(java.sql.Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   * Sets the lastAnchor attribute of the OptionList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(String tmp) {
    this.lastAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the nextAnchor attribute of the OptionList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(java.sql.Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   * Sets the nextAnchor attribute of the OptionList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(String tmp) {
    this.nextAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the syncType attribute of the OptionList object
   *
   * @param tmp The new syncType value
   */
  public void setSyncType(int tmp) {
    this.syncType = tmp;
  }


  /**
   * Sets the syncType attribute of the OptionList object
   *
   * @param tmp The new syncType value
   */
  public void setSyncType(String tmp) {
    this.syncType = Integer.parseInt(tmp);
  }


  /**
   * Sets the inventoryId attribute of the OptionList object
   *
   * @param tmp The new inventoryId value
   */
  public void setInventoryId(int tmp) {
    this.inventoryId = tmp;
  }


  /**
   * Sets the inventoryId attribute of the OptionList object
   *
   * @param tmp The new inventoryId value
   */
  public void setInventoryId(String tmp) {
    this.inventoryId = Integer.parseInt(tmp);
  }


  /**
   * Sets the accountInventoryId attribute of the OptionList object
   *
   * @param tmp The new accountInventoryId value
   */
  public void setAccountInventoryId(int tmp) {
    this.setInventoryId(tmp);
  }


  /**
   * Sets the accountInventoryId attribute of the OptionList object
   *
   * @param tmp The new accountInventoryId value
   */
  public void setAccountInventoryId(String tmp) {
    this.setInventoryId(tmp);
  }


  /**
   * Gets the tableName attribute of the OptionList object
   *
   * @return The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   * Gets the uniqueField attribute of the OptionList object
   *
   * @return The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }


  /**
   * Gets the inventoryId attribute of the OptionList object
   *
   * @return The inventoryId value
   */
  public int getInventoryId() {
    return inventoryId;
  }


  /**
   * Gets the object attribute of the OptionList object
   *
   * @param rs Description of Parameter
   * @return The object value
   * @throws SQLException Description of Exception
   */
  public Option getObject(ResultSet rs) throws SQLException {
    Option thisOption = new Option(rs);
    return thisOption;
  }


  /**
   * Description of the Method
   *
   * @param optionId Description of Parameter
   * @return Description of the Returned Value
   */
  public boolean hasOption(int optionId) {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Option thisOption = (Option) i.next();
      if (thisOption.getId() == optionId) {
        return true;
      }
    }
    return false;
  }


  /**
   * Description of the Method
   *
   * @param db Description of Parameter
   * @throws SQLException Description of Exception
   */
  public void select(Connection db) throws SQLException {
    buildList(db);
  }


  /**
   * Description of the Method
   *
   * @param db Description of Parameter
   * @throws SQLException Description of Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = prepareList(db);
    ResultSet rs = DatabaseUtils.executeQuery(db, pst);
    while (rs.next()) {
      Option thisOption = this.getObject(rs);
      this.add(thisOption);
    }
    rs.close();
    if (pst != null) {
      pst.close();
    }
  }


  /**
   * This method is required for synchronization, it allows for the resultset
   * to be streamed with lower overhead
   *
   * @param db  Description of Parameter
   * @return Description of the Returned Value
   * @throws SQLException Description of Exception
   */
  public PreparedStatement prepareList(Connection db) throws SQLException {
    int items = -1;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT o.option_id, o.option_name, o.entered, o.modified " +
        "FROM autoguide_options o ");
    if (inventoryId > -1) {
      sql.append(", autoguide_inventory_options io ");
    }
    sql.append("WHERE o.option_id > -1 ");
    createFilter(sql);
    sql.append("ORDER BY " + DatabaseUtils.addQuotes(db, "level") + ", o.option_name ");
    PreparedStatement pst = db.prepareStatement(sql.toString());
    items = prepareFilter(pst);
    return pst;
  }


  /**
   * Description of the Method
   *
   * @param db Description of Parameter
   * @throws SQLException Description of Exception
   */
  public void insert(Connection db) throws SQLException {
    Iterator optionList = this.iterator();
    while (optionList.hasNext()) {
      Option thisOption = (Option) optionList.next();
      thisOption.setInventoryId(inventoryId);
      thisOption.insert(db);
    }
  }


  /**
   * Description of the Method
   *
   * @param db Description of Parameter
   * @throws SQLException Description of Exception
   */
  public void update(Connection db) throws SQLException {
    this.delete(db);
    this.insert(db);
  }


  /**
   * Description of the Method
   *
   * @param db Description of Parameter
   * @throws SQLException Description of Exception
   */
  public void delete(Connection db) throws SQLException {
    String sql =
        "DELETE FROM autoguide_inventory_options " +
        "WHERE inventory_id = ? ";
    PreparedStatement pst = db.prepareStatement(sql);
    pst.setInt(1, inventoryId);
    pst.execute();
    pst.close();
  }


  /**
   * Description of the Method
   *
   * @param sqlFilter Description of Parameter
   */
  private void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        sqlFilter.append("AND o.entered > ? ");
      }
      sqlFilter.append("AND o.entered < ? ");
    }
    if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append("AND o.modified > ? ");
      sqlFilter.append("AND o.entered < ? ");
      sqlFilter.append("AND o.modified < ? ");
    }
    if (inventoryId > -1) {
      sqlFilter.append(
          "AND o.option_id = io.option_id AND io.inventory_id = ? ");
    }
  }


  /**
   * Description of the Method
   *
   * @param pst Description of Parameter
   * @return Description of the Returned Value
   * @throws SQLException Description of Exception
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        pst.setTimestamp(++i, lastAnchor);
      }
      pst.setTimestamp(++i, nextAnchor);
    }
    if (syncType == Constants.SYNC_UPDATES) {
      pst.setTimestamp(++i, lastAnchor);
      pst.setTimestamp(++i, lastAnchor);
      pst.setTimestamp(++i, nextAnchor);
    }
    if (inventoryId > -1) {
      pst.setInt(++i, inventoryId);
    }
    return i;
  }
}

