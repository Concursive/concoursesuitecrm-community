//Copyright 2001 Dark Horse Ventures
package com.darkhorseventures.cfsbase;

import java.sql.*;
import com.darkhorseventures.utils.DatabaseUtils;

/**
 *  Builds an address for a contact using a custom query that extends the fields
 *  and methods of a typical Address.
 *
 *@author     mrajkowski
 *@created    September 1, 2001
 *@version    $Id$
 */
public class ContactAddress extends Address {

  /**
   *  Constructor for the ContactAddress object
   *
   *@since    1.1
   */
  public ContactAddress() {
    isContact = true;
  }


  /**
   *  Constructor for the ContactAddress object
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since                    1.1
   */
  public ContactAddress(ResultSet rs) throws SQLException {
    isContact = true;
    buildRecord(rs);
  }


  /**
   *  Constructor for the ContactAddress object
   *
   *@param  db                Description of Parameter
   *@param  addressId         Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since                    1.1
   */
  public ContactAddress(Connection db, String addressId) throws SQLException {
    isContact = true;
    Statement st = null;
    ResultSet rs = null;
    StringBuffer sql = new StringBuffer();
    sql.append("SELECT * " +
        "FROM contact_address c, lookup_contactaddress_types l " +
        "WHERE c.address_type = l.code " +
        "AND address_id = " + addressId + " ");
    st = db.createStatement();
    rs = st.executeQuery(sql.toString());
    if (rs.next()) {
      buildRecord(rs);
    } else {
      rs.close();
      st.close();
      throw new SQLException("Address record not found.");
    }
    rs.close();
    st.close();
  }


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


  public void insert(Connection db, int contactId, int enteredBy) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO contact_address " +
        "(contact_id, address_type, addrline1, addrline2, city, state, postalcode, country, enteredby, modifiedby) " +
        "VALUES " +
        "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
    int i = 0;
    pst.setInt(++i, contactId);
    pst.setInt(++i, this.getType());
    pst.setString(++i, this.getStreetAddressLine1());
    pst.setString(++i, this.getStreetAddressLine2());
    pst.setString(++i, this.getCity());
    pst.setString(++i, this.getState());
    pst.setString(++i, this.getZip());
    pst.setString(++i, this.getCountry());
    pst.setInt(++i, enteredBy);
    pst.setInt(++i, enteredBy);
    pst.execute();
    pst.close();
    
    Statement st = db.createStatement();
    ResultSet rs = null;
      switch (DatabaseUtils.getType(db)) {
        case DatabaseUtils.POSTGRESQL:
          rs = st.executeQuery("select currval('contact_address_address_id_seq')");
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
        "UPDATE contact_address " +
        "SET address_type = ?, addrline1 = ?, addrline2 = ?, city = ?, state = ?, postalcode = ?, country = ?, " +
        "modifiedby = ?, modified = CURRENT_TIMESTAMP " +
        "WHERE address_id = ? ");
    int i = 0;
    pst.setInt(++i, this.getType());
    pst.setString(++i, this.getStreetAddressLine1());
    pst.setString(++i, this.getStreetAddressLine2());
    pst.setString(++i, this.getCity());
    pst.setString(++i, this.getState());
    pst.setString(++i, this.getZip());
    pst.setString(++i, this.getCountry());
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
        "DELETE FROM contact_address " +
        "WHERE address_id = ? ");
    int i = 0;
    pst.setInt(++i, this.getId());
    pst.execute();
    pst.close();
  }

}

