//Copyright 2001 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import java.sql.*;
import java.text.*;

public class CustomFieldRecord {

  //Properties for a Field
  private int id = -1;
  private java.sql.Timestamp entered = null;
  private int enteredBy = -1;
  private java.sql.Timestamp modified = null;
  private int modifiedBy = -1;
  private boolean enabled = false;

  //Properties for related data
  private int linkModuleId = -1;
  private int linkItemId = -1;
  private int categoryId = -1;

  public CustomFieldRecord() { }


  public CustomFieldRecord(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }

  public void setId(int tmp) { this.id = tmp; }
  public void setEntered(java.sql.Timestamp tmp) { this.entered = tmp; }
  public void setEnteredBy(int tmp) { this.enteredBy = tmp; }
  public void setModified(java.sql.Timestamp tmp) { this.modified = tmp; }
  public void setModifiedBy(int tmp) { this.modifiedBy = tmp; }

  
  public void setLinkModuleId(int tmp) { this.linkModuleId = tmp; }
  public void setLinkItemId(int tmp) { this.linkItemId = tmp; }
  public void setCategoryId(int tmp) { this.categoryId = tmp; }
  
  public int getId() { return id; }
  public java.sql.Timestamp getEntered() { return entered; }
  public String getEnteredString() {
    String tmp = "";
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(entered);
    } catch (NullPointerException e) {
    }
    return tmp;
  }
  public int getEnteredBy() { return enteredBy; }
  public java.sql.Timestamp getModified() { return modified; }
  public String getModifiedDateTimeString() {
    String tmp = "";
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(modified);
    } catch (NullPointerException e) {
    }
    return tmp;
  }
  public int getModifiedBy() { return modifiedBy; }


  /**
   *  Description of the Method
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since                    1.1
   */
  private void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("record_id");
    linkModuleId = rs.getInt("link_module_id");
    linkItemId = rs.getInt("link_item_id");
    categoryId = rs.getInt("category_id");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
    enabled = rs.getBoolean("enabled");
  }

  public void insert(Connection db) throws SQLException {
    StringBuffer sql = new StringBuffer();
    sql.append(
        "INSERT INTO custom_field_record " +
        "(link_module_id, link_item_id, category_id, enteredby, modifiedby) " +
        "VALUES (?, ?, ?, ?, ?) ");
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql.toString());
    pst.setInt(++i, linkModuleId);
    pst.setInt(++i, linkItemId);
    pst.setInt(++i, categoryId);
    pst.setInt(++i, enteredBy);
    pst.setInt(++i, modifiedBy);
    pst.execute();
    pst.close();
    
    Statement st = db.createStatement();
    ResultSet rs = st.executeQuery("select currval('custom_field_reco_record_id_seq')");
    if (rs.next()) {
      this.setId(rs.getInt(1));
    }
    rs.close();
    st.close();
  }
  
  public void delete(Connection db) throws SQLException {
    String sql =
      "DELETE FROM custom_field_data " +
      "WHERE record_id IN (SELECT record_id FROM custom_field_record WHERE link_module_id = ? " +
      "AND category_id = ?) ";
    PreparedStatement pst = db.prepareStatement(sql);
    int i = 0;
    pst.setInt(++i, linkModuleId);
    pst.setInt(++i, categoryId);
    pst.execute();
    pst.close();
    
    sql =
      "DELETE FROM custom_field_record " +
      "WHERE link_module_id = ? " +
      "AND category_id = ? ";
    pst = db.prepareStatement(sql);
    i = 0;
    pst.setInt(++i, linkModuleId);
    pst.setInt(++i, categoryId);
    pst.execute();
    pst.close();
    System.out.println("CustomFieldRecord-> Delete Complete");
  }
  
}

