/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.netapps.base;

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
 * @author kbhoopal
 * @version $Id: ContractExpirationLog.java,v 1.1.2.2 2004/09/29 21:01:11
 *          kbhoopal Exp $
 * @created September 27, 2004
 */
public class ContractExpirationLog extends GenericBean {

  private int id = -1;
  private int expirationId = -1;
  private String entryText = "";

  //Fields that need to be logged
  private double quoteAmount = -1.0;
  private java.sql.Timestamp quoteGeneratedDate = null;
  private java.sql.Timestamp quoteAcceptedDate = null;
  private java.sql.Timestamp quoteRejectedDate = null;
  private String comment = null;

  //General maintenance fields
  private java.sql.Timestamp entered = null;
  private int enteredBy = -1;
  private java.sql.Timestamp modified = null;
  private int modifiedBy = -1;
  private boolean override = false;
  private boolean systemMessage = false;

  private String enteredByName = null;


  /**
   * Constructor for the ContractExpirationLog object
   */
  public ContractExpirationLog() {
  }


  /**
   * Constructor for the ContractExpirationLog object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public ContractExpirationLog(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Sets the id attribute of the ContractExpirationLog object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the ContractExpirationLog object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Sets the expirationId attribute of the ContractExpirationLog object
   *
   * @param tmp The new expirationId value
   */
  public void setExpirationId(int tmp) {
    this.expirationId = tmp;
  }


  /**
   * Sets the expirationId attribute of the ContractExpirationLog object
   *
   * @param tmp The new expirationId value
   */
  public void setExpirationId(String tmp) {
    this.expirationId = Integer.parseInt(tmp);
  }


  /**
   * Sets the entryText attribute of the ContractExpirationLog object
   *
   * @param tmp The new entryText value
   */
  public void setEntryText(String tmp) {
    this.entryText = tmp;
  }


  /**
   * Sets the quoteAmount attribute of the ContractExpirationLog object
   *
   * @param tmp The new quoteAmount value
   */
  public void setQuoteAmount(double tmp) {
    this.quoteAmount = tmp;
  }


  /**
   * Sets the quoteGeneratedDate attribute of the ContractExpirationLog object
   *
   * @param tmp The new quoteGeneratedDate value
   */
  public void setQuoteGeneratedDate(java.sql.Timestamp tmp) {
    this.quoteGeneratedDate = tmp;
  }


