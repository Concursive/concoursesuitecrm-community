//Copyright 2001 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import java.sql.*;

/**
 *  Represents an organization phone number, extending the base PhoneNumber
 *  class.
 *
 *@author     mrajkowski
 *@created    September 4, 2001
 *@version    $Id$
 */
public class OrganizationPhoneNumber extends PhoneNumber {

  /**
   *  Constructor for the OrganizationPhoneNumber object
   *
   *@since
   */
  public OrganizationPhoneNumber() { }


  /**
   *  Constructor for the OrganizationPhoneNumber object
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since
   */
  public OrganizationPhoneNumber(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Constructor for the OrganizationPhoneNumber object
   *
   *@param  db                Description of Parameter
   *@param  phoneNumberId     Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since
   */
  public OrganizationPhoneNumber(Connection db, String phoneNumberId) throws SQLException {
    if (phoneNumberId == null) {
      throw new SQLException("Phone Number ID not specified.");
    }

    Statement st = null;
    ResultSet rs = null;
    StringBuffer sql = new StringBuffer();
    sql.append("SELECT * " +
        "FROM organization_phone p, lookup_orgphone_types l " +
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
  
  public void insert(Connection db, int orgId, int enteredBy) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO organization_phone " +
        "(org_id, phone_type, number, extension, enteredby, modifiedby) " +
        "VALUES " +
        "(?, ?, ?, ?, ?, ?) ");
    int i = 0;
    pst.setInt(++i, orgId);
    pst.setInt(++i, this.getType());
    pst.setString(++i, this.getNumber());
    pst.setString(++i, this.getExtension());
    pst.setInt(++i, enteredBy);
    pst.setInt(++i, enteredBy);
    pst.execute();
    pst.close();

    Statement st = db.createStatement();
    ResultSet rs = st.executeQuery("select currval('organization_phone_phone_id_seq')");
    if (rs.next()) {
      this.setId(rs.getInt(1));
    }
    rs.close();
    st.close();
  }
  
  public void update(Connection db, int modifiedBy) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "UPDATE organization_phone " +
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
  
  public void delete(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "DELETE FROM organization_phone " +
        "WHERE phone_id = ? ");
    int i = 0;
    pst.setInt(++i, this.getId());
    pst.execute();
    pst.close();
  }

}

