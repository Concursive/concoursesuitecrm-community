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

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.modules.accounts.base.OrganizationHistory;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Description of the Class
 *
 * @author partha
 * @version $Id: ContactHistory.java,v 1.1.2.3 2005/06/02 18:22:46 partha Exp
 *          $
 * @created May 27, 2005
 */
public class ContactHistory extends GenericBean {
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
  private boolean reset = false;


  /**
   * Gets the id attribute of the ContactHistory object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Sets the id attribute of the ContactHistory object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the ContactHistory object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Gets the contactId attribute of the ContactHistory object
   *
   * @return The contactId value
   */
  public int getContactId() {
    return contactId;
  }


  /**
   * Sets the contactId attribute of the ContactHistory object
   *
   * @param tmp The new contactId value
   */
  public void setContactId(int tmp) {
    this.contactId = tmp;
  }


  /**
   * Sets the contactId attribute of the ContactHistory object
   *
   * @param tmp The new contactId value
   */
  public void setContactId(String tmp) {
    this.contactId = Integer.parseInt(tmp);
  }


  /**
   * Gets the orgId attribute of the ContactHistory object
   *
   * @return The orgId value
   */
  public int getOrgId() {
    return orgId;
  }


  /**
   * Sets the orgId attribute of the ContactHistory object
   *
   * @param tmp The new orgId value
   */
  public void setOrgId(int tmp) {
    this.orgId = tmp;
  }


  /**
   * Sets the orgId attribute of the ContactHistory object
   *
   * @param tmp The new orgId value
   */
  public void setOrgId(String tmp) {
    this.orgId = Integer.parseInt(tmp);
  }


  /**
   * Gets the linkObjectId attribute of the ContactHistory object
   *
   * @return The linkObjectId value
   */
  public int getLinkObjectId() {
    return linkObjectId;
  }


  /**
   * Sets the linkObjectId attribute of the ContactHistory object
   *
   * @param tmp The new linkObjectId value
   */
  public void setLinkObjectId(int tmp) {
    this.linkObjectId = tmp;
  }


  /**
   * Sets the linkObjectId attribute of the ContactHistory object
   *
   * @param tmp The new linkObjectId value
   */
  public void setLinkObjectId(String tmp) {
    this.linkObjectId = Integer.parseInt(tmp);
  }


  /**
   * Gets the linkItemId attribute of the ContactHistory object
   *
   * @return The linkItemId value
   */
  public int getLinkItemId() {
    return linkItemId;
  }


  /**
   * Sets the linkItemId attribute of the ContactHistory object
   *
   * @param tmp The new linkItemId value
   */
  public void setLinkItemId(int tmp) {
    this.linkItemId = tmp;
  }


  /**
   * Sets the linkItemId attribute of the ContactHistory object
   *
   * @param tmp The new linkItemId value
   */
  public void setLinkItemId(String tmp) {
    this.linkItemId = Integer.parseInt(tmp);
  }


  /**
   * Gets the type attribute of the ContactHistory object
   *
   * @return The type value
   */
  public String getType() {
    return type;
  }


  /**
   * Sets the type attribute of the ContactHistory object
   *
   * @param tmp The new type value
   */
  public void setType(String tmp) {
    this.type = tmp;
  }


  /**
   * Gets the description attribute of the ContactHistory object
   *
   * @return The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   * Sets the description attribute of the ContactHistory object
   *
   * @param tmp The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   * Gets the status attribute of the ContactHistory object
   *
   * @return The status value
   */
  public String getStatus() {
    return status;
  }


  /**
   * Sets the status attribute of the ContactHistory object
   *
   * @param tmp The new status value
   */
  public void setStatus(String tmp) {
    this.status = tmp;
  }


  /**
   * Gets the enabled attribute of the ContactHistory object
   *
   * @return The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   * Sets the enabled attribute of the ContactHistory object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   * Sets the enabled attribute of the ContactHistory object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the level attribute of the ContactHistory object
   *
   * @return The level value
   */
  public int getLevel() {
    return level;
  }


