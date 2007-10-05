package com.zeroio.iteam.base;

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Represents a project's category
 *
 * @author matt rajkowski
 * @version $Id$
 * @created December 27, 2004
 */
public class ProjectCategory extends GenericBean {
  private int id = -1;
  private String description = null;
  private boolean enabled = true;
  private int level = -1;


  public ProjectCategory() {
  }


  public ProjectCategory(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  public int getId() {
    return id;
  }


  public void setId(int tmp) {
    this.id = tmp;
  }


  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  public String getDescription() {
    return description;
  }


  public void setDescription(String tmp) {
    this.description = tmp;
  }


  public boolean getEnabled() {
    return enabled;
  }


  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }


  public int getLevel() {
    return level;
  }


  public void setLevel(int tmp) {
    this.level = tmp;
  }


  public void setLevel(String tmp) {
    this.level = Integer.parseInt(tmp);
  }


  private void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("code");
    description = rs.getString("description");
    enabled = rs.getBoolean("enabled");
    level = rs.getInt("level");
  }


  public void insert(Connection db) throws SQLException {
    id = DatabaseUtils.getNextSeq(db, "lookup_project_cat_code_seq");
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO lookup_project_category " +
        "(" + (id > -1 ? "code, " : "") + "description, enabled, " + DatabaseUtils.addQuotes(db, "level") + ") VALUES " +
        "(" + (id > -1 ? "?, " : "") + "?, ?, ?) ");
    int i = 0;
    pst.setString(++i, description);
    pst.setBoolean(++i, enabled);
    pst.setInt(++i, level);
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "lookup_project_cat_code_seq", id);
  }


  public void update(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "UPDATE lookup_project_category " +
        "SET description = ?, enabled = ?, modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", " + DatabaseUtils.addQuotes(db, "level") + " = ? " +
        "WHERE code = ? ");
    int i = 0;
    pst.setString(++i, description);
    pst.setBoolean(++i, enabled);
    pst.setInt(++i, level);
    pst.setInt(++i, id);
    pst.executeUpdate();
    pst.close();
  }
}

