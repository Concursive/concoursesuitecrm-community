//Copyright 2001 Dark Horse Ventures

package org.aspcfs.modules.help.base;

import com.darkhorseventures.framework.beans.*;
import java.sql.*;
import org.aspcfs.utils.DatabaseUtils;
import java.io.*;

/**
 *  Represents help for a page in CFS
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
  private String description = null;
  private String permission = null;
  private int enteredBy = -1;
  private java.sql.Timestamp entered = null;
  private int modifiedBy = -1;
  private java.sql.Timestamp modified = null;
  private boolean buildFeatures = false;
  private boolean buildRules = false;
  private boolean buildNotes = false;
  private boolean buildTips = false;

  //details
  private HelpFeatureList features = new HelpFeatureList();
  private HelpNoteList notes = new HelpNoteList();
  private HelpTipList tips = new HelpTipList();
  private HelpBusinessRuleList businessRules = new HelpBusinessRuleList();


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
   *@param  userId            Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public HelpItem(Connection db, String module, String section, String subsection, int userId) throws SQLException {
    if ("null".equals(module)) {
      module = null;
    }
    if ("null".equals(section)) {
      section = null;
    }
    if ("null".equals(subsection)) {
      subsection = null;
    }
    processRecord(db, userId);
  }


  /**
   *Constructor for the HelpItem object
   *
   *@param  db                Description of the Parameter
   *@param  id                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public HelpItem(Connection db, int id) throws SQLException {
    this.id = id;
    queryRecord(db);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void queryRecord(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    String sql =
        "SELECT * " +
        "FROM help_contents h " +
        "WHERE help_id = ? ";
    pst = db.prepareStatement(sql);
    if (System.getProperty("DEBUG") != null) {
      System.out.println("HelpItem-> Prepared");
    }
    int i = 0;
    pst.setInt(++i, id);
    rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();

    buildFeatures(db);
    buildRules(db);
    buildNotes(db);
    buildTips(db);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  userId            Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public synchronized void processRecord(Connection db, int userId) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    boolean newItem = true;
    String sql =
        "SELECT * " +
        "FROM help_contents h " +
        "WHERE module = ? AND section = ? " +
        (subsection != null ? "AND subsection = ? " : "");
    pst = db.prepareStatement(sql);
    if (System.getProperty("DEBUG") != null) {
      System.out.println("HelpItem-> Prepared");
    }
    int i = 0;
    pst.setString(++i, module);
    pst.setString(++i, section);
    if (subsection != null) {
      pst.setString(++i, subsection);
    }
    rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
      newItem = false;
    }
    rs.close();
    pst.close();
    if (System.getProperty("DEBUG") != null) {
      System.out.println("HelpItem-> Init ok");
    }

    //check for a new entry
    if (newItem) {
      this.setEnteredBy(userId);
      this.setModifiedBy(userId);
      this.insert(db);
      if (System.getProperty("DEBUG") != null) {
        System.out.println("HelpItem-> Record not present.. Inserting new record ");
      }
    } else {
      //build general features
      if (buildFeatures) {
        buildFeatures(db);
      }
      //build business rules
      if (buildRules) {
        buildRules(db);
      }
      //build notes
      if (buildNotes) {
        buildNotes(db);
      }
      //build tips
      if (buildTips) {
        buildTips(db);
      }
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
   *  Sets the features attribute of the HelpItem object
   *
   *@param  features  The new features value
   */
  public void setFeatures(HelpFeatureList features) {
    this.features = features;
  }


  /**
   *  Sets the buildFeatures attribute of the HelpItem object
   *
   *@param  buildFeatures  The new buildFeatures value
   */
  public void setBuildFeatures(boolean buildFeatures) {
    this.buildFeatures = buildFeatures;
  }


  /**
   *  Sets the businessRules attribute of the HelpItem object
   *
   *@param  businessRules  The new businessRules value
   */
  public void setBusinessRules(HelpBusinessRuleList businessRules) {
    this.businessRules = businessRules;
  }


  /**
   *  Sets the buildRules attribute of the HelpItem object
   *
   *@param  buildRules  The new buildRules value
   */
  public void setBuildRules(boolean buildRules) {
    this.buildRules = buildRules;
  }


  /**
   *  Sets the buildNotes attribute of the HelpItem object
   *
   *@param  buildNotes  The new buildNotes value
   */
  public void setBuildNotes(boolean buildNotes) {
    this.buildNotes = buildNotes;
  }


  /**
   *  Sets the buildTips attribute of the HelpItem object
   *
   *@param  buildTips  The new buildTips value
   */
  public void setBuildTips(boolean buildTips) {
    this.buildTips = buildTips;
  }


  /**
   *  Sets the notes attribute of the HelpItem object
   *
   *@param  notes  The new notes value
   */
  public void setNotes(HelpNoteList notes) {
    this.notes = notes;
  }


  /**
   *  Sets the tips attribute of the HelpItem object
   *
   *@param  tips  The new tips value
   */
  public void setTips(HelpTipList tips) {
    this.tips = tips;
  }


  /**
   *  Gets the notes attribute of the HelpItem object
   *
   *@return    The notes value
   */
  public HelpNoteList getNotes() {
    return notes;
  }


  /**
   *  Gets the tips attribute of the HelpItem object
   *
   *@return    The tips value
   */
  public HelpTipList getTips() {
    return tips;
  }


  /**
   *  Gets the buildRules attribute of the HelpItem object
   *
   *@return    The buildRules value
   */
  public boolean getBuildRules() {
    return buildRules;
  }


  /**
   *  Gets the buildNotes attribute of the HelpItem object
   *
   *@return    The buildNotes value
   */
  public boolean getBuildNotes() {
    return buildNotes;
  }


  /**
   *  Gets the buildTips attribute of the HelpItem object
   *
   *@return    The buildTips value
   */
  public boolean getBuildTips() {
    return buildTips;
  }


  /**
   *  Gets the businessRules attribute of the HelpItem object
   *
   *@return    The businessRules value
   */
  public HelpBusinessRuleList getBusinessRules() {
    return businessRules;
  }


  /**
   *  Gets the buildFeatures attribute of the HelpItem object
   *
   *@return    The buildFeatures value
   */
  public boolean getBuildFeatures() {
    return buildFeatures;
  }


  /**
   *  Gets the features attribute of the HelpItem object
   *
   *@return    The features value
   */
  public HelpFeatureList getFeatures() {
    return features;
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
        "(module, section, subsection, description, enteredby, modifiedby) " +
        "VALUES (?, ?, ?, ?, ?, ?) ";
    pst = db.prepareStatement(sql);
    int i = 0;
    pst.setString(++i, module);
    pst.setString(++i, section);
    pst.setString(++i, subsection);
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
   *  Builds features of this help item
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildFeatures(Connection db) throws SQLException {
    features.setLinkHelpId(this.getId());
    features.buildList(db);
  }


  /**
   *  Builds Notes for this help item
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildNotes(Connection db) throws SQLException {
    notes.setLinkHelpId(this.getId());
    notes.buildList(db);
  }


  /**
   *  Builds Tips for this help item
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildTips(Connection db) throws SQLException {
    tips.setLinkHelpId(this.getId());
    tips.buildList(db);
  }


  /**
   *  Builds business rules for this help item
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildRules(Connection db) throws SQLException {
    businessRules.setLinkHelpId(this.getId());
    businessRules.buildList(db);
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

