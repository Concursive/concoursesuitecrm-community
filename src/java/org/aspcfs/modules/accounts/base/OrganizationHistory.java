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
package org.aspcfs.modules.accounts.base;

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Description of the Class
 *
 * @author partha
 * @version $Id$
 * @created May 27, 2005
 */
public class OrganizationHistory extends GenericBean {
  public final static int ACCOUNT = 1;
  public final static int CONTACT = 2;
  public final static int COMPLETE_ACTIVITY = 3;
  public final static int OPPORTUNITY = 4;
  public final static int QUOTE = 5;
  public final static int PROJECT = 6;
  public final static int SERVICE_CONTRACT = 7;
  public final static int ASSET = 8;
  public final static int TICKET = 9;
  public final static int ACCOUNT_DOCUMENT = 10;
  public final static int RELATIONSHIP = 11;
  public final static int NOTE = 12;
  public final static int CANCELED_ACTIVITY = 13;
  public final static int CFSNOTE = 14;
  public final static int CAMPAIGN = 15;
  public final static int TASK = 16;
  public final static int USER_NOTE = 17;

  private int id = -1;
  private int contactId = -1;
  private int orgId = -1;
  private int linkObjectId = -1;
  private int linkItemId = -1;
  private String type = null;
  private String description = null;
  private String status = null;
  private boolean enabled = false;
  private int level = 10;

  private int enteredBy = -1;
  private int modifiedBy = -1;
  private java.sql.Timestamp entered = null;
  private java.sql.Timestamp modified = null;
  //related records
  private String linkObjectName = null;
  private Contact contact = null;
  private boolean reset = false;
  private String nameLast = null;


  /**
   * Gets the id attribute of the OrganizationHistory object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Sets the id attribute of the OrganizationHistory object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the OrganizationHistory object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Gets the contactId attribute of the OrganizationHistory object
   *
   * @return The contactId value
   */
  public int getContactId() {
    return contactId;
  }


  /**
   * Sets the contactId attribute of the OrganizationHistory object
   *
   * @param tmp The new contactId value
   */
  public void setContactId(int tmp) {
    this.contactId = tmp;
  }


  /**
   * Sets the contactId attribute of the OrganizationHistory object
   *
   * @param tmp The new contactId value
   */
  public void setContactId(String tmp) {
    this.contactId = Integer.parseInt(tmp);
  }


  /**
   * Gets the orgId attribute of the OrganizationHistory object
   *
   * @return The orgId value
   */
  public int getOrgId() {
    return orgId;
  }


  /**
   * Sets the orgId attribute of the OrganizationHistory object
   *
   * @param tmp The new orgId value
   */
  public void setOrgId(int tmp) {
    this.orgId = tmp;
  }


  /**
   * Sets the orgId attribute of the OrganizationHistory object
   *
   * @param tmp The new orgId value
   */
  public void setOrgId(String tmp) {
    this.orgId = Integer.parseInt(tmp);
  }


  /**
   * Gets the linkObjectId attribute of the OrganizationHistory object
   *
   * @return The linkObjectId value
   */
  public int getLinkObjectId() {
    return linkObjectId;
  }


  /**
   * Sets the linkObjectId attribute of the OrganizationHistory object
   *
   * @param tmp The new linkObjectId value
   */
  public void setLinkObjectId(int tmp) {
    this.linkObjectId = tmp;
  }


  /**
   * Sets the linkObjectId attribute of the OrganizationHistory object
   *
   * @param tmp The new linkObjectId value
   */
  public void setLinkObjectId(String tmp) {
    this.linkObjectId = Integer.parseInt(tmp);
  }


  /**
   * Gets the linkItemId attribute of the OrganizationHistory object
   *
   * @return The linkItemId value
   */
  public int getLinkItemId() {
    return linkItemId;
  }


  /**
   * Sets the linkItemId attribute of the OrganizationHistory object
   *
   * @param tmp The new linkItemId value
   */
  public void setLinkItemId(int tmp) {
    this.linkItemId = tmp;
  }


