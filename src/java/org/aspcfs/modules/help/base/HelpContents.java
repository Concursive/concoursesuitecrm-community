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
public class HelpContents extends Vector {

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
    PreparedStatement pst = null;
    ResultSet rs = null;

    String sql =
        "SELECT module, section, subsection, description, " +
        "help_id, display1, display2, display3, display4, " +
        "enteredby, entered, modifiedby, modified " +
        "FROM help_contents h " +
        "WHERE enabled = " + DatabaseUtils.getTrue(db) + " " +
        "GROUP BY module, section, subsection, description, " +
        "help_id, display1, display2, display3, display4, " +
        "enteredby, entered, modifiedby, modified ";
    pst = db.prepareStatement(sql);
    rs = pst.executeQuery();
    while (rs.next()) {
      HelpItem thisItem = new HelpItem(rs);
      this.add(thisItem);
    }
    rs.close();
    pst.close();
  }

}

