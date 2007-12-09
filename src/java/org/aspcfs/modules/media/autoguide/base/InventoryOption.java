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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Represents an available option that an Inventory object has
 *
 * @author matt rajkowski
 * @version $Id: InventoryOption.java,v 1.1 2002/06/16 19:49:40 mrajkowski Exp
 *          $
 * @created June 16, 2002
 */
public class InventoryOption {
  private int inventoryId = -1;
  private int optionId = -1;


  /**
   * Constructor for the Option object
   */
  public InventoryOption() {
  }


  /**
   * Constructor for the Option object
   *
   * @param rs Description of Parameter
   * @throws SQLException Description of Exception
   */
  public InventoryOption(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Sets the optionId attribute of the InventoryOption object
   *
   * @param tmp The new optionId value
   */
  public void setOptionId(int tmp) {
    this.optionId = tmp;
  }


  /**
   * Sets the inventoryId attribute of the Option object
   *
   * @param tmp The new inventoryId value
   */
  public void setInventoryId(int tmp) {
    this.inventoryId = tmp;
  }


  /**
   * Sets the accountInventoryId attribute of the InventoryOption object, used
   * for the XML API
   *
   * @param tmp The new accountInventoryId value
   */
  public void setAccountInventoryId(String tmp) {
    this.inventoryId = Integer.parseInt(tmp);
  }


  /**
   * Sets the optionId attribute of the InventoryOption object
   *
   * @param tmp The new optionId value
   */
  public void setOptionId(String tmp) {
    this.optionId = Integer.parseInt(tmp);
  }


  /**
   * Gets the id attribute of the Option object
   *
   * @return The id value
   */
  public int getOptionId() {
    return optionId;
  }


  /**
   * Gets the accountInventoryId attribute of the InventoryOption object
   *
   * @return The accountInventoryId value
   */
  public int getAccountInventoryId() {
    return this.inventoryId;
  }


  /**
   * Description of the Method
   *
   * @param db Description of Parameter
   * @throws SQLException Description of Exception
   */
  public void insert(Connection db) throws SQLException {
    StringBuffer sql = new StringBuffer();
    sql.append(
        "INSERT INTO autoguide_inventory_options " +
        "(inventory_id, option_id) " +
        "VALUES (?, ?)");
    PreparedStatement pst = db.prepareStatement(sql.toString());
    pst.setInt(1, inventoryId);
    pst.setInt(2, optionId);
    pst.execute();
    pst.close();
  }


  /**
   * Description of the Method
   *
   * @param db Description of Parameter
   * @throws SQLException Description of Exception
   */
  public void delete(Connection db) throws SQLException {
    StringBuffer sql = new StringBuffer();
    sql.append(
        "DELETE FROM autoguide_inventory_options " +
        "WHERE inventory_id = ? AND option_id = ? ");
    PreparedStatement pst = db.prepareStatement(sql.toString());
    pst.setInt(1, inventoryId);
    pst.setInt(2, optionId);
    pst.execute();
    pst.close();
  }


  /**
   * Description of the Method
   *
   * @param rs Description of Parameter
   * @throws SQLException Description of Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    inventoryId = rs.getInt("inventory_id");
    optionId = rs.getInt("option_id");
  }
}

