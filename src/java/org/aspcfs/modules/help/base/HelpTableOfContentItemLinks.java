/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.help.base;

import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * List class for TableOfContentItemLink
 *
 * @author kbhoopal
 * @version $Id: HelpTableOfContentItemLinks.java,v 1.3 2003/12/10 19:14:38
 *          mrajkowski Exp $
 * @created November 11, 2003
 */
public class HelpTableOfContentItemLinks extends ArrayList {
  public final static String tableName = "help_tableofcontentitem_links";
  public final static String uniqueField = "link_id";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;
  private PagedListInfo pagedListInfo = null;

  /**
   * Constructor for the HelpTableOfContentItemLinks object
   */
  public HelpTableOfContentItemLinks() {
  }

  /**
   * Sets the lastAnchor attribute of the HelpTableOfContentItemLinks object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(java.sql.Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   * Sets the lastAnchor attribute of the HelpTableOfContentItemLinks object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(String tmp) {
    this.lastAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the nextAnchor attribute of the HelpTableOfContentItemLinks object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(java.sql.Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   * Sets the nextAnchor attribute of the HelpTableOfContentItemLinks object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(String tmp) {
    this.nextAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the syncType attribute of the HelpTableOfContentItemLinks object
   *
   * @param tmp The new syncType value
   */
  public void setSyncType(int tmp) {
    this.syncType = tmp;
  }

  /**
   * Sets the PagedListInfo attribute of the HelpTableOfContentItemLinks object. <p>
   * <p/>
   * The query results will be constrained to the PagedListInfo parameters.
   *
   * @param tmp The new PagedListInfo value
   * @since 1.1
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }

  /**
   * Gets the tableName attribute of the HelpTableOfContentItemLinks object
   *
   * @return The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   * Gets the uniqueField attribute of the HelpTableOfContentItemLinks object
   *
   * @return The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }


  /**
   * Constructor for the HelpTableOfContentItemLinks object
   *
   * @param db            Description of the Parameter
   * @param contentItemId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public HelpTableOfContentItemLinks(Connection db, int contentItemId) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "SELECT * " +
            "FROM help_tableofcontentitem_links hl " +
            "WHERE global_link_id = ?");
    int i = 0;
    pst.setInt(++i, contentItemId);
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      HelpTableOfContentItemLink thisLinkItem = new HelpTableOfContentItemLink(
          rs);
      this.add(thisLinkItem);
    }
    rs.close();
    pst.close();
    buildLinkTypes(db);
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  void buildLinkTypes(Connection db) throws SQLException {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      ((HelpTableOfContentItemLink) i.next()).fetchLinkDetails(db);
    }
  }
}