  /**
   * Sets the level attribute of the ContactHistory object
   *
   * @param tmp The new level value
   */
  public void setLevel(int tmp) {
    this.level = tmp;
  }


  /**
   * Sets the level attribute of the ContactHistory object
   *
   * @param tmp The new level value
   */
  public void setLevel(String tmp) {
    this.level = Integer.parseInt(tmp);
  }


  /**
   * Gets the enteredBy attribute of the ContactHistory object
   *
   * @return The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   * Sets the enteredBy attribute of the ContactHistory object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   * Sets the enteredBy attribute of the ContactHistory object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   * Gets the modifiedBy attribute of the ContactHistory object
   *
   * @return The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   * Sets the modifiedBy attribute of the ContactHistory object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   * Sets the modifiedBy attribute of the ContactHistory object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   * Gets the entered attribute of the ContactHistory object
   *
   * @return The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   * Sets the entered attribute of the ContactHistory object
   *
   * @param tmp The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   * Sets the entered attribute of the ContactHistory object
   *
   * @param tmp The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Gets the modified attribute of the ContactHistory object
   *
   * @return The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   * Sets the modified attribute of the ContactHistory object
   *
   * @param tmp The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   * Sets the modified attribute of the ContactHistory object
   *
   * @param tmp The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Gets the linkObjectName attribute of the ContactHistory object
   *
   * @return The linkObjectName value
   */
  public String getLinkObjectName() {
    return linkObjectName;
  }


  /**
   * Sets the linkObjectName attribute of the ContactHistory object
   *
   * @param tmp The new linkObjectName value
   */
  public void setLinkObjectName(String tmp) {
    this.linkObjectName = tmp;
  }


  /**
   * Gets the reset attribute of the ContactHistory object
   *
   * @return The reset value
   */
  public boolean getReset() {
    return reset;
  }


  /**
   * Sets the reset attribute of the ContactHistory object
   *
   * @param tmp The new reset value
   */
  public void setReset(boolean tmp) {
    this.reset = tmp;
  }


