/*
 *  Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. DARK HORSE
 *  VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.help.base;

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Help Module class to allow administrators to insert biref and detailed
 * description of application modules
 *
 * @author kbhoopal
 * @version $Id$
 * @created December 10, 2003
 */
public class HelpModule extends GenericBean {
  //static variables
  public static int DONE = 1;

  //properties
  private int id = -1;
  private int linkCategoryId = -1;
  private String briefDescription = null;
  private String detailDescription = null;
  private String moduleName = null;

  //The action that resulted in this module being invoked
  private String relatedAction = null;


  /**
   * Constructor for the HelpModule object
   */
  public HelpModule() {
  }


  /**
   * Constructor for the HelpModule object
   *
   * @param db     Description of the Parameter
   * @param thisId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public HelpModule(Connection db, int thisId) throws SQLException {
    if (thisId == -1) {
      throw new SQLException("Module Id not specified");
    }
    PreparedStatement pst = db.prepareStatement(
        "SELECT module_id, hm.category_id as category_id, category, module_brief_description, module_detail_description " +
        "FROM help_module hm, permission_category pc " +
        "WHERE module_id = ? " +
        "AND hm.category_id = pc.category_id");
    int i = 0;
    pst.setInt(++i, thisId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (this.id == -1) {
      throw new SQLException("Module ID not found");
    }
  }


  /**
   * Constructor for the HelpModule object fetches information of a module
   * based on the page the user is in
   *
   * @param db     Description of the Parameter
   * @param action Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public HelpModule(Connection db, String action) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "SELECT * " +
        "FROM help_module hm, permission_category pc " +
        "WHERE hm.module_id in " +
        "(SELECT link_module_id " +
        "from help_contents hc " +
        "WHERE hc." + DatabaseUtils.addQuotes(db, "module") + " = ?) " +
        "AND hm.category_id = pc.category_id ");
    int i = 0;
    pst.setString(++i, action);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
      this.relatedAction = action;
    }
    rs.close();
    pst.close();
  }


  /**
   * Constructor for the HelpModule object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public HelpModule(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Sets the id attribute of the HelpModule object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the HelpModule object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Sets the linkCategoryId attribute of the HelpModule object
   *
   * @param tmp The new linkCategoryId value
   */
  public void setLinkCategoryId(int tmp) {
    this.linkCategoryId = tmp;
  }


  /**
   * Sets the linkCategoryId attribute of the HelpModule object
   *
   * @param tmp The new linkCategoryId value
   */
  public void setLinkCategoryId(String tmp) {
    this.linkCategoryId = Integer.parseInt(tmp);
  }


  /**
   * Sets the module name attribute of the HelpModule object
   *
   * @param tmp The new section value
   */
  public void setModuleName(String tmp) {
    this.moduleName = tmp;
  }


  /**
   * Sets the module name attribute of the HelpModule object
   *
   * @param tmp The new detail description value
   */
  public void setDetailDescription(String tmp) {
    this.detailDescription = tmp;
  }


  /**
   * Sets the module name attribute of the HelpModule object
   *
   * @param tmp The new brief description value
   */
  public void setBriefDescription(String tmp) {
    this.briefDescription = tmp;
  }


  /**
   * Sets the relatedAction attribute of the HelpModule object
   *
   * @param tmp The new relatedAction value
   */
  public void setRelatedAction(String tmp) {
    this.relatedAction = tmp;
  }


  /**
   * Gets the module Id of the HelpModule object
   *
   * @return The module value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the module attribute of the HelpModule object
   *
   * @return The module name
   */
  public String getModuleName() {
    return moduleName;
  }


  /**
   * Gets the module attribute of the HelpModule object
   *
   * @return The Link Category Id
   */
  public int getLinkCategoryId() {
    return linkCategoryId;
  }


  /**
   * Gets the brief description of the module of the HelpModule object
   *
   * @return The brief description
   */
  public String getBriefDescription() {
    return briefDescription;
  }


  /**
   * Gets the relatedAction attribute of the HelpModule object
   *
   * @return The relatedAction value
   */
  public String getRelatedAction() {
    return relatedAction;
  }


  /**
   * Gets the detail description of the module of the HelpModule object
   *
   * @return The detail description
   */
  public String getDetailDescription() {
    return detailDescription;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    id = DatabaseUtils.getNextSeq(db, "help_module_module_id_seq");
    int i = 0;
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO help_module " +
        "(" + (id > -1 ? "module_id, " : "") + "category_id , module_brief_description , module_detail_description) " +
        "VALUES (" + (id > -1 ? "?, " : "") + "?,?, ?) ");
    if (id > -1) {
      pst.setInt(++i, id);
    }
    pst.setInt(++i, this.getLinkCategoryId());
    pst.setString(++i, this.getBriefDescription());
    pst.setString(++i, this.getDetailDescription());
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "help_module_module_id_seq", id);
    return true;
  }


  /**
   * Updates the database with the modified brief ad detailed description
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public int update(Connection db) throws SQLException {
    if (id == -1) {
      throw new SQLException("Module ID not specified");
    }
    int count = 0;
    int i = 0;
    PreparedStatement pst = db.prepareStatement(
        "UPDATE help_module " +
        "SET module_brief_description = ?, module_detail_description = ? " +
        "WHERE module_id = ?");
    pst.setString(++i, this.getBriefDescription());
    pst.setString(++i, this.getDetailDescription());
    pst.setInt(++i, this.getId());
    count = pst.executeUpdate();
    pst.close();
    return count;
  }


  /**
   * Deletes a record from the database
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean delete(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Tip ID not specified");
    }
    try {
      db.setAutoCommit(false);
      PreparedStatement pst = db.prepareStatement(
          "DELETE from help_module " +
          "WHERE module_id = ? ");
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();
      db.commit();
    } catch (SQLException e) {
      db.rollback();
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
    return true;
  }


  /**
   * Builds the object with the information from the database
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("module_id");
    linkCategoryId = rs.getInt("category_id");
    moduleName = rs.getString("category");
    briefDescription = rs.getString("module_brief_description");
    detailDescription = rs.getString("module_detail_description");
  }
}

