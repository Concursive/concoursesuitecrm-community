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
import org.aspcfs.utils.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Description of the Class
 *
 * @author kbhoopal
 * @version $Id$
 * @created November 26, 2003
 */
public class HelpModule {

  private int id = -1;
  private String category = null;
  private int categoryId = -1;
  private String briefDescription = null;
  private String detailDescription = null;
  private int pageId = -1;

  private int contentLevel = -1;

  ArrayList helpContents;

  private boolean insert = false;


  /**
   * Constructor for the HelpModule object
   */
  public HelpModule() {

    helpContents = new ArrayList();

  }


  /**
   * Constructor for the HelpModule object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public HelpModule(ResultSet rs) throws SQLException {

    helpContents = new ArrayList();
    buildRecord(rs);
  }


  /**
   * Sets the id attribute of the HelpModule object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the HelpModule object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Sets the category attribute of the HelpModule object
   *
   * @param tmp The new category value
   */
  public void setCategory(String tmp) {
    this.category = tmp;
  }


  /**
   * Sets the categoryId attribute of the HelpModule object
   *
   * @param tmp The new categoryId value
   */
  public void setCategoryId(int tmp) {
    this.categoryId = tmp;
  }


  /**
   * Sets the categoryId attribute of the HelpModule object
   *
   * @param tmp The new categoryId value
   */
  public void setCategoryId(String tmp) {
    this.categoryId = Integer.parseInt(tmp);
  }


  /**
   * Sets the briefDescription attribute of the HelpModule object
   *
   * @param tmp The new briefDescription value
   */
  public void setBriefDescription(String tmp) {
    this.briefDescription = tmp;
  }


  /**
   * Sets the detailDescription attribute of the HelpModule object
   *
   * @param tmp The new detailDescription value
   */
  public void setDetailDescription(String tmp) {
    this.detailDescription = tmp;
  }


  /**
   * Sets the pageId attribute of the HelpModule object
   *
   * @param tmp The new pageId value
   */
  public void setPageId(int tmp) {
    this.pageId = tmp;
  }


  /**
   * Sets the pageId attribute of the HelpModule object
   *
   * @param tmp The new pageId value
   */
  public void setPageId(String tmp) {
    this.pageId = Integer.parseInt(tmp);
  }


  /**
   * Sets the contentLevel attribute of the HelpModule object
   *
   * @param tmp The new contentLevel value
   */
  public void setContentLevel(int tmp) {
    this.contentLevel = tmp;
  }


  /**
   * Sets the contentLevel attribute of the HelpModule object
   *
   * @param tmp The new contentLevel value
   */
  public void setContentLevel(String tmp) {
    this.contentLevel = Integer.parseInt(tmp);
  }


  /**
   * Sets the helpContents attribute of the HelpModule object
   *
   * @param tmp The new helpContents value
   */
  public void setHelpContents(ArrayList tmp) {
    this.helpContents = tmp;
  }


  /**
   * Gets the id attribute of the HelpModule object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the category attribute of the HelpModule object
   *
   * @return The category value
   */
  public String getCategory() {
    return category;
  }


  /**
   * Gets the categoryId attribute of the HelpModule object
   *
   * @return The categoryId value
   */
  public int getCategoryId() {
    return categoryId;
  }


  /**
   * Gets the briefDescription attribute of the HelpModule object
   *
   * @return The briefDescription value
   */
  public String getBriefDescription() {
    return briefDescription;
  }


  /**
   * Gets the detailDescription attribute of the HelpModule object
   *
   * @return The detailDescription value
   */
  public String getDetailDescription() {
    return detailDescription;
  }


  /**
   * Gets the helpContents attribute of the HelpModule object
   *
   * @return The helpContents value
   */
  public ArrayList getHelpContents() {
    return helpContents;
  }


  /**
   * Gets the insert attribute of the HelpContent object
   *
   * @return The insert value
   */
  public boolean getInsert() {
    return insert;
  }


  /**
   * Gets the pageId attribute of the HelpModule object
   *
   * @return The pageId value
   */
  public int getPageId() {
    return pageId;
  }


  /**
   * Gets the contentLevel attribute of the HelpModule object
   *
   * @return The contentLevel value
   */
  public int getContentLevel() {
    return contentLevel;
  }


