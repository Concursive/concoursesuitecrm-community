package org.aspcfs.apps.help;
import java.io.*;
import java.sql.*;
import java.util.*;
import com.darkhorseventures.database.*;
import org.aspcfs.utils.DatabaseUtils;

/**
 *  Description of the Class
 *
 *@author     kbhoopal
 *@created    December 1, 2003
 *@version    $Id: TableOfContentItem.java,v 1.1.2.1 2003/12/02 20:29:04
 *      kbhoopal Exp $
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
   *  Constructor for the TableOfContentItem object
   */
  public TableOfContentItem() { }


  /**
   *  Constructor for the TableOfContentItem object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public TableOfContentItem(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Sets the id attribute of the TableOfContentItem object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the TableOfContentItem object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the categoryId attribute of the TableOfContentItem object
   *
   *@param  tmp  The new categoryId value
   */
  public void setCategoryId(int tmp) {
    this.categoryId = tmp;
  }


  /**
   *  Sets the displayText attribute of the TableOfContentItem object
   *
   *@param  tmp  The new displayText value
   */
  public void setDisplayText(String tmp) {
    this.displayText = tmp;
  }


  /**
   *  Sets the parent attribute of the TableOfContentItem object
   *
   *@param  tmp  The new parent value
   */
  public void setParent(int tmp) {
    this.parent = tmp;
  }


  /**
   *  Sets the level attribute of the TableOfContentItem object
   *
   *@param  tmp  The new level value
   */
  public void setLevel(int tmp) {
    this.level = tmp;
  }


  /**
   *  Sets the order attribute of the TableOfContentItem object
   *
   *@param  tmp  The new order value
   */
  public void setOrder(int tmp) {
    this.order = tmp;
  }


  /**
   *  Sets the pageId attribute of the TableOfContentItem object
   *
   *@param  tmp  The new pageId value
   */
  public void setPageId(int tmp) {
    this.pageId = tmp;
  }



  /**
   *  Gets the id attribute of the TableOfContentItem object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the categoryId attribute of the TableOfContentItem object
   *
   *@return    The categoryId value
   */
  public int getCategoryId() {
    return categoryId;
  }


  /**
   *  Gets the displayText attribute of the TableOfContentItem object
   *
   *@return    The displayText value
   */
  public String getDisplayText() {
    return displayText;
  }


  /**
   *  Gets the parent attribute of the TableOfContentItem object
   *
   *@return    The parent value
   */
  public int getParent() {
    return parent;
  }


  /**
   *  Gets the level attribute of the TableOfContentItem object
   *
   *@return    The level value
   */
  public int getLevel() {
    return level;
  }


  /**
   *  Gets the order attribute of the TableOfContentItem object
   *
   *@return    The order value
   */
  public int getOrder() {
    return order;
  }


  /**
   *  Gets the pageId attribute of the TableOfContentItem object
   *
   *@return    The pageId value
   */
  public int getPageId() {
    return pageId;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Table of Content Id for the item
   *@exception  SQLException  Description of the Exception
   */
  int insert(Connection db) throws SQLException {

    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO help_tableof_contents " +
        "(displaytext" +
        ((categoryId == -1) ? "" : ",category_id ") + ((parent == -1) ? "" : ",parent") +
        ",contentlevel,contentorder, enteredby, modifiedby) " +
        "VALUES (?" + ((categoryId == -1) ? "" : ",?") + ((parent == -1) ? "" : ",?") + ",?,?,?,?)");

    int i = 0;
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

    id = DatabaseUtils.getCurrVal(db, "help_tableof_contents_content_id_seq");

    // inserting a link to a TOC item if a link page exists
    // Link page may not exist for top level items
    if (pageId != -1) {
      pst = db.prepareStatement(
          "INSERT INTO help_tableofcontentitem_links " +
          "(global_link_id, linkto_content_id,enteredby, modifiedby) " +
          "VALUES (?,?,?,?)");

      i = 0;

      pst.setInt(++i, id);
      pst.setInt(++i, pageId);
      pst.setInt(++i, 0);
      pst.setInt(++i, 0);
      pst.execute();
      pst.close();
    }
    return id;
  }



  /**
   *  Description of the Method
   */
  public void buildRecord() {
    id = 1;
    displayText = "Modules";
    order = 5;
    level = 1;

  }


  /**
   *  Description of the Method
   *
   *@param  tmpCategory    Description of the Parameter
   *@param  tmpCategoryId  Description of the Parameter
   *@param  tmpOrder       Description of the Parameter
   */
  public void buildRecord(String tmpCategory, int tmpCategoryId, int tmpOrder) {

    categoryId = tmpCategoryId;
    displayText = tmpCategory;
    level = 2;
    parent = 1;
    order = tmpOrder;

  }



  /**
   *  Description of the Method
   *
   *@param  tmpCategoryId  Description of the Parameter
   *@param  tmpTitle       Description of the Parameter
   *@param  tmpOrder       Description of the Parameter
   *@param  tmpLevel       Description of the Parameter
   *@param  tmpPageId      Description of the Parameter
   */
  public void buildRecord(int tmpCategoryId, String tmpTitle, int tmpOrder, int tmpLevel, int tmpPageId) {

    categoryId = tmpCategoryId;
    displayText = tmpTitle;
    level = tmpLevel;
    order = tmpOrder;
    pageId = tmpPageId;

  }


  /**
   *  Description of the Method
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
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


