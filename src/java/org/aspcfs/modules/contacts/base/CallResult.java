//Copyright 2001-2003 Dark Horse Ventures

package org.aspcfs.modules.contacts.base;

import com.darkhorseventures.framework.beans.*;
import com.darkhorseventures.framework.actions.*;
import java.sql.*;
import org.aspcfs.utils.DatabaseUtils;

public class CallResult extends GenericBean {

  private int id = -1;
  private String description = null;
  private int level = -1;
  private boolean enabled = false;
  private boolean nextRequired = false;
  private int nextDays = -1;
  private int nextCallTypeId = -1;
  private boolean canceledType = false;
  
  public void setId(int tmp) { this.id = tmp; }
public void setId(String tmp) { this.id = Integer.parseInt(tmp); }
public void setDescription(String tmp) { this.description = tmp; }
public void setLevel(int tmp) { this.level = tmp; }
public void setLevel(String tmp) { this.level = Integer.parseInt(tmp); }
public void setEnabled(boolean tmp) { this.enabled = tmp; }
public void setEnabled(String tmp) { this.enabled = DatabaseUtils.parseBoolean(tmp); }
public void setNextRequired(boolean tmp) { this.nextRequired = tmp; }
public void setNextRequired(String tmp) { this.nextRequired = DatabaseUtils.parseBoolean(tmp); }
public void setNextDays(int tmp) { this.nextDays = tmp; }
public void setNextDays(String tmp) { this.nextDays = Integer.parseInt(tmp); }
public void setNextCallTypeId(int tmp) { this.nextCallTypeId = tmp; }
public void setNextCallTypeId(String tmp) { this.nextCallTypeId = Integer.parseInt(tmp); }
public void setCanceledType(boolean tmp) { this.canceledType = tmp; }
public void setCanceledType(String tmp) { this.canceledType = DatabaseUtils.parseBoolean(tmp); }
public int getId() { return id; }
public String getDescription() { return description; }
public int getLevel() { return level; }
public boolean getEnabled() { return enabled; }
public boolean getNextRequired() { return nextRequired; }
public int getNextDays() { return nextDays; }
public int getNextCallTypeId() { return nextCallTypeId; }
public boolean getCanceledType() { return canceledType; }

  
  public CallResult() { }
  
  public CallResult(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }
  
  public CallResult(Connection db, int resultId) throws SQLException {
    queryRecord(db, resultId);
  }
  
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

