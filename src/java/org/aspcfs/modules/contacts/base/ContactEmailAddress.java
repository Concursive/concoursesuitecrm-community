//Copyright 2001 Dark Horse Ventures
package com.darkhorseventures.cfsbase;

import java.sql.*;
import com.darkhorseventures.utils.DatabaseUtils;

public class ContactEmailAddress extends EmailAddress {

  public ContactEmailAddress() {
    isContact = true;
  }


  public ContactEmailAddress(ResultSet rs) throws SQLException {
    isContact = true;
    buildRecord(rs);
  }


  public ContactEmailAddress(Connection db, String emailAddressId) throws SQLException {
    isContact = true;
    if (emailAddressId == null) {
      throw new SQLException("Email Address ID not specified.");
    }

    Statement st = null;
    ResultSet rs = null;
    StringBuffer sql = new StringBuffer();
    sql.append("SELECT * " +
        "FROM contact_emailaddress c, lookup_contactemail_types l " +
        "WHERE c.emailaddress_type = l.code " +
        "AND emailaddress_id = " + emailAddressId + " ");
    st = db.createStatement();
    rs = st.executeQuery(sql.toString());
    if (rs.next()) {
      buildRecord(rs);
    } else {
      rs.close();
      st.close();
      throw new SQLException("Email record not found.");
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


  public void insert(Connection db, int contactId, int enteredBy) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO contact_emailaddress " +
        "(contact_id, emailaddress_type, email, enteredby, modifiedby) " +
        "VALUES " +
        "(?, ?, ?, ?, ?) ");
    int i = 0;
    pst.setInt(++i, contactId);
    pst.setInt(++i, this.getType());
    pst.setString(++i, this.getEmail());
    pst.setInt(++i, enteredBy);
    pst.setInt(++i, enteredBy);
    pst.execute();
    pst.close();

    Statement st = db.createStatement();
    ResultSet rs = null;
      switch (DatabaseUtils.getType(db)) {
        case DatabaseUtils.POSTGRESQL:
          rs = st.executeQuery("select currval('contact_email_emailaddress__seq')");
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
        "UPDATE contact_emailaddress " +
        "SET emailaddress_type = ?, email = ?, modifiedby = ?, " +
        "modified = CURRENT_TIMESTAMP " +
        "WHERE emailaddress_id = ? ");
    int i = 0;
    pst.setInt(++i, this.getType());
    pst.setString(++i, this.getEmail());
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
        "DELETE FROM contact_emailaddress " +
        "WHERE emailaddress_id = ? ");
    int i = 0;
    pst.setInt(++i, this.getId());
    pst.execute();
    pst.close();
  }

}

