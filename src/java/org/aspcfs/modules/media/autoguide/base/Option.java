//autoguide_options_option_id_seq

package com.darkhorseventures.autoguide.base;

import java.sql.*;

/**
 *  Represents an available option that an Inventory object has
 *
 *@author     matt
 *@created    May 17, 2002
 *@version    $Id$
 */
public class Option {
  private int id = -1;
  private int inventoryId = -1;
  private String name = null;


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
   *  Sets the name attribute of the Option object
   *
   *@param  tmp  The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
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
  }
}

