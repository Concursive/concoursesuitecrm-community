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

import com.darkhorseventures.database.ConnectionElement;
import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Hashtable;

/**
 * Description of the Class
 *
 * @author Chris
 * @version $Id$
 * @created January 13, 2003
 */
public class Revenue extends GenericBean {

  private int id = -1;
  private int orgId = -1;
  private int transactionId = -1;
  private int month = -1;
  private int year = 0;
  private double amount = 0;
  private int type = -1;
  private int owner = -1;
  private String description = "";

  private int enteredBy = -1;
  private int modifiedBy = -1;
  private java.sql.Timestamp modified = null;
  private java.sql.Timestamp entered = null;

  private String enteredByName = "";
  private String modifiedByName = "";

  private String ownerNameFirst = "";
  private String ownerNameLast = "";

  private String typeName = "";
  private String orgName = "";

  private boolean hasEnabledOwnerAccount = true;


  /**
   * Constructor for the Revenue object
   */
  public Revenue() {
  }


  /**
   * Constructor for the Revenue object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public Revenue(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Constructor for the Revenue object
   *
   * @param db        Description of the Parameter
   * @param revenueId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public Revenue(Connection db, String revenueId) throws SQLException {
    queryRecord(db, Integer.parseInt(revenueId));
  }


  /**
   * Constructor for the Revenue object
   *
   * @param db        Description of the Parameter
   * @param revenueId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public Revenue(Connection db, int revenueId) throws SQLException {
    queryRecord(db, revenueId);
  }


  /**
   * Description of the Method
   *
   * @param db        Description of the Parameter
   * @param revenueId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void queryRecord(Connection db, int revenueId) throws SQLException {
    if (revenueId == -1) {
      throw new SQLException("Revenue ID not specified.");
    }
    PreparedStatement pst = db.prepareStatement(
        "SELECT r.*, " +
            "ct_eb.namelast as eb_namelast, ct_eb.namefirst as eb_namefirst, " +
            "ct_mb.namelast as mb_namelast, ct_mb.namefirst as mb_namefirst, ct_own.namelast as own_namelast, ct_own.namefirst as own_namefirst, rt.description as typename, o.name as orgname " +
            "FROM revenue r " +
            "LEFT JOIN contact ct_eb ON (r.enteredby = ct_eb.user_id) " +
            "LEFT JOIN contact ct_mb ON (r.modifiedby = ct_mb.user_id) " +
            "LEFT JOIN contact ct_own ON (r.owner = ct_own.user_id) " +
            "LEFT JOIN organization o ON (r.org_id = o.org_id) " +
            "LEFT JOIN lookup_revenue_types rt ON (r." + DatabaseUtils.addQuotes(db, "type") + " = rt.code) " +
            "WHERE r.id = ? ");
    pst.setInt(1, revenueId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (revenueId == -1) {
      throw new SQLException("Revenue ID not found.");
    }
  }


  /**
   * Gets the enteredString attribute of the Revenue object
   *
   * @return The enteredString value
   */
  public String getEnteredString() {
    try {
      return DateFormat.getDateInstance(DateFormat.SHORT).format(entered);
    } catch (NullPointerException e) {
    }
    return ("");
  }


  /**
   * Gets the enteredDateTimeString attribute of the Revenue object
   *
   * @return The enteredDateTimeString value
   */
  public String getEnteredDateTimeString() {
    try {
      return DateFormat.getDateTimeInstance(
          DateFormat.SHORT, DateFormat.SHORT).format(entered);
    } catch (NullPointerException e) {
    }
    return ("");
  }


  /**
   * Gets the modifiedString attribute of the Revenue object
   *
   * @return The modifiedString value
   */
  public String getModifiedString() {
    try {
      return DateFormat.getDateInstance(DateFormat.SHORT).format(modified);
    } catch (NullPointerException e) {
    }
    return ("");
  }


  /**
   * Gets the modifiedDateTimeString attribute of the Revenue object
   *
   * @return The modifiedDateTimeString value
   */
  public String getModifiedDateTimeString() {
    try {
      return DateFormat.getDateTimeInstance(
          DateFormat.SHORT, DateFormat.SHORT).format(modified);
    } catch (NullPointerException e) {
    }
    return ("");
  }


