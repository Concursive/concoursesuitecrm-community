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

import com.darkhorseventures.framework.beans.*;
import java.sql.*;
import org.aspcfs.utils.DatabaseUtils;
import java.io.*;
import java.util.*;
import java.text.*;

/**
 *  Represents a Help Note for a page in CFS
 *
 *@author     akhi_m
 *@created    July 9, 2003
 *@version    $id:exp$
 */
public class HelpNote extends GenericBean {
  //static variables
  public static int DONE = 1;

  //properties
  private int id = -1;
  private int linkHelpId = -1;
  private String description = null;
  private int enteredBy = -1;
  private java.sql.Timestamp entered = null;
  private int modifiedBy = -1;
  private java.sql.Timestamp modified = null;
  private boolean complete = false;
  private java.sql.Timestamp completeDate = null;
  private int completedBy = -1;
  private boolean enabled = true;


  /**
   *  Constructor for the HelpNote object
   */
  public HelpNote() { }



  /**
   *  Constructor for the HelpNote object
   *
   *@param  db                Description of the Parameter
   *@param  thisId            Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public HelpNote(Connection db, int thisId) throws SQLException {
    if (thisId == -1) {
      throw new SQLException("Note ID not specified");
    }

    PreparedStatement pst = db.prepareStatement(
        "SELECT f.* " +
        "FROM help_notes f " +
        "WHERE note_id = ? ");
    int i = 0;
    pst.setInt(++i, thisId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (thisId == -1) {
      throw new SQLException("Note ID not found");
    }
  }


  /**
   *  Constructor for the HelpNote object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public HelpNote(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Sets the id attribute of the HelpNote object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the HelpNote object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the linkHelpId attribute of the HelpNote object
   *
   *@param  tmp  The new linkHelpId value
   */
  public void setLinkHelpId(int tmp) {
    this.linkHelpId = tmp;
  }


