package org.aspcfs.modules.admin.base;

import java.sql.*;
import org.aspcfs.utils.web.LookupElement;

/**
 *  Represents Access Type for a CFS component
 *
 *@author     Mathur
 *@created    June 25, 2003
 *@version    $id:exp$
 */
public class AccessType extends LookupElement {
  //link module ids
  public final static int GENERAL_CONTACTS = 626030330;
  public final static int ACCOUNT_CONTACTS = 626030331;
  public final static int EMPLOYEES = 626030332;

  //sharing types
  public final static int PERSONAL = 626030333;
  public final static int PUBLIC = 626030334;
  public final static int CONTROLLED_HIERARCHY = 626030335;

  int linkModuleId = -1;
  int ruleId = -1;


  /**
   *Constructor for the AccessType object
   */
  public AccessType() { }


  /**
   *Constructor for the AccessType object
   *
   *@param  db                         Description of the Parameter
   *@param  code                       Description of the Parameter
   *@exception  java.sql.SQLException  Description of the Exception
   */
  public AccessType(Connection db, int code) throws java.sql.SQLException {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("AccessType-> Retrieving ID: " + code + " from lookup_access_types ");
    }
    PreparedStatement pst = db.prepareStatement(
        "SELECT code, link_module_id, description, default_item, level, enabled, rule_id " +
        "FROM lookup_access_types " +
        "WHERE code = ? "
        );
    pst.setInt(1, code);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      build(rs);
    }
    rs.close();
    pst.close();
    if (code < 0) {
      throw new java.sql.SQLException("ID not found");
    }
  }


  /**
   *Constructor for the AccessType object
   *
   *@param  rs                         Description of the Parameter
   *@exception  java.sql.SQLException  Description of the Exception
   */
  public AccessType(ResultSet rs) throws java.sql.SQLException {
    build(rs);
  }


  /**
   *  Sets the ruleId attribute of the AccessType object
   *
   *@param  ruleId  The new ruleId value
   */
  public void setRuleId(int ruleId) {
    this.ruleId = ruleId;
  }


  /**
   *  Sets the linkModuleId attribute of the AccessType object
   *
   *@param  linkModuleId  The new linkModuleId value
   */
  public void setLinkModuleId(int linkModuleId) {
    this.linkModuleId = linkModuleId;
  }


  /**
   *  Gets the ruleId attribute of the AccessType object
   *
   *@return    The ruleId value
   */
  public int getRuleId() {
    return ruleId;
  }


  /**
   *  Gets the linkModuleId attribute of the AccessType object
   *
   *@return    The linkModuleId value
   */
  public int getLinkModuleId() {
    return linkModuleId;
  }


  /**
   *  Build the Access Type record
   *
   *@param  rs                         Description of the Parameter
   *@exception  java.sql.SQLException  Description of the Exception
   */
  public void build(ResultSet rs) throws java.sql.SQLException {
    code = rs.getInt("code");
    linkModuleId = rs.getInt("link_module_id");
    description = rs.getString("description");
    defaultItem = rs.getBoolean("default_item");
    level = rs.getInt("level");
    enabled = rs.getBoolean("enabled");
    if (!(this.getEnabled())) {
      description += " (X)";
    }
    ruleId = rs.getInt("rule_id");
  }

}


