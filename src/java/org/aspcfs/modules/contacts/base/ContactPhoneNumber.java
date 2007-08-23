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

import org.aspcfs.modules.base.PhoneNumber;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Represents a contact phone number, extending the base PhoneNumber class.
 *
 * @author mrajkowski
 * @version $Id: ContactPhoneNumber.java,v 1.2 2001/09/04 19:58:02 mrajkowski
 *          Exp $
 * @created September 4, 2001
 */
public class ContactPhoneNumber extends PhoneNumber {

  /**
   * Constructor for the ContactPhoneNumber object
   */
  public ContactPhoneNumber() {
    isContact = true;
  }


  /**
   * Constructor for the ContactPhoneNumber object
   *
   * @param rs Description of Parameter
   * @throws SQLException Description of Exception
   */
  public ContactPhoneNumber(ResultSet rs) throws SQLException {
    isContact = true;
    buildRecord(rs);
  }


  /**
   * Constructor for the ContactPhoneNumber object
   *
   * @param db            Description of the Parameter
   * @param phoneNumberId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public ContactPhoneNumber(Connection db, int phoneNumberId) throws SQLException {
    queryRecord(db, phoneNumberId);
  }


  /**
   * Constructor for the ContactPhoneNumber object
   *
   * @param db            Description of Parameter
   * @param phoneNumberId Description of Parameter
   * @throws SQLException Description of Exception
   */
  public ContactPhoneNumber(Connection db, String phoneNumberId) throws SQLException {
    queryRecord(db, Integer.parseInt(phoneNumberId));
  }


  /**
   * Description of the Method
   *
   * @param db            Description of the Parameter
   * @param phoneNumberId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void queryRecord(Connection db, int phoneNumberId) throws SQLException {
    isContact = true;
    if (phoneNumberId <= 0) {
      throw new SQLException("Invalid Phone Number ID specified.");
    }
    PreparedStatement pst = db.prepareStatement(
        "SELECT * " +
        "FROM contact_phone p, lookup_contactphone_types l " +
        "WHERE p.phone_type = l.code " +
        "AND phone_id = ? ");
    pst.setInt(1, phoneNumberId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (this.getId() == -1) {
      throw new SQLException("Phone record not found.");
    }
  }


  /**
   * Determines what to do if this record is marked for INSERT, UPDATE, or
   * DELETE
   *
   * @param db         Description of Parameter
   * @param contactId  Description of Parameter
   * @param enteredBy  Description of Parameter
   * @param modifiedBy Description of Parameter
   * @throws SQLException Description of Exception
   */
  public void process(Connection db, int contactId, int enteredBy, int modifiedBy) throws SQLException {
    if (this.getEnabled() == true) {
      if (this.getId() == -1) {
        this.setContactId(contactId);
        this.setEnteredBy(enteredBy);
        this.setModifiedBy(modifiedBy);
        this.insert(db);
      } else {
        this.setModifiedBy(modifiedBy);
        this.update(db, modifiedBy);
      }
    } else {
      this.delete(db);
    }
  }


  /**
   * Inserts this phone number into the contact database
   *
   * @param db Description of Parameter
   * @throws SQLException Description of Exception
   */

  public void insert(Connection db) throws SQLException {
    insert(db, this.getContactId(), this.getEnteredBy());
  }


  /**
   * Description of the Method
   *
   * @param db        Description of the Parameter
   * @param contactId Description of the Parameter
   * @param enteredBy Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void insert(Connection db, int contactId, int enteredBy) throws SQLException {
    StringBuffer sql = new StringBuffer();
    this.setId(DatabaseUtils.getNextSeq(db, "contact_phone_phone_id_seq"));
    int id = getId();
    sql.append(
        "INSERT INTO contact_phone " +
        "(contact_id, phone_type, " + DatabaseUtils.addQuotes(db, "number")+ ", extension, primary_number, ");
    if (id > -1) {
      sql.append("phone_id, ");
    }
    sql.append("entered, modified, ");
    sql.append("enteredBy, modifiedBy ) ");
    sql.append("VALUES (?, ?, ?, ?, ?, ");
    if (id > -1) {
      sql.append("?, ");
    }
    if (this.getEntered() != null) {
      sql.append("?, ");
    } else {
      sql.append(DatabaseUtils.getCurrentTimestamp(db) + ", ");
    }
    if (this.getModified() != null) {
      sql.append("?, ");
    } else {
      sql.append(DatabaseUtils.getCurrentTimestamp(db) + ", ");
    }
    sql.append("?, ?) ");
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql.toString());
    if (contactId > -1) {
      pst.setInt(++i, this.getContactId());
    } else {
      pst.setNull(++i, java.sql.Types.INTEGER);
    }
    if (this.getType() > -1) {
      pst.setInt(++i, this.getType());
    } else {
      pst.setNull(++i, java.sql.Types.INTEGER);
    }
    pst.setString(++i, this.getNumber());
    pst.setString(++i, this.getExtension());
    pst.setBoolean(++i, this.getPrimaryNumber());
    if (id > -1) {
      pst.setInt(++i, id);
    }
    if (this.getEntered() != null) {
      pst.setTimestamp(++i, this.getEntered());
    }
    if (this.getModified() != null) {
      pst.setTimestamp(++i, this.getModified());
    }
    pst.setInt(++i, this.getEnteredBy());
    pst.setInt(++i, this.getModifiedBy());
    pst.execute();
    pst.close();

    this.setId(DatabaseUtils.getCurrVal(db, "contact_phone_phone_id_seq", id));
  }


  /**
   * Description of the Method
   *
   * @param db         Description of Parameter
   * @param modifiedBy Description of Parameter
   * @throws SQLException Description of Exception
   */
  public void update(Connection db, int modifiedBy) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "UPDATE contact_phone " +
        "SET phone_type = ?, " + DatabaseUtils.addQuotes(db, "number")+ " = ?, extension = ?, primary_number = ?, modifiedby = ?, " +
        "modified = " + DatabaseUtils.getCurrentTimestamp(db) + " " +
        "WHERE phone_id = ? ");
    int i = 0;
    if (this.getType() > -1) {
      pst.setInt(++i, this.getType());
    } else {
      pst.setNull(++i, java.sql.Types.INTEGER);
    }
    pst.setString(++i, this.getNumber());
    pst.setString(++i, this.getExtension());
    pst.setBoolean(++i, this.getPrimaryNumber());
    pst.setInt(++i, modifiedBy);
    pst.setInt(++i, this.getId());
    pst.execute();
    pst.close();
  }


  /**
   * Description of the Method
   *
   * @param db Description of Parameter
   * @throws SQLException Description of Exception
   */
  public void delete(Connection db) throws SQLException {
    String where = "";
    if(this.getContactId() != -1){
      where += " contact_id = ?";
    }
    if(this.getId() != -1){
      where += " phone_id = ?";
    }

    PreparedStatement pst = db.prepareStatement(
        "DELETE FROM contact_phone " +
        "WHERE " + (where.length() != 0?where:"1 = 0"));
    int i = 0;
    if(this.getContactId() != -1){
      pst.setInt(++i, this.getContactId());
    }
    if(this.getId() != -1){
      pst.setInt(++i, this.getId());
    }
    pst.execute();
    pst.close();
  }
}