  /**
   *  Sets the linkHelpId attribute of the HelpNote object
   *
   *@param  tmp  The new linkHelpId value
   */
  public void setLinkHelpId(String tmp) {
    this.linkHelpId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the description attribute of the HelpNote object
   *
   *@param  tmp  The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the HelpNote object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the HelpNote object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the entered attribute of the HelpNote object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the entered attribute of the HelpNote object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the modifiedBy attribute of the HelpNote object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the HelpNote object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the modified attribute of the HelpNote object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the modified attribute of the HelpNote object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the complete attribute of the HelpNote object
   *
   *@param  tmp  The new complete value
   */
  public void setComplete(boolean tmp) {
    this.complete = tmp;
  }


  /**
   *  Sets the complete attribute of the HelpNote object
   *
   *@param  tmp  The new complete value
   */
  public void setComplete(String tmp) {
    this.complete = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the enabled attribute of the HelpNote object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the enabled attribute of the HelpNote object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the completeDate attribute of the HelpNote object
   *
   *@param  completeDate  The new completeDate value
   */
  public void setCompleteDate(java.sql.Timestamp completeDate) {
    this.completeDate = completeDate;
  }


  /**
   *  Gets the completeDate attribute of the HelpNote object
   *
   *@return    The completeDate value
   */
  public java.sql.Timestamp getCompleteDate() {
    return completeDate;
  }


  /**
   *  Sets the completedBy attribute of the HelpNote object
   *
   *@param  completedBy  The new completedBy value
   */
  public void setCompletedBy(int completedBy) {
    this.completedBy = completedBy;
  }


  /**
   *  Gets the completedBy attribute of the HelpNote object
   *
   *@return    The completedBy value
   */
  public int getCompletedBy() {
    return completedBy;
  }


  /**
   *  Gets the completeDateString attribute of the HelpNote object
   *
   *@return    The completeDateString value
   */
  public String getCompleteDateString() {
    String tmp = "";
    try {
      return DateFormat.getDateInstance(3).format(completeDate);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the id attribute of the HelpNote object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the linkHelpId attribute of the HelpNote object
   *
   *@return    The linkHelpId value
   */
  public int getLinkHelpId() {
    return linkHelpId;
  }


  /**
   *  Gets the description attribute of the HelpNote object
   *
   *@return    The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   *  Gets the enteredBy attribute of the HelpNote object
   *
   *@return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the entered attribute of the HelpNote object
   *
   *@return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the modifiedBy attribute of the HelpNote object
   *
   *@return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Gets the modified attribute of the HelpNote object
   *
   *@return    The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the complete attribute of the HelpNote object
   *
   *@return    The complete value
   */
  public boolean getComplete() {
    return complete;
  }


  /**
   *  Gets the enabled attribute of the HelpNote object
   *
   *@return    The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   *  Inserts a Help Note
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    try {
      db.setAutoCommit(false);
      int i = 0;
      PreparedStatement pst = db.prepareStatement(
          "INSERT INTO help_notes " +
          "(link_help_id, description, enteredby, modifiedby, enabled, completedate, completedby) " +
          "VALUES (?, ?, ?, ?, ?, ?, ? ) "
          );
      pst.setInt(++i, this.getLinkHelpId());
      pst.setString(++i, this.getDescription());
      pst.setInt(++i, this.getEnteredBy());
      pst.setInt(++i, this.getModifiedBy());
      pst.setBoolean(++i, this.getEnabled());
      if (this.getComplete()) {
        pst.setTimestamp(++i, new Timestamp(System.currentTimeMillis()));
        pst.setInt(++i, completedBy);
      } else {
        pst.setTimestamp(++i, null);
        pst.setNull(++i, java.sql.Types.SMALLINT);
      }
      pst.execute();
      this.id = DatabaseUtils.getCurrVal(db, "help_notes_note_id_seq");
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
   *  Updates a Help Note
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public int update(Connection db) throws SQLException {
    String sql = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    int count = 0;
    if (id == -1) {
      throw new SQLException("Note ID not specified");
    }

    try {
      db.setAutoCommit(false);
      HelpNote previousNote = new HelpNote(db, id);
      int i = 0;
      pst = db.prepareStatement(
          "UPDATE help_notes " +
          "SET modifiedby = ?, description = ?, enabled = ?, completedate = ?, completedby = ? " +
          "WHERE note_id = ? AND modified = ? "
          );
      pst.setInt(++i, this.getModifiedBy());
      pst.setString(++i, this.getDescription());
      pst.setBoolean(++i, this.getEnabled());
      if (previousNote.getComplete() && this.getComplete()) {
        pst.setTimestamp(++i, previousNote.getCompleteDate());
        pst.setInt(++i, previousNote.getCompletedBy());
      } else if (this.getComplete() && !previousNote.getComplete()) {
        pst.setTimestamp(++i, new Timestamp(System.currentTimeMillis()));
        pst.setInt(++i, this.getCompletedBy());
      } else {
        pst.setTimestamp(++i, null);
        pst.setNull(++i, java.sql.Types.SMALLINT);
      }
      pst.setInt(++i, id);
      pst.setTimestamp(++i, this.getModified());
      count = pst.executeUpdate();
      pst.close();
      db.commit();
    } catch (SQLException e) {
      db.rollback();
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
    return count;
  }


  /**
   *  Deletes a Help Note
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean delete(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Note ID not specified");
    }
    try {
      db.setAutoCommit(false);
      PreparedStatement pst = db.prepareStatement(
          "DELETE from help_notes " +
          "WHERE note_id = ? ");
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
   *  Description of the Method
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("note_id");
    linkHelpId = rs.getInt("link_help_id");
    description = rs.getString("description");
    enteredBy = rs.getInt("enteredby");
    entered = rs.getTimestamp("entered");
    modifiedBy = rs.getInt("modifiedby");
    modified = rs.getTimestamp("modified");
    completeDate = rs.getTimestamp("completedate");
    if (!rs.wasNull()) {
      complete = true;
    }
    completedBy = rs.getInt("completedby");
    enabled = rs.getBoolean("enabled");
  }
}

