//Copyright 2001 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import org.theseus.beans.*;
import java.sql.*;
import com.darkhorseventures.utils.DatabaseUtils;

public class HelpItem extends GenericBean {

  private int id = -1;
  private String module = null;
  private String section = null;
  private String subsection = null;
  private String display1 = null;
  private String display2 = null;
  private String display3 = null;
  private String display4 = null;
  private String description = null;
  private String permission = null;
  private int enteredBy = -1;
  private java.sql.Timestamp entered = null;
  private int modifiedBy = -1;
  private java.sql.Timestamp modified = null;
  
  public HelpItem() { }


  public HelpItem(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }

  public HelpItem(Connection db, String module, String section, String subsection) throws SQLException {

    if ("null".equals(module)) module = null;
    if ("null".equals(section)) section = null;
    if ("null".equals(subsection)) subsection = null;
    
    PreparedStatement pst = null;
    ResultSet rs = null;

    String sql =
      "SELECT * " +
      "FROM help_contents h " +
      "WHERE module = ? AND section = ? AND subsection = ? ";
    pst = db.prepareStatement(sql);
    if (System.getProperty("DEBUG") != null) {
      System.out.println("HelpItem-> Prepared");
    }
    int i = 0;
    pst.setString(++i, module);
    pst.setString(++i, section);
    pst.setString(++i, subsection);
    rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    } else {
      this.module = module;
      this.section = section;
      this.subsection = subsection;
    }
    rs.close();
    pst.close();
    if (System.getProperty("DEBUG") != null) {
      System.out.println("HelpItem-> Init ok");
    }
  }
  
  public void setId(int tmp) { this.id = tmp; }
  public void setId(String tmp) { this.id = Integer.parseInt(tmp); }
  public void setModule(String tmp) { this.module = tmp; }
  public void setSection(String tmp) { this.section = tmp; }
  public void setSubsection(String tmp) { this.subsection = tmp; }
  public void setDisplay1(String tmp) { this.display1 = tmp; }
  public void setDisplay2(String tmp) { this.display2 = tmp; }
  public void setDisplay3(String tmp) { this.display3 = tmp; }
  public void setDisplay4(String tmp) { this.display4 = tmp; }
  public void setDescription(String tmp) { this.description = tmp; }
  public void setPermission(String tmp) { this.permission = tmp; }
  public void setEnteredBy(int tmp) { this.enteredBy = tmp; }
  public void setEntered(java.sql.Timestamp tmp) { this.entered = tmp; }
  public void setModifiedBy(int tmp) { this.modifiedBy = tmp; }
  public void setModified(java.sql.Timestamp tmp) { this.modified = tmp; }

  public int getId() { return id; }
  public String getModule() { return module; }
  public String getSection() { return section; }
  public String getSubsection() { return subsection; }
  public String getDisplay1() { return display1; }
  public String getDisplay2() { return display2; }
  public String getDisplay3() { return display3; }
  public String getDisplay4() { return display4; }
  public String getDescription() { return description; }
  public String getPermission() { return permission; }
  public int getEnteredBy() { return enteredBy; }
  public java.sql.Timestamp getEntered() { return entered; }
  public int getModifiedBy() { return modifiedBy; }
  public java.sql.Timestamp getModified() { return modified; }

  public void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("help_id");
    module = rs.getString("module");
    section = rs.getString("section");
    subsection = rs.getString("subsection");
    display1 = rs.getString("display1");
    display2 = rs.getString("display2");
    display3 = rs.getString("display3");
    display4 = rs.getString("display4");
    description = rs.getString("description");
    enteredBy = rs.getInt("enteredby");
    entered = rs.getTimestamp("entered");
    modifiedBy = rs.getInt("modifiedby");
    modified = rs.getTimestamp("modified");
  }
  
  public boolean insert(Connection db) throws SQLException {
    if (System.getProperty("DEBUG") != null) System.out.println("HelpItem-> INSERT");
    PreparedStatement pst = null;

    String sql =
      "INSERT INTO help_contents " +
      "(module, section, subsection, display1, display2, display3, display4, description, enteredby, modifiedby) " +
      "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
    pst = db.prepareStatement(sql);
    int i = 0;
    pst.setString(++i, module);
    pst.setString(++i, section);
    pst.setString(++i, subsection);
    pst.setString(++i, display1);
    pst.setString(++i, display2);
    pst.setString(++i, display3);
    pst.setString(++i, display4);
    pst.setString(++i, description);
    pst.setInt(++i, enteredBy);
    pst.setInt(++i, modifiedBy);
    pst.execute();
    pst.close();
    
    id = DatabaseUtils.getCurrVal(db, "help_contents_help_id_seq");
    
    return true;
  }
  
  public int update(Connection db) throws SQLException {
    if (id == -1) {
      if ("null".equals(module)) module = null;
      if ("null".equals(section)) section = null;
      if ("null".equals(subsection)) subsection = null;
      this.insert(db);
      return 1;
    }
    
    if (System.getProperty("DEBUG") != null) System.out.println("HelpItem-> UPDATE");
    PreparedStatement pst = null;

    String sql =
      "UPDATE help_contents " +
      "SET description = ?, modifiedby = ?, modified = CURRENT_TIMESTAMP " +
      "WHERE help_id = ? ";
    pst = db.prepareStatement(sql);
    int i = 0;
    pst.setString(++i, description);
    pst.setInt(++i, modifiedBy);
    pst.setInt(++i, id);
    int count = pst.executeUpdate();
    pst.close();
    
    return count;
  }

}

