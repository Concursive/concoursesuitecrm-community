package com.darkhorseventures.cfsbase;

import java.sql.*;

public class RevenueDetailType {

  private int id = 0;
  private String description = null;
  private boolean enabled = true;

  public RevenueDetailType() {
  }

  public RevenueDetailType(ResultSet rs) throws java.sql.SQLException {
    id = rs.getInt("code");
    description = rs.getString("description");
    enabled = rs.getBoolean("enabled");
  }

  public void setId(int tmp) {
    this.id = tmp;
  }

  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }
  
  public void setDescription(String tmp) {
    this.description = tmp;
  }

  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }

  public int getId() {
    return id;
  }

  public String getDescription() {
    return description;
  }

  public boolean getEnabled() {
    return enabled;
  }

}