  /**
   * Sets the linkItemId attribute of the OrganizationHistory object
   *
   * @param tmp The new linkItemId value
   */
  public void setLinkItemId(String tmp) {
    this.linkItemId = Integer.parseInt(tmp);
  }


  /**
   * Gets the type attribute of the OrganizationHistory object
   *
   * @return The type value
   */
  public String getType() {
    return type;
  }


  /**
   * Sets the type attribute of the OrganizationHistory object
   *
   * @param tmp The new type value
   */
  public void setType(String tmp) {
    this.type = tmp;
  }


  /**
   * Gets the description attribute of the OrganizationHistory object
   *
   * @return The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   * Sets the description attribute of the OrganizationHistory object
   *
   * @param tmp The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   * Gets the status attribute of the OrganizationHistory object
   *
   * @return The status value
   */
  public String getStatus() {
    return status;
  }


  /**
   * Sets the status attribute of the OrganizationHistory object
   *
   * @param tmp The new status value
   */
  public void setStatus(String tmp) {
    this.status = tmp;
  }


  /**
   * Gets the enabled attribute of the OrganizationHistory object
   *
   * @return The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   * Sets the enabled attribute of the OrganizationHistory object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   * Sets the enabled attribute of the OrganizationHistory object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the level attribute of the OrganizationHistory object
   *
   * @return The level value
   */
  public int getLevel() {
    return level;
  }


  /**
   * Sets the level attribute of the OrganizationHistory object
   *
   * @param tmp The new level value
   */
  public void setLevel(int tmp) {
    this.level = tmp;
  }


  /**
   * Sets the level attribute of the OrganizationHistory object
   *
   * @param tmp The new level value
   */
  public void setLevel(String tmp) {
    this.level = Integer.parseInt(tmp);
  }


  /**
   * Gets the enteredBy attribute of the OrganizationHistory object
   *
   * @return The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   * Sets the enteredBy attribute of the OrganizationHistory object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   * Sets the enteredBy attribute of the OrganizationHistory object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   * Gets the modifiedBy attribute of the OrganizationHistory object
   *
   * @return The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   * Sets the modifiedBy attribute of the OrganizationHistory object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   * Sets the modifiedBy attribute of the OrganizationHistory object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   * Gets the entered attribute of the OrganizationHistory object
   *
   * @return The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   * Sets the entered attribute of the OrganizationHistory object
   *
   * @param tmp The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   * Sets the entered attribute of the OrganizationHistory object
   *
   * @param tmp The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Gets the modified attribute of the OrganizationHistory object
   *
   * @return The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   * Sets the modified attribute of the OrganizationHistory object
   *
   * @param tmp The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   * Sets the modified attribute of the OrganizationHistory object
   *
   * @param tmp The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Gets the linkObjectName attribute of the OrganizationHistory object
   *
   * @return The linkObjectName value
   */
  public String getLinkObjectName() {
    return linkObjectName;
  }


  /**
   * Sets the linkObjectName attribute of the OrganizationHistory object
   *
   * @param tmp The new linkObjectName value
   */
  public void setLinkObjectName(String tmp) {
    this.linkObjectName = tmp;
  }


  /**
   * Gets the contact attribute of the OrganizationHistory object
   *
   * @return The contact value
   */
  public Contact getContact() {
    return contact;
  }


  /**
   * Sets the contact attribute of the OrganizationHistory object
   *
   * @param tmp The new contact value
   */
  public void setContact(Contact tmp) {
    this.contact = tmp;
  }


  /**
   * Gets the reset attribute of the OrganizationHistory object
   *
   * @return The reset value
   */
  public boolean getReset() {
    return reset;
  }


  /**
   * Sets the reset attribute of the OrganizationHistory object
   *
   * @param tmp The new reset value
   */
  public void setReset(boolean tmp) {
    this.reset = tmp;
  }


