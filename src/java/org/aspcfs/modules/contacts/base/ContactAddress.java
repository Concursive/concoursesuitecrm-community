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
          queryRecord(db, Integer.parseInt(addressId));
  }
          
  public ContactAddress(Connection db, int addressId) throws SQLException {
          queryRecord(db, addressId);
  }
          
  public void queryRecord(Connection db, int addressId) throws SQLException {
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
  
  public void insert(Connection db) throws SQLException {
          insert(db, this.getContactId(), this.getEnteredBy());
  }


  public void insert(Connection db, int contactId, int enteredBy) throws SQLException {
    StringBuffer sql = new StringBuffer();
    sql.append("INSERT INTO contact_address " +
        "(contact_id, address_type, addrline1, addrline2, city, state, postalcode, country, ");
                if (this.getEntered() != null) {
                        sql.append("entered, ");
                }
                if (this.getModified() != null) {
                        sql.append("modified, ");
                }        
    sql.append("enteredBy, modifiedBy ) ");
    sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ");
                if (this.getEntered() != null) {
                        sql.append("?, ");
                }
                if (this.getModified() != null) {
                        sql.append("?, ");
                }    
    sql.append("?, ?) ");                
    
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql.toString());
    
    if (this.getContactId() > -1) {
      pst.setInt(++i, this.getContactId());
    } else {
      pst.setNull(++i, java.sql.Types.INTEGER);
    }
    if (this.getType() > -1) {
      pst.setInt(++i, this.getType());
    } else {
      pst.setNull(++i, java.sql.Types.INTEGER);
    }
    pst.setString(++i, this.getStreetAddressLine1());
    pst.setString(++i, this.getStreetAddressLine2());
    pst.setString(++i, this.getCity());
    pst.setString(++i, this.getState());
    pst.setString(++i, this.getZip());
    pst.setString(++i, this.getCountry());
    
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
    
    this.setId(DatabaseUtils.getCurrVal(db, "contact_address_address_id_seq"));
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
    if (this.getType() > -1) {
      pst.setInt(++i, this.getType());
    } else {
      pst.setNull(++i, java.sql.Types.INTEGER);
    }
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

