//autoguide_options_option_id_seq

package com.darkhorseventures.autoguide.base;

import java.sql.*;

public class Option {
  private int id = -1;
  private String name = null;
  
  public Option() {}

  public Option(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }
  
  public void setId(int tmp) { this.id = tmp; }
  public void setName(String tmp) { this.name = tmp; }
  public int getId() { return id; }
  public String getName() { return name; }
  
  protected void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("option_id");
    name = rs.getString("option_name");
  }
}