  /**
   * Description of the Method
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("catId");
    category = rs.getString("category");
    categoryId = rs.getInt("catId");
    briefDescription = rs.getString("module_brief_description");
    detailDescription = rs.getString("module_detail_description");
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean insertModule(Connection db) throws SQLException {

    insert = true;
    id = DatabaseUtils.getNextSeq(db, "help_module_module_id_seq");
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO help_module " +
        "(" + (id > -1 ? "module_id, " : "") + "category_id, module_brief_description, module_detail_description) " +
        "VALUES (" + (id > -1 ? "?, " : "") + "?, ?, ?) ");
    int i = 0;
    if (id > -1) {
      pst.setInt(++i, id);
    }
    pst.setInt(++i, categoryId);
    pst.setString(++i, briefDescription);
    pst.setString(++i, detailDescription);
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "help_module_module_id_seq", id);
    Iterator itr = helpContents.iterator();
    while (itr.hasNext()) {
      HelpContent tmpHelpContent = (HelpContent) itr.next();
      tmpHelpContent.insertHelpContent(db, id, categoryId);
    }
    return true;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildHelpContents(Connection db) throws SQLException {
    System.out.println("Building Help Contents for:" + category);

    PreparedStatement pst = db.prepareStatement(
        "SELECT help_id, \"module\", \"section\", subsection, title, description, content_id, parent, contentlevel, contentorder " +
        "FROM help_contents hc, help_tableof_contents htc, help_tableofcontentitem_links hi " +
        "WHERE hc.category_id= ? " +
        "AND htc.content_id = hi.global_link_id " +
        "AND hi.linkto_content_id = hc.help_id " +
        "ORDER BY contentorder ASC ");
    pst.setInt(1, this.categoryId);
    ResultSet rs = pst.executeQuery();

    ArrayList itemsAtLevel3 = new ArrayList();
    ArrayList itemsAtLevel4 = new ArrayList();
    ArrayList itemsAtLevel5 = new ArrayList();
    while (rs.next()) {
      HelpContent tmpHelpContent = new HelpContent(rs);
      if (tmpHelpContent.getContentLevel() == 3) {
        itemsAtLevel3.add(tmpHelpContent);
      }
      if (tmpHelpContent.getContentLevel() == 4) {
        itemsAtLevel4.add(tmpHelpContent);
      }
      if (tmpHelpContent.getContentLevel() == 5) {
        itemsAtLevel5.add(tmpHelpContent);
      }
    }

    rs.close();
    pst.close();

    Iterator itr3 = itemsAtLevel3.iterator();

    while (itr3.hasNext()) {
      HelpContent tmpHelpContent3 = (HelpContent) itr3.next();
      helpContents.add(tmpHelpContent3);

      ArrayList childrenof3 = fetchChildren(tmpHelpContent3, itemsAtLevel4);
      Iterator itr4 = childrenof3.iterator();
      while (itr4.hasNext()) {
        HelpContent tmpHelpContent4 = (HelpContent) itr4.next();
        helpContents.add(tmpHelpContent4);

        ArrayList childrenof4 = fetchChildren(tmpHelpContent4, itemsAtLevel5);
        Iterator itr5 = childrenof4.iterator();
        while (itr5.hasNext()) {
          HelpContent tmpHelpContent5 = (HelpContent) itr5.next();
          helpContents.add(tmpHelpContent5);
        }
      }
    }

    helpContents.addAll(buildNonTocHelpContents(db));
    Iterator itr = helpContents.iterator();
    while (itr.hasNext()) {
      HelpContent tempHelpContent = (HelpContent) itr.next();
      tempHelpContent.buildDescription(db);
    }
  }


  /**
   * Include Help Contents that are not linked to the Table of Contents
   *
   * @param db Description of the Parameter
   * @return the list of helpContents not included in the TOC
   * @throws SQLException Description of the Exception
   */
  private ArrayList buildNonTocHelpContents(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "SELECT help_id, \"module\", \"section\", subsection, title, description " +
        "FROM help_contents hc " +
        "WHERE hc.category_id=? ");

    pst.setInt(1, this.categoryId);
    ResultSet rs = pst.executeQuery();

    ArrayList tempHelpContents = new ArrayList();
    while (rs.next()) {
      Iterator itr = helpContents.iterator();
      boolean found = false;

      int tempId = rs.getInt("help_id");
      while (itr.hasNext()) {
        HelpContent tempHelpContent = (HelpContent) itr.next();
        if (tempHelpContent.getId() == tempId) {
          found = true;
          break;
        }
      }

      if (found == false) {
        HelpContent tmpHelpContent = new HelpContent();
        tmpHelpContent.setId(tempId);
        tmpHelpContent.setAction(rs.getString("module"));
        tmpHelpContent.setSection(rs.getString("section"));
        tmpHelpContent.setSub(rs.getString("subsection"));
        tmpHelpContent.setTitle(rs.getString("title"));
        tmpHelpContent.setDescription(rs.getString("description"));
        tmpHelpContent.setContentLevel(0);

        tempHelpContents.add(tmpHelpContent);
      }
    }

    rs.close();
    pst.close();

    return tempHelpContents;
  }


  /**
   * Description of the Method
   *
   * @param tmpHelpItem Description of the Parameter
   * @param items       Description of the Parameter
   * @return Description of the Return Value
   */
  private ArrayList fetchChildren(HelpContent tmpHelpItem, ArrayList items) {
    Iterator itr = items.iterator();
    ArrayList tmpList = new ArrayList();

    while (itr.hasNext()) {
      HelpContent tmpItem = (HelpContent) itr.next();
      if (tmpItem.getParent() == tmpHelpItem.getTocId()) {
        tmpList.add(tmpItem);
      }
    }

    return tmpList;
  }


  /**
   * Description of the Method
   *
   * @param d Description of the Parameter
   * @return Description of the Return Value
   */
  public Node buildXML(Document d) {
    Element module = d.createElement("module");
    module.setAttribute("name", getCategory());
    module.setAttribute("contentLevel", String.valueOf(getContentLevel()));

    Node briefDescription = d.createElement("briefDescription");
    Node detailDescription = d.createElement("detailDescription");

    if (getBriefDescription() != null) {
      briefDescription.appendChild(
          d.createTextNode(XMLUtils.toXMLValue(getBriefDescription())));
    }
    if (getDetailDescription() != null) {
      detailDescription.appendChild(
          d.createTextNode(XMLUtils.toXMLValue(getDetailDescription())));
    }
    module.appendChild(briefDescription);
    module.appendChild(detailDescription);

    Iterator i = helpContents.iterator();
    int n = 0;
    while (i.hasNext()) {
      HelpContent tempHC = (HelpContent) i.next();
      module.appendChild(tempHC.buildXML(d));
      n++;
    }
    System.out.println("help items for " + getCategory() + " - " + n);

    return module;
  }
}


