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
package org.aspcfs.modules.orders.base;

import org.aspcfs.modules.base.Address;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Build an address for an Order using a custom query that extends the fields
 * and methods of a typical Address
 *
 * @author ananth
 * @version $Id: OrderAddress.java,v 1.3.12.1 2004/11/12 19:55:25 mrajkowski
 *          Exp $
 * @created March 18, 2004
 */
public class OrderAddress extends Address {

  /**
   * Constructor for the OrderAddress object
   */
  public OrderAddress() {
    isOrder = true;
  }


  /**
   * Constructor for the OrderAddress object
   *
   * @param db Description of the Parameter
   * @param id Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public OrderAddress(Connection db, int id) throws SQLException {
    isOrder = true;
    queryRecord(db, id);
  }


  /**
   * Constructor for the OrderAddress object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public OrderAddress(ResultSet rs) throws SQLException {
    isOrder = true;
    buildRecord(rs);
  }


  /**
   * Description of the Method
   *
   * @param db        Description of the Parameter
   * @param addressId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void queryRecord(Connection db, int addressId) throws SQLException {
    if (addressId == -1) {
      throw new SQLException("Invalid Address Number");
    }

    PreparedStatement pst = db.prepareStatement(
        " SELECT addr.address_id, addr.contact_id, addr.address_type, addr.addrline1, addr.addrline2, " +
        "        addr.addrline3, addr.addrline4, addr.city, addr.state, addr.country, addr.postalcode, addr.primary_address, " +
        "				 addr.entered, addr.enteredby, addr.modified, addr.modifiedby, l.description " +
        " FROM order_address addr " +
        " LEFT JOIN lookup_orderaddress_types l " +
        " ON ( addr.address_type = l.code ) " +
        " WHERE addr.address_id = ? ");
    pst.setInt(1, addressId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (this.getId() == -1) {
      throw new SQLException("Address record not found");
    }
  }


  /**
   * Description of the Method
   *
   * @param db         Description of the Parameter
   * @param orderId    Description of the Parameter
   * @param enteredBy  Description of the Parameter
   * @param modifiedBy Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void process(Connection db, int orderId, int enteredBy, int modifiedBy) throws SQLException {
    if (this.getEnabled() == true) {
      if (this.getId() == -1) {
        this.setOrderId(orderId);
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
    insert(db, this.getOrderId(), this.getEnteredBy());
  }


  /**
   * Description of the Method
   *
   * @param db        Description of the Parameter
   * @param orderId   Description of the Parameter
   * @param enteredBy Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void insert(Connection db, int orderId, int enteredBy) throws SQLException {
    if (orderId > -1) {
      this.setOrderId(orderId);
    }
    if (enteredBy > -1) {
      this.setEnteredBy(enteredBy);
    }
    StringBuffer sql = new StringBuffer();
    this.setId(DatabaseUtils.getNextSeq(db, "order_address_address_id_seq"));
    int id = getId();
    sql.append(
        " INSERT INTO order_address(contact_id, order_id, address_type, addrline1, addrline2, addrline3, addrline4, " +
        " 	city, state, postalcode, country, ");
    if (id > -1) {
      sql.append("address_id, ");
    }
    sql.append("entered, ");
    sql.append("enteredby, ");
    sql.append("modified, ");
    sql.append("modifiedby ) ");
    sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
    if (id > -1) {
      sql.append("?,");
    }
    if (this.getEntered() != null) {
      sql.append("?, ");
    } else {
      sql.append(DatabaseUtils.getCurrentTimestamp(db) + ", ");
    }
    sql.append("?, ");
    if (this.getModified() != null) {
      sql.append("?, ");
    } else {
      sql.append(DatabaseUtils.getCurrentTimestamp(db) + ", ");
    }
    sql.append("?) ");
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql.toString());
    if (this.getContactId() > -1) {
      pst.setInt(++i, this.getContactId());
    } else {
      pst.setNull(++i, java.sql.Types.INTEGER);
    }
    pst.setInt(++i, this.getOrderId());
    if (this.getType() > -1) {
      pst.setInt(++i, this.getType());
    } else {
      pst.setNull(++i, java.sql.Types.INTEGER);
    }
    pst.setString(++i, this.getStreetAddressLine1());
    pst.setString(++i, this.getStreetAddressLine2());
    pst.setString(++i, this.getStreetAddressLine3());
    pst.setString(++i, this.getStreetAddressLine4());
    pst.setString(++i, this.getCity());
    pst.setString(++i, this.getState());
    if (this.getEntered() != null) {
      pst.setTimestamp(++i, this.getEntered());
    }
    pst.setString(++i, this.getZip());
    pst.setString(++i, this.getCountry());
    if (id > -1) {
      pst.setInt(++i, id);
    }
    DatabaseUtils.setInt(pst, ++i, this.getEnteredBy());
    if (this.getModified() != null) {
      pst.setTimestamp(++i, this.getModified());
    }
    DatabaseUtils.setInt(pst, ++i, this.getModifiedBy());
    pst.execute();
    pst.close();
    this.setId(
        DatabaseUtils.getCurrVal(db, "order_address_address_id_seq", id));
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
      throw new SQLException("Address ID not specified");
    }
    boolean commit = true;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      PreparedStatement pst = db.prepareStatement(
          " DELETE FROM order_address WHERE address_id = ?");
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();
      if (commit) {
        db.commit();
      }
    } catch (SQLException e) {
      e.printStackTrace();
      if (commit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (commit) {
        db.setAutoCommit(true);
      }
    }
    return true;
  }


  /**
   * Description of the Method
   *
   * @param db         Description of the Parameter
   * @param modifiedBy Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public int update(Connection db, int modifiedBy) throws SQLException {
    int resultCount = 0;
    if (this.getId() == -1) {
      return -1;
    }
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        " UPDATE order_address " +
        " SET address_type = ?, " +
        "		 addrline1 = ?, " +
        "     addrline2 = ?, " +
        "     addrline3 = ?, " +
        "     city = ?, " +
        "     state = ?, " +
        "     postalcode = ?, " +
        "     country = ?, " +
        "     modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", " +
        "     modifiedby = ? " +
        " WHERE address_id = ? ");

    pst = db.prepareStatement(sql.toString());
    int i = 0;
    if (this.getType() > -1) {
      pst.setInt(++i, this.getType());
    } else {
      pst.setNull(++i, java.sql.Types.INTEGER);
    }
    pst.setString(++i, this.getStreetAddressLine1());
    pst.setString(++i, this.getStreetAddressLine2());
    pst.setString(++i, this.getStreetAddressLine3());
    pst.setString(++i, this.getCity());
    pst.setString(++i, this.getState());
    pst.setString(++i, this.getZip());
    pst.setString(++i, this.getCountry());
    pst.setInt(++i, modifiedBy);
    pst.setInt(++i, this.getId());

    resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }

}

