/*
 *  Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. DARK HORSE
 *  VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.system.base;

import java.sql.*;

/**
 *  Class for reading the database version information
 *
 *@author     matt rajkowski
 *@created    July 31, 2003
 *@version    $Id: DatabaseVersion.java,v 1.1 2003/07/31 20:38:12 mrajkowski Exp
 *      $
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

