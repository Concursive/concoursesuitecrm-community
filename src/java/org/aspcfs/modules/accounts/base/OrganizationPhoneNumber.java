//Copyright 2001 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import java.sql.*;
import com.darkhorseventures.utils.DatabaseUtils;

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
          queryRecord(db, Integer.parseInt(phoneNumberId));
  }
  
  public OrganizationPhoneNumber(Connection db, int phoneNumberId) throws SQLException {
          queryRecord(db, phoneNumberId);
  }
          
  public void queryRecord(Connection db, int phoneNumberId) throws SQLException {
    if (phoneNumberId <= 0) {
      throw new SQLException("Invalid Phone Number ID specified.");
    }

    Statement st = null;
    ResultSet rs = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT * " +
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
  
  public void insert(Connection db) throws SQLException {
          insert(db, this.getOrgId(), this.getEnteredBy());
  }
          
  public void insert(Connection db, int orgId, int enteredBy) throws SQLException {
    StringBuffer sql = new StringBuffer();
    sql.append("INSERT INTO organization_phone " +
        "(org_id, phone_type, number, extension, ");
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
    
    if (orgId > -1) {
      pst.setInt(++i, this.getOrgId());
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

    this.setId(DatabaseUtils.getCurrVal(db, "organization_phone_phone_id_seq"));
  }
  
  public void update(Connection db, int modifiedBy) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "UPDATE organization_phone " +
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
    pst.setInt(++i, this.getModifiedBy());
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

