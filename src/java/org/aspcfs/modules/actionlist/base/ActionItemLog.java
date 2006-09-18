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
package org.aspcfs.modules.actionlist.base;

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.communications.base.Campaign;
import org.aspcfs.modules.contacts.base.Call;
import org.aspcfs.modules.mycfs.base.CFSNote;
import org.aspcfs.modules.pipeline.base.OpportunityHeader;
import org.aspcfs.modules.tasks.base.Task;
import org.aspcfs.modules.troubletickets.base.Ticket;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;

/**
 * Represents an action performed on a action item
 *
 * @author akhi_m
 * @created April 24, 2003
 */
public class ActionItemLog extends GenericBean {
  private int id = -1;
  private int itemId = -1;
  private int linkItemId = -1;
  private int type = -1;
  private int enteredBy = -1;
  private int modifiedBy = -1;
  private String description = null;
  private java.sql.Timestamp modified = null;
  private java.sql.Timestamp entered = null;


  /**
   * Constructor for the ActionItemLog object
   */
  public ActionItemLog() {
  }


  /**
   * Constructor for the ActionItemLog object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public ActionItemLog(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Sets the id attribute of the ActionItemLog object
   *
   * @param id The new id value
   */
  public void setId(int id) {
    this.id = id;
  }


  /**
   * Sets the itemId attribute of the ActionItemLog object
   *
   * @param itemId The new itemId value
   */
  public void setItemId(int itemId) {
    this.itemId = itemId;
  }


  /**
   * Sets the linkItemId attribute of the ActionItemLog object
   *
   * @param linkItemId The new linkItemId value
   */
  public void setLinkItemId(int linkItemId) {
    this.linkItemId = linkItemId;
  }


  /**
   * Sets the type attribute of the ActionItemLog object
   *
   * @param type The new type value
   */
  public void setType(int type) {
    this.type = type;
  }


  /**
   * Sets the enteredBy attribute of the ActionItemLog object
   *
   * @param enteredBy The new enteredBy value
   */
  public void setEnteredBy(int enteredBy) {
    this.enteredBy = enteredBy;
  }


  /**
   * Sets the modifiedBy attribute of the ActionItemLog object
   *
   * @param modifiedBy The new modifiedBy value
   */
  public void setModifiedBy(int modifiedBy) {
    this.modifiedBy = modifiedBy;
  }


  /**
   * Sets the modified attribute of the ActionItemLog object
   *
   * @param modified The new modified value
   */
  public void setModified(java.sql.Timestamp modified) {
    this.modified = modified;
  }


  /**
   * Sets the entered attribute of the ActionItemLog object
   *
   * @param entered The new entered value
   */
  public void setEntered(java.sql.Timestamp entered) {
    this.entered = entered;
  }


  /**
   * Sets the description attribute of the ActionItemLog object
   *
   * @param description The new description value
   */
  public void setDescription(String description) {
    this.description = description;
  }


  /**
   * Gets the description attribute of the ActionItemLog object
   *
   * @return The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   * Gets the id attribute of the ActionItemLog object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the itemId attribute of the ActionItemLog object
   *
   * @return The itemId value
   */
  public int getItemId() {
    return itemId;
  }


  /**
   * Gets the linkItemId attribute of the ActionItemLog object
   *
   * @return The linkItemId value
   */
  public int getLinkItemId() {
    return linkItemId;
  }


  /**
   * Gets the type attribute of the ActionItemLog object
   *
   * @return The type value
   */
  public int getType() {
    return type;
  }


  /**
   * Gets the typeString attribute of the ActionItemLog object
   *
   * @return The typeString value
   */
  public String getTypeString() {
    String tmp = null;
    switch (type) {
      case Constants.CALL_OBJECT:
        tmp = "Call";
        break;
      case Constants.TICKET_OBJECT:
        tmp = "Ticket";
        break;
      case Constants.TASK_OBJECT:
        tmp = "Task";
        break;
      case Constants.OPPORTUNITY_OBJECT:
        tmp = "Opp";
        break;
      case Constants.MESSAGE_OBJECT:
        tmp = "Message";
        break;
      case Constants.CAMPAIGN_OBJECT:
        tmp = "Message";
        break;
      default:
        break;
    }
    return tmp;
  }


  /**
   * Gets the itemLink attribute of the ActionItemLog object
   *
   * @param contactId Description of the Parameter
   * @return The itemLink value
   */
  public String getItemLink(int contactId) {
    String tmp = null;
    switch (type) {
      case Constants.CALL_OBJECT:
        tmp = "ExternalContactsCalls.do?command=Details&id=" + this.getLinkItemId() + "&contactId=" + contactId;
        break;
      case Constants.TICKET_OBJECT:
        tmp = "TroubleTickets.do?command=Details&id=" + this.getLinkItemId();
        break;
      case Constants.TASK_OBJECT:
        tmp = "MyTasks.do?command=Modify&id=" + this.getLinkItemId();
        break;
      case Constants.OPPORTUNITY_OBJECT:
        tmp = "ExternalContactsOpps.do?command=DetailsOpp&headerId=" + this.getLinkItemId() + "&contactId=" + contactId;
        break;
      case Constants.MESSAGE_OBJECT:
        tmp = "MyCFSInbox.do?command=CFSNoteDetails&id=" + this.getLinkItemId();
        break;
      case Constants.CAMPAIGN_OBJECT:
        tmp = "CampaignManager.do?command=Details&id=" + this.getLinkItemId();
        break;
      default:
        break;
    }
    return tmp;
  }


