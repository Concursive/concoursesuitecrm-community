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
  
  
  public ContactPhoneNumber(Connection db, int phoneNumberId) throws SQLException {
          queryRecord(db, phoneNumberId);
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
          queryRecord(db, Integer.parseInt(phoneNumberId));
  }
          
  public void queryRecord(Connection db, int phoneNumberId) throws SQLException {
    isContact = true;
    if (phoneNumberId <= 0) {
      throw new SQLException("Invalid Phone Number ID specified.");
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
   
   public void insert(Connection db) throws SQLException {
           insert(db, this.getContactId(), this.getEnteredBy());
   }
   
  public void insert(Connection db, int contactId, int enteredBy) throws SQLException {
    StringBuffer sql = new StringBuffer();
    sql.append("INSERT INTO contact_phone " +
        "(contact_id, phone_type, number, extension, ");
                if (this.getEntered() != null) {
                        sql.append("entered, ");
                }
                if (this.getModified() != null) {
                        sql.append("modified, ");
                }
    sql.append("enteredBy, modifiedBy ) ");
    sql.append("VALUES (?, ?, ?, ?, ");
                if (this.getEntered() != null) {
                        sql.append("?, ");
                }
                if (this.getModified() != null) {
                        sql.append("?, ");
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

    this.setId(DatabaseUtils.getCurrVal(db, "contact_phone_phone_id_seq"));
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
    if (this.getType() > -1) {
      pst.setInt(++i, this.getType());
    } else {
      pst.setNull(++i, java.sql.Types.INTEGER);
    }
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

