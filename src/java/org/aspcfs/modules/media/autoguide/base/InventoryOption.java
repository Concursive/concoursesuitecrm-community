//autoguide_options_option_id_seq

package org.aspcfs.modules.media.autoguide.base;

import java.sql.*;

/**
 *  Represents an available option that an Inventory object has
 *
 *@author     matt rajkowski
 *@created    June 16, 2002
 *@version    $Id: InventoryOption.java,v 1.1 2002/06/16 19:49:40 mrajkowski Exp
 *      $
 */
public class InventoryOption {
  private int inventoryId = -1;
  private int optionId = -1;


  /**
   *  Constructor for the Option object
   */
  public InventoryOption() { }


  /**
   *  Constructor for the Option object
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  public InventoryOption(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Sets the optionId attribute of the InventoryOption object
   *
   *@param  tmp  The new optionId value
   */
  public void setOptionId(int tmp) {
    this.optionId = tmp;
  }


  /**
   *  Sets the inventoryId attribute of the Option object
   *
   *@param  tmp  The new inventoryId value
   */
  public void setInventoryId(int tmp) {
    this.inventoryId = tmp;
  }


  /**
   *  Sets the accountInventoryId attribute of the InventoryOption object, used
   *  for the XML API
   *
   *@param  tmp  The new accountInventoryId value
   */
  public void setAccountInventoryId(String tmp) {
    this.inventoryId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the optionId attribute of the InventoryOption object
   *
   *@param  tmp  The new optionId value
   */
  public void setOptionId(String tmp) {
    this.optionId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the id attribute of the Option object
   *
   *@return    The id value
   */
  public int getOptionId() {
    return optionId;
  }


  /**
   *  Gets the accountInventoryId attribute of the InventoryOption object
   *
   *@return    The accountInventoryId value
   */
  public int getAccountInventoryId() {
    return this.inventoryId;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
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
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
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
   *  Description of the Method
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    inventoryId = rs.getInt("inventory_id");
    optionId = rs.getInt("option_id");
  }
}

