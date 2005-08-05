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
package org.aspcfs.apps.help;

import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Description of the Class
 *
 * @author kbhoopal
 * @version $Id: TableOfContentItem.java,v 1.1.2.1 2003/12/02 20:29:04
 *          kbhoopal Exp $
 * @created December 1, 2003
 */
public class TableOfContentItem {

  int id = -1;
  int categoryId = -1;
  String displayText = null;
  int parent = -1;
  int level = -1;
  int order = -1;
  int pageId = -1;


  /**
   * Constructor for the TableOfContentItem object
   */
  public TableOfContentItem() {
  }


  /**
   * Constructor for the TableOfContentItem object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public TableOfContentItem(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Sets the id attribute of the TableOfContentItem object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the TableOfContentItem object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Sets the categoryId attribute of the TableOfContentItem object
   *
   * @param tmp The new categoryId value
   */
  public void setCategoryId(int tmp) {
    this.categoryId = tmp;
  }


  /**
   * Sets the displayText attribute of the TableOfContentItem object
   *
   * @param tmp The new displayText value
   */
  public void setDisplayText(String tmp) {
    this.displayText = tmp;
  }


  /**
   * Sets the parent attribute of the TableOfContentItem object
   *
   * @param tmp The new parent value
   */
  public void setParent(int tmp) {
    this.parent = tmp;
  }


  /**
   * Sets the level attribute of the TableOfContentItem object
   *
   * @param tmp The new level value
   */
  public void setLevel(int tmp) {
    this.level = tmp;
  }


  /**
   * Sets the order attribute of the TableOfContentItem object
   *
   * @param tmp The new order value
   */
  public void setOrder(int tmp) {
    this.order = tmp;
  }


  /**
   * Sets the pageId attribute of the TableOfContentItem object
   *
   * @param tmp The new pageId value
   */
  public void setPageId(int tmp) {
    this.pageId = tmp;
  }


  /**
   * Gets the id attribute of the TableOfContentItem object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the categoryId attribute of the TableOfContentItem object
   *
   * @return The categoryId value
   */
  public int getCategoryId() {
    return categoryId;
  }


  /**
   * Gets the displayText attribute of the TableOfContentItem object
   *
   * @return The displayText value
   */
  public String getDisplayText() {
    return displayText;
  }


  /**
   * Gets the parent attribute of the TableOfContentItem object
   *
   * @return The parent value
   */
  public int getParent() {
    return parent;
  }


  /**
   * Gets the level attribute of the TableOfContentItem object
   *
   * @return The level value
   */
  public int getLevel() {
    return level;
  }


  /**
   * Gets the order attribute of the TableOfContentItem object
   *
   * @return The order value
   */
  public int getOrder() {
    return order;
  }


  /**
   * Gets the pageId attribute of the TableOfContentItem object
   *
   * @return The pageId value
   */
  public int getPageId() {
    return pageId;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Table of Content Id for the item
   * @throws SQLException Description of the Exception
   */
  int insert(Connection db) throws SQLException {
    id = DatabaseUtils.getNextSeq(db, "help_tableof_contents_content_id_seq");
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO help_tableof_contents " +
        "(" + (id > -1 ? "content_id, " : "") + "displaytext " +
        ((categoryId == -1) ? "" : ",category_id ") +
        ((parent == -1) ? "" : ",parent") +
        ",contentlevel,contentorder, enteredby, modifiedby) " +
        "VALUES (" + (id > -1 ? "?, " : "") + "?" + ((categoryId == -1) ? "" : ",?") +
        ((parent == -1) ? "" : ",?") + ",?,?,?,?)");
    int i = 0;
    if (id > -1) {
      pst.setInt(++i, id);
    }
    pst.setString(++i, displayText);
    if (categoryId != -1) {
      pst.setInt(++i, categoryId);
    }
    if (parent != -1) {
      pst.setInt(++i, parent);
    }
    pst.setInt(++i, level);
    pst.setInt(++i, order);
    pst.setInt(++i, 0);
    pst.setInt(++i, 0);
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(
        db, "help_tableof_contents_content_id_seq",
        id);

    // inserting a link to a TOC item if a link page exists
    // Link page may not exist for top level items
    if (pageId != -1) {
      int linkId = DatabaseUtils.getNextSeq(
          db, "help_tableofcontentitem_links_link_id_seq");
      pst = db.prepareStatement(
          "INSERT INTO help_tableofcontentitem_links " +
          "(" + (linkId > -1 ? "link_id, " : "") + "global_link_id, linkto_content_id, enteredby, modifiedby) " +
          "VALUES (" + (linkId > -1 ? "?, " : "") + "?,?,?,?)");
      i = 0;
      if (linkId > -1) {
        pst.setInt(++i, linkId);
      }
      pst.setInt(++i, id);
      pst.setInt(++i, pageId);
      pst.setInt(++i, 0);
      pst.setInt(++i, 0);
      pst.execute();
      pst.close();
      linkId = DatabaseUtils.getCurrVal(
          db, "help_tableofcontentitem_links_link_id_seq", id);
    }
    return id;
  }


  /**
   * Description of the Method
   */
  public void buildRecord() {
    id = 1;
    displayText = "Modules";
    order = 5;
    level = 1;

  }


  /**
   * Description of the Method
   *
   * @param tmpCategory   Description of the Parameter
   * @param tmpCategoryId Description of the Parameter
   * @param tmpOrder      Description of the Parameter
   */
  public void buildRecord(String tmpCategory, int tmpCategoryId, int tmpOrder) {

    categoryId = tmpCategoryId;
    displayText = tmpCategory;
    level = 2;
    parent = 1;
    order = tmpOrder;

  }


  /**
   * Description of the Method
   *
   * @param tmpCategoryId Description of the Parameter
   * @param tmpTitle      Description of the Parameter
   * @param tmpOrder      Description of the Parameter
   * @param tmpLevel      Description of the Parameter
   * @param tmpPageId     Description of the Parameter
   */
  public void buildRecord(int tmpCategoryId, String tmpTitle, int tmpOrder, int tmpLevel, int tmpPageId) {

    categoryId = tmpCategoryId;
    displayText = tmpTitle;
    level = tmpLevel;
    order = tmpOrder;
    pageId = tmpPageId;

  }


  /**
   * Description of the Method
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("content_id");
    categoryId = rs.getInt("category_id");
    parent = rs.getInt("parent");
    order = rs.getInt("contentorder");
    level = rs.getInt("contentlevel");
    pageId = rs.getInt("linkto_content_id");
  }

}


