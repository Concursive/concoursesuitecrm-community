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
package org.aspcfs.modules.admin.base;

import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.XMLUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.servlet.ServletContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * Description of the Class
 *
 * @author Ananth
 * @created November 3, 2005
 */
public class CustomListViewEditor {
  private int id = -1;
  private int moduleId = -1;
  private int constantId = -1;
  private String description = null;
  private int categoryId = -1;
  //View Fields
  private LinkedHashMap fields = new LinkedHashMap();
  public final static String fs = System.getProperty("file.separator");

  /**
   * Gets the fields attribute of the CustomListViewEditor object
   *
   * @return The fields value
   */
  public LinkedHashMap getFields() {
    return fields;
  }


  /**
   * Sets the fields attribute of the CustomListViewEditor object
   *
   * @param tmp The new fields value
   */
  public void setFields(LinkedHashMap tmp) {
    this.fields = tmp;
  }


  /**
   * Gets the id attribute of the CustomListViewEditor object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Sets the id attribute of the CustomListViewEditor object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the CustomListViewEditor object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Gets the moduleId attribute of the CustomListViewEditor object
   *
   * @return The moduleId value
   */
  public int getModuleId() {
    return moduleId;
  }


  /**
   * Sets the moduleId attribute of the CustomListViewEditor object
   *
   * @param tmp The new moduleId value
   */
  public void setModuleId(int tmp) {
    this.moduleId = tmp;
  }


  /**
   * Sets the moduleId attribute of the CustomListViewEditor object
   *
   * @param tmp The new moduleId value
   */
  public void setModuleId(String tmp) {
    this.moduleId = Integer.parseInt(tmp);
  }


  /**
   * Gets the constantId attribute of the CustomListViewEditor object
   *
   * @return The constantId value
   */
  public int getConstantId() {
    return constantId;
  }


  /**
   * Sets the constantId attribute of the CustomListViewEditor object
   *
   * @param tmp The new constantId value
   */
  public void setConstantId(int tmp) {
    this.constantId = tmp;
  }


  /**
   * Sets the constantId attribute of the CustomListViewEditor object
   *
   * @param tmp The new constantId value
   */
  public void setConstantId(String tmp) {
    this.constantId = Integer.parseInt(tmp);
  }


  /**
   * Gets the description attribute of the CustomListViewEditor object
   *
   * @return The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   * Sets the description attribute of the CustomListViewEditor object
   *
   * @param tmp The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   * Gets the categoryId attribute of the CustomListViewEditor object
   *
   * @return The categoryId value
   */
  public int getCategoryId() {
    return categoryId;
  }


  /**
   * Sets the categoryId attribute of the CustomListViewEditor object
   *
   * @param tmp The new categoryId value
   */
  public void setCategoryId(int tmp) {
    this.categoryId = tmp;
  }


  /**
   * Sets the categoryId attribute of the CustomListViewEditor object
   *
   * @param tmp The new categoryId value
   */
  public void setCategoryId(String tmp) {
    this.categoryId = Integer.parseInt(tmp);
  }


  /**
   * Constructor for the CustomListViewEditor object
   */
  public CustomListViewEditor() { }


  /**
   * Constructor for the CustomListViewEditor object
   *
   * @param db         Description of the Parameter
   * @param constantId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public CustomListViewEditor(Connection db, int constantId) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "SELECT * " +
            "FROM custom_list_view_editor " +
            "WHERE constant_id = ? ");
    pst.setInt(1, constantId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
  }


  /**
   * Constructor for the CustomListViewEditor object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public CustomListViewEditor(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Description of the Method
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("editor_id");
    moduleId = rs.getInt("module_id");
    constantId = rs.getInt("constant_id");
    description = rs.getString("description");
    categoryId = rs.getInt("category_id");
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   */
  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append("-------------------------------\r\n");
    sb.append("Editor Id: " + id + "\r\n");
    sb.append("Module Id: " + moduleId + "\r\n");
    sb.append("Constant Id: " + constantId + "\r\n");
    sb.append("Description: " + description + "\r\n");
    sb.append("Category Id: " + categoryId + "\r\n");
    sb.append("-------------------------------\r\n");
    return sb.toString();
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void insert(Connection db) throws SQLException {
    id = DatabaseUtils.getNextSeq(db, "custom_list_view_editor_editor_id_seq");
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO custom_list_view_editor " +
            "(" + (id > -1 ? "editor_id, " : "") + "module_id, constant_id, description, category_id) " +
            "VALUES (" + (id > -1 ? "?, " : "") + "?, ?, ?, ?) ");
    int i = 0;
    if (id > -1) {
      pst.setInt(++i, id);
    }
    pst.setInt(++i, moduleId);
    pst.setInt(++i, constantId);
    pst.setString(++i, description);
    pst.setInt(++i, categoryId);
    pst.executeUpdate();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "custom_list_view_editor_editor_id_seq", id);
  }


  /**
   * Description of the Method
   *
   * @param context         Description of the Parameter
   * @param db         Description of the Parameter
   * @param webinfPath Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void build(ServletContext context, Connection db, String webinfPath) throws SQLException {
    if (System.getProperty("DEBUG") != null) {
      System.out.println(
          "CustomListViewEditor-> Loading editor(" + this.getDescription() + ") fields: " + webinfPath + "cfs-customlistview-fields.xml");
    }
    fields.clear();
    try {
      if (webinfPath != null) {
        XMLUtils xml = new XMLUtils(context, webinfPath + "cfs-customlistview-fields.xml");
        if (xml.getDocument() != null) {
          ArrayList viewElements = new ArrayList();
          //Traverse the file and add the fields under the required view to the LinkedHashMap
          XMLUtils.getAllChildren(
              xml.getFirstChild("views"), "view", viewElements);
          Iterator views = viewElements.iterator();

          //load the view
          while (views.hasNext()) {
            //Get the map node
            Element view = (Element) views.next();
            if (view.getAttribute("constantId").equals(String.valueOf(this.constantId))) {
              NodeList nl = view.getChildNodes();
              for (int i = 0; i < nl.getLength(); i++) {
                Node n = nl.item(i);
                if (n.getNodeType() == Node.ELEMENT_NODE && ((Element) n).getTagName().equals(
                    "field")) {
                  CustomListViewField thisField = new CustomListViewField(n);
                  //thisField.setUniqueName(constantId + "." + thisField.getName());
                  if (thisField.isValid()) {
                    this.addField(thisField);
                  }
                }
              }
              break;
            }
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }
  }


  /**
   * Adds a feature to the Field attribute of the CustomListViewEditor object
   *
   * @param thisField The feature to be added to the Field attribute
   * @return Description of the Return Value
   */
  public boolean addField(CustomListViewField thisField) {
    if (!fields.containsKey(thisField.getName())) {
      fields.put(thisField.getName(), thisField);
    } else {
      return false;
    }
    return true;
  }
}