  /**
   * Sets the quoteGeneratedDate attribute of the ContractExpirationLog object
   *
   * @param tmp The new quoteGeneratedDate value
   */
  public void setQuoteGeneratedDate(String tmp) {
    this.quoteGeneratedDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the quoteAcceptedDate attribute of the ContractExpirationLog object
   *
   * @param tmp The new quoteAcceptedDate value
   */
  public void setQuoteAcceptedDate(java.sql.Timestamp tmp) {
    this.quoteAcceptedDate = tmp;
  }


  /**
   * Sets the quoteAcceptedDate attribute of the ContractExpirationLog object
   *
   * @param tmp The new quoteAcceptedDate value
   */
  public void setQuoteAcceptedDate(String tmp) {
    this.quoteAcceptedDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the quoteRejectedDate attribute of the ContractExpirationLog object
   *
   * @param tmp The new quoteRejectedDate value
   */
  public void setQuoteRejectedDate(java.sql.Timestamp tmp) {
    this.quoteRejectedDate = tmp;
  }


  /**
   * Sets the quoteRejectedDate attribute of the ContractExpirationLog object
   *
   * @param tmp The new quoteRejectedDate value
   */
  public void setQuoteRejectedDate(String tmp) {
    this.quoteRejectedDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the comment attribute of the ContractExpirationLog object
   *
   * @param tmp The new comment value
   */
  public void setComment(String tmp) {
    this.comment = tmp;
  }


  /**
   * Sets the entered attribute of the ContractExpirationLog object
   *
   * @param tmp The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   * Sets the entered attribute of the ContractExpirationLog object
   *
   * @param tmp The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the enteredBy attribute of the ContractExpirationLog object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   * Sets the enteredBy attribute of the ContractExpirationLog object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the modified attribute of the ContractExpirationLog object
   *
   * @param tmp The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   * Sets the modified attribute of the ContractExpirationLog object
   *
   * @param tmp The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the modifiedBy attribute of the ContractExpirationLog object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   * Sets the modifiedBy attribute of the ContractExpirationLog object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the override attribute of the ContractExpirationLog object
   *
   * @param tmp The new override value
   */
  public void setOverride(boolean tmp) {
    this.override = tmp;
  }


  /**
   * Sets the override attribute of the ContractExpirationLog object
   *
   * @param tmp The new override value
   */
  public void setOverride(String tmp) {
    this.override = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the systemMessage attribute of the ContractExpirationLog object
   *
   * @param tmp The new systemMessage value
   */
  public void setSystemMessage(boolean tmp) {
    this.systemMessage = tmp;
  }


  /**
   * Sets the systemMessage attribute of the ContractExpirationLog object
   *
   * @param tmp The new systemMessage value
   */
  public void setSystemMessage(String tmp) {
    this.systemMessage = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the enteredByName attribute of the ContractExpirationLog object
   *
   * @param tmp The new enteredByName value
   */
  public void setEnteredByName(String tmp) {
    this.enteredByName = tmp;
  }


  /**
   * Gets the id attribute of the ContractExpirationLog object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the expirationId attribute of the ContractExpirationLog object
   *
   * @return The expirationId value
   */
  public int getExpirationId() {
    return expirationId;
  }


  /**
   * Gets the entryText attribute of the ContractExpirationLog object
   *
   * @return The entryText value
   */
  public String getEntryText() {
    return entryText;
  }


  /**
   * Gets the quoteAmount attribute of the ContractExpirationLog object
   *
   * @return The quoteAmount value
   */
  public double getQuoteAmount() {
    return quoteAmount;
  }


  /**
   * Gets the quoteGeneratedDate attribute of the ContractExpirationLog object
   *
   * @return The quoteGeneratedDate value
   */
  public java.sql.Timestamp getQuoteGeneratedDate() {
    return quoteGeneratedDate;
  }


  /**
   * Gets the quoteAcceptedDate attribute of the ContractExpirationLog object
   *
   * @return The quoteAcceptedDate value
   */
  public java.sql.Timestamp getQuoteAcceptedDate() {
    return quoteAcceptedDate;
  }


  /**
   * Gets the quoteRejectedDate attribute of the ContractExpirationLog object
   *
   * @return The quoteRejectedDate value
   */
  public java.sql.Timestamp getQuoteRejectedDate() {
    return quoteRejectedDate;
  }


  /**
   * Gets the comment attribute of the ContractExpirationLog object
   *
   * @return The comment value
   */
  public String getComment() {
    return comment;
  }


  /**
   * Gets the entered attribute of the ContractExpirationLog object
   *
   * @return The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   * Gets the enteredBy attribute of the ContractExpirationLog object
   *
   * @return The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   * Gets the modified attribute of the ContractExpirationLog object
   *
   * @return The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   * Gets the modifiedBy attribute of the ContractExpirationLog object
   *
   * @return The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   * Gets the override attribute of the ContractExpirationLog object
   *
   * @return The override value
   */
  public boolean getOverride() {
    return override;
  }


  /**
   * Gets the systemMessage attribute of the ContractExpirationLog object
   *
   * @return The systemMessage value
   */
  public boolean getSystemMessage() {
    return systemMessage;
  }


  /**
   * Gets the enteredByName attribute of the ContractExpirationLog object
   *
   * @return The enteredByName value
   */
  public String getEnteredByName() {
    return enteredByName;
  }


  /**
   * Description of the Method
   *
   * @param db           Description of the Parameter
   * @param expirationId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void process(Connection db, int expirationId) throws SQLException {
    if (expirationId != -1) {
      this.insert(db);
    }
  }


  /**
   * Description of the Method
   *
   * @param db    Description of the Parameter
   * @param tmpId Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean queryRecord(Connection db, int tmpId) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    pst = db.prepareStatement(
        " SELECT ncl.*, " +
            "ct_eb.namelast AS eb_namelast, ct_eb.namefirst AS eb_namefirst " +
            "FROM netapp_contractexpiration_log ncl " +
            "LEFT JOIN contact ct_eb ON (ncl.enteredby = ct_eb.user_id) " +
            " WHERE ncl.id = ? ");
    pst.setInt(1, tmpId);
    rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();

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
    id = DatabaseUtils.getNextSeq(db, "netapp_contractexpiration_log_id_seq");
    PreparedStatement pst = null;
    pst = db.prepareStatement(
        "INSERT INTO netapp_contractexpiration_log " +
            "(" +
            (id > -1 ? "id, " : "") +
            "expiration_id, " +
            "quote_amount, " +
            "quotegenerateddate, " +
            "quoteaccepteddate, " +
            "quoterejecteddate, " +
            "comment, " +
            "enteredBy, " +
            "modifiedBy )" +
            "VALUES (" +
            (id > -1 ? "?, " : "") +
            "?,?,?,?,?,?,?,?)");
    int i = 0;
    if (id > -1) {
      pst.setInt(++i, id);
    }
    pst.setInt(++i, this.expirationId);
    DatabaseUtils.setDouble(pst, ++i, this.quoteAmount);
    DatabaseUtils.setTimestamp(pst, ++i, this.quoteGeneratedDate);
    DatabaseUtils.setTimestamp(pst, ++i, this.quoteAcceptedDate);
    DatabaseUtils.setTimestamp(pst, ++i, this.quoteRejectedDate);
    pst.setString(++i, this.comment);
    DatabaseUtils.setInt(pst, ++i, this.enteredBy);
    DatabaseUtils.setInt(pst, ++i, this.modifiedBy);
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "netapp_contractexpiration_log_id_seq", id);
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
    if (!isValid()) {
      return resultCount;
    }
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "UPDATE netapp_contractexpiration_log " +
            "SET  " +
            "expiration_id = ? , " +
            "quote_amount = ? , " +
            "quotegenerateddate = ? , " +
            "quoteaccepteddate = ? , " +
            "quoterejecteddate = ? , " +
            "comment = ? , " +
            "modified = " + DatabaseUtils.getCurrentTimestamp(db) + " , " +
            "modifiedBy = ?  " +
            "WHERE id = ? "
    );

    pst = db.prepareStatement(sql.toString());
    int i = 0;
    pst.setInt(++i, this.expirationId);
    DatabaseUtils.setDouble(pst, ++i, this.quoteAmount);
    DatabaseUtils.setTimestamp(pst, ++i, this.quoteGeneratedDate);
    DatabaseUtils.setTimestamp(pst, ++i, this.quoteAcceptedDate);
    DatabaseUtils.setTimestamp(pst, ++i, this.quoteRejectedDate);
    pst.setString(++i, this.comment);
    DatabaseUtils.setInt(pst, ++i, this.modifiedBy);
    DatabaseUtils.setInt(pst, ++i, this.id);

    resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }


  /**
   * Gets the valid attribute of the ContractExpirationLog object
   *
   * @return The valid value
   */
  public boolean isValid() {
    if (hasErrors()) {
      return false;
    } else {
      return true;
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
    PreparedStatement pst = null;
    pst = db.prepareStatement(
        " DELETE " +
            " FROM netapp_contractexpiration_log " +
            " WHERE id = ? ");
    pst.setInt(1, this.id);
    pst.execute();
    pst.close();

    return true;
  }


  /**
   * Description of the Method
   *
   * @param source Description of the Parameter
   */
  public void createSysMsg(ContractExpirationLog source) {
    this.setEnteredByName(source.getEnteredByName());
    this.setEnteredBy(source.getEnteredBy());
    this.setEntered(source.getEntered());
    this.setQuoteAmount(source.getQuoteAmount());
    this.setQuoteGeneratedDate(source.getQuoteGeneratedDate());
    this.setQuoteAcceptedDate(source.getQuoteAcceptedDate());
    this.setQuoteRejectedDate(source.getQuoteRejectedDate());
    this.setComment(source.getComment());
    this.setSystemMessage(true);
  }


  /**
   * Description of the Method
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildRecord(ResultSet rs) throws SQLException {
    id = DatabaseUtils.getInt(rs, "id");
    expirationId = DatabaseUtils.getInt(rs, "expiration_id");

    //Additional fields that are added/modified
    quoteAmount = DatabaseUtils.getDouble(rs, "quote_amount");
    quoteGeneratedDate = rs.getTimestamp("quotegenerateddate");
    quoteAcceptedDate = rs.getTimestamp("quoteaccepteddate");
    quoteRejectedDate = rs.getTimestamp("quoterejecteddate");

    //General maintenance fields
    comment = rs.getString("comment");
    entered = rs.getTimestamp("entered");
    enteredBy = DatabaseUtils.getInt(rs, "enteredBy");
    modified = rs.getTimestamp("modified");
    modifiedBy = DatabaseUtils.getInt(rs, "modifiedBy");

    //contact table
    enteredByName = Contact.getNameLastFirst(rs.getString("eb_namelast"), rs.getString("eb_namefirst"));
  }

}