  /**
   * Sets the reset attribute of the OrganizationHistory object
   *
   * @param tmp The new reset value
   */
  public void setReset(String tmp) {
    this.reset = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the nameLast attribute of the OrganizationHistory object
   *
   * @return The nameLast value
   */
  public String getNameLast() {
    return nameLast;
  }


  /**
   * Sets the nameLast attribute of the OrganizationHistory object
   *
   * @param tmp The new nameLast value
   */
  public void setNameLast(String tmp) {
    this.nameLast = tmp;
  }


  /**
   * Constructor for the OrganizationHistory object
   */
  public OrganizationHistory() {
  }


  /**
   * Constructor for the OrganizationHistory object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public OrganizationHistory(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Constructor for the OrganizationHistory object
   *
   * @param db Description of the Parameter
   * @param id Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public OrganizationHistory(Connection db, int id) throws SQLException {
    queryRecord(db, id);
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
      throw new SQLException("Invalid Organization History ID");
    }
    StringBuffer sb = new StringBuffer(
        "SELECT history.*, c.namelast AS namelast " +
        "FROM history " +
        "LEFT JOIN contact c ON (history.contact_id = c.contact_id) " +
        "LEFT JOIN organization o ON (history.org_id = o.org_id) " +
        "WHERE history_id = ? ");
    PreparedStatement pst = db.prepareStatement(sb.toString());
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (this.id == -1) {
      throw new SQLException("Organization History Entry not found");
    }
    if (this.getContactId() != -1) {
      buildContactRecord(db);
    }
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean queryRecord(Connection db) throws SQLException {
    if (linkObjectId == -1 || linkItemId == -1) {
      throw new SQLException("Invalid Module Id or Item Id");
    }
    int i = 0;
    StringBuffer sb = new StringBuffer(
        "SELECT history.*, c.namelast AS namelast " +
        "FROM history " +
        "LEFT JOIN contact c ON (history.contact_id = c.contact_id) " +
        "LEFT JOIN organization o ON (history.org_id = o.org_id) " +
        "WHERE link_object_id = ? " +
        "AND link_item_id = ? ");
    if (contactId != -1) {
      sb.append("AND history.contact_id = ? AND history.org_id IS NULL ");
    } else if (orgId != -1) {
      sb.append("AND history.org_id = ? AND history.contact_id IS NULL ");
    }
    PreparedStatement pst = db.prepareStatement(sb.toString());
    pst.setInt(++i, linkObjectId);
    pst.setInt(++i, linkItemId);
    if (contactId != -1) {
      DatabaseUtils.setInt(pst, ++i, contactId);
    } else if (orgId != -1) {
      DatabaseUtils.setInt(pst, ++i, orgId);
    }
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (this.id == -1) {
      return false;
    }
    return true;
  }


  /**
   * Description of the Method
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    //contact_history table
    this.setId(rs.getInt("history_id"));
    contactId = DatabaseUtils.getInt(rs, "contact_id");
    orgId = DatabaseUtils.getInt(rs, "org_id");
    linkObjectId = DatabaseUtils.getInt(rs, "link_object_id");
    linkItemId = DatabaseUtils.getInt(rs, "link_item_id");
    level = DatabaseUtils.getInt(rs, "level");
    status = rs.getString("status");
    type = rs.getString("type");
    description = rs.getString("description");
    enabled = rs.getBoolean("enabled");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");

    //contact table
    nameLast = rs.getString("namelast");
  }


  /**
   * Gets the moduleObjectName attribute of the OrganizationHistory object
   *
   * @return The moduleObjectName value
   */
  public String getModuleObjectName() {
    String result = null;
    switch (linkObjectId) {
      case OrganizationHistory.ACCOUNT:
        result = "account";
        break;
      case OrganizationHistory.COMPLETE_ACTIVITY:
        result = "completeActivity";
        break;
      case OrganizationHistory.CANCELED_ACTIVITY:
        result = "canceledActivity";
        break;
      case OrganizationHistory.ASSET:
        result = "asset";
        break;
      case OrganizationHistory.CONTACT:
        result = "contact";
        break;
      case OrganizationHistory.ACCOUNT_DOCUMENT:
        result = "document";
        break;
      case OrganizationHistory.OPPORTUNITY:
        result = "opportunity";
        break;
      case OrganizationHistory.PROJECT:
        result = "project";
        break;
      case OrganizationHistory.QUOTE:
        result = "quote";
        break;
      case OrganizationHistory.RELATIONSHIP:
        result = "relationship";
        break;
      case OrganizationHistory.SERVICE_CONTRACT:
        result = "serviceContract";
        break;
      case OrganizationHistory.TICKET:
        result = "ticket";
        break;
      case OrganizationHistory.NOTE:
        result = "note";
        break;
      case OrganizationHistory.USER_NOTE:
        result = "notes";
        break;

      default:
        result = "ticket";
        break;
    }
    return result;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    id = DatabaseUtils.getNextSeq(db, "history_history_id_seq");
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "INSERT INTO history ( " +
        (id > -1 ? "history_id, " : "") +
        "contact_id, org_id, link_object_id, link_item_id, " + DatabaseUtils.addQuotes(db, "level") + ", " +
        "status, " + DatabaseUtils.addQuotes(db, "type") + ", description, enabled, ");
    sql.append("entered, modified, ");
    sql.append("enteredBy, modifiedBy ) ");
    sql.append(
        "VALUES (" + (id > -1 ? "?, " : "") + "?, ?, ?, ?, ?, ?, ?, ?, ?, ");
    if (entered != null) {
      sql.append("?, ");
    } else {
      sql.append(DatabaseUtils.getCurrentTimestamp(db) + ", ");
    }
    if (modified != null) {
      sql.append("?, ");
    } else {
      sql.append(DatabaseUtils.getCurrentTimestamp(db) + ", ");
    }
    sql.append("?, ?) ");
    int i = 0;
    pst = db.prepareStatement(sql.toString());
    if (id > -1) {
      pst.setInt(++i, id);
    }
    DatabaseUtils.setInt(pst, ++i, contactId);
    DatabaseUtils.setInt(pst, ++i, orgId);
    pst.setInt(++i, linkObjectId);
    DatabaseUtils.setInt(pst, ++i, linkItemId);
    pst.setInt(++i, level);
    pst.setString(++i, status);
    pst.setString(++i, type);
    pst.setString(++i, description);
    pst.setBoolean(++i, enabled);
    if (entered != null) {
      pst.setTimestamp(++i, entered);
    }
    if (modified != null) {
      pst.setTimestamp(++i, modified);
    }
    pst.setInt(++i, this.getEnteredBy());
    pst.setInt(++i, this.getModifiedBy());
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "history_history_id_seq", id);
    return true;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public int update(Connection db) throws SQLException {
    int resultCount = 0;
    if (this.getId() == -1) {
      throw new SQLException("Organization History ID was not specified");
    }
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "UPDATE history " +
        "SET description = ?, enabled = ?, " + DatabaseUtils.addQuotes(db, "level") + " = ?, " + DatabaseUtils.addQuotes(db, "type") + " = ?, ");
    if (reset) {
      sql.append("contact_id = ?, org_id = ?, ");
    }
    sql.append("modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", ");
    sql.append("modifiedby = ? WHERE history_id = ? ");
    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setString(++i, this.getDescription());
    pst.setBoolean(++i, this.getEnabled());
    pst.setInt(++i, this.getLevel());
    pst.setString(++i, this.getType());
    if (reset) {
      DatabaseUtils.setInt(pst, ++i, this.getContactId());
      DatabaseUtils.setInt(pst, ++i, this.getOrgId());
    }
    DatabaseUtils.setInt(pst, ++i, this.getModifiedBy());
    DatabaseUtils.setInt(pst, ++i, id);
    resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }


