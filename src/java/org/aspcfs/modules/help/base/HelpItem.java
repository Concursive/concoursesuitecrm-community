//Copyright 2001 Dark Horse Ventures

package org.aspcfs.modules.help.base;

import com.darkhorseventures.framework.beans.*;
import java.sql.*;
import org.aspcfs.utils.DatabaseUtils;
import java.io.*;


/**
 *  Description of the Class
 *
 *@author     matt rajkowski
 *@created    January 21, 2002
 *@version    $Id$
 */
public class HelpItem extends GenericBean {

  public final static String fs = System.getProperty("file.separator");
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


  /**
   *  Constructor for the HelpItem object
   */
  public HelpItem() { }


  /**
   *  Constructor for the HelpItem object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public HelpItem(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Constructor for the HelpItem object
   *
   *@param  db                Description of the Parameter
   *@param  module            Description of the Parameter
   *@param  section           Description of the Parameter
   *@param  subsection        Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public HelpItem(Connection db, String module, String section, String subsection) throws SQLException {
    if ("null".equals(module)) {
      module = null;
    }
    if ("null".equals(section)) {
      section = null;
    }
    if ("null".equals(subsection)) {
      subsection = null;
    }
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


  /**
   *  Sets the id attribute of the HelpItem object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the HelpItem object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the module attribute of the HelpItem object
   *
   *@param  tmp  The new module value
   */
  public void setModule(String tmp) {
    this.module = tmp;
  }


  /**
   *  Sets the section attribute of the HelpItem object
   *
   *@param  tmp  The new section value
   */
  public void setSection(String tmp) {
    this.section = tmp;
  }


  /**
   *  Sets the subsection attribute of the HelpItem object
   *
   *@param  tmp  The new subsection value
   */
  public void setSubsection(String tmp) {
    this.subsection = tmp;
  }


  /**
   *  Sets the display1 attribute of the HelpItem object
   *
   *@param  tmp  The new display1 value
   */
  public void setDisplay1(String tmp) {
    this.display1 = tmp;
  }


  /**
   *  Sets the display2 attribute of the HelpItem object
   *
   *@param  tmp  The new display2 value
   */
  public void setDisplay2(String tmp) {
    this.display2 = tmp;
  }


  /**
   *  Sets the display3 attribute of the HelpItem object
   *
   *@param  tmp  The new display3 value
   */
  public void setDisplay3(String tmp) {
    this.display3 = tmp;
  }


  /**
   *  Sets the display4 attribute of the HelpItem object
   *
   *@param  tmp  The new display4 value
   */
  public void setDisplay4(String tmp) {
    this.display4 = tmp;
  }


  /**
   *  Sets the description attribute of the HelpItem object
   *
   *@param  tmp  The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   *  Sets the permission attribute of the HelpItem object
   *
   *@param  tmp  The new permission value
   */
  public void setPermission(String tmp) {
    this.permission = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the HelpItem object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the entered attribute of the HelpItem object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the HelpItem object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the modified attribute of the HelpItem object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Gets the id attribute of the HelpItem object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the module attribute of the HelpItem object
   *
   *@return    The module value
   */
  public String getModule() {
    return module;
  }


  /**
   *  Gets the section attribute of the HelpItem object
   *
   *@return    The section value
   */
  public String getSection() {
    return section;
  }


  /**
   *  Gets the subsection attribute of the HelpItem object
   *
   *@return    The subsection value
   */
  public String getSubsection() {
    return subsection;
  }


  /**
   *  Gets the display1 attribute of the HelpItem object
   *
   *@return    The display1 value
   */
  public String getDisplay1() {
    return display1;
  }


  /**
   *  Gets the display2 attribute of the HelpItem object
   *
   *@return    The display2 value
   */
  public String getDisplay2() {
    return display2;
  }


  /**
   *  Gets the display3 attribute of the HelpItem object
   *
   *@return    The display3 value
   */
  public String getDisplay3() {
    return display3;
  }


  /**
   *  Gets the display4 attribute of the HelpItem object
   *
   *@return    The display4 value
   */
  public String getDisplay4() {
    return display4;
  }


  /**
   *  Gets the description attribute of the HelpItem object
   *
   *@return    The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   *  Gets the permission attribute of the HelpItem object
   *
   *@return    The permission value
   */
  public String getPermission() {
    return permission;
  }


  /**
   *  Gets the enteredBy attribute of the HelpItem object
   *
   *@return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the entered attribute of the HelpItem object
   *
   *@return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the modifiedBy attribute of the HelpItem object
   *
   *@return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Gets the modified attribute of the HelpItem object
   *
   *@return    The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Description of the Method
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
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


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("HelpItem-> INSERT");
    }
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


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public int update(Connection db) throws SQLException {
    if (id == -1) {
      if ("null".equals(module)) {
        module = null;
      }
      if ("null".equals(section)) {
        section = null;
      }
      if ("null".equals(subsection)) {
        subsection = null;
      }
      this.insert(db);
      return 1;
    }

    if (System.getProperty("DEBUG") != null) {
      System.out.println("HelpItem-> UPDATE");
    }
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


  /**
   *  Gets the baseFilename attribute of the HelpItem object
   *
   *@return    The baseFilename value
   */
  public String getBaseFilename() {
    StringBuffer filename = new StringBuffer();
    if (module.indexOf(".do") > -1) {
      filename.append(module.substring(0, module.indexOf(".do")));
    } else {
      filename.append(module);
    }
    filename.append("-");
    filename.append(section);
    if (subsection != null && subsection.length() > 0) {
      filename.append("-" + subsection);
    }
    return filename.toString().toLowerCase();
  }


  /**
   *  Description of the Method
   *
   *@param  path  Description of the Parameter
   *@return       Description of the Return Value
   */
  public boolean hasImageFile(String path) {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("HelpItem-> Looking for the following image: " + path + fs + this.getBaseFilename() + ".png");
    }
    File helpImage = new File(path + fs + this.getBaseFilename() + ".png");
    return helpImage.exists();
  }
}