  /**
   * Gets the amountValue attribute of the Revenue object
   *
   * @return The amountValue value
   */
  public String getAmountValue() {
    String toReturn = "";
    double value_2dp = (double) Math.round(amount * 100.0) / 100.0;
    toReturn = String.valueOf(value_2dp);
    if (toReturn.endsWith(".0")) {
      toReturn = toReturn.substring(0, toReturn.length() - 2);
    }
    try {
      if (Double.parseDouble(toReturn) == 0.0 || Integer.parseInt(toReturn) == 0) {
        toReturn = "";
      }
    } catch (Exception e) {
      //Use original value
    }

    if (toReturn.indexOf(".") > 0 && toReturn.indexOf(".") == toReturn.length() - 2) {
      return (toReturn + "0");
    } else {
      return toReturn;
    }
  }


  /**
   * Gets the orgName attribute of the Revenue object
   *
   * @return The orgName value
   */
  public String getOrgName() {
    return orgName;
  }


  /**
   * Sets the orgName attribute of the Revenue object
   *
   * @param orgName The new orgName value
   */
  public void setOrgName(String orgName) {
    this.orgName = orgName;
  }


  /**
   * Gets the hasEnabledOwnerAccount attribute of the Revenue object
   *
   * @return The hasEnabledOwnerAccount value
   */
  public boolean getHasEnabledOwnerAccount() {
    return hasEnabledOwnerAccount;
  }


  /**
   * Sets the hasEnabledOwnerAccount attribute of the Revenue object
   *
   * @param hasEnabledOwnerAccount The new hasEnabledOwnerAccount value
   */
  public void setHasEnabledOwnerAccount(boolean hasEnabledOwnerAccount) {
    this.hasEnabledOwnerAccount = hasEnabledOwnerAccount;
  }


  /**
   * Gets the id attribute of the Revenue object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the orgId attribute of the Revenue object
   *
   * @return The orgId value
   */
  public int getOrgId() {
    return orgId;
  }


  /**
   * Gets the transactionId attribute of the Revenue object
   *
   * @return The transactionId value
   */
  public int getTransactionId() {
    return transactionId;
  }


  /**
   * Gets the month attribute of the Revenue object
   *
   * @return The month value
   */
  public int getMonth() {
    return month;
  }


  /**
   * Gets the year attribute of the Revenue object
   *
   * @return The year value
   */
  public int getYear() {
    return year;
  }


  /**
   * Gets the amount attribute of the Revenue object
   *
   * @return The amount value
   */
  public double getAmount() {
    return amount;
  }


  /**
   * Gets the description attribute of the Revenue object
   *
   * @return The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   * Gets the enteredBy attribute of the Revenue object
   *
   * @return The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   * Gets the modifiedBy attribute of the Revenue object
   *
   * @return The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   * Gets the modified attribute of the Revenue object
   *
   * @return The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   * Gets the entered attribute of the Revenue object
   *
   * @return The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   * Gets the enteredByName attribute of the Revenue object
   *
   * @return The enteredByName value
   */
  public String getEnteredByName() {
    return enteredByName;
  }


  /**
   * Gets the modifiedByName attribute of the Revenue object
   *
   * @return The modifiedByName value
   */
  public String getModifiedByName() {
    return modifiedByName;
  }


  /**
   * Gets the typeName attribute of the Revenue object
   *
   * @return The typeName value
   */
  public String getTypeName() {
    return typeName;
  }


  /**
   * Sets the id attribute of the Revenue object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the Revenue object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Sets the orgId attribute of the Revenue object
   *
   * @param tmp The new orgId value
   */
  public void setOrgId(int tmp) {
    this.orgId = tmp;
  }


  /**
   * Sets the orgId attribute of the Revenue object
   *
   * @param tmp The new orgId value
   */
  public void setOrgId(String tmp) {
    this.orgId = Integer.parseInt(tmp);
  }


  /**
   * Sets the transactionId attribute of the Revenue object
   *
   * @param tmp The new transactionId value
   */
  public void setTransactionId(int tmp) {
    this.transactionId = tmp;
  }


  /**
   * Sets the transactionId attribute of the Revenue object
   *
   * @param tmp The new transactionId value
   */
  public void setTransactionId(String tmp) {
    this.transactionId = Integer.parseInt(tmp);
  }