  /**
   * Gets the enteredBy attribute of the ActionItemLog object
   *
   * @return The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   * Gets the modifiedBy attribute of the ActionItemLog object
   *
   * @return The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   * Gets the modified attribute of the ActionItemLog object
   *
   * @return The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   * Gets the entered attribute of the ActionItemLog object
   *
   * @return The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   * Gets the enteredString attribute of the ActionItemLog object
   *
   * @return The enteredString value
   */
  public String getEnteredString() {
    String tmp = "";
    try {
      return DateFormat.getDateInstance(3).format(entered);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   * Builds description based on the type of the action
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildDescription(Connection db) throws SQLException {
    if (this.getType() == -1) {
      throw new SQLException("Type not specified");
    }
    switch (type) {
      case Constants.CALL_OBJECT:
        Call thisCall = new Call(db, this.getLinkItemId());
        this.description = thisCall.getSubject();
        break;
      case Constants.TICKET_OBJECT:
        Ticket thisTicket = new Ticket(db, this.getLinkItemId());
        this.description = thisTicket.getProblem();
        break;
      case Constants.TASK_OBJECT:
        Task thisTask = new Task(db, this.getLinkItemId());
        this.description = thisTask.getDescription();
        break;
      case Constants.OPPORTUNITY_OBJECT:
        OpportunityHeader thisOpp = new OpportunityHeader(
            db, this.getLinkItemId());
        this.description = thisOpp.getDescription();
        break;
      case Constants.MESSAGE_OBJECT:
        CFSNote thisNote = new CFSNote(
            db, this.getLinkItemId(), this.getEnteredBy(), "sent");
        this.description = thisNote.getSubject();
        break;
      case Constants.CAMPAIGN_OBJECT:
        Campaign thisCampaign = new Campaign(db, this.getLinkItemId());
        this.description = thisCampaign.getSubject();
        break;
      default:
        break;
    }
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @param id Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void queryRecord(Connection db, int id) throws SQLException {
    if (id == -1) {
      throw new SQLException("Id not specified");
    }
    PreparedStatement pst = db.prepareStatement(
        "SELECT al.log_id, al.item_id, al.link_item_id, al." + DatabaseUtils.addQuotes(db, "type")+ ", " +
        "al.enteredby, al.entered, al.modifiedby, al.modified " +
        "FROM action_item_log al " +
        "WHERE log_id = ? ");
    int i = 0;
    pst.setInt(++i, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (id == -1) {
      throw new SQLException("ActionItem Id not found");
    }
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    boolean doCommit = false;
    try {
      if ((doCommit = db.getAutoCommit()) == true) {
        db.setAutoCommit(false);
      }
      int i = 0;
      id = DatabaseUtils.getNextSeq(db, "action_item_log_code_seq");
      PreparedStatement pst = db.prepareStatement(
          "INSERT INTO action_item_log " +
          "(" + (id > -1 ? "log_id, " : "") + "item_id, link_item_id, " + DatabaseUtils.addQuotes(db, "type")+ ", enteredby, modifiedby) " +
          "VALUES (" + (id > -1 ? "?, " : "") + "?, ?, ?, ?, ? ) ");
      if (id > -1) {
        pst.setInt(++i, id);
      }
      pst.setInt(++i, this.getItemId());
      pst.setInt(++i, this.getLinkItemId());
      pst.setInt(++i, this.getType());
      pst.setInt(++i, this.getEnteredBy());
      pst.setInt(++i, this.getModifiedBy());
      pst.execute();
      this.id = DatabaseUtils.getCurrVal(db, "action_item_log_code_seq", id);
      pst.close();
      //update the action item table to reflect the change
      i = 0;
      pst = db.prepareStatement(
          "UPDATE action_item " +
          "SET modified = CURRENT_TIMESTAMP, modifiedby = ? " +
          "WHERE item_id = ? ");
      pst.setInt(++i, this.getModifiedBy());
      pst.setInt(++i, this.getItemId());
      pst.execute();
      pst.close();
      if (doCommit) {
        db.commit();
      }
    } catch (SQLException e) {
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
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean delete(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("ID was not specified");
    }
    int recordCount = 0;
    PreparedStatement pst = db.prepareStatement(
        "DELETE FROM action_item_log " +
        "WHERE log_id = ? ");
    pst.setInt(1, id);
    recordCount = pst.executeUpdate();
    pst.close();
    if (recordCount == 0) {
//      errors.put("actionError", "Item could not be deleted because it no longer exists.");
      return false;
    } else {
      return true;
    }
  }


  /**
   * Deletes the link from the log on deletion of the actual entity
   *
   * @param db       Description of the Parameter
   * @param linkId   Description of the Parameter
   * @param thisType Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public static boolean deleteLink(Connection db, int linkId, int thisType) throws SQLException {
    if (linkId == -1) {
      throw new SQLException("Link Id was not specified");
    }
    int recordCount = 0;
    PreparedStatement pst = db.prepareStatement(
        "DELETE FROM action_item_log " +
        "WHERE link_item_id = ? AND " + DatabaseUtils.addQuotes(db, "type")+ " = ? ");
    pst.setInt(1, linkId);
    pst.setInt(2, thisType);
    recordCount = pst.executeUpdate();
    pst.close();
    return true;
  }


  /**
   * Description of the Method
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("log_id");
    itemId = rs.getInt("item_id");
    linkItemId = rs.getInt("link_item_id");
    type = rs.getInt("type");
    enteredBy = rs.getInt("enteredby");
    entered = rs.getTimestamp("entered");
    modifiedBy = rs.getInt("modifiedby");
    modified = rs.getTimestamp("modified");
  }
}

