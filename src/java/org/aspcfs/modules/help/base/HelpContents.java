//Copyright 2002 Dark Horse Ventures

package org.aspcfs.modules.help.base;

import java.util.*;
import java.sql.*;
import org.aspcfs.utils.DatabaseUtils;

/**
 *  Description of the Class
 *
 *@author     mrajkowski
 *@created    January 14, 2003
 *@version    $Id$
 */
public class HelpContents extends ArrayList {

  /**
   *  Constructor for the HelpContents object
   */
  public HelpContents() { }


  /**
   *  Constructor for the HelpContents object
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public HelpContents(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "SELECT * " +
        "FROM help_contents h " +
        "WHERE enabled = ? " +
        "ORDER BY module, section, subsection");
    pst.setBoolean(1, true);
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      HelpItem thisItem = new HelpItem(rs);
      this.add(thisItem);
    }
    rs.close();
    pst.close();
  }

}

