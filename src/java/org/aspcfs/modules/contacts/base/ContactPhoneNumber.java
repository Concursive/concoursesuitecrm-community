//Copyright 2001 Dark Horse Ventures
package com.darkhorseventures.cfsbase;

import java.sql.*;
import com.darkhorseventures.utils.DatabaseUtils;

/**
 *  Represents a contact phone number, extending the base PhoneNumber class.
 *
 *@author     mrajkowski
 *@created    September 4, 2001
 *@version    $Id: ContactPhoneNumber.java,v 1.2 2001/09/04 19:58:02 mrajkowski
 *      Exp $
 */
public class ContactPhoneNumber extends PhoneNumber {

  /**
   *  Constructor for the ContactPhoneNumber object
   *
   *@since
   */
  public ContactPhoneNumber() {
    isContact = true;
  }


  /**
   *  Constructor for the ContactPhoneNumber object
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since
   */
  public ContactPhoneNumber(ResultSet rs) throws SQLException {
    isContact = true;
    buildRecord(rs);
  }


  /**
   *  Constructor for the ContactPhoneNumber object
   *
   *@param  db                Description of Parameter
   *@param  phoneNumberId     Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since
   */
  public ContactPhoneNumber(Connection db, String phoneNumberId) throws SQLException {
    isContact = true;
    if (phoneNumberId == null) {
      throw new SQLException("Phone Number ID not specified.");
    }

    Statement st = null;
    ResultSet rs = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
      "SELECT * " +
      "FROM contact_phone p, lookup_contactphone_types l " +
      "WHERE p.phone_type = l.code " +
      "AND phone_id = " + phoneNumberId + " ");
    st = db.createStatement();
    rs = st.executeQuery(sql.toString());
    if (rs.next()) {
      buildRecord(rs);
    } else {
      rs.close();
      st.close();
      throw new SQLException("Phone record not found.");
    }
    rs.close();
    st.close();
  }


  /**
   *  Determines what to do if this record is marked for INSERT, UPDATE, or
   *  DELETE
   *
   *@param  db                Description of Parameter
   *@param  contactId         Description of Parameter
   *@param  enteredBy         Description of Parameter
   *@param  modifiedBy        Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since
   */
  public void process(Connection db, int contactId, int enteredBy, int modifiedBy) throws SQLException {
    if (this.getEnabled() == true) {
      if (this.getId() == -1) {
      this.insert(db, contactId, enteredBy);
      } else {
        this.update(db, modifiedBy);
      }
    } else {
      this.delete(db);
    }
  }


  /**
   *  Inserts this phone number into the contact database
   *
   *@param  db                Description of Parameter
   *@param  contactId         Description of Parameter
   *@param  enteredBy         Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since
   */
  public void insert(Connection db, int contactId, int enteredBy) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO contact_phone " +
        "(contact_id, phone_type, number, extension, enteredby, modifiedby) " +
        "VALUES " +
        "(?, ?, ?, ?, ?, ?) ");
    int i = 0;
    pst.setInt(++i, contactId);
    pst.setInt(++i, this.getType());
    pst.setString(++i, this.getNumber());
    pst.setString(++i, this.getExtension());
    pst.setInt(++i, enteredBy);
    pst.setInt(++i, enteredBy);
    pst.execute();
    pst.close();

    Statement st = db.createStatement();
    ResultSet rs = null;
      switch (DatabaseUtils.getType(db)) {
        case DatabaseUtils.POSTGRESQL:
          rs = st.executeQuery("select currval('contact_phone_phone_id_seq')");
          break;
        case DatabaseUtils.MSSQL:
          rs = st.executeQuery("SELECT @@IDENTITY");
          break;
        default:
          break;
      }
    if (rs.next()) {
      this.setId(rs.getInt(1));
    }
    rs.close();
    st.close();
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@param  modifiedBy        Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since
   */
  public void update(Connection db, int modifiedBy) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "UPDATE contact_phone " +
        "SET phone_type = ?, number = ?, extension = ?, modifiedby = ?, " +
        "modified = CURRENT_TIMESTAMP " +
        "WHERE phone_id = ? ");
    int i = 0;
    pst.setInt(++i, this.getType());
    pst.setString(++i, this.getNumber());
    pst.setString(++i, this.getExtension());
    pst.setInt(++i, modifiedBy);
    pst.setInt(++i, this.getId());
    pst.execute();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since
   */
  public void delete(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "DELETE FROM contact_phone " +
        "WHERE phone_id = ? ");
    int i = 0;
    pst.setInt(++i, this.getId());
    pst.execute();
    pst.close();
  }

}

