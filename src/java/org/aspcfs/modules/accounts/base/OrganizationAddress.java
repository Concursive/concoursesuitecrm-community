//Copyright 2001 Dark Horse Ventures

package org.aspcfs.modules.accounts.base;

import java.sql.*;

import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.modules.base.Address;

/**
 *  Builds an address for an organization using a custom query that extends the
 *  fields and methods of a typical Address.
 *
 *@author     mrajkowski
 *@created    September 1, 2001
 *@version    $Id: OrganizationAddress.java,v 1.7 2002/09/20 14:52:45 chris Exp
 *      $
 */
public class OrganizationAddress extends Address {

  /**
   *  Constructor for the OrganizationAddress object
   *
   *@since    1.1
   */
  public OrganizationAddress() { }


  /**
   *  Constructor for the OrganizationAddress object
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since                    1.1
   */
  public OrganizationAddress(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Constructor for the OrganizationAddress object
   *
   *@param  db                Description of Parameter
   *@param  addressId         Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since                    1.1
   */
  public OrganizationAddress(Connection db, String addressId) throws SQLException {
    queryRecord(db, Integer.parseInt(addressId));
  }


  /**
   *  Constructor for the OrganizationAddress object
   *
   *@param  db                Description of the Parameter
   *@param  addressId         Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public OrganizationAddress(Connection db, int addressId) throws SQLException {
    queryRecord(db, addressId);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  addressId         Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void queryRecord(Connection db, int addressId) throws SQLException {
    Statement st = null;
    ResultSet rs = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT * " +
        "FROM organization_address c, lookup_orgaddress_types l " +
        "WHERE c.address_type = l.code " +
        "AND address_id = " + addressId + " ");
    st = db.createStatement();
    rs = st.executeQuery(sql.toString());
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    st.close();
    if (this.getId() == -1) {
      throw new SQLException("Address record not found.");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  orgId             Description of the Parameter
   *@param  enteredBy         Description of the Parameter
   *@param  modifiedBy        Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void process(Connection db, int orgId, int enteredBy, int modifiedBy) throws SQLException {
    if (this.getEnabled() == true) {
      if (this.getId() == -1) {
        this.setOrgId(orgId);
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
    insert(db, this.getOrgId(), this.getEnteredBy());
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  orgId             Description of the Parameter
   *@param  enteredBy         Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void insert(Connection db, int orgId, int enteredBy) throws SQLException {
    StringBuffer sql = new StringBuffer();
    sql.append("INSERT INTO organization_address " +
        "(org_id, address_type, addrline1, addrline2, city, state, postalcode, country, ");
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

    if (this.getOrgId() > -1) {
      pst.setInt(++i, this.getOrgId());
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

    this.setId(DatabaseUtils.getCurrVal(db, "organization_add_address_id_seq"));
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
        "UPDATE organization_address " +
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
        "DELETE FROM organization_address " +
        "WHERE address_id = ? ");
    int i = 0;
    pst.setInt(++i, this.getId());
    pst.execute();
    pst.close();
  }

}

