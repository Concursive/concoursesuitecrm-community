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

import java.sql.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.modules.base.TextMessageAddress;

/**
 *  Description of the Class
 *
 *@author     kailash
 *@created    January 11, 2005
 *@version    $Id$
 */
public class ContactTextMessageAddress extends TextMessageAddress {

  public ContactTextMessageAddress() {
    isContact = true;
  }


  /**
   *  Constructor for the ContactTextMessageAddress object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public ContactTextMessageAddress(ResultSet rs) throws SQLException {
    isContact = true;
    buildRecord(rs);
  }


  /**
   *  Constructor for the ContactTextMessageAddress object
   *
   *@param  db                Description of the Parameter
   *@param  addressId    Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public ContactTextMessageAddress(Connection db, int addressId) throws SQLException {
    queryRecord(db, addressId);
  }


  /**
   *  Constructor for the ContactTextMessageAddress object
   *
   *@param  db                Description of the Parameter
   *@param  addressId    Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public ContactTextMessageAddress(Connection db, String addressId) throws SQLException {
    queryRecord(db, Integer.parseInt(addressId));
  }



  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  addressId    Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void queryRecord(Connection db, int addressId) throws SQLException {
    isContact = true;
    if (addressId < 0) {
      throw new SQLException("Valid Text Message Address ID not specified.");
    }
    PreparedStatement pst = db.prepareStatement("SELECT * " +
        "FROM contact_textmessageaddress c, lookup_textmessage_types l " +
        "WHERE c.textmessageaddress_type = l.code " +
        "AND c.address_id = " + addressId + " ");
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (this.getId() == -1) {
      throw new SQLException("Email record not found.");
    }
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
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void insert(Connection db) throws SQLException {
    insert(db, this.getContactId(), this.getEnteredBy());
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  contactId         Description of the Parameter
   *@param  enteredBy         Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void insert(Connection db, int contactId, int enteredBy) throws SQLException {
    StringBuffer sql = new StringBuffer();

    sql.append("INSERT INTO contact_textmessageaddress " +
        "(contact_id, textmessageaddress_type, textmessageaddress, primary_textmessage_address, ");
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

    pst.setString(++i, this.getTextMessageAddress());
    pst.setBoolean(++i, this.getPrimaryTextMessageAddress());

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

    this.setId(DatabaseUtils.getCurrVal(db, "contact_textmessageaddress_address_id_seq"));
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
        "UPDATE contact_textmessageaddress " +
        "SET textmessageaddress_type = ?, textmessageaddress = ?, primary_textmessage_address = ?, modifiedby = ?, " +
        "modified = CURRENT_TIMESTAMP " +
        "WHERE address_id = ? ");
    int i = 0;
    if (this.getType() > -1) {
      pst.setInt(++i, this.getType());
    } else {
      pst.setNull(++i, java.sql.Types.INTEGER);
    }
    pst.setString(++i, this.getTextMessageAddress());
    pst.setBoolean(++i, this.getPrimaryTextMessageAddress());
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
        "DELETE FROM contact_textmessageaddress " +
        "WHERE address_id = ? ");
    int i = 0;
    pst.setInt(++i, this.getId());
    pst.execute();
    pst.close();
  }

}