  /**
   * Gets the descriptionHeader attribute of the OrganizationHistory object
   *
   * @return The descriptionHeader value
   */
  public String getDescriptionHeader() {
    if (description.trim().length() > 100) {
      return (description.substring(0, 100) + "...");
    } else {
      return getDescription();
    }
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
      throw new SQLException("Organization History Id was not specified");
    }
    int recordCount = 0;
    PreparedStatement pst = db.prepareStatement(
        "DELETE FROM history " +
        "WHERE history_id = ? ");
    pst.setInt(1, id);
    recordCount = pst.executeUpdate();
    pst.close();
    if (recordCount == 0) {
      return false;
    }
    return true;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildContactRecord(Connection db) throws SQLException {
    this.contact = new Contact(db, this.getContactId());
  }


  /**
   * Gets the permission attribute of the OrganizationHistory object
   *
   * @param modify Description of the Parameter
   * @return The permission value
   */
  public String getPermission(boolean modify) {
    String result = null;
    switch (linkObjectId) {
      case OrganizationHistory.COMPLETE_ACTIVITY:
        result = (modify ? "accounts-accounts-contacts-calls-edit" : "accounts-accounts-contacts-calls-view");
        break;
      case OrganizationHistory.CANCELED_ACTIVITY:
        result = (modify ? "accounts-accounts-contacts-calls-edit" : "accounts-accounts-contacts-calls-view");
        break;
      case OrganizationHistory.ASSET:
        result = (modify ? "accounts-assets-edit" : "accounts-assets-view");
        break;
      case OrganizationHistory.ACCOUNT_DOCUMENT:
        result = (modify ? "accounts-accounts-documents-edit" : "accounts-accounts-documents-view");
        break;
      case OrganizationHistory.OPPORTUNITY:
        result = (modify ? "accounts-accounts-opportunities-edit" : "accounts-accounts-opportunities-view");
        break;
      case OrganizationHistory.PROJECT:
        result = (modify ? "accounts-projects-view" : "accounts-projects-view");
        break;
      case OrganizationHistory.QUOTE:
        result = (modify ? "accounts-quotes-edit" : "accounts-quotes-view");
        break;
      case OrganizationHistory.RELATIONSHIP:
        result = (modify ? "m" : "accounts-accounts-relationships-view");
        break;
      case OrganizationHistory.SERVICE_CONTRACT:
        result = (modify ? "accounts-service-contracts-edit" : "accounts-service-contracts-view");
        break;
      case OrganizationHistory.TICKET:
        result = (modify ? "accounts-accounts-tickets-edit" : "accounts-accounts-tickets-view");
        break;
      case OrganizationHistory.TASK:
        result = (modify ? "myhomepage-tasks-edit" : "myhomepage-tasks-view");
        break;
      case OrganizationHistory.NOTE:
        result = (modify ? "accounts-accounts-history-edit" : "v");
        break;
      case OrganizationHistory.CFSNOTE:
        result = (modify ? "m" : "myhomepage-inbox-view");
        break;
      case OrganizationHistory.CAMPAIGN:
        result = (modify ? "m" : "accounts-accounts-contacts-messages-view");
        break;
      default:
        result = (modify ? "m" : "v");
        break;
    }
    return result;
  }


  /**
   * Gets the deletePermission attribute of the OrganizationHistory object
   *
   * @return The deletePermission value
   */
  public String getDeletePermission() {
    if (this.getLinkObjectId() == OrganizationHistory.NOTE) {
      return "accounts-accounts-history-delete";
    }
    return "can_not_delete";
  }


  /**
   * Gets the viewOrModifyPermission attribute of the OrganizationHistory
   * object
   *
   * @return The viewOrModifyPermission value
   */
  public String getViewOrModifyOrDeletePermission() {
    String modify = this.getPermission(true);
    String view = this.getPermission(false);
    String delete = this.getDeletePermission();
    StringBuffer result = new StringBuffer("");
    if (view != null && !"".equals(view)) {
      result.append(view);
      if (modify != null && !"".equals(modify)) {
        result.append("," + modify);
      }
    } else {
      if (modify != null && !"".equals(modify)) {
        result.append(modify);
      }
    }
    if (delete != null && !"".equals(delete) && !"can_not_delete".equals(
        delete)) {
      if (!result.toString().equals("")) {
        result.append(",");
      }
      result.append(delete);
    }
    return result.toString();
  }
}

