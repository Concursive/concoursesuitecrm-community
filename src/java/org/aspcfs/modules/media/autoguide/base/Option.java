//autoguide_options_option_id_seq

package org.aspcfs.modules.media.autoguide.base;

import java.sql.*;

/**
 *  Represents an available option that any vehicle object has
 *
 *@author     matt rajkowski
 *@created    May 17, 2002
 *@version    $Id$
 */
public class Option {
  private int id = -1;
  private int inventoryId = -1;
  private String name = null;
  private java.sql.Timestamp entered = null;
  private java.sql.Timestamp modified = null;


  /**
   *  Constructor for the Option object
   */
  public Option() { }


  /**
   *  Constructor for the Option object
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  public Option(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Sets the id attribute of the Option object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
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
   *  Sets the accountInventoryId attribute of the Option object
   *
   *@param  tmp  The new accountInventoryId value
   */
  public void setAccountInventoryId(String tmp) {
    this.inventoryId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the optionId attribute of the Option object
   *
   *@param  tmp  The new optionId value
   */
  public void setOptionId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the name attribute of the Option object
   *
   *@param  tmp  The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   *  Sets the entered attribute of the Option object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the modified attribute of the Option object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Gets the id attribute of the Option object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the name attribute of the Option object
   *
   *@return    The name value
   */
  public String getName() {
    return name;
  }


  /**
   *  Gets the entered attribute of the Option object
   *
   *@return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the modified attribute of the Option object
   *
   *@return    The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the accountInventoryId attribute of the Option object
   *
   *@return    The accountInventoryId value
   */
  public int getAccountInventoryId() {
    return this.inventoryId;
  }


  /**
   *  Gets the optionId attribute of the Option object
   *
   *@return    The optionId value
   */
  public int getOptionId() {
    return this.id;
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
    pst.setInt(2, id);
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
    pst.setInt(2, id);
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
    id = rs.getInt("option_id");
    name = rs.getString("option_name");
    entered = rs.getTimestamp("entered");
    modified = rs.getTimestamp("modified");
  }
}

