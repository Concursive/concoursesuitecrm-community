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
 * Represents the properties/methods of a href(s) of each item in the table of
 * contents of the Table of Contents of the help module
 *
 * @author kbhoopal
 * @version $Id: HelpTableOfContentItemLink.java,v 1.3 2003/12/10 19:14:38
 *          mrajkowski Exp $
 * @created November 11, 2003
 */
public class HelpTableOfContentItemLink extends GenericBean {
  private int id = -1;
  private int linkToTableOfContentItem = -1;
  private int linkToHelpItem = -1;
  private int enteredBy = -1;
  private java.sql.Timestamp entered = null;
  private int modifiedBy = -1;
  private java.sql.Timestamp modified = null;
  private boolean enabled = true;

  /*
   *  Wether the leads belongs to a module item or a context item
   */
  private String linkType = null;
  private int linkModule = -1;


  /**
   * Constructor for the HelpTableOfContentItemLink object
   */
  public HelpTableOfContentItemLink() {
  }


  /**
   * Constructor for the HelpTableOfContentItemLink object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public HelpTableOfContentItemLink(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Constructor for the HelpTableOfContentItemLink object
   *
   * @param db     Description of the Parameter
   * @param thisId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public HelpTableOfContentItemLink(Connection db, int thisId) throws SQLException {
    if (thisId == -1) {
      throw new SQLException("Link ID not specified");
    }

    PreparedStatement pst = db.prepareStatement(
        "SELECT t.* " +
        "FROM help_tableofcontentitem_links t " +
        "WHERE link_id = ? ");

    int i = 0;
    pst.setInt(++i, thisId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (thisId == -1) {
      throw new SQLException("Link ID not found");
    }
  }


  /**
   * Sets the id attribute of the HelpTableOfContentItemLink object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the HelpTableOfContentItemLink object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Sets the linkToTableOfContentItem attribute of the
   * HelpTableOfContentItemLink object
   *
   * @param tmp The new linkToTableOfContentItem value
   */
  public void setLinkToTableOfContentItem(int tmp) {
    this.linkToTableOfContentItem = tmp;
  }


  /**
   * Sets the linkToTableOfContentItem attribute of the
   * HelpTableOfContentItemLink object
   *
   * @param tmp The new linkToTableOfContentItem value
   */
  public void setLinkToTableOfContentItem(String tmp) {
    this.linkToTableOfContentItem = Integer.parseInt(tmp);
  }


  /**
   * Sets the linkToHelpItem attribute of the HelpTableOfContentItemLink object
   *
   * @param tmp The new linkToHelpItem value
   */
  public void setLinkToHelpItem(int tmp) {
    this.linkToHelpItem = tmp;
  }


  /**
   * Sets the linkToHelpItem attribute of the HelpTableOfContentItemLink object
   *
   * @param tmp The new linkToHelpItem value
   */
  public void setLinkToHelpItem(String tmp) {
    this.linkToHelpItem = Integer.parseInt(tmp);
  }


  /**
   * Sets the enteredBy attribute of the HelpTableOfContentItemLink object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   * Sets the enteredBy attribute of the HelpTableOfContentItemLink object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the entered attribute of the HelpTableOfContentItemLink object
   *
   * @param tmp The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   * Sets the entered attribute of the HelpTableOfContentItemLink object
   *
   * @param tmp The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the modifiedBy attribute of the HelpTableOfContentItemLink object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   * Sets the modifiedBy attribute of the HelpTableOfContentItemLink object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the modified attribute of the HelpTableOfContentItemLink object
   *
   * @param tmp The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   * Sets the modified attribute of the HelpTableOfContentItemLink object
   *
   * @param tmp The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the enabled attribute of the HelpTableOfContentItemLink object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   * Sets the enabled attribute of the HelpTableOfContentItemLink object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the linkType attribute of the HelpTableOfContentItemLink object
   *
   * @param tmp The new linkType value
   */
  public void setLinkType(String tmp) {
    this.linkType = tmp;
  }


  /**
   * Sets the linkModule attribute of the HelpTableOfContentItemLink object
   *
   * @param tmp The new linkModule value
   */
  public void setLinkModule(int tmp) {
    this.linkModule = tmp;
  }


  /**
   * Sets the linkModule attribute of the HelpTableOfContentItemLink object
   *
   * @param tmp The new linkModule value
   */
  public void setLinkModule(String tmp) {
    this.linkModule = Integer.parseInt(tmp);
  }


  /**
   * Gets the id attribute of the HelpTableOfContentItemLink object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the linkToTableOfContentItem attribute of the
   * HelpTableOfContentItemLink object
   *
   * @return The linkToTableOfContentItem value
   */
  public int getLinkToTableOfContentItem() {
    return linkToTableOfContentItem;
  }


  /**
   * Gets the linkToHelpItem attribute of the HelpTableOfContentItemLink object
   *
   * @return The linkToHelpItem value
   */
  public int getLinkToHelpItem() {
    return linkToHelpItem;
  }


  /**
   * Gets the enteredBy attribute of the HelpTableOfContentItemLink object
   *
   * @return The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   * Gets the entered attribute of the HelpTableOfContentItemLink object
   *
   * @return The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   * Gets the modifiedBy attribute of the HelpTableOfContentItemLink object
   *
   * @return The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   * Gets the modified attribute of the HelpTableOfContentItemLink object
   *
   * @return The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   * Gets the enabled attribute of the HelpTableOfContentItemLink object
   *
   * @return The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   * Gets the linkType attribute of the HelpTableOfContentItemLink object
   *
   * @return The linkType value
   */
  public String getLinkType() {
    return linkType;
  }


  /**
   * Gets the linkModule attribute of the HelpTableOfContentItemLink object
   *
   * @return The linkModule value
   */
  public int getLinkModule() {
    return linkModule;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void fetchLinkDetails(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "SELECT link_module_id, " + DatabaseUtils.addQuotes(db, "module") + " " +
        "FROM help_contents h " +
        "WHERE help_id = ? ");

    int i = 0;
    pst.setInt(++i, this.linkToHelpItem);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      String module = rs.getString("module");
      this.linkModule = rs.getInt("link_module_id");
      if (module == null) {
        this.linkType = "MODULE";
      } else {
        this.linkType = "CONTEXT";
      }
    }
  }


  /**
   * Description of the Method
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("link_id");
    linkToTableOfContentItem = rs.getInt("global_link_id");
    linkToHelpItem = rs.getInt("linkto_content_id");
    enteredBy = rs.getInt("enteredby");
    entered = rs.getTimestamp("entered");
    modifiedBy = rs.getInt("modifiedby");
    modified = rs.getTimestamp("modified");
    enabled = rs.getBoolean("enabled");
  }

}

