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
package org.aspcfs.modules.contacts.base;

import java.sql.*;

/**
 *  Represents a ContactType -- every contact has a type. <br>
 *  0 is an Employee <br>
 *  1 is Not Specified
 *
 *@author     mrajkowski
 *@created    August 29, 2001
 *@version    $Id: ContactType.java,v 1.1.1.1 2002/01/14 19:49:24 mrajkowski Exp
 *      $
 */
public class ContactType {

  public final static int GENERAL = 0;
  public final static int ACCOUNT = 1;

  private int id = 0;
  private String description = null;
  private boolean enabled = true;
  private int category = -1;
  private int userId = -1;
  private int level = 0;


  /**
   *  Constructor for the ContactType object
   *
   *@since    1.1
   */
  public ContactType() { }


  /**
   *  Constructor for the ContactType object
   *
   *@param  rs                         Description of Parameter
   *@exception  java.sql.SQLException  Description of Exception
   *@since                             1.1
   */
  public ContactType(ResultSet rs) throws java.sql.SQLException {
    id = rs.getInt("code");
    description = rs.getString("description");
    enabled = rs.getBoolean("enabled");
    category = rs.getInt("category");
    userId = rs.getInt("user_id");
    if (rs.wasNull()) {
      userId = -1;
    }
  }


  /**
   *  Sets the Id attribute of the ContactType object
   *
   *@param  tmp  The new Id value
   *@since       1.1
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the Description attribute of the ContactType object
   *
   *@param  tmp  The new Description value
   *@since       1.1
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   *  Sets the Enabled attribute of the ContactType object
   *
   *@param  tmp  The new Enabled value
   *@since       1.1
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the userId attribute of the ContactType object
   *
   *@param  userId  The new user_id value
   */
  public void setUserId(int userId) {
    this.userId = userId;
  }


  /**
   *  Sets the category attribute of the ContactType object
   *
   *@param  category  The new category value
   */
  public void setCategory(int category) {
    this.category = category;
  }


  /**
   *  Sets the category attribute of the ContactType object
   *
   *@param  category  The new category value
   */
  public void setCategory(String category) {
    this.category = Integer.parseInt(category);
  }


  /**
   *  Sets the level attribute of the ContactType object
   *
   *@param  level  The new level value
   */
  public void setLevel(int level) {
    this.level = level;
  }


  /**
   *  Gets the level attribute of the ContactType object
   *
   *@return    The level value
   */
  public int getLevel() {
    return level;
  }


  /**
   *  Gets the userId attribute of the ContactType object
   *
   *@return    The userId value
   */
  public int getUserId() {
    return userId;
  }


  /**
   *  Gets the category attribute of the ContactType object
   *
   *@return    The category value
   */
  public int getCategory() {
    return category;
  }


  /**
   *  Gets the Id attribute of the ContactType object
   *
   *@return    The Id value
   *@since     1.1
   */
  public int getId() {
    return id;
  }


  /**
   *  Returns the id of the Contact Type object<br>
   *  Required for LookupHtmlHandler when a validation fails
   *
   *@return    The code value
   */
  public int getCode() {
    return id;
  }


  /**
   *  Gets the codeString attribute of the ContactType object
   *
   *@return    The codeString value
   */
  public String getCodeString() {
    return String.valueOf(id);
  }


  /**
   *  Gets the Description attribute of the ContactType object
   *
   *@return    The Description value
   *@since     1.1
   */
  public String getDescription() {
    return description;
  }


  /**
   *  Gets the Enabled attribute of the ContactType object
   *
   *@return    The Enabled value
   *@since     1.1
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   *  Sets the enabled attribute of the ContactType object
   *
   *@param  db                The new enabled value
   *@param  tmp               The new enabled value
   *@exception  SQLException  Description of the Exception
   */
  public void setEnabled(Connection db, boolean tmp) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Contact Type ID not specified");
    }
    try {
      db.setAutoCommit(false);
      PreparedStatement pst = db.prepareStatement(
          "UPDATE lookup_contact_types " +
          "SET enabled = ? " +
          "WHERE code = ? ");
      int i = 0;
      pst.setBoolean(++i, tmp);
      pst.setInt(++i, this.getId());
      pst.execute();
      pst.close();
      db.commit();
    } catch (SQLException e) {
      db.rollback();
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
  }


  /**
   *  Inserts a Contact Type in the DB.
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    String sql = null;

    try {
      db.setAutoCommit(false);
      int i = 0;
      PreparedStatement pst = db.prepareStatement(
          "INSERT INTO lookup_contact_types " +
          "(description, level, enabled, category" + (userId > -1 ? ", user_id" : "") + ") " +
          "VALUES (?, ?, ?, ?" + (userId > -1 ? ", ?" : "") + ") ");
      pst.setString(++i, this.getDescription());
      pst.setInt(++i, this.getLevel());
      pst.setBoolean(++i, true);
      pst.setInt(++i, category);
      if (userId > -1) {
        pst.setInt(++i, userId);
      }
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
   *  Sets the newOrder attribute of the ContactType object
   *
   *@param  db                The new newOrder value
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public int setNewOrder(Connection db) throws SQLException {
    int resultCount = 0;

    if (this.getId() == 0) {
      throw new SQLException("ContactType Id not specified.");
    }

    PreparedStatement pst = null;
    int i = 0;

    pst = db.prepareStatement("UPDATE lookup_contact_types " +
        "SET level = ? " +
        "WHERE code = ? ");
    pst.setInt(++i, this.getLevel());
    pst.setInt(++i, this.getId());

    resultCount = pst.executeUpdate();
    pst.close();

    return resultCount;
  }

}

