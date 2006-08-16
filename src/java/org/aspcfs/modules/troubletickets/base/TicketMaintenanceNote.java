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
package org.aspcfs.modules.troubletickets.base;

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.utils.DatabaseUtils;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

/**
 * Description of the Class
 *
 * @author kbhoopal
 * @version $Id: TicketSunMaintenance.java,v 1.1.2.6 2004/02/11 19:26:19
 *          kbhoopal Exp $
 * @created January 29, 2004
 */
public class TicketMaintenanceNote extends GenericBean {

  private int id = -1;
  private int linkTicketId = -1;
  private String formTypeName = null;
  private String descriptionOfService = null;
  private TicketReplacementPartList ticketReplacementPartList = null;
  private java.sql.Timestamp entered = null;
  private int enteredBy = -1;
  private java.sql.Timestamp modified = null;
  private int modifiedBy = -1;
  private boolean enabled = true;
  private boolean override = false;


  /**
   * Constructor for the TicketSunMaintenance object
   */
  public TicketMaintenanceNote() {
  }


  /**
   * Constructor for the TicketSunMaintenance object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public TicketMaintenanceNote(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Sets the id attribute of the TicketSunMaintenance object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the TicketSunMaintenance object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Sets the linkTicketId attribute of the TicketSunMaintenance object
   *
   * @param tmp The new linkTicketId value
   */
  public void setLinkTicketId(int tmp) {
    this.linkTicketId = tmp;
  }


  /**
   * Sets the linkTicketId attribute of the TicketSunMaintenance object
   *
   * @param tmp The new linkTicketId value
   */
  public void setLinkTicketId(String tmp) {
    this.linkTicketId = Integer.parseInt(tmp);
  }


  /**
   * Sets the formTypeName attribute of the TicketSunMaintenance object
   *
   * @param tmp The new formTypeName value
   */
  public void setFormTypeName(String tmp) {
    this.formTypeName = tmp;
  }


  /**
   * Sets the descriptionOfService attribute of the TicketSunMaintenance object
   *
   * @param tmp The new descriptionOfService value
   */
  public void setDescriptionOfService(String tmp) {
    this.descriptionOfService = tmp;
  }


  /**
   * Sets the replacementParts attribute of the TicketSunMaintenance object
   *
   * @param tmp The new replacementParts value
   */
  public void setTicketReplacementPartList(TicketReplacementPartList tmp) {
    this.ticketReplacementPartList = tmp;
  }


  /**
   * Sets the entered attribute of the TicketSunMaintenance object
   *
   * @param tmp The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   * Sets the entered attribute of the TicketSunMaintenance object
   *
   * @param tmp The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the enteredBy attribute of the TicketSunMaintenance object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   * Sets the enteredBy attribute of the TicketSunMaintenance object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the modified attribute of the TicketSunMaintenance object
   *
   * @param tmp The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   * Sets the modified attribute of the TicketSunMaintenance object
   *
   * @param tmp The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the modifiedBy attribute of the TicketSunMaintenance object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   * Sets the modifiedBy attribute of the TicketSunMaintenance object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the enabled attribute of the TicketSunMaintenance object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   * Sets the enabled attribute of the TicketSunMaintenance object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the override attribute of the TicketSunMaintenance object
   *
   * @param tmp The new override value
   */
  public void setOverride(boolean tmp) {
    this.override = tmp;
  }


