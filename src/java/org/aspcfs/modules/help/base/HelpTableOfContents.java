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
 * List class of TableofContentItem
 *
 * @author kbhoopal
 * @version $Id: HelpTableOfContents.java,v 1.8 2003/07/25 21:19:25 kbhoopal
 *          Exp $
 * @created November 10, 2003
 */
public class HelpTableOfContents extends ArrayList {
  public final static String tableName = "help_tableof_contents";
  public final static String uniqueField = "content_id";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;
  private PagedListInfo pagedListInfo = null;

  /**
   * Constructor for the HelpTableOfContents object
   */
  public HelpTableOfContents() {
  }

  /**
   * Sets the lastAnchor attribute of the HelpTableOfContents object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(java.sql.Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   * Sets the lastAnchor attribute of the HelpTableOfContents object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(String tmp) {
    this.lastAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the nextAnchor attribute of the HelpTableOfContents object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(java.sql.Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   * Sets the nextAnchor attribute of the HelpTableOfContents object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(String tmp) {
    this.nextAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   * Sets the syncType attribute of the HelpTableOfContents object
   *
   * @param tmp The new syncType value
   */
  public void setSyncType(int tmp) {
    this.syncType = tmp;
  }

  /**
   * Sets the PagedListInfo attribute of the HelpTableOfContents object. <p>
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
   * Gets the tableName attribute of the HelpTableOfContents object
   *
   * @return The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   * Gets the uniqueField attribute of the HelpTableOfContents object
   *
   * @return The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
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
   * Description of the Method
   *
   * @param id          Description of the Parameter
   * @param contentList Description of the Parameter
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
   * Description of the Method
   *
   * @param contentList Description of the Parameter
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
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  void buildItemLinks(Connection db) throws SQLException {
    for (int i = 0; i < this.size(); i++) {
      ((HelpTableOfContentItem) this.get(i)).buildHTOCLinks(db);
    }
  }
}

