//Copyright 2003 Dark Horse Ventures

package org.aspcfs.modules.help.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;

/**
 *  Description of the Class
 *
 *@author     kbhoopal
 *@created    November 11, 2003
 *@version    $Id$
 */
public class HelpTableOfContentItemLinks extends ArrayList {

  /**
   *  Constructor for the HelpTableOfContentItemLinks object
   */
  public HelpTableOfContentItemLinks() { }


  /**
   *  Constructor for the HelpTableOfContentItemLinks object
   *
   *@param  db                Description of the Parameter
   *@param  contentItemId     Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public HelpTableOfContentItemLinks(Connection db, int contentItemId) throws SQLException {
    PreparedStatement pst = db.prepareStatement("SELECT * " +
        "FROM help_tableofcontentitem_links hl " +
        "WHERE global_link_id = ?");
    int i = 0;
    pst.setInt(++i, contentItemId);
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      HelpTableOfContentItemLink thisLinkItem = new HelpTableOfContentItemLink(rs);
      this.add(thisLinkItem);
    }
    rs.close();
    pst.close();
    buildLinkTypes(db);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  void buildLinkTypes(Connection db) throws SQLException {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      ((HelpTableOfContentItemLink) i.next()).fetchLinkDetails(db);
    }
  }
}

