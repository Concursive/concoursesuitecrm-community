//Copyright 2001 Dark Horse Ventures

package org.aspcfs.modules.troubletickets.base;

import java.util.*;
import java.sql.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.modules.troubletickets.base.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 *  Description of the Class
 *
 *@author     kbhoopal
 *@created    February 11, 2004
 *@version    $Id$
 */
public class TicketReplacementPartList extends ArrayList {

  /**
   *  Constructor for the TicketReplacementPartList object
   */
  public TicketReplacementPartList() { }


  /**
   *  Constructor for the TicketReplacementPartList object
   *
   *@param  request  Description of the Parameter
   */
  public TicketReplacementPartList(HttpServletRequest request) {
    int i = 0;
    while (request.getParameter("partNumber" + (++i)) != null) {
      if (!request.getParameter("partNumber" + (i)).trim().equals("")) {
        TicketReplacementPart thisPart = new TicketReplacementPart();
        thisPart.buildRecord(request, i);
        this.add(thisPart);
      }
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  tmpFormId         Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void queryList(Connection db, int tmpFormId) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;

    pst = db.prepareStatement(" SELECT *  " +
        " FROM  trouble_asset_replacement " +
        " WHERE link_form_id = ? ");
    pst.setInt(1, tmpFormId);
    if (System.getProperty("DEBUG") != null) {
      System.out.println("TicketReplacementPartList-> before query: " + pst.toString());
    }
    rs = pst.executeQuery();
    if (System.getProperty("DEBUG") != null) {
      System.out.println("TicketReplacementPartList-> resultset");
    }
    while (rs.next()) {
      TicketReplacementPart thisPart = new TicketReplacementPart(rs);
      this.add(thisPart);
    }
    rs.close();
    pst.close();
    if (System.getProperty("DEBUG") != null) {
      System.out.println("TicketReplacementPartList-> rs closed");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  tmpFormId         Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public boolean deleteList(Connection db, int tmpFormId) throws SQLException {
    PreparedStatement pst = null;
    pst = db.prepareStatement(" DELETE " +
        " FROM  trouble_asset_replacement " +
        " WHERE link_form_id = ? ");

    pst.setInt(1, tmpFormId);
    pst.execute();
    pst.close();

    return true;
  }
}

