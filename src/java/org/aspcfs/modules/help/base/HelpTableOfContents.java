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
 *  List class of TableofContentItem
 *
 *@author     kbhoopal
 *@created    November 10, 2003
 *@version    $Id: HelpTableOfContents.java,v 1.8 2003/07/25 21:19:25 kbhoopal
 *      Exp $
 */
public class HelpTableOfContents extends ArrayList {

  /**
   *  Constructor for the HelpTableOfContents object
   */
  public HelpTableOfContents() { }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public HelpTableOfContents(Connection db) throws SQLException {
    int items = -1;
    PreparedStatement pst = db.prepareStatement(
        "SELECT * " +
        "FROM help_tableof_contents ht ");
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      HelpTableOfContentItem thisTOCItem = new HelpTableOfContentItem(rs);
      this.add(thisTOCItem);
    }
    rs.close();
    pst.close();
    buildItemLinks(db);
  }


  /**
   *  Description of the Method
   *
   *@param  id           Description of the Parameter
   *@param  contentList  Description of the Parameter
   */
  public void buildChildren(int id, HelpTableOfContents contentList) {
    Iterator i = contentList.iterator();
    while (i.hasNext()) {
      HelpTableOfContentItem thisTOCItem = (HelpTableOfContentItem) i.next();
      if (id == thisTOCItem.getParent()) {
        this.add(thisTOCItem);
      }
    }
  }


  /**
   *  Description of the Method
   *
   *@param  contentList  Description of the Parameter
   */
  public void buildTopLevelList(HelpTableOfContents contentList) {
    Iterator i = contentList.iterator();
    while (i.hasNext()) {
      HelpTableOfContentItem thisTOCItem = (HelpTableOfContentItem) i.next();
      if (0 == thisTOCItem.getParent()) {
        this.add(thisTOCItem);
      }
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  void buildItemLinks(Connection db) throws SQLException {
    for (int i = 0; i < this.size(); i++) {
      ((HelpTableOfContentItem) this.get(i)).buildHTOCLinks(db);
    }
  }
}

