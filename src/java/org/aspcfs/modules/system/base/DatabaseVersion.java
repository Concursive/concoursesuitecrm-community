package org.aspcfs.modules.system.base;

import java.sql.*;

/**
 *  Class for reading the database version information
 *
 *@author     matt rajkowski
 *@created    July 31, 2003
 *@version    $Id$
 */
public class DatabaseVersion {

  /**
   *  Constructor for the DatabaseVersion object
   */
  public DatabaseVersion() { }


  /**
   *  Gets the latestVersion attribute of the DatabaseVersion class
   *
   *@param  db                Description of the Parameter
   *@return                   The latestVersion value
   *@exception  SQLException  Description of the Exception
   */
  public static String getLatestVersion(Connection db) throws SQLException {
    String version = "";
    PreparedStatement pst = db.prepareStatement(
        "SELECT max(script_version) AS version " +
        "FROM database_version ");
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      version = rs.getString("version");
    }
    rs.close();
    pst.close();
    return version;
  }

}

