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
  
  public ContactEmailAddress(Connection db, int emailAddressId) throws SQLException {
          queryRecord(db, emailAddressId);
  }


  public ContactEmailAddress(Connection db, String emailAddressId) throws SQLException {
          queryRecord(db, Integer.parseInt(emailAddressId));
  }
 

  
  public void queryRecord(Connection db, int emailAddressId) throws SQLException {
    isContact = true;
    
    if (emailAddressId < 0) {
      throw new SQLException("Valid Email Address ID not specified.");
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
  
  public void insert(Connection db) throws SQLException  {
          insert(db, this.getContactId(), this.getEnteredBy());
  }

  public void insert(Connection db, int contactId, int enteredBy) throws SQLException {
    StringBuffer sql = new StringBuffer();
    
    sql.append("INSERT INTO contact_emailaddress " +
        "(contact_id, emailaddress_type, email, ");
                if (this.getEntered() != null) {
                        sql.append("entered, ");
                }
                if (this.getModified() != null) {
                        sql.append("modified, ");
                }        
    sql.append("enteredBy, modifiedBy ) ");        
    sql.append("VALUES (?, ?, ?, ");
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
    
    pst.setString(++i, this.getEmail());
    
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

    this.setId(DatabaseUtils.getCurrVal(db, "contact_email_emailaddress__seq"));
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
    if (this.getType() > -1) {
      pst.setInt(++i, this.getType());
    } else {
      pst.setNull(++i, java.sql.Types.INTEGER);
    }
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

