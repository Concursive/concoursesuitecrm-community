package com.darkhorseventures.cfsbase;

import java.sql.*;

/**
 *  Represents a ContactType -- every contact has a type. <br>
 *  0 is an Employee <br>
 *  1 is Not Specified
 *
 *@author     mrajkowski
 *@created    August 29, 2001
 *@version    $Id$
 */
public class ContactType {

  private int id = 0;
  private String description = null;
  private boolean enabled = true;


  /**
   *  Constructor for the ContactType object
   *
   *@since    1.1
   */
  public ContactType() {
  }


  /**
   *  Constructor for the ContactType object
   *
   *@param  rs                         Description of Parameter
   *@exception  java.sql.SQLException  Description of Exception
   *@since                             1.1
   */
  public ContactType(ResultSet rs) throws java.sql.SQLException {
    id = rs.getInt("code");
    description = rs.getString("description");
    enabled = rs.getBoolean("enabled");
  }


  /**
   *  Sets the Id attribute of the ContactType object
   *
   *@param  tmp  The new Id value
   *@since       1.1
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the Description attribute of the ContactType object
   *
   *@param  tmp  The new Description value
   *@since       1.1
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   *  Sets the Enabled attribute of the ContactType object
   *
   *@param  tmp  The new Enabled value
   *@since       1.1
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   *  Gets the Id attribute of the ContactType object
   *
   *@return    The Id value
   *@since     1.1
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the Description attribute of the ContactType object
   *
   *@return    The Description value
   *@since     1.1
   */
  public String getDescription() {
    return description;
  }


  /**
   *  Gets the Enabled attribute of the ContactType object
   *
   *@return    The Enabled value
   *@since     1.1
   */
  public boolean getEnabled() {
    return enabled;
  }

}

