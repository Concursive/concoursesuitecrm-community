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

import java.sql.*;
import java.text.DateFormat;

/**
 * Description of the Class
 *
 * @author Mathur
 * @version $Id$
 * @created January 13, 2003
 */
public class RevenueDetail
    extends GenericBean {

  private int id = -1;
  private int revenueId = -1;
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
  private String typeName = "";

  /**
   * Constructor for the RevenueDetail object
   */
  public RevenueDetail() {
  }

  /**
   * Constructor for the RevenueDetail object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public RevenueDetail(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }

  /**
   * Constructor for the RevenueDetail object
   *
   * @param db              Description of the Parameter
   * @param revenueDetailId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public RevenueDetail(Connection db, String revenueDetailId) throws
      SQLException {
    Statement st = null;
    ResultSet rs = null;

    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT rd.*, " +
        "ct_eb.namelast as eb_namelast, ct_eb.namefirst as eb_namefirst, " +
        "ct_mb.namelast as mb_namelast, ct_mb.namefirst as mb_namefirst, rdt.description as typename " +
        "FROM revenue_detail rd " +
        "LEFT JOIN contact ct_eb ON (rd.enteredby = ct_eb.user_id) " +
        "LEFT JOIN contact ct_mb ON (rd.modifiedby = ct_mb.user_id) " +
        "LEFT JOIN lookup_revenuedetail_types rdt ON (rd.\"type\" = rdt.code) " +
        "WHERE rd.id > -1 ");

    if (revenueDetailId != null && !revenueDetailId.equals("")) {
      sql.append("AND rd.id = " + revenueDetailId + " ");
    } else {
      throw new SQLException("Revenue Detail ID not specified.");
    }
    st = db.createStatement();
    rs = st.executeQuery(sql.toString());
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    st.close();
    if (id == -1) {
      throw new SQLException("Revenue Detail record not found.");
    }
  }

  /**
   * Gets the enteredString attribute of the RevenueDetail object
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
   * Gets the enteredDateTimeString attribute of the RevenueDetail object
   *
   * @return The enteredDateTimeString value
   */
  public String getEnteredDateTimeString() {
    try {
      return DateFormat.getDateTimeInstance(
          DateFormat.SHORT, DateFormat.SHORT).
          format(entered);
    } catch (NullPointerException e) {
    }
    return ("");
  }

  /**
   * Gets the modifiedString attribute of the RevenueDetail object
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
   * Gets the modifiedDateTimeString attribute of the RevenueDetail object
   *
   * @return The modifiedDateTimeString value
   */
  public String getModifiedDateTimeString() {
    try {
      return DateFormat.getDateTimeInstance(
          DateFormat.SHORT, DateFormat.SHORT).
          format(modified);
    } catch (NullPointerException e) {
    }
    return ("");
  }

  /**
   * Gets the id attribute of the RevenueDetail object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }

  /**
   * Gets the revenueId attribute of the RevenueDetail object
   *
   * @return The revenueId value
   */
  public int getRevenueId() {
    return revenueId;
  }

  /**
   * Gets the amount attribute of the RevenueDetail object
   *
   * @return The amount value
   */
  public double getAmount() {
    return amount;
  }

  /**
   * Gets the description attribute of the RevenueDetail object
   *
   * @return The description value
   */
  public String getDescription() {
    return description;
  }

  /**
   * Gets the enteredBy attribute of the RevenueDetail object
   *
   * @return The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }

  /**
   * Gets the modifiedBy attribute of the RevenueDetail object
   *
   * @return The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }

  /**
   * Gets the modified attribute of the RevenueDetail object
   *
   * @return The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }

  /**
   * Gets the entered attribute of the RevenueDetail object
   *
   * @return The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }

  /**
   * Gets the enteredByName attribute of the RevenueDetail object
   *
   * @return The enteredByName value
   */
  public String getEnteredByName() {
    return enteredByName;
  }

  /**
   * Gets the modifiedByName attribute of the RevenueDetail object
   *
   * @return The modifiedByName value
   */
  public String getModifiedByName() {
    return modifiedByName;
  }

  /**
   * Gets the typeName attribute of the RevenueDetail object
   *
   * @return The typeName value
   */
  public String getTypeName() {
    return typeName;
  }

  /**
   * Sets the id attribute of the RevenueDetail object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }

  /**
   * Sets the id attribute of the RevenueDetail object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }

  /**
   * Sets the revenueId attribute of the RevenueDetail object
   *
   * @param tmp The new revenueId value
   */
  public void setRevenueId(int tmp) {
    this.revenueId = tmp;
  }

  /**
   * Sets the revenueId attribute of the RevenueDetail object
   *
   * @param tmp The new revenueId value
   */
  public void setRevenueId(String tmp) {
    this.revenueId = Integer.parseInt(tmp);
  }

  /**
   * Sets the amount attribute of the RevenueDetail object
   *
   * @param tmp The new amount value
   */
  public void setAmount(double tmp) {
    this.amount = tmp;
  }

  /**
   * Sets the description attribute of the RevenueDetail object
   *
   * @param tmp The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }

  /**
   * Sets the enteredBy attribute of the RevenueDetail object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }

  /**
   * Sets the modifiedBy attribute of the RevenueDetail object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }

  /**
   * Sets the modified attribute of the RevenueDetail object
   *
   * @param tmp The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }

  /**
   * Sets the entered attribute of the RevenueDetail object
   *
   * @param tmp The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }

  /**
   * Sets the enteredByName attribute of the RevenueDetail object
   *
   * @param tmp The new enteredByName value
   */
  public void setEnteredByName(String tmp) {
    this.enteredByName = tmp;
  }

  /**
   * Sets the modifiedByName attribute of the RevenueDetail object
   *
   * @param tmp The new modifiedByName value
   */
  public void setModifiedByName(String tmp) {
    this.modifiedByName = tmp;
  }

  /**
   * Sets the typeName attribute of the RevenueDetail object
   *
   * @param tmp The new typeName value
   */
  public void setTypeName(String tmp) {
    this.typeName = tmp;
  }

  /**
   * Gets the type attribute of the RevenueDetail object
   *
   * @return The type value
   */
  public int getType() {
    return type;
  }

  /**
   * Gets the owner attribute of the RevenueDetail object
   *
   * @return The owner value
   */
  public int getOwner() {
    return owner;
  }

  /**
   * Sets the type attribute of the RevenueDetail object
   *
   * @param tmp The new type value
   */
  public void setType(int tmp) {
    this.type = tmp;
  }

  /**
   * Sets the owner attribute of the RevenueDetail object
   *
   * @param tmp The new owner value
   */
  public void setOwner(int tmp) {
    this.owner = tmp;
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
    id = DatabaseUtils.getNextSeq(db, "revenue_detail_id_seq");
    sql.append(
        "INSERT INTO revenue_detail " +
        "(" + (id > -1 ? "id, " : "") + "revenue_id, amount, \"type\", owner, description, enteredBy, modifiedBy) " +
        "VALUES (" + (id > -1 ? "?, " : "") + "?, ?, ?, ?, ?, ?, ?) ");
    try {
      db.setAutoCommit(false);
      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      if (id > -1) {
        pst.setInt(++i, id);
      }
      pst.setInt(++i, revenueId);
      pst.setDouble(++i, amount);
      pst.setInt(++i, type);
      pst.setInt(++i, owner);
      pst.setString(++i, description);
      pst.setInt(++i, enteredBy);
      pst.setInt(++i, modifiedBy);
      pst.execute();
      pst.close();
      id = DatabaseUtils.getCurrVal(db, "revenue_detail_id_seq", id);
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
    Statement st = db.createStatement();

    try {
      db.setAutoCommit(false);
      st.executeUpdate(
          "DELETE FROM revenue_detail WHERE id = " + this.getId());
      st.close();
      db.commit();
    } catch (SQLException e) {
      db.rollback();
      System.out.println(e.toString());
    } finally {
      db.setAutoCommit(true);
    }
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
      throw new SQLException("Revenue Detail ID was not specified");
    }

    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();

    sql.append(
        "UPDATE revenue " +
        "SET amount = ?, owner = ?, description = ?, " +
        "\"type\" = ?, " +
        "modified = CURRENT_TIMESTAMP, modifiedby = ? " +
        "WHERE id = ? ");
    //if (!override) {
    //  sql.append("AND modified = ? ");
    //}

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setDouble(++i, this.getAmount());
    pst.setInt(++i, this.getOwner());
    pst.setString(++i, this.getDescription());
    pst.setInt(++i, this.getType());
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
    // revenue
    id = rs.getInt("id");
    revenueId = rs.getInt("revenue_id");
    amount = rs.getDouble("amount");
    type = rs.getInt("type");
    owner = rs.getInt("owner");
    description = rs.getString("description");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
    // contact
    enteredByName = Contact.getNameLastFirst(
        rs.getString("eb_namelast"),
        rs.getString("eb_namefirst"));
    modifiedByName = Contact.getNameLastFirst(
        rs.getString("mb_namelast"),
        rs.getString("mb_namefirst"));
    typeName = rs.getString("typename");
  }

}
