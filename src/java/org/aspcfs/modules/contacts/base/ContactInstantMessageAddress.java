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
package org.aspcfs.modules.contacts.base;

import org.aspcfs.modules.base.InstantMessageAddress;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Description of the Class
 *
 * @author Ananth
 * @created April 21, 2005
 */
public class ContactInstantMessageAddress extends InstantMessageAddress {
  /**
   * Constructor for the ContactInstantMessageAddress object
   */
  public ContactInstantMessageAddress() {
    isContact = true;
  }


  /**
   * Constructor for the ContactInstantMessageAddress object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public ContactInstantMessageAddress(ResultSet rs) throws SQLException {
    isContact = true;
    buildRecord(rs);
  }


  /**
   * Constructor for the ContactInstantMessageAddress object
   *
   * @param db        Description of the Parameter
   * @param addressId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public ContactInstantMessageAddress(Connection db, int addressId) throws SQLException {
    queryRecord(db, addressId);
  }


  /**
   * Constructor for the ContactInstantMessageAddress object
   *
   * @param db        Description of the Parameter
   * @param addressId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public ContactInstantMessageAddress(Connection db, String addressId) throws SQLException {
    queryRecord(db, Integer.parseInt(addressId));
  }


  /**
   * Description of the Method
   *
   * @param db        Description of the Parameter
   * @param addressId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void queryRecord(Connection db, int addressId) throws SQLException {
    isContact = true;
    if (addressId < 0) {
      throw new SQLException(
          "Valid Instant Message Address ID not specified.");
    }
    PreparedStatement pst = db.prepareStatement(
        "SELECT cim.*, lims.description AS service_description, limt.description AS type_description " +
        "FROM contact_imaddress cim " +
        "LEFT JOIN lookup_im_services lims ON (cim.imaddress_service = lims.code) " +
        "LEFT JOIN lookup_im_types limt ON (cim.imaddress_type = limt.code) " +
        "WHERE cim.address_id = ? ");
    pst.setInt(1, addressId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (this.getId() == -1) {
      throw new SQLException("Instant Message Address record not found.");
    }
  }


  /**
   * Description of the Method
   *
   * @param db         Description of the Parameter
   * @param contactId  Description of the Parameter
   * @param enteredBy  Description of the Parameter
   * @param modifiedBy Description of the Parameter
   * @throws SQLException Description of the Exception
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
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
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
    this.setId(
        DatabaseUtils.getNextSeq(db, "contact_imaddress_address_id_seq"));
    StringBuffer sql = new StringBuffer();
    sql.append(
        "INSERT INTO contact_imaddress " +
        "(contact_id, imaddress_type, imaddress_service, imaddress, primary_im, ");
    if (this.getId() > -1) {
      sql.append("address_id, ");
    }
    sql.append("entered, modified, ");
    sql.append("enteredby, modifiedby ) ");
    sql.append("VALUES (?, ?, ?, ?, ?, ");
    if (this.getId() > -1) {
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

    if (this.getAddressIMType() > -1) {
      pst.setInt(++i, this.getAddressIMType());
    } else {
      pst.setNull(++i, java.sql.Types.INTEGER);
    }

    if (this.getAddressIMService() > -1) {
      pst.setInt(++i, this.getAddressIMService());
    } else {
      pst.setNull(++i, java.sql.Types.INTEGER);
    }

    pst.setString(++i, this.getAddressIM());
    pst.setBoolean(++i, this.getPrimaryIM());
    if (this.getId() > -1) {
      pst.setInt(++i, getId());
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

    this.setId(
        DatabaseUtils.getCurrVal(
            db, "contact_imaddress_address_id_seq", this.getId()));
  }


  /**
   * Description of the Method
   *
   * @param db         Description of the Parameter
   * @param modifiedBy Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void update(Connection db, int modifiedBy) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "UPDATE contact_imaddress " +
        "SET imaddress_type = ?, imaddress_service = ?, imaddress = ?, primary_im = ?, modifiedby = ?, " +
        "modified = " + DatabaseUtils.getCurrentTimestamp(db) + " " +
        "WHERE address_id = ? ");
    int i = 0;
    if (this.getAddressIMType() > -1) {
      pst.setInt(++i, this.getAddressIMType());
    } else {
      pst.setNull(++i, java.sql.Types.INTEGER);
    }

    if (this.getAddressIMService() > -1) {
      pst.setInt(++i, this.getAddressIMService());
    } else {
      pst.setNull(++i, java.sql.Types.INTEGER);
    }

    pst.setString(++i, this.getAddressIM());
    pst.setBoolean(++i, this.getPrimaryIM());
    pst.setInt(++i, modifiedBy);
    pst.setInt(++i, this.getId());
    pst.execute();
    pst.close();
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void delete(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "DELETE FROM contact_imaddress " +
        "WHERE address_id = ? ");
    int i = 0;
    pst.setInt(++i, this.getId());
    pst.execute();
    pst.close();
  }
}

