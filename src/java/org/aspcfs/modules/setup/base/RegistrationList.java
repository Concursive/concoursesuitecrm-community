package org.aspcfs.modules.setup.base;

import java.util.ArrayList;
import java.sql.*;

/**
 *  Used for retrieving Registration items from the database
 *
 *@author     matt rajkowski
 *@created    November 21, 2003
 *@version    $Id$
 */
public class RegistrationList extends ArrayList {

  /**
   *  Retrieves a specific entry from the database
   *
   *@param  db                Description of the Parameter
   *@param  email             Description of the Parameter
   *@param  profile           Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public static Registration locate(Connection db, String email, String profile, boolean enabled) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "SELECT * " +
        "FROM registration " +
        "WHERE email = ?, profile = ?, enabled = ? ");
    pst.setString(1, email);
    pst.setString(2, profile);
    pst.setBoolean(3, enabled);
    ResultSet rs = pst.executeQuery();
    Registration previous = null;
    if (rs.next()) {
      previous = new Registration(rs);
    }
    rs.close();
    return previous;
  }

}