  /**
   * Sets the month attribute of the Revenue object
   *
   * @param tmp The new month value
   */
  public void setMonth(int tmp) {
    this.month = tmp;
  }


  /**
   * Sets the month attribute of the Revenue object
   *
   * @param tmp The new month value
   */
  public void setMonth(String tmp) {
    this.month = Integer.parseInt(tmp);
  }


  /**
   * Sets the year attribute of the Revenue object
   *
   * @param tmp The new year value
   */
  public void setYear(int tmp) {
    this.year = tmp;
  }


  /**
   * Sets the year attribute of the Revenue object
   *
   * @param tmp The new year value
   */
  public void setYear(String tmp) {
    this.year = Integer.parseInt(tmp);
  }


  /**
   * Sets the amount attribute of the Revenue object
   *
   * @param tmp The new amount value
   */
  public void setAmount(double tmp) {
    this.amount = tmp;
  }


  /**
   * Sets the amount attribute of the Revenue object
   *
   * @param tmp The new amount value
   */
  public void setAmount(String tmp) {
    tmp = StringUtils.replace(tmp, ",", "");
    tmp = StringUtils.replace(tmp, "$", "");
    this.amount = Double.parseDouble(tmp);
  }


  /**
   * Gets the monthName attribute of the Revenue object
   *
   * @return The monthName value
   */
  public String getMonthName() {
    String r = "";

    switch (month) {
      case 1:
        r = "January";
        break;
      case 2:
        r = "February";
        break;
      case 3:
        r = "March";
        break;
      case 4:
        r = "April";
        break;
      case 5:
        r = "May";
        break;
      case 6:
        r = "June";
        break;
      case 7:
        r = "July";
        break;
      case 8:
        r = "August";
        break;
      case 9:
        r = "September";
        break;
      case 10:
        r = "October";
        break;
      case 11:
        r = "November";
        break;
      case 12:
        r = "December";
        break;
      default:
        break;
    }

    return r;
  }


  /**
   * Sets the description attribute of the Revenue object
   *
   * @param tmp The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   * Sets the enteredBy attribute of the Revenue object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   * Sets the modifiedBy attribute of the Revenue object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   * Sets the enteredBy attribute of the Revenue object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the modifiedBy attribute of the Revenue object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the modified attribute of the Revenue object
   *
   * @param tmp The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   * Sets the entered attribute of the Revenue object
   *
   * @param tmp The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   * Sets the enteredByName attribute of the Revenue object
   *
   * @param tmp The new enteredByName value
   */
  public void setEnteredByName(String tmp) {
    this.enteredByName = tmp;
  }


  /**
   * Sets the modifiedByName attribute of the Revenue object
   *
   * @param tmp The new modifiedByName value
   */
  public void setModifiedByName(String tmp) {
    this.modifiedByName = tmp;
  }


  /**
   * Sets the typeName attribute of the Revenue object
   *
   * @param tmp The new typeName value
   */
  public void setTypeName(String tmp) {
    this.typeName = tmp;
  }


  /**
   * Sets the type attribute of the Revenue object
   *
   * @param tmp The new type value
   */
  public void setType(int tmp) {
    this.type = tmp;
  }


  /**
   * Sets the owner attribute of the Revenue object
   *
   * @param tmp The new owner value
   */
  public void setOwner(int tmp) {
    this.owner = tmp;
  }


  /**
   * Sets the type attribute of the Revenue object
   *
   * @param tmp The new type value
   */
  public void setType(String tmp) {
    this.type = Integer.parseInt(tmp);
  }


  /**
   * Sets the owner attribute of the Revenue object
   *
   * @param tmp The new owner value
   */
  public void setOwner(String tmp) {
    this.owner = Integer.parseInt(tmp);
  }


  /**
   * Gets the type attribute of the Revenue object
   *
   * @return The type value
   */
  public int getType() {
    return type;
  }


  /**
   * Gets the owner attribute of the Revenue object
   *
   * @return The owner value
   */
  public int getOwner() {
    return owner;
  }


  /**
   * Sets the ownerNameFirst attribute of the Revenue object
   *
   * @param tmp The new ownerNameFirst value
   */
  public void setOwnerNameFirst(String tmp) {
    this.ownerNameFirst = tmp;
  }


