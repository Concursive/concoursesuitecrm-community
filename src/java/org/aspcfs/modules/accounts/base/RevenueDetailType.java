package org.aspcfs.modules.accounts.base;

import java.sql.*;
import com.darkhorseventures.database.Connection;
import org.aspcfs.utils.DatabaseUtils;

/**
 *  Description of the Class
 *
 *@author     Mathur
 *@created    January 13, 2003
 *@version    $Id$
 */
public class RevenueDetailType {

  private int id = 0;
  private String description = null;
  private boolean enabled = true;


  /**
   *  Constructor for the RevenueDetailType object
   */
  public RevenueDetailType() { }


  /**
   *  Constructor for the RevenueDetailType object
   *
   *@param  rs                         Description of the Parameter
   *@exception  java.sql.SQLException  Description of the Exception
   */
  public RevenueDetailType(ResultSet rs) throws java.sql.SQLException {
    id = rs.getInt("code");
    description = rs.getString("description");
    enabled = rs.getBoolean("enabled");
  }


  /**
   *  Sets the id attribute of the RevenueDetailType object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the RevenueDetailType object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the description attribute of the RevenueDetailType object
   *
   *@param  tmp  The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   *  Sets the enabled attribute of the RevenueDetailType object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   *  Gets the id attribute of the RevenueDetailType object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the description attribute of the RevenueDetailType object
   *
   *@return    The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   *  Gets the enabled attribute of the RevenueDetailType object
   *
   *@return    The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }

}

