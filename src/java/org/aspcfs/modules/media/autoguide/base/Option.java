//autoguide_options_option_id_seq

package com.darkhorseventures.autoguide.base;

import java.sql.*;

public class Option {
  private int id = -1;
  private int inventoryId = -1;
  private String name = null;
  
  public Option() {}

  public Option(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }
  
  public void setId(int tmp) { this.id = tmp; }
  public void setInventoryId(int tmp) { this.inventoryId = tmp; }
  public void setName(String tmp) { this.name = tmp; }
  public int getId() { return id; }
  public String getName() { return name; }
  
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
  
  protected void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("option_id");
    name = rs.getString("option_name");
  }
}