  /**
   * Sets the ownerNameLast attribute of the Revenue object
   *
   * @param tmp The new ownerNameLast value
   */
  public void setOwnerNameLast(String tmp) {
    this.ownerNameLast = tmp;
  }


  /**
   * Gets the ownerNameFirst attribute of the Revenue object
   *
   * @return The ownerNameFirst value
   */
  public String getOwnerNameFirst() {
    return ownerNameFirst;
  }


  /**
   * Gets the ownerNameLast attribute of the Revenue object
   *
   * @return The ownerNameLast value
   */
  public String getOwnerNameLast() {
    return ownerNameLast;
  }


  /**
   * Sets the entered attribute of the Ticket object
   *
   * @param tmp The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DateUtils.parseTimestampString(tmp);
  }


  /**
   * Sets the modified attribute of the Ticket object
   *
   * @param tmp The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DateUtils.parseTimestampString(tmp);
  }


  /**
   * Gets the ownerName attribute of the Revenue object
   *
   * @return The ownerName value
   */
  public String getOwnerName() {
    return (Contact.getNameLastFirst(
        this.getOwnerNameLast(), this.getOwnerNameFirst()));
  }


  /**
   * Gets the ownerNameAbbr attribute of the Revenue object
   *
   * @return The ownerNameAbbr value
   */
  public String getOwnerNameAbbr() {
    return (this.getOwnerNameFirst().charAt(0) + ". " + this.getOwnerNameLast());
  }