  /**
   * Sets the reset attribute of the ContactHistory object
   *
   * @param tmp The new reset value
   */
  public void setReset(String tmp) {
    this.reset = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Constructor for the ContactHistory object
   */
  public ContactHistory() {
  }


  /**
   * Constructor for the ContactHistory object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public ContactHistory(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Constructor for the ContactHistory object
   *
   * @param db Description of the Parameter
   * @param id Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public ContactHistory(Connection db, int id) throws SQLException {
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
      throw new SQLException("Invalid Contact History ID");
    }
    StringBuffer sb = new StringBuffer(
        "SELECT * " +
        "FROM history " +
        "WHERE history_id = ? " +
        "AND org_id IS NULL ");
    PreparedStatement pst = db.prepareStatement(sb.toString());
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (this.id == -1) {
      throw new SQLException("Contact History Entry not found");
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
    if (contactId == -1 || linkObjectId == -1 || linkItemId == -1) {
      throw new SQLException("Invalid Contact Id or Module Id or Item Id");
    }
    int i = 0;
    StringBuffer sb = new StringBuffer(
        "SELECT * " +
        "FROM history " +
        "WHERE contact_id = ? " +
        "AND link_object_id = ? " +
        "AND link_item_id = ? " +
        "AND org_id IS NULL ");
    PreparedStatement pst = db.prepareStatement(sb.toString());
    pst.setInt(++i, contactId);
    pst.setInt(++i, linkObjectId);
    pst.setInt(++i, linkItemId);
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
    DatabaseUtils.setInt(pst, ++i, linkObjectId);
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
      throw new SQLException("Contact History ID was not specified");
    }
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "UPDATE history " +
        "SET description = ?, enabled = ?, " + DatabaseUtils.addQuotes(db, "level") + " = ?, " + DatabaseUtils.addQuotes(db, "type") + " = ?, ");
    if (reset) {
      sql.append(" contact_id = ?, ");
    }
    sql.append("modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", ");
    sql.append(
        "modifiedby = ? " +
        "WHERE history_id = ? ");
    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setString(++i, this.getDescription());
    pst.setBoolean(++i, this.getEnabled());
    pst.setInt(++i, this.getLevel());
    pst.setString(++i, this.getType());
    if (reset) {
      pst.setInt(++i, this.getContactId());
    }
    DatabaseUtils.setInt(pst, ++i, this.getModifiedBy());
    DatabaseUtils.setInt(pst, ++i, id);
    resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }


  /**
   * Gets the moduleObjectName attribute of the ContactHistory object
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
      case OrganizationHistory.CAMPAIGN:
        result = "campaign";
        break;
      case OrganizationHistory.TASK:
        result = "task";
        break;
      default:
        result = "ticket";
        break;
    }
    return result;
  }


  /**
   * Gets the timeZoneParams attribute of the ContactHistory class
   *
   * @return The timeZoneParams value
   */
  public static ArrayList getTimeZoneParams() {
    ArrayList thisList = new ArrayList();
    thisList.add("entered");
    thisList.add("modified");
    return thisList;
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
      throw new SQLException("Contact History Id was not specified");
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
   * @return Description of the Return Value
   */
  public boolean canDisplayInPopup() {
    boolean result = false;
    switch (linkObjectId) {
      case OrganizationHistory.COMPLETE_ACTIVITY:
      case OrganizationHistory.CANCELED_ACTIVITY:
      case OrganizationHistory.OPPORTUNITY:
      case OrganizationHistory.NOTE:
      case OrganizationHistory.CAMPAIGN:
        result = true;
        break;
      case OrganizationHistory.QUOTE:
      case OrganizationHistory.RELATIONSHIP:
      case OrganizationHistory.SERVICE_CONTRACT:
      case OrganizationHistory.TICKET:
      case OrganizationHistory.CFSNOTE:
      case OrganizationHistory.TASK:
      case OrganizationHistory.ASSET:
        result = false;
        break;
      default:
        result = false;
        break;
    }
    return result;
  }


  /**
   * Gets the permission attribute of the ContactHistory object
   *
   * @param modify          Description of the Parameter
   * @param inContactModule Description of the Parameter
   * @return The permission value
   */
  public String getPermission(boolean modify, boolean inContactModule) {
    String result = null;
    switch (linkObjectId) {
      case OrganizationHistory.COMPLETE_ACTIVITY:
        if (inContactModule) {
          result = (modify ? "contacts-external_contacts-calls-edit" : "contacts-external_contacts-calls-view");
        } else {
          result = (modify ? "accounts-accounts-contacts-calls-edit" : "accounts-accounts-contacts-calls-view");
        }
        break;
      case OrganizationHistory.CANCELED_ACTIVITY:
        if (inContactModule) {
          result = (modify ? "contacts-external_contacts-calls-edit" : "contacts-external_contacts-calls-view");
        } else {
          result = (modify ? "accounts-accounts-contacts-calls-edit" : "accounts-accounts-contacts-calls-view");
        }
        break;
      case OrganizationHistory.ASSET:
        if (inContactModule) {
          result = (modify ? "accounts-assets-edit" : "accounts-assets-view");
        } else {
          result = (modify ? "accounts-assets-edit" : "accounts-assets-view");
        }
        break;
      case OrganizationHistory.OPPORTUNITY:
        if (inContactModule) {
          result = (modify ? "contacts-external_contacts-opportunities-edit" : "contacts-external_contacts-opportunities-view");
        } else {
          result = (modify ? "accounts-accounts-contacts-opportunities-edit" : "accounts-accounts-contacts-opportunities-view");
        }
        break;
      case OrganizationHistory.QUOTE:
        if (inContactModule) {
          result = (modify ? "quotes-quotes-edit" : "quotes-quotes-view");
        } else {
          result = (modify ? "accounts-quotes-edit" : "accounts-quotes-view");
        }
        break;
      case OrganizationHistory.SERVICE_CONTRACT:
        if (inContactModule) {
          result = (modify ? "accounts-service-contracts-edit" : "accounts-service-contracts-view");
        } else {
          result = (modify ? "accounts-service-contracts-edit" : "accounts-service-contracts-view");
        }
        break;
      case OrganizationHistory.TICKET:
        if (inContactModule) {
          result = (modify ? "tickets-tickets-edit" : "tickets-tickets-view");
        } else {
          result = (modify ? "accounts-accounts-tickets-edit" : "accounts-accounts-tickets-view");
        }
        break;
      case OrganizationHistory.TASK:
        if (inContactModule) {
          result = (modify ? "myhomepage-tasks-edit" : "myhomepage-tasks-view");
        } else {
          result = (modify ? "myhomepage-tasks-edit" : "myhomepage-tasks-view");
        }
        break;
      case OrganizationHistory.NOTE:
        if (inContactModule) {
          result = (modify ? "contacts-external_contacts-history-edit" : "v");
        } else {
          result = (modify ? "accounts-accounts-contacts-history-edit" : "v");
        }
        break;
      case OrganizationHistory.CFSNOTE:
        if (inContactModule) {
          result = (modify ? "myhomepage-inbox-edit" : "myhomepage-inbox-view");
        } else {
          result = (modify ? "m" : "myhomepage-inbox-view");
        }
        break;
      case OrganizationHistory.CAMPAIGN:
        if (inContactModule) {
          result = (modify ? "m" : "contacts-external_contacts-messages-view");
        } else {
          result = (modify ? "m" : "accounts-accounts-contacts-messages-view");
        }
        break;
      default:
        result = (modify ? "m" : "v");
        break;
    }
    return result;
  }


  /**
   * Gets the deletePermission attribute of the ContactHistory object
   *
   * @param inContactModule Description of the Parameter
   * @return The deletePermission value
   */
  public String getDeletePermission(boolean inContactModule) {
    if (this.getLinkObjectId() == OrganizationHistory.NOTE) {
      if (inContactModule) {
        return "contacts-external_contacts-history-delete";
      } else {
        return "accounts-accounts-contacts-history-delete";
      }
    }
    return "can_not_delete";
  }


  /**
   * Gets the viewOrModifyPermission attribute of the ContactHistory object
   *
   * @param isContactModule Description of the Parameter
   * @return The viewOrModifyPermission value
   */
  public String getViewOrModifyOrDeletePermission(boolean isContactModule) {
    String modify = this.getPermission(true, isContactModule);
    String view = this.getPermission(false, isContactModule);
    String delete = this.getDeletePermission(isContactModule);
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


  /**
   * Description of the Method
   *
   * @param db    Description of the Parameter
   * @param objId Description of the Parameter
   * @param id    Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public static boolean deleteObject(Connection db, int objId, int id) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "DELETE FROM history " +
        "WHERE link_object_id = ? " +
        "AND link_item_id = ? ");
    pst.setInt(1, objId);
    pst.setInt(2, id);
    boolean result = pst.execute();
    pst.close();
    return result;
  }


  /**
   * Description of the Method
   *
   * @param db    Description of the Parameter
   * @param objId Description of the Parameter
   * @param id    Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public static int retrieveRecordCount(Connection db, int objId, int id) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "SELECT count(*) AS record_count " +
        "FROM history " +
        "WHERE link_object_id = ? " +
        "AND link_item_id = ? ");
    pst.setInt(1, objId);
    pst.setInt(2, id);
    ResultSet rs = pst.executeQuery();
    int resultCount = -1;
    if (rs.next()) {
      resultCount = DatabaseUtils.getInt(rs, "record_count");
    }
    pst.close();
    return resultCount;
  }


  /**
   * Description of the Method
   *
   * @param db     Description of the Parameter
   * @param objId  Description of the Parameter
   * @param id     Description of the Parameter
   * @param enable Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public static boolean trash(Connection db, int objId, int id, boolean enable) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "UPDATE history " +
        "SET enabled = ?, " +
        "modified = " + DatabaseUtils.getCurrentTimestamp(db) + " " +
        "WHERE link_object_id = ? " +
        "AND link_item_id = ? ");
    pst.setBoolean(1, enable);
    pst.setInt(2, objId);
    pst.setInt(3, id);
    boolean result = pst.execute();
    pst.close();
    return result;
  }

}

