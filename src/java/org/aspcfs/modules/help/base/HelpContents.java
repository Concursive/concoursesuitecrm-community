//Copyright 2002 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import java.util.*;
import java.sql.*;

public class HelpContents extends Vector {

  public HelpContents() { }

  public HelpContents(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;

    String sql =
        "SELECT * " +
        "FROM help_contents h " +
        "WHERE level1 = 0 ";
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

