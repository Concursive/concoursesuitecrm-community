//Copyright 2002 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import java.util.*;
import java.sql.*;
import com.darkhorseventures.utils.DatabaseUtils;

public class HelpContents extends Vector {

  public HelpContents() { }

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

