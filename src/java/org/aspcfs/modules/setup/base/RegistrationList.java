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
package org.aspcfs.modules.setup.base;

import java.util.ArrayList;
import java.sql.*;

/**
 *  Used for retrieving Registration items from the database
 *
 *@author     matt rajkowski
 *@created    November 21, 2003
 *@version    $Id: RegistrationList.java,v 1.2 2003/11/21 22:05:54 mrajkowski
 *      Exp $
 */
public class RegistrationList extends ArrayList {

  /**
   *  Retrieves a specific entry from the database
   *
   *@param  db                Description of the Parameter
   *@param  email             Description of the Parameter
   *@param  profile           Description of the Parameter
   *@param  enabled           Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public static Registration locate(Connection db, String email, String profile, boolean enabled) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "SELECT * " +
        "FROM registration " +
        "WHERE email = ? AND profile = ? AND enabled = ? ");
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

