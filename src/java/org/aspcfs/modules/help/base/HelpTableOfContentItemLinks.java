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
package org.aspcfs.modules.help.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;

/**
 *  List class for TableOfContentItemLink
 *
 *@author     kbhoopal
 *@created    November 11, 2003
 *@version    $Id: HelpTableOfContentItemLinks.java,v 1.3 2003/12/10 19:14:38
 *      mrajkowski Exp $
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