  /**
   * Sets the override attribute of the TicketSunMaintenance object
   *
   * @param tmp The new override value
   */
  public void setOverride(String tmp) {
    this.override = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the requestItems attribute of the TicketSunMaintenance object
   *
   * @param request The new requestItems value
   */
  public void setRequestItems(HttpServletRequest request) {

    ticketReplacementPartList = new TicketReplacementPartList(request);
  }


  /**
   * Gets the id attribute of the TicketSunMaintenance object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the linkTicketId attribute of the TicketSunMaintenance object
   *
   * @return The linkTicketId value
   */
  public int getLinkTicketId() {
    return linkTicketId;
  }


  /**
   * Gets the formTypeName attribute of the TicketSunMaintenance object
   *
   * @return The formTypeName value
   */
  public String getFormTypeName() {
    return formTypeName;
  }


  /**
   * Gets the descriptionOfService attribute of the TicketSunMaintenance object
   *
   * @return The descriptionOfService value
   */
  public String getDescriptionOfService() {
    return descriptionOfService;
  }


  /**
   * Gets the replacementParts attribute of the TicketSunMaintenance object
   *
   * @return The replacementParts value
   */
  public TicketReplacementPartList getTicketReplacementPartList() {
    return ticketReplacementPartList;
  }


  /**
   * Gets the entered attribute of the TicketSunMaintenance object
   *
   * @return The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   * Gets the enteredBy attribute of the TicketSunMaintenance object
   *
   * @return The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   * Gets the modified attribute of the TicketSunMaintenance object
   *
   * @return The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   * Gets the modifiedBy attribute of the TicketSunMaintenance object
   *
   * @return The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   * Gets the enabled attribute of the TicketSunMaintenance object
   *
   * @return The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   * Gets the override attribute of the TicketSunMaintenance object
   *
   * @return The override value
   */
  public boolean getOverride() {
    return override;
  }


  /**
   * Description of the Method
   *
   * @param db    Description of the Parameter
   * @param tmpId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void queryRecord(Connection db, int tmpId) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;

    pst = db.prepareStatement(
        " SELECT tsf.*  " +
        " FROM  ticket_sun_form tsf " +
        " WHERE tsf.form_id = ? ");

    pst.setInt(1, tmpId);
    rs = pst.executeQuery();

    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (System.getProperty("DEBUG") != null) {
      System.out.println("TicketSunMaintenance-> before build parts");
    }

    //retrieve the replacement parts for the asset for this maintenance report
    buildTicketReplacementPartList(db);
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildTicketReplacementPartList(Connection db) throws SQLException {
    ticketReplacementPartList = new TicketReplacementPartList();
    ticketReplacementPartList.queryList(db, this.id);
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean deleteTicketReplacementPartList(Connection db) throws SQLException {
    ticketReplacementPartList = new TicketReplacementPartList();
    return ticketReplacementPartList.deleteList(db, this.id);
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public int update(Connection db) throws SQLException {
    int resultCount = -1;

    boolean doCommit = false;
    try {
      if ((doCommit = db.getAutoCommit()) == true) {
        db.setAutoCommit(false);
      }
      PreparedStatement pst = null;
      StringBuffer sql = new StringBuffer();
      sql.append(
          "UPDATE ticket_sun_form SET " +
          "description_of_service = ? ");

      if (!override) {
        sql.append(
            " , modified = " + DatabaseUtils.getCurrentTimestamp(db) + " , modifiedby = ? ");
      }
      sql.append("WHERE form_id = ? ");
      if (!override) {
        sql.append("AND modified " + ((this.getModified() == null)?"IS NULL ":"= ? "));
      }
      pst = db.prepareStatement(sql.toString());
      int i = 0;
      pst.setString(++i, descriptionOfService);
      if (!override) {
        pst.setInt(++i, modifiedBy);
      }
      pst.setInt(++i, id);
      if (!override && this.getModified() != null) {
        pst.setTimestamp(++i, modified);
      }
      resultCount = pst.executeUpdate();
      pst.close();

      if (resultCount == -1) {
        return resultCount;
      } else if (resultCount == 1) {
        // Delete existing parts and insert part list
        TicketReplacementPartList tmpPartList = this.getTicketReplacementPartList();
        tmpPartList.deleteList(db, this.id);
        // Save the parts
        insertReplacementParts(db, tmpPartList);
      }
      if (doCommit) {
        db.commit();
      }
    } catch (SQLException e) {
      e.printStackTrace(System.out);
      if (doCommit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (doCommit) {
        db.setAutoCommit(true);
      }
    }
    return resultCount;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean delete(Connection db) throws SQLException {

    if (this.getId() == -1) {
      throw new SQLException("Note Id not specified.");
    }
    boolean doCommit = false;
    try {
      if ((doCommit = db.getAutoCommit()) == true) {
        db.setAutoCommit(false);
      }
      deleteTicketReplacementPartList(db);

      PreparedStatement pst = null;
      pst = db.prepareStatement(
          " DELETE FROM ticket_sun_form " +
          " WHERE form_id = ? ");

      pst.setInt(1, this.id);
      pst.execute();
      pst.close();
      if (doCommit) {
        db.commit();
      }
    } catch (SQLException e) {
      e.printStackTrace(System.out);
      if (doCommit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (doCommit) {
        db.setAutoCommit(true);
      }
    }

    return true;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void insert(Connection db) throws SQLException {

    try {
      db.setAutoCommit(false);
      id = DatabaseUtils.getNextSeq(db, "ticket_sun_form_form_id_seq");
      PreparedStatement pst = db.prepareStatement(
          "INSERT INTO  ticket_sun_form " +
          "(" + (id > -1 ? "form_id, " : "") + "link_ticket_id, " +
          "description_of_service, " +
          "enteredby, " +
          "modifiedby) " +
          "VALUES (" + (id > -1 ? "?, " : "") + "?, ?, ?, ?)");
      int i = 0;
      if (id > -1) {
        pst.setInt(++i, id);
      }
      pst.setInt(++i, linkTicketId);
      pst.setString(++i, descriptionOfService);
      pst.setInt(++i, enteredBy);
      pst.setInt(++i, modifiedBy);
      pst.execute();
      id = DatabaseUtils.getCurrVal(db, "ticket_sun_form_form_id_seq", id);
      pst.close();
      // Save the parts
      insertReplacementParts(db, this.getTicketReplacementPartList());
      db.commit();
    } catch (SQLException e) {
      e.printStackTrace(System.out);
      db.rollback();
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
  }


  /**
   * inserts the replacement parts
   *
   * @param db          Description of the Parameter
   * @param tmpPartList Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void insertReplacementParts(Connection db, TicketReplacementPartList tmpPartList) throws SQLException {
    Iterator itr = tmpPartList.iterator();
    while (itr.hasNext()) {
      TicketReplacementPart thisPart = (TicketReplacementPart) itr.next();
      thisPart.setLinkFormId(this.id);
      thisPart.insert(db);
    }
  }


  /**
   * Description of the Method
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildRecord(ResultSet rs) throws SQLException {

    id = rs.getInt("form_id");
    linkTicketId = rs.getInt("link_ticket_id");
    descriptionOfService = rs.getString("description_of_service");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
    enabled = rs.getBoolean("enabled");
  }

}