  /**
   * Description of the Method
   *
   * @param db      Description of the Parameter
   * @param context Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean insert(Connection db, ActionContext context) throws SQLException {
    if (insert(db)) {
      invalidateUserData(context);
      return true;
    } else {
      return false;
    }
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void checkEnabledOwnerAccount(Connection db) throws SQLException {
    if (this.getOwner() == -1) {
      throw new SQLException("ID not specified for lookup.");
    }

    PreparedStatement pst = db.prepareStatement(
        "SELECT * " +
            "FROM " + DatabaseUtils.addQuotes(db, "access") + " " +
            "WHERE user_id = ? AND enabled = ? ");
    pst.setInt(1, this.getOwner());
    pst.setBoolean(2, true);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      this.setHasEnabledOwnerAccount(true);
    } else {
      this.setHasEnabledOwnerAccount(false);
    }
    rs.close();
    pst.close();
  }


  /**
   * Description of the Method
   *
   * @param db      Description of the Parameter
   * @param context Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public int update(Connection db, ActionContext context) throws SQLException {
    int oldId = -1;
    PreparedStatement pst = db.prepareStatement(
        "SELECT owner " +
            "FROM revenue " +
            "WHERE id = ? ");
    pst.setInt(1, this.getId());
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      oldId = rs.getInt("owner");
    }
    rs.close();
    pst.close();
    int result = update(db);
    if (result == 1) {
      invalidateUserData(context);
      if (oldId != this.getOwner()) {
        invalidateUserData(context, oldId);
      }
    }
    return result;
  }


  /**
   * Description of the Method
   *
   * @param db      Description of the Parameter
   * @param context Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean delete(Connection db, ActionContext context) throws SQLException {
    if (delete(db)) {
      invalidateUserData(context);
      return true;
    } else {
      return false;
    }
  }


  /**
   * Description of the Method
   *
   * @param db       Description of the Parameter
   * @param newOwner Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean reassign(Connection db, int newOwner) throws SQLException {
    int result = -1;
    this.setOwner(newOwner);
    result = this.update(db);

    if (result == -1) {
      return false;
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
  public boolean insert(Connection db) throws SQLException {
    StringBuffer sql = new StringBuffer();
    id = DatabaseUtils.getNextSeq(db, "revenue_id_seq");
    sql.append(
        "INSERT INTO revenue " +
            "(org_id, transaction_id, " + DatabaseUtils.addQuotes(db, "month") + ", " + DatabaseUtils.addQuotes(db, "year") + ", amount, " + DatabaseUtils.addQuotes(db, "type") + ", owner, description, ");
    if (id > -1) {
      sql.append("id, ");
    }
    sql.append("entered, modified, ");
    sql.append("enteredBy, modifiedBy ) ");
    sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ");
    if (id > -1) {
      sql.append("?, ");
    }
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
    try {
      db.setAutoCommit(false);
      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      pst.setInt(++i, orgId);
      pst.setInt(++i, transactionId);
      pst.setInt(++i, month);
      pst.setInt(++i, year);
      pst.setDouble(++i, amount);
      if (type > 0) {
        pst.setInt(++i, this.getType());
      } else {
        pst.setNull(++i, java.sql.Types.INTEGER);
      }
      if (owner > 0) {
        pst.setInt(++i, this.getOwner());
      } else {
        pst.setNull(++i, java.sql.Types.INTEGER);
      }
      pst.setString(++i, description);
      if (id > -1) {
        pst.setInt(++i, id);
      }
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
      id = DatabaseUtils.getCurrVal(db, "revenue_id_seq", id);
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
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean delete(Connection db) throws SQLException {
    PreparedStatement pst = null;
    pst = db.prepareStatement("DELETE FROM revenue WHERE id = ? ");
    pst.setInt(1, this.getId());
    pst.execute();
    pst.close();
    return true;
  }


  /**
   * Description of the Method
   *
   * @param db       Description of the Parameter
   * @param override Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  protected int update(Connection db, boolean override) throws SQLException {
    int resultCount = 0;
    if (this.getId() == -1) {
      throw new SQLException("Revenue ID was not specified");
    }

    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();

    sql.append(
        "UPDATE revenue " +
            "SET " + DatabaseUtils.addQuotes(db, "month") + " = ?, " + DatabaseUtils.addQuotes(db, "year") + " = ?, amount = ?, owner = ?, description = ?, " +
            "" + DatabaseUtils.addQuotes(db, "type") + " = ?, " +
            "modified = CURRENT_TIMESTAMP, modifiedby = ? " +
            "WHERE id = ? ");
    //if (!override) {
    //  sql.append("AND modified = ? ");
    //}

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setInt(++i, this.getMonth());
    pst.setInt(++i, this.getYear());
    pst.setDouble(++i, this.getAmount());
    pst.setInt(++i, this.getOwner());
    pst.setString(++i, this.getDescription());
    if (type > 0) {
      pst.setInt(++i, this.getType());
    } else {
      pst.setNull(++i, java.sql.Types.INTEGER);
    }
    pst.setInt(++i, this.getModifiedBy());
    pst.setInt(++i, this.getId());

    //if (!override) {
    //  pst.setTimestamp(++i, modified);
    //}

    resultCount = pst.executeUpdate();
    pst.close();

    return resultCount;
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

    try {
      db.setAutoCommit(false);
      resultCount = this.update(db, false);
      db.commit();
    } catch (Exception e) {
      db.rollback();
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
    return resultCount;
  }


  /**
   * Description of the Method
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("id");
    orgId = rs.getInt("org_id");
    transactionId = rs.getInt("transaction_id");
    month = rs.getInt("month");
    year = rs.getInt("year");
    amount = rs.getDouble("amount");
    type = rs.getInt("type");
    if (rs.wasNull()) {
      type = -1;
    }
    owner = rs.getInt("owner");
    if (rs.wasNull()) {
      owner = -1;
    }
    description = rs.getString("description");

    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");

    enteredByName = Contact.getNameLastFirst(
        rs.getString("eb_namelast"), rs.getString("eb_namefirst"));
    modifiedByName = Contact.getNameLastFirst(
        rs.getString("mb_namelast"), rs.getString("mb_namefirst"));

    ownerNameFirst = rs.getString("own_namefirst");
    ownerNameLast = rs.getString("own_namelast");

    typeName = rs.getString("typename");
    orgName = rs.getString("orgname");
  }


  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   */
  public void invalidateUserData(ActionContext context) {
    invalidateUserData(context, owner);
  }


  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @param userId  Description of Parameter
   */
  public void invalidateUserData(ActionContext context, int userId) {
    ConnectionElement ce = (ConnectionElement) context.getSession().getAttribute(
        "ConnectionElement");
    SystemStatus systemStatus = (SystemStatus) ((Hashtable) context.getServletContext().getAttribute(
        "SystemStatus")).get(ce.getUrl());
    systemStatus.getHierarchyList().getUser(userId).setRevenueIsValid(
        false, true);
  }

}

